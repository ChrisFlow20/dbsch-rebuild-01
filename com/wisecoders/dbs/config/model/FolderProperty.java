package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javafx.scene.paint.Color;

public class FolderProperty extends Property {
  public final List a = new ArrayList();
  
  private boolean b = false;
  
  FolderProperty(FolderProperty paramFolderProperty, String paramString1, String paramString2) {
    super(paramFolderProperty, paramString1);
  }
  
  public boolean a() {
    return this.b;
  }
  
  public void a(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public FolderProperty b(String paramString) {
    FolderProperty folderProperty = c(paramString);
    folderProperty.a(true);
    return folderProperty;
  }
  
  public FolderProperty a(String paramString1, String paramString2) {
    FolderProperty folderProperty = new FolderProperty(this, paramString1, paramString2);
    this.a.add(folderProperty);
    return folderProperty;
  }
  
  public FolderProperty c(String paramString) {
    FolderProperty folderProperty = new FolderProperty(this, paramString, q(paramString));
    a(folderProperty);
    return folderProperty;
  }
  
  public static Class d(String paramString) {
    switch (paramString) {
      case "color":
      
      case "boolean":
      
      case "int":
      
      case "long":
      
      case "text":
      
    } 
    return 




      
      String.class;
  }
  
  public void a(String paramString1, String paramString2, Class<Boolean> paramClass, boolean paramBoolean) {
    Property property = p(paramString1);
    if (property == null) {
      if (paramClass == Boolean.class) {
        property = new BooleanProperty(this, paramString1);
      } else if (paramClass == Color.class) {
        property = new ColorProperty(this, paramString1);
      } else if (paramClass == Integer.class) {
        property = new IntProperty(this, paramString1, -2147483648, 2147483647);
      } else if (paramClass == Long.class) {
        property = new LongProperty(this, paramString1, Long.MIN_VALUE, Long.MAX_VALUE);
      } else if (paramClass == TextProperty.class) {
        property = new TextProperty(this, paramString1);
      } else if (paramClass == String.class) {
        property = new StringProperty(this, paramString1);
      } else if (paramClass == Character.class) {
        property = new CharProperty(this, paramString1);
      } 
      if (property != null) {
        this.a.add(property);
        property.a(paramString2);
        property.c(paramBoolean);
      } 
    } 
  }
  
  public ColorProperty e(String paramString) {
    ColorProperty colorProperty = new ColorProperty(this, paramString);
    a(colorProperty);
    return colorProperty;
  }
  
  public FileProperty a(String paramString, Path paramPath) {
    FileProperty fileProperty = new FileProperty(this, paramString, paramPath);
    a(fileProperty);
    return fileProperty;
  }
  
  public ImageProperty b(String paramString, Path paramPath) {
    ImageProperty imageProperty = new ImageProperty(this, paramString, paramPath);
    a(imageProperty);
    return imageProperty;
  }
  
  public BooleanProperty f(String paramString) {
    BooleanProperty booleanProperty = new BooleanProperty(this, paramString);
    a(booleanProperty);
    return booleanProperty;
  }
  
  public StringProperty g(String paramString) {
    StringProperty stringProperty = new StringProperty(this, paramString);
    a(stringProperty);
    return stringProperty;
  }
  
  public StringProperty h(String paramString) {
    TextProperty textProperty = new TextProperty(this, paramString);
    a(textProperty);
    return textProperty;
  }
  
  public StringProperty i(String paramString) {
    SqlProperty sqlProperty = new SqlProperty(this, paramString);
    a(sqlProperty);
    return sqlProperty;
  }
  
  public OptionsProperty j(String paramString) {
    OptionsProperty optionsProperty = new OptionsProperty(this, paramString);
    a(optionsProperty);
    return optionsProperty;
  }
  
  public ProviderProperty k(String paramString) {
    ProviderProperty providerProperty = new ProviderProperty(this, paramString);
    a(providerProperty);
    return providerProperty;
  }
  
  public LOVProperty l(String paramString) {
    LOVProperty lOVProperty = new LOVProperty(this, paramString);
    a(lOVProperty);
    return lOVProperty;
  }
  
  public DatePatternProperty m(String paramString) {
    DatePatternProperty datePatternProperty = new DatePatternProperty(this, paramString);
    a(datePatternProperty);
    return datePatternProperty;
  }
  
  public CharProperty n(String paramString) {
    CharProperty charProperty = new CharProperty(this, paramString);
    a(charProperty);
    return charProperty;
  }
  
  public IntProperty a(String paramString, int paramInt1, int paramInt2) {
    IntProperty intProperty = new IntProperty(this, paramString, paramInt1, paramInt2);
    a(intProperty);
    return intProperty;
  }
  
  public LongProperty a(String paramString, long paramLong1, long paramLong2) {
    LongProperty longProperty = new LongProperty(this, paramString, paramLong1, paramLong2);
    a(longProperty);
    return longProperty;
  }
  
  public ListProperty a(String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString) {
    ListProperty listProperty = new ListProperty(this, paramString, paramArrayOfObject, paramArrayOfString);
    a(listProperty);
    return listProperty;
  }
  
  public void a(Properties paramProperties, boolean paramBoolean) {
    for (Property property : this.a)
      property.a(paramProperties, paramBoolean); 
  }
  
  public void b(Properties paramProperties, boolean paramBoolean) {
    for (Property property : this.a)
      property.b(paramProperties, paramBoolean); 
  }
  
  public void a(Object paramObject) {}
  
  public Object c() {
    return null;
  }
  
  public void a(String paramString) {}
  
  public Object d() {
    return null;
  }
  
  private void a(Property paramProperty) {
    this.a.add(paramProperty);
  }
  
  private String q(String paramString) {
    return b(paramString, "text");
  }
  
  private String r(String paramString) {
    return b(paramString, "tooltip");
  }
  
  public Properties b_() {
    Property property = this.e;
    FolderProperty folderProperty = (FolderProperty)property;
    return (property instanceof FolderProperty) ? folderProperty.b_() : null;
  }
  
  private String b(String paramString1, String paramString2) {
    String str1 = (l() != null) ? (l() + "." + l() + "." + paramString1) : (paramString1 + "." + paramString1);
    String str2 = (b_() != null) ? b_().getProperty(str1) : null;
    if (str2 == null) {
      System.out.println("Missing property " + str1);
      Log.f("Missing property " + str1);
      str2 = "???";
    } 
    return str2;
  }
  
  public boolean e() {
    return true;
  }
  
  public String o(String paramString) {
    if (paramString != null && Sys.B.replaceVariables.b())
      for (Property property : this.a) {
        if (property.c() != null)
          paramString = paramString.replaceAll("&" + property.c, String.valueOf(property.c())); 
      }  
    return paramString;
  }
  
  public Map f() {
    HashMap<Object, Object> hashMap = new HashMap<>();
    for (Property property : this.a) {
      if (property.c() != null)
        hashMap.put(property.c, property.c()); 
    } 
    return hashMap;
  }
  
  public Property p(String paramString) {
    if (paramString.contains(".")) {
      int i = paramString.indexOf(".");
      String str = paramString.substring(0, i);
      for (Property property : this.a) {
        if (property instanceof FolderProperty && property.c.equals(str))
          return ((FolderProperty)property).p(paramString.substring(i + 1)); 
      } 
    } else {
      for (Property property : this.a) {
        if (property.c.equalsIgnoreCase(paramString))
          return property; 
      } 
    } 
    return null;
  }
  
  public boolean a(String paramString, Object paramObject) {
    Property property = p(paramString);
    if (property != null) {
      property.a(paramObject);
      return true;
    } 
    return false;
  }
  
  public Glyph g() {
    return BootstrapIcons.folder;
  }
}
