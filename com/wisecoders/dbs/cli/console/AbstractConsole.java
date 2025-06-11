package com.wisecoders.dbs.cli.console;

import com.wisecoders.dbs.cli.command.Dictionary;
import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.command.system.ExecuteCommand;
import com.wisecoders.dbs.cli.command.system.Verbosity;
import com.wisecoders.dbs.cli.csv.CSVStringBuilder;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.schema.CLIConnectorManager;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.MailUtil;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.mail.MessagingException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public abstract class AbstractConsole {
  private Command a;
  
  private String b = "Not Connected > ";
  
  private PrintStream c = System.out;
  
  private boolean d = false;
  
  public abstract String a();
  
  public abstract void b();
  
  public void c() {
    this.d = true;
  }
  
  public void d() {
    try {
      String str;
      while ((str = a()) != null && !this.d) {
        if (str.isEmpty() || str.startsWith("//") || str.startsWith("#") || str.startsWith("--"))
          continue; 
        Command command = Dictionary.a(str);
        if (command == null) {
          a("Not a command '" + StringUtil.cutOfWithDots(str, 30) + "'", new Object[0]);
          continue;
        } 
        a(command, str);
      } 
    } catch (IOException iOException) {
      Log.a("Error in Processor", iOException);
      d(iOException.toString(), new Object[0]);
    } catch (Throwable throwable) {
      Log.a("Error in Processor", throwable);
      throwable.printStackTrace();
    } 
  }
  
  public void e() {
    if (this.a != null)
      this.a.interrupt(); 
  }
  
  public void f() {
    if (this.a != null) {
      String str = this.a.getDebugInfo();
      System.out.println((str != null) ? str : "No debug information");
    } 
  }
  
  public void a(Command paramCommand, String paramString) {
    boolean bool = false;
    try {
      if (Sys.B.enableEcho.b())
        a("> " + paramString, new Object[0]); 
      this.a = paramCommand;
      String str = paramCommand.getType().b(paramString);
      str = paramCommand.getCommandParameters(str);
      str = Sys.B.cli.o(str);
      Log.c(String.valueOf(paramCommand) + " " + String.valueOf(paramCommand));
      paramCommand.resetInterrupt();
      paramCommand.process(this, paramCommand.getKeyword(), str);
      this.a = null;
      s();
      a(true);
    } catch (ParameterException parameterException) {
      a(paramString, parameterException);
      if (parameterException.getMessage() != null) {
        c("Error: " + parameterException.getMessage(), new Object[0]);
      } else if (paramCommand.getUsage() == null) {
        c("Invalid parameters. ", new Object[0]);
      } else {
        c("Invalid parameters. Usage: " + paramCommand.getUsage(), new Object[0]);
      } 
      bool = true;
      Log.a("Error in Processor", parameterException);
    } catch (SQLException sQLException) {
      a(paramString, sQLException);
      a(sQLException.getLocalizedMessage(), new Object[0]);
      if (sQLException.getCause() != sQLException && sQLException.getCause() != null)
        d(sQLException.getCause().toString(), new Object[0]); 
      bool = true;
      Log.a("Error in Processor", sQLException);
    } catch (Throwable throwable) {
      throwable.printStackTrace();
      a(paramString, throwable);
      a(throwable.toString(), new Object[0]);
      if (throwable.getCause() != throwable && throwable.getCause() != null)
        d(throwable.getCause().toString(), new Object[0]); 
      bool = true;
      Log.a("Error in Processor", throwable);
    } 
    if (bool && !Sys.B.ignoreErrors.b())
      u(); 
  }
  
  private void u() {
    try {
      MailUtil.a("DbSchemaCLI execution ERRORS (ignore_errors=false) !!! Please read", o());
    } catch (Throwable throwable) {
      Log.a("Error in Processor", throwable);
      throwable.printStackTrace();
    } 
    System.exit(1);
  }
  
  public void g() {
    File file = Sys.t.toFile();
    if (file.exists()) {
      a(file);
    } else {
      try {
        FileWriter fileWriter = new FileWriter(file);
        try {
          fileWriter.write("# Add connections and initialization scripts here\n#connection mysql1 -d MySql -u root -p secret -h localhost -D db1\n");
          fileWriter.close();
        } catch (Throwable throwable) {
          try {
            fileWriter.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (IOException iOException) {}
    } 
  }
  
  public void a(File paramFile) {
    if (paramFile.exists()) {
      try {
        (new ExecuteCommand()).executeScript(this, paramFile, null);
      } catch (Throwable throwable) {
        Log.a("Error in Processor", throwable);
        a("Error processing file " + paramFile.getAbsolutePath(), throwable);
        throwable.printStackTrace();
      } 
    } else {
      a("File '%s' not found.", new Object[] { paramFile.getAbsolutePath() });
    } 
  }
  
  public void a(String paramString) {
    this.b = paramString;
  }
  
  public String h() {
    return this.b;
  }
  
  public void a(PrintStream paramPrintStream) {
    if (paramPrintStream != null)
      this.c = paramPrintStream; 
  }
  
  public PrintStream i() {
    return this.c;
  }
  
  private Path e = Paths.get(".", new String[0]);
  
  private final Verbosity f = Verbosity.a;
  
  private boolean g = true;
  
  private final StringBuilder h = new StringBuilder();
  
  private File i;
  
  private OutputStream j;
  
  private static final int k = 1000;
  
  private static final String l = "Get all rows by using the spool command to output result to file.";
  
  private String m;
  
  public boolean j() {
    return this.f.a();
  }
  
  public Path k() {
    return this.e;
  }
  
  public void a(Path paramPath) {
    if (paramPath != null)
      this.e = paramPath; 
  }
  
  public void a(String paramString, Object... paramVarArgs) {
    i().println((paramVarArgs.length > 0) ? String.format(paramString, paramVarArgs) : paramString);
  }
  
  public void b(String paramString, Object... paramVarArgs) {
    i().println((paramVarArgs.length > 0) ? String.format(paramString, paramVarArgs) : paramString);
  }
  
  public void l() {
    a("", new Object[0]);
  }
  
  public void a(Throwable paramThrowable) {
    if (paramThrowable != null)
      a(b(paramThrowable), new Object[0]); 
  }
  
  public void a(Object paramObject, Object... paramVarArgs) {
    d(String.valueOf(paramObject) + "\n", paramVarArgs);
  }
  
  public void a(Object paramObject) {
    String str = String.format("%-9s ", new Object[] { paramObject.toString() + " >" });
    d(str, new Object[0]);
    try {
      d(str);
    } catch (IOException iOException) {
      i().println(iOException.toString());
    } 
  }
  
  public void c(String paramString, Object... paramVarArgs) {
    if (this.f.a())
      if (paramVarArgs.length > 0) {
        b(String.format(paramString + "\n", paramVarArgs), new Object[0]);
      } else {
        b(paramString + "\n", new Object[0]);
      }  
  }
  
  public void d(String paramString, Object... paramVarArgs) {
    String str = (paramVarArgs.length > 0) ? String.format(paramString, paramVarArgs) : paramString;
    try {
      d(str);
    } catch (IOException iOException) {}
    i().print(str);
  }
  
  public static String b(Throwable paramThrowable) {
    ArrayList<Throwable> arrayList = new ArrayList();
    StringBuilder stringBuilder = new StringBuilder();
    Throwable throwable = paramThrowable;
    byte b = 4;
    while (throwable != null && !arrayList.contains(throwable) && --b > 0) {
      stringBuilder.append(paramThrowable).append("\n").append(a(throwable.getStackTrace()));
      arrayList.add(throwable);
      throwable = throwable.getCause();
    } 
    return stringBuilder.toString();
  }
  
  private static String a(StackTraceElement[] paramArrayOfStackTraceElement) {
    StringBuilder stringBuilder = new StringBuilder();
    for (StackTraceElement stackTraceElement : paramArrayOfStackTraceElement) {
      stringBuilder.append("\tat ");
      stringBuilder.append(stackTraceElement.toString());
      stringBuilder.append("\n");
    } 
    return stringBuilder.toString();
  }
  
  public void a(ResultSet paramResultSet, Connector paramConnector, boolean paramBoolean) {
    if (v()) {
      b(paramResultSet, paramConnector, paramBoolean);
    } else {
      a(paramResultSet);
    } 
  }
  
  private void a(ResultSet paramResultSet) {
    ResultSetMetaData resultSetMetaData = paramResultSet.getMetaData();
    int i = resultSetMetaData.getColumnCount();
    int[] arrayOfInt1 = new int[i], arrayOfInt2 = new int[i];
    String[] arrayOfString = new String[i];
    byte b;
    for (b = 0; b < i; b++) {
      arrayOfString[b] = resultSetMetaData.getColumnName(b + 1);
      arrayOfInt1[b] = Math.max(arrayOfInt1[b], (arrayOfString[b] != null) ? arrayOfString[b].length() : 0);
      arrayOfInt2[b] = resultSetMetaData.getColumnType(b + 1);
    } 
    b = 0;
    boolean bool = false;
    String[][] arrayOfString1 = new String[1000][i];
    while (paramResultSet.next() && !bool) {
      if (b < arrayOfString1.length) {
        for (byte b1 = 0; b1 < i; b1++) {
          String str = a(paramResultSet, b1 + 1);
          arrayOfString1[b][b1] = str;
          arrayOfInt1[b1] = Math.max(arrayOfInt1[b1], (str != null) ? str.length() : 0);
        } 
        b++;
        continue;
      } 
      bool = true;
    } 
    if (b == 0) {
      c("No data", new Object[0]);
    } else if ("csv".equalsIgnoreCase(Sys.B.screenFormat.c_())) {
      l();
      a(arrayOfString);
      for (byte b1 = 0; b1 < b; b1++)
        a(arrayOfString1[b1]); 
      c("Selected %d rows. " + (bool ? "Get all rows by using the spool command to output result to file." : ""), new Object[] { Integer.valueOf(b) });
    } else if (b == 1 && "auto".equalsIgnoreCase(Sys.B.screenFormat.c_())) {
      a(arrayOfString, arrayOfString1[0]);
    } else {
      a(arrayOfString, arrayOfInt1, (int[])null);
      a(arrayOfInt1);
      for (byte b1 = 0; b1 < b; b1++)
        a(arrayOfString1[b1], arrayOfInt1, arrayOfInt2); 
      c("Selected %d rows. " + (bool ? "Get all rows by using the spool command to output result to file." : ""), new Object[] { Integer.valueOf(b) });
    } 
  }
  
  private String a(ResultSet paramResultSet, int paramInt) {
    Object object = paramResultSet.getObject(paramInt);
    if (object instanceof java.sql.Timestamp)
      return (new SimpleDateFormat(Sys.B.timestampFormat.c_())).format(object); 
    if (object instanceof java.util.Date)
      return (new SimpleDateFormat(Sys.B.dateFormat.c_())).format(object); 
    if (object != null)
      return object.toString(); 
    return "";
  }
  
  public void m() {
    this.m = null;
  }
  
  private void b(ResultSet paramResultSet, Connector paramConnector, boolean paramBoolean) {
    ResultSetMetaData resultSetMetaData = paramResultSet.getMetaData();
    int i = resultSetMetaData.getColumnCount();
    int[] arrayOfInt = new int[i];
    boolean bool = "html".equalsIgnoreCase(Sys.B.spoolFormat.c_());
    if (bool)
      d("<table>"); 
    if (paramBoolean && Sys.B.spoolHeader.b()) {
      CSVStringBuilder cSVStringBuilder = new CSVStringBuilder(Sys.B.spoolSeparator.c_().charAt(0), Sys.B.spoolQuotes.b());
      if (bool)
        cSVStringBuilder.a("<tr>"); 
      if (Sys.B.spoolDb.b() && CLIConnectorManager.c())
        cSVStringBuilder.a(bool ? "<th>database</th>" : "database"); 
      for (byte b1 = 0; b1 < i; b1++) {
        if (bool)
          cSVStringBuilder.a("<th>"); 
        cSVStringBuilder.a(resultSetMetaData.getColumnName(b1 + 1));
        if (bool)
          cSVStringBuilder.a("</th>"); 
        arrayOfInt[b1] = resultSetMetaData.getColumnType(b1 + 1);
      } 
      if (bool)
        cSVStringBuilder.a("</tr>"); 
      if (!cSVStringBuilder.toString().equalsIgnoreCase(this.m))
        d(cSVStringBuilder.toString()); 
      this.m = cSVStringBuilder.toString();
    } 
    byte b = 0;
    while (paramResultSet.next()) {
      if (!b)
        b("Spooling...", new Object[0]); 
      CSVStringBuilder cSVStringBuilder = new CSVStringBuilder(Sys.B.spoolSeparator.c_().charAt(0), Sys.B.spoolQuotes.b());
      if (bool)
        cSVStringBuilder.a("<tr>"); 
      if (Sys.B.spoolDb.b() && CLIConnectorManager.c())
        cSVStringBuilder.a(bool ? ("<td>" + paramConnector.getName() + "</td>") : paramConnector.getName()); 
      for (byte b1 = 0; b1 < i; b1++) {
        if (bool)
          cSVStringBuilder.a("<td>"); 
        cSVStringBuilder.a(a(paramResultSet, b1 + 1));
        if (bool)
          cSVStringBuilder.a("</td>"); 
      } 
      if (bool)
        cSVStringBuilder.a("</tr>"); 
      d(cSVStringBuilder.toString());
      b++;
    } 
    if (bool)
      d("</table>"); 
    b("Selected %d rows.", new Object[] { Integer.valueOf(b) });
  }
  
  private void a(String[] paramArrayOfString1, String[] paramArrayOfString2) {
    StringBuilder stringBuilder = new StringBuilder("\t");
    for (byte b = 0; b < paramArrayOfString2.length && b < paramArrayOfString1.length; b++) {
      if (b > 0)
        stringBuilder.append("; "); 
      stringBuilder.append(paramArrayOfString1[b]).append(" : ");
      if (paramArrayOfString2[b] != null) {
        stringBuilder.append(paramArrayOfString2[b]);
      } else {
        stringBuilder.append("NULL");
      } 
    } 
    a(stringBuilder.toString(), new Object[0]);
  }
  
  private void a(String[] paramArrayOfString, int[] paramArrayOfint1, int[] paramArrayOfint2) {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < paramArrayOfString.length; b++) {
      if (b > 0)
        stringBuilder.append(" | "); 
      String str1 = (paramArrayOfString[b] != null) ? paramArrayOfString[b] : "";
      String str2 = String.format("%" + ((paramArrayOfint2 != null && a(paramArrayOfint2[b])) ? "" : "-") + paramArrayOfint1[b] + "s ", new Object[] { str1 });
      stringBuilder.append(str2);
    } 
    a(stringBuilder.toString(), new Object[0]);
  }
  
  private void a(String[] paramArrayOfString) {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < paramArrayOfString.length; b++) {
      if (b > 0)
        stringBuilder.append(","); 
      String str = (paramArrayOfString[b] != null) ? paramArrayOfString[b] : "";
      stringBuilder.append(str);
    } 
    a(stringBuilder.toString(), new Object[0]);
  }
  
  private void a(int[] paramArrayOfint) {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < paramArrayOfint.length; b++) {
      if (b > 0)
        stringBuilder.append("-+-"); 
      stringBuilder.append("-".repeat(Math.max(0, paramArrayOfint[b] + 1)));
    } 
    a(stringBuilder.toString(), new Object[0]);
  }
  
  private static boolean a(int paramInt) {
    return (4 == paramInt || 2 == paramInt || -5 == paramInt || 16 == paramInt || -7 == paramInt || 91 == paramInt);
  }
  
  public void a(boolean paramBoolean) {
    this.g = paramBoolean;
  }
  
  public void a(String paramString, Throwable paramThrowable) {
    a(false);
    this.h.append(paramString).append(" : ").append(paramThrowable.getLocalizedMessage()).append("\n");
    if (paramThrowable instanceof MessagingException && ((MessagingException)paramThrowable).getNextException() != null)
      this.h.append(((MessagingException)paramThrowable).getNextException()).append("\n"); 
    this.h.append("\n");
  }
  
  public boolean n() {
    return !this.h.isEmpty();
  }
  
  public String o() {
    return !this.h.isEmpty() ? this.h.toString() : null;
  }
  
  public void p() {
    if (!this.h.isEmpty())
      this.h.delete(0, this.h.length()); 
  }
  
  public boolean q() {
    return this.g;
  }
  
  private boolean b(File paramFile) {
    if (paramFile != null)
      try {
        boolean bool = paramFile.exists();
        if (bool)
          return paramFile.delete(); 
        return (paramFile.createNewFile() && paramFile.delete());
      } catch (Exception exception) {
        return false;
      }  
    return false;
  }
  
  public boolean b(String paramString) {
    t();
    if (paramString != null && !paramString.trim().isEmpty())
      if ("/dev/null".equals(paramString)) {
        this.j = new AbstractConsole$1(this);
        this.i = null;
      } else {
        this.i = FileSystems.getDefault().getPath(".", new String[0]).resolve(paramString).toFile();
        if (!b(this.i)) {
          this.i = null;
          return false;
        } 
        if (paramString.endsWith(".zip")) {
          ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(this.i));
          zipOutputStream.putNextEntry(new ZipEntry(this.i.getName().substring(0, this.i.getName().length() - ".zip".length()) + ".csv"));
          this.j = new AbstractConsole$2(this, zipOutputStream, 52718592, zipOutputStream);
        } else {
          this.j = new BufferedOutputStream(new FileOutputStream(this.i), 52718592);
        } 
      }  
    return true;
  }
  
  public File r() {
    return this.i;
  }
  
  private boolean v() {
    return (this.j != null);
  }
  
  private void d(String paramString) {
    if (this.j != null) {
      String str = ((paramString != null) ? paramString : "null") + "\n";
      this.j.write(str.getBytes());
    } 
  }
  
  void s() {
    if (this.j != null)
      this.j.flush(); 
  }
  
  public void t() {
    try {
      if (this.j != null)
        this.j.close(); 
    } catch (Throwable throwable) {
      a(throwable);
    } finally {
      this.j = null;
      this.i = null;
    } 
  }
  
  public static String[] c(String paramString) {
    if (StringUtil.isEmpty(paramString))
      return new String[0]; 
    CSVFormat cSVFormat = CSVFormat.Builder.create().setQuote('"').setDelimiter(' ').build();
    CSVParser cSVParser = CSVParser.parse(paramString, cSVFormat);
    try {
      CSVRecord cSVRecord = cSVParser.getRecords().get(0);
      String[] arrayOfString1 = new String[cSVRecord.size()];
      for (byte b = 0; b < cSVRecord.size(); b++)
        arrayOfString1[b] = cSVRecord.get(b); 
      String[] arrayOfString2 = arrayOfString1;
      if (cSVParser != null)
        cSVParser.close(); 
      return arrayOfString2;
    } catch (Throwable throwable) {
      if (cSVParser != null)
        try {
          cSVParser.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
  }
}
