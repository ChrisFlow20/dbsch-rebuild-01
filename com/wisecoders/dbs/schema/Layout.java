package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.browse.model.Browse;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractLayout;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.ArrangerMode;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Callout;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.DiagramRouterTask;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.Point;
import com.wisecoders.dbs.diagram.model.Shape;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.project.html.HTML5Writer;
import com.wisecoders.dbs.project.html.MarkdownWriter;
import com.wisecoders.dbs.project.pdf.PdfDocumentationWriter;
import com.wisecoders.dbs.query.model.items.Query;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@DoNotObfuscate
public class Layout extends AbstractUnit implements AbstractLayout {
  private static final String a = "diagram_small.png";
  
  public final Project project;
  
  public final Diagram diagram;
  
  public final Folder scripts;
  
  public final Folder layoutDepicts;
  
  public final Folder browses = new Folder("Relational Data Editor", "Editor", this, Browse.class);
  
  public final Folder queries = new Folder("Query Builders", "Builder", this, Query.class);
  
  private final Folder[] b;
  
  private boolean c = false;
  
  private String d = getDefaultKey();
  
  private int e;
  
  public Layout(Project paramProject, String paramString) {
    super(paramString);
    this.e = 0;
    if (paramProject == null)
      throw new NullPointerException(); 
    this.diagram = new Diagram(paramProject.is(UnitProperty.f).booleanValue());
    this.project = paramProject;
    String str = StringUtil.getPluralOf((Dbms.get(paramProject.getDbId())).tableAlias.c_()) + " & " + StringUtil.getPluralOf((Dbms.get(paramProject.getDbId())).tableAlias.c_());
    this.layoutDepicts = new Folder(str, str, this, LayoutDepict.class, true);
    this.scripts = new Folder(paramProject.is(UnitProperty.f).booleanValue() ? "Query Editors" : "SQL Editors", paramProject.is(UnitProperty.f).booleanValue() ? "Query" : "SQL Editor", this, Script.class);
    (new Folder[1])[0] = this.layoutDepicts;
    (new Folder[4])[0] = this.layoutDepicts;
    (new Folder[4])[1] = this.scripts;
    (new Folder[4])[2] = this.browses;
    (new Folder[4])[3] = this.queries;
    this.b = paramProject.is(UnitProperty.g).booleanValue() ? new Folder[1] : new Folder[4];
  }
  
  public int getChildrenCount() {
    return this.b.length;
  }
  
  public TreeUnit getChildAt(int paramInt) {
    return this.b[paramInt];
  }
  
  public AbstractTable getEntity() {
    return null;
  }
  
  public List getDepicts() {
    return this.diagram.depicts;
  }
  
  public List getEntities() {
    ArrayList<Entity> arrayList = new ArrayList();
    for (Depict depict : this.diagram.depicts)
      arrayList.add(depict.entity); 
    return arrayList;
  }
  
  public String getSymbolicName() {
    return "Layout";
  }
  
  public String getSymbolicIcon() {
    return "diagram.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.easel2;
  }
  
  public TreeUnit getParent() {
    return this.project.layouts;
  }
  
  public Script createScript(String paramString) {
    Script script = new Script(this, this.scripts.proposeName(paramString));
    this.scripts.add(script);
    return script;
  }
  
  public Browse createBrowse(String paramString) {
    Browse browse = new Browse(this, this.browses.proposeName(paramString));
    this.browses.add(browse);
    return browse;
  }
  
  public Query createQuery(String paramString) {
    Query query = new Query(this, this.queries.proposeName(paramString));
    this.queries.add(query);
    return query;
  }
  
  public void refresh() {
    if (isMarkedForDeletion()) {
      for (Depict depict : this.diagram.depicts)
        depict.markForDeletion(); 
      for (Callout callout : this.diagram.callouts)
        callout.markForDeletion(); 
      for (Group group : this.diagram.groups)
        group.markForDeletion(); 
      for (Shape shape : this.diagram.shapes)
        shape.markForDeletion(); 
      for (Browse browse : this.browses)
        browse.markForDeletion(); 
      for (Script script : this.scripts)
        script.markForDeletion(); 
      for (Query query : this.queries)
        query.markForDeletion(); 
    } 
    this.diagram.refresh();
    this.diagram.depicts.sort((paramDepict1, paramDepict2) -> paramDepict1.getEntity().getName().compareTo(paramDepict2.entity.getName()));
    ArrayList<Depict> arrayList = new ArrayList();
    for (LayoutDepict layoutDepict : this.layoutDepicts)
      arrayList.add(layoutDepict.a); 
    for (Depict depict : this.diagram.depicts) {
      if (!arrayList.contains(depict)) {
        LayoutDepict layoutDepict = new LayoutDepict(this, depict);
        this.layoutDepicts.add(layoutDepict);
      } 
    } 
    if (this.diagram.isChanged()) {
      TreeUnit.touch(this);
      this.diagram.resetChanged();
    } 
    this.layoutDepicts.refresh();
    this.browses.refresh();
    this.scripts.refresh();
    this.queries.refresh();
  }
  
