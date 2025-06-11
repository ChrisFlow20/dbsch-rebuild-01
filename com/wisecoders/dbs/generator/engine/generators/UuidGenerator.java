package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import java.text.ParseException;
import java.util.UUID;

public class UuidGenerator extends Generator {
  public static final String a = "uuid:";
  
  public static final String b = "uuid:";
  
  private RegExpGenerator c;
  
  private final int d;
  
  public UuidGenerator(String paramString, int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
    this.d = paramInt2;
    parseProperties(paramString);
  }
  
  public void addProperty(String paramString1, String paramString2, int paramInt) {
    if ("pattern".equalsIgnoreCase(paramString1))
      this.c = new RegExpGenerator(paramString2, 0, this.d); 
  }
  
  public Object generate(GeneratorTable paramGeneratorTable, Column paramColumn) {
    if (this.c == null) {
      long l1 = this.f.nextLong();
      long l2 = this.f.nextLong();
      return new UUID(l2, l1);
    } 
    String str = this.c.a();
    if (str != null) {
      str = str.replaceAll("-", "");
      if (str.length() == 32) {
        long l1 = Long.parseLong(str.substring(0, 15), 16);
        long l2 = Long.parseLong(str.substring(16, 31), 16);
        return new UUID(l1, l2);
      } 
      throw new ParseException("Regexp used for UUID should generate 32 hex digits.", 0);
    } 
    return null;
  }
}
