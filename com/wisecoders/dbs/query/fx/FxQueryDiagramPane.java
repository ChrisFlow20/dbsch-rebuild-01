package com.wisecoders.dbs.query.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.filter.fx.FxQueryColumnEditor;
import com.wisecoders.dbs.data.filter.fx.FxQueryEntityEditor;
import com.wisecoders.dbs.diagram.fx.FxAbstractDiagramPane;
import com.wisecoders.dbs.diagram.fx.FxDnd;
import com.wisecoders.dbs.diagram.fx.ToolTipFactory;
import com.wisecoders.dbs.diagram.fx.notation.Notation;
import com.wisecoders.dbs.diagram.fx.print.FxPrintPreviewDialog;
import com.wisecoders.dbs.diagram.fx.print.printable.PrintableDiagram;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.PainterColumnPart;
import com.wisecoders.dbs.diagram.model.Point;
import com.wisecoders.dbs.diagram.model.Rect;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.Selection;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.dialogs.layout.FxEditorFactory;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.query.model.engine.Aggregate;
import com.wisecoders.dbs.query.model.items.AbstractQueryColumn;
import com.wisecoders.dbs.query.model.items.Query;
import com.wisecoders.dbs.query.model.items.QueryAggregate;
import com.wisecoders.dbs.query.model.items.QueryColumn;
import com.wisecoders.dbs.query.model.items.QueryRelation;
import com.wisecoders.dbs.query.model.items.QueryTable;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.FxQuickMenu;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

public class FxQueryDiagramPane extends FxAbstractDiagramPane {
  protected final Rx an = new Rx(FxQueryDiagramPane.class, this);
  
  protected final FxQueryEditor ao;
  
  public final Query at;
  
  FxQueryDiagramPane(FxQueryEditor paramFxQueryEditor, Query paramQuery) {
    super(paramQuery.b, true);
    this.at = paramQuery;
    this.ao = paramFxQueryEditor;
    this.a.setShowDataType(false);
    this.a.setJoinedRouting(true);
    this.an.a("flagSelectedColumn", () -> (this.c.a.size() == 1 && this.c.c() instanceof QueryColumn));
    this.an.a("flagSelectedFilterableColumn", () -> (this.c.a.size() == 1 && (this.c.c() instanceof QueryColumn || this.c.c() instanceof QueryAggregate)));
    this.an.a("flagSelectedOperationColumn", () -> (this.c.a.size() == 1 && this.c.c() instanceof com.wisecoders.dbs.query.model.items.QueryOperationColumn));
    Objects.requireNonNull(paramQuery);
    this.an.a("flagGroupBy", paramQuery::d);
    Objects.requireNonNull(paramQuery.b);
    this.an.a("flagJoinedRouting", paramQuery.b::isJoinedRouting);
    Objects.requireNonNull(paramQuery.b);
    this.an.a("flagShowDataType", paramQuery.b::isShowDataType);
    Objects.requireNonNull(paramQuery.b);
    this.an.a("flagShowSchemaName", paramQuery.b::isShowSchemaName);
    k();
    this.an.b();
    this.c.a(paramActionEvent -> this.an.b());
  }
  
  public void a(Point paramPoint) {
    AbstractTable abstractTable = null;
    boolean bool = false;
    Log.c("# Drag into Query Builder " + FxDnd.b.size() + " units");
    for (Unit unit : FxDnd.b) {
      if (unit instanceof AbstractTable) {
        Entity entity;
        AbstractTable abstractTable1 = (AbstractTable)unit;
        QueryTable queryTable = null;
        Relation relation = null;
        for (Group group : this.at.b.groups) {
          for (Depict depict : group.getDepicts()) {
            QueryTable queryTable1 = (QueryTable)depict.entity;
            Relation relation1 = a(queryTable1.f(), abstractTable1);
            if (relation1 != null) {
              relation = relation1;
              queryTable = queryTable1;
            } 
          } 
        } 
        for (Depict depict : this.at.b.depicts) {
          Relation relation1 = a(((QueryTable)depict.entity).f(), abstractTable1);
          if (relation1 != null) {
            relation = relation1;
            queryTable = (QueryTable)depict.entity;
          } 
        } 
        if (queryTable != null) {
          entity = queryTable.a(relation);
        } else if ((O()).b.depicts.isEmpty()) {
          entity = O().a(abstractTable1);
        } else {
          bool = true;
          entity = null;
        } 
        if (entity != null) {
          d().attach(entity, paramPoint.a, paramPoint.b);
          this.an.b();
          paramPoint.c(40.0D, 20.0D);
          abstractTable = abstractTable1;
        } 
      } 
    } 
    if (abstractTable != null) {
      k();
      c(abstractTable);
      this.ao.z();
    } 
    if (bool)
      this.an.d(getScene(), "warnOnAddMultipleRootTables"); 
  }
  
