package com.wisecoders.dbs.config.system;

import com.wisecoders.dbs.config.model.BooleanProperty;
import com.wisecoders.dbs.config.model.FileProperty;
import com.wisecoders.dbs.config.model.FolderProperty;
import com.wisecoders.dbs.config.model.IntProperty;
import com.wisecoders.dbs.config.model.ListProperty;
import com.wisecoders.dbs.config.model.LongProperty;
import com.wisecoders.dbs.config.model.RootProperty;
import com.wisecoders.dbs.config.model.StringProperty;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.nio.file.Path;

@DoNotObfuscate
public class Configuration {
  public final RootProperty root = new RootProperty(Configuration.class, "Configuration", true);
  
  public final FolderProperty format = this.root.b("format");
  
  public final ListProperty locale = this.format.a("locale", (Object[])new String[] { 
        "default", "en", "es", "pt", "de", "fr", "it", "zh_CN", "zh_TW", "ja", 
        "ko", "hi", "ru", "ar", "id", "tr" }, new String[] { 
        "Default", "English", "Spanish", "Portuguese", "German", "French", "Italian", "Chinese Simplified", "Chinese Traditional", "Japanese", 
        "Korean", "Hindu", "Russian", "Arabic", "Indonesian", "Turkish" });
  
  public final StringProperty dateFormat = this.format.g("date");
  
  public final StringProperty dateTimeFormat = this.format.g("dateTime");
  
  public final StringProperty timeFormat = this.format.g("time");
  
  public final StringProperty timestampFormat = this.format.g("timestamp");
  
  public final ListProperty numberFormat = this.format.a("number", (Object[])new Integer[] { Integer.valueOf(0), Integer.valueOf(1) }, new String[] { "System Default", "9999999.99 ( without thousand grouping )" });
  
  public final FolderProperty connectivity = this.root.b("connectivity");
  
  public final BooleanProperty logConnectivityDetails = this.connectivity.f("logConnectivityDetails");
  
  public final BooleanProperty readOnly = this.connectivity.f("readOnly");
  
  public final StringProperty readOnlyMessage = this.connectivity.g("readOnlyMessage");
  
  public final StringProperty keyStorePassword = this.connectivity.g("keyStorePassword");
  
  public final FolderProperty proxy = this.connectivity.b("proxy");
  
  public final BooleanProperty useSystemProxy = this.proxy.f("useSystemProxy");
  
  public final ListProperty proxyType = this.proxy.a("type", (Object[])new Integer[] { Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3) }, new String[] { "None", "HTTP", "HTTPS", "Socks" });
  
  public final StringProperty proxyHost = this.proxy.g("host");
  
  public final StringProperty proxyPort = this.proxy.g("port");
  
  public final StringProperty proxyUser = this.proxy.g("user");
  
  public final StringProperty proxyPassword = this.proxy.g("password");
  
  public final FolderProperty ssh = this.connectivity.b("ssh");
  
  public final IntProperty sshLocalPort = this.ssh.a("localPort", 1000, 500000);
  
  public final FolderProperty general = this.root.b("general");
  
  public final BooleanProperty reopenProject = this.general.f("reopen");
  
  public final IntProperty reopenHistorySize = this.general.a("reopenSize", 0, 16);
  
  public final IntProperty reopenLayouts = this.general.a("reopenLayouts", 0, 32);
  
  public final BooleanProperty enableBackup = this.general.f("backup");
  
  public final BooleanProperty validateBeforeSave = this.general.f("validateBeforeSave");
  
  public final IntProperty displayNamesMaxLength = this.general.a("displayNamesMaxLength", 50, 99999);
  
  public final IntProperty displaySubDocumentsMaxFields = this.general.a("displaySubDocumentsMaxFields", 10, 99999);
  
  public final StringProperty conversionGroovyScript = this.general.i("conversionGroovyScript");
  
  public final BooleanProperty betaFeatures = this.general.f("betaFeatures");
  
  public final FolderProperty reverseEngineer = this.root.b("reverseEngineer");
  
  public final BooleanProperty orderColumns = this.reverseEngineer.f("orderColumns");
  
  public final IntProperty hideColumnsOnLargeTablesByImport = this.reverseEngineer.a("importAutoHideColumns", 0, 100000000);
  
  public final BooleanProperty autoRefresh = this.reverseEngineer.f("autoRefresh");
  
  public final IntProperty autoRefreshDelay = this.reverseEngineer.a("autoRefreshDelaySeconds", 0, 1000);
  
  public final IntProperty viewDeduceColumnsTimeoutMillis = this.reverseEngineer.a("viewDeduceColumnsTimeoutMillis", 100, 100000000);
  
  public final BooleanProperty DDLParserLogAllStatements = this.reverseEngineer.f("DDLParserLogAllStatements");
  
  public final FolderProperty sql = this.root.b("sql");
  
  public final IntProperty clobReadString = this.sql.a("clobReadString", 1000, 100000000);
  
  public final BooleanProperty generateSqlToClipboard = this.sql.f("generateSqlToClipboard");
  
  public final IntProperty sqlRecordsPerPage = this.sql.a("recordsPerPage", 100, 100000000);
  
  public final IntProperty queryMaximalNumberOfLines = this.sql.a("queryMaximalNumberOfLines", 20, 1999999999);
  
  public final FolderProperty browse = this.root.b("browse");
  
  public final IntProperty browseRecordsPerPage = this.browse.a("recordsPerPage", 100, 100000000);
  
  public final BooleanProperty showRowNumber = this.browse.f("showRowNumber");
  
  public final FolderProperty queryEditor = this.root.b("queryEditor");
  
  public final BooleanProperty tickAllColumns = this.queryEditor.f("tickAllColumns");
  
  public final BooleanProperty alwaysShowUsageTips = this.queryEditor.f("alwaysShowUsageTips");
  
  public final FolderProperty dataLoader = this.root.b("dataLoader");
  
  public final IntProperty maxRowsPerChunk = this.dataLoader.a("maxRowsPerChunk", 1, 100000000);
  
  public final LongProperty maxMillisPerChunk = this.dataLoader.a("maxMillisPerChunk", 1L, 100000000L);
  
  public final IntProperty fkReadCount = this.dataLoader.a("fkReadCount", 10000, 1000000000);
  
  public final IntProperty maxErrors = this.dataLoader.a("maxErrors", 1, 1000000000);
  
  public final BooleanProperty skipErrors = this.dataLoader.f("skipErrors");
  
  public final IntProperty generatorNullPercent = this.dataLoader.a("generatorNullPercent", 0, 100);
  
  public final FolderProperty documentation = this.root.b("documentation");
  
  public final BooleanProperty logo = this.documentation.f("logo");
  
  public final StringProperty copyright = this.documentation.g("copyright").e("DbSchema @ Wise Coders");
  
  public final StringProperty copyrightHref = this.documentation.g("copyrightHref");
  
  public final BooleanProperty commentsAsMouseOver = this.documentation.f("commentsAsMouseOver");
  
  public final IntProperty groupInsets = this.documentation.a("groupInsets", 0, 150);
  
  public final FolderProperty messages = this.root.b("messages");
  
  public final BooleanProperty showRenewalMessage = this.messages.f("showRenewalMessage");
  
  public final FolderProperty cli = this.root.b("cli");
  
  public final FolderProperty cliSettings = this.cli.b("settings");
  
  public final IntProperty transferParallel = this.cliSettings.a("transfer_threads", 1, 300);
  
  public final StringProperty connectionInitScript = this.cliSettings.g("init_script");
  
  public final BooleanProperty replaceVariables = this.cliSettings.f("replace_variables");
  
  public final BooleanProperty convertEmptyStringToNull = this.cliSettings.f("convert_empty_string_to_null");
  
  public final BooleanProperty enableEcho = this.cliSettings.f("enable_echo");
  
  public final BooleanProperty enableParallelDDL = this.cliSettings.f("parallel_ddl");
  
  public final BooleanProperty ignoreErrors = this.cliSettings.f("ignore_errors");
  
  public final StringProperty screenFormat = this.cliSettings.g("screen_format");
  
  public final FolderProperty cliMail = this.cli.b("mail");
  
  public final StringProperty mailServer = this.cliMail.g("host");
  
  public final IntProperty mailPort = this.cliMail.a("port", 0, 10000);
  
  public final StringProperty mailUser = this.cliMail.g("user");
  
  public final StringProperty mailPassword = this.cliMail.g("password");
  
  public final StringProperty mailFrom = this.cliMail.g("from");
  
  public final FolderProperty cliCron = this.cli.b("cron");
  
  public final StringProperty cronEmail = this.cliCron.g("email");
  
  public final StringProperty cronFolder = this.cliCron.g("folder");
  
  public final FolderProperty cliSpool = this.cli.b("spool");
  
  public final StringProperty spoolFormat = this.cliSpool.g("format");
  
  public final BooleanProperty spoolHeader = this.cliSpool.f("header");
  
  public final StringProperty spoolSeparator = this.cliSpool.g("separator");
  
  public final BooleanProperty spoolQuotes = this.cliSpool.f("quotes");
  
  public final BooleanProperty spoolDb = this.cliSpool.f("db");
  
  public final FolderProperty store = this.root.b("store");
  
  public final FileProperty configFolder = this.store.a("configFolder", (Path)null).h();
  
  public final FileProperty connectionsFile = this.store.a("connectionsFile", (Path)null);
  
  public final FileProperty passwordsFile = this.store.a("passwordsFile", (Path)null);
  
  public final FileProperty groovyScriptsFolder = this.store.a("groovyScriptsFolder", (Path)null).h();
  
  public Configuration() {
    this.root.b(paramObject -> Boolean.valueOf(this.root.i()));
    this.root.q();
    Path path = this.configFolder.a() ? this.configFolder.f().toPath() : Sys.k;
    this.root.a(path.resolve("Configuration.properties"));
    this.root.h();
    if (System.getenv().containsKey("DbSchemaReadOnly")) {
      this.readOnly.a(true);
      this.readOnly.b(true);
    } 
  }
}
