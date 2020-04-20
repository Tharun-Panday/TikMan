import java.util.Scanner;
/**
* Admin Class is responsible for all the facilities responsible for Admins
*@param args null
* @author  Yukti
* @version 1.0
* @since   2020-03-31 
*/
public class AdminClass 
	{
	String userChoice;
	Scanner userInputScanner;
	TicketClass ticketClassInstance;
	EmployeeClass employeeClassInstance;
	
	public AdminClass() {
		// TODO Auto-generated constructor stub
		userInputScanner= new Scanner(System.in);
		ticketClassInstance=new TicketClass();
		employeeClassInstance=new EmployeeClass();
		showMenu();
	}
/**
   * showMenu method is used by user to select choices given in the menu and accordingly action will be performed in the system.
   * @param args null
   * @returm null
   */
	
	void showMenu()
	{
		do {
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tAdmin\n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t1.Create Ticket \n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t2.Update Ticket\n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t3.Delete Ticket\n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t4.View Ticket\n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t5.Search Ticket\n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t6.Resolve Assigned Ticket\n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t7.View Assigned Ticket\n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t8.Search Assigned Ticket\n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t9.Create Employee");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t10.Update Employee");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t11.View Employee");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t12.Search Employee");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t13.Delete Employee");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t14.Log Out\n");
			System.out.print("\t\t\t\tEnter Your Choice: ");
			userChoice=userInputScanner.next();
			
			while (!userChoice.matches("^[1-9][0-4]*$")) {
				System.out.print("\n\t\t\t\tEnter Valid Choice: ");
				userChoice=userInputScanner.next();
			}
			while (!(Integer.valueOf(userChoice)>0 && Integer.valueOf(userChoice)<17)) {
				System.out.print("\n\t\t\t\tEnter Valid Choice: ");
				userChoice=userInputScanner.next();
			}
			switch (Integer.valueOf(userChoice)) {
				case 1:
				{
					ticketClassInstance.create_ticket_module();
					break;
				}
				case 2:
				{
					ticketClassInstance.update_ticket_module();
					break;
				}
				case 3:
				{
					ticketClassInstance.delete_ticket_module();
					break;
				}
				case 4:
				{
					ticketClassInstance.viewAll_ticket_module();
					break;
				}
				case 5:
				{
					ticketClassInstance.search_ticket_module(false,false);
					break;
				}	
				case 6:
				{
					ticketClassInstance.resolveAssignedTicket();
					break;
				}	
				case 7:
				{
					ticketClassInstance.viewAssignedTicket();
					break;
				}	
				case 8:
				{
					ticketClassInstance.searchAssignedTicket();
					break;
				}	
				case 9:
				{
					employeeClassInstance.createEmployee();
					break;
				}	
				case 10:
				{
					employeeClassInstance.updateEmployee();
					break;
				}	
				case 11:
				{
					employeeClassInstance.viewAllEmployee();
					break;
				}	
				case 12:
				{
					employeeClassInstance.searchEmployee(false);
					break;
				}	
				case 13:
				{
					employeeClassInstance.deleteEmployee();
					break;
				}	
				
			}
		}while (Integer.valueOf(userChoice)!=14);
		LoginClass.USER_ID="";
		LoginClass.ROLE="";
		LoginClass.DEPARTMENT="";
		new LoginClass();
		
	}

}
