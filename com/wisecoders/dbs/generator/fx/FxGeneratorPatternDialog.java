package com.wisecoders.dbs.generator.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.generator.engine.generators.Generator;
import com.wisecoders.dbs.generator.engine.plan.DefinedPattern;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import groovy.lang.MissingPropertyException;
import java.io.File;
import java.text.ParseException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class FxGeneratorPatternDialog extends Dialog$ implements WorkspaceWindow {
  private final Column a;
  
  private final boolean b;
  
  private final ObservableList c = FXCollections.observableArrayList();
  
  private static final int d = 12;
  
  private final Workspace e;
  
  private final Label f = this.rx.e("patternLabel");
  
  private final Label i = this.rx.e("samplesLabel");
  
  private final StyledEditor j = new StyledEditor();
  
  private final TableView k = new TableView();
  
  private final Timeline l;
  
  private long m = -1L;
  
  private boolean n = false;
  
  private final TableColumn o;
  
  public FxGeneratorPatternDialog(WorkspaceWindow paramWorkspaceWindow, Column paramColumn, String paramString, boolean paramBoolean) {
    super(paramWorkspaceWindow);
    this.o = new TableColumn("Sample");
    this.e = paramWorkspaceWindow.getWorkspace();
    this.a = paramColumn;
    this.b = paramBoolean;
    setGraphic(BootstrapIcons.dice_5_fill);
    this.rx.a("edited", () -> this.n);
    this.j.addEventHandler(KeyEvent.KEY_TYPED, paramKeyEvent -> {
          this.n = true;
          this.rx.b();
          b();
        });
    this.k.setItems(this.c);
    this.j.b(paramString);
    this.rx.b();
    a();
    b();
    this.j.a((paramObservableValue, paramInteger1, paramInteger2) -> b());
    setResultConverter(paramButtonType -> (paramButtonType == ButtonType.OK) ? this.j.t() : null);
    this.l = new Timeline(new KeyFrame[] { new KeyFrame(Duration.millis(500.0D), paramActionEvent -> c(), new javafx.animation.KeyValue[0]) });
    this.l.setCycleCount(-1);
    this.l.play();
    setOnHiding(paramDialogEvent -> this.l.stop());
  }
  
  private void a() {
    this.o.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    this.k.getColumns().add(this.o);
    this.k.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).a(new int[] { -2, -1, -2 }).b(new int[] { -1, -2, -1 }).e();
    gridPane$.a((Node)this.f, "0,0,r,c");
    gridPane$.a((Node)this.j, "1,0,f,f");
    if (this.b)
      gridPane$.a((Node)(new FlowPane$()).a().a(this.rx.c(new String[] { "choose", "chooseFilePattern" }, )), "1,1,c,c"); 
    gridPane$.a((Node)this.i, "0,2,r,c");
    gridPane$.a((Node)this.k, "1,2,f,f");
    this.k.setPrefHeight(200.0D);
    setRegionPrefSize((Region)gridPane$, 800.0D, 600.0D);
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("random-data-generator.html");
  }
  
  public boolean apply() {
    return true;
  }
  
  @Action
  public void choose() {
    (new FxGeneratorRepositoryDialog(this)).showDialog().ifPresent(paramDefinedPattern -> {
          this.j.b(paramDefinedPattern.e());
          b();
        });
  }
  
  @Action
  public void chooseFilePattern() {
    File file = FxFileChooser.a(getDialogPane().getScene(), "Choose File", FileOperation.a, new FileType[] { FileType.i });
    if (file != null)
      this.j.b("list:file=" + file.getAbsolutePath()); 
  }
  
  private void b() {
    this.m = System.currentTimeMillis();
  }
  
  private void c() {
    if (this.m > -1L && System.currentTimeMillis() > this.m + 1000L) {
      this.m = -1L;
      try {
        String str = this.j.t();
        this.j.w();
        this.c.clear();
        if (str != null && str.length() > 0) {
          Generator generator = Generator.getGenerator(str, (this.a != null) ? this.a.getGeneratorNullsPercentage() : 0, -1);
          for (byte b = 0; b < 12; b++) {
            Object object = generator.generateWithNulls(null, this.a);
            this.c.add((object != null) ? object.toString() : null);
          } 
        } 
      } catch (ParseException parseException) {
        this.j.b(parseException.getErrorOffset(), parseException.getLocalizedMessage());
        this.c.add(parseException.getLocalizedMessage());
      } catch (MissingPropertyException missingPropertyException) {
        this.c.add("Will be evaluated in the table dialog");
      } catch (Throwable throwable) {
        this.c.add(throwable.getLocalizedMessage());
      } 
      this.k.refresh();
      this.rx.b();
    } 
  }
  
  public Workspace getWorkspace() {
    return this.e;
  }
}
