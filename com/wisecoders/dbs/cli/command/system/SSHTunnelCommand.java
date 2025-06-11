package com.wisecoders.dbs.cli.command.system;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import java.util.Hashtable;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;

@DoNotObfuscate
public class SSHTunnelCommand extends Command {
  private final Options b = new Options();
  
  public SSHTunnelCommand() {
    this.b.addRequiredOption("th", "tunnelHost", true, "Tunnel Host");
    this.b.addOption("tp", "tunnelPort", true, "Tunnel Port, default 22");
    this.b.addRequiredOption("fh", "forwardHost", true, "Forward Host");
    this.b.addRequiredOption("flp", "forwardLeftPort", true, "Forward Local Port");
    this.b.addRequiredOption("frp", "forwardRightPort", true, "Forward Remote Port");
    this.b.addRequiredOption("u", "user", true, "User");
    this.b.addOption("p", "password", true, "Password");
    this.b.addOption("k", "keyFile", true, "Key File");
    this.b.addOption("kp", "keyParaphrase", true, "Key Paraphrase");
    this.b.addOption("h", "help", false, "Help");
  }
  
  public void process(AbstractConsole paramAbstractConsole, String paramString1, String paramString2) {
    CommandLine commandLine;
    String[] arrayOfString = AbstractConsole.c(paramString2);
    try {
      DefaultParser defaultParser = new DefaultParser();
      commandLine = defaultParser.parse(this.b, arrayOfString);
    } catch (MissingOptionException missingOptionException) {
      paramAbstractConsole.a(missingOptionException.getLocalizedMessage(), new Object[0]);
      (new HelpFormatter()).printHelp("ssh tunnel", this.b);
      return;
    } 
    if (commandLine.hasOption("h")) {
      (new HelpFormatter()).printHelp("ssh tunnel", this.b);
      return;
    } 
    Log.c("Setup SSH Tunnel");
    JSch jSch = new JSch();
    JSch.setLogger(new SSHTunnelCommand$1(this));
    Session session = jSch.getSession(commandLine.getOptionValue("u"), commandLine.getOptionValue("th"), Integer.parseInt(commandLine.getOptionValue("tp", "22")));
    session.setUserInfo(new a(paramAbstractConsole, commandLine));
    Log.c("SSH Tunnel got session.");
    Hashtable<Object, Object> hashtable = new Hashtable<>();
    hashtable.put("StrictHostKeyChecking", "no");
    session.setConfig(hashtable);
    if (commandLine.hasOption("k")) {
      Log.c("Add identity key");
      if (commandLine.hasOption("kp")) {
        jSch.addIdentity(commandLine.getOptionValue("k"), commandLine.getOptionValue("kp"));
      } else {
        jSch.addIdentity(commandLine.getOptionValue("k"));
      } 
    } else {
      session.setPassword(commandLine.getOptionValue("p"));
    } 
    session.connect();
    session.setPortForwardingL(Integer.parseInt(commandLine.getOptionValue("flp")), commandLine.getOptionValue("fh"), Integer.parseInt(commandLine.getOptionValue("frp")));
    Log.c("SSH Tunnel connected.");
    paramAbstractConsole.c("SSH Tunnel Successfully Set", new Object[0]);
  }
}
