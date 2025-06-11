package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.diagram.model.PropertyAddOnFolder;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.PropertyAddOn;
import com.wisecoders.dbs.sys.EscapeChars;
import com.wisecoders.dbs.sys.StringAsRegEx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.RowPane$;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SyntaxOption {
  public final SyntaxOption a;
  
  private final SyntaxOption$Type g;
  
  public final String b;
  
  public final String c;
  
  public boolean d = false;
  
  private List h = null;
  
  public final SimpleStringProperty e;
  
  private boolean i = false;
  
  private static final String j = "\\s*(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?\\s*";
  
  private static final String k = ".+";
  
  private static final String l = "\\w+";
  
  private Node m;
  
  protected ToggleGroup f;
  
  SyntaxOption(SyntaxOption paramSyntaxOption, String paramString, SyntaxOption$Type paramSyntaxOption$Type) {
    this.a = paramSyntaxOption;
    if (paramSyntaxOption$Type == SyntaxOption$Type.e) {
      String[] arrayOfString = paramString.split("=");
      this.b = (arrayOfString.length > 1) ? arrayOfString[0] : paramString;
      this.c = (arrayOfString.length > 1) ? arrayOfString[1] : null;
      this.e = new SimpleStringProperty();
    } else if (paramSyntaxOption$Type == SyntaxOption$Type.f) {
      this



        
        .b = paramString.replaceAll("\\\\\\{", EscapeChars.forReplacementString("{")).replaceAll("\\\\}", "}").replaceAll("\\\\\\[", "[").replaceAll("\\\\}", "]").replaceAll("\\\\\\|", "|");
      this.e = null;
      this.c = null;
    } else {
      this.b = paramString;
      this.c = null;
      this.e = null;
      if (!o()) {
        if (paramSyntaxOption$Type == SyntaxOption$Type.b)
          paramSyntaxOption$Type = SyntaxOption$Type.g; 
        p();
      } 
    } 
    this.g = paramSyntaxOption$Type;
    if ((paramSyntaxOption$Type == SyntaxOption$Type.b || paramSyntaxOption$Type == SyntaxOption$Type.c) && paramSyntaxOption != null && paramSyntaxOption.a != null)
      paramSyntaxOption.a.b(true); 
  }
  
  private boolean o() {
    StringBuilder stringBuilder = new StringBuilder();
    byte b = 0;
    boolean bool = false;
    for (char c : this.b.toCharArray()) {
      if (bool) {
        stringBuilder.append('\\').append(c);
        bool = false;
      } else {
        switch (c) {
          case '\\':
            bool = true;
            break;
          case '[':
          case '{':
            b++;
            stringBuilder.append(c);
            break;
          case '|':
            if (b == 0) {
              a(stringBuilder, SyntaxOption$Type.d);
              break;
            } 
            stringBuilder.append(c);
            break;
          case ']':
          case '}':
            b--;
            stringBuilder.append(c);
            break;
          default:
            stringBuilder.append(c);
            break;
        } 
      } 
    } 
    if (g() || this.h != null) {
      a(stringBuilder, SyntaxOption$Type.d);
      return true;
    } 
    return false;
  }
  
  private void p() {
    StringBuilder stringBuilder = new StringBuilder();
    byte b = 0;
    char c = Character.MIN_VALUE;
    boolean bool1 = false;
    boolean bool2 = false;
    for (char c1 : this.b.toCharArray()) {
      if (bool2) {
        stringBuilder.append('\\').append(c1);
        bool2 = false;
      } else {
        switch (c1) {
          case '\\':
            bool2 = true;
            break;
          case '[':
          case '{':
            if (!b) {
              c = c1;
              a(stringBuilder, SyntaxOption$Type.f);
            } else {
              stringBuilder.append(c1);
            } 
            b++;
            break;
          case ']':
          case '}':
            b--;
            if (b == 0) {
              a(stringBuilder, (c == '{') ? SyntaxOption$Type.c : SyntaxOption$Type.b);
              break;
            } 
            stringBuilder.append(c1);
            break;
          case '~':
            if (b == 0) {
              if (bool1) {
                a(stringBuilder, SyntaxOption$Type.e);
                bool1 = false;
                break;
              } 
              a(stringBuilder, SyntaxOption$Type.f);
              bool1 = true;
              break;
            } 
            stringBuilder.append(c1);
            break;
          default:
            stringBuilder.append(c1);
            break;
        } 
      } 
    } 
    a(stringBuilder, SyntaxOption$Type.f);
  }
  
  private void a(StringBuilder paramStringBuilder, SyntaxOption$Type paramSyntaxOption$Type) {
    if (!paramStringBuilder.isEmpty()) {
      if (this.h == null)
        this.h = new ArrayList(); 
      SyntaxOption syntaxOption = new SyntaxOption(this, paramStringBuilder.toString(), paramSyntaxOption$Type);
      this.h.add(syntaxOption);
    } 
    paramStringBuilder.delete(0, paramStringBuilder.length());
  }
  
  public String toString() {
    return this.b;
  }
  
  public boolean a() {
    return (this.h != null);
  }
  
  public List b() {
    return this.h;
  }
  
  public boolean c() {
    return this.d;
  }
  
  public void a(boolean paramBoolean) {
    this.d = paramBoolean;
    if (paramBoolean) {
      h();
      if (this.g == SyntaxOption$Type.d && a(SyntaxOption$Type.c))
        for (SyntaxOption syntaxOption : this.a.b()) {
          if (syntaxOption != this)
            syntaxOption.a(false); 
        }  
    } else {
      if (this.e != null)
        this.e.setValue(null); 
      if (a())
        for (SyntaxOption syntaxOption : b())
          syntaxOption.a(false);  
      if (this.g == SyntaxOption$Type.c && m() != null && m().m() != null) {
        (m().m()).d = true;
        (m()).d = true;
      } 
    } 
  }
  
  private boolean a(SyntaxOption$Type paramSyntaxOption$Type) {
    return (this.a != null && this.a.i() == paramSyntaxOption$Type);
  }
  
  public boolean d() {
    return (i() != SyntaxOption$Type.d && c());
  }
  
  public String e() {
    StringBuilder stringBuilder = new StringBuilder();
    if (this.g == SyntaxOption$Type.e) {
      stringBuilder.append(this.e.getValue());
    } else if (i() == SyntaxOption$Type.f) {
      stringBuilder.append(this.b);
    } 
    if ((c() || g() || this.g == SyntaxOption$Type.b || this.g == SyntaxOption$Type.c) && 
      a())
      for (SyntaxOption syntaxOption : b()) {
        String str = syntaxOption.e();
        if (stringBuilder.toString().endsWith(" ") && str.startsWith(" "))
          str = StringUtil.ltrim(str); 
        if (!stringBuilder.isEmpty() && !stringBuilder.toString().endsWith(" "))
          stringBuilder.append(" "); 
        stringBuilder.append(str);
      }  
    return stringBuilder.toString();
  }
  
  public void f() {
    if (a())
      for (SyntaxOption syntaxOption : b())
        syntaxOption.f();  
    System.out.printf("%-30s regEx=%s\n", new Object[] { this.b, k() });
  }
  
  public boolean g() {
    return (this.a == null);
  }
  
  public void h() {
    SyntaxOption syntaxOption = this.a;
    while (syntaxOption != null) {
      syntaxOption.a(true);
      syntaxOption = syntaxOption.a;
    } 
  }
  
  public SyntaxOption$Type i() {
    return this.g;
  }
  
  public String j() {
    if (this.a == null)
      return "G"; 
    int i = this.a.b().indexOf(this);
    char c = (char)(((i < 10) ? 48 : 97) + i);
    return this.a.j() + this.a.j();
  }
  
  public String k() {
    StringBuilder stringBuilder = new StringBuilder();
    if (this.g == SyntaxOption$Type.e) {
      stringBuilder.append("(?<").append(j()).append(">");
      if (this.b.startsWith("#")) {
        stringBuilder.append("\\s*(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?\\s*");
      } else if (this.b.toLowerCase().contains("text") || this.b.toLowerCase().contains("columns")) {
        stringBuilder.append(".+");
      } else {
        stringBuilder.append("\\w+");
      } 
      stringBuilder.append(")");
    } else if (this.g == SyntaxOption$Type.f) {
      stringBuilder.append("(?<").append(j()).append(">").append(new StringAsRegEx(this.b)).append(")");
    } else {
      if (this.g == SyntaxOption$Type.d || this.g == SyntaxOption$Type.g || this.g == SyntaxOption$Type.b)
        stringBuilder.append("("); 
      if (a())
        for (SyntaxOption syntaxOption : this.h)
          stringBuilder.append(syntaxOption.k());  
      if (this.g == SyntaxOption$Type.d || this.g == SyntaxOption$Type.g || this.g == SyntaxOption$Type.b)
        stringBuilder.append(")?"); 
    } 
    return stringBuilder.toString();
  }
  
  public void a(String paramString) {
    a(false);
    if (paramString != null) {
      String str = k();
      Pattern pattern = Pattern.compile(str, 2);
      if (StringUtil.isFilledTrim(paramString)) {
        Matcher matcher = pattern.matcher(paramString);
        while (matcher.find())
          a(matcher); 
      } 
    } 
  }
  
  private void a(Matcher paramMatcher) {
    if (i() == SyntaxOption$Type.e || i() == SyntaxOption$Type.f) {
      String str = paramMatcher.group(j());
      if (StringUtil.isFilledTrim(str)) {
        a(true);
        if (this.e != null)
          this.e.set(str); 
      } 
    } 
    if (a())
      for (SyntaxOption syntaxOption : this.h)
        syntaxOption.a(paramMatcher);  
  }
  
  public String l() {
    return this.c;
  }
  
  public SyntaxOption m() {
    return (this.h != null && !this.h.isEmpty()) ? this.h.get(0) : null;
  }
  
  public void a(Workspace paramWorkspace, RowPane$ paramRowPane$, boolean paramBoolean1, boolean paramBoolean2) {
    Label label;
    PropertyAddOnFolder propertyAddOnFolder;
    ComboBox comboBox;
    if (this.f != null)
      this.f = null; 
    boolean bool1 = false, bool2 = false;
    switch (SyntaxOption$1.a[this.g.ordinal()]) {
      case 1:
        bool1 = true;
        break;
      case 2:
        if (this.a != null && this.a.n())
          paramRowPane$.h(); 
        if (a(SyntaxOption$Type.c)) {
          bool2 = true;
          break;
        } 
        if (paramBoolean1 || this.a == null || this.a.i() != SyntaxOption$Type.a)
          bool1 = true; 
        break;
      case 3:
        label = new Label(this.b);
        paramRowPane$.c(this.m = (Node)label);
        break;
      case 4:
        propertyAddOnFolder = (paramWorkspace.m() != null) ? paramWorkspace.m().getPropertyAddOnFolder(this.b) : null;
        if (propertyAddOnFolder == null) {
          TextField textField = new TextField(this.e.getValue());
          textField.setPromptText(this.b);
          if (this.b.startsWith("#")) {
            textField.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
                  if (!paramString2.matches("\\s*(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?\\s*"))
                    paramTextField.setText(paramString2.replaceAll("[^\\d\\-\\.]", "")); 
                });
            textField.setPromptText(this.b.substring(1));
          } 
          if (paramBoolean1)
            textField.setDisable(paramBoolean2); 
          paramRowPane$.c(this.m = (Node)textField);
          textField.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.e.set(paramString2));
          textField.focusedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
                if (paramBoolean2.booleanValue())
                  a((Node)paramTextField); 
              });
          break;
        } 
        comboBox = new ComboBox();
        comboBox.setMaxWidth(Double.MAX_VALUE);
        comboBox.setEditable(true);
        comboBox.setPromptText(this.b);
        comboBox.getEditor().textProperty().bindBidirectional((Property)this.e);
        comboBox.setOnShowing(paramEvent -> {
              if (paramPropertyAddOnFolder.isLoaded()) {
                for (PropertyAddOn propertyAddOn : paramPropertyAddOnFolder)
                  paramComboBox.getItems().add(propertyAddOn.toString()); 
              } else if (paramWorkspace.t()) {
                paramWorkspace.getRx().a(paramPropertyAddOnFolder.a(paramWorkspace.s().startEnvoy("AddOn"), (), ()));
              } 
            });
        paramRowPane$.c(this.m = (Node)comboBox);
        comboBox.focusedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
              if (paramBoolean2.booleanValue())
                a((Node)paramComboBox); 
            });
        break;
    } 
    if (bool1) {
      CheckBox checkBox = new CheckBox();
      checkBox.setSelected(this.d);
      if (paramBoolean1)
        checkBox.setDisable(paramBoolean2); 
      paramRowPane$.c(this.m = (Node)checkBox);
      if (!this.d)
        paramBoolean2 = true; 
      checkBox.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
            this.d = paramBoolean2.booleanValue();
            if (paramBoolean)
              c(!paramBoolean2.booleanValue()); 
            a((Node)paramCheckBox);
          });
    } else if (bool2) {
      RadioButton radioButton = new RadioButton();
      radioButton.setSelected(this.d);
      if (paramBoolean1)
        radioButton.setDisable(paramBoolean2); 
      paramRowPane$.c(this.m = (Node)radioButton);
      if (this.a != null) {
        if (this.a.f == null)
          this.a.f = new ToggleGroup(); 
        this.a.f.getToggles().add(radioButton);
      } 
      if (!this.d)
        paramBoolean2 = true; 
      radioButton.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
            this.d = paramBoolean2.booleanValue();
            if (paramBoolean)
              c(!paramBoolean2.booleanValue()); 
            a((Node)paramRadioButton);
          });
    } 
    if (a())
      for (SyntaxOption syntaxOption : b())
        syntaxOption.a(paramWorkspace, paramRowPane$, paramBoolean1, paramBoolean2);  
  }
  
  private static void a(Node paramNode) {
    while (paramNode != null) {
      if (paramNode instanceof ListCell) {
        ListCell listCell = (ListCell)paramNode;
        listCell.getListView().getSelectionModel().select(listCell.getItem());
      } 
      Parent parent = paramNode.getParent();
    } 
  }
  
  private void c(boolean paramBoolean) {
    if (a()) {
      if ((this.m instanceof CheckBox && !((CheckBox)this.m).isSelected()) || (this.m instanceof RadioButton && !((RadioButton)this.m).isSelected()))
        paramBoolean = true; 
      for (SyntaxOption syntaxOption : b()) {
        if (syntaxOption.m != null)
          syntaxOption.m.setDisable(paramBoolean); 
        syntaxOption.c(paramBoolean);
      } 
    } 
  }
  
  public void b(boolean paramBoolean) {
    this.i = paramBoolean;
  }
  
  public boolean n() {
    return this.i;
  }
}
