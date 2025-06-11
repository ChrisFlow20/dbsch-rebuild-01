package com.wisecoders.dbs.dialogs.admin.tasks;

import java.util.HashMap;
import java.util.Map;
import javafx.util.Callback;

public class PPGChangeSubscriptionQuantity extends PPGTask {
  public String a() {
    return "Export subscriptions using https://cc.payproglobal.com/Reports/Subscriptions";
  }
  
  public Map b() {
    HashMap<Object, Object> hashMap = new HashMap<>();
    hashMap.put("quantity", "2");
    hashMap.put("newSubscriptionName", "DbSchema PRO Upgrades and Maintenance");
    hashMap.put("subscriptionId", "");
    hashMap.put("vendorAccountId", "");
    hashMap.put("apiSecretKey", "");
    return hashMap;
  }
  
  public String a(Map paramMap, Callback paramCallback) {
    String str = a("POST", "https://store.payproglobal.com/api/Subscriptions/ChangeQuantity", "{  \"newSubscriptionName\": \"" + (String)paramMap
        
        .get("newSubscriptionName") + "\",  \"quantity\": \"" + (String)paramMap
        .get("quantity") + "\",  \"sendCustomerNotification\": false,  \"subscriptionId\": " + (String)paramMap
        
        .get("subscriptionId") + ",  \"vendorAccountId\": " + (String)paramMap
        .get("vendorAccountId") + ",  \"apiSecretKey\": \"" + (String)paramMap
        .get("apiSecretKey") + "\",  }");
    paramCallback.call("Parse " + (String)paramMap.get("subscriptionId") + " " + str);
    return "Done";
  }
  
  public String toString() {
    return "Change Subscription Quantity";
  }
}
