package com.wisecoders.dbs.sys;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class ImageUtil {
  private boolean a = false;
  
  public void a(Graphics paramGraphics, Image paramImage, int paramInt1, int paramInt2) {
    Object object = new Object();
    long l = System.currentTimeMillis();
    ImageObserver imageObserver = (paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5) -> {
        if (paramInt1 < 32)
          return true; 
        this.a = true;
        synchronized (paramObject) {
          paramObject.notify();
        } 
        return false;
      };
    synchronized (object) {
      if (!paramGraphics.drawImage(paramImage, paramInt1, paramInt2, imageObserver))
        while (!this.a && System.currentTimeMillis() - l < 3000L) {
          try {
            object.wait(500L);
          } catch (InterruptedException interruptedException) {}
        }  
    } 
  }
}
