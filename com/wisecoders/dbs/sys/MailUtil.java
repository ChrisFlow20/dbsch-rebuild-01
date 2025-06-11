package com.wisecoders.dbs.sys;

import com.wisecoders.dbs.cli.command.base.ParameterException;
import com.wisecoders.dbs.config.system.Sys;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

public class MailUtil {
  public static void a(String[] paramArrayOfString, String paramString1, String paramString2) {
    b(paramArrayOfString, paramString1, paramString2, null);
  }
  
  public static Session a() {
    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.imap.starttls.enable", "true");
    properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
    return Session.getDefaultInstance(properties, new MailUtil$1(properties));
  }
  
  public static void a(String[] paramArrayOfString, String paramString1, String paramString2, List paramList) {
    Scanner scanner = (new Scanner(MailUtil.class.getResourceAsStream("/templates/mail/mail.txt"))).useDelimiter("\\A");
    String str = scanner.hasNext() ? scanner.next() : "";
    str = str.replaceFirst("__BODY__", Matcher.quoteReplacement(paramString2));
    b(paramArrayOfString, paramString1, str, paramList);
  }
  
  public static void a(String paramString1, String paramString2) {
    if (Sys.B.cronEmail.j())
      b(Sys.B.cronEmail.c_().split(";"), paramString1, paramString2, null); 
  }
  
  public static void b(String[] paramArrayOfString, String paramString1, String paramString2, List paramList) {
    String str = Sys.B.mailFrom.c_();
    if (str == null)
      throw new ParameterException("Missing system parameter 'cli.mail.from'"); 
    Session session = a();
    Transport transport = session.getTransport("smtp");
    transport.connect(Sys.B.mailServer.c_(), Sys.B.mailPort.a(), Sys.B.mailUser.c_(), Sys.B.mailPassword.c_());
    MimeMultipart mimeMultipart = new MimeMultipart("related");
    MimeBodyPart mimeBodyPart = new MimeBodyPart();
    mimeBodyPart.setText(paramString2, "UTF-8", "html");
    mimeMultipart.addBodyPart((BodyPart)mimeBodyPart);
    if (paramList != null)
      for (File file : paramList) {
        if (file.exists()) {
          MimeBodyPart mimeBodyPart1 = new MimeBodyPart();
          mimeBodyPart1.attachFile(file);
          mimeMultipart.addBodyPart((BodyPart)mimeBodyPart1);
          continue;
        } 
        throw new FileNotFoundException(file.getAbsolutePath());
      }  
    MimeMessage mimeMessage = new MimeMessage(session);
    mimeMessage.setFrom((Address)new InternetAddress(str));
    InternetAddress[] arrayOfInternetAddress = new InternetAddress[paramArrayOfString.length];
    for (byte b = 0; b < arrayOfInternetAddress.length; b++)
      arrayOfInternetAddress[b] = new InternetAddress(paramArrayOfString[b]); 
    mimeMessage.setRecipients(Message.RecipientType.TO, (Address[])arrayOfInternetAddress);
    mimeMessage.setSubject(paramString1);
    mimeMessage.setContent((Multipart)mimeMultipart);
    mimeMessage.setSentDate(new Date());
    transport.sendMessage((Message)mimeMessage, mimeMessage.getAllRecipients());
  }
  
  static String[] a(String paramString) {
    InitialDirContext initialDirContext = new InitialDirContext();
    Attributes attributes = initialDirContext.getAttributes("dns:/" + paramString, new String[] { "MX" });
    Attribute attribute = attributes.get("MX");
    if (attribute == null)
      return new String[] { paramString }; 
    String[][] arrayOfString = new String[attribute.size()][2];
    for (byte b1 = 0; b1 < attribute.size(); b1++)
      arrayOfString[b1] = String.valueOf(attribute.get(b1)).split("\\s+"); 
    Arrays.sort(arrayOfString, (paramArrayOfString1, paramArrayOfString2) -> Integer.parseInt(paramArrayOfString1[0]) - Integer.parseInt(paramArrayOfString2[0]));
    String[] arrayOfString1 = new String[arrayOfString.length];
    for (byte b2 = 0; b2 < arrayOfString.length; b2++)
      arrayOfString1[b2] = arrayOfString[b2][1].endsWith(".") ? 
        arrayOfString[b2][1].substring(0, arrayOfString[b2][1].length() - 1) : arrayOfString[b2][1]; 
    return arrayOfString1;
  }
}
