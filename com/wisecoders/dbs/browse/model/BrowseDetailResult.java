package com.wisecoders.dbs.browse.model;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.InputStreamUtil;
import com.wisecoders.dbs.sys.Log;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import javafx.scene.image.Image;

public class BrowseDetailResult extends BrowseResult {
  public final Attribute a;
  
  private final boolean h;
  
  public final String b;
  
  private static int i = 0;
  
  private final int j;
  
  private Image k;
  
  private String l;
  
  private File m;
  
  private boolean n = false;
  
  private String o;
  
  public BrowseDetailResult(BrowseTable paramBrowseTable, Attribute paramAttribute, String paramString, boolean paramBoolean) {
    super(paramBrowseTable);
    this.a = paramAttribute;
    this.b = paramString;
    this.h = paramBoolean;
    this.j = i++;
  }
  
  public Object a(String paramString, ResultSet paramResultSet, int paramInt1, int paramInt2) {
    Attribute attribute = this.c.c.getAttributes().get(paramInt2);
    if (this.c.e.m() == D() && attribute == this.a) {
      String str;
      Reader reader;
      Object object;
      switch (paramInt1) {
        case -1:
        case 12:
          str = paramResultSet.getString(paramInt2 + 1);
          break;
        case 2005:
        case 2011:
          reader = paramResultSet.getCharacterStream(paramInt2 + 1);
          break;
        case -4:
        case -3:
        case -2:
        case 2004:
          try {
            InputStream inputStream = paramResultSet.getBinaryStream(paramInt2 + 1);
          } catch (Throwable throwable) {
            Object object1 = paramResultSet.getObject(paramInt2 + 1);
          } 
          break;
        default:
          object = paramResultSet.getObject(paramInt2 + 1);
          break;
      } 
      a(object);
    } 
    return null;
  }
  
  public String a() {
    return this.l;
  }
  
  public String b() {
    switch (BrowseDetailResult$1.a[this.d.ordinal()]) {
      case 1:
        return "Queued for load";
      case 2:
        return "Loading";
    } 
    return this.o;
  }
  
  public File c() {
    return this.m;
  }
  
  public Image d() {
    return this.k;
  }
  
  public boolean e() {
    return (!this.h && ("png".equals(this.b) || "gif".equals(this.b) || "jpg".equals(this.b)));
  }
  
  public boolean f() {
    return (!this.h && ("txt".equals(this.b) || "html".equals(this.b) || "xml".equals(this.b)));
  }
  
  public String toString() {
    return this.b + this.b;
  }
  
  private void a(Object paramObject) {
    J();
    try {
      if (this.h) {
        this.m = Sys.g.resolve("dbsBrowseTemp_" + this.j + "." + this.b).toFile();
        if (!this.m.exists())
          this.m.createNewFile(); 
        if (paramObject instanceof InputStream) {
          InputStream inputStream = (InputStream)paramObject;
          try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.m);
            try {
              FileUtils.a(inputStream, fileOutputStream);
              fileOutputStream.close();
            } catch (Throwable throwable) {
              try {
                fileOutputStream.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              } 
              throw throwable;
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
        } else if (paramObject != null) {
          String str = String.valueOf(paramObject);
          if (str != null) {
            FileOutputStream fileOutputStream = new FileOutputStream(this.m);
            try {
              fileOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
              fileOutputStream.close();
            } catch (Throwable throwable) {
              try {
                fileOutputStream.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              } 
              throw throwable;
            } 
          } 
        } 
        if (this.n) {
          this.o = "Reload file in application";
        } else {
          Desktop.getDesktop().open(this.m);
          this.n = true;
          this.o = "Loaded";
        } 
      } else if (e()) {
        this.k = null;
        if (paramObject instanceof InputStream) {
          InputStream inputStream = (InputStream)paramObject;
          this.k = new Image(inputStream);
          this.o = "Loaded";
          inputStream.close();
        } else if (paramObject instanceof byte[]) {
          this.k = new Image(new ByteArrayInputStream((byte[])paramObject));
          this.o = "Loaded";
        } 
      } else {
        if (paramObject instanceof Reader) {
          Reader reader = (Reader)paramObject;
          this.l = InputStreamUtil.a(reader, -1);
          reader.close();
        } else if (paramObject instanceof InputStream) {
          InputStream inputStream = (InputStream)paramObject;
          this.l = InputStreamUtil.a(inputStream, -1);
          inputStream.close();
        } else if (paramObject != null) {
          this.l = String.valueOf(paramObject);
        } 
        this.o = "Loaded";
      } 
    } catch (Exception exception) {
      Log.a("Error in browse detail by loading data", exception);
      this.o = exception.toString();
    } 
  }
  
  private void J() {
    this.o = null;
    this.l = null;
  }
}
