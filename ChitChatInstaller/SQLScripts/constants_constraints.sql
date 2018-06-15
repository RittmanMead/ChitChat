  ALTER TABLE "RMREP"."APPLICATION_CONSTANT" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 8388608 NEXT 8388608 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "RMREP_TS"  ENABLE;
  ALTER TABLE "RMREP"."APPLICATION_CONSTANT" MODIFY ("ENCRYPTED" NOT NULL ENABLE);
  ALTER TABLE "RMREP"."APPLICATION_CONSTANT" MODIFY ("VALUE" NOT NULL ENABLE);
  ALTER TABLE "RMREP"."APPLICATION_CONSTANT" MODIFY ("KEY" NOT NULL ENABLE);
  ALTER TABLE "RMREP"."APPLICATION_CONSTANT" MODIFY ("VERSION" NOT NULL ENABLE);
  ALTER TABLE "RMREP"."APPLICATION_CONSTANT" MODIFY ("ID" NOT NULL ENABLE);