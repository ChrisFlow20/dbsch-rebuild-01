package com.wisecoders.dbs.cli.command.sql;

import com.wisecoders.dbs.cli.command.base.SQLCommand;
import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.UpdateStatement;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;

@DoNotObfuscate
public class NewsletterReadCommand extends SQLCommand {
  private final String b;
  
  private final Options c = new Options();
  
  public NewsletterReadCommand() {
    this.c.addOption(this.b = "d", "delete", false, "Delete messages after read");
    this.c.addOption("h", "help", false, "Command used to read the newsletter bounces from the IMAP account.\nCommand will update the 'newsletter' table 'bounce' field with the current date.\nLook the 'newsletter send' on how to connect to the email server.");
  }
  
  public boolean process(AbstractConsole paramAbstractConsole, Connector paramConnector, Envoy paramEnvoy, String paramString1, String paramString2, int paramInt) {
    CommandLine commandLine;
    String[] arrayOfString = AbstractConsole.c(paramString2);
    try {
      DefaultParser defaultParser = new DefaultParser();
      commandLine = defaultParser.parse(this.c, arrayOfString);
    } catch (MissingOptionException missingOptionException) {
      paramAbstractConsole.a(missingOptionException.getLocalizedMessage(), new Object[0]);
      (new HelpFormatter()).printHelp("newsletter read", this.c);
      return true;
    } 
    if (commandLine.hasOption("h"))
      (new HelpFormatter()).printHelp("newsletter", this.c); 
    Properties properties = new Properties();
    properties.put("mail.store.protocol", "imap");
    properties.put("mail.imap.host", Sys.B.mailServer.c_());
    properties.put("mail.imap.port", "993");
    properties.put("mail.imap.ssl.enable", "true");
    properties.put("mail.imap.ssl.protocols", "TLSv1.2");
    paramAbstractConsole.a("Connecting to IMAP server...", new Object[0]);
    Session session = Session.getInstance(properties);
    Store store = session.getStore("imap");
    store.connect(Sys.B.mailUser.c_(), Sys.B.mailPassword.c_());
    paramAbstractConsole.a("Read Inbox...", new Object[0]);
    Folder folder = store.getFolder("INBOX");
    folder.open(1);
    for (Message message : folder.getMessages()) {
      try {
        if (a(message)) {
          a(message.getContent(), paramAbstractConsole, paramEnvoy);
          paramAbstractConsole.a("Parse: " + String.valueOf(message.getSentDate()) + " " + message.getSubject(), new Object[0]);
        } else {
          paramAbstractConsole.a("Skip: " + String.valueOf(message.getSentDate()) + " " + message.getSubject(), new Object[0]);
        } 
      } catch (Exception exception) {
        paramAbstractConsole.a(exception);
        Log.b(exception);
      } 
      if (commandLine.hasOption(this.b))
        message.setFlag(Flags.Flag.DELETED, true); 
    } 
    if (commandLine.hasOption(this.b))
      folder.expunge(); 
    folder.close(false);
    store.close();
    return true;
  }
  
  private void a(Object paramObject, AbstractConsole paramAbstractConsole, Envoy paramEnvoy) {
    if (paramObject instanceof String) {
      a((String)paramObject, paramAbstractConsole, paramEnvoy);
    } else if (paramObject instanceof Multipart) {
      Multipart multipart = (Multipart)paramObject;
      int i = multipart.getCount();
      for (byte b = 0; b < i; b++) {
        BodyPart bodyPart = multipart.getBodyPart(b);
        String str = bodyPart.getContentType();
        Object object = bodyPart.getContent();
        if (object instanceof String) {
          a((String)object, paramAbstractConsole, paramEnvoy);
        } else if (object instanceof Multipart) {
          Multipart multipart1 = (Multipart)object;
          a(multipart1, paramAbstractConsole, paramEnvoy);
        } else if (object instanceof InputStream) {
          a(object, paramAbstractConsole, paramEnvoy);
        } else if (object instanceof MimeMessage) {
          a(((MimeMessage)object).getContent(), paramAbstractConsole, paramEnvoy);
        } else if (object != null) {
          paramAbstractConsole.a("DbSchemaCLI missing feature: Cannot handle " + object.getClass().getName(), new Object[0]);
        } 
      } 
    } else if (paramObject instanceof InputStream) {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)paramObject));
      String str;
      while ((str = bufferedReader.readLine()) != null)
        a(str, paramAbstractConsole, paramEnvoy); 
    } else if (paramObject instanceof MimeMessage) {
      a(((MimeMessage)paramObject).getContent(), paramAbstractConsole, paramEnvoy);
    } else if (paramObject != null) {
      System.out.println("B Cannot manage " + paramObject.getClass().getName());
    } 
  }
  
  private static boolean a(Message paramMessage) {
    String str = paramMessage.getSubject();
    if (str != null) {
      str = str.toLowerCase();
      return (str.contains("undelivered mail") || str
        .contains("delayed mail") || str
        .contains("returned mail") || str
        .contains("delivery status notification") || str
        .contains("notificación de estado de entrega") || str
        .contains("notification de statut de livraison") || str
        .contains("zustellbenachrichtigung") || str
        .contains("投递状态通知") || str
        .contains("配信状況通知") || str
        .contains("undeliverable") || str
        .contains("failure report") || str
        .contains("message delivery failed"));
    } 
    return false;
  }
  
  private static List a(String paramString) {
    ArrayList arrayList = new ArrayList();
    if (paramString != null)
      arrayList.addAll(b(paramString)); 
    return arrayList;
  }
  
  private static List b(String paramString) {
    ArrayList<String> arrayList = new ArrayList();
    String str = "[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}";
    Pattern pattern = Pattern.compile(str);
    Matcher matcher = pattern.matcher(paramString);
    while (matcher.find())
      arrayList.add(matcher.group(0)); 
    return arrayList;
  }
  
  private void a(String paramString, AbstractConsole paramAbstractConsole, Envoy paramEnvoy) {
    if (paramString != null) {
      List list = a(paramString);
      for (String str : list) {
        UpdateStatement updateStatement = paramEnvoy.b("UPDATE dbschema_newsletter SET bounce_date=? WHERE email=?", new Object[] { new Date(System.currentTimeMillis()), str });
        try {
          paramAbstractConsole.a("Bounce: " + str, new Object[0]);
          updateStatement.a();
          paramEnvoy.p();
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
      } 
    } 
  }
}
