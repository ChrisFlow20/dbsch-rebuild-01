package com.wisecoders.dbs.loader.engine;

class a {
  final String a;
  
  int b = 0;
  
  final JSONCallback c;
  
  public a(String paramString) {
    this(paramString, null);
  }
  
  public a(String paramString, JSONCallback paramJSONCallback) {
    this.a = paramString;
    this.c = paramJSONCallback;
  }
  
  public Object a() {
    return a((String)null);
  }
  
  protected Object a(String paramString) {
    Boolean bool;
    String str;
    Number number;
    Object object;
    Double double_ = null;
    char c = f();
    switch (c) {
      case 'n':
        a('n');
        a('u');
        a('l');
        a('l');
        double_ = null;
        return double_;
      case 'N':
        a('N');
        a('a');
        a('N');
        double_ = Double.valueOf(Double.NaN);
        return double_;
      case 't':
        a('t');
        a('r');
        a('u');
        a('e');
        bool = Boolean.valueOf(true);
        return bool;
      case 'f':
        a('f');
        a('a');
        a('l');
        a('s');
        a('e');
        bool = Boolean.valueOf(false);
        return bool;
      case '"':
      case '\'':
        return a(true);
      case '+':
      case '-':
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
        return g();
      case '[':
        object = c(paramString);
        return object;
      case '{':
        object = b(paramString);
        return object;
    } 
    throw new JSONParseException(this.a, this.b);
  }
  
  public Object b() {
    return b((String)null);
  }
  
  protected Object b(String paramString) {
    if (paramString != null) {
      this.c.a(paramString);
    } else {
      this.c.a();
    } 
    a('{');
    char c = f();
    String str = a(false);
    a(':');
    Object object = a(str);
    a(str, object);
    while (f() != '}' && (c = f()) == ',')
      a(','); 
    a('}');
    return this.c.b();
  }
  
  protected void a(String paramString, Object paramObject) {
    if (paramObject == null) {
      this.c.c(paramString);
    } else if (paramObject instanceof String) {
      this.c.a(paramString, (String)paramObject);
    } else if (paramObject instanceof Boolean) {
      this.c.a(paramString, ((Boolean)paramObject).booleanValue());
    } else if (paramObject instanceof Integer) {
      this.c.a(paramString, ((Integer)paramObject).intValue());
    } else if (paramObject instanceof Long) {
      this.c.a(paramString, ((Long)paramObject).longValue());
    } else if (paramObject instanceof Double) {
      this.c.a(paramString, ((Double)paramObject).doubleValue());
    } 
  }
  
  public void a(char paramChar) {
    if (!b(paramChar))
      throw new JSONParseException(this.a, this.b); 
    this.b++;
  }
  
  public char c() {
    if (this.b >= this.a.length())
      throw new IllegalStateException("string done"); 
    return this.a.charAt(this.b++);
  }
  
  public void d() {
    if (this.b < this.a.length() && ((this.a
      .charAt(this.b) >= '0' && this.a.charAt(this.b) <= '9') || (this.a
      .charAt(this.b) >= 'A' && this.a.charAt(this.b) <= 'F') || (this.a
      .charAt(this.b) >= 'a' && this.a.charAt(this.b) <= 'f'))) {
      this.b++;
    } else {
      throw new JSONParseException(this.a, this.b);
    } 
  }
  
  public boolean b(char paramChar) {
    return (f() == paramChar);
  }
  
  public void e() {
    while (this.b < this.a.length() && Character.isWhitespace(this.a.charAt(this.b)))
      this.b++; 
  }
  
  public char f() {
    e();
    if (this.b < this.a.length())
      return this.a.charAt(this.b); 
    return Character.MAX_VALUE;
  }
  
  public String a(boolean paramBoolean) {
    byte b = 0;
    if (b('\'')) {
      b = 39;
    } else if (b('"')) {
      b = 34;
    } else if (paramBoolean) {
      throw new JSONParseException(this.a, this.b);
    } 
    if (b > 0)
      a(b); 
    StringBuilder stringBuilder = new StringBuilder();
    int i = this.b;
    while (this.b < this.a.length()) {
      char c = this.a.charAt(this.b);
      if ((b > 0) ? (
        c == b) : (


        
        c == ':' || c == ' '))
        break; 
      if (c == '\\') {
        int j, k;
        this.b++;
        char c1 = f();
        byte b1 = 0;
        switch (c1) {
          case 'u':
            stringBuilder.append(this.a.substring(i, this.b - 1));
            j = ++this.b;
            d();
            d();
            d();
            d();
            k = Integer.parseInt(this.a.substring(j, j + 4), 16);
            stringBuilder.append((char)k);
            i = this.b;
            continue;
          case 'n':
            b1 = 10;
            break;
          case 'r':
            b1 = 13;
            break;
          case 't':
            b1 = 9;
            break;
          case 'b':
            b1 = 8;
            break;
          case '"':
            b1 = 34;
            break;
          case '\\':
            b1 = 92;
            break;
        } 
        stringBuilder.append(this.a.substring(i, this.b - 1));
        if (b1 != 0) {
          this.b++;
          stringBuilder.append(b1);
        } 
        i = this.b;
        continue;
      } 
      this.b++;
    } 
    stringBuilder.append(this.a.substring(i, this.b));
    if (b > 0)
      a(b); 
    return stringBuilder.toString();
  }
  
  public Number g() {
    f();
    int i = this.b;
    boolean bool = false;
    if (b('-') || b('+'))
      this.b++; 
    while (this.b < this.a.length()) {
      switch (this.a.charAt(this.b)) {
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
          this.b++;
        case '.':
          bool = true;
          h();
        case 'E':
        case 'e':
          bool = true;
          i();
      } 
    } 
    try {
      if (bool)
        return Double.valueOf(this.a.substring(i, this.b)); 
      Long long_ = Long.valueOf(this.a.substring(i, this.b));
      if (long_.longValue() <= 2147483647L && long_.longValue() >= -2147483648L)
        return Integer.valueOf(long_.intValue()); 
      return long_;
    } catch (NumberFormatException numberFormatException) {
      throw new JSONParseException(this.a, i, numberFormatException);
    } 
  }
  
  public void h() {
    this.b++;
    while (this.b < this.a.length()) {
      switch (this.a.charAt(this.b)) {
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
          this.b++;
        case 'E':
        case 'e':
          i();
      } 
    } 
  }
  
  public void i() {
    this.b++;
    if (b('-') || b('+'))
      this.b++; 
    while (this.b < this.a.length()) {
      switch (this.a.charAt(this.b)) {
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
          this.b++;
      } 
    } 
  }
  
  public Object j() {
    return c(null);
  }
  
  protected Object c(String paramString) {
    if (paramString != null) {
      this.c.b(paramString);
    } else {
      this.c.f();
    } 
    a('[');
    byte b = 0;
    char c = f();
    while (c != ']') {
      String str = String.valueOf(b++);
      Object object = a(str);
      a(str, object);
      if ((c = f()) == ',') {
        a(',');
        continue;
      } 
      if (c == ']')
        break; 
      throw new JSONParseException(this.a, this.b);
    } 
    a(']');
    return this.c.g();
  }
}
