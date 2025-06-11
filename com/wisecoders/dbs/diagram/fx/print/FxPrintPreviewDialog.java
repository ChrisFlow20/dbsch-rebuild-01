package com.wisecoders.dbs.diagram.fx.print;

import com.wisecoders.dbs.diagram.fx.print.printable.GeneralPrintableNode;
import com.wisecoders.dbs.project.fx.Theme;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Alert$;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.HGrowBox$;
import com.wisecoders.dbs.sys.fx.ScrollPane$;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrintQuality;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.transform.Transform;
import javafx.stage.Modality;
import javafx.stage.Window;

public class FxPrintPreviewDialog extends Dialog$ {
  public static final double a = 0.6D;
  
  private final GeneralPrintableNode b;
  
  private final ListView c = new ListView();
  
  private final Slider d = new Slider(10.0D, 200.0D, 100.0D);
  
  private final Slider e = new Slider(0.3D, 1.5D, 0.7D);
  
  private final FxPrintPreviewCanvas f = new FxPrintPreviewCanvas(FxPrintPreviewCanvas$Mode.a, this.e.getValue(), false);
  
  private final ComboBox i = new ComboBox(FXCollections.observableArrayList((Collection)Printer.getAllPrinters()));
  
  private final Label j = new Label();
  
  private final BorderPane k = new BorderPane((Node)new Label("Please select a printer"));
  
  private boolean l = true;
  
  private boolean m = Pref.b("PrintBorders", false);
  
  private boolean n = Pref.b("PrintCorners", true);
  
  private boolean o = Pref.b("PrintPageNumber", true);
  
  private PageLayout p;
  
  private static final String q = "PaperName";
  
  private static final String r = "PagePrintableWidth2";
  
  private static final String s = "PagePrintableHeight2";
  
  private static final String t = "PageLayoutMarginTop";
  
  private static final String u = "PageLayoutMarginBottom";
  
  private static final String v = "PageLayoutMarginLeft";
  
  private static final String w = "PageLayoutMarginRight";
  
  private static final String x = "PageLayoutOrientation";
  
  private static final String y = "PrintBorders";
  
  private static final String z = "PrintCorners";
  
  private static final String A = "PrintPageNumber";
  
  private static final double B = 4.0D;
  
  private static final double C = 2.0D;
  
  public static double a() {
    return Pref.b("PagePrintableWidth2", -1.0D);
  }
  
  public static double b() {
    return Pref.b("PagePrintableHeight2", -1.0D);
  }
  
  private void c() {
    if (this.p != null) {
      Pref.a("PagePrintableWidth2", this.p.getPrintableWidth());
      Pref.a("PagePrintableHeight2", this.p.getPrintableHeight());
      Pref.a("PageLayoutMarginTop", this.p.getTopMargin());
      Pref.a("PageLayoutMarginBottom", this.p.getBottomMargin());
      Pref.a("PageLayoutMarginLeft", this.p.getLeftMargin());
      Pref.a("PageLayoutMarginRight", this.p.getRightMargin());
      Pref.a("PageLayoutOrientation", String.valueOf(this.p.getPageOrientation()));
      if (this.p.getPaper() != null)
        Pref.a("PaperName", this.p.getPaper().getName()); 
    } 
  }
  
