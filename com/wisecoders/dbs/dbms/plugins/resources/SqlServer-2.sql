::: GetExtendedProperties
SELECT
       'SCHEMA' as type,
       null as tablename,
       objname COLLATE SQL_Latin1_General_CP1_CI_AS as objname,
       CONVERT(nvarchar(max),value) as comment
   FROM
       [$catalog].sys.fn_listextendedproperty (NULL, 'schema', '$schema', null, null, null, null)
   WHERE
       name='$property'
UNION ALL
SELECT
        'TABLE' as type,
        objname COLLATE SQL_Latin1_General_CP1_CI_AS as tablename,
        objname COLLATE SQL_Latin1_General_CP1_CI_AS as objname,
        CONVERT(nvarchar(max),value) as comment
   FROM
        [$catalog].sys.fn_listextendedproperty (NULL, 'schema', '$schema', 'table', default, null, null)
   WHERE
        name='$property'
UNION ALL
(
SELECT
        'COLUMN' as type,
        t.name COLLATE SQL_Latin1_General_CP1_CI_AS as tablename,
        f.objname COLLATE SQL_Latin1_General_CP1_CI_AS as objname,
        CONVERT(nvarchar(max),f.value) as comment
    FROM
        ( SELECT ta.name FROM [$catalog].sys.tables ta JOIN [$catalog].sys.schemas sc ON ta.schema_id = sc.schema_id WHERE sc.name = '$schema' ) t
    CROSS APPLY
         [$catalog].sys.fn_listextendedproperty (NULL, 'schema', '$schema', 'table', t.name, 'column', default) f
    WHERE f.name='$property'
)
UNION ALL
(
SELECT
        'INDEX' as type,
        t.name COLLATE SQL_Latin1_General_CP1_CI_AS as tablename,
        f.objname COLLATE SQL_Latin1_General_CP1_CI_AS as objname,
        CONVERT(nvarchar(max),f.value) as comment
    FROM
        ( SELECT ta.name FROM [$catalog].sys.tables ta JOIN [$catalog].sys.schemas sc ON ta.schema_id = sc.schema_id WHERE sc.name = '$schema' ) t
    CROSS APPLY
         [$catalog].sys.fn_listextendedproperty (NULL, 'schema', '$schema', 'table', t.name, 'index', default) f
    WHERE f.name='$property'
)
UNION ALL
(
SELECT
        'FK' as type,
        t.name COLLATE SQL_Latin1_General_CP1_CI_AS as tablename,
        f.objname COLLATE SQL_Latin1_General_CP1_CI_AS as objname,
        CONVERT(nvarchar(max),f.value) as comment
    FROM
        ( SELECT ta.name FROM [$catalog].sys.tables ta JOIN [$catalog].sys.schemas sc ON ta.schema_id = sc.schema_id WHERE sc.name = '$schema' ) t
    CROSS APPLY
         [$catalog].sys.fn_listextendedproperty (NULL, 'schema', '$schema', 'table', t.name, 'constraint', default) f
    WHERE f.name='$property'
)
UNION ALL
SELECT
        'SEQUENCE' as type,
        objname COLLATE SQL_Latin1_General_CP1_CI_AS as tablename,
        objname COLLATE SQL_Latin1_General_CP1_CI_AS as objname,
        CONVERT(nvarchar(max),value) as comment
   FROM
        [$catalog].sys.fn_listextendedproperty (NULL, 'schema', '$schema', 'sequence', default, null, null)
   WHERE
        name='$property'
UNION ALL
SELECT
        'PROCEDURE' as type,
        objname COLLATE SQL_Latin1_General_CP1_CI_AS as tablename,
        objname COLLATE SQL_Latin1_General_CP1_CI_AS as objname,
        CONVERT(nvarchar(max),value) as comment
   FROM
        [$catalog].sys.fn_listextendedproperty (NULL, 'schema', '$schema', 'procedure', default, null, null)
   WHERE
        name='$property'
UNION ALL
SELECT
        'FUNCTION' as type,
        objname COLLATE SQL_Latin1_General_CP1_CI_AS as tablename,
        objname COLLATE SQL_Latin1_General_CP1_CI_AS as objname,
        CONVERT(nvarchar(max),value) as comment
   FROM
        [$catalog].sys.fn_listextendedproperty (NULL, 'schema', '$schema', 'function', default, null, null)
   WHERE
        name='$property'
