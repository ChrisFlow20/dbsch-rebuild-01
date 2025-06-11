package com.wisecoders.dbs.cli.console;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.jline.builtins.Commands;
import org.jline.builtins.Completers;
import org.jline.keymap.KeyMap;
import org.jline.reader.Binding;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.Macro;
import org.jline.reader.MaskingCallback;
import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.Reference;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Cursor;
import org.jline.terminal.MouseEvent;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.jline.utils.InfoCmp;

public class Sample {
  public static void a() {
    System.out.println("Usage: java " + Sample.class.getName() + " [none/simple/files/dictionary [trigger mask]]");
    System.out.println("  none - no completors");
    System.out.println("  simple - a simple completor that comples \"foo\", \"bar\", and \"baz\"");
    System.out
      .println("  files - a completor that comples file names");
    System.out.println("  classes - a completor that comples java class names");
    System.out
      .println("  trigger - a special word which causes it to assume the next line is a password");
    System.out.println("  mask - is the character to print in place of the actual password character");
    System.out.println("  color - colored prompt and feedback");
    System.out.println("\n  E.g - java Example simple su '*'\nwill use the simple completor with 'su' triggering\nthe use of '*' as a password mask.");
  }
  
  public static void main(String[] paramArrayOfString) {
    try {
      StringsCompleter stringsCompleter;
      String str1 = "prompt> ";
      String str2 = null;
      Character character = null;
      String str3 = null;
      boolean bool1 = false;
      boolean bool2 = false;
      TerminalBuilder terminalBuilder = TerminalBuilder.builder();
      if (paramArrayOfString == null || paramArrayOfString.length == 0) {
        a();
        return;
      } 
      byte b1 = 0;
      Completers.FileNameCompleter fileNameCompleter = null;
      DefaultParser defaultParser = null;
      byte b2 = 0;
      while (paramArrayOfString.length > b2) {
        StringsCompleter stringsCompleter1;
        ArgumentCompleter argumentCompleter;
        Completer completer;
        Completers.TreeCompleter treeCompleter;
        Completers.RegexCompleter regexCompleter;
        DefaultParser defaultParser1, defaultParser2;
        HashMap<Object, Object> hashMap;
        switch (paramArrayOfString[b2]) {
          case "timer":
            bool2 = true;
            b2++;
            continue;
          case "-system":
            terminalBuilder.system(false);
            b2++;
            continue;
          case "+system":
            terminalBuilder.system(true);
            b2++;
            continue;
          case "none":
            break;
          case "files":
            fileNameCompleter = new Completers.FileNameCompleter();
            break;
          case "simple":
            stringsCompleter1 = new StringsCompleter(new String[] { "foo", "bar", "baz" });
            break;
          case "quotes":
            defaultParser1 = new DefaultParser();
            defaultParser1.setEofOnUnclosedQuote(true);
            defaultParser = defaultParser1;
            break;
          case "brackets":
            str1 = "long-prompt> ";
            defaultParser2 = new DefaultParser();
            defaultParser2.setEofOnUnclosedBracket(new DefaultParser.Bracket[] { DefaultParser.Bracket.CURLY, DefaultParser.Bracket.ROUND, DefaultParser.Bracket.SQUARE });
            defaultParser = defaultParser2;
            break;
          case "foo":
            argumentCompleter = new ArgumentCompleter(new Completer[] { (Completer)new StringsCompleter(new String[] { "foo11", "foo12", "foo13" }), (Completer)new StringsCompleter(new String[] { "foo21", "foo22", "foo23" }), new Sample$1() });
            break;
          case "param":
            completer = ((paramLineReader, paramParsedLine, paramList) -> {
                if (paramParsedLine.wordIndex() == 0) {
                  paramList.add(new Candidate("Command1"));
                } else if (((String)paramParsedLine.words().get(0)).equals("Command1")) {
                  if (((String)paramParsedLine.words().get(paramParsedLine.wordIndex() - 1)).equals("Option1")) {
                    paramList.add(new Candidate("Param1"));
                    paramList.add(new Candidate("Param2"));
                  } else {
                    if (paramParsedLine.wordIndex() == 1)
                      paramList.add(new Candidate("Option1")); 
                    if (!paramParsedLine.words().contains("Option2"))
                      paramList.add(new Candidate("Option2")); 
                    if (!paramParsedLine.words().contains("Option3"))
                      paramList.add(new Candidate("Option3")); 
                  } 
                } 
              });
            break;
          case "tree":
            treeCompleter = new Completers.TreeCompleter(new Completers.TreeCompleter.Node[] { Completers.TreeCompleter.node(new Object[] { "Command1", Completers.TreeCompleter.node(new Object[] { "Option1", Completers.TreeCompleter.node(new Object[] { "Param1", "Param2" }) }), Completers.TreeCompleter.node(new Object[] { "Option2" }), Completers.TreeCompleter.node(new Object[] { "Option3" }) }) });
            break;
          case "regexp":
            hashMap = new HashMap<>();
            hashMap.put("C1", new StringsCompleter(new String[] { "cmd1" }));
            hashMap.put("C11", new StringsCompleter(new String[] { "--opt11", "--opt12" }));
            hashMap.put("C12", new StringsCompleter(new String[] { "arg11", "arg12", "arg13" }));
            hashMap.put("C2", new StringsCompleter(new String[] { "cmd2" }));
            hashMap.put("C21", new StringsCompleter(new String[] { "--opt21", "--opt22" }));
            hashMap.put("C22", new StringsCompleter(new String[] { "arg21", "arg22", "arg23" }));
            Objects.requireNonNull(hashMap);
            regexCompleter = new Completers.RegexCompleter("C1 C11* C12+ | C2 C21* C22+", hashMap::get);
            break;
          case "color":
            bool1 = true;
            str1 = (new AttributedStringBuilder()).style(AttributedStyle.DEFAULT.background(2)).append("foo").style(AttributedStyle.DEFAULT).append("@bar").style(AttributedStyle.DEFAULT.foreground(2)).append("\nbaz").style(AttributedStyle.DEFAULT).append("> ").toAnsi();
            str2 = (new AttributedStringBuilder()).style(AttributedStyle.DEFAULT.background(1)).append(LocalDate.now().format(DateTimeFormatter.ISO_DATE)).append("\n").style(AttributedStyle.DEFAULT.foreground(9)).append(LocalTime.now().format((new DateTimeFormatterBuilder()).appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':').appendValue(ChronoField.MINUTE_OF_HOUR, 2).toFormatter())).toAnsi();
            stringsCompleter = new StringsCompleter(new String[] { "\033[1mfoo\033[0m", "bar", "\033[32mbaz\033[0m", "foobar" });
            break;
          case "mouse":
            b1 = 1;
            break;
          case "mousetrack":
            b1 = 2;
            break;
        } 
        a();
        return;
      } 
      if (paramArrayOfString.length == b2 + 2) {
        character = Character.valueOf(paramArrayOfString[b2 + 1].charAt(0));
        str3 = paramArrayOfString[b2];
      } 
      Terminal terminal = terminalBuilder.build();
      LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).completer((Completer)stringsCompleter).parser((Parser)defaultParser).variable("secondary-prompt-pattern", "%M%P > ").build();
      if (bool2)
        Executors.newScheduledThreadPool(1)
          .scheduleAtFixedRate(() -> {
              paramLineReader.callWidget("clear");
              paramLineReader.getTerminal().writer().println("Hello world!");
              paramLineReader.callWidget("redraw-line");
              paramLineReader.callWidget("redisplay");
              paramLineReader.getTerminal().writer().flush();
            }1L, 1L, TimeUnit.SECONDS); 
      if (b1 != 0) {
        lineReader.setOpt(LineReader.Option.MOUSE);
        if (b1 == 2) {
          lineReader.getWidgets().put("callback-init", () -> {
                paramTerminal.trackMouse(Terminal.MouseTracking.Any);
                return true;
              });
          lineReader.getWidgets().put("mouse", () -> {
                MouseEvent mouseEvent = paramLineReader.readMouseEvent();
                StringBuilder stringBuilder = new StringBuilder();
                Cursor cursor = paramTerminal.getCursorPosition(());
                paramLineReader.runMacro(stringBuilder.toString());
                String str = "          " + mouseEvent.toString();
                int i = paramTerminal.getWidth();
                paramTerminal.puts(InfoCmp.Capability.cursor_address, new Object[] { Integer.valueOf(0), Integer.valueOf(Math.max(0, i - str.length())) });
                paramTerminal.writer().append(str);
                paramTerminal.puts(InfoCmp.Capability.cursor_address, new Object[] { Integer.valueOf(cursor.getY()), Integer.valueOf(cursor.getX()) });
                paramTerminal.flush();
                return true;
              });
        } 
      } 
      while (true) {
        String str = null;
        try {
          str = lineReader.readLine(str1, str2, (MaskingCallback)null, null);
        } catch (UserInterruptException userInterruptException) {
        
        } catch (EndOfFileException endOfFileException) {
          return;
        } 
        if (str == null)
          continue; 
        str = str.trim();
        if (bool1) {
          terminal.writer().println(
              AttributedString.fromAnsi("\033[33m======>\033[0m\"" + str + "\"")
              .toAnsi(terminal));
        } else {
          terminal.writer().println("======>\"" + str + "\"");
        } 
        terminal.flush();
        if (str3 != null && str.compareTo(str3) == 0)
          str = lineReader.readLine("password> ", character); 
        if (str.equalsIgnoreCase("quit") || str.equalsIgnoreCase("exit"))
          break; 
        ParsedLine parsedLine = lineReader.getParser().parse(str, 0);
        String[] arrayOfString = (String[])parsedLine.words().subList(1, parsedLine.words().size()).toArray((Object[])new String[0]);
        if ("set".equals(parsedLine.word())) {
          if (parsedLine.words().size() == 3)
            lineReader.setVariable(parsedLine.words().get(1), parsedLine.words().get(2)); 
          continue;
        } 
        if ("tput".equals(parsedLine.word())) {
          if (parsedLine.words().size() == 2) {
            InfoCmp.Capability capability = InfoCmp.Capability.byName(parsedLine.words().get(1));
            if (capability != null) {
              terminal.puts(capability, new Object[0]);
              continue;
            } 
            terminal.writer().println("Unknown capability");
          } 
          continue;
        } 
        if ("testkey".equals(parsedLine.word())) {
          terminal.writer().write("Input the key event(Enter to complete): ");
          terminal.writer().flush();
          StringBuilder stringBuilder = new StringBuilder();
          while (true) {
            int i = ((LineReaderImpl)lineReader).readCharacter();
            if (i == 10 || i == 13)
              break; 
            stringBuilder.append(new String(Character.toChars(i)));
          } 
          terminal.writer().println(KeyMap.display(stringBuilder.toString()));
          terminal.writer().flush();
          continue;
        } 
        if ("bindkey".equals(parsedLine.word())) {
          if (parsedLine.words().size() == 1) {
            StringBuilder stringBuilder = new StringBuilder();
            Map map = lineReader.getKeys().getBoundKeys();
            for (Map.Entry entry : map.entrySet()) {
              stringBuilder.append("\"");
              ((String)entry.getKey()).chars().forEachOrdered(paramInt -> {
                    if (paramInt < 32) {
                      paramStringBuilder.append('^');
                      paramStringBuilder.append((char)(paramInt + 65 - 1));
                    } else {
                      paramStringBuilder.append((char)paramInt);
                    } 
                  });
              stringBuilder.append("\" ");
              if (entry.getValue() instanceof Macro) {
                stringBuilder.append("\"");
                ((Macro)entry.getValue()).getSequence().chars().forEachOrdered(paramInt -> {
                      if (paramInt < 32) {
                        paramStringBuilder.append('^');
                        paramStringBuilder.append((char)(paramInt + 65 - 1));
                      } else {
                        paramStringBuilder.append((char)paramInt);
                      } 
                    });
                stringBuilder.append("\"");
              } else if (entry.getValue() instanceof Reference) {
                stringBuilder.append(((Reference)entry.getValue()).name().toLowerCase().replace('_', '-'));
              } else {
                stringBuilder.append(((Binding)entry.getValue()).toString());
              } 
              stringBuilder.append("\n");
            } 
            terminal.writer().print(stringBuilder.toString());
            terminal.flush();
            continue;
          } 
          if (parsedLine.words().size() == 3)
            lineReader.getKeys().bind(new Reference(parsedLine
                  .words().get(2)), KeyMap.translate(parsedLine.words().get(1))); 
          continue;
        } 
        if ("cls".equals(parsedLine.word())) {
          terminal.puts(InfoCmp.Capability.clear_screen, new Object[0]);
          terminal.flush();
          continue;
        } 
        if ("sleep".equals(parsedLine.word())) {
          Thread.sleep(3000L);
          continue;
        } 
        if ("history".equals(parsedLine.word()))
          Commands.history(lineReader, System.out, System.err, null, arrayOfString); 
      } 
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    } 
  }
}