  private Relation a(Entity paramEntity1, Entity paramEntity2) {
    Relation relation = null;
    for (Relation relation1 : paramEntity1.getRelations()) {
      if (relation1.getTargetEntity() == paramEntity2)
        relation = relation1; 
    } 
    for (Relation relation1 : paramEntity1.getImportedRelations()) {
      if (relation1.getEntity() == paramEntity2)
        relation = relation1; 
    } 
    return relation;
  }
  
  public Query O() {
    return this.at;
  }
  
  public void a(Unit paramUnit, MouseEvent paramMouseEvent) {
    FxEditorFactory.a(this.ao.getWorkspace(), paramUnit);
    this.ao.z();
  }
  
  public void a(Unit paramUnit1, Unit paramUnit2) {}
  
  public void a(Entity paramEntity, Attribute paramAttribute) {}
  
  public void a(Entity paramEntity, List paramList) {}
  
  public void a() {
    j();
    this.ao.z();
  }
  
  public void a(Entity paramEntity, Attribute paramAttribute1, Attribute paramAttribute2) {
    if (paramEntity instanceof QueryTable)
      ((QueryTable)paramEntity).b().exchangePosition(paramAttribute1, paramAttribute2); 
  }
  
  public void b(MouseEvent paramMouseEvent) {
    if (this.c.a.size() == 1) {
      e();
      Unit unit = this.c.c();
      if (unit instanceof com.wisecoders.dbs.query.model.items.QueryFilter) {
        this.j = (new FxQuickMenu()).e();
        this.j.a(this.an.a(new String[] { "edit", "delete" }));
        this.j.show(getScene().getWindow(), paramMouseEvent.getScreenX() + 3.0D, paramMouseEvent.getScreenY() + -55.0D);
      } else if (unit instanceof QueryAggregate) {
        this.j = (new FxQuickMenu()).e();
        this.j.a(this.an.a(new String[] { "filterColumn", "delete" }));
        this.j.show(getScene().getWindow(), paramMouseEvent.getScreenX() + 3.0D, paramMouseEvent.getScreenY() + -55.0D);
      } else if (unit instanceof com.wisecoders.dbs.query.model.items.QueryOperationColumn) {
        this.j = (new FxQuickMenu()).e();
        this.j.a(this.an.a(new String[] { "delete" }));
        this.j.show(getScene().getWindow(), paramMouseEvent.getScreenX() + 3.0D, paramMouseEvent.getScreenY() + -55.0D);
      } else if (unit instanceof QueryColumn) {
        QueryColumn queryColumn = (QueryColumn)unit;
        this.j = (new FxQuickMenu()).e();
        if (queryColumn.hasMarker(-16288))
          this.j.a((Node)this.an.k("joinTable")); 
        this.j.a(this.an.a(new String[] { "filterColumn", "orderColumnAsc", "orderColumnDesc" }));
        this.j.show(getScene().getWindow(), paramMouseEvent.getScreenX() + 3.0D, paramMouseEvent.getScreenY() + -55.0D);
      } else if (unit instanceof QueryTable) {
        this.j = (new FxQuickMenu()).e();
        this.j.a((Node)this.an.k("joinTable"));
        this.j.show(getScene().getWindow(), paramMouseEvent.getScreenX() + 3.0D, paramMouseEvent.getScreenY() + -55.0D);
      } 
      if (unit instanceof Relation) {
        this.i.getItems().clear();
        this.i.getItems().addAll(this.an.e(new String[] { "joinInner", "joinLeftOuter", "joinFullOuter", "joinExists", "joinNotExists" }));
        this.i.a(false);
        this.i.show(getScene().getWindow(), paramMouseEvent.getScreenX(), paramMouseEvent.getScreenY());
      } 
    } 
  }
  
