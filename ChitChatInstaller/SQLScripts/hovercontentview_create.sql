CREATE OR REPLACE FORCE VIEW "RMREP"."HOVER_CONTENT" ("ID", "KEY", "CONTENT", "PAGE") AS 
  select max(id) id, key, listagg(content, ' ') within group (order by id) as content,page 
from 
(SELECT rownum AS id, 
cell_id AS KEY, 
'<table>' 
|| '  <tr>'
|| '<td align="left">'
|| '<p align="left"><h3>'
|| title
|| '</h3>'
|| '<small>'
|| created_by
|| ' at '
|| to_char(created_on, 'DD-MON-YYYY HH24:Mi')
|| '</small>'
|| '</p><p align="left">'
|| annotation_text
|| '</p>'
|| '<p><button class="addComment btn btn-black" modifiedBy="@{biServer.variables[''NQ_SESSION.USER'']}"  cid="'
|| ID
||'"'
|| ' action="edit">Edit</button> '
|| '<button class="addComment btn btn-gray" modifiedBy="@{biServer.variables[''NQ_SESSION.USER'']}" cid="'
|| ID
|| '"'
|| ' action="share">Share</button></p><hr>' 
|| '</table>' content, 
page 
FROM annotation 
WHERE cell_id IS NOT NULL) 
group by key, page 
union 
select id + 4000, key, descriptor, dashboard from descriptor;