create table ADMIN ( 
    ADMIN_ID varchar(15) primary key,
    ADMIN_NAME varchar(20),
    ADMIN_NUMBER number(10)
);

create table ACTIVITY_TYPE ( 
    A_CODE varchar(20) primary key,  
    A_NAME varchar(50) not null, 
    ADMIN_ID varchar(15), 
    constraint fk_admin_id_act foreign key (ADMIN_ID) references ADMIN(ADMIN_ID) ON DELETE SET NULL
);

create table REWARD_TYPE ( 
    R_CODE varchar(20) primary key, 
    R_NAME varchar(50) not null, 
    ADMIN_ID varchar(15), 
    constraint fk_admin_id_rwd foreign key (ADMIN_ID) references ADMIN(ADMIN_ID) ON DELETE SET NULL
);

create table BRAND ( 
    B_ID varchar(15) primary key, 
    B_NAME varchar(100) unique, 
    JOIN_DATE date, 
    B_ADDRESS varchar(200), 
    ADMIN_ID varchar(15), 
    constraint fk_admin_id_brand foreign key (ADMIN_ID) references ADMIN(ADMIN_ID) ON DELETE SET NULL
);

create table LOYALTY_PROGRAM ( 
    B_ID varchar(15), 
    LP_ID varchar(15), 
    LP_name varchar(100),
    STATE char(10), 
    constraint fk_b_id_lp foreign key (B_ID) references BRAND(B_ID) ON DELETE CASCADE, 
    constraint pk_lp primary key (B_ID, LP_ID) 
);

create table TIER (           
    B_ID varchar(15), 
    LP_ID varchar(15), 
    TIER_ID number(1), 
    TIER_NAME varchar(50) not null,
    POINTS_THRESHOLD number(5) default '0',
    MULTIPLIER number(5) default '1', 
    constraint fk_lp_tier foreign key (B_ID,LP_ID) references LOYALTY_PROGRAM(B_ID,LP_ID) ON DELETE CASCADE, 
    constraint pk_tier primary key (B_ID, LP_ID, TIER_ID),
    constraint check_tier check(TIER_ID<4) -- NEED TO ENFORCE CONSTRAINT (TIER_ID<4) 
);

create table ACTIVITY ( 
    B_ID varchar(15),
     LP_ID varchar(15),
    --ACTIVITY_ID varchar(10), 
    A_CODE varchar(20) not null, 
      constraint fk_activity foreign key (B_ID,LP_ID) references LOYALTY_PROGRAM(B_ID,LP_ID) ON DELETE CASCADE, 
    constraint fk_a_code_act foreign key (A_CODE) references ACTIVITY_TYPE(A_CODE) ON DELETE CASCADE, 
    constraint pk_act primary key (B_ID,LP_ID, A_CODE) 
);

create table RE_RULE ( 
    B_ID varchar(15), 
    LP_ID varchar(15), 
    RE_RULE_CODE varchar(10), --not required
    RE_VERSION number(5) default '1', 
    POINTS number(5), 
    A_CODE varchar(20) not null,  
    constraint fk_a_code_re foreign key (B_ID,LP_ID,A_CODE) references ACTIVITY(B_ID,LP_ID,A_CODE) ON DELETE CASCADE, 
    constraint pk_re primary key (B_ID, LP_ID, RE_RULE_CODE, RE_VERSION)
);
create table REWARD ( 
    B_ID varchar(15), 
    LP_ID varchar(15),
    REWARD_ID varchar(10), 
    GIFT_CARD_AMT number(5), 
    FREE_PROD_NAME varchar(50), 
    GIFT_CARD_EXPIRY_DATE date, 
    R_CODE varchar(20) not null, 
	NO_OF_REWARDS number(5),
    constraint fk_rwd foreign key (B_ID,LP_ID) references LOYALTY_PROGRAM(B_ID,LP_ID) ON DELETE CASCADE, 
    constraint fk_r_code_rwd foreign key (R_CODE) references REWARD_TYPE(R_CODE) ON DELETE CASCADE, 
    --constraint fk_rr_rule foreign key (B_ID,LP_ID,RR_RULE_CODE,RR_VERSION) references RR_RULE(B_ID,LP_ID,RR_RULE_CODE,RR_VERSION) ON DELETE CASCADE, 
    constraint pk_rwd primary key (B_ID,LP_ID, REWARD_ID) 
);
create table RR_RULE ( 
    B_ID varchar(15),
    REWARD_ID varchar(10),
    LP_ID varchar(15), 
    RR_RULE_CODE varchar(10), 
    RR_VERSION number(5) not null, 
    POINTS number(5), 
    --constraint fk_lp_rr foreign key (B_ID,LP_ID) references LOYALTY_PROGRAM(B_ID,LP_ID) ON DELETE CASCADE,
    constraint fk_lp_rr foreign key (B_ID,LP_ID,REWARD_ID) references REWARD(B_ID,LP_ID,REWARD_ID) ON DELETE CASCADE,
    constraint pk_rr primary key (B_ID, LP_ID, RR_RULE_CODE, RR_VERSION) 
);



