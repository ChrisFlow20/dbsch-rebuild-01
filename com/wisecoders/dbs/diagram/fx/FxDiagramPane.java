package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.browse.fx.FxBrowsePreviewPopup;
import com.wisecoders.dbs.diagram.fx.notation.Notation;
import com.wisecoders.dbs.diagram.fx.print.FxPrintPreviewDialog;
import com.wisecoders.dbs.diagram.fx.print.printable.PrintableDiagram;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.ArrangerMode;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Callout;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.LineTextType;
import com.wisecoders.dbs.diagram.model.PainterColumnPart;
import com.wisecoders.dbs.diagram.model.Point;
import com.wisecoders.dbs.diagram.model.Rect;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.Shape;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.dialogs.layout.FxEditorFactory;
import com.wisecoders.dbs.dialogs.layout.FxForeignKeyEditor;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxDocumentationDialog;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.dialogs.system.FxZoomDialog;
import com.wisecoders.dbs.project.fx.FxFrame;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.html.SvgWriter;
import com.wisecoders.dbs.project.pdf.PdfDocumentationWriter;
import com.wisecoders.dbs.project.pdf.PdfUtil;
import com.wisecoders.dbs.schema.ChildEntity;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Expose;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.FxQuickMenu;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class FxDiagramPane extends FxAbstractDiagramPane implements FxUnitEditor {
  private final FxLayoutPane at;
  
  public final Layout an;
  
  public final Rx ao = new Rx(FxDiagramPane.class, this);
  
  FxDiagramPane(FxLayoutPane paramFxLayoutPane, Layout paramLayout) {
    super(paramLayout.diagram, false);
    this.at = paramFxLayoutPane;
    this.an = paramLayout;
    this.ao.G(paramLayout.project.getDbId());
    Q();
    k();
    addEventFilter(KeyEvent.KEY_PRESSED, paramKeyEvent -> {
          if (!paramKeyEvent.isShiftDown())
            switch (FxDiagramPane$1.b[paramKeyEvent.getCode().ordinal()]) {
              case 1:
                if (paramKeyEvent.isControlDown()) {
                  deleteSelected();
                } else {
                  excludeFromLayout();
                } 
                paramKeyEvent.consume();
                break;
              case 2:
                if (paramKeyEvent.isControlDown())
                  zoomIn(); 
                paramKeyEvent.consume();
                break;
              case 3:
                if (paramKeyEvent.isControlDown())
                  zoomOut(); 
                paramKeyEvent.consume();
                break;
            }  
        });
  }
  
  public void a(Point paramPoint) {
    Entity entity = null;
    Log.c("# Drag into layout " + FxDnd.b.size() + " units");
    boolean bool = this.at.d.getDepicts().isEmpty();
    this.c.b();
    for (Unit unit : FxDnd.b) {
      if (unit instanceof com.wisecoders.dbs.diagram.model.Folder) {
        for (E e : unit) {
          if (e instanceof Entity)
            this.a.attachRecursiveAndCreateGroupForHierarchicalEntities(entity = (Entity)e, paramPoint); 
        } 
        continue;
      } 
      if (unit instanceof Layout) {
        this.at.d.cloneLayout((Layout)unit, (int)this.at.getWidth(), 0, false);
        continue;
      } 
      if (unit instanceof Entity) {
        this.a.attachRecursiveAndCreateGroupForHierarchicalEntities(entity = (Entity)unit, paramPoint);
        this.at.d.refresh();
        continue;
      } 
      if (unit instanceof Column) {
        Column column = (Column)unit;
        Unit unit1 = this.at.c.o();
        if (unit1 instanceof Table) {
          Table table = (Table)unit1;
          entity = table;
          this.at.a(table, column);
        } 
      } 
    } 
    if (bool)
      this.at.c.a(ArrangerMode.SIMPLE); 
    this.at.c.k();
    if (entity != null)
      this.at.c.c(entity); 
  }
  
  public String b(Attribute paramAttribute) {
    StringBuilder stringBuilder = new StringBuilder();
    String str = ToolTipFactory.a(paramAttribute, this.a.isShowSchemaName());
    if (str == null) {
      stringBuilder.append(ToolTipFactory.d(paramAttribute));
    } else {
      stringBuilder.append("Click to show the other foreign key table\n").append(str);
    } 
    return stringBuilder.toString();
  }
  
  public String toString() {
    return this.at.d.getName();
  }
  
  public void a(Unit paramUnit, MouseEvent paramMouseEvent) {
    FxEditorFactory.a(this.at.b, paramUnit);
  }
  
  public Callout a(String paramString, Point paramPoint) {
    Callout callout = this.a.createCallout(paramString, paramPoint);
    k();
    return callout;
  }
  
  public Callout a(Unit paramUnit, Point paramPoint) {
    return this.a.createCallout(paramUnit, paramPoint);
  }
  
  public Shape b(String paramString, Point paramPoint) {
    return this.a.createShape(paramString, paramPoint);
  }
  
  public void a(Unit paramUnit1, Unit paramUnit2) {
    if (paramUnit2 instanceof Column && paramUnit1 instanceof Column) {
      (new FxForeignKeyEditor(this.at.b, (Column)paramUnit2, (Column)paramUnit1, true)).showDialog();
    } else if (paramUnit2 instanceof Shape && paramUnit1 instanceof Shape) {
      ((Shape)paramUnit1).a((Shape)paramUnit2, "");
    } else if (paramUnit1 instanceof AbstractTable) {
      (new FxForeignKeyEditor(this.at.b, (AbstractTable)paramUnit1, (paramUnit2 instanceof AbstractTable) ? (AbstractTable)paramUnit2 : null, true)).showDialog();
    } 
    k();
  }
  
  public void a(Entity paramEntity, Attribute paramAttribute) {
    if (paramEntity instanceof Table && paramAttribute instanceof Column)
      this.at.a((Table)paramEntity, (Column)paramAttribute); 
  }
  
  public void a(Entity paramEntity, List paramList) {
    if (paramEntity instanceof Table && this.ao.a(getScene(), "confirmCloneColumns"))
      this.at.a((Table)paramEntity, paramList); 
  }
  
  public void a(MouseEvent paramMouseEvent) {
    PopupFactory.a(getWorkspace(), this.at, this.i, this.c.a);
    this.i.a(false);
    if (!this.i.getItems().isEmpty())
      this.i.show(getWorkspace().getWindow(), paramMouseEvent.getScreenX(), paramMouseEvent.getScreenY()); 
  }
  
  public void a() {
    j();
  }
  
  public void b(MouseEvent paramMouseEvent) {
    e();
    if (paramMouseEvent.isShortcutDown() && paramMouseEvent.isShiftDown()) {
      if (!this.at.b.t()) {
        this.ao.d(getScene(), "browseOffline");
      } else if (this.c.a.size() == 1 && ((Unit)this.c.a.get(0)).getEntity() != null) {
        (new FxBrowsePreviewPopup(this.at.b, (AbstractTable)((Unit)this.c.a.get(0)).getEntity()))
          .a(getScene(), paramMouseEvent.getScreenX(), paramMouseEvent.getScreenY());
      } 
    } else {
      this.j = (new FxQuickMenu()).e();
      this.j.e.getChildren().clear();
      if (this.c.a.size() == 1) {
        if (this.c.c.size() == 1) {
          this.j.a(this.at.b.getRx().a(new String[] { "editUnit", "deleteUnit" }));
        } else {
          if (!getWorkspace().k())
            this.j.a(this.at.b.getRx().a(new String[] { "browseSelected", "querySelected", "generateSelectSql" })); 
          this.j.a(this.at.b.getRx().a(new String[] { "editUnit" }));
          this.j.a(this.ao.a(new String[] { "joinTable" }));
        } 
      } else if (!getWorkspace().k()) {
        this.j.a(this.at.b.getRx().a(new String[] { "browseSelected", "querySelected", "generateSelectSql" }));
      } 
      this.j.f();
      this.j.show(getScene().getWindow(), paramMouseEvent.getScreenX() + 3.0D, paramMouseEvent.getScreenY() + -55.0D);
    } 
  }
  
  @Action
  public void showPreviewDataPopup() {
    e();
    if (this.c.a.size() == 1 && ((Unit)this.c.a.get(0)).getEntity() != null) {
      Entity entity = ((Unit)this.c.a.get(0)).getEntity();
      a(entity, false);
      Rect rect = a(entity);
      if (rect != null) {
        Point2D point2D = this.ar.localToScreen(rect.a() + rect.c() - T(), rect.b() - U());
        (new FxBrowsePreviewPopup(getWorkspace(), (AbstractTable)((Unit)this.c.a.get(0)).getEntity()))
          .a(getScene(), point2D.getX(), point2D.getY());
      } 
    } 
  }
  
  public void a(AbstractUnit paramAbstractUnit) {
    if (paramAbstractUnit != null) {
      a(paramAbstractUnit, false);
      String str = ToolTipFactory.a(paramAbstractUnit);
      Rect rect = a(paramAbstractUnit);
      if (str != null && rect != null)
        a(str, rect.a() + rect.c() + 3.0D, rect.b() + rect.d() + 20.0D); 
    } 
  }
  
  public void a(Attribute paramAttribute, MouseEvent paramMouseEvent, PainterColumnPart paramPainterColumnPart) {
    Entity entity;
    switch (FxDiagramPane$1.a[paramPainterColumnPart.ordinal()]) {
      case 1:
        this.c.a.clear();
        entity = paramAttribute.getEntity();
        if (entity instanceof Table) {
          this.i.getItems().clear();
          for (Index index : ((Table)entity).getIndexes()) {
            if (index.getColumns().contains(paramAttribute))
              this.c.a.add(index); 
          } 
          a(paramMouseEvent);
        } 
        break;
      case 2:
        if (!a(paramAttribute)) {
          b(paramMouseEvent);
          break;
        } 
      case 3:
        a(paramAttribute, paramMouseEvent, true, paramMouseEvent.isControlDown());
        break;
    } 
  }
  
  public boolean a(Attribute paramAttribute) {
    return false;
  }
  
  public boolean b() {
    return true;
  }
  
  public boolean c() {
    return false;
  }
  
  public Unit getUnit() {
    return this.an;
  }
  
  public Workspace getWorkspace() {
    return this.at.b;
  }
  
  @Action
  public void showTable() {
    Unit unit = this.c.c();
    if (unit != null)
      c(unit); 
  }
  
  @Action
  public void autoplace() {
    if (getWorkspace().C() instanceof Group) {
      this.a.autoArrange((Group)getWorkspace().C());
      k();
    } else {
      ArrangerMode arrangerMode = null;
      if (this.a.depicts.isEmpty()) {
        this.ao.a(getScene(), "The current layout is empty. Please add tables first.", new String[0]);
      } else if (this.a.groups.isEmpty()) {
        ButtonType buttonType1 = new ButtonType("With Grouping");
        ButtonType buttonType2 = new ButtonType("Without Grouping");
        Optional<ButtonType> optional = this.ao.b(getScene(), "chooseGroupingDialog", Alert.AlertType.CONFIRMATION, new String[0]).a(new ButtonType[] { buttonType1, buttonType2, ButtonType.CANCEL }).showAndWait();
        if (optional.isPresent())
          if (optional.get() == buttonType1) {
            arrangerMode = ArrangerMode.KEEP_GROUPS;
          } else if (optional.get() == buttonType2) {
            arrangerMode = ArrangerMode.SIMPLE;
          }  
      } else if (this.ao.a(getScene(), "This operation will re-arrange the tables in the layout.\nThe operation is not undo-able. Continue ?")) {
        arrangerMode = ArrangerMode.SIMPLE;
      } 
      if (arrangerMode != null) {
        a(arrangerMode);
        i();
      } 
    } 
    D();
  }
  
  @Action
  public void showFkColumns() {
    LineTextType lineTextType = LineTextType.b;
    this.a.setDefaultLineText(lineTextType);
    this.a.setLineTextType(lineTextType);
    k();
    D();
  }
  
  @Action
  public void showFkName() {
    LineTextType lineTextType = LineTextType.a;
    this.a.setDefaultLineText(lineTextType);
    this.a.setLineTextType(lineTextType);
    k();
    D();
  }
  
  @Action
  public void showFkCascade() {
    LineTextType lineTextType = LineTextType.c;
    this.a.setDefaultLineText(lineTextType);
    this.a.setLineTextType(lineTextType);
    k();
    D();
  }
  
  @Action
  public void joinedRouting() {
    this.a.setJoinedRouting(!this.a.isJoinedRouting());
    k();
    D();
  }
  
  @Action
  public void deducedFks() {
    this.a.setShowDeducedFks(!this.a.isShowDeducedFks());
    k();
    D();
  }
  
  @Action
  public void showPageBorders() {
    this.a.setShowPageBorders(!this.a.isShowPageBorders());
    j();
    D();
  }
  
  @Action
  public void showSchemaName() {
    this.a.setShowSchemaName(!this.a.isShowSchemaName());
    k();
    D();
  }
  
  @Action
  public void showDataType() {
    this.a.setShowDataType(!this.a.isShowDataType());
    k();
    D();
  }
  
  @Action
  public void showPhysicalName() {
    this.a.setShowPhysicalName(!this.a.isShowPhysicalName());
    k();
    D();
  }
  
  @Action
  public void showPhysicalDictionaryName() {
    this.a.setShowPhysicalDictionaryName(!this.a.isShowPhysicalDictionaryName());
    k();
    D();
  }
  
  @Action
  public void deleteSelected() {
    ((FxFrame)this.at.b).deleteUnit();
  }
  
  @Action
  public void print() {
    new FxPrintPreviewDialog(this.at.getScene().getWindow(), new PrintableDiagram(this.an.getName(), this));
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
  
  @Action
  public void printPdf() {
    File file = FxFileChooser.a(getScene(), "Choose file", FileOperation.b, new FileType[] { FileType.k });
    if (file != null)
      try {
        Expose expose = new Expose(file, this.an);
        if (PdfUtil.a(this.an.project))
          expose.put((K)"unicode", (V)Boolean.valueOf(true)); 
        expose.setNotation(this.an.project.getNotation());
        expose.put((K)"imageTooltips", (V)Boolean.valueOf(true));
        expose.put((K)"tableHeaderFontName", (V)((Font)this.p.getValue()).getFamily());
        expose.put((K)"tableHeaderFontSize", (V)Double.valueOf(((Font)this.p.getValue()).getSize()));
        expose.put((K)"tableHeaderFontWeight", (V)((Font)this.p.getValue()).getStyle());
        expose.put((K)"fontName", (V)((Font)this.o.getValue()).getFamily());
        expose.put((K)"fontSize", (V)Double.valueOf(((Font)this.o.getValue()).getSize()));
        expose.put((K)"dataTypeFontSize", (V)Double.valueOf(((Font)this.q.getValue()).getSize()));
        expose.put((K)"calloutFontName", (V)((Font)this.r.getValue()).getFamily());
        expose.put((K)"calloutFontSize", (V)Double.valueOf(((Font)this.r.getValue()).getSize()));
        new PdfDocumentationWriter(expose);
      } catch (Throwable throwable) {
        this.ao.a(getScene(), throwable);
      }  
  }
  
  @Action
  public void printSVG() {
    File file = FxFileChooser.a(getScene(), "Choose file", FileOperation.b, new FileType[] { FileType.f });
    if (file != null)
      try {
        PrintWriter printWriter = new PrintWriter(file);
        try {
          Expose expose = new Expose(file, this.an);
          SvgWriter.a(expose, this.a, printWriter, this.an.getName());
          printWriter.close();
        } catch (Throwable throwable) {
          try {
            printWriter.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (IOException iOException) {
        this.ao.a(getScene(), iOException);
      }  
  }
  
  @Action
  public void zoomNormal() {
    a(1.0D);
  }
  
  @Action
  public void zoomOut() {
    a(n() * 0.909D);
  }
  
  @Action
  public void zoomIn() {
    a(n() * 1.1D);
  }
  
  @Action
  public void zoomChoose() {
    new FxZoomDialog(this.at.c);
  }
  
  @Action
  public void showSourceTable() {
    Unit unit = this.c.c();
    if (unit instanceof com.wisecoders.dbs.schema.ForeignKey)
      c(unit.getEntity()); 
  }
  
  @Action
  public void showDestTable() {
    Unit unit = this.c.c();
    if (unit instanceof com.wisecoders.dbs.schema.ForeignKey)
      c(((Relation)unit).getTargetEntity()); 
  }
  
  @Action
  public void joinTable() {
    a(o(), (MouseEvent)null, false, false);
  }
  
  @Action
  public void includeInLayout() {
    for (Unit unit : this.at.b.D()) {
      if (unit instanceof Entity && this.an.diagram.getDepictFor(unit) == null)
        a((Entity)unit); 
    } 
    k();
  }
  
  @Action
  public void excludeFromLayout() {
    for (Entity entity : this.at.b.E()) {
      Depict depict = this.an.diagram.getDepictFor(entity);
      if (depict != null)
        this.an.diagram.detachUndoable(depict); 
    } 
    this.an.cleanChildEntities();
    D();
    k();
  }
  
  public void a(Entity paramEntity, Attribute paramAttribute1, Attribute paramAttribute2) {
    if (paramEntity instanceof AbstractTable) {
      AbstractTable abstractTable = (AbstractTable)paramEntity;
      abstractTable.getAttributes().exchangePosition(paramAttribute1, paramAttribute2);
    } else if (paramEntity instanceof ChildEntity) {
      ChildEntity childEntity = (ChildEntity)paramEntity;
      childEntity.columns.exchangePosition(paramAttribute1, paramAttribute2);
    } 
  }
  
  public List a(Entity paramEntity, Attribute paramAttribute, boolean paramBoolean1, boolean paramBoolean2) {
    ArrayList<MenuItem> arrayList = new ArrayList();
    byte b = 0;
    boolean bool = false;
    for (Relation relation : paramEntity.getRelations()) {
      if (paramAttribute == null || relation.getSourceAttributes().contains(paramAttribute)) {
        if (!this.a.containsDepictFor(relation.getTargetEntity()))
          b++; 
        if (b(relation.getTargetEntity()))
          bool = true; 
        arrayList.add(a(relation, true, paramBoolean2));
      } 
    } 
    for (Relation relation : paramEntity.getImportedRelations()) {
      if (paramAttribute == null || relation.getTargetAttributes().contains(paramAttribute)) {
        if (!this.a.containsDepictFor(relation.getEntity()))
          b++; 
        if (b(relation.getEntity()))
          bool = true; 
        arrayList.add(a(relation, false, paramBoolean2));
      } 
    } 
    if (b > 2) {
      MenuItem menuItem = new MenuItem("Add All", (Node)BootstrapIcons.arrow_right_square_fill.glyph(new String[0]));
      menuItem.setOnAction(paramActionEvent -> {
            for (Relation relation : paramEntity.getRelations()) {
              if ((paramAttribute == null || relation.getSourceAttributes().contains(paramAttribute)) && this.a.getDepictFor(relation.getTargetEntity()) == null)
                a(relation.getTargetEntity()); 
            } 
            for (Relation relation : paramEntity.getImportedRelations()) {
              if ((paramAttribute == null || relation.getTargetAttributes().contains(paramAttribute)) && this.a.getDepictFor(relation.getEntity()) == null)
                a(relation.getEntity()); 
            } 
            this.c.b();
            k();
          });
      arrayList.add(menuItem);
    } 
    if (paramBoolean1 && !paramBoolean2 && bool) {
      MenuItem menuItem = new MenuItem("Open with CTRL pressed for more options");
      menuItem.setDisable(true);
      arrayList.add(menuItem);
    } 
    return arrayList;
  }
  
  private MenuItem a(Relation paramRelation, boolean paramBoolean1, boolean paramBoolean2) {
    Entity entity = paramBoolean1 ? paramRelation.getTargetEntity() : paramRelation.getEntity();
    String str = (this.a.containsDepictFor(entity) ? "Show " : "Add ") + (this.a.containsDepictFor(entity) ? "Show " : "Add ") + " via " + entity.getName();
    Label label = paramBoolean1 ? BootstrapIcons.arrow_90deg_right.glyph(new String[] { "glyph-blue" }) : BootstrapIcons.arrow_90deg_left.glyph(new String[] { "glyph-orange" });
    MenuItem menuItem = new MenuItem(str, (Node)label);
    menuItem.setMnemonicParsing(false);
    menuItem.setOnAction(paramActionEvent -> {
          Depict depict = a(paramEntity);
          k();
          c(depict.getEntity());
        });
    if (!paramBoolean2 || !b(entity))
      return menuItem; 
    Menu menu = new Menu(str, (Node)label);
    menuItem.setText("Show in this layout");
    menu.getItems().add(menuItem);
    for (Layout layout : this.an.project.layouts) {
      if (layout != this.an && layout.diagram.containsDepictFor(entity)) {
        MenuItem menuItem1 = new MenuItem("Show in Layout '" + layout.getName() + "'", (Node)label);
        menuItem1.setMnemonicParsing(false);
        menuItem1.setOnAction(paramActionEvent -> {
              this.c.b();
              FxLayoutPane fxLayoutPane = getWorkspace().a(paramLayout);
              fxLayoutPane.c.c(paramEntity);
            });
        menu.getItems().add(menuItem1);
      } 
    } 
    return (MenuItem)menu;
  }
  
  private boolean b(Entity paramEntity) {
    boolean bool = false;
    for (Layout layout : this.an.project.layouts) {
      if (layout != this.an && layout.diagram.containsDepictFor(paramEntity))
        bool = true; 
    } 
    return bool;
  }
  
  public Depict a(Entity paramEntity) {
    Depict depict = this.a.getDepictFor(paramEntity);
    if (depict == null) {
      depict = this.an.diagram.attach(paramEntity, (v()).a + 70.0D, (v()).b - 30.0D);
      a(depict.getPosition().c() + 70.0D, 0.0D);
    } 
    return depict;
  }
  
  public Depict a(Entity paramEntity, boolean paramBoolean) {
    Depict depict = this.a.getDepictFor(paramEntity);
    if (depict == null) {
      this.an.diagram.attachRecursive(paramEntity, new Point((v()).a + 70.0D, (v()).b - 30.0D), paramBoolean);
      a(70.0D, 0.0D);
    } 
    return depict;
  }
  
  public void O() {
    this.c.b();
    for (Depict depict : this.a.depicts) {
      for (Relation relation : depict.getEntity().getRelations()) {
        if (relation.isVirtual())
          this.c.b(relation); 
      } 
    } 
  }
  
  public void a(ArrangerMode paramArrangerMode) {
    l();
    this.a.autoArrange(paramArrangerMode);
    k();
  }
  
  @Action
  public void documentation() {
    if (License.a().f() && this.at.d.diagram.depicts.size() > 12) {
      this.ao.a(getScene(), "featureLimitForFreeVersion", new String[0]);
    } else {
      new FxDocumentationDialog(this.at.b, this.at);
    } 
  }
  
  public Notation C() {
    return (this.at != null) ? this.at.d.project.getNotation() : null;
  }
  
  public void D() {
    this.at.b.u();
  }
  
  public Color P() {
    Color color = Color.BLUE;
    for (Depict depict : this.a.depicts) {
      if (this.c.b.contains(depict.getEntity()))
        color = depict.getColor(); 
    } 
    return color;
  }
}
