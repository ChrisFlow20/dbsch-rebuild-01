package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public abstract class Generator {
  protected final int e;
  
  protected final Random f = new Random();
  
  private static final Pattern a = Pattern.compile("(.+)=(.+)", 34);
  
  public abstract Object generate(GeneratorTable paramGeneratorTable, Column paramColumn);
  
  public Generator(int paramInt1, int paramInt2) {
    this.e = paramInt1;
    if (paramInt2 != -1)
      this.f.setSeed(paramInt2); 
  }
  
  public void parseProperties(String paramString) {
    int i = paramString.indexOf(":");
    if (i < 0)
      throw new ParseException("Missing ':'", 0); 
    String str = paramString.substring(i + 1);
    if (StringUtil.isFilledTrim(str)) {
      String[] arrayOfString = str.split(";");
      for (String str1 : arrayOfString) {
        Matcher matcher = a.matcher(str1);
        if (matcher.matches()) {
          try {
            String str2 = StringUtil.removeQuotes(matcher.group(2));
            addProperty(matcher.group(1), str2, i);
          } catch (NumberFormatException numberFormatException) {
            throw new ParseException(numberFormatException.toString(), i + matcher.group(1).length() + 1);
          } 
          i += str1.length() + 1;
        } else {
          throw new ParseException("Wrong formatted pattern.", i);
        } 
      } 
    } 
  }
  
  public void addProperty(String paramString1, String paramString2, int paramInt) {}
  
  public static Generator getGenerator(Column paramColumn, int paramInt) {
    String str = paramColumn.getOrGuessGeneratorPattern();
    if (StringUtil.isEmptyTrim(str))
      throw new ParseException("Empty pattern for '" + paramColumn.getNameWithPath() + "'", 0); 
    return getGenerator(str, paramColumn.getGeneratorNullsPercentage(), (paramColumn.getGeneratorSeed() != -1) ? paramColumn.getGeneratorSeed() : paramInt);
  }
  
  public static Generator getGenerator(String paramString, int paramInt1, int paramInt2) {
    if (StringUtil.isEmptyTrim(paramString))
      throw new ParseException("Empty Pattern", 0); 
    String str = paramString.toLowerCase();
    if (str.startsWith("list:"))
      return new ValuesFromFileGenerator(paramString, paramInt1, paramInt2); 
    if (str.startsWith("long:"))
      return new NumberGenerator(paramString, -5, paramInt1, paramInt2); 
    if (str.startsWith("int:"))
      return new NumberGenerator(paramString, 4, paramInt1, paramInt2); 
    if (str.startsWith("short:"))
      return new NumberGenerator(paramString, 5, paramInt1, paramInt2); 
    if (str.startsWith("float:"))
      return new NumberGenerator(paramString, 6, paramInt1, paramInt2); 
    if (str.startsWith("double:"))
      return new NumberGenerator(paramString, 8, paramInt1, paramInt2); 
    if (str.startsWith("sequence:"))
      return new SequenceGenerator(paramString, paramInt1); 
    if (str.startsWith("groovy:"))
      return new GroovyGenerator(paramString, paramInt1); 
    if (str.startsWith("pyhton:"))
      return new PythonGenerator(paramString, paramInt1); 
    if (str.startsWith("timestamp:"))
      return new TimestampGenerator(paramString, paramInt1, paramInt2); 
    if (str.startsWith("date:"))
      return new DateGenerator(paramString, paramInt1, paramInt2); 
    if (str.startsWith("json_map:"))
      return new JsonMapGenerator(paramString, paramInt1, paramInt2); 
    if (str.startsWith("json_list:"))
      return new JsonListGenerator(paramString, paramInt1, paramInt2); 
    if (str.startsWith("load_values_from_pk"))
      return new ForeignKeyGenerator(paramInt1, paramInt2); 
    if (str.startsWith("boolean"))
      return new BooleanGenerator(paramString, paramInt1, paramInt2); 
    if (str.startsWith("identity"))
      return new IdentityGenerator(paramInt1, paramInt2); 
    if (str.startsWith("skip"))
      return new SkipGenerator(paramInt1, paramInt2); 
    if (str.startsWith("uuid:"))
      return new UuidGenerator(paramString, paramInt1, paramInt2); 
    if (str.startsWith("ref"))
      return new RefGenerator(paramString, paramInt1, paramInt2); 
    if (str.startsWith("db_sequence"))
      return new AssociatedSequenceGenerator(paramInt1, paramInt2); 
    return new RegExpGenerator(paramString, paramInt1, paramInt2);
  }
  
  public Object generateWithNulls(GeneratorTable paramGeneratorTable, Column paramColumn) {
    if (this.e > 0 && this.f.nextFloat() < this.e / 100.0F)
      return null; 
    return generate(paramGeneratorTable, paramColumn);
  }
  
  public String generateWithNullsAsString(GeneratorTable paramGeneratorTable, Column paramColumn) {
    Object object = generateWithNulls(paramGeneratorTable, paramColumn);
    if (object instanceof Timestamp) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Sys.B.timestampFormat.c_());
      return simpleDateFormat.format((Timestamp)object);
    } 
    if (object instanceof Date) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Sys.B.dateFormat.c_());
      return simpleDateFormat.format((Date)object);
    } 
    return (object != null) ? String.valueOf(object) : "";
  }
  
  public static String convertValueToString(Object paramObject) {
    if (paramObject instanceof Timestamp) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Sys.B.timestampFormat.c_());
      return simpleDateFormat.format((Timestamp)paramObject);
    } 
    if (paramObject instanceof Date) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Sys.B.dateFormat.c_());
      return simpleDateFormat.format((Date)paramObject);
    } 
    return (paramObject != null) ? String.valueOf(paramObject) : "";
  }
  
  public int getNullPercent() {
    return this.e;
  }
}
