import java.util.Scanner;

public class CommandUI {

	public static void main(String[] args) {


		System.out.println("Hello, welcome to program.");
		System.out.println("Are you a 'manager' or 'trader':");
		
		Scanner input = new Scanner(System.in);
		String userType = input.nextLine().toLowerCase();
		
		while ((!userType.equals("manager")) || (!userType.equals("trader"))) {
			System.out.println("Cannot understand your input, please try again:");
			userType = input.nextLine().toLowerCase();
		}
		
		if (userType.equals("manager")) {
			Manager manager = new Manager();
			
			while(manager.isInSession()) {
				
				
				System.out.println("What would you like to do?");
				System.out.println("Here are the available actions.");
				
				String action = input.nextLine().toLowerCase();
			}
		}
		else {	// userType.equals("trader")
			Trader trader = new Trader();
			
			while(trader.isInSession()) {
				System.out.println("What would you like to do?");
				System.out.println("Here are the available actions.");
				
				String action = input.nextLine().toLowerCase();
			}
		}
		
		input.close();
	}
}
