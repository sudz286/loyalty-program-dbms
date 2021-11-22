-- Admin Table
insert into ADMIN
values('Admin1', 'John Crater',9812342356);
insert into ADMIN
values('Admin2', 'Mila Brown',9912842356);
insert into ADMIN
values('Admin3', 'Samy Turner',5102342356);

-- Brand Table
INSERT INTO BRAND(B_ID,B_NAME,JOIN_DATE,B_ADDRESS,ADMIN_ID) 
VALUES ('Brand01', 'Brand X', TO_DATE('04-01-2021', 'MM-DD-YYYY'), '503 Rolling Creek Dr Austin, AR', 'Admin1');
INSERT INTO BRAND(B_ID,B_NAME,JOIN_DATE,B_ADDRESS,ADMIN_ID) 
VALUES ('Brand02', 'Brand Y', TO_DATE('03-25-2021', 'MM-DD-YYYY'), '939 Orange Ave Coronado, CA', 'Admin2');
INSERT INTO BRAND(B_ID,B_NAME,JOIN_DATE,B_ADDRESS,ADMIN_ID) 
VALUES ('Brand03', 'Brand Z', TO_DATE('05-08-2021', 'MM-DD-YYYY'), '20 Roszel Rd, Princeton, NJ', 'Admin3');

-- Loyalty Program Table
INSERT INTO LOYALTY_PROGRAM VALUES('Brand01', 'TLP01','SportGoods', 'ACTIVE');
INSERT INTO LOYALTY_PROGRAM VALUES('Brand02', 'TLP02','MegaCenter', 'ACTIVE');
INSERT INTO LOYALTY_PROGRAM VALUES('Brand03', 'RLP01','TechSups', 'ACTIVE');

-- Tier for Brand01
INSERT INTO TIER VALUES('Brand01','TLP01','1','Bronze',0,1);
INSERT INTO TIER VALUES('Brand01','TLP01','2','Silver',170,2);
INSERT INTO TIER VALUES('Brand01','TLP01','3','Gold',270,3);

-- Tier for Brand02
INSERT INTO TIER VALUES('Brand02','TLP02','1','Special',0,1);
INSERT INTO TIER VALUES('Brand02','TLP02','2','Premium',210,2);

-- Tier for Brand03
INSERT INTO TIER VALUES('Brand03','RLP01','0','Regular',0,1);

-- Activity Types table
INSERT INTO ACTIVITY_TYPE VALUES('A01','Purchase','Admin1');
INSERT INTO ACTIVITY_TYPE VALUES('A02','Write a review','Admin2');
INSERT INTO ACTIVITY_TYPE VALUES('A03','Refer a friend','Admin3');

-- Activities for Brand01
INSERT INTO ACTIVITY VALUES('Brand01', 'TLP01', 'A01');
INSERT INTO ACTIVITY VALUES('Brand01', 'TLP01', 'A02');

-- Activities for Brand02
INSERT INTO ACTIVITY VALUES('Brand02', 'TLP02', 'A01');
INSERT INTO ACTIVITY VALUES('Brand02', 'TLP02', 'A03');

-- Activities for Brand03
INSERT INTO ACTIVITY VALUES('Brand03', 'RLP01', 'A03');


-- RE Rules for Brand01
INSERT INTO RE_RULE VALUES('Brand01', 'TLP01', '1', 1,15,'A01');
INSERT INTO RE_RULE VALUES('Brand01', 'TLP01', '2', 1,10,'A02');

-- RE Rules for Brand02
INSERT INTO RE_RULE VALUES('Brand02', 'TLP02', '1', 1,40,'A01');
INSERT INTO RE_RULE VALUES('Brand02', 'TLP02', '2', 1,30,'A03');

-- RE Rules for Brand03
INSERT INTO RE_RULE VALUES('Brand03', 'RLP01', '1', 1,10,'A03');


-- Reward Types table
INSERT INTO REWARD_TYPE VALUES('R01', 'Gift Card', 'Admin1');
INSERT INTO REWARD_TYPE VALUES('R02', 'Free Product', 'Admin2');

-- Rewards for Brand01
INSERT INTO REWARD VALUES('Brand01', 'TLP01','R1', 50, null, null, 'R01', 40);
INSERT INTO REWARD VALUES('Brand01', 'TLP01','R2',null, 'FP1', null, 'R02',  25);

