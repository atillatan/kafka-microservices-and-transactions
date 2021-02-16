
# Problem Statement

There is a scenario where thousands of trades are flowing into one store, assume any way of transmission of trades (could be receiving the trades via Kafka or MQ or…).

We need to create a one trade store, which stores the trade (any kind of database) in the following order:


| Trade Id | Version | Counter-Party Id | Book-Id | Maturity Date | Created Date | Expired |
| -------- | ------- | ---------------- | ------- | ------------- | ------------ | ------- |
| T1       | 1       | CP-1             | B1      | 20/05/2020    | today date   | N       |
| T2       | 2       | CP-2             | B1      | 20/05/2021    | today date   | N       |
| T2       | 1       | CP-1             | B1      | 20/05/2021    | 14/03/2015   | N       |
| T3       | 3       | CP-3             | B2      | 20/05/2014    | today date   | Y       |


**Application to be created:**

1. Producer
1. Consumer
1. Validation to be applied  
1. Junit test Case to be written on Components
1. Architecture document  

 
