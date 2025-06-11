package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSyntaxOptionPane;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.scene.Node;
import javafx.scene.control.Tab;

class v extends Tab {
  final FxSyntaxOptionPane a = new FxSyntaxOptionPane(this.b.b, (Dbms.get(this.b.a)).tableOptions);
  
  v(FxTableEditor paramFxTableEditor) {
    this.a.a.c(paramFxTableEditor.rx.H("options.promptText"));
    setGraphic((Node)BootstrapIcons.card_checklist.glyph(new String[0]));
    setText((Dbms.get(paramFxTableEditor.a)).tableOptions.i());
    setContent((Node)this.a);
  }
}
