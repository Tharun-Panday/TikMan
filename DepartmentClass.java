import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;


/**
* The Department class implements an application that simply displays the department details to the standard output.
*
* @author  Prateek Chopra
* @version 1.0
* @date created  2020-04-14
*/
public class DepartmentClass {

/**
  * This method creates the Department with accepting the values using scanner class.
  * @param args department_id, department_name, scanInputScanner. 
  * @return null.
  */
String department_id,department_name;
Scanner scanInputScanner;
private LinkedHashMap<Object, String> values,argsValues;
ArrayList<String> columns,whereClauseColumns,args;
public DepartmentClass() {
// TODO Auto-generated constructor stub
scanInputScanner=new Scanner(System.in);
columns=new ArrayList<String>();
whereClauseColumns=new ArrayList<String>();
args=new ArrayList<String>();
values=new LinkedHashMap<Object, String>();
argsValues=new LinkedHashMap<Object, String>();
}


public void createDeparment()
{
System.out.print("\nEnter the department id: ");
department_id=scanInputScanner.next();
while(!department_id.matches("^[1-9][0-9][0-9]$"))
{
System.out.print("\nEnter a valid department ID: ");
department_id=scanInputScanner.next();
}

System.out.println("Enter Department name: ");
department_name=scanInputScanner.next();
while(!department_name.matches("^[A-z]+$"))
{
System.out.print("\nEnter a valid department name(only alphabets): ");
department_name=scanInputScanner.next();
}


columns.clear();
columns.add(ConstantsClass.department_col_1);
columns.add(ConstantsClass.department_col_2);
values.clear();
values.put(new Integer(Integer.valueOf(department_id)),department_id);
values.put(new String(department_name),department_name);
try {
DbConnectorClass.getInstanceClass().insertValue(ConstantsClass.DATABASE_STRING,
ConstantsClass.Department_TABLE, columns, values);
System.out.print("\n\t\tDepartment Created");
}
catch (Exception e) {
// TODO: handle exception
System.out.print("\n\t\tDepartment Creation failed");
}
}

/**
  * This method looks for Boolean search for condition.
  * @param used is isUpdate.
  * @return either true or false depending value of department if found.
  * @exception IOException On no department found.
  * @see IOException
  */
public boolean searchDepartment(boolean isUpdate)
{
System.out.print("\nEnter the department id: ");
department_id=scanInputScanner.next();
while(!department_id.matches("^[1-9][0-9][0-9]$"))
{
System.out.print("\nEnter a valid department ID: ");
department_id=scanInputScanner.next();
}
whereClauseColumns.add(ConstantsClass.department_col_1);
args.add(department_id);
ResultSet getTicketResultSet=DbConnectorClass.getInstanceClass()
.getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.Ticket_TABLE,
null, whereClauseColumns, args);
if (getTicketResultSet!=null) {
try {
if (getTicketResultSet.last()&&getTicketResultSet.getRow()==1) {
getTicketResultSet.first();
showDepartment(getTicketResultSet, false);
return true;
}
else {
System.out.print("\nNo Department Found");
return false;
}
} catch (SQLException e) {
// TODO Auto-generated catch block     
System.out.print("\nNo Department Found");
return false;
}
}
else {
System.out.print("\nNo Department Found");
return false;
}
}

/**
  * This method ask to delete the department if not found.
  * @return the value whether department is deleted or not.
  */
public boolean deleteDepartment() {
// TODO Auto-generated method stub
if (searchDepartment(false)) {
System.out.println("\n Do you want to delete this Department(yes|no):");
String userChoiceString=scanInputScanner.next();
while (!(userChoiceString.equals("yes")|userChoiceString.equals("no"))) {
System.out.println("\nPlease enter valid option(yes|no): ");
userChoiceString=scanInputScanner.next();
}
if (userChoiceString.equals("yes")) {
columns=new ArrayList<String>();
columns.add(ConstantsClass.department_col_1);
values=new LinkedHashMap<Object, String>();
values.put(new Integer(department_id),department_id);
DbConnectorClass.getInstanceClass().deleteRecords(ConstantsClass.Department_TABLE,
columns, values);
System.out.print("\n\t\tDepartment Deleted");
return true;
}       
System.out.print("\n\t\tDepartment Deletion failed");
return false;
}
else {
System.out.println("\n Department not found!");
return false;
}
}

/**
  * This method is responsible to view the details of department.
  * @return the value of department if found.
  */
public void viewAllDepartment() {
// TODO Auto-generated method stub
ResultSet getDataResultSet=DbConnectorClass.getInstanceClass()
.getDataFromDb(ConstantsClass.DATABASE_STRING, ConstantsClass.Department_TABLE,
null, null, null);
if (getDataResultSet!=null) {

try {
while (getDataResultSet.next()) {
showDepartment(getDataResultSet, false);       
}
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}

}
}

/**
  * This method is to update the department.
  * @return null
  * @exception IOException if department updation gets failed.
  * @see IOException
  */
public void updateDepartment() {
// TODO Auto-generated method stub

int userChoice;
if (searchDepartment(true)) {
System.out.println("\nEnter your choice: ");

while (!scanInputScanner.hasNextInt()) {
System.out.println("\nEnter valid choice: ");
scanInputScanner.next();       
}       
userChoice = scanInputScanner.nextInt();
while (!(userChoice>0 && userChoice<2)) {
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
System.out.println("\nEnter Department name: ");
try {
department_name=reader.readLine();     
columns=new ArrayList<String>();
whereClauseColumns=new ArrayList<String>();
columns.add(ConstantsClass.department_col_2);
whereClauseColumns.add(ConstantsClass.department_col_1);
values=new LinkedHashMap<Object,String>();
argsValues=new LinkedHashMap<Object,String>();
values.put(new String(department_name),department_name);
argsValues.put(new Integer(department_id),department_id);
DbConnectorClass.getInstanceClass().updateRecords(ConstantsClass.
Ticket_TABLE, columns, whereClauseColumns, values, argsValues);
System.out.print("\n\t\tDepartment Updation success"); 
} catch (IOException e1) {
// TODO Auto-generated catch block
System.out.print("\n\t\tDepartment Updation Failed");
e1.printStackTrace();
}       
}

}       
}

public void showDepartment(ResultSet dataSet,boolean isUpdate)
{
try {
String subMenuString="";
if (isUpdate) {
subMenuString="\n\nDepartment id: "+dataSet.getInt(1)+"\n1.Department name: "
+dataSet.getString(2);
}
else {
subMenuString="\n\nDepartment id: "+dataSet.getInt(1)+"\nDepartment name: "
+dataSet.getString(2);
}       
System.out.print(subMenuString);
} catch (Exception e) {
// TODO: handle exception
}
}
}
