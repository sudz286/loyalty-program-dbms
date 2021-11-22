import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


public class Main {

    static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
    private  static Connection conn = null;

    public static void main(String[] args) throws SQLException {

        try {
            Class.forName("oracle.jdbc.OracleDriver");


            Scanner input = new Scanner(System.in);


            try {

                System.out.println("\n Enter db username:");
                String uname=input.nextLine();

                System.out.println("\n Enter db password:");
                String pswrd=input.nextLine();


                conn = DriverManager.getConnection(jdbcURL, uname, pswrd);

                Home.homepage(conn);
                input.close();


            } finally {

                close(conn);
                System.out.println("End of application!");
            }
        } catch (Throwable oops) {
            oops.printStackTrace();
        }
    }

    static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Throwable whatever) {
            }
        }
    }

}


