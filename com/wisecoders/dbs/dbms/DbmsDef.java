package com.wisecoders.dbs.dbms;

import com.wisecoders.dbs.config.model.BooleanFormat;
import com.wisecoders.dbs.config.model.BooleanProperty;
import com.wisecoders.dbs.config.model.DatePatternProperty;
import com.wisecoders.dbs.config.model.FolderProperty;
import com.wisecoders.dbs.config.model.IntProperty;
import com.wisecoders.dbs.config.model.LOVProperty;
import com.wisecoders.dbs.config.model.LetterCase;
import com.wisecoders.dbs.config.model.ListProperty;
import com.wisecoders.dbs.config.model.OptionsProperty;
import com.wisecoders.dbs.config.model.ProviderProperty;
import com.wisecoders.dbs.config.model.RootProperty;
import com.wisecoders.dbs.config.model.StringProperty;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.operations.AlterColumn;
import com.wisecoders.dbs.dbms.sync.engine.operations.AlterConstraint;
import com.wisecoders.dbs.dbms.sync.engine.operations.AlterForeignKey;
import com.wisecoders.dbs.dbms.sync.engine.operations.AlterIndex;
import com.wisecoders.dbs.dbms.sync.engine.operations.AlterMaterializedView;
import com.wisecoders.dbs.dbms.sync.engine.operations.AlterPlsql;
import com.wisecoders.dbs.dbms.sync.engine.operations.AlterSchema;
import com.wisecoders.dbs.dbms.sync.engine.operations.AlterSequence;
import com.wisecoders.dbs.dbms.sync.engine.operations.AlterTable;
import com.wisecoders.dbs.dbms.sync.engine.operations.AlterUserDataType;
import com.wisecoders.dbs.dbms.sync.engine.operations.AlterView;
import com.wisecoders.dbs.dbms.sync.engine.operations.K;
import com.wisecoders.dbs.query.model.items.AliasSupport;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.CommentTag;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.schema.DbUnit;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@DoNotObfuscate
public class DbmsDef {
  public static final String DEFAULT_SCHEMA_NAME = "Default";
  
  public final String dbId;
  
  public final RootProperty root;
  
  public final FolderProperty formatting;
  
  public final FolderProperty cases;
  
  public final ListProperty identifierCases;
  
  public final ListProperty quotedIdentifierCases;
  
  public final BooleanProperty autoLetterCases;
  
  public final BooleanProperty quoteAllNames;
  
  public final FolderProperty escapes;
  
  public final StringProperty escapeSingleQuote;
  
  public final StringProperty escapeDoubleQuote;
  
  public final StringProperty escapeBackspace;
  
  public final StringProperty escapeFormFeed;
  
  public final StringProperty escapeNewline;
  
  public final StringProperty escapeCarriageReturn;
  
  public final StringProperty escapeTabulator;
  
  public final StringProperty escapeAmpersand;
  
  public final BooleanProperty useDatabaseDateFormat;
  
  public final ListProperty booleanFormat;
  
  public final FolderProperty format;
  
  public final DatePatternProperty dateFormat;
  
  public final DatePatternProperty dateTimeFormat;
  
  public final DatePatternProperty timestampFormat;
  
  public final StringProperty toTimestamp;
  
  public final StringProperty systemDate;
  
  public final StringProperty systemTime;
  
  public final StringProperty systemTimestamp;
  
  public final BooleanProperty booleanAs01;
  
  private final FolderProperty a;
  
  public final StringProperty identifierDelimiter;
  
  public final StringProperty stringDelimiter;
  
  public final StringProperty statementDelimiter;
  
  public final StringProperty psqlDelimiter;
  
  public final StringProperty comments;
  
  public final StringProperty brackets;
  
  public final FolderProperty syntax;
  
  public final FolderProperty database;
  
  public final StringProperty databaseCreate;
  
  public final OptionsProperty databaseOptions;
  
  public final FolderProperty schema;
  
  public final BooleanProperty prefixSchemaWithCatalog;
  
  public final StringProperty schemaCreate;
  
  public final StringProperty schemaDrop;
  
  public final StringProperty schemaRename;
  
  public final StringProperty schemaComment;
  
  public final StringProperty schemaChangeComment;
  
  public final StringProperty schemaDropComment;
  
  public final OptionsProperty schemaSpecificationOptions;
  
  public final OptionsProperty schemaOptions;
  
  public final FolderProperty table;
  
  public final BooleanProperty prefixTableWithSchema;
  
  public final StringProperty tableCreate;
  
  public final StringProperty tableCreatePartition;
  
  public final StringProperty tableRename;
  
  public final StringProperty childEntityRename;
  
  public final StringProperty tableDrop;
  
  public final StringProperty tableComment;
  
  public final StringProperty tableChangeComment;
  
  public final StringProperty tableDropComment;
  
  public final BooleanProperty commentsAtScriptEnd;
  
  public final StringProperty tableInsert;
  
  public final OptionsProperty tableSpecificationOptions;
  
  public final OptionsProperty tableOptions;
  
  public final OptionsProperty preScriptOptions;
  
  public final OptionsProperty postScriptOptions;
  
  public final StringProperty resetIdentity;
  
  public final StringProperty deleteStatement;
  
  public final FolderProperty column;
  
  public final StringProperty columnCreateInline;
  
  public final StringProperty columnCreate;
  
  public final StringProperty columnRename;
  
  public final StringProperty columnSetDefault;
  
  public final BooleanProperty columnUseDefaultNull;
  
  public final StringProperty columnRemoveDefault;
  
  public final StringProperty columnModifyType;
  
  public final StringProperty columnSetMandatory;
  
  public final StringProperty columnDropMandatory;
  
  public final StringProperty columnSetIdentity;
  
  public final StringProperty columnDropIdentity;
  
  public final StringProperty columnReorderFirst;
  
  public final StringProperty columnReorderAfter;
  
  public final StringProperty columnDrop;
  
  public final StringProperty columnComment;
  
  public final StringProperty columnChangeComment;
  
  public final StringProperty columnDropComment;
  
  public final StringProperty columnInsertFromSequence;
  
  public final OptionsProperty columnOptions;
  
  public final OptionsProperty columnIdentityOptions;
  
  public final BooleanProperty columnIdentitySetMandatory;
  
  public final BooleanProperty columnGeneratedUnsetMandatory;
  
  public final FolderProperty computed;
  
  public final StringProperty columnCreateComputedInline;
  
  public final StringProperty columnCreateComputed;
  
  public final OptionsProperty columnComputedOptions;
  
  public final FolderProperty columnKeywords;
  
  public final StringProperty columnNotNull;
  
  public final StringProperty columnNull;
  
  public final StringProperty columnNullInline;
  
  public final StringProperty columnUnsigned;
  
  public final StringProperty columnDefault;
  
  public final FolderProperty constraint;
  
  public final StringProperty constraintCreateInline;
  
  public final StringProperty constraintCreate;
  
  public final StringProperty constraintRename;
  
  public final StringProperty constraintDrop;
  
  public final StringProperty constraintComment;
  
  public final StringProperty constraintTypes;
  
  public final OptionsProperty constraintOptions;
  
  public final FolderProperty pk;
  
  public final StringProperty pkCreate;
  
  public final StringProperty pkInlineKeyword;
  
  public final StringProperty pkCreateInline;
  
  public final StringProperty pkCreateEnding;
  
  public final StringProperty pkRename;
  
  public final StringProperty pkDrop;
  
  public final StringProperty pkComment;
  
  public final OptionsProperty pkColumnOptions;
  
  public final OptionsProperty pkSpecificationOptions;
  
  public final OptionsProperty pkOptions;
  
  public final FolderProperty uniqueKey;
  
  public final StringProperty uniqueKeyCreate;
  
  public final StringProperty uniqueKeyCreateInline;
  
  public final StringProperty uniqueKeyRename;
  
  public final StringProperty uniqueKeyDrop;
  
  public final StringProperty uniqueKeyComment;
  
  public final OptionsProperty uniqueKeyColumnOptions;
  
  public final OptionsProperty uniqueKeySpecificationOptions;
  
  public final OptionsProperty uniqueKeyOptions;
  
