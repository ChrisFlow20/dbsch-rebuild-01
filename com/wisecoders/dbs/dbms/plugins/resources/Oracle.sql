
:::Columns

SELECT /*+ ALL_ROWS */
  NULL AS table_cat,
  t.owner                                                   AS table_schem,
  t.table_name                                              AS tableName$,
  t.column_name                                             AS columnName$,
  DECODE (t.data_type,
      'CHAR', 1,
      'VARCHAR2', 12,
      'NVARCHAR2', 12,
      'NUMBER', 3,
      'LONG', -1,
      'DATE', 93,
      'RAW', -3,
      'LONG RAW', -4,
      'BLOB', 2004,
      'CLOB', 2005,
      'BFILE', -13,
      'FLOAT', 6,
      'TIMESTAMP(6)', 93,
      'TIMESTAMP(6) WITH TIME ZONE', -101,
      'TIMESTAMP(6) WITH LOCAL TIME ZONE', -102,
      'INTERVAL YEAR(2) TO MONTH', -103,
      'INTERVAL DAY(2) TO SECOND(6)', -104,
      'BINARY_FLOAT', 100,
      'BINARY_DOUBLE', 101, 1111)                            AS javaDataType$,
 t.data_type                                                 AS typeName$,
 DECODE (t.data_precision, null, -9999999, t.data_precision) AS columnSize$,      -- MODIFIED TO -9999999
 data_length                                                 AS dataLength$,
 DECODE(t.data_scale, null, -9999999, t.data_scale)          AS decimalDigits$,   -- MODIFIED
 10                                                          AS num_prec_radix,
 DECODE (t.nullable, 'N', 0, 1)                              AS nullable$,
 c.comments                                                  AS remarks$,
 t.data_default                                              AS dataDefault$,
 0                                                           AS sql_data_type,
 0                                                           AS sql_datetime_sub,
 t.data_length                                               AS char_octet_length,
 t.column_id                                                 AS ordinal_position,
 DECODE (t.nullable, 'N', 'NO', 'YES')                       AS is_nullable,
 char_used                                                   AS charUsed$,
 char_length                                                 AS charLength$,
 CASE WHEN ( char_used = 'C' AND data_length >= char_length*4 ) THEN 'true' ELSE 'false' END AS useChar$,
 virtual_column                                              AS virtual$,
 hidden_column                                               AS hidden$
FROM
    ALL_TAB_COLS t,
    ALL_COL_COMMENTS c
WHERE t.owner LIKE ? ESCAPE '/'
  AND t.owner = c.owner (+)
  AND t.table_name = c.table_name (+)
  AND t.column_name = c.column_name (+)
ORDER BY table_schem, tableName$, ordinal_position



::: Indexes

SELECT /*+ ALL_ROWS */
i.owner as owner$,
i.table_name as tableName$,
i.index_name as indexName$,
c.column_name as columnName$,
i.uniqueness as unique$,
ie.column_expression as expression$,
k.constraint_type as constraintType$
           FROM all_indexes i
     INNER JOIN all_ind_columns c
             ON ( i.owner=c.index_owner AND i.index_name=c.index_name AND i.table_name = c.table_name )
LEFT OUTER JOIN all_constraints k ON ( k.owner=i.owner AND k.constraint_name=i.index_name )
LEFT OUTER JOIN all_ind_expressions ie
             ON (ie.index_owner = c.index_owner AND ie.index_name = c.index_name AND ie.column_position = c.column_position )
          WHERE i.table_owner = ?
       ORDER BY i.owner, i.table_name, i.index_name, c.column_position

               
::: ForeignKeys                        

WITH jamm AS ( SELECT p.owner, p.table_name, column_name, p.constraint_name, p.constraint_type, p.deferrable, p.delete_rule, r_owner, r_constraint_name, position
  FROM all_constraints p
  INNER JOIN all_cons_columns pc ON ( pc.owner = p.owner AND pc.constraint_name = p.constraint_name AND pc.table_name = p.table_name AND p.constraint_type IN ( 'P','U','R' )) 
)
SELECT /*+ ALL_ROWS */ 
     NULL                           as pkTableCatalog$,
     p.owner                        as pkTableSchema$,
     p.table_name                   as pkTableName$,
     p.column_name                  as pkColumnName$,
     NULL                           as fkTableCatalog$,
     f.owner                        as fkTableSchema$,
     f.table_name                   as fkTableName$,
     f.column_name                  as fkColumnName$,
     f.position                     as keySequence$,
     NULL                           as updateRule$,
     decode (f.delete_rule, 'CASCADE', 0, 'SET NULL', 2, 1) as deleteRule$,
     f.constraint_name              as fkName$,
     p.constraint_name              as pkName$,
     decode(f.deferrable, 'DEFERRABLE', 5, 'NOT DEFERRABLE', 7, 'DEFERRED', 6) as deferrability$
FROM jamm p 
  INNER JOIN jamm f
  ON ( p.owner = f.r_owner AND p.constraint_name = f.r_constraint_name AND p.position = f.position )
WHERE
     p.owner = ?
 AND p.constraint_type in ('P','U')
 AND f.constraint_type = 'R'
ORDER BY
     fkTableSchema$, fkTableName$, keySequence$
     
     
::: ErrorPosition

DECLARE
  l_theCursor integer default dbms_sql.open_cursor; 
  l_status integer;  
BEGIN  
  BEGIN  
    dbms_sql.parse( l_theCursor, ?, dbms_sql.native ); 
    EXCEPTION WHEN OTHERS THEN l_status := dbms_sql.last_error_position;
  END;  
  dbms_sql.close_cursor( l_theCursor );  
  ? := l_status;  
END;

::: TableComments

SELECT /*+ ALL_ROWS */
         table_name as tableName$,
         table_type as tableType$,
         comments   as comments$
 FROM all_tab_comments
WHERE comments IS NOT NULL
  AND substr(table_name,1,4) != 'BIN$' AND owner=?

::: ColumnComments

SELECT /*+ ALL_ROWS */
         table_name  as tableName$,
         column_name as columnName$,
         comments    as comments$
 FROM all_col_comments
WHERE substr(table_name,1,4) != 'BIN$'
  AND comments IS NOT NULL
  AND owner=?

::: MatViewsComments

SELECT /*+ ALL_ROWS */
   mview_name   as mViewName$,
   comments     as comments$
 FROM all_mview_comments
WHERE comments IS NOT NULL
  AND substr(mview_name,1,4) != 'BIN$'
  AND owner=?