package com.wisecoders.dbs.dialogs.admin.tasks;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Callback;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class PPGGetSubscriptionDetails extends PPGTask {
  public String a() {
    return "Export subscriptions using https://cc.payproglobal.com/Reports/Subscriptions";
  }
  
  public Map b() {
    HashMap<Object, Object> hashMap = new HashMap<>();
    hashMap.put("subscriptionDetailsFile", "C:/Temp/subscriptionDetails.csv");
    hashMap.put("vendorAccountId", "");
    hashMap.put("apiSecretKey", "");
    return hashMap;
  }
  
  public String a(Map paramMap, Callback paramCallback) {
    FileReader fileReader = new FileReader(Paths.get((String)paramMap.get("subscriptionDetailsFile"), new String[0]).toFile());
    CSVParser cSVParser = CSVFormat.Builder.create().setDelimiter(";").setEscape('\\').setQuote('"').setRecordSeparator('\n').setSkipHeaderRecord(false).build().parse(fileReader);
    for (CSVRecord cSVRecord : cSVParser) {
      String str1 = cSVRecord.get(0);
      String str2 = a("POST", "https://store.payproglobal.com/api/Subscriptions/GetSubscriptionDetails", "{  \"subscriptionId\": " + str1 + ",  \"vendorAccountId\": " + (String)paramMap

          
          .get("vendorAccountId") + ",  \"apiSecretKey\": \"" + (String)paramMap
          .get("apiSecretKey") + "\",  }");
      paramCallback.call("Parse " + str1 + " " + str2);
      JsonObject jsonObject = JsonParser.parseString(str2).getAsJsonObject();
      paramCallback.call("JsonObject: " + String.valueOf(jsonObject));
      paramCallback.call("Resolve response.orders[].billingTotalPrice: " + String.valueOf(a(jsonObject, "response.orders[].billingTotalPrice")));
    } 
    return "Done";
  }
  
  private static JsonElement a(JsonObject paramJsonObject, String paramString) {
    JsonElement jsonElement;
    JsonObject jsonObject = paramJsonObject;
    int i;
    while (jsonObject instanceof JsonObject && (i = paramString.indexOf(".")) > 0) {
      jsonElement = b(jsonObject, paramString.substring(0, i));
      paramString = paramString.substring(i + 1);
    } 
    return (jsonElement instanceof JsonObject) ? b((JsonObject)jsonElement, paramString) : null;
  }
  
  private static JsonElement b(JsonObject paramJsonObject, String paramString) {
    if (paramJsonObject != null && paramString != null)
      if (paramString.endsWith("[]")) {
        JsonArray jsonArray = paramJsonObject.getAsJsonArray(paramString.substring(0, paramString.length() - "[]".length()));
        if (jsonArray != null && jsonArray.size() > 0)
          return jsonArray.get(jsonArray.size() - 1); 
      } else if (paramString.endsWith("[0]")) {
        JsonArray jsonArray = paramJsonObject.getAsJsonArray(paramString.substring(0, paramString.length() - "[0]".length()));
        if (jsonArray != null && jsonArray.size() > 0)
          return jsonArray.get(0); 
      } else {
        return paramJsonObject.get(paramString);
      }  
    return null;
  }
  
  public String toString() {
    return "Get Subscription Details";
  }
}
