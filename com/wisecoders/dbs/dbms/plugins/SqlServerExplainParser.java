package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.data.model.explain.ExecutionPlan;
import com.wisecoders.dbs.project.store.Loader;
import com.wisecoders.dbs.sys.Log;
import java.io.InputStream;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SqlServerExplainParser extends Loader {
  public final ExecutionPlan a = new ExecutionPlan(new String[] { "Cost", "CpuCost", "IOCost", "EstimatedRows", "TableOrIndex", "LogicalOp", "PhysicalOp" }, new Class[] { Double.class, Double.class, Double.class, Integer.class, String.class, String.class, String.class });
  
  private a b;
  
  public SqlServerExplainParser(InputStream paramInputStream) {
    try {
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = documentBuilder.parse(paramInputStream);
      Element element = document.getDocumentElement();
      a(element, 0);
    } catch (Exception exception) {
      Log.b(exception);
    } 
    if (this.b != null)
      this.b.a(); 
  }
  
  private void a(Node paramNode, int paramInt) {
    String str = paramNode.getNodeName();
    if (str.startsWith("#"))
      str = str.substring(1); 
    if ("RelOp".equalsIgnoreCase(str)) {
      if (this.b != null)
        this.b.a(); 
      this





        
        .b = new a(this, paramInt, a(paramNode, "PhysicalOp"), a(paramNode, "LogicalOp"), b(paramNode, "EstimatedTotalSubtreeCost"), b(paramNode, "EstimateCPU"), b(paramNode, "EstimateIO"), (int)b(paramNode, "EstimateRows"));
    } else if ("Object".equalsIgnoreCase(str) && this.b != null) {
      String str1 = a(paramNode, "Schema");
      String str2 = a(paramNode, "Table");
      String str3 = a(paramNode, "Index");
      StringBuilder stringBuilder = new StringBuilder();
      if (str1 != null)
        stringBuilder.append(str1).append("."); 
      if (str3 != null) {
        stringBuilder.append(str3);
      } else if (str2 != null) {
        stringBuilder.append(str2);
      } 
      this.b.h = stringBuilder.toString().replaceAll("\\[", "").replaceAll("\\]", "");
      this.b.a();
      this.b = null;
    } 
    NodeList nodeList = paramNode.getChildNodes();
    for (byte b = 0; b < nodeList.getLength(); b++)
      a(nodeList.item(b), paramInt + 1); 
  }
  
  protected static String a(Node paramNode, Object paramObject) {
    if (!paramNode.hasAttributes())
      return null; 
    Node node = paramNode.getAttributes().getNamedItem(paramObject.toString());
    if (node != null)
      return node.getNodeValue(); 
    return null;
  }
  
  protected static double b(Node paramNode, Object paramObject) {
    if (!paramNode.hasAttributes())
      return 0.0D; 
    Node node = paramNode.getAttributes().getNamedItem(paramObject.toString());
    if (node != null)
      return Double.parseDouble(node.getNodeValue()); 
    return 0.0D;
  }
  
  public static void a(InputStream paramInputStream) {
    try {
      if (paramInputStream != null) {
        Scanner scanner = (new Scanner(paramInputStream)).useDelimiter("<");
        byte b = 0;
        while (scanner.hasNext()) {
          String str = scanner.next();
          if (str.startsWith("RelOp") || str.startsWith("Object")) {
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b1 = 0; b1 < b; b1++)
              stringBuilder.append("\t"); 
            stringBuilder.append("<").append(str);
          } 
          if (str.endsWith("/>"))
            continue; 
          if (str.startsWith("/")) {
            b--;
            continue;
          } 
          b++;
        } 
      } 
    } catch (Exception exception) {
      Log.a("Error in browse detail by reading Blob", exception);
    } 
  }
}
