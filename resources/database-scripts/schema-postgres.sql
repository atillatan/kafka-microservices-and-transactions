DROP TABLE IF EXISTS trade;
CREATE TABLE Trade(id serial PRIMARY KEY, tradeid VARCHAR(255), version integer, counterparty VARCHAR(255), bookid VARCHAR(255),maturitydate DATE, createddate DATE, expiredflag VARCHAR(255));





 
 