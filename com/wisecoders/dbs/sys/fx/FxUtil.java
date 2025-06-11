package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.model.result.ResultColumn;
import com.wisecoders.dbs.diagram.fx.VirtualCanvas;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.project.fx.FxFrame;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.EscapeChars;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;

public class FxUtil {
  private static final String j = "installFirst";
  
  public static final Insets a = new Insets(0.0D, 0.0D, 0.0D, 0.0D);
  
  public static final double b = Font.getDefault().getSize();
  
  public static final double c = 1.041666666666667D;
  
  public static final double d = 1.083333333333333D;
  
  public static final double e = 1.125D;
  
  public static final double f = 1.166666666666667D;
  
  public static final double g = 1.208333333333333D;
  
  public static final double h = 1.25D;
  
  public static void a(double paramDouble, EventHandler paramEventHandler) {
    (new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(paramDouble), paramEventHandler, new javafx.animation.KeyValue[0]) })).play();
  }
  
  public static void a(Runnable paramRunnable) {
    if (paramRunnable != null)
      if (Platform.isFxApplicationThread()) {
        try {
          paramRunnable.run();
        } catch (Throwable throwable) {
          Log.b(throwable);
        } 
      } else {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
              try {
                paramRunnable.run();
              } catch (Throwable throwable) {
                Log.b(throwable);
              } finally {
                paramCountDownLatch.countDown();
              } 
            });
        try {
          countDownLatch.await();
        } catch (InterruptedException interruptedException) {}
      }  
  }
  
  public static void b(Runnable paramRunnable) {
    if (paramRunnable != null)
      if (Platform.isFxApplicationThread()) {
        try {
          paramRunnable.run();
        } catch (Throwable throwable) {
          Log.b(throwable);
        } 
      } else {
        Platform.runLater(() -> {
              try {
                paramRunnable.run();
              } catch (Throwable throwable) {
                Log.b(throwable);
              } 
            });
      }  
  }
  
  public static void a(Node paramNode) {
    Platform.runLater(() -> {
          Parent parent = paramNode.getParent();
          while (parent != null && !(parent instanceof ScrollPane))
            parent = parent.getParent(); 
          if (parent != null) {
            ScrollPane scrollPane = (ScrollPane)parent;
            Bounds bounds = scrollPane.getViewportBounds();
            double d1 = scrollPane.getContent().localToScene(scrollPane.getContent().getBoundsInLocal()).getWidth();
            double d2 = paramNode.localToScene(paramNode.getBoundsInLocal()).getMinX();
            double d3 = paramNode.localToScene(paramNode.getBoundsInLocal()).getMaxX();
            if (d3 < 0.0D) {
              scrollPane.setHvalue(scrollPane.getHvalue() + (d2 - bounds.getWidth()) / d1);
            } else if (d2 > bounds.getWidth()) {
              scrollPane.setHvalue(scrollPane.getHvalue() + (d2 + bounds.getWidth()) / d1);
            } 
            double d4 = scrollPane.getContent().localToScene(scrollPane.getContent().getBoundsInLocal()).getHeight();
            double d5 = paramNode.localToScene(paramNode.getBoundsInLocal()).getMinY();
            double d6 = paramNode.localToScene(paramNode.getBoundsInLocal()).getMaxY();
            if (d6 < 0.0D) {
              scrollPane.setVvalue(scrollPane.getVvalue() + (d5 - bounds.getHeight()) / d4);
            } else if (d5 > bounds.getHeight()) {
              scrollPane.setVvalue(scrollPane.getVvalue() + (d5 + bounds.getHeight()) / d4);
            } 
          } 
        });
  }
  
  public static int[] a(List paramList) {
    int[] arrayOfInt = new int[paramList.size()];
    for (byte b = 0; b < paramList.size(); b++)
      arrayOfInt[b] = ((Integer)paramList.get(b)).intValue(); 
    return arrayOfInt;
  }
  
  public static boolean a() {
    return Platform.isFxApplicationThread();
  }
  
  public static ScrollPane b(Node paramNode) {
    Node node = paramNode;
    while (node != null) {
      if (node instanceof ScrollPane)
        return (ScrollPane)node; 
      Parent parent = node.getParent();
    } 
    return null;
  }
  
  public static void a(TableColumn paramTableColumn) {
    Object object = paramTableColumn.getUserData();
    if (object instanceof ResultColumn) {
      ResultColumn resultColumn = (ResultColumn)object;
      resultColumn.a(paramTableColumn.getWidth());
    } 
  }
  
  private static final Text k = new Text();
  
  public static final String i = "open:";
  
  public static synchronized int a(String paramString) {
    k.setText(paramString);
    k.applyCss();
    double d = k.getBoundsInLocal().getWidth();
    return (int)d;
  }
  
  public static void a(TableView paramTableView, FxUtil$ResizeMode paramFxUtil$ResizeMode) {
    paramTableView.getColumns().forEach(paramTableColumn -> {
          double d = a((paramFxUtil$ResizeMode == FxUtil$ResizeMode.a) ? b(paramTableColumn) : "");
          for (byte b = 0; b < paramTableView.getItems().size(); b++) {
            if (paramTableColumn.getCellData(b) != null) {
              double d1 = a(paramTableColumn.getCellData(b).toString());
              d = Math.max(d, d1);
            } 
          } 
          if (d > 300.0D)
            d = 300.0D; 
          paramTableColumn.setPrefWidth(d + 30.0D);
        });
  }
  
  public static String b(TableColumn paramTableColumn) {
    if (StringUtil.isFilledTrim(paramTableColumn.getText()))
      return paramTableColumn.getText(); 
    if (paramTableColumn.getGraphic() instanceof Label)
      return ((Label)paramTableColumn.getGraphic()).getText() + "   "; 
    return "";
  }
  
  public static void a(TableView paramTableView, TableColumn paramTableColumn, ResultColumn paramResultColumn, boolean paramBoolean) {
    double d = paramResultColumn.d();
    if (paramBoolean && d > -1.0D) {
      paramTableColumn.setPrefWidth(d);
    } else {
      d = d(b(paramTableColumn) + "13.0");
      for (byte b = 0; b < paramTableView.getItems().size(); b++) {
        Object object = paramTableColumn.getCellData(b);
        if (object != null) {
          double d1 = d(object.toString());
          d = Math.max(d, d1 + 13.0D);
        } 
      } 
      if (d > 300.0D)
        d = 300.0D; 
      paramTableColumn.setPrefWidth(d);
      paramTableColumn.setMaxWidth(d);
      paramTableColumn.setMinWidth(d);
      paramTableColumn.setMinWidth(0.0D);
      paramTableColumn.setMaxWidth(5000.0D);
    } 
  }
  
  private static double d(String paramString) {
    return (paramString != null) ? (paramString.length() * 6) : 0.0D;
  }
  
  public static void a(FxFrame paramFxFrame) {
    paramFxFrame.getWindow().setWidth(1140.0D);
    paramFxFrame.getWindow().setHeight(780.0D);
    File file = FxFileChooser.a(paramFxFrame, "Save Snapshot to File", FileOperation.b, new FileType[] { FileType.z });
    if (file != null) {
      WritableImage writableImage = paramFxFrame.getRoot().snapshot(new SnapshotParameters(), null);
      try {
        ImageIO.write(SwingFXUtils.fromFXImage((Image)writableImage, null), "png", file);
        paramFxFrame.a.a(paramFxFrame, "Done", new String[0]);
      } catch (IOException iOException) {
        paramFxFrame.a.a(paramFxFrame, iOException);
      } 
    } 
  }
  
  public static void a(Object paramObject) {}
  
  public static void a(Control paramControl, Rx paramRx) {
    paramControl.addEventFilter(MouseEvent.MOUSE_PRESSED, paramMouseEvent -> {
          boolean bool = a((Node)paramControl, paramMouseEvent.getSceneX(), paramMouseEvent.getSceneY());
          if (bool && paramRx.d(paramControl.getScene(), "Press ENTER to save cell editing, ESC to abort."))
            paramMouseEvent.consume(); 
        });
  }
  
  private static boolean a(Node paramNode, double paramDouble1, double paramDouble2) {
    if (paramNode instanceof TableCell) {
      TableCell tableCell = (TableCell)paramNode;
      Bounds bounds = paramNode.localToScene(paramNode.getBoundsInLocal());
      if (tableCell.isEditing() && tableCell.isVisible() && !bounds.contains(paramDouble1, paramDouble2))
        return true; 
    } 
    if (paramNode instanceof Parent) {
      Parent parent = (Parent)paramNode;
      for (Node node : parent.getChildrenUnmodifiable()) {
        if (a(node, paramDouble1, paramDouble2))
          return true; 
      } 
    } 
    return false;
  }
  
  public static void a(WebView paramWebView, WebErrorEvent paramWebErrorEvent) {
    if (paramWebErrorEvent.getEventType() == WebErrorEvent.USER_DATA_DIRECTORY_ALREADY_IN_USE || paramWebErrorEvent
      .getEventType() == WebErrorEvent.USER_DATA_DIRECTORY_IO_ERROR || paramWebErrorEvent
      .getEventType() == WebErrorEvent.USER_DATA_DIRECTORY_SECURITY_ERROR) {
      int i = (int)(Math.random() * 1000.0D);
      paramWebView.getEngine().setUserDataDirectory(Sys.j.resolve("" + i + "/").toFile());
    } 
  }
  
  public static int a(AbstractUnit paramAbstractUnit) {
    return Pref.b("open:" + paramAbstractUnit.getKey(), -1);
  }
  
  public static void a(Folder paramFolder) {
    for (AbstractUnit abstractUnit : paramFolder)
      Pref.b("open:" + abstractUnit.getKey()); 
  }
  
  public static void b() {
    Runtime runtime = Runtime.getRuntime();
    runtime.gc();
    try {
      Thread.sleep(100L);
    } catch (InterruptedException interruptedException) {
      Log.h();
    } 
    try {
      Thread.sleep(100L);
    } catch (InterruptedException interruptedException) {
      Log.h();
    } 
    runtime.gc();
  }
  
  public static void a(File paramFile1, File paramFile2) {
    ZipFile zipFile = new ZipFile(paramFile1);
    for (Enumeration<? extends ZipEntry> enumeration = zipFile.entries(); enumeration.hasMoreElements(); ) {
      ZipEntry zipEntry = enumeration.nextElement();
      if (!zipEntry.isDirectory()) {
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        try {
          File file = new File(paramFile2.toURI().resolve(EscapeChars.forURL(zipEntry.getName())));
          file.getParentFile().mkdirs();
          if (!zipEntry.isDirectory()) {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            try {
              inputStream.transferTo(bufferedOutputStream);
              bufferedOutputStream.close();
            } catch (Throwable throwable) {
              try {
                bufferedOutputStream.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              } 
              throw throwable;
            } 
          } 
          if (inputStream != null)
            inputStream.close(); 
        } catch (Throwable throwable) {
          if (inputStream != null)
            try {
              inputStream.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            }  
          throw throwable;
        } 
      } 
    } 
    zipFile.close();
    paramFile1.delete();
  }
  
  public static String a(String[] paramArrayOfString) {
    List list = Font.getFamilies();
    for (String str : paramArrayOfString) {
      if (list.contains(str))
        return str; 
    } 
    return "System";
  }
  
  public static void a(Node paramNode, boolean paramBoolean) {
    if (paramNode instanceof VirtualCanvas) {
      ((VirtualCanvas)paramNode).ar.a(paramBoolean);
    } else if (paramNode instanceof TabPane) {
      TabPane tabPane = (TabPane)paramNode;
      Tab tab = (Tab)tabPane.getSelectionModel().getSelectedItem();
      if (tab != null)
        a(tab.getContent(), paramBoolean); 
    } else if (paramNode instanceof SplitPane) {
      for (Node node : ((SplitPane)paramNode).getItems())
        a(node, paramBoolean); 
    } else if (paramNode instanceof Parent) {
      Parent parent = (Parent)paramNode;
      ObservableList observableList = parent.getChildrenUnmodifiable();
      if (observableList != null)
        for (Node node : observableList)
          a(node, paramBoolean);  
    } 
  }
  
  private static final SimpleDateFormat l = new SimpleDateFormat("yyyy-MM-dd");
  
  public static long c() {
    l.setLenient(false);
    long l = System.currentTimeMillis();
    String str = Pref.d("installFirst", (String)null);
    if (str == null) {
      Pref.b("installFirst", l.format(new Date()));
    } else {
      try {
        l = Math.min(l, l.parse(str).getTime());
      } catch (ParseException parseException) {}
    } 
    if (Sys.i.exists())
      try {
        BasicFileAttributes basicFileAttributes = (BasicFileAttributes)Files.readAttributes(Sys.i.toPath(), (Class)BasicFileAttributes.class, new java.nio.file.LinkOption[0]);
        l = Math.min(l, basicFileAttributes.creationTime().toMillis());
      } catch (IOException iOException) {} 
    return l;
  }
  
  public static long b(String paramString) {
    if (paramString != null)
      return l.parse(paramString).getTime(); 
    return -1L;
  }
  
  public static String b(String... paramVarArgs) {
    String str = null;
    for (String str1 : paramVarArgs) {
      for (String str2 : Font.getFontNames()) {
        if (str1.equalsIgnoreCase(str2))
          return str1; 
      } 
      str = str1;
    } 
    return str;
  }
  
  public static void c(String paramString) {
    Clipboard clipboard = Clipboard.getSystemClipboard();
    ClipboardContent clipboardContent = new ClipboardContent();
    clipboardContent.putString(paramString);
    clipboard.setContent((Map)clipboardContent);
  }
  
  public static boolean a(List<Object> paramList, int paramInt) {
    if (paramInt < 1 || paramInt > paramList.size())
      return false; 
    Object object = paramList.remove(paramInt);
    paramList.add(paramInt - 1, object);
    return true;
  }
  
  public static boolean b(List<Object> paramList, int paramInt) {
    if (paramInt < 0 || paramInt > paramList.size() - 1)
      return false; 
    Object object = paramList.remove(paramInt);
    paramList.add(paramInt + 1, object);
    return true;
  }
  
  public static void a(WebView paramWebView) {
    File[] arrayOfFile = Sys.j.toFile().listFiles();
    if (arrayOfFile != null)
      for (File file1 : arrayOfFile) {
        if (file1.getName().startsWith("webview") && file1.isDirectory() && System.currentTimeMillis() - file1.lastModified() > 432000000L)
          try {
            FileUtils.deleteDirectory(file1);
          } catch (IOException iOException) {
            Log.h();
          }  
      }  
    File file = Sys.j.resolve("webview" + System.currentTimeMillis()).toFile();
    file.deleteOnExit();
    paramWebView.getEngine().setUserDataDirectory(file);
  }
}
