 CREATE OR REPLACE FORCE VIEW "RMREP"."ANNOTATION_TAGS" ("ANNOTATION_ID", "TAGS") AS 
 WITH t AS
 ( SELECT id, tag_string str FROM annotation
 )
SELECT DISTINCT id annotation_id, 
 trim(x.column_value.extract('e/text()')) tags 
FROM t t, 
 TABLE (xmlsequence(xmltype('<e><e>' 
 || REPLACE(t.str,',','</e><e>') 
 || '</e></e>').extract('e/e'))) x;