  public void a(MouseEvent paramMouseEvent) {
    Unit unit = this.c.c();
    this.i.getItems().clear();
    if (unit instanceof QueryTable) {
      this.i.getItems().addAll(this.an.e(new String[] { "edit", "separator", "selectTableColumns", "deselectTableColumns", "separator", "aliasTable", "joinTable", "separator", "exclude" }));
    } else if (unit instanceof QueryAggregate) {
      this.i.getItems().addAll(this.an.e(new String[] { "filterColumn", "delete" }));
    } else if (unit instanceof com.wisecoders.dbs.query.model.items.QueryOperationColumn) {
      this.i.getItems().add(this.an.A("delete"));
    } else if (unit instanceof QueryColumn) {
      this.i.getItems().addAll(this.an.e(new String[] { "aliasColumn", "joinTable", "separator", "selectTableColumns", "deselectTableColumns", "separator", "filterColumn" }));
      Menu menu = new Menu("Aggregate", (Node)BootstrapIcons.collection.glyph(new String[0]));
      menu.getItems().addAll(this.an.e(new String[] { "aggregateMin", "aggregateMax", "aggregateCount", "aggregateCountDistinct", "aggregateAvg", "aggregateSum" }));
      this.i.getItems().add(menu);
      this.i.getItems().addAll(this.an.e(new String[] { "separator", "orderColumnAsc", "orderColumnDesc" }));
    } else if (unit instanceof Relation) {
      if (!this.a.json)
        this.i.getItems().addAll(this.an.e(new String[] { "joinInner", "joinLeftOuter", "joinFullOuter", "joinExists", "joinNotExists" })); 
    } else {
      this.i.getItems().addAll(this.ao.g.e(new String[] { "run" }));
      this.i.getItems().addAll(this.an.e(new String[] { "separator", "exclude" }));
    } 
    if (!this.i.getItems().isEmpty()) {
      this.i.a(false);
      this.i.show((Node)this, paramMouseEvent.getScreenX(), paramMouseEvent.getScreenY());
    } 
  }
  
