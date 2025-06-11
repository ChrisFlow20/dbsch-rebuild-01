package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public final class ShuffleOperations {
  public static Automaton a(Automaton paramAutomaton1, Automaton paramAutomaton2) {
    paramAutomaton1.H();
    paramAutomaton2.H();
    Transition[][] arrayOfTransition1 = Automaton.b(paramAutomaton1.i());
    Transition[][] arrayOfTransition2 = Automaton.b(paramAutomaton2.i());
    Automaton automaton = new Automaton();
    LinkedList<StatePair> linkedList = new LinkedList();
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
    State state = new State();
    automaton.f = state;
    StatePair statePair = new StatePair(state, paramAutomaton1.f, paramAutomaton2.f);
    linkedList.add(statePair);
    linkedHashMap.put(statePair, statePair);
    while (linkedList.size() > 0) {
      statePair = linkedList.removeFirst();
      statePair.a.b = (statePair.b.b && statePair.c.b);
      Transition[] arrayOfTransition3 = arrayOfTransition1[statePair.b.d];
      for (byte b1 = 0; b1 < arrayOfTransition3.length; b1++) {
        StatePair statePair1 = new StatePair((arrayOfTransition3[b1]).d, statePair.c);
        StatePair statePair2 = (StatePair)linkedHashMap.get(statePair1);
        if (statePair2 == null) {
          statePair1.a = new State();
          linkedList.add(statePair1);
          linkedHashMap.put(statePair1, statePair1);
          statePair2 = statePair1;
        } 
        statePair.a.c.add(new Transition((arrayOfTransition3[b1]).b, (arrayOfTransition3[b1]).c, statePair2.a));
      } 
      Transition[] arrayOfTransition4 = arrayOfTransition2[statePair.c.d];
      for (byte b2 = 0; b2 < arrayOfTransition4.length; b2++) {
        StatePair statePair1 = new StatePair(statePair.b, (arrayOfTransition4[b2]).d);
        StatePair statePair2 = (StatePair)linkedHashMap.get(statePair1);
        if (statePair2 == null) {
          statePair1.a = new State();
          linkedList.add(statePair1);
          linkedHashMap.put(statePair1, statePair1);
          statePair2 = statePair1;
        } 
        statePair.a.c.add(new Transition((arrayOfTransition4[b2]).b, (arrayOfTransition4[b2]).c, statePair2.a));
      } 
    } 
    automaton.g = false;
    automaton.p();
    automaton.c();
    return automaton;
  }
  
  public static String a(Collection<Automaton> paramCollection, Automaton paramAutomaton, Character paramCharacter1, Character paramCharacter2) {
    if (paramCollection.size() == 0)
      return null; 
    if (paramCollection.size() == 1) {
      Automaton automaton = paramCollection.iterator().next();
      if (automaton.d()) {
        if (paramAutomaton.h(automaton.j))
          return null; 
        return automaton.j;
      } 
      if (automaton == paramAutomaton)
        return null; 
    } 
    paramAutomaton.H();
    Transition[][][] arrayOfTransition = new Transition[paramCollection.size()][][];
    byte b = 0;
    for (Automaton automaton : paramCollection)
      arrayOfTransition[b++] = Automaton.b(automaton.i()); 
    Transition[][] arrayOfTransition1 = Automaton.b(paramAutomaton.i());
    g g = new g(false);
    e e = new e(paramCollection, paramAutomaton);
    LinkedList<e> linkedList = new LinkedList();
    TreeSet<e> treeSet = new TreeSet();
    linkedList.add(e);
    treeSet.add(e);
    while (!linkedList.isEmpty()) {
      e e1 = linkedList.removeFirst();
      boolean bool = true;
      for (byte b1 = 0; b1 < paramCollection.size(); b1++) {
        if (!(e1.b[b1]).b) {
          bool = false;
          break;
        } 
      } 
      if (e1.c.b)
        bool = false; 
      if (bool) {
        StringBuilder stringBuilder1 = new StringBuilder();
        while (e1.a != null) {
          stringBuilder1.append(e1.d);
          e1 = e1.a;
        } 
        StringBuilder stringBuilder2 = new StringBuilder();
        for (int j = stringBuilder1.length() - 1; j >= 0; j--)
          stringBuilder2.append(stringBuilder1.charAt(j)); 
        return stringBuilder2.toString();
      } 
      Transition[] arrayOfTransition2 = arrayOfTransition1[e1.c.d];
      for (int i = 0; i < paramCollection.size(); i++) {
        if (e1.f)
          i = e1.h; 
        Transition[] arrayOfTransition3;
        int j;
        byte b2;
        for (arrayOfTransition3 = arrayOfTransition[i][(e1.b[i]).d], j = arrayOfTransition3.length, b2 = 0; b2 < j; ) {
          Transition transition = arrayOfTransition3[b2];
          ArrayList<Transition> arrayList = new ArrayList();
          int k = Arrays.binarySearch(arrayOfTransition2, transition, g);
          if (k < 0)
            k = -k - 1; 
          if (k > 0 && (arrayOfTransition2[k - 1]).c >= transition.b)
            k--; 
          while (k < arrayOfTransition2.length) {
            Transition transition1 = arrayOfTransition2[k++];
            char c1 = transition.b;
            char c2 = transition.c;
            if (transition1.b > c1)
              c1 = transition1.b; 
            if (transition1.c < c2)
              c2 = transition1.c; 
            if (c1 <= c2) {
              a(paramCharacter1, paramCharacter2, linkedList, treeSet, e1, i, transition, transition1, c1, c2);
              arrayList.add(new Transition(c1, c2, null));
            } 
          } 
          Transition[] arrayOfTransition4 = arrayList.<Transition>toArray(new Transition[arrayList.size()]);
          Arrays.sort(arrayOfTransition4, g);
          char c = transition.b;
          for (byte b3 = 0; b3 < arrayOfTransition4.length && 
            (arrayOfTransition4[b3]).b <= c; b3++) {
            if ((arrayOfTransition4[b3]).c >= transition.c) {
              b2++;
              continue;
            } 
            c = (char)((arrayOfTransition4[b3]).c + 1);
          } 
          e e2 = new e(e1, i, transition.d, c);
          StringBuilder stringBuilder1 = new StringBuilder();
          e e3 = e2;
          while (e3.a != null) {
            stringBuilder1.append(e3.d);
            e3 = e3.a;
          } 
          StringBuilder stringBuilder2 = new StringBuilder();
          for (int m = stringBuilder1.length() - 1; m >= 0; m--)
            stringBuilder2.append(stringBuilder1.charAt(m)); 
          if (e1.f)
            stringBuilder2.append(BasicOperations.a(e2.b[e1.h], true)); 
          for (i = 0; i < paramCollection.size(); i++) {
            if (!e1.f || i != e1.h)
              stringBuilder2.append(BasicOperations.a(e2.b[i], true)); 
          } 
          return stringBuilder2.toString();
        } 
        if (e1.f)
          break; 
      } 
    } 
    return null;
  }
  
  private static void a(Character paramCharacter1, Character paramCharacter2, LinkedList<e> paramLinkedList, Set<e> paramSet, e parame, int paramInt, Transition paramTransition1, Transition paramTransition2, char paramChar1, char paramChar2) {
    char c1 = '?';
    char c2 = '?';
    if (paramCharacter1 != null && paramChar1 <= paramCharacter1.charValue() && paramCharacter1.charValue() <= paramChar2 && paramChar1 != paramChar2) {
      if (paramChar1 < paramCharacter1.charValue())
        a(paramCharacter1, paramCharacter2, paramLinkedList, paramSet, parame, paramInt, paramTransition1, paramTransition2, paramChar1, (char)(paramCharacter1.charValue() - 1)); 
      a(paramCharacter1, paramCharacter2, paramLinkedList, paramSet, parame, paramInt, paramTransition1, paramTransition2, paramCharacter1.charValue(), paramCharacter1.charValue());
      if (paramCharacter1.charValue() < paramChar2)
        a(paramCharacter1, paramCharacter2, paramLinkedList, paramSet, parame, paramInt, paramTransition1, paramTransition2, (char)(paramCharacter1.charValue() + 1), paramChar2); 
    } else if (paramCharacter2 != null && paramChar1 <= paramCharacter2.charValue() && paramCharacter2.charValue() <= paramChar2 && paramChar1 != paramChar2) {
      if (paramChar1 < paramCharacter2.charValue())
        a(paramCharacter1, paramCharacter2, paramLinkedList, paramSet, parame, paramInt, paramTransition1, paramTransition2, paramChar1, (char)(paramCharacter2.charValue() - 1)); 
      a(paramCharacter1, paramCharacter2, paramLinkedList, paramSet, parame, paramInt, paramTransition1, paramTransition2, paramCharacter2.charValue(), paramCharacter2.charValue());
      if (paramCharacter2.charValue() < paramChar2)
        a(paramCharacter1, paramCharacter2, paramLinkedList, paramSet, parame, paramInt, paramTransition1, paramTransition2, (char)(paramCharacter2.charValue() + 1), paramChar2); 
    } else if (paramChar1 < '?' && paramChar2 >= '?') {
      a(paramCharacter1, paramCharacter2, paramLinkedList, paramSet, parame, paramInt, paramTransition1, paramTransition2, paramChar1, '퟿');
      a(paramCharacter1, paramCharacter2, paramLinkedList, paramSet, parame, paramInt, paramTransition1, paramTransition2, '?', paramChar2);
    } else if (paramChar1 <= '?' && paramChar2 > '?') {
      a(paramCharacter1, paramCharacter2, paramLinkedList, paramSet, parame, paramInt, paramTransition1, paramTransition2, paramChar1, '?');
      a(paramCharacter1, paramCharacter2, paramLinkedList, paramSet, parame, paramInt, paramTransition1, paramTransition2, '?', paramChar2);
    } else {
      e e1 = new e(parame, paramInt, paramTransition1.d, paramTransition2.d, paramChar1);
      if (paramCharacter1 != null && paramChar1 == paramCharacter1.charValue()) {
        e1.f = true;
        e1.h = paramInt;
      } else if (paramCharacter2 != null && paramChar1 == paramCharacter2.charValue()) {
        e1.f = false;
      } 
      if (paramChar1 >= '?' && paramChar1 <= '?') {
        e1.f = true;
        e1.h = paramInt;
        e1.g = true;
      } 
      if (!paramSet.contains(e1)) {
        paramLinkedList.add(e1);
        paramSet.add(e1);
      } 
    } 
  }
}
