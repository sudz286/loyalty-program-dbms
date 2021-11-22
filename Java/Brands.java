
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;

public class Brands {


    // public void Brands(Connection con) {

    // }

    static Scanner input = new Scanner(System.in);
    static Integer lp_tier = null;



    static void Brand_Landing(Connection conn) throws SQLException, ClassNotFoundException {


        System.out.println(" Choose \n " + "1. addLoyaltyProgram\n" +
                "2. addRERules\n" +
                "3. updateRERules\n" +
                "4. addRRRules\n" +
                "5. updateRRRules\n" +
                "6. validateLoyaltyProgram\n" +
                "7. Log out");

        int option = input.nextInt();


        switch (option){

            case 1:
                addLoyaltyProgram(conn);
                break;

            case 2:
                addRERules(conn);
                break;
            case 3:
                updateRERules(conn);
                break;

            case 4:
                addRRRules(conn);
                break;

            case 5:
                updateRRRules(conn);
                break;

            case 6:
                validateLoyaltyProgram(conn);
                break;

            case 7:
                Home.homepage(conn);
                break;

            default:
                break;
        }


    }

    static  void addLoyaltyProgram(Connection conn) throws SQLException, ClassNotFoundException {
        // check if brand is not associated with any loyalty programs
        // if so enroll if not exist
        int option=-1;
        String LPName = null;
        if(lp_tier==null)
        {
            ResultSet rs1 = null;
            PreparedStatement stmt1 = conn.prepareStatement("SELECT LP_ID FROM LOYALTY_PROGRAM WHERE B_ID=?");
            stmt1.setString(1, Home.Brand_id);




            rs1 = stmt1.executeQuery();
            String lp_id=null;
            while (rs1.next())
            {
                lp_id = rs1.getString("LP_ID");

            }

            if(lp_id== null){
                System.out.println("Enter LP Name:");
                input.nextLine();
                LPName =input.nextLine();
                //System.out.println(LPName);
                System.out.println(" Choose "+"\n 1. Regular" +
                        "2. Tier" +
                        "3. Go back");
                option = input.nextInt();

            }else{
                System.out.println("Already enrolled in loyalty program");
                Brand_Landing(conn);
            }
        }
        else{
            System.out.println(" Choose "+"\n 1. Regular" +
                    "2. Tier" +
                    "3. Go back");
            option = input.nextInt();

        }


        switch (option){

            case 1:
                try
                {
                    Class.forName("oracle.jdbc.OracleDriver");

                    CallableStatement cstmt = conn.prepareCall("{call addLoyaltyProgram(?, ? ,?)}");
                    cstmt.setString(1, Home.Brand_id);
                    cstmt.setString(2, LPName);
                    cstmt.setString(3, "Regular");
                    cstmt.executeUpdate();
                    PreparedStatement stmt2 = conn.prepareStatement("SELECT LP_ID FROM LOYALTY_PROGRAM WHERE LP_NAME=?");
                    stmt2.setString(1, LPName);
                    ResultSet rs=null;
                    rs = stmt2.executeQuery();
                    //System.out.println(LPName);
                    String lpid=null;
                    while (rs.next())
                    {
                        lpid = rs.getString("LP_ID");

                    }
                    Home.LP_id=lpid;
                    System.out.println("Enrolled into "+LPName+" program successfully");
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }

                Regular(conn);
                break;

            case 2:
                try
                {
                    Class.forName("oracle.jdbc.OracleDriver");

                    CallableStatement cstmt = conn.prepareCall("{call addLoyaltyProgram(?, ? ,?)}");
                    cstmt.setString(1, Home.Brand_id);
                    cstmt.setString(2, LPName);
                    cstmt.setString(3, "Tier");
                    cstmt.executeUpdate();

                    PreparedStatement stmt2 = conn.prepareStatement("SELECT LP_ID FROM LOYALTY_PROGRAM WHERE LP_NAME=?");
                    stmt2.setString(1, LPName);
                    ResultSet rs=null;
                    rs = stmt2.executeQuery();
                    String lpid=null;
                    while (rs.next())
                    {
                        lpid = rs.getString("LP_ID");

                    }
                    Home.LP_id=lpid;
                    System.out.println("Enrolled into"+LPName+" program successfully");
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                Tier(conn);
                break;
            case 3:
                Brand_Landing(conn);
                break;
            default:
                break;
        }


    }


    static  void addRERules(Connection conn) throws SQLException, ClassNotFoundException {

        PreparedStatement stmt = conn.prepareStatement("Select A_CODE,A_NAME from ACTIVITY_TYPE where A_CODE IN (Select A_CODE from ACTIVITY where B_ID=?)");
        stmt.setString(1, Home.Brand_id);
        ResultSet rs = stmt.executeQuery();

        System.out.println("Activity Code \t Activity Name");
        while (rs.next()) {
            System.out.println(rs.getString("A_CODE")+"\t"+rs.getString("A_NAME"));
        }
        System.out.println("Input activity code of the activity for which you need to add RE Rule");
        input.nextLine();
        String a_code = input.nextLine();

        System.out.println("ADD new RE Rule ");
        System.out.println("A. Input brand RE rule code ");
        String reRuleCode = input.nextLine();
        // System.out.println("B. Activity Type");
        // String activityType =input.nextLine();
        System.out.println("C. Number of Points");
        int points =input.nextInt();

        System.out.println(" 1. addRERule\n" +
                "2. Go Back");

        int option = input.nextInt();
        // System.out.println("Input :"+option);
        switch (option){
            case 1:
                try
                {
                    Class.forName("oracle.jdbc.OracleDriver");
                    //System.out.println("Adding RE :"+option);

                    CallableStatement cstmt = conn.prepareCall("{call addRERule(?, ? ,?,?)}");
                    cstmt.setString(1, reRuleCode);
                    cstmt.setString(2, a_code);
                    cstmt.setInt(3, points);
                    cstmt.setString(4, Home.Brand_id);

                    cstmt.executeUpdate();
                }
                catch (java.sql.SQLException e)
                {

                    if(e.getMessage().contains("integrity constraint")){
                        System.out.println("Activity type does not exist");
                        addRERules(conn);
                    }else{
                        System.out.println ("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());

                    }

                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                addRERules(conn);

            case 2:
                Brand_Landing(conn);
                break;
            default:
                break;
        }

    }

    static  void updateRERules(Connection conn) throws SQLException, ClassNotFoundException {
        System.out.println(" 1.  updateRERule\n" +
                "2. Go Back");

        int option = input.nextInt();
        switch (option){
            case 1:
                System.out.println("Update new RE Rule ");
                System.out.println("A. Input brand rule code ");
                input.nextLine();
                String reRuleCode = input.nextLine();
                System.out.println("B. Activity Type");
                String activityType =input.nextLine();
                System.out.println("C. Number of Points");
                int points =input.nextInt();
                try
                {
                    Class.forName("oracle.jdbc.OracleDriver");

                    CallableStatement cstmt = conn.prepareCall("{call updateRERule(?, ? ,?,?)}");
                    cstmt.setString(1, reRuleCode);
                    cstmt.setString(2, activityType);
                    cstmt.setInt(3, points);
                    cstmt.setString(4, Home.Brand_id);

                    cstmt.executeUpdate();

                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                updateRERules(conn);
                break;
            case 2:
                Brand_Landing(conn);
                break;
            default:
                break;
        }

    }


    static  void addRRRules(Connection conn) throws SQLException, ClassNotFoundException {


        PreparedStatement stmt = conn.prepareStatement("SELECT REWARD_ID,RT.R_NAME FROM REWARD R, REWARD_TYPE RT WHERE R.R_CODE=RT.R_CODE and B_ID=?");
        stmt.setString(1, Home.Brand_id);
        ResultSet rs = stmt.executeQuery();
        System.out.println("List of enrolled rewards in the loyalty program");
        System.out.println("Reward ID \t Reward Name");
        while (rs.next()) {
            System.out.println(rs.getString("REWARD_ID") + "\t" + rs.getString("R_NAME"));
        }

        System.out.println("Select Reward ID of the Reward for which you need to add RR Rule:");
        input.nextLine();
        String rewardid = input.nextLine();

        System.out.println("ADD new RR Rule ");
        System.out.println("A. Input brand reward code ");
        String rrRuleCode = input.nextLine();
        // System.out.println("B. Reward Type");
        // String rewardType =input.nextLine();
        System.out.println("C. Number of Points");
        int points =input.nextInt();
        System.out.println(" 1. addRRRule\n" +
                "2. Go Back");

        int option = input.nextInt();
        switch (option){
            case 1:
                try{

                    Class.forName("oracle.jdbc.OracleDriver");

                    CallableStatement cstmt = conn.prepareCall("{call addRRRule(?, ? ,?,?)}");
                    cstmt.setString(1, rrRuleCode);
                    cstmt.setString(2, rewardid);
                    cstmt.setInt(3, points);
                    cstmt.setString(4, Home.Brand_id);

                    cstmt.executeUpdate();
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                addRRRules(conn);

            case 2:
                Brand_Landing(conn);
                break;
            default:
                break;
        }

    }


    static  void updateRRRules(Connection conn) throws SQLException, ClassNotFoundException {
        System.out.println(" 1.  updateRRRule\n" +
                "2. Go Back");

        int option = input.nextInt();
        switch (option){
            case 1:
                System.out.println("Update RR Rule ");
                System.out.println("A. Input reward rule code ");
                input.nextLine();
                String rrRuleCode = input.nextLine();
                System.out.println("B. Reward Type");
                String rewardType =input.nextLine();
                System.out.println("C. Number of Points");
                int points =input.nextInt();
                try
                {
                    Class.forName("oracle.jdbc.OracleDriver");

                    CallableStatement cstmt = conn.prepareCall("{call updateRRRule(?, ? ,?,?)}");
                    cstmt.setString(1, rrRuleCode);
                    cstmt.setString(2, rewardType);
                    cstmt.setInt(3, points);
                    cstmt.setString(4, Home.Brand_id);

                    cstmt.executeUpdate();

                }

                catch(Exception e)
                {
                    System.out.println(e);
                }
                updateRRRules(conn);
                break;
            case 2:
                Brand_Landing(conn);
                break;
            default:
                break;
        }

    }



    static  void validateLoyaltyProgram(Connection conn) throws SQLException, ClassNotFoundException {
        System.out.println(" Choose " + "1. Validate\n" +
                "2. Go back\n");

        int option = input.nextInt();

        switch (option){
            case 1:
                // check for lp+id b+id in rr rule table and RE rule table
                ResultSet rs = null;
                ResultSet rs2 = null;

                PreparedStatement stmt = conn.prepareStatement("SELECT LP_ID FROM LOYALTY_PROGRAM  WHERE B_ID=?");
                stmt.setString(1, Home.Brand_id);
                rs = stmt.executeQuery();
                String lpid = null;
                while(rs.next()){
                    lpid = rs.getString("LP_ID");
                }
                //System.out.println(lpid);


                PreparedStatement stmt1 = conn.prepareStatement("select re.RE_RULE_CODE, rr.RR_RULE_CODE from RE_RULE re, RR_RULE rr " +
                        "where rr.B_ID=? and rr.LP_ID=? and re.B_ID=? and re.LP_ID=? ");
                stmt1.setString(1, Home.Brand_id);
                stmt1.setString(2, lpid);
                stmt1.setString(3, Home.Brand_id);
                stmt1.setString(4, lpid);
                rs2 = stmt1.executeQuery();
                String rr_rule=null, re_rule = null;
                while(rs2.next()){
                    rr_rule = rs2.getString("RE_RULE_CODE");
                    re_rule = rs2.getString("RR_RULE_CODE");
                }
                //System.out.println(rr_rule + "  "+ re_rule);

                if(rr_rule == null || re_rule ==null){
                    System.out.println("Loyalty program is Invalid!");
                    Brand_Landing(conn);
                }else{
                    //LOYALTY_PROGRAM
                    PreparedStatement  stmt3 = conn.prepareStatement("update loyalty_program set STATE='ACTIVE' where LP_ID = ?");
                    stmt3.setString(1, lpid);
                    stmt3.executeUpdate();

                    System.out.println("Loyalty Program successfully validated!");
                    Brand_Landing(conn);
                }

                break;
            // loyalty program table update state to vlaid
            case 2:
                Brand_Landing(conn);
                break;

            default:
                break;
        }

    }


    static  void Regular(Connection conn) throws SQLException, ClassNotFoundException {

        if(lp_tier==null)
        {
            try
            {
                Class.forName("oracle.jdbc.OracleDriver");



                PreparedStatement stmt = conn.prepareStatement("INSERT INTO TIER (B_ID,LP_ID, TIER_ID, TIER_NAME, MULTIPLIER) values (?,  ?,? ,?, ?)");
                stmt.setString(1,Home.Brand_id);
                stmt.setString(2,Home.LP_id );
                stmt.setInt(3,0);
                stmt.setString(4, "Regular");
                stmt.setInt(5, 1);
                stmt.executeUpdate();

            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }

        System.out.println("Choose \n 1. Activity Types\n" +
                "2. Reward Types\n" +
                "3. Go back\n");

        int option = input.nextInt();
        switch (option){

            case 1:
                lp_tier = 0;
                activityTypes(conn);
                break;

            case 2:
                lp_tier = 0;
                rewardTypes(conn);
                break;


            case 3:
                addLoyaltyProgram(conn);
                break;
            default:
                break;
        }


    }


    static  void Tier(Connection conn) throws SQLException, ClassNotFoundException {


        lp_tier = 1;
        System.out.println("Choose \n  1. Tiers Set up\n" +
                "2. Activity Types\n" +
                "3. Reward Types\n" +
                "4. Go back\n");

        int option = input.nextInt();
        switch (option){
            case 1:
                tiersSetup(conn);
                break;

            case 2:
                activityTypes(conn);
                break;
            case 3:
                rewardTypes(conn);
                break;

            case 4:
                addLoyaltyProgram(conn);
                break;
            default:
                break;
        }
    }


    static  void activityTypes(Connection conn) throws SQLException, ClassNotFoundException {

        System.out.println("Choose \n 1. Purchase\n" +
                "2. Leave a review\n" +
                "3. Refer a friend\n" +
                "4. Go back\n");

        int option = input.nextInt();
        switch (option){
            case 1:
                try {
                    //System.out.println("Inside purchase");
                    ResultSet rs = null;
                    ResultSet rs2 = null;

                    Class.forName("oracle.jdbc.OracleDriver");
                    String act="Purchase";
                    PreparedStatement stmt1 = conn.prepareStatement("SELECT A_CODE FROM ACTIVITY_TYPE WHERE A_NAME=?");
                    stmt1.setString(1, act);
                    rs = stmt1.executeQuery();
                    String a_code = null;

                    while (rs.next()) {
                        a_code = rs.getString("A_CODE");
                        //System.out.println("RS PURCHASE"+a_code);
                    }

                    //System.out.println( a_code + " " + Home.Brand_id);

                    PreparedStatement stmt2 = conn.prepareStatement("SELECT LP_ID FROM LOYALTY_PROGRAM  WHERE B_ID=?");
                    stmt2.setString(1,Home.Brand_id);
                    rs2 = stmt2.executeQuery();
                    String lp_id = null;
                    while (rs2.next()) {
                        lp_id = rs2.getString("LP_ID");
                    }

                    //System.out.println("lp id :" + lp_id);

                    PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO ACTIVITY (B_ID,LP_ID,A_CODE)" + " values ( ?,?,?)");
                    stmt3.setString(1, Home.Brand_id);
                    stmt3.setString(2, lp_id);
                    stmt3.setString(3, a_code);
                    stmt3.executeUpdate();
                    //System.out.println("second "  + a_code);

                    activityTypes(conn);
                }
                catch (java.sql.SQLException e)
                {

                    if(e.getMessage().contains("unique constraint")){
                        System.out.println("Activity type already exists");
                        activityTypes(conn);
                    }else{
                        System.out.println ("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());

                    }

                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                break;
            case 2:
                try {

                    ResultSet rs = null;
                    ResultSet rs2 = null;

                    Class.forName("oracle.jdbc.OracleDriver");

                    PreparedStatement stmt1 = conn.prepareStatement("SELECT A_CODE FROM ACTIVITY_TYPE WHERE A_NAME = ?");
                    stmt1.setString(1, "Write a review");
                    rs = stmt1.executeQuery();
                    String a_code = "";

                    while (rs.next()) {
                        a_code = rs.getString("A_CODE");
                    }
                    //System.out.println(  a_code + " " + Home.Brand_id);

                    PreparedStatement stmt2 = conn.prepareStatement("SELECT LP_ID FROM LOYALTY_PROGRAM  WHERE B_ID=? ");
                    stmt2.setString(1,Home.Brand_id);
                    rs2 = stmt2.executeQuery();
                    String lp_id = null;
                    while (rs2.next()) {
                        lp_id = rs2.getString("LP_ID");
                    }

                    PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO ACTIVITY (B_ID,LP_ID,A_CODE)" + " values ( ?,?,?)");
                    stmt3.setString(1, Home.Brand_id);
                    stmt3.setString(2, lp_id);
                    stmt3.setString(3, a_code);
                    stmt3.executeUpdate();
                    //System.out.println("second "  + a_code);

                    activityTypes(conn);
                }
                catch (java.sql.SQLException e)
                {

                    if(e.getMessage().contains("unique constraint")){
                        System.out.println("Activity type already exists");
                        activityTypes(conn);
                    }else{
                        System.out.println ("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());

                    }
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                break;
            case 3:
                try {
                    ResultSet rs = null;
                    ResultSet rs2 = null;

                    Class.forName("oracle.jdbc.OracleDriver");

                    PreparedStatement stmt1 = conn.prepareStatement("SELECT A_CODE FROM ACTIVITY_TYPE WHERE A_NAME = ?");
                    stmt1.setString(1, "Refer a friend");
                    rs = stmt1.executeQuery();
                    String a_code = null;

                    while (rs.next()) {
                        a_code = rs.getString("A_CODE");
                    }


                    PreparedStatement stmt2 = conn.prepareStatement("SELECT LP_ID FROM LOYALTY_PROGRAM  WHERE B_ID=? ");
                    stmt2.setString(1,Home.Brand_id);
                    rs2 = stmt2.executeQuery();
                    String lp_id = null;
                    while (rs2.next()) {
                        lp_id = rs2.getString("LP_ID");
                    }

                    PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO ACTIVITY (B_ID,LP_ID,A_CODE)" + " values ( ?,?,?)");
                    stmt3.setString(1, Home.Brand_id);
                    stmt3.setString(2, lp_id);
                    stmt3.setString(3, a_code);
                    stmt3.executeUpdate();

                    //System.out.println("second "  + a_code);

                    activityTypes(conn);
                }
                catch (java.sql.SQLException e)
                {

                    if(e.getMessage().contains("unique constraint")){
                        System.out.println("Activity type already exists");
                        activityTypes(conn);
                    }else{
                        System.out.println ("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());

                    }

                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                break;

            case 4:
                if(lp_tier ==0){
                    Regular(conn);
                }else{
                    Tier(conn);
                }
                break;
            default:
                break;
        }

    }


    static  void rewardTypes(Connection conn) throws SQLException, ClassNotFoundException {

        System.out.println("Choose \n 1. Gift Card\n" +
                "2. Free Product\n" +
                "3. Go back");

        int option = input.nextInt();
        switch (option){
            case 1:
                try {
                    ResultSet rs = null;
                    ResultSet rs2 = null;

                    Class.forName("oracle.jdbc.OracleDriver");
                    System.out.println("Input Gift card amount:");
                    int gift_amt=input.nextInt();
                    System.out.println("Input no of gift cards:");
                    int no_gift=input.nextInt();

                    PreparedStatement stmt1 = conn.prepareStatement("SELECT R_CODE FROM REWARD_TYPE WHERE R_NAME = ?");
                    stmt1.setString(1, "Gift Card");
                    rs = stmt1.executeQuery();
                    String r_code = null;

                    while (rs.next()) {
                        r_code = rs.getString("R_CODE");
                    }

                    //System.out.println(r_code);
                    PreparedStatement stmt2 = conn.prepareStatement("SELECT LP_ID FROM LOYALTY_PROGRAM  WHERE B_ID=? ");
                    stmt2.setString(1,Home.Brand_id);
                    rs2 = stmt2.executeQuery();
                    String lp_id = null;
                    while (rs2.next()) {
                        lp_id = rs2.getString("LP_ID");
                    }


                    // System.out.println(lp_id);
                    PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO REWARD (B_ID,LP_ID,GIFT_CARD_AMT,R_CODE,NO_OF_REWARDS)" + " values ( ?,?,?,?,?)");
                    stmt3.setString(1, Home.Brand_id);
                    stmt3.setString(2, lp_id);
                    stmt3.setInt(3, gift_amt);
                    stmt3.setString(4, r_code);
                    stmt3.setInt(5, no_gift);
                    stmt3.executeUpdate();

                    System.out.println("Added"+no_gift+" gift cards of amount "+gift_amt);


                    rewardTypes(conn);
                }
                catch (java.sql.SQLException e)
                {

                    if(e.getMessage().contains("unique constraint")){
                        System.out.println("Reward type already exists");
                        activityTypes(conn);
                    }else{
                        System.out.println ("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());

                    }

                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                break;

            case 2:
                try {
                    ResultSet rs = null;
                    ResultSet rs2 = null;

                    Class.forName("oracle.jdbc.OracleDriver");

                    System.out.println("Input free product name:");
                    input.nextLine();
                    String free_p_name=input.nextLine();
                    System.out.println("Input no of products:");
                    int no_free_p=input.nextInt();

                    PreparedStatement stmt1 = conn.prepareStatement("SELECT R_CODE FROM REWARD_TYPE WHERE R_NAME = ?");
                    stmt1.setString(1, "Free Product");
                    rs = stmt1.executeQuery();
                    String r_code = null;

                    while (rs.next()) {
                        r_code = rs.getString("R_CODE");
                    }


                    PreparedStatement stmt2 = conn.prepareStatement("SELECT LP_ID FROM LOYALTY_PROGRAM  WHERE B_ID=? ");
                    stmt2.setString(1,Home.Brand_id);
                    rs2 = stmt2.executeQuery();
                    String lp_id = null;
                    while (rs2.next()) {
                        lp_id = rs2.getString("LP_ID");
                    }

                    PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO REWARD (B_ID,LP_ID,FREE_PROD_NAME,R_CODE,NO_OF_REWARDS)" + " values ( ?,?,?,?,?)");
                    stmt3.setString(1, Home.Brand_id);
                    stmt3.setString(2, lp_id);
                    stmt3.setString(3, free_p_name);
                    stmt3.setString(4, r_code);
                    stmt3.setInt(5, no_free_p);
                    stmt3.executeUpdate();

                    System.out.println("Added"+no_free_p+"  "+free_p_name+" products");

                    rewardTypes(conn);
                }
                catch (java.sql.SQLException e)
                {

                    if(e.getMessage().contains("unique constraint")){
                        System.out.println("Reward type already exists");
                        activityTypes(conn);
                    }else{
                        System.out.println ("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());

                    }

                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                break;

            case 3:
                if(lp_tier ==0){
                    Regular(conn);
                }else{
                    Tier(conn);
                }
                break;
            default:
                break;
        }
    }

    // check if brand already enrolled in regular can't be enrolled in tiers

    static  void tiersSetup(Connection conn) throws SQLException, ClassNotFoundException {
        System.out.println("Choose \n 1. Set up\n" +
                "2. Go back");
        ResultSet rs2=null;

        PreparedStatement stmt2 = conn.prepareStatement("SELECT LP_ID FROM LOYALTY_PROGRAM  WHERE B_ID=? ");
        stmt2.setString(1,Home.Brand_id);
        rs2 = stmt2.executeQuery();
        String lp_id = null;
        while (rs2.next()) {
            lp_id = rs2.getString("LP_ID");
        }

        int option = input.nextInt();
        switch (option){
            case 1:
                System.out.println("Input Number of tiers (max 3)");
                int tiersCount = input.nextInt();
                if(tiersCount >3){
                    System.out.println("You can only choose upto 3 tiers, try again.");
                    tiersSetup(conn);
                }else{
                    System.out.println("Input details of all the tiers one by one");
                    int tierid=1;
                    while(tiersCount>0)
                    {
                        System.out.println("Input name of the tier( in increasing order of precedence):");
                        input.nextLine();
                        String tierName = input.nextLine();
                        System.out.println("Points required:");
                        int points = input.nextInt();
                        System.out.println("Multiplier of the tier:");
                        int multiplier = input.nextInt();
                        try
                        {
                            Class.forName("oracle.jdbc.OracleDriver");

                            PreparedStatement stmt = conn.prepareStatement("INSERT INTO TIER (B_ID, LP_ID, TIER_ID, TIER_NAME,POINTS_THRESHOLD, MULTIPLIER) values (?, ?, ? ,?,?, ?)");
                            stmt.setString(1,Home.Brand_id);
                            stmt.setString(2, lp_id);
                            stmt.setInt(3,tierid++);
                            stmt.setString(4, tierName);
                            stmt.setInt(5, points);
                            stmt.setInt(6, multiplier);
                            stmt.executeUpdate();
                        }
                        catch (java.sql.SQLException e)
                        {

                            if(e.getMessage().contains("unique constraint")){
                                System.out.println("Tier already exists");
                                Tier(conn);
                            }else{
                                System.out.println ("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());

                            }

                        }
                        catch(Exception e)
                        {
                            System.out.println(e);
                        }

                        tiersCount--;
                    }
                }

                Tier(conn);
                break;
            case 2:
                Tier(conn);
                break;
            default:
                break;
        }


    }
}

