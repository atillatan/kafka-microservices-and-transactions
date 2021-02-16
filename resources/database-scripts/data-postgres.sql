INSERT INTO Trade(tradeid, version, counterparty, bookid, maturitydate, createddate, expiredflag) VALUES('T1', 1, 'CP-1', 'B1','2020-05-20 00:00:00',NOW(),'N');
INSERT INTO Trade(tradeid, version, counterparty, bookid, maturitydate, createddate, expiredflag) VALUES('T2', 2, 'CP-2', 'B1','2020-05-20 00:00:00',NOW(),'N');
INSERT INTO Trade(tradeid, version, counterparty, bookid, maturitydate, createddate, expiredflag) VALUES('T2', 1, 'CP-1', 'B2','2020-05-20 00:00:00',NOW(),'N');
INSERT INTO Trade(tradeid, version, counterparty, bookid, maturitydate, createddate, expiredflag) VALUES('T3', 3, 'CP-3', 'B2','2020-05-20 00:00:00',NOW(),'N');

-- select * from trade