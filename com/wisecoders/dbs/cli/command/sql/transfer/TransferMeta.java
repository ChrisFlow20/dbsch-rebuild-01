package com.wisecoders.dbs.cli.command.sql.transfer;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Table;
import java.sql.ResultSetMetaData;
import org.apache.commons.csv.CSVRecord;

public class TransferMeta {
  public final String a;
  
  public final int b;
  
  public final int[] c;
  
  public final String[] d;
  
  public TransferMeta(String paramString, ResultSetMetaData paramResultSetMetaData) {
    this.a = paramString;
    this.b = paramResultSetMetaData.getColumnCount();
    this.c = new int[this.b];
    this.d = new String[this.b];
    for (byte b = 0; b < this.b; b++) {
      this.d[b] = paramResultSetMetaData.getColumnName(b + 1);
      this.c[b] = paramResultSetMetaData.getColumnType(b + 1);
    } 
  }
  
  public TransferMeta(Table paramTable, CSVRecord paramCSVRecord) {
    this.a = paramTable.getName();
    this.b = paramCSVRecord.size();
    this.c = new int[paramCSVRecord.size()];
    this.d = new String[paramCSVRecord.size()];
    for (byte b = 0; b < this.b; b++) {
      this.d[b] = paramCSVRecord.get(b);
      Column column = (Column)paramTable.columns.getByName(paramCSVRecord.get(b));
      this.c[b] = (column != null) ? column.getDataType().getJavaType() : 12;
    } 
  }
  
  public int a() {
    if (this.b > 300)
      return 100; 
    if (this.b > 200)
      return 200; 
    if (this.b > 100)
      return 400; 
    if (this.b > 50)
      return 1200; 
    if (this.b > 15)
      return 5000; 
    if (this.b > 8)
      return 15000; 
    if (this.b > 2)
      return 30000; 
    return 200000;
  }
  
  public int a(String paramString) {
    for (byte b = 0; b < this.d.length; b++) {
      if (paramString.equalsIgnoreCase(this.d[b]))
        return b; 
    } 
    return -1;
  }
}
