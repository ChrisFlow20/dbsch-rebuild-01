package com.wisecoders.dbs.sys;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.StringCharacterIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class EscapeChars {
  public static String forHtml(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramString != null) {
      StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(paramString);
      char c = stringCharacterIterator.current();
      while (c != Character.MAX_VALUE) {
        switch (c) {
          case '<':
            stringBuilder.append("&lt;");
            break;
          case '>':
            stringBuilder.append("&gt;");
            break;
          case '&':
            stringBuilder.append("&amp;");
            break;
          case '"':
            stringBuilder.append("&quot;");
            break;
          case '\t':
            a(Integer.valueOf(9), stringBuilder);
            break;
          case '!':
            a(Integer.valueOf(33), stringBuilder);
            break;
          case '#':
            a(Integer.valueOf(35), stringBuilder);
            break;
          case '$':
            a(Integer.valueOf(36), stringBuilder);
            break;
          case '%':
            a(Integer.valueOf(37), stringBuilder);
            break;
          case '\'':
            a(Integer.valueOf(39), stringBuilder);
            break;
          case '(':
            a(Integer.valueOf(40), stringBuilder);
            break;
          case ')':
            a(Integer.valueOf(41), stringBuilder);
            break;
          case '*':
            a(Integer.valueOf(42), stringBuilder);
            break;
          case '+':
            a(Integer.valueOf(43), stringBuilder);
            break;
          case ',':
            a(Integer.valueOf(44), stringBuilder);
            break;
          case '-':
            a(Integer.valueOf(45), stringBuilder);
            break;
          case '.':
            a(Integer.valueOf(46), stringBuilder);
            break;
          case '/':
            a(Integer.valueOf(47), stringBuilder);
            break;
          case ':':
            a(Integer.valueOf(58), stringBuilder);
            break;
          case ';':
            a(Integer.valueOf(59), stringBuilder);
            break;
          case '=':
            a(Integer.valueOf(61), stringBuilder);
            break;
          case '?':
            a(Integer.valueOf(63), stringBuilder);
            break;
          case '@':
            a(Integer.valueOf(64), stringBuilder);
            break;
          case '[':
            a(Integer.valueOf(91), stringBuilder);
            break;
          case '\\':
            a(Integer.valueOf(92), stringBuilder);
            break;
          case ']':
            a(Integer.valueOf(93), stringBuilder);
            break;
          case '^':
            a(Integer.valueOf(94), stringBuilder);
            break;
          case '_':
            a(Integer.valueOf(95), stringBuilder);
            break;
          case '`':
            a(Integer.valueOf(96), stringBuilder);
            break;
          case '{':
            a(Integer.valueOf(123), stringBuilder);
            break;
          case '|':
            a(Integer.valueOf(124), stringBuilder);
            break;
          case '}':
            a(Integer.valueOf(125), stringBuilder);
            break;
          case '~':
            a(Integer.valueOf(126), stringBuilder);
            break;
          default:
            stringBuilder.append(c);
            break;
        } 
        c = stringCharacterIterator.next();
      } 
    } 
    return stringBuilder.toString();
  }
  
  public static String titleForHtml(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramString != null) {
      StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(paramString);
      char c = stringCharacterIterator.current();
      while (c != Character.MAX_VALUE) {
        switch (c) {
          case '&':
            stringBuilder.append("&amp;");
            break;
          case '\'':
            stringBuilder.append("&apos;");
            break;
          case '‘':
            stringBuilder.append("&#8216;");
            break;
          case '’':
            stringBuilder.append("&#8217;");
            break;
          case '"':
            stringBuilder.append("&quot;");
            break;
          case '“':
            stringBuilder.append("&#8220;");
            break;
          case '”':
            stringBuilder.append("&#8221;");
            break;
          case '<':
            stringBuilder.append("&lt;");
            break;
          case '>':
            stringBuilder.append("&gt;");
            break;
          case '—':
            stringBuilder.append("&#8212");
            break;
          default:
            stringBuilder.append(c);
            break;
        } 
        c = stringCharacterIterator.next();
      } 
    } 
    return stringBuilder.toString();
  }
  
  public static String forHrefAmpersand(String paramString) {
    return paramString.replace("&", "&amp;");
  }
  
  public static String forURL(String paramString) {
    return StringUtil.isFilled(paramString) ? URLEncoder.encode(paramString, StandardCharsets.UTF_8) : "";
  }
  
  public static String forXML(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(paramString);
    char c = stringCharacterIterator.current();
    while (c != Character.MAX_VALUE) {
      switch (c) {
        case '<':
          stringBuilder.append("&lt;");
          break;
        case '>':
          stringBuilder.append("&gt;");
          break;
        case '"':
          stringBuilder.append("&quot;");
          break;
        case '\'':
          stringBuilder.append("&#039;");
          break;
        case '&':
          stringBuilder.append("&amp;");
          break;
        default:
          stringBuilder.append(c);
          break;
      } 
      c = stringCharacterIterator.next();
    } 
    return stringBuilder.toString();
  }
  
  public static String forJSON(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(paramString);
    char c = stringCharacterIterator.current();
    while (c != Character.MAX_VALUE) {
      switch (c) {
        case '"':
          stringBuilder.append("\\\"");
          break;
        case '\\':
          stringBuilder.append("\\\\");
          break;
        case '/':
          stringBuilder.append("\\/");
          break;
        case '\b':
          stringBuilder.append("\\b");
          break;
        case '\f':
          stringBuilder.append("\\f");
          break;
        case '\n':
          stringBuilder.append("\\n");
          break;
        case '\r':
          stringBuilder.append("\\r");
          break;
        case '\t':
          stringBuilder.append("\\t");
          break;
        default:
          stringBuilder.append(c);
          break;
      } 
      c = stringCharacterIterator.next();
    } 
    return stringBuilder.toString();
  }
  
  public static String toDisableTags(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(paramString);
    char c = stringCharacterIterator.current();
    while (c != Character.MAX_VALUE) {
      if (c == '<') {
        stringBuilder.append("&lt;");
      } else if (c == '>') {
        stringBuilder.append("&gt;");
      } else {
        stringBuilder.append(c);
      } 
      c = stringCharacterIterator.next();
    } 
    return stringBuilder.toString();
  }
  
  public static String forRegex(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(paramString);
    char c = stringCharacterIterator.current();
    while (c != Character.MAX_VALUE) {
      switch (c) {
        case '.':
          stringBuilder.append("\\.");
          break;
        case '\\':
          stringBuilder.append("\\\\");
          break;
        case '?':
          stringBuilder.append("\\?");
          break;
        case '*':
          stringBuilder.append("\\*");
          break;
        case '+':
          stringBuilder.append("\\+");
          break;
        case '&':
          stringBuilder.append("\\&");
          break;
        case '{':
          stringBuilder.append("\\{");
          break;
        case '}':
          stringBuilder.append("\\}");
          break;
        case '[':
          stringBuilder.append("\\[");
          break;
        case ']':
          stringBuilder.append("\\]");
          break;
        case '(':
          stringBuilder.append("\\(");
          break;
        case ')':
          stringBuilder.append("\\)");
          break;
        case '^':
          stringBuilder.append("\\^");
          break;
        case '$':
          stringBuilder.append("\\$");
          break;
        default:
          stringBuilder.append(c);
          break;
      } 
      c = stringCharacterIterator.next();
    } 
    return stringBuilder.toString();
  }
  
  public static String forReplacementString(String paramString) {
    return (paramString != null) ? Matcher.quoteReplacement(paramString) : paramString;
  }
  
  public static String forScriptTagsOnly(String paramString) {
    Matcher matcher = a.matcher(paramString);
    String str = matcher.replaceAll("&lt;SCRIPT>");
    matcher = b.matcher(str);
    str = matcher.replaceAll("&lt;/SCRIPT>");
    return str;
  }
  
  private static final Pattern a = Pattern.compile("<SCRIPT>", 2);
  
  private static final Pattern b = Pattern.compile("</SCRIPT>", 2);
  
  private static void a(Integer paramInteger, StringBuilder paramStringBuilder) {
    paramStringBuilder.append("&#");
    if (paramInteger.intValue() <= 9)
      paramStringBuilder.append("0"); 
    paramStringBuilder.append(paramInteger).append(";");
  }
  
  public static String forJavaScript(String paramString) {
    return URLEncoder.encode(paramString, StandardCharsets.UTF_8)
      .replaceAll("\\+", "%20")
      .replaceAll("\\%21", "!")
      .replaceAll("\\%27", "'")
      .replaceAll("\\%28", "(")
      .replaceAll("\\%29", ")")
      .replaceAll("\\%7E", "~");
  }
  
  public static String forJdbcURL(String paramString1, String paramString2) {
    if (paramString1 != null) {
      StringBuilder stringBuilder = new StringBuilder();
      for (char c : paramString1.toCharArray()) {
        if (paramString2.indexOf(c) >= 0) {
          stringBuilder.append('%');
          stringBuilder.append(a(c / 16));
          stringBuilder.append(a(c % 16));
        } else {
          stringBuilder.append(c);
        } 
      } 
      return stringBuilder.toString();
    } 
    return null;
  }
  
  private static char a(int paramInt) {
    return (char)((paramInt < 10) ? (48 + paramInt) : (65 + paramInt - 10));
  }
  
  public static String forHtmlNewLinesAsBR(String paramString) {
    return (paramString != null) ? forHtml(paramString).replaceAll("\n", "<br>") : null;
  }
  
  public static String forMDWithNewLines(String paramString) {
    return forMD(paramString).replaceAll("\n", "\\\\");
  }
  
  public static String forMD(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramString != null) {
      StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(paramString);
      char c = stringCharacterIterator.current();
      while (c != Character.MAX_VALUE) {
        switch (c) {
          case '<':
            stringBuilder.append("&lt;");
            break;
          case '>':
            stringBuilder.append("&gt;");
            break;
          case '_':
            stringBuilder.append("\\_");
            break;
          default:
            stringBuilder.append(c);
            break;
        } 
        c = stringCharacterIterator.next();
      } 
    } 
    return stringBuilder.toString();
  }
  
  public static String forGroovy(String paramString) {
    if (paramString != null) {
      paramString = paramString.replace("\\", "\\\\");
      paramString = paramString.replaceAll("\\$(?!\\{)", "\\\\\\$");
    } 
    return paramString;
  }
}
