package com.wisecoders.dbs.dialogs.admin.tasks;

import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Callback;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class PPGRenameSubscription extends PPGTask {
  public String a() {
    return "Export subscriptions using https://cc.payproglobal.com/Reports/Subscriptions";
  }
  
  public Map b() {
    HashMap<Object, Object> hashMap = new HashMap<>();
    hashMap.put("newSubscriptionName", "DbSchema PRO Upgrades and Maintenance");
    hashMap.put("subscriptionFile", "C:/Temp/subscriptions.csv");
    hashMap.put("vendorAccountId", "");
    hashMap.put("apiSecretKey", "");
    return hashMap;
  }
  
  public String a(Map paramMap, Callback paramCallback) {
    FileReader fileReader = new FileReader(Paths.get((String)paramMap.get("subscriptionFile"), new String[0]).toFile());
    CSVParser cSVParser = CSVFormat.Builder.create().setDelimiter(";").setEscape('\\').setQuote('"').setRecordSeparator('\n').setSkipHeaderRecord(true).build().parse(fileReader);
    boolean bool = true;
    for (CSVRecord cSVRecord : cSVParser) {
      if (!bool) {
        String str1 = cSVRecord.get(0);
        String str2 = a("POST", "https://store.payproglobal.com/api/Subscriptions/ChangeName", "{  \"newSubscriptionName\": " + (String)paramMap
            
            .get("newSubscriptionName") + ",  \"sendCustomerNotification\": false,  \"subscriptionId\": " + str1 + ",  \"vendorAccountId\": " + (String)paramMap

            
            .get("vendorAccountId") + ",  \"apiSecretKey\": \"" + (String)paramMap
            .get("apiSecretKey") + "\",  }");
        paramCallback.call("Parse " + str1 + " " + str2);
      } 
      bool = false;
    } 
    return "Done";
  }
  
  public String toString() {
    return "Rename Subscription";
  }
}
