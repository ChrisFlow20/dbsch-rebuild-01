package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.project.convert.model.NamingDictionary;
import com.wisecoders.dbs.schema.DbUnit;
import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DoNotObfuscate
public abstract class AbstractUnit implements TreeUnit, Comparable {
  private String w;
  
  private String x = null;
  
  private boolean y = false;
  
  private int z = -1;
  
  private Object[] A;
  
  private boolean B = false;
  
  public AbstractUnit(String paramString) {
    if (paramString == null)
      throw new NullPointerException(); 
    this.w = paramString;
  }
  
  public String getName() {
    return this.w;
  }
  
  public String getPlainName() {
    return this.w.startsWith("<html>") ? this.w.substring("<html>".length()) : this.w;
  }
  
  public void setTouchId(int paramInt) {
    this.z = paramInt;
  }
  
  public int getTouchId() {
    return this.z;
  }
  
  public boolean rename(String paramString) {
    if (!this.w.equals(paramString)) {
      setUnitProperty(UnitProperty.a, this.w);
      this.w = (paramString != null) ? paramString : "";
      TreeUnit treeUnit = getParent();
      if (treeUnit instanceof Folder) {
        Folder folder = (Folder)treeUnit;
        folder.a();
      } 
      TreeUnit.touch(this);
      return true;
    } 
    return false;
  }
  
  public String toString() {
    return getName();
  }
  
  public void markForDeletion() {
    this.y = true;
  }
  
  public boolean isMarkedForDeletion() {
    return this.y;
  }
  
  public String getComment() {
    return this.x;
  }
  
  public String getCommentNotNull() {
    return (this.x != null) ? this.x : "";
  }
  
  public void setComment(String paramString) {
    boolean bool = StringUtil.areEqual(this.x, paramString);
    this.x = StringUtil.isFilledTrim(paramString) ? paramString.trim() : null;
    if (!bool)
      TreeUnit.touch(this); 
  }
  
  protected static String c(String paramString) {
    if (paramString != null && paramString.isEmpty())
      return null; 
    return paramString;
  }
  
  public int compareTo(AbstractUnit paramAbstractUnit) {
    if (paramAbstractUnit != null && this.w != null)
      return this.w.compareTo(paramAbstractUnit.getName()); 
    return -1;
  }
  
  public boolean matches(AbstractUnit paramAbstractUnit) {
    return (paramAbstractUnit != null && getClass() == paramAbstractUnit.getClass() && this.w.equalsIgnoreCase(paramAbstractUnit.getName()));
  }
  
  public Folder[] getSyncFolders() {
    return null;
  }
  
  @GroovyMethod
  public boolean sameAs(AbstractUnit paramAbstractUnit) {
    return sameAs(paramAbstractUnit, false);
  }
  
  public boolean sameAs(AbstractUnit paramAbstractUnit, boolean paramBoolean) {
    String str = getString(UnitProperty.a);
    return (getClass() == paramAbstractUnit.getClass() && (this.w
      .equalsIgnoreCase(paramAbstractUnit.getName()) || (paramBoolean && str != null && str.equalsIgnoreCase(paramAbstractUnit.getName()))));
  }
  
  public int getChildrenCount() {
    return 0;
  }
  
  public TreeUnit getChildAt(int paramInt) {
    return null;
  }
  
  public TreeUnit getByName(String paramString) {
    for (byte b = 0; b < getChildrenCount(); b++) {
      TreeUnit treeUnit = getChildAt(b);
      if (treeUnit.getName().equalsIgnoreCase(paramString))
        return treeUnit; 
    } 
    return null;
  }
  
  public int getTickId() {
    return (getParent() != null) ? getParent().getTickId() : -1;
  }
  
  public String getKey() {
    return null;
  }
  
  public String getDefaultKey() {
    return getSymbolicName() + "-" + getSymbolicName();
  }
  
  public Entity getParentEntity() {
    return getEntity();
  }
  
  public static Unit getByName(List paramList, String paramString) {
    if (paramString == null)
      return null; 
    if (paramString.startsWith("\"") && paramString.endsWith("\""))
      paramString = paramString.substring(1, paramString.length() - 1); 
    Unit unit = null;
    for (Unit unit1 : paramList) {
      if (paramString.equals(unit1.getName()))
        return unit1; 
      if (paramString.equalsIgnoreCase(unit1.getName()))
        unit = unit1; 
    } 
    return unit;
  }
  
