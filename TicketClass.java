import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * This class demonstrates the TicketClass.
 * @author Tarun
 * @version 1.0
 * date created 2020-04-10
 */
public class TicketClass {

	Scanner userInputScanner;
	String ticket_id,ticketDescription,ticketCurrentStatus,ticketDepartment,department_name,department_id;
	ArrayList<String> whereClauseColumns, args, columns;
	LinkedHashMap<Object,String> values,argsValues;
	
	public TicketClass() {
		// TODO Auto-generated constructor stub
		userInputScanner= new Scanner(System.in);
		columns=new ArrayList<String>();
		whereClauseColumns=new ArrayList<String>();
		args=new ArrayList<String>();
		values=new LinkedHashMap<Object, String>();
		argsValues=new LinkedHashMap<Object, String>();
	}
	
	/**
       * This method is responsible for search ticket module.
	 * @param used in this method are isUpdate, showAssigned. 
	 * @return the boolean value.
	 */
	public boolean search_ticket_module(boolean isUpdate, boolean showAssigned) {
		// TODO Auto-generated method stub
		System.out.print("\n Enter the Ticket Id: ");
		ticket_id=userInputScanner.next();				
		whereClauseColumns.clear();
		args.clear();		
		
		while (!ticket_id.matches("^[1-9][0-9]*$")) {
			System.out.print("\n Enter a valid Ticket Id: ");
			ticket_id=userInputScanner.next();
		}
		whereClauseColumns.add(ConstantsClass.tiket_col_1);
		args.add(ticket_id);
		ResultSet getTicketResultSet=DbConnectorClass.getInstanceClass()
				.getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.Ticket_TABLE,
						null, whereClauseColumns, args);
		if (getTicketResultSet!=null) {
			try {
				if (getTicketResultSet.last()&&getTicketResultSet.getRow()==1) {
					getTicketResultSet.first();
					showTicket(getTicketResultSet, isUpdate,showAssigned);
					return true;
				}
				else {
					System.out.print("\n\t\t Ticket Search failed");
					return false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block	
				System.out.print("\n\t\t Ticket Search failed");
				return false;
			}
		}
		return false;
	}

	/**
	 * This method is responsible for delete ticket module.
      * @return the string value according to the action performed by the user.
	*/
	public void delete_ticket_module() {
		// TODO Auto-generated method stub
		if (search_ticket_module(false,false)) {
			System.out.println("\n Do you want to delete this record(yes|no):");
			String userChoiceString=userInputScanner.next();
			while (!(userChoiceString.equals("yes")|userChoiceString.equals("no"))) {
				System.out.println("\nPlease enter valid option(yes|no): ");
				userChoiceString=userInputScanner.next();
			}
			if (userChoiceString.equals("yes")) {
				columns=new ArrayList<String>();
				columns.add(ConstantsClass.tiket_col_1);
				values=new LinkedHashMap<Object, String>();
				values.put(new Integer(Integer.valueOf(ticket_id)),ticket_id);
				DbConnectorClass.getInstanceClass().deleteRecords(ConstantsClass.Ticket_TABLE,
						columns, values);
				System.out.print("\n\t\t Ticket delete failed");
			}
			
		}
		else {
			System.out.println("\n Ticket not found!");
			delete_ticket_module();
		}
	}

