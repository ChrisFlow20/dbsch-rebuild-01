package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

public final class BasicAutomata {
  public static Automaton a() {
    Automaton automaton = new Automaton();
    State state = new State();
    automaton.f = state;
    automaton.g = true;
    return automaton;
  }
  
  public static Automaton b() {
    Automaton automaton = new Automaton();
    automaton.j = "";
    automaton.g = true;
    return automaton;
  }
  
  public static Automaton c() {
    Automaton automaton = new Automaton();
    State state = new State();
    automaton.f = state;
    state.b = true;
    state.c.add(new Transition(false, '￿', state));
    automaton.g = true;
    return automaton;
  }
  
  public static Automaton d() {
    return a(false, '￿');
  }
  
  public static Automaton a(char paramChar) {
    Automaton automaton = new Automaton();
    automaton.j = Character.toString(paramChar);
    automaton.g = true;
    return automaton;
  }
  
  public static Automaton a(char paramChar1, char paramChar2) {
    if (paramChar1 == paramChar2)
      return a(paramChar1); 
    Automaton automaton = new Automaton();
    State state1 = new State();
    State state2 = new State();
    automaton.f = state1;
    state2.b = true;
    if (paramChar1 <= paramChar2)
      state1.c.add(new Transition(paramChar1, paramChar2, state2)); 
    automaton.g = true;
    return automaton;
  }
  
  public static Automaton a(String paramString) {
    if (paramString.length() == 1)
      return a(paramString.charAt(0)); 
    Automaton automaton = new Automaton();
    State state1 = new State();
    State state2 = new State();
    automaton.f = state1;
    state2.b = true;
    for (byte b = 0; b < paramString.length(); b++)
      state1.c.add(new Transition(paramString.charAt(b), state2)); 
    automaton.g = true;
    automaton.m();
    return automaton;
  }
  
  private static State a(String paramString, int paramInt) {
    State state = new State();
    if (paramString.length() == paramInt) {
      state.a(true);
    } else {
      state.a(new Transition('0', '9', a(paramString, paramInt + 1)));
    } 
    return state;
  }
  
  private static State a(String paramString, int paramInt, Collection<State> paramCollection, boolean paramBoolean) {
    State state = new State();
    if (paramString.length() == paramInt) {
      state.a(true);
    } else {
      if (paramBoolean)
        paramCollection.add(state); 
      char c = paramString.charAt(paramInt);
      state.a(new Transition(c, a(paramString, paramInt + 1, paramCollection, (paramBoolean && c == '0'))));
      if (c < '9')
        state.a(new Transition((char)(c + 1), '9', a(paramString, paramInt + 1))); 
    } 
    return state;
  }
  
  private static State b(String paramString, int paramInt) {
    State state = new State();
    if (paramString.length() == paramInt) {
      state.a(true);
    } else {
      char c = paramString.charAt(paramInt);
      state.a(new Transition(c, b(paramString, (char)paramInt + 1)));
      if (c > '0')
        state.a(new Transition('0', (char)(c - 1), a(paramString, paramInt + 1))); 
    } 
    return state;
  }
  
  private static State a(String paramString1, String paramString2, int paramInt, Collection<State> paramCollection, boolean paramBoolean) {
    State state = new State();
    if (paramString1.length() == paramInt) {
      state.a(true);
    } else {
      if (paramBoolean)
        paramCollection.add(state); 
      char c1 = paramString1.charAt(paramInt);
      char c2 = paramString2.charAt(paramInt);
      if (c1 == c2) {
        state.a(new Transition(c1, a(paramString1, paramString2, paramInt + 1, paramCollection, (paramBoolean && c1 == '0'))));
      } else {
        state.a(new Transition(c1, a(paramString1, paramInt + 1, paramCollection, (paramBoolean && c1 == '0'))));
        state.a(new Transition(c2, b(paramString2, paramInt + 1)));
        if (c1 + 1 < c2)
          state.a(new Transition((char)(c1 + 1), (char)(c2 - 1), a(paramString1, paramInt + 1))); 
      } 
    } 
    return state;
  }
  
