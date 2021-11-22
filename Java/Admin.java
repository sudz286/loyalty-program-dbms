import java.util.Scanner;
import java.sql.Date;
import java.sql.*;
public class Admin {
    static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
    //    static String user = Main.user;
//    static String passwd =Main.passwd;
    static Date date = new java.sql.Date(System.currentTimeMillis());
    static Scanner input = new Scanner(System.in);

    // ADMIN PART
    static void AddBrand(Connection conn) throws SQLException, ClassNotFoundException
    {
        System.out.println("Enter Brand name: ");
        input.nextLine();
        String brandname = input.nextLine();

        System.out.println("Enter Brand Address: ");
        String brandadd = input.nextLine();

        System.out.println("Choose an option: \n 1. Add Brand" + "\n 2. Go back");
        int option;
        option = input.nextInt();
        switch (option)
        {
            case 1:

                try
                {
                    // Connection conn = DriverManager.getConnection(jdbcURL, user, passwd);
                    Class.forName("oracle.jdbc.OracleDriver");
                    ResultSet rs=null;
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO BRAND (B_NAME, JOIN_DATE,B_ADDRESS,ADMIN_ID) values ( ?, ?,?,?)");

                    stmt.setString(1, brandname);
                    stmt.setDate(2, date);
                    stmt.setString(3, brandadd);
                    stmt.setString(4, Home.Admin_id);

                    stmt.executeUpdate();
                    System.out.println(" Record inserted");
                    PreparedStatement stmt2 = conn.prepareStatement("SELECT B_ID FROM BRAND WHERE B_NAME=?");
                    stmt2.setString(1, brandname);
                    rs = stmt2.executeQuery();
                    String bid=null;
                    while (rs.next())
                    {
                        bid = rs.getString("B_ID");
                        // System.out.println(cid);
                    }


                    PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO CREDENTIALS (USERNAME, CUST_ID, B_ID, ADMIN_ID) values (?, ?, ? , ?)");
                    stmt3.setString(1,bid);
                    stmt3.setString(2, null);
                    stmt3.setString(3,bid);
                    stmt3.setString(4, null);
                    stmt3.executeUpdate();
                    // conn.close();
                    // rs.close();
                    stmt.close();
                }
                catch (java.sql.SQLException e)
                {

                    System.out.println ("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());

                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                Admin_Landing(conn);
                break;

            case 2:
                Admin_Landing(conn);
                break;

            default:
                System.out.println("Wrong Input!");
                Admin_Landing(conn);
                break;
        }



    }

    static void AddCustomer(Connection conn) throws SQLException, ClassNotFoundException {
        System.out.println("\n Enter Customer name: ");
        input.nextLine();
        String custname = input.nextLine();

        System.out.println("\n Enter Customer Address: ");
        String custadd = input.nextLine();

        System.out.println("\n Enter Customer Phone number: ");
        String c_phone = input.nextLine();

        System.out.println("Choose an option: \n 1. Add Customer" + "\n 2. Go back");
        int option;
        option = input.nextInt();
        switch (option)
        {
            case 1:
                try
                {
                    //Connection conn = DriverManager.getConnection(jdbcURL, user, passwd);
                    Class.forName("oracle.jdbc.OracleDriver");
                    ResultSet rs=null;

                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO CUSTOMER " +
                            "( CUST_NAME, C_ADDRESS,C_PH_NO,ADMIN_ID)" + " values (?, ?,?,?)");

                    stmt.setString(1, custname);
                    stmt.setString(2, custadd);
                    stmt.setString(3, c_phone);
                    stmt.setString(4, Home.Admin_id);

                    stmt.executeUpdate();
                    System.out.println(" Record inserted");


                    PreparedStatement stmt2 = conn.prepareStatement("SELECT CUST_ID,WALLET_ID FROM CUSTOMER WHERE CUST_NAME=? and C_PH_NO=?");
                    stmt2.setString(1, custname);
                    stmt2.setString(2, c_phone);
                    rs = stmt2.executeQuery();
                    String cid=null;
                    String wid=null;
                    while (rs.next())
                    {
                        cid = rs.getString("CUST_ID");
                        wid = rs.getString("WALLET_ID");
                        // System.out.println(cid);
                    }


                    PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO CREDENTIALS (USERNAME, CUST_ID,WALLET_ID, B_ID, ADMIN_ID) values (?, ?,?, ? , ?)");
                    stmt3.setString(1,cid);
                    stmt3.setString(2, cid);
                    stmt3.setString(3, wid);
                    stmt3.setString(4,null);
                    stmt3.setString(5, null);
                    stmt3.executeUpdate();


                    //conn.close();
                    stmt.close();
                }
                catch (java.sql.SQLException e)
                {

                    System.out.println ("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());

                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                Admin_Landing(conn);
                break;

            case 2:
                Admin_Landing(conn);
                break;

            default:
                System.out.println("Wrong Input!");
                Admin_Landing(conn);
                break;
        }
    }


    static void AddActivityType(Connection conn) throws SQLException, ClassNotFoundException {
        System.out.println("\n Enter Activity name: ");
        input.nextLine();
        String act_name = input.nextLine();

        System.out.println("\n Enter Activity code: ");
        String a_code = input.nextLine();

        System.out.println("Choose an option: \n 1. Add Activity Type" + "\n 2. Go back");
        int option;
        option = input.nextInt();
        switch (option)
        {
            case 1:
                try
                {
                    //  Connection conn = DriverManager.getConnection(jdbcURL, user, passwd);
                    Class.forName("oracle.jdbc.OracleDriver");

                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO ACTIVITY_TYPE " +
                            "(A_CODE, A_NAME,ADMIN_ID)" + " values (?, ?,?)");

                    stmt.setString(1, a_code);
                    stmt.setString(2, act_name);
                    stmt.setString(3, Home.Admin_id);
                    stmt.executeUpdate();
                    System.out.println(" Record inserted");
                    // conn.close();
                    stmt.close();
                }
                catch (java.sql.SQLException e)
                {

                    System.out.println ("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());

                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                AddActivityType(conn);
                break;

            case 2:
                Admin_Landing(conn);
                break;

            default:
                System.out.println("Wrong Input!");
                Admin_Landing(conn);
                break;
        }
    }


    static void AddRewardType(Connection conn) throws SQLException, ClassNotFoundException {
        System.out.println("\n Enter Reward name: ");
        input.nextLine();
        String rew_name = input.nextLine();

        System.out.println("\n Enter Reward code: ");
        String r_code = input.nextLine();

        System.out.println("Choose an option: \n 1. Add Reward Type" + "\n 2. Go back");
        int option;
        option = input.nextInt();
        switch (option)
        {
            case 1:
                try
                {
                    //  Connection conn = DriverManager.getConnection(jdbcURL, user, passwd);
                    Class.forName("oracle.jdbc.OracleDriver");


                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO REWARD_TYPE " +
                            "(R_CODE, R_NAME,ADMIN_ID)" + " values (?, ?,?)");

                    stmt.setString(1, r_code);
                    stmt.setString(2, rew_name);
                    stmt.setString(3, Home.Admin_id);
                    stmt.executeUpdate();
                    //conn.close();
                    stmt.close();
                }
                catch (java.sql.SQLException e)
                {

                    System.out.println ("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());

                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                AddRewardType(conn);
                break;

            case 2:
                Admin_Landing(conn);
                break;

            default:
                System.out.println("Wrong Input!");
                Admin_Landing(conn);
                break;
        }
    }

    static void ShowBrandInfo(Connection conn) throws SQLException, ClassNotFoundException {
        try
        {
            // Connection conn = DriverManager.getConnection(jdbcURL, user, passwd);
            Class.forName("oracle.jdbc.OracleDriver");
            ResultSet rs = null;
            Statement result = conn.createStatement();
            rs = result.executeQuery("Select * from BRAND ");
            System.out.println("Brand ID \t Brand Name \n");
            while (rs.next())
            {
                String b_id = rs.getString("B_ID");
                String b_name = rs.getString("B_NAME");

                System.out.println(b_id + ", " + b_name );
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        System.out.println("\n Enter Brand ID: ");
        input.nextLine();
        String b_id = input.nextLine();

        System.out.println("Choose an option: \n 1. Show Brand Info" + "\n 2. Go back");
        int option;
        option = input.nextInt();
        switch (option)
        {
            case 1:
                try
                {
                    // Connection conn = DriverManager.getConnection(jdbcURL, user, passwd);
                    Class.forName("oracle.jdbc.OracleDriver");
                    ResultSet rs = null;

                    PreparedStatement stmt = conn.prepareStatement("select * from BRAND where B_ID=?");
                    stmt.setString(1, b_id);
                    rs = stmt.executeQuery();
                    while (rs.next())
                    {
                        String brand_id = rs.getString("B_ID");
                        String b_name = rs.getString("B_NAME");
                        Date join_date = rs.getDate("JOIN_DATE");
                        String b_address = rs.getString("B_ADDRESS");
                        String admin_id = rs.getString("ADMIN_ID");
                        System.out.println("\n Showing Brand details in Database:");
                        System.out.println(brand_id + ", " + b_name + ", " + join_date +
                                ", " + b_address + ", " + admin_id);
                    }
                    //conn.close();
                    rs.close();
                    stmt.close();
                }
                catch (java.sql.SQLException e)
                {

                    System.out.println ("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());

                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                ShowBrandInfo(conn);
                break;

            case 2:
                Admin_Landing(conn);
                break;

            default:
                System.out.println("Wrong Input, try again");
                ShowBrandInfo(conn);


        }
    }



    static void ShowCustInfo(Connection conn) throws SQLException, ClassNotFoundException {
        try
        {
            // Connection conn = DriverManager.getConnection(jdbcURL, user, passwd);
            Class.forName("oracle.jdbc.OracleDriver");
            ResultSet rs = null;
            Statement result = conn.createStatement();
            rs = result.executeQuery("Select * from CUSTOMER ");
            System.out.println("Customer ID \t Customer Name \n");
            while (rs.next())
            {
                String c_id = rs.getString("CUST_ID");
                String c_name = rs.getString("CUST_NAME");
                System.out.println(c_id + ", " + c_name );
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        System.out.println("\n Enter Customer ID: ");
        input.nextLine();
        String cust_id = input.nextLine();

        System.out.println("Choose an option: \n 1. Show Customer Info" + "\n 2. Go back");
        int option;
        option = input.nextInt();
        switch (option)
        {
            case 1:
                try
                {
                    // Connection conn = DriverManager.getConnection(jdbcURL, user, passwd);
                    Class.forName("oracle.jdbc.OracleDriver");
                    ResultSet rs = null;

                    PreparedStatement stmt = conn.prepareStatement("select * from CUSTOMER where CUST_ID=?");
                    stmt.setString(1, cust_id);
                    rs = stmt.executeQuery();
                    while (rs.next())
                    {
                        String c_id = rs.getString("CUST_ID");
                        String c_name = rs.getString("CUST_NAME");
                        String c_address = rs.getString("C_ADDRESS");
                        int c_number = rs.getInt("C_PH_NO");
                        String admin_id = rs.getString("ADMIN_ID");
                        System.out.println("\n Showing Customer details in Database:");
                        System.out.println(c_id + ", " + c_name + ", " + c_address +
                                ", " + c_number + ", " + admin_id);
                    }
                    stmt.close();
                    //conn.close();
                    rs.close();
                }
                catch (java.sql.SQLException e)
                {

                    System.out.println ("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());

                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                ShowCustInfo(conn);
                break;

            case 2:
                Admin_Landing(conn);
                break;

            default:
                System.out.println("Wrong Input, try again");
                ShowCustInfo(conn);


        }
    }

    static void Admin_Landing(Connection conn) throws SQLException, ClassNotFoundException {
        System.out.println("Choose an option: \n 1. Add Brand" + "\n 2. Add Customer" + "\n 3. Show Brand's info"+ "\n 4. Show Customer's info"+ "\n 5. Add Activity Type"+ "\n 6. Add Reward Activity"+ "\n 7. Logout");

        int option;
        option = input.nextInt();
        switch (option)
        {
            case 1:
                AddBrand(conn);
                break;
            case 2:
                AddCustomer(conn);
                break;
            case 3:
                ShowBrandInfo(conn);
                break;
            case 4:
                ShowCustInfo(conn);
                break;
            case 5:
                AddActivityType(conn);
                break;
            case 6:
                AddRewardType(conn);
                break;
            case 7:
                try
                {
                    //  Connection conn = DriverManager.getConnection(jdbcURL, user, passwd);
                    Class.forName("oracle.jdbc.OracleDriver");
                    // System.out.println("Logged out!");
                    Home.homepage(conn);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                break;

            default:
                System.out.println("\n Invalid Input, try again!");
                Admin_Landing(conn);
                break;
        }

    }





}