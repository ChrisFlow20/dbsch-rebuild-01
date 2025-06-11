package com.wisecoders.dbs.cli.console;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.sys.Log;
import org.jline.builtins.Commands;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.MaskingCallback;
import org.jline.reader.Parser;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class Console extends AbstractConsole {
  private final Terminal a;
  
  private final LineReader b;
  
  public Console() {
    TerminalBuilder terminalBuilder = TerminalBuilder.builder();
    terminalBuilder
      
      .signalHandler(paramSignal -> {
          switch (Console$2.a[paramSignal.ordinal()]) {
            case 1:
              System.exit(0);
              break;
            case 2:
              System.out.println("CTRL-C pressed");
              e();
              break;
          } 
        });
    this.a = terminalBuilder.build();
    this
















      
      .b = LineReaderBuilder.builder().terminal(this.a).completer(new ConsoleCompleter(this)).parser((Parser)new Console$1(this)).variable("secondary-prompt-pattern", "; or / > ").build();
    this.b.setVariable("history-file", Sys.u);
  }
  
  public String a() {
    String str = null;
    try {
      str = this.b.readLine(h(), null, (MaskingCallback)null, null);
    } catch (UserInterruptException userInterruptException) {
      e();
    } catch (EndOfFileException endOfFileException) {
      return null;
    } catch (Throwable throwable) {
      Log.a("Error in Console Reader", throwable);
    } 
    if (str != null)
      str = str.trim(); 
    this.a.flush();
    return str;
  }
  
  public void b() {
    try {
      Commands.history(this.b, System.out, System.err, null, new String[0]);
    } catch (Exception exception) {
      Log.a("Error in Console Reader History", exception);
    } 
  }
}
