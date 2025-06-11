package com.wisecoders.dbs.data.model.data;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Keys;
import com.wisecoders.dbs.sys.License;
import groovy.lang.MissingPropertyException;
import groovy.lang.Script;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@DoNotObfuscate
public abstract class GroovyScriptBaseClass extends Script {
  @GroovyMethod
  public void loadKey(String paramString) {
    Keys.d.a(paramString);
    println("Result " + String.valueOf(License.a().g()));
  }
  
  private Object a(String paramString, Class paramClass) {
    if (getBinding() != null) {
      Object object = getBinding().getVariable(paramString);
      if (object == null)
        throw new MissingPropertyException("Missing " + paramString + " variable in GroovyScriptBaseClass."); 
      if (paramClass.isInstance(object))
        return object; 
      throw new MissingPropertyException("Wrong type " + paramString + " variable in GroovyScriptBaseClass.");
    } 
    throw new MissingPropertyException("Missing binding in GroovyScriptBaseClass.");
  }
  
  public void print(Object paramObject) {
    Object object = getProperty("out");
    if (object instanceof PrintStream) {
      ((PrintStream)object).print(paramObject);
    } else {
      super.print(paramObject);
    } 
  }
  
  public void println(Object paramObject) {
    Object object = getProperty("out");
    if (object instanceof PrintStream) {
      ((PrintStream)object).println(paramObject);
    } else {
      super.print(paramObject);
    } 
  }
  
  public void println() {
    Object object = getProperty("out");
    if (object instanceof PrintStream) {
      ((PrintStream)object).println();
    } else {
      super.println();
    } 
  }
  
  private static final String[] a = new String[] { "Bytes", "Kb", "Mb", "Gb", "bT", "Pb", "Eb" };
  
  @GroovyMethod
  public static String formatBytes(long paramLong) {
    DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    decimalFormat.setMaximumFractionDigits(6);
    for (byte b = 6; b > 0; b--) {
      double d = Math.pow(1024.0D, b);
      if (paramLong > d || b == 2)
        return decimalFormat.format(paramLong / d) + " " + decimalFormat.format(paramLong / d); 
    } 
    return Long.toString(paramLong);
  }
  
  @GroovyMethod
  public String evaluate(String paramString) {
    return (new SimpleStatement(paramString)).toString();
  }
  
  @GroovyMethod
  public void parseDDLAndCompareWithExpectedResult(String paramString1, String paramString2, String paramString3) {
    paramString2 = paramString2.trim();
    paramString3 = paramString3.trim();
    Project project = new Project("Test", paramString1);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(byteArrayOutputStream, true, StandardCharsets.UTF_8);
    (new DDLParser(project, printStream)).parse(paramString2);
    StringWriter stringWriter = new StringWriter();
    SyncPair syncPair = new SyncPair(project, new Project(project.getName(), paramString1));
    AlterScript alterScript = syncPair.generateCommitScript(paramString1, null, SyncSide.left, (Dbms.get(project.getDbId())).alterForeignKey.c());
    alterScript.writeToOutputStream(new PrintWriter(stringWriter));
    String str = stringWriter.toString().trim().replaceAll("\t", "");
    String[] arrayOfString1 = str.split("\n");
    String[] arrayOfString2 = paramString3.split("\n");
    boolean bool = false;
    if (arrayOfString1.length == arrayOfString2.length) {
      for (byte b = 0; b < arrayOfString1.length; b++) {
        String str1 = arrayOfString1[b];
        String str2 = arrayOfString2[b];
        if (!str1.replaceAll(" ", "").equals(str2.replaceAll(" ", ""))) {
          print("Line " + b + "\nInput: " + str1 + "\nOutput: " + paramString3);
          bool = true;
        } 
      } 
    } else {
      print("Output\n" + str + "\n\nExpected\n" + paramString3);
      bool = true;
    } 
    if (!bool)
      println("Ok"); 
  }
}
