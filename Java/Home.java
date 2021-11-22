import java.sql.*;
import java.util.Scanner;


public class Home {


    static Scanner input = new Scanner(System.in);
    static String Brand_id = null, Cust_id = null, Admin_id = null, LP_id = null, Wallet_id = null;
    static Date date = new java.sql.Date(System.currentTimeMillis());

    public Home(Connection conn) {

    }

    static void homepage(Connection conn) throws SQLException, ClassNotFoundException {

        System.out.println("WELCOME TO CUSTOMER LOYALTY PROGRAM");
        System.out.println("Choose from below options:");
        System.out.println("1.Login \n" + "2.Sign Up \n " + "3.Show Queries \n " + "4.EXIT \n");
        int option = input.nextInt();
        switch (option) {
            case 1:
                Login(conn);
                break;
            case 2:
                Sign_Up(conn);
                break;

            case 3:
                ShowQueries(conn);
                break;

            default:
                break;
        }
    }

    static void Login(Connection conn) throws SQLException, ClassNotFoundException {

        System.out.println("Choose \n" + " 1.Sign In \n  " + " 2. Go Back  \n ");


        int option = input.nextInt();

        switch (option) {
            case 1:

                System.out.println("Enter USER ID : ");
                String USER_ID = input.next();

                System.out.println("Enter PASSWORD : ");
                String PASSWORD = input.next();

                try {

                    Class.forName("oracle.jdbc.OracleDriver");
//                    Statement stmt = conn.createStatement(tempQuery);
//                    PreparedStatement stmt = conn.prepareStatement("select * from CREDENTIALS where USERNAME=? AND U_PASSWORD=?");
//                    stmt.setString(1, USER_ID);
//                    stmt.setString(2, PASSWORD);
//                    ResultSet rs = null;
                    String tempQuery = "select * from CREDENTIALS where USERNAME='" + USER_ID + "' AND U_PASSWORD= '" + PASSWORD + "'";
                    Statement stmt = conn.createStatement();
                    //System.out.println("Query: "+ tempQuery);
                    ResultSet rs = stmt.executeQuery(tempQuery);
                    //String myUsername=null;
                    if (rs.next()) {
                        //System.out.println("Entered if condition!!!");
                        //myUsername = rs.getString("username");
                        Brand_id = rs.getString("B_ID");
                        Cust_id = rs.getString("CUST_ID");
                        Wallet_id = rs.getString("WALLET_ID");
                        Admin_id = rs.getString("ADMIN_ID");
                        //System.out.println(Brand_id + " B" +Cust_id+ " C"+Admin_id);
                        //System.out.println(myUsername);
                    }
                    //System.out.println(Admin_id);
                    if (Brand_id != null) {
                        Brands.Brand_Landing(conn);
                    } else if (Cust_id != null) {
                        Customer.customerLanding(conn);
                    } else if (Admin_id != null) {
                        Admin.Admin_Landing(conn);
//                        System.out.println("fweas");
                    } else {
                        System.out.println("Invalid credentials, try again!");
                        Login(conn);
                    }
                } catch (java.sql.SQLException e) {

                    System.out.println("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());

                } catch (Exception e) {
                    System.out.println(e);
                }
                break;

            case 2:
                homepage(conn);
                break;

            default:
                break;
        }
        conn.close();

    }

    static void Sign_Up(Connection conn) throws SQLException, ClassNotFoundException {
        System.out.println("Choose \n 1. Brand sign-up " + "\n 2. Customer Sign-up " + "\n 3. Go Back");

        int option = input.nextInt();
        switch (option) {
            case 1:
                brandSignUp(conn);
                break;
            case 2:
                customerSignUp(conn);
                break;

            case 3:
                homepage(conn);
                break;

            default:
                break;
        }

    }

    static void brandSignUp(Connection conn) throws SQLException, ClassNotFoundException {

        System.out.println("Set Brand USER ID : ");
        input.nextLine();
        String USER_ID = input.nextLine();

        System.out.println("Set PASSWORD : ");
        String PASSWORD = input.nextLine();

        System.out.println("\n Enter Brand Name :");
        String BName = input.nextLine();
        System.out.println("\n Enter Brand Address :");
        String BAddress = input.nextLine();


        System.out.println("Choose \n " + "1. Brand sign-up \n " + " 2. Go Back");
        int option = input.nextInt();

        ResultSet RS = null;
        // check if brand is already signed up
        PreparedStatement stmt = conn.prepareStatement("SELECT B_ID FROM CREDENTIALS WHERE USERNAME=? AND U_PASSWORD=?");
        stmt.setString(1, USER_ID);
        stmt.setString(2, PASSWORD);
        RS = stmt.executeQuery();
        String brand = null;

        while (RS.next()) {
            brand = RS.getString("B_ID");
            System.out.println(brand);
        }

        if (brand != null) {
            System.out.println("Brand already exists, try again.");
            Sign_Up(conn);

        } else {


            switch (option) {
                case 1:
                    Class.forName("oracle.jdbc.OracleDriver");
                    ResultSet rs = null;
                    PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO BRAND (B_NAME, JOIN_DATE, B_ADDRESS, ADMIN_ID) values (?, ?,?,?)");
                    stmt1.setString(1, BName);
                    stmt1.setDate(2, date);
                    stmt1.setString(3, BAddress);
                    stmt1.setString(4, null);
                    stmt1.executeUpdate();

                    PreparedStatement stmt2 = conn.prepareStatement("SELECT B_ID FROM BRAND WHERE B_NAME=?");
                    stmt2.setString(1, BName);
                    rs = stmt2.executeQuery();
                    String brandid = null;
                    while (rs.next()) {
                        brandid = rs.getString("B_ID");
                        System.out.println(brandid);
                    }


                    PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO CREDENTIALS (USERNAME, U_PASSWORD, CUST_ID, B_ID, ADMIN_ID) values (?, ?, ?, ? , ?)");

                    stmt3.setString(1, USER_ID);
                    stmt3.setString(2, PASSWORD);
                    stmt3.setString(3, null);
                    stmt3.setString(4, brandid);
                    stmt3.setString(5, null);
                    stmt3.executeUpdate();

                    System.out.println("Brand Created!");
                    System.out.println("Band Name : " + BName + "\n Brand Address : " + BAddress + "\n Date Of Join : " + date);
                    Login(conn);
                    break;

                case 2:
                    Sign_Up(conn);
                    break;

                default:
                    break;
            }


        }

    }

    static void customerSignUp(Connection conn) throws SQLException, ClassNotFoundException {

        System.out.println("Set Customer USER ID : ");
        input.nextLine();
        String USER_ID = input.nextLine();

        System.out.println("Set PASSWORD : ");
        String PASSWORD = input.nextLine();

        System.out.println("\n Enter Customer name: ");
        String custname = input.nextLine();

        System.out.println("\n Enter Customer Address: ");
        String custadd = input.nextLine();

        System.out.println("\n Enter Customer Phone number: ");
        String c_phone = input.nextLine();


        System.out.println("Choose \n " + "1. Customer sign-up \n" + "2. Go Back");


        int option = input.nextInt();


        ResultSet RS = null;
        // check if brand is already signed up
        PreparedStatement stmt = conn.prepareStatement("SELECT CUST_ID FROM CREDENTIALS WHERE USERNAME=? AND U_PASSWORD=?");
        stmt.setString(1, USER_ID);
        stmt.setString(2, PASSWORD);
        RS = stmt.executeQuery();
        String cust = null;

        while (RS.next()) {
            cust = RS.getString("CUST_ID");
            System.out.println(cust);
        }

        if (cust != null) {
            System.out.println("Customer already exists, try again.");
            Sign_Up(conn);
        } else {

            switch (option) {
                case 1:
                    Class.forName("oracle.jdbc.OracleDriver");
                    ResultSet rs = null;
                    PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO CUSTOMER ( CUST_NAME, C_ADDRESS,C_PH_NO,ADMIN_ID) values (?, ?, ?,?)");
                    stmt1.setString(1, custname);
                    stmt1.setString(2, custadd);
                    stmt1.setString(3, c_phone);
                    stmt1.setString(4, null);
                    stmt1.executeUpdate();

                    PreparedStatement stmt2 = conn.prepareStatement("SELECT CUST_ID, WALLET_ID FROM CUSTOMER WHERE CUST_NAME=? and C_PH_NO=? ");
                    stmt2.setString(1, custname);
                    stmt2.setString(2, c_phone);
                    rs = stmt2.executeQuery();
                    String cid = null;
                    String Wid = null;
                    while (rs.next()) {
                        cid = rs.getString("CUST_ID");
                        Wid = rs.getString("WALLET_ID");
                        System.out.println(cid + " " + Wid);
                    }
                    Wallet_id = Wid;

                    PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO CREDENTIALS (USERNAME, U_PASSWORD, CUST_ID, B_ID, ADMIN_ID) values (?, ?, ?, ? , ?)");

                    stmt3.setString(1, USER_ID);
                    stmt3.setString(2, PASSWORD);
                    stmt3.setString(3, cid);
                    stmt3.setString(4, null);
                    stmt3.setString(5, null);
                    stmt3.executeUpdate();

                    System.out.println("Customer Created!");
                    System.out.println("Customer Name : " + custname + "\n Customer Address : " + custadd + "\n Customer Ph. No : " + c_phone);
                    Login(conn);
                    break;
                case 2:
                    Sign_Up(conn);

                default:
                    break;
            }


        }


    }

    static void ShowQueries(Connection conn) throws SQLException, ClassNotFoundException {
        System.out.println("List Of Queries" + "\n Choose between 1 - 8");
        System.out.println("1. List all customers that are not part of Brand02’s program.\n" +
                "2. List customers that have joined a loyalty program but have not participated in any activity\n" +
                "in that program (list the customerid and the loyalty program id).\n" +
                "3. List the rewards that are part of Brand01 loyalty program.\n" +
                "4. List all the loyalty programs that include “refer a friend” as an activity in at least one of\n" +
                "their reward rules.\n" +
                "5. For Brand01, list for each activity type in their loyalty program, the number instances that\n" +
                "have occurred.\n" +
                "6. List customers of Brand01 that have redeemed at least twice.\n" +
                "7. All brands where total number of points redeemed overall is less than 500 points\n" +
                "8. For Customer C0003, and Brand02, number of activities they have done in the period of\n" +
                "08/1/2021 and 9/30/2021.");

        int option = input.nextInt();

        switch (option) {
            case 1:
                String query = "select cust_id from customer where cust_id not in( select distinct c.cust_id from CUSTOMER C, WALLET W " +
                        "where C.WALLET_ID=W.WALLET_ID and W.B_ID  IN ('Brand02'))";
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(query);
                    System.out.println("Query: \n" + query);
                    System.out.println("Customer ID ");
                    while (rs.next()) {
                        String cust_id = rs.getString("cust_id");
                        System.out.println(cust_id);
                    }
                } catch (SQLException e) {
//                    JDBCTutorialUtilities.printSQLException(e);
                    System.out.println("ERRORR !!!");
                }
                break;

            case 2:
                String query2 = "SELECT W.CUST_ID, W.LP_ID FROM wallet W\n" +
                        "WHERE NOT EXISTS(\n" +
                        "\tSELECT * FROM  CUSTOMER_ACTIVITIES CA\n" +
                        "\tWHERE W.WALLET_ID=CA.WALLET_ID AND W.LP_ID=CA.LP_ID)";
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(query2);
                    System.out.println("Query: \n" + query2);
                    System.out.println("Customer ID, \t Loyalty Program ID");
                    while (rs.next()) {
                        String cust_id = rs.getString("cust_id");
                        String LP_ID = rs.getString("SUP_ID");
                        float price = rs.getFloat("PRICE");
                        int sales = rs.getInt("SALES");
                        int total = rs.getInt("TOTAL");
                        System.out.println(cust_id + ",\t" + LP_ID);
                    }
                } catch (SQLException e) {
//                    JDBCTutorialUtilities.printSQLException(e);
                    System.out.println("ERRORR !!!");
                }
                break;
            case 3:
                String query3 = "SELECT * FROM REWARD R WHERE B_ID = 'Brand01'";
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(query3);
                    System.out.println("Query: \n" + query3);
                    System.out.println("Reward ID, R_code, Gift Card Amount, Free Product Name, No of Rewards");
                    while (rs.next()) {
                        String reward_id = rs.getString("reward_id");
                        String r_code = rs.getString("r_code");
                        int gift_card_amt = rs.getInt("gift_card_amt");
                        String free_prod_name = rs.getString("free_prod_name");
                        int no_of_rewards = rs.getInt("no_of_rewards");
                        System.out.println(reward_id + ",\t\t" + r_code + ",\t\t" + gift_card_amt +
                                ",\t\t" + free_prod_name + ",\t\t" + no_of_rewards);
                    }
                } catch (SQLException e) {
//                    JDBCTutorialUtilities.printSQLException(e);
                    System.out.println("ERRORR !!!");
                }
                break;

