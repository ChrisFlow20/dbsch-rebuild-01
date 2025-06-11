package com.wisecoders.dbs.dialogs.admin.tasks;

import java.util.HashMap;
import java.util.Map;
import javafx.util.Callback;

public class PPGChangeSubscriptionRecurringPrice extends PPGTask {
  public String a() {
    return "Export subscriptions using https://cc.payproglobal.com/Reports/Subscriptions";
  }
  
  public Map b() {
    HashMap<Object, Object> hashMap = new HashMap<>();
    hashMap.put("priceCurrencyCode", "USD");
    hashMap.put("sendCustomerNotification", "false");
    hashMap.put("newPriceValue", "");
    hashMap.put("subscriptionId", "");
    hashMap.put("vendorAccountId", "");
    hashMap.put("apiSecretKey", "");
    return hashMap;
  }
  
  public String a(Map paramMap, Callback paramCallback) {
    String str = a("POST", "https://store.payproglobal.com/api/Subscriptions/BulkChangeRecurringPrice", "{  \"priceCurrencyCode\": \"" + (String)paramMap
        
        .get("priceCurrencyCode") + "\",  \"newPriceValue\": \"" + (String)paramMap
        .get("newPriceValue") + "\",  \"sendCustomerNotification\": false,  \"subscriptionId\": " + (String)paramMap
        
        .get("subscriptionId") + ",  \"vendorAccountId\": " + (String)paramMap
        .get("vendorAccountId") + ",  \"apiSecretKey\": \"" + (String)paramMap
        .get("apiSecretKey") + "\",  }");
    paramCallback.call("Parse " + (String)paramMap.get("subscriptionId") + " " + str);
    return "Done";
  }
  
  public String toString() {
    return "Change Subscription Recurring Price";
  }
}
