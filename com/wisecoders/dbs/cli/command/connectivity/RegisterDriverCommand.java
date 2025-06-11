package com.wisecoders.dbs.cli.command.connectivity;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class RegisterDriverCommand extends Command {
  public static final Pattern PATTERN_DRIVER = Pattern.compile("\\s*(\\S*)\\s+(\\S*)\\s+(\\S*)\\s*(.*)", 2);
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    Matcher matcher;
    if (paramString2.length() == 0 || !(matcher = PATTERN_DRIVER.matcher(paramString2)).matches())
      throw new ParameterException("Invalid command. Expected : <dbms> <class> <jdbcUrl>"); 
    DriverManager driverManager = DriverManager.a(matcher.group(1));
    driverManager.a(matcher.group(2), matcher.group(3), "", "", "", "", "", "", "", "", "", true);
  }
}