create table CUSTOMER ( 
    CUST_ID varchar(15) ,  
    CUST_NAME varchar(100) not null, 
    C_ADDRESS varchar(200), 
    C_PH_NO number(10) not null, 
    ADMIN_ID varchar(15),
    WALLET_ID varchar(10),
    constraint fk_admin_id_cust foreign key (ADMIN_ID) references ADMIN(ADMIN_ID) ON DELETE SET NULL,
    constraint pk_cust primary key (WALLET_ID,CUST_ID)
    
);

create table WALLET ( 
    WALLET_ID varchar(10) , 
    CUST_ID varchar(15) not null, 
    B_ID varchar(15) , 
    LP_ID varchar(15) , 
    CUST_TIER_STATUS number(1), 
    E_POINTS number(10) default '0', 
    R_POINTS number(10) default '0', 
    constraint fk_cust_id_wallet foreign key (WALLET_ID,CUST_ID) references CUSTOMER(WALLET_ID,CUST_ID) ON DELETE CASCADE, 
   -- constraint fk_cust_wallet foreign key (B_ID,LP_ID) references LOYALTY_PROGRAM(B_ID,LP_ID), 
    constraint fk_tier_wallet foreign key (B_ID,LP_ID,CUST_TIER_STATUS) references TIER(B_ID,LP_ID,TIER_ID) ON DELETE CASCADE, 
    constraint pk_wallet primary key (WALLET_ID, LP_ID,B_ID) 
);


create table CUSTOMER_ACTIVITIES ( 
    CA_ID varchar(20) primary key,  
    WALLET_ID varchar(10) not null,  
    B_ID varchar(15) not null, 
    LP_ID varchar(15) not null, 
    RE_RULE_CODE varchar(10), 
    RE_VERSION number(5), 
    RR_RULE_CODE varchar(10), 
    RR_VERSION number(5), 
    REVIEW varchar(500), 
    GIFT_AMT number(5), 
    GIFT_CARD_USED number(1), -- 0 if not used. 1 if used 
    PROD_NAME varchar(50), 
    ACTIVITY_DATE date not null, 
    PURCHASE_AMT number(5), 
    constraint fk_wallet_ca foreign key (WALLET_ID,LP_ID,B_ID) references WALLET(WALLET_ID,LP_ID,B_ID) ON DELETE CASCADE, 
    constraint fk_rr_ca foreign key (B_ID, LP_ID, RR_RULE_CODE, RR_VERSION) references RR_RULE(B_ID, LP_ID, RR_RULE_CODE, RR_VERSION) ON DELETE CASCADE, 
    constraint fk_re_ca foreign key (B_ID, LP_ID, RE_RULE_CODE, RE_VERSION) references RE_RULE(B_ID, LP_ID, RE_RULE_CODE, RE_VERSION) ON DELETE CASCADE
-- foreign key Cust_id : Is this needed if we have wallet_id?
);

create table CREDENTIALS(
USERNAME varchar(50),
U_PASSWORD varchar(15) default 'abcd1234', --check constraint
CUST_ID varchar(15),
WALLET_ID varchar(15),
B_ID varchar(15),
ADMIN_ID varchar(15),
constraint fk_cust_id_cred foreign key (CUST_ID, WALLET_ID) references CUSTOMER(CUST_ID, WALLET_ID) ON DELETE CASCADE,
constraint fk_admin_id_cred foreign key (ADMIN_ID) references ADMIN(ADMIN_ID) ON DELETE CASCADE,
constraint fk_brand_id_cred foreign key (B_ID) references BRAND(B_ID) ON DELETE CASCADE,
constraint pk_cred primary key (USERNAME,U_PASSWORD) 
);

