package com.wisecoders.dbs.project.store;

import org.w3c.dom.Node;

public class Loader {
  protected static String a(Node paramNode) {
    for (byte b = 0; b < paramNode.getChildNodes().getLength(); b++) {
      Node node1 = paramNode.getChildNodes().item(b);
      if (node1.getNodeType() == 4) {
        String str = node1.getNodeValue();
        if (str != null)
          str = str.replaceAll("]]_>", "]]>"); 
        return str;
      } 
    } 
    Node node = paramNode.getFirstChild();
    if (node == null)
      return null; 
    return node.getNodeValue();
  }
  
  protected static String c(Node paramNode, Object paramObject) {
    if (!paramNode.hasAttributes())
      return null; 
    Node node = paramNode.getAttributes().getNamedItem(paramObject.toString());
    if (node != null)
      return node.getNodeValue(); 
    return null;
  }
  
  protected static int d(Node paramNode, Object paramObject) {
    if (!paramNode.hasAttributes())
      return -1; 
    Node node = paramNode.getAttributes().getNamedItem(paramObject.toString());
    String str;
    if (node != null && (str = node.getNodeValue()) != null) {
      if (str.startsWith("#"))
        return Integer.parseInt(str.substring(1), 16); 
      return Integer.parseInt(node.getNodeValue());
    } 
    return -1;
  }
  
  protected static boolean e(Node paramNode, Object paramObject) {
    return (paramNode.hasAttributes() && paramNode.getAttributes().getNamedItem(paramObject.toString()) != null);
  }
  
  protected static boolean f(Node paramNode, Object paramObject) {
    if (!paramNode.hasAttributes())
      return false; 
    Node node = paramNode.getAttributes().getNamedItem(paramObject.toString());
    return (node != null && ("y".equalsIgnoreCase(node.getNodeValue()) || "true".equalsIgnoreCase(node.getNodeValue())));
  }
  
  protected static boolean a(Node paramNode, Object paramObject, boolean paramBoolean) {
    if (!paramNode.hasAttributes())
      return paramBoolean; 
    Node node = paramNode.getAttributes().getNamedItem(paramObject.toString());
    if (node == null)
      return paramBoolean; 
    return "true".equalsIgnoreCase(node.getNodeValue());
  }
}