	/**
	 * This method is responsible for view all ticket module.
	 */
	public void viewAll_ticket_module() {
		// TODO Auto-generated method stub
		whereClauseColumns.clear();
		args.clear();
		whereClauseColumns.add(ConstantsClass.tiket_col_6);
		args.add(LoginClass.USER_ID);
		ResultSet getDataResultSet=DbConnectorClass.getInstanceClass()
			.getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.Ticket_TABLE, 
					null, whereClauseColumns, args);
		if (getDataResultSet!=null) {
			
			try {
				while (getDataResultSet.next()) {
					showTicket(getDataResultSet, false,false);					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * This method is responsible for update ticket module.
       * @param used under this method -- userChoice
	 */
	public void update_ticket_module() {
		// TODO Auto-generated method stub
		
		int userChoice;
		if (search_ticket_module(true,false)) {
			System.out.println("\nEnter your choice: ");
		 	 
			 while (!userInputScanner.hasNextInt()) {
				 System.out.println("\nEnter valid choice: ");
				 userInputScanner.next();							 
		 	 }		
			 userChoice = userInputScanner.nextInt();
			 while (!(userChoice>0 && userChoice<3)) {
					System.out.println("\nEnter valid choice: ");
					 userInputScanner.next();	
					 while (!userInputScanner.hasNextInt()) {
						 System.out.println("\nEnter valid choice: ");
						 userInputScanner.next();								 
				 	 }		
					 userChoice = userInputScanner.nextInt();
				}

			
			if (userChoice==1) {
				//Enter data using BufferReader 
				BufferedReader reader = 
						new BufferedReader(new InputStreamReader(System.in)); 
				
				// Reading data using readLine 
				System.out.println("\nEnter ticket Description: ");
				try {
					ticketDescription=reader.readLine();							
					columns=new ArrayList<String>();
					whereClauseColumns=new ArrayList<String>();
					columns.add(ConstantsClass.tiket_col_2);
					whereClauseColumns.add(ConstantsClass.tiket_col_1);
					values=new LinkedHashMap<Object,String>(); 
					argsValues=new LinkedHashMap<Object,String>();
					values.put(new String(ticketDescription),ticketDescription);
					argsValues.put(new Integer(ticket_id),ticket_id);
					DbConnectorClass.getInstanceClass().updateRecords(ConstantsClass.
					Ticket_TABLE, columns, whereClauseColumns, values, argsValues);
					System.out.print("\n\t\t Ticket update failed");					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.print("\n\t\t Ticket update failed");
					e1.printStackTrace();
				}																		
			}
			else if (userChoice==2) {
				System.out.println("\nEnter status(resolved|deffered): ");
				try {
					ticketCurrentStatus=userInputScanner.next();	
					while (!(ticketCurrentStatus.matches("^(resolved|deffered)$"))) {
						System.out.println("\nPlease enter valid status: ");
						ticketCurrentStatus=userInputScanner.next();
					}
					columns.clear();
					whereClauseColumns.clear();
					columns.add(ConstantsClass.tiket_col_2);
					whereClauseColumns.add(ConstantsClass.tiket_col_1);
					values.clear();
					argsValues.clear();
					values.put(new String(ticketDescription),ticketDescription);
					argsValues.put(new Integer(ticket_id),ticket_id);
					DbConnectorClass.getInstanceClass().updateRecords(ConstantsClass.
					Ticket_TABLE, columns, whereClauseColumns, values, argsValues);
					System.out.print("\n\t\t Ticket update success");				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.print("\n\t\t Ticket update failed");
					e1.printStackTrace();
				}	
			}
			else if (userChoice==3) {
				//Enter data using BufferReader 
				BufferedReader reader = 
						new BufferedReader(new InputStreamReader(System.in)); 
				
				// Reading data using readLine 
				try {
					System.out.print("\n Enter Department name: ");
					department_name=reader.readLine(); 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				department_id="0";
				
				boolean flag=false;
				while (!flag) {
						columns=new ArrayList<String>();
						whereClauseColumns=new ArrayList<String>();
						args=new ArrayList<String>();
						columns.add(ConstantsClass.department_col_1);
						whereClauseColumns.add(ConstantsClass.department_col_2);
						args.add(department_name);
						ResultSet rSet=DbConnectorClass.getInstanceClass()
								.getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.Department_TABLE,
										columns, whereClauseColumns, args);
						
						if (rSet!=null) {
							try {
								if (rSet.last()&&rSet.getRow()==1) {
									rSet.first();
									department_id=String.valueOf(rSet.getInt(1));
									flag=true;
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					
					if (!flag) {
						System.out.print("\nEnter valid department name: ");
						department_name=userInputScanner.nextLine();
					}
				}
				columns.clear();
				whereClauseColumns.clear();
				columns.add(ConstantsClass.tiket_col_5);
				whereClauseColumns.add(ConstantsClass.tiket_col_1);
				values.clear(); 
				argsValues.clear();
				values.put(new String(department_id),department_id);
				argsValues.put(new Integer(ticket_id),ticket_id);
				DbConnectorClass.getInstanceClass().updateRecords(ConstantsClass.
				Ticket_TABLE, columns, whereClauseColumns, values, argsValues);
				System.out.print("\n\t\t Ticket update success");
			}
		}
		else {
			update_ticket_module();
		}
		
	}
/** 
  * This method is responsible for show all the details of tickets.
  * @param used in method are isUpdate, showAssigned.
  */
	void showTicket(ResultSet getTicketResultSet,boolean isUpdate,boolean showAssigned)
	{
		try {
			String subMenuString="";
			if (isUpdate) {
				subMenuString="\n\nTicket id: "+getTicketResultSet.getInt(1)+"\n1.Ticket Description: "
						 +getTicketResultSet.getString(2)+"\n2.Current Status: "+getTicketResultSet.getString(4)+"\n3.Assigned Department : ";
			}
			else {
				subMenuString="\n\nTicket id: "+getTicketResultSet.getInt(1)+"\nTicket Description: "
						 +getTicketResultSet.getString(2)+"\nCurrent Status: "+getTicketResultSet.getString(4)+"\nAssigned Department : ";
			}		
			 columns.clear();
			 whereClauseColumns.clear();
			 args.clear();
			 columns.add(ConstantsClass.department_col_2);
			 whereClauseColumns.add(ConstantsClass.department_col_1);
			 args.add(String.valueOf(getTicketResultSet.getInt(5)));
			 ResultSet getDepartmentNameResultSet=DbConnectorClass.getInstanceClass()
					 .getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.Department_TABLE,
							 columns, whereClauseColumns, args);
			 if (getDepartmentNameResultSet!=null) {	
				 getDepartmentNameResultSet.first();
				 subMenuString=subMenuString+getDepartmentNameResultSet.getString(1);
			}
			 subMenuString=subMenuString+"\nCreated On : "+getTicketResultSet.getDate(3);
			 if (showAssigned) {
				subMenuString=subMenuString+"\nTicket Owner: "+getTicketResultSet.getString(6);
			}
			 System.out.print(subMenuString);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
/**
  * This method is responsible for create ticket module.
  */ 
 
	void create_ticket_module()
	{			
		//Enter data using BufferReader 
		BufferedReader reader = 
				new BufferedReader(new InputStreamReader(System.in)); 
		
		// Reading data using readLine 
		System.out.println("\nEnter ticket Description: ");
		try {
			ticketDescription=reader.readLine();
			System.out.print("\n Enter Department name: ");
			department_name=reader.readLine(); 
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		department_id="0";
		
		boolean flag=false;
		while (!flag) {
				columns=new ArrayList<String>();
				whereClauseColumns=new ArrayList<String>();
				columns.add(ConstantsClass.department_col_1);
				whereClauseColumns.add(ConstantsClass.department_col_2);
				args.add(department_name);
				ResultSet rSet=DbConnectorClass.getInstanceClass()
						.getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.Department_TABLE,
								columns, whereClauseColumns, args);
				
				if (rSet!=null) {
					try {
						if (rSet.last()&&rSet.getRow()==1) {
							rSet.first();
							department_id=String.valueOf(rSet.getInt(1));
							flag=true;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
			if (!flag) {
				System.out.print("\nEnter valid department name: ");
				department_name=userInputScanner.nextLine();
			}
		}
		
		if (DbConnectorClass.getInstanceClass().getCountOf(ConstantsClass.Ticket_TABLE)==0) {
			insertIntoTicketTable(1,ticketDescription,department_id);
		}
		else if (DbConnectorClass.getInstanceClass().getCountOf(ConstantsClass.Ticket_TABLE)>0) {
			insertIntoTicketTable(DbConnectorClass.getInstanceClass().getCountOf(ConstantsClass.Ticket_TABLE)+1
					,ticketDescription,department_id);
		}
		else {
			System.out.print("\n Something went wrong! Please contact admin.");
		}
		
	}

	/**
       * This method is reponsible for insert data ticket table.
	 * @param used under this method are ticket_id,    ticketDescription2, department_id2
	 */
	private void insertIntoTicketTable(int ticket_id, String ticketDescription2, String department_id2) {
		// TODO Auto-generated method stub
		columns.clear();
		values.clear();
		columns=new ArrayList<String>();
		columns.add(ConstantsClass.tiket_col_1);
		columns.add(ConstantsClass.tiket_col_2);
		columns.add(ConstantsClass.tiket_col_5);
		columns.add(ConstantsClass.tiket_col_6);
		values=new LinkedHashMap<Object, String>();
		values.put(new Integer(ticket_id),String.valueOf(ticket_id));
		values.put(new String(ticketDescription2),ticketDescription2);
		values.put(new Integer(Integer.valueOf(department_id2)),department_id2);
		values.put(new String(LoginClass.USER_ID), LoginClass.USER_ID);
		try {
		DbConnectorClass.getInstanceClass().insertValue(ConstantsClass.DATABASE_STRING,
		ConstantsClass.Ticket_TABLE, columns, values);
		System.out.print("\n\t\t Ticket creation success");
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.print("\n\t\t Ticket creation failed3");
			System.out.println(e);
		}
	}

	/**
	 * This method is used for resolve assigned ticket in the system.
	 */
	public void resolveAssignedTicket() {
		// TODO Auto-generated method stub
		if (search_ticket_module(false,true)) {
			System.out.println("\n what do you want to do(resolved|deffered):");
			String newStatus=userInputScanner.next();
			while (!(newStatus.equals("resolved")|newStatus.equals("deffered"))) {
				System.out.println("\nPlease enter valid option(resolved|deffered): ");
				newStatus=userInputScanner.next();
			}
			
			columns.clear();
			whereClauseColumns.clear();
			columns.add(ConstantsClass.tiket_col_4);
			whereClauseColumns.add(ConstantsClass.tiket_col_1);
			values.clear(); 
			argsValues.clear();
			values.put(new String(newStatus),newStatus);
			argsValues.put(new Integer(ticket_id),ticket_id);
			DbConnectorClass.getInstanceClass().updateRecords(ConstantsClass.
			Ticket_TABLE, columns, whereClauseColumns, values, argsValues);			
		}
	}

	/**
	 * This method is used for view assigned ticket in the system.
	 */
	public void viewAssignedTicket() {
		// TODO Auto-generated method stub
		whereClauseColumns.clear();
		args.clear();
		whereClauseColumns.add(ConstantsClass.tiket_col_5);
		args.add(LoginClass.DEPARTMENT);
		ResultSet getDataResultSet=DbConnectorClass.getInstanceClass()
			.getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.Ticket_TABLE, 
					null, whereClauseColumns, args);
		if (getDataResultSet!=null) {
			
			try {
				while (getDataResultSet.next()) {
					showTicket(getDataResultSet, false,true);					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * This method is used for search assigned ticket in the system. 
	 */
	public void searchAssignedTicket() {
		// TODO Auto-generated method stub
		search_ticket_module(false,true);
	}
	
	


}
