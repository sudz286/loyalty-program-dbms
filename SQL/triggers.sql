-- Brand ID seq
CREATE SEQUENCE brand_id_seq START WITH 4
INCREMENT BY 1;
/
CREATE OR REPLACE TRIGGER brand_insert 
BEFORE INSERT ON BRAND 
FOR EACH ROW

BEGIN
  SELECT concat('Brand0',brand_id_seq.NEXTVAL)
  INTO   :new.B_ID
  FROM   dual;
END; 
/

-- Customer ID seq
CREATE SEQUENCE customer_id_seq START WITH 5
INCREMENT BY 1;
/
CREATE OR REPLACE TRIGGER customer_insert 
BEFORE INSERT ON CUSTOMER 
FOR EACH ROW

BEGIN
  SELECT concat('C000',customer_id_seq.NEXTVAL)
  INTO   :new.CUST_ID
  FROM   dual;
END; 
/

-- wallet ID seq
CREATE SEQUENCE wallet_id_seq START WITH 1
INCREMENT BY 1;
/
CREATE OR REPLACE TRIGGER wallet_insert 
BEFORE INSERT ON CUSTOMER 
FOR EACH ROW

BEGIN
  SELECT concat('W000',wallet_id_seq.NEXTVAL)
  INTO   :new.WALLET_ID
  FROM   dual;
END; 
/
-- CUSTOMER_ACTIVITIES ID seq
CREATE SEQUENCE ca_id_seq START WITH 60
INCREMENT BY 1;
CREATE OR REPLACE TRIGGER ca_insert 
BEFORE INSERT ON CUSTOMER_ACTIVITIES 
FOR EACH ROW
BEGIN
  SELECT ca_id_seq.nextval
  INTO   :new.CA_ID
  FROM   dual;
END; 
/
-- REWARD ID seq
CREATE SEQUENCE reward_id_seq START WITH 6
INCREMENT BY 1;
/
CREATE OR REPLACE TRIGGER reward_insert 
BEFORE INSERT ON REWARD 
FOR EACH ROW

BEGIN
  SELECT concat('R',reward_id_seq.NEXTVAL)
  INTO   :new.REWARD_ID
  FROM   dual;
END; 
/

CREATE PROCEDURE enrollment(bid VARCHAR, custid VARCHAR, walletid VARCHAR) AS
BEGIN
 INSERT INTO WALLET 
 SELECT
    walletid as WALLET_ID  , 
    custid as CUST_ID , 
    B_ID  , 
    LP_ID ,  
  (SELECT min(tier_id)from tier where b_id=bid) AS CUST_TIER_STATUS,
  0 as  E_POINTS, 
   0 as R_POINTS 
  FROM LOYALTY_PROGRAM where b_id=bid;
END; 
/

-- LP ID seq
CREATE SEQUENCE lp_regular_id_seq START WITH 2
INCREMENT BY 1;
/
CREATE SEQUENCE lp_tier_id_seq START WITH 3
INCREMENT BY 1;
/
CREATE PROCEDURE addLoyaltyProgram(bid VARCHAR, LPName VARCHAR, tier VARCHAR) AS
BEGIN
    INSERT INTO LOYALTY_PROGRAM
  SELECT B_ID,
  case when tier='Regular' then concat('RLP0',lp_regular_id_seq.NEXTVAL) else concat('TLP0',lp_tier_id_seq.NEXTVAL) end as LP_ID,
  LPName as LP_Name,
  'Inactive' as STATE
  FROM BRAND where B_ID=bid;
END; 
/

CREATE PROCEDURE updateRERule(reRuleCode VARCHAR, activityType VARCHAR, pts NUMBER, bid VARCHAR) AS
BEGIN
INSERT INTO RE_RULE
SELECT B_ID, 
     LP_ID, 
    RE_RULE_CODE, 
    RE_VERSION+1, 
    pts as POINTS, 
    A_CODE
    FROM RE_RULE 
    WHERE B_ID=bid and A_CODE=(select A_CODE from ACTIVITY_TYPE where A_NAME=activityType) and RE_RULE_CODE=reRuleCode and
    RE_VERSION=(select max(RE_VERSION)from RE_RULE where B_ID=bid and A_CODE=(select A_CODE from ACTIVITY_TYPE where A_NAME=activityType) and RE_RULE_CODE=reRuleCode group by B_ID,A_CODE);
END;
/

CREATE PROCEDURE updateRRRule(rrRuleCode VARCHAR, rewardType VARCHAR, pts NUMBER, bid VARCHAR) AS
BEGIN
INSERT INTO RR_RULE
SELECT B_ID,
    REWARD_ID,
     LP_ID, 
    RR_RULE_CODE, 
    RR_VERSION+1, 
    pts as POINTS 
    FROM RR_RULE 
    WHERE B_ID=bid and RR_RULE_CODE=rrRuleCode and
    RR_VERSION=(select max(RR_VERSION)from RR_RULE where B_ID=bid and RR_RULE_CODE=rrRuleCode group by B_ID,RR_RULE_CODE);
    
    
END;
/

CREATE PROCEDURE addRERule(reRuleCode VARCHAR, a_code VARCHAR, points NUMBER, bid VARCHAR) AS
BEGIN
INSERT INTO RE_RULE
SELECT B_ID, 
     LP_ID, 
    reRuleCode as RE_RULE_CODE, 
    1 as RE_VERSION, 
    points as POINTS, 
    a_code as A_CODE
    FROM LOYALTY_PROGRAM 
    WHERE B_ID=bid;
END;
/


CREATE PROCEDURE addRRRule(rrRuleCode VARCHAR,rewardid VARCHAR, pts NUMBER, bid VARCHAR) AS
BEGIN
INSERT INTO RR_RULE
SELECT B_ID, 
    REWARD_ID,
    LP_ID, 
    rrRuleCode as RR_RULE_CODE, 
    1 as RR_VERSION, 
    pts as POINTS 
    FROM REWARD 
    WHERE B_ID=bid and REWARD_ID=rewardid;
END;
/


