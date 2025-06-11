package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public final class MinimizationOperations {
  public static void a(Automaton paramAutomaton) {
    if (!paramAutomaton.d())
      switch (Automaton.e) {
        case 0:
          b(paramAutomaton);
          break;
        case 1:
          c(paramAutomaton);
          break;
        default:
          d(paramAutomaton);
          break;
      }  
    paramAutomaton.t();
  }
  
  private static boolean a(Transition[][] paramArrayOfTransition, boolean[][] paramArrayOfboolean, int paramInt1, int paramInt2) {
    Transition[] arrayOfTransition1 = paramArrayOfTransition[paramInt1];
    Transition[] arrayOfTransition2 = paramArrayOfTransition[paramInt2];
    for (byte b1 = 0, b2 = 0; b1 < arrayOfTransition1.length && b2 < arrayOfTransition2.length; ) {
      if ((arrayOfTransition1[b1]).c < (arrayOfTransition2[b2]).b) {
        b1++;
        continue;
      } 
      if ((arrayOfTransition2[b2]).c < (arrayOfTransition1[b1]).b) {
        b2++;
        continue;
      } 
      int i = (arrayOfTransition1[b1]).d.d;
      int j = (arrayOfTransition2[b2]).d.d;
      if (i > j) {
        int k = i;
        i = j;
        j = k;
      } 
      if (paramArrayOfboolean[i][j])
        return false; 
      if ((arrayOfTransition1[b1]).c < (arrayOfTransition2[b2]).c) {
        b1++;
        continue;
      } 
      b2++;
    } 
    return true;
  }
  
  private static void a(Transition[][] paramArrayOfTransition, ArrayList<ArrayList> paramArrayList, int paramInt1, int paramInt2) {
    Transition[] arrayOfTransition1 = paramArrayOfTransition[paramInt1];
    Transition[] arrayOfTransition2 = paramArrayOfTransition[paramInt2];
    for (byte b1 = 0, b2 = 0; b1 < arrayOfTransition1.length && b2 < arrayOfTransition2.length; ) {
      if ((arrayOfTransition1[b1]).c < (arrayOfTransition2[b2]).b) {
        b1++;
        continue;
      } 
      if ((arrayOfTransition2[b2]).c < (arrayOfTransition1[b1]).b) {
        b2++;
        continue;
      } 
      if ((arrayOfTransition1[b1]).d != (arrayOfTransition2[b2]).d) {
        int i = (arrayOfTransition1[b1]).d.d;
        int j = (arrayOfTransition2[b2]).d.d;
        if (i > j) {
          int k = i;
          i = j;
          j = k;
        } 
        if (((ArrayList)paramArrayList.get(i)).get(j) == null)
          ((ArrayList)paramArrayList.get(i)).set(j, new TreeSet()); 
        ((TreeSet<a>)((ArrayList<TreeSet<a>>)paramArrayList.get(i)).get(j)).add(new a(paramInt1, paramInt2));
      } 
      if ((arrayOfTransition1[b1]).c < (arrayOfTransition2[b2]).c) {
        b1++;
        continue;
      } 
      b2++;
    } 
  }
  
  private static void a(boolean[][] paramArrayOfboolean, ArrayList<ArrayList> paramArrayList, int paramInt1, int paramInt2) {
    paramArrayOfboolean[paramInt1][paramInt2] = true;
    if (((ArrayList)paramArrayList.get(paramInt1)).get(paramInt2) != null)
      for (a a : ((ArrayList<TreeSet>)paramArrayList.get(paramInt1)).get(paramInt2)) {
        int i = a.a;
        int j = a.b;
        if (i > j) {
          int k = i;
          i = j;
          j = k;
        } 
        if (!paramArrayOfboolean[i][j])
          a(paramArrayOfboolean, paramArrayList, i, j); 
      }  
  }
  
  private static void a(ArrayList paramArrayList, int paramInt) {
    for (byte b = 0; b < paramInt; b++)
      paramArrayList.add(null); 
  }
  
  public static void b(Automaton paramAutomaton) {
    paramAutomaton.H();
    paramAutomaton.k();
    Set set = paramAutomaton.i();
    Transition[][] arrayOfTransition = new Transition[set.size()][];
    State[] arrayOfState1 = (State[])set.toArray((Object[])new State[set.size()]);
    boolean[][] arrayOfBoolean = new boolean[arrayOfState1.length][arrayOfState1.length];
    ArrayList<ArrayList> arrayList = new ArrayList();
    byte b1;
    for (b1 = 0; b1 < arrayOfState1.length; b1++) {
      ArrayList arrayList1 = new ArrayList();
      a(arrayList1, arrayOfState1.length);
      arrayList.add(arrayList1);
    } 
    for (b1 = 0; b1 < arrayOfState1.length; b1++) {
      (arrayOfState1[b1]).d = b1;
      arrayOfTransition[b1] = arrayOfState1[b1].b(false);
      for (int i = b1 + 1; i < arrayOfState1.length; i++) {
        if ((arrayOfState1[b1]).b != (arrayOfState1[i]).b)
          arrayOfBoolean[b1][i] = true; 
      } 
    } 
    for (b1 = 0; b1 < arrayOfState1.length; b1++) {
      for (int i = b1 + 1; i < arrayOfState1.length; i++) {
        if (!arrayOfBoolean[b1][i])
          if (a(arrayOfTransition, arrayOfBoolean, b1, i)) {
            a(arrayOfTransition, arrayList, b1, i);
          } else {
            a(arrayOfBoolean, arrayList, b1, i);
          }  
      } 
    } 
    b1 = 0;
    byte b2;
    for (b2 = 0; b2 < arrayOfState1.length; b2++)
      (arrayOfState1[b2]).d = -1; 
    for (b2 = 0; b2 < arrayOfState1.length; b2++) {
      if ((arrayOfState1[b2]).d == -1) {
        (arrayOfState1[b2]).d = b1;
        for (int i = b2 + 1; i < arrayOfState1.length; i++) {
          if (!arrayOfBoolean[b2][i])
            (arrayOfState1[i]).d = b1; 
        } 
        b1++;
      } 
    } 
    State[] arrayOfState2 = new State[b1];
    byte b3;
    for (b3 = 0; b3 < b1; b3++)
      arrayOfState2[b3] = new State(); 
    for (b3 = 0; b3 < arrayOfState1.length; b3++) {
      (arrayOfState2[(arrayOfState1[b3]).d]).d = b3;
      if (arrayOfState1[b3] == paramAutomaton.f)
        paramAutomaton.f = arrayOfState2[(arrayOfState1[b3]).d]; 
    } 
    for (b3 = 0; b3 < b1; b3++) {
      State state = arrayOfState2[b3];
      state.b = (arrayOfState1[state.d]).b;
      for (Transition transition : (arrayOfState1[state.d]).c)
        state.c.add(new Transition(transition.b, transition.c, arrayOfState2[transition.d.d])); 
    } 
    paramAutomaton.p();
  }
  
  public static void c(Automaton paramAutomaton) {
    if (paramAutomaton.d())
      return; 
    BasicOperations.a(paramAutomaton, SpecialOperations.a(paramAutomaton));
    BasicOperations.a(paramAutomaton, SpecialOperations.a(paramAutomaton));
  }
  
  public static void d(Automaton paramAutomaton) {
    paramAutomaton.H();
    Set<Transition> set = paramAutomaton.f.b();
    if (set.size() == 1) {
      Transition transition = set.iterator().next();
      if (transition.d == paramAutomaton.f && transition.b == '\000' && transition.c == Character.MAX_VALUE)
        return; 
    } 
    paramAutomaton.k();
    Set set1 = paramAutomaton.i();
    State[] arrayOfState1 = new State[set1.size()];
    byte b1 = 0;
    for (State state : set1) {
      arrayOfState1[b1] = state;
      state.d = b1++;
    } 
    char[] arrayOfChar = paramAutomaton.n();
    ArrayList<ArrayList> arrayList = new ArrayList();
    for (byte b2 = 0; b2 < arrayOfState1.length; b2++) {
      ArrayList arrayList5 = new ArrayList();
      a(arrayList5, arrayOfChar.length);
      arrayList.add(arrayList5);
    } 
    boolean[][] arrayOfBoolean1 = new boolean[arrayOfState1.length][arrayOfChar.length];
    ArrayList<LinkedList<State>> arrayList1 = new ArrayList();
    a(arrayList1, arrayOfState1.length);
    int[] arrayOfInt = new int[arrayOfState1.length];
    b[][] arrayOfB = new b[arrayOfState1.length][arrayOfChar.length];
    c[][] arrayOfC = new c[arrayOfState1.length][arrayOfChar.length];
    LinkedList<a> linkedList = new LinkedList();
    boolean[][] arrayOfBoolean2 = new boolean[arrayOfChar.length][arrayOfState1.length];
    ArrayList<State> arrayList2 = new ArrayList();
    boolean[] arrayOfBoolean3 = new boolean[arrayOfState1.length];
    ArrayList<Integer> arrayList3 = new ArrayList();
    boolean[] arrayOfBoolean4 = new boolean[arrayOfState1.length];
    ArrayList<ArrayList<State>> arrayList4 = new ArrayList();
    a(arrayList4, arrayOfState1.length);
    byte b3;
    for (b3 = 0; b3 < arrayOfState1.length; b3++) {
      arrayList4.set(b3, new ArrayList());
      arrayList1.set(b3, new LinkedList());
      for (byte b = 0; b < arrayOfChar.length; b++) {
        ((ArrayList)arrayList.get(b3)).set(b, new LinkedList());
        arrayOfB[b3][b] = new b();
      } 
    } 
    for (b3 = 0; b3 < arrayOfState1.length; b3++) {
      boolean bool;
      State state = arrayOfState1[b3];
      if (state.b) {
        bool = false;
      } else {
        bool = true;
      } 
      ((LinkedList<State>)arrayList1.get(bool)).add(state);
      arrayOfInt[state.d] = bool;
      for (byte b = 0; b < arrayOfChar.length; b++) {
        char c = arrayOfChar[b];
        State state1 = state.a(c);
        ((LinkedList<State>)((ArrayList<LinkedList<State>>)arrayList.get(state1.d)).get(b)).add(state);
        arrayOfBoolean1[state1.d][b] = true;
      } 
    } 
    for (b3 = 0; b3 <= 1; b3++) {
      for (byte b = 0; b < arrayOfChar.length; b++) {
        for (State state : arrayList1.get(b3)) {
          if (arrayOfBoolean1[state.d][b])
            arrayOfC[state.d][b] = arrayOfB[b3][b].a(state); 
        } 
      } 
    } 
    for (b3 = 0; b3 < arrayOfChar.length; b3++) {
      boolean bool;
      int i = (arrayOfB[0][b3]).a;
      int j = (arrayOfB[1][b3]).a;
      if (i <= j) {
        bool = false;
      } else {
        bool = true;
      } 
      linkedList.add(new a(bool, b3));
      arrayOfBoolean2[b3][bool] = true;
    } 
    b3 = 2;
    while (!linkedList.isEmpty()) {
      a a = linkedList.removeFirst();
      int i = a.a;
      int j = a.b;
      arrayOfBoolean2[j][i] = false;
      for (c c = (arrayOfB[i][j]).b; c != null; c = c.b) {
        for (State state : ((ArrayList<LinkedList>)arrayList.get(c.a.d)).get(j)) {
          if (!arrayOfBoolean3[state.d]) {
            arrayOfBoolean3[state.d] = true;
            arrayList2.add(state);
            int k = arrayOfInt[state.d];
            ((ArrayList<State>)arrayList4.get(k)).add(state);
            if (!arrayOfBoolean4[k]) {
              arrayOfBoolean4[k] = true;
              arrayList3.add(Integer.valueOf(k));
            } 
          } 
        } 
      } 
      for (Iterator<Integer> iterator = arrayList3.iterator(); iterator.hasNext(); ) {
        int k = ((Integer)iterator.next()).intValue();
        if (((ArrayList)arrayList4.get(k)).size() < ((LinkedList)arrayList1.get(k)).size()) {
          LinkedList linkedList1 = arrayList1.get(k);
          LinkedList<State> linkedList2 = arrayList1.get(b3);
          for (State state : arrayList4.get(k)) {
            linkedList1.remove(state);
            linkedList2.add(state);
            arrayOfInt[state.d] = b3;
            for (byte b5 = 0; b5 < arrayOfChar.length; b5++) {
              c c1 = arrayOfC[state.d][b5];
              if (c1 != null && c1.d == arrayOfB[k][b5]) {
                c1.a();
                arrayOfC[state.d][b5] = arrayOfB[b3][b5].a(state);
              } 
            } 
          } 
          for (byte b = 0; b < arrayOfChar.length; b++) {
            int m = (arrayOfB[k][b]).a;
            int n = (arrayOfB[b3][b]).a;
            if (!arrayOfBoolean2[b][k] && 0 < m && m <= n) {
              arrayOfBoolean2[b][k] = true;
              linkedList.add(new a(k, b));
            } else {
              arrayOfBoolean2[b][b3] = true;
              linkedList.add(new a(b3, b));
            } 
          } 
          b3++;
        } 
        for (State state : arrayList4.get(k))
          arrayOfBoolean3[state.d] = false; 
        arrayOfBoolean4[k] = false;
        ((ArrayList)arrayList4.get(k)).clear();
      } 
      arrayList2.clear();
      arrayList3.clear();
    } 
    State[] arrayOfState2 = new State[b3];
    byte b4;
    for (b4 = 0; b4 < arrayOfState2.length; b4++) {
      State state = new State();
      arrayOfState2[b4] = state;
      for (State state1 : arrayList1.get(b4)) {
        if (state1 == paramAutomaton.f)
          paramAutomaton.f = state; 
        state.b = state1.b;
        state.d = state1.d;
        state1.d = b4;
      } 
    } 
    for (b4 = 0; b4 < arrayOfState2.length; b4++) {
      State state = arrayOfState2[b4];
      state.b = (arrayOfState1[state.d]).b;
      for (Transition transition : (arrayOfState1[state.d]).c)
        state.c.add(new Transition(transition.b, transition.c, arrayOfState2[transition.d.d])); 
    } 
    paramAutomaton.p();
  }
}
