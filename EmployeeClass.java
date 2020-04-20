import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * The employee class implements an application that simply displays employees details to the standard output.
 * @author Kamal
 * @version 1.0
 * @date created 2020-04-11
 */
public class EmployeeClass {

	static int PasswordAttempts=0;
	String emp_id,emp_name,emp_password,emp_role,emp_department,emp_con_password,userChoice;
	Scanner scanInputScanner;
	private LinkedHashMap<Object, String> values,argsValues;
	ArrayList<String> columns,whereClauseColumns,args;
	TicketClass ticketClassInstance;
	boolean isFirstTime;
/**
 * This is the EmployeeClass which makes use of ShowMenu method.
 */	

	public EmployeeClass() {
		// TODO Auto-generated constructor stub
		isFirstTime=false;
		scanInputScanner=new Scanner(System.in);
		columns=new ArrayList<String>();
		whereClauseColumns=new ArrayList<String>();
		args=new ArrayList<String>();
		values=new LinkedHashMap<Object, String>();
		argsValues=new LinkedHashMap<Object, String>();
		ticketClassInstance=new TicketClass();	
		if (LoginClass.ROLE.equals("employee")) {
			showMenu();
		}
	}
	void showMenu()
	{
		do {
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tEmployee\n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t1.Create Ticket \n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t2.Update Ticket\n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t3.Delete Ticket\n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t4.View Ticket\n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t5.Search Ticket\n");
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t6.Log Out\n");
			System.out.print("\t\t\t\tEnter Your Choice: ");
			userChoice=scanInputScanner.next();
			
			while (!userChoice.matches("^[1-6]$")) {
				System.out.print("\n\t\t\t\tEnter Valid Choice: ");
				userChoice=scanInputScanner.next();
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
			}
		} while (Integer.valueOf(userChoice)!=6);
		LoginClass.USER_ID="";
		LoginClass.ROLE="";
		LoginClass.DEPARTMENT="";
		new LoginClass();
	}

   

	public EmployeeClass(boolean isFirstTime) {
		// TODO Auto-generated constructor stub
		this.isFirstTime=isFirstTime;
		scanInputScanner=new Scanner(System.in);
		columns=new ArrayList<String>();
		whereClauseColumns=new ArrayList<String>();
		args=new ArrayList<String>();
		values=new LinkedHashMap<Object, String>();
		argsValues=new LinkedHashMap<Object, String>();
	}
/**
        * This method is used for creating employees in the system.
*/

