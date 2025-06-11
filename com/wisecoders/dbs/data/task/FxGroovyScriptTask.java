package com.wisecoders.dbs.data.task;

import com.wisecoders.dbs.data.fx.FxAbstractSqlEditor;
import com.wisecoders.dbs.data.fx.FxScriptResultPane;
import com.wisecoders.dbs.data.groovy.GroovyConfig;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.editors.text.StyledEditorHighlight;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StackTraceHelper;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.sql.Sql;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import javafx.application.Platform;
import javafx.concurrent.Task;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.codehaus.groovy.control.messages.Message;
import org.codehaus.groovy.control.messages.SyntaxErrorMessage;
import org.codehaus.groovy.syntax.SyntaxException;

public class FxGroovyScriptTask extends Task {
  protected final FxAbstractSqlEditor a;
  
  protected final FxScriptResultPane b;
  
  protected final Envoy c;
  
  protected final String d;
  
  protected final OutputStream e;
  
  public FxGroovyScriptTask(FxAbstractSqlEditor paramFxAbstractSqlEditor, Envoy paramEnvoy, String paramString, FxScriptResultPane paramFxScriptResultPane) {
    this.e = new FxGroovyScriptTask$1(this);
    this.a = paramFxAbstractSqlEditor;
    this.b = paramFxScriptResultPane;
    this.c = paramEnvoy;
    this.d = paramString;
  }
  
  protected String a() {
    Binding binding = new Binding();
    if (this.c != null) {
      Sql sql = new Sql(this.c.c());
      binding.setVariable("sql", sql);
    } 
    binding.setVariable("project", this.a.x());
    binding.setProperty("out", new PrintStream(this.e));
    try {
      GroovyShell groovyShell = new GroovyShell(new GroovyConfig((this.a.x() != null) ? this.a.x().getFile() : null));
      Script script = groovyShell.parse(this.d, binding);
      if (this.d.contains("groovyx.javafx.GroovyFX")) {
        Platform.runLater(() -> {
              try {
                paramScript.run();
              } catch (Throwable throwable) {
                if (this.b != null)
                  this.b.a(throwable.getLocalizedMessage() + "\n" + throwable.getLocalizedMessage(), StyledEditorHighlight.c); 
                Log.f(throwable.toString());
              } 
            });
      } else if (this.b != null) {
        script.run();
      } 
    } catch (MultipleCompilationErrorsException multipleCompilationErrorsException) {
      a(multipleCompilationErrorsException);
    } catch (Throwable throwable) {
      if (this.c != null)
        this.c.a(throwable); 
      throw new Exception(StackTraceHelper.a(throwable));
    } 
    return null;
  }
  
  public static void a(MultipleCompilationErrorsException paramMultipleCompilationErrorsException) {
    ErrorCollector errorCollector = paramMultipleCompilationErrorsException.getErrorCollector();
    for (byte b = 0; b < errorCollector.getErrorCount(); b++) {
      Message message = errorCollector.getError(b);
      if (message instanceof SyntaxErrorMessage) {
        SyntaxErrorMessage syntaxErrorMessage = (SyntaxErrorMessage)message;
        throw syntaxErrorMessage.getCause();
      } 
    } 
    throw paramMultipleCompilationErrorsException;
  }
  
  protected void a(List paramList) {
    if (this.b != null)
      for (String str : paramList)
        this.b.b(str);  
  }
  
  protected void succeeded() {
    this.b.a("Execution Succeed");
    if (this.c != null)
      this.a.c(this.c.k()); 
    b();
  }
  
  protected void failed() {
    Throwable throwable = getException();
    if (this.b != null)
      if (this.c == null) {
        this.b.a(throwable.getLocalizedMessage(), throwable);
      } else {
        this.b.a(this.c.a.getPlainMessageAndAdvice(throwable), throwable);
      }  
    if (throwable instanceof SyntaxException) {
      SyntaxException syntaxException = (SyntaxException)throwable;
      this.a.a(syntaxException.getStartLine() - 1, syntaxException.getStartColumn() - 1, syntaxException.getEndLine() - 1, syntaxException.getEndColumn() - 1, syntaxException.getLocalizedMessage());
    } 
    Log.f(throwable.toString());
    this.a.c(false);
    b();
  }
  
  private void b() {
    if (this.b != null)
      this.b.a(true); 
    this.a.f(true);
    this.a.u().b();
  }
}
