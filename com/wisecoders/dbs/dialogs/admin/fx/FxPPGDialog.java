package com.wisecoders.dbs.dialogs.admin.fx;

import com.wisecoders.dbs.dialogs.admin.tasks.PPGApplySubscriptionDiscount;
import com.wisecoders.dbs.dialogs.admin.tasks.PPGChangeSubscriptionPaymentDate;
import com.wisecoders.dbs.dialogs.admin.tasks.PPGChangeSubscriptionQuantity;
import com.wisecoders.dbs.dialogs.admin.tasks.PPGChangeSubscriptionRecurringPrice;
import com.wisecoders.dbs.dialogs.admin.tasks.PPGGetSubscriptionDetails;
import com.wisecoders.dbs.dialogs.admin.tasks.PPGMigrateSubscriptions;
import com.wisecoders.dbs.dialogs.admin.tasks.PPGReactivateSubscriptions;
import com.wisecoders.dbs.dialogs.admin.tasks.PPGRenameSubscription;
import com.wisecoders.dbs.dialogs.admin.tasks.PPGSubscriptionToAffiliate;
import com.wisecoders.dbs.dialogs.admin.tasks.PPGTask;
import com.wisecoders.dbs.dialogs.admin.tasks.PPGTerminateSubscriptions;
import com.wisecoders.dbs.dialogs.system.ActivationTask$Mode;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.VBox$;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Window;

public class FxPPGDialog extends Dialog$ {
  private final Label a = new Label();
  
  private final ComboBox b = new ComboBox();
  
  private final TextArea c = new TextArea();
  
  private final GridPane$ d = (new GridPane$()).l().a(new int[] { -2, -1 });
  
  private final HashMap e = new HashMap<>();
  
  public FxPPGDialog(Window paramWindow) {
    super(paramWindow);
    this.b.getItems().addAll((Object[])new PPGTask[] { new PPGRenameSubscription(), new PPGSubscriptionToAffiliate(), new PPGReactivateSubscriptions(), new PPGChangeSubscriptionQuantity(), new PPGChangeSubscriptionPaymentDate(), new PPGChangeSubscriptionRecurringPrice(), new PPGApplySubscriptionDiscount(), new PPGTerminateSubscriptions(), new PPGGetSubscriptionDetails(), new PPGMigrateSubscriptions() });
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramPPGTask1, paramPPGTask2) -> a(paramPPGTask2));
  }
  
  public Node createContentPane() {
    this.c.setPrefSize(700.0D, 450.0D);
    VBox$.setVgrow((Node)this.c, Priority.ALWAYS);
    return (Node)(new VBox$()).l().c(new Node[] { (Node)this.b, (Node)this.a, (Node)this.d, (Node)this.c });
  }
  
  private void a(PPGTask paramPPGTask) {
    this.a.setText(paramPPGTask.a());
    Map map = paramPPGTask.b();
    byte b = 0;
    this.d.getChildren().clear();
    for (String str : map.keySet()) {
      this.d.add((Node)new Label(str), 0, b);
      TextField textField = new TextField(Pref.d(str, (String)map.get(str)));
      GridPane.setFillWidth((Node)textField, Boolean.valueOf(true));
      this.e.put(str, textField);
      this.d.add((Node)textField, 1, b);
      b++;
    } 
  }
  
  public void createButtons() {
    createActionButton("send");
    createActionButton("activate");
    createCloseButton();
  }
  
  @Action
  public Task activate() {
    return new FxPPGDialog$1(this, ActivationTask$Mode.a);
  }
  
  public boolean apply() {
    return true;
  }
  
  @Action
  public Task send() {
    d d = new d(this);
    d.messageProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.c.appendText("\n" + paramString2));
    return d;
  }
}