	public void createEmployee()
	{
		emp_password="";
		System.out.print("\n Enter Employee ID: ");
		emp_id=scanInputScanner.next();
		while(!emp_id.matches("(tik)[0-9]{3}"))
		{
			System.out.print("\nEnter a valid Employee ID(tikXXX): ");
			emp_id=scanInputScanner.next();
		}
		System.out.print("\nEnter Username: ");
		emp_name=scanInputScanner.next();
		while(!emp_name.matches("^[A-z]+$"))
		{
			System.out.print("\nEnter a valid Username(only alphabets): ");
			emp_name=scanInputScanner.next();
		}
		if (!(DbConnectorClass.getInstanceClass().getCountOf(ConstantsClass.LOGIN_TABLE)>0)) {
			System.out.print("\nEnter a Password: ");
			emp_password=scanInputScanner.next();
			System.out.print("\nConfirm Password: ");
			emp_con_password=scanInputScanner.next();
			while (emp_name.equals(emp_password)) {
				System.out.print("\nEnter a strong Password: ");
				emp_password=scanInputScanner.next();
				System.out.print("\nConfirm Password: ");
				emp_con_password=scanInputScanner.next();
			}
			while (!emp_password.equals(emp_con_password)) {
				System.out.println("Password Mismatch.\tPlease Try again!");
				System.out.print("\nEnter a Password: ");
				emp_password=scanInputScanner.next();
				System.out.print("\nConfirm Password: ");
				emp_con_password=scanInputScanner.next();
				
			}
		}		
		if (isFirstTime) {
			emp_role="superadmin";
		}
		else {
			System.out.print("\nEnter role: ");
			emp_role=scanInputScanner.next();
			if (LoginClass.ROLE.equals("superadmin")) {
				while (!emp_role.matches("^(superadmin|admin|employee)$")) {
					System.out.print("\nEnter valid role(superadmin|admin|employee): ");
					emp_role=scanInputScanner.next();
				}
			}else {
				while (!emp_role.matches("^(admin|employee)$")) {
					System.out.print("\nEnter valid role(superadmin|admin|employee): ");
					emp_role=scanInputScanner.next();
				}
			}
		}
		
		
		System.out.print("\nEnter department id: ");
		emp_department=scanInputScanner.next();
		boolean flag=false;
		while (!flag) {
			if (emp_department.matches("^[1-9][0-9][0-9]$")) {
				columns.clear();
				whereClauseColumns.clear();
				columns.add(ConstantsClass.department_col_1);
				whereClauseColumns.add(ConstantsClass.department_col_1);
				args.add(emp_department);
				ResultSet rSet=DbConnectorClass.getInstanceClass()
						.getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.Department_TABLE,
								columns, whereClauseColumns, args);
				
				if (rSet!=null) {
					try {
						if (rSet.last()&&rSet.getRow()==1) {
							flag=true;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if (!flag) {
				System.out.print("\nEnter valid department id: ");
				emp_department=scanInputScanner.next();
			}
		}
		columns.clear();
		values.clear();
		columns=new ArrayList<String>();
		columns.add(ConstantsClass.login_col_1);
		columns.add(ConstantsClass.login_col_2);
		if (emp_password.length()>0) {
			columns.add(ConstantsClass.login_col_3);
		}	
		columns.add(ConstantsClass.login_col_4);
		columns.add(ConstantsClass.login_col_5);
		values.put(new String(emp_id),emp_id);
		values.put(new String(emp_name),emp_name);
		if (emp_password.length()>0) {
			values.put(new String(emp_password),emp_password);
		}		
		values.put(new String(emp_role),emp_role);
		values.put(new Integer(emp_department),emp_department);
		try {
		DbConnectorClass.getInstanceClass().insertValue(ConstantsClass.DATABASE_STRING,
		ConstantsClass.LOGIN_TABLE, columns, values);	
		System.out.print("\n\t\t Employee Created");
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.print("\n\t\t Employee Creation Failed");
//			System.out.println(e);
		}
	}
/**
        * This method is used for searching employees in the system.
        * @return the boolean value as per the action performed by the user.
        * @param used in this method – isUpdate.
*/

	
	public boolean searchEmployee(boolean isUpdate)
	{
		System.out.print("\n Enter Employee ID: ");
		emp_id=scanInputScanner.next();
		while(!emp_id.matches("(tik)[0-9]{3}"))
		{
			System.out.print("\nEnter a valid Employee ID(tikXXX): ");
			emp_id=scanInputScanner.next();
		}
		whereClauseColumns.clear();
		args.clear();
		whereClauseColumns.add(ConstantsClass.login_col_1);
		args.add(emp_id);
		ResultSet getEmployeResultSet=DbConnectorClass.getInstanceClass()
				.getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.LOGIN_TABLE,
						null, whereClauseColumns, args);
		if (getEmployeResultSet!=null) {
			try {
				if (getEmployeResultSet.last()&&getEmployeResultSet.getRow()==1) {
					getEmployeResultSet.first();
					showEmployee(getEmployeResultSet, isUpdate);
					return true;
				}
				else {
					System.out.print("\n\t\t Search Employee Failed");
					return false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block		
				System.out.print("\n\t\t Search Employee Failed");
				return false;
			}
		}
		System.out.print("\n\t\t Search Employee Failed");
		return false;
	}
	
	/**
	 * This method is responsible for deleting the employees from the system. 
      * @return boolean value according to the action performed by the user.
	*/
	public boolean deleteEmployee() {
		// TODO Auto-generated method stub
		if (searchEmployee(false)) {
			System.out.println("\n Do you want to delete this Employee(yes|no):");
			String userChoiceString=scanInputScanner.next();
			while (!(userChoiceString.equals("yes")|userChoiceString.equals("no"))) {
				System.out.println("\nPlease enter valid option(yes|no): ");
				userChoiceString=scanInputScanner.next();
			}
			if (userChoiceString.equals("yes")) {
				columns.clear();
				values.clear();
				columns.add(ConstantsClass.login_col_1);
				values.put(new String(emp_id),emp_id);
				DbConnectorClass.getInstanceClass().deleteRecords(ConstantsClass.LOGIN_TABLE,
						columns, values);
				System.out.print("\n\t\t Employee Deleted Sucess");
				return true;
			}			
			System.out.print("\n\t\t Employee Deleted failed");
			return false;
		}
		else {
			System.out.println("\n Employee not found!");
			return false;
		}
	}

	/**
	 * This method is responsible for view all the employees from the database.
      * @return the string value.
      * @exception SQLException e on input error.
	 */
	public void viewAllEmployee() {
		// TODO Auto-generated method stub
		ResultSet getDataResultSet=DbConnectorClass.getInstanceClass()
			.getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.LOGIN_TABLE, 
					null, null, null);
		if (getDataResultSet!=null) {
			
			try {
				while (getDataResultSet.next()) {
					showEmployee(getDataResultSet, false);					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * This method is used for update the employees information in database.
      * @param used in this method is userChoice.
	*/
	public void updateEmployee() {
		// TODO Auto-generated method stub
		
		int userChoice;
		if (searchEmployee(true)) {
			System.out.println("\nEnter your choice: ");
		 	 
			 while (!scanInputScanner.hasNextInt()) {
				 System.out.println("\nEnter valid choice: ");
				 scanInputScanner.next();							 
		 	 }		
			 userChoice = scanInputScanner.nextInt();
			 while (!(userChoice>0 && userChoice<5)) {
					System.out.println("\nEnter valid choice: ");
					scanInputScanner.next();	
					 while (!scanInputScanner.hasNextInt()) {
						 System.out.println("\nEnter valid choice: ");
						 scanInputScanner.next();								 
				 	 }		
					 userChoice = scanInputScanner.nextInt();
				}

			
			if (userChoice==1) {
				//Enter data using BufferReader 
				BufferedReader reader = 
						new BufferedReader(new InputStreamReader(System.in)); 
				
				// Reading data using readLine 
				System.out.println("\nEnter Employee name: ");
				try {
					emp_name=reader.readLine();							
					columns.clear();
					whereClauseColumns.clear();
					columns.add(ConstantsClass.login_col_2);
					whereClauseColumns.add(ConstantsClass.login_col_1);
					values.clear();
					argsValues.clear();
					values.put(new String(emp_name),emp_name);
					argsValues.put(new String(emp_id),emp_id);
					DbConnectorClass.getInstanceClass().updateRecords(ConstantsClass.
					LOGIN_TABLE, columns, whereClauseColumns, values, argsValues);
					System.out.print("\n\t\t Employee Updated success");						
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.print("\n\t\t Employee Updated failed");
					e1.printStackTrace();
				}																		
			}
			else if (userChoice==2) {
				
				if (verifyPassword()) {
					try {
						emp_password=scanInputScanner.next();							
						columns.clear();
						whereClauseColumns.clear();
						values.clear();
						argsValues.clear();
						columns.add(ConstantsClass.login_col_3);
						whereClauseColumns.add(ConstantsClass.login_col_1);				
						values.put(new String(emp_password),emp_password);
						argsValues.put(new String(emp_id),emp_id);
						DbConnectorClass.getInstanceClass().updateRecords(ConstantsClass.
						LOGIN_TABLE, columns, whereClauseColumns, values, argsValues);
						System.out.print("\n\t\t Employee Updated success");				
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						System.out.print("\n\t\t Employee Updated failed");
						e1.printStackTrace();
					}	
				}
				else {
					PasswordAttempts++;
					if (PasswordAttempts<4) {
						verifyPassword();
					}
					else {
						System.out.println("\nPlease Log In again.");
						new LoginClass();
					}
				}
																				
			}
			else if (userChoice==3) {
				System.out.println("\nEnter Role name: ");
				try {
					emp_role=scanInputScanner.next();
					if (LoginClass.ROLE.equals("superadmin")) {
						while (!emp_role.matches("^(superadmin|admin|employee)$")) {
							System.out.print("\nEnter valid role(superadmin|admin|employee): ");
							emp_role=scanInputScanner.next();
						}
					}
					else if (LoginClass.ROLE.equals("admin")) {
						while (!emp_role.matches("^(admin|employee)$")) {
							System.out.print("\nEnter valid role(admin|employee): ");
							emp_role=scanInputScanner.next();
						}
					}					
					columns.clear();
					whereClauseColumns.clear();
					columns.add(ConstantsClass.login_col_4);
					whereClauseColumns.add(ConstantsClass.login_col_1);
					values=new LinkedHashMap<Object,String>(); 
					argsValues=new LinkedHashMap<Object,String>();
					values.put(new String(emp_role),emp_role);
					argsValues.put(new String(emp_id),emp_id);
					DbConnectorClass.getInstanceClass().updateRecords(ConstantsClass.
					LOGIN_TABLE, columns, whereClauseColumns, values, argsValues);
					System.out.print("\n\t\t Employee Updated success");					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.print("\n\t\t Employee Updated failed");
					e1.printStackTrace();
				}																		
			}
			else if (userChoice==4) {
				//Enter data using BufferReader 
				BufferedReader reader = 
						new BufferedReader(new InputStreamReader(System.in)); 
				
				// Reading data using readLine 
				System.out.println("\nEnter Department name: ");
				try {
					emp_department=reader.readLine();							
					columns=new ArrayList<String>();
					whereClauseColumns=new ArrayList<String>();
					columns.add(ConstantsClass.login_col_5);
					whereClauseColumns.add(ConstantsClass.login_col_1);
					values=new LinkedHashMap<Object,String>(); 
					argsValues=new LinkedHashMap<Object,String>();
					values.put(new String(emp_department),emp_department);
					argsValues.put(new String(emp_id),emp_id);
					DbConnectorClass.getInstanceClass().updateRecords(ConstantsClass.
					LOGIN_TABLE, columns, whereClauseColumns, values, argsValues);
					System.out.print("\n\t\t Employee Updated success");						
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.print("\n\t\t Employee Updated failed");
					e1.printStackTrace();
				}																		
			}
			
		}		
	}
	/**
*This method is used to show employees in the system.
*@param used under this method –- dataset, isUpdate.
*/
	public void showEmployee(ResultSet dataSet,boolean isUpdate)
	{
		try {
			String subMenuString="";
			if (isUpdate) {
				subMenuString="\n\nEmployee id: "+dataSet.getString(1)+"\n1.Employee name: "
						 +dataSet.getString(2)+"\n2.Employee Password\n3.Employee role: "
						 +dataSet.getString(4)+"\n4.Employee Department: ";
			}
			else {
				subMenuString="\n\nEmployee id: "+dataSet.getString(1)+"\nEmployee name: "
						 +dataSet.getString(2)+"\nEmployee role: "
						 +dataSet.getString(4)+"\nEmployee Department: ";
			}	
			
			columns.clear();
			whereClauseColumns.clear();
			args.clear();
			columns.add(ConstantsClass.department_col_2);
			whereClauseColumns.add(ConstantsClass.department_col_1);
			args.add(String.valueOf(dataSet.getInt(5)));
			ResultSet getDepartmentNameResultSet=DbConnectorClass.getInstanceClass()
					 .getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.Department_TABLE,
							 columns, whereClauseColumns, args);
			if (getDepartmentNameResultSet!=null) {	
				 getDepartmentNameResultSet.first();
				 subMenuString=subMenuString+getDepartmentNameResultSet.getString(1);
			}			 
			System.out.print(subMenuString);
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
	}
/**
        * This method is used for verify password in database.
        * @param used in this method is currentPassword.
        * @return the boolean value as per the action performed by the user.
*/

	public boolean verifyPassword()
	{
		String currentPassword="";
		System.out.println("\nEnter current password: ");
		currentPassword=scanInputScanner.next();
		columns.clear();
		whereClauseColumns.clear();
		args.clear();
		columns.add(ConstantsClass.login_col_3);
		whereClauseColumns.add(ConstantsClass.login_col_1);
		args.add(LoginClass.USER_ID);
		ResultSet getCurrentPassword=DbConnectorClass.getInstanceClass()
				.getDataFromDb(ConstantsClass.DATABASE_STRING,
				ConstantsClass.LOGIN_TABLE, columns, whereClauseColumns, args);
		
		if (getCurrentPassword!=null) {
			try {
				if (getCurrentPassword.last()&&getCurrentPassword.getRow()==1) {
					getCurrentPassword.first();
					if (getCurrentPassword.getString(1).equals(currentPassword)) {
							return true;
					}
					else {
						return false;
					}
				}
				else {
					return false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
}

