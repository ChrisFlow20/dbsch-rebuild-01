package com.wisecoders.dbs.project.store;

import com.wisecoders.dbs.sys.Log;
import java.io.IOException;
import java.io.Writer;
import java.text.StringCharacterIterator;

public class XMLWriter implements AutoCloseable {
  private int c = 0;
  
  private a d = a.a;
  
  private final Writer b;
  
  public XMLWriter(Writer paramWriter) {
    this.b = paramWriter;
  }
  
  public void a(String paramString) {
    this.b.write(paramString);
  }
  
  public XMLWriter a(Object paramObject) {
    byte b;
    if (!a && paramObject == null)
      throw new AssertionError(); 
    switch (XMLWriter$1.a[this.d.ordinal()]) {
      case 1:
        this.b.write(">");
      case 2:
        this.b.write("\n");
        for (b = 0; b < this.c; b++)
          this.b.write("\t"); 
        this.b.write("<");
        this.b.write(paramObject.toString());
        this.b.write(" ");
        break;
      case 3:
        this.b.write("<");
        this.b.write(paramObject.toString());
        this.b.write(" ");
        break;
    } 
    this.c++;
    this.d = a.b;
    return this;
  }
  
  public XMLWriter a(Object paramObject1, Object paramObject2) {
    if (paramObject1 != null && paramObject2 != null && paramObject2.toString() != null && paramObject2.toString().length() > 0) {
      byte b;
      switch (XMLWriter$1.a[this.d.ordinal()]) {
        case 1:
          this.b.write(">");
        case 2:
        case 3:
          this.b.write("\n");
          for (b = 0; b < this.c; b++)
            this.b.write("\t"); 
          this.b.write("<");
          this.b.write(paramObject1.toString());
          this.b.write(">");
          b(paramObject2.toString());
          this.b.write("</");
          this.b.write(paramObject1.toString());
          this.b.write(">");
          break;
      } 
      this.d = a.c;
    } 
    return this;
  }
  
  public XMLWriter b(Object paramObject1, Object paramObject2) {
    if (paramObject1 != null && paramObject2 != null && paramObject2.toString() != null && !paramObject2.toString().isEmpty()) {
      byte b;
      switch (XMLWriter$1.a[this.d.ordinal()]) {
        case 1:
          this.b.write(">");
        case 2:
        case 3:
          this.b.write("\n");
          for (b = 0; b < this.c; b++)
            this.b.write("\t"); 
          this.b.write("<");
          this.b.write(paramObject1.toString());
          this.b.write("><![CDATA[");
          c(paramObject2.toString());
          this.b.write("]]></");
          this.b.write(paramObject1.toString());
          this.b.write(">");
          break;
      } 
      this.d = a.c;
    } 
    return this;
  }
  
  public XMLWriter b(Object paramObject) {
    byte b;
    if (!a && paramObject == null)
      throw new AssertionError(); 
    this.c--;
    switch (XMLWriter$1.a[this.d.ordinal()]) {
      case 1:
        this.b.write("/>");
        break;
      case 2:
      case 3:
        this.b.write("\n");
        for (b = 0; b < this.c; b++)
          this.b.write("\t"); 
        this.b.write("</");
        this.b.write(paramObject.toString());
        this.b.write(">");
        break;
    } 
    this.d = a.c;
    return this;
  }
  
  public XMLWriter c(Object paramObject1, Object paramObject2) {
    if (!a && paramObject1 == null)
      throw new AssertionError(); 
    this.c--;
    if (this.d == a.b)
      if (paramObject2 == null) {
        this.b.write("/>");
      } else {
        this.b.write(">");
        b(paramObject2.toString());
        this.b.write("</");
        this.b.write(paramObject1.toString());
        this.b.write(">");
      }  
    this.d = a.c;
    return this;
  }
  
  public XMLWriter d(Object paramObject1, Object paramObject2) {
    if (!a && paramObject1 == null)
      throw new AssertionError(); 
    this.c--;
    if (this.d == a.b) {
      this.b.write("><![CDATA[");
      c(paramObject2.toString());
      this.b.write("]]></");
      this.b.write(paramObject1.toString());
      this.b.write(">");
    } 
    this.d = a.c;
    return this;
  }
  
  public XMLWriter a() {
    this.b.write("/>");
    this.d = a.c;
    this.c--;
    return this;
  }
  
  public XMLWriter c(Object paramObject) {
    if (paramObject == null)
      return this; 
    this.b.write(paramObject.toString());
    this.b.write("=\"y\" ");
    return this;
  }
  
  public XMLWriter a(Object paramObject, boolean paramBoolean) {
    if (paramObject != null) {
      this.b.write(paramObject.toString());
      this.b.write(paramBoolean ? "=\"y\" " : "=\"n\" ");
    } 
    return this;
  }
  
  public XMLWriter e(Object paramObject1, Object paramObject2) {
    if (paramObject1 != null && paramObject2 != null && paramObject2.toString() != null) {
      this.b.write(paramObject1.toString());
      this.b.write("=\"");
      b(paramObject2.toString());
      this.b.write("\" ");
    } 
    return this;
  }
  
  private void b(String paramString) {
    StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(paramString);
    char c = stringCharacterIterator.current();
    while (c != Character.MAX_VALUE) {
      switch (c) {
        case '\'':
          this.b.write("&#039;");
          break;
        case '"':
          this.b.write("&quot;");
          break;
        case '&':
          this.b.write("&amp;");
          break;
        case '<':
          this.b.write("&lt;");
          break;
        case '>':
          this.b.write("&gt;");
          break;
        case '\n':
          this.b.write("&#10;");
          break;
        case '\r':
          this.b.write("&#13;");
          break;
        case '\000':
        case '\001':
        case '\002':
        case '\003':
        case '\004':
        case '\005':
        case '\006':
        case '\007':
        case '\b':
        case '\013':
        case '\f':
        case '\016':
        case '\017':
        case '\020':
        case '\021':
        case '\022':
        case '\023':
        case '\024':
        case '\025':
        case '\026':
        case '\027':
        case '\030':
        case '\031':
        case '\032':
        case '\033':
        case '\034':
        case '\035':
        case '\036':
        case '\037':
          break;
        default:
          this.b.write(c);
          break;
      } 
      c = stringCharacterIterator.next();
    } 
  }
  
  private void c(String paramString) {
    paramString = paramString.replaceAll("]]>", "]]_>");
    StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(paramString);
    char c = stringCharacterIterator.current();
    while (c != Character.MAX_VALUE) {
      switch (c) {
        case '\000':
        case '\001':
        case '\002':
        case '\003':
        case '\004':
        case '\005':
        case '\006':
        case '\007':
        case '\b':
        case '\013':
        case '\f':
        case '\016':
        case '\017':
        case '\020':
        case '\021':
        case '\022':
        case '\023':
        case '\024':
        case '\025':
        case '\026':
        case '\027':
        case '\030':
        case '\031':
        case '\032':
        case '\033':
        case '\034':
        case '\035':
        case '\036':
        case '\037':
          break;
        default:
          this.b.write(c);
          break;
      } 
      c = stringCharacterIterator.next();
    } 
  }
  
  public void close() {
    try {
      this.b.close();
    } catch (IOException iOException) {
      Log.b(iOException);
    } 
  }
  
  public void b() {
    try {
      this.b.flush();
    } catch (IOException iOException) {
      Log.b(iOException);
    } 
  }
}