  public static Automaton a(int paramInt1, int paramInt2, int paramInt3) {
    int i;
    Automaton automaton = new Automaton();
    String str1 = Integer.toString(paramInt1);
    String str2 = Integer.toString(paramInt2);
    if (paramInt1 > paramInt2 || (paramInt3 > 0 && str2.length() > paramInt3))
      throw new IllegalArgumentException(); 
    if (paramInt3 > 0) {
      i = paramInt3;
    } else {
      i = str2.length();
    } 
    StringBuilder stringBuilder1 = new StringBuilder();
    for (int j = str1.length(); j < i; j++)
      stringBuilder1.append('0'); 
    stringBuilder1.append(str1);
    str1 = stringBuilder1.toString();
    StringBuilder stringBuilder2 = new StringBuilder();
    for (int k = str2.length(); k < i; k++)
      stringBuilder2.append('0'); 
    stringBuilder2.append(str2);
    str2 = stringBuilder2.toString();
    ArrayList arrayList = new ArrayList();
    automaton.f = a(str1, str2, 0, arrayList, (paramInt3 <= 0));
    if (paramInt3 <= 0) {
      ArrayList<StatePair> arrayList1 = new ArrayList();
      for (State state : arrayList) {
        if (automaton.f != state)
          arrayList1.add(new StatePair(automaton.f, state)); 
      } 
      automaton.b(arrayList1);
      automaton.f.a(new Transition('0', automaton.f));
      automaton.g = false;
    } else {
      automaton.g = true;
    } 
    automaton.c();
    return automaton;
  }
  
  public static Automaton b(String paramString) {
    Automaton automaton = new Automaton();
    automaton.j = paramString;
    automaton.g = true;
    return automaton;
  }
  
  public static Automaton a(CharSequence... paramVarArgs) {
    if (paramVarArgs.length == 0)
      return a(); 
    Arrays.sort(paramVarArgs, StringUnionOperations.a);
    Automaton automaton = new Automaton();
    automaton.a(StringUnionOperations.a(paramVarArgs));
    automaton.c(true);
    automaton.m();
    automaton.t();
    return automaton;
  }
  
  public static Automaton c(String paramString) {
    byte b = 0;
    while (b < paramString.length() && paramString.charAt(b) == '0')
      b++; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("0*(0|");
    if (b < paramString.length())
      stringBuilder.append("[0-9]{1," + paramString.length() - b - 1 + "}|"); 
    a(paramString.substring(b), 0, stringBuilder);
    stringBuilder.append(")");
    return Automaton.f((new RegExp(stringBuilder.toString())).a());
  }
  
  private static void a(String paramString, int paramInt, StringBuilder paramStringBuilder) {
    paramStringBuilder.append('(');
    if (paramInt < paramString.length()) {
      char c = paramString.charAt(paramInt);
      if (c != '0')
        paramStringBuilder.append("[0-" + (char)(c - 1) + "][0-9]{" + paramString.length() - paramInt - 1 + "}|"); 
      paramStringBuilder.append(c);
      a(paramString, paramInt + 1, paramStringBuilder);
    } 
    paramStringBuilder.append(')');
  }
  
  public static Automaton d(String paramString) {
    byte b = 0;
    while (b + 1 < paramString.length() && paramString.charAt(b) == '0')
      b++; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("0*");
    b(paramString.substring(b), 0, stringBuilder);
    stringBuilder.append("[0-9]*");
    return Automaton.f((new RegExp(stringBuilder.toString())).a());
  }
  
  private static void b(String paramString, int paramInt, StringBuilder paramStringBuilder) {
    paramStringBuilder.append('(');
    if (paramInt < paramString.length()) {
      char c = paramString.charAt(paramInt);
      if (c != '9')
        paramStringBuilder.append("[" + (char)(c + 1) + "-9][0-9]{" + paramString.length() - paramInt - 1 + "}|"); 
      paramStringBuilder.append(c);
      b(paramString, paramInt + 1, paramStringBuilder);
    } 
    paramStringBuilder.append(')');
  }
  
  public static Automaton a(int paramInt) {
    return Automaton.f((new RegExp("[ \t\n\r]*[-+]?0*([0-9]{0," + paramInt + "}|((([0-9]\\.*){0," + paramInt + "})&@\\.@)0*)[ \t\n\r]*")).a());
  }
  
  public static Automaton b(int paramInt) {
    return Automaton.f((new RegExp("[ \t\n\r]*[-+]?[0-9]+(\\.[0-9]{0," + paramInt + "}0*)?[ \t\n\r]*")).a());
  }
  
