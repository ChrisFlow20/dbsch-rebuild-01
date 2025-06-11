package com.wisecoders.dbs.dialogs.admin.tasks;

import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Callback;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class PPGReactivateSubscriptions extends PPGTask {
  public String a() {
    return "Export subscriptions using https://cc.payproglobal.com/Reports/Subscriptions";
  }
  
  public Map b() {
    HashMap<Object, Object> hashMap = new HashMap<>();
    hashMap.put("newNextPaymentDate", "2050-01-01T00:00:00.0000000-00:00");
    hashMap.put("newSubscriptionName", "DbSchema PRO Upgrades and Maintenance");
    hashMap.put("subscriptionReactivateFile", "C:/Temp/subscriptionsReactivate.csv");
    hashMap.put("vendorAccountId", "");
    hashMap.put("apiSecretKey", "");
    return hashMap;
  }
  
  public String a(Map paramMap, Callback paramCallback) {
    FileReader fileReader = new FileReader(Paths.get((String)paramMap.get("subscriptionReactivateFile"), new String[0]).toFile());
    CSVParser cSVParser = CSVFormat.Builder.create().setDelimiter(";").setEscape('\\').setQuote('"').setRecordSeparator('\n').setSkipHeaderRecord(false).build().parse(fileReader);
    for (CSVRecord cSVRecord : cSVParser) {
      String str1 = cSVRecord.get(0);
      String str2 = a("POST", "https://store.payproglobal.com/api/Subscriptions/ChangeNextPaymentDate", "{  \"newNextPaymentDate\": \"" + (String)paramMap
          
          .get("newNextPaymentDate") + "\",  \"shiftPaymentSchedule\": true,  \"sendCustomerNotification\": false,  \"subscriptionId\": " + str1 + ",  \"vendorAccountId\": " + (String)paramMap


          
          .get("vendorAccountId") + ",  \"apiSecretKey\": \"" + (String)paramMap
          .get("apiSecretKey") + "\",  }");
      paramCallback.call("ChangeNextPaymentDate " + str1 + " " + str2);
      str2 = a("POST", "https://store.payproglobal.com/api/Subscriptions/Renew", "{  \"reasonText\": \"Reactivate\",  \"subscriptionId\": " + str1 + ",  \"vendorAccountId\": " + (String)paramMap


          
          .get("vendorAccountId") + ",  \"apiSecretKey\": \"" + (String)paramMap
          .get("apiSecretKey") + "\",  }");
      paramCallback.call("Renew " + str1 + " " + str2);
    } 
    return "Done";
  }
  
  public String toString() {
    return "Reactivate Subscriptions";
  }
}
