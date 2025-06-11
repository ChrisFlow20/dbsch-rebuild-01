package com.wisecoders.dbs.project.convert.fx;

import com.wisecoders.dbs.project.convert.model.DataTypeConverter;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.sys.StringUtil;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class ConverterRangeField extends ComboBox {
  public static final String a = "ORIGINAL";
  
  public static final String b = "MIN";
  
  public static final String c = "MAX";
  
  public static final String d = "*";
  
  public static final String e = "";
  
  public ConverterRangeField(int paramInt) {
    setEditable(true);
    setPrefWidth(90.0D);
    a(paramInt);
    getItems().addAll((Object[])new String[] { "", "MIN", "MAX" });
    setCellFactory(paramListView -> new ConverterRangeField$1(this));
  }
  
  public void a(int paramInt) {
    if (paramInt == DataTypeConverter.c) {
      setValue("MIN");
    } else if (paramInt == DataTypeConverter.e) {
      setValue("MAX");
    } else if (paramInt == DataTypeConverter.d) {
      setValue("ORIGINAL");
    } else if (paramInt == DataType.UNSET) {
      setValue("*");
    } else {
      setValue("" + paramInt);
    } 
  }
  
  public int a() {
    String str = (getValue() != null) ? ((String)getValue()).trim() : null;
    if (StringUtil.isEmpty(str))
      return 0; 
    if ("MIN".equalsIgnoreCase(str))
      return DataTypeConverter.c; 
    if ("MAX".equalsIgnoreCase(str))
      return DataTypeConverter.e; 
    if ("ORIGINAL".equalsIgnoreCase(str))
      return DataTypeConverter.d; 
    if ("*".equalsIgnoreCase(str))
      return DataType.UNSET; 
    return Integer.parseInt(str);
  }
}
