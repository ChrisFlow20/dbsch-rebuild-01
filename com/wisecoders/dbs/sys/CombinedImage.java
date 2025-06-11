package com.wisecoders.dbs.sys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class CombinedImage {
  private final List a;
  
  public CombinedImage(Image... paramVarArgs) {
    this.a = new ArrayList();
    Collections.addAll(this.a, paramVarArgs);
  }
  
  public void a(Image paramImage) {
    this.a.add(paramImage);
  }
  
  public void a() {
    this.a.clear();
  }
  
  public Image b() {
    if (!this.a.isEmpty()) {
      int i = 0, j = 0;
      for (Image image : this.a) {
        i = (int)(i + image.getWidth());
        j = (int)Math.max(j, image.getHeight());
      } 
      WritableImage writableImage = new WritableImage(i, j);
      int k = 0;
      for (Image image : this.a) {
        a(writableImage, image, k);
        k = (int)(k + image.getWidth());
      } 
      return (Image)writableImage;
    } 
    return null;
  }
  
  public Image c() {
    if (!this.a.isEmpty()) {
      int i = (int)((Image)this.a.get(0)).getWidth();
      int j = (int)((Image)this.a.get(0)).getHeight();
      int[][] arrayOfInt = new int[i][j];
      boolean bool = true;
      for (Image image : this.a) {
        PixelReader pixelReader = image.getPixelReader();
        for (byte b1 = 0; b1 < image.getWidth(); b1++) {
          for (byte b2 = 0; b2 < image.getHeight(); b2++) {
            int k = pixelReader.getArgb(b1, b2);
            int m = k >> 24 & 0xFF;
            if (b1 < i && b2 < j)
              if (bool) {
                arrayOfInt[b1][b2] = k;
              } else if (m > 5) {
                arrayOfInt[b1][b2] = k;
              }  
          } 
        } 
        bool = false;
      } 
      WritableImage writableImage = new WritableImage(i, j);
      PixelWriter pixelWriter = writableImage.getPixelWriter();
      for (byte b = 0; b < i; b++) {
        for (byte b1 = 0; b1 < j; b1++)
          pixelWriter.setArgb(b, b1, arrayOfInt[b][b1]); 
      } 
      return (Image)writableImage;
    } 
    return null;
  }
  
  private static void a(WritableImage paramWritableImage, Image paramImage, int paramInt) {
    int i = (int)paramImage.getHeight();
    int j = (int)paramImage.getWidth();
    PixelReader pixelReader = paramImage.getPixelReader();
    PixelWriter pixelWriter = paramWritableImage.getPixelWriter();
    for (byte b = 0; b < i; b++) {
      for (byte b1 = 0; b1 < j; b1++) {
        Color color = pixelReader.getColor(b1, b);
        pixelWriter.setColor(paramInt + b1, b, color);
      } 
    } 
  }
  
  public static Color a(Color paramColor1, Color paramColor2, int paramInt) {
    float f = paramInt / 255.0F;
    int i = (int)(paramColor1.getRed() * (1.0F - f) + paramColor2.getRed() * f);
    int j = (int)(paramColor1.getGreen() * (1.0F - f) + paramColor2.getGreen() * f);
    int k = (int)(paramColor1.getBlue() * (1.0F - f) + paramColor2.getBlue() * f);
    return new Color(i, j, k, 1.0D);
  }
}
