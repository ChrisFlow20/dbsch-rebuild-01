package com.wisecoders.dbs.dialogs.admin.tasks;

import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Callback;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class PPGMigrateSubscriptions extends PPGTask {
  public String a() {
    return "Export subscriptions using https://cc.payproglobal.com/Reports/Subscriptions";
  }
  
  public Map b() {
    HashMap<Object, Object> hashMap = new HashMap<>();
    hashMap.put("PPGFile", "C:/Temp/dbschemaExportPPG.csv");
    hashMap.put("vendorAccountId", "");
    hashMap.put("apiSecretKey", "");
    return hashMap;
  }
  
  public String a(Map paramMap, Callback paramCallback) {
    FileReader fileReader = new FileReader(Paths.get((String)paramMap.get("PPGFile"), new String[0]).toFile());
    CSVParser cSVParser = CSVFormat.Builder.create().setDelimiter(",").setEscape('\\').setQuote('"').setRecordSeparator('\n').setSkipHeaderRecord(false).build().parse(fileReader);
    for (CSVRecord cSVRecord : cSVParser) {
      String str1 = cSVRecord.get(0);
      String str2 = cSVRecord.get(3);
      int i = -1;
      switch (str2) {
        case "74970":
          i = 84341;
          break;
        case "74971":
          i = 84342;
          break;
        case "74972":
          i = 84343;
          break;
        case "74973":
          i = 84344;
          break;
        case "74974":
          i = 84345;
          break;
        case "74975":
          i = 84346;
          break;
      } 
      if (i > -1) {
        String str = a("POST", "https://store.payproglobal.com/api/Subscriptions/ChangeProduct", "{  \"subscriptionId\": " + str1 + ",  \"productId\": " + i + ",  \"sendCustomerNotification\": false,  \"vendorAccountId\": " + (String)paramMap



            
            .get("vendorAccountId") + ",  \"apiSecretKey\": \"" + (String)paramMap
            .get("apiSecretKey") + "\",  }");
        paramCallback.call("Migrate " + str1 + " " + str);
        continue;
      } 
      paramCallback.call("Failed " + str1 + " option: " + str2);
    } 
    return "Done";
  }
  
  public String toString() {
    return "Migrate Subscriptions";
  }
}
