import java.io.Console;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
* This Class demostrates the Login Class
* simply gets the information for the  User  from the Admin and perform the standard action.
*/
/**
* @author  Manjot Kaur
* @version 1.0
* @Date created   2020-03-31 
*/
public class LoginClass {

	static String USER_ID="",ROLE="",DEPARTMENT="";
	Scanner scanInputScanner = new Scanner(System.in);
	String emp_id, userName, password, conPassword, department_id, department_name, role;
	Console consoleInstance = System.console();
	ArrayList<String> whereClausecolumns, args, columns;
	LinkedHashMap<Object,String> values;
/** 
 * This is constructor which checks the availability of user
 * @param used in this method are whereClausecolumns,args,EmployeeClassInstance,UserName,password,checkuser;
   @return String value according to the availibility of details in their corressponding departments.
 * @exception e will thrown if login fails.
*/	

	
	public LoginClass() {
		columns=new ArrayList<String>();
		whereClausecolumns=new ArrayList<String>();
		args=new ArrayList<String>();
		values=new LinkedHashMap<Object, String>();
		// TODO Auto-generated constructor stub
		if (checkDepartmentsAvailable()) {
			if(checkUsersAvailable())
			{
				System.out.print("\n\nUsername: ");
				userName=scanInputScanner.next();
				System.out.println("\nPassword: ");
				password=scanInputScanner.next();
				try {
					whereClausecolumns=new ArrayList<String>();
					args=new ArrayList<String>();
					columns=new ArrayList<String>();
					columns.add(ConstantsClass.login_col_1);
					columns.add(ConstantsClass.login_col_4);
					columns.add(ConstantsClass.login_col_5);
					whereClausecolumns.add("username");
					whereClausecolumns.add("password");
					args.add(userName);
					args.add(password);
					ResultSet checkUser=DbConnectorClass
							.getInstanceClass()
							.getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.LOGIN_TABLE,
									columns, whereClausecolumns, args);
					if (checkUser!=null) {
						if (checkUser.last() && checkUser.getRow()==1) {
							
							checkUser.first();
							
							USER_ID=checkUser.getString(1);
							ROLE=checkUser.getString(2);
							DEPARTMENT=checkUser.getString(3);
							
							System.out.print("\n\nLogin Success\n ");
							switch (ROLE) {
								case "superadmin":
								{
									new SuperAdminClass();
									break;
								}
								case "admin":
								{
									new AdminClass();
									break;
								}
								case "employee":
								{
									new EmployeeClass();
									break;
								}
							}
						}
						else {
							System.out.println("\n\nLogin failed. Try Again");
							new LoginClass();
						}
					}
					else {
						System.out.println("\n\nLogin failed. Try Again");
						new LoginClass();
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.print(e);
				}
			}
			else {					
				System.out.print("\n\nNo User's found. \n\n "
						+ "Please configure the super admin\n");
				EmployeeClass employeeClassInstance=new EmployeeClass(true);
				employeeClassInstance.createEmployee();
				System.out.println("\n\t\tPlease Login Again");
				new LoginClass();				
			}
		}
		else {
			System.out.print("\nNo Department found.\n\t\t\tPlease create a department first.\n");
			DepartmentClass departmentClassInstanceClass=new DepartmentClass();
			departmentClassInstanceClass.createDeparment();
			System.out.print("\n\nNo User's found. \n\n "
					+ "Please configure the super admin\n");
			EmployeeClass employeeClassInstance=new EmployeeClass(true);
			employeeClassInstance.createEmployee();
			System.out.println("\n\t\tPlease Login Again");
			new LoginClass();			
		}
		
	    
	}
/** 
 * This class includes checkUsersAvailable method to verify  the availability of user.
 * @param used in this method is rSet
   @return String value according to the availibility of details in database.
 * @exception e will thrown if login fails.
*/


	private boolean checkUsersAvailable() {
		DbConnectorClass dbConnectorClassInstance = DbConnectorClass.getInstanceClass();
		ResultSet rSet;

		try {
			rSet = dbConnectorClassInstance.getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.LOGIN_TABLE,
					null, null, null);
			if (rSet != null) {
				if (rSet.last()) {
					if (rSet.getRow() == 0) {
						return false;
					}
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
/** 
 * This class includes checkDepartmentAvailable method to verify  the availability of user with respect to his department.
 * @param used in this method is rSet
   @return String value according to the availibility of details in their corressponding departments.
 * @exception e will thrown if login fails.
*/
	
	private boolean checkDepartmentsAvailable() {
		DbConnectorClass dbConnectorClassInstance = DbConnectorClass.getInstanceClass();
		ResultSet rSet;

		try {
			rSet = dbConnectorClassInstance.getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.Department_TABLE,
					null, null, null);
			if (rSet != null) {
				if (rSet.last()) {
					if (rSet.getRow() == 0) {
						return false;
					}
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

}
