package com.wisecoders.dbs.project.store;

import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.XMLReader;

@DoNotObfuscate
public abstract class AbstractContentHandler implements ContentHandler {
  private String a;
  
  private String b;
  
  private final HashMap c = new HashMap<>();
  
  private boolean d = true;
  
  private int e = -1;
  
  public void parse(InputStream paramInputStream) {
    SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
    SAXParser sAXParser = sAXParserFactory.newSAXParser();
    XMLReader xMLReader = sAXParser.getXMLReader();
    xMLReader.setContentHandler(this);
    InputSource inputSource = new InputSource(new BufferedInputStream(paramInputStream));
    inputSource.setEncoding("UTF-8");
    xMLReader.parse(inputSource);
  }
  
  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes) {
    a();
    this.a = paramString3;
    this.b = null;
    this.e++;
    this.c.clear();
    for (byte b = 0; b < paramAttributes.getLength(); b++)
      this.c.put(paramAttributes.getQName(b), paramAttributes.getValue(b)); 
    this.d = false;
  }
  
  private void a() {
    if (!this.d) {
      a(this.a, this.e);
      this.d = true;
    } 
  }
  
  public void endElement(String paramString1, String paramString2, String paramString3) {
    a();
    this.e--;
  }
  
  public void characters(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
    String str = new String(paramArrayOfchar, paramInt1, paramInt2);
    if (str.trim().isEmpty())
      str = ""; 
    this.b = (this.b == null) ? str : (this.b + this.b);
  }
  
  public String getBody() {
    return this.b;
  }
  
  public String get(Object paramObject) {
    return (String)this.c.get(String.valueOf(paramObject));
  }
  
  public boolean has(Object paramObject) {
    return (this.c.get(String.valueOf(paramObject)) != null);
  }
  
  public int getInt(Object paramObject) {
    String str = get(paramObject);
    if (str != null) {
      if (str.startsWith("#"))
        return Integer.parseInt(str.substring(1), 16); 
      return Integer.parseInt(str);
    } 
    return -1;
  }
  
  protected long a(Object paramObject) {
    String str = get(paramObject);
    if (str != null)
      return Long.parseLong(str); 
    return -1L;
  }
  
  protected short b(Object paramObject) {
    String str = get(paramObject);
    if (str != null) {
      if (str.startsWith("#"))
        return Short.parseShort(str.substring(1), 16); 
      return Short.parseShort(str);
    } 
    return -1;
  }
  
  public boolean getBoolean(Object paramObject) {
    String str = get(paramObject);
    return ("y".equalsIgnoreCase(str) || "true".equalsIgnoreCase(str));
  }
  
  public boolean getBoolean(Object paramObject, boolean paramBoolean) {
    String str = get(paramObject);
    return (str != null) ? (("y".equalsIgnoreCase(str) || "true".equalsIgnoreCase(str))) : paramBoolean;
  }
  
  protected abstract void a(String paramString, int paramInt);
  
  public void ignorableWhitespace(char[] paramArrayOfchar, int paramInt1, int paramInt2) {}
  
  public void processingInstruction(String paramString1, String paramString2) {}
  
  public void skippedEntity(String paramString) {}
  
  public void setDocumentLocator(Locator paramLocator) {}
  
  public void startDocument() {}
  
  public void endDocument() {}
  
  public void startPrefixMapping(String paramString1, String paramString2) {}
  
  public void endPrefixMapping(String paramString) {}
}
