package com.wisecoders.dbs.dbms.connect.fx;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.net.ssl.HttpsURLConnection;

class g extends Task {
  private final SampleMediaApp$MediaEntry b;
  
  private boolean c;
  
  public g(SampleMediaApp paramSampleMediaApp, SampleMediaApp$MediaEntry paramSampleMediaApp$MediaEntry) {
    this.b = paramSampleMediaApp$MediaEntry;
    paramSampleMediaApp.g.setVisible(true);
    paramSampleMediaApp.g.progressProperty().bind((ObservableValue)progressProperty());
    paramSampleMediaApp.f();
    paramSampleMediaApp.g();
    this.c = true;
  }
  
  public Void a() {
    Thread.currentThread().setName("DbSchema: Download " + String.valueOf(this.b) + " Video Task.");
    updateMessage("Downloading video from dbschema.com...");
    if (!this.a.d.exists() && 
      !this.a.d.mkdir())
      throw new IOException("Cannot create video download folder"); 
    this.b.f.delete();
    if (this.b.e.exists())
      return null; 
    URL uRL = new URL("https://dbschema.com/video/" + this.b.d);
    HttpsURLConnection httpsURLConnection = (HttpsURLConnection)uRL.openConnection();
    int i = Math.max(1, httpsURLConnection.getContentLength());
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(this.b.f);
      try {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        try {
          InputStream inputStream = httpsURLConnection.getInputStream();
          try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            try {
              byte[] arrayOfByte = new byte[16384];
              int k = 0;
              int j;
              while ((j = bufferedInputStream.read(arrayOfByte)) != -1) {
                bufferedOutputStream.write(arrayOfByte, 0, j);
                k += j;
                if (isCancelled()) {
                  Void void_ = null;
                  bufferedInputStream.close();
                  if (inputStream != null)
                    inputStream.close(); 
                  bufferedOutputStream.close();
                  fileOutputStream.close();
                  return void_;
                } 
                updateProgress(k, (i + 1000));
              } 
              bufferedInputStream.close();
            } catch (Throwable throwable) {
              try {
                bufferedInputStream.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              } 
              throw throwable;
            } 
            if (inputStream != null)
              inputStream.close(); 
          } catch (Throwable throwable) {
            if (inputStream != null)
              try {
                inputStream.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
          bufferedOutputStream.close();
        } catch (Throwable throwable) {
          try {
            bufferedOutputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
        fileOutputStream.close();
      } catch (Throwable throwable) {
        try {
          fileOutputStream.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (Throwable throwable) {
      this.b.f.delete();
      throw throwable;
    } 
    this.b.f.renameTo(this.b.e);
    return null;
  }
  
  protected void succeeded() {
    this.a.g.progressProperty().unbind();
    try {
      MediaPlayer mediaPlayer = new MediaPlayer(new Media(this.b.e.toURI().toString()));
      mediaPlayer.currentTimeProperty().addListener((paramObservableValue, paramDuration1, paramDuration2) -> this.a.g());
      mediaPlayer.volumeProperty().bind((ObservableValue)this.a.a.progressProperty());
      mediaPlayer.errorProperty().addListener((paramObservableValue, paramMediaException1, paramMediaException2) -> {
            if (this.c) {
              paramMediaPlayer.play();
            } else {
              SampleMediaApp.a("Please try again to start the video. " + String.valueOf(paramMediaException2), "Retry");
            } 
            this.c = false;
          });
      mediaPlayer.setAutoPlay(true);
      this.a.b.setMediaPlayer(mediaPlayer);
      Objects.requireNonNull(mediaPlayer);
      Platform.runLater(mediaPlayer::play);
    } catch (Throwable throwable) {
      SampleMediaApp.a(throwable.getLocalizedMessage(), "Error");
    } 
  }
  
  protected void failed() {
    this.a.g.progressProperty().unbind();
    Throwable throwable = getException();
    SampleMediaApp.a(throwable.toString(), "Error");
  }
}
