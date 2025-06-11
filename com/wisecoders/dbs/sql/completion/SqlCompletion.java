package com.wisecoders.dbs.sql.completion;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.editors.text.StyledEditorCompletion;
import com.wisecoders.dbs.editors.text.StyledEditorCompletion$Mode;
import com.wisecoders.dbs.editors.text.StyledEditorCompletionMenu;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sql.fx.SqlRange;
import com.wisecoders.dbs.sys.SqlUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class SqlCompletion implements StyledEditorCompletion {
  private final SqlCompletionParser a;
  
  private final SqlRange b;
  
  private final Project c;
  
  public SqlCompletion(StyledEditor paramStyledEditor, Project paramProject) {
    this.c = paramProject;
    this.b = new SqlRange(paramStyledEditor);
    this.a = new SqlCompletionParser(paramProject);
  }
  
  public void a(StyledEditor paramStyledEditor, StyledEditorCompletionMenu paramStyledEditorCompletionMenu, StyledEditorCompletion$Mode paramStyledEditorCompletion$Mode, boolean paramBoolean) {
    if (paramStyledEditorCompletion$Mode == StyledEditorCompletion$Mode.b) {
      this.b.b(paramStyledEditor.i());
      if (paramBoolean && SqlUtil.isCommentBlock(this.b.b()))
        return; 
      this.a.a(this.b.b(), paramStyledEditor.K.c(this.b.a, paramStyledEditor.i()));
      ArrayList arrayList = new ArrayList();
      this.a.a(arrayList, Dbms.get(this.c.getDbId()).getDefaultStatements());
      this.a.a(arrayList, paramBoolean);
      a(paramStyledEditor, paramStyledEditorCompletionMenu, arrayList, this.a.a());
      if (!paramBoolean && paramStyledEditorCompletionMenu.getItems().isEmpty())
        paramStyledEditorCompletionMenu.getItems().add(new MenuItem("No suggestion")); 
      paramStyledEditorCompletionMenu.a();
    } 
  }
  
  public void a(StyledEditor paramStyledEditor, StyledEditorCompletionMenu paramStyledEditorCompletionMenu, List paramList, int paramInt) {
    paramStyledEditorCompletionMenu.getItems().clear();
    ArrayList<b> arrayList = new ArrayList();
    for (SqlCompletionSuggestion sqlCompletionSuggestion : paramList) {
      b b = new b(sqlCompletionSuggestion);
      b.setOnAction(paramActionEvent -> {
            paramStyledEditor.K.a(paramSqlCompletionSuggestion.toString(), paramInt);
            paramStyledEditor.J.q();
            paramStyledEditorCompletionMenu.getItems().clear();
            paramStyledEditorCompletionMenu.hide();
          });
      arrayList.add(b);
    } 
    paramStyledEditorCompletionMenu.getItems().setAll(arrayList);
  }
}
