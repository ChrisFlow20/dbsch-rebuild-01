package com.wisecoders.dbs.dialogs.admin.tasks;

import java.util.HashMap;
import java.util.Map;
import javafx.util.Callback;

public class PPGApplySubscriptionDiscount extends PPGTask {
  public String a() {
    return "Export subscriptions using https://cc.payproglobal.com/Reports/Subscriptions";
  }
  
  public Map b() {
    HashMap<Object, Object> hashMap = new HashMap<>();
    hashMap.put("discountValue", "");
    hashMap.put("isPercentage", "false");
    hashMap.put("subscriptionId", "");
    hashMap.put("vendorAccountId", "");
    hashMap.put("apiSecretKey", "");
    return hashMap;
  }
  
  public String a(Map paramMap, Callback paramCallback) {
    String str = a("POST", "https://store.payproglobal.com/api/Subscriptions/ApplyDiscount", "{  \"discountValue\": \"" + (String)paramMap
        
        .get("discountValue") + "\",  \"isPercentage\": \"" + (String)paramMap
        .get("isPercentage") + "\",  \"applyDiscountForAllFuturePayments\": false,  \"subscriptionId\": " + (String)paramMap
        
        .get("subscriptionId") + ",  \"vendorAccountId\": " + (String)paramMap
        .get("vendorAccountId") + ",  \"apiSecretKey\": \"" + (String)paramMap
        .get("apiSecretKey") + "\",  }");
    paramCallback.call("Parse " + (String)paramMap.get("subscriptionId") + " " + str);
    return "Done";
  }
  
  public String toString() {
    return "Apply Discount to Subscription";
  }
}
