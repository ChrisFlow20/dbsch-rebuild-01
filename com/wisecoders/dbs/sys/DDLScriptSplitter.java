package com.wisecoders.dbs.sys;

import com.wisecoders.dbs.dbms.Dbms;
import java.util.ArrayList;
import java.util.List;

public class DDLScriptSplitter {
  private final Dbms a;
  
  private final StringBuilder b = new StringBuilder();
  
  private final List c = new ArrayList();
  
  public DDLScriptSplitter(String paramString) {
    this.a = Dbms.get(paramString);
  }
  
  public List a(String paramString) {
    String str = "";
    if (paramString != null) {
      for (String str1 : paramString.split("\\r?\\n")) {
        str1 = this.a.removeLineComments(str1);
        String str2 = str1.trim().toLowerCase();
        if ("go".equals(str2)) {
          a();
          str2 = "";
        } 
        if (str.endsWith(";") && (str2.startsWith("create") || str2.startsWith("drop") || str2.startsWith("alter") || str2.startsWith("comment") || str2.startsWith("exec")))
          a(); 
        if (StringUtil.isFilled(str2)) {
          this.b.append(str1).append("\n");
          str = str2;
        } 
      } 
      a();
    } 
    return this.c;
  }
  
  private void a() {
    String str = b(this.b.toString());
    if (str.trim().length() > 0)
      this.c.add(str); 
    if (this.b.length() > 0)
      this.b.delete(0, this.b.length() - 1); 
  }
  
  private static String b(String paramString) {
    paramString = paramString.trim();
    if (paramString.endsWith(";"))
      paramString = paramString.substring(0, paramString.length() - 1); 
    return paramString;
  }
}
