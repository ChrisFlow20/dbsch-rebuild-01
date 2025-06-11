package com.wisecoders.dbs.project.pdf;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.schema.Expose;
import java.awt.Color;
import java.io.File;

public class PdfBaseFonts {
  private final Expose c;
  
  public static final String a = "unifont.ttf";
  
  public static final File b = Sys.l.resolve("unifont.ttf").toFile();
  
  PdfBaseFonts(Expose paramExpose) {
    this.c = paramExpose;
    if (paramExpose.is("unicode"))
      FontFactory.register(b.getAbsolutePath(), "Unicode"); 
  }
  
  public Font a(float paramFloat) {
    return new Font(a(), paramFloat);
  }
  
  public Font a(float paramFloat, int paramInt) {
    return new Font(a(), paramFloat, paramInt);
  }
  
  public Font a(float paramFloat, int paramInt, Color paramColor) {
    return new Font(a(), paramFloat, paramInt, paramColor);
  }
  
  public BaseFont a() {
    return this.c.is("unicode") ? 
      BaseFont.createFont(b.getAbsolutePath(), "Identity-H", true) : 
      BaseFont.createFont("Helvetica", "Cp1252", false);
  }
}
