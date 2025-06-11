package com.wisecoders.dbs.dbms.connect.fx;

import com.wisecoders.dbs.config.fx.FxSettingsDialog;
import com.wisecoders.dbs.config.fx.FxSettingsDialog$SelectTab;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.connect.model.KeyStoreFile;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.io.File;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class FxKeyStorePanel extends GridPane$ {
  private final TreeTableView a = new TreeTableView();
  
  private final Rx e = new Rx(FxKeyStorePanel.class, this);
  
  private final KeyStoreFile f = new KeyStoreFile(Sys.q.toFile(), Sys.B.keyStorePassword.c_());
  
  private final FxKeyStorePanel$FxTreeItem g = new FxKeyStorePanel$FxTreeItem(new e(this.f, f.b, "Java Default", this.e.H("undefined"), this.e.H("keyStore.description")));
  
  private final SimpleDateFormat h;
  
  public FxKeyStorePanel() {
    this.h = new SimpleDateFormat(Sys.B.dateFormat.c_());
    this.e.a("flagHasStore", () -> (this.a.getSelectionModel().getSelectedItem() != null && ((e)((TreeItem)this.a.getSelectionModel().getSelectedItem()).getValue()).a != null));
    this.e.a("flagHasSelectedStore", () -> (this.a.getSelectionModel().getSelectedItem() != null && ((e)((TreeItem)this.a.getSelectionModel().getSelectedItem()).getValue()).d == f.b));
    this.e.a("flagHasSelectedCertificate", () -> (this.a.getSelectionModel().getSelectedItem() != null && ((e)((TreeItem)this.a.getSelectionModel().getSelectedItem()).getValue()).d == f.c));
    TreeTableColumn treeTableColumn1 = new TreeTableColumn("Alias");
    treeTableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn1.setCellFactory(paramTreeTableColumn -> new FxKeyStorePanel$1(this));
    TreeTableColumn treeTableColumn2 = new TreeTableColumn("Status");
    treeTableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn2.setCellFactory(paramTreeTableColumn -> new FxKeyStorePanel$2(this));
    TreeTableColumn treeTableColumn3 = new TreeTableColumn("Description");
    treeTableColumn3.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn3.setCellFactory(paramTreeTableColumn -> new FxKeyStorePanel$3(this));
    a(this.g);
    this.a.getColumns().addAll((Object[])new TreeTableColumn[] { treeTableColumn1, treeTableColumn2, treeTableColumn3 });
    Rx.b(this.a, new double[] { 0.3D, 0.2D, 0.5D });
    this.a.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    this.a.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> this.e.b());
    FxKeyStorePanel$FxTreeItem fxKeyStorePanel$FxTreeItem = new FxKeyStorePanel$FxTreeItem(new e(null, f.a, "KeyStores", null, this.e.H("root.description")));
    fxKeyStorePanel$FxTreeItem.getChildren().addAll((Object[])new TreeItem[] { this.g });
    fxKeyStorePanel$FxTreeItem.setExpanded(true);
    this.a.setRoot(fxKeyStorePanel$FxTreeItem);
    this.a.setPrefSize(400.0D, 250.0D);
    ContextMenu$ contextMenu$ = new ContextMenu$();
    contextMenu$.getItems().addAll(this.e.e(new String[] { "importCertificate", "separator", "deleteStore", "deleteCertificate" }));
    this.a.setContextMenu(contextMenu$);
    l().a(new int[] { -2, -1 }).b(new int[] { -2, -1, -2 }).a((Node)BootstrapIcons.key_fill.glyph(new String[] { "glyph-48", "glyph-3d" }, ), "0,0,c,c").a((Node)this.e.e("info"), "1,0,l,c").a((Node)this.a, "0,1,1,1,f,c").a((Node)(new FlowPane$()).a().a(this.e.c(new String[] { "importCertificate", "settings" }, )), "0,2,1,2,l,c");
  }
  
  private void a(FxKeyStorePanel$FxTreeItem paramFxKeyStorePanel$FxTreeItem) {
    KeyStoreFile keyStoreFile = ((e)paramFxKeyStorePanel$FxTreeItem.getValue()).a;
    if (keyStoreFile != null)
      try {
        ((e)paramFxKeyStorePanel$FxTreeItem.getValue()).a("Undefined");
        paramFxKeyStorePanel$FxTreeItem.getChildren().clear();
        keyStoreFile.a();
        KeyStore keyStore = keyStoreFile.b();
        if (keyStore != null) {
          Enumeration<String> enumeration = keyStore.aliases();
          while (enumeration.hasMoreElements()) {
            String str1 = enumeration.nextElement();
            Certificate certificate = keyStore.getCertificate(str1);
            String str2 = null, str3 = null;
            if (certificate instanceof X509Certificate) {
              X509Certificate x509Certificate = (X509Certificate)certificate;
              str2 = "Valid from: " + this.h.format(x509Certificate.getNotBefore()) + " to: " + this.h.format(x509Certificate.getNotAfter());
              str3 = String.valueOf(x509Certificate.getIssuerX500Principal()) + " " + String.valueOf(x509Certificate.getIssuerX500Principal());
            } 
            FxKeyStorePanel$FxTreeItem fxKeyStorePanel$FxTreeItem = new FxKeyStorePanel$FxTreeItem(new e(keyStoreFile, f.c, str1, str2, str3));
            paramFxKeyStorePanel$FxTreeItem.getChildren().add(fxKeyStorePanel$FxTreeItem);
            Certificate[] arrayOfCertificate = keyStore.getCertificateChain(str1);
            if (arrayOfCertificate != null) {
              FxKeyStorePanel$FxTreeItem fxKeyStorePanel$FxTreeItem1 = fxKeyStorePanel$FxTreeItem;
              for (Certificate certificate1 : arrayOfCertificate) {
                FxKeyStorePanel$FxTreeItem fxKeyStorePanel$FxTreeItem2 = new FxKeyStorePanel$FxTreeItem(new e(keyStoreFile, f.c, certificate1.getType(), null, null));
                fxKeyStorePanel$FxTreeItem1.getChildren().add(fxKeyStorePanel$FxTreeItem2);
                fxKeyStorePanel$FxTreeItem1 = fxKeyStorePanel$FxTreeItem2;
              } 
            } 
          } 
          ((e)paramFxKeyStorePanel$FxTreeItem.getValue()).a("");
          paramFxKeyStorePanel$FxTreeItem.setExpanded(true);
        } 
      } catch (Exception exception) {
        Log.b(exception);
      }  
  }
  
  private KeyStore a() {
    return (new KeyStoreFile(new File(System.getProperty("java.home") + System.getProperty("java.home")), "changeit")).a().b();
  }
  
  @Action(b = "flagHasStore")
  public void importCertificate() {
    KeyStoreFile keyStoreFile = (this.a.getSelectionModel().getSelectedItem() != null) ? ((e)((TreeItem)this.a.getSelectionModel().getSelectedItem()).getValue()).a : null;
    if (keyStoreFile != null) {
      File file = FxFileChooser.a(getScene(), this.e.H("openCertificateFile"), FileOperation.a, new FileType[] { FileType.h });
      if (file != null)
        try {
          if (file.getName().endsWith(".pem") || file.getName().endsWith(".p7c")) {
            keyStoreFile.b(file);
          } else {
            keyStoreFile.a(file);
          } 
          keyStoreFile.d();
          a(this.g);
        } catch (CertificateException|java.security.KeyStoreException|java.io.IOException|java.security.NoSuchAlgorithmException certificateException) {
          this.e.a(getScene(), certificateException);
        }  
    } 
  }
  
  @Action
  public void settings() {
    (new FxSettingsDialog(getScene(), FxSettingsDialog$SelectTab.a, Sys.B.keyStorePassword)).showDialog();
  }
  
  @Action(b = "flagHasSelectedStore")
  public void deleteStore() {
    FxKeyStorePanel$FxTreeItem fxKeyStorePanel$FxTreeItem = (FxKeyStorePanel$FxTreeItem)this.a.getSelectionModel().getSelectedItem();
    if (fxKeyStorePanel$FxTreeItem != null) {
      e e = (e)fxKeyStorePanel$FxTreeItem.getValue();
      try {
        if (e.d == f.b && e.a.b() != null) {
          FileUtils.b(e.a.a);
          a(this.g);
        } 
      } catch (SecurityException securityException) {
        this.e.a(getScene(), securityException);
      } 
    } 
  }
  
  @Action(b = "flagHasSelectedCertificate")
  public void deleteCertificate() {
    FxKeyStorePanel$FxTreeItem fxKeyStorePanel$FxTreeItem = (FxKeyStorePanel$FxTreeItem)this.a.getSelectionModel().getSelectedItem();
    if (fxKeyStorePanel$FxTreeItem != null) {
      e e = (e)fxKeyStorePanel$FxTreeItem.getValue();
      try {
        if (e.d == f.c && e.a.b() != null) {
          e.a.b().deleteEntry(e.b);
          e.a.d();
          a(this.g);
        } 
      } catch (SecurityException|java.security.KeyStoreException|java.io.IOException|java.security.NoSuchAlgorithmException|CertificateException securityException) {
        this.e.a(getScene(), securityException);
      } 
    } 
  }
}
