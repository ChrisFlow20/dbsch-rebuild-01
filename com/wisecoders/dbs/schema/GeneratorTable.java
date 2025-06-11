package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.generator.engine.generators.ForeignKeyGenerator;
import com.wisecoders.dbs.generator.engine.generators.Generator;
import com.wisecoders.dbs.generator.engine.generators.GroovyGenerator;
import com.wisecoders.dbs.generator.engine.plan.FlatTextWarn;
import com.wisecoders.dbs.generator.engine.plan.HasData;
import com.wisecoders.dbs.generator.engine.plan.ParseWarn;
import com.wisecoders.dbs.generator.engine.plan.Warn;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SqlUtil;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@DoNotObfuscate
public class GeneratorTable {
  public final Table table;
  
  private final HashMap a = new HashMap<>();
  
  private static final int b = 15;
  
  private GeneratorTable$Status c = GeneratorTable$Status.a;
  
  private int d = 0;
  
  private int e = 0;
  
  private int f = 0;
  
  private HasData g = HasData.a;
  
  public final ObservableList warns = FXCollections.observableArrayList();
  
  private final boolean h;
  
  private final Map i = new HashMap<>();
  
  private final Map j;
  
  private static final int k = 200;
  
  public void setStatus(GeneratorTable$Status paramGeneratorTable$Status) {
    this.c = paramGeneratorTable$Status;
  }
  
  public GeneratorTable$Status getStatus() {
    return this.c;
  }
  
  public void initialize() {
    a(this.table);
  }
  
  private void a(Entity paramEntity) {
    byte b = 0;
    for (Attribute attribute : paramEntity.getAttributes()) {
      Column column = (Column)attribute;
      String str = column.getOrGuessGeneratorPattern();
      if (str != null && !str.isEmpty()) {
        try {
          this.a.put(column, Generator.getGenerator(column, b++));
        } catch (ParseException parseException) {
          Log.b(parseException);
          if (this.h) {
            this.i.put(column, SqlUtil.getExceptionText(parseException));
          } else {
            throw new ParseException("Generating '" + String.valueOf(column.getEntity()) + "." + String.valueOf(column) + "' got " + parseException.getMessage(), parseException.getErrorOffset());
          } 
        } 
      } else if (this.h) {
        this.i.put(column, "Missing generator pattern");
      } else {
        throw new ParseException("Missing data generator pattern for " + String.valueOf(column.getEntity()) + "." + String.valueOf(column) + " .\nRight-click the column in the layout to configure.", 0);
      } 
      if (column.hasChildEntity())
        a(column.getChildEntity()); 
    } 
  }
  
  public void validate() {
    this.warns.clear();
    b(this.table);
  }
  
  private void b(Entity paramEntity) {
    for (Attribute attribute : paramEntity.getAttributes()) {
      Column column = (Column)attribute;
      try {
        Generator.getGenerator(column, 0);
      } catch (ParseException parseException) {
        a(new ParseWarn(paramEntity, column, "Pattern error"));
      } 
      if (column.hasChildEntity())
        b(column.getChildEntity()); 
    } 
  }
  
  public String buildSQL() {
    return buildSQL(null);
  }
  
