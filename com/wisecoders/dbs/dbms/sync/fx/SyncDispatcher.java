package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.dbms.reverseEngineer.tasks.GeneralKeepCommentsSyncFilter;
import com.wisecoders.dbs.dbms.reverseEngineer.tasks.SyncMode;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncAction;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.diagram.fx.FxDiagramPane;
import com.wisecoders.dbs.diagram.model.ArrangerMode;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.fx.Alert$;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class SyncDispatcher {
  public static void a(Workspace paramWorkspace, SyncPair paramSyncPair, SyncMode paramSyncMode, boolean paramBoolean1, String paramString1, String paramString2, boolean paramBoolean2) {
    boolean bool1 = false, bool2 = false;
    boolean bool3 = (paramWorkspace.p() != null && (paramWorkspace.p()).diagram.depicts.isEmpty()) ? true : false;
    int i = paramSyncPair.getDiffCount();
    if (i == 0) {
      if (!paramBoolean2)
        paramWorkspace.getRx().a(paramWorkspace, "Schemas are identical", new String[0]); 
    } else if (!paramWorkspace.m().hasEntities()) {
      bool2 = true;
    } else if (paramSyncMode == SyncMode.a) {
      Alert$ alert$ = (new Alert$(Alert.AlertType.CONFIRMATION)).a(paramWorkspace).a("Schema Synchronization Dialog").c("- 'Review Differences' to selective apply differences in the model or in the database.\n- 'Refresh Model' to merge all differences in the model.").b("Found " + i + ((i > 1) ? " differences" : " difference") + " between the DbSchema model and the database.\nReview the differences or refresh the model ?").b().a(ButtonType.NEXT, "Refresh Model").a(ButtonType.PREVIOUS, "Review Differences").a(ButtonType.CANCEL);
      Optional<ButtonType> optional = alert$.showAndWait();
      if (optional.isPresent())
        if (optional.get() == ButtonType.NEXT) {
          bool2 = true;
        } else if (optional.get() == ButtonType.PREVIOUS) {
          bool1 = true;
        }  
    } else {
      bool1 = true;
    } 
    if (bool2) {
      if (paramWorkspace.H() && paramSyncPair.actionWillDrop(SyncAction.toLeft, SyncSide.left, false) > 0) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(paramWorkspace.getWindow());
        alert.setTitle("Modify DbSchema Model");
        alert.setHeaderText("Refreshing the local model will irreversibly lose all DbSchema Model changes.\nContinue ?");
        ButtonType buttonType1 = new ButtonType("Continue", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonType2 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll((Object[])new ButtonType[] { buttonType1, buttonType2 });
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.isPresent() && optional.get() != buttonType1)
          return; 
      } 
      paramSyncPair.setAction(SyncAction.toLeft, true);
      paramSyncPair.filter(new GeneralKeepCommentsSyncFilter());
      paramSyncPair.mergeInto(SyncSide.left, SyncAction.toLeft, paramWorkspace.o());
      paramWorkspace.u();
    } 
    if (bool1)
      (new FxSynchronizationDialog(paramWorkspace, paramSyncPair, paramBoolean1, paramString1, paramString2)).showDialog(); 
    if (bool3) {
      FxDiagramPane fxDiagramPane = (paramWorkspace.o()).c;
      fxDiagramPane.d().hideFunctionalColumns();
      fxDiagramPane.d().hideColumnsOnLargeDepicts();
      fxDiagramPane.a(ArrangerMode.REMOVE_GROUPS);
      Depict depict = fxDiagramPane.d().getMostReferredDepict();
      if (depict != null)
        fxDiagramPane.c(depict.getEntity()); 
    } 
    paramWorkspace.y();
  }
}
