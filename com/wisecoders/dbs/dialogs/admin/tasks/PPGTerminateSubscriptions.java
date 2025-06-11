package com.wisecoders.dbs.dialogs.admin.tasks;

import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Callback;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class PPGTerminateSubscriptions extends PPGTask {
  public String a() {
    return "Export subscriptions using https://cc.payproglobal.com/Reports/Subscriptions";
  }
  
  public Map b() {
    HashMap<Object, Object> hashMap = new HashMap<>();
    hashMap.put("cancellationReasonId", "2");
    hashMap.put("reasonText", "Requested by customer");
    hashMap.put("subscriptionTerminateFile", "C:/Temp/subscriptionsTerminate.csv");
    hashMap.put("vendorAccountId", "");
    hashMap.put("apiSecretKey", "");
    return hashMap;
  }
  
  public String a(Map paramMap, Callback paramCallback) {
    FileReader fileReader = new FileReader(Paths.get((String)paramMap.get("subscriptionTerminateFile"), new String[0]).toFile());
    CSVParser cSVParser = CSVFormat.Builder.create().setDelimiter(";").setEscape('\\').setQuote('"').setRecordSeparator('\n').setSkipHeaderRecord(false).build().parse(fileReader);
    for (CSVRecord cSVRecord : cSVParser) {
      String str1 = cSVRecord.get(0);
      String str2 = a("POST", "https://store.payproglobal.com/api/Subscriptions/Terminate", "{  \"cancellationReasonId\": \"" + (String)paramMap
          
          .get("cancellationReasonId") + "\",  \"reasonText\": \"" + (String)paramMap
          .get("reasonText") + "\",  \"sendCustomerNotification\": false,  \"subscriptionId\": " + str1 + ",  \"vendorAccountId\": " + (String)paramMap

          
          .get("vendorAccountId") + ",  \"apiSecretKey\": \"" + (String)paramMap
          .get("apiSecretKey") + "\",  }");
      paramCallback.call("Terminate subscription " + str1 + " " + str2);
    } 
    return "Done";
  }
  
  public String toString() {
    return "Terminate Subscriptions";
  }
}
