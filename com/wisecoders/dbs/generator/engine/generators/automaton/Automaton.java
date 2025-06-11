package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Automaton implements Serializable, Cloneable {
  static int e = 2;
  
  static boolean k = false;
  
  static boolean l = false;
  
  static Boolean m = null;
  
  State f = new State();
  
  boolean g = true;
  
  String j = null;
  
  static final long a = 10001L;
  
  public static final int b = 0;
  
  public static final int c = 1;
  
  public static final int d = 2;
  
  transient Object h;
  
  int i;
  
  boolean a() {
    if (m == null)
      m = Boolean.valueOf((System.getProperty("dk.brics.automaton.debug") != null)); 
    return m.booleanValue();
  }
  
  public static void a(int paramInt) {
    e = paramInt;
  }
  
  public static void a(boolean paramBoolean) {
    k = paramBoolean;
  }
  
  public static boolean b(boolean paramBoolean) {
    boolean bool = l;
    l = paramBoolean;
    return bool;
  }
  
  static boolean b() {
    return l;
  }
  
  void c() {
    if (k)
      L(); 
  }
  
  boolean d() {
    return (this.j != null);
  }
  
  public String e() {
    return this.j;
  }
  
  public void a(State paramState) {
    this.f = paramState;
    this.j = null;
  }
  
  public State f() {
    q();
    return this.f;
  }
  
  public boolean g() {
    return this.g;
  }
  
  public void c(boolean paramBoolean) {
    this.g = paramBoolean;
  }
  
  public void a(Object paramObject) {
    this.h = paramObject;
  }
  
  public Object h() {
    return this.h;
  }
  
  public Set i() {
    TreeSet<State> treeSet;
    q();
    if (a()) {
      LinkedHashSet linkedHashSet = new LinkedHashSet();
    } else {
      treeSet = new TreeSet();
    } 
    LinkedList<State> linkedList = new LinkedList();
    linkedList.add(this.f);
    treeSet.add(this.f);
    while (linkedList.size() > 0) {
      Set set;
      State state = linkedList.removeFirst();
      if (a()) {
        List list = state.c(false);
      } else {
        set = state.c;
      } 
      for (Transition transition : set) {
        if (!treeSet.contains(transition.d)) {
          treeSet.add(transition.d);
          linkedList.add(transition.d);
        } 
      } 
    } 
    return treeSet;
  }
  
  public Set j() {
    q();
    TreeSet<State> treeSet1 = new TreeSet();
    TreeSet<State> treeSet2 = new TreeSet();
    LinkedList<State> linkedList = new LinkedList();
    linkedList.add(this.f);
    treeSet2.add(this.f);
    while (linkedList.size() > 0) {
      State state = linkedList.removeFirst();
      if (state.b)
        treeSet1.add(state); 
      for (Transition transition : state.c) {
        if (!treeSet2.contains(transition.d)) {
          treeSet2.add(transition.d);
          linkedList.add(transition.d);
        } 
      } 
    } 
    return treeSet1;
  }
  
  static void a(Set paramSet) {
    byte b = 0;
    for (State state : paramSet)
      state.d = b++; 
  }
  
  void k() {
    State state = new State();
    state.c.add(new Transition(false, '￿', state));
    for (State state1 : i()) {
      int i = 0;
      for (Transition transition : state1.c(false)) {
        if (transition.b > i)
          state1.c.add(new Transition((char)i, (char)(transition.b - 1), state)); 
        if (transition.c + 1 > i)
          i = transition.c + 1; 
      } 
      if (i <= 65535)
        state1.c.add(new Transition((char)i, '￿', state)); 
    } 
  }
  
  public void l() {
    p();
  }
  
  public void m() {
    if (d())
      return; 
    Set set = i();
    a(set);
    for (State state1 : set) {
      List list = state1.c(true);
      state1.a();
      State state2 = null;
      int i = -1, j = -1;
      for (Transition transition : list) {
        if (state2 == transition.d) {
          if (transition.b <= j + 1) {
            if (transition.c > j)
              j = transition.c; 
            continue;
          } 
          if (state2 != null)
            state1.c.add(new Transition((char)i, (char)j, state2)); 
          i = transition.b;
          j = transition.c;
          continue;
        } 
        if (state2 != null)
          state1.c.add(new Transition((char)i, (char)j, state2)); 
        state2 = transition.d;
        i = transition.b;
        j = transition.c;
      } 
      if (state2 != null)
        state1.c.add(new Transition((char)i, (char)j, state2)); 
    } 
    u();
  }
  
  char[] n() {
    TreeSet<Character> treeSet = new TreeSet();
    for (State state : i()) {
      treeSet.add(Character.valueOf(false));
      for (Transition transition : state.c) {
        treeSet.add(Character.valueOf(transition.b));
        if (transition.c < Character.MAX_VALUE)
          treeSet.add(Character.valueOf((char)(transition.c + 1))); 
      } 
    } 
    char[] arrayOfChar = new char[treeSet.size()];
    byte b = 0;
    for (Character character : treeSet)
      arrayOfChar[b++] = character.charValue(); 
    Arrays.sort(arrayOfChar);
    return arrayOfChar;
  }
  
  public Set o() {
    q();
    return d(i());
  }
  
  private Set d(Set paramSet) {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
    for (State state : paramSet)
      linkedHashMap.put(state, new TreeSet()); 
    for (State state : paramSet) {
      for (Transition transition : state.c)
        ((Set<State>)linkedHashMap.get(transition.d)).add(state); 
    } 
    TreeSet<?> treeSet = new TreeSet(j());
    LinkedList<State> linkedList = new LinkedList(treeSet);
    while (linkedList.size() > 0) {
      State state = linkedList.removeFirst();
      for (State state1 : linkedHashMap.get(state)) {
        if (!treeSet.contains(state1)) {
          treeSet.add(state1);
          linkedList.add(state1);
        } 
      } 
    } 
    return treeSet;
  }
  
  public void p() {
    u();
    if (d())
      return; 
    Set set1 = i();
    Set set2 = d(set1);
    for (State state : set1) {
      Set set = state.c;
      state.a();
      for (Transition transition : set) {
        if (set2.contains(transition.d))
          state.c.add(transition); 
      } 
    } 
    m();
  }
  
  static Transition[][] b(Set paramSet) {
    a(paramSet);
    Transition[][] arrayOfTransition = new Transition[paramSet.size()][];
    for (State state : paramSet)
      arrayOfTransition[state.d] = state.b(false); 
    return arrayOfTransition;
  }
  
  public void q() {
    if (d()) {
      State state = new State();
      this.f = state;
      for (byte b = 0; b < this.j.length(); b++) {
        State state1 = new State();
        state.c.add(new Transition(this.j.charAt(b), state1));
        state = state1;
      } 
      state.b = true;
      this.g = true;
      this.j = null;
    } 
  }
  
  public int r() {
    if (d())
      return this.j.length() + 1; 
    return i().size();
  }
  
  public int s() {
    if (d())
      return this.j.length(); 
    int i = 0;
    for (State state : i())
      i += state.c.size(); 
    return i;
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == this)
      return true; 
    if (!(paramObject instanceof Automaton))
      return false; 
    Automaton automaton = (Automaton)paramObject;
    if (d() && automaton.d())
      return this.j.equals(automaton.j); 
    return (hashCode() == automaton.hashCode() && d(automaton) && automaton.d(this));
  }
  
  public int hashCode() {
    if (this.i == 0)
      L(); 
    return this.i;
  }
  
  void t() {
    this.i = r() * 3 + s() * 2;
    if (this.i == 0)
      this.i = 1; 
  }
  
  void u() {
    this.i = 0;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    if (d()) {
      stringBuilder.append("singleton: ");
      for (char c : this.j.toCharArray())
        Transition.a(c, stringBuilder); 
      stringBuilder.append("\n");
    } else {
      Set set = i();
      a(set);
      stringBuilder.append("initial state: ").append(this.f.d).append("\n");
      for (State state : set)
        stringBuilder.append(state.toString()); 
    } 
    return stringBuilder.toString();
  }
  
  public String v() {
    StringBuilder stringBuilder = new StringBuilder("digraph Automaton {\n");
    stringBuilder.append("  rankdir = LR;\n");
    Set set = i();
    a(set);
    for (State state : set) {
      stringBuilder.append("  ").append(state.d);
      if (state.b) {
        stringBuilder.append(" [shape=doublecircle,label=\"\"];\n");
      } else {
        stringBuilder.append(" [shape=circle,label=\"\"];\n");
      } 
      if (state == this.f) {
        stringBuilder.append("  initial [shape=plaintext,label=\"\"];\n");
        stringBuilder.append("  initial -> ").append(state.d).append("\n");
      } 
      for (Transition transition : state.c) {
        stringBuilder.append("  ").append(state.d);
        transition.a(stringBuilder);
      } 
    } 
    return stringBuilder.append("}\n").toString();
  }
  
  Automaton w() {
    Automaton automaton = y();
    automaton.q();
    return automaton;
  }
  
  Automaton x() {
    if (l) {
      q();
      return this;
    } 
    return w();
  }
  
  public Automaton y() {
    try {
      Automaton automaton = (Automaton)super.clone();
      if (!d()) {
        LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
        Set set = i();
        for (State state : set)
          linkedHashMap.put(state, new State()); 
        for (State state1 : set) {
          State state2 = (State)linkedHashMap.get(state1);
          state2.b = state1.b;
          if (state1 == this.f)
            automaton.f = state2; 
          for (Transition transition : state1.c)
            state2.c.add(new Transition(transition.b, transition.c, (State)linkedHashMap.get(transition.d))); 
        } 
      } 
      return automaton;
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      throw new RuntimeException(cloneNotSupportedException);
    } 
  }
  
  Automaton z() {
    if (l)
      return this; 
    return y();
  }
  
  public static Automaton a(URL paramURL) {
    return a(paramURL.openStream());
  }
  
  public static Automaton a(InputStream paramInputStream) {
    ObjectInputStream objectInputStream = new ObjectInputStream(paramInputStream);
    return (Automaton)objectInputStream.readObject();
  }
  
  public void a(OutputStream paramOutputStream) {
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(paramOutputStream);
    objectOutputStream.writeObject(this);
    objectOutputStream.flush();
  }
  
  public static Automaton A() {
    return BasicAutomata.a();
  }
  
  public static Automaton B() {
    return BasicAutomata.b();
  }
  
  public static Automaton C() {
    return BasicAutomata.c();
  }
  
  public static Automaton D() {
    return BasicAutomata.d();
  }
  
  public static Automaton a(char paramChar) {
    return BasicAutomata.a(paramChar);
  }
  
  public static Automaton a(char paramChar1, char paramChar2) {
    return BasicAutomata.a(paramChar1, paramChar2);
  }
  
  public static Automaton a(String paramString) {
    return BasicAutomata.a(paramString);
  }
  
  public static Automaton a(int paramInt1, int paramInt2, int paramInt3) {
    return BasicAutomata.a(paramInt1, paramInt2, paramInt3);
  }
  
  public static Automaton b(String paramString) {
    return BasicAutomata.b(paramString);
  }
  
  public static Automaton a(CharSequence... paramVarArgs) {
    return BasicAutomata.a(paramVarArgs);
  }
  
  public static Automaton c(String paramString) {
    return BasicAutomata.c(paramString);
  }
  
  public static Automaton d(String paramString) {
    return BasicAutomata.d(paramString);
  }
  
  public static Automaton b(int paramInt) {
    return BasicAutomata.a(paramInt);
  }
  
  public static Automaton c(int paramInt) {
    return BasicAutomata.b(paramInt);
  }
  
  public static Automaton e(String paramString) {
    return BasicAutomata.e(paramString);
  }
  
  public static Automaton f(String paramString) {
    return BasicAutomata.f(paramString);
  }
  
  public static Automaton g(String paramString) {
    return BasicAutomata.g(paramString);
  }
  
  public Automaton a(Automaton paramAutomaton) {
    return BasicOperations.a(this, paramAutomaton);
  }
  
  public static Automaton a(List paramList) {
    return BasicOperations.a(paramList);
  }
  
  public Automaton E() {
    return BasicOperations.a(this);
  }
  
  public Automaton F() {
    return BasicOperations.b(this);
  }
  
  public Automaton d(int paramInt) {
    return BasicOperations.a(this, paramInt);
  }
  
  public Automaton a(int paramInt1, int paramInt2) {
    return BasicOperations.a(this, paramInt1, paramInt2);
  }
  
  public Automaton G() {
    return BasicOperations.c(this);
  }
  
  public Automaton b(Automaton paramAutomaton) {
    return BasicOperations.b(this, paramAutomaton);
  }
  
  public Automaton c(Automaton paramAutomaton) {
    return BasicOperations.c(this, paramAutomaton);
  }
  
  public boolean d(Automaton paramAutomaton) {
    return BasicOperations.d(this, paramAutomaton);
  }
  
  public Automaton e(Automaton paramAutomaton) {
    return BasicOperations.e(this, paramAutomaton);
  }
  
  public static Automaton a(Collection paramCollection) {
    return BasicOperations.a(paramCollection);
  }
  
  public void H() {
    BasicOperations.d(this);
  }
  
  public void b(Collection paramCollection) {
    BasicOperations.a(this, paramCollection);
  }
  
  public boolean I() {
    return BasicOperations.e(this);
  }
  
  public boolean J() {
    return BasicOperations.f(this);
  }
  
  public boolean K() {
    return BasicOperations.g(this);
  }
  
  public String d(boolean paramBoolean) {
    return BasicOperations.a(this, paramBoolean);
  }
  
  public boolean h(String paramString) {
    return BasicOperations.a(this, paramString);
  }
  
  public void L() {
    MinimizationOperations.a(this);
  }
  
  public static Automaton f(Automaton paramAutomaton) {
    paramAutomaton.L();
    return paramAutomaton;
  }
  
  public Automaton g(Automaton paramAutomaton) {
    return SpecialOperations.a(this, paramAutomaton);
  }
  
  public Automaton M() {
    return SpecialOperations.b(this);
  }
  
  public Automaton a(String paramString, char paramChar) {
    return SpecialOperations.a(this, paramString, paramChar);
  }
  
  public Automaton b(String paramString, char paramChar) {
    return SpecialOperations.b(this, paramString, paramChar);
  }
  
  public Automaton a(Map paramMap) {
    return SpecialOperations.a(this, paramMap);
  }
  
  public Automaton a(char paramChar, String paramString) {
    return SpecialOperations.a(this, paramChar, paramString);
  }
  
  public Automaton a(char[] paramArrayOfchar1, char[] paramArrayOfchar2) {
    return SpecialOperations.a(this, paramArrayOfchar1, paramArrayOfchar2);
  }
  
  public Automaton c(Set paramSet) {
    return SpecialOperations.a(this, paramSet);
  }
  
  public boolean N() {
    return SpecialOperations.c(this);
  }
  
  public Set e(int paramInt) {
    return SpecialOperations.a(this, paramInt);
  }
  
  public Set O() {
    return SpecialOperations.d(this);
  }
  
  public Set f(int paramInt) {
    return SpecialOperations.b(this, paramInt);
  }
  
  public String P() {
    return SpecialOperations.e(this);
  }
  
  public void Q() {
    SpecialOperations.f(this);
  }
  
  public static Automaton h(Automaton paramAutomaton) {
    return SpecialOperations.g(paramAutomaton);
  }
  
  public static Automaton i(Automaton paramAutomaton) {
    return SpecialOperations.h(paramAutomaton);
  }
  
  public static String a(Collection paramCollection, Automaton paramAutomaton, Character paramCharacter1, Character paramCharacter2) {
    return ShuffleOperations.a(paramCollection, paramAutomaton, paramCharacter1, paramCharacter2);
  }
  
  public Automaton j(Automaton paramAutomaton) {
    return ShuffleOperations.a(this, paramAutomaton);
  }
}
