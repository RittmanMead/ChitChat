CREATE TABLE "RMREP"."DESCRIPTOR" 
   (	"ID" NUMBER(19,0), 
	"VERSION" NUMBER(19,0), 
	"DASHBOARD" VARCHAR2(50 CHAR), 
	"DESCRIPTOR" VARCHAR2(4000 CHAR), 
	"KEY" VARCHAR2(50 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 8388608 NEXT 8388608 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "RMREP_TS" ;