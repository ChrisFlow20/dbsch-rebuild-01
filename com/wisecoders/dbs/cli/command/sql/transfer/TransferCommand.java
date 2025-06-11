package com.wisecoders.dbs.cli.command.sql.transfer;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.CommandType;
import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.command.sql.transfer.db.TransferDbReader;
import com.wisecoders.dbs.cli.command.sql.transfer.db.TransferDbWriter;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.schema.CLIConnectorManager;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ConnectorGroup;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.TableDependency;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class TransferCommand extends Command {
  private static final Pattern b = Pattern.compile("(\\S*)\\s*from\\s*(\\S*)\\s*(using)?\\s*(.*)", 42);
  
  private TransferDbReader c;
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    if (CLIConnectorManager.a().size() == 0)
      throw new SQLException("Not connected."); 
    if (CLIConnectorManager.a().size() > 1)
      throw new SQLException("Need to be connected to a single database."); 
    Connector connector = CLIConnectorManager.a().get(0);
    Matcher matcher;
    if ((matcher = b.matcher(paramString2)).matches()) {
      String str = matcher.group(4);
      if ("using".equalsIgnoreCase(matcher.group(3)) && StringUtil.isEmpty(str))
        throw new ParameterException("Missing transfer query"); 
      a(paramAbstractConsole, connector.getCliProject(), connector, matcher.group(1), a(paramAbstractConsole, matcher.group(2)), str);
    } else {
      throw new ParameterException("Wrong transfer command. Use 'transfer [truncate] <table> from <connection> [using SELECT...]");
    } 
  }
  
  private void a(AbstractConsole paramAbstractConsole, Project paramProject, Connector paramConnector, String paramString1, ConnectorGroup paramConnectorGroup, String paramString2) {
    Table table = SyncUtil.a(paramProject, paramConnector.getInstance(), paramString1);
    if (table == null)
      throw new SQLException("Target table " + paramString1 + " not found. Use 'learn schema' first."); 
    for (Connector connector : paramConnectorGroup.getConnectors()) {
      Envoy envoy = connector.startEnvoy("Transfer");
      if (this.a)
        return; 
      paramAbstractConsole.a(connector);
      if (StringUtil.isEmpty(paramString2)) {
        paramString2 = "SELECT * FROM " + table.ref();
      } else {
        paramString2 = paramString2.replaceAll("\\$db", "'" + connector.getName() + "'");
      } 
      a(paramAbstractConsole, envoy, paramConnector, table, paramString2);
    } 
  }
  
  private void a(AbstractConsole paramAbstractConsole, Envoy paramEnvoy, Connector paramConnector, Table paramTable, String paramString) {
    int i = Sys.B.transferParallel.a();
    paramAbstractConsole.d(String.format("Transfer using %d thread(s) %-40s ", new Object[] { Integer.valueOf(i), paramTable.getName() + " ..." }), new Object[0]);
    ArrayList<Future<?>> arrayList = new ArrayList();
    TransferQueues transferQueues = new TransferQueues(paramTable.columns.size());
    this.c = new TransferDbReader(paramEnvoy, transferQueues, paramTable.getName(), paramString);
    ExecutorService executorService = Executors.newFixedThreadPool(i + 1);
    executorService.submit(this.c);
    for (byte b = 0; b < i; b++)
      arrayList.add(executorService.submit(new TransferDbWriter(paramAbstractConsole, paramConnector, this.c.b, this.c.a, paramTable))); 
    for (Future<?> future : arrayList)
      future.get(); 
    executorService.shutdownNow();
    transferQueues.m();
    if (TransferQueues.g())
      throw new SQLException("Transfer Interrupted"); 
    paramAbstractConsole.a(this.c.a.h(), new Object[0]);
  }
  
  private ConnectorGroup a(AbstractConsole paramAbstractConsole, String paramString) {
    ConnectorGroup connectorGroup = new ConnectorGroup("from");
    for (String str : paramString.split(",")) {
      str = str.trim();
      connectorGroup.addAll(CLIConnectorManager.b(str));
      connectorGroup.addConnector(CLIConnectorManager.getByName(str));
    } 
    if (connectorGroup.isEmpty())
      throw new ParameterException("Invalid connection " + paramString); 
    return connectorGroup;
  }
  
  public void interrupt() {
    super.interrupt();
    if (this.c != null)
      TransferQueues.e(); 
  }
  
  public CommandType getType() {
    return CommandType.c;
  }
  
  private static List a(Connection paramConnection, Schema paramSchema, String paramString) {
    ArrayList<Table> arrayList = new ArrayList();
    Statement statement = paramConnection.createStatement();
    statement.setFetchSize(200);
    arrayList.clear();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM " + paramString);
    while (resultSet.next()) {
      Table table = paramSchema.getTable(resultSet.getString(1));
      if (table != null)
        arrayList.add(table); 
    } 
    statement.close();
    return arrayList;
  }
  
  public static void transferToOutputStream(Envoy paramEnvoy, OutputStream paramOutputStream, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    ArrayList arrayList = new ArrayList();
    TransferQueues transferQueues = new TransferQueues(200);
    TransferDbReader transferDbReader = new TransferDbReader(paramEnvoy, transferQueues, "custom", paramString);
    byte b1 = 1;
    ExecutorService executorService = Executors.newFixedThreadPool(b1 + 1);
    executorService.submit(transferDbReader);
    for (byte b2 = 0; b2 < b1; b2++);
    for (Future future : arrayList)
      future.get(); 
    executorService.shutdownNow();
    arrayList.clear();
    transferQueues.m();
    paramOutputStream.flush();
    if (TransferQueues.g())
      throw new SQLException("Transfer Interrupted"); 
    System.out.println(transferDbReader.a.h());
  }
  
  private static void a(AbstractConsole paramAbstractConsole, Connector paramConnector, TableDependency paramTableDependency) {}
}
