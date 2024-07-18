package where;

public class Colour {
	// ANSI escape codes - a great list provided by user
	// shakram02 on StackOverflow
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	
	private static final String RESET = "\033[0m";
	
	private static final String RED = "\033[31m";
	private static final String GREEN = "\033[32m";
	private static final String YELLOW = "\033[33m";
	private static final String BLUE = "\033[34m";
	private static final String PURPLE = "\033[35m";
	private static final String CYAN = "\033[36m";
	private static final String WHITE = "\033[37m";
	
	public static void printRed(String msg) {
		System.out.print(RED + msg + RESET);
	}
	
	public static void printGreen(String msg) {
		System.out.print(GREEN + msg + RESET);
	}
	
	public static void printYellow(String msg) {
		System.out.print(YELLOW + msg + RESET);
	}
	
	public static void printBlue(String msg) {
		System.out.print(BLUE + msg + RESET);
	}
	
	public static void printPurple(String msg) {
		System.out.print(PURPLE + msg + RESET);
	}
	
	public static void printCyan(String msg) {
		System.out.print(CYAN + msg + RESET);
	}
	
	public static void printWhite(String msg) {
		System.out.print(WHITE + msg + RESET);
	}
}
