package com.wisecoders.dbs.project.convert.model;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.project.convert.store.ConversionDictionaryLoader;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConversionDictionary {
  public static final String a = "1.0";
  
  public final String b;
  
  public final ObservableList c = FXCollections.observableArrayList();
  
  public final ObservableList d = FXCollections.observableArrayList();
  
  public final ObservableList e = FXCollections.observableArrayList();
  
  public ConversionDictionary(String paramString) {
    this.b = paramString;
    new ConversionDictionaryLoader(this, d());
  }
  
  public DataTypeConverter a(AbstractUnit paramAbstractUnit) {
    DataTypeConverter dataTypeConverter = null;
    for (DataTypeConverter dataTypeConverter1 : this.c) {
      if (dataTypeConverter1.a(paramAbstractUnit) && (dataTypeConverter == null || dataTypeConverter.c()))
        dataTypeConverter = dataTypeConverter1; 
    } 
    return dataTypeConverter;
  }
  
  public DefaultValueConverter b(AbstractUnit paramAbstractUnit) {
    DefaultValueConverter defaultValueConverter = null;
    for (DefaultValueConverter defaultValueConverter1 : this.d) {
      if (defaultValueConverter1.a(paramAbstractUnit) && (defaultValueConverter == null || defaultValueConverter.c()))
        defaultValueConverter = defaultValueConverter1; 
    } 
    return defaultValueConverter;
  }
  
  public OptionsConverter c(AbstractUnit paramAbstractUnit) {
    OptionsConverter optionsConverter = null;
    for (OptionsConverter optionsConverter1 : this.e) {
      if (optionsConverter1.a(paramAbstractUnit) && (optionsConverter == null || optionsConverter.c()))
        optionsConverter = optionsConverter1; 
    } 
    return optionsConverter;
  }
  
  public boolean a(Project paramProject, String paramString) {
    for (DataTypeConverter dataTypeConverter : this.c) {
      for (DataTypeConverterReplacement dataTypeConverterReplacement : dataTypeConverter.g) {
        if (paramString.equals(dataTypeConverterReplacement.a) && !dataTypeConverterReplacement.a())
          return false; 
      } 
    } 
    for (DefaultValueConverter defaultValueConverter : this.d) {
      for (DefaultValueConverterReplacement defaultValueConverterReplacement : defaultValueConverter.f) {
        if (paramString.equals(defaultValueConverterReplacement.a) && !defaultValueConverterReplacement.a())
          return false; 
      } 
    } 
    for (Schema schema : paramProject.schemas) {
      for (Table table : schema.tables) {
        for (Column column : table.columns) {
          if (!(column.getDataType() instanceof com.wisecoders.dbs.schema.UserDataType)) {
            boolean bool = false;
            for (DataTypeConverter dataTypeConverter : this.c) {
              if (dataTypeConverter.a(column))
                bool = true; 
            } 
            if (!bool)
              return false; 
          } 
        } 
      } 
    } 
    return true;
  }
  
  public DataTypeConverter a() {
    DataTypeConverter dataTypeConverter = new DataTypeConverter(this);
    this.c.add(dataTypeConverter);
    return dataTypeConverter;
  }
  
  public DefaultValueConverter b() {
    DefaultValueConverter defaultValueConverter = new DefaultValueConverter(this);
    this.d.add(defaultValueConverter);
    return defaultValueConverter;
  }
  
  public OptionsConverter c() {
    OptionsConverter optionsConverter = new OptionsConverter(this);
    this.e.add(optionsConverter);
    return optionsConverter;
  }
  
  public void a(DefaultValueConverter paramDefaultValueConverter) {
    this.d.remove(paramDefaultValueConverter);
  }
  
  public void a(OptionsConverter paramOptionsConverter) {
    this.e.remove(paramOptionsConverter);
  }
  
  public void a(DataTypeConverter paramDataTypeConverter) {
    this.c.remove(paramDataTypeConverter);
  }
  
  public boolean a(DataType paramDataType) {
    for (DataTypeConverter dataTypeConverter : this.c) {
      if (dataTypeConverter.e() == paramDataType)
        return true; 
    } 
    return false;
  }
  
  public void a(Project paramProject, List paramList) {
    for (String str : paramList)
      b(paramProject, str); 
  }
  
  public void b(Project paramProject, String paramString) {
    for (DataTypeConverter dataTypeConverter : this.c) {
      if (dataTypeConverter.c())
        dataTypeConverter.b(true); 
    } 
    for (DefaultValueConverter defaultValueConverter : this.d) {
      if (defaultValueConverter.c())
        defaultValueConverter.b(true); 
    } 
    for (OptionsConverter optionsConverter : this.e) {
      if (optionsConverter.c())
        optionsConverter.b(true); 
    } 
    for (AbstractTable abstractTable : paramProject.getEntities()) {
      for (Column column : abstractTable.getAttributes()) {
        DataTypeConverter dataTypeConverter = a(column);
        if (dataTypeConverter == null) {
          dataTypeConverter = new DataTypeConverter(this);
          dataTypeConverter.a(column.getDataType());
          dataTypeConverter.a(true);
          dataTypeConverter.d(column.getTypeOptions());
          this.c.add(dataTypeConverter);
        } else {
          dataTypeConverter.b(false);
        } 
        if (!dataTypeConverter.c(paramString))
          dataTypeConverter.b(paramString).a(column); 
        switch (ConversionDictionary$1.a[column.getSpec().ordinal()]) {
          case 1:
          
          case 2:
          
          default:
            break;
        } 
        String str1 = column.getDefaultValue();
        if (StringUtil.isFilled(str1)) {
          DefaultValueConverter defaultValueConverter = b(column);
          if (defaultValueConverter == null) {
            defaultValueConverter = new DefaultValueConverter(this);
            defaultValueConverter.b(DefaultValueConverter.a(column));
            defaultValueConverter.a(column.getDataType());
            defaultValueConverter.a(true);
            this.d.add(defaultValueConverter);
          } else {
            defaultValueConverter.b(false);
          } 
          if (!defaultValueConverter.d(paramString))
            defaultValueConverter.c(paramString).b(); 
        } 
        String str2 = (column.getIdentity() != null) ? column.getIdentity() : ((column.getDefinition() != null) ? column.getDefinition() : column.getOptions());
        if (StringUtil.isFilledTrim(str2)) {
          OptionsConverter optionsConverter = c(column);
          if (optionsConverter == null) {
            optionsConverter = new OptionsConverter(this);
            optionsConverter.b(Pattern.quote(str2));
            optionsConverter.a(true);
            this.e.add(optionsConverter);
          } else {
            optionsConverter.b(false);
          } 
          if (!optionsConverter.d(paramString))
            optionsConverter.c(paramString).b(); 
        } 
      } 
      if (abstractTable instanceof Table) {
        Table table = (Table)abstractTable;
        if (StringUtil.isFilledTrim(table.getOptions())) {
          OptionsConverter optionsConverter = c(table);
          if (optionsConverter == null) {
            optionsConverter = new OptionsConverter(this);
            optionsConverter.b(Pattern.quote(table.getOptions()));
            optionsConverter.a(true);
            this.e.add(optionsConverter);
          } else {
            optionsConverter.b(false);
          } 
          if (!optionsConverter.d(paramString))
            optionsConverter.c(paramString).b(); 
        } 
      } 
    } 
    this.c.removeIf(Converter::d);
    this.d.removeIf(Converter::d);
    this.e.removeIf(Converter::d);
    this.c.sort((paramDataTypeConverter1, paramDataTypeConverter2) -> {
          null = String.valueOf(paramDataTypeConverter1.f).compareTo(String.valueOf(paramDataTypeConverter2.f));
          if (null != 0)
            return null; 
          null = Integer.compare(paramDataTypeConverter1.g(), paramDataTypeConverter2.g());
          if (null != 0)
            return null; 
          null = Integer.compare(paramDataTypeConverter1.h(), paramDataTypeConverter2.h());
          if (null != 0)
            return null; 
          null = Integer.compare(paramDataTypeConverter1.i(), paramDataTypeConverter2.i());
          if (null != 0)
            return null; 
          null = Integer.compare(paramDataTypeConverter1.j(), paramDataTypeConverter2.j());
          return (null != 0) ? null : StringUtil.compare(paramDataTypeConverter1.f(), paramDataTypeConverter2.f());
        });
    this.d.sort(Comparator.comparing(paramDefaultValueConverter -> String.valueOf(paramDefaultValueConverter.d)));
  }
  
  private static final Map f = new HashMap<>();
  
  public static ConversionDictionary a(String paramString) {
    return f.computeIfAbsent(paramString, paramString2 -> new ConversionDictionary(paramString1));
  }
  
  public void a(Project paramProject, String paramString, boolean paramBoolean) {
    for (Schema schema : paramProject.schemas) {
      a(schema, paramBoolean, paramString);
      for (Table table : new ArrayList(schema.tables)) {
        a(table, paramBoolean, paramString);
        if (table.getOptions() != null) {
          boolean bool = true;
          for (OptionsConverter optionsConverter : this.e) {
            if (optionsConverter.a(table)) {
              optionsConverter.a(paramString, table);
              bool = false;
              break;
            } 
          } 
          if (bool)
            table.setOptions(null); 
        } 
        for (Column column : table.getAttributes()) {
          a(column, paramBoolean, paramString);
          if (DefaultValueConverter.a(column) != null)
            for (DefaultValueConverter defaultValueConverter : this.d) {
              if (defaultValueConverter.a(column)) {
                defaultValueConverter.a(paramString, column);
                break;
              } 
            }  
          for (DataTypeConverter dataTypeConverter : this.c) {
            if (dataTypeConverter.a(column)) {
              dataTypeConverter.a(paramString, column);
              break;
            } 
          } 
          boolean bool = true;
          for (OptionsConverter optionsConverter : this.e) {
            if (optionsConverter.a(column)) {
              optionsConverter.a(paramString, column);
              bool = false;
            } 
          } 
          if (bool)
            column.setOptions((String)null); 
        } 
        if (paramBoolean)
          for (ForeignKey foreignKey : table.foreignKeys) {
            a(foreignKey, paramBoolean, paramString);
            foreignKey.resolveLogical();
            foreignKey.resolveManyToMany();
          }  
      } 
      for (Sequence sequence : new ArrayList(schema.sequences)) {
        a(sequence, paramBoolean, paramString);
        if (sequence.getOptions() != null) {
          boolean bool = true;
          for (OptionsConverter optionsConverter : this.e) {
            if (optionsConverter.a(sequence)) {
              optionsConverter.a(paramString, sequence);
              bool = false;
            } 
          } 
          if (bool)
            sequence.setOptions(null); 
        } 
      } 
      schema.refresh();
    } 
  }
  
  private void a(AbstractUnit paramAbstractUnit, boolean paramBoolean, String paramString) {
    if (paramBoolean) {
      if (paramAbstractUnit.getCommentTag("PhysicalName") != null)
        paramAbstractUnit.rename(paramAbstractUnit.getCommentTag("PhysicalName")); 
    } else if ("LogicalDesign".equals(paramString)) {
      paramAbstractUnit.setCommentTag("PhysicalName", paramAbstractUnit.getName());
    } 
  }
  
  private Index a(String paramString1, Table paramTable, IndexType paramIndexType, String paramString2, String paramString3, boolean paramBoolean) {
    Index index = (paramIndexType == IndexType.PRIMARY_KEY) ? paramTable.getIndexByType(paramIndexType) : null;
    if (index == null) {
      Column column = paramTable.createColumn(paramString3, DbmsTypes.get(paramString1).getDataType(4));
      if (paramBoolean)
        column.setMandatory(true); 
      index = paramTable.createIndex(paramString2);
      index.setType(paramIndexType);
      index.addColumn(column);
    } 
    return index;
  }
  
  public File d() {
    return Sys.a().resolve(StringUtil.escapeForFileName(this.b) + "/ConversionDictionary1.0.xml").toFile();
  }
}
