package com.wisecoders.dbs.sys.fx;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import javafx.scene.control.DatePicker;

public class DatePicker$ extends DatePicker {
  public Date a() {
    setValue(getConverter().fromString(getEditor().getText()));
    Instant instant = Instant.from((TemporalAccessor)getValue());
    return Date.from(instant);
  }
  
  public void a(Date paramDate) {
    if (paramDate != null) {
      Instant instant = paramDate.toInstant();
      ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
      LocalDate localDate = zonedDateTime.toLocalDate();
      valueProperty().set(localDate);
    } 
  }
}
