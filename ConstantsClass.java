/**
 * 
 */

/**
 * @author Tarun
 *
 */
public class ConstantsClass {

	static final String DATABASE_STRING="MAD_3463_1";
	static final String CONNECTION_URL =
            "jdbc:sqlserver://localhost:1433;"
    		+"integratedSecurity=true;";
	
	static final String CLASS_FOR_NAME="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static final String LOGIN_TABLE="login_table";
	static final String Ticket_TABLE="ticket_table";
	static final String Department_TABLE="department_table";
	
	//Table 1
	static final String login_col_1="login_id";
	static final String login_col_2="username";
	static final String login_col_3="password";
	static final String login_col_4="role";
	static final String login_col_5="department_id";
	
	//Table 2
	static final String tiket_col_1="ticket_id";
	static final String tiket_col_2="ticket_description";
	static final String tiket_col_3="created_on";
	static final String tiket_col_4="current_status";
	static final String tiket_col_5="assigned_department";
	static final String tiket_col_6="login_id";
		
	//Table 1
	static final String department_col_1="department_id";
	static final String department_col_2="department_name";

	
}
