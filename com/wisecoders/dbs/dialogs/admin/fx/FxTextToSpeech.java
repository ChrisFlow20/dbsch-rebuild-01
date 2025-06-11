package com.wisecoders.dbs.dialogs.admin.fx;

import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.project.history.History;
import com.wisecoders.dbs.project.history.HistoryFile;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.io.File;
import java.nio.file.Paths;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Window;

public class FxTextToSpeech extends Dialog$ {
  private static final String a = "TextToSpeechKey";
  
  private static final String b = "TextToSpeechAutoPlay";
  
  private static final String c = "TextToSpeechFolder";
  
  private static final String d = "TextToSpeechVoice";
  
  private final TextField e = new TextField();
  
  private final ComboBox f = new ComboBox();
  
  private final ComboBox i = new ComboBox();
  
  private final CheckBox j;
  
  private final History k = new History("textToSpeech");
  
  private MediaPlayer l;
  
  public FxTextToSpeech(Window paramWindow) {
    super(paramWindow);
    this.j = this.rx.h("autoPlayCheckBox", Pref.b("TextToSpeechAutoPlay", true));
    this.rx.a("flagIsPlaying", () -> (this.l != null));
    this.e.setText(Pref.d("TextToSpeechKey", (String)null));
    this.f.setEditable(true);
    this.f.setMaxWidth(Double.MAX_VALUE);
    for (HistoryFile historyFile : this.k.c())
      this.f.getItems().add(historyFile.b.getAbsolutePath()); 
    this.i.getItems().addAll((Object[])new String[] { 
          "en-AU-KimNeural", "en-AU-TinaNeural", "en-US-AvaMultilingualNeural", "en-US-CoraNeural", "en-US-NancyNeural", "en-US-EmmaNeural", "en-US-JennyNeural", "en-US-AndrewMultilingualNeural", "en-US-JasonNeural", "en-US-BrandonNeural", 
          "en-US-SteffanNeural", "en-US-EricNeural" });
    this.i.setValue(Pref.d("TextToSpeechVoice", "en-AU-TinaNeural"));
    this.rx.b();
  }
  
  public Node createContentPane() {
    return (Node)(new GridPane$()).l().a(new int[] { -2, -1, -2 }).b(new int[] { -2, -2, -2, -2, -1 }).a((Node)this.rx.e("subscriptionLabel"), "0,0,r,c")
      .a((Node)this.e, "1,0,f,c")
      .a((Node)this.rx.e("inputFile"), "0,1,r,c")
      .a((Node)this.f, "1,1,f,c")
      .a((Node)this.rx.j("chooseFile"), "2,1,c,c")
      .a((Node)this.rx.e("voiceLabel"), "0,2,r,c")
      .a((Node)this.i, "1,2,f,c")
      .a((Node)this.rx.j("voices"), "2,2,l,c")
      .a((Node)this.j, "1,3,l,c");
  }
  
  public void createButtons() {
    createActionButton("generate");
    createActionButton("stopPlay");
    createCancelButton();
  }
  
  public boolean apply() {
    if (StringUtil.isEmpty(this.f.getValue())) {
      this.rx.c(getDialogScene(), "Please choose an output file");
      return false;
    } 
    if (StringUtil.isEmpty(this.i.getValue())) {
      this.rx.c(getDialogScene(), "Please choose a voice");
      return false;
    } 
    return true;
  }
  
  @Action
  public void chooseFile() {
    File file1 = Paths.get(Pref.c("TextToSpeechFolder", System.getProperty("user.home")), new String[0]).toFile();
    File file2 = FxFileChooser.a(getDialogScene(), "Input File", FileOperation.a, file1, new FileType[] { FileType.l, FileType.i });
    if (file2 != null) {
      this.f.setValue(file2.getPath());
      Pref.a("TextToSpeechFolder", file2.getParentFile().getPath());
    } 
  }
  
  private static File a(File paramFile) {
    String str = paramFile.getName();
    int i;
    if ((i = str.lastIndexOf(".")) > -1)
      str = str.substring(0, i); 
    File file = new File(paramFile.getParentFile().toURI().resolve("out/"));
    if (!file.exists())
      file.mkdir(); 
    return new File(file.toURI().resolve(str + ".mp3"));
  }
  
  @Action(b = "flagIsPlaying")
  public void stopPlay() {
    if (this.l != null)
      this.l.stop(); 
    this.rx.b();
  }
  
  @Action
  public void voices() {
    WebBrowserExternal.a(getDialogScene(), "https://speech.azure.cn/portal/voicegallery");
  }
  
  @Action
  public Task generate() {
    stopPlay();
    File file1 = Paths.get((String)this.f.getValue(), new String[0]).toFile();
    File file2 = a(file1);
    return new FxTextToSpeech$1(this, file1, file2);
  }
}
