package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class Folder extends CopyOnWriteArrayList implements TreeUnit, Unit {
  private String b;
  
  private String c;
  
  public final AbstractUnit parent;
  
  public final Class childClass;
  
  private final boolean d;
  
  protected boolean a = false;
  
  private int e = -1;
  
  public Folder(String paramString, AbstractUnit paramAbstractUnit, Class paramClass) {
    this(StringUtil.getPluralOf(paramString), paramString, paramAbstractUnit, paramClass, true);
  }
  
  public Folder(String paramString, AbstractUnit paramAbstractUnit, Class paramClass, boolean paramBoolean) {
    this(StringUtil.getPluralOf(paramString), paramString, paramAbstractUnit, paramClass, paramBoolean);
  }
  
  public Folder(String paramString1, String paramString2, AbstractUnit paramAbstractUnit, Class paramClass) {
    this(paramString1, paramString2, paramAbstractUnit, paramClass, true);
  }
  
  public Folder(String paramString1, String paramString2, AbstractUnit paramAbstractUnit, Class paramClass, boolean paramBoolean) {
    if (paramString1 == null || paramString2 == null)
      throw new NullPointerException(); 
    this.b = paramString1;
    this.c = paramString2;
    this.parent = paramAbstractUnit;
    this.childClass = paramClass;
    this.d = paramBoolean;
  }
  
  public Class getChildClass() {
    return this.childClass;
  }
  
  public AbstractUnit getByName(String paramString) {
    if (paramString == null)
      return null; 
    if (paramString.startsWith("\"") && paramString.endsWith("\"") && paramString.length() > 2)
      paramString = paramString.substring(1, paramString.length() - 1); 
    AbstractUnit abstractUnit = null;
    for (AbstractUnit abstractUnit1 : this) {
      if (paramString.equals(abstractUnit1.getName()))
        return abstractUnit1; 
      if (paramString.equalsIgnoreCase(abstractUnit1.getName()))
        abstractUnit = abstractUnit1; 
    } 
    return abstractUnit;
  }
  
  public AbstractUnit getByKey(String paramString) {
    for (AbstractUnit abstractUnit : this) {
      if (paramString.equals(abstractUnit.getKey()))
        return abstractUnit; 
    } 
    return null;
  }
  
  public boolean isWrongName(String paramString, AbstractUnit paramAbstractUnit) {
    if (StringUtil.isEmptyTrim(paramString))
      return true; 
    AbstractUnit abstractUnit = getByName(paramString);
    return (abstractUnit != null && abstractUnit != paramAbstractUnit);
  }
  
  public int getChildrenCount() {
    return size();
  }
  
  public TreeUnit getChildAt(int paramInt) {
    return (paramInt < size()) ? (TreeUnit)get(paramInt) : null;
  }
  
  public AbstractUnit getParent() {
    return this.parent;
  }
  
  public boolean moveUp(int paramInt) {
    if (paramInt < 1 || paramInt > size())
      return false; 
    AbstractUnit abstractUnit = remove(paramInt);
    add(paramInt - 1, abstractUnit);
    return true;
  }
  
  public boolean moveDown(int paramInt) {
    if (paramInt < 0 || paramInt > size() - 1)
      return false; 
    AbstractUnit abstractUnit = remove(paramInt);
    add(paramInt + 1, abstractUnit);
    return true;
  }
  
  public boolean moveUp(AbstractUnit paramAbstractUnit) {
    int i = indexOf(paramAbstractUnit);
    if (i < 1 || i > size())
      return false; 
    AbstractUnit abstractUnit = remove(i);
    add(i - 1, abstractUnit);
    return true;
  }
  
  public void moveFirst(AbstractUnit paramAbstractUnit) {
    int i = indexOf(paramAbstractUnit);
    if (i < 0 || i > size() - 1)
      return; 
    AbstractUnit abstractUnit = remove(i);
    add(0, abstractUnit);
  }
  
  public boolean moveDown(AbstractUnit paramAbstractUnit) {
    int i = indexOf(paramAbstractUnit);
    if (i < 0 || i > size() - 1)
      return false; 
    AbstractUnit abstractUnit = remove(i);
    add(i + 1, abstractUnit);
    return true;
  }
  
  public AbstractTable getEntity() {
    return null;
  }
  
  public Entity getParentEntity() {
    return null;
  }
  
  public boolean isChecked() {
    return this.a;
  }
  
  public void exchangePosition(Attribute paramAttribute1, Attribute paramAttribute2) {
    if (contains(paramAttribute2)) {
      int i = indexOf(paramAttribute2);
      if (contains(paramAttribute1)) {
        remove(paramAttribute1);
        add(i, (AbstractUnit)paramAttribute1);
      } 
    } 
  }
  
  public String toString() {
    return this.b;
  }
  
  public boolean sameWith(Folder paramFolder, boolean paramBoolean) {
    if (paramFolder == null || (!paramBoolean && size() != paramFolder.size()))
      return false; 
    for (AbstractUnit abstractUnit : this) {
      boolean bool = false;
      for (AbstractUnit abstractUnit1 : paramFolder) {
        if (abstractUnit.sameAs(abstractUnit1, false))
          bool = true; 
      } 
      if (!bool)
        return false; 
    } 
    return true;
  }
  
  private static final Pattern f = Pattern.compile("(.+)_(\\d+)", 2);
  
  public static final DecimalFormat decimalFormat000 = new DecimalFormat("000");
  
  public String proposeName(String paramString) {
    int i = 1;
    if (paramString == null || paramString.trim().isEmpty())
      paramString = this.c.replaceAll(" ", "_"); 
    if (getByName(paramString) == null)
      return paramString; 
    Matcher matcher = f.matcher(paramString);
    if (matcher.matches()) {
      paramString = matcher.group(1);
      String str1 = matcher.group(2);
      i = Integer.parseInt(str1);
    } 
    String str;
    while (getByName(str = paramString + "_" + paramString) != null)
      i++; 
    return str;
  }
  
  private void a(AbstractUnit paramAbstractUnit) {
    int i = size();
    if (this.d && paramAbstractUnit != null) {
      i = 0;
      while (i < size() && paramAbstractUnit.compareTo((AbstractUnit)get(i)) > 0)
        i++; 
    } 
    super.add(i, paramAbstractUnit);
    TreeUnit.touch(this);
  }
  
  public boolean add(AbstractUnit paramAbstractUnit) {
    a(paramAbstractUnit);
    return true;
  }
  
  public void add(int paramInt, AbstractUnit paramAbstractUnit) {
    super.add(paramInt, paramAbstractUnit);
    TreeUnit.touch(this);
  }
  
  public boolean addAll(Collection paramCollection) {
    for (AbstractUnit abstractUnit : paramCollection)
      a(abstractUnit); 
    return true;
  }
  
  public boolean remove(Object paramObject) {
    int i = indexOf(paramObject);
    if (i > -1) {
      super.remove(paramObject);
      TreeUnit.touch(this);
      return true;
    } 
    return false;
  }
  
  public boolean removeAll(Collection<?> paramCollection) {
    boolean bool = super.removeAll(paramCollection);
    TreeUnit.touch(this);
    return bool;
  }
  
  public AbstractUnit remove(int paramInt) {
    AbstractUnit abstractUnit = super.remove(paramInt);
    TreeUnit.touch(this);
    return abstractUnit;
  }
  
  public void clear() {
    super.clear();
    TreeUnit.touch(this);
  }
  
  public String getName() {
    return this.b;
  }
  
  public boolean equals(Object paramObject) {
    return (this == paramObject);
  }
  
  public int hashCode() {
    int i = (this.b != null) ? this.b.hashCode() : 0;
    i = 31 * i + ((this.parent != null) ? this.parent.hashCode() : 0);
    i = 31 * i + ((this.childClass != null) ? this.childClass.hashCode() : 0);
    i = 31 * i + (this.d ? 1 : 0);
    i = 31 * i + ((this.c != null) ? this.c.hashCode() : 0);
    return i;
  }
  
  public String getSymbolicName() {
    return this.b;
  }
  
  public void markForDeletion() {
    for (AbstractUnit abstractUnit : this)
      abstractUnit.markForDeletion(); 
  }
  
  public boolean isMarkedForDeletion() {
    return false;
  }
  
  public void setComment(String paramString) {}
  
  public String getChildrenName() {
    return this.c;
  }
  
  public String getComment() {
    return null;
  }
  
  public void refresh() {
    for (AbstractUnit abstractUnit : this) {
      if (abstractUnit != null) {
        abstractUnit.refresh();
        if (abstractUnit.isMarkedForDeletion())
          remove(abstractUnit); 
      } 
    } 
  }
  
  public boolean rename(String paramString1, String paramString2) {
    this.b = paramString1;
    this.c = paramString2;
    TreeUnit.touch(this);
    return true;
  }
  
  public int getTickId() {
    return getParent().getTickId();
  }
  
  public void orderAlphabetically() {
    Object[] arrayOfObject = toArray();
    Arrays.sort(arrayOfObject);
    for (byte b = 0; b < arrayOfObject.length; b++) {
      AbstractUnit abstractUnit = (AbstractUnit)arrayOfObject[b];
      if (abstractUnit != get(b)) {
        remove(abstractUnit);
        add(b, abstractUnit);
      } 
    } 
  }
  
  void a() {
    if (this.d)
      orderAlphabetically(); 
  }
  
  public void setTouchId(int paramInt) {
    this.e = paramInt;
  }
  
  public int getTouchId() {
    return this.e;
  }
  
  public Object getUnitProperty(UnitProperty paramUnitProperty) {
    return this.parent.getUnitProperty(paramUnitProperty);
  }
  
  public Boolean is(UnitProperty paramUnitProperty) {
    return Boolean.FALSE;
  }
  
  public String ref() {
    return this.b;
  }
  
  public Map getCommentTags() {
    return null;
  }
  
  public void setCommentTags(Map paramMap) {}
  
  public void setCommentTag(String paramString1, String paramString2) {}
  
  public String getCommentTag(String paramString) {
    return "";
  }
  
  public String getDisplayName(Diagram paramDiagram) {
    return this.b;
  }
  
  public boolean isLoaded() {
    return true;
  }
  
  public boolean hasComments() {
    for (AbstractUnit abstractUnit : this) {
      if (StringUtil.isFilled(abstractUnit.getComment()))
        return true; 
    } 
    return false;
  }
}
