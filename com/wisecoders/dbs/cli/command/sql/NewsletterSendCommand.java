package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.cli.command.base.SQLCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.connect.model.envoy.StatementParameter;
import com.wisecoders.dbs.dbms.connect.model.envoy.UpdateStatement;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.MailUtil;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;

@DoNotObfuscate
public class NewsletterSendCommand extends SQLCommand {
  private final String b;
  
  private final String c;
  
  private final String d;
  
  private final String e;
  
  private final String f;
  
  private final String g;
  
  private final Options h = new Options();
  
  private final HashMap i;
  
  public boolean process(AbstractConsole paramAbstractConsole, Connector paramConnector, Envoy paramEnvoy, String paramString1, String paramString2, int paramInt) {
    CommandLine commandLine;
    String[] arrayOfString = AbstractConsole.c(paramString2);
    try {
      DefaultParser defaultParser = new DefaultParser();
      commandLine = defaultParser.parse(this.h, arrayOfString);
    } catch (MissingOptionException missingOptionException) {
      paramAbstractConsole.a(missingOptionException.getLocalizedMessage(), new Object[0]);
      (new HelpFormatter()).printHelp("newsletter send", this.h);
      return true;
    } 
    if (commandLine.hasOption(this.g)) {
      (new HelpFormatter()).printHelp("newsletter send", this.h);
      paramAbstractConsole.d("Newsletter send can be used to send an email campaign using recipients stored in the database.\nPrevious set this parameters:\nvset cli.mail.smtp.host=mail.myserver.com\nvset cli.mail.smtp.port=587\nvset cli.mail.user=myuser\nvset cli.mail.password=xxx\nvset cli.mail.debug=true\nvset cli.mail.smtp.auth=true\nvset cli.mail.smtp.starttls.enable=true\nvset cli.mail.imap.starttls.enable=true\nvset cli.mail.smtp.ssl.protocols=TLSv1.2\nvset cli.mail.imap.ssl.protocols=TLSv1.2\nvset cli.mail.debug=true\nCommand require to be connected to a database (can be any) where this table exits:\nCREATE TABLE dbschema_newsletter ( \n\tuserid            int NOT NULL AUTO_INCREMENT PRIMARY KEY,\n\temail             varchar(120) NOT NULL,\n\tfirstname         varchar(120),\n\tlastname          varchar(120),\n\tsend              boolean,\n\topen_date         date,\n\tbounce_date       date,\n\tunsubscribe_date  date,\n\tCONSTRAINT        idx_dbschema_newsletter UNIQUE ( email ) \n );\n", new Object[0]);
      return true;
    } 
    String str1 = readFileFromURL(new URL(commandLine.getOptionValue(this.c)));
    String str2 = readFileFromURL(new URL(commandLine.getOptionValue(this.d)));
    if (StringUtil.isEmpty(str1) || StringUtil.isEmpty(str2))
      throw new SQLException("Got empty HTML or text message body"); 
    paramAbstractConsole.d("Connecting...", new Object[0]);
    Session session = MailUtil.a();
    Transport transport = session.getTransport("smtp");
    transport.connect(Sys.B.mailServer.c_(), Sys.B.mailPort.a(), Sys.B.mailUser.c_(), Sys.B.mailPassword.c_());
    boolean bool = commandLine.hasOption(this.f) ? Integer.parseInt(commandLine.getOptionValue("l")) : true;
    if (transport.isConnected()) {
      paramAbstractConsole.a("Connected to Server", new Object[0]);
      List list = a(paramEnvoy);
      paramAbstractConsole.a("Start sending " + list.size() + " emails.", new Object[0]);
      UpdateStatement updateStatement = paramEnvoy.b("UPDATE dbschema_newsletter SET send=?, last_send=?, bounce_date=? WHERE userid=?", new Object[0]);
      try {
        for (b b : list) {
          String str3 = b.a(str1);
          String str4 = b.a(str2);
          b.a(bool);
          boolean bool1 = false;
          try {
            Message message = a(session, commandLine.getOptionValue(this.b), str3, str4, b
                .a(), new InternetAddress(commandLine.getOptionValue(this.e)));
            transport.sendMessage(message, message.getAllRecipients());
          } catch (MessagingException messagingException) {
            Log.b((Throwable)messagingException);
            bool1 = true;
          } 
          updateStatement.i();
          updateStatement.a(Boolean.valueOf(false));
          updateStatement.a(new Date(System.currentTimeMillis()));
          updateStatement.a(new StatementParameter(bool1 ? new Date(System.currentTimeMillis()) : null, 91));
          updateStatement.a(new StatementParameter(Integer.valueOf(b.b), 2));
          updateStatement.a();
          paramEnvoy.p();
        } 
        if (updateStatement != null)
          updateStatement.close(); 
      } catch (Throwable throwable) {
        if (updateStatement != null)
          try {
            updateStatement.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }  
        throw throwable;
      } 
      transport.close();
    } 
    return true;
  }
  