-- Rewards for Brand02
INSERT INTO REWARD VALUES('Brand02', 'TLP02','R3',  100, null, null , 'R01',  30);
INSERT INTO REWARD VALUES('Brand02', 'TLP02','R4', null, 'FP2', null, 'R02', 50);

-- Rewards for Brand03
INSERT INTO REWARD VALUES('Brand03', 'RLP01','R5', 75, null, null , 'R01', 25);


-- RR Rules for Brand01
INSERT INTO RR_RULE VALUES('Brand01','R1','TLP01','1', 1,80);
INSERT INTO RR_RULE VALUES('Brand01','R2','TLP01','2', 1,70);

-- RR Rules for Brand02
INSERT INTO RR_RULE VALUES('Brand02','R3','TLP02','1', 1,120);
INSERT INTO RR_RULE VALUES('Brand02','R4','TLP02','2', 1,90);

-- RR Rules for Brand03
INSERT INTO RR_RULE VALUES('Brand03','R5','RLP01','1', 1,100);




-- Customer table
INSERT INTO CUSTOMER VALUES('C0001', 'Peter Parker', '20 Ingram Street, NY', 8636368778, 'Admin1', 'W0001');
INSERT INTO CUSTOMER VALUES('C0002', 'Steve Rogers', '569 Leaman Place, NY', 8972468552, 'Admin2', 'W0002');
INSERT INTO CUSTOMER VALUES('C0003', 'Diana Prince', '1700 Broadway St, NY', 8547963210, 'Admin1', 'W0003');
INSERT INTO CUSTOMER VALUES('C0004', 'Billy Batson', '5015 Broad St, Philadelphia, PA', 8974562583, 'Admin3', 'W0004');
INSERT INTO CUSTOMER VALUES('C0005', 'Tony Stark', '10880 Malibu Point, CA', 8731596464, 'Admin2', 'W0005');

-- Wallet for Customer 1
INSERT INTO WALLET VALUES('W0001', 'C0001', 'Brand01', 'TLP01', 1, 80, 80);
INSERT INTO WALLET VALUES('W0001', 'C0001', 'Brand02', 'TLP02', 2, 210, 210);

-- Wallet for Customer 2
INSERT INTO WALLET VALUES('W0002', 'C0002', 'Brand01', 'TLP01', 1, 70, 70);

-- Wallet for Customer 3
INSERT INTO WALLET VALUES('W0003', 'C0003', 'Brand02', 'TLP02', 2, 220, 180);
INSERT INTO WALLET VALUES('W0003', 'C0003', 'Brand03', 'RLP01', 0, 40, 0);

-- INSERT INTO WALLET VALUES('w0004', 'C0004', 'Brand01', 'TLP01', 1, 70, 70);

-- Wallet for Customer 5
INSERT INTO WALLET VALUES('W0005', 'C0005', 'Brand01', 'TLP01', 2, 170, 150);
INSERT INTO WALLET VALUES('W0005', 'C0005', 'Brand02', 'TLP02', 1, 160, 120);
INSERT INTO WALLET VALUES('W0005', 'C0005', 'Brand03', 'RLP01', 0, 50, 0);


-- Customer Activities Table
-- Activities for Customer 1
-- RE Based Activities

