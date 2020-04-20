import java.util.Scanner;

/**
 * 
 */

/**
 * @author Tarun
 *
 */
public class SuperAdminClass {

	String userChoice;
	Scanner userInputScanner;
	TicketClass ticketClassInstance;
	EmployeeClass employeeClassInstance;
	DepartmentClass departmentClassInstance;
	/**
	 * 
	 */
	public SuperAdminClass() {
		// TODO Auto-generated constructor stub
		
		userInputScanner= new Scanner(System.in);
		ticketClassInstance=new TicketClass();
		employeeClassInstance=new EmployeeClass();
		departmentClassInstance=new DepartmentClass();
		showMenu();
	}
	
	public void showMenu()
	{
		do {
			System.out.println("\t\t\t\t\t\t\t\tSuper Admin Menu\n");
			System.out.println("\t\t\t\t\t\t\t\t1.Create Ticket");
			System.out.println("\t\t\t\t\t\t\t\t2.Update Ticket");
			System.out.println("\t\t\t\t\t\t\t\t3.View Tickets");
			System.out.println("\t\t\t\t\t\t\t\t4.Search Ticket");
			System.out.println("\t\t\t\t\t\t\t\t5.Delete Ticket");
			System.out.println("\t\t\t\t\t\t\t\t6.CREATE Employee");
			System.out.println("\t\t\t\t\t\t\t\t7.DELETE Employee");
			System.out.println("\t\t\t\t\t\t\t\t8.UPDATE Employee");
			System.out.println("\t\t\t\t\t\t\t\t9.SEARCH Employee");
			System.out.println("\t\t\t\t\t\t\t\t10.View Employee");
			System.out.println("\t\t\t\t\t\t\t\t11.Create Department");
			System.out.println("\t\t\t\t\t\t\t\t12.Update Department");
			System.out.println("\t\t\t\t\t\t\t\t13.View Department");
			System.out.println("\t\t\t\t\t\t\t\t14.Search Department");
			System.out.println("\t\t\t\t\t\t\t\t15.Delete Department");
			System.out.println("\t\t\t\t\t\t\t\t16.Log Out\n");
			System.out.print("\t\t\t\tEnter Your Choice: ");
			userChoice=userInputScanner.next();
			
			while (!userChoice.matches("^[1-9][0-6]*$")) {
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
					ticketClassInstance.viewAll_ticket_module();
					break;
				}
				case 4:
				{
					ticketClassInstance.search_ticket_module(false,false);
					break;
				}
				case 5:
				{
					ticketClassInstance.delete_ticket_module();
					break;
				}
				case 6:
				{
					employeeClassInstance.createEmployee();
					break;
				}
				case 7:
				{
					employeeClassInstance.deleteEmployee();
					break;
				}
				case 8:
				{
					employeeClassInstance.updateEmployee();
					break;
				}
				case 9:
				{
					employeeClassInstance.searchEmployee(false);
					break;
				}
				case 10:
				{
					employeeClassInstance.viewAllEmployee();
					break;
				}
				case 11:
				{
					departmentClassInstance.createDeparment();
					break;
				}
				case 12:
				{
					departmentClassInstance.updateDepartment();
					break;
				}
				case 13:
				{
					departmentClassInstance.viewAllDepartment();
					break;
				}
				case 14:
				{
					departmentClassInstance.searchDepartment(false);
					break;
				}
				case 15:
				{
					departmentClassInstance.deleteDepartment();
					break;
				}	
			}
		} while (Integer.valueOf(userChoice)!=16);
		LoginClass.USER_ID="";
		LoginClass.ROLE="";
		LoginClass.DEPARTMENT="";
		new LoginClass();
	}
}
