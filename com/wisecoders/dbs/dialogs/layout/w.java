package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSyntaxOptionPane;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.scene.Node;
import javafx.scene.control.Tab;

class w extends Tab {
  final FxSyntaxOptionPane a = new FxSyntaxOptionPane(this.c.b, (Dbms.get(this.c.a)).preScriptOptions);
  
  final FxSyntaxOptionPane b = new FxSyntaxOptionPane(this.c.b, (Dbms.get(this.c.a)).postScriptOptions);
  
  w(FxTableEditor paramFxTableEditor) {
    setGraphic((Node)BootstrapIcons.filetype_sql.glyph(new String[0]));
    this.a.a.c(paramFxTableEditor.rx.H("preScriptOptions.promptText"));
    this.b.a.c(paramFxTableEditor.rx.H("postScriptOptions.promptText"));
    setText((Dbms.get(paramFxTableEditor.a)).tableOptions.i());
    GridPane$ gridPane$ = (new GridPane$()).l().a(new int[] { -1, -1 }).b(new int[] { -2, -1 });
    gridPane$.a((Node)paramFxTableEditor.rx.h("preScriptSeparator"), "0,0,f,c");
    gridPane$.a((Node)paramFxTableEditor.rx.h("postScriptSeparator"), "1,0,f,c");
    gridPane$.a((Node)this.a, "0,1,f,f");
    gridPane$.a((Node)this.b, "1,1,f,f");
    setContent((Node)gridPane$);
  }
}
