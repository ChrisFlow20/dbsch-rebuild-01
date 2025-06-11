package com.wisecoders.dbs.cli.command.system;

import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.MailUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

@DoNotObfuscate
public class MailCommand extends Command {
  private final Options b = new Options();
  
  public MailCommand() {
    this.b.addOption("r", "recipients", true, "Email to recipients");
    this.b.addOption("s", "subject", true, "Mail subject");
    this.b.addOption("b", "body", true, "Mail body");
    this.b.addOption("v", "validate", true, "iMap server to validate emails against");
    this.b.addOption("a", "attach", true, "Wildcard file names to attach");
    this.b.addOption("e", "embed", true, "Embed files in body");
    this.b.addOption("d", "delete", false, "Delete attached files");
    this.b.addOption("h", "help", false, "Show help.");
  }
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    String[] arrayOfString1 = AbstractConsole.c(paramString2);
    DefaultParser defaultParser = new DefaultParser();
    CommandLine commandLine = defaultParser.parse(this.b, arrayOfString1);
    if (commandLine.hasOption("h")) {
      (new HelpFormatter()).printHelp("sendmail", this.b);
      return;
    } 
    if (!commandLine.hasOption("r"))
      throw new ParameterException("Missing to recipients"); 
    paramAbstractConsole.t();
    String[] arrayOfString2 = commandLine.getOptionValues("r");
    if (commandLine.hasOption("v"))
      try {
        a(paramAbstractConsole, commandLine.getOptionValue("v"), arrayOfString2);
      } catch (Throwable throwable) {
        Log.a("Error in Mail Command", throwable);
        throwable.printStackTrace();
      }  
    String str = commandLine.hasOption("s") ? String.join("", (CharSequence[])commandLine.getOptionValues("s")) : "Mail from Database Admin";
    StringBuilder stringBuilder = new StringBuilder();
    if (commandLine.hasOption("b"))
      stringBuilder.append(commandLine.getOptionValue("b")); 
    ArrayList arrayList = new ArrayList();
    if (commandLine.hasOption("a"))
      arrayList.addAll(FileUtils.a(commandLine.getOptionValues("a"))); 
    paramAbstractConsole.d("Sending email... ", new Object[0]);
    if (commandLine.hasOption("e")) {
      for (File file : FileUtils.a(commandLine.getOptionValues("e"))) {
        byte[] arrayOfByte = Files.readAllBytes(file.toPath());
        stringBuilder.append(new String(arrayOfByte, StandardCharsets.UTF_8));
        if (commandLine.hasOption("d"))
          FileUtils.b(file); 
      } 
      MailUtil.a(arrayOfString2, str, stringBuilder.toString(), arrayList);
    } else {
      if (stringBuilder.isEmpty())
        stringBuilder.append("Mail from Database Admin"); 
      MailUtil.b(arrayOfString2, str, stringBuilder.toString(), arrayList);
    } 
    paramAbstractConsole.a("done.", new Object[0]);
    if (commandLine.hasOption("d"))
      for (File file : arrayList)
        FileUtils.b(file);  
  }
  
  private static void a(AbstractConsole paramAbstractConsole, String paramString, String[] paramArrayOfString) {
    for (String str1 : paramArrayOfString) {
      String str2 = str1.substring(0, str1.indexOf("@"));
      String str3 = a(paramString + paramString);
      if (str3.isEmpty())
        throw new InvalidParameterException("No response from IMAP server " + paramString + "."); 
      if (str3.contains("<Disabled>False</Disabled>")) {
        paramAbstractConsole.d("Address '" + str1 + "' is valid.", new Object[0]);
      } else if (str3.contains("<Disabled>True</Disabled>")) {
        if (str3.contains("<User_found>False</User_found>"))
          throw new InvalidParameterException("Address '" + str1 + "' not found on IMAP sever " + paramString + ". Response was: " + str3); 
        throw new InvalidParameterException("Address '" + str1 + "' disabled on IMAP server " + paramString + ". Response was: " + str3);
      } 
    } 
  }
  
  private static String a(String paramString) {
    InputStream inputStream;
    System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
    System.setProperty("sun.net.client.defaultReadTimeout", "5000");
    HttpURLConnection httpURLConnection = (HttpURLConnection)(new URL(paramString)).openConnection();
    int i = httpURLConnection.getResponseCode();
    if (200 <= i && i <= 299) {
      inputStream = httpURLConnection.getInputStream();
    } else {
      inputStream = httpURLConnection.getErrorStream();
    } 
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    StringBuilder stringBuilder = new StringBuilder();
    String str;
    while ((str = bufferedReader.readLine()) != null)
      stringBuilder.append(str); 
    bufferedReader.close();
    httpURLConnection.disconnect();
    return stringBuilder.toString();
  }
}
