package com.wisecoders.dbs.browse.model;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataTypeFormat;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BrowseTable extends AbstractUnit {
  private int a = Integer.MIN_VALUE;
  
  private int i = Integer.MIN_VALUE;
  
  private int j = Integer.MIN_VALUE;
  
  private int k = Integer.MIN_VALUE;
  
  private final Folder l = new Folder("Joined Tables", "Tables", this, BrowseTable.class, false);
  
  public final BrowseTable b;
  
  public final Entity c;
  
  public final Relation d;
  
  public final BrowseResult e;
  
  public final List f = new CopyOnWriteArrayList();
  
  public final boolean g;
  
  private String m = null;
  
  private String n;
  
  protected final HashMap h = new HashMap<>();
  
  private final List o = new ArrayList();
  
  private final List p = new ArrayList();
  
  public BrowseTable(Entity paramEntity) {
    this(null, paramEntity, null, false);
  }
  
  private BrowseTable(BrowseTable paramBrowseTable, Entity paramEntity, Relation paramRelation, boolean paramBoolean) {
    super(paramEntity.getName());
    this.b = paramBrowseTable;
    this.c = paramEntity;
    this.d = paramRelation;
    this.g = paramBoolean;
    this.e = new BrowseResult(this);
    this.e.b((paramEntity.getSchema().is(UnitProperty.f).booleanValue() || (paramRelation != null && paramRelation.getEntity() != paramEntity)));
  }
  
  public BrowseTable a(Relation paramRelation) {
    return a(paramRelation, (this.c == paramRelation.getTargetEntity()));
  }
  
  public BrowseTable a(Relation paramRelation, boolean paramBoolean) {
    Entity entity = paramBoolean ? paramRelation.getEntity() : paramRelation.getTargetEntity();
    BrowseTable browseTable = new BrowseTable(this, entity, paramRelation, paramBoolean);
    browseTable.a(k() + m() + 40 + 20 * this.l.size(), l() + 20 * this.l.size());
    browseTable.b(500, 350);
    this.l.add(browseTable);
    return browseTable;
  }
  
  public void a(BrowseDetailResult paramBrowseDetailResult) {
    if (this.c.getAttributes().contains(paramBrowseDetailResult.a))
      this.f.add(paramBrowseDetailResult); 
  }
  
  public boolean a() {
    return !this.f.isEmpty();
  }
  
  public void b(BrowseDetailResult paramBrowseDetailResult) {
    this.f.remove(paramBrowseDetailResult);
  }
  
  public Index b() {
    return (this.c instanceof Table) ? ((Table)this.c).getPrimaryKeyOrUniqueIndex() : null;
  }
  
  public boolean c() {
    return (b() == null);
  }
  
  public int getChildrenCount() {
    return 1;
  }
  
  public TreeUnit getChildAt(int paramInt) {
    return this.l;
  }
  
  public List d() {
    return this.l;
  }
  
  public void refresh() {
    if ((this.c != null && this.c.isMarkedForDeletion()) || (this.d != null && this.d
      .isMarkedForDeletion()))
      markForDeletion(); 
    for (BrowseTable browseTable : this.l) {
      if (isMarkedForDeletion() || browseTable.isMarkedForDeletion()) {
        browseTable.markForDeletion();
        this.l.remove(browseTable);
      } 
      browseTable.refresh();
    } 
    this.f.removeIf(paramBrowseDetailResult -> paramBrowseDetailResult.a.isMarkedForDeletion());
  }
  
  public Entity getEntity() {
    return this.c;
  }
  
  public void a(String paramString) {
    if (paramString != null) {
      paramString = paramString.trim();
      if (paramString.length() == 0)
        paramString = null; 
    } 
    this.m = paramString;
  }
  
  public String e() {
    return this.m;
  }
  
  public boolean a(Attribute paramAttribute) {
    return (paramAttribute != null && this.h.containsKey(paramAttribute));
  }
  
  public Collection f() {
    return this.h.keySet();
  }
  
  public String b(Attribute paramAttribute) {
    if (paramAttribute == null)
      return null; 
    return (String)this.h.get(paramAttribute);
  }
  
  public boolean c(Attribute paramAttribute) {
    return (paramAttribute != null && this.h.get(paramAttribute) != null);
  }
  
  public Collection g() {
    return this.o;
  }
  
  public int d(Attribute paramAttribute) {
    return this.o.indexOf(paramAttribute);
  }
  
  public int e(Attribute paramAttribute) {
    if (this.o.contains(paramAttribute))
      return this.p.contains(paramAttribute) ? 1 : -1; 
    return 0;
  }
  
  public void a(Attribute paramAttribute, boolean paramBoolean) {
    if (paramAttribute == null)
      return; 
    if (paramBoolean) {
      if (!this.p.contains(paramAttribute))
        this.p.add(paramAttribute); 
      if (!this.o.contains(paramAttribute))
        this.o.add(paramAttribute); 
    } else {
      this.p.remove(paramAttribute);
      if (!this.o.contains(paramAttribute))
        this.o.add(paramAttribute); 
    } 
  }
  
  public void h() {
    this.p.clear();
    this.o.clear();
  }
  
  public void f(Attribute paramAttribute) {
    this.p.remove(paramAttribute);
    this.o.remove(paramAttribute);
  }
  
  public String getSymbolicName() {
    return "Browse Table";
  }
  
  public final String getSymbolicIcon() {
    return "table.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.tablet_fill;
  }
  
  public String toString() {
    return this.c.getName();
  }
  
  public TreeUnit getParent() {
    return (this.b != null) ? this.b.l : null;
  }
  
  public void markForDeletion() {
    super.markForDeletion();
    for (BrowseTable browseTable : this.l)
      browseTable.markForDeletion(); 
  }
  
  public Object a(Attribute paramAttribute, String paramString) {
    if (paramString != null)
      if (paramString.length() == 0) {
        this.h.remove(paramAttribute);
      } else {
        this.h.put(paramAttribute, paramString);
      }  
    return paramString;
  }
  
  public void g(Attribute paramAttribute) {
    this.h.remove(paramAttribute);
  }
  
  public void i() {
    this.m = null;
    this.h.clear();
  }
  
  public boolean a(Column paramColumn) {
    return (this.d != null && (
      this.g ? this.d.getSourceAttributes() : this.d.getTargetAttributes()).contains(paramColumn));
  }
  
  public boolean b(Column paramColumn) {
    return (this.d != null && (
      this.g ? this.d.getTargetAttributes() : this.d.getSourceAttributes()).contains(paramColumn));
  }
  
  public String h(Attribute paramAttribute) {
    if (paramAttribute != null) {
      StringBuilder stringBuilder = new StringBuilder("<html>");
      stringBuilder.append(paramAttribute.getName()).append(" <small>").append(paramAttribute.getTypeString(DataTypeFormat.b)).append("</small>");
      String str = b(paramAttribute);
      if (str != null)
        stringBuilder.append("<br>&nbsp;&nbsp;&nbsp;Filter: ").append(str); 
      if (paramAttribute.getComment() != null)
        stringBuilder.append("<hr/>").append(StringUtil.splitMultiline(paramAttribute.getComment())); 
      return stringBuilder.toString();
    } 
    return null;
  }
  
  public boolean a(BrowseTable paramBrowseTable) {
    if (this == paramBrowseTable)
      return true; 
    for (BrowseTable browseTable : this.l) {
      if (browseTable.a(paramBrowseTable))
        return true; 
    } 
    return false;
  }
  
  public String j() {
    return this.n;
  }
  
  public void b(String paramString) {
    this.n = paramString;
  }
  
  public int k() {
    return this.a;
  }
  
  public void a(int paramInt1, int paramInt2) {
    this.a = paramInt1;
    this.i = paramInt2;
  }
  
  public int l() {
    return this.i;
  }
  
  public int m() {
    return this.j;
  }
  
  public void b(int paramInt1, int paramInt2) {
    this.j = paramInt1;
    this.k = paramInt2;
  }
  
  public int n() {
    return this.k;
  }
}
