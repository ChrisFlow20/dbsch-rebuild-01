package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dialogs.web.fx.FxVideoDialog;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.tips.fx.FxTipsDialog;
import com.wisecoders.dbs.tips.tips.QuickTour;
import com.wisecoders.dbs.tips.tips.QuickTour$Type;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class FxTechnicalSupportDialog extends Dialog$ {
  private final TextField a = new TextField();
  
  private final TextField b = new TextField();
  
  private final TextArea c = new TextArea();
  
  private final ListView d = new ListView();
  
  private final boolean e;
  
  private static final String f = "SupportEmail";
  
  public FxTechnicalSupportDialog(Window paramWindow) {
    this(paramWindow, (String)null, (String)null, false);
  }
  
  public FxTechnicalSupportDialog(Window paramWindow, String paramString1, String paramString2, boolean paramBoolean) {
    super(paramWindow);
    this.e = paramBoolean;
    this.rx.G(paramString2);
    setDialogTitleAndHeader();
    if (paramString1 != null)
      setDialogTitle(paramString1); 
    File file = Sys.h.toFile();
    if (file.exists())
      this.d.getItems().addAll((Object[])file.listFiles()); 
    this.d.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.d.selectionModelProperty().addListener((paramObservableValue, paramMultipleSelectionModel1, paramMultipleSelectionModel2) -> this.rx.b());
    this.d.setCellFactory(paramListView -> new h());
    this.d.setPrefHeight(120.0D);
    this.b.setText(Pref.c("SupportEmail", (License.a()).c));
    this.c.setPrefSize(600.0D, 300.0D);
    if ("feedback".equals(paramString2)) {
      this.a.setText("Feedback");
      setInitFocusedNode((Node)this.c);
    } else {
      setInitFocusedNode((Node)this.a);
    } 
    this.a.setPromptText(this.rx.H("subjectField.promptText"));
    this.c.setPromptText(this.rx.H("messageField.promptText"));
    this.c.setWrapText(true);
    this.b.setPromptText(this.rx.H("emailField.promptText"));
    this.rx.a("flagSelectedAttachment", () -> (this.d.getSelectionModel().getSelectedItem() != null));
    this.rx.a("flagHasMessage", () -> StringUtil.isFilledTrim(this.c.getText()));
    this.c.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.rx.b());
    this.b.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.rx.b());
    initModality(Modality.NONE);
    setResultConverter(paramButtonType -> (paramButtonType != null && paramButtonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) ? (Boolean)getResult() : null);
  }
  
  public FxTechnicalSupportDialog a(String paramString) {
    this.a.setText(paramString);
    return this;
  }
  
  public FxTechnicalSupportDialog b(String paramString) {
    this.c.setText(paramString);
    return this;
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l().a(new int[] { -2, -2, -1 }).b(new int[] { -2, -2, -1, -2 });
    if (!this.e)
      gridPane$.a((Node)this.rx.e("emailLabel"), "1,0,r,c")
        .a((Node)this.b, "2,0,f,c"); 
    gridPane$.a((Node)this.rx.e("subjectLabel"), "1,1,r,c")
      .a((Node)this.a, "2,1,f,c")
      .a((Node)this.rx.e("messageLabel"), "1,2,r,c")
      .a((Node)this.c, "2,2,f,c");
    if (!this.e)
      gridPane$.a((Node)this.rx.e("attachmentLabel"), "1,3,r,c")
        .a((Node)this.d, "2,3,f,c")
        .a((Node)(new FlowPane$()).a().a(new Node[] { (Node)this.rx.j("addScreenshot"), (Node)this.rx.j("addAttachment"), (Node)this.rx.j("deleteAttachment") }, ), "2,4,l,c"); 
    return (Node)gridPane$;
  }
  
  public boolean apply() {
    return false;
  }
  
  public void createButtons() {
    createActionButton("send");
    createCancelButton();
    createActionButton("quickTour", ButtonBar.ButtonData.HELP_2);
    createActionButton("videoTutorials", ButtonBar.ButtonData.HELP_2);
  }
  
  @Action(b = "flagHasMessage")
  public Task send() {
    if (StringUtil.isEmptyTrim(this.c.getText())) {
      this.rx.a(getDialogScene(), "setMessage", new String[0]);
      return null;
    } 
    if (this.b.getText() != null)
      Pref.a("SupportEmail", this.b.getText()); 
    return new FxTechnicalSupportDialog$1(this, this, this.a.getText(), this.b.getText(), this.c.getText(), (List)this.d.getItems());
  }
  
  @Action
  public void addAttachment() {
    File file = FxFileChooser.a(getDialogScene(), "Choose File", FileOperation.a, new FileType[] { FileType.D });
    if (file != null)
      this.d.getItems().add(file); 
  }
  
  @Action(d = "flagSelectedAttachment")
  public void deleteAttachment() {
    if (this.rx.a(getDialogScene(), "confirmDeleteAttachment"))
      this.d.getItems().removeAll((Collection)this.d.getSelectionModel().getSelectedItems()); 
  }
  
  @Action
  public void addScreenshot() {
    ArrayList<i> arrayList = new ArrayList();
    for (Iterator<Screen> iterator = Screen.getScreens().iterator(); iterator.hasNext(); ) {
      Screen screen = iterator.next();
      i i = new i(screen, Sys.h);
      i.setOnHiding(paramWindowEvent -> {
            File file = parami.a();
            if (file != null)
              this.d.getItems().add(file); 
            for (i i1 : paramList) {
              if (i1 != parami)
                i1.hide(); 
            } 
          });
      arrayList.add(i);
      i.show();
    } 
  }
  
  @Action
  public void record() {}
  
  @Action
  public void quickTour() {
    new FxTipsDialog(getDialogScene(), new QuickTour(QuickTour$Type.b, true));
  }
  
  @Action
  public void videoTutorials() {
    (new FxVideoDialog(getDialogScene(), null)).showDialog();
  }
}
