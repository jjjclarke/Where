package where;

public class Flags {

	// toggleable options
	
	// Show the computer hostname (e.g. "john@arch")
	// DEFAULT: true
	public static boolean SHOW_HOSTNAME = true;
	
	// Show the CPU architecture (e.g. AMD Athlon Gold 3150U with Radeon Graphics)
	// DEFAULT: false
	public static boolean SHOW_CPU = false;
	
	// Show the GPU (e.g. [AMD/ATI] Picasso/Raven 2) - not recommended to use
	// DEFAULT: false
	public static boolean SHOW_GPU = true;
	
	// Show the operating system in use (e.g. Arch Linux)
	// DEFAULT: true
	public static boolean SHOW_OS = true;
	
	// Show the current shell in use (e.g. bash)
	// DEFAULT: true
	public static boolean SHOW_SHELL = true;
	
	// Show the number of packages installed via package managers (e.g. 500)
	// DEFAULT: true
	public static boolean SHOW_PKGS = true;
	
	// Show the running Linux kernel version (e.g. 6.4.10.5-manjaro)
	// DEFAULT: true
	public static boolean SHOW_KERNEL = true;
	
}
