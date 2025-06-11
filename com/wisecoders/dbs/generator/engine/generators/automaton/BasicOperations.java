package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public final class BasicOperations {
  public static Automaton a(Automaton paramAutomaton1, Automaton paramAutomaton2) {
    if (paramAutomaton1.d() && paramAutomaton2.d())
      return BasicAutomata.b(paramAutomaton1.j + paramAutomaton1.j); 
    if (f(paramAutomaton1) || f(paramAutomaton2))
      return BasicAutomata.a(); 
    boolean bool = (paramAutomaton1.d() && paramAutomaton2.g()) ? true : false;
    if (paramAutomaton1 == paramAutomaton2) {
      paramAutomaton1 = paramAutomaton1.w();
      paramAutomaton2 = paramAutomaton2.w();
    } else {
      paramAutomaton1 = paramAutomaton1.x();
      paramAutomaton2 = paramAutomaton2.x();
    } 
    for (State state : paramAutomaton1.j()) {
      state.b = false;
      state.a(paramAutomaton2.f);
    } 
    paramAutomaton1.g = bool;
    paramAutomaton1.u();
    paramAutomaton1.c();
    return paramAutomaton1;
  }
  
  public static Automaton a(List<Automaton> paramList) {
    if (paramList.isEmpty())
      return BasicAutomata.b(); 
    boolean bool1 = true;
    for (Automaton automaton1 : paramList) {
      if (!automaton1.d()) {
        bool1 = false;
        break;
      } 
    } 
    if (bool1) {
      StringBuilder stringBuilder = new StringBuilder();
      for (Automaton automaton1 : paramList)
        stringBuilder.append(automaton1.j); 
      return BasicAutomata.b(stringBuilder.toString());
    } 
    for (Automaton automaton1 : paramList) {
      if (automaton1.J())
        return BasicAutomata.a(); 
    } 
    TreeSet<Integer> treeSet = new TreeSet();
    for (Automaton automaton1 : paramList)
      treeSet.add(Integer.valueOf(System.identityHashCode(automaton1))); 
    boolean bool2 = (treeSet.size() != paramList.size()) ? true : false;
    Automaton automaton = paramList.get(0);
    if (bool2) {
      automaton = automaton.w();
    } else {
      automaton = automaton.x();
    } 
    Set<State> set = automaton.j();
    boolean bool3 = true;
    for (Automaton automaton1 : paramList) {
      if (bool3) {
        bool3 = false;
        continue;
      } 
      if (automaton1.I())
        continue; 
      Automaton automaton2 = automaton1;
      if (bool2) {
        automaton2 = automaton2.w();
      } else {
        automaton2 = automaton2.x();
      } 
      Set<State> set1 = automaton2.j();
      for (State state : set) {
        state.b = false;
        state.a(automaton2.f);
        if (state.b)
          set1.add(state); 
      } 
      set = set1;
    } 
    automaton.g = false;
    automaton.u();
    automaton.c();
    return automaton;
  }
  
  public static Automaton a(Automaton paramAutomaton) {
    paramAutomaton = paramAutomaton.x();
    State state = new State();
    state.a(paramAutomaton.f);
    state.b = true;
    paramAutomaton.f = state;
    paramAutomaton.g = false;
    paramAutomaton.u();
    paramAutomaton.c();
    return paramAutomaton;
  }
  
  public static Automaton b(Automaton paramAutomaton) {
    paramAutomaton = paramAutomaton.w();
    State state = new State();
    state.b = true;
    state.a(paramAutomaton.f);
    for (State state1 : paramAutomaton.j())
      state1.a(state); 
    paramAutomaton.f = state;
    paramAutomaton.g = false;
    paramAutomaton.u();
    paramAutomaton.c();
    return paramAutomaton;
  }
  
  public static Automaton a(Automaton paramAutomaton, int paramInt) {
    if (paramInt == 0)
      return b(paramAutomaton); 
    ArrayList<Automaton> arrayList = new ArrayList();
    while (paramInt-- > 0)
      arrayList.add(paramAutomaton); 
    arrayList.add(b(paramAutomaton));
    return a(arrayList);
  }
  
  public static Automaton a(Automaton paramAutomaton, int paramInt1, int paramInt2) {
    Automaton automaton;
    if (paramInt1 > paramInt2)
      return BasicAutomata.a(); 
    paramInt2 -= paramInt1;
    paramAutomaton.q();
    if (paramInt1 == 0) {
      automaton = BasicAutomata.b();
    } else if (paramInt1 == 1) {
      automaton = paramAutomaton.y();
    } else {
      ArrayList<Automaton> arrayList = new ArrayList();
      while (paramInt1-- > 0)
        arrayList.add(paramAutomaton); 
      automaton = a(arrayList);
    } 
    if (paramInt2 > 0) {
      Automaton automaton1 = paramAutomaton.y();
      while (--paramInt2 > 0) {
        Automaton automaton2 = paramAutomaton.y();
        for (State state : automaton2.j())
          state.a(automaton1.f); 
        automaton1 = automaton2;
      } 
      for (State state : automaton.j())
        state.a(automaton1.f); 
      automaton.g = false;
      automaton.u();
      automaton.c();
    } 
    return automaton;
  }
  
  public static Automaton c(Automaton paramAutomaton) {
    paramAutomaton = paramAutomaton.x();
    paramAutomaton.H();
    paramAutomaton.k();
    for (State state : paramAutomaton.i())
      state.b = !state.b; 
    paramAutomaton.p();
    return paramAutomaton;
  }
  
  public static Automaton b(Automaton paramAutomaton1, Automaton paramAutomaton2) {
    if (paramAutomaton1.J() || paramAutomaton1 == paramAutomaton2)
      return BasicAutomata.a(); 
    if (paramAutomaton2.J())
      return paramAutomaton1.z(); 
    if (paramAutomaton1.d()) {
      if (paramAutomaton2.h(paramAutomaton1.j))
        return BasicAutomata.a(); 
      return paramAutomaton1.z();
    } 
    return c(paramAutomaton1, paramAutomaton2.G());
  }
  
  public static Automaton c(Automaton paramAutomaton1, Automaton paramAutomaton2) {
    if (paramAutomaton1.d()) {
      if (paramAutomaton2.h(paramAutomaton1.j))
        return paramAutomaton1.z(); 
      return BasicAutomata.a();
    } 
    if (paramAutomaton2.d()) {
      if (paramAutomaton1.h(paramAutomaton2.j))
        return paramAutomaton2.z(); 
      return BasicAutomata.a();
    } 
    if (paramAutomaton1 == paramAutomaton2)
      return paramAutomaton1.z(); 
    Transition[][] arrayOfTransition1 = Automaton.b(paramAutomaton1.i());
    Transition[][] arrayOfTransition2 = Automaton.b(paramAutomaton2.i());
    Automaton automaton = new Automaton();
    LinkedList<StatePair> linkedList = new LinkedList();
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
    StatePair statePair = new StatePair(automaton.f, paramAutomaton1.f, paramAutomaton2.f);
    linkedList.add(statePair);
    linkedHashMap.put(statePair, statePair);
    while (linkedList.size() > 0) {
      statePair = linkedList.removeFirst();
      statePair.a.b = (statePair.b.b && statePair.c.b);
      Transition[] arrayOfTransition3 = arrayOfTransition1[statePair.b.d];
      Transition[] arrayOfTransition4 = arrayOfTransition2[statePair.c.d];
      for (byte b1 = 0, b2 = 0; b1 < arrayOfTransition3.length; b1++) {
        while (b2 < arrayOfTransition4.length && (arrayOfTransition4[b2]).c < (arrayOfTransition3[b1]).b)
          b2++; 
        for (byte b = b2; b < arrayOfTransition4.length && (arrayOfTransition3[b1]).c >= (arrayOfTransition4[b]).b; b++) {
          if ((arrayOfTransition4[b]).c >= (arrayOfTransition3[b1]).b) {
            StatePair statePair1 = new StatePair((arrayOfTransition3[b1]).d, (arrayOfTransition4[b]).d);
            StatePair statePair2 = (StatePair)linkedHashMap.get(statePair1);
            if (statePair2 == null) {
              statePair1.a = new State();
              linkedList.add(statePair1);
              linkedHashMap.put(statePair1, statePair1);
              statePair2 = statePair1;
            } 
            char c1 = ((arrayOfTransition3[b1]).b > (arrayOfTransition4[b]).b) ? (arrayOfTransition3[b1]).b : (arrayOfTransition4[b]).b;
            char c2 = ((arrayOfTransition3[b1]).c < (arrayOfTransition4[b]).c) ? (arrayOfTransition3[b1]).c : (arrayOfTransition4[b]).c;
            statePair.a.c.add(new Transition(c1, c2, statePair2.a));
          } 
        } 
      } 
    } 
    automaton.g = (paramAutomaton1.g && paramAutomaton2.g);
    automaton.p();
    automaton.c();
    return automaton;
  }
  
  public static boolean d(Automaton paramAutomaton1, Automaton paramAutomaton2) {
    if (paramAutomaton1 == paramAutomaton2)
      return true; 
    if (paramAutomaton1.d()) {
      if (paramAutomaton2.d())
        return paramAutomaton1.j.equals(paramAutomaton2.j); 
      return paramAutomaton2.h(paramAutomaton1.j);
    } 
    paramAutomaton2.H();
    Transition[][] arrayOfTransition1 = Automaton.b(paramAutomaton1.i());
    Transition[][] arrayOfTransition2 = Automaton.b(paramAutomaton2.i());
    LinkedList<StatePair> linkedList = new LinkedList();
    TreeSet<StatePair> treeSet = new TreeSet();
    StatePair statePair = new StatePair(paramAutomaton1.f, paramAutomaton2.f);
    linkedList.add(statePair);
    treeSet.add(statePair);
    while (linkedList.size() > 0) {
      statePair = linkedList.removeFirst();
      if (statePair.b.b && !statePair.c.b)
        return false; 
      Transition[] arrayOfTransition3 = arrayOfTransition1[statePair.b.d];
      Transition[] arrayOfTransition4 = arrayOfTransition2[statePair.c.d];
      for (byte b1 = 0, b2 = 0; b1 < arrayOfTransition3.length; b1++) {
        while (b2 < arrayOfTransition4.length && (arrayOfTransition4[b2]).c < (arrayOfTransition3[b1]).b)
          b2++; 
        char c1 = (arrayOfTransition3[b1]).b, c2 = (arrayOfTransition3[b1]).c;
        for (byte b = b2; b < arrayOfTransition4.length && (arrayOfTransition3[b1]).c >= (arrayOfTransition4[b]).b; b++) {
          if ((arrayOfTransition4[b]).b > c1)
            return false; 
          if ((arrayOfTransition4[b]).c < Character.MAX_VALUE) {
            int i = (arrayOfTransition4[b]).c + 1;
          } else {
            c1 = Character.MAX_VALUE;
            c2 = Character.MIN_VALUE;
          } 
          StatePair statePair1 = new StatePair((arrayOfTransition3[b1]).d, (arrayOfTransition4[b]).d);
          if (!treeSet.contains(statePair1)) {
            linkedList.add(statePair1);
            treeSet.add(statePair1);
          } 
        } 
        if (c1 <= c2)
          return false; 
      } 
    } 
    return true;
  }
  
  public static Automaton e(Automaton paramAutomaton1, Automaton paramAutomaton2) {
    if ((paramAutomaton1.d() && paramAutomaton2.d() && paramAutomaton1.j.equals(paramAutomaton2.j)) || paramAutomaton1 == paramAutomaton2)
      return paramAutomaton1.z(); 
    if (paramAutomaton1 == paramAutomaton2) {
      paramAutomaton1 = paramAutomaton1.w();
      paramAutomaton2 = paramAutomaton2.w();
    } else {
      paramAutomaton1 = paramAutomaton1.x();
      paramAutomaton2 = paramAutomaton2.x();
    } 
    State state = new State();
    state.a(paramAutomaton1.f);
    state.a(paramAutomaton2.f);
    paramAutomaton1.f = state;
    paramAutomaton1.g = false;
    paramAutomaton1.u();
    paramAutomaton1.c();
    return paramAutomaton1;
  }
  
  public static Automaton a(Collection paramCollection) {
    TreeSet<Integer> treeSet = new TreeSet();
    for (Automaton automaton1 : paramCollection)
      treeSet.add(Integer.valueOf(System.identityHashCode(automaton1))); 
    boolean bool = (treeSet.size() != paramCollection.size()) ? true : false;
    State state = new State();
    for (Automaton automaton1 : paramCollection) {
      if (automaton1.J())
        continue; 
      Automaton automaton2 = automaton1;
      if (bool) {
        automaton2 = automaton2.w();
      } else {
        automaton2 = automaton2.x();
      } 
      state.a(automaton2.f);
    } 
    Automaton automaton = new Automaton();
    automaton.f = state;
    automaton.g = false;
    automaton.u();
    automaton.c();
    return automaton;
  }
  
  public static void d(Automaton paramAutomaton) {
    if (paramAutomaton.g || paramAutomaton.d())
      return; 
    TreeSet<State> treeSet = new TreeSet();
    treeSet.add(paramAutomaton.f);
    a(paramAutomaton, treeSet);
  }
  
  static void a(Automaton paramAutomaton, Set paramSet) {
    char[] arrayOfChar = paramAutomaton.n();
    LinkedHashMap<Object, Object> linkedHashMap1 = new LinkedHashMap<>();
    LinkedList<Set> linkedList = new LinkedList();
    LinkedHashMap<Object, Object> linkedHashMap2 = new LinkedHashMap<>();
    linkedHashMap1.put(paramSet, paramSet);
    linkedList.add(paramSet);
    paramAutomaton.f = new State();
    linkedHashMap2.put(paramSet, paramAutomaton.f);
    while (linkedList.size() > 0) {
      Set set = linkedList.removeFirst();
      State state = (State)linkedHashMap2.get(set);
      for (State state1 : set) {
        if (state1.b) {
          state.b = true;
          break;
        } 
      } 
      for (byte b = 0; b < arrayOfChar.length; b++) {
        char c1;
        TreeSet<State> treeSet = new TreeSet();
        for (State state2 : set) {
          for (Transition transition : state2.c) {
            if (transition.b <= arrayOfChar[b] && arrayOfChar[b] <= transition.c)
              treeSet.add(transition.d); 
          } 
        } 
        if (!linkedHashMap1.containsKey(treeSet)) {
          linkedHashMap1.put(treeSet, treeSet);
          linkedList.add(treeSet);
          linkedHashMap2.put(treeSet, new State());
        } 
        State state1 = (State)linkedHashMap2.get(treeSet);
        char c = arrayOfChar[b];
        if (b + 1 < arrayOfChar.length) {
          c1 = (char)(arrayOfChar[b + 1] - 1);
        } else {
          c1 = '￿';
        } 
        state.c.add(new Transition(c, c1, state1));
      } 
    } 
    paramAutomaton.g = true;
    paramAutomaton.p();
  }
  
  public static void a(Automaton paramAutomaton, Collection<?> paramCollection) {
    paramAutomaton.q();
    LinkedHashMap<Object, Object> linkedHashMap1 = new LinkedHashMap<>();
    LinkedHashMap<Object, Object> linkedHashMap2 = new LinkedHashMap<>();
    for (StatePair statePair : paramCollection) {
      TreeSet<State> treeSet1 = (TreeSet)linkedHashMap1.get(statePair.b);
      if (treeSet1 == null) {
        treeSet1 = new TreeSet();
        linkedHashMap1.put(statePair.b, treeSet1);
      } 
      treeSet1.add(statePair.c);
      TreeSet<State> treeSet2 = (TreeSet)linkedHashMap2.get(statePair.c);
      if (treeSet2 == null) {
        treeSet2 = new TreeSet();
        linkedHashMap2.put(statePair.c, treeSet2);
      } 
      treeSet2.add(statePair.b);
    } 
    LinkedList<StatePair> linkedList = new LinkedList(paramCollection);
    TreeSet<StatePair> treeSet = new TreeSet(paramCollection);
    while (!linkedList.isEmpty()) {
      StatePair statePair = linkedList.removeFirst();
      treeSet.remove(statePair);
      TreeSet treeSet1 = (TreeSet)linkedHashMap1.get(statePair.c);
      TreeSet treeSet2 = (TreeSet)linkedHashMap2.get(statePair.b);
      if (treeSet1 != null)
        for (State state : treeSet1) {
          StatePair statePair1 = new StatePair(statePair.b, state);
          if (!paramCollection.contains(statePair1)) {
            paramCollection.add(statePair1);
            ((TreeSet<State>)linkedHashMap1.get(statePair.b)).add(state);
            ((TreeSet<State>)linkedHashMap2.get(state)).add(statePair.b);
            linkedList.add(statePair1);
            treeSet.add(statePair1);
            if (treeSet2 != null)
              for (State state1 : treeSet2) {
                StatePair statePair2 = new StatePair(state1, statePair.b);
                if (!treeSet.contains(statePair2)) {
                  linkedList.add(statePair2);
                  treeSet.add(statePair2);
                } 
              }  
          } 
        }  
    } 
    for (StatePair statePair : paramCollection)
      statePair.b.a(statePair.c); 
    paramAutomaton.g = false;
    paramAutomaton.u();
    paramAutomaton.c();
  }
  
  public static boolean e(Automaton paramAutomaton) {
    if (paramAutomaton.d())
      return (paramAutomaton.j.length() == 0); 
    return (paramAutomaton.f.b && paramAutomaton.f.c.isEmpty());
  }
  
  public static boolean f(Automaton paramAutomaton) {
    if (paramAutomaton.d())
      return false; 
    return (!paramAutomaton.f.b && paramAutomaton.f.c.isEmpty());
  }
  
  public static boolean g(Automaton paramAutomaton) {
    if (paramAutomaton.d())
      return false; 
    if (paramAutomaton.f.b && paramAutomaton.f.c.size() == 1) {
      Transition transition = paramAutomaton.f.c.iterator().next();
      return (transition.d == paramAutomaton.f && transition.b == '\000' && transition.c == Character.MAX_VALUE);
    } 
    return false;
  }
  
  public static String a(Automaton paramAutomaton, boolean paramBoolean) {
    if (paramAutomaton.d()) {
      if (paramBoolean)
        return paramAutomaton.j; 
      if (paramAutomaton.j.length() > 0)
        return ""; 
      return "\000";
    } 
    return a(paramAutomaton.f(), paramBoolean);
  }
  
  static String a(State paramState, boolean paramBoolean) {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
    LinkedList<State> linkedList = new LinkedList();
    linkedHashMap.put(paramState, "");
    linkedList.add(paramState);
    String str = null;
    while (!linkedList.isEmpty()) {
      State state = linkedList.removeFirst();
      String str1 = (String)linkedHashMap.get(state);
      if (state.b == paramBoolean) {
        if (str == null || str1.length() < str.length() || (str1.length() == str.length() && str1.compareTo(str) < 0))
          str = str1; 
        continue;
      } 
      for (Transition transition : state.b()) {
        String str2 = (String)linkedHashMap.get(transition.d);
        String str3 = str1 + str1;
        if (str2 == null || (str2.length() == str3.length() && str3.compareTo(str2) < 0)) {
          if (str2 == null)
            linkedList.addLast(transition.d); 
          linkedHashMap.put(transition.d, str3);
        } 
      } 
    } 
    return str;
  }
  
  public static boolean a(Automaton paramAutomaton, String paramString) {
    if (paramAutomaton.d())
      return paramString.equals(paramAutomaton.j); 
    if (paramAutomaton.g) {
      State state = paramAutomaton.f;
      for (byte b1 = 0; b1 < paramString.length(); b1++) {
        State state1 = state.a(paramString.charAt(b1));
        if (state1 == null)
          return false; 
        state = state1;
      } 
      return state.b;
    } 
    Set set = paramAutomaton.i();
    Automaton.a(set);
    LinkedList<State> linkedList1 = new LinkedList();
    LinkedList<State> linkedList2 = new LinkedList();
    BitSet bitSet1 = new BitSet(set.size());
    BitSet bitSet2 = new BitSet(set.size());
    linkedList1.add(paramAutomaton.f);
    ArrayList arrayList = new ArrayList();
    boolean bool = paramAutomaton.f.b;
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      bool = false;
      linkedList2.clear();
      bitSet2.clear();
      for (State state : linkedList1) {
        arrayList.clear();
        state.a(c, arrayList);
        for (State state1 : arrayList) {
          if (state1.b)
            bool = true; 
          if (!bitSet2.get(state1.d)) {
            bitSet2.set(state1.d);
            linkedList2.add(state1);
          } 
        } 
      } 
      LinkedList<State> linkedList = linkedList1;
      linkedList1 = linkedList2;
      linkedList2 = linkedList;
      BitSet bitSet = bitSet1;
      bitSet1 = bitSet2;
      bitSet2 = bitSet;
    } 
    return bool;
  }
}
