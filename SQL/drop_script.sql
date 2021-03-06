
DROP TABLE CUSTOMER_ACTIVITIES;
DROP TABLE CREDENTIALS;
DROP TABLE WALLET;
DROP TABLE TIER;

DROP TABLE RE_RULE;
DROP TABLE RR_RULE;
DROP TABLE REWARD;
DROP TABLE ACTIVITY;
DROP TABLE LOYALTY_PROGRAM;
DROP TABLE CUSTOMER;
DROP TABLE BRAND;
DROP TABLE ACTIVITY_TYPE;
DROP TABLE REWARD_TYPE;
DROP TABLE ADMIN;

DROP SEQUENCE reward_id_seq;
DROP TRIGGER reward_insert;

DROP SEQUENCE ca_id_seq;
DROP TRIGGER ca_insert;

DROP SEQUENCE activity_id_seq;
DROP TRIGGER activity_insert;

DROP SEQUENCE lp_regular_id_seq;
DROP SEQUENCE lp_tier_id_seq;

DROP SEQUENCE customer_id_seq;
DROP TRIGGER customer_insert;
DROP SEQUENCE brand_id_seq;
DROP TRIGGER brand_insert;

DROP SEQUENCE wallet_id_seq;
DROP TRIGGER wallet_insert;


DROP PROCEDURE addLoyaltyProgram;
DROP PROCEDURE updateRERule;
DROP PROCEDURE updateRRRule;
DROP PROCEDURE addRERule;
DROP PROCEDURE addRRRule;
DROP PROCEDURE enrollment;
