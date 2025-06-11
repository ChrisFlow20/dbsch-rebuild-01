package com.wisecoders.dbs.loader.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVLoader extends AbstractLoader {
  private CSVFormat.Builder b;
  
  private final File c;
  
  private final String d;
  
  public CSVLoader(CSVFormat.Builder paramBuilder, File paramFile, String paramString) {
    this.b = paramBuilder;
    this.c = paramFile;
    this.d = paramString;
  }
  
  public void parse() {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(this.c), this.d));
    try {
      CSVParser cSVParser = new CSVParser(bufferedReader, this.b.build());
      for (Iterator<CSVRecord> iterator = cSVParser.iterator(); iterator.hasNext() && !reachedMaxProcessingTime(); ) {
        CSVRecord cSVRecord = iterator.next();
        Map<Object, Object> map = cSVRecord.toMap();
        if (map == null || map.isEmpty()) {
          map = new LinkedHashMap<>();
          for (byte b = 0; b < cSVRecord.size(); b++)
            map.put("col_" + b, cSVRecord.get(b)); 
        } 
        for (LoaderConsumer loaderConsumer : this.a)
          loaderConsumer.consumeRecord(map); 
      } 
      bufferedReader.close();
    } catch (Throwable throwable) {
      try {
        bufferedReader.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  public void a(CSVFormat.Builder paramBuilder) {
    this.b = paramBuilder;
  }
  
  public boolean firstLineIsHeader() {
    return this.b.build().getSkipHeaderRecord();
  }
}
