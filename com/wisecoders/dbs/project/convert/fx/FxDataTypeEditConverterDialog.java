package com.wisecoders.dbs.project.convert.fx;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.project.convert.model.DataTypeConverter;
import com.wisecoders.dbs.project.convert.model.DataTypeConverterReplacement;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.sys.fx.AutoCompleteComboBox;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.util.Collection;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class FxDataTypeEditConverterDialog extends Dialog$ {
  private final DataTypeConverter f;
  
  private DataTypeConverterReplacement i;
  
  final ComboBox a = new ComboBox();
  
  final ComboBox b = new ComboBox();
  
  final TextField c = new TextField();
  
  final ConverterRangeField d = new ConverterRangeField(DataTypeConverter.d);
  
  final ConverterRangeField e = new ConverterRangeField(DataTypeConverter.d);
  
  public FxDataTypeEditConverterDialog(Window paramWindow, DataTypeConverter paramDataTypeConverter, DataTypeConverterReplacement paramDataTypeConverterReplacement) {
    super(paramWindow);
    this.f = paramDataTypeConverter;
    this.i = paramDataTypeConverterReplacement;
    this.a.getItems().addAll(Dbms.getKnownDbmsExclude(new String[] { paramDataTypeConverter.b.b }));
    this.a.setOnAction(paramActionEvent -> a());
    new AutoCompleteComboBox(this.a);
    this.b.setOnAction(paramActionEvent -> b());
    this.d.getItems().setAll((Object[])new String[] { "", "ORIGINAL" });
    this.e.getItems().setAll((Object[])new String[] { "", "ORIGINAL" });
    this.d.setMaxWidth(Double.MAX_VALUE);
    this.e.setMaxWidth(Double.MAX_VALUE);
    this.c.setMaxWidth(Double.MAX_VALUE);
    if (paramDataTypeConverterReplacement != null) {
      this.a.setValue(paramDataTypeConverterReplacement.a);
      this.a.setDisable(true);
      this.b.setValue(paramDataTypeConverterReplacement.b());
      this.d.a(paramDataTypeConverterReplacement.c());
      this.e.a(paramDataTypeConverterReplacement.d());
      this.c.setText(paramDataTypeConverterReplacement.e());
      a();
    } 
    setResultConverter(paramButtonType -> {
          if (this.i == null)
            this.i = paramDataTypeConverter.b((String)this.a.getValue()); 
          this.i.a((DataType)this.b.getValue());
          if (!this.d.isDisable())
            this.i.a(this.d.a()); 
          if (!this.e.isDisable())
            this.i.b(this.e.a()); 
          this.i.a(this.c.getText());
          return this.i;
        });
  }
  
  public Node createContentPane() {
    return (Node)(new GridPane$()).l()
      .a((Node)this.rx.e("targetDbmsLabel"), "0,0,r,c")
      .a((Node)this.a, "1,0,l,c")
      .a((Node)this.rx.e("dataTypeLabel"), "0,1,r,c")
      .a((Node)this.b, "1,1,l,c")
      .a((Node)this.rx.e("precisionLabel"), "0,2,r,c")
      .a((Node)this.d, "1,2,f,c")
      .a((Node)this.rx.e("decimalLabel"), "0,3,r,c")
      .a((Node)this.e, "1,3,f,c")
      .a((Node)this.rx.e("optionsLabel"), "0,4,r,c")
      .a((Node)this.c, "1,4,f,c");
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    if (!this.d.isDisabled())
      try {
        this.d.a();
      } catch (NumberFormatException numberFormatException) {
        this.rx.c(getDialogScene(), "Please edit the length");
        return false;
      }  
    if (!this.e.isDisabled())
      try {
        this.e.a();
      } catch (NumberFormatException numberFormatException) {
        this.rx.c(getDialogScene(), "Please edit the decimal");
        return false;
      }  
    this.f.a(false);
    return true;
  }
  
  private void a() {
    this.b.getItems().clear();
    if (this.a.getValue() != null)
      this.b.getItems().setAll((Collection)(DbmsTypes.get((String)this.a.getValue())).dataTypes); 
    b();
  }
  
  private void b() {
    DataType dataType = (DataType)this.b.getValue();
    this.d.setDisable((dataType == null || !dataType.getPrecision().usesLength()));
    this.e.setDisable((dataType == null || !dataType.getPrecision().usesDecimal()));
  }
}
