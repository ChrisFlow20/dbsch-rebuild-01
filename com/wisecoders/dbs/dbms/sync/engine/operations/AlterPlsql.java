package com.wisecoders.dbs.dbms.sync.engine.operations;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsDef;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Rule;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.sys.SqlUtil;

public class AlterPlsql extends Alter {
  private String c() {
    return this.a.dropFunction.c_();
  }
  
  private String d() {
    return this.a.dropProcedure.c_();
  }
  
  private String e() {
    return this.a.dropTrigger.c_();
  }
  
  private String f() {
    return this.a.dropRule.c_();
  }
  
  private String g() {
    return this.a.functionComment.c_();
  }
  
  private String h() {
    return this.a.functionChangeComment.c_();
  }
  
  private String i() {
    return this.a.functionDropComment.c_();
  }
  
  private String j() {
    return this.a.procedureComment.c_();
  }
  
  private String k() {
    return this.a.procedureChangeComment.c_();
  }
  
  private String l() {
    return this.a.procedureDropComment.c_();
  }
  
  private String m() {
    return this.a.triggerComment.c_();
  }
  
  private String n() {
    return this.a.triggerChangeComment.c_();
  }
  
  private String o() {
    return this.a.triggerDropComment.c_();
  }
  
  private String p() {
    return this.a.ruleComment.c_();
  }
  
  private String q() {
    return this.a.ruleChangeComment.c_();
  }
  
  private String r() {
    return this.a.ruleDropComment.c_();
  }
  
  public AlterPlsql(DbmsDef paramDbmsDef) {
    super(paramDbmsDef);
  }
  
  public boolean b() {
    return (c() != null);
  }
  
  public boolean a(Sql paramSql) {
    if (paramSql instanceof Procedure && j() != null)
      return true; 
    if (paramSql instanceof Function && g() != null)
      return true; 
    if (paramSql instanceof Rule && p() != null)
      return true; 
    return (paramSql instanceof Trigger && m() != null);
  }
  
