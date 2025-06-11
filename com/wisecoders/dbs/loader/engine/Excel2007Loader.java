package com.wisecoders.dbs.loader.engine;

import com.wisecoders.dbs.sys.StringUtil;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStrings;
import org.apache.poi.xssf.model.Styles;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class Excel2007Loader extends AbstractLoader implements XSSFSheetXMLHandler.SheetContentsHandler {
  private final File d;
  
  private final boolean e;
  
  private final int f;
  
  private int g;
  
  final LinkedHashMap b;
  
  final Map c;
  
  public Excel2007Loader(File paramFile, boolean paramBoolean, int paramInt) {
    this.g = -1;
    this.b = new LinkedHashMap<>();
    this.c = new LinkedHashMap<>();
    this.d = paramFile;
    this.e = paramBoolean;
    this.f = Math.max(0, paramInt);
  }
  
  public List a() {
    ArrayList<String> arrayList = new ArrayList();
    OPCPackage oPCPackage = OPCPackage.open(this.d, PackageAccess.READ);
    try {
      new ReadOnlySharedStringsTable(oPCPackage);
      XSSFReader xSSFReader = new XSSFReader(oPCPackage);
      XSSFReader.SheetIterator sheetIterator = (XSSFReader.SheetIterator)xSSFReader.getSheetsData();
      while (sheetIterator.hasNext()) {
        sheetIterator.next();
        arrayList.add(sheetIterator.getSheetName());
      } 
      if (oPCPackage != null)
        oPCPackage.close(); 
    } catch (Throwable throwable) {
      if (oPCPackage != null)
        try {
          oPCPackage.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    return arrayList;
  }
  
  public boolean firstLineIsHeader() {
    return this.e;
  }
  
  public void parse() {
    OPCPackage oPCPackage = OPCPackage.open(this.d, PackageAccess.READ);
    try {
      ReadOnlySharedStringsTable readOnlySharedStringsTable = new ReadOnlySharedStringsTable(oPCPackage);
      XSSFReader xSSFReader = new XSSFReader(oPCPackage);
      StylesTable stylesTable = xSSFReader.getStylesTable();
      XSSFReader.SheetIterator sheetIterator = (XSSFReader.SheetIterator)xSSFReader.getSheetsData();
      byte b = 0;
      while (sheetIterator.hasNext()) {
        InputStream inputStream = sheetIterator.next();
        try {
          if (b == this.f)
            a((Styles)stylesTable, (SharedStrings)readOnlySharedStringsTable, this, inputStream); 
          if (inputStream != null)
            inputStream.close(); 
        } catch (Throwable throwable) {
          if (inputStream != null)
            try {
              inputStream.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            }  
          throw throwable;
        } 
        b++;
      } 
      if (oPCPackage != null)
        oPCPackage.close(); 
    } catch (Throwable throwable) {
      if (oPCPackage != null)
        try {
          oPCPackage.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
  }
  
  public void a(Styles paramStyles, SharedStrings paramSharedStrings, XSSFSheetXMLHandler.SheetContentsHandler paramSheetContentsHandler, InputStream paramInputStream) {
    DataFormatter dataFormatter = new DataFormatter();
    InputSource inputSource = new InputSource(paramInputStream);
    try {
      XMLReader xMLReader = XMLHelper.newXMLReader();
      XSSFSheetXMLHandler xSSFSheetXMLHandler = new XSSFSheetXMLHandler(paramStyles, null, paramSharedStrings, paramSheetContentsHandler, dataFormatter, false);
      xMLReader.setContentHandler((ContentHandler)xSSFSheetXMLHandler);
      xMLReader.parse(inputSource);
    } catch (ParserConfigurationException parserConfigurationException) {
      throw new RuntimeException("SAX parser appears to be broken - " + parserConfigurationException.getMessage());
    } 
  }
  
  public void startRow(int paramInt) {
    this.g = paramInt;
  }
  
  public void endRow(int paramInt) {
    if (paramInt > 0 || !this.e)
      for (LoaderConsumer loaderConsumer : this.a)
        loaderConsumer.consumeRecord(this.c);  
    this.c.clear();
  }
  
  public void cell(String paramString1, String paramString2, XSSFComment paramXSSFComment) {
    short s = (new CellReference(paramString1)).getCol();
    if (this.e && this.g == 0) {
      String str = Excel2003Loader.a(s);
      if (!StringUtil.isEmptyTrim(paramString2))
        str = paramString2; 
      this.b.put(Integer.valueOf(s), str);
    } else {
      String str = Excel2003Loader.a(s);
      if (this.b.containsKey(Integer.valueOf(s)))
        str = (String)this.b.get(Integer.valueOf(s)); 
      this.c.put(str, paramString2);
    } 
  }
}
