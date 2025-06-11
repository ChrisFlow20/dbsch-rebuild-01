package com.wisecoders.dbs.sql.fx;

import com.wisecoders.dbs.sql.parser.MatcherCommaPhrase;
import com.wisecoders.dbs.sql.parser.MatcherNode;
import javafx.scene.control.TreeItem;

class a extends TreeItem {
  public a(MatcherNode paramMatcherNode) {
    super(paramMatcherNode);
    if (paramMatcherNode instanceof com.wisecoders.dbs.sql.parser.MatcherStatement)
      for (MatcherCommaPhrase matcherCommaPhrase : paramMatcherNode) {
        a a1 = new a(matcherCommaPhrase);
        getChildren().add(a1);
        for (MatcherNode matcherNode : matcherCommaPhrase)
          a1.getChildren().add(new a(matcherNode)); 
      }  
    setExpanded(true);
  }
}