  public final FolderProperty uniqueIndex;
  
  public final StringProperty uniqueIndexCreate;
  
  public final StringProperty uniqueIndexCreateInline;
  
  public final StringProperty uniqueIndexRename;
  
  public final StringProperty uniqueIndexDrop;
  
  public final StringProperty uniqueIndexComment;
  
  public final OptionsProperty uniqueIndexColumnOptions;
  
  public final OptionsProperty uniqueIndexSpecificationOptions;
  
  public final OptionsProperty uniqueIndexOptions;
  
  public final FolderProperty index;
  
  public final StringProperty indexCreate;
  
  public final StringProperty indexRename;
  
  public final StringProperty indexDrop;
  
  public final StringProperty indexComment;
  
  public final StringProperty indexIncludeColumns;
  
  public final OptionsProperty indexColumnOptions;
  
  public final OptionsProperty indexSpecificationOptions;
  
  public final OptionsProperty indexOptions;
  
  public final FolderProperty partition;
  
  public final StringProperty partitionName;
  
  public final StringProperty partitionTooltip;
  
  public final StringProperty partitionCreate;
  
  public final StringProperty partitionCreateInline;
  
  public final StringProperty partitionCreateEnding;
  
  public final StringProperty partitionRename;
  
  public final StringProperty partitionDrop;
  
  public final OptionsProperty partitionColumnOptions;
  
  public final OptionsProperty partitionSpecificationOptions;
  
  public final OptionsProperty partitionOptions;
  
  public final BooleanProperty partitionIsColumnDefinition;
  
  public final StringProperty partitionColumnDefinition;
  
  public final FolderProperty sort;
  
  public final StringProperty sortName;
  
  public final StringProperty sortTooltip;
  
  public final StringProperty sortCreate;
  
  public final StringProperty sortCreateInline;
  
  public final StringProperty sortCreateEnding;
  
  public final StringProperty sortRename;
  
  public final StringProperty sortDrop;
  
  public final OptionsProperty sortColumnOptions;
  
  public final OptionsProperty sortSpecificationOptions;
  
  public final OptionsProperty sortOptions;
  
  public final FolderProperty cluster;
  
  public final StringProperty clusterName;
  
  public final StringProperty clusterTooltip;
  
  public final StringProperty clusterCreate;
  
  public final StringProperty clusterCreateInline;
  
  public final StringProperty clusterCreateEnding;
  
  public final StringProperty clusterRename;
  
  public final StringProperty clusterDrop;
  
  public final OptionsProperty clusterColumnOptions;
  
  public final OptionsProperty clusterSpecificationOptions;
  
  public final OptionsProperty clusterOptions;
  
  public final FolderProperty index1;
  
  public final StringProperty index1Name;
  
  public final StringProperty index1Tooltip;
  
  public final StringProperty index1Create;
  
  public final StringProperty index1CreateInline;
  
  public final StringProperty index1CreateEnding;
  
  public final StringProperty index1Rename;
  
  public final StringProperty index1Drop;
  
  public final OptionsProperty index1ColumnOptions;
  
  public final OptionsProperty index1SpecificationOptions;
  
  public final OptionsProperty index1Options;
  
  public final FolderProperty index2;
  
  public final StringProperty index2Name;
  
  public final StringProperty index2Tooltip;
  
  public final StringProperty index2Create;
  
  public final StringProperty index2CreateInline;
  
  public final StringProperty index2CreateEnding;
  
  public final StringProperty index2Rename;
  
  public final StringProperty index2Drop;
  
  public final OptionsProperty index2ColumnOptions;
  
  public final OptionsProperty index2SpecificationOptions;
  
  public final OptionsProperty index2Options;
  
  public final FolderProperty fk;
  
  public final StringProperty fkCreate;
  
  public final StringProperty fkCreateInline;
  
  public final StringProperty fkRename;
  
  public final StringProperty fkComment;
  
  public final StringProperty fkChangeComment;
  
  public final StringProperty fkDropComment;
  
  public final StringProperty fkDrop;
  
  public final FolderProperty fkDelete;
  
  public final StringProperty fkDeleteCascade;
  
  public final StringProperty fkDeleteSetNull;
  
  public final StringProperty fkDeleteRestrict;
  
  public final StringProperty fkDeleteNoAction;
  
  public final OptionsProperty fkOptions;
  
  public final FolderProperty fkUpdate;
  
  public final StringProperty fkUpdateCascade;
  
  public final StringProperty fkUpdateSetNull;
  
  public final StringProperty fkUpdateRestrict;
  
  public final StringProperty fkUpdateNoAction;
  
  public final FolderProperty userDataType;
  
  public final StringProperty userDataTypeRename;
  
  public final StringProperty userDataTypeComment;
  
  public final StringProperty userDataTypeDrop;
  
  public final StringProperty userDataUpdateCast;
  
  public final OptionsProperty userDataTypeDeclaration;
  
  public final FolderProperty view;
  
  public final StringProperty viewDrop;
  
  public final StringProperty viewComment;
  
  public final FolderProperty materializedView;
  
  public final StringProperty materializedViewCreate;
  
  public final StringProperty materializedViewDrop;
  
  public final StringProperty materializedViewComment;
  
  public final FolderProperty sequence;
  
  public final StringProperty sequenceCreate;
  
  public final StringProperty sequenceComment;
  
  public final StringProperty sequenceChangeComment;
  
  public final StringProperty sequenceDropComment;
  
  public final StringProperty sequenceDrop;
  
  public final OptionsProperty sequenceOptions;
  
  public final FolderProperty plsql;
  
  public final StringProperty dropFunction;
  
  public final StringProperty dropProcedure;
  
  public final StringProperty dropTrigger;
  
  public final StringProperty dropRule;
  
  public final StringProperty functionComment;
  
  public final StringProperty functionChangeComment;
  
  public final StringProperty functionDropComment;
  
  public final StringProperty procedureComment;
  
  public final StringProperty procedureChangeComment;
  
  public final StringProperty procedureDropComment;
  
  public final StringProperty triggerComment;
  
  public final StringProperty triggerChangeComment;
  
  public final StringProperty triggerDropComment;
  
  public final BooleanProperty triggerSkipCatalogName;
  
  public final StringProperty ruleComment;
  
  public final StringProperty ruleChangeComment;
  
  public final StringProperty ruleDropComment;
  
  public final FolderProperty keywords;
  
  public final LOVProperty stringFunctions;
  
  public final LOVProperty dateTimeFunctions;
  
  public final LOVProperty reserved;
  
  public final LOVProperty tableTypes;
  
  public final FolderProperty other;
  
  public final ProviderProperty dataProvider;
  
  public final StringProperty truncateTable;
  
  public final StringProperty tags;
  
  public final AlterSchema alterSchema;
  
  public final AlterTable alterTable;
  
  public final AlterColumn alterColumn;
  
  public final AlterConstraint alterConstraint;
  
  public final AlterIndex alterPrimaryKey;
  
  public final AlterIndex alterUniqueIndex;
  
  public final AlterIndex alterUniqueKey;
  
  public final AlterIndex alterNormalIndex;
  
  public final AlterIndex alterPartition;
  
  public final AlterIndex alterSort;
  
  public final AlterIndex alterCluster;
  
  public final AlterIndex alterIndex1;
  
  public final AlterIndex alterIndex2;
  
  public final AlterForeignKey alterForeignKey;
  
  public final AlterView alterView;
  
  public final AlterUserDataType alterUserDataType;
  
  public final AlterMaterializedView alterMaterializedView;
  
  public final AlterSequence alterSequence;
  
  public final AlterPlsql alterPlSql;
  
  public final FolderProperty engineer;
  
  public final BooleanProperty reverseEngineerUsingDDL;
  
  public final BooleanProperty reverseEngineerPartitionTables;
  
  public final FolderProperty queries;
  
  public final StringProperty loadProcedureName;
  
  public final StringProperty loadProcedureDefinition;
  
  public final StringProperty loadFunctionName;
  
  public final StringProperty loadFunctionDefinition;
  
  public final StringProperty loadTriggerName;
  
  public final StringProperty loadTriggerDefinition;
  
