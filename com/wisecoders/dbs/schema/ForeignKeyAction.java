package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public enum ForeignKeyAction {
  noAction, cascade, setNull, setDefault, restrict;
  
  public static ForeignKeyAction decode(short paramShort) {
    switch (paramShort) {
      case 0:
        return cascade;
      case 4:
        return setDefault;
      case 2:
        return setNull;
      case 1:
        return restrict;
    } 
    return noAction;
  }
  
  public boolean compatibleWith(ForeignKeyAction paramForeignKeyAction) {
    return (this == paramForeignKeyAction || (this == noAction && paramForeignKeyAction == restrict) || (this == restrict && paramForeignKeyAction == noAction));
  }
  
  public static ForeignKeyAction fromString(String paramString) {
    if (paramString != null) {
      switch (paramString.trim().toLowerCase()) {
        case "restrict":
          return restrict;
        case "set default":
          return setDefault;
        case "set null":
          return setNull;
        case "cascade":
          return cascade;
      } 
      return noAction;
    } 
    return noAction;
  }
}