  public AlterScript a(SyncPair paramSyncPair, Sql paramSql) {
    // Byte code:
    //   0: new com/wisecoders/dbs/dbms/sync/engine/diffs/AlterScript
    //   3: dup
    //   4: aload_0
    //   5: getfield a : Lcom/wisecoders/dbs/dbms/DbmsDef;
    //   8: getfield dbId : Ljava/lang/String;
    //   11: invokespecial <init> : (Ljava/lang/String;)V
    //   14: astore_3
    //   15: aload_2
    //   16: instanceof com/wisecoders/dbs/schema/Trigger
    //   19: ifeq -> 36
    //   22: aload_2
    //   23: checkcast com/wisecoders/dbs/schema/Trigger
    //   26: astore #6
    //   28: aload #6
    //   30: getfield schema : Lcom/wisecoders/dbs/schema/Schema;
    //   33: goto -> 58
    //   36: aload_2
    //   37: instanceof com/wisecoders/dbs/schema/AbstractFunction
    //   40: ifeq -> 57
    //   43: aload_2
    //   44: checkcast com/wisecoders/dbs/schema/AbstractFunction
    //   47: astore #5
    //   49: aload #5
    //   51: getfield schema : Lcom/wisecoders/dbs/schema/Schema;
    //   54: goto -> 58
    //   57: aconst_null
    //   58: astore #4
    //   60: new com/wisecoders/dbs/dbms/sync/engine/diffs/AlterStatement
    //   63: dup
    //   64: aload_1
    //   65: aload_2
    //   66: invokevirtual getText : ()Ljava/lang/String;
    //   69: iconst_1
    //   70: iconst_1
    //   71: invokespecial <init> : (Lcom/wisecoders/dbs/dbms/sync/engine/nodes/SyncPair;Ljava/lang/String;ZZ)V
    //   74: getstatic com/wisecoders/dbs/dbms/sync/engine/operations/K.a : Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;
    //   77: aload_0
    //   78: getfield a : Lcom/wisecoders/dbs/dbms/DbmsDef;
    //   81: aload_2
    //   82: invokevirtual getName : ()Ljava/lang/String;
    //   85: invokevirtual quote : (Ljava/lang/String;)Ljava/lang/String;
    //   88: invokevirtual set : (Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;Ljava/lang/Object;)Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterStatement;
    //   91: getstatic com/wisecoders/dbs/dbms/sync/engine/operations/K.C : Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;
    //   94: aload_2
    //   95: invokevirtual ref : ()Ljava/lang/String;
    //   98: invokevirtual set : (Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;Ljava/lang/Object;)Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterStatement;
    //   101: getstatic com/wisecoders/dbs/dbms/sync/engine/operations/K.D : Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;
    //   104: aload_2
    //   105: instanceof com/wisecoders/dbs/schema/Trigger
    //   108: ifeq -> 151
    //   111: aload_2
    //   112: checkcast com/wisecoders/dbs/schema/Trigger
    //   115: astore #6
    //   117: aload_0
    //   118: getfield a : Lcom/wisecoders/dbs/dbms/DbmsDef;
    //   121: getfield triggerSkipCatalogName : Lcom/wisecoders/dbs/config/model/BooleanProperty;
    //   124: invokevirtual b : ()Z
    //   127: ifeq -> 151
    //   130: aload #6
    //   132: getfield schema : Lcom/wisecoders/dbs/schema/Schema;
    //   135: invokestatic quote : (Lcom/wisecoders/dbs/schema/DbUnit;)Ljava/lang/String;
    //   138: aload #6
    //   140: invokestatic quote : (Lcom/wisecoders/dbs/schema/DbUnit;)Ljava/lang/String;
    //   143: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   148: goto -> 155
    //   151: aload_2
    //   152: invokevirtual ref : ()Ljava/lang/String;
    //   155: invokevirtual set : (Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;Ljava/lang/Object;)Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterStatement;
    //   158: getstatic com/wisecoders/dbs/dbms/sync/engine/operations/K.l : Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;
    //   161: aload #4
    //   163: invokevirtual ref : ()Ljava/lang/String;
    //   166: invokevirtual set : (Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;Ljava/lang/Object;)Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterStatement;
    //   169: getstatic com/wisecoders/dbs/dbms/sync/engine/operations/K.m : Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;
    //   172: aload #4
    //   174: invokestatic quote : (Lcom/wisecoders/dbs/schema/DbUnit;)Ljava/lang/String;
    //   177: invokevirtual set : (Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;Ljava/lang/Object;)Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterStatement;
    //   180: astore #5
    //   182: aload_2
    //   183: instanceof com/wisecoders/dbs/schema/Procedure
    //   186: ifeq -> 232
    //   189: aload #5
    //   191: getstatic com/wisecoders/dbs/dbms/sync/engine/operations/K.H : Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;
    //   194: aload_0
    //   195: getfield a : Lcom/wisecoders/dbs/dbms/DbmsDef;
    //   198: aload_2
    //   199: invokevirtual getName : ()Ljava/lang/String;
    //   202: invokevirtual quote : (Ljava/lang/String;)Ljava/lang/String;
    //   205: invokevirtual set : (Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;Ljava/lang/Object;)Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterStatement;
    //   208: pop
    //   209: aload #5
    //   211: getstatic com/wisecoders/dbs/dbms/sync/engine/operations/K.I : Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;
    //   214: aload_0
    //   215: getfield a : Lcom/wisecoders/dbs/dbms/DbmsDef;
    //   218: aload_2
    //   219: invokevirtual getName : ()Ljava/lang/String;
    //   222: invokevirtual quote : (Ljava/lang/String;)Ljava/lang/String;
    //   225: invokevirtual set : (Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;Ljava/lang/Object;)Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterStatement;
    //   228: pop
    //   229: goto -> 375
    //   232: aload_2
    //   233: instanceof com/wisecoders/dbs/schema/Function
    //   236: ifeq -> 282
    //   239: aload #5
    //   241: getstatic com/wisecoders/dbs/dbms/sync/engine/operations/K.H : Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;
    //   244: aload_0
    //   245: getfield a : Lcom/wisecoders/dbs/dbms/DbmsDef;
    //   248: aload_2
    //   249: invokevirtual getName : ()Ljava/lang/String;
    //   252: invokevirtual quote : (Ljava/lang/String;)Ljava/lang/String;
    //   255: invokevirtual set : (Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;Ljava/lang/Object;)Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterStatement;
    //   258: pop
    //   259: aload #5
    //   261: getstatic com/wisecoders/dbs/dbms/sync/engine/operations/K.I : Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;
    //   264: aload_0
    //   265: getfield a : Lcom/wisecoders/dbs/dbms/DbmsDef;
    //   268: aload_2
    //   269: invokevirtual getName : ()Ljava/lang/String;
    //   272: invokevirtual quote : (Ljava/lang/String;)Ljava/lang/String;
    //   275: invokevirtual set : (Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;Ljava/lang/Object;)Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterStatement;
    //   278: pop
    //   279: goto -> 375
    //   282: aload_2
    //   283: instanceof com/wisecoders/dbs/schema/Rule
    //   286: ifeq -> 312
    //   289: aload #5
    //   291: getstatic com/wisecoders/dbs/dbms/sync/engine/operations/K.K : Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;
    //   294: aload_0
    //   295: getfield a : Lcom/wisecoders/dbs/dbms/DbmsDef;
    //   298: aload_2
    //   299: invokevirtual getName : ()Ljava/lang/String;
    //   302: invokevirtual quote : (Ljava/lang/String;)Ljava/lang/String;
    //   305: invokevirtual set : (Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;Ljava/lang/Object;)Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterStatement;
    //   308: pop
    //   309: goto -> 375
    //   312: aload_2
    //   313: instanceof com/wisecoders/dbs/schema/Trigger
    //   316: ifeq -> 375
    //   319: aload_2
    //   320: checkcast com/wisecoders/dbs/schema/Trigger
    //   323: astore #6
    //   325: aload #5
    //   327: getstatic com/wisecoders/dbs/dbms/sync/engine/operations/K.J : Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;
    //   330: aload_0
    //   331: getfield a : Lcom/wisecoders/dbs/dbms/DbmsDef;
    //   334: aload_2
    //   335: invokevirtual getName : ()Ljava/lang/String;
    //   338: invokevirtual quote : (Ljava/lang/String;)Ljava/lang/String;
    //   341: invokevirtual set : (Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;Ljava/lang/Object;)Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterStatement;
    //   344: pop
    //   345: aload #5
    //   347: getstatic com/wisecoders/dbs/dbms/sync/engine/operations/K.p : Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;
    //   350: aload #6
    //   352: invokevirtual getOwningTable : ()Lcom/wisecoders/dbs/schema/Table;
    //   355: ifnull -> 369
    //   358: aload #6
    //   360: invokevirtual getOwningTable : ()Lcom/wisecoders/dbs/schema/Table;
    //   363: invokevirtual getName : ()Ljava/lang/String;
    //   366: goto -> 371
    //   369: ldc ''
    //   371: invokevirtual set : (Lcom/wisecoders/dbs/dbms/sync/engine/operations/K;Ljava/lang/Object;)Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterStatement;
    //   374: pop
    //   375: aload_3
    //   376: aload #5
    //   378: invokevirtual add : (Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterStatement;)V
    //   381: aload_2
    //   382: invokevirtual getComment : ()Ljava/lang/String;
    //   385: ifnull -> 407
    //   388: aload_0
    //   389: aload_2
    //   390: invokevirtual a : (Lcom/wisecoders/dbs/schema/Sql;)Z
    //   393: ifeq -> 407
    //   396: aload_3
    //   397: aload_0
    //   398: aload_1
    //   399: aconst_null
    //   400: aload_2
    //   401: invokevirtual a : (Lcom/wisecoders/dbs/dbms/sync/engine/nodes/SyncPair;Lcom/wisecoders/dbs/schema/Sql;Lcom/wisecoders/dbs/schema/Sql;)Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterScript;
    //   404: invokevirtual addAll : (Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterScript;)V
    //   407: aload_3
    //   408: aload_2
    //   409: invokevirtual getSyncPriority : ()I
    //   412: iconst_m1
    //   413: if_icmpeq -> 423
    //   416: aload_2
    //   417: invokevirtual getSyncPriority : ()I
    //   420: goto -> 426
    //   423: sipush #9000
    //   426: invokevirtual setSortOrder : (I)Lcom/wisecoders/dbs/dbms/sync/engine/diffs/AlterScript;
    //   429: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #49	-> 0
    //   #50	-> 15
    //   #51	-> 60
    //   #52	-> 82
    //   #53	-> 95
    //   #55	-> 104
    //   #56	-> 163
    //   #57	-> 174
    //   #58	-> 182
    //   #59	-> 189
    //   #60	-> 209
    //   #61	-> 232
    //   #62	-> 239
    //   #63	-> 259
    //   #64	-> 282
    //   #65	-> 289
    //   #66	-> 312
    //   #67	-> 325
    //   #68	-> 345
    //   #70	-> 375
    //   #72	-> 381
    //   #73	-> 396
    //   #75	-> 407
  }
  
