package com.wisecoders.dbs.cli.console;

import com.wisecoders.dbs.cli.command.Dictionary;
import com.wisecoders.dbs.cli.command.base.Command;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.CLIConnectorManager;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sql.completion.SqlCompletionParser;
import com.wisecoders.dbs.sql.completion.SqlCompletionSuggestion;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.ArrayList;
import java.util.List;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jline.utils.AttributedString;

public class ConsoleCompleter implements Completer {
  private final Console a;
  
  private SqlCompletionParser b;
  
  public ConsoleCompleter(Console paramConsole) {
    this.a = paramConsole;
  }
  
  public void complete(LineReader paramLineReader, ParsedLine paramParsedLine, List<Candidate> paramList) {
    a(paramParsedLine, paramList, paramParsedLine.word());
    if (!CLIConnectorManager.a().isEmpty()) {
      Connector connector = CLIConnectorManager.a().get(0);
      if (this.b == null || this.b.a != connector.getCliProject())
        this.b = new SqlCompletionParser(connector.getCliProject()); 
      this.b.a(paramParsedLine.line(), paramParsedLine.cursor());
      ArrayList arrayList = new ArrayList();
      this.b.a(arrayList, false);
      for (SqlCompletionSuggestion sqlCompletionSuggestion : arrayList) {
        AbstractUnit abstractUnit = sqlCompletionSuggestion.c();
        if (abstractUnit instanceof Schema) {
          Schema schema = (Schema)abstractUnit;
          paramList.add(new Candidate(AttributedString.stripAnsi(schema.getName()), schema.getName(), "Schemas", StringUtil.cutOfWithDots(schema.getComment(), 40), null, null, true));
          continue;
        } 
        abstractUnit = sqlCompletionSuggestion.c();
        if (abstractUnit instanceof Table) {
          Table table = (Table)abstractUnit;
          paramList.add(new Candidate(AttributedString.stripAnsi(table.getName()), table.getName(), "Tables in " + String.valueOf(table.schema), StringUtil.cutOfWithDots(table.getComment(), 40), null, null, true));
          continue;
        } 
        abstractUnit = sqlCompletionSuggestion.c();
        if (abstractUnit instanceof Column) {
          Column column = (Column)abstractUnit;
          paramList.add(new Candidate(AttributedString.stripAnsi(column.getName()), column.getName(), "Columns in " + String.valueOf(column.table), StringUtil.cutOfWithDots(column.getComment(), 40), null, null, true));
        } 
      } 
    } 
  }
  
  private void a(ParsedLine paramParsedLine, List<Candidate> paramList, String paramString) {
    if (paramParsedLine.words().size() <= 1)
      for (Command command : Dictionary.b) {
        if (StringUtil.isEmpty(paramString) || command.getKeyword().startsWith(paramString))
          paramList.add(new Candidate(command.getKeyword(), command.getKeyword(), "DbSchema Shell Commands", command.getShortDescription(), null, null, true)); 
      }  
  }
}