  public void setUnitProperty(UnitProperty paramUnitProperty, Object paramObject) {
    if (paramObject != null) {
      if (this.A == null)
        this.A = new Object[(UnitProperty.values()).length]; 
      this.A[paramUnitProperty.i] = paramObject;
    } else if (this.A != null) {
      this.A[paramUnitProperty.i] = null;
    } 
  }
  
  public Object getUnitProperty(UnitProperty paramUnitProperty) {
    Object object = (this.A != null) ? this.A[paramUnitProperty.i] : null;
    if (object != null)
      return object; 
    if (paramUnitProperty.h && getParent() != null)
      return getParent().getUnitProperty(paramUnitProperty); 
    return null;
  }
  
  public Boolean is(UnitProperty paramUnitProperty) {
    return is(paramUnitProperty, false);
  }
  
  public Boolean is(UnitProperty paramUnitProperty, boolean paramBoolean) {
    Object object = getUnitProperty(paramUnitProperty);
    return Boolean.valueOf((object instanceof Boolean) ? ((Boolean)object).booleanValue() : paramBoolean);
  }
  
  public String getString(UnitProperty paramUnitProperty) {
    Object object = getUnitProperty(paramUnitProperty);
    return (object instanceof String) ? (String)object : null;
  }
  
  public boolean has(UnitProperty paramUnitProperty) {
    return (getUnitProperty(paramUnitProperty) != null);
  }
  
  public void removeUnitProperty(UnitProperty paramUnitProperty) {
    if (this.A != null)
      this.A[paramUnitProperty.i] = null; 
  }
  
  public Map getCommentTags() {
    return has(UnitProperty.e) ? (Map)getUnitProperty(UnitProperty.e) : null;
  }
  
  public String getCommentTag(String paramString) {
    return (getCommentTags() != null && getCommentTags().containsKey(paramString)) ? (String)getCommentTags().get(paramString) : null;
  }
  
  public void setCommentTags(Map paramMap) {
    if (paramMap == null) {
      setUnitProperty(UnitProperty.e, null);
    } else {
      if (!has(UnitProperty.e))
        setUnitProperty(UnitProperty.e, new HashMap<>()); 
      getCommentTags().clear();
      getCommentTags().putAll(paramMap);
    } 
  }
  
  public void setCommentTag(String paramString1, String paramString2) {
    if (!has(UnitProperty.e))
      setUnitProperty(UnitProperty.e, new HashMap<>()); 
    getCommentTags().put(paramString1, paramString2);
  }
  
  public String ref() {
    AbstractUnit abstractUnit = this;
    if (abstractUnit instanceof DbUnit) {
      DbUnit dbUnit = (DbUnit)abstractUnit;
      String str = Dbms.quote(dbUnit);
      return (dbUnit.getSchema() != null && (Dbms.get((dbUnit.getSchema()).project.getDbId())).prefixTableWithSchema.b()) ? (
        dbUnit.getSchema().ref() + "." + dbUnit.getSchema().ref()) : 
        str;
    } 
    return this.w;
  }
  
  public String getDisplayName(Diagram paramDiagram) {
    return ((paramDiagram.isShowPhysicalName() || paramDiagram.isShowPhysicalDictionaryName()) && getCommentTag("PhysicalName") != null) ? 
      getCommentTag("PhysicalName") : (
      paramDiagram.isShowPhysicalDictionaryName() ? NamingDictionary.c.a(getName()) : getName());
  }
  
  public PropertyAddOnFolder getPropertyAddOnFolder(String paramString) {
    for (byte b = 0; b < getChildrenCount(); b++) {
      TreeUnit treeUnit = getChildAt(b);
      if (treeUnit instanceof PropertyAddOnFolder) {
        PropertyAddOnFolder propertyAddOnFolder = (PropertyAddOnFolder)treeUnit;
        if (propertyAddOnFolder.getChildrenName().equalsIgnoreCase(paramString))
          return propertyAddOnFolder; 
      } 
    } 
    return null;
  }
  
  @GroovyMethod
  public boolean isVirtual() {
    return this.B;
  }
  
  @GroovyMethod
  public void setVirtual(boolean paramBoolean) {
    this.B = paramBoolean;
  }
  
  public abstract void refresh();
  
  public abstract TreeUnit getParent();
  
  public abstract String getSymbolicName();
  
  public abstract String getSymbolicIcon();
  
  public abstract Glyph getSymbolicGlyph();
}
