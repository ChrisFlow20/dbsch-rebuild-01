package com.wisecoders.dbs.sql.completion;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.editors.text.StyledEditorCompletion;
import com.wisecoders.dbs.editors.text.StyledEditorCompletion$Mode;
import com.wisecoders.dbs.editors.text.StyledEditorCompletionMenu;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;
import javafx.scene.control.MenuItem;
import net.prominic.groovyls.GroovyServices;
import net.prominic.groovyls.config.ICompilationUnitFactory;
import org.eclipse.lsp4j.TextDocumentIdentifier;

public class GroovyCompletion implements StyledEditorCompletion {
  private static final String b = "Groovy";
  
  private static final TextDocumentIdentifier c = new TextDocumentIdentifier(Sys.g.resolve("Completion.groovy").toUri().toString());
  
  private Task d;
  
  private GroovyServices e;
  
  public final List a = new UniqueArrayList();
  
  public void a(StyledEditor paramStyledEditor, StyledEditorCompletionMenu paramStyledEditorCompletionMenu, StyledEditorCompletion$Mode paramStyledEditorCompletion$Mode, boolean paramBoolean) {
    if (this.d == null || !this.d.isRunning()) {
      this.a.clear();
      paramStyledEditor.K.v();
      paramStyledEditor.J.n();
      paramStyledEditorCompletionMenu.getItems().clear();
      int i = paramStyledEditor.i().getLine();
      int j = paramStyledEditor.i().getCharacter();
      if (!paramBoolean) {
        paramStyledEditorCompletionMenu.getItems().add(new MenuItem("Please wait..."));
        paramStyledEditorCompletionMenu.a();
      } 
      paramStyledEditor.L.a(this.d = new GroovyCompletion$1(this, paramStyledEditor, i, j, paramStyledEditorCompletion$Mode, paramStyledEditorCompletionMenu, paramBoolean));
    } 
  }
  
  public void a() {
    if (this.e == null) {
      GroovyCompletion$2 groovyCompletion$2 = new GroovyCompletion$2(this);
      ArrayList<String> arrayList = new ArrayList();
      File file = new File(GroovyCompletion.class.getProtectionDomain().getCodeSource().getLocation().getFile());
      if (file.isFile() && file.getName().endsWith(".jar")) {
        File[] arrayOfFile = file.getParentFile().listFiles();
        if (arrayOfFile != null)
          for (File file1 : arrayOfFile)
            arrayList.add(file1.getAbsolutePath());  
      } 
      arrayList.add("C:/work/dbschema/build/app-libs/dbschema.jar");
      groovyCompletion$2.setAdditionalClasspathList(arrayList);
      this.e = new GroovyServices((ICompilationUnitFactory)groovyCompletion$2);
      this.e.setWorkspaceRoot(Sys.g);
      this.e.connect(new GroovyCompletion$3(this));
    } 
  }
}
