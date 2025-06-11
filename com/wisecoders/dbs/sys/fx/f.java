package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

class f extends SkinBase {
  private final TextFlow b = new TextFlow();
  
  final ContextMenu a = new ContextMenu();
  
  private boolean c;
  
  private final StringBuilder d;
  
  f(HtmlLabel paramHtmlLabel, boolean paramBoolean) {
    super(paramHtmlLabel);
    this.c = true;
    this.d = new StringBuilder();
    this.b.setOnContextMenuRequested(paramContextMenuEvent -> this.a.show((Node)this.b, paramContextMenuEvent.getScreenX(), paramContextMenuEvent.getScreenY()));
    this.b.getStyleClass().addAll((Collection)paramHtmlLabel.getStyleClass());
    MenuItem menuItem = new MenuItem("Copy");
    menuItem.setOnAction(paramActionEvent -> b());
    this.a.getItems().add(menuItem);
    c();
    if (paramBoolean) {
      ScrollPane scrollPane = new ScrollPane((Node)this.b);
      scrollPane.setFitToWidth(true);
      scrollPane.setPannable(true);
      getChildren().add(scrollPane);
    } else {
      getChildren().add(this.b);
    } 
    this.b.setOnMousePressed(paramMouseEvent -> {
          if (paramMouseEvent.isPrimaryButtonDown()) {
            PseudoClass pseudoClass = PseudoClass.getPseudoClass("selected");
            this.b.pseudoClassStateChanged(pseudoClass, !this.b.getPseudoClassStates().contains(pseudoClass));
          } 
        });
    paramHtmlLabel.a().addListener((paramObservableValue, paramString1, paramString2) -> c());
  }
  
  private void b() {
    Clipboard clipboard = Clipboard.getSystemClipboard();
    ClipboardContent clipboardContent = new ClipboardContent();
    clipboardContent.putString(a());
    clipboardContent.putHtml(((HtmlLabel)getSkinnable()).b());
    clipboard.setContent((Map)clipboardContent);
  }
  
  private void c() {
    String str = ((HtmlLabel)getSkinnable()).b();
    if (str == null || str.isEmpty()) {
      this.b.getChildren().clear();
      return;
    } 
    ArrayList arrayList = new ArrayList();
    Document document = Jsoup.parse(str);
    a(arrayList, (Node)document.body());
    this.b.getStyleClass().add("html-label");
    this.b.getChildren().setAll(arrayList);
  }
  
  public String a() {
    return this.d.toString();
  }
  
  private void a(List<Text> paramList, Node paramNode) {
    if (paramNode instanceof Element) {
      Text text;
      Hyperlink hyperlink;
      Element element = (Element)paramNode;
      this.c = false;
      String str = element.tag().toString().toLowerCase();
      switch (str) {
        case "h1":
        case "h2":
        case "h3":
          text = new Text("\n" + element.text() + "\n");
          text.getStyleClass().addAll((Object[])new String[] { str });
          this.d.append(element.text()).append("\n");
          paramList.add(text);
          this.c = true;
          return;
        case "li":
          paramList.add(new Text("\n > "));
          if (element.childNodeSize() == 0) {
            text = new Text(" » " + element.text() + "\n");
            paramList.add(text);
            this.d.append(" » ").append(element.text()).append("\n");
            text.getStyleClass().add("text");
          } 
          this.c = true;
          break;
        case "br":
          paramList.add(new Text("\n"));
          this.d.append("\n");
          this.c = true;
          return;
        case "b":
        case "strong":
          text = new Text(element.text());
          text.getStyleClass().add("text");
          text.setStyle("-fx-font-weight:bold");
          this.d.append(element.text());
          paramList.add(text);
          return;
        case "a":
          hyperlink = new Hyperlink(element.ownText());
          this.d.append(element.ownText());
          hyperlink.setUnderline(true);
          hyperlink.setPadding(new Insets(0.0D, 5.0D, 0.0D, 0.0D));
          paramList.add(hyperlink);
          hyperlink.setOnAction(paramActionEvent -> {
                String str = paramElement.attr("href");
                if (((HtmlLabel)getSkinnable()).d() != null) {
                  ((HtmlLabel)getSkinnable()).d().handle((Event)paramActionEvent);
                } else if (str.startsWith("documentation:")) {
                  WebBrowserExternal.a(getNode().getScene(), str.substring("documentation:".length()));
                } else {
                  WebBrowserExternal.a(getNode().getScene(), str);
                } 
              });
          return;
        case "p":
          paramList.add(new Text("\n\n"));
          this.d.append("\n\n");
          this.c = true;
          break;
      } 
    } else if (paramNode instanceof TextNode) {
      TextNode textNode = (TextNode)paramNode;
      String str = textNode.text();
      if (StringUtil.isFilledTrim(str)) {
        if (this.c || paramList.isEmpty())
          while (str.startsWith(" ") || str.startsWith("\n"))
            str = str.substring(1);  
        Text text = new Text(str);
        this.d.append(str);
        text.getStyleClass().setAll((Object[])new String[] { "text" });
        paramList.add(text);
        this.c = false;
      } 
    } 
    if (paramNode.childNodeSize() > 0)
      for (Node node : paramNode.childNodes())
        a(paramList, node);  
  }
}
