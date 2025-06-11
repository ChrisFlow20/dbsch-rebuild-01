package com.wisecoders.dbs.dialogs.admin.tasks;

import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Callback;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class PPGSubscriptionToAffiliate extends PPGTask {
  public String a() {
    return "Export subscriptions using https://cc.payproglobal.com/Reports/Subscriptions";
  }
  
  public Map b() {
    HashMap<Object, Object> hashMap = new HashMap<>();
    hashMap.put("affiliateAgreementId", "");
    hashMap.put("subscriptionToAffiliateFile", "C:/Temp/subscriptionsToAffiliate.csv");
    hashMap.put("vendorAccountId", "");
    hashMap.put("apiSecretKey", "");
    return hashMap;
  }
  
  public String a(Map paramMap, Callback paramCallback) {
    FileReader fileReader = new FileReader(Paths.get((String)paramMap.get("subscriptionToAffiliateFile"), new String[0]).toFile());
    CSVParser cSVParser = CSVFormat.Builder.create().setDelimiter(";").setEscape('\\').setQuote('"').setRecordSeparator('\n').setSkipHeaderRecord(false).build().parse(fileReader);
    for (CSVRecord cSVRecord : cSVParser) {
      String str1 = cSVRecord.get(0);
      String str2 = a("POST", "https://store.payproglobal.com/api/Affiliates/AssignToSubscription", "{  \"affiliateAgreementId\": " + (String)paramMap
          
          .get("affiliateAgreementId") + ",  \"subscriptionId\": " + str1 + ",  \"vendorAccountId\": " + (String)paramMap
          
          .get("vendorAccountId") + ",  \"apiSecretKey\": \"" + (String)paramMap
          .get("apiSecretKey") + "\",  }");
      paramCallback.call("Assign " + str1 + " " + str2);
    } 
    return "Done";
  }
  
  public String toString() {
    return "Assign Subscriptions to Affiliate";
  }
}
