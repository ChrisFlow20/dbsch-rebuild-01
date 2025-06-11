package com.wisecoders.dbs.cli.command.fls;

import com.sun.net.httpserver.HttpServer;
import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.command.connectivity.DownloadDriverCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.dialogs.system.HttpResponseDetails;
import com.wisecoders.dbs.dialogs.system.HttpsUtils;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.License;
import java.net.InetSocketAddress;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

@DoNotObfuscate
public class FLSCommand extends Command {
  private final String b;
  
  private final String c;
  
  private final String d;
  
  private final String e;
  
  private final String f;
  
  private final String g;
  
  private final String h;
  
  private final String i;
  
  private final Options j;
  
  private static HttpServer k;
  
  public FLSCommand() {
    this.j = new Options();
    this.j.addOption(this.b = "s", "start", false, "Start the license server");
    this.j.addOption(this.c = "H", "host", true, "The license server host, used by the log, debug and status commands");
    this.j.addOption(this.d = "p", "port", true, "License server port, default 8080");
    this.j.addOption(this.e = "l", "log", false, "Show server log");
    this.j.addOption(this.f = "i", "info", false, "Show License Server information");
    this.j.addOption(this.g = "d", "debug", true, "Debug server with parameter");
    this.j.addOption(this.h = "q", "quit", false, "Stop the license server");
    this.j.addOption(this.i = "h", "help", false, "Print this help");
  }
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    CommandLine commandLine;
    String[] arrayOfString = AbstractConsole.c(paramString2);
    try {
      DefaultParser defaultParser = new DefaultParser();
      commandLine = defaultParser.parse(this.j, arrayOfString);
    } catch (MissingOptionException missingOptionException) {
      paramAbstractConsole.a(missingOptionException.getLocalizedMessage(), new Object[0]);
      (new HelpFormatter()).printHelp("server start", this.j);
      return;
    } 
    boolean bool = commandLine.hasOption(this.d) ? Integer.parseInt(commandLine.getOptionValue(this.d)) : true;
    if (commandLine.hasOption(this.h)) {
      if (k == null) {
        paramAbstractConsole.d("License server does not run.", new Object[0]);
      } else {
        k.stop(1);
        k = null;
        paramAbstractConsole.d("Server stopped", new Object[0]);
      } 
    } else if (commandLine.hasOption(this.b)) {
      try {
        DownloadDriverCommand.downloadH2DriverIfMissing();
      } catch (Throwable throwable) {
        paramAbstractConsole.a("Error downloading H2 JDBC driver from dbschema.com. " + String.valueOf(throwable), new Object[0]);
      } 
      if (k != null) {
        paramAbstractConsole.d("License Server already running. Please stop it first.", new Object[0]);
        return;
      } 
      if (!FLSHandler.isFloatingLicense()) {
        paramAbstractConsole.a("No floating license loaded. Please load it using the command 'register'.", new Object[0]);
        return;
      } 
      paramAbstractConsole.a(a(bool), new Object[0]);
    } else if (commandLine.hasOption(this.f) || commandLine.hasOption(this.g) || commandLine.hasOption(this.e)) {
      String str = commandLine.getOptionValue(this.c, "localhost");
      StringBuilder stringBuilder = new StringBuilder();
      if (commandLine.hasOption(this.f)) {
        if (!stringBuilder.isEmpty())
          stringBuilder.append("&"); 
        stringBuilder.append("INFO").append("=true");
      } 
      if (commandLine.hasOption(this.e)) {
        if (!stringBuilder.isEmpty())
          stringBuilder.append("&"); 
        stringBuilder.append("LOG").append("=true");
      } 
      String[] arrayOfString1 = commandLine.getOptionValues(this.g);
      if (arrayOfString1 != null)
        for (String str1 : arrayOfString1) {
          if (!stringBuilder.isEmpty())
            stringBuilder.append("&"); 
          stringBuilder.append(str1);
        }  
      HttpPost httpPost = new HttpPost("http://" + str + ":" + bool + "/fls");
      httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
      httpPost.setHeader("charset", "utf-8");
      httpPost.setEntity((HttpEntity)new StringEntity(stringBuilder.toString()));
      CloseableHttpResponse closeableHttpResponse = HttpsUtils.a((HttpUriRequest)httpPost);
      try {
        HttpResponseDetails httpResponseDetails = HttpResponseDetails.a(closeableHttpResponse);
        paramAbstractConsole.a("" + httpResponseDetails.a() + " " + httpResponseDetails.a(), new Object[0]);
        if (closeableHttpResponse != null)
          closeableHttpResponse.close(); 
      } catch (Throwable throwable) {
        if (closeableHttpResponse != null)
          try {
            closeableHttpResponse.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }  
        throw throwable;
      } 
    } else {
      paramAbstractConsole.a("Start and manage the Floating License Server.\nA floating license should be previously loaded using the 'register' command.\nUse '-s' to start the server, '-l' to get the log, '-S' for status.", new Object[0]);
      (new HelpFormatter()).printHelp("license server", this.j);
    } 
  }
  
  private static String a(int paramInt) {
    k = HttpServer.create(new InetSocketAddress(paramInt), 0);
    k.createContext("/fls", new FLSHandler());
    k.setExecutor(null);
    k.start();
    return "License Server started.\nMaximal " + (License.a()).f + " concurrent user" + (((License.a()).f > 1) ? "s." : ".");
  }
}
