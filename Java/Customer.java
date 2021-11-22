import java.sql.*;
import java.util.Scanner;

public class Customer {

    static Scanner input = new Scanner(System.in);

    public Customer(Connection conn) {

    }

    static void customerLanding(Connection conn) throws SQLException, ClassNotFoundException {

        System.out.println("WELCOME TO CUSTOMER LOYALTY PROGRAM");
        System.out.println("CHOOSE FROM BELOW OPTIONS");
        System.out.println("1. Enroll in Loyalty Program\n" +
                "2. Reward Activities\n" +
                "3. View Wallet\n" +
                "4. Redeem Points\n" +
                "5. Log out");
        int option = input.nextInt();
        switch (option) {
            case 1:
                enrollInLoyaltyProgram(conn);
                break;
            case 2:
                rewardActivities(conn);
                break;

            case 3:
                viewWallet(conn);
                break;

            case 4:
                redeemPoints(conn);
                break;

            case 5:
                Home.homepage(conn);
                break;


            default:
                break;

        }
    }


    static void enrollInLoyaltyProgram(Connection conn) throws SQLException, ClassNotFoundException {

        // display available loyalty program by name!!!!!!!!!!!
        // display brand name and lp names and take (b_id+lp_id ) as input
        // create the wallet
        Class.forName("oracle.jdbc.OracleDriver");
        PreparedStatement stmt = conn.prepareStatement("SELECT LP.B_ID, B.B_NAME, LP.LP_ID, LP.LP_NAME " +
                "FROM BRAND B, LOYALTY_PROGRAM LP " +
                "WHERE LP.B_ID=B.B_ID AND LP.STATE='ACTIVE'");

        ResultSet rs = stmt.executeQuery();

        System.out.println("Brand ID \t Brand Name \t LP ID \t Loyalty Program Name ");
        while (rs.next()) {

            String b_id = rs.getString("B_ID");
            String b_name = rs.getString("B_NAME");
            String lp_id = rs.getString("LP_ID");
            String lp_name = rs.getString("LP_NAME");
            System.out.println(b_id + ", " + b_name + " ," +lp_id + ", " + lp_name);

        }
        System.out.println("Enter brand ID:");
        input.nextLine();
        String brand_id = input.nextLine();
        System.out.println("Enter LP ID:");
        String lp_id = input.nextLine();
        System.out.println("1. Enroll in Loyalty\n" +
                "Program\n" +
                "2. Go back\n");
        int option = input.nextInt();

        switch (option) {
            case 1:
                try {
                    Class.forName("oracle.jdbc.OracleDriver");
                    ResultSet rs1 = null;

                    PreparedStatement stmt1 = conn.prepareStatement("select WALLET_ID " +
                            "from WALLET where CUST_ID=? and B_ID=? and LP_ID=?");

                    stmt1.setString(1, Home.Cust_id);
                    stmt1.setString(2, brand_id);
                    stmt1.setString(3, lp_id);
                    rs1 = stmt1.executeQuery();

                    System.out.println("1st Statement executed");
                    String WalletID = null;
                    while (rs1.next()) {
                        System.out.println("Enter while");
                        WalletID = rs1.getString("WALLET_ID");
                        System.out.println(WalletID);
                    }

                    if (WalletID != null) {
                        System.out.println("Already Enrolled in loyalty program");
                        //System.out.println(rs1.getString("B_ID"));
                    } else {
                        System.out.println("Got to 2nd statement");
//                        PreparedStatement stmt2 = conn.prepareStatement("INSERT into WALLET(WALLET_ID, CUST_ID, B_ID, LP_ID) " +
//                                "values (?,?,?,?)");
//                        stmt2.setString(1, Home.Wallet_id);
//                        stmt2.setString(2, Home.Cust_id);
//                        stmt2.setString(3, brand_id);
//                        stmt2.setString(4, lp_id);
//                        stmt2.executeUpdate();
                        CallableStatement cstmt = conn.prepareCall("{call enrollment(?, ? ,?)}");
                        cstmt.setString(1, brand_id);

                        cstmt.setString(2, Home.Cust_id);
                        cstmt.setString(3, Home.Wallet_id);
                        System.out.println(Home.Wallet_id);
                        //cstmt.setString(4, Home.Brand_id);

                        cstmt.executeUpdate();
                        System.out.println("You are successfully enrolled!!!");
                    }
                    customerLanding(conn);

                } catch (java.sql.SQLException e) {

                    System.out.println("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " +
                            e.getMessage());

                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case 2:
                customerLanding(conn);
                break;

            default:
                break;
        }
    }


    static void rewardActivities(Connection conn) throws SQLException, ClassNotFoundException {

        // display the list of enrolled loyalty programs
        PreparedStatement stmt = conn.prepareStatement("select lp.b_id, lp.lp_id, lp.lp_name " +
                "from loyalty_program lp, wallet w " +
                "where w.cust_id=? and w.lp_id = lp.lp_id");
        stmt.setString(1, Home.Cust_id);
        ResultSet rs = stmt.executeQuery();
        System.out.println("List of enrolled loyalty programs");
        System.out.println("Brand ID \t Brand Name \t LP ID \t Loyalty Program Name ");
        if (rs.next()) {
            System.out.println(rs.getString("b_id") + ", " + rs.getString("lp_id") + ", " + rs.getString("lp_name"));
            while (rs.next()) {
                System.out.println(rs.getString("b_id") + ", " + rs.getString("lp_id") + ", " + rs.getString("lp_name"));
            }

            System.out.println("Select Loyalty Program:");
            System.out.println("Enter Brand ID");
            input.nextLine();
            String B_id = input.nextLine();
            System.out.println("Enter LP ID:");
            String LP_id = input.nextLine();

            stmt = conn.prepareStatement("select distinct re.a_code, a.a_name from re_rule re, activity_type a " +
                    "where re.b_id=? and re.lp_id=? and re.a_code=a.a_code");
            stmt.setString(1, B_id);
            stmt.setString(2, LP_id);
            rs = stmt.executeQuery();

            //Need to display options according to activities available in the specified loyalty program (rs)
            while (rs.next()) {
                System.out.println(rs.getString("a_code") + "\t" + rs.getString("a_name"));
            }
            System.out.println("BACK\t Go back");
            System.out.println("Enter activity code:");


            String option = input.nextLine();
            if (option.equals("BACK")) {
                customerLanding(conn);
            } else {
                stmt = conn.prepareStatement("select a_name from activity_type where a_code=?");
                stmt.setString(1, option);
                rs = stmt.executeQuery();
                rs.next();
                String activity = rs.getString("a_name");
                switch (activity) {
                    case "Purchase":
                        purchase(conn, B_id, LP_id);
                        break;

                    case "Write a review":
                        leaveAReview(conn, B_id, LP_id);
                        break;

                    case "Refer a friend":
                        referAFriend(conn, B_id, LP_id);
                        break;

                    default:
                        System.out.println("Invalid Input. Try Again");
                        rewardActivities(conn);
                        break;
                }
            }
        } else {
            System.out.println("Not enrolled in any loyalty program");
            customerLanding(conn);
        }
    }


    static void viewWallet(Connection conn) throws SQLException, ClassNotFoundException {

        System.out.println("display details of wallet ");
        try {

            Class.forName("oracle.jdbc.OracleDriver");

            System.out.println("Customer ID: " + Home.Cust_id);
            System.out.println("Wallet ID: " + Home.Wallet_id);

            PreparedStatement stmt = conn.prepareStatement("select * from WALLET where CUST_ID =?");
            stmt.setString(1, Home.Cust_id);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int tierStatus = rs.getInt("CUST_TIER_STATUS");
                int ePoints = rs.getInt("E_POINTS");
                int rPoints = rs.getInt("R_POINTS");
                String b_ID = rs.getString("B_ID");
                String lpID = rs.getString("LP_ID");

                System.out.println("\n Showing Brand details in Database:");
                System.out.println("\nbrand Id : " + b_ID + "\nloyalty Id : " + ", " + lpID + "\ntier Status  :" + tierStatus +
                        ", " + "\nEarned Points : " + ePoints + ", " + "\nreward points :" + rPoints);
            }

        } catch (SQLException e) {
            // JDBCTutorialUtilities.printSQLException(e);
            System.out.println("ERRORRR!!");
        }
        System.out.println("1. Go back");
        int option = input.nextInt();
        switch (option) {
            case 1:
                customerLanding(conn);
                break;
            default:
                break;
        }
    }


