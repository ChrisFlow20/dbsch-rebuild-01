package com.wisecoders.dbs.data.task;

import com.wisecoders.dbs.data.fx.FxAbstractSqlEditor;
import com.wisecoders.dbs.data.fx.FxScriptResultPane;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.sys.Log;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import javafx.concurrent.Task;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.codehaus.groovy.control.messages.Message;
import org.codehaus.groovy.control.messages.SyntaxErrorMessage;
import org.codehaus.groovy.syntax.SyntaxException;

public class FxJavaScriptTask extends Task {
  private final FxAbstractSqlEditor a;
  
  private final FxScriptResultPane b;
  
  private final Envoy c;
  
  private final String d;
  
  private final OutputStream e;
  
  public FxJavaScriptTask(FxAbstractSqlEditor paramFxAbstractSqlEditor, Envoy paramEnvoy, String paramString, FxScriptResultPane paramFxScriptResultPane) {
    this.e = new FxJavaScriptTask$1(this);
    this.a = paramFxAbstractSqlEditor;
    this.b = paramFxScriptResultPane;
    this.c = paramEnvoy;
    this.d = paramString;
  }
  
  protected String a() {
    try {
      ScriptEngine scriptEngine = (new ScriptEngineManager()).getEngineByName("JavaScript");
      Bindings bindings = scriptEngine.getBindings(100);
      bindings.put("polyglot.js.allowHostAccess", Boolean.valueOf(true));
      bindings.put("polyglot.js.allowHostClassLookup", paramString -> true);
      bindings.put("sql", this.c.c());
      bindings.put("out", new PrintStream(this.e));
      scriptEngine.eval(this.d);
    } catch (Throwable throwable) {
      this.c.a(throwable);
      Log.b(throwable);
      throw new SQLException(throwable.getMessage());
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
  
  protected void succeeded() {
    this.a.c(this.c.k());
    b();
  }
  
  protected void failed() {
    Throwable throwable = getException();
    if (this.b != null)
      this.b.a((this.c != null) ? this.c.a.getPlainMessageAndAdvice(throwable) : throwable.getLocalizedMessage(), throwable); 
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
