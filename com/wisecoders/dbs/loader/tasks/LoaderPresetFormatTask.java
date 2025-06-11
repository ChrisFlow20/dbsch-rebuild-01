package com.wisecoders.dbs.loader.tasks;

import com.wisecoders.dbs.loader.engine.AbstractLoader;
import com.wisecoders.dbs.loader.engine.CSVLoader;
import com.wisecoders.dbs.loader.engine.Excel2003Loader;
import com.wisecoders.dbs.loader.engine.Excel2007Loader;
import com.wisecoders.dbs.loader.engine.JSONParseException;
import com.wisecoders.dbs.loader.engine.LoaderConsumer;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import org.apache.commons.csv.CSVFormat;

public class LoaderPresetFormatTask extends Task implements LoaderConsumer {
  private static final char a = '?';
  
  private static final char[] b = new char[] { '\t', ',', ';', ':', '|', ' ' };
  
  private static final char[] c = new char[] { '"', '\'', '`' };
  
  private static final char[] d = new char[] { '/', '\\' };
  
  private final Rx e;
  
  private final File f;
  
  private final AbstractLoader g;
  
  private final Scene h;
  
  private final List i = new ArrayList();
  
  private String j;
  
  private Locale k;
  
  private CSVFormat.Builder l = CSVFormat.DEFAULT.builder();
  
  private int m;
  
  protected Void a() {
    Thread.currentThread().setName("DbSchema: DataLoader Preset Task");
    if (this.g instanceof CSVLoader) {
      updateMessage("Check file format...");
      f();
      ((CSVLoader)this.g).a(this.l);
    } else if (this.g instanceof Excel2007Loader) {
      updateMessage("Load sheets...");
      this.i.addAll(((Excel2007Loader)this.g).a());
    } else if (this.g instanceof Excel2003Loader) {
      updateMessage("Load sheets...");
      this.i.addAll(((Excel2003Loader)this.g).a());
    } 
    this.g.parse();
    return null;
  }
  
  protected void succeeded() {}
  
  public CSVFormat.Builder b() {
    return this.l;
  }
  
  public Locale c() {
    return this.k;
  }
  
  public String d() {
    return this.j;
  }
  
  public List e() {
    return this.i;
  }
  
  public void failed() {
    Throwable throwable = getException();
    Log.a("Error in data evaluator task", throwable);
    if (throwable instanceof JSONParseException) {
      JSONParseException jSONParseException = (JSONParseException)throwable;
      this.e.c(this.h, "Parse error at position " + jSONParseException.a() + "\n" + throwable.getLocalizedMessage() + "\n\nDbSchema expect one Json document per line, please check.");
    } else {
      this.e.c(this.h, throwable.getLocalizedMessage());
    } 
  }
  
  private char a(char[] paramArrayOfchar1, char[] paramArrayOfchar2, char paramChar1, char paramChar2) {
    HashMap<Object, Object> hashMap = new HashMap<>();
    for (char c1 : paramArrayOfchar2)
      hashMap.put(Character.valueOf(c1), Integer.valueOf(0)); 
    int i;
    for (i = 0; i < paramArrayOfchar1.length; i++) {
      boolean bool1 = (i == 0 || paramChar1 == '?' || paramChar1 == paramArrayOfchar1[i - 1]) ? true : false;
      boolean bool2 = (i == paramArrayOfchar1.length - 1 || paramChar2 == '?' || paramChar2 == paramArrayOfchar1[i + 1]) ? true : false;
      if (bool1 && bool2 && hashMap.containsKey(Character.valueOf(paramArrayOfchar1[i])))
        hashMap.put(Character.valueOf(paramArrayOfchar1[i]), Integer.valueOf(((Integer)hashMap.get(Character.valueOf(paramArrayOfchar1[i]))).intValue() + 1)); 
    } 
    i = -1;
    char c = '?';
    for (Iterator<Character> iterator = hashMap.keySet().iterator(); iterator.hasNext(); ) {
      char c1 = ((Character)iterator.next()).charValue();
      if (i < ((Integer)hashMap.get(Character.valueOf(c1))).intValue()) {
        i = ((Integer)hashMap.get(Character.valueOf(c1))).intValue();
        c = c1;
      } 
    } 
    return c;
  }
  
  private void f() {
    char[] arrayOfChar = new char[10000];
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(this.f)));
    try {
      int i = bufferedReader.read(arrayOfChar);
      arrayOfChar = Arrays.copyOf(arrayOfChar, i);
      bufferedReader.close();
    } catch (Throwable throwable) {
      try {
        bufferedReader.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    char c1 = a(arrayOfChar, b, '?', '?');
    char c2 = a(arrayOfChar, c, c1, '?');
    char c3 = a(arrayOfChar, d, '?', c2);
    if (c1 != '?')
      this.l = this.l.setDelimiter(c1); 
    if (c2 != '?')
      this.l = this.l.setQuote(c2); 
    if (c3 != '?')
      this.l = this.l.setEscape(c3); 
  }
  
  public LoaderPresetFormatTask(Scene paramScene, Rx paramRx, File paramFile, AbstractLoader paramAbstractLoader) {
    this.m = 0;
    this.h = paramScene;
    this.e = paramRx;
    this.g = paramAbstractLoader;
    this.f = paramFile;
    paramAbstractLoader.addConsumer(this);
  }
  
  public void consumeRecord(Map paramMap) {
    if (this.m == 1)
      for (String str : paramMap.keySet()) {
        Object object = paramMap.get(str);
        if (object instanceof String && StringUtil.isFilledTrim(object))
          for (String str1 : LoaderPresetSeparators.a) {
            a((String)object, str1, Locale.ENGLISH);
            a((String)object, str1, Locale.getDefault());
          }  
      }  
    this.m++;
  }
  
  private void a(String paramString1, String paramString2, Locale paramLocale) {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(paramString2, paramLocale);
      simpleDateFormat.setLenient(false);
      simpleDateFormat.parse(paramString1);
      this.k = paramLocale;
      this.j = paramString2;
    } catch (Exception exception) {}
  }
}
