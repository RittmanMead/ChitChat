CREATE OR REPLACE FORCE VIEW "RMREP"."TAG" ("ID", "VALUE") AS 
  WITH t AS 
  ( SELECT tag_string str FROM annotation
  )
SELECT rownum id, 
  value 
FROM 
  (SELECT DISTINCT lower(trim(x.column_value.extract('e/text()'))) value 
  FROM t t, 
    TABLE (xmlsequence(xmltype('<e><e>' 
    || REPLACE(t.str,',','</e><e>') 
    || '</e></e>').extract('e/e'))) x 
  );