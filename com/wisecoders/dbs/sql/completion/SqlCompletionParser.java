package com.wisecoders.dbs.sql.completion;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.editors.text.Token;
import com.wisecoders.dbs.editors.text.TokenType;
import com.wisecoders.dbs.editors.text.lexers.StatementLexer;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sql.parser.MatcherCommaPhrase;
import com.wisecoders.dbs.sql.parser.PatternPhrase;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlCompletionParser {
  public final Project a;
  
  private final MatcherCommaPhrase c = new MatcherCommaPhrase();
  
  private String d;
  
  private String e;
  
  private SqlCompletionParser$Section f = SqlCompletionParser$Section.a;
  
  private final Map g = new HashMap<>();
  
  public static final int b = 100;
  
  public int a() {
    return (this.e != null) ? this.e.length() : 0;
  }
  
  public SqlCompletionParser(Project paramProject) {
    this.a = paramProject;
  }
  
  public void a(String paramString, int paramInt) {
    b();
    StringReader stringReader = new StringReader(paramString);
    SqlCompletionParser$Section sqlCompletionParser$Section = SqlCompletionParser$Section.b;
    int i = 0;
    try {
      StatementLexer statementLexer = new StatementLexer(stringReader);
      this.c.clear();
      Token token;
      while ((token = statementLexer.b()) != null) {
        String str = paramString.substring(token.b, token.b + token.c);
        int j = token.b - i;
        this.c.a(str, token.b, token.c, token.a, paramString.substring(i, token.b));
        if (paramInt >= token.b && paramInt <= token.b + token.c) {
          this.e = str.substring(0, paramInt - token.b);
          if (".".equals(this.e)) {
            this.e = "";
            this.d = (j == 0 && this.c.c(-1) != null) ? this.c.c(-1).c() : null;
          } else if (token.a != TokenType.e) {
            this.e = "";
          } else if (j == 0 && this.c.c(-2) != null && ".".equals(this.c.c(-1).c())) {
            this.d = this.c.c(-2).c();
          } 
        } 
        if (paramInt >= i && paramInt <= token.b + token.c)
          this.f = sqlCompletionParser$Section; 
        sqlCompletionParser$Section = a(str, sqlCompletionParser$Section);
        i = token.b + token.c;
      } 
      if (paramInt >= i)
        this.f = sqlCompletionParser$Section; 
    } catch (IOException iOException) {
      Log.a("Completion error", iOException);
    } 
    c();
    stringReader.close();
  }
  
  public void b() {
    this.c.f();
    this.g.clear();
    this.e = null;
    this.d = null;
    this.f = SqlCompletionParser$Section.a;
  }
  
  public void a(List<SqlCompletionSuggestion> paramList, boolean paramBoolean) {
    if (paramBoolean && StringUtil.isEmptyTrim(this.d) && StringUtil.isEmptyTrim(this.e))
      return; 
    if (StringUtil.isFilledTrim(this.d)) {
      if (this.g.containsKey(this.d.toLowerCase()))
        a(((AbstractTable)this.g.get(this.d.toLowerCase())).getAttributes(), this.e, (String)null, paramList); 
      for (Schema schema : this.a.schemas) {
        if (schema.getName().equalsIgnoreCase(this.d)) {
          a(schema.tables, this.e, (String)null, paramList);
          a(schema.views, this.e, (String)null, paramList);
        } 
      } 
      for (AbstractTable abstractTable : this.a.getEntities()) {
        if (abstractTable.getName().equalsIgnoreCase(this.d) || abstractTable.getNameWithSchemaName().equalsIgnoreCase(this.d))
          a(abstractTable.getAttributes(), this.e, (String)null, paramList); 
      } 
    } else {
      if (StringUtil.isFilledTrim(this.e))
        for (String str : this.g.keySet()) {
          if (str.startsWith(this.e))
            paramList.add(new SqlCompletionSuggestion(null, str)); 
        }  
      if (this.f == SqlCompletionParser$Section.b)
        for (String str : this.g.keySet())
          a(((AbstractTable)this.g.get(str)).getAttributes(), this.e, (this.g.size() > 1) ? str : null, paramList);  
      if (paramList.isEmpty() || StringUtil.isFilledTrim(this.e)) {
        for (Schema schema : this.a.schemas)
          a(schema, this.e, (String)null, paramList); 
        for (AbstractTable abstractTable : this.a.getEntities())
          a(abstractTable, this.e, abstractTable.getSchema().getNameWithCatalog(), paramList); 
      } 
    } 
  }
  
  private void a(Folder paramFolder, String paramString1, String paramString2, List<SqlCompletionSuggestion> paramList) {
    for (AbstractUnit abstractUnit : paramFolder) {
      if ((paramList.size() < 100 && (StringUtil.isEmptyTrim(paramString1) || abstractUnit.getName().toLowerCase().startsWith(paramString1.toLowerCase()))) || (paramString1
        .length() > 2 && abstractUnit.getName().toLowerCase().contains(paramString1.toLowerCase())))
        paramList.add(new SqlCompletionSuggestion(abstractUnit, paramString2)); 
    } 
  }
  
  private void a(AbstractUnit paramAbstractUnit, String paramString1, String paramString2, List<SqlCompletionSuggestion> paramList) {
    if (paramList.size() < 100 && (StringUtil.isEmptyTrim(paramString1) || paramAbstractUnit.getName().toLowerCase().startsWith(paramString1.toLowerCase()) || (paramString1
      .length() > 2 && paramAbstractUnit.getName().toLowerCase().contains(paramString1.toLowerCase()))))
      paramList.add(new SqlCompletionSuggestion(paramAbstractUnit, paramString2)); 
  }
  
  private static final List h = Arrays.asList(new String[] { 
        "ON", ",", "JOIN", "(", ")", "{", "}", "[", "]", "LEFT", 
        "RIGHT", "WHERE", "HAVING", "GROUP", "FROM" });
  
  private void c() {
    for (String str : new String[] { "FROM", "JOIN", ",", "INTO" }) {
      this.c.d(str + " ?.?.? ?", paramPatternPhrase -> a(paramPatternPhrase.f(0), paramPatternPhrase.f(1), paramPatternPhrase.f(2), paramPatternPhrase.f(3)));
      this.c.d(str + " ?.? ?", paramPatternPhrase -> a((String)null, paramPatternPhrase.f(0), paramPatternPhrase.f(1), paramPatternPhrase.f(2)));
      this.c.d(str + " ? ?", paramPatternPhrase -> a((String)null, (String)null, paramPatternPhrase.f(0), paramPatternPhrase.f(1)));
    } 
    for (String str : new String[] { "FROM", "JOIN", ",", "INTO" }) {
      this.c.d(str + " ?.?.?", paramPatternPhrase -> a(paramPatternPhrase.f(0), paramPatternPhrase.f(1), paramPatternPhrase.f(2), paramPatternPhrase.f(2)));
      this.c.d(str + " ?.?", paramPatternPhrase -> a((String)null, paramPatternPhrase.f(0), paramPatternPhrase.f(1), paramPatternPhrase.f(1)));
      this.c.d(str + " ?", paramPatternPhrase -> a((String)null, (String)null, paramPatternPhrase.f(0), paramPatternPhrase.f(0)));
    } 
  }
  
  private Unit a(String paramString1, String paramString2, String paramString3, String paramString4) {
    if (StringUtil.isEmptyTrim(paramString3))
      return null; 
    if (paramString4 != null && h.contains(paramString4.toUpperCase()))
      return null; 
    Schema schema = null;
    if (paramString1 != null || paramString2 != null)
      schema = this.a.getSchema(paramString1, paramString2); 
    AbstractTable abstractTable = null;
    for (Schema schema1 : this.a.schemas) {
      if (schema == null || schema == schema1)
        for (AbstractTable abstractTable1 : schema1.getEntities()) {
          if (StringUtil.areEqualIgnoreCase(paramString3, abstractTable1.getName())) {
            this.g.put((paramString4 != null) ? paramString4.toLowerCase() : abstractTable1.getName().toLowerCase(), abstractTable1);
            abstractTable = abstractTable1;
          } 
        }  
    } 
    return abstractTable;
  }
  
  private SqlCompletionParser$Section a(String paramString, SqlCompletionParser$Section paramSqlCompletionParser$Section) {
    switch (paramString.toLowerCase()) {
      case "merge":
      case "update":
      case "into":
      case "join":
      case "left":
      case "right":
      case "outer":
      case "delete":
      case "from":
      
      case "where":
      case "group":
      case "by":
      case "on":
      case "having":
      case "select":
      
    } 
    return 

      
      paramSqlCompletionParser$Section;
  }
  
  public void a(List<SqlCompletionSuggestion> paramList, Iterable paramIterable) {
    if (!StringUtil.isFilledTrim(this.d) && StringUtil.isFilledTrim(this.e) && paramIterable != null)
      for (String str : paramIterable) {
        if (str.toLowerCase().startsWith(this.e.toLowerCase()))
          paramList.add(new SqlCompletionSuggestion(null, str)); 
      }  
  }
}
