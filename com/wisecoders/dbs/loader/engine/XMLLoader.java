package com.wisecoders.dbs.loader.engine;

import com.wisecoders.dbs.sys.StringUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.XMLReader;

public class XMLLoader extends AbstractLoader implements ContentHandler {
  private final File b;
  
  private final String c;
  
  private final List d = new ArrayList();
  
  private final HashMap e = new HashMap<>();
  
  private String f;
  
  private int g;
  
  private final HashMap h;
  
  public void parse() {
    SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
    SAXParser sAXParser = sAXParserFactory.newSAXParser();
    XMLReader xMLReader = sAXParser.getXMLReader();
    xMLReader.setContentHandler(this);
    InputSource inputSource = new InputSource(new BufferedInputStream(new FileInputStream(this.b)));
    inputSource.setEncoding(this.c);
    xMLReader.parse(inputSource);
  }
  
  public XMLLoader(File paramFile, String paramString) {
    this.g = -1;
    this.h = new HashMap<>();
    this.b = paramFile;
    this.c = paramString;
  }
  
  public int a() {
    return this.d.size() - 1;
  }
  
  private String b() {
    if (this.g == -1)
      this.g = Math.max(0, a()); 
    return this.d.isEmpty() ? "body" : this.d.get(this.d.size() - 1);
  }
  
  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes) {
    this.d.add(paramString3);
    this.f = null;
    this.e.clear();
    for (byte b = 0; b < paramAttributes.getLength(); b++)
      this.e.put(paramAttributes.getQName(b), paramAttributes.getValue(b)); 
  }
  
  public void characters(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
    String str = new String(paramArrayOfchar, paramInt1, paramInt2);
    if (str.trim().length() == 0)
      str = ""; 
    this.f = (this.f == null) ? str : (this.f + this.f);
  }
  
  public void endElement(String paramString1, String paramString2, String paramString3) {
    c();
    if (this.d.size() > 0)
      this.d.remove(this.d.size() - 1); 
  }
  
  private void c() {
    String str = b();
    if (StringUtil.isFilledTrim(this.f)) {
      this.h.put(str, this.f);
      this.f = null;
    } 
    for (String str1 : this.e.keySet())
      this.h.put(str + "." + str, (String)this.e.get(str1)); 
    if (a() == this.g - 1) {
      for (LoaderConsumer loaderConsumer : this.a)
        loaderConsumer.consumeRecord(this.h); 
      this.h.clear();
    } 
  }
  
  public boolean firstLineIsHeader() {
    return false;
  }
  
  public void setDocumentLocator(Locator paramLocator) {}
  
  public void startDocument() {}
  
  public void endDocument() {}
  
  public void startPrefixMapping(String paramString1, String paramString2) {}
  
  public void endPrefixMapping(String paramString) {}
  
  public void ignorableWhitespace(char[] paramArrayOfchar, int paramInt1, int paramInt2) {}
  
  public void processingInstruction(String paramString1, String paramString2) {}
  
  public void skippedEntity(String paramString) {}
}
