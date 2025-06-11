package com.wisecoders.dbs.sys;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;

public class ColorUtil {
  public static int a(Color paramColor) {
    int i = (int)Math.round(paramColor.getRed() * 255.0D) & 0xFF;
    int j = (int)Math.round(paramColor.getGreen() * 255.0D) & 0xFF;
    int k = (int)Math.round(paramColor.getBlue() * 255.0D) & 0xFF;
    return i << 16 | j << 8 | k;
  }
  
  public static Color a(int paramInt) {
    return new Color((paramInt >> 16 & 0xFF) / 255.0D, (paramInt >> 8 & 0xFF) / 255.0D, (paramInt & 0xFF) / 255.0D, 1.0D);
  }
  
  public static String b(Color paramColor) {
    return String.format("%02X%02X%02X", new Object[] { Integer.valueOf((int)(paramColor.getRed() * 255.0D)), 
          Integer.valueOf((int)(paramColor.getGreen() * 255.0D)), 
          Integer.valueOf((int)(paramColor.getBlue() * 255.0D)) });
  }
  
  private static final Map a = new HashMap<>();
  
  public static Color a(Color paramColor1, Color paramColor2) {
    if (paramColor1 == null)
      paramColor1 = Color.WHITE; 
    int i = a(paramColor2.getHue(), paramColor1.getSaturation(), paramColor1.getBrightness());
    Color color = (Color)a.get(Integer.valueOf(i));
    if (color == null) {
      color = Color.hsb(paramColor2.getHue(), paramColor1.getSaturation(), paramColor1.getBrightness());
      a.put(Integer.valueOf(i), color);
    } 
    return color;
  }
  
  public static int a(double paramDouble1, double paramDouble2, double paramDouble3) {
    return (int)(paramDouble1 * 255.0D) << 16 | (int)(paramDouble2 * 255.0D) << 8 | (int)(paramDouble3 * 255.0D);
  }
  
  public static Color a(Color paramColor, int paramInt) {
    if (paramColor == null)
      paramColor = Color.WHITE; 
    return Color.hsb(b(paramInt), paramColor.getSaturation(), paramColor.getBrightness());
  }
  
  public static String b(Color paramColor1, Color paramColor2) {
    return b(a(paramColor1, paramColor2));
  }
  
  public static double b(int paramInt) {
    int m, n, i = paramInt >> 16 & 0xFF;
    int j = paramInt >> 8 & 0xFF;
    int k = paramInt & 0xFF;
    if (i < j) {
      m = i;
      n = j;
    } else {
      m = j;
      n = i;
    } 
    if (k > n) {
      n = k;
    } else if (k < m) {
      m = k;
    } 
    float f1 = 0.0F, f2 = 0.0F;
    if (n == 0) {
      f1 = 0.0F;
    } else {
      f1 = (n - m) / n;
    } 
    if (f1 == 0.0F) {
      f2 = 0.0F;
    } else {
      float f = ((n - m) * 6);
      if (i == n) {
        f2 = (j - k) / f;
      } else if (j == n) {
        f2 = 0.33333334F + (k - i) / f;
      } else {
        f2 = 0.6666667F + (i - j) / f;
      } 
      if (f2 < 0.0F)
        f2++; 
    } 
    return f2;
  }
  
  public static float[] a(int paramInt, float[] paramArrayOffloat) {
    int i = paramInt >> 16 & 0xFF;
    int j = paramInt >> 8 & 0xFF;
    int k = paramInt & 0xFF;
    return a(i, j, k, paramArrayOffloat);
  }
  
  public static float[] a(int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOffloat) {
    int i, j;
    if (paramArrayOffloat == null)
      paramArrayOffloat = new float[3]; 
    if (paramInt1 < paramInt2) {
      i = paramInt1;
      j = paramInt2;
    } else {
      i = paramInt2;
      j = paramInt1;
    } 
    if (paramInt3 > j) {
      j = paramInt3;
    } else if (paramInt3 < i) {
      i = paramInt3;
    } 
    paramArrayOffloat[2] = j / 255.0F;
    if (j == 0) {
      paramArrayOffloat[1] = 0.0F;
    } else {
      paramArrayOffloat[1] = (j - i) / j;
    } 
    if (paramArrayOffloat[1] == 0.0F) {
      paramArrayOffloat[0] = 0.0F;
    } else {
      float f = ((j - i) * 6);
      if (paramInt1 == j) {
        paramArrayOffloat[0] = (paramInt2 - paramInt3) / f;
      } else if (paramInt2 == j) {
        paramArrayOffloat[0] = 0.33333334F + (paramInt3 - paramInt1) / f;
      } else {
        paramArrayOffloat[0] = 0.6666667F + (paramInt1 - paramInt2) / f;
      } 
      if (paramArrayOffloat[0] < 0.0F)
        paramArrayOffloat[0] = paramArrayOffloat[0] + 1.0F; 
    } 
    return paramArrayOffloat;
  }
  
  public static int b(double paramDouble1, double paramDouble2, double paramDouble3) {
    double d1 = (paramDouble1 % 360.0D + 360.0D) % 360.0D;
    paramDouble1 = d1 / 360.0D;
    double d2 = 0.0D, d3 = 0.0D, d4 = 0.0D;
    d2 = d3 = d4 = paramDouble3;
    double d5 = (paramDouble1 - Math.floor(paramDouble1)) * 6.0D;
    double d6 = d5 - Math.floor(d5);
    double d7 = paramDouble3 * (1.0D - paramDouble2);
    double d8 = paramDouble3 * (1.0D - paramDouble2 * d6);
    double d9 = paramDouble3 * (1.0D - paramDouble2 * (1.0D - d6));
    switch ((int)d5) {
      case 0:
        d2 = paramDouble3;
        d3 = d9;
        d4 = d7;
        break;
      case 1:
        d2 = d8;
        d3 = paramDouble3;
        d4 = d7;
        break;
      case 2:
        d2 = d7;
        d3 = paramDouble3;
        d4 = d9;
        break;
      case 3:
        d2 = d7;
        d3 = d8;
        d4 = paramDouble3;
        break;
      case 4:
        d2 = d9;
        d3 = d7;
        d4 = paramDouble3;
        break;
      case 5:
        d2 = paramDouble3;
        d3 = d7;
        d4 = d8;
        break;
    } 
    int i = (int)Math.round(d2 * 255.0D) & 0xFF;
    int j = (int)Math.round(d3 * 255.0D) & 0xFF;
    int k = (int)Math.round(d4 * 255.0D) & 0xFF;
    return i << 16 | j << 8 | k;
  }
}