  public AlterScript b(SyncPair paramSyncPair, Sql paramSql) {
    Dbms dbms = Dbms.get(this.a.dbId);
    if (paramSql instanceof Procedure) {
      Procedure procedure = (Procedure)paramSql;
      return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
            d()))
          .set(K.l, procedure.schema.ref())
          .set(K.m, this.a.quote(procedure.schema.getName()))
          .set(K.W, dbms.procedureAlias.c_())
          .set(K.a, this.a.quote(paramSql.getName()))
          .set(K.D, paramSql.ref())
          .set(K.H, this.a.quote(paramSql.getName()))
          .set(K.I, this.a.quote(paramSql.getName()))
          .set(K.j, paramSql.ref())
          .set(K.ag, procedure.listInputParameterTypes())
          .set(K.ah, procedure.listInputParameterTypesInOut())))
        .setSortOrder(0);
    } 
    if (paramSql instanceof Function) {
      Function function = (Function)paramSql;
      return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
            c()))
          .set(K.l, function.schema.ref())
          .set(K.m, this.a.quote(function.schema.getName()))
          .set(K.I, this.a.quote(function.getName()))
          .set(K.H, this.a.quote(function.getName()))
          .set(K.a, this.a.quote(paramSql.getName()))
          .set(K.D, paramSql.ref())
          .set(K.W, dbms.functionAlias.c_())
          .set(K.j, paramSql.ref())
          .set(K.ag, function.listInputParameterTypes())
          .set(K.ah, function.listInputParameterTypesInOut())))
        .setSortOrder(0);
    } 
    if (paramSql instanceof Rule) {
      Rule rule = (Rule)paramSql;
      return new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
            f()))
          .set(K.l, rule.schema.ref())
          .set(K.m, this.a.quote(rule.schema.getName()))
          .set(K.K, this.a.quote(rule.getName()))
          .set(K.o, (rule.getOwningTable() != null) ? rule.getOwningTable().ref() : "")
          .set(K.p, (rule.getOwningTable() != null) ? rule.getOwningTable().getName() : "")
          .set(K.a, this.a.quote(paramSql.getName()))
          .set(K.D, paramSql.ref())
          .set(K.W, dbms.ruleAlias.c_())
          .set(K.j, paramSql.ref())
          .setSortOrder(0));
    } 
    if (paramSql instanceof Trigger) {
      Trigger trigger = (Trigger)paramSql;
      return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, 
            e()))
          .set(K.l, trigger.schema.ref())
          .set(K.m, this.a.quote(trigger.schema.getName()))
          .set(K.J, this.a.quote(trigger.getName()))
          .set(K.p, (trigger.getOwningTable() != null) ? trigger.getOwningTable().getName() : "")
          .set(K.a, this.a.quote(paramSql.getName()))
          .set(K.D, paramSql.ref())
          .set(K.W, dbms.triggerAlias.c_())
          .set(K.j, paramSql.ref())
          .set(K.o, (trigger.getOwningTable() != null) ? trigger.getOwningTable().ref() : "")))
        .setSortOrder(0);
    } 
    return null;
  }
  
  public AlterScript a(SyncPair paramSyncPair, Sql paramSql1, Sql paramSql2) {
    if (paramSql2 instanceof Procedure) {
      Procedure procedure = (Procedure)paramSql2;
      String str = j();
      if (paramSql1 != null && paramSql1.getComment() != null)
        str = (paramSql2.getComment() != null) ? k() : l(); 
      return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, str))
          
          .set(K.n, procedure.getSchema().getCatalogName(paramSyncPair.getMapping()))
          .set(K.l, procedure.schema.ref())
          .set(K.m, this.a.quote(procedure.schema.getName()))
          .set(K.W, (Dbms.get(this.a.dbId)).procedureAlias.c_())
          .set(K.H, this.a.quote(paramSql2.getName()))
          .set(K.I, this.a.quote(paramSql2.getName()))
          .set(K.a, this.a.quote(paramSql2.getName()))
          .set(K.D, paramSql2.ref())
          .set(K.j, paramSql2.ref())
          .set(K.d, SqlUtil.escapeCommentQuotes(paramSql2.getComment()))
          .set(K.e, Dbms.get(paramSql2.getDbId()).escapeString(paramSql2.getComment()))
          .set(K.ag, procedure.listInputParameterTypes())
          .set(K.ah, procedure.listInputParameterTypesInOut())))
        .setSortOrder(a());
    } 
    if (paramSql2 instanceof Function) {
      Function function = (Function)paramSql2;
      String str = g();
      if (paramSql1 != null && paramSql1.getComment() != null)
        str = (paramSql2.getComment() != null) ? h() : i(); 
      return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, str))
          
          .set(K.n, function.getSchema().getCatalogName(paramSyncPair.getMapping()))
          .set(K.l, function.schema.ref())
          .set(K.m, this.a.quote(function.schema.getName()))
          .set(K.I, this.a.quote(paramSql2.getName()))
          .set(K.H, this.a.quote(paramSql2.getName()))
          .set(K.a, this.a.quote(paramSql2.getName()))
          .set(K.D, paramSql2.ref())
          .set(K.W, (Dbms.get(this.a.dbId)).functionAlias.c_())
          .set(K.j, paramSql2.ref())
          .set(K.d, SqlUtil.escapeCommentQuotes(paramSql2.getComment()))
          .set(K.e, Dbms.get(paramSql2.getDbId()).escapeString(paramSql2.getComment()))
          .set(K.ag, function.listInputParameterTypes())
          .set(K.ah, function.listInputParameterTypesInOut())))
        .setSortOrder(a());
    } 
    if (paramSql2 instanceof Rule) {
      Rule rule = (Rule)paramSql2;
      String str = p();
      if (paramSql1 != null && paramSql1.getComment() != null)
        str = (paramSql2.getComment() != null) ? q() : r(); 
      return new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, str))
          
          .set(K.n, rule.getSchema().getCatalogName(paramSyncPair.getMapping()))
          .set(K.l, rule.schema.ref())
          .set(K.m, this.a.quote(rule.schema.getName()))
          .set(K.K, this.a.quote(paramSql2.getName()))
          .set(K.p, (rule.getOwningTable() != null) ? rule.getOwningTable().getName() : "")
          .set(K.a, this.a.quote(paramSql2.getName()))
          .set(K.D, paramSql2.ref())
          .set(K.W, (Dbms.get(this.a.dbId)).ruleAlias.c_())
          .set(K.j, paramSql2.ref())
          .set(K.d, SqlUtil.escapeCommentQuotes(paramSql2.getComment()))
          .set(K.e, Dbms.get(paramSql2.getDbId()).escapeString(paramSql2.getComment()))
          .setSortOrder(a()));
    } 
    if (paramSql2 instanceof Trigger) {
      Trigger trigger = (Trigger)paramSql2;
      String str = m();
      if (paramSql1 != null && paramSql1.getComment() != null)
        str = (paramSql2.getComment() != null) ? n() : o(); 
      return (new AlterScript(this.a.dbId, (new AlterStatement(paramSyncPair, str))
          
          .set(K.n, trigger.getSchema().getCatalogName(paramSyncPair.getMapping()))
          .set(K.l, trigger.schema.ref())
          .set(K.m, this.a.quote(trigger.schema.getName()))
          .set(K.J, this.a.quote(paramSql2.getName()))
          .set(K.p, (trigger.getOwningTable() != null) ? trigger.getOwningTable().getName() : "")
          .set(K.a, this.a.quote(paramSql2.getName()))
          .set(K.D, paramSql2.ref())
          .set(K.W, (Dbms.get(this.a.dbId)).triggerAlias.c_())
          .set(K.j, paramSql2.ref())
          .set(K.d, SqlUtil.escapeCommentQuotes(paramSql2.getComment()))
          .set(K.e, Dbms.get(paramSql2.getDbId()).escapeString(paramSql2.getComment()))
          .set(K.o, (trigger.getOwningTable() != null) ? trigger.getOwningTable().ref() : "")))
        .setSortOrder(a());
    } 
    return null;
  }
}
