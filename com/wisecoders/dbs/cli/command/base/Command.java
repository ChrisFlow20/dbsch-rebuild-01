package com.wisecoders.dbs.cli.command.base;

import com.wisecoders.dbs.cli.command.groovy.GroovyBaseClass;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public abstract class Command implements Comparable {
  private String b;
  
  private Pattern c;
  
  private String d;
  
  private String e;
  
  private String f;
  
  private String g;
  
  private int h;
  
  protected boolean a = false;
  
  public CommandType getType() {
    return CommandType.b;
  }
  
  public void setShortDescription(String paramString) {
    this.e = paramString;
  }
  
  public String getShortDescription() {
    return this.e;
  }
  
  public void setLongDescription(String paramString) {
    this.f = paramString;
  }
  
  public String getLongDescription() {
    return this.f;
  }
  
  public void setGroup(String paramString) {
    this.g = paramString;
  }
  
  public String getGroup() {
    return this.g;
  }
  
  public void setUsage(String paramString) {
    this.d = paramString;
  }
  
  public String getUsage() {
    return this.d;
  }
  
  public int getOrder() {
    return this.h;
  }
  
  public void setOrder(int paramInt) {
    this.h = paramInt;
  }
  
  public void setKeyword(String paramString) {
    this.b = paramString;
    if (paramString != null) {
      StringBuilder stringBuilder = new StringBuilder();
      for (String str : paramString.split(" ")) {
        if (stringBuilder.length() > 0)
          stringBuilder.append("\\s+"); 
        stringBuilder.append(str);
      } 
      stringBuilder.append("($|\\s)(.*)");
      this.c = Pattern.compile(stringBuilder.toString(), 34);
    } 
  }
  
  public String getKeyword() {
    return this.b;
  }
  
  public boolean matchesInput(String paramString) {
    Matcher matcher = this.c.matcher(paramString);
    return matcher.matches();
  }
  
  public String getCommandParameters(String paramString) {
    Matcher matcher;
    if ((matcher = this.c.matcher(paramString)).matches())
      return matcher.group(2).trim(); 
    return null;
  }
  
  public String toString() {
    return this.b;
  }
  
  public int compareTo(Command paramCommand) {
    int i = (getKeyword().split(" ")).length;
    int j = (paramCommand.getKeyword().split(" ")).length;
    return Integer.compare(i, j);
  }
  
  public void resetInterrupt() {
    this.a = false;
  }
  
  public void interrupt() {
    GroovyBaseClass.a();
    this.a = true;
  }
  
  public boolean isInterrupt() {
    return this.a;
  }
  
  public String getDebugInfo() {
    return null;
  }
  
  public abstract void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2);
}