  private Message a(Session paramSession, String paramString1, String paramString2, String paramString3, InternetAddress paramInternetAddress1, InternetAddress paramInternetAddress2) {
    MimeMultipart mimeMultipart;
    MimeMessage mimeMessage = new MimeMessage(paramSession);
    mimeMessage.setSubject(paramString1);
    mimeMessage.setFrom((Address)paramInternetAddress2);
    mimeMessage.setRecipient(Message.RecipientType.TO, (Address)paramInternetAddress1);
    MimeBodyPart mimeBodyPart = new MimeBodyPart();
    mimeBodyPart.setContent(paramString2, "text/html");
    if (paramString3 == null) {
      mimeMultipart = new MimeMultipart("related");
      mimeMultipart.addBodyPart((BodyPart)mimeBodyPart);
    } else {
      mimeMultipart = new MimeMultipart("alternative");
      MimeBodyPart mimeBodyPart1 = new MimeBodyPart();
      mimeBodyPart1.setText(paramString3);
      mimeMultipart.addBodyPart((BodyPart)mimeBodyPart1);
      mimeMultipart.addBodyPart((BodyPart)mimeBodyPart);
    } 
    mimeMessage.setContent((Multipart)mimeMultipart);
    mimeMessage.saveChanges();
    return (Message)mimeMessage;
  }
  
  private List a(Envoy paramEnvoy) {
    ArrayList<b> arrayList = new ArrayList();
    SelectStatement selectStatement = paramEnvoy.a("SELECT * FROM dbschema_newsletter WHERE bounce_date IS NULL AND unsubscribe_date IS NULL AND send=true", new Object[0]);
    try {
      ResultSet resultSet = selectStatement.j();
      ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
      HashMap<Object, Object> hashMap = new HashMap<>();
      while (resultSet.next()) {
        hashMap.clear();
        for (byte b = 1; b <= resultSetMetaData.getColumnCount(); b++)
          hashMap.put(resultSetMetaData.getColumnName(b), resultSet.getObject(b)); 
        arrayList.add(new b(this, hashMap));
      } 
      if (selectStatement != null)
        selectStatement.close(); 
    } catch (Throwable throwable) {
      if (selectStatement != null)
        try {
          selectStatement.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    return arrayList;
  }
  
  public NewsletterSendCommand() {
    this.i = new HashMap<>();
    this.h.addRequiredOption(this.b = "s", "subject", true, "Subject");
    this.h.addRequiredOption(this.c = "H", "html_file", true, "HTML Body");
    this.h.addRequiredOption(this.d = "T", "text_file", true, "Text Body");
    this.h.addRequiredOption(this.e = "f", "from", true, "From Address");
    this.h.addOption(this.f = "l", "l", true, "Limit number of emails per domain per day");
    this.h.addOption(this.g = "h", "help", false, "Help");
  }
  
  public static String readFileFromURL(URL paramURL) {
    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramURL.openStream()));
    String str;
    while ((str = bufferedReader.readLine()) != null) {
      if (!str.contains("zaraz"))
        stringBuilder.append(str).append("\n"); 
    } 
    bufferedReader.close();
    return stringBuilder.toString();
  }
}