INSERT INTO CUSTOMER_ACTIVITIES VALUES('1', 'W0001', 'Brand01', 'TLP01','1', 1, null, null, null, null, null, null, TO_DATE('06-10-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('2', 'W0001', 'Brand01', 'TLP01','1', 1, null, null, null, null, null, null, TO_DATE('06-10-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('3', 'W0001', 'Brand01', 'TLP01','1', 1, null, null, null, null, null, null, TO_DATE('06-10-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('4', 'W0001', 'Brand01', 'TLP01','1', 1, null, null, null, null, null, null, TO_DATE('06-10-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('5', 'W0001', 'Brand01', 'TLP01','2', 1, null, null, null, null, null, null, TO_DATE('06-18-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('6', 'W0001', 'Brand01', 'TLP01','2', 1, null, null, null, null, null, null, TO_DATE('06-18-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('7', 'W0001', 'Brand02', 'TLP02','1', 1, null, null, null, null, null, null, TO_DATE('08-09-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('8', 'W0001', 'Brand02', 'TLP02','1', 1, null, null, null, null, null, null, TO_DATE('08-09-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('9', 'W0001', 'Brand02', 'TLP02','1', 1, null, null, null, null, null, null, TO_DATE('08-15-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('10', 'W0001', 'Brand02', 'TLP02','2', 1, null, null, null, null, null, null, TO_DATE('10-03-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('11', 'W0001', 'Brand02', 'TLP02','2', 1, null, null, null, null, null, null, TO_DATE('10-18-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('12', 'W0001', 'Brand02', 'TLP02','2', 1, null, null, null, null, null, null, TO_DATE('10-18-2021', 'MM-DD-YYYY'), null);

-- RR Based Activities

INSERT INTO CUSTOMER_ACTIVITIES VALUES('13', 'W0001', 'Brand01', 'TLP01',null, null, '1', 1, null, 50, 0, null, TO_DATE('07-02-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('14', 'W0001', 'Brand02', 'TLP02',null, null, '1', 1, null, 100, 0, null, TO_DATE('08-25-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('15', 'W0001', 'Brand02', 'TLP02',null, null, '2', 1, null, null, null, null, TO_DATE('10-20-2021', 'MM-DD-YYYY'), null);


-- Activities for Customer 2
-- RE Based Activities

INSERT INTO CUSTOMER_ACTIVITIES VALUES('16', 'W0002', 'Brand01', 'TLP01','1', 1, null, null, null, null, null, null, TO_DATE('07-02-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('17', 'W0002', 'Brand01', 'TLP01','1', 1, null, null, null, null, null, null, TO_DATE('07-02-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('18', 'W0002', 'Brand01', 'TLP01','1', 1, null, null, null, null, null, null, TO_DATE('07-08-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('19', 'W0002', 'Brand01', 'TLP01','1', 1, null, null, null, null, null, null, TO_DATE('07-08-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('20', 'W0002', 'Brand01', 'TLP01','2', 1, null, null, null, null, null, null, TO_DATE('08-05-2021', 'MM-DD-YYYY'), null);

-- RR Based Activities
INSERT INTO CUSTOMER_ACTIVITIES VALUES('21', 'W0002', 'Brand01', 'TLP01',null, null, '2', 1, null, null, null, null, TO_DATE('09-01-2021', 'MM-DD-YYYY'), null);

-- Activities for Customer 3
-- RE Based Activities

INSERT INTO CUSTOMER_ACTIVITIES VALUES('22', 'W0003', 'Brand03', 'RLP01','1', 1, null, null, null, null, null, null, TO_DATE('07-30-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('23', 'W0003', 'Brand03', 'RLP01','1', 1, null, null, null, null, null, null, TO_DATE('07-30-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('24', 'W0003', 'Brand03', 'RLP01','1', 1, null, null, null, null, null, null, TO_DATE('07-30-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('25', 'W0003', 'Brand03', 'RLP01','1', 1, null, null, null, null, null, null, TO_DATE('07-30-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('26', 'W0003', 'Brand02', 'TLP02','1', 1, null, null, null, null, null, null, TO_DATE('08-01-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('27', 'W0003', 'Brand02', 'TLP02','1', 1, null, null, null, null, null, null, TO_DATE('08-10-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('28', 'W0003', 'Brand02', 'TLP02','1', 1, null, null, null, null, null, null, TO_DATE('08-10-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('29', 'W0003', 'Brand02', 'TLP02','1', 1, null, null, null, null, null, null, TO_DATE('09-02-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('30', 'W0003', 'Brand02', 'TLP02','2', 1, null, null, null, null, null, null, TO_DATE('10-01-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('31', 'W0003', 'Brand02', 'TLP02','2', 1, null, null, null, null, null, null, TO_DATE('10-16-2021', 'MM-DD-YYYY'), null);

-- RR Based Activities
INSERT INTO CUSTOMER_ACTIVITIES VALUES('32', 'W0003', 'Brand02', 'TLP02',null, null, '2', 1, null, null, null, null, TO_DATE('08-26-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('33', 'W0003', 'Brand02', 'TLP02',null, null, '2', 1, null, null, null, null, TO_DATE('10-18-2021', 'MM-DD-YYYY'), null);

-- Activities for Customer 5
-- RE Based Activities

INSERT INTO CUSTOMER_ACTIVITIES VALUES('34', 'W0005', 'Brand01', 'TLP01','1', 1, null, null, null, null, null, null, TO_DATE('08-10-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('35', 'W0005', 'Brand01', 'TLP01','1', 1, null, null, null, null, null, null, TO_DATE('08-10-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('36', 'W0005', 'Brand01', 'TLP01','1', 1, null, null, null, null, null, null, TO_DATE('08-10-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('37', 'W0005', 'Brand01', 'TLP01','1', 1, null, null, null, null, null, null, TO_DATE('08-10-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('38', 'W0005', 'Brand01', 'TLP01','1', 1, null, null, null, null, null, null, TO_DATE('08-10-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('39', 'W0005', 'Brand01', 'TLP01','1', 1, null, null, null, null, null, null, TO_DATE('08-10-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('40', 'W0005', 'Brand03', 'RLP01','1', 1, null, null, null, null, null, null, TO_DATE('09-16-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('41', 'W0005', 'Brand03', 'RLP01','1', 1, null, null, null, null, null, null, TO_DATE('09-16-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('42', 'W0005', 'Brand03', 'RLP01','1', 1, null, null, null, null, null, null, TO_DATE('09-16-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('43', 'W0005', 'Brand03', 'RLP01','1', 1, null, null, null, null, null, null, TO_DATE('09-30-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('44', 'W0005', 'Brand03', 'RLP01','1', 1, null, null, null, null, null, null, TO_DATE('09-30-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('45', 'W0005', 'Brand01', 'TLP01','2', 1, null, null, null, null, null, null, TO_DATE('09-30-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('46', 'W0005', 'Brand01', 'TLP01','2', 1, null, null, null, null, null, null, TO_DATE('09-30-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('47', 'W0005', 'Brand01', 'TLP01','2', 1, null, null, null, null, null, null, TO_DATE('09-30-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('48', 'W0005', 'Brand01', 'TLP01','2', 1, null, null, null, null, null, null, TO_DATE('09-30-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('49', 'W0005', 'Brand01', 'TLP01','2', 1, null, null, null, null, null, null, TO_DATE('09-30-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('50', 'W0005', 'Brand02', 'TLP02','1', 1, null, null, null, null, null, null, TO_DATE('10-10-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('51', 'W0005', 'Brand02', 'TLP02','1', 1, null, null, null, null, null, null, TO_DATE('10-10-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('52', 'W0005', 'Brand02', 'TLP02','1', 1, null, null, null, null, null, null, TO_DATE('10-10-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('53', 'W0005', 'Brand02', 'TLP02','1', 1, null, null, null, null, null, null, TO_DATE('10-10-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('54', 'W0005', 'Brand01', 'TLP01','2', 1, null, null, null, null, null, null, TO_DATE('10-15-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('55', 'W0005', 'Brand01', 'TLP01','2', 1, null, null, null, null, null, null, TO_DATE('10-15-2021', 'MM-DD-YYYY'), null);
INSERT INTO CUSTOMER_ACTIVITIES VALUES('56', 'W0005', 'Brand01', 'TLP01','2', 1, null, null, null, null, null, null, TO_DATE('10-15-2021', 'MM-DD-YYYY'), null);

-- RR Based Activities
INSERT INTO CUSTOMER_ACTIVITIES VALUES('57', 'W0005', 'Brand02', 'TLP02',null, null, '1', 1, null, 100, 0, null, TO_DATE('10-11-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('58', 'W0005', 'Brand01', 'TLP01',null, null, '1', 1, null, 50, 0, null, TO_DATE('10-11-2021', 'MM-DD-YYYY'), null);

INSERT INTO CUSTOMER_ACTIVITIES VALUES('59', 'W0005', 'Brand01', 'TLP01',null, null, '2', 1, null, null,null, null, TO_DATE('10-17-2021', 'MM-DD-YYYY'), null);

-- Credentials Table
insert into CREDENTIALS
values('johncrater', 'admin',null,null,null,'Admin1');
insert into CREDENTIALS
values('milabrown', 'admin', null, null, null ,'Admin2');
insert into CREDENTIALS
values('samyturner', 'admin', null, null, null ,'Admin3');
insert into CREDENTIALS
values('brandx', 'x', null, null, 'Brand01', null);
insert into CREDENTIALS
values('brandy', 'y', null, null, 'Brand02', null);
insert into CREDENTIALS
values('brandz', 'z', null, null, 'Brand03', null);
insert into CREDENTIALS
values('peterparker', 'pp', 'C0001', 'W0001', null, null);
insert into CREDENTIALS
values('steverodgers', 'sr', 'C0002', 'W0002', null, null);
insert into CREDENTIALS
values('dianaprince', 'dp', 'C0003', 'W0003', null, null);
insert into CREDENTIALS
values('billybatson', 'bb', 'C0004', 'W0004', null, null);
insert into CREDENTIALS
values('tonystark', 'ts', 'C0005', 'W0005', null, null);
