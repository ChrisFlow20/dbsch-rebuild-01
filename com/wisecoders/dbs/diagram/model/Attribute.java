package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.DataTypeFormat;
import com.wisecoders.dbs.schema.Sequence;

public interface Attribute extends Unit {
  public static final int a = 24;
  
  public static final int b = 1;
  
  public static final int c = 4;
  
  public static final int d = 8;
  
  public static final int e = 16;
  
  public static final int f = 32;
  
  public static final int g = 64;
  
  public static final int h = 128;
  
  public static final int i = 256;
  
  public static final int j = 512;
  
  public static final int k = 1024;
  
  public static final int l = 2048;
  
  public static final int m = 4096;
  
  public static final int n = 8192;
  
  public static final int o = 16384;
  
  public static final int p = 32768;
  
  public static final int q = 65536;
  
  public static final int r = 131072;
  
  public static final int s = 262144;
  
  public static final int t = 524288;
  
  public static final int u = 1048576;
  
  public static final int v = 2097152;
  
  void setTicked(boolean paramBoolean);
  
  boolean isTicked();
  
  String getTypeString(DataTypeFormat paramDataTypeFormat);
  
  boolean isMandatory();
  
  boolean hasMarker(int paramInt);
  
  boolean isSelectable();
  
  short getToDoFlag();
  
  DataType getDataType();
  
  String getIndexingIcon();
  
  Sequence getAssociatedSequence();
  
  AttributeSpec getSpec();
  
  static String a(Attribute paramAttribute) {
    String str = null;
    if (paramAttribute.hasMarker(128)) {
      str = "#";
    } else if (paramAttribute.hasMarker(2048)) {
      str = "d";
    } else if (paramAttribute.hasMarker(512)) {
      str = "t";
    } else if (paramAttribute.hasMarker(8192)) {
      str = "[]";
    } else if (paramAttribute.hasMarker(1024)) {
      str = "c";
    } else if (paramAttribute.hasMarker(256)) {
      str = "b";
    } else if (paramAttribute.hasMarker(4096)) {
      str = "~";
    } 
    return str;
  }
  
  String getNameWithPath();
}