            case 4:
                String query4 = "SELECT RE_RULE.LP_ID \n" +
                        "FROM RE_RULE, ACTIVITY_TYPE \n" +
                        "WHERE ACTIVITY_TYPE.A_NAME = 'Refer a friend' AND ACTIVITY_TYPE.A_CODE = RE_RULE.A_CODE";
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(query4);
                    System.out.println("Query: \n" + query4);
                    System.out.println("Loyalty Program ID");
                    while (rs.next()) {
                        String lp_id = rs.getString("lp_id");
                        System.out.println(lp_id);
                    }
                } catch (SQLException e) {
//                    JDBCTutorialUtilities.printSQLException(e);
                    System.out.println("ERRORR !!!");
                }
                break;

            case 5:
                String query5 = "SELECT RE.A_CODE, COUNT(*) AS CT\n" +
                        "FROM RE_RULE RE, CUSTOMER_ACTIVITIES CA\n" +
                        "WHERE RE.B_ID='Brand01' AND CA.B_ID=RE.B_ID AND CA.RE_RULE_CODE=RE.RE_RULE_CODE AND CA.RE_VERSION=RE.RE_VERSION\n" +
                        "GROUP BY RE.A_CODE";
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(query5);
                    System.out.println("Query: \n" + query5);
                    System.out.println("A_code, Count");
                    while (rs.next()) {
                        String a_code = rs.getString("a_code");
                        int count = rs.getInt("ct");
                        System.out.println(a_code + ",\t" + count);
                    }
                } catch (SQLException e) {
//                    JDBCTutorialUtilities.printSQLException(e);
                    System.out.println("ERRORR !!!");
                }
                break;

            case 6:
                String query6 = "SELECT WALLET_ID, COUNT(*) AS CT\n" +
                        "FROM CUSTOMER_ACTIVITIES WHERE RR_RULE_CODE <> 'null' AND B_ID='Brand01'\n" +
                        "GROUP BY WALLET_ID HAVING COUNT(*) >=2";
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(query6);
                    System.out.println("Query: \n" + query6);
                    System.out.println("Wallet ID, Count");
                    while (rs.next()) {
                        String wallet_id = rs.getString("wallet_id");
                        int count = rs.getInt("ct");
                        System.out.println(wallet_id + ",\t" + count);
                    }
                } catch (SQLException e) {
//                    JDBCTutorialUtilities.printSQLException(e);
                    System.out.println("ERRORR !!!");
                }
                break;

            case 7:
                String query7 = "SELECT B_ID,SUM(R_POINTS) AS PTS FROM  WALLET GROUP BY B_ID HAVING SUM(R_POINTS) < 500";
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(query7);
                    System.out.println("Query: \n" + query7);
                    System.out.println("Brand ID, Points redeemed");
                    while (rs.next()) {
                        String b_id = rs.getString("b_id");
                        int points = rs.getInt("pts");
                        System.out.println(b_id + ",\t" + points);
                    }
                } catch (SQLException e) {
//                    JDBCTutorialUtilities.printSQLException(e);
                    System.out.println("ERRORR !!!");
                }
                break;

            case 8:
                String query8 = "SELECT COUNT(*) AS CT FROM CUSTOMER_ACTIVITIES \n" +
                        "WHERE B_ID = 'Brand02' AND RE_RULE_CODE<>'NULL' AND ACTIVITY_DATE>=DATE'2021-08-01' AND ACTIVITY_DATE<=DATE'2021-09-30' \n" +
                        "    AND WALLET_ID IN (SELECT WALLET_ID FROM WALLET WHERE CUST_ID = 'C0003')";
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(query8);
                    System.out.println("Query: \n" + query8);
                    System.out.println("Count");
                    while (rs.next()) {
                        int count = rs.getInt("ct");
                        System.out.println(count);
                    }
                } catch (SQLException e) {
//                    JDBCTutorialUtilities.printSQLException(e);
                    System.out.println("ERRORR !!!");
                }
                break;


            default:
                break;
        }
        System.out.println("Choose:\n1. Try another query\n2. Go to home page");
        int opt = input.nextInt();
        if (opt == 1) {
            ShowQueries(conn);
        } else {
            homepage(conn);
        }
    }


}
