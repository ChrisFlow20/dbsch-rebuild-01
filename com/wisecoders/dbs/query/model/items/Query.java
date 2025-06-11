package com.wisecoders.dbs.query.model.items;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractLayout;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.LineTextType;
import com.wisecoders.dbs.diagram.model.ToolUnit;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.query.model.engine.QueryGenerator;
import com.wisecoders.dbs.query.model.engine.QueryMongoGenerator;
import com.wisecoders.dbs.query.model.engine.QuerySQLGenerator;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Query extends AbstractUnit implements AbstractLayout, ToolUnit {
  private static final Pattern d = Pattern.compile("_([A-Za-z])");
  
  public final Layout a;
  
  private boolean e = false;
  
  private String f = getDefaultKey();
  
  public final Diagram b;
  
  public final QueryGenerator c;
  
  private boolean g = false;
  
  public Query(Layout paramLayout, String paramString) {
    super(paramString);
    this.a = paramLayout;
    this.b = new Diagram(paramLayout.is(UnitProperty.f).booleanValue());
    this.c = paramLayout.is(UnitProperty.f).booleanValue() ? new QueryMongoGenerator(this, paramLayout.project.getDbId()) : new QuerySQLGenerator(this);
    this.b.setLineTextType(LineTextType.a);
  }
  
  public String getSymbolicName() {
    return "Query";
  }
  
  public String getSymbolicIcon() {
    return "query.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.building_check;
  }
  
  public TreeUnit getParent() {
    return this.a.queries;
  }
  
  public List getDepicts() {
    return this.b.depicts;
  }
  
  public Depict a() {
    Depict depict = null;
    for (Depict depict1 : getDepicts()) {
      if (depict == null || ((QueryTable)depict1.getEntity()).c().isEmpty())
        depict = depict1; 
    } 
    return depict;
  }
  
  private String b(Entity paramEntity) {
    StringBuilder stringBuilder;
    Matcher matcher;
    String str1, str2;
    byte b;
    if (paramEntity instanceof com.wisecoders.dbs.schema.ChildEntity || paramEntity.getName().isEmpty())
      return null; 
    ArrayList<String> arrayList = new ArrayList();
    for (Depict depict : this.b.depicts)
      arrayList.add(((QueryTable)depict.getEntity()).d()); 
    switch (Query$1.a[Dbms.get(this.a.project.getDbId()).getAliasSupport().ordinal()]) {
      case 1:
        stringBuilder = new StringBuilder();
        stringBuilder.append(paramEntity.getName().charAt(0));
        matcher = d.matcher(paramEntity.getName());
        while (matcher.find())
          stringBuilder.append(matcher.group(1)); 
        str1 = stringBuilder.toString().toLowerCase();
        str2 = str1;
        b = 1;
        while (arrayList.contains(str2) || Dbms.get(this.a.project.getDbId()).isReservedKeyword(str2))
          str2 = str1 + str1; 
        return str2;
      case 2:
        return paramEntity.getName();
    } 
    return "";
  }
  
  public QueryTable a(Entity paramEntity) {
    return new QueryTable(this, paramEntity, b(paramEntity));
  }
  
  public void refresh() {
    for (Depict depict : getDepicts())
      ((QueryTable)depict.getEntity()).refresh(); 
    this.b.refresh();
  }
  
  public AbstractTable b() {
    return null;
  }
  
  public Layout getLayout() {
    return this.a;
  }
  
  public boolean c() {
    return this.b.depicts.isEmpty();
  }
  
  public void setConfirmed(boolean paramBoolean) {
    this.e = paramBoolean;
  }
  
  public boolean isConfirmed() {
    return this.e;
  }
  
  public String getKey() {
    return this.f;
  }
  
  public void a(String paramString) {
    if (StringUtil.isFilledTrim(paramString))
      this.f = paramString; 
  }
  
  public void a(boolean paramBoolean) {
    this.g = paramBoolean;
  }
  
  public boolean d() {
    return this.g;
  }
}
