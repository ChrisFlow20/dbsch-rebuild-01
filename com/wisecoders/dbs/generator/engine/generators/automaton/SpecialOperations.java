package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class SpecialOperations {
  public static Set a(Automaton paramAutomaton) {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
    Set set1 = paramAutomaton.i();
    Set set2 = paramAutomaton.j();
    for (State state : set1) {
      linkedHashMap.put(state, new TreeSet());
      state.b = false;
    } 
    for (State state : set1) {
      for (Transition transition : state.b())
        ((TreeSet<Transition>)linkedHashMap.get(transition.d)).add(new Transition(transition.b, transition.c, state)); 
    } 
    for (State state : set1)
      state.c = (Set)linkedHashMap.get(state); 
    paramAutomaton.f.b = true;
    paramAutomaton.f = new State();
    for (State state : set2)
      paramAutomaton.f.a(state); 
    paramAutomaton.g = false;
    return set2;
  }
  
  public static Automaton a(Automaton paramAutomaton1, Automaton paramAutomaton2) {
    Automaton automaton1 = paramAutomaton1.w();
    automaton1.H();
    i(automaton1);
    Automaton automaton2 = paramAutomaton2.w();
    a(automaton2);
    automaton2.H();
    i(automaton2);
    a(automaton2);
    automaton2.H();
    return automaton1.c(automaton2).b(BasicAutomata.b());
  }
  
  private static void i(Automaton paramAutomaton) {
    State state = new State();
    for (State state1 : paramAutomaton.j())
      state.a(state1); 
    paramAutomaton.f = state;
    paramAutomaton.g = false;
  }
  
  public static Automaton b(Automaton paramAutomaton) {
    Automaton automaton = new Automaton();
    State state1 = new State();
    automaton.f = state1;
    State state2 = new State();
    state2.b = true;
    if (paramAutomaton.d()) {
      for (byte b = 0; b < paramAutomaton.j.length(); b++)
        state1.c.add(new Transition(paramAutomaton.j.charAt(b), state2)); 
    } else {
      for (State state : paramAutomaton.i()) {
        for (Transition transition : state.c)
          state1.c.add(new Transition(transition.b, transition.c, state2)); 
      } 
    } 
    automaton.g = true;
    automaton.p();
    return automaton;
  }
  
  public static Automaton a(Automaton paramAutomaton, String paramString, char paramChar) {
    paramAutomaton = paramAutomaton.x();
    State state1 = new State();
    a(state1, paramString, state1);
    state1.b = true;
    for (State state3 : paramAutomaton.i()) {
      State state4 = state3.a(paramChar);
      if (state4 != null) {
        State state = new State();
        a(state, paramString, state);
        a(state3, paramString, state);
        state.a(state4);
      } 
      if (state3.b)
        state3.a(state1); 
    } 
    State state2 = new State();
    a(state2, paramString, state2);
    state2.a(paramAutomaton.f);
    paramAutomaton.f = state2;
    paramAutomaton.g = false;
    paramAutomaton.p();
    paramAutomaton.c();
    return paramAutomaton;
  }
  
  private static void a(State paramState1, String paramString, State paramState2) {
    for (byte b = 0; b < paramString.length(); b++)
      paramState1.c.add(new Transition(paramString.charAt(b), paramState2)); 
  }
  
  public static Automaton b(Automaton paramAutomaton, String paramString, char paramChar) {
    paramAutomaton = paramAutomaton.x();
    for (State state1 : paramAutomaton.i()) {
      State state2 = state1.a(paramChar);
      if (state2 != null) {
        State state = new State();
        a(state, paramString, state);
        a(state1, paramString, state);
        state.a(state2);
      } 
    } 
    paramAutomaton.g = false;
    paramAutomaton.p();
    paramAutomaton.c();
    return paramAutomaton;
  }
  
  public static Automaton a(Automaton paramAutomaton, Map paramMap) {
    if (paramMap.isEmpty())
      return paramAutomaton.z(); 
    TreeSet treeSet = new TreeSet(paramMap.keySet());
    char[] arrayOfChar = new char[treeSet.size()];
    byte b = 0;
    for (Character character : treeSet)
      arrayOfChar[b++] = character.charValue(); 
    paramAutomaton = paramAutomaton.x();
    for (State state : paramAutomaton.i()) {
      Set set = state.c;
      state.a();
      for (Transition transition : set) {
        int i = a(transition.b, arrayOfChar);
        while (transition.b <= transition.c) {
          if (arrayOfChar[i] > transition.b) {
            char c = (char)(arrayOfChar[i] - 1);
            if (transition.c < c)
              c = transition.c; 
            state.c.add(new Transition(transition.b, c, transition.d));
            if (c + 1 > 65535)
              break; 
            transition.b = (char)(c + 1);
            continue;
          } 
          if (arrayOfChar[i] < transition.b) {
            char c;
            if (i + 1 < arrayOfChar.length) {
              c = (char)(arrayOfChar[++i] - 1);
            } else {
              c = '￿';
            } 
            if (transition.c < c)
              c = transition.c; 
            state.c.add(new Transition(transition.b, c, transition.d));
            if (c + 1 > 65535)
              break; 
            transition.b = (char)(c + 1);
            continue;
          } 
          for (Character character : paramMap.get(Character.valueOf(transition.b)))
            state.c.add(new Transition(character.charValue(), transition.d)); 
          if (transition.b + 1 > 65535)
            break; 
          transition.b = (char)(transition.b + 1);
          if (i + 1 < arrayOfChar.length && arrayOfChar[i + 1] == transition.b)
            i++; 
        } 
      } 
    } 
    paramAutomaton.g = false;
    paramAutomaton.p();
    paramAutomaton.c();
    return paramAutomaton;
  }
  
  static int a(char paramChar, char[] paramArrayOfchar) {
    int i = 0;
    int j = paramArrayOfchar.length;
    while (j - i > 1) {
      int k = i + j >>> 1;
      if (paramArrayOfchar[k] > paramChar) {
        j = k;
        continue;
      } 
      if (paramArrayOfchar[k] < paramChar) {
        i = k;
        continue;
      } 
      return k;
    } 
    return i;
  }
  
  public static Automaton a(Automaton paramAutomaton, char paramChar, String paramString) {
    paramAutomaton = paramAutomaton.x();
    TreeSet<StatePair> treeSet = new TreeSet();
    for (State state : paramAutomaton.i()) {
      Set set = state.c;
      state.a();
      for (Transition transition : set) {
        if (transition.c < paramChar || transition.b > paramChar) {
          state.c.add(transition);
          continue;
        } 
        if (transition.b < paramChar)
          state.c.add(new Transition(transition.b, (char)(paramChar - 1), transition.d)); 
        if (transition.c > paramChar)
          state.c.add(new Transition((char)(paramChar + 1), transition.c, transition.d)); 
        if (paramString.length() == 0) {
          treeSet.add(new StatePair(state, transition.d));
          continue;
        } 
        State state1 = state;
        for (byte b = 0; b < paramString.length(); b++) {
          State state2;
          if (b + 1 == paramString.length()) {
            state2 = transition.d;
          } else {
            state2 = new State();
          } 
          state1.c.add(new Transition(paramString.charAt(b), state2));
          state1 = state2;
        } 
      } 
    } 
    paramAutomaton.b(treeSet);
    paramAutomaton.g = false;
    paramAutomaton.p();
    paramAutomaton.c();
    return paramAutomaton;
  }
  
  public static Automaton a(Automaton paramAutomaton, char[] paramArrayOfchar1, char[] paramArrayOfchar2) {
    paramAutomaton = paramAutomaton.x();
    for (State state : paramAutomaton.i()) {
      Set set = state.c;
      state.a();
      for (Transition transition : set) {
        char c = transition.b;
        while (c <= transition.c) {
          int k, j = a((char)c, paramArrayOfchar1);
          char c1 = (char)(paramArrayOfchar2[j] + c - paramArrayOfchar1[j]);
          byte b = (j + 1 == paramArrayOfchar1.length) ? 65535 : (paramArrayOfchar1[j + 1] - 1);
          if (b < transition.c) {
            k = b + 1 - c;
          } else {
            k = transition.c + 1 - c;
          } 
          state.c.add(new Transition(c1, (char)(c1 + k - 1), transition.d));
          int i = c + k;
        } 
      } 
    } 
    paramAutomaton.g = false;
    paramAutomaton.p();
    paramAutomaton.c();
    return paramAutomaton;
  }
  
  public static Automaton a(Automaton paramAutomaton, Set paramSet) {
    Character[] arrayOfCharacter = (Character[])paramSet.toArray((Object[])new Character[paramSet.size()]);
    char[] arrayOfChar = new char[arrayOfCharacter.length];
    boolean bool = false;
    byte b;
    for (b = 0; b < arrayOfCharacter.length; b++) {
      if (arrayOfCharacter[b] == null) {
        bool = true;
      } else {
        arrayOfChar[b] = arrayOfCharacter[b].charValue();
      } 
    } 
    Arrays.sort(arrayOfChar);
    if (paramAutomaton.d()) {
      for (b = 0; b < paramAutomaton.j.length(); b++) {
        char c = paramAutomaton.j.charAt(b);
        if ((!bool || (c > '?' && c < '豈')) && Arrays.binarySearch(arrayOfChar, c) < 0)
          return BasicAutomata.a(); 
      } 
      return paramAutomaton.z();
    } 
    TreeSet<StatePair> treeSet = new TreeSet();
    paramAutomaton = paramAutomaton.x();
    for (State state : paramAutomaton.i()) {
      TreeSet<Transition> treeSet1 = new TreeSet();
      for (Transition transition : state.c) {
        boolean bool1 = false;
        if (transition.b < '豈' && transition.c > '?') {
          int i = Arrays.binarySearch(arrayOfChar, (transition.b > '') ? transition.b : 57344);
          if (i < 0) {
            i = -i - 1;
            bool1 = true;
          } 
          int j = Arrays.binarySearch(arrayOfChar, (transition.c < '') ? transition.c : 63743);
          if (j < 0) {
            j = -j - 2;
            bool1 = true;
          } 
          for (int k = i; k <= j; k++) {
            treeSet1.add(new Transition(arrayOfChar[k], transition.d));
            if (k > i && arrayOfChar[k - 1] + 1 != arrayOfChar[k])
              bool1 = true; 
          } 
        } 
        if (bool) {
          if (transition.b <= '?')
            treeSet1.add(new Transition(transition.b, (transition.c < '?') ? transition.c : 57343, transition.d)); 
          if (transition.c >= '豈')
            treeSet1.add(new Transition((transition.b > '豈') ? transition.b : 63744, transition.c, transition.d)); 
        } else if (transition.b <= '?' || transition.c >= '豈') {
          bool1 = true;
        } 
        if (bool1)
          treeSet.add(new StatePair(state, transition.d)); 
      } 
      state.c = treeSet1;
    } 
    paramAutomaton.m();
    paramAutomaton.b(treeSet);
    paramAutomaton.p();
    paramAutomaton.c();
    return paramAutomaton;
  }
  
  public static boolean c(Automaton paramAutomaton) {
    if (paramAutomaton.d())
      return true; 
    return a(paramAutomaton.f, new TreeSet(), new TreeSet());
  }
  
  private static boolean a(State paramState, TreeSet<State> paramTreeSet1, TreeSet<State> paramTreeSet2) {
    paramTreeSet1.add(paramState);
    for (Transition transition : paramState.c) {
      if (paramTreeSet1.contains(transition.d) || (!paramTreeSet2.contains(transition.d) && !a(transition.d, paramTreeSet1, paramTreeSet2)))
        return false; 
    } 
    paramTreeSet1.remove(paramState);
    paramTreeSet2.add(paramState);
    return true;
  }
  
  public static Set a(Automaton paramAutomaton, int paramInt) {
    TreeSet<String> treeSet = new TreeSet();
    if (paramAutomaton.d() && paramAutomaton.j.length() == paramInt) {
      treeSet.add(paramAutomaton.j);
    } else if (paramInt >= 0) {
      a(paramAutomaton.f, treeSet, new StringBuilder(), paramInt);
    } 
    return treeSet;
  }
  
  private static void a(State paramState, Set<String> paramSet, StringBuilder paramStringBuilder, int paramInt) {
    if (paramInt == 0) {
      if (paramState.b)
        paramSet.add(paramStringBuilder.toString()); 
    } else {
      for (Transition transition : paramState.c) {
        for (char c = transition.b; c <= transition.c; c++) {
          paramStringBuilder.append((char)c);
          a(transition.d, paramSet, paramStringBuilder, paramInt - 1);
          paramStringBuilder.deleteCharAt(paramStringBuilder.length() - 1);
        } 
      } 
    } 
  }
  
  public static Set d(Automaton paramAutomaton) {
    TreeSet<String> treeSet = new TreeSet();
    if (paramAutomaton.d()) {
      treeSet.add(paramAutomaton.j);
    } else if (!a(paramAutomaton.f, new TreeSet(), treeSet, new StringBuilder(), -1)) {
      return null;
    } 
    return treeSet;
  }
  
  public static Set b(Automaton paramAutomaton, int paramInt) {
    TreeSet<String> treeSet = new TreeSet();
    if (paramAutomaton.d()) {
      if (paramInt > 0) {
        treeSet.add(paramAutomaton.j);
      } else {
        return null;
      } 
    } else if (!a(paramAutomaton.f, new TreeSet(), treeSet, new StringBuilder(), paramInt)) {
      return null;
    } 
    return treeSet;
  }
  
  private static boolean a(State paramState, TreeSet<State> paramTreeSet1, TreeSet<String> paramTreeSet2, StringBuilder paramStringBuilder, int paramInt) {
    paramTreeSet1.add(paramState);
    for (Transition transition : paramState.c) {
      if (paramTreeSet1.contains(transition.d))
        return false; 
      for (char c = transition.b; c <= transition.c; c++) {
        paramStringBuilder.append((char)c);
        if (transition.d.b) {
          paramTreeSet2.add(paramStringBuilder.toString());
          if (paramInt >= 0 && paramTreeSet2.size() > paramInt)
            return false; 
        } 
        if (!a(transition.d, paramTreeSet1, paramTreeSet2, paramStringBuilder, paramInt))
          return false; 
        paramStringBuilder.deleteCharAt(paramStringBuilder.length() - 1);
      } 
    } 
    paramTreeSet1.remove(paramState);
    return true;
  }
  
  public static String e(Automaton paramAutomaton) {
    if (paramAutomaton.d())
      return paramAutomaton.j; 
    StringBuilder stringBuilder = new StringBuilder();
    TreeSet<State> treeSet = new TreeSet();
    State state = paramAutomaton.f;
    while (true) {
      boolean bool = true;
      treeSet.add(state);
      if (!state.b && state.c.size() == 1) {
        Transition transition = state.c.iterator().next();
        if (transition.b == transition.c && !treeSet.contains(transition.d)) {
          stringBuilder.append(transition.b);
          state = transition.d;
          bool = false;
        } 
      } 
      if (bool)
        return stringBuilder.toString(); 
    } 
  }
  
  public static void f(Automaton paramAutomaton) {
    for (State state : paramAutomaton.i())
      state.a(true); 
    paramAutomaton.u();
    paramAutomaton.c();
  }
  
  public static Automaton g(Automaton paramAutomaton) {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
    for (char c1 = 'a', c2 = 'A'; c1 <= 'f'; c1 = (char)(c1 + 1), c2 = (char)(c2 + 1)) {
      TreeSet<Character> treeSet = new TreeSet();
      treeSet.add(Character.valueOf(c1));
      treeSet.add(Character.valueOf(c2));
      linkedHashMap.put(Character.valueOf(c1), treeSet);
      linkedHashMap.put(Character.valueOf(c2), treeSet);
    } 
    Automaton automaton = Datatypes.a();
    return automaton.a(paramAutomaton.a(linkedHashMap)).a(automaton);
  }
  
  public static Automaton h(Automaton paramAutomaton) {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
    TreeSet<Character> treeSet = new TreeSet();
    treeSet.add(Character.valueOf(' '));
    treeSet.add(Character.valueOf('\t'));
    treeSet.add(Character.valueOf('\n'));
    treeSet.add(Character.valueOf('\r'));
    linkedHashMap.put(Character.valueOf(' '), treeSet);
    return paramAutomaton.a(linkedHashMap);
  }
}
