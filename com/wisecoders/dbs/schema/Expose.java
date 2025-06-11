package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsDef;
import com.wisecoders.dbs.diagram.fx.notation.Notation;
import com.wisecoders.dbs.diagram.model.DiagramRouterTask;
import com.wisecoders.dbs.diagram.model.PainterStatus;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.io.File;
import java.util.HashMap;
import java.util.List;

@DoNotObfuscate
public class Expose extends HashMap {
  public static final String TOC = "TOC";
  
  public static final String IMAGE = "image";
  
  public static final String IMAGE_TOOLTIPS = "imageTooltips";
  
  public static final String TEXT = "text";
  
  public static final String ALL_COLUMNS = "allColumn";
  
  public static final String INDEXES = "indexes";
  
  public static final String FOREIGN_KEYS = "foreignKeys";
  
  public static final String FUNCTIONS = "functions";
  
  public static final String TRIGGERS = "triggers";
  
  public static final String SQL_CODE = "sql_code";
  
  public static final String SEQUENCES = "sequences";
  
  public static final String USER_DATA_TYPE = "user_data_type";
  
  public static final String VIEW_QUERY = "view_query";
  
  public static final String DARK_THEME = "darkTheme";
  
  public static final String SEPARATE_SVG_FILE = "separateSVGFile";
  
  public static final String UNICODE = "unicode";
  
  public static final String TITLE = "title";
  
  public static final String TABLE_HEADER_FONT_NAME = "tableHeaderFontName";
  
  public static final String TABLE_HEADER_FONT_SIZE = "tableHeaderFontSize";
  
  public static final String TABLE_HEADER_FONT_WEIGHT = "tableHeaderFontWeight";
  
  public static final String FONT_NAME = "fontName";
  
  public static final String FONT_SIZE = "fontSize";
  
  public static final String DATA_TYPE_FONT_SIZE = "dataTypeFontSize";
  
  public static final String CALLOUT_FONT_NAME = "calloutFontName";
  
  public static final String CALLOUT_FONT_SIZE = "calloutFontSize";
  
  public static final String PAGE_SIZE = "pageSize";
  
  public static final String LANDSCAPE = "landscape";
  
  public final Project project;
  
  public final List layouts = new UniqueArrayList();
  
  public final File file;
  
  private Notation a = Project.barkerNotation;
  
  public Expose(File paramFile, Project paramProject, List paramList) {
    this.project = paramProject;
    this.layouts.addAll(paramList);
    for (Layout layout : paramList) {
      if (layout.diagram.getStatus() == PainterStatus.a)
        new DiagramRouterTask(layout.diagram); 
    } 
    this.file = paramFile;
  }
  
  public Expose(File paramFile, Layout paramLayout) {
    this.layouts.add(paramLayout);
    this.file = paramFile;
    this.project = paramLayout.project;
  }
  
  public void setNotation(Notation paramNotation) {
    this.a = paramNotation;
  }
  
  public Notation getNotation() {
    return this.a;
  }
  
  public String getString(String paramString) {
    return getString(paramString, (String)null);
  }
  
  public String getString(String paramString1, String paramString2) {
    return containsKey(paramString1) ? String.valueOf(get(paramString1)) : paramString2;
  }
  
  public int getInt(String paramString, int paramInt) {
    return (containsKey(paramString) && get(paramString) instanceof Integer) ? ((Integer)get(paramString)).intValue() : paramInt;
  }
  
  public boolean is(String paramString) {
    boolean bool = (!"darkTheme".equals(paramString) && !"separateSVGFile".equals(paramString) && !"unicode".equals(paramString)) ? true : false;
    return getBoolean(paramString, bool);
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean) {
    if (containsKey(paramString))
      return (get(paramString) instanceof Boolean) ? ((Boolean)get(paramString)).booleanValue() : "true".equalsIgnoreCase(String.valueOf(get(paramString))); 
    return paramBoolean;
  }
  
  public DbmsDef getDbms() {
    return Dbms.get(!this.layouts.isEmpty() ? ((Layout)this.layouts.get(0)).project.getDbId() : "MySql");
  }
}
