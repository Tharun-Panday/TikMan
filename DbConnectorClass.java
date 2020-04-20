import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 */

/**
 * @author Tarun
 *
 */
public class DbConnectorClass {

	private static DbConnectorClass instanceClass;
	
	/**
	 * @return the instanceClass
	 */
	public static DbConnectorClass getInstanceClass() {
		if (instanceClass==null) instanceClass=new DbConnectorClass();
		return instanceClass;
	}
	
	public void checkDatabaseExsistance(String databaseName) {
		
        ResultSet resultSet = null;
        Connection connection=null;
        Statement statement = null;
        try{
    		Class.forName(ConstantsClass.CLASS_FOR_NAME);
    		connection = DriverManager.getConnection(ConstantsClass.CONNECTION_URL);
    		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // Create and execute a SELECT SQL statement.
            String selectSql = "if exists(select name from sys.databases where name = '"+databaseName+"')"+ 
            		"begin select name from sys.databases where name like '"+databaseName+"'"+
            		"end else begin create database "+databaseName+" "
            				+ "select name from sys.databases where name like '\"+databaseName+\"' end";
            resultSet = statement.executeQuery(selectSql);
            if (resultSet==null) {
            	System.out.println("Error in Database Connection, Please contact Admin");
			}
            else {
//            		System.out.println("Database Connected Successfully");
			}
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
			try {
				if (statement!=null)statement.close();
				if (resultSet!=null) resultSet.close();
				if (connection!=null) connection.close();							
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void checkTableExsistance(String databaseName) {
		
        ResultSet resultSet = null;
        Connection connection=null;
        Statement statement = null;
        try{
    		Class.forName(ConstantsClass.CLASS_FOR_NAME);
    		connection = DriverManager.getConnection(ConstantsClass.CONNECTION_URL);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            String selectSql ="use "+databaseName;
            statement.execute(selectSql);
            
            selectSql = "IF EXISTS (SELECT TABLE_NAME " + 
            		"FROM INFORMATION_SCHEMA.TABLES " + 
            		"WHERE  TABLE_NAME like '"+ConstantsClass.Department_TABLE+"') "
            				+ "begin select count(*) from "+ConstantsClass.Department_TABLE+" end "
            				+ "else "
            				+ "begin "
            				+ "create table "+ConstantsClass.Department_TABLE
            				+"(department_id int primary key,"
            				+ "department_name varchar(15) not null) "
            				+ "select name from sys.databases where name like '"+databaseName+"' "
            				+ "end";
            resultSet = statement.executeQuery(selectSql);
            
            if (resultSet==null) {
            	System.out.println("Error in Database, Please contact Admin");
			}
            else {
//            		System.out.println("table found");
			}
            
            selectSql = "IF EXISTS (SELECT TABLE_NAME " + 
            		"FROM INFORMATION_SCHEMA.TABLES " + 
            		"WHERE  TABLE_NAME like '"+ConstantsClass.LOGIN_TABLE+"') "
            				+ "begin select count(*) from "+ConstantsClass.LOGIN_TABLE+" end "
            				+ "else "
            				+ "begin "
            				+ "create table "+ConstantsClass.LOGIN_TABLE+"(login_id varchar(6) primary key,"
            				+ "username varchar(15) not null check (username NOT LIKE '%[^A-Z]%'),"
            				+ "password varchar(30) not null default 'tikman',"
            				+ "role varchar(20) not null default 'employee',"
            				+ "department_id int references "+ConstantsClass.Department_TABLE+"("+ConstantsClass.department_col_1+")) "
            				+ "select name from sys.databases where name like '"+databaseName+"' "
            				+ "end";
            resultSet = statement.executeQuery(selectSql);
            
            if (resultSet==null) {
            	System.out.println("Error in Database, Please contact Admin");
			}
            else {
//            		System.out.println("table found");
			}
            selectSql = "IF EXISTS (SELECT TABLE_NAME " + 
            		"FROM INFORMATION_SCHEMA.TABLES " + 
            		"WHERE  TABLE_NAME like '"+ConstantsClass.Ticket_TABLE+"') "
            				+ "begin select count(*) from "+ConstantsClass.Ticket_TABLE+" end "
            				+ "else "
            				+ "begin "
            				+ "create table "+ConstantsClass.Ticket_TABLE+"(ticket_id int primary key,"
            				+ "ticket_description varchar(50) not null,"
            				+ "created_on date default getDate(),"
            				+ "current_status varchar(15) default 'assigned',"
            				+ "assigned_department int references "+ConstantsClass.Department_TABLE+"("+ConstantsClass.department_col_1+"),"
            				+ "login_id varchar(6) references "+ConstantsClass.LOGIN_TABLE+"("+ConstantsClass.login_col_1+")) "
            				+ "select name from sys.databases where name like '"+databaseName+"' "
            				+ "end";
            resultSet = statement.executeQuery(selectSql);
            
            if (resultSet==null) {
            	System.out.println("Error in Database, Please contact Admin");
			}
            else {
//            		System.out.println("table found");
			}           
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
			try {
				if (statement!=null)statement.close();
				if (resultSet!=null) resultSet.close();
				if (connection!=null) connection.close();							
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ResultSet getDataFromDb(String databaseName,String tableName,ArrayList<String> columnNames,
			ArrayList<String> whereClauseColumn,ArrayList<String> args)
	{
		ResultSet resultSet = null;
        Connection connection=null;
        Statement statement = null;
        try{
			Class.forName(ConstantsClass.CLASS_FOR_NAME);
			connection = DriverManager.getConnection(ConstantsClass.CONNECTION_URL);
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        
	        String selectSql ="use "+databaseName;
	        statement.execute(selectSql);
	        
	        if (columnNames==null) {
	        	selectSql = "select * from "+tableName;
			}
	        else {
	        	selectSql = "select ";
	        	for (int temp=0;temp<columnNames.size();temp++) {
	        		selectSql = selectSql+columnNames.get(temp);
	        		if ((columnNames.size()-1) !=temp) {
						selectSql=selectSql+",";
					}
	        		else {
	        			selectSql=selectSql+" from "+tableName;
					}
				}
			}
	        if (whereClauseColumn!=null && args!=null) {
	        	if (whereClauseColumn.size()==args.size()) {
	        		selectSql=selectSql+" where ";
					for (int temp = 0; temp < whereClauseColumn.size(); temp++) {
						selectSql=selectSql+" "+whereClauseColumn.get(temp)+" = '"+args.get(temp)+"'";
						if (whereClauseColumn.size()-1 !=temp) {
							selectSql=selectSql+" and";
						}
					}	
				}
			}
            
//	        System.out.println(selectSql);
			 resultSet = statement.executeQuery(selectSql);
			 
			 if (resultSet==null) { return null; } else { return resultSet; }
			
        }
        catch (Exception e) {
        	return null;
        }
	}
	
	public int insertValue(String databaseName,String tableName,
			ArrayList<String> columns,LinkedHashMap<Object,String> values)
	{
		Connection connection=null;
        Statement statement = null;
        try{
			Class.forName(ConstantsClass.CLASS_FOR_NAME);
			connection = DriverManager.getConnection(ConstantsClass.CONNECTION_URL);
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        
	        String sqlQuery ="use "+databaseName;
	        statement.execute(sqlQuery);
	 
	        sqlQuery="insert into "+tableName;
	        if (columns!=null ) {
	        	sqlQuery=sqlQuery+"(";
        		for (int temp = 0; temp < columns.size(); temp++) {
        			sqlQuery=sqlQuery+columns.get(temp);
					if ((columns.size()-1) != temp) {
						sqlQuery=sqlQuery+",";
					}
				}
	        	sqlQuery=sqlQuery+")";
			}
	        
	        sqlQuery=sqlQuery+" values (";
	        int tempIncrementer=1;
	        for (Map.Entry<Object,String> singlEntry : values.entrySet()) {
	        	System.out.println(singlEntry.getKey()+" "+singlEntry.getValue()); 
        		if ((Object)singlEntry.getKey() instanceof Integer) {
					sqlQuery=sqlQuery+singlEntry.getValue();
				}
        		else if ((Object)singlEntry.getKey() instanceof String) {
        			sqlQuery=sqlQuery+"'"+singlEntry.getValue()+"'";
				}
    			if (tempIncrementer!=values.size()) {
					sqlQuery=sqlQuery+",";
				}
    			tempIncrementer++;
			}
	        sqlQuery=sqlQuery+")";
	        
//	        System.out.println(sqlQuery);
	        return statement.executeUpdate(sqlQuery);
        }
        catch (Exception e) {
        	return 0;
        }
	}
	
	public int getCountOf(String tableName)
	{
		Connection connection=null;
        Statement statement = null;
        try{
			Class.forName(ConstantsClass.CLASS_FOR_NAME);
			connection = DriverManager.getConnection(ConstantsClass.CONNECTION_URL);
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        
	        String sqlQuery ="use "+ConstantsClass.DATABASE_STRING;
	        statement.execute(sqlQuery);
	 
	        sqlQuery="select count(*) as total from "+tableName;
	        ResultSet rSet=statement.executeQuery(sqlQuery);
	        if (rSet!=null) {
				while (rSet.next()) {
					 return rSet.getInt("total");
				}
			}   
	        return -20;        
        }
        catch (Exception e) {
        	return -20;
        }
	}
	
	public int updateRecords(String tableName,
			ArrayList<String> columnNames,ArrayList<String> whereClauseColumn
			,LinkedHashMap<Object,String> newValue,LinkedHashMap<Object,String> argsValue)
	{
		Connection connection=null;
        Statement statement = null;
        try{
			Class.forName(ConstantsClass.CLASS_FOR_NAME);
			connection = DriverManager.getConnection(ConstantsClass.CONNECTION_URL);
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        
	        String sqlQuery ="use "+ConstantsClass.DATABASE_STRING;
	        statement.execute(sqlQuery);
	 
	        sqlQuery="update "+tableName+" set ";
	        if (columnNames!=null && newValue!=null) {
	        	if (columnNames.size()==newValue.size()) {
	        		Set set = newValue.entrySet();
	        		Iterator iterator=set.iterator();
	        		for (int temp = 0; temp < columnNames.size(); temp++) {
	        			 Map.Entry singlEntryValue = (Map.Entry)iterator.next();
	        			sqlQuery=sqlQuery+columnNames.get(temp)+" = ";
	        			if ((Object)singlEntryValue.getKey() instanceof Integer) {
	    					sqlQuery=sqlQuery+singlEntryValue.getValue();
	    				}
	            		else if ((Object)singlEntryValue.getKey() instanceof String) {
	            			sqlQuery=sqlQuery+"'"+singlEntryValue.getValue()+"'";
	    				}
						if ((columnNames.size()-1) != temp) {
							sqlQuery=sqlQuery+",";
						}
					}
				}        		
			}
	        
	        sqlQuery=sqlQuery+" where ";
	        if (whereClauseColumn!=null && argsValue!=null) {
	        	if (whereClauseColumn.size()==argsValue.size()) {
	        		Set set = argsValue.entrySet();
	        		Iterator iterator=set.iterator();
	        		for (int temp = 0; temp < whereClauseColumn.size(); temp++) {
	        			 Map.Entry singlEntryValue = (Map.Entry)iterator.next();
	        			sqlQuery=sqlQuery+whereClauseColumn.get(temp)+" = ";
	        			if ((Object)singlEntryValue.getKey() instanceof Integer) {
	    					sqlQuery=sqlQuery+singlEntryValue.getValue();
	    				}
	            		else if ((Object)singlEntryValue.getKey() instanceof String) {
	            			sqlQuery=sqlQuery+"'"+singlEntryValue.getValue()+"'";
	    				}
						if ((whereClauseColumn.size()-1) != temp) {
							sqlQuery=sqlQuery+" and ";
						}
					}
				}        		
			}
	        
//	        System.out.println(sqlQuery);
	        return statement.executeUpdate(sqlQuery);
        }
        catch (Exception e) {
        	return 0;
        }
	}
	
	public int deleteRecords(String tableName,ArrayList<String> whereClauseColumn,
			LinkedHashMap<Object,String> argsValue)
	{
		Connection connection=null;
        Statement statement = null;
        try{
			Class.forName(ConstantsClass.CLASS_FOR_NAME);
			connection = DriverManager.getConnection(ConstantsClass.CONNECTION_URL);
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        
	        String sqlQuery ="use "+ConstantsClass.DATABASE_STRING;
	        statement.execute(sqlQuery);
	 
	        sqlQuery="delete from "+tableName+" where ";
	        	        
	        if (whereClauseColumn!=null && argsValue!=null) {
	        	if (whereClauseColumn.size()==argsValue.size()) {
	        		Set set = argsValue.entrySet();
	        		Iterator iterator=set.iterator();
	        		for (int temp = 0; temp < whereClauseColumn.size(); temp++) {
	        			 Map.Entry singlEntryValue = (Map.Entry)iterator.next();
	        			sqlQuery=sqlQuery+whereClauseColumn.get(temp)+" = ";
	        			if ((Object)singlEntryValue.getKey() instanceof Integer) {
	    					sqlQuery=sqlQuery+singlEntryValue.getValue();
	    				}
	            		else if ((Object)singlEntryValue.getKey() instanceof String) {
	            			sqlQuery=sqlQuery+"'"+singlEntryValue.getValue()+"'";
	    				}
						if ((whereClauseColumn.size()-1) != temp) {
							sqlQuery=sqlQuery+" and ";
						}
					}
				}        		
			}
	        
//	        System.out.println(sqlQuery);
	        return statement.executeUpdate(sqlQuery);
        }
        catch (Exception e) {
        	return 0;
        }
	}

}