  public String buildSQL(Map paramMap) {
    Dbms dbms = Dbms.get(this.table.getDbId());
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(dbms.tableInsert.c_()).append(" ");
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder1.append(this.table.ref()).append(" ( ");
    boolean bool = true;
    for (byte b = 0; b < this.table.getAttributes().size(); b++) {
      Column column = (Column)this.table.getAttributes().get(b);
      if (columnHasActiveGenerator(column)) {
        if (!bool) {
          stringBuilder1.append(", ");
          stringBuilder2.append(", ");
        } 
        if (columnHasSequence(column)) {
          stringBuilder1.append(column.ref());
          stringBuilder2.append(dbms.columnInsertFromSequence.c_().replaceAll("\\$\\{name}", column.getAssociatedSequence().ref()));
        } else {
          stringBuilder1.append(column.ref());
          String str = column.getDataType().getUpdateCast();
          if (str != null) {
            stringBuilder2.append(str);
          } else if (paramMap == null) {
            stringBuilder2.append("?");
          } else {
            Object object = paramMap.get(column);
            if (object == null) {
              stringBuilder2.append("NULL");
            } else if (column.getDataType().isNumeric()) {
              stringBuilder2.append(object);
            } else if (column.getDataType().isBoolean()) {
              if (dbms.booleanAs01.b() && object instanceof Boolean) {
                stringBuilder2.append(((Boolean)object).booleanValue() ? "1" : "0");
              } else {
                stringBuilder2.append(object);
              } 
            } else if (object instanceof Timestamp) {
              stringBuilder2.append("'").append(dbms.timestampFormat.a((Timestamp)object)).append("'");
            } else if (object instanceof Date) {
              stringBuilder2.append("'").append(dbms.dateFormat.a((Date)object)).append("'");
            } else {
              stringBuilder2.append("'").append(object).append("'");
            } 
          } 
        } 
        bool = false;
      } 
    } 
    stringBuilder1.append(" ) VALUES ( ").append(stringBuilder2).append(" )");
    return stringBuilder1.toString();
  }
  