  public String b(Attribute paramAttribute) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramAttribute instanceof QueryColumn) {
      String str = ToolTipFactory.a(((QueryColumn)paramAttribute).x, this.a.isShowSchemaName());
      if (str == null) {
        stringBuilder.append(ToolTipFactory.d(paramAttribute));
      } else {
        stringBuilder.append("Click to JOIN referred or referring table.\n").append(str);
      } 
    } 
    return stringBuilder.toString();
  }
  
  public void a(Attribute paramAttribute, MouseEvent paramMouseEvent, PainterColumnPart paramPainterColumnPart) {
    q();
    switch (FxQueryDiagramPane$2.a[paramPainterColumnPart.ordinal()]) {
      case 1:
        b(paramMouseEvent);
        break;
      case 2:
        a(paramAttribute, paramMouseEvent, false, false);
        break;
    } 
  }
  
  public List a(Entity paramEntity, Attribute paramAttribute, boolean paramBoolean1, boolean paramBoolean2) {
    QueryTable queryTable = (QueryTable)paramEntity;
    Entity entity = queryTable.b;
    Attribute attribute = (paramAttribute instanceof QueryColumn) ? ((QueryColumn)paramAttribute).x : null;
    ArrayList<MenuItem> arrayList = new ArrayList();
    if (License.a().e()) {
      for (Relation relation : entity.getRelations()) {
        if (attribute == null || relation.getSourceAttributes().contains(attribute)) {
          MenuItem menuItem = new MenuItem("Join " + relation.getTargetEntity().getName() + " via " + relation.getName(), (Node)BootstrapIcons.arrow_90deg_right.glyph(new String[] { "glyph-blue" }));
          menuItem.setMnemonicParsing(false);
          menuItem.setOnAction(paramActionEvent -> {
                a(paramQueryTable.a(paramRelation));
                k();
                this.ao.z();
              });
          arrayList.add(menuItem);
        } 
      } 
      for (Relation relation : entity.getImportedRelations()) {
        if (attribute == null || relation.getTargetAttributes().contains(attribute)) {
          MenuItem menuItem = new MenuItem("Join " + relation.getEntity().getName() + " via " + relation.getName(), (Node)BootstrapIcons.arrow_90deg_left.glyph(new String[] { "glyph-orange" }));
          menuItem.setMnemonicParsing(false);
          menuItem.setOnAction(paramActionEvent -> {
                a(paramQueryTable.a(paramRelation));
                k();
                this.ao.z();
              });
          arrayList.add(menuItem);
        } 
      } 
      if (arrayList.isEmpty())
        arrayList.add(new MenuItem(this.an.H("noFksToChildren"))); 
    } else {
      MenuItem menuItem = new MenuItem(this.an.H("requirePro"));
      menuItem.setDisable(true);
      arrayList.add(menuItem);
    } 
    return arrayList;
  }
  
  public boolean a(Attribute paramAttribute) {
    return false;
  }
  
  public boolean b() {
    return !this.at.is(UnitProperty.f).booleanValue();
  }
  
  public boolean c() {
    return !this.at.is(UnitProperty.f).booleanValue();
  }
  
  String P() {
    return this.at.c.a();
  }
  
  public String toString() {
    return this.at.getName();
  }
  
  @Action
  public void joinTable() {
    a(o(), (MouseEvent)null, false, false);
  }
  
  @Action(d = "flagJoinedRouting")
  public void joinedRouting() {
    this.at.b.setJoinedRouting(!this.at.b.isJoinedRouting());
    k();
  }
  
  @Action(d = "flagJoinedRouting")
  public void showDataType() {
    this.at.b.setShowDataType(!this.at.b.isShowDataType());
    k();
  }
  
  @Action(d = "flagShowSchemaName")
  public void showSchemaName() {
    this.at.b.setShowSchemaName(!this.at.b.isShowSchemaName());
    k();
  }
  
  @Action
  public void joinInner() {
    e("Inner Join");
  }
  
  @Action
  public void joinLeftOuter() {
    e("Left Outer Join");
  }
  
  @Action
  public void joinFullOuter() {
    e("Full Outer Join");
  }
  
  @Action
  public void joinExists() {
    e("Exists");
  }
  
  @Action
  public void joinNotExists() {
    e("Not Exists");
  }
  
  private void e(String paramString) {
    Unit unit = this.c.c();
    if (unit instanceof QueryRelation) {
      QueryRelation queryRelation = (QueryRelation)unit;
      queryRelation.rename(paramString);
      k();
      this.ao.z();
    } 
  }
  
  @Action(d = "flagGroupBy")
  public void groupBy() {
    boolean bool = this.at.d();
    boolean bool1 = !bool ? true : false;
    for (Depict depict : this.at.b.depicts) {
      for (AbstractQueryColumn abstractQueryColumn : ((QueryTable)depict.entity).c) {
        if (abstractQueryColumn instanceof QueryAggregate && abstractQueryColumn.isTicked())
          bool1 = true; 
      } 
    } 
    this.at.a(bool1);
    if (bool1 && !bool)
      this.an.d(getScene(), "groupByMessage"); 
    this.an.b();
    this.ao.z();
  }
  
  @Action(b = "flagSelectedColumn")
  public void aggregateMin() {
    a(Aggregate.e);
  }
  
  @Action(b = "flagSelectedColumn")
  public void aggregateMax() {
    a(Aggregate.f);
  }
  
  @Action(b = "flagSelectedColumn")
  public void aggregateAvg() {
    a(Aggregate.b);
  }
  
  @Action(b = "flagSelectedColumn")
  public void aggregateSum() {
    a(Aggregate.a);
  }
  
  @Action(b = "flagSelectedColumn")
  public void aggregateCountDistinct() {
    a(Aggregate.d);
  }
  
  @Action(b = "flagSelectedColumn")
  public void aggregateCount() {
    a(Aggregate.c);
  }
  
  private void a(Aggregate paramAggregate) {
    this.at.a(true);
    Unit unit = this.c.c();
    if (unit instanceof QueryColumn) {
      QueryColumn queryColumn = (QueryColumn)unit;
      QueryAggregate queryAggregate = queryColumn.a().a(queryColumn.x, paramAggregate);
      queryAggregate.setTicked(true);
      this.c.b().b(queryAggregate);
      k();
      this.ao.z();
    } 
    this.an.b();
  }
  
  @Action(b = "flagSelectedFilterableColumn")
  public void filterColumn() {
    Unit unit = this.c.c();
    if (unit instanceof AbstractQueryColumn)
      a((AbstractQueryColumn)unit); 
    this.an.b();
  }
  
  public void a(AbstractQueryColumn paramAbstractQueryColumn) {
    Rect rect = a(paramAbstractQueryColumn);
    Point2D point2D = localToScreen(rect.a(), rect.b());
    (new FxQueryDiagramPane$1(this, paramAbstractQueryColumn.a(), (paramAbstractQueryColumn instanceof QueryAggregate) ? paramAbstractQueryColumn : paramAbstractQueryColumn.x, null))





      
      .show((Node)this, point2D.getX() - T(), point2D.getY() - U());
  }
  
  @Action(b = "flagSelectedOperationColumn")
  public void delete() {
    Unit unit = this.c.c();
    if (unit instanceof com.wisecoders.dbs.query.model.items.QueryOperationColumn) {
      unit.markForDeletion();
      this.at.refresh();
      k();
      this.ao.z();
    } 
  }
  
  @Action
  public void edit() {
    Unit unit = this.c.c();
    if (unit instanceof QueryTable || unit instanceof com.wisecoders.dbs.query.model.items.QueryFilter) {
      FxEditorFactory.a(this.ao.getWorkspace(), unit);
      k();
      this.ao.z();
    } 
  }
  
  @Action(b = "flagSelectedColumn")
  public void orderColumnAsc() {
    c(true);
  }
  
  @Action(b = "flagSelectedColumn")
  public void orderColumnDesc() {
    c(false);
  }
  
  @Action
  public void print() {
    new FxPrintPreviewDialog(getScene().getWindow(), new PrintableDiagram(this.at.getName(), this));
  }
  
  @Action
  public void exclude() {
    for (Entity entity : this.c.b)
      entity.markForDeletion(); 
    k();
    this.ao.z();
  }
  
  public void k() {
    this.ao.y().refresh();
    super.k();
  }
  
  private void c(boolean paramBoolean) {
    Unit unit = this.c.c();
    if (unit instanceof QueryColumn) {
      QueryColumn queryColumn = (QueryColumn)unit;
      for (AbstractQueryColumn abstractQueryColumn : (queryColumn.a()).c) {
        if (abstractQueryColumn instanceof com.wisecoders.dbs.query.model.items.QueryOrderBy && abstractQueryColumn.x == queryColumn.x)
          abstractQueryColumn.markForDeletion(); 
      } 
      queryColumn.a().a(queryColumn.x, paramBoolean);
      this.c.b();
      k();
      this.ao.z();
    } 
    this.an.b();
  }
  
  @Action
  public void selectTableColumns() {
    d(true);
  }
  
  @Action
  public void deselectTableColumns() {
    d(false);
  }
  
  private void d(boolean paramBoolean) {
    Unit unit = this.c.c();
    if (unit instanceof QueryColumn)
      unit = ((QueryColumn)unit).a(); 
    if (unit instanceof QueryTable) {
      ((QueryTable)unit).a(paramBoolean, true);
      this.ao.z();
      j();
    } 
  }
  
  public void a(List paramList, boolean paramBoolean) {
    if (!paramList.isEmpty()) {
      Selection selection = new Selection(paramList);
      if (paramBoolean)
        selection.a(); 
      int i = 10;
      ArrayList<QueryTable> arrayList = new ArrayList();
      for (Entity entity : selection.b) {
        QueryTable queryTable = null;
        Relation relation = null;
        for (QueryTable queryTable1 : arrayList) {
          List<Relation> list = Diagram.findRelationsWithEntity(queryTable1.b, entity);
          if (!selection.d.isEmpty())
            list.retainAll(selection.d); 
          if (!list.isEmpty()) {
            queryTable = queryTable1;
            relation = list.get(0);
          } 
        } 
        Depict depict = null;
        if (queryTable != null) {
          queryTable.a(Sys.B.tickAllColumns.b(), false);
          QueryTable queryTable1 = queryTable.a(relation);
          depict = (a(queryTable1, new Point(i, 10.0D))).b;
          arrayList.add(queryTable1);
        } else if (this.at.b.depicts.isEmpty()) {
          QueryTable queryTable1 = this.at.a(entity);
          queryTable1.a(Sys.B.tickAllColumns.b(), false);
          depict = (a(queryTable1, new Point(i, 10.0D))).b;
          arrayList.add(queryTable1);
        } 
        if (depict != null)
          i += (int)(depict.getPosition().c() + 90.0D); 
      } 
      k();
      this.ao.z();
    } 
  }
  
  public QueryTable a(AbstractTable paramAbstractTable) {
    if (this.at.b.depicts.isEmpty())
      return this.at.a(paramAbstractTable); 
    this.an.a(getScene(), "warnOnAddMultipleRootTables", new String[0]);
    return null;
  }
  
  public Depict a(QueryTable paramQueryTable) {
    Depict depict = this.a.attach(paramQueryTable, (v()).a + 100.0D, (v()).b - 30.0D);
    a(depict.getPosition().c() + 100.0D, 0.0D);
    this.an.b();
    return depict;
  }
  
  public final FxQueryDiagramPane$AttachLocation a(QueryTable paramQueryTable, Point paramPoint) {
    Depict depict = this.a.getDepictFor(paramQueryTable);
    int i = Diagram.cell * 2;
    Point point = paramPoint;
    if (depict == null) {
      depict = this.a.attach(paramQueryTable, paramPoint.a, paramPoint.b);
      point = new Point(depict.getPosition().a() + depict.getPosition().c() + (2 * i), depict.getPosition().b());
      for (Attribute attribute : paramQueryTable.b.getAttributes()) {
        if (attribute instanceof Column) {
          Column column = (Column)attribute;
          if (((Column)attribute).hasChildEntity()) {
            QueryTable queryTable = paramQueryTable.a((column.getChildEntity()).parentRelation);
            queryTable.a(true, false);
            FxQueryDiagramPane$AttachLocation fxQueryDiagramPane$AttachLocation = a(queryTable, point);
            point.b = Math.max(point.b, fxQueryDiagramPane$AttachLocation.a + Diagram.cell);
          } 
        } 
      } 
      point.b = Math.max(point.b, depict.getPosition().b() + depict.getPosition().d() + Diagram.cell);
    } 
    this.an.b();
    return new FxQueryDiagramPane$AttachLocation(depict, point.b);
  }
  
  @Action
  public void aliasTable() {
    Unit unit = this.c.c();
    if (unit instanceof QueryTable)
      (new FxQueryEntityEditor(this.ao.getWorkspace(), (QueryTable)unit)).showDialog(); 
  }
  
  @Action
  public void aliasColumn() {
    Unit unit = this.c.c();
    if (unit instanceof QueryColumn) {
      (new FxQueryColumnEditor(this.ao.getWorkspace(), (QueryColumn)unit)).showDialog();
      k();
    } 
  }
  
  @Action
  public void printPng() {
    a(FileType.z);
  }
  
  @Action
  public void printGif() {
    a(FileType.y);
  }
  
  @Action
  public void printJpg() {
    a(FileType.A);
  }
  
  public Notation C() {
    return Project.barkerNotation;
  }
  
  public void D() {
    this.ao.getWorkspace().u();
  }
}