  public void setConfirmed(boolean paramBoolean) {
    this.c = paramBoolean;
  }
  
  public boolean isConfirmed() {
    return this.c;
  }
  
  public void setStoreId(String paramString) {
    if (paramString != null)
      this.d = paramString; 
  }
  
  public String getKey() {
    return this.d;
  }
  
  @GroovyMethod
  public void generateHtmlDocumentation(File paramFile) {
    Objects.requireNonNull(this.project);
    FxUtil.a(this.project::refresh);
    new DiagramRouterTask(this.diagram);
    Expose expose = new Expose(paramFile, this);
    expose.setNotation(this.project.getNotation());
    expose.put((K)"title", (V)getName());
    HTML5Writer.generateHtml5(expose);
  }
  
  @GroovyMethod
  public void generateHtmlDocumentation(File paramFile, List paramList) {
    Expose expose = new Expose(paramFile, this.project, paramList);
    expose.setNotation(this.project.getNotation());
    expose.put((K)"title", (V)getName());
    generateHtmlDocumentation(expose);
  }
  
  @GroovyMethod
  public void generateHtmlDocumentation(Expose paramExpose) {
    Objects.requireNonNull(this.project);
    FxUtil.a(this.project::refresh);
    paramExpose.setNotation(this.project.getNotation());
    for (Layout layout : paramExpose.layouts)
      new DiagramRouterTask(layout.diagram); 
    HTML5Writer.generateHtml5(paramExpose);
  }
  
  @GroovyMethod
  public void generatePdfDocumentation(File paramFile) {
    Objects.requireNonNull(this.project);
    FxUtil.a(this.project::refresh);
    new DiagramRouterTask(this.diagram);
    Expose expose = new Expose(paramFile, this);
    expose.setNotation(this.project.getNotation());
    expose.put((K)"TOC", (V)Boolean.valueOf(true));
    expose.put((K)"image", (V)Boolean.valueOf(true));
    expose.put((K)"imageTooltips", (V)Boolean.valueOf(true));
    expose.put((K)"title", (V)getName());
    new PdfDocumentationWriter(expose);
  }
  
  @GroovyMethod
  public void generatePdfDocumentation(File paramFile, List paramList) {
    Objects.requireNonNull(this.project);
    FxUtil.a(this.project::refresh);
    for (Layout layout : paramList)
      new DiagramRouterTask(layout.diagram); 
    Expose expose = new Expose(paramFile, this.project, paramList);
    expose.setNotation(this.project.getNotation());
    expose.put((K)"imageTooltips", (V)Boolean.valueOf(true));
    expose.put((K)"title", (V)getName());
    new PdfDocumentationWriter(expose);
  }
  
  @GroovyMethod
  public void generatePdfDocumentation(Expose paramExpose) {
    Objects.requireNonNull(this.project);
    FxUtil.a(this.project::refresh);
    for (Layout layout : paramExpose.layouts)
      new DiagramRouterTask(layout.diagram); 
    paramExpose.setNotation(this.project.getNotation());
    paramExpose.put((K)"title", (V)getName());
    new PdfDocumentationWriter(paramExpose);
  }
  
  @GroovyMethod
  public void generateMarkdownDocumentation(File paramFile, List paramList) {
    Objects.requireNonNull(this.project);
    FxUtil.a(this.project::refresh);
    for (Layout layout : paramList)
      new DiagramRouterTask(layout.diagram); 
    Expose expose = new Expose(paramFile, this.project, paramList);
    expose.setNotation(this.project.getNotation());
    expose.put((K)"title", (V)getName());
    MarkdownWriter.a(expose);
  }
  
  @GroovyMethod
  public void generateMarkdownDocumentation(Expose paramExpose) {
    Objects.requireNonNull(this.project);
    FxUtil.a(this.project::refresh);
    for (Layout layout : paramExpose.layouts)
      new DiagramRouterTask(layout.diagram); 
    paramExpose.setNotation(this.project.getNotation());
    paramExpose.put((K)"title", (V)getName());
    MarkdownWriter.a(paramExpose);
  }
  
  @GroovyMethod
  public Depict attach(Entity paramEntity) {
    Depict depict = this.diagram.attach(paramEntity, this.e, 0.0D);
    this.e += 30;
    return depict;
  }
  
  @GroovyMethod
  public void attachHierarchical(Entity paramEntity) {
    this.diagram.attachRecursiveAndCreateGroupForHierarchicalEntities(paramEntity, new Point(0.0D, 0.0D));
    this.diagram.autoArrange(ArrangerMode.SIMPLE);
  }
  
