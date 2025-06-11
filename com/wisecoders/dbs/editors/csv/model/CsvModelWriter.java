package com.wisecoders.dbs.editors.csv.model;

import com.wisecoders.dbs.sys.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvModelWriter {
  final ByteArrayOutputStream a = new ByteArrayOutputStream(1024);
  
  final Writer b = new OutputStreamWriter(this.a, StandardCharsets.UTF_8);
  
  final CSVPrinter c;
  
  public CsvModelWriter() {
    CSVPrinter cSVPrinter = null;
    try {
      cSVPrinter = new CSVPrinter(this.b, CSVFormat.DEFAULT);
    } catch (IOException iOException) {
      Log.b(iOException);
    } 
    this.c = cSVPrinter;
  }
  
  public byte[] a(Iterable paramIterable) {
    this.a.reset();
    try {
      this.c.print(paramIterable);
      this.c.flush();
    } catch (IOException iOException) {}
    byte[] arrayOfByte = this.a.toByteArray();
    this.a.reset();
    return arrayOfByte;
  }
  
  public int a() {
    return this.a.size();
  }
}
