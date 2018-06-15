 CREATE TABLESPACE RMREP_TS datafile '/opt/oracle/oradata/ORCLDB/ORCLPDB1/RMREP_D01' SIZE 240M AUTOEXTEND ON BLOCKSIZE 8192 EXTENT MANAGEMENT LOCAL UNIFORM SIZE 8M;CREATE USER RMREP IDENTIFIED BY Admin123 DEFAULT TABLESPACE RMREP_TS TEMPORARY TABLESPACE temp QUOTA UNLIMITED ON RMREP_TS ;GRANT CONNECT, RESOURCE TO RMREP;GRANT CREATE SEQUENCE TO RMREP;GRANT CREATE VIEW TO RMREP;GRANT CREATE SNAPSHOT TO RMREP;CREATE SEQUENCE  "RMREP"."ANNOTATION_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;