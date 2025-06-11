package com.wisecoders.dbs.cli.command.connectivity;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.driver.model.DriverJarClass;
import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.schema.CLIConnectorManager;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.sql.SQLException;
import java.util.Arrays;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;

@DoNotObfuscate
public class ConnectionCommand extends Command {
  private final String b;
  
  private final String c;
  
  private final String d;
  
  private final String e;
  
  private final String f;
  
  private final String g;
  
  private final String h;
  
  private final String i;
  
  private final String j;
  
  private final String k;
  
  private final String l;
  
  private final String m;
  
  private final String n;
  
  private final Options o;
  
  public ConnectionCommand() {
    this.o = new Options();
    this.o.addRequiredOption("d", "dbms", true, "The DBMS name");
    this.o.addOption(this.c = "dc", "driver-class", true, "The JDBC driver class");
    this.o.addOption(this.d = "df", "driver-file", true, "The JDBC driver file name, requires to specify also the JDBC driver class.");
    this.o.addOption(this.b = "ut", "url-template", true, "The JDBC URL pattern, requires to specify also the JDBC driver class");
    this.o.addOption(this.e = "url", "url", true, "The JDBC URL");
    this.o.addRequiredOption(this.f = "h", "host", true, "The database host");
    this.o.addOption(this.g = "P", "port", true, "The database port");
    this.o.addOption(this.h = "D", "db", true, "The database");
    this.o.addOption(this.i = "u", "user", true, "The database user");
    this.o.addOption(this.j = "p", "password", true, "The database password");
    this.o.addOption(this.k = "param", "param", true, "The URL parameter.");
    this.o.addOption(this.l = "param2", "param2", true, "The URL parameter2");
    this.o.addOption(this.m = "param3", "param3", true, "The URL parameter3");
    this.o.addOption(this.n = "param4", "param4", true, "The URL parameter4");
    this.o.addOption("?", "help", true, "Help");
  }
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    CommandLine commandLine;
    String[] arrayOfString = AbstractConsole.c(paramString2);
    if (arrayOfString.length < 1)
      throw new SQLException("Connection name is missing"); 
    try {
      DefaultParser defaultParser = new DefaultParser();
      commandLine = defaultParser.parse(this.o, Arrays.<String>copyOfRange(arrayOfString, 1, arrayOfString.length));
    } catch (MissingOptionException missingOptionException) {
      paramAbstractConsole.a(missingOptionException.getLocalizedMessage(), new Object[0]);
      (new HelpFormatter()).printHelp("connection", this.o);
      return;
    } 
    if (commandLine.hasOption("?"))
      (new HelpFormatter()).printHelp("connection", this.o); 
    String str1 = arrayOfString[0];
    String str2 = commandLine.getOptionValue("dbms", "MySql");
    Connector connector = CLIConnectorManager.createConnector(str2);
    connector.setName(str1);
    DriverManager driverManager = DriverManager.a(str2);
    driverManager.k();
    if (driverManager.i().isEmpty() && 
      (Dbms.get(str2)).hasJDBCDriverOnWeb.b()) {
      DownloadDriverCommand.downloadDriver(str2);
      driverManager.k();
    } 
    if (commandLine.hasOption(this.c))
      connector.setDriverJarClass(commandLine.getOptionValue(this.c), commandLine.getOptionValue(this.d)); 
    if (connector.getDriverJarClassName() == null && !driverManager.i().isEmpty())
      connector.setDriverJarClass((((DriverJarClass)driverManager.i().get(0)).b()).b.getName(), ((DriverJarClass)driverManager
          .i().get(0)).a()); 
    if (connector.getDriverJarClassName() != null && commandLine.hasOption(this.b))
      connector.setDriverUrl(driverManager.c(connector.getDriverJarClassName(), commandLine.getOptionValue(this.b))); 
    if (connector.getDriverUrl() == null && !driverManager.f().isEmpty())
      connector.setDriverUrl(driverManager.f().get(0)); 
    if (commandLine.hasOption(this.e))
      connector.setCustomUrl(commandLine.getOptionValue(this.e)); 
    if (commandLine.hasOption(this.f))
      connector.setHost(commandLine.getOptionValue(this.f)); 
    if (commandLine.hasOption(this.g))
      connector.setPort(Integer.parseInt(commandLine.getOptionValue(this.g))); 
    if (commandLine.hasOption(this.h))
      connector.setInstance(commandLine.getOptionValue(this.h)); 
    if (commandLine.hasOption(this.i))
      connector.setUser(commandLine.getOptionValue(this.i)); 
    if (commandLine.hasOption(this.j))
      connector.setPassword(commandLine.getOptionValue(this.j)); 
    if (commandLine.hasOption(this.k))
      connector.setParam(commandLine.getOptionValue(this.k)); 
    if (commandLine.hasOption(this.l))
      connector.setParam2(commandLine.getOptionValue(this.l)); 
    if (commandLine.hasOption(this.m))
      connector.setParam3(commandLine.getOptionValue(this.m)); 
    if (commandLine.hasOption(this.n))
      connector.setParam4(commandLine.getOptionValue(this.n)); 
    paramAbstractConsole.c("Ok", new Object[0]);
  }
}
