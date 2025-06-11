package com.wisecoders.dbs.dbms.connect.fx;

import javafx.scene.image.Image;

class i {
  public String a;
  
  public String b;
  
  public String c;
  
  public Image d;
  
  public i(SampleMenuApp paramSampleMenuApp, String paramString1, String paramString2, String paramString3) {
    this.a = paramString1;
    this.b = paramString2;
    this.c = paramString3;
    this.d = new Image(getClass().getResourceAsStream("/icons/new.png"));
  }
}