  public final StringProperty loadRuleName;
  
  public final StringProperty loadRuleDefinition;
  
  public final StringProperty loadConstraintDefinition;
  
  public final BooleanProperty loadConstraintRemoveBrackets;
  
  public final StringProperty loadSequenceName;
  
  public final StringProperty loadViewDefinition;
  
  public final StringProperty loadMaterializedViewsDefinition;
  
  public final StringProperty loadSchemaComment;
  
  public final FolderProperty projectScriptAddOns;
  
  public final StringProperty projectScriptAddon1Name;
  
  public final StringProperty projectScriptAddon1Load;
  
  public final StringProperty projectScriptAddon1Script;
  
  public final StringProperty projectScriptAddon2Name;
  
  public final StringProperty projectScriptAddon2Load;
  
  public final StringProperty projectScriptAddon2Script;
  
  public final StringProperty projectScriptAddon3Name;
  
  public final StringProperty projectScriptAddon3Load;
  
  public final StringProperty projectScriptAddon3Script;
  
  public final StringProperty projectScriptAddon4Name;
  
  public final StringProperty projectScriptAddon4Load;
  
  public final StringProperty projectScriptAddon4Script;
  
  public final FolderProperty projectPropertyAddOns;
  
  public final StringProperty projectPropertyAddon1Name;
  
  public final StringProperty projectPropertyAddon1Load;
  
  public final StringProperty projectPropertyAddon2Name;
  
  public final StringProperty projectPropertyAddon2Load;
  
  public final StringProperty projectPropertyAddon3Name;
  
  public final StringProperty projectPropertyAddon3Load;
  
  public final StringProperty projectPropertyAddon4Name;
  
  public final StringProperty projectPropertyAddon4Load;
  
  public final FolderProperty schemaScriptAddOns;
  
  public final StringProperty schemaScriptAddon1Name;
  
  public final StringProperty schemaScriptAddon1Load;
  
  public final StringProperty schemaScriptAddon1Script;
  
  public final StringProperty schemaScriptAddon2Name;
  
  public final StringProperty schemaScriptAddon2Load;
  
  public final StringProperty schemaScriptAddon2Script;
  
  public final StringProperty schemaScriptAddon3Name;
  
  public final StringProperty schemaScriptAddon3Load;
  
  public final StringProperty schemaScriptAddon3Script;
  
  public final StringProperty schemaScriptAddon4Name;
  
  public final StringProperty schemaScriptAddon4Load;
  
  public final StringProperty schemaScriptAddon4Script;
  
  public final FolderProperty schemaPropertyAddOns;
  
  public final StringProperty schemaPropertyAddon1Name;
  
  public final StringProperty schemaPropertyAddon1Load;
  
  public final StringProperty schemaPropertyAddon2Name;
  
  public final StringProperty schemaPropertyAddon2Load;
  
  public final StringProperty schemaPropertyAddon3Name;
  
  public final StringProperty schemaPropertyAddon3Load;
  
  public final StringProperty schemaPropertyAddon4Name;
  
  public final StringProperty schemaPropertyAddon4Load;
  
  public final FolderProperty statistics;
  
  public final StringProperty tableRowCount;
  
  public final StringProperty statementsHistoryQuery;
  
  public final StringProperty statementsHistoryAdvice;
  
  public final FolderProperty other2;
  
  public final StringProperty groovyCustomCode;
  
  public final StringProperty explainPlanView;
  
  public final StringProperty importRemarksProperty;
  
  public final FolderProperty behaviour;
  
  public final FolderProperty transaction;
  
  public final BooleanProperty disableAutoCommit;
  
  public final ListProperty commit;
  
  public final StringProperty selectHint;
  
  public final StringProperty initializationScript;
  
  public final IntProperty loadTimeout;
  
  public final BooleanProperty useLimit;
  
  public final BooleanProperty useOffset;
  
  public final IntProperty recordsPerPage;
  
  public final BooleanProperty multipleValuesInInserts;
  
  public final FolderProperty behaviourDriver;
  
  public final BooleanProperty useDefaultSchema;
  
  public final BooleanProperty schemaIsCatalogInDatabaseMetaData;
  
  public final BooleanProperty queriesUsesCatalogAndSchema;
  
  public final BooleanProperty allowEmptyPassword;
  
  public final BooleanProperty removeSemicolonFromQueries;
  
  public final BooleanProperty hasJDBCDriverOnWeb;
  
  public final BooleanProperty runDDLUsingStatementExecute;
  
  public final StringProperty jdbcTipFx;
  
  public final StringProperty defaultUser;
  
  public final StringProperty defaultPassword;
  
  public final IntProperty defaultPort;
  
  public final StringProperty defaultInstance;
  
  public final StringProperty defaultUrlParam;
  
  public final StringProperty defaultUrlParam2;
  
  public final StringProperty defaultUrlParam3;
  
  public final StringProperty defaultUrlParam4;
  
  public final StringProperty defaultUrlParam5;
  
  public final StringProperty defaultSchema;
  
  public final StringProperty defaultDatabase;
  
  public final FolderProperty behaviourVirtualization;
  
  public final BooleanProperty allowVirtualTables;
  
  public final BooleanProperty pkIndexAllowVirtual;
  
  public final BooleanProperty createNewForeignKeysAsVirtual;
  
  public final BooleanProperty allowVirtualViews;
  
  public final FolderProperty behaviourSettings;
  
  public final BooleanProperty useTreeTableDetailsPane;
  
  public final BooleanProperty disableReorderColumns;
  
  public final BooleanProperty identityOnAllColumns;
  
  public final StringProperty systemDatabases;
  
  public final StringProperty systemDatabasesAlias;
  
  public final StringProperty duplicateIndexesOptions;
  
  public final ListProperty aliasSupport;
  
  public final BooleanProperty pkCanUseNonMandatoryColumns;
  
  public final FolderProperty aliases;
  
  public final StringProperty catalogAlias;
  
  public final StringProperty schemaAlias;
  
  public final StringProperty tableAlias;
  
  public final StringProperty viewAlias;
  
  public final StringProperty columnAlias;
  
  public final StringProperty fkAlias;
  
  public final StringProperty userDataTypeAlias;
  
  public final StringProperty materializedViewAlias;
  
  public final StringProperty procedureAlias;
  
  public final StringProperty functionAlias;
  
  public final StringProperty ruleAlias;
  
  public final StringProperty triggerAlias;
  
  public final FolderProperty defaults;
  
  public final StringProperty defaultTableName;
  
  public final StringProperty defaultConstraintName;
  
  public final StringProperty defaultPrimaryKeyName;
  
  public final StringProperty defaultUniqueKeyName;
  
  public final StringProperty defaultIndexName;
  
  public final StringProperty defaultPartitionName;
  
  public final StringProperty defaultSortName;
  
  public final StringProperty defaultClusterName;
  
  public final StringProperty defaultIndex1Name;
  
  public final StringProperty defaultIndex2Name;
  
  public final StringProperty defaultForeignKeyName;
  
  public final StringProperty defaultSequenceName;
  
  public final StringProperty defaultTableStorage;
  
  public final StringProperty defaultStatements;
  
  public final StringProperty defaultProcedureText;
  
  public final StringProperty defaultFunctionText;
  
  public final StringProperty defaultTriggerName;
  
  public final StringProperty defaultTriggerText;
  
  public final StringProperty defaultRuleName;
  
  public final StringProperty defaultRuleText;
  
  public final StringProperty defaultViewName;
  
  public final StringProperty defaultViewText;
  
  public final StringProperty defaultMaterializedViewText;
  
  public final StringProperty defaultDomainText;
  
  public final FolderProperty dataExplorer;
  
  public final BooleanProperty userOrderBy;
  
  public final FolderProperty fkBehaviour;
  
  public final BooleanProperty foreignKeysInline;
  
  public final BooleanProperty newForeignKeyOptimizeDeletion;
  
  public final FolderProperty behaviourLimits;
  
  public final IntProperty maxSchemaNameLength;
  
  public final IntProperty maxTableNameLength;
  
  public final IntProperty maxColumnNameLength;
  
