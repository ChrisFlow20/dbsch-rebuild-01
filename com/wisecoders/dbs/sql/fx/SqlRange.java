package com.wisecoders.dbs.sql.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.editors.text.Position;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SqlUtil;

public class SqlRange {
  private static final String c = ".";
  
  private final StyledEditor d;
  
  public Position a = new Position();
  
  public Position b = new Position();
  
  private String e = "", f = "";
  
  private String g;
  
  public SqlRange(StyledEditor paramStyledEditor) {
    this.d = paramStyledEditor;
  }
  
  public boolean a() {
    return this.b.b(this.a);
  }
  
  public String b() {
    return this.e;
  }
  
  public String c() {
    return (this.f != null && this.f.length() == 0) ? null : this.f;
  }
  
  public void d() {
    this.a.a(0, 0);
    this.b.a(0, 0);
    this.e = "";
    this.f = null;
  }
  
  public boolean e() {
    return ((this.e == null || this.e.length() == 0) && (this.f == null || this.f.length() == 0));
  }
  
  public String toString() {
    return "(" + String.valueOf(this.a) + ", " + String.valueOf(this.b) + ") " + ((this.f != null) ? (this.f + ".") : "") + this.e;
  }
  
  public boolean a(SqlRange paramSqlRange) {
    return (a() && paramSqlRange.a() && paramSqlRange.a.c(this.a) && this.b.c(paramSqlRange.b));
  }
  
  public void f() {
    if (this.d.k()) {
      this.a.a(this.d.g());
      this.b.a(this.d.h());
      this.e = this.d.a(this.a, this.b);
    } else {
      d();
    } 
  }
  
  public void g() {
    f();
    if (!a())
      a(this.d.i()); 
  }
  
  public void h() {
    this.a.a(new Position(0, 0));
    int i = this.d.j() - 1;
    this.b.a(new Position(i, this.d.c(i)));
    this.e = this.d.a(this.a, this.b);
  }
  
  public void a(Position paramPosition) {
    String str1 = this.d.b(paramPosition.getLine());
    this.a.setLine(paramPosition.getLine());
    this.b.setLine(paramPosition.getLine());
    this.a.setCharacter(paramPosition.getCharacter());
    this.b.setCharacter(paramPosition.getCharacter());
    while (this.a.getCharacter() > 0 && !SqlUtil.isDelimiter(str1.charAt(this.a.getCharacter() - 1)))
      this.a.setCharacter(this.a.getCharacter() - 1); 
    while (this.b.getCharacter() < str1.length() && !SqlUtil.isDelimiter(str1.charAt(this.b.getCharacter())))
      this.b.setCharacter(this.b.getCharacter() + 1); 
    String str2 = str1.substring(this.a.getCharacter(), this.b.getCharacter());
    int i;
    if ((i = str2.indexOf('.')) > -1) {
      this.e = str2.substring(i + 1).replace(".", "");
      this.f = str2.substring(0, i);
    } else {
      this.e = str2.replace(".", "");
      this.f = null;
    } 
  }
  
  public void i() {
    while (true) {
      if (this.b.getLine() >= this.d.j())
        return; 
      String str = this.d.b(this.b.getLine()).trim();
      if (str.toLowerCase().startsWith("delimiter ")) {
        this.g = str.substring("delimiter ".length()).trim();
      } else if (str.equalsIgnoreCase("GO")) {
        Log.h();
      } else {
        return;
      } 
      a(true);
    } 
  }
  
  public void a(boolean paramBoolean) {
    int i = paramBoolean ? this.b.getLine() : this.a.getLine();
    if (paramBoolean)
      i++; 
    int j = this.d.j();
    while (i < j - 1 && LineType.a(this.d.b(i)).e())
      i++; 
    this.a.a(i, 0);
    this.b.a(i, 0);
    this.e = "";
  }
  
  public SqlRange b(Position paramPosition) {
    this.a.a(paramPosition.getLine(), 0);
    byte b = 0;
    while (b < Sys.B.queryMaximalNumberOfLines.a() && this.a
      .getLine() > 0 && 
      LineType.a(this.d.b(this.a.getLine() - 1)).d()) {
      this.a.setLine(this.a.getLine() - 1);
      b++;
    } 
    j();
    return this;
  }
  
  public void j() {
    int i = this.a.getLine();
    byte b = 0;
    while (i < this.d.j()) {
      String str = this.d.b(i).trim();
      if (++b > Sys.B.queryMaximalNumberOfLines.a() && !str.startsWith("("))
        break; 
      LineType lineType = LineType.a(this.d.b(i));
      if (str.toLowerCase().startsWith("delimiter ")) {
        this.b.a(i, 0);
        break;
      } 
      if (lineType.c() || lineType.b()) {
        this.b.a(i, this.d.c(i));
        break;
      } 
      if (lineType.f())
        break; 
      this.b.a(i, this.d.c(i));
      i++;
    } 
    this.e = this.d.a(this.a, this.b);
    this.f = null;
  }
  
