::: GetSchemaDDL

DO $$
DECLARE
  v_id          INT;
  v_name        VARCHAR(100);
  tbl           record;
  MVE           record;
  V_SCHEMA_NAME VARCHAR(200) ='__SCHEMA_NAME__';
  V_SCHEMA_ID   INT;
  V_TABLE_NAME  VARCHAR(200) ='__TABLE_NAME__';-- IF THE TABLE NAME IS EMPTY, THE COMPLETE SCHEMA WILL BE EXPORTED
  V_IMP_PARTITION_TBL BOOLEAN = __IMP_PARTITION_TBL__;
  V_TABLE_ID    INT = 0;
  V_OBJ_ID      INT = 0;
  V_LINE_NO     INT = 0;
  V_BUFF TEXT       = '';
  V_SCH_OID     oid;
  V_TBL_OID     oid;

BEGIN
  DROP TABLE IF EXISTS ddl_table;
  DROP SEQUENCE IF EXISTS obj_ids;
  DROP SEQUENCE IF EXISTS line_ids;
  CREATE TEMP SEQUENCE obj_ids;
  CREATE TEMP SEQUENCE line_ids;
  CREATE TEMP TABLE ddl_table (
    OBJECT_ID INT,
    AUX_ID VARCHAR(400),
    TYP INT,
    LINE_NO INT ,
    LINE_TEXT text,
    LINE_PREFIX VARCHAR(100) NOT NULL DEFAULT '',
    LINE_SUFFIX VARCHAR(100) NOT NULL DEFAULT ''
  );


  ---Check that source_schema exists
  SELECT oid INTO V_SCH_OID FROM pg_namespace WHERE nspname = V_SCHEMA_NAME;
  ----TYPE --0
  IF V_TABLE_NAME <> '' THEN
   SELECT oid INTO V_TBL_OID FROM pg_class c WHERE c.relnamespace=V_SCH_OID and c.relname = V_TABLE_NAME;
  END IF;

  IF V_TABLE_NAME ='' THEN
  INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
	SELECT 0,
	nextval('obj_ids') obj_id,
	1 line_no,
    CASE
       WHEN t.typcategory = 'C' THEN
            'CREATE TYPE ' || n.nspname || '.' || t.typname || ' AS (' || array_to_string(array_agg(a.attname || ' ' || pg_catalog.format_type(a.atttypid, a.atttypmod) ORDER BY c.relname, a.attnum), ', ') || ')'
       WHEN t.typcategory = 'E' THEN
            'CREATE TYPE ' || n.nspname || '.' || t.typname || ' AS ENUM (' || REPLACE(quote_literal(array_to_string(array_agg(e.enumlabel ORDER BY e.enumsortorder), ',')), ',', ''',''') || ');'
       ELSE
            ''
       END AS type_ddl, ';'  as line_suffix
    FROM pg_type t
        JOIN pg_namespace n ON (n.oid = t.typnamespace)
        LEFT JOIN pg_enum e ON (t.oid = e.enumtypid)
        LEFT JOIN pg_class c ON (c.reltype = t.oid)
        LEFT JOIN pg_attribute a ON (a.attrelid = c.oid)
    WHERE n.nspname = V_SCHEMA_NAME
        AND (c.relkind IS NULL
            OR c.relkind = 'c')
        AND t.typcategory IN ('C', 'E')
    GROUP BY c.relkind, n.nspname , t.typname , t.typcategory, pg_catalog.pg_get_userbyid(t.typowner)
    ORDER BY n.nspname, t.typcategory, t.typname;

 INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
	SELECT 0,
	nextval('obj_ids') obj_id,
	nextval('line_ids') line_no,
	 'COMMENT ON TYPE ' || n.nspname ||'.'||t.typname || ' IS ' || pg_catalog.obj_description(t.oid, 'pg_type') , ';' as ddl
    FROM pg_catalog.pg_type t
    JOIN pg_catalog.pg_namespace n ON n.oid = t.typnamespace
    WHERE (t.typrelid = 0 OR (SELECT c.relkind = 'c' FROM pg_catalog.pg_class c WHERE c.oid = t.typrelid))
      AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_type el WHERE el.oid = t.typelem AND el.typarray = t.oid)
      AND n.nspname = V_SCHEMA_NAME COLLATE pg_catalog.default
      AND pg_catalog.obj_description(t.oid, 'pg_type') IS NOT NULL and t.typtype = 'c';

