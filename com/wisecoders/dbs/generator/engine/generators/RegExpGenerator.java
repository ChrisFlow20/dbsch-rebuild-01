package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.generator.engine.generators.automaton.Automaton;
import com.wisecoders.dbs.generator.engine.generators.automaton.RegExp;
import com.wisecoders.dbs.generator.engine.generators.automaton.State;
import com.wisecoders.dbs.generator.engine.generators.automaton.Transition;
import com.wisecoders.dbs.generator.engine.plan.Category;
import com.wisecoders.dbs.generator.engine.plan.DefinedPattern;
import com.wisecoders.dbs.generator.engine.plan.Domain;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import com.wisecoders.dbs.sys.Log;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpGenerator extends Generator {
  private static final int g = 3000;
  
  static {
    b = Pattern.compile("at position (.*)", 42);
  }
  
  private final HashMap d = new HashMap<>();
  
  private final Automaton c;
  
  private static final Pattern b;
  
  public RegExpGenerator(String paramString, int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
    try {
      String str = paramString.replaceAll(Pattern.quote("."), Matcher.quoteReplacement("\\."));
      str = str.replaceAll("@", Matcher.quoteReplacement("\\@"));
      a(str, paramInt2);
      RegExp regExp = new RegExp(str, 10);
      this.c = regExp.a();
      if (paramInt2 != -1)
        this.f.setSeed(paramInt2); 
    } catch (IllegalArgumentException illegalArgumentException) {
      int i = paramString.length() - 1;
      Matcher matcher = b.matcher(illegalArgumentException.toString());
      if (matcher.find()) {
        i = Integer.parseInt(matcher.group(1)) - 1;
        throw new ParseException(illegalArgumentException.getMessage(), i);
      } 
      throw new ParseException(illegalArgumentException.getMessage(), i);
    } 
  }
  
  public String a(GeneratorTable paramGeneratorTable, Column paramColumn) {
    return a(paramGeneratorTable, a(), paramColumn);
  }
  
  public String a() {
    StringBuilder stringBuilder = new StringBuilder();
    a(stringBuilder, this.c.f());
    return stringBuilder.toString();
  }
  
  private void a(StringBuilder paramStringBuilder, State paramState) {
    byte b = 0;
    int i;
    while ((i = a(paramState)) != -1 && b++ < 'ஸ') {
      List<Transition> list = paramState.c(true);
      Transition transition = list.get(i - (paramState.c() ? 1 : 0));
      a(paramStringBuilder, transition);
      paramState = transition.c();
    } 
    if (b >= 'ஸ')
      paramStringBuilder.insert(0, "ERROR: "); 
  }
  
  private int a(State paramState) {
    List list = paramState.c(true);
    if (list.size() == 0) {
      if (!a && !paramState.c())
        throw new AssertionError(); 
      return -1;
    } 
    int i = paramState.c() ? list.size() : (list.size() - 1);
    int j = a(0, i);
    if (paramState.c() && j == 0)
      return -1; 
    return j;
  }
  
  private void a(StringBuilder paramStringBuilder, Transition paramTransition) {
    char c = (char)a(paramTransition.a(), paramTransition.b());
    paramStringBuilder.append(c);
  }
  
  public int a(int paramInt1, int paramInt2) {
    int i = paramInt2 - paramInt1;
    float f = this.f.nextFloat();
    return paramInt1 + Math.round(f * i);
  }
  
  private void a(String paramString, int paramInt) {
    for (Category category : Domain.b()) {
      for (DefinedPattern definedPattern : category.a()) {
        if (paramString.contains("$" + definedPattern.b()))
          try {
            this.d.put(definedPattern.b(), Generator.getGenerator(definedPattern.e(), 0, paramInt));
          } catch (ParseException parseException) {
            Log.f("Strange exception in RegExpGenerator.generateLinks() " + String.valueOf(parseException));
          }  
      } 
    } 
  }
  
  private String a(GeneratorTable paramGeneratorTable, String paramString, Column paramColumn) {
    if (paramString != null)
      for (String str1 : this.d.keySet()) {
        Object object = ((Generator)this.d.get(str1)).generateWithNulls(paramGeneratorTable, paramColumn);
        String str2 = (object != null) ? object.toString() : "";
        paramString = paramString.replaceAll("\\$" + str1, str2);
      }  
    return paramString;
  }
  
  public String toString() {
    return "RegExp Generator";
  }
}
