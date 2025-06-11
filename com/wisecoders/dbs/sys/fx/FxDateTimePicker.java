package com.wisecoders.dbs.sys.fx;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyEvent;

public class FxDateTimePicker extends DatePicker {
  private DateTimeFormatter a;
  
  private ObjectProperty b = (ObjectProperty)new SimpleObjectProperty(LocalDateTime.now());
  
  private ObjectProperty c = (ObjectProperty)new FxDateTimePicker$1(this);
  
  public FxDateTimePicker(String paramString) {
    getStyleClass().add("datetime-picker");
    a(paramString);
    setConverter(new c(this));
    valueProperty().addListener((paramObservableValue, paramLocalDate1, paramLocalDate2) -> {
          if (paramLocalDate2 == null) {
            this.b.set(null);
          } else if (this.b.get() == null) {
            this.b.set(LocalDateTime.of(paramLocalDate2, LocalTime.now()));
          } else {
            LocalTime localTime = ((LocalDateTime)this.b.get()).toLocalTime();
            this.b.set(LocalDateTime.of(paramLocalDate2, localTime));
          } 
        });
    this.b.addListener((paramObservableValue, paramLocalDateTime1, paramLocalDateTime2) -> setValue((paramLocalDateTime2 == null) ? null : paramLocalDateTime2.toLocalDate()));
    getEditor().focusedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (!paramBoolean2.booleanValue())
            f(); 
        });
    getEditor().setOnKeyTyped(paramKeyEvent -> f());
    getEditor().setOnAction(paramActionEvent -> f());
  }
  
  public LocalDateTime a() {
    f();
    return (LocalDateTime)this.b.getValue();
  }
  
  private void f() {
    try {
      this.b.set(LocalDateTime.parse(getEditor().getText(), this.a));
      a(false);
    } catch (DateTimeParseException dateTimeParseException) {
      a(true);
    } 
  }
  
  public LocalDateTime b() {
    return (LocalDateTime)this.b.get();
  }
  
  public void a(LocalDateTime paramLocalDateTime) {
    this.b.set(paramLocalDateTime);
  }
  
  public ObjectProperty c() {
    return this.b;
  }
  
  public String d() {
    return (String)this.c.get();
  }
  
  public ObjectProperty e() {
    return this.c;
  }
  
  public void a(String paramString) {
    this.c.set(paramString);
  }
  
  private static final PseudoClass d = PseudoClass.getPseudoClass("error");
  
  public void a(boolean paramBoolean) {
    pseudoClassStateChanged(d, paramBoolean);
  }
}
