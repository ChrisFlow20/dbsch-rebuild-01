::: GetSchemaDDL

BEGIN
    SET NOCOUNT ON;
    DECLARE
        @SCHEMA_NAME            VARCHAR(200) ='__SCHEMA_NAME__',
        @SCHEMA_ID              INT,
        @TABLE_NAME             VARCHAR(200) ='__TABLE_NAME__',
        @TABLE_ID               INT = 0;
    DECLARE @DDLScript TABLE (
        [OBJECT_ID]             INT,
        [TYPE]                  INT,
        [LINE_NO]               INT IDENTITY,
        [LINE_TEXT]             VARCHAR(MAX),
        [LINE_PREFIX]           VARCHAR(100) NOT NULL DEFAULT '',
        [LINE_SUFFIX]           VARCHAR(100) NOT NULL DEFAULT ''
);

SET @SCHEMA_ID = SCHEMA_ID( @SCHEMA_NAME )
IF ( @TABLE_NAME != '' AND LEFT(@TABLE_NAME,1) = '#'  COLLATE SQL_Latin1_General_CP1_CI_AS )
    BEGIN
        PRINT '--GO TO TEMP PROCESS';
        GOTO TEMPPROCESS;
    END;
IF ( @TABLE_NAME != '' )
    SELECT
        @TABLE_ID = [objz].[object_id]
      FROM [sys].[objects]     AS [objz]
      WHERE [objz].[type]      IN ('S','U')
        AND [objz].[name]      <>  'dtproperties'
        AND [objz].[name]      =  @TABLE_NAME
        AND [objz].[schema_id] =  SCHEMA_ID(@SCHEMA_NAME) ;
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
SELECT 02, [tabz].[object_id],
    CASE
        WHEN [tabz].[history_table_id] IS NULL
        THEN ''
        ELSE 'ALTER TABLE ' + QUOTENAME(OBJECT_SCHEMA_NAME([tabz].[object_id]) ) + '.' + QUOTENAME(OBJECT_NAME([tabz].[object_id])) + ' SET (SYSTEM_VERSIONING = OFF);' + CHAR(10)
             +  'IF OBJECT_ID(''' + QUOTENAME(OBJECT_SCHEMA_NAME([tabz].[history_table_id]) ) + '.' + QUOTENAME(OBJECT_NAME([tabz].[history_table_id])) + ''') IS NOT NULL ' + CHAR(10)
             + 'DROP TABLE ' + QUOTENAME(OBJECT_SCHEMA_NAME([tabz].[history_table_id])) + '.' + QUOTENAME(OBJECT_NAME([tabz].[history_table_id])) + ' ' + CHAR(10) + 'GO' + CHAR(10)
        END
    + 'IF OBJECT_ID(''' + QUOTENAME(OBJECT_SCHEMA_NAME([tabz].[object_id]) ) + '.' + QUOTENAME(OBJECT_NAME([tabz].[object_id])) + ''') IS NOT NULL ' + CHAR(10)
    + 'DROP TABLE ' + QUOTENAME(OBJECT_SCHEMA_NAME([tabz].[object_id])) + '.' + QUOTENAME(OBJECT_NAME([tabz].[object_id])) + ' ' + CHAR(10) + 'GO' + CHAR(10)
    + 'CREATE ' + ( CASE WHEN tabz.is_external = 1 THEN 'EXTERNAL ' ELSE '' END ) + 'TABLE ' + QUOTENAME(OBJECT_SCHEMA_NAME([tabz].[object_id])) + '.' + QUOTENAME(OBJECT_NAME([tabz].[object_id])) + ' ( '
FROM [sys].[tables] [tabz]
WHERE
  [tabz].[schema_id] = @SCHEMA_ID AND ( @TABLE_ID = 0 OR [tabz].[object_id] = @TABLE_ID )
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
SELECT 3, [objz].[object_id],
   CASE
       WHEN [colz].[is_computed] = 1
       THEN QUOTENAME([colz].[name])
           + ' '
           + 'AS ' + ISNULL([CALC].[definition],'')
           + CASE WHEN [CALC].[is_persisted] = 1 THEN ' PERSISTED' ELSE '' END
       ELSE QUOTENAME([colz].[name])
           + ' '
           + UPPER(TYPE_NAME([colz].[user_type_id]))
           + CASE
            -- DATA TYPES WITH PRECISION AND SCALE
           WHEN TYPE_NAME([colz].[user_type_id]) IN ('decimal','numeric')
           THEN '('
                + CONVERT(VARCHAR,[colz].[precision])
                + ','
                + CONVERT(VARCHAR,[colz].[scale])
                + ') '
                + CASE

                    WHEN COLUMNPROPERTY ( [colz].[object_id], [colz].[name] , 'IsIdentity' ) = 0
                    THEN ''
                    ELSE ' IDENTITY'
                        + CASE
                          WHEN ISNULL(IDENT_SEED([objz].[name]),1) = 1 AND ISNULL(IDENT_INCR([objz].[name]),1) = 1 THEN ''
                          ELSE '(' + CONVERT(VARCHAR,ISNULL(IDENT_SEED([objz].[name]),1) ) + ',' + CONVERT(VARCHAR,ISNULL(IDENT_INCR([objz].[name]),1) ) + ')'
                          END

                   END
                + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
              -- DATA TYPES WITH SCALE
              WHEN TYPE_NAME([colz].[user_type_id]) IN ('datetime2','datetimeoffset','time')
              THEN CASE WHEN [colz].[scale] < 7 THEN '(' + CONVERT(VARCHAR,[colz].[scale]) + ') ' ELSE '    ' END
                 + ' '
                 + CASE  WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                 + CASE [colz].[generated_always_type]
                     WHEN 0 THEN ''
                     WHEN 1 THEN ' GENERATED ALWAYS AS ROW START'
                     WHEN 2 THEN ' GENERATED ALWAYS AS ROW END'
                     ELSE ''
                   END
                 + CASE WHEN [colz].[is_hidden] = 1 THEN ' HIDDEN' ELSE '' END
                 + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
              -- DATA TYPES WITH NO/PRECISION/SCALE
              WHEN  TYPE_NAME([colz].[user_type_id]) IN ('float') --,'real')
              THEN
                   CASE
                   -- 53 IS DEFAULT PRECISION
                   WHEN [colz].[precision] = 53
                   THEN
                       + SPACE(1)
                       + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                       + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
                   ELSE '('
                       + CONVERT(VARCHAR,[colz].[precision])
                       + ') '
                       + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                       + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
                   END
              -- DATA TYPE WITH MAX_LENGTH
              WHEN  TYPE_NAME([colz].[user_type_id]) IN ('char','varchar','binary','varbinary')
              THEN CASE
                   WHEN  [colz].[max_length] = -1
                   THEN  '(max)'
                        + CASE WHEN [colz].collation_name IS NULL OR [colz].collation_name = 'SQL_Latin1_General_CP1_CI_AS' THEN '' ELSE ' COLLATE ' + [colz].collation_name END
                        + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                        + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
                   ELSE '('
                        + CONVERT(VARCHAR,[colz].[max_length])
                        + ') '
                        -- COLLATE
                        + CASE WHEN [colz].collation_name IS NULL OR [colz].collation_name = 'SQL_Latin1_General_CP1_CI_AS' THEN '' ELSE ' COLLATE ' + [colz].collation_name END
                        + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                        + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
                   END
              -- DATA TYPE WITH MAX_LENGTH
              WHEN TYPE_NAME([colz].[user_type_id]) IN ('nchar','nvarchar')
              THEN CASE
                   WHEN  [colz].[max_length] = -1
                   THEN '(max)'
                        + SPACE(1)
                        + CASE WHEN [colz].collation_name IS NULL OR [colz].collation_name = 'SQL_Latin1_General_CP1_CI_AS' THEN '' ELSE ' COLLATE ' + [colz].collation_name END
                        + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                        + CASE WHEN [colz].[is_nullable] = 0 THEN  ' NOT NULL' ELSE ' NULL' END
                   ELSE '('
                        + CONVERT(VARCHAR,([colz].[max_length] / 2))
                        + ') '
                        + SPACE(1)
                        + CASE WHEN [colz].collation_name IS NULL OR [colz].collation_name = 'SQL_Latin1_General_CP1_CI_AS' THEN '' ELSE ' COLLATE ' + [colz].collation_name END
                        + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                        + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
                   END
              WHEN TYPE_NAME([colz].[user_type_id]) IN ('datetime','money','text','image','real')
              THEN '              '
                 + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                 + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
              --  OTHER DATA TYPE: INT, DATETIME, MONEY, CUSTOM DATA TYPE
              ELSE CASE

                        WHEN COLUMNPROPERTY ( [colz].[object_id] , [colz].[name] , 'IsIdentity' ) = 0
                        THEN '              '
                        ELSE ' IDENTITY'
                           + CASE
                             WHEN ISNULL(IDENT_SEED([objz].[name]),1) = 1 AND ISNULL(IDENT_INCR([objz].[name]),1) = 1 THEN ''
                             ELSE '(' + CONVERT(VARCHAR,ISNULL(IDENT_SEED([objz].[name]),1) ) + ',' + CONVERT(VARCHAR,ISNULL(IDENT_INCR([objz].[name]),1) ) + ')'
                             END

                        END
                   + SPACE(2)
                   + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                   + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
           END
           + CASE
                WHEN [colz].[default_object_id] = 0
                THEN ''
                --ELSE ' DEFAULT '  + ISNULL(def.[definition] ,'')
                --optional section in case NAMED default constraints are needed:
                ELSE ISNULL('  CONSTRAINT ' + QUOTENAME([DEF].[name]), '') + ' DEFAULT ' + ISNULL([DEF].[definition] ,'')
                       --i thought it needed to be handled differently! NOT!
           END  --CASE cdefault
       END --iscomputed
   FROM
       [sys].[objects] AS [objz]
       JOIN [sys].[columns] AS [colz] ON [objz].[object_id] = [colz].[object_id]
       LEFT OUTER JOIN [sys].[default_constraints]  AS [DEF] ON [colz].[default_object_id] = [DEF].[object_id]
       LEFT OUTER JOIN [sys].[computed_columns] AS [CALC] ON  [colz].[object_id] = [CALC].[object_id] AND [colz].[column_id] = [CALC].[column_id]
   WHERE
       [objz].[type]  IN ('S','U')
       AND [objz].[name] <>  'dtproperties'
       AND [objz].[schema_id] = @SCHEMA_ID
       AND ( @TABLE_ID = 0 OR [objz].[object_id] = @TABLE_ID )
   ORDER BY [objz].[object_id], [colz].[column_id];
--##############################################################################
--PARTITIONING INFO
--##############################################################################
DECLARE @Partitioning TABLE (
   [SchemaName]           NVARCHAR(128)                                                                 NULL,
   [ObjectNameName]       NVARCHAR(128)                                                                 NULL,
   [object_id]            INT                                                                       NOT NULL,
   [ColumnName]           SYSNAME                                                                       NULL,
   [column_id]            INT                                                                       NOT NULL,
   [IndexName]            SYSNAME                                                                       NULL,
   [PartitionSchemeName]  SYSNAME                                                                   NOT NULL,
   [index_id]             INT                                                                       NOT NULL,
   [type_desc]            NVARCHAR(60)                                                                  NULL,
   [PartitionStatement]   NVARCHAR(max)                                                                 NULL)
   ;WITH t
AS
(
SELECT
    object_schema_name([i].[object_id]) AS [SchemaName],
    object_name([i].[object_id]) AS [ObjectNameName],
    [i].[object_id],
    [c].[name] As ColumnName,
    [c].[column_id],
    [i].[name] AS IndexName,
    [ps].[name] AS PartitionSchemeName,
    [i].[index_id],
    [i].[type_desc]
FROM [sys].[dm_db_partition_stats] AS [pstats]
    INNER JOIN [sys].[partitions] AS [p] ON [pstats].[partition_id] = [p].[partition_id]
    INNER JOIN [sys].[destination_data_spaces] AS [dds] ON [pstats].[partition_number] = [dds].[destination_id]
    INNER JOIN [sys].[data_spaces] AS [ds] ON [dds].[data_space_id] = [ds].[data_space_id]
    INNER JOIN [sys].[partition_schemes] AS [ps] ON [dds].[partition_scheme_id] = [ps].[data_space_id]
    INNER JOIN [sys].[partition_functions] AS [pf] ON [ps].[function_id] = [pf].[function_id]
    INNER JOIN [sys].[indexes] AS [i] ON [pstats].[object_id] = [i].[object_id] AND [pstats].[index_id] = [i].[index_id] AND [dds].[partition_scheme_id] = [i].[data_space_id] AND [i].[type] <= 1 /* Heap or Clustered Index */
    INNER JOIN [sys].[index_columns] AS [ic] ON [i].[index_id] = [ic].[index_id] AND [i].[object_id] = [ic].[object_id] AND [ic].[partition_ordinal] > 0
    INNER JOIN [sys].[columns] AS [c] ON [pstats].[object_id] = [c].[object_id] AND [ic].[column_id] = [c].[column_id]
WHERE object_schema_name([i].[object_id]) = @SCHEMA_NAME AND ( @TABLE_ID = 0 OR i.[object_id] = @TABLE_ID )
GROUP BY [i].[object_id],[c].[name],[c].[column_id],[i].[name], [ps].[name],[i].[index_id],[i].[type_desc]
)
INSERT INTO @Partitioning
    SELECT DISTINCT t.* , ' ON ' + t.PartitionSchemeName + '(' + sq.Colzs + ')'  AS PartitionStatement
FROM t
    CROSS APPLY (
        SELECT s.Colzs
        FROM (
            SELECT
               Colzs = STUFF( (SELECT ',' +ColumnName FROM [t] ORDER BY column_id FOR XML PATH(''), TYPE).value('.','varchar(max)'),1,1,'')
            ) s
        ) sq;

INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
    SELECT 4, fn.[object_id], fn.[PartitionStatement]
FROM @Partitioning fn
WHERE fn.[index_id] <=0;
--##############################################################################
--PK/UNIQUE CONSTRAINTS AND INDEXES, USING THE 2005/08 INCLUDE SYNTAX
--##############################################################################
DECLARE @PrimaryUniqueKeys  TABLE (
        [SCHEMA_ID]             INT,
        [SCHEMA_NAME]           VARCHAR(255),
        [OBJECT_ID]             INT,
        [OBJECT_NAME]           VARCHAR(255),
        [index_id]              INT,
        [index_name]            VARCHAR(255),
        [ROWS]                  BIGINT,
        [SizeMB]                DECIMAL(19,3),
        [IndexDepth]            INT,
        [TYPE]                  INT,
        [type_desc]             VARCHAR(30),
        [fill_factor]           INT,
        [is_unique]             INT,
        [is_primary_key]        INT ,
        [is_unique_constraint]  INT,
        [index_columns_key]     VARCHAR(MAX),
        [index_columns_include] VARCHAR(MAX),
        [has_filter]            BIT ,
        [filter_definition]     VARCHAR(MAX),
        [currentFilegroupName]  VARCHAR(128),
        [CurrentCompression]    VARCHAR(128));
INSERT INTO @PrimaryUniqueKeys
    SELECT
        [SCH].[schema_id], [SCH].[name] AS [SCHEMA_NAME],
        [objz].[object_id], [objz].[name] AS [OBJECT_NAME],
        [IDX].[index_id], ISNULL([IDX].[name], '---') AS [index_name],
        [partitions].[ROWS], [partitions].[SizeMB], INDEXPROPERTY([objz].[object_id], [IDX].[name], 'IndexDepth') AS [IndexDepth],
        [IDX].[type], [IDX].[type_desc], [IDX].[fill_factor],
        [IDX].[is_unique], [IDX].[is_primary_key], [IDX].[is_unique_constraint],
        ISNULL([Index_Columns].[index_columns_key], '---') AS [index_columns_key],
        ISNULL([Index_Columns].[index_columns_include], '---') AS [index_columns_include],
        [IDX].[has_filter],
        [IDX].[filter_definition],
        [filz].[name],
        ISNULL([p].[data_compression_desc],'')
    FROM [sys].[objects] AS [objz]
        INNER JOIN [sys].[schemas] AS [SCH] ON [objz].[schema_id]=[SCH].[schema_id]
        INNER JOIN [sys].[indexes] AS [IDX] ON [objz].[object_id]=[IDX].[object_id]
        INNER JOIN [sys].[filegroups] AS [filz] ON [IDX].[data_space_id] = [filz].[data_space_id]
        INNER JOIN [sys].[partitions] AS [p]     ON  [IDX].[object_id] =  [p].[object_id]  AND [IDX].[index_id] = [p].[index_id]
        INNER JOIN (
             SELECT
                 [statz].[object_id], [statz].[index_id], SUM([statz].[row_count]) AS [ROWS],
                 CONVERT(NUMERIC(19,3), CONVERT(NUMERIC(19,3), SUM([statz].[in_row_reserved_page_count]+[statz].[lob_reserved_page_count]+[statz].[row_overflow_reserved_page_count]))/CONVERT(NUMERIC(19,3), 128)) AS [SizeMB]
             FROM [sys].[dm_db_partition_stats] AS [statz]
             GROUP BY [statz].[object_id], [statz].[index_id]
             ) AS [partitions]
        ON  [IDX].[object_id]=[partitions].[object_id]
        AND [IDX].[index_id]=[partitions].[index_id]
    CROSS APPLY (
        SELECT
            LEFT([Index_Columns].[index_columns_key], LEN([Index_Columns].[index_columns_key])-1) AS [index_columns_key],
            LEFT([Index_Columns].[index_columns_include], LEN([Index_Columns].[index_columns_include])-1) AS [index_columns_include]
                 FROM (
                       SELECT (
                              SELECT QUOTENAME([colz].[name]) + CASE WHEN [IXCOLS].[is_descending_key] = 0 THEN ' asc' ELSE ' desc' END + ',' + ' '
                              FROM [sys].[index_columns] AS [IXCOLS]
                              INNER JOIN [sys].[columns] AS [colz]
                                   ON  [IXCOLS].[column_id]   = [colz].[column_id]
                                   AND [IXCOLS].[object_id] = [colz].[object_id]
                              WHERE [IXCOLS].[is_included_column] = 0
                                   AND [IDX].[object_id] = [IXCOLS].[object_id]
                                   AND [IDX].[index_id] = [IXCOLS].[index_id]
                              ORDER BY [IXCOLS].[key_ordinal]
                              FOR XML PATH('')
                              ) AS [index_columns_key],
                             (
                              SELECT QUOTENAME([colz].[name]) + ',' + ' '
                              FROM [sys].[index_columns] AS [IXCOLS]
                              INNER JOIN [sys].[columns] AS [colz]
                                  ON  [IXCOLS].[column_id]   = [colz].[column_id]
                                  AND [IXCOLS].[object_id] = [colz].[object_id]
                              WHERE [IXCOLS].[is_included_column] = 1
                                  AND [IDX].[object_id] = [IXCOLS].[object_id]
                                  AND [IDX].[index_id] = [IXCOLS].[index_id]
                              ORDER BY [IXCOLS].[index_column_id]
                              FOR XML PATH('')
                             ) AS [index_columns_include]
                      ) AS [Index_Columns]
                ) AS [Index_Columns]
        WHERE [SCH].[name] = @SCHEMA_NAME
        AND ( @TABLE_ID = 0 OR [objz].[object_id] = @TABLE_ID )
    ORDER BY
      [SCH].[name],
      [objz].[name],
      [IDX].[name];
--##############################################################################
--CONSTRAINTS
--COLUMN STORE INDEXES ARE DIFFERENT: THE "INCLUDE" COLUMNS FOR NORMAL INDEXES AS SCRIPTED ABOVE ARE THE COLUMNSTORES INDEXED COLUMNS
--ADD A CASE FOR THAT SITUATION.
--##############################################################################
INSERT INTO
    @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
SELECT 3, rs.[OBJECT_ID],
    CASE
       WHEN rs.[is_primary_key] = 1 OR rs.[is_unique] = 1
       THEN
            'CONSTRAINT   '  COLLATE SQL_Latin1_General_CP1_CI_AS + QUOTENAME(rs.[index_name]) + ' '
            + CASE
                 WHEN rs.[is_primary_key] = 1
                 THEN ' PRIMARY KEY '
                 ELSE CASE WHEN rs.[is_unique] = 1 THEN ' UNIQUE      ' ELSE '' END
                 END
            + rs.[type_desc]
            + CASE WHEN rs.[type_desc]='NONCLUSTERED' THEN '' ELSE '   ' END
            + ' (' + rs.[index_columns_key] + ')'
            + CASE WHEN rs.[index_columns_include] <> '---' THEN ' INCLUDE (' + rs.[index_columns_include] + ')' ELSE '' END
            + CASE WHEN rs.[has_filter] = 1 THEN ' WHERE ' + rs.[filter_definition] ELSE ' ' END
            + CASE WHEN rs.[fill_factor] <> 0 OR rs.[CurrentCompression] <> 'NONE'
            THEN ' WITH ('
                 + CASE WHEN rs.[fill_factor] <> 0 THEN 'FILLFACTOR = ' + CONVERT(VARCHAR(30),rs.[fill_factor]) ELSE '' END
                 + CASE
                     WHEN rs.[fill_factor] <> 0  AND rs.[CurrentCompression] <> 'NONE' THEN ',DATA_COMPRESSION = ' + rs.[CurrentCompression] + ' '
                     WHEN rs.[fill_factor] <> 0  AND rs.[CurrentCompression]  = 'NONE' THEN ''
                     WHEN rs.[fill_factor]  = 0  AND rs.[CurrentCompression] <> 'NONE' THEN 'DATA_COMPRESSION = ' + rs.[CurrentCompression] + ' '
                     ELSE ''
                 END
                + ')'
             ELSE ''
             END
        ELSE ''
    END
    + ISNULL(fn.[PartitionStatement],'')
FROM @PrimaryUniqueKeys rs
    LEFT JOIN @Partitioning fn ON rs.[OBJECT_ID] = fn.[object_id] AND rs.[index_id] = fn.[index_id]
WHERE rs.[type_desc] != 'HEAP'
   AND rs.[is_primary_key] = 1 OR ( rs.[is_unique] = 1 AND rs.[is_unique_constraint] = 1 )
ORDER BY
    [is_primary_key] DESC,
    [is_unique] DESC;
--##############################################################################
--CHECK CONSTRAINTS
--##############################################################################
INSERT INTO @DDLScript ( TYPE, OBJECT_ID,  LINE_TEXT )
SELECT 3, [objz].[parent_object_id],
    'CONSTRAINT   ' + QUOTENAME([objz].[name]) + ' '
    + ' CHECK ' + ISNULL([CHECKS].[definition],'')
FROM [sys].[objects] AS [objz]
    INNER JOIN [sys].[check_constraints] AS [CHECKS] ON [objz].[object_id] = [CHECKS].[object_id]
WHERE [objz].[type] = 'C'
   AND [objz].[schema_id] = @SCHEMA_ID
   AND ( @TABLE_ID = 0 OR [objz].[parent_object_id] = @TABLE_ID );
--##############################################################################
--FOREIGN KEYS
--##############################################################################
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
SELECT
    3, [OBJECT_ID], [Command]
    FROM
(
SELECT
    DISTINCT
    [conz].[parent_object_id] AS OBJECT_ID,
    --FK must be added AFTER the PK/unique constraints are added back.
    850 AS [ExecutionOrder],
    'CONSTRAINT '
    + QUOTENAME([conz].[name])
    + ' FOREIGN KEY ('
    + [ChildCollection].[ChildColumns]
    + ') REFERENCES '
    + QUOTENAME(OBJECT_SCHEMA_NAME([conz].[referenced_object_id]))
    + '.'
    + QUOTENAME(OBJECT_NAME([conz].[referenced_object_id]))
    + ' (' + [ParentCollection].[ParentColumns]
    + ') '
    +  CASE [conz].[update_referential_action]
        WHEN 0 THEN '' --' ON UPDATE NO ACTION '
        WHEN 1 THEN ' ON UPDATE CASCADE '
        WHEN 2 THEN ' ON UPDATE SET NULL '
        ELSE ' ON UPDATE SET DEFAULT '
    END
    + CASE [conz].[delete_referential_action]
        WHEN 0 THEN '' --' ON DELETE NO ACTION '
        WHEN 1 THEN ' ON DELETE CASCADE '
        WHEN 2 THEN ' ON DELETE SET NULL '
        ELSE ' ON DELETE SET DEFAULT '
    END
    + CASE [conz].[is_not_for_replication]
        WHEN 1 THEN ' NOT FOR REPLICATION '
        ELSE ''
    END
    AS [Command]
FROM [sys].[foreign_keys] AS [conz]
INNER JOIN [sys].[foreign_key_columns] AS [colz] ON [conz].[object_id] = [colz].[constraint_object_id]
INNER JOIN (--gets my child tables column names
    SELECT
       [conz].[schema_id],
       [conz].[name],
       --technically, FK's can contain up to 16 columns, but real life is often a single column. coding here is for all columns
       [ChildColumns] = STUFF((SELECT
         ',' + QUOTENAME([REFZ].[name])
       FROM  [sys].[foreign_key_columns] AS [fkcolz]
       INNER JOIN [sys].[columns] AS [REFZ] ON [fkcolz].[parent_object_id] = [REFZ].[object_id] AND [fkcolz].[parent_column_id] = [REFZ].[column_id]
       WHERE [fkcolz].[parent_object_id] = [conz].[parent_object_id] AND [fkcolz].[constraint_object_id] = [conz].[object_id]
       ORDER BY [fkcolz].[constraint_column_id]
       FOR XML PATH(''), TYPE).[value]('.','varchar(max)'),1,1,'')
    FROM [sys].[foreign_keys] AS [conz]
    INNER JOIN [sys].[foreign_key_columns] AS [colz] ON [conz].[object_id] = [colz].[constraint_object_id]
    WHERE object_schema_name( [conz].[parent_object_id]) = @SCHEMA_NAME AND ( @TABLE_ID = 0 OR [conz].[parent_object_id]= @TABLE_ID )
    GROUP  BY
    [conz].[schema_id],
    [conz].[name],
    [conz].[parent_object_id],--- without GROUP BY multiple rows are returned
    [conz].[object_id]
) AS [ChildCollection]
ON [conz].[schema_id] = [ChildCollection].[schema_id] AND [conz].[name] = [ChildCollection].[name]
INNER JOIN (--gets the parent tables column names for the FK reference
SELECT
    [conz].[schema_id],
    [conz].[name],
    [ParentColumns] = STUFF((SELECT
          ',' +  QUOTENAME([REFZ].[name])
        FROM [sys].[foreign_key_columns] AS [fkcolz]
               INNER JOIN [sys].[columns] AS [REFZ]
                   ON [fkcolz].[referenced_object_id] = [REFZ].[object_id]
                   AND [fkcolz].[referenced_column_id] = [REFZ].[column_id]
        WHERE  [fkcolz].[referenced_object_id] = [conz].[referenced_object_id]
          AND [fkcolz].[constraint_object_id] = [conz].[object_id]
        ORDER BY [fkcolz].[constraint_column_id]
        FOR XML PATH(''), TYPE).[value]('.','varchar(max)'),1,1,'')
    FROM  [sys].[foreign_keys] AS [conz]
      INNER JOIN [sys].[foreign_key_columns] AS [colz]
          ON [conz].[object_id] = [colz].[constraint_object_id]
          -- AND colz.parent_column_id
    GROUP  BY
        [conz].[schema_id],
        [conz].[name],
        [conz].[referenced_object_id],--- without GROUP BY multiple rows are returned
        [conz].[object_id]
    ) AS [ParentCollection]
 ON [conz].[schema_id] = [ParentCollection].[schema_id] AND [conz].[name] = [ParentCollection].[name]
)AS [MyAlias];
--##############################################################################
-- Temporal tables
--##############################################################################
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
SELECT 3, [colz].[object_id],
   'PERIOD FOR SYSTEM_TIME ('
    + MAX(CASE WHEN [colz].[generated_always_type] = 1 THEN [colz].[name] ELSE '' END)
    +','
    + MAX(CASE WHEN [colz].[generated_always_type] = 2 THEN [colz].[name] ELSE '' END)
    +')'
FROM [sys].[tables] [objz]
INNER JOIN [sys].[columns] [colz] ON [objz].[object_id] = [colz].[object_id]
WHERE
    [objz].[schema_id] = @SCHEMA_ID
    AND ( @TABLE_ID = 0  OR [colz].[object_id] = @TABLE_ID )
    AND [colz].[generated_always_type] > 0
GROUP BY [colz].[object_id],[objz].[history_table_id];

INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
SELECT 9, [colz].[object_id],
    ' SYSTEM_VERSIONING = ON (HISTORY_TABLE=' + QUOTENAME(OBJECT_SCHEMA_NAME([objz].[history_table_id])) + '.' + QUOTENAME(OBJECT_NAME([objz].[history_table_id])) + '),'
FROM [sys].[tables] [objz]
INNER JOIN [sys].[columns] [colz] ON [objz].[object_id] = [colz].[object_id]
WHERE
    [objz].[schema_id] = @SCHEMA_ID
    AND ( @TABLE_ID = 0  OR [colz].[object_id] = @TABLE_ID )
    AND [colz].[generated_always_type] > 0
GROUP BY [colz].[object_id],[objz].[history_table_id];
  --##############################################################################
  -- EXTERNAL TABLES
  --##############################################################################
  INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
  SELECT
      9, [etabz].[object_id],
      CASE WHEN [etabz].[location] IS NOT NULL THEN ' LOCATION = ' + [etabz].[location] + ',' END
      + CASE WHEN [etabz].[data_source_id] IS NOT NULL THEN ' DATA_SOURCE = ' + [eds].[name] + ',' END
      + CASE WHEN [etabz].[file_format_id] IS NOT NULL THEN ' FILE_FORMAT = ' + [efs].[name] + ',' END
      + CASE WHEN [etabz].[reject_type] IS NOT NULL THEN ' REJECT_TYPE = ' + [etabz].[reject_type] + ',' END
      + CASE WHEN [etabz].[reject_value] IS NOT NULL THEN ' REJECT_VALUE = ' + [etabz].[reject_value] + ',' END
      + CASE WHEN [etabz].[reject_sample_value] IS NOT NULL THEN ' REJECT_SAMPLE_VALUE = ' + [etabz].[reject_sample_value] + ',' END
      + ' '
  FROM [sys].[external_tables] [etabz]
  LEFT JOIN [sys].[external_data_sources] [eds] ON [eds].[data_source_id] = [etabz].[data_source_id]
  LEFT JOIN [sys].[external_file_formats] [efs] ON [efs].[file_format_id] = [etabz].[file_format_id]
  WHERE
      [etabz].[schema_id] = @SCHEMA_ID
      AND ( @TABLE_ID = 0 OR [etabz].[object_id] = @TABLE_ID );
--##############################################################################
-- MEMORY OPTIMIZED
--##############################################################################
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
SELECT 9, [objz].[object_id],
    'MEMORY_OPTIMIZED=ON, DURABILITY=' + [objz].[durability_desc] + ','
FROM [sys].[tables] [objz]
WHERE
    [objz].[is_memory_optimized] =1
    AND [objz].[schema_id] = @SCHEMA_ID
    AND ( @TABLE_ID = 0 OR [objz].[object_id] = @TABLE_ID )
DELETE FROM @DDLScript WHERE LINE_TEXT = '' OR LINE_TEXT IS NULL;
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT, LINE_SUFFIX )
SELECT 12, [tabz].[object_id], 'GO' + CHAR(10) + CHAR(10), ''
FROM [sys].[tables] [tabz]
WHERE
  [tabz].[schema_id] = @SCHEMA_ID AND ( @TABLE_ID = 0 OR [tabz].[object_id] = @TABLE_ID )
--##############################################################################
--INDEXES
--##############################################################################
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
SELECT 13, [OBJECT_ID],
     CASE
         WHEN [is_primary_key] = 0 OR [is_unique] = 0
         THEN CHAR(10)
              + 'CREATE ' COLLATE SQL_Latin1_General_CP1_CI_AS
              + CASE WHEN [is_unique] = 1 THEN 'UNIQUE ' COLLATE SQL_Latin1_General_CP1_CI_AS ELSE '' END
              + [type_desc]
              + ' INDEX '  COLLATE SQL_Latin1_General_CP1_CI_AS + QUOTENAME([index_name]) + ' '
              + CHAR(10)
              + '   ON '   COLLATE SQL_Latin1_General_CP1_CI_AS
              + QUOTENAME([SCHEMA_NAME]) + '.' + QUOTENAME([OBJECT_NAME])
              + CASE
                    WHEN [CurrentCompression] = 'COLUMNSTORE'  COLLATE SQL_Latin1_General_CP1_CI_AS
                    THEN ' (' + [index_columns_include] + ')'
                    ELSE ' (' + [index_columns_key] + ')'
                END
              + CASE
                  WHEN [CurrentCompression] = 'COLUMNSTORE'  COLLATE SQL_Latin1_General_CP1_CI_AS
                  THEN ''  COLLATE SQL_Latin1_General_CP1_CI_AS
                  ELSE
                    CASE
                 WHEN [index_columns_include] <> '---'
                 THEN CHAR(10) + '   INCLUDE ('  COLLATE SQL_Latin1_General_CP1_CI_AS + [index_columns_include] + ')'   COLLATE SQL_Latin1_General_CP1_CI_AS
                 ELSE ''   COLLATE SQL_Latin1_General_CP1_CI_AS
               END
                END
              --2008 filtered indexes syntax
              + CASE
                  WHEN [has_filter] = 1
                  THEN CHAR(10) + '   WHERE '  COLLATE SQL_Latin1_General_CP1_CI_AS + [filter_definition]
                  ELSE ''
                END
              + CASE WHEN [fill_factor] <> 0 OR [CurrentCompression] <> 'NONE'  COLLATE SQL_Latin1_General_CP1_CI_AS
              THEN ' WITH ('  COLLATE SQL_Latin1_General_CP1_CI_AS + CASE
                        WHEN [fill_factor] <> 0
                        THEN 'FILLFACTOR = '  COLLATE SQL_Latin1_General_CP1_CI_AS + CONVERT(VARCHAR(30),[fill_factor])
                        ELSE ''
                      END
                    + CASE
                        WHEN [fill_factor] <> 0  AND [CurrentCompression] <> 'NONE' THEN ',DATA_COMPRESSION = ' + [CurrentCompression]+' '
                        WHEN [fill_factor] <> 0  AND [CurrentCompression]  = 'NONE' THEN ''
                        WHEN [fill_factor]  = 0  AND [CurrentCompression] <> 'NONE' THEN 'DATA_COMPRESSION = ' + [CurrentCompression]+' '
                        ELSE ''
                      END
                      + ')'
              ELSE ''
              END
               + CHAR(10) + 'GO' + CHAR(10)
       END
FROM @PrimaryUniqueKeys
WHERE [type_desc] != 'HEAP'
    AND [is_primary_key] = 0 AND ( [is_unique] = 0 OR [is_unique_constraint] = 0)
ORDER BY
    [is_primary_key] DESC,
    [is_unique] DESC;
--##############################################################################
--RULES
--##############################################################################
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_SUFFIX, LINE_TEXT )
SELECT 14, [colz].[object_id], CHAR(10) + 'GO' + CHAR(10) + CHAR(10),
         'if not exists(SELECT [name] FROM sys.objects WHERE TYPE=''R'' AND schema_id = ' COLLATE SQL_Latin1_General_CP1_CI_AS
         + CONVERT(VARCHAR(30),[objz].[schema_id]) + ' AND [name] = '''  COLLATE SQL_Latin1_General_CP1_CI_AS
         + QUOTENAME(OBJECT_NAME([colz].[rule_object_id])) + ''')'  COLLATE SQL_Latin1_General_CP1_CI_AS + CHAR(10)
         + [MODS].[definition]  + CHAR(10) + 'GO' COLLATE SQL_Latin1_General_CP1_CI_AS + CHAR(10)
         + 'EXEC sp_binderule  ' + QUOTENAME([objz].[name]) + ', ''' + QUOTENAME(OBJECT_NAME([colz].[object_id])) + '.' + QUOTENAME([colz].[name]) + ''''  COLLATE SQL_Latin1_General_CP1_CI_AS + CHAR(10) + 'GO'  COLLATE SQL_Latin1_General_CP1_CI_AS
FROM [sys].[columns] [colz]
    INNER JOIN [sys].[objects] [objz] ON [objz].[object_id] = [colz].[object_id]
    INNER JOIN [sys].[sql_modules] AS [MODS] ON [colz].[rule_object_id] = [MODS].[object_id]
WHERE [colz].[rule_object_id] <> 0
    AND [objz].[schema_id] = @SCHEMA_ID
    AND ( @TABLE_ID = 0 OR [colz].[object_id] = @TABLE_ID ) ;
--##############################################################################
--TRIGGERS
--##############################################################################
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_SUFFIX, LINE_TEXT )
  SELECT
    15, [objz].[parent_object_id], CHAR(10) + 'GO' + CHAR(10) + CHAR(10),
    [MODS].[definition]
  FROM [sys].[sql_modules] AS [MODS]
  JOIN [sys].[objects] AS [objz] ON [MODS].[object_id]=[objz].[parent_object_id]
  WHERE [objz].[type] = 'TR'
        AND [objz].[schema_id] = @SCHEMA_ID
        AND ( @TABLE_ID = 0 OR [objz].[parent_object_id] = @TABLE_ID )
  ;
--##############################################################################
--NEW SECTION QUERY ALL EXTENDED PROPERTIES
--##############################################################################
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
  SELECT  16, [tabz].[object_id],
         'EXEC sys.sp_addextendedproperty
          @name = N'''  COLLATE SQL_Latin1_General_CP1_CI_AS + [fn].[name] + ''', @value = N'''  COLLATE SQL_Latin1_General_CP1_CI_AS + REPLACE(CONVERT(VARCHAR(MAX),[value]),'''','''''') + ''',
          @level0type = N''SCHEMA'', @level0name = '  COLLATE SQL_Latin1_General_CP1_CI_AS + QUOTENAME(@SCHEMA_NAME) + ',
          @level1type = N''TABLE'', @level1name = '  COLLATE SQL_Latin1_General_CP1_CI_AS + QUOTENAME(OBJECT_NAME([tabz].[object_id])) + ';' + CHAR(10) + 'GO' + CHAR(10) + CHAR(10)
 --SELECT objtype, objname, name, value
  FROM [sys].[tables] [tabz]
    OUTER APPLY [sys].[fn_listextendedproperty] (NULL, 'schema', @SCHEMA_NAME, 'table', OBJECT_NAME([tabz].[object_id]), NULL, NULL) AS [fn]
  WHERE
    [fn].[name] IS NOT NULL AND
    [tabz].[schema_id] = @SCHEMA_ID AND ( @TABLE_ID = 0 OR [tabz].[object_id] = @TABLE_ID );
    --OMacoder suggestion for column extended properties http://www.sqlservercentral.com/Forums/FindPost1651606.aspx
   WITH [obj] AS (
	SELECT [split].[a].[value]('.', 'VARCHAR(20)') AS [name]
	FROM (
		SELECT CAST ('<M>' + REPLACE('column,constraint,index,trigger,parameter', ',', '</M><M>') + '</M>' AS XML) AS [data]
		) AS [A]
		CROSS APPLY [data].[nodes] ('/M') AS [split]([a])
	)
	INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
	SELECT  16, [tabz].[object_id],
         'EXEC sys.sp_addextendedproperty
         @name = N''' COLLATE SQL_Latin1_General_CP1_CI_AS
         + [lep].[name]
         + ''', @value = N''' COLLATE SQL_Latin1_General_CP1_CI_AS
         + REPLACE(CONVERT(VARCHAR(MAX),[lep].[value]),'''','''''') + ''',
         @level0type = N''SCHEMA'', @level0name = ' COLLATE SQL_Latin1_General_CP1_CI_AS
         + QUOTENAME(@SCHEMA_NAME)
         + ',
         @level1type = N''TABLE'', @level1name = ' COLLATE SQL_Latin1_General_CP1_CI_AS
         + QUOTENAME(OBJECT_NAME([tabz].[object_id]))
         + ',
         @level2type = N''' COLLATE SQL_Latin1_General_CP1_CI_AS
         + UPPER([obj].[name])
         + ''', @level2name = ' COLLATE SQL_Latin1_General_CP1_CI_AS
         + QUOTENAME([lep].[objname]) + ';' + CHAR(10) + 'GO' + CHAR(10) + CHAR(10)  COLLATE SQL_Latin1_General_CP1_CI_AS
  --SELECT objtype, objname, name, value
  FROM [obj]
    CROSS APPLY [sys].[tables] [tabz]
	CROSS APPLY [sys].[fn_listextendedproperty] (NULL, 'schema', @SCHEMA_NAME, 'table', OBJECT_NAME([tabz].[object_id]), [obj].[name], NULL) AS [lep]
    WHERE
       [lep].[name] IS NOT NULL AND
       [tabz].[schema_id] = @SCHEMA_ID AND ( @TABLE_ID = 0 OR [tabz].[object_id] = @TABLE_ID );
--#########################
-- TEMP TABLES
--#########################
TEMPPROCESS:
  SELECT @TABLE_ID = OBJECT_ID('tempdb..' COLLATE SQL_Latin1_General_CP1_CI_AS + @TABLE_NAME);
 PRINT 'TEMP @TABLE_ID' + CONVERT(VARCHAR,@TABLE_ID)
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
SELECT 02, [tabz].[object_id],
    CASE
        WHEN [tabz].[history_table_id] IS NULL
        THEN ''
        ELSE 'ALTER TABLE ' + QUOTENAME(OBJECT_SCHEMA_NAME([tabz].[object_id]) ) + '.' + QUOTENAME(OBJECT_NAME([tabz].[object_id])) + ' SET (SYSTEM_VERSIONING = OFF);' + CHAR(10)
             +  'IF OBJECT_ID(''' + QUOTENAME(OBJECT_SCHEMA_NAME([tabz].[history_table_id]) ) + '.' + QUOTENAME(OBJECT_NAME([tabz].[history_table_id])) + ''') IS NOT NULL ' + CHAR(10)
             + 'DROP TABLE ' + QUOTENAME(OBJECT_SCHEMA_NAME([tabz].[history_table_id])) + '.' + QUOTENAME(OBJECT_NAME([tabz].[history_table_id])) + ' ' + CHAR(10) + 'GO' + CHAR(10)
        END
    + 'IF OBJECT_ID(''' + QUOTENAME(OBJECT_SCHEMA_NAME([tabz].[object_id]) ) + '.' + QUOTENAME(OBJECT_NAME([tabz].[object_id])) + ''') IS NOT NULL ' + CHAR(10)
    + 'DROP TABLE ' + QUOTENAME(OBJECT_SCHEMA_NAME([tabz].[object_id])) + '.' + QUOTENAME(OBJECT_NAME([tabz].[object_id])) + ' ' + CHAR(10) + 'GO' + CHAR(10)
    + 'CREATE ' + ( CASE WHEN tabz.is_external = 1 THEN 'EXTERNAL ' ELSE '' END ) + 'TABLE ' + QUOTENAME(OBJECT_SCHEMA_NAME([tabz].[object_id])) + '.' + QUOTENAME(OBJECT_NAME([tabz].[object_id])) + ' ( '
FROM [tempdb].[sys].[tables] [tabz]
WHERE
  [tabz].[schema_id] = @SCHEMA_ID AND ( @TABLE_ID = 0 OR [tabz].[object_id] = @TABLE_ID )
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
SELECT 3, [objz].[object_id],
   CASE
       WHEN [colz].[is_computed] = 1
       THEN QUOTENAME([colz].[name])
           + ' '
           + 'AS ' + ISNULL([CALC].[definition],'')
           + CASE WHEN [CALC].[is_persisted] = 1 THEN ' PERSISTED' ELSE '' END
       ELSE QUOTENAME([colz].[name])
           + ' '
           + UPPER(TYPE_NAME([colz].[user_type_id]))
           + CASE
            -- DATA TYPES WITH PRECISION AND SCALE
           WHEN TYPE_NAME([colz].[user_type_id]) IN ('decimal','numeric')
           THEN '('
                + CONVERT(VARCHAR,[colz].[precision])
                + ','
                + CONVERT(VARCHAR,[colz].[scale])
                + ') '
                + CASE

                    WHEN [colz].[is_identity] = 1 THEN ' IDENTITY' ELSE ' '

                   END
                + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
              -- DATA TYPES WITH SCALE
              WHEN TYPE_NAME([colz].[user_type_id]) IN ('datetime2','datetimeoffset','time')
              THEN CASE WHEN [colz].[scale] < 7 THEN '(' + CONVERT(VARCHAR,[colz].[scale]) + ') ' ELSE '    ' END
                 + ' '
                 + CASE  WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                 + CASE [colz].[generated_always_type]
                     WHEN 0 THEN ''
                     WHEN 1 THEN ' GENERATED ALWAYS AS ROW START'
                     WHEN 2 THEN ' GENERATED ALWAYS AS ROW END'
                     ELSE ''
                   END
                 + CASE WHEN [colz].[is_hidden] = 1 THEN ' HIDDEN' ELSE '' END
                 + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
              -- DATA TYPES WITH NO/PRECISION/SCALE
              WHEN  TYPE_NAME([colz].[user_type_id]) IN ('float') --,'real')
              THEN
                   CASE
                   -- 53 IS DEFAULT PRECISION
                   WHEN [colz].[precision] = 53
                   THEN
                       + SPACE(1)
                       + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                       + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
                   ELSE '('
                       + CONVERT(VARCHAR,[colz].[precision])
                       + ') '
                       + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                       + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
                   END
              -- DATA TYPE WITH MAX_LENGTH
              WHEN  TYPE_NAME([colz].[user_type_id]) IN ('char','varchar','binary','varbinary')
              THEN CASE
                   WHEN  [colz].[max_length] = -1
                   THEN  '(max)'
                        + CASE WHEN [colz].collation_name IS NULL OR [colz].collation_name = 'SQL_Latin1_General_CP1_CI_AS' THEN '' ELSE ' COLLATE ' + [colz].collation_name END
                        + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                        + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
                   ELSE '('
                        + CONVERT(VARCHAR,[colz].[max_length])
                        + ') '
                        -- COLLATE
                        + CASE WHEN [colz].collation_name IS NULL OR [colz].collation_name = 'SQL_Latin1_General_CP1_CI_AS' THEN '' ELSE ' COLLATE ' + [colz].collation_name END
                        + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                        + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
                   END
              -- DATA TYPE WITH MAX_LENGTH
              WHEN TYPE_NAME([colz].[user_type_id]) IN ('nchar','nvarchar')
              THEN CASE
                   WHEN  [colz].[max_length] = -1
                   THEN '(max)'
                        + SPACE(1)
                        + CASE WHEN [colz].collation_name IS NULL OR [colz].collation_name = 'SQL_Latin1_General_CP1_CI_AS' THEN '' ELSE ' COLLATE ' + [colz].collation_name END
                        + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                        + CASE WHEN [colz].[is_nullable] = 0 THEN  ' NOT NULL' ELSE ' NULL' END
                   ELSE '('
                        + CONVERT(VARCHAR,([colz].[max_length] / 2))
                        + ') '
                        + SPACE(1)
                        + CASE WHEN [colz].collation_name IS NULL OR [colz].collation_name = 'SQL_Latin1_General_CP1_CI_AS' THEN '' ELSE ' COLLATE ' + [colz].collation_name END
                        + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                        + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
                   END
              WHEN TYPE_NAME([colz].[user_type_id]) IN ('datetime','money','text','image','real')
              THEN '              '
                 + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                 + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
              --  OTHER DATA TYPE: INT, DATETIME, MONEY, CUSTOM DATA TYPE
              ELSE CASE

                        WHEN [colz].[is_identity] = 1 THEN ' IDENTITY' ELSE ' '

                        END
                   + SPACE(2)
                   + CASE WHEN [colz].[is_sparse] = 1 THEN ' sparse' ELSE ' ' END
                   + CASE WHEN [colz].[is_nullable] = 0 THEN ' NOT NULL' ELSE ' NULL' END
           END
           + CASE
                WHEN [colz].[default_object_id] = 0
                THEN ''
                --ELSE ' DEFAULT '  + ISNULL(def.[definition] ,'')
                --optional section in case NAMED default constraints are needed:
                ELSE ISNULL('  CONSTRAINT ' + QUOTENAME([DEF].[name]), '') + ' DEFAULT ' + ISNULL([DEF].[definition] ,'')
                       --i thought it needed to be handled differently! NOT!
           END  --CASE cdefault
       END --iscomputed
   FROM
       [sys].[objects] AS [objz]
       JOIN [tempdb].[sys].[columns] AS [colz] ON [objz].[object_id] = [colz].[object_id]
       LEFT OUTER JOIN [tempdb].[sys].[default_constraints]  AS [DEF] ON [colz].[default_object_id] = [DEF].[object_id]
       LEFT OUTER JOIN [tempdb].[sys].[computed_columns] AS [CALC] ON  [colz].[object_id] = [CALC].[object_id] AND [colz].[column_id] = [CALC].[column_id]
   WHERE
       [objz].[type]  IN ('S','U')
       AND [objz].[name] <>  'dtproperties'
       AND [objz].[schema_id] = @SCHEMA_ID
       AND ( @TABLE_ID = 0 OR [objz].[object_id] = @TABLE_ID )
   ORDER BY [objz].[object_id], [colz].[column_id];
--##############################################################################
--PK/UNIQUE CONSTRAINTS AND INDEXES, USING THE 2005/08 INCLUDE SYNTAX
--##############################################################################
DECLARE @PrimaryUniqueKeysTmp  TABLE (
        [SCHEMA_ID]             INT,
        [SCHEMA_NAME]           VARCHAR(255),
        [OBJECT_ID]             INT,
        [OBJECT_NAME]           VARCHAR(255),
        [index_id]              INT,
        [index_name]            VARCHAR(255),
        [ROWS]                  BIGINT,
        [SizeMB]                DECIMAL(19,3),
        [IndexDepth]            INT,
        [TYPE]                  INT,
        [type_desc]             VARCHAR(30),
        [fill_factor]           INT,
        [is_unique]             INT,
        [is_primary_key]        INT ,
        [is_unique_constraint]  INT,
        [index_columns_key]     VARCHAR(MAX),
        [index_columns_include] VARCHAR(MAX),
        [has_filter]            BIT ,
        [filter_definition]     VARCHAR(MAX),
        [currentFilegroupName]  VARCHAR(128),
        [CurrentCompression]    VARCHAR(128));
INSERT INTO @PrimaryUniqueKeysTmp
    SELECT
        [SCH].[schema_id], [SCH].[name] AS [SCHEMA_NAME],
        [objz].[object_id], [objz].[name] AS [OBJECT_NAME],
        [IDX].[index_id], ISNULL([IDX].[name], '---') AS [index_name],
        [partitions].[ROWS], [partitions].[SizeMB], INDEXPROPERTY([objz].[object_id], [IDX].[name], 'IndexDepth') AS [IndexDepth],
        [IDX].[type], [IDX].[type_desc], [IDX].[fill_factor],
        [IDX].[is_unique], [IDX].[is_primary_key], [IDX].[is_unique_constraint],
        ISNULL([Index_Columns].[index_columns_key], '---') AS [index_columns_key],
        ISNULL([Index_Columns].[index_columns_include], '---') AS [index_columns_include],
        [IDX].[has_filter],
        [IDX].[filter_definition],
        [filz].[name],
        ISNULL([p].[data_compression_desc],'')
    FROM [sys].[objects] AS [objz]
        INNER JOIN [tempdb].[sys].[schemas] AS [SCH] ON [objz].[schema_id]=[SCH].[schema_id]
        INNER JOIN [tempdb].[sys].[indexes] AS [IDX] ON [objz].[object_id]=[IDX].[object_id]
        INNER JOIN [tempdb].[sys].[filegroups] AS [filz] ON [IDX].[data_space_id] = [filz].[data_space_id]
        INNER JOIN [tempdb].[sys].[partitions] AS [p]     ON  [IDX].[object_id] =  [p].[object_id]  AND [IDX].[index_id] = [p].[index_id]
        INNER JOIN (
             SELECT
                 [statz].[object_id], [statz].[index_id], SUM([statz].[row_count]) AS [ROWS],
                 CONVERT(NUMERIC(19,3), CONVERT(NUMERIC(19,3), SUM([statz].[in_row_reserved_page_count]+[statz].[lob_reserved_page_count]+[statz].[row_overflow_reserved_page_count]))/CONVERT(NUMERIC(19,3), 128)) AS [SizeMB]
             FROM [tempdb].[sys].[dm_db_partition_stats] AS [statz]
             GROUP BY [statz].[object_id], [statz].[index_id]
             ) AS [partitions]
        ON  [IDX].[object_id]=[partitions].[object_id]
        AND [IDX].[index_id]=[partitions].[index_id]
    CROSS APPLY (
        SELECT
            LEFT([Index_Columns].[index_columns_key], LEN([Index_Columns].[index_columns_key])-1) AS [index_columns_key],
            LEFT([Index_Columns].[index_columns_include], LEN([Index_Columns].[index_columns_include])-1) AS [index_columns_include]
                 FROM (
                       SELECT (
                              SELECT QUOTENAME([colz].[name]) + CASE WHEN [IXCOLS].[is_descending_key] = 0 THEN ' asc' ELSE ' desc' END + ',' + ' '
                              FROM [tempdb].[sys].[index_columns] AS [IXCOLS]
                              INNER JOIN [tempdb].[sys].[columns] AS [colz]
                                   ON  [IXCOLS].[column_id]   = [colz].[column_id]
                                   AND [IXCOLS].[object_id] = [colz].[object_id]
                              WHERE [IXCOLS].[is_included_column] = 0
                                   AND [IDX].[object_id] = [IXCOLS].[object_id]
                                   AND [IDX].[index_id] = [IXCOLS].[index_id]
                              ORDER BY [IXCOLS].[key_ordinal]
                              FOR XML PATH('')
                              ) AS [index_columns_key],
                             (
                              SELECT QUOTENAME([colz].[name]) + ',' + ' '
                              FROM [tempdb].[sys].[index_columns] AS [IXCOLS]
                              INNER JOIN [tempdb].[sys].[columns] AS [colz]
                                  ON  [IXCOLS].[column_id]   = [colz].[column_id]
                                  AND [IXCOLS].[object_id] = [colz].[object_id]
                              WHERE [IXCOLS].[is_included_column] = 1
                                  AND [IDX].[object_id] = [IXCOLS].[object_id]
                                  AND [IDX].[index_id] = [IXCOLS].[index_id]
                              ORDER BY [IXCOLS].[index_column_id]
                              FOR XML PATH('')
                             ) AS [index_columns_include]
                      ) AS [Index_Columns]
                ) AS [Index_Columns]
        WHERE [SCH].[name] = @SCHEMA_NAME
        AND ( @TABLE_ID = 0 OR [objz].[object_id] = @TABLE_ID )
    ORDER BY
      [SCH].[name],
      [objz].[name],
      [IDX].[name];
--##############################################################################
--CONSTRAINTS
--COLUMN STORE INDEXES ARE DIFFERENT: THE "INCLUDE" COLUMNS FOR NORMAL INDEXES AS SCRIPTED ABOVE ARE THE COLUMNSTORES INDEXED COLUMNS
--ADD A CASE FOR THAT SITUATION.
--##############################################################################
INSERT INTO
    @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
SELECT 3, rs.[OBJECT_ID],
    CASE
       WHEN rs.[is_primary_key] = 1 OR rs.[is_unique] = 1
       THEN
            'CONSTRAINT   '  COLLATE SQL_Latin1_General_CP1_CI_AS + QUOTENAME(rs.[index_name]) + ' '
            + CASE
                 WHEN rs.[is_primary_key] = 1
                 THEN ' PRIMARY KEY '
                 ELSE CASE WHEN rs.[is_unique] = 1 THEN ' UNIQUE      ' ELSE '' END
                 END
            + rs.[type_desc]
            + CASE WHEN rs.[type_desc]='NONCLUSTERED' THEN '' ELSE '   ' END
            + ' (' + rs.[index_columns_key] + ')'
            + CASE WHEN rs.[index_columns_include] <> '---' THEN ' INCLUDE (' + rs.[index_columns_include] + ')' ELSE '' END
            + CASE WHEN rs.[has_filter] = 1 THEN ' WHERE ' + rs.[filter_definition] ELSE ' ' END
            + CASE WHEN rs.[fill_factor] <> 0 OR rs.[CurrentCompression] <> 'NONE'
            THEN ' WITH ('
                 + CASE WHEN rs.[fill_factor] <> 0 THEN 'FILLFACTOR = ' + CONVERT(VARCHAR(30),rs.[fill_factor]) ELSE '' END
                 + CASE
                     WHEN rs.[fill_factor] <> 0  AND rs.[CurrentCompression] <> 'NONE' THEN ',DATA_COMPRESSION = ' + rs.[CurrentCompression] + ' '
                     WHEN rs.[fill_factor] <> 0  AND rs.[CurrentCompression]  = 'NONE' THEN ''
                     WHEN rs.[fill_factor]  = 0  AND rs.[CurrentCompression] <> 'NONE' THEN 'DATA_COMPRESSION = ' + rs.[CurrentCompression] + ' '
                     ELSE ''
                 END
                + ')'
             ELSE ''
             END
        ELSE ''
    END
    + ISNULL(fn.[PartitionStatement],'')
FROM @PrimaryUniqueKeysTmp rs
    LEFT JOIN @Partitioning fn ON rs.[OBJECT_ID] = fn.[object_id] AND rs.[index_id] = fn.[index_id]
WHERE rs.[type_desc] != 'HEAP'
   AND rs.[is_primary_key] = 1 OR ( rs.[is_unique] = 1 AND rs.[is_unique_constraint] = 1 )
ORDER BY
    [is_primary_key] DESC,
    [is_unique] DESC;
--##############################################################################
--CHECK CONSTRAINTS
--##############################################################################
INSERT INTO @DDLScript ( TYPE, OBJECT_ID,  LINE_TEXT )
SELECT 3, [objz].[parent_object_id],
    'CONSTRAINT   ' + QUOTENAME([objz].[name]) + ' '
    + ' CHECK ' + ISNULL([CHECKS].[definition],'')
FROM [tempdb].[sys].[objects] AS [objz]
    INNER JOIN [tempdb].[sys].[check_constraints] AS [CHECKS] ON [objz].[object_id] = [CHECKS].[object_id]
WHERE [objz].[type] = 'C'
   AND [objz].[schema_id] = @SCHEMA_ID
   AND ( @TABLE_ID = 0 OR [objz].[parent_object_id] = @TABLE_ID );
--##############################################################################
--FOREIGN KEYS
--##############################################################################
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
SELECT
    3, [OBJECT_ID], [Command]
    FROM
(
SELECT
    DISTINCT
    [conz].[parent_object_id] AS OBJECT_ID,
    --FK must be added AFTER the PK/unique constraints are added back.
    850 AS [ExecutionOrder],
    'CONSTRAINT '
    + QUOTENAME([conz].[name])
    + ' FOREIGN KEY ('
    + [ChildCollection].[ChildColumns]
    + ') REFERENCES '
    + QUOTENAME(OBJECT_SCHEMA_NAME([conz].[referenced_object_id]))
    + '.'
    + QUOTENAME(OBJECT_NAME([conz].[referenced_object_id]))
    + ' (' + [ParentCollection].[ParentColumns]
    + ') '
    +  CASE [conz].[update_referential_action]
        WHEN 0 THEN '' --' ON UPDATE NO ACTION '
        WHEN 1 THEN ' ON UPDATE CASCADE '
        WHEN 2 THEN ' ON UPDATE SET NULL '
        ELSE ' ON UPDATE SET DEFAULT '
    END
    + CASE [conz].[delete_referential_action]
        WHEN 0 THEN '' --' ON DELETE NO ACTION '
        WHEN 1 THEN ' ON DELETE CASCADE '
        WHEN 2 THEN ' ON DELETE SET NULL '
        ELSE ' ON DELETE SET DEFAULT '
    END
    + CASE [conz].[is_not_for_replication]
        WHEN 1 THEN ' NOT FOR REPLICATION '
        ELSE ''
    END
    AS [Command]
FROM [tempdb].[sys].[foreign_keys] AS [conz]
INNER JOIN [tempdb].[sys].[foreign_key_columns] AS [colz] ON [conz].[object_id] = [colz].[constraint_object_id]
INNER JOIN (--gets my child tables column names
    SELECT
       [conz].[schema_id],
       [conz].[name],
       --technically, FK's can contain up to 16 columns, but real life is often a single column. coding here is for all columns
       [ChildColumns] = STUFF((SELECT
         ',' + QUOTENAME([REFZ].[name])
       FROM  [tempdb].[sys].[foreign_key_columns] AS [fkcolz]
       INNER JOIN [tempdb].[sys].[columns] AS [REFZ] ON [fkcolz].[parent_object_id] = [REFZ].[object_id] AND [fkcolz].[parent_column_id] = [REFZ].[column_id]
       WHERE [fkcolz].[parent_object_id] = [conz].[parent_object_id] AND [fkcolz].[constraint_object_id] = [conz].[object_id]
       ORDER BY [fkcolz].[constraint_column_id]
       FOR XML PATH(''), TYPE).[value]('.','varchar(max)'),1,1,'')
    FROM [tempdb].[sys].[foreign_keys] AS [conz]
    INNER JOIN [tempdb].[sys].[foreign_key_columns] AS [colz] ON [conz].[object_id] = [colz].[constraint_object_id]
    WHERE object_schema_name( [conz].[parent_object_id]) = @SCHEMA_NAME AND ( @TABLE_ID = 0 OR [conz].[parent_object_id]= @TABLE_ID )
    GROUP  BY
    [conz].[schema_id],
    [conz].[name],
    [conz].[parent_object_id],--- without GROUP BY multiple rows are returned
    [conz].[object_id]
) AS [ChildCollection]
ON [conz].[schema_id] = [ChildCollection].[schema_id] AND [conz].[name] = [ChildCollection].[name]
INNER JOIN (--gets the parent tables column names for the FK reference
SELECT
    [conz].[schema_id],
    [conz].[name],
    [ParentColumns] = STUFF((SELECT
          ',' + [REFZ].[name]
        FROM [tempdb].[sys].[foreign_key_columns] AS [fkcolz]
               INNER JOIN [tempdb].[sys].[columns] AS [REFZ]
                   ON [fkcolz].[referenced_object_id] = [REFZ].[object_id]
                   AND [fkcolz].[referenced_column_id] = [REFZ].[column_id]
        WHERE  [fkcolz].[referenced_object_id] = [conz].[referenced_object_id]
          AND [fkcolz].[constraint_object_id] = [conz].[object_id]
        ORDER BY [fkcolz].[constraint_column_id]
        FOR XML PATH(''), TYPE).[value]('.','varchar(max)'),1,1,'')
    FROM  [tempdb].[sys].[foreign_keys] AS [conz]
      INNER JOIN [tempdb].[sys].[foreign_key_columns] AS [colz]
          ON [conz].[object_id] = [colz].[constraint_object_id]
          -- AND colz.parent_column_id
    GROUP  BY
        [conz].[schema_id],
        [conz].[name],
        [conz].[referenced_object_id],--- without GROUP BY multiple rows are returned
        [conz].[object_id]
    ) AS [ParentCollection]
 ON [conz].[schema_id] = [ParentCollection].[schema_id] AND [conz].[name] = [ParentCollection].[name]
)AS [MyAlias];
DELETE FROM @DDLScript WHERE LINE_TEXT = '' OR LINE_TEXT IS NULL;
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT, LINE_SUFFIX )
SELECT 12, [tabz].[object_id], 'GO' + CHAR(10) + CHAR(10), ''
FROM [tempdb].[sys].[tables] [tabz]
WHERE
  [tabz].[schema_id] = @SCHEMA_ID AND ( @TABLE_ID = 0 OR [tabz].[object_id] = @TABLE_ID )
--##############################################################################
--INDEXES
--##############################################################################
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
SELECT 13, [OBJECT_ID],
     CASE
         WHEN [is_primary_key] = 0 OR [is_unique] = 0
         THEN CHAR(10)
              + 'CREATE ' COLLATE SQL_Latin1_General_CP1_CI_AS
              + CASE WHEN [is_unique] = 1 THEN 'UNIQUE ' COLLATE SQL_Latin1_General_CP1_CI_AS ELSE '' END
              + [type_desc]
              + ' INDEX '  COLLATE SQL_Latin1_General_CP1_CI_AS + QUOTENAME([index_name]) + ' '
              + CHAR(10)
              + '   ON '   COLLATE SQL_Latin1_General_CP1_CI_AS
              + QUOTENAME([SCHEMA_NAME]) + '.' + QUOTENAME([OBJECT_NAME])
              + CASE
                    WHEN [CurrentCompression] = 'COLUMNSTORE'  COLLATE SQL_Latin1_General_CP1_CI_AS
                    THEN ' (' + [index_columns_include] + ')'
                    ELSE ' (' + [index_columns_key] + ')'
                END
              + CASE
                  WHEN [CurrentCompression] = 'COLUMNSTORE'  COLLATE SQL_Latin1_General_CP1_CI_AS
                  THEN ''  COLLATE SQL_Latin1_General_CP1_CI_AS
                  ELSE
                    CASE
                 WHEN [index_columns_include] <> '---'
                 THEN CHAR(10) + '   INCLUDE ('  COLLATE SQL_Latin1_General_CP1_CI_AS + [index_columns_include] + ')'   COLLATE SQL_Latin1_General_CP1_CI_AS
                 ELSE ''   COLLATE SQL_Latin1_General_CP1_CI_AS
               END
                END
              --2008 filtered indexes syntax
              + CASE
                  WHEN [has_filter] = 1
                  THEN CHAR(10) + '   WHERE '  COLLATE SQL_Latin1_General_CP1_CI_AS + [filter_definition]
                  ELSE ''
                END
              + CASE WHEN [fill_factor] <> 0 OR [CurrentCompression] <> 'NONE'  COLLATE SQL_Latin1_General_CP1_CI_AS
              THEN ' WITH ('  COLLATE SQL_Latin1_General_CP1_CI_AS + CASE
                        WHEN [fill_factor] <> 0
                        THEN 'FILLFACTOR = '  COLLATE SQL_Latin1_General_CP1_CI_AS + CONVERT(VARCHAR(30),[fill_factor])
                        ELSE ''
                      END
                    + CASE
                        WHEN [fill_factor] <> 0  AND [CurrentCompression] <> 'NONE' THEN ',DATA_COMPRESSION = ' + [CurrentCompression]+' '
                        WHEN [fill_factor] <> 0  AND [CurrentCompression]  = 'NONE' THEN ''
                        WHEN [fill_factor]  = 0  AND [CurrentCompression] <> 'NONE' THEN 'DATA_COMPRESSION = ' + [CurrentCompression]+' '
                        ELSE ''
                      END
                      + ')'
              ELSE ''
              END
               + CHAR(10) + 'GO' + CHAR(10)
       END
FROM @PrimaryUniqueKeysTmp
WHERE [type_desc] != 'HEAP'
    AND [is_primary_key] = 0 AND ( [is_unique] = 0 OR [is_unique_constraint] = 0)
ORDER BY
    [is_primary_key] DESC,
    [is_unique] DESC;
--##############################################################################
--RULES
--##############################################################################
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_SUFFIX, LINE_TEXT )
SELECT 14, [colz].[object_id], CHAR(10) + 'GO' + CHAR(10) + CHAR(10),
         'if not exists(SELECT [name] FROM sys.objects WHERE TYPE=''R'' AND schema_id = ' COLLATE SQL_Latin1_General_CP1_CI_AS
         + CONVERT(VARCHAR(30),[objz].[schema_id]) + ' AND [name] = '''  COLLATE SQL_Latin1_General_CP1_CI_AS
         + QUOTENAME(OBJECT_NAME([colz].[rule_object_id])) + ''')'  COLLATE SQL_Latin1_General_CP1_CI_AS + CHAR(10)
         + [MODS].[definition]  + CHAR(10) + 'GO' COLLATE SQL_Latin1_General_CP1_CI_AS + CHAR(10)
         + 'EXEC sp_binderule  ' + QUOTENAME([objz].[name]) + ', ''' + QUOTENAME(OBJECT_NAME([colz].[object_id])) + '.' + QUOTENAME([colz].[name]) + ''''  COLLATE SQL_Latin1_General_CP1_CI_AS + CHAR(10) + 'GO'  COLLATE SQL_Latin1_General_CP1_CI_AS
FROM [tempdb].[sys].[columns] [colz]
    INNER JOIN [tempdb].[sys].[objects] [objz] ON [objz].[object_id] = [colz].[object_id]
    INNER JOIN [tempdb].[sys].[sql_modules] AS [MODS] ON [colz].[rule_object_id] = [MODS].[object_id]
WHERE [colz].[rule_object_id] <> 0
    AND [objz].[schema_id] = @SCHEMA_ID
    AND ( @TABLE_ID = 0 OR [colz].[object_id] = @TABLE_ID ) ;
--##############################################################################
--TRIGGERS
--##############################################################################
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_SUFFIX, LINE_TEXT )
  SELECT
    15, [objz].[parent_object_id], CHAR(10) + 'GO' + CHAR(10) + CHAR(10),
    [MODS].[definition]
  FROM [tempdb].[sys].[sql_modules] AS [MODS]
  JOIN [tempdb].[sys].[objects] AS [objz] ON [MODS].[object_id]=[objz].[parent_object_id]
  WHERE [objz].[type] = 'TR'
        AND [objz].[schema_id] = @SCHEMA_ID
        AND ( @TABLE_ID = 0 OR [objz].[parent_object_id] = @TABLE_ID )
  ;
--##############################################################################
--NEW SECTION QUERY ALL EXTENDED PROPERTIES
--##############################################################################
INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
  SELECT  16, [tabz].[object_id],
         'EXEC sys.sp_addextendedproperty
          @name = N'''  COLLATE SQL_Latin1_General_CP1_CI_AS + [fn].[name] + ''', @value = N'''  COLLATE SQL_Latin1_General_CP1_CI_AS + REPLACE(CONVERT(VARCHAR(MAX),[value]),'''','''''') + ''',
          @level0type = N''SCHEMA'', @level0name = '  COLLATE SQL_Latin1_General_CP1_CI_AS + QUOTENAME(@SCHEMA_NAME) + ',
          @level1type = N''TABLE'', @level1name = '  COLLATE SQL_Latin1_General_CP1_CI_AS + QUOTENAME(OBJECT_NAME([tabz].[object_id])) + ';' + CHAR(10) + 'GO' + CHAR(10) + CHAR(10)
 --SELECT objtype, objname, name, value
  FROM [sys].[tables] [tabz]
    OUTER APPLY [tempdb].[sys].[fn_listextendedproperty] (NULL, 'schema', @SCHEMA_NAME, 'table', OBJECT_NAME([tabz].[object_id]), NULL, NULL) AS [fn]
  WHERE
    [fn].[name] IS NOT NULL AND
    [tabz].[schema_id] = @SCHEMA_ID AND ( @TABLE_ID = 0 OR [tabz].[object_id] = @TABLE_ID );
    --OMacoder suggestion for column extended properties http://www.sqlservercentral.com/Forums/FindPost1651606.aspx
   WITH [obj] AS (
	SELECT [split].[a].[value]('.', 'VARCHAR(20)') AS [name]
	FROM (
		SELECT CAST ('<M>' + REPLACE('column,constraint,index,trigger,parameter', ',', '</M><M>') + '</M>' AS XML) AS [data]
		) AS [A]
		CROSS APPLY [data].[nodes] ('/M') AS [split]([a])
	)
	INSERT INTO @DDLScript ( TYPE, OBJECT_ID, LINE_TEXT )
	SELECT  16, [tabz].[object_id],
         'EXEC sys.sp_addextendedproperty
         @name = N''' COLLATE SQL_Latin1_General_CP1_CI_AS
         + [lep].[name]
         + ''', @value = N''' COLLATE SQL_Latin1_General_CP1_CI_AS
         + REPLACE(CONVERT(VARCHAR(MAX),[lep].[value]),'''','''''') + ''',
         @level0type = N''SCHEMA'', @level0name = ' COLLATE SQL_Latin1_General_CP1_CI_AS
         + QUOTENAME(@SCHEMA_NAME)
         + ',
         @level1type = N''TABLE'', @level1name = ' COLLATE SQL_Latin1_General_CP1_CI_AS
         + QUOTENAME(OBJECT_NAME([tabz].[object_id]))
         + ',
         @level2type = N''' COLLATE SQL_Latin1_General_CP1_CI_AS
         + UPPER([obj].[name])
         + ''', @level2name = ' COLLATE SQL_Latin1_General_CP1_CI_AS
         + QUOTENAME([lep].[objname]) + ';' + CHAR(10) + 'GO' + CHAR(10) + CHAR(10)  COLLATE SQL_Latin1_General_CP1_CI_AS
  --SELECT objtype, objname, name, value
  FROM [obj]
    CROSS APPLY [sys].[tables] [tabz]
	CROSS APPLY [tempdb].[sys].[fn_listextendedproperty] (NULL, 'schema', @SCHEMA_NAME, 'table', OBJECT_NAME([tabz].[object_id]), [obj].[name], NULL) AS [lep]
    WHERE
       [lep].[name] IS NOT NULL AND
       [tabz].[schema_id] = @SCHEMA_ID AND ( @TABLE_ID = 0 OR [tabz].[object_id] = @TABLE_ID );
--###################
-- FINAL
--###################
--TYPE 3 - COLUMNS GETS A COMMA OR )
UPDATE S SET LINE_SUFFIX=','
FROM @DDLScript S
WHERE TYPE=3 AND EXISTS ( SELECT 1 FROM @DDLScript V WHERE S.OBJECT_ID=V.OBJECT_ID AND S.LINE_NO < V.LINE_NO AND V.TYPE = S.TYPE );
UPDATE S SET LINE_SUFFIX=' )'
FROM @DDLScript S
WHERE TYPE=3 AND NOT EXISTS ( SELECT 1 FROM @DDLScript V WHERE S.OBJECT_ID=V.OBJECT_ID AND S.LINE_NO < V.LINE_NO AND V.TYPE = S.TYPE );
-- TYPE 9 - TEMPORAL, EXTERNAL OR MEMORY OPTIMIZED GETS A WITH (, COMMA OR )
UPDATE S SET LINE_SUFFIX=','
FROM @DDLScript S
WHERE TYPE=9 AND EXISTS ( SELECT 1 FROM @DDLScript V WHERE S.OBJECT_ID=V.OBJECT_ID AND S.LINE_NO < V.LINE_NO AND V.TYPE = S.TYPE );
UPDATE S SET LINE_SUFFIX=' )'
FROM @DDLScript S
WHERE TYPE=9 AND NOT EXISTS ( SELECT 1 FROM @DDLScript V WHERE S.OBJECT_ID=V.OBJECT_ID AND S.LINE_NO < V.LINE_NO AND V.TYPE = S.TYPE );
UPDATE S SET LINE_PREFIX='WITH ( '
FROM @DDLScript S
WHERE TYPE=9 AND NOT EXISTS ( SELECT 1 FROM @DDLScript V WHERE S.OBJECT_ID=V.OBJECT_ID AND S.LINE_NO > V.LINE_NO AND V.TYPE = S.TYPE );
--###################
-- END
--###################
SELECT [LINE_PREFIX] + [LINE_TEXT] + [LINE_SUFFIX] as TEXT FROM @DDLScript ORDER BY [OBJECT_ID], [TYPE], [LINE_NO];

END