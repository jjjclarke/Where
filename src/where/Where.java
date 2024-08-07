package where;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class Where {

	// method to execute a command
	private static String exec(String command) {
		StringBuilder output = new StringBuilder();
		Process process;
		try {
			process = Runtime.getRuntime().exec(new String[] {"sh", "-c", command});
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			char[] buffer = new char[16];
			int numRead;
			while ((numRead = reader.read(buffer)) > 0) {
				output.append(buffer, 0, numRead);
			}
			reader.close();
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return output.toString();
	}
	
	private static String checkCPU() {
		String cpu = exec("grep -m 1 'model name' /proc/cpuinfo | awk -F: '{ print $2 }' | sed 's/^ *//'\n");
		
		return cpu;
	}
	
	private static String checkGPU() {
		// different commands for different GPU vendors
		String[] commands = {
			"nvidia-smi --query-gpu=name --format=csv,noheader", // nvidia always being different
			"lspci | grep VGA | grep -i amd | awk -F: '{ print $3 }' | sed 's/^ *//'", // amd
			"lspci | grep VGA | grep -i intel | awk -F: '{ print $3 }' | sed 's/^ *//'", // intel
			"lspci | grep -E 'VGA|3D' | awk -F: '{ print $3 }' | sed 's/^ *//'" // generic
		};
		
		for (int i = 0; i < commands.length; i++) {
			String output = exec(commands[i]);
			
			if (!output.isEmpty()) {
				return output;
			}
		}
		
		return "No GPU information could be found. Disable SHOW_GPU in Flags.java\n"; // newline for error
	}
	
	private static String checkOS() {
		String os = exec("cat /etc/os-release");
		
		String osName;
		
		if (os.contains("FreeBSD")) {
			osName = "FreeBSD";
		} else if (os.contains("Gentoo")) {
			osName = "Gentoo";
		} else if (os.contains("Arch")) {
			osName = "Arch Linux";
		} else if (os.contains("Void Linux")) {
			osName = "Void Linux";
		} else if (os.contains("Ubuntu")) {
			osName = "Ubuntu";
		} else if (os.contains("Debian")) {
			osName = "Debian";
		} else if (os.contains("Manjaro")) {
			osName = "Manjaro";
		} else {
			osName = "Unsupported OS";
		}
		
		return osName;
	}
	
	private static String checkShell() {
		String a = exec("printf $SHELL");
		String b = a.replaceAll(Pattern.quote("/bin/"), "");
		
		return b;
	}
	
	private static String checkPkgs() {
		StringBuilder output = new StringBuilder();
		
		if (new File("/usr/bin/emerge").exists()) {
			output.append(exec("echo -n $(cd /var/db/pkg && ls -d */* | wc -l)"));
			output.append(" ( emerge ) ");
		}
		
		else if (new File("/usr/bin/pacman").exists()) {
			output.append(exec("echo -n $(pacman -Qq | wc -l)"));
			output.append(" ( pacman ) ");
		}
		
		else if (new File("/usr/bin/xbps-install").exists()) {
			output.append(exec("echo -n $(xbps-query -l | wc -l)"));
			output.append(" ( xbps ) ");
		}
		
		else if (new File("/usr/sbin/pkg_info").exists()) {
			output.append(exec("echo -n $(pkg_info | wc -l)"));
			output.append(" ( pkg ) ");
		}
		
		else if (new File("/usr/bin/dpkg-query").exists()) {
			output.append(exec("echo -n $(dpkg-query -f '.\\n' -W | wc -l)"));
			output.append(" ( xbps ) ");
		}
		
		else if (new File("/usr/bin").exists()) {
			output.append(exec("echo -n $(ls /usr/bin | wc -l)"));
			output.append(" ( all packages ) ");
		}
		
		return output.toString();
	}
	
	public static void main(String[] args) {
		if (Flags.SHOW_HOSTNAME) {
			Colour.printPurple(exec("echo -n $USER@$HOSTNAME"));
			
			System.out.println(); // newline
		}
		if (Flags.SHOW_CPU) {
			Colour.printWhite("cpu: ");
			Colour.printCyan(checkCPU());
			// no newline needed here for some??? reason
		}
		if (Flags.SHOW_GPU) {
			Colour.printWhite("gpu: ");
			Colour.printCyan(checkGPU());
		}
		if (Flags.SHOW_OS) {
			Colour.printWhite("os: ");
			Colour.printCyan(checkOS());
			
			System.out.println(); // newline
		}
		if (Flags.SHOW_SHELL) {
			Colour.printWhite("shell: ");
			Colour.printCyan(checkShell());
			
			System.out.println(); // newline
		}
		if (Flags.SHOW_PKGS) {
			Colour.printWhite("packages: ");
			Colour.printCyan(checkPkgs());
			
			System.out.println(); // newline
		}
		if (Flags.SHOW_KERNEL) {
			Colour.printWhite("kernel: ");
			Colour.printCyan(exec("uname -r"));
			
			System.out.println(); // newline
		}
	}
}
