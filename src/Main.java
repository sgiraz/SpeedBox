public class Main {
	public static void main(String[] args) {
		String winScopeAddress = Utils.windowsReadRegistry("HKEY_LOCAL_MACHINE\\SYSTEM\\CurrentControlSet\\Services\\SharedAccess\\Parameters", "ScopeAddress");
		System.out.println(winScopeAddress);
		new SendBoxGUI();
	}
}