-- TODO FOR <10000
-- Insert data into the temporary table
-- SEQUENCES - TYPE=1

	INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
	select 1,
	nextval('obj_ids') obj_id,
	nextval('line_ids') line_no,
	'CREATE SEQUENCE ' || s1.sequence_schema || '.' || s1.sequence_name ||
        CASE WHEN s2.data_type = ANY ('{integer,smallint}'::regtype[])
             THEN  ' AS '||s2.data_type  ::regtype
             ELSE '' END ||' START WITH ' || s2.start_value || ' INCREMENT BY ' || s2.increment_by ||
        CASE WHEN s2.min_value=1 then '' else ' MINVALUE ' || s2.min_value end ||
        CASE WHEN s2.data_type ='integer' ::regtype and s2.max_value=2147483647 THEN  ''
             WHEN s2.data_type ='bigint' ::regtype and s2.max_value=9223372036854775807 THEN  ''
             WHEN s2.data_type ='smallint' ::regtype and s2.max_value=32767 THEN  ''
                ELSE ' MAXVALUE ' || s2.max_value  end ||' ' || CASE WHEN s2.cycle THEN 'CYCLE' ELSE '' END ||
           CASE WHEN s2.cache_size=1 THEN ''
                ELSE ' CACHE ' || s2.cache_size END
         AS line_text,
    ';'  AS line_suffix FROM information_schema.sequences s1
    JOIN pg_sequences s2 ON (s1.sequence_schema = s2.schemaname AND s1.sequence_name = s2.sequencename)
    AND s1.sequence_schema = V_SCHEMA_NAME;


INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
		SELECT  1 as typ,nextval('obj_ids')  as obj_id,nextval('line_ids') as line_no ,
 'COMMENT ON ' ||  'SEQUENCE ' || quote_ident(n.nspname) || '.' ||  quote_ident(c.relname)  ||
       ' IS '   || quote_literal(d.description) as ddl,';' AS LINE_SUFFIX
    FROM pg_class c
    JOIN pg_namespace n ON (n.oid = c.relnamespace)
    LEFT JOIN pg_description d ON (c.oid = d.objoid)
       WHERE c.relkind = 'S' AND d.description IS NOT NULL AND n.oid = V_SCH_OID
    ORDER BY ddl;
	END IF;

--2 - TABLES
--3 - COLUMNS
--4 - CONSTRAINT
--5 - INDEXES
--6 - TRIGGERS
--7 - VIEWS
--8 - MVIEWS
--9 - mVIEWINDEX
--10 - FOREIGN KEY
--11 - FUNCTION/PROCEDURES
--12 - TRIGGERS

FOR TBL IN SELECT DISTINCT n.nspname, c.relpartbound, i.inhparent, c.relname, c.relpersistence, c.relispartition,
                           c.relkind, obj_description(c.oid), i.inhrelid,
                           pg_catalog.pg_get_userbyid(c.relowner) AS "Owner",c.oid, CASE WHEN reltablespace = 0 THEN 'pg_default' ELSE ts.spcname END as tablespace
    FROM pg_class c
        JOIN pg_namespace n ON (n.oid = c.relnamespace
                AND n.nspname = V_SCHEMA_NAME
                AND c.relkind IN ('r', 'p'))
        LEFT JOIN pg_inherits i ON (c.oid = i.inhrelid)
        LEFT JOIN pg_tablespace ts ON (c.reltablespace = ts.oid)
		WHERE CASE WHEN V_TABLE_NAME <>'' then  (relname in (V_TABLE_NAME) OR i.inhparent=V_TBL_OID )  else 1=1 end
    ORDER BY c.relkind , c.relname
  LOOP
	V_OBJ_ID := nextval('obj_ids');
	 SELECT setval('line_ids', 1) INTO V_LINE_NO;

		IF TBL.relispartition AND TBL.inhrelid  IS NOT NULL AND TBL.RELKIND ='r' THEN  -- FOR INHRITED INTERNAL PARITIONS
		    IF ( V_IMP_PARTITION_TBL = TRUE ) THEN
				SELECT ' PARTITION OF ' || quote_ident(TBL.nspname) || '.' || quote_ident(c2.relname) || ' ' || pg_get_expr(TBL.relpartbound, TBL.oid, true)  into 		V_BUFF
						FROM   pg_class c2
				WHERE  TBL.inhparent = c2.oid and TBL.relkind = 'r';
			    INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
			    SELECT  2,V_OBJ_ID as obj_id,V_LINE_NO, 'CREATE '
			    || ' TABLE ' || quote_ident(TBL.nspname) || '.' || quote_ident(TBL.relname)  ||V_BUFF ,' ;' as suffix			;
            END IF;
		ELSE
			INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
			SELECT  2,V_OBJ_ID as obj_id,V_LINE_NO, 'CREATE '
			|| CASE TBL.relpersistence when 'u' then 'UNLOGGED' when 't' then 'TEMPORARY' else '' END
			|| ' TABLE ' || quote_ident(TBL.nspname) || '.' || quote_ident(TBL.relname)  ,' (' as suffix ;
		END IF;