  public static Automaton e(String paramString) {
    Automaton automaton1;
    boolean bool = false;
    byte b = 0;
    while (b < paramString.length()) {
      char c = paramString.charAt(b);
      if (c == '-')
        bool = true; 
      if (c >= '1' && c <= '9')
        break; 
      b++;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString.substring(b));
    if (stringBuilder.length() == 0)
      stringBuilder.append("0"); 
    if (bool) {
      automaton1 = Automaton.a('-');
    } else {
      automaton1 = Automaton.a('+').E();
    } 
    Automaton automaton2 = Datatypes.a();
    return Automaton.f(automaton2.a(automaton1.a(Automaton.a('0').F()).a(Automaton.b(stringBuilder.toString()))).a(automaton2));
  }
  
  public static Automaton f(String paramString) {
    Automaton automaton1, automaton2;
    boolean bool = false;
    int i = 0;
    while (i < paramString.length()) {
      char c = paramString.charAt(i);
      if (c == '-')
        bool = true; 
      if ((c >= '1' && c <= '9') || c == '.')
        break; 
      i++;
    } 
    StringBuilder stringBuilder1 = new StringBuilder();
    StringBuilder stringBuilder2 = new StringBuilder();
    int j = paramString.indexOf('.', i);
    if (j == -1) {
      stringBuilder1.append(paramString.substring(i));
    } else {
      stringBuilder1.append(paramString.substring(i, j));
      i = paramString.length() - 1;
      while (i > j) {
        char c = paramString.charAt(i);
        if (c >= '1' && c <= '9')
          break; 
        i--;
      } 
      stringBuilder2.append(paramString.substring(j + 1, i + 1));
    } 
    if (stringBuilder1.length() == 0)
      stringBuilder1.append("0"); 
    if (bool) {
      automaton1 = Automaton.a('-');
    } else {
      automaton1 = Automaton.a('+').E();
    } 
    if (stringBuilder2.length() == 0) {
      automaton2 = Automaton.a('.').a(Automaton.a('0').d(1)).E();
    } else {
      automaton2 = Automaton.a('.').a(Automaton.b(stringBuilder2.toString())).a(Automaton.a('0').F());
    } 
    Automaton automaton3 = Datatypes.a();
    return Automaton.f(automaton3.a(automaton1.a(Automaton.a('0').F()).a(Automaton.b(stringBuilder1.toString())).a(automaton2)).a(automaton3));
  }
  
  public static Automaton g(String paramString) {
    Automaton automaton = new Automaton();
    State[] arrayOfState = new State[paramString.length() + 1];
    arrayOfState[0] = automaton.f;
    for (byte b1 = 0; b1 < paramString.length(); b1++)
      arrayOfState[b1 + 1] = new State(); 
    State state = arrayOfState[paramString.length()];
    state.b = true;
    state.c.add(new Transition(false, '￿', state));
    for (byte b2 = 0; b2 < paramString.length(); b2++) {
      TreeSet<Character> treeSet = new TreeSet();
      char c = paramString.charAt(b2);
      (arrayOfState[b2]).c.add(new Transition(c, arrayOfState[b2 + 1]));
      treeSet.add(Character.valueOf(c));
      for (byte b3 = b2; b3 >= 1; b3--) {
        char c1 = paramString.charAt(b3 - 1);
        if (!treeSet.contains(Character.valueOf(c1)) && paramString.substring(0, b3 - 1).equals(paramString.substring(b2 - b3 + 1, b2))) {
          (arrayOfState[b2]).c.add(new Transition(c1, arrayOfState[b3]));
          treeSet.add(Character.valueOf(c1));
        } 
      } 
      char[] arrayOfChar = new char[treeSet.size()];
      byte b4 = 0;
      for (Iterator<Character> iterator = treeSet.iterator(); iterator.hasNext(); ) {
        char c1 = ((Character)iterator.next()).charValue();
        arrayOfChar[b4++] = c1;
      } 
      Arrays.sort(arrayOfChar);
      int i = 0;
      byte b5 = 0;
      while (i <= 65535) {
        while (b5 < arrayOfChar.length && arrayOfChar[b5] == i) {
          b5++;
          i++;
        } 
        if (i <= 65535) {
          int j = 65535;
          if (b5 < arrayOfChar.length) {
            j = arrayOfChar[b5] - 1;
            b5++;
          } 
          (arrayOfState[b2]).c.add(new Transition((char)i, (char)j, arrayOfState[0]));
          i = j + 2;
        } 
      } 
    } 
    automaton.g = true;
    return automaton;
  }
}
