package com.wisecoders.dbs.loader.fx;

import javafx.scene.control.ListCell;

class g extends ListCell {
  protected void a(String paramString, boolean paramBoolean) {
    super.updateItem(paramString, paramBoolean);
    if (paramString != null && !paramBoolean) {
      switch (paramString) {
        case " ":
          setText("Space (' ')");
          return;
        case "\n":
          setText("LF (\\n)");
          return;
        case "\r":
          setText("CR (\\r)");
          return;
        case "\r\n":
          setText("CRLF (\\r\\n)");
          return;
        case "\t":
          setText("Tab (\\t)");
          return;
      } 
      setText(paramString);
    } 
  }
}
