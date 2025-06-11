package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.diagram.fx.FxLayoutPane;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.history.History;
import com.wisecoders.dbs.project.history.HistoryFile;
import com.wisecoders.dbs.project.html.HTML5Writer;
import com.wisecoders.dbs.project.html.MarkdownWriter;
import com.wisecoders.dbs.project.pdf.PdfBaseFonts;
import com.wisecoders.dbs.project.pdf.PdfDocumentationWriter;
import com.wisecoders.dbs.project.pdf.PdfUtil;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Expose;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.ToggleGroup$;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FxUtil$FileCell;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import org.controlsfx.control.CheckComboBox;

@DoNotObfuscate
public class FxDocumentationDialog extends ButtonDialog$ {
  private static final String a = "DOCU-TITLE-";
  
  private static final String b = "DOCU-FOLDER";
  
  public static final String DOC_INCLUDE_TOC = "doc-includeToc";
  
  public static final String DOC_INCLUDE_HEADER = "doc-includeHeader";
  
  public static final String DOC_INCLUDE_IMAGE = "doc-includeImage";
  
  public static final String DOC_INCLUDE_TOOLTIPS = "doc-includeTooltips";
  
  public static final String DOC_TEXT = "doc-text";
  
  public static final String DOC_INCLUDE_ALL_COLUMNS = "doc-includeAllColumns";
  
  public static final String DOC_INCLUDE_INDEXES = "doc-includeIndexes";
  
  public static final String DOC_INCLUDE_FOREIGN_KEYS = "doc-includeForeignKeys";
  
  public static final String DOC_INCLUDE_FUNCTIONS = "doc-includeFunctions";
  
  public static final String DOC_INCLUDE_TRIGGERS = "doc-includeTriggers";
  
  public static final String DOC_INCLUDE_SQL_CODE = "doc-includeSqlCode";
  
  public static final String DOC_INCLUDE_SEQUENCES = "doc-includeSequences";
  
  public static final String DOC_INCLUDE_USER_DATA_TYPE = "doc-includeUserDataType";
  
  public static final String DOC_INCLUDE_VIEW_QUERY = "doc-includeViewQuery";
  
  public static final String DOC_DARK_THEME = "doc-darkTheme";
  
  public static final String DOC_SEPARATE_SVG = "doc-separateSVG";
  
  public static final String DOC_LANDSCAPE = "doc-landscape";
  
  public static final String DOC_PREVIEW = "doc-preview";
  
  public static final String LAYOUT_SELECTION = "layout-selection";
  
  private final Workspace c;
  
  private final FxLayoutPane d;
  
  private final CheckBox e;
  
  private final CheckBox f;
  
  private final CheckBox i;
  
  private final CheckBox j;
  
  private final CheckBox k;
  
  private final CheckBox l;
  
  private final CheckBox m;
  
  private final CheckBox n;
  
  private final CheckBox o;
  
  private final CheckBox p;
  
  private final CheckBox q;
  
  private final CheckBox r;
  
  private final CheckBox s;
  
  private final CheckBox t;
  
  private final CheckBox u;
  
  private final CheckBox v;
  
  private final CheckBox w;
  
  private final TextField x = new TextField();
  
  private final RadioButton y = this.rx.i("htmlFormat", true);
  
  private final RadioButton z = this.rx.i("pdfFormat", false);
  
  private final RadioButton A = this.rx.i("mdFormat", false);
  
  private final ComboBox B = new ComboBox();
  
  private final CheckBox C;
  
  private final ComboBox D = new ComboBox();
  
  private final History E = new History("PDFDocu");
  
  private final History F = new History("MDDocu");
  
  private final History G = new History("HTMLDocu");
  
  private final ComboBox H = new ComboBox();
  
  private final CheckComboBox I = new CheckComboBox();
  
