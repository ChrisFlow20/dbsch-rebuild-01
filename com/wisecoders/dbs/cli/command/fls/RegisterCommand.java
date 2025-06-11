package com.wisecoders.dbs.cli.command.fls;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dialogs.system.FloatingLicenseTask;
import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Features;
import com.wisecoders.dbs.sys.InputStreamUtil;
import com.wisecoders.dbs.sys.Keys;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.SRx;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import javafx.util.Pair;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;

@DoNotObfuscate
public class RegisterCommand extends Command {
  final SRx b;
  
  private final Options c;
  
  public RegisterCommand() {
    this.b = new SRx(RegisterCommand.class, this);
    this.c = new Options();
    this.c.addOption("f", "file", true, "The license file");
    this.c.addOption("s", "status", false, "Show current license");
    this.c.addOption("H", "host", true, "Subscribe to Floating License Server, located on host");
    this.c.addOption("P", "port", true, "Subscribe to Floating License Server, running on port");
    this.c.addOption("N", "name", true, "Subscribe to Floating License Server, as username");
    this.c.addOption("h", "help", false, "Help");
  }
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    CommandLine commandLine;
    String[] arrayOfString = AbstractConsole.c(paramString2);
    try {
      DefaultParser defaultParser = new DefaultParser();
      commandLine = defaultParser.parse(this.c, arrayOfString);
    } catch (MissingOptionException missingOptionException) {
      paramAbstractConsole.a(missingOptionException.getLocalizedMessage(), new Object[0]);
      (new HelpFormatter()).printHelp("register", this.c);
      return;
    } 
    if (commandLine.hasOption("f")) {
      String str = commandLine.getOptionValue("f");
      if (str == null)
        throw new ParameterException(); 
      File file = Paths.get(StringUtil.removeQuotes(str.trim()), new String[0]).toFile();
      if (!file.exists())
        throw new ParameterException("Cannot find file '" + String.valueOf(file) + "'. Please use '/' as folder separator."); 
      switch (RegisterCommand$1.a[register(file).ordinal()]) {
        case 1:
          paramAbstractConsole.a(FLSHandler.isFloatingLicense() ? this.b.H("succeedFloating") : this.b.H("succeedNoFloating"), new Object[0]);
          break;
        case 2:
          paramAbstractConsole.a(this.b.H("trialActive"), new Object[0]);
          break;
        case 3:
        case 4:
          paramAbstractConsole.a(this.b.H("expired"), new Object[0]);
          break;
        case 5:
        case 6:
        case 7:
          paramAbstractConsole.a(this.b.H("failed"), new Object[0]);
          break;
      } 
    } else if (commandLine.hasOption("H")) {
      Keys.g.a(commandLine.getOptionValue("N"));
      Keys.h.a(commandLine.getOptionValue("H"));
      Keys.i.a(commandLine.getOptionValue("P"));
      Pair pair = FloatingLicenseTask.b();
      if (((Boolean)pair.getKey()).booleanValue()) {
        paramAbstractConsole.a("Server registered successfully", new Object[0]);
      } else {
        paramAbstractConsole.a("Failed to register to Floating License Server. " + String.valueOf(pair.getValue()), new Object[0]);
      } 
    } else if (commandLine.hasOption("s")) {
      paramAbstractConsole.a(License.a().a(false), new Object[0]);
    } else {
      paramAbstractConsole.a("Load a DbSchema license.", new Object[0]);
      (new HelpFormatter()).printHelp("license", this.c);
    } 
  }
  
  @GroovyMethod
  public Features register(File paramFile) {
    FileInputStream fileInputStream = new FileInputStream(paramFile);
    try {
      String str = InputStreamUtil.a(fileInputStream, 10000);
      if (StringUtil.isFilledTrim(str)) {
        String str1 = str.trim();
        Keys.d.a(str1);
        if (!str1.equals(Keys.d.a()))
          throw new ParameterException(this.b.H("saveFailed")); 
      } else {
        throw new ParameterException(this.b.H("fillFields"));
      } 
      Features features = License.a().h();
      fileInputStream.close();
      return features;
    } catch (Throwable throwable) {
      try {
        fileInputStream.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
}
