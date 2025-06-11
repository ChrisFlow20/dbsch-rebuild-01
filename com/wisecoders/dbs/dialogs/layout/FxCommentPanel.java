package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.fx.FxDiagramPane;
import com.wisecoders.dbs.diagram.model.Callout;
import com.wisecoders.dbs.diagram.model.CalloutPointer;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Point;
import com.wisecoders.dbs.diagram.model.Rect;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.CommentTag;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.UniqueArrayList;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.VBox$;
import com.wisecoders.dbs.sys.fx.VGrowBox$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FxCommentPanel extends TabPane {
  public final TextArea a = new TextArea();
  
  private final Unit b;
  
  private final Workspace c;
  
  private final Callout d;
  
  private final Rx e = new Rx(FxCommentPanel.class, this);
  
  private final SplitMenuButton f = new SplitMenuButton();
  
  private CalloutPointer g = CalloutPointer.e;
  
  private final GridPane$ h = (new GridPane$()).a(new int[] { -1, -2 }).b(new int[] { -1, -2 }).a(3.0D);
  
  private final String i;
  
  private final TableView j = new TableView();
  
  private final ObservableList k = FXCollections.observableArrayList();
  
  private final HashMap l = new HashMap<>();
  
  private final TableColumn m = new TableColumn();
  
  private final TableColumn n = new TableColumn("Value");
  
  public FxCommentPanel(Workspace paramWorkspace, Unit paramUnit) {
    this(paramWorkspace, true, paramUnit, false);
  }
  
  public FxCommentPanel(Workspace paramWorkspace, boolean paramBoolean, Unit paramUnit) {
    this(paramWorkspace, paramBoolean, paramUnit, false);
  }
  
  public FxCommentPanel(Workspace paramWorkspace, boolean paramBoolean1, Unit paramUnit, boolean paramBoolean2) {
    this.c = paramWorkspace;
    this.b = paramUnit;
    this.i = this.e.H("newTag");
    this.e.a("flagHasLayout", () -> true);
    this.e.a("flagSelectedProperty", () -> (this.j.getSelectionModel().getSelectedItem() != null));
    if (paramUnit instanceof Callout) {
      this.d = (Callout)paramUnit;
    } else if (paramWorkspace.p() == null) {
      this.d = null;
    } else {
      this.d = (paramWorkspace.p()).diagram.getCalloutFor(paramUnit);
    } 
    this.a.setPrefRowCount(7);
    this.a.setWrapText(true);
    this.f.getItems().addAll(this.e.e(new String[] { "calloutNo", "calloutRound", "calloutSV", "calloutSE", "calloutNV", "calloutNE" }));
    this.f.setOnAction(paramActionEvent -> this.f.show());
    this.f.setFocusTraversable(false);
    this.m.setGraphic((Node)this.e.e("nameColumnLabel"));
    if (paramUnit != null) {
      this.a.setText(paramUnit.getComment());
      UniqueArrayList uniqueArrayList = new UniqueArrayList();
      if (paramUnit.getCommentTags() != null) {
        this.l.putAll(paramUnit.getCommentTags());
        uniqueArrayList.addAll(this.l.keySet());
      } 
      uniqueArrayList.sort(String::compareTo);
      uniqueArrayList.addAll(b(paramUnit));
      this.k.addAll(uniqueArrayList);
    } 
    this.k.add(this.i);
    if (this.d != null) {
      this.g = this.d.a();
    } else if (paramWorkspace.o() != null) {
      if (paramBoolean2) {
        this.g = CalloutPointer.a;
      } else {
        this.g = CalloutPointer.e;
      } 
    } 
    d();
    this.a.setFocusTraversable(false);
    this.h.a((Node)this.a, "0,0,0,1,f,f");
    this.h.a((Node)this.f, "1,0,c,b");
    Tab tab = new Tab(null, (Node)this.h);
    tab.setGraphic((Node)BootstrapIcons.blockquote_left.glyph(new String[0]));
    tab.setClosable(false);
    getTabs().add(tab);
    this.m.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper((String)paramCellDataFeatures.getValue()));
    this.m.setCellFactory(paramTableColumn -> new b(this));
    this.m.setEditable(true);
    this.m.setOnEditCommit(paramCellEditEvent -> {
          String str1 = (String)paramCellEditEvent.getOldValue();
          String str2 = (String)paramCellEditEvent.getNewValue();
          String str3 = (String)this.l.get(str1);
          this.k.remove(str1);
          this.l.remove(str1);
          if (StringUtil.isFilled(str2)) {
            if (!this.k.contains(str2))
              this.k.add(str2); 
            if (str3 != null)
              this.l.put(str2, str3); 
          } 
          if (!this.k.contains(this.i))
            this.k.add(this.i); 
          this.j.refresh();
          this.j.getSelectionModel().select(str2);
          paramWorkspace.u();
        });
    this.n.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper((String)this.l.get(paramCellDataFeatures.getValue())));
    this.n.setEditable(true);
    this.n.setCellFactory(paramTableColumn -> new c(this));
    this.n.setOnEditCommit(paramCellEditEvent -> {
          this.l.put((String)paramCellEditEvent.getRowValue(), (String)paramCellEditEvent.getNewValue());
          this.j.refresh();
          paramWorkspace.u();
        });
    this.j.setContextMenu((new ContextMenu$()).a(this.e, new String[] { "editTagName", "editTagValue", "tagManager", "deleteTag" }));
    VBox.setVgrow((Node)this.j, Priority.ALWAYS);
    this.j.setEditable(true);
    this.j.getColumns().addAll((Object[])new TableColumn[] { this.m, this.n });
    this.j.setPrefHeight(40.0D);
    this.j.setItems(this.k);
    this.j.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    this.j.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.e.b());
    HBox$ hBox$ = (new HBox$()).a(3.0D);
    HBox.setHgrow((Node)this.j, Priority.ALWAYS);
    hBox$.getChildren().add(this.j);
    hBox$.getChildren().add((new VBox$()).b(3).d((Node)new VGrowBox$()).b(this.e.a(new String[] { "editTagValue", "deleteTag", "tagManager" })));
    if (paramBoolean1) {
      Tab tab1 = new Tab(null, (Node)hBox$);
      tab1.setGraphic((Node)BootstrapIcons.bookmarks.glyph(new String[0]));
      tab1.setClosable(false);
      getTabs().add(tab1);
      if (StringUtil.isEmpty(this.a.getText()) && !this.l.isEmpty())
        getSelectionModel().select(tab1); 
    } 
    setSide(Side.LEFT);
    Rx.a(this);
    getStyleClass().add("no-border");
  }
  
  private List b(Unit paramUnit) {
    ArrayList<String> arrayList = new ArrayList();
    if (this.c.m() != null)
      for (CommentTag commentTag : Dbms.get(this.c.m().getDbId()).getTags()) {
        if ((paramUnit instanceof com.wisecoders.dbs.schema.Project && commentTag.isForProject.get()) || (paramUnit instanceof com.wisecoders.dbs.schema.Schema && commentTag.isForSubjectArea
          .get()) || (paramUnit instanceof com.wisecoders.dbs.schema.Table && commentTag.isForEntity
          .get()) || (paramUnit instanceof com.wisecoders.dbs.schema.Column && commentTag.isForField
          .get()) || (paramUnit instanceof com.wisecoders.dbs.schema.Index && commentTag.isForIndex
          .get()) || (paramUnit instanceof com.wisecoders.dbs.schema.ForeignKey && commentTag.isForRelation
          .get()))
          arrayList.add(commentTag.getName()); 
      }  
    return arrayList;
  }
  
  public void a(Unit paramUnit) {
    String str = this.a.getText();
    if (paramUnit != null) {
      paramUnit.setComment(str);
      paramUnit.setCommentTags(a());
    } 
    if (this.g == CalloutPointer.e || StringUtil.isEmptyTrim(str)) {
      if (this.d != null) {
        this.d.markForDeletion();
        if (this.c.o() != null) {
          (this.c.o()).c.k();
          this.c.u();
        } 
      } 
    } else if (this.c.o() != null) {
      FxDiagramPane fxDiagramPane = (this.c.o()).c;
      Callout callout = this.d;
      if (this.d == null) {
        Rect rect = fxDiagramPane.b(paramUnit);
        if (rect == null) {
          callout = fxDiagramPane.a(paramUnit, fxDiagramPane.v());
        } else {
          callout = fxDiagramPane.a(paramUnit, new Point(rect.a(), rect.b() - (2 * Diagram.cell)));
        } 
      } 
      if (callout != null) {
        callout.setComment(str);
        callout.setCommentTags(a());
        callout.a(this.g);
        this.c.u();
      } 
      fxDiagramPane.k();
    } 
  }
  
  public Map a() {
    return this.l.isEmpty() ? null : this.l;
  }
  
  public String b() {
    return this.a.getText();
  }
  
  @Action(b = "flagHasLayout")
  public void calloutNV() {
    this.g = CalloutPointer.c;
    d();
  }
  
  @Action(b = "flagHasLayout")
  public void calloutNE() {
    this.g = CalloutPointer.d;
    d();
  }
  
  @Action(b = "flagHasLayout")
  public void calloutSV() {
    this.g = CalloutPointer.a;
    d();
  }
  
  @Action(b = "flagHasLayout")
  public void calloutSE() {
    this.g = CalloutPointer.b;
    d();
  }
  
  @Action(b = "flagHasLayout")
  public void calloutNo() {
    this.g = CalloutPointer.e;
    d();
  }
  
  @Action(b = "flagHasLayout")
  public void calloutRound() {
    this.g = CalloutPointer.f;
    d();
  }
  
  private void d() {
    this.e.b();
    switch (FxCommentPanel$1.a[this.g.ordinal()]) {
      case 1:
        this.f.setGraphic((Node)new ImageView(Rx.c("hint_no.png")));
        break;
      case 2:
        this.f.setGraphic((Node)new ImageView(Rx.c("hint_round.png")));
        break;
      case 3:
        this.f.setGraphic((Node)new ImageView(Rx.c("hint_nv.png")));
        break;
      case 4:
        this.f.setGraphic((Node)new ImageView(Rx.c("hint_ne.png")));
        break;
      case 5:
        this.f.setGraphic((Node)new ImageView(Rx.c("hint_se.png")));
        break;
      case 6:
        this.f.setGraphic((Node)new ImageView(Rx.c("hint_sv.png")));
        break;
    } 
  }
  
  void a(Node paramNode) {
    this.h.a(paramNode, "1,1,c,c");
  }
  
  @Action(b = "flagSelectedProperty")
  public void editTagName() {
    this.j.edit(this.j.getSelectionModel().getSelectedIndex(), this.m);
  }
  
  @Action
  public void tagManager() {
    (new FxTagsDialog(this.c, getScene())).showDialog();
  }
  
  @Action(b = "flagSelectedProperty")
  public void editTagValue() {
    this.j.edit(this.j.getSelectionModel().getSelectedIndex(), this.n);
  }
  
  @Action(b = "flagSelectedProperty")
  public void deleteTag() {
    String str = (String)this.j.getSelectionModel().getSelectedItem();
    this.k.remove(str);
    this.l.remove(str);
    this.j.refresh();
  }
  
  private void a(TreeUnit paramTreeUnit, List paramList) {
    if (paramTreeUnit.getCommentTags() != null)
      paramList.addAll(paramTreeUnit.getCommentTags().keySet()); 
    for (byte b = 0; b < paramTreeUnit.getChildrenCount(); b++)
      a(paramTreeUnit.getChildAt(b), paramList); 
  }
  
  private void a(String paramString, TreeUnit paramTreeUnit, List<String> paramList) {
    if (paramTreeUnit.getCommentTags() != null)
      paramList.add((String)paramTreeUnit.getCommentTags().get(paramString)); 
    for (byte b = 0; b < paramTreeUnit.getChildrenCount(); b++)
      a(paramString, paramTreeUnit.getChildAt(b), paramList); 
  }
  
  public void c() {
    this.k.sort((paramString1, paramString2) -> {
          Boolean bool = Boolean.valueOf(this.l.containsKey(paramString1));
          boolean bool1 = this.l.containsKey(paramString2);
          return (bool.booleanValue() != bool1) ? bool.compareTo(Boolean.valueOf(bool1)) : paramString1.compareTo(paramString2);
        });
  }
}