  public FxDocumentationDialog(Workspace paramWorkspace, FxLayoutPane paramFxLayoutPane) {
    super(paramWorkspace);
    setGraphic(BootstrapIcons.filetype_html);
    this.c = paramWorkspace;
    this.d = paramFxLayoutPane;
    this.x.setText(Pref.c("DOCU-TITLE-" + paramFxLayoutPane.d.getKey(), paramFxLayoutPane.d.getName()));
    this.e = this.rx.h("includeTOC", Pref.b("doc-includeToc", false));
    this.f = this.rx.h("image", Pref.b("doc-includeImage", true));
    this.i = this.rx.h("imageTooltips", Pref.b("doc-includeTooltips", true));
    this.j = this.rx.h("text", Pref.b("doc-text", true));
    this.k = this.rx.h("includeAllColumns", Pref.b("doc-includeAllColumns", true));
    this.l = this.rx.h("includeIndexes", Pref.b("doc-includeIndexes", true));
    this.m = this.rx.h("includeForeignKeys", Pref.b("doc-includeForeignKeys", true));
    this.t = this.rx.h("useUnicodeFont", PdfUtil.a(paramWorkspace.m()));
    this.n = this.rx.h("includeFunctions", Pref.b("doc-includeFunctions", false));
    this.o = this.rx.h("includeTriggers", Pref.b("doc-includeTriggers", false));
    this.p = this.rx.h("includeSqlCode", Pref.b("doc-includeSqlCode", false));
    this.q = this.rx.h("includeSequences", Pref.b("doc-includeSequences", false));
    this.r = this.rx.h("includeUserDataType", Pref.b("doc-includeUserDataType", false));
    this.s = this.rx.h("includeViewQuery", Pref.b("doc-includeViewQuery", true));
    this.u = this.rx.h("checkPreview", Pref.b("doc-preview", true));
    this.v = this.rx.h("checkDarkTheme", Pref.b("doc-darkTheme", false));
    this.w = this.rx.h("checkSeparateSVG", Pref.b("doc-separateSVG", false));
    this.C = this.rx.h("checkLandscape", Pref.b("doc-landscape", false));
    this.i.setDisable(true);
    this.B.setDisable(true);
    this.C.setDisable(true);
    this.j.setOnAction(paramActionEvent -> {
          this.l.setDisable(this.j.isDisable());
          this.o.setDisable(this.j.isDisable());
        });
    new ToggleGroup$(new ToggleButton[] { (ToggleButton)this.z, (ToggleButton)this.y, (ToggleButton)this.A });
    this.z.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          c();
          this.v.setDisable(paramBoolean2.booleanValue());
          if (paramBoolean2.booleanValue())
            this.v.setSelected(false); 
          this.i.setDisable(!paramBoolean2.booleanValue());
          this.i.setSelected(false);
          this.B.setDisable(false);
          this.C.setDisable(false);
          b();
        });
    this.A.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> b());
    c();
    this.B.getItems().addAll((Object[])new String[] { 
          "Letter", "Legal", "Tabloid", "Executive", "A0", "A1", "A2", "A3", "A4", "A5", 
          "B0", "B1", "B2" });
    this.B.setValue("Letter");
    this.rx.b();
    this.I.getItems().addAll(paramFxLayoutPane.d.project.layouts);
    this.I.getCheckModel().check(paramFxLayoutPane.d);
    this.H.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramd1, paramd2) -> this.I.setDisable((paramd2 == d.a || paramd2 == d.b || paramd2 == d.d)));
    this.H.getItems().addAll((Object[])d.values());
    this.H.setButtonCell(new FxDocumentationDialog$LayoutSelectionCell());
    this.H.setCellFactory(paramListView -> new FxDocumentationDialog$LayoutSelectionCell());
    try {
      this.H.setValue(d.valueOf(Pref.c("layout-selection", d.a.toString())));
    } catch (Exception exception) {}
    b();
    this.D.setEditable(true);
    this.D.setOnAction(paramActionEvent -> this.rx.b());
    this.D.setMaxWidth(Double.MAX_VALUE);
    this.D.setCellFactory(paramListView -> new FxUtil$FileCell());
    this.D.setPromptText(this.rx.H("fileComboPrompt"));
    this.rx.b();
    showDialog();
  }
  
  private History a() {
    return this.y.isSelected() ? this.G : (this.z.isSelected() ? this.E : this.F);
  }
  
  private void b() {
    this.D.getItems().clear();
    for (HistoryFile historyFile : a().c())
      this.D.getItems().add(historyFile.b.getAbsolutePath()); 
    this.D.setValue("");
  }
  
  private void c() {
    this.t.setDisable(!this.z.isSelected());
  }
  
  public Node createContentPane() {
    this.f.setSelected(true);
    GridPane$ gridPane$ = (new GridPane$()).a(new int[] { -2, -2, -2, -1 }).l();
    gridPane$.a((Node)this.rx.e("titleLabel"), "0,0,r,c");
    gridPane$.a((Node)this.x, "1,0,3,0,f,c");
    gridPane$.a((Node)this.rx.h("formatSeparator"), "0,1,3,1,f,c");
    gridPane$.a((Node)this.y, "1,2,l,c");
    gridPane$.a((Node)this.z, "2,2,l,c");
    gridPane$.a((Node)this.A, "3,2,l,c");
    gridPane$.a((Node)this.t, "2,3,l,c");
    gridPane$.a((Node)this.rx.h("contentSeparator"), "0,4,3,4,f,c");
    gridPane$.a((Node)(new HBox$()).d().a(Pos.CENTER_LEFT).a(new Node[] { (Node)this.rx.e("layoutsLabel"), (Node)this.H, (Node)this.I }, ), "1,5,3,5,l,c");
    gridPane$.a((Node)this.f, "1,6,l,c");
    gridPane$.a((Node)this.i, "2,6,l,c");
    gridPane$.a((Node)this.e, "1,7,l,c");
    gridPane$.a((Node)this.j, "2,7,l,c");
    gridPane$.a((Node)this.k, "3,7,l,c");
    gridPane$.a((Node)this.l, "1,8,l,c");
    gridPane$.a((Node)this.m, "2,8,l,c");
    gridPane$.a((Node)this.s, "3,8,l,c");
    gridPane$.a((Node)this.o, "1,9,l,c");
    gridPane$.a((Node)this.n, "2,9,l,c");
    gridPane$.a((Node)this.p, "3,9,l,c");
    gridPane$.a((Node)this.q, "1,10,l,c");
    gridPane$.a((Node)this.r, "2,10,l,c");
    gridPane$.a((Node)this.rx.h("settingsSeparator"), "0,11,3,11,f,c");
    gridPane$.a((Node)this.v, "1,12,l,c");
    gridPane$.a((Node)this.w, "2,12,l,c");
    gridPane$.a((Node)this.u, "3,12,l,c");
    gridPane$.a((Node)this.rx.o("automate"), "0,13,1,13,l,c");
    gridPane$.a((Node)this.rx.h("pageSizeSeparator"), "0,14,3,14,f,c");
    gridPane$.a((Node)this.B, "1,15,l,c");
    gridPane$.a((Node)this.C, "2,15,l,c");
    gridPane$.a((Node)this.rx.h("locationSeparator"), "0,16,3,16,f,c");
    HBox$.setHgrow((Node)this.D, Priority.ALWAYS);
    gridPane$.a((Node)(new HBox$()).f().a(new Node[] { (Node)this.D, (Node)this.rx.j("chooseFile") }, ), "1,17,3,17,f,f");
    setInitFocusedNode((Node)this.x);
    return (Node)gridPane$;
  }
  
  @Action
  public void automate() {
    WebBrowserExternal.a(getDialogScene(), "automation-api.html");
  }
  
  public void createButtons() {
    createActionButton("generate");
    createCancelButton();
    createHelpButton("layouts.html#print");
  }
  
  @Action
  public void chooseFile() {
    String str = Pref.c("DOCU-FOLDER", (this.c.m().getFile() != null) ? this.c.m().getFile().getParent() : null);
    File file = this.z.isSelected() ? FxFileChooser.a(getDialogScene(), this.rx.H("chooseFileTitle"), str, FileOperation.b, new FileType[] { FileType.k }) : (this.A.isSelected() ? FxFileChooser.a(getDialogScene(), this.rx.H("chooseFileTitle"), str, FileOperation.b, new FileType[] { FileType.l }) : FxFileChooser.a(getDialogScene(), this.rx.H("chooseFileTitle"), str, FileOperation.b, new FileType[] { FileType.c }));
    if (file != null) {
      Pref.a("DOCU-FOLDER", file.getParent());
      this.D.setValue(file.getAbsolutePath());
    } 
  }
  
  public boolean apply() {
    if (StringUtil.isEmptyTrim((String)this.D.getValue())) {
      this.D.requestFocus();
      showError("selectFileError");
      return false;
    } 
    if (!this.j.isSelected() && !this.f.isSelected()) {
      showError("selectDiagramOrText");
      return false;
    } 
    this.d.d.setComment(this.x.getText());
    return true;
  }
  
  private File d() {
    String str = (String)this.D.getValue();
    if (StringUtil.isFilledTrim(str))
      return Paths.get(str, new String[0]).toFile(); 
    return null;
  }
  
  @Action
  public void generate() {
    if (apply()) {
      File file = d();
      if (file != null) {
        TreeMap<Object, Object> treeMap;
        a().a(null, d());
        a().a(d());
        Pref.a("DOCU-FOLDER", file.getParent());
        ArrayList<Layout> arrayList = new ArrayList();
        switch (FxDocumentationDialog$2.a[((d)this.H.getValue()).ordinal()]) {
          case 1:
            arrayList.add(this.d.d);
            break;
          case 2:
            for (FxLayoutPane fxLayoutPane : this.d.b.z())
              arrayList.add(fxLayoutPane.d); 
            break;
          case 3:
            arrayList.addAll((Collection<? extends Layout>)this.I.getCheckModel().getCheckedItems());
            break;
          case 4:
            treeMap = new TreeMap<>();
            for (Layout layout : (this.d.b.m()).layouts) {
              if (layout.getCommentTags() != null)
                for (String str : layout.getCommentTags().keySet()) {
                  if ("documentation".equalsIgnoreCase(str.trim())) {
                    String str1 = (String)layout.getCommentTags().get(str);
                    String str2 = str1;
                    byte b = 0;
                    while (treeMap.containsKey(str2))
                      str2 = str1 + str1; 
                    treeMap.put(str2, layout);
                  } 
                }  
            } 
            arrayList.addAll(treeMap.values());
            if (arrayList.isEmpty()) {
              showError("noSelectedLayoutsByTag");
              return;
            } 
            break;
        } 
        Expose expose = new Expose(file, this.c.m(), arrayList);
        expose.setNotation(this.c.m().getNotation());
        expose.put((K)"TOC", (V)Boolean.valueOf(this.e.isSelected()));
        expose.put((K)"image", (V)Boolean.valueOf(this.f.isSelected()));
        expose.put((K)"imageTooltips", (V)Boolean.valueOf(this.i.isSelected()));
        expose.put((K)"text", (V)Boolean.valueOf(this.j.isSelected()));
        expose.put((K)"allColumn", (V)Boolean.valueOf(this.k.isSelected()));
        expose.put((K)"indexes", (V)Boolean.valueOf(this.l.isSelected()));
        expose.put((K)"foreignKeys", (V)Boolean.valueOf(this.m.isSelected()));
        expose.put((K)"functions", (V)Boolean.valueOf(this.n.isSelected()));
        expose.put((K)"triggers", (V)Boolean.valueOf(this.o.isSelected()));
        expose.put((K)"sql_code", (V)Boolean.valueOf(this.p.isSelected()));
        expose.put((K)"sequences", (V)Boolean.valueOf(this.q.isSelected()));
        expose.put((K)"user_data_type", (V)Boolean.valueOf(this.r.isSelected()));
        expose.put((K)"view_query", (V)Boolean.valueOf(this.s.isSelected()));
        expose.put((K)"unicode", (V)Boolean.valueOf(this.t.isSelected()));
        expose.put((K)"separateSVGFile", (V)Boolean.valueOf(this.w.isSelected()));
        expose.put((K)"darkTheme", (V)Boolean.valueOf(this.v.isSelected()));
        expose.put((K)"tableHeaderFontName", (V)((Font)this.d.c.p.getValue()).getFamily());
        expose.put((K)"tableHeaderFontSize", (V)Double.valueOf(((Font)this.d.c.p.getValue()).getSize()));
        expose.put((K)"tableHeaderFontWeight", (V)((Font)this.d.c.p.getValue()).getStyle());
        expose.put((K)"fontName", (V)((Font)this.d.c.o.getValue()).getFamily());
        expose.put((K)"fontSize", (V)Double.valueOf(((Font)this.d.c.o.getValue()).getSize()));
        expose.put((K)"calloutFontName", (V)((Font)this.d.c.r.getValue()).getFamily());
        expose.put((K)"calloutFontSize", (V)Double.valueOf(((Font)this.d.c.r.getValue()).getSize()));
        expose.put((K)"title", (V)this.x.getText());
        expose.put((K)"pageSize", (V)this.B.getValue());
        expose.put((K)"landscape", (V)Boolean.valueOf(this.C.isSelected()));
        if (this.z.isSelected() && this.t.isSelected() && !PdfBaseFonts.b.exists()) {
          this.rx.a(new FxDocumentationDialog$1(this, file, expose));
        } else {
          a(file, expose);
        } 
        Pref.a("doc-includeToc", this.e.isSelected());
        Pref.a("doc-includeImage", this.f.isSelected());
        Pref.a("doc-includeTooltips", this.i.isSelected());
        Pref.a("doc-text", this.j.isSelected());
        Pref.a("doc-includeAllColumns", this.k.isSelected());
        Pref.a("doc-includeIndexes", this.l.isSelected());
        Pref.a("doc-includeForeignKeys", this.m.isSelected());
        Pref.a("doc-includeFunctions", this.n.isSelected());
        Pref.a("doc-includeTriggers", this.o.isSelected());
        Pref.a("doc-includeSqlCode", this.p.isSelected());
        Pref.a("doc-includeSequences", this.q.isSelected());
        Pref.a("doc-includeUserDataType", this.r.isSelected());
        Pref.a("doc-includeViewQuery", this.s.isSelected());
        Pref.a("doc-darkTheme", this.v.isSelected());
        Pref.a("doc-separateSVG", this.w.isSelected());
        Pref.a("doc-landscape", this.C.isSelected());
        Pref.a("doc-preview", this.u.isSelected());
        Pref.a("DOCU-TITLE-" + this.d.d.getKey(), this.x.getText());
        Pref.a("layout-selection", String.valueOf(this.H.getValue()));
      } 
    } 
  }
  
  private void a(File paramFile, Expose paramExpose) {
    if (this.z.isSelected()) {
      new PdfDocumentationWriter(paramExpose);
    } else if (this.y.isSelected()) {
      HTML5Writer.generateHtml5(paramExpose);
    } else {
      MarkdownWriter.a(paramExpose);
    } 
    if (this.u.isSelected())
      WebBrowserExternal.a(this.c, paramFile.toURI()); 
    hide();
  }
}