  public FxPrintPreviewDialog(Window paramWindow, GeneralPrintableNode paramGeneralPrintableNode) {
    super(paramWindow, Modality.WINDOW_MODAL);
    this.b = paramGeneralPrintableNode;
    this.rx.a("flagIdle", () -> (this.i.getValue() != null && this.l));
    this.rx.a("flagPrintBorders", () -> this.m);
    this.rx.a("flagPrintCorners", () -> this.n);
    this.rx.a("flagPrintPageNumber", () -> this.o);
    paramWindow.getScene().getStylesheets().removeAll((Object[])(Theme.a()).h);
    this.c.setCellFactory(paramListView -> new FxPrintPreviewDialog$PreviewListCell(this));
    this.c.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    this.c.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramPrintPreviewPage1, paramPrintPreviewPage2) -> f());
    this.c.setPrefWidth(180.0D);
    this.i.setPromptText("Select a Printer");
    this.i.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramPrinter1, paramPrinter2) -> {
          a(paramPrinter2);
          d();
        });
    Printer printer;
    if ((printer = Printer.getDefaultPrinter()) != null) {
      this.i.setValue(printer);
    } else if (this.i.getItems().isEmpty()) {
      Platform.runLater(() -> showNotificationPane("No printer found. Please configure."));
    } 
    d();
    this.i.valueProperty().addListener((paramObservableValue, paramPrinter1, paramPrinter2) -> this.rx.b());
    this.d.valueProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> {
          d();
          f();
        });
    this.e.valueProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> {
          this.f.a(paramNumber2.doubleValue());
          d();
          f();
        });
    this.d.setShowTickLabels(true);
    this.d.setShowTickMarks(true);
    this.d.setMajorTickUnit(90.0D);
    this.d.setMinorTickCount(20);
    this.d.setBlockIncrement(1.0D);
    this.d.setPrefWidth(300.0D);
    this.e.setOrientation(Orientation.VERTICAL);
    setOnHiding(paramDialogEvent -> {
          paramWindow.getScene().getStylesheets().addAll((Object[])(Theme.a()).h);
          c();
        });
    showDialog(paramWindow);
  }
  
  private void a(Printer paramPrinter) {
    if (paramPrinter != null) {
      this.k.setCenter((Node)this.f);
      String str = Pref.a("PaperName");
      if (str != null)
        try {
          for (Paper paper : paramPrinter.getPrinterAttributes().getSupportedPapers()) {
            if (str.equals(paper.getName())) {
              this.p = paramPrinter.createPageLayout(paper, PageOrientation.valueOf(Pref.a("PageLayoutOrientation")), 
                  Pref.b("PageLayoutMarginLeft", 0.0D), 
                  Pref.b("PageLayoutMarginRight", 0.0D), 
                  Pref.b("PageLayoutMarginTop", 0.0D), 
                  Pref.b("PageLayoutMarginBottom", 0.0D));
              return;
            } 
          } 
        } catch (Throwable throwable) {
          Log.b(throwable);
        }  
      this.p = paramPrinter.getDefaultPageLayout();
    } 
  }
  
  private void d() {
    if (this.p != null) {
      int i = (int)Math.ceil(this.b.a() * g() / this.p.getPrintableWidth());
      int j = (int)Math.ceil(e() * g() / this.p.getPrintableHeight());
      boolean bool = false;
      for (PrintPreviewPage printPreviewPage : this.c.getItems()) {
        if (!printPreviewPage.b())
          bool = true; 
      } 
      ArrayList<PrintPreviewPage> arrayList = new ArrayList();
      int k;
      for (k = 0; k < j; k++) {
        for (byte b = 0; b < i; b++) {
          PrintPreviewPage printPreviewPage = new PrintPreviewPage(k, b, (j + 1) * (i + 1));
          arrayList.add(printPreviewPage);
          int m = arrayList.indexOf(printPreviewPage);
          if (m < this.c.getItems().size()) {
            printPreviewPage.d.set(((PrintPreviewPage)this.c.getItems().get(m)).b());
          } else {
            printPreviewPage.d.set(!bool);
          } 
        } 
      } 
      k = Math.max(0, this.c.getSelectionModel().getSelectedIndex());
      this.c.getItems().setAll(arrayList);
      this.j.setText("" + arrayList.size() + arrayList.size());
      if (k < this.c.getItems().size())
        Platform.runLater(() -> {
              this.c.getSelectionModel().select(paramInt);
              this.c.scrollTo(paramInt);
            }); 
    } 
    this.c.refresh();
  }
  
  private double e() {
    return this.b.b() - (this.o ? ((Font)this.f.a.getValue()).getSize() : 0.0D);
  }
  
  private void f() {
    PrintPreviewPage printPreviewPage = (PrintPreviewPage)this.c.getSelectionModel().getSelectedItem();
    if (printPreviewPage == null && !this.c.getItems().isEmpty())
      printPreviewPage = (PrintPreviewPage)this.c.getItems().get(0); 
    if (printPreviewPage != null)
      this.f.a(this.p, this.b, this.m, this.n, this.o, 
          g(), printPreviewPage); 
  }
  
  private double g() {
    return 0.6D * this.d.getValue() / 100.0D;
  }
  
  public Node createContentPane() {
    ScrollPane$ scrollPane$ = (new ScrollPane$((Node)this.k)).a();
    SplitPane splitPane = new SplitPane();
    BorderPane borderPane1 = new BorderPane();
    borderPane1.setCenter((Node)this.c);
    borderPane1.setBottom((Node)this.j);
    BorderPane.setAlignment((Node)this.j, Pos.CENTER);
    splitPane.getItems().addAll((Object[])new Node[] { (Node)borderPane1, (Node)scrollPane$ });
    SplitPane.setResizableWithParent((Node)borderPane1, Boolean.FALSE);
    borderPane1.setPrefSize(300.0D, 600.0D);
    scrollPane$.setPrefSize(700.0D, 600.0D);
    MenuButton menuButton1 = this.rx.f("selectAll", false);
    menuButton1.getItems().addAll(this.rx.e(new String[] { "selectAll", "selectNone" }));
    MenuButton menuButton2 = this.rx.f("show", false);
    menuButton2.getItems().addAll((Object[])new MenuItem[] { (MenuItem)this.rx
          .y("printPageNumber"), (MenuItem)this.rx
          .y("printBorders"), (MenuItem)this.rx
          .y("printCorners") });
    MenuButton menuButton3 = this.rx.f("contentZoom", false);
    menuButton3.getItems().addAll(this.rx.e(new String[] { "zoomFitContent", "zoomFitContentWidth", "zoomFitContentHeight", "separator", "manuallyZoomContent" }));
    BorderPane borderPane2 = new BorderPane();
    HBox$ hBox$ = (new HBox$()).a(7.0D).a(new Node[] { (Node)menuButton1, (Node)this.i, (Node)this.rx

          
          .j("pageSetup"), (Node)menuButton2, (Node)new HGrowBox$(), (Node)menuButton3, (Node)this.d });
    hBox$.setAlignment(Pos.CENTER);
    borderPane2.setTop((Node)hBox$);
    borderPane2.setCenter((Node)splitPane);
    borderPane2.setRight((Node)this.e);
    this.e.setMaxHeight(150.0D);
    BorderPane.setAlignment((Node)this.e, Pos.BOTTOM_LEFT);
    return (Node)borderPane2;
  }
  
  public void createButtons() {
    createActionButton("print");
    createCancelButton();
  }
  
  public boolean apply() {
    return true;
  }
  
  @Action(b = "flagIdle")
  public void pageSetup() {
    if (this.i.getValue() == null) {
      this.rx.a(getDialogScene(), "choosePrinter", new String[0]);
    } else {
      PrinterJob printerJob = PrinterJob.createPrinterJob((Printer)this.i.getValue());
      printerJob.getJobSettings().setPageLayout(this.p);
      try {
        printerJob.showPageSetupDialog(getDialogPane().getScene().getWindow());
        this.i.setValue(printerJob.getPrinter());
        this.p = printerJob.getJobSettings().getPageLayout();
        d();
      } catch (Exception exception) {
        (new Alert$(Alert.AlertType.ERROR)).b(exception.getLocalizedMessage()).a(exception).showAndWait();
      } finally {
        printerJob.cancelJob();
      } 
    } 
  }
  
  @Action
  public void selectAll() {
    a(true);
  }
  
  @Action
  public void selectNone() {
    a(false);
  }
  
  private void a(boolean paramBoolean) {
    for (PrintPreviewPage printPreviewPage : this.c.getItems())
      printPreviewPage.a(paramBoolean); 
    this.c.refresh();
  }
  
  @Action(b = "flagIdle")
  public Task print() {
    PrinterJob printerJob = PrinterJob.createPrinterJob((Printer)this.i.getValue());
    printerJob.jobStatusProperty().addListener((paramObservableValue, paramJobStatus1, paramJobStatus2) -> setTitle(paramJobStatus2.toString()));
    printerJob.getJobSettings().setPageLayout(this.p);
    if (printerJob.showPrintDialog(getOwner())) {
      this.l = false;
      this.rx.b();
      boolean bool = true;
      try {
        for (PrintPreviewPage printPreviewPage : this.c.getItems()) {
          if (bool && printPreviewPage.b()) {
            printerJob.getJobSettings().setPrintQuality(PrintQuality.HIGH);
            FxPrintPreviewCanvas fxPrintPreviewCanvas = new FxPrintPreviewCanvas(FxPrintPreviewCanvas$Mode.b, 2.0D, false);
            PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();
            fxPrintPreviewCanvas.a(pageLayout, this.b, this.m, this.n, this.o, g(), printPreviewPage);
            ImageView imageView = new ImageView((Image)a(fxPrintPreviewCanvas));
            imageView.setFitWidth(pageLayout.getPrintableWidth());
            imageView.setFitHeight(pageLayout.getPrintableHeight());
            if (!printerJob.printPage(pageLayout, (Node)imageView))
              bool = false; 
          } 
        } 
        if (bool)
          printerJob.endJob(); 
        close();
      } catch (Throwable throwable) {
        this.rx.a(getDialogPane().getScene(), throwable);
      } finally {
        this.l = true;
        this.rx.b();
      } 
    } 
    return null;
  }
  
  private static WritableImage a(Canvas paramCanvas) {
    double d = 4.0D;
    while (true) {
      try {
        WritableImage writableImage = a(paramCanvas, d);
        if (writableImage != null)
          return writableImage; 
      } catch (Throwable throwable) {
        Log.a("Error printing diagram with scale " + d, throwable);
      } 
      d--;
      if (d <= 0.0D)
        return null; 
    } 
  }
  
  private static WritableImage a(Canvas paramCanvas, double paramDouble) {
    WritableImage writableImage = new WritableImage((int)Math.rint(paramDouble * paramCanvas.getWidth()), (int)Math.rint(paramDouble * paramCanvas.getHeight()));
    SnapshotParameters snapshotParameters = new SnapshotParameters();
    snapshotParameters.setTransform((Transform)Transform.scale(paramDouble, paramDouble));
    return paramCanvas.snapshot(snapshotParameters, writableImage);
  }
  
  @Action(d = "flagPrintBorders")
  public void printBorders() {
    this.m = !this.m;
    Pref.a("PrintBorders", this.m);
    d();
    f();
    this.rx.b();
  }
  
  @Action(d = "flagPrintCorners")
  public void printCorners() {
    this.n = !this.n;
    Pref.a("PrintCorners", this.n);
    d();
    f();
    this.rx.b();
  }
  
  @Action(d = "flagPrintPageNumber")
  public void printPageNumber() {
    this.o = !this.o;
    Pref.a("PrintPageNumber", this.o);
    d();
    f();
    this.rx.b();
  }
  
  @Action
  public void zoomFitContent() {
    this.d.setValue(100.0D * Math.min(this.p.getPrintableWidth() / this.b.a(), this.p.getPrintableHeight() / e()) / 0.6D);
  }
  
  @Action
  public void zoomFitContentWidth() {
    this.d.setValue(100.0D * this.p.getPrintableWidth() / this.b.a() / 0.6D);
  }
  
  @Action
  public void zoomFitContentHeight() {
    this.d.setValue(100.0D * this.p.getPrintableHeight() / e() / 0.6D);
  }
  
  @Action
  public void manuallyZoomContent() {
    String str = this.rx.b(getDialogScene(), "inputZoomDialog", (new DecimalFormat("0.00")).format(this.d.getValue()));
    if (StringUtil.isFilled(str)) {
      double d = Math.min(200.0D, Math.max(10.0D, Double.parseDouble(str)));
      this.d.setValue(d);
    } 
  }
}