  public final IntProperty maxForeignKeyNameLength;
  
  private final ObservableList b;
  
  public DbmsDef(String paramString) {
    this.root = new RootProperty(DbmsDef.class, "Database Settings", false);
    this.formatting = this.root.b("formatting");
    this.cases = this.formatting.b("cases");
    this.identifierCases = this.cases.a("identifier", new Object[] { LetterCase.a, LetterCase.d, LetterCase.b }, new String[] { "Lower", "Mixed", "Upper" });
    this.quotedIdentifierCases = this.cases.a("quotedIdentifier", new Object[] { LetterCase.a, LetterCase.d, LetterCase.b }, new String[] { "Lower", "Mixed", "Upper" });
    this.autoLetterCases = this.cases.f("convertNames");
    this.quoteAllNames = this.cases.f("quoteAllNames");
    this.escapes = this.formatting.b("escapes");
    this.escapeSingleQuote = this.escapes.g("singleQuote");
    this.escapeDoubleQuote = this.escapes.g("doubleQuote");
    this.escapeBackspace = this.escapes.g("backspace");
    this.escapeFormFeed = this.escapes.g("formFeed");
    this.escapeNewline = this.escapes.g("newLine");
    this.escapeCarriageReturn = this.escapes.g("carriageReturn");
    this.escapeTabulator = this.escapes.g("tabulator");
    this.escapeAmpersand = this.escapes.g("ampersand");
    this.useDatabaseDateFormat = this.escapes.f("useDatabaseDateFormat");
    this.booleanFormat = this.escapes.a("booleanFormat", new Object[] { BooleanFormat.a, BooleanFormat.c, BooleanFormat.b }, new String[] { "0/1", "true/false", "yes/no" });
    this.format = this.formatting.b("format");
    this.dateFormat = this.format.m("date");
    this.dateTimeFormat = this.format.m("time");
    this.timestampFormat = this.format.m("timestamp");
    this.toTimestamp = this.format.g("toTimestamp");
    this.systemDate = this.format.g("dateFunction");
    this.systemTime = this.format.g("timeFunction");
    this.systemTimestamp = this.format.g("timestampFunction");
    this.booleanAs01 = this.format.f("booleanAs01");
    this.a = this.formatting.b("delimiter");
    this.identifierDelimiter = this.a.g("identifier");
    this.stringDelimiter = this.a.g("string");
    this.statementDelimiter = this.a.g("statement").e("");
    this.psqlDelimiter = this.a.g("psql").e("");
    this.comments = this.a.g("comments").e("-- ");
    this.brackets = this.a.g("brackets").e("(");
    this.syntax = this.root.b("syntax");
    this.database = this.syntax.b("database");
    this.databaseCreate = this.database.i("create");
    this.databaseOptions = this.database.j("options");
    this.schema = this.syntax.b("schema");
    this.prefixSchemaWithCatalog = this.schema.f("prefixWithCatalog");
    this.schemaCreate = this.schema.i("create");
    this.schemaDrop = this.schema.i("drop");
    this.schemaRename = this.schema.i("rename");
    this.schemaComment = this.schema.i("comment");
    this.schemaChangeComment = this.schema.i("changeComment");
    this.schemaDropComment = this.schema.i("dropComment");
    this.schemaSpecificationOptions = this.schema.j("specificationOptions");
    this.schemaOptions = this.schema.j("options");
    this.table = this.syntax.b("table");
    this.prefixTableWithSchema = this.table.f("prefixWithSchema");
    this.tableCreate = this.table.i("create");
    this.tableCreatePartition = this.table.i("createPartition");
    this.tableRename = this.table.i("rename");
    this.childEntityRename = this.table.i("renameChildEntity");
    this.tableDrop = this.table.i("drop");
    this.tableComment = this.table.i("comment");
    this.tableChangeComment = this.table.i("changeComment");
    this.tableDropComment = this.table.i("dropComment");
    this.commentsAtScriptEnd = this.table.f("commentsAtScriptEnd");
    this.tableInsert = this.table.i("insert").e("INSERT INTO");
    this.tableSpecificationOptions = this.table.j("specificationOptions");
    this.tableOptions = this.table.j("options");
    this.preScriptOptions = this.table.j("preScriptOptions");
    this.postScriptOptions = this.table.j("postScriptOptions");
    this.resetIdentity = this.table.i("resetIdentity");
    this.deleteStatement = this.table.i("deleteStatement");
    this.column = this.table.c("column");
    this.columnCreateInline = this.column.i("createInline");
    this.columnCreate = this.column.i("create");
    this.columnRename = this.column.i("rename");
    this.columnSetDefault = this.column.i("setDefault");
    this.columnUseDefaultNull = this.column.f("useDefaultNull");
    this.columnRemoveDefault = this.column.i("removeDefault");
    this.columnModifyType = this.column.i("modifyType");
    this.columnSetMandatory = this.column.i("setMandatory");
    this.columnDropMandatory = this.column.i("dropMandatory");
    this.columnSetIdentity = this.column.i("setIdentity");
    this.columnDropIdentity = this.column.i("dropIdentity");
    this.columnReorderFirst = this.column.i("reorderFirst");
    this.columnReorderAfter = this.column.i("reorderAfter");
    this.columnDrop = this.column.i("drop");
    this.columnComment = this.column.i("comment");
    this.columnChangeComment = this.column.i("changeComment");
    this.columnDropComment = this.column.i("dropComment");
    this.columnInsertFromSequence = this.column.i("insertFromSequence");
    this.columnOptions = this.column.j("options");
    this.columnIdentityOptions = this.column.j("identityOptions");
    this.columnIdentitySetMandatory = this.column.f("identitySetMandatory");
    this.columnGeneratedUnsetMandatory = this.column.f("generatedUnsetMandatory");
    this.computed = this.column.c("computed");
    this.columnCreateComputedInline = this.computed.i("createInline");
    this.columnCreateComputed = this.computed.i("create");
    this.columnComputedOptions = this.computed.j("options");
    this.columnKeywords = this.column.c("keywords");
    this.columnNotNull = this.columnKeywords.i("notNull");
    this.columnNull = this.columnKeywords.i("null");
    this.columnNullInline = this.columnKeywords.i("nullInline");
    this.columnUnsigned = this.columnKeywords.i("unsigned");
    this.columnDefault = this.columnKeywords.i("default");
    this.constraint = this.table.c("constraint");
    this.constraintCreateInline = this.constraint.i("createInline");
    this.constraintCreate = this.constraint.i("create");
    this.constraintRename = this.constraint.i("rename");
    this.constraintDrop = this.constraint.i("drop");
    this.constraintComment = this.constraint.i("comment");
    this.constraintTypes = this.constraint.i("types");
    this.constraintOptions = this.constraint.j("options");
    this.pk = this.table.c("pk");
    this.pkCreate = this.pk.i("create");
    this.pkInlineKeyword = this.pk.i("inlineKeyword");
    this.pkCreateInline = this.pk.i("createInline");
    this.pkCreateEnding = this.pk.i("createEnding");
    this.pkRename = this.pk.i("rename");
    this.pkDrop = this.pk.i("drop");
    this.pkComment = this.pk.i("comment");
    this.pkColumnOptions = this.pk.j("columnOptions");
    this.pkSpecificationOptions = this.pk.j("specificationOptions");
    this.pkOptions = this.pk.j("options");
    this.uniqueKey = this.table.c("uniqueKey");
    this.uniqueKeyCreate = this.uniqueKey.i("create");
    this.uniqueKeyCreateInline = this.uniqueKey.i("createInline");
    this.uniqueKeyRename = this.uniqueKey.i("rename");
    this.uniqueKeyDrop = this.uniqueKey.i("drop");
    this.uniqueKeyComment = this.uniqueKey.i("comment");
    this.uniqueKeyColumnOptions = this.uniqueKey.j("columnOptions");
    this.uniqueKeySpecificationOptions = this.uniqueKey.j("specificationOptions");
    this.uniqueKeyOptions = this.uniqueKey.j("options");
    this.uniqueIndex = this.table.c("uniqueIndex");
    this.uniqueIndexCreate = this.uniqueIndex.i("create");
    this.uniqueIndexCreateInline = this.uniqueIndex.i("createInline");
    this.uniqueIndexRename = this.uniqueIndex.i("rename");
    this.uniqueIndexDrop = this.uniqueIndex.i("drop");
    this.uniqueIndexComment = this.uniqueIndex.i("comment");
    this.uniqueIndexColumnOptions = this.uniqueIndex.j("columnOptions");
    this.uniqueIndexSpecificationOptions = this.uniqueIndex.j("specificationOptions");
    this.uniqueIndexOptions = this.uniqueIndex.j("options");
    this.index = this.table.c("index");
    this.indexCreate = this.index.i("create");
    this.indexRename = this.index.i("rename");
    this.indexDrop = this.index.i("drop");
    this.indexComment = this.index.i("comment");
    this.indexIncludeColumns = this.index.i("includeColumns");
    this.indexColumnOptions = this.index.j("columnOptions");
    this.indexSpecificationOptions = this.index.j("specificationOptions");
    this.indexOptions = this.index.j("options");
    this.partition = this.table.c("partition");
    this.partitionName = this.partition.i("name");
    this.partitionTooltip = this.partition.i("tooltip");
    this.partitionCreate = this.partition.i("create");
    this.partitionCreateInline = this.partition.i("createInline");
    this.partitionCreateEnding = this.partition.i("createEnding");
    this.partitionRename = this.partition.i("rename");
    this.partitionDrop = this.partition.i("drop");
    this.partitionColumnOptions = this.partition.j("columnOptions");
    this.partitionSpecificationOptions = this.partition.j("specificationOptions");
    this.partitionOptions = this.partition.j("options");
    this.partitionIsColumnDefinition = this.partition.f("isColumnDefinition");
    this.partitionColumnDefinition = this.partition.i("columnDefinition");
    this.sort = this.table.c("sort");
    this.sortName = this.sort.i("name");
    this.sortTooltip = this.sort.i("tooltip");
    this.sortCreate = this.sort.i("create");
    this.sortCreateInline = this.sort.i("createInline");
    this.sortCreateEnding = this.sort.i("createEnding");
    this.sortRename = this.sort.i("rename");
    this.sortDrop = this.sort.i("drop");
    this.sortColumnOptions = this.sort.j("columnOptions");
    this.sortSpecificationOptions = this.sort.j("specificationOptions");
    this.sortOptions = this.sort.j("options");
    this.cluster = this.table.c("cluster");
    this.clusterName = this.cluster.i("name");
    this.clusterTooltip = this.cluster.i("tooltip");
    this.clusterCreate = this.cluster.i("create");
    this.clusterCreateInline = this.cluster.i("createInline");
    this.clusterCreateEnding = this.cluster.i("createEnding");
    this.clusterRename = this.cluster.i("rename");
    this.clusterDrop = this.cluster.i("drop");
    this.clusterColumnOptions = this.cluster.j("columnOptions");
    this.clusterSpecificationOptions = this.cluster.j("specificationOptions");
    this.clusterOptions = this.cluster.j("options");
    this.index1 = this.table.c("index1");
    this.index1Name = this.index1.i("name");
    this.index1Tooltip = this.index1.i("tooltip");
    this.index1Create = this.index1.i("create");
    this.index1CreateInline = this.index1.i("createInline");
    this.index1CreateEnding = this.index1.i("createEnding");
    this.index1Rename = this.index1.i("rename");
    this.index1Drop = this.index1.i("drop");
    this.index1ColumnOptions = this.index1.j("columnOptions");
    this.index1SpecificationOptions = this.index1.j("specificationOptions");
    this.index1Options = this.index1.j("options");
    this.index2 = this.table.c("index2");
    this.index2Name = this.index2.i("name");
    this.index2Tooltip = this.index2.i("tooltip");
    this.index2Create = this.index2.i("create");
    this.index2CreateInline = this.index2.i("createInline");
    this.index2CreateEnding = this.index2.i("createEnding");
    this.index2Rename = this.index2.i("rename");
    this.index2Drop = this.index2.i("drop");
    this.index2ColumnOptions = this.index2.j("columnOptions");
    this.index2SpecificationOptions = this.index2.j("specificationOptions");
    this.index2Options = this.index2.j("options");
    this.fk = this.table.c("fk");
    this.fkCreate = this.fk.i("create");
    this.fkCreateInline = this.fk.i("createInline");
    this.fkRename = this.fk.i("rename");
    this.fkComment = this.fk.i("comment");
    this.fkChangeComment = this.fk.i("changeComment");
    this.fkDropComment = this.fk.i("dropComment");
    this.fkDrop = this.fk.i("drop");
    this.fkDelete = this.fk.c("deleteAction");
    this.fkDeleteCascade = this.fkDelete.i("cascade");
    this.fkDeleteSetNull = this.fkDelete.i("setNull");
    this.fkDeleteRestrict = this.fkDelete.i("restrict");
    this.fkDeleteNoAction = this.fkDelete.i("noAction");
    this.fkOptions = this.fk.j("options");
    this.fkUpdate = this.fk.c("updateAction");
    this.fkUpdateCascade = this.fkUpdate.i("cascade");
    this.fkUpdateSetNull = this.fkUpdate.i("setNull");
    this.fkUpdateRestrict = this.fkUpdate.i("restrict");
    this.fkUpdateNoAction = this.fkUpdate.i("noAction");
    this.userDataType = this.syntax.b("userDataType");
    this.userDataTypeRename = this.userDataType.i("rename");
    this.userDataTypeComment = this.userDataType.i("comment");
    this.userDataTypeDrop = this.userDataType.i("drop");
    this.userDataUpdateCast = this.userDataType.i("updateCast");
    this.userDataTypeDeclaration = this.userDataType.j("declaration");
    this.view = this.syntax.b("view");
    this.viewDrop = this.view.i("drop");
    this.viewComment = this.view.i("comment");
    this.materializedView = this.syntax.b("materializedView");
    this.materializedViewCreate = this.materializedView.i("create");
    this.materializedViewDrop = this.materializedView.i("drop");
    this.materializedViewComment = this.materializedView.i("comment");
    this.sequence = this.syntax.b("sequence");
    this.sequenceCreate = this.sequence.i("create");
    this.sequenceComment = this.sequence.i("comment");
    this.sequenceChangeComment = this.sequence.i("changeComment");
    this.sequenceDropComment = this.sequence.i("dropComment");
    this.sequenceDrop = this.sequence.i("drop");
    this.sequenceOptions = this.sequence.j("options");
    this.plsql = this.syntax.b("plsql");
    this.dropFunction = this.plsql.i("dropFunction");
    this.dropProcedure = this.plsql.i("dropProcedure");
    this.dropTrigger = this.plsql.i("dropTrigger");
    this.dropRule = this.plsql.i("dropRule");
    this.functionComment = this.plsql.i("functionComment");
    this.functionChangeComment = this.plsql.i("functionChangeComment");
    this.functionDropComment = this.plsql.i("functionDropComment");
    this.procedureComment = this.plsql.i("procedureComment");
    this.procedureChangeComment = this.plsql.i("procedureChangeComment");
    this.procedureDropComment = this.plsql.i("procedureDropComment");
    this.triggerComment = this.plsql.i("triggerComment");
    this.triggerChangeComment = this.plsql.i("triggerChangeComment");
    this.triggerDropComment = this.plsql.i("triggerDropComment");
    this.triggerSkipCatalogName = this.plsql.f("triggerSkipCatalogName");
    this.ruleComment = this.plsql.i("ruleComment");
    this.ruleChangeComment = this.plsql.i("ruleChangeComment");
    this.ruleDropComment = this.plsql.i("ruleDropComment");
    this.keywords = this.syntax.b("keywords");
    this.stringFunctions = this.keywords.l("stringFunctions");
    this.dateTimeFunctions = this.keywords.l("dateTimeFunctions");
    this.reserved = this.keywords.l("reserved");
    this.tableTypes = this.keywords.l("tableTypes");
    this.other = this.syntax.b("other");
    this.dataProvider = this.other.k("dataProvider");
    this.truncateTable = this.other.i("truncateTable");
    this.tags = this.other.i("tags");
    this.alterSchema = new AlterSchema(this);
    this.alterTable = new AlterTable(this);
    this.alterColumn = new AlterColumn(this);
    this.alterConstraint = new AlterConstraint(this);
    this.alterPrimaryKey = new AlterIndex(this, IndexType.PRIMARY_KEY);
    this.alterUniqueIndex = new AlterIndex(this, IndexType.UNIQUE_INDEX);
    this.alterUniqueKey = new AlterIndex(this, IndexType.UNIQUE_KEY);
    this.alterNormalIndex = new AlterIndex(this, IndexType.NORMAL);
    this.alterPartition = new AlterIndex(this, IndexType.PARTITION);
    this.alterSort = new AlterIndex(this, IndexType.SORT);
    this.alterCluster = new AlterIndex(this, IndexType.CLUSTER);
    this.alterIndex1 = new AlterIndex(this, IndexType.INDEX1);
    this.alterIndex2 = new AlterIndex(this, IndexType.INDEX2);
    this.alterForeignKey = new AlterForeignKey(this);
    this.alterView = new AlterView(this);
    this.alterUserDataType = new AlterUserDataType(this);
    this.alterMaterializedView = new AlterMaterializedView(this);
    this.alterSequence = new AlterSequence(this);
    this.alterPlSql = new AlterPlsql(this);
    this.engineer = this.root.b("engineer");
    this.reverseEngineerUsingDDL = this.engineer.f("reverseEngineerUsingDDL");
    this.reverseEngineerPartitionTables = this.engineer.f("reverseEngineerPartitionTables");
    this.queries = this.engineer.b("queries");
    this.loadProcedureName = this.queries.i("procedureName");
    this.loadProcedureDefinition = this.queries.i("procedureDefinition");
    this.loadFunctionName = this.queries.i("functionName");
    this.loadFunctionDefinition = this.queries.i("functionDefinition");
    this.loadTriggerName = this.queries.i("triggerName");
    this.loadTriggerDefinition = this.queries.i("triggerDefinition");
    this.loadRuleName = this.queries.i("ruleName");
    this.loadRuleDefinition = this.queries.i("ruleDefinition");
    this.loadConstraintDefinition = this.queries.i("constraintDefinition");
    this.loadConstraintRemoveBrackets = this.queries.f("loadConstraintRemoveBrackets");
    this.loadSequenceName = this.queries.i("sequenceName");
    this.loadViewDefinition = this.queries.i("viewDefinition");
    this.loadMaterializedViewsDefinition = this.queries.i("materializedViewDefinition");
    this.loadSchemaComment = this.queries.i("schemaComment");
    this.projectScriptAddOns = this.engineer.b("projectScriptAddons");
    this.projectScriptAddon1Name = this.projectScriptAddOns.g("name1");
    this.projectScriptAddon1Load = this.projectScriptAddOns.g("load1");
    this.projectScriptAddon1Script = this.projectScriptAddOns.g("script1");
    this.projectScriptAddon2Name = this.projectScriptAddOns.g("name2");
    this.projectScriptAddon2Load = this.projectScriptAddOns.g("load2");
    this.projectScriptAddon2Script = this.projectScriptAddOns.g("script2");
    this.projectScriptAddon3Name = this.projectScriptAddOns.g("name3");
    this.projectScriptAddon3Load = this.projectScriptAddOns.g("load3");
    this.projectScriptAddon3Script = this.projectScriptAddOns.g("script3");
    this.projectScriptAddon4Name = this.projectScriptAddOns.g("name4");
    this.projectScriptAddon4Load = this.projectScriptAddOns.g("load4");
    this.projectScriptAddon4Script = this.projectScriptAddOns.g("script4");
    this.projectPropertyAddOns = this.engineer.b("projectPropertyAddons");
    this.projectPropertyAddon1Name = this.projectPropertyAddOns.g("name1");
    this.projectPropertyAddon1Load = this.projectPropertyAddOns.g("load1");
    this.projectPropertyAddon2Name = this.projectPropertyAddOns.g("name2");
    this.projectPropertyAddon2Load = this.projectPropertyAddOns.g("load2");
    this.projectPropertyAddon3Name = this.projectPropertyAddOns.g("name3");
    this.projectPropertyAddon3Load = this.projectPropertyAddOns.g("load3");
    this.projectPropertyAddon4Name = this.projectPropertyAddOns.g("name4");
    this.projectPropertyAddon4Load = this.projectPropertyAddOns.g("load4");
    this.schemaScriptAddOns = this.engineer.b("schemaScriptAddons");
    this.schemaScriptAddon1Name = this.schemaScriptAddOns.g("name1");
    this.schemaScriptAddon1Load = this.schemaScriptAddOns.g("load1");
    this.schemaScriptAddon1Script = this.schemaScriptAddOns.g("script1");
    this.schemaScriptAddon2Name = this.schemaScriptAddOns.g("name2");
    this.schemaScriptAddon2Load = this.schemaScriptAddOns.g("load2");
    this.schemaScriptAddon2Script = this.schemaScriptAddOns.g("script2");
    this.schemaScriptAddon3Name = this.schemaScriptAddOns.g("name3");
    this.schemaScriptAddon3Load = this.schemaScriptAddOns.g("load3");
    this.schemaScriptAddon3Script = this.schemaScriptAddOns.g("script3");
    this.schemaScriptAddon4Name = this.schemaScriptAddOns.g("name4");
    this.schemaScriptAddon4Load = this.schemaScriptAddOns.g("load4");
    this.schemaScriptAddon4Script = this.schemaScriptAddOns.g("script4");
    this.schemaPropertyAddOns = this.engineer.b("schemaPropertyAddons");
    this.schemaPropertyAddon1Name = this.schemaPropertyAddOns.g("name1");
    this.schemaPropertyAddon1Load = this.schemaPropertyAddOns.g("load1");
    this.schemaPropertyAddon2Name = this.schemaPropertyAddOns.g("name2");
    this.schemaPropertyAddon2Load = this.schemaPropertyAddOns.g("load2");
    this.schemaPropertyAddon3Name = this.schemaPropertyAddOns.g("name3");
    this.schemaPropertyAddon3Load = this.schemaPropertyAddOns.g("load3");
    this.schemaPropertyAddon4Name = this.schemaPropertyAddOns.g("name4");
    this.schemaPropertyAddon4Load = this.schemaPropertyAddOns.g("load4");
    this.statistics = this.engineer.b("statistics");
    this.tableRowCount = this.statistics.g("tableRowCount");
    this.statementsHistoryQuery = this.statistics.g("statementsHistoryQuery");
    this.statementsHistoryAdvice = this.statistics.g("statementsHistoryAdvice");
    this.other2 = this.engineer.b("other");
    this.groovyCustomCode = this.other2.i("groovyCustomCode");
    this.explainPlanView = this.other2.i("explainPlanView");
    this.importRemarksProperty = this.other2.g("importRemarksProperty");
    this.behaviour = this.root.b("behaviour");
    this.transaction = this.behaviour.b("transaction");
    this.disableAutoCommit = this.transaction.f("disableAutoCommit");
    this.commit = this.transaction.a("commit", (Object[])new Integer[] { Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3) }, new String[] { "DML", "ALTER TABLE", "SELECT", "Never" });
    this.selectHint = this.transaction.g("selectHint");
    this.initializationScript = this.transaction.g("initializationScript");
    this.loadTimeout = this.transaction.a("loadTimeout", 1000, 100000000);
    this.useLimit = this.transaction.f("useLimit");
    this.useOffset = this.transaction.f("useOffset");
    this.recordsPerPage = this.transaction.a("recordsPerPage", 1, 10000000);
    this.multipleValuesInInserts = this.transaction.f("multipleValuesInInserts");
    this.behaviourDriver = this.behaviour.b("driver");
    this.useDefaultSchema = this.behaviourDriver.f("useDefaultSchema");
    this.schemaIsCatalogInDatabaseMetaData = this.behaviourDriver.f("schemaIsCatalogInDatabaseMetaData");
    this.queriesUsesCatalogAndSchema = this.behaviourDriver.f("queriesUsesCatalogAndSchema");
    this.allowEmptyPassword = this.behaviourDriver.f("allowEmptyPassword");
    this.removeSemicolonFromQueries = this.behaviourDriver.f("removeSemicolonFromQueries");
    this.hasJDBCDriverOnWeb = this.behaviourDriver.f("hasJDBCDriverOnWeb");
    this.runDDLUsingStatementExecute = this.behaviourDriver.f("runDDLUsingStatementExecute");
    this.jdbcTipFx = this.behaviourDriver.g("jdbcTipFx");
    this.defaultUser = this.behaviourDriver.g("user");
    this.defaultPassword = this.behaviourDriver.g("password");
    this.defaultPort = this.behaviourDriver.a("port", -1, 100000);
    this.defaultInstance = this.behaviourDriver.g("instance");
    this.defaultUrlParam = this.behaviourDriver.g("urlParam");
    this.defaultUrlParam2 = this.behaviourDriver.g("urlParam2");
    this.defaultUrlParam3 = this.behaviourDriver.g("urlParam3");
    this.defaultUrlParam4 = this.behaviourDriver.g("urlParam4");
    this.defaultUrlParam5 = this.behaviourDriver.g("urlParam5");
    this.defaultSchema = this.behaviourDriver.g("schema");
    this.defaultDatabase = this.behaviourDriver.g("defaultDatabase");
    this.behaviourVirtualization = this.behaviour.b("virtualization");
    this.allowVirtualTables = this.behaviourVirtualization.f("allowVirtualTables");
    this.pkIndexAllowVirtual = this.behaviourVirtualization.f("pkIndexAllowVirtual");
    this.createNewForeignKeysAsVirtual = this.behaviourVirtualization.f("createNewForeignKeysAsVirtual");
    this.allowVirtualViews = this.behaviourVirtualization.f("allowVirtualViews");
    this.behaviourSettings = this.behaviour.b("settings");
    this.useTreeTableDetailsPane = this.behaviourSettings.f("useTreeTableDetailsPane");
    this.disableReorderColumns = this.behaviourSettings.f("disableReorderColumns");
    this.identityOnAllColumns = this.behaviourSettings.f("identityOnAllColumns");
    this.systemDatabases = this.behaviourSettings.g("systemDatabases");
    this.systemDatabasesAlias = this.behaviourSettings.g("systemDatabasesAlias");
    this.duplicateIndexesOptions = this.behaviourSettings.g("duplicateIndexesOptions");
    this.aliasSupport = this.behaviourSettings.a("aliasSupport", new Object[] { AliasSupport.a, AliasSupport.b, AliasSupport.c }, new String[] { "Supported", "As Table Name", "Unsupported" });
    this.pkCanUseNonMandatoryColumns = this.behaviourSettings.f("pkCanUseNonMandatoryColumns");
    this.aliases = this.behaviour.b("aliases");
    this.catalogAlias = this.aliases.i("catalogAlias").e("Catalog");
    this.schemaAlias = this.aliases.i("schemaAlias").e("Schema");
    this.tableAlias = this.aliases.i("tableAlias").e("Table");
    this.viewAlias = this.aliases.i("viewAlias").e("View");
    this.columnAlias = this.aliases.i("columnAlias").e("Combo");
    this.fkAlias = this.aliases.i("fkAlias").e("Foreign Key");
    this.userDataTypeAlias = this.aliases.i("userDataTypeAlias").e("User Data Type");
    this.materializedViewAlias = this.aliases.i("materializedViewAlias").e("Materialized View");
    this.procedureAlias = this.aliases.i("procedure").e("procedure");
    this.functionAlias = this.aliases.i("function").e("function");
    this.ruleAlias = this.aliases.i("rule").e("rule");
    this.triggerAlias = this.aliases.i("trigger").e("trigger");
    this.defaults = this.behaviour.b("defaults");
    this.defaultTableName = this.defaults.i("tableName");
    this.defaultConstraintName = this.defaults.i("constraintName");
    this.defaultPrimaryKeyName = this.defaults.i("primaryKeyName");
    this.defaultUniqueKeyName = this.defaults.i("uniqueKeyName");
    this.defaultIndexName = this.defaults.i("indexName");
    this.defaultPartitionName = this.defaults.i("partitionName");
    this.defaultSortName = this.defaults.i("sortName");
    this.defaultClusterName = this.defaults.i("clusterName");
    this.defaultIndex1Name = this.defaults.i("index1Name");
    this.defaultIndex2Name = this.defaults.i("index2Name");
    this.defaultForeignKeyName = this.defaults.i("foreignKeyName");
    this.defaultSequenceName = this.defaults.i("sequenceName");
    this.defaultTableStorage = this.defaults.g("tableStorage");
    this.defaultStatements = this.defaults.g("statements");
    this.defaultProcedureText = this.defaults.i("procedure");
    this.defaultFunctionText = this.defaults.i("function");
    this.defaultTriggerName = this.defaults.i("triggerName");
    this.defaultTriggerText = this.defaults.i("trigger");
    this.defaultRuleName = this.defaults.i("ruleName");
    this.defaultRuleText = this.defaults.i("rule");
    this.defaultViewName = this.defaults.i("viewName");
    this.defaultViewText = this.defaults.i("view");
    this.defaultMaterializedViewText = this.defaults.i("materializedView");
    this.defaultDomainText = this.defaults.i("domain");
    this.dataExplorer = this.behaviour.b("dataExplorer");
    this.userOrderBy = this.dataExplorer.f("useOrderBy");
    this.fkBehaviour = this.behaviour.b("fk");
    this.foreignKeysInline = this.fkBehaviour.f("foreignKeysInline");
    this.newForeignKeyOptimizeDeletion = this.fkBehaviour.f("newForeignKeyOptimizeDeletion");
    this.behaviourLimits = this.behaviour.b("limits");
    this.maxSchemaNameLength = this.behaviourLimits.a("maxSchemaNameLength", 30, 1000000);
    this.maxTableNameLength = this.behaviourLimits.a("maxTableNameLength", 30, 1000000);
    this.maxColumnNameLength = this.behaviourLimits.a("maxColumnNameLength", 30, 1000000);
    this.maxForeignKeyNameLength = this.behaviourLimits.a("maxForeignKeyNameLength", 30, 1000000);
    this.b = FXCollections.observableArrayList();
    this.dbId = paramString;
    this.disableAutoCommit.a(paramObject -> {
          ConnectorManager.resetConnectors(paramString);
          return Boolean.valueOf(true);
        });
  }
  
  public boolean defaultIsUsingBrackets() {
    return (this.columnDefault.j() && this.columnDefault.c_().contains("(") && this.columnDefault.c_().contains(")"));
  }
  
  public AlterIndex getAlterIndex(IndexType paramIndexType) {
    switch (DbmsDef$1.a[paramIndexType.ordinal()]) {
      case 1:
      
      case 2:
      
      case 3:
      
      case 4:
      
      case 5:
      
      case 6:
      
      case 7:
      
      case 8:
      
    } 
    return this.alterNormalIndex;
  }
  
  public String toDefaultCases(String paramString) {
    if (paramString == null)
      return null; 
    switch (DbmsDef$1.b[getLetterCases().ordinal()]) {
      case 1:
      
      case 2:
      
      case 3:
      
    } 
    return paramString.toLowerCase();
  }
  
  public static String quote(DbUnit paramDbUnit) {
    return Dbms.get(paramDbUnit.getDbId()).quote(paramDbUnit.getName());
  }
  
  public String quote(String paramString) {
    if (paramString != null && !paramString.isEmpty()) {
      boolean bool = (this.quoteAllNames.b() || paramString.indexOf(' ') > -1 || paramString.indexOf('(') > -1 || paramString.indexOf(')') > -1 || paramString.indexOf('\\') > -1 || paramString.indexOf('+') > -1 || paramString.indexOf('-') > -1 || paramString.indexOf('*') > -1 || paramString.indexOf('%') > -1 || paramString.indexOf('=') > -1 || paramString.indexOf('&') > -1 || paramString.indexOf('#') > -1 || paramString.indexOf('$') > -1 || paramString.indexOf('>') > -1 || paramString.indexOf('<') > -1 || paramString.indexOf(':') > -1 || paramString.indexOf('/') > -1 || paramString.startsWith("_") || StringUtil.isNumberOrSpecialCharacter(paramString.charAt(0)) || (!"HBase".equals(this.dbId) && !"Cassandra".equals(this.dbId) && paramString.indexOf('.') > -1) || this.reserved.c(paramString) || (getLetterCases() == LetterCase.a && !paramString.equals(paramString.toLowerCase())) || (getLetterCases() == LetterCase.b && !paramString.equals(paramString.toUpperCase()))) ? true : false;
      if (bool)
        return quoteAlways(paramString); 
    } 
    return paramString;
  }
  
  public String quoteAlways(String paramString) {
    return this.identifierDelimiter.p() ? (this.identifierDelimiter.c_() + this.identifierDelimiter.c_() + paramString) : paramString;
  }
  
  private String a(String paramString) {
    if ("[".equals(paramString))
      return "]"; 
    return paramString;
  }
  
  public boolean isReservedKeyword(String paramString) {
    return (StringUtil.isFilledTrim(paramString) && this.reserved.c(paramString.toLowerCase()));
  }
  
  public String indexColumnsWithOptions(Index paramIndex) {
    StringBuilder stringBuilder = new StringBuilder();
    boolean bool = false;
    for (Column column : paramIndex.columns) {
      if (column != null) {
        stringBuilder.append(bool ? ", " : "");
        if (paramIndex.getType() == IndexType.PARTITION && this.partitionColumnDefinition.p()) {
          SimpleStatement simpleStatement = (new SimpleStatement(this.partitionColumnDefinition.c_())).set(K.q, column.ref()).set(K.W, column.getTypeString());
          stringBuilder.append(simpleStatement.toString());
        } else if (column.getSpec() == AttributeSpec.functional) {
          stringBuilder.append(column.getName());
        } else {
          stringBuilder.append(quote(column.getName()));
        } 
        bool = true;
        String str = paramIndex.getColumnOptions(column);
        if (str != null)
          stringBuilder.append(" ").append(str); 
      } 
    } 
    return stringBuilder.toString();
  }
  
  public String identifierCommaList(List paramList) {
    StringBuilder stringBuilder = new StringBuilder();
    boolean bool = false;
    for (Column column : paramList) {
      if (column != null) {
        stringBuilder.append(bool ? ", " : "");
        if (column.getSpec() == AttributeSpec.functional) {
          stringBuilder.append(column.getName());
        } else {
          stringBuilder.append(quote(column.getName()));
        } 
        bool = true;
      } 
    } 
    return stringBuilder.toString();
  }
  
  public String jsonIdentifierCommaList(List paramList) {
    StringBuilder stringBuilder = new StringBuilder();
    boolean bool = false;
    for (Column column : paramList) {
      if (column != null) {
        stringBuilder.append(bool ? ", '" : "'").append(column.getNameWithPath()).append("':1");
        bool = true;
      } 
    } 
    return stringBuilder.toString();
  }
  
  public LetterCase getLetterCases() {
    return (LetterCase)this.identifierCases.c();
  }
  
  public boolean isStartBracketInDDL(String paramString) {
    return this.brackets.c_().contains(paramString);
  }
  
  public boolean isEndBracketInDDL(String paramString) {
    if (")".equals(paramString))
      return this.brackets.c_().contains("("); 
    if (">".equals(paramString))
      return this.brackets.c_().contains("<"); 
    if ("}".equals(paramString))
      return this.brackets.c_().contains("{"); 
    return false;
  }
  
  public String[] getCommentDelimiters() {
    return this.comments.c_().split(",");
  }
  
  public String removeLineComments(String paramString) {
    if (paramString != null)
      for (String str : getCommentDelimiters()) {
        int i = paramString.indexOf(str);
        if (i == 0)
          return ""; 
        if (i > 0)
          return paramString.substring(0, i); 
      }  
    return paramString;
  }
  
  public String escapeString(String paramString) {
    if (paramString != null) {
      HashMap<Object, Object> hashMap = new HashMap<>();
      if (this.escapeSingleQuote.j())
        hashMap.put(Character.valueOf('\''), this.escapeSingleQuote.c_()); 
      if (this.escapeDoubleQuote.j())
        hashMap.put(Character.valueOf('"'), this.escapeDoubleQuote.c_()); 
      if (this.escapeBackspace.j())
        hashMap.put(Character.valueOf('\\'), this.escapeBackspace.c_()); 
      if (this.escapeFormFeed.j())
        hashMap.put(Character.valueOf('\f'), this.escapeFormFeed.c_()); 
      if (this.escapeNewline.j())
        hashMap.put(Character.valueOf('\n'), this.escapeNewline.c_()); 
      if (this.escapeCarriageReturn.j())
        hashMap.put(Character.valueOf('\r'), this.escapeCarriageReturn.c_()); 
      if (this.escapeTabulator.j())
        hashMap.put(Character.valueOf('\t'), this.escapeTabulator.c_()); 
      if (this.escapeAmpersand.j())
        hashMap.put(Character.valueOf('&'), this.escapeAmpersand.c_()); 
      StringBuilder stringBuilder = new StringBuilder();
      StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(paramString);
      char c = stringCharacterIterator.current();
      while (c != Character.MAX_VALUE) {
        String str = (String)hashMap.get(Character.valueOf(c));
        stringBuilder.append((str == null) ? Character.valueOf(c) : ("REMOVE".equals(str) ? "" : str));
        c = stringCharacterIterator.next();
      } 
      return stringBuilder.toString();
    } 
    return null;
  }
  
  public List getDefaultStatements() {
    ArrayList<String> arrayList = new ArrayList();
    if (this.defaultStatements.j())
      for (String str : this.defaultStatements.c_().split(";")) {
        if (StringUtil.isFilledTrim(str))
          arrayList.add(str); 
      }  
    return arrayList;
  }
  
  public AliasSupport getAliasSupport() {
    return (AliasSupport)this.aliasSupport.c();
  }
  
  public int getSqlRecordsPerPage() {
    return Math.min(Sys.B.sqlRecordsPerPage.a(), this.recordsPerPage.a());
  }
  
  public int getBrowseRecordsPerPage() {
    return Math.min(Sys.B.browseRecordsPerPage.a(), this.recordsPerPage.a());
  }
  
  public boolean computedColumnUsesDataType() {
    return ((this.columnCreateComputedInline.c_() != null && this.columnCreateComputedInline.c_().contains("${type}")) || (this.columnCreateComputed.c_() != null && this.columnCreateComputed.c_().contains("${type}")));
  }
  
  public ObservableList getTags() {
    String str = this.tags.c_();
    if (str != null && this.b.isEmpty())
      for (String str1 : str.split(";")) {
        String[] arrayOfString = str1.split(":");
        if (arrayOfString.length > 1) {
          CommentTag commentTag = new CommentTag(arrayOfString[0], arrayOfString[1]);
          if (arrayOfString.length > 2)
            commentTag.setCommaSeparatedValuesOrGroovyScript(arrayOfString[2]); 
          this.b.add(commentTag);
        } 
      }  
    return this.b;
  }
  
  public void saveTags() {
    StringBuilder stringBuilder = new StringBuilder();
    for (CommentTag commentTag : this.b) {
      if (!stringBuilder.isEmpty())
        stringBuilder.append(";"); 
      stringBuilder.append(commentTag.getName()).append(":").append(commentTag.getAppliesTo());
      if (commentTag.getCommaSeparatedValuesOrGroovyScript() != null)
        stringBuilder.append(":").append(commentTag.getCommaSeparatedValuesOrGroovyScript()); 
    } 
    this.tags.b(stringBuilder.toString());
  }
  
  public String getIndexTypeName(IndexType paramIndexType) {
    switch (DbmsDef$1.a[paramIndexType.ordinal()]) {
      case 1:
      
      case 2:
      
      case 3:
      
      case 5:
      
      case 6:
      
      case 4:
      
      case 7:
      
      case 8:
      
    } 
    return 







      
      "Index";
  }
}
