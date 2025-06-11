package com.wisecoders.dbs.browse.model;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.ToolUnit;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.Collection;

public class Browse extends AbstractUnit implements ToolUnit {
  private String c = getDefaultKey();
  
  public final Layout a;
  
  private boolean d = false;
  
  public final Folder b = new Folder("Browse Tables", "Browse Tables", this, BrowseTable.class, false);
  
  private final Folder[] e = new Folder[] { this.b };
  
  private boolean f = false;
  
  private boolean g = true;
  
  public Browse(Layout paramLayout, String paramString) {
    super(paramString);
    this.a = paramLayout;
  }
  
  public int getChildrenCount() {
    return this.e.length;
  }
  
  public TreeUnit getChildAt(int paramInt) {
    return this.e[paramInt];
  }
  
  public BrowseTable a(Entity paramEntity) {
    Browse$1 browse$1 = new Browse$1(this, paramEntity.getEntity());
    this.b.add(browse$1);
    return browse$1;
  }
  
  public void refresh() {
    for (BrowseTable browseTable : this.b) {
      if (browseTable.isMarkedForDeletion())
        this.b.remove(browseTable); 
      browseTable.refresh();
    } 
  }
  
  public Layout getLayout() {
    return this.a;
  }
  
  public String getSymbolicName() {
    return "Browse";
  }
  
  public String getSymbolicIcon() {
    return "browse.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.table;
  }
  
  public Collection a() {
    return this.b;
  }
  
  public TreeUnit getParent() {
    return this.a.browses;
  }
  
  public AbstractTable b() {
    return null;
  }
  
  public boolean c() {
    return this.f;
  }
  
  public void a(boolean paramBoolean) {
    this.f = paramBoolean;
  }
  
  public boolean d() {
    return this.b.isEmpty();
  }
  
  public void setConfirmed(boolean paramBoolean) {
    this.d = paramBoolean;
  }
  
  public boolean isConfirmed() {
    return this.d;
  }
  
  public String getKey() {
    return this.c;
  }
  
  public void a(String paramString) {
    if (StringUtil.isFilledTrim(paramString))
      this.c = paramString; 
  }
  
  public boolean e() {
    return this.g;
  }
  
  public void b(boolean paramBoolean) {
    this.g = paramBoolean;
  }
}