-- COLUMNS
-- SKIP INHERITANCE TABLES
		INSERT INTO ddl_table (TYP,OBJECT_ID,AUX_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
			SELECT  3 as typ, V_OBJ_ID as obj_id, v_colrec.column_name,
			     nextval('line_ids') as line_no,
			     '  ' || CASE WHEN v_colrec.column_name in ('key') THEN '"' || v_colrec.column_name || '"' ELSE quote_ident(v_colrec.column_name) || ' ' END ||
			CASE WHEN v_colrec.udt_name in ('geometry', 'box2d', 'box2df', 'box3d', 'geography', 'geometry_dump', 'gidx', 'spheroid', 'valid_detail')  THEN v_colrec.udt_name
			     WHEN v_colrec.data_type = 'USER-DEFINED' THEN v_colrec.udt_schema || '.' || v_colrec.udt_name
			     WHEN v_colrec.domain_name IS NOT NULL THEN v_colrec.domain_schema || '.' || v_colrec.domain_name
				 WHEN v_colrec.data_type = 'character varying' then 'varchar'
				 WHEN v_colrec.data_type = 'character' then 'char'
				 WHEN v_colrec.data_type = 'time with time zone' then 'timetz'
				 WHEN v_colrec.data_type = 'time without time zone' then 'time'
				 WHEN v_colrec.data_type = 'timestamp with time zone' then 'timestamptz'
				 WHEN v_colrec.data_type = 'timestamp without time zone' then 'timestamp'
				 WHEN v_colrec.data_type = 'bit varying' then 'varbit'
				 WHEN (v_colrec.data_type = 'ARRAY' OR pg_get_serial_sequence(quote_ident(table_schema)||'.'||quote_ident(table_name), v_colrec.column_name) IS NOT NULL ) THEN
	                  (SELECT CASE WHEN a.atttypid = ANY ('{int,int8,int2}'::regtype[]) AND EXISTS (SELECT FROM pg_attrdef ad WHERE ad.adrelid = a.attrelid AND ad.adnum   = a.attnum AND
		                               pg_get_expr(ad.adbin, ad.adrelid) = 'nextval(''' || (pg_get_serial_sequence (a.attrelid::regclass::text, a.attname))::regclass || '''::regclass)') AND
		                               -- ENSURE THE SEQUENCE NAME IS <table_name>_<column_name>_seq, AS POSTGRES DO
		                               pg_get_serial_sequence (a.attrelid::regclass::text, a.attname)::regclass::text = v_colrec.table_name || '_' || v_colrec.column_name || '_seq'
                                 THEN
                                    CASE a.atttypid
                                    WHEN 'int'::regtype  THEN 'serial'
                                    WHEN 'int8'::regtype THEN 'bigserial'
                                    WHEN 'int2'::regtype THEN 'smallserial'
                                 END
		                     ELSE format_type(a.atttypid, a.atttypmod)
		                     END AS data_type
  		              FROM  pg_attribute a, pg_type t
		              WHERE  a.attname = v_colrec.column_name and a.attnum > 0 AND a.attrelid = TBL.oid AND a.atttypid = t.oid ORDER BY a.attnum LIMIT 1)
  		    ELSE v_colrec.data_type END
			 || CASE WHEN v_colrec.data_type = 'bit' AND COALESCE(v_colrec.character_maximum_length,0) = 1 THEN ' ' ELSE
				CASE WHEN v_colrec.character_maximum_length IS NOT NULL THEN ('(' || v_colrec.character_maximum_length || ')')
					 WHEN v_colrec.numeric_precision > 0 AND v_colrec.numeric_scale > 0 THEN '(' || v_colrec.numeric_precision || ',' || v_colrec.numeric_scale || ')'
					 WHEN v_colrec.numeric_precision > 0 AND v_colrec.numeric_scale = 0 THEN '(' || v_colrec.numeric_precision || ')'
				     WHEN v_colrec.datetime_precision IS NOT NULL THEN
				     	 CASE WHEN v_colrec.data_type = 'timestamp with time zone' AND v_colrec.datetime_precision = 6 THEN ''
                     		  WHEN v_colrec.data_type = 'timestamp without time zone' AND v_colrec.datetime_precision = 6 THEN ''
                              ELSE ('(' || v_colrec.datetime_precision || ')') END
					 ELSE '' END || ' '
				END
			 || CASE WHEN pg_get_serial_sequence(quote_ident(table_schema)||'.'||quote_ident(table_name), v_colrec.column_name) IS NOT NULL THEN '' ELSE CASE WHEN v_colrec.collation_name  IS NOT NULL THEN (' COLLATE "'||v_colrec.collation_name ||'" ') ELSE '' END END
		     || CASE WHEN v_colrec.is_nullable = 'NO' THEN 'NOT NULL' ELSE '' END
			 || CASE WHEN v_colrec.is_identity = 'YES' THEN
			       CASE WHEN v_colrec.identity_generation = 'ALWAYS' THEN ' GENERATED ALWAYS AS IDENTITY'
			       ELSE ' GENERATED BY DEFAULT AS IDENTITY' END ELSE ''
			    END
			 || CASE WHEN pg_get_serial_sequence(quote_ident(table_schema)||'.'||quote_ident(table_name), v_colrec.column_name) IS NOT NULL THEN '' ELSE CASE WHEN v_colrec.column_default IS NOT NULL THEN (' DEFAULT ' || v_colrec.column_default) ELSE '' END END as line_text,
			  ',' as suffix
			 FROM information_schema.columns v_colrec
			 WHERE (table_schema, table_name) = (TBL.nspname, TBL.relname) ORDER BY ordinal_position;

     	 	 IF TBL.inhrelid IS NOT NULL THEN
                DELETE FROM ddl_table WHERE OBJECT_ID=V_OBJ_ID AND AUX_ID IN (
                   SELECT column_name FROM information_schema.columns WHERE (table_schema, table_name) = (SELECT n.nspname, c.relname FROM pg_class c JOIN pg_namespace n ON (n.oid = c.relnamespace ) WHERE c.oid = TBL.inhparent ));
			 END IF;


-- CONSTRAINTS
		INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
		SELECT  4 as typ,V_OBJ_ID as obj_id,nextval('line_ids') as line_no ,
		 '  CONSTRAINT '|| quote_ident(constraint_name) ||' '||constraint_definition , ',' AS SUFFIX from ( SELECT con.conname as constraint_name  , pg_get_constraintdef(con.oid) as constraint_definition,
        CASE
            WHEN con.contype = 'p' THEN 1 -- primary key constraint
            WHEN con.contype = 'u' THEN 2 -- unique constraint
            WHEN con.contype = 'f' THEN 3 -- foreign key constraint
            WHEN con.contype = 'c' THEN 4
            ELSE 5
          END as type_rank

        FROM pg_catalog.pg_constraint con where  con.conrelid=TBL.oid       and con.contype <> 'f'
              AND con.conparentid =  0 -- not valid for version before 11
              ORDER BY type_rank ) CO ;

		V_LINE_NO := nextval('line_ids')-1;

			 V_BUFF:='';
			 -- add partition by if parition
			 IF TBL.RELKIND='p' THEN
				SELECT ' PARTITION BY '||pg_catalog.pg_get_partkeydef(TBL.oid::pg_catalog.oid) into V_BUFF;
			  -- remove last suffix
			  END IF;

-- See if this is an inheritance-based child table and finish up the table create TRIGGERD ONS
		IF NOT TBL.relispartition AND TBL.inhrelid  IS NOT NULL AND TBL.RELKIND ='r' THEN
  		    SELECT  ' INHERITS (' || quote_ident(TBL.nspname)  || '.' || quote_ident(c2.relname) || ') ' into V_BUFF  FROM  pg_class c2
				WHERE  TBL.inhparent = c2.oid and TBL.relkind = 'r';
			END IF;
		IF NOT (TBL.relispartition AND TBL.inhrelid  IS NOT NULL AND TBL.RELKIND ='r')  THEN
		    UPDATE ddl_table SET   LINE_SUFFIX=' ) '||V_BUFF||' ;' WHERE LINE_NO=V_LINE_NO AND OBJECT_ID=V_OBJ_ID ;
		END IF;


		PERFORM SETVAL('line_ids',V_LINE_NO) ;
--INDEXES
INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
		SELECT  5 as typ, V_OBJ_ID as obj_id,nextval('line_ids') as line_no,
		   REPLACE(
		     REPLACE(indexdef, 'CREATE INDEX', 'CREATE INDEX IF NOT EXISTS'),
		   'CREATE UNIQUE INDEX','CREATE UNIQUE INDEX IF NOT EXISTS') INDEX_DEF,';' AS LINE_SUFFIX
		FROM pg_indexes
		WHERE (schemaname, tablename) =  (TBL.nspname, TBL.relname) AND indexname NOT IN
		   ( SELECT con.conname
              FROM pg_catalog.pg_constraint con
              WHERE con.conrelid=TBL.oid
		      AND ( con.contype = 'p' OR con.contype = 'u' OR con.contype = 'x') AND con.conparentid = 0 -- not valid for version before 11
          );

-----------------TRIGGERS-----------
/*
INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
		SELECT  6 as typ,V_OBJ_ID as obj_id,nextval('line_ids') as line_no ,
  pg_get_triggerdef(t.oid, True) , ';' AS SUFFIX  FROM pg_trigger t
      WHERE TBL.relkind = 'r' and t.tgrelid = TBL.oid and NOT t.tgisinternal;

*/


--------------COMMENTS----------------------

INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
		SELECT 5 as typ, V_OBJ_ID as obj_id, nextval('line_ids') as line_no ,
 'COMMENT ON ' || CASE WHEN c.relkind in ('r','p') AND a.attname IS NULL THEN 'TABLE '
                       WHEN c.relkind in ('r','p') AND a.attname IS NOT NULL THEN 'COLUMN '
                       WHEN c.relkind = 'm' THEN 'MATERIALIZED VIEW '
                       WHEN c.relkind = 'v' THEN 'VIEW '
                       WHEN c.relkind = 'i' THEN 'INDEX '
                       WHEN c.relkind = 'S' THEN 'SEQUENCE '
                       ELSE 'XX' END
                || quote_ident(TBL.nspname) || '.'
                || CASE WHEN c.relkind in ('r','p') AND a.attname IS NOT NULL THEN quote_ident(c.relname) || '.' || quote_ident(a.attname)
                        ELSE quote_ident(c.relname) END
                || ' IS '   || quote_literal(d.description) as ddl,
       ';' AS LINE_SUFFIX
    FROM pg_class c
    JOIN pg_namespace n ON (n.oid = c.relnamespace)
    LEFT JOIN pg_description d ON (c.oid = d.objoid)
    LEFT JOIN pg_attribute a ON (c.oid = a.attrelid  AND a.attnum > 0 AND d.objsubid = a.attnum )
    WHERE c.relkind <> 'f' AND d.description IS NOT NULL AND n.nspname = TBL.nspname AND c.oid=TBL.oid
    ORDER BY ddl;



INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
	SELECT  5 as typ,V_OBJ_ID as obj_id,nextval('line_ids') as line_no ,
 'COMMENT ON '||'INDEX ' || quote_ident(TBL.nspname) || '.' || quote_ident(c.relname) || ' IS '   || quote_literal(d.description) as ddl,';' AS LINE_SUFFIX
FROM pg_indexes i, pg_class c, pg_description d , pg_namespace n where  c.oid = d.objoid and c.relname=i.indexname and
i.schemaname=n.nspname
and c.relnamespace=n.oid and
(i.schemaname, i.tablename) =  (TBL.nspname, TBL.relname) AND
indexname NOT IN ( SELECT con.conname
        FROM pg_catalog.pg_constraint con where  con.conrelid=TBL.oid
	AND
				  con.contype = 'p'  AND con.conparentid =  0 -- not valid for version before 11
        );
END LOOP;


-------------------FOREIGN KEYS CONSTRAINTS--------------
SELECT setval('line_ids', 1,FALSE) INTO V_LINE_NO;
INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
SELECT  10 as typ,nextval('obj_ids') as obj_id,nextval('line_ids') as line_no ,
'ALTER TABLE ' || quote_ident(V_SCHEMA_NAME) || '.' || quote_ident(rn.relname)
                          || ' ADD CONSTRAINT ' || quote_ident(ct.conname) || ' ' || pg_get_constraintdef(ct.oid) , ' ;' AS SUFFIX
    FROM pg_constraint ct
    JOIN pg_class rn ON rn.oid = ct.conrelid
    -- Issue#103 needed to add this left join
    LEFT JOIN pg_inherits i ON (rn.oid = i.inhrelid)
    WHERE connamespace = V_SCH_OID
        AND rn.relkind IN ('r','p')
        AND ct.contype = 'f'
        AND i.inhrelid is null
		AND CASE WHEN V_TABLE_NAME <>'' THEN V_TABLE_NAME=rn.relname else 1=1 end ;

INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
SELECT  10 as typ,nextval('obj_ids') as obj_id,nextval('line_ids') as line_no
, 'COMMENT on CONSTRAINT '|| t.conname ||' ON '||c.relname||' IS '||  d.description , ' ;' AS SUFFIX
from pg_class c
join pg_constraint t on c.oid = t.conrelid
join pg_description d on t.oid = d.objoid and t.tableoid = d.classoid where
relkind  in ('r','p')
and c.relnamespace=V_SCH_OID  AND CASE WHEN V_TABLE_NAME <>'' THEN V_TABLE_NAME=c.relname else 1=1 end ;


-------------------VIEWS--------------

IF V_TABLE_NAME ='' THEN
    --V_OBJ_ID := nextval('obj_ids');
     SELECT setval('line_ids', 1) INTO V_LINE_NO;

   WITH RECURSIVE views AS (
       SELECT n.nspname as schemaname, v.relname as tablename, v.oid::regclass AS viewname,v.oid,
              pg_catalog.pg_get_userbyid(v.relowner) as owner,
              1 AS level
       FROM pg_depend AS d
          JOIN pg_rewrite AS r
             ON r.oid = d.objid
          JOIN pg_class AS v
             ON v.oid = r.ev_class
          JOIN pg_namespace n
             ON n.oid = v.relnamespace
       -- WHERE v.relkind IN ('v', 'm')
       WHERE v.relkind IN ('v')
         AND d.classid = 'pg_rewrite'::regclass
         AND d.refclassid = 'pg_class'::regclass
         AND d.deptype = 'n'
   UNION
       -- add the views that depend on these
       SELECT n.nspname as schemaname, v.relname as tablename, v.oid::regclass AS viewname,v.oid,
               pg_catalog.pg_get_userbyid(v.relowner) as owner,
              views.level + 1
       FROM views
          JOIN pg_depend AS d
             ON d.refobjid = views.viewname
          JOIN pg_rewrite AS r
             ON r.oid = d.objid
          JOIN pg_class AS v
             ON v.oid = r.ev_class
          JOIN pg_namespace n
             ON n.oid = v.relnamespace
       -- WHERE v.relkind IN ('v', 'm')
       WHERE v.relkind IN ('v')
         AND d.classid = 'pg_rewrite'::regclass
             AND d.refclassid = 'pg_class'::regclass
         AND d.deptype = 'n'
         AND v.oid <> views.viewname
   ),
   ins1 as (INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
				SELECT  7 as typ,nextval('obj_ids') as obj_id,nextval('line_ids') as line_no ,

 'COMMENT ON ' || 'VIEW '||
       viewname  ||
       ' IS '   || quote_literal(d.description) as ddl,';' AS LINE_SUFFIX
    FROM views v

         JOIN pg_description d ON (v.oid = d.objoid)
    WHERE  d.description IS NOT NULL AND  schemaname = V_SCHEMA_NAME GROUP BY schemaname, tablename, viewname, owner ,d.description
    ORDER BY max(level), schemaname, tablename returning OBJECT_ID)
	INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
				SELECT  7 as typ,nextval('obj_ids') as obj_id,V_LINE_NO as line_no ,
		   format('CREATE OR REPLACE%s VIEW %s AS%s',
						  '',
						  viewname,
						  pg_get_viewdef(viewname)) , '' AS SUFFIX
			FROM views
			WHERE schemaname = V_SCHEMA_NAME
			GROUP BY schemaname, tablename, viewname, owner
			ORDER BY max(level), schemaname, tablename	;


--MATERIALIZED VIEWS

	 SELECT setval('line_ids', 1) INTO V_LINE_NO;

	 FOR MVE IN ( SELECT  matviewname::text mviewname, definition mviewdef FROM pg_catalog.pg_matviews WHERE schemaname = V_SCHEMA_NAME )
	 LOOP
	V_OBJ_ID := nextval('obj_ids');
		INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
				SELECT  8 as typ,V_OBJ_ID as obj_id,V_LINE_NO as line_no ,
		' CREATE MATERIALIZED VIEW ' ||quote_ident(V_SCHEMA_NAME)|| '.' || quote_ident(MVE.mviewname)|| ' AS ' ||replace(MVE.mviewdef,';','') , ' ;' AS SUFFIX;
		INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
				SELECT  9 as typ,V_OBJ_ID as obj_id,nextval('line_ids') as line_no ,indexdef, ' ;' AS SUFFIX  from 	pg_indexes where schemaname = V_SCHEMA_NAME and tablename = MVE.mviewname order by indexname	;


		INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
				SELECT  8 as typ,V_OBJ_ID as obj_id,nextval('line_ids') as line_no ,
			'COMMENT ON ' || 'MATERIALIZED VIEW '
       ||quote_ident(V_SCHEMA_NAME)|| '.' || quote_ident(MVE.mviewname)||
       ' IS '   || quote_literal(d.description) as ddl,';' AS LINE_SUFFIX
	    FROM pg_class c
    JOIN pg_namespace n ON (n.oid = c.relnamespace)
     JOIN pg_description d ON (c.oid = d.objoid)
   WHERE c.relkind='m' AND d.description IS NOT NULL AND n.nspname = V_SCHEMA_NAME and c.relname=MVE.mviewname;
		END LOOP;


---FUNCTION/PROCEDURES
SELECT setval('line_ids', 1,FALSE) INTO V_LINE_NO;
INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
SELECT  11 as typ,nextval('obj_ids') as obj_id,nextval('line_ids') as line_no ,
        pg_get_functiondef(p.oid),' ;' AS SUFFIX
        FROM pg_proc p WHERE p.pronamespace = V_SCH_OID AND p.prokind != 'a'	;



SELECT setval('line_ids', 1,FALSE) INTO V_LINE_NO;
INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
SELECT  11 as typ,nextval('obj_ids') as obj_id,nextval('line_ids') as line_no ,
 'COMMENT ON ' || CASE WHEN p.prokind = 'f' THEN 'FUNCTION ' WHEN p.prokind = 'p' THEN 'PROCEDURE '  END ||
    n.nspname || '.' || p.proname || ' (' || oidvectortypes(p.proargtypes) || ')'
    ' IS '   || quote_literal(d.description) , ';' as suffix
    FROM pg_catalog.pg_namespace n
    JOIN pg_catalog.pg_proc p ON p.pronamespace = n.oid
    JOIN pg_description d ON (d.objoid = p.oid)
    WHERE n.nspname = V_SCHEMA_NAME and p.prokind in ('p','f') ;


--------TRIGGERS---------

SELECT setval('line_ids', 1,FALSE) INTO V_LINE_NO;
INSERT INTO ddl_table (TYP,OBJECT_ID,LINE_NO,LINE_TEXT,LINE_SUFFIX )
SELECT  12 as typ,nextval('obj_ids') as obj_id,nextval('line_ids') as line_no ,
  pg_get_triggerdef(t.oid),  ';' AS SUFFIX
    FROM pg_trigger t, pg_class c, pg_namespace n, pg_proc p
    WHERE n.nspname = V_SCHEMA_NAME
      AND n.oid = c.relnamespace
      AND c.relkind in ('r','p')
      AND n.oid = p.pronamespace
      AND c.oid = t.tgrelid
      AND p.oid = t.tgfoid
	  AND CASE WHEN V_TABLE_NAME <>'' THEN V_TABLE_NAME=c.relname else 1=1 end
	  --and n.nspname=V_SCHEMA_NAME
      ORDER BY c.relname, t.tgname;
END IF;
END $$