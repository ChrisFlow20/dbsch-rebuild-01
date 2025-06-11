package com.wisecoders.dbs.sys.fx;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.util.StringConverter;

class a extends StringConverter {
  a(DateTimePicker paramDateTimePicker) {}
  
  public String a(LocalDate paramLocalDate) {
    LocalDateTime localDateTime = this.a.b();
    return (localDateTime != null) ? localDateTime.format(this.a.b) : "";
  }
  
  public LocalDate a(String paramString) {
    if (paramString == null || paramString.isEmpty()) {
      this.a.c.set(null);
      return null;
    } 
    this.a.c.set(LocalDateTime.parse(paramString, this.a.b));
    return ((LocalDateTime)this.a.c.get()).toLocalDate();
  }
}