    static void redeemPoints(Connection conn) throws SQLException, ClassNotFoundException {

        // Select which loyalty program:
        PreparedStatement stmt = conn.prepareStatement("select lp.b_id, lp.lp_id, lp.lp_name " +
                "from loyalty_program lp, wallet w " +
                "where w.cust_id=? and w.lp_id = lp.lp_id and w.b_id = lp.b_id");
        stmt.setString(1, Home.Cust_id);
        ResultSet rs = stmt.executeQuery();
        System.out.println("List of enrolled loyalty programs");
        System.out.println("Brand ID \t Brand Name \t LP ID \t Loyalty Program Name ");
        while (rs.next()) {
            System.out.println(rs.getString("b_id") + " \t" + rs.getString("lp_id") + " \t" + rs.getString("lp_name"));
        }

        System.out.println("Select Loyalty Program:");
        System.out.println("Enter Brand ID:");
        input.nextLine();
        String B_id = input.nextLine();
        System.out.println("Enter LP ID:");
        String LP_id = input.nextLine();

        // Display available rewards for specified loyalty program
        stmt = conn.prepareStatement("select distinct rt.r_code, rt.r_name from rr_rule rr, reward r, reward_type rt " +
                "where rr.b_id=? and rr.lp_id=? and rr.reward_id=r.reward_id and rt.r_code=r.r_code");
        stmt.setString(1, B_id);
        stmt.setString(2, LP_id);
        rs = stmt.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("r_code") + "\t" + rs.getString("r_name"));
        }
        System.out.println("BACK\t Go back");
        System.out.println("Enter reward type code:");
        String r_code = input.nextLine();
        String reward_type = null;
        if (r_code.equals("BACK")) {
            customerLanding(conn);
        } else {
            System.out.println("Fetching R_name");
            stmt = conn.prepareStatement("select r_name from reward_type where r_code=?");
            stmt.setString(1, r_code);
            rs = stmt.executeQuery();
            if (rs.next()) {
                reward_type = rs.getString("r_name");
                System.out.println("Fetched R_name");
                if (reward_type.equals("Gift Card")) {
                    System.out.println("Fetching available gift cards");
                    //Display giftcards
                    stmt = conn.prepareStatement("select r.reward_id, r.gift_card_amt, rr.points from reward r, rr_rule rr " +
                            "where r.b_id=? and r.lp_id=? and r.r_code=? and r.no_of_rewards>0 " +
                            "and r.b_id = rr.b_id and r.lp_id = rr.lp_id and r.reward_id=rr.reward_id " +
                            "and rr.rr_version = (select max(rr2.rr_version) from reward r2, rr_rule rr2 " +
                            "where r2.b_id=? and r2.lp_id=? and r2.r_code=? and r2.no_of_rewards>0 " +
                            "and r2.b_id = rr2.b_id and r2.lp_id = rr2.lp_id and r2.reward_id=rr2.reward_id)");
                    stmt.setString(1, B_id);
                    stmt.setString(2, LP_id);
                    stmt.setString(3, r_code);
                    stmt.setString(4, B_id);
                    stmt.setString(5, LP_id);
                    stmt.setString(6, r_code);
                    rs = stmt.executeQuery();
                    rs.next();
                    if (rs.getString("reward_id") != null) {
                        System.out.println("Entered if");
                        System.out.println("reward_id: " + rs.getString("reward_id") +
                                "\tGift Card Amount: " + rs.getInt("gift_card_amt") +
                                "\tPoints: " + rs.getInt("points"));
                        while (rs.next()) {
                            System.out.println("reward_id: " + rs.getString("reward_id") +
                                    "\tGift Card Amount: " + rs.getInt("gift_card_amt") +
                                    "\tPoints: " + rs.getInt("points"));
                        }
                        System.out.println("Enter reward_id");
                        String reward_id = input.nextLine();

                        stmt = conn.prepareStatement("select rr.points, rr.rr_rule_code, rr.rr_version " +
                                "from rr_rule rr, reward r " +
                                "where r.reward_id = ? and r.b_id = ? and r.lp_id = ? " +
                                "and r.b_id = rr.b_id and r.lp_id = rr.lp_id and r.reward_id=rr.reward_id " +
                                "and rr.rr_version = (select max(rr2.rr_version) from rr_rule rr2, reward r2 " +
                                "where r.reward_id = ? and r.b_id = ? and r.lp_id = ? " +
                                "and r.b_id = rr.b_id and r.lp_id = rr.lp_id and r.reward_id=rr.reward_id)");
                        stmt.setString(1, reward_id);
                        stmt.setString(2, B_id);
                        stmt.setString(3, LP_id);
                        stmt.setString(4, reward_id);
                        stmt.setString(5, B_id);
                        stmt.setString(6, LP_id);
                        rs = stmt.executeQuery();

                        while (!rs.next()) {
                            System.out.println("Invalid input. Enter valid reward_id: ");
                            reward_id = input.nextLine();
                            stmt = conn.prepareStatement("select rr.points, rr.rr_rule_code, rr.rr_version " +
                                    "from rr_rule rr, reward r " +
                                    "where r.reward_id = ? and r.b_id = ? and r.lp_id = ? " +
                                    "and r.b_id = rr.b_id and r.lp_id = rr.lp_id and r.reward_id=rr.reward_id " +
                                    "and rr.rr_version = (select max(rr2.rr_version) from rr_rule rr2, reward r2 " +
                                    "where r.reward_id = ? and r.b_id = ? and r.lp_id = ? " +
                                    "and r.b_id = rr.b_id and r.lp_id = rr.lp_id and r.reward_id=rr.reward_id)");
                            stmt.setString(1, reward_id);
                            stmt.setString(2, B_id);
                            stmt.setString(3, LP_id);
                            stmt.setString(4, reward_id);
                            stmt.setString(5, B_id);
                            stmt.setString(6, LP_id);
                            rs = stmt.executeQuery();
                        }

                        int points = rs.getInt("points");
                        String rule_code = rs.getString("rr_rule_code");
                        String version = rs.getString("rr_version");

                        System.out.println("Choose 1. Rewards Selection\n" +
                                "2. Go back ");
                        int option = input.nextInt();

                        switch (option) {
                            case 1:

                                if (removePoints(conn, B_id, LP_id, points)) {
                                    stmt = conn.prepareStatement("select gift_card_amt from reward " +
                                            "where b_id=? and lp_id=? and reward_id=?");
                                    stmt.setString(1, B_id);
                                    stmt.setString(2, LP_id);
                                    stmt.setString(3, reward_id);
                                    rs = stmt.executeQuery();
                                    rs.next();
                                    int gift_card_amt = rs.getInt("gift_card_amt");

                                    stmt = conn.prepareStatement("insert into customer_activities" +
                                            "(wallet_id, b_id, lp_id, rr_rule_code, rr_version, activity_date, gift_amt, gift_card_used)" +
                                            " values (?,?,?,?,?,?,?,?)");
                                    stmt.setString(1, Home.Wallet_id);
                                    stmt.setString(2, B_id);
                                    stmt.setString(3, LP_id);
                                    stmt.setString(4, rule_code);
                                    stmt.setString(5, version);
                                    stmt.setDate(6, Home.date);
                                    stmt.setInt(7, gift_card_amt);
                                    stmt.setInt(8, 0);
                                    stmt.executeUpdate();

//                        Need to decrement number of instances by 1
                                    stmt = conn.prepareStatement("update reward set no_of_rewards=no_of_rewards-1 " +
                                            "where b_id=? and lp_id=? and reward_id=?");
                                    stmt.setString(1, B_id);
                                    stmt.setString(2, LP_id);
                                    stmt.setString(3, reward_id);
                                    stmt.executeQuery();
                                    System.out.println("Gift card availed");
                                } else System.out.println("Insufficient balance points");

                                break;

                            case 2:
                                customerLanding(conn);
                                break;

                            default:
                                break;
                        }


                    } else {
                        System.out.println("No available gift cards");
                    }

                }
                //This stuff pending
                else if (reward_type.equals("Free Product")) {

                    //Display free products
                    stmt = conn.prepareStatement("select r.reward_id, r.free_prod_name, rr.points from reward r, rr_rule rr " +
                            "where r.b_id=? and r.lp_id=? and r.r_code=? and r.no_of_rewards>0 " +
                            "and r.b_id = rr.b_id and r.lp_id = rr.lp_id and r.reward_id=rr.reward_id " +
                            "and rr.rr_version = (select max(rr2.rr_version) from reward r2, rr_rule rr2 " +
                            "where r2.b_id=? and r2.lp_id=? and r2.r_code=? and r2.no_of_rewards>0 " +
                            "and r2.b_id = rr2.b_id and r2.lp_id = rr2.lp_id and r2.reward_id=rr2.reward_id)");
                    stmt.setString(1, B_id);
                    stmt.setString(2, LP_id);
                    stmt.setString(3, r_code);
                    stmt.setString(4, B_id);
                    stmt.setString(5, LP_id);
                    stmt.setString(6, r_code);
                    rs = stmt.executeQuery();

                    if (rs.next()) {
                        System.out.println("reward_id: " + rs.getString("reward_id") +
                                "\tProduct Name: " + rs.getString("free_prod_name") +
                                "\tPoints: " + rs.getInt("points"));
                        while (rs.next()) {
                            System.out.println("reward_id: " + rs.getString("reward_id") +
                                    "\tProduct Name: " + rs.getString("free_prod_name") +
                                    "\tPoints: " + rs.getInt("points"));
                        }

                        System.out.println("Enter reward_id");

                        String reward_id = input.nextLine();
                        stmt = conn.prepareStatement("select rr.points, rr.rr_rule_code, rr.rr_version " +
                                "from rr_rule rr, reward r " +
                                "where r.reward_id = ? and r.b_id = ? and r.lp_id = ? " +
                                "and r.b_id = rr.b_id and r.lp_id = rr.lp_id and r.reward_id=rr.reward_id " +
                                "and rr.rr_version = (select max(rr2.rr_version) from rr_rule rr2, reward r2 " +
                                "where r.reward_id = ? and r.b_id = ? and r.lp_id = ? " +
                                "and r.b_id = rr.b_id and r.lp_id = rr.lp_id and r.reward_id=rr.reward_id)");
                        stmt.setString(1, reward_id);
                        stmt.setString(2, B_id);
                        stmt.setString(3, LP_id);
                        stmt.setString(4, reward_id);
                        stmt.setString(5, B_id);
                        stmt.setString(6, LP_id);
                        rs = stmt.executeQuery();

                        while (!rs.next()) {
                            System.out.println("Invalid input. Enter valid reward_id: ");
                            reward_id = input.nextLine();
                            stmt = conn.prepareStatement("select rr.points, rr.rr_rule_code, rr.rr_version " +
                                    "from rr_rule rr, reward r " +
                                    "where r.reward_id = ? and r.b_id = ? and r.lp_id = ? " +
                                    "and r.b_id = rr.b_id and r.lp_id = rr.lp_id and r.reward_id=rr.reward_id " +
                                    "and rr.rr_version = (select max(rr2.rr_version) from rr_rule rr2, reward r2 " +
                                    "where r.reward_id = ? and r.b_id = ? and r.lp_id = ? " +
                                    "and r.b_id = rr.b_id and r.lp_id = rr.lp_id and r.reward_id=rr.reward_id)");
                            stmt.setString(1, reward_id);
                            stmt.setString(2, B_id);
                            stmt.setString(3, LP_id);
                            stmt.setString(4, reward_id);
                            stmt.setString(5, B_id);
                            stmt.setString(6, LP_id);
                            rs = stmt.executeQuery();
                        }

                        int points = rs.getInt("points");
                        String rule_code = rs.getString("rr_rule_code");
                        String version = rs.getString("rr_version");

                        System.out.println("Choose 1. Rewards Selection\n" +
                                "2. Go back ");
                        int option = input.nextInt();

                        switch (option) {
                            case 1:

                                if (removePoints(conn, B_id, LP_id, points)) {
                                    stmt = conn.prepareStatement("select free_prod_name from reward " +
                                            "where b_id=? and lp_id=? and reward_id=?");
                                    stmt.setString(1, B_id);
                                    stmt.setString(2, LP_id);
                                    stmt.setString(3, reward_id);
                                    rs = stmt.executeQuery();
                                    rs.next();
                                    String free_prod_name = rs.getString("free_prod_name");

                                    stmt = conn.prepareStatement("insert into customer_activities" +
                                            "(wallet_id, b_id, lp_id, rr_rule_code, rr_version, activity_date, prod_name)" +
                                            " values (?,?,?,?,?,?,?)");
                                    stmt.setString(1, Home.Wallet_id);
                                    stmt.setString(2, B_id);
                                    stmt.setString(3, LP_id);
                                    stmt.setString(4, rule_code);
                                    stmt.setString(5, version);
                                    stmt.setDate(6, Home.date);
                                    stmt.setString(7, free_prod_name);
                                    stmt.executeUpdate();

//                        Need to decrement number of instances by 1
                                    stmt = conn.prepareStatement("update reward set no_of_rewards=no_of_rewards-1 " +
                                            "where b_id=? and lp_id=? and reward_id=?");
                                    stmt.setString(1, B_id);
                                    stmt.setString(2, LP_id);
                                    stmt.setString(3, reward_id);
                                    stmt.executeQuery();
                                    System.out.println("Free Product availed");
                                } else System.out.println("Insufficient balance points");
                                break;

                            case 2:

                                customerLanding(conn);
                                break;

                            default:

                                break;

                        }


                    } else {
                        System.out.println("No available free products");
                    }
                }
                rewardActivities(conn);
            } else {
                System.out.println("Invalid input");
                redeemPoints(conn);
            }
        }

    }


    static void purchase(Connection conn, String B_id, String LP_id) throws SQLException, ClassNotFoundException {

        System.out.println("Enter purchase amount: ");
        int purchase_amount = input.nextInt();

        PreparedStatement stmt = conn.prepareStatement("select ca_id, gift_amt from customer_activities " +
                "where wallet_id=? and b_id=? and lp_id=? and  gift_card_used=0");
        stmt.setString(1, Home.Wallet_id);
        stmt.setString(2, B_id);
        stmt.setString(3, LP_id);
        ResultSet rs = stmt.executeQuery();
        // display giftcards and choose
        String id = "None";
        if (rs.next()) {
            System.out.println("You can use a gift card to make the purchase.");
            System.out.println("ID: " + rs.getString("ca_id") + "\tGift card amount: " + rs.getInt("gift_amt"));
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("ca_id") + "\tGift card amount: " + rs.getInt("gift_amt"));
            }
            System.out.println("If you want to use a gift card enter the ID. Otherwise, enter \'None\'");
            input.nextLine();
            id = input.nextLine();

        }

        System.out.println("choose 1. purchase\n" +
                "2. Go back");

        int option = input.nextInt();
        switch (option) {
            case 1:

                if (!id.equals("None")) {
                    stmt = conn.prepareStatement("select gift_amt from customer_activities where ca_id = ?");
                    stmt.setString(1, id);
                    rs = stmt.executeQuery();
                    rs.next();
                    int gift_amount = rs.getInt("gift_amt");

                    stmt = conn.prepareStatement("update customer_activities set gift_card_used=1 where ca_id = ?");
                    stmt.setString(1, id);
                    stmt.executeUpdate();
                    purchase_amount = purchase_amount - gift_amount;
                }

                if (purchase_amount > 0) {

                    stmt = conn.prepareStatement("select re.points, re.re_rule_code, re.re_version " +
                            "from re_rule re, activity_type a " +
                            "where a.a_name='Purchase' and a.a_code=re.a_code and re.b_id = ? and re.lp_id = ? " +
                            "and re.re_version = (select max(re_version) as temp " +
                            "from re_rule re, activity_type a " +
                            "where a.a_code=re.a_code and re.b_id = ? and re.lp_id = ?)");
                    stmt.setString(1, B_id);
                    stmt.setString(2, LP_id);
                    stmt.setString(3, B_id);
                    stmt.setString(4, LP_id);
                    rs = stmt.executeQuery();
                    rs.next();
                    int points = rs.getInt("points");
                    String rule_code = rs.getString("re_rule_code");
                    String version = rs.getString("re_version");

                    addPoints(conn, B_id, LP_id, points);

                    stmt = conn.prepareStatement("insert into customer_activities" +
                            "(wallet_id, b_id, lp_id, re_rule_code, re_version, activity_date, purchase_amt) values (?,?,?,?,?,?,?)");
                    stmt.setString(1, Home.Wallet_id);
                    stmt.setString(2, B_id);
                    stmt.setString(3, LP_id);
                    stmt.setString(4, rule_code);
                    stmt.setString(5, version);
                    stmt.setDate(6, Home.date);
                    stmt.setInt(7, purchase_amount);
                    stmt.executeUpdate();
                } else {
                    stmt = conn.prepareStatement("insert into customer_activities" +
                            "(wallet_id, b_id, lp_id, activity_date, purchase_amt) values (?,?,?,?,?)");
                    stmt.setString(1, Home.Wallet_id);
                    stmt.setString(2, B_id);
                    stmt.setString(3, LP_id);
                    stmt.setDate(4, Home.date);
                    stmt.setInt(5, 0);
                    stmt.executeUpdate();
                }
                System.out.println("Purchase recorded!");

                rewardActivities(conn);
                break;
            case 2:
                customerLanding(conn);
                break;
            default:
                break;
        }


    }


    static void leaveAReview(Connection conn, String B_id, String LP_id) throws SQLException, ClassNotFoundException {

        System.out.println("Please write us the review of product");
        String review = input.nextLine();
        System.out.println("choose 1. Leave a review\n" +
                "2. Go back");
        int option = input.nextInt();
        switch (option) {
            case 1:

                Class.forName("oracle.jdbc.OracleDriver");
                PreparedStatement stmt = conn.prepareStatement("select re.points, re.re_rule_code, re.re_version " +
                        "from re_rule re, activity_type a " +
                        "where a.a_name='Write a review' and a.a_code=re.a_code and re.b_id = ? and re.lp_id = ? " +
                        "and re.re_version = (select max(re_version) as temp " +
                        "from re_rule re, activity_type a " +
                        "where a.a_code=re.a_code and re.b_id = ? and re.lp_id = ?)");
                stmt.setString(1, B_id);
                stmt.setString(2, LP_id);
                stmt.setString(3, B_id);
                stmt.setString(4, LP_id);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                int points = rs.getInt("points");
                String rule_code = rs.getString("re_rule_code");
                String version = rs.getString("re_version");

                addPoints(conn, B_id, LP_id, points);

                System.out.println("Insert into customer_activities");
                PreparedStatement stmt1 = conn.prepareStatement("insert into customer_activities" +
                        "(wallet_id, b_id, lp_id, re_rule_code, re_version, activity_date, review) values (?,?,?,?,?,?,?)");
                stmt1.setString(1, Home.Wallet_id);
                stmt1.setString(2, B_id);
                stmt1.setString(3, LP_id);
                stmt1.setString(4, rule_code);
                stmt1.setString(5, version);
                stmt1.setDate(6, Home.date);
                stmt1.setString(7, review);
                stmt1.executeUpdate();
                System.out.println("review added!");

                rewardActivities(conn);
                break;

            case 2:
                customerLanding(conn);
                break;

            default:
                break;
        }

    }


    static void referAFriend(Connection conn, String B_id, String LP_id) throws SQLException, ClassNotFoundException {

        System.out.println("choose 1. Refer\n" +
                "2. Go back\n ");
        int option = input.nextInt();
        switch (option) {
            case 1:

                Class.forName("oracle.jdbc.OracleDriver");
                PreparedStatement stmt = conn.prepareStatement("select re.points, re.re_rule_code, re.re_version " +
                        "from re_rule re, activity_type a " +
                        "where a.a_name='Refer a friend' and a.a_code=re.a_code and re.b_id = ? and re.lp_id = ? " +
                        "and re.re_version = (select max(re_version) as temp " +
                        "from re_rule re, activity_type a " +
                        "where a.a_name='Refer a friend' and a.a_code=re.a_code and re.b_id = ? and re.lp_id = ?)");
                stmt.setString(1, B_id);
                stmt.setString(2, LP_id);
                stmt.setString(3, B_id);
                stmt.setString(4, LP_id);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                int points = rs.getInt("points");
                String rule_code = rs.getString("re_rule_code");
                String version = rs.getString("re_version");

                addPoints(conn, B_id, LP_id, points);

                PreparedStatement stmt1 = conn.prepareStatement("insert into customer_activities" +
                        "(wallet_id, b_id, lp_id, re_rule_code, re_version, activity_date) values (?,?,?,?,?,?)");
                stmt1.setString(1, Home.Wallet_id);
                stmt1.setString(2, B_id);
                stmt1.setString(3, LP_id);
                stmt1.setString(4, rule_code);
                stmt1.setString(5, version);
                stmt1.setDate(6, Home.date);
                stmt1.executeQuery();

                System.out.println("Referral successful!");
                customerLanding(conn);
                break;

            case 2:
                customerLanding(conn);
                break;
            default:
                break;
        }
    }


    static void addPoints(Connection conn, String B_id, String LP_id, int points) throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.OracleDriver");
        PreparedStatement stmt = conn.prepareStatement("select e_points, cust_tier_status from wallet where cust_id=? and b_id=? and lp_id=?");
        stmt.setString(1, Home.Cust_id);
        stmt.setString(2, B_id);
        stmt.setString(3, LP_id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int currentPoints = rs.getInt("e_points");
        int tier_status = rs.getInt("cust_tier_status");

        stmt = conn.prepareStatement("select multiplier from tier where b_id=? and lp_id=? and tier_id=?");
        stmt.setString(1, B_id);
        stmt.setString(2, LP_id);
        stmt.setInt(3, tier_status);
        rs = stmt.executeQuery();
        rs.next();
        int multiplier = rs.getInt("multiplier");

        currentPoints = currentPoints + points * multiplier;

        stmt = conn.prepareStatement("update wallet set e_points = ? where cust_id=? and b_id=? and lp_id=?");
        stmt.setInt(1, currentPoints);
        stmt.setString(2, Home.Cust_id);
        stmt.setString(3, B_id);
        stmt.setString(4, LP_id);
        stmt.executeUpdate();

        System.out.println("Added points " + points);

        // Checks if tier can be upgraded.
        stmt = conn.prepareStatement("select tier_name, points_threshold from tier where b_id=? and lp_id=? and tier_id=?");
        stmt.setString(1, B_id);
        stmt.setString(2, LP_id);
        stmt.setInt(3, tier_status + 1);
        rs = stmt.executeQuery();
        if (rs.next()) {
            if (currentPoints > rs.getInt("points_threshold")) {

                stmt = conn.prepareStatement("update wallet set cust_tier_status = ? where cust_id=? and b_id=? and lp_id=?");
                stmt.setInt(1, tier_status + 1);
                stmt.setString(2, Home.Cust_id);
                stmt.setString(3, B_id);
                stmt.setString(4, LP_id);
                stmt.executeUpdate();

                System.out.println("Upgraded tier status to " + rs.getString("tier_name") + "!");

            }
        }

    }


    static boolean removePoints(Connection conn, String B_id, String LP_id, int points) throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.OracleDriver");
        PreparedStatement stmt = conn.prepareStatement("select e_points, r_points from wallet " +
                "where cust_id=? and b_id=? and lp_id=?");
        stmt.setString(1, Home.Cust_id);
        stmt.setString(2, B_id);
        stmt.setString(3, LP_id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int earnedPoints = rs.getInt("e_points");
        int redeemedPoints = rs.getInt("r_points");
        //int tier_status = rs.getInt("cust_tier_status");

        if ((earnedPoints - redeemedPoints) < points) return false;

        redeemedPoints = redeemedPoints + points;

        stmt = conn.prepareStatement("update wallet set r_points = ? where cust_id=? and b_id=? and lp_id=?");
        stmt.setInt(1, redeemedPoints);
        stmt.setString(2, Home.Cust_id);
        stmt.setString(3, B_id);
        stmt.setString(4, LP_id);
        stmt.executeUpdate();

        System.out.println("Removed points " + points);
        return true;

    }


}


