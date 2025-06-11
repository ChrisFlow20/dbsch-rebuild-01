package com.wisecoders.dbs.loader.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.loader.tasks.LoaderPresetSeparators;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.apache.commons.csv.CSVFormat;

public class FxLoaderFormatDialog extends Dialog$ {
  private final ComboBox a = new ComboBox();
  
  private final ComboBox b = new ComboBox();
  
  private final ComboBox c = new ComboBox();
  
  private final ComboBox d = new ComboBox();
  
  private CSVFormat.Builder e;
  
  FxLoaderFormatDialog(WorkspaceWindow paramWorkspaceWindow, CSVFormat.Builder paramBuilder) {
    super(paramWorkspaceWindow);
    this.e = paramBuilder;
    this.a.setEditable(false);
    this.b.setEditable(false);
    this.c.setEditable(false);
    this.d.setEditable(false);
    for (String str : LoaderPresetSeparators.c)
      this.a.getItems().add(str); 
    CSVFormat cSVFormat = paramBuilder.build();
    this.a.setValue(cSVFormat.getDelimiterString());
    this.a.setButtonCell(new g());
    this.a.setCellFactory(paramListView -> new g());
    this.b.getItems().addAll((Object[])LoaderPresetSeparators.d);
    this.b.setValue(cSVFormat.getRecordSeparator());
    this.b.setButtonCell(new g());
    this.b.setCellFactory(paramListView -> new g());
    for (String str : LoaderPresetSeparators.e)
      this.c.getItems().add(str); 
    this.c.setValue("" + cSVFormat.getQuoteCharacter());
    for (String str : LoaderPresetSeparators.f)
      this.d.getItems().add(str); 
    this.d.setValue((cSVFormat.getEscapeCharacter() != null) ? ("" + cSVFormat.getEscapeCharacter()) : "Not Set");
    setResultConverter(paramButtonType -> 
        (paramButtonType != null && paramButtonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) ? (CSVFormat.Builder)getResult() : null);
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l();
    gridPane$.a((Node)this.rx.e("delimiterLabel"), "0,0,r,c");
    gridPane$.a((Node)this.a, "1,0,l,c");
    gridPane$.a((Node)this.rx.e("recordSeparatorLabel"), "0,1,r,c");
    gridPane$.a((Node)this.b, "1,1,l,c");
    gridPane$.a((Node)this.rx.e("quoteLabel"), "0,2,r,c");
    gridPane$.a((Node)this.c, "1,2,l,c");
    gridPane$.a((Node)this.rx.e("escapeLabel"), "0,3,r,c");
    gridPane$.a((Node)this.d, "1,3,l,c");
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    try {
      if (this.a.getValue() != null)
        this.e = this.e.setDelimiter(((String)this.a.getValue()).charAt(0)); 
      if (this.b.getValue() != null) {
        String str = (String)this.b.getValue();
        this.e = this.e.setRecordSeparator(str);
      } 
      if (StringUtil.isFilledTrim(this.c.getValue()))
        this.e = this.e.setQuote(((String)this.c.getValue()).charAt(0)); 
      if (StringUtil.isFilledTrim(this.d.getValue()))
        if ("Not Set".equals(this.d.getValue())) {
          this.e = this.e.setEscape(null);
        } else {
          this.e = this.e.setEscape(((String)this.d.getValue()).charAt(0));
        }  
      setResult(this.e);
      return true;
    } catch (Throwable throwable) {
      this.rx.b(getDialogPane().getScene(), throwable.getLocalizedMessage(), throwable);
      return false;
    } 
  }
}