  public String buildCSVHeader() {
    boolean bool = false;
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < this.table.getAttributes().size(); b++) {
      Column column = (Column)this.table.getAttributes().get(b);
      if (columnHasActiveGenerator(column)) {
        if (bool)
          stringBuilder.append(","); 
        stringBuilder.append(column.getName());
        bool = true;
      } 
    } 
    stringBuilder.append("\n");
    return stringBuilder.toString();
  }
  
  public String buildCSVRecord(Map paramMap) {
    Dbms dbms = Dbms.get(this.table.getDbId());
    StringBuilder stringBuilder = new StringBuilder();
    boolean bool = true;
    for (byte b = 0; b < this.table.getAttributes().size(); b++) {
      Column column = (Column)this.table.getAttributes().get(b);
      if (columnHasActiveGenerator(column)) {
        if (!bool)
          stringBuilder.append(","); 
        if (columnHasSequence(column)) {
          stringBuilder.append(dbms.columnInsertFromSequence.c_().replaceAll("\\$\\{name}", column.getAssociatedSequence().ref()));
        } else if (paramMap != null) {
          Object object = paramMap.get(column);
          if (object != null)
            if (column.getDataType().isNumeric()) {
              stringBuilder.append(object);
            } else if (column.getDataType().isBoolean()) {
              if (dbms.booleanAs01.b() && object instanceof Boolean) {
                stringBuilder.append(((Boolean)object).booleanValue() ? "1" : "0");
              } else {
                stringBuilder.append(object);
              } 
            } else if (object instanceof Timestamp) {
              stringBuilder.append(dbms.timestampFormat.a((Timestamp)object));
            } else if (object instanceof Date) {
              stringBuilder.append(dbms.dateFormat.a((Date)object));
            } else {
              stringBuilder.append("'").append(object).append("'");
            }  
        } 
        bool = false;
      } 
    } 
    return stringBuilder.toString();
  }
  
  public boolean columnHasActiveGenerator(Column paramColumn) {
    return (!(this.a.get(paramColumn) instanceof com.wisecoders.dbs.generator.engine.generators.SkipGenerator) && 
      !(this.a.get(paramColumn) instanceof com.wisecoders.dbs.generator.engine.generators.IdentityGenerator));
  }
  
  public boolean columnHasSequence(Column paramColumn) {
    return (paramColumn.hasAssociatedSequence() && (Dbms.get(paramColumn.getDbId())).columnInsertFromSequence.j());
  }
  
  public GeneratorTable(Table paramTable, boolean paramBoolean) {
    this.j = new HashMap<>();
    this.table = paramTable;
    this.h = paramBoolean;
  }
  
  public Map generateValuesSet() {
    this.j.clear();
    ArrayList arrayList = new ArrayList(this.table.getAttributes());
    arrayList.sort((paramColumn1, paramColumn2) -> Boolean.compare(this.a.get(paramColumn1) instanceof GroovyGenerator, this.a.get(paramColumn2) instanceof GroovyGenerator));
    for (Column column : arrayList) {
      Generator generator = (Generator)this.a.get(column);
      if (this.h) {
        try {
          if (generator instanceof GroovyGenerator) {
            this.j.put(column, ((GroovyGenerator)generator).a(this.j));
            continue;
          } 
          if (generator == null) {
            this.j.put(column, this.i.get(column));
            continue;
          } 
          this.j.put(column, generator.generateWithNulls(this, column));
        } catch (Throwable throwable) {
          this.j.put(column, SqlUtil.getExceptionText(throwable));
        } 
        continue;
      } 
      if (generator instanceof GroovyGenerator) {
        this.j.put(column, ((GroovyGenerator)generator).a(this.j));
        continue;
      } 
      if (generator != null)
        this.j.put(column, generator.generateWithNulls(this, column)); 
    } 
    return this.j;
  }
  
  public void learnFkColumnValues(Envoy paramEnvoy) {
    for (ForeignKey foreignKey : this.table.getRelations()) {
      for (Iterator<Column> iterator1 = foreignKey.getSourceAttributes().iterator(), iterator2 = foreignKey.getTargetAttributes().iterator(); iterator1.hasNext() && iterator2.hasNext(); ) {
        Column column1 = iterator1.next();
        Column column2 = iterator2.next();
        Generator generator = (Generator)this.a.get(column1);
        if (column2 != null && generator instanceof ForeignKeyGenerator) {
          int i = ((ForeignKeyGenerator)generator).a(paramEnvoy, column2);
          if (column1.isMandatory() && i == 0)
            createFlatTextWarn(column2.getEntity().getSymbolicName() + " '" + column2.getEntity().getSymbolicName() + "', referred by '" + String.valueOf(column2.getEntity()) + "." + String.valueOf(column1.getEntity()) + "' is empty. Referring column '" + String.valueOf(column1) + "' is mandatory."); 
        } 
      } 
    } 
  }
  
  public void loadForeignKeyGeneratorsValues(Map paramMap) {
    for (Column column : paramMap.keySet()) {
      Generator generator = (Generator)this.a.get(column);
      if (generator instanceof ForeignKeyGenerator)
        ((ForeignKeyGenerator)generator).a(paramMap.get(column)); 
    } 
  }
  
  public void resetForeignKeyGenerators() {
    for (Column column : this.table.getAttributes()) {
      Generator generator = (Generator)this.a.get(column);
      if (generator instanceof ForeignKeyGenerator)
        ((ForeignKeyGenerator)generator).a(); 
    } 
  }
  
  public String toString() {
    return this.table.toString();
  }
  
  public void setHasData(HasData paramHasData) {
    this.g = paramHasData;
  }
  
  public HasData hasData() {
    return this.g;
  }
  
  public void createFlatTextWarn(String paramString) {
    for (Warn warn : this.warns) {
      if (warn.a(paramString)) {
        warn.a();
        return;
      } 
    } 
    if (this.warns.size() < 15) {
      Column column = null;
      for (Column column1 : this.table.getAttributes()) {
        if (paramString.toLowerCase().contains(column1.getName().toLowerCase()) && (column == null || column
          .getName().length() < column1.getName().length()))
          column = column1; 
      } 
      FlatTextWarn flatTextWarn = new FlatTextWarn(this.table, column, paramString);
      this.warns.add(flatTextWarn);
    } 
  }
  
  public int getSucceedCount() {
    return this.d;
  }
  
  public void increaseSucceedCount(int paramInt) {
    this.d += paramInt;
    this.f = 0;
    this.g = HasData.b;
  }
  
  public int getFailedCount() {
    return this.e;
  }
  
  public void increaseFailedCount() {
    this.e++;
    this.f++;
  }
  
  void a() {
    this.d = 0;
    this.e = 0;
    this.f = 0;
  }
  
  public boolean insertMore() {
    int i = Sys.B.maxErrors.a();
    return (this.d < this.table.getGeneratorRowsCount() && this.f < i);
  }
  
  public boolean hasReachedConsecutiveFailureCount() {
    return (this.f == 200);
  }
  
  void b() {
    this.warns.clear();
  }
  
  void a(Warn paramWarn) {
    this.warns.add(paramWarn);
  }
  
  public Generator getCachedGenerator(Column paramColumn) {
    return (Generator)this.a.get(paramColumn);
  }
}
