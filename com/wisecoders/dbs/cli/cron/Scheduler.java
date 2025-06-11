package com.wisecoders.dbs.cli.cron;

import com.wisecoders.dbs.cli.console.AbstractConsole;
import com.wisecoders.dbs.cli.console.FileConsole;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.MailUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Scheduler {
  private static final Pattern a = Pattern.compile("\\.schedule_(.+)\\.", 2);
  
  public Scheduler(AbstractConsole paramAbstractConsole) {
    if (Sys.B.cronEmail.c() == null)
      throw new InvalidParameterException("Missing variable cli.cron.email. Please define it in ~/.DbSchema/cli/init.sql or in DbSchema Settings Dialog"); 
    if (Sys.B.cronFolder.c() == null)
      throw new InvalidParameterException("Missing variable cli.cron.folder. Please define it in ~/.DbSchema/cli/init.sql or in DbSchema Settings Dialog"); 
    paramAbstractConsole.a("-- DbSchemaCLI Cron " + (new SimpleDateFormat("dd-MM-yyyy HH:mm")).format(Long.valueOf(System.currentTimeMillis())), new Object[0]);
    File file = Paths.get(Sys.B.cronFolder.c_(), new String[0]).toFile();
    if (!file.exists())
      throw new FileNotFoundException("Folder does not exists: '" + String.valueOf(file) + "'"); 
    if (!file.isDirectory())
      throw new FileNotFoundException("Not a folder: '" + String.valueOf(file) + "'"); 
    File[] arrayOfFile = file.listFiles();
    if (arrayOfFile != null) {
      boolean bool = false;
      paramAbstractConsole.p();
      for (File file1 : arrayOfFile) {
        try {
          if (file1.isFile() && (new Schedule(file1.getName())).a()) {
            bool = true;
            paramAbstractConsole.a("- execute '" + file1.getAbsolutePath() + "'", new Object[0]);
            FileConsole fileConsole = new FileConsole(file1);
            fileConsole.a(file1);
          } 
        } catch (InvalidParameterException invalidParameterException) {
          Log.b(invalidParameterException);
          paramAbstractConsole.a(invalidParameterException.getLocalizedMessage(), new Object[0]);
        } catch (Throwable throwable) {
          Log.a("Error in Scheduler", throwable);
          paramAbstractConsole.a("Error executing " + file1.getAbsolutePath(), throwable);
          paramAbstractConsole.a(throwable.getLocalizedMessage(), new Object[0]);
        } 
      } 
      if (paramAbstractConsole.n()) {
        paramAbstractConsole.a("- send error logs to '" + String.valueOf(Sys.B.cronEmail.c()) + "'", new Object[0]);
        try {
          MailUtil.a("DbSchemaCLI CRON ERRORS !!! Please read", paramAbstractConsole.o());
        } catch (Throwable throwable) {
          Log.a("Error sending mail from scheduler", throwable);
          throwable.printStackTrace();
        } 
      } 
      if (!bool)
        paramAbstractConsole.a("- nothing to execute", new Object[0]); 
    } 
  }
}
