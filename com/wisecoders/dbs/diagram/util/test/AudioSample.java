package com.wisecoders.dbs.diagram.util.test;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioSample {
  AudioFormat a;
  
  TargetDataLine b;
  
  public static void main(String[] paramArrayOfString) {
    new AudioSample();
  }
  
  public AudioSample() {
    Runtime.getRuntime().addShutdownHook(new Thread(new AudioSample$1(this)));
    a();
  }
  
  private void a() {
    try {
      this.a = b();
      DataLine.Info info = new DataLine.Info(TargetDataLine.class, this.a);
      this.b = (TargetDataLine)AudioSystem.getLine(info);
      (new a(this)).start();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private AudioFormat b() {
    float f = 8000.0F;
    byte b = 16;
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = false;
    return new AudioFormat(f, b, bool1, bool2, bool3);
  }
}
