/**
 * 
 */

/**
 * @author Tarun
 *
 */
public class TikMan {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("\n\n\t\t\t\t\t\tTik Man\n \t\t\t");
		DbConnectorClass sClass=DbConnectorClass.getInstanceClass();
		sClass.checkDatabaseExsistance(ConstantsClass.DATABASE_STRING);
		
		sClass.checkTableExsistance(ConstantsClass.DATABASE_STRING);
		new LoginClass();
		
	}

}
