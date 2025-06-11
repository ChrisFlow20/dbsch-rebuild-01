package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import javafx.util.StringConverter;

class c extends StringConverter {
  c(FxDateTimePicker paramFxDateTimePicker) {}
  
  public String a(LocalDate paramLocalDate) {
    LocalDateTime localDateTime = this.a.b();
    return (localDateTime != null) ? localDateTime.format(this.a.a) : "";
  }
  
  public LocalDate a(String paramString) {
    if (StringUtil.isEmpty(paramString)) {
      this.a.b.set(null);
      return null;
    } 
    try {
      this.a.b.set(LocalDateTime.parse(paramString, this.a.a));
    } catch (DateTimeParseException dateTimeParseException) {
      Log.b(dateTimeParseException);
    } 
    return (this.a.b.get() != null) ? ((LocalDateTime)this.a.b.get()).toLocalDate() : null;
  }
}
