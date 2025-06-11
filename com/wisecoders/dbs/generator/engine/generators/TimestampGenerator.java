package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampGenerator extends Generator {
  public static final String a = "timestamp:";
  
  public static final String b = "timestamp:from='01.01.2008 00:00:00 000';to='01.01.2009 00:00:00 000';";
  
  protected long c = System.currentTimeMillis() - 31536000000L, d = System.currentTimeMillis();
  
  public TimestampGenerator(String paramString, int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
    parseProperties(paramString);
    if (this.c > this.d)
      throw new ParseException("To must be greater than from.", 0); 
  }
  
  public void addProperty(String paramString1, String paramString2, int paramInt) {
    if ("from".equalsIgnoreCase(paramString1)) {
      this.c = a(paramString2);
    } else if ("to".equalsIgnoreCase(paramString1)) {
      this.d = a(paramString2);
    } else {
      throw new ParseException("Unknown property " + paramString1, paramInt);
    } 
  }
  
  private long a(String paramString) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Sys.B.timestampFormat.c_());
    simpleDateFormat.setLenient(false);
    Date date = simpleDateFormat.parse(paramString);
    return date.getTime();
  }
  
  public Date a(GeneratorTable paramGeneratorTable, Column paramColumn) {
    long l1 = this.d - this.c;
    float f = this.f.nextFloat();
    long l2 = this.c + (long)(f * (float)l1);
    return (paramColumn != null && paramColumn.getEntity().is(UnitProperty.f).booleanValue()) ? new Date(l2) : new Timestamp(l2);
  }
  
  public String toString() {
    return "Timestamp Generator";
  }
}
