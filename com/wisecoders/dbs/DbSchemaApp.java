package com.wisecoders.dbs;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.project.fx.FxFrame;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Alert$;
import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.swing.JOptionPane;

public class DbSchemaApp extends Application {
  public static File a;
  
  public void start(Stage paramStage) {
    Application.Parameters parameters = getParameters();
    Platform.setImplicitExit(true);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Log.c("Got SIGTERM");
            Stage.getWindows().forEach(());
          }));
    Log.c("Start DbSchema" + (
        (parameters != null && parameters.getRaw() != null && !parameters.getRaw().isEmpty()) ? ("" + 
        parameters.getRaw().size() + " parameters.") : "."));
    try {
      if (a == null)
        if (parameters != null && !parameters.getRaw().isEmpty() && !((String)parameters.getRaw().get(0)).isEmpty()) {
          String str = parameters.getRaw().get(0);
          File file = Paths.get(str, new String[0]).toFile();
          Log.c("Start App with " + str + " exists=" + file.exists());
          if (file.exists()) {
            a = file;
          } else {
            (new Alert$(Alert.AlertType.ERROR)).a("Error")
              .c("File not found: " + str).showAndWait();
          } 
        } else if (Sys.B.reopenProject.b() && License.a().e()) {
          a = FxFrame.c.a();
        }  
      paramStage.setTitle("DbSchema");
      new FxFrame(paramStage, a);
    } catch (Throwable throwable) {
      Log.b(throwable);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("DbSchema.app could not start.\n");
      stringBuilder.append("Please write our technicians https://dbschema.com/support.php about this issue.\n");
      stringBuilder.append("Please include a screenshot of this screen.\n\n");
      if (throwable.getCause() != null)
        stringBuilder.append(throwable.getCause().getLocalizedMessage()).append("\n"); 
      stringBuilder.append(StringUtil.printExceptionFirstLinesOfStackTrace(throwable));
      JOptionPane.showMessageDialog(null, stringBuilder.toString());
      throw throwable;
    } 
  }
  
  public void stop() {
    Log.c("Exit DbSchema.");
    try {
      if (!Rx.c.awaitTermination(7L, TimeUnit.SECONDS))
        Rx.c.shutdownNow(); 
    } catch (InterruptedException interruptedException) {
      Rx.c.shutdownNow();
    } 
    System.exit(0);
  }
}