  @GroovyMethod
  public void autoArrange() {
    this.diagram.autoArrange(ArrangerMode.SIMPLE);
  }
  
  @GroovyMethod
  public void autoArrange(Group paramGroup) {
    this.diagram.autoArrange(paramGroup);
  }
  
  @GroovyMethod
  public void autoArrangeKeepGroups() {
    this.diagram.autoArrange(ArrangerMode.KEEP_GROUPS);
  }
  
  public boolean containsDepictForEntity(Entity paramEntity) {
    for (Depict depict : getDepicts()) {
      if (depict.entity == paramEntity)
        return true; 
    } 
    return false;
  }
  
  public void cloneLayout(Layout paramLayout, int paramInt1, int paramInt2, boolean paramBoolean) {
    for (Depict depict : paramLayout.getDepicts()) {
      Entity entity = this.project.getSimilarEntity(depict.getEntity());
      if (entity != null) {
        Depict depict1 = this.diagram.attach(entity, depict.getPosition().a() + paramInt1, depict.getPosition().b() + paramInt2);
        depict1.setColor(depict.getColor());
        for (Attribute attribute : depict.getHiddenAttributes()) {
          for (Attribute attribute1 : entity.getAttributes()) {
            if (attribute1.getName().equalsIgnoreCase(attribute.getName()))
              depict1.setAttributeVisible(attribute1, false); 
          } 
        } 
      } 
    } 
    for (Shape shape1 : paramLayout.diagram.shapes) {
      Shape shape2 = this.diagram.createShape(shape1.getComment(), new Point(shape1.a.getPosition().a() + paramInt1, shape1.a.getPosition().b() + paramInt2));
      shape2.a(shape1.c());
      shape2.a(shape1.b());
    } 
    for (Callout callout1 : paramLayout.diagram.callouts) {
      Callout callout2;
      if (callout1.c == null || paramLayout.project != this.project) {
        callout2 = this.diagram.createCallout(callout1.getComment(), new Point(callout1.getPosition().a() + paramInt1, callout1.getPosition().b() + paramInt2));
      } else {
        callout2 = this.diagram.createCallout(callout1.c, new Point(callout1.getPosition().a() + paramInt1, callout1.getPosition().b() + paramInt2));
      } 
      callout2.a(callout1.a());
    } 
    for (Group group1 : paramLayout.diagram.groups) {
      Group group2 = this.diagram.createGroup(group1.getName());
      group2.setComment(group1.getComment());
      group2.setColor(group1.getColor());
      for (Depict depict1 : group1.getDepicts()) {
        Depict depict2 = this.diagram.getDepictFor(this.project.getSimilarEntity(depict1.getEntity()));
        if (depict2 != null)
          group2.attachDepict(depict2); 
      } 
    } 
    if (paramBoolean) {
      this.diagram.setJoinedRouting(paramLayout.diagram.isJoinedRouting());
      this.diagram.setShowDataType(paramLayout.diagram.isShowDataType());
      this.diagram.setLineTextType(paramLayout.diagram.getLineTextType());
      this.diagram.setShowSchemaName(paramLayout.diagram.isShowSchemaName());
      this.diagram.setShowPageBorders(paramLayout.diagram.isShowPageBorders());
    } 
  }
  
  public void attachManyToManyGeneratedTables() {
    for (Schema schema : this.project.schemas) {
      for (Table table : schema.tables) {
        if (this.diagram.getDepictFor(table) == null && table.getRelations().size() == 2 && table.getAttributes().size() == 2) {
          Depict depict1 = this.diagram.getDepictFor(((ForeignKey)table.foreignKeys.get(0)).getTargetEntity());
          Depict depict2 = this.diagram.getDepictFor(((ForeignKey)table.foreignKeys.get(1)).getTargetEntity());
          if (depict1 != null && depict2 != null)
            this.diagram.attach(table, 
                Depict.middleOf(depict1.getPosition().a(), depict2.getPosition().a()), 
                Depict.middleOf(depict1.getPosition().b(), depict2.getPosition().b())); 
        } 
      } 
    } 
  }
  
  public void cleanChildEntities() {
    while (true) {
      boolean bool = false;
      for (Depict depict : this.diagram.depicts) {
        if (depict.getEntity() instanceof ChildEntity) {
          ChildEntity childEntity = (ChildEntity)depict.getEntity();
          Entity entity = childEntity.ownerColumn.getParentEntity();
          if (entity != null && this.diagram.getDepictFor(entity) == null) {
            this.diagram.detachUndoable(depict);
            bool = true;
          } 
        } 
      } 
      refresh();
      if (!bool) {
        this.project.refresh();
        return;
      } 
    } 
  }
}
