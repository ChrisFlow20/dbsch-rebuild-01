package com.wisecoders.dbs.diagram.util.test;

import java.io.File;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

class a extends Thread {
  a(AudioSample paramAudioSample) {}
  
  public void run() {
    AudioFileFormat.Type type = AudioFileFormat.Type.WAVE;
    File file = new File("C:/Downloads/junk.wav");
    try {
      this.a.b.open(this.a.a);
      this.a.b.start();
      AudioSystem.write(new AudioInputStream(this.a.b), type, file);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}
