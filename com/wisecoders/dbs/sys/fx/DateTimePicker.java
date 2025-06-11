package com.wisecoders.dbs.sys.fx;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;

public class DateTimePicker extends DatePicker {
  public static final String a = "yyyy-MM-dd HH:mm:ss";
  
  private DateTimeFormatter b;
  
  private final ObjectProperty c = (ObjectProperty)new SimpleObjectProperty(LocalDateTime.now());
  
  private final ObjectProperty d = (ObjectProperty)new DateTimePicker$1(this);
  
  public void a() {
    getEditor().setPrefColumnCount(d().length());
  }
  
  public DateTimePicker() {
    getStyleClass().add("datetime-picker");
    a("yyyy-MM-dd HH:mm:ss");
    setConverter(new a(this));
    a();
    valueProperty().addListener((paramObservableValue, paramLocalDate1, paramLocalDate2) -> {
          if (paramLocalDate2 == null) {
            this.c.set(null);
          } else if (this.c.get() == null) {
            this.c.set(LocalDateTime.of(paramLocalDate2, LocalTime.now()));
          } else {
            LocalTime localTime = ((LocalDateTime)this.c.get()).toLocalTime();
            this.c.set(LocalDateTime.of(paramLocalDate2, localTime));
          } 
        });
    this.c.addListener((paramObservableValue, paramLocalDateTime1, paramLocalDateTime2) -> {
          if (paramLocalDateTime2 == null) {
            setValue(null);
          } else {
            LocalDate localDate = paramLocalDateTime2.toLocalDate();
            boolean bool = localDate.equals(valueProperty().get());
            setValue(localDate);
            if (bool)
              setConverter(new a(this)); 
          } 
        });
    getEditor().focusedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (!paramBoolean2.booleanValue())
            f(); 
        });
  }
  
  private void f() {
    getEditor().commitValue();
  }
  
  public LocalDateTime b() {
    return (LocalDateTime)this.c.get();
  }
  
  public void a(LocalDateTime paramLocalDateTime) {
    this.c.set(paramLocalDateTime);
  }
  
  public ObjectProperty c() {
    return this.c;
  }
  
  public String d() {
    return (String)this.d.get();
  }
  
  public ObjectProperty e() {
    return this.d;
  }
  
  public void a(String paramString) {
    this.d.set(paramString);
    a();
  }
  
  public void a(Date paramDate) {
    a(b(paramDate));
  }
  
  public static LocalDateTime b(Date paramDate) {
    Instant instant = Instant.ofEpochMilli(paramDate.getTime());
    return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
  }
  
  public static Date b(LocalDateTime paramLocalDateTime) {
    Instant instant = paramLocalDateTime.toInstant(ZoneOffset.UTC);
    return Date.from(instant);
  }
}
