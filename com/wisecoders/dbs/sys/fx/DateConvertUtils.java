package com.wisecoders.dbs.sys.fx;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateConvertUtils {
  public static LocalDate a(Date paramDate) {
    return a(paramDate, ZoneId.systemDefault());
  }
  
  public static LocalDate a(Date paramDate, ZoneId paramZoneId) {
    if (paramDate == null)
      return null; 
    if (paramDate instanceof Date)
      return ((Date)paramDate).toLocalDate(); 
    return Instant.ofEpochMilli(paramDate.getTime()).atZone(paramZoneId).toLocalDate();
  }
  
  public static LocalDateTime b(Date paramDate) {
    return b(paramDate, ZoneId.systemDefault());
  }
  
  public static LocalDateTime b(Date paramDate, ZoneId paramZoneId) {
    if (paramDate == null)
      return null; 
    if (paramDate instanceof Timestamp)
      return ((Timestamp)paramDate).toLocalDateTime(); 
    return Instant.ofEpochMilli(paramDate.getTime()).atZone(paramZoneId).toLocalDateTime();
  }
  
  public static Date a(Object paramObject) {
    return a(paramObject, ZoneId.systemDefault());
  }
  
  public static Date a(Object paramObject, ZoneId paramZoneId) {
    if (paramObject == null)
      return null; 
    if (paramObject instanceof Date || paramObject instanceof Timestamp)
      return new Date(((Date)paramObject).getTime()); 
    if (paramObject instanceof Date)
      return (Date)paramObject; 
    if (paramObject instanceof LocalDate)
      return Date.from(((LocalDate)paramObject).atStartOfDay(paramZoneId).toInstant()); 
    if (paramObject instanceof LocalDateTime)
      return Date.from(((LocalDateTime)paramObject).atZone(paramZoneId).toInstant()); 
    if (paramObject instanceof ZonedDateTime)
      return Date.from(((ZonedDateTime)paramObject).toInstant()); 
    if (paramObject instanceof Instant)
      return Date.from((Instant)paramObject); 
    throw new UnsupportedOperationException("Don't know hot to convert " + paramObject.getClass().getName() + " to java.util.Date");
  }
  
  public static Timestamp b(Object paramObject) {
    return b(paramObject, ZoneId.systemDefault());
  }
  
  public static Timestamp b(Object paramObject, ZoneId paramZoneId) {
    if (paramObject == null)
      return null; 
    if (paramObject instanceof Date || paramObject instanceof Timestamp)
      return new Timestamp(((Date)paramObject).getTime()); 
    if (paramObject instanceof Date)
      return new Timestamp(((Date)paramObject).getTime()); 
    if (paramObject instanceof LocalDate)
      return Timestamp.from(((LocalDate)paramObject).atStartOfDay(paramZoneId).toInstant()); 
    if (paramObject instanceof LocalDateTime)
      return Timestamp.from(((LocalDateTime)paramObject).atZone(paramZoneId).toInstant()); 
    if (paramObject instanceof ZonedDateTime)
      return Timestamp.from(((ZonedDateTime)paramObject).toInstant()); 
    if (paramObject instanceof Instant)
      return Timestamp.from((Instant)paramObject); 
    throw new UnsupportedOperationException("Don't know hot to convert " + paramObject.getClass().getName() + " to java.sql.Timestamp");
  }
  
  public static Instant c(Date paramDate) {
    if (paramDate == null)
      return null; 
    return Instant.ofEpochMilli(paramDate.getTime());
  }
  
  public static ZonedDateTime d(Date paramDate) {
    return c(paramDate, ZoneId.systemDefault());
  }
  
  public static ZonedDateTime c(Date paramDate, ZoneId paramZoneId) {
    if (paramDate == null)
      return null; 
    return c(paramDate).atZone(paramZoneId);
  }
}
