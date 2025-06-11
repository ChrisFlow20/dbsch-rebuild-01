package com.wisecoders.dbs.loader.engine;

import com.wisecoders.dbs.sys.StringUtil;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel2003Loader extends AbstractLoader {
  private final File b;
  
  private final boolean c;
  
  private final int d;
  
  public Excel2003Loader(File paramFile, boolean paramBoolean, int paramInt) {
    this.b = paramFile;
    this.c = paramBoolean;
    this.d = paramInt;
  }
  
  public List a() {
    FileInputStream fileInputStream = new FileInputStream(this.b);
    try {
      Workbook workbook = a(fileInputStream);
      ArrayList<String> arrayList1 = new ArrayList();
      for (byte b = 0; b < workbook.getNumberOfSheets(); b++)
        arrayList1.add(workbook.getSheetName(b)); 
      ArrayList<String> arrayList2 = arrayList1;
      fileInputStream.close();
      return arrayList2;
    } catch (Throwable throwable) {
      try {
        fileInputStream.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  private Workbook a(FileInputStream paramFileInputStream) {
    return this.b.getName().endsWith("xls") ? (Workbook)new HSSFWorkbook(paramFileInputStream) : (Workbook)new XSSFWorkbook(paramFileInputStream);
  }
  
  public void parse() {
    FileInputStream fileInputStream = new FileInputStream(this.b);
    try {
      Workbook workbook = a(fileInputStream);
      int i = Math.max(0, this.d);
      if (i > workbook.getNumberOfSheets())
        throw new IllegalArgumentException("Invalid sheet number " + i); 
      Sheet sheet = workbook.getSheetAt(i);
      boolean bool = true;
      ArrayList<String> arrayList = new ArrayList();
      LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
      for (Iterator<Row> iterator = sheet.iterator(); iterator.hasNext() && !reachedMaxProcessingTime(); ) {
        Row row = iterator.next();
        Iterator<Cell> iterator1 = row.cellIterator();
        while (iterator1.hasNext()) {
          Cell cell = iterator1.next();
          int j = cell.getColumnIndex();
          if (this.c && bool) {
            String str1 = a(j);
            if (cell.getCellType() == CellType.STRING) {
              String str2 = cell.getStringCellValue();
              if (!StringUtil.isEmptyTrim(str2) && !arrayList.contains(str2))
                str1 = str2; 
            } 
            arrayList.add(str1);
            continue;
          } 
          String str = (j < arrayList.size()) ? arrayList.get(j) : a(j);
          switch (Excel2003Loader$1.a[cell.getCellType().ordinal()]) {
            case 1:
              linkedHashMap.put(str, Double.valueOf(cell.getNumericCellValue()));
              continue;
            case 2:
              linkedHashMap.put(str, Boolean.valueOf(cell.getBooleanCellValue()));
              continue;
            case 3:
              linkedHashMap.put(str, null);
              continue;
          } 
          linkedHashMap.put(str, cell.getStringCellValue());
        } 
        if (!bool || !this.c)
          for (LoaderConsumer loaderConsumer : this.a)
            loaderConsumer.consumeRecord(linkedHashMap);  
        linkedHashMap.clear();
        bool = false;
      } 
      fileInputStream.close();
    } catch (Throwable throwable) {
      try {
        fileInputStream.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  public static String a(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    int i = paramInt;
    while (true) {
      stringBuilder.insert(0, (char)(65 + i % 35));
      i /= 35;
      if (i <= 0)
        return stringBuilder.toString(); 
    } 
  }
  
  public boolean firstLineIsHeader() {
    return this.c;
  }
}