  public void a(Dbms paramDbms, Position paramPosition) {
    String str = null;
    this.a.a(paramPosition.getLine(), 0);
    int i = this.a.getLine();
    boolean bool = false;
    while (i < this.d.j()) {
      String str1 = this.d.b(i);
      String str2 = str1.trim().toLowerCase();
      String str3 = paramDbms.extractDelimiterFromCommand(str2);
      if (str == null && str3 != null)
        str = str3; 
      LineType lineType = LineType.a(this.d.b(i));
      if (lineType.c())
        break; 
      if (str != null && str2.endsWith(str)) {
        str = null;
        if (lineType.b()) {
          this.b.a(i, str2.length());
          break;
        } 
      } else {
        if (this.g != null && str2.endsWith(this.g)) {
          this.b.a(i, str1.lastIndexOf(this.g));
          break;
        } 
        if (lineType == LineType.e && bool)
          break; 
      } 
      bool = (lineType == LineType.a || (bool && lineType == LineType.f)) ? true : false;
      this.b.a(i, str1.length());
      i++;
    } 
    this.e = this.d.a(this.a, this.b);
    this.f = null;
  }
  
  public AbstractUnit a(Project paramProject, Position paramPosition) {
    a(paramPosition);
    if (!a())
      return null; 
    String str1 = c();
    String str2 = b();
    b(this.a);
    String str3 = b();
    if (str2 != null && str3 != null) {
      str2 = str2.trim().toLowerCase();
      str3 = str3.trim().toLowerCase();
      for (AbstractTable abstractTable : paramProject.getEntities()) {
        boolean bool = false;
        if (str1 == null) {
          if (str2.equalsIgnoreCase(abstractTable.getName()))
            return abstractTable; 
          if (a(abstractTable.getName(), str3) != null)
            bool = true; 
        } else {
          if (str1.equalsIgnoreCase(abstractTable.getSchema().getName()) && str2
            .equalsIgnoreCase(abstractTable.getName()))
            return abstractTable; 
          if (str1.equalsIgnoreCase(abstractTable.getName())) {
            bool = true;
          } else {
            String str;
            if ((str = a(abstractTable.getName(), str3)) != null && str
              .equalsIgnoreCase(str1))
              bool = true; 
          } 
        } 
        if (bool)
          for (Column column : abstractTable.getAttributes()) {
            if (column.getName().equalsIgnoreCase(str2))
              return column; 
          }  
      } 
    } 
    return null;
  }
  
  private String a(String paramString1, String paramString2) {
    if (paramString1 != null && paramString2 != null) {
      paramString2 = paramString2.toLowerCase();
      paramString1 = paramString1.toLowerCase();
      int i = -2;
      while ((i = paramString2.indexOf(paramString1, i + 1)) > -1) {
        int j = paramString2.length();
        char c;
        if (i == 0 || (c = paramString2.charAt(i - 1)) == ' ' || c == ',' || c == '"' || c == '\r' || c == '\n' || c == '.')
          if ((i += paramString1.length()) <= j) {
            if (i == j)
              return paramString1; 
            c = paramString2.charAt(i);
            if (c == '"') {
              i++;
              if (i == j)
                return paramString1; 
              c = paramString2.charAt(i);
            } 
            if (c == ' ') {
              while (i < j && paramString2.charAt(i) == ' ')
                i++; 
              if (i + 3 < j && paramString2.substring(i).startsWith("as ")) {
                i += 3;
                while (i < j && paramString2.charAt(i) == ' ')
                  i++; 
              } 
              int k = i;
              while (k < j && 
                !SqlUtil.isDelimiter(paramString2.charAt(k)))
                k++; 
              if (k > i)
                return paramString2.substring(i, k); 
              return paramString1;
            } 
            if (c == ',' || c == ';' || c == '\r' || c == '\n')
              return paramString1; 
          }  
      } 
    } 
    return null;
  }
  
  void k() {
    for (int i = this.d.g().getLine(); i <= this.d.h().getLine(); i++) {
      String str = this.d.b(i);
      if (str.endsWith("\n"))
        str = str.substring(0, str.length() - 1); 
      str = str.trim().startsWith("--") ? str.trim().substring(2) : ("-- " + str);
      this.d.a(i, str);
    } 
  }
}
