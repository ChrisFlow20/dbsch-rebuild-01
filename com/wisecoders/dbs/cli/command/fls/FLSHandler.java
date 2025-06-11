package com.wisecoders.dbs.cli.command.fls;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Features;
import com.wisecoders.dbs.sys.Keys;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Qx;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

@DoNotObfuscate
public class FLSHandler implements HttpHandler {
  private static final int a = 403;
  
  public static final int SC_INTERNAL_SERVER_ERROR = 500;
  
  public static final int SC_OK = 200;
  
  private final Connector b = ConnectorManager.createH2LocalConnector("FLS", "~/.DbSchema/connections/licenseServer");
  
  private Envoy c;
  
  private static final Qx d = new Qx(FLSHandler.class);
  
  public void handle(HttpExchange paramHttpExchange) {
    URI uRI = paramHttpExchange.getRequestURI();
    String str1 = uRI.getQuery();
    HashMap<Object, Object> hashMap = new HashMap<>();
    if (str1 != null)
      for (String str : str1.split("&")) {
        String[] arrayOfString = str.split("=");
        if (arrayOfString.length > 1) {
          hashMap.put(arrayOfString[0], arrayOfString[1]);
        } else {
          hashMap.put(arrayOfString[0], "");
        } 
      }  
    InputStream inputStream = paramHttpExchange.getRequestBody();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    try {
      String str = bufferedReader.lines().collect(Collectors.joining("\n"));
      for (String str4 : str.split("&")) {
        String[] arrayOfString = str4.split("=");
        if (arrayOfString.length > 1) {
          hashMap.put(arrayOfString[0], arrayOfString[1]);
        } else {
          hashMap.put(arrayOfString[0], "");
        } 
      } 
      bufferedReader.close();
    } catch (Throwable throwable) {
      try {
        bufferedReader.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    paramHttpExchange.getResponseHeaders().set("Content-Type", "text/plain;charset=UTF-8");
    if (!isFloatingLicense()) {
      a(paramHttpExchange, 403, "No active floating license loaded on the License Server. Please load it first.");
      return;
    } 
    if (this.c == null)
      this.c = this.b.startEnvoy("fls"); 
    try {
      PreparedStatement preparedStatement = this.c.b(d.a("CreateSequence"));
      try {
        PreparedStatement preparedStatement1 = this.c.b(d.a("CreateTable"));
        try {
          preparedStatement.executeUpdate();
          preparedStatement1.executeUpdate();
          this.c.p();
          if (preparedStatement1 != null)
            preparedStatement1.close(); 
        } catch (Throwable throwable) {
          if (preparedStatement1 != null)
            try {
              preparedStatement1.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            }  
          throw throwable;
        } 
        if (preparedStatement != null)
          preparedStatement.close(); 
      } catch (Throwable throwable) {
        if (preparedStatement != null)
          try {
            preparedStatement.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }  
        throw throwable;
      } 
    } catch (Exception exception) {
      Log.a("Error in Floating License Server Init", exception);
      a(paramHttpExchange, 500, exception.getLocalizedMessage());
    } 
    int i = -1;
    String str2 = (String)hashMap.get(Keys.g.a);
    String str3 = (String)hashMap.get(Keys.j.a);
    boolean bool = "true".equalsIgnoreCase((String)hashMap.get("UNREGISTER"));
    if (str3 != null)
      i = Integer.parseInt(str3); 
    int j = -1;
    try {
      PreparedStatement preparedStatement = this.c.b(d.a("CreateSession"));
      try {
        PreparedStatement preparedStatement1 = this.c.b(d.a("GetNextId"));
        try {
          PreparedStatement preparedStatement2 = this.c.b(d.a("UpdateSession"));
          try {
            PreparedStatement preparedStatement3 = this.c.b(d.a("Unregister"));
            try {
              PreparedStatement preparedStatement4 = this.c.b(d.a("RefreshSessions"));
              try {
                PreparedStatement preparedStatement5 = this.c.b(d.a("GetActiveCount"));
                try {
                  PreparedStatement preparedStatement6 = this.c.b(d.a("ListActiveSessions"));
                  try {
                    PreparedStatement preparedStatement7 = this.c.b(d.a("History"));
                    try {
                      preparedStatement4.executeUpdate();
                      this.c.p();
                      ResultSet resultSet = preparedStatement5.executeQuery();
                      if (resultSet.next())
                        j = resultSet.getInt(1); 
                      StringBuilder stringBuilder = new StringBuilder();
                      if (bool) {
                        Log.c("FLS unregister id=" + i);
                        preparedStatement3.setInt(1, i);
                        preparedStatement3.executeUpdate();
                        this.c.p();
                      } else if (hashMap.get("INFO") != null) {
                        stringBuilder.append("DbSchema Floating License\nCurrent active ").append(j).append(" from maximal ").append((License.a()).f).append(" users. Details:");
                        ResultSet resultSet1 = preparedStatement6.executeQuery();
                        while (resultSet1.next())
                          stringBuilder.append(resultSet1.getString(1)).append(" session start date: ").append((new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(resultSet1.getTimestamp(2))); 
                      } else if (hashMap.get("LOG") == null) {
                        if (i == -1) {
                          if (j >= (License.a()).f) {
                            a(paramHttpExchange, 403, "Increasing the number of active users to " + j + 1 + " would exceed the floating license quantity " + 
                                (License.a()).f + ".");
                            Log.c("FLS increasing the number of active users to " + j + " would exceed the floating license quantity " + (License.a()).f + ".");
                            if (preparedStatement7 != null)
                              preparedStatement7.close(); 
                            if (preparedStatement6 != null)
                              preparedStatement6.close(); 
                            if (preparedStatement5 != null)
                              preparedStatement5.close(); 
                            if (preparedStatement4 != null)
                              preparedStatement4.close(); 
                            if (preparedStatement3 != null)
                              preparedStatement3.close(); 
                            if (preparedStatement2 != null)
                              preparedStatement2.close(); 
                            if (preparedStatement1 != null)
                              preparedStatement1.close(); 
                            if (preparedStatement != null)
                              preparedStatement.close(); 
                            return;
                          } 
                          ResultSet resultSet1 = preparedStatement1.executeQuery();
                          if (resultSet1.next())
                            i = resultSet1.getInt(1); 
                          preparedStatement.setString(1, str2);
                          preparedStatement.setInt(2, i);
                          preparedStatement.executeUpdate();
                          Log.c("FLS start session username=" + str2 + " id=" + i);
                        } else {
                          preparedStatement2.setInt(1, i);
                          preparedStatement2.executeUpdate();
                        } 
                        this.c.p();
                        stringBuilder.append(Keys.j.a).append("=").append(i);
                      } else {
                        stringBuilder.append("License Server Log");
                        printResultSetAsTable(preparedStatement7.executeQuery(), stringBuilder);
                      } 
                      a(paramHttpExchange, 200, stringBuilder.toString());
                      if (preparedStatement7 != null)
                        preparedStatement7.close(); 
                    } catch (Throwable throwable) {
                      if (preparedStatement7 != null)
                        try {
                          preparedStatement7.close();
                        } catch (Throwable throwable1) {
                          throwable.addSuppressed(throwable1);
                        }  
                      throw throwable;
                    } 
                    if (preparedStatement6 != null)
                      preparedStatement6.close(); 
                  } catch (Throwable throwable) {
                    if (preparedStatement6 != null)
                      try {
                        preparedStatement6.close();
                      } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                      }  
                    throw throwable;
                  } 
                  if (preparedStatement5 != null)
                    preparedStatement5.close(); 
                } catch (Throwable throwable) {
                  if (preparedStatement5 != null)
                    try {
                      preparedStatement5.close();
                    } catch (Throwable throwable1) {
                      throwable.addSuppressed(throwable1);
                    }  
                  throw throwable;
                } 
                if (preparedStatement4 != null)
                  preparedStatement4.close(); 
              } catch (Throwable throwable) {
                if (preparedStatement4 != null)
                  try {
                    preparedStatement4.close();
                  } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                  }  
                throw throwable;
              } 
              if (preparedStatement3 != null)
                preparedStatement3.close(); 
            } catch (Throwable throwable) {
              if (preparedStatement3 != null)
                try {
                  preparedStatement3.close();
                } catch (Throwable throwable1) {
                  throwable.addSuppressed(throwable1);
                }  
              throw throwable;
            } 
            if (preparedStatement2 != null)
              preparedStatement2.close(); 
          } catch (Throwable throwable) {
            if (preparedStatement2 != null)
              try {
                preparedStatement2.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
          if (preparedStatement1 != null)
            preparedStatement1.close(); 
        } catch (Throwable throwable) {
          if (preparedStatement1 != null)
            try {
              preparedStatement1.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            }  
          throw throwable;
        } 
        if (preparedStatement != null)
          preparedStatement.close(); 
      } catch (Throwable throwable) {
        if (preparedStatement != null)
          try {
            preparedStatement.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }  
        throw throwable;
      } 
    } catch (Exception exception) {
      Log.a("Error in Floating License Server", exception);
      a(paramHttpExchange, 500, exception.getLocalizedMessage());
    } 
  }
  
  public static void printResultSetAsTable(ResultSet paramResultSet, StringBuilder paramStringBuilder) {
    ArrayList<String[]> arrayList = new ArrayList();
    String[] arrayOfString = new String[paramResultSet.getMetaData().getColumnCount()];
    int[] arrayOfInt = new int[arrayOfString.length];
    byte b;
    for (b = 0; b < arrayOfString.length; b++) {
      arrayOfString[b] = paramResultSet.getMetaData().getColumnName(b + 1);
      arrayOfInt[b] = (arrayOfString[b] != null) ? arrayOfString[b].length() : 0;
    } 
    while (paramResultSet.next()) {
      String[] arrayOfString1 = new String[arrayOfString.length];
      for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
        arrayOfString1[b1] = paramResultSet.getString(b1 + 1);
        arrayOfInt[b1] = Math.max(arrayOfInt[b1], (arrayOfString1[b1] != null) ? arrayOfString1[b1].length() : 0);
      } 
      arrayList.add(arrayOfString1);
    } 
    for (b = 0; b < arrayOfString.length; b++) {
      paramStringBuilder.append(String.format("%" + arrayOfInt[b] + "s", new Object[] { arrayOfString[b] }));
      paramStringBuilder.append(" ");
    } 
    paramStringBuilder.append("\n");
    for (b = 0; b < arrayOfString.length; b++) {
      paramStringBuilder.append("=".repeat(arrayOfInt[b]));
      paramStringBuilder.append(" ");
    } 
    paramStringBuilder.append("\n");
    for (String[] arrayOfString1 : arrayList) {
      for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
        paramStringBuilder.append(String.format("%" + arrayOfInt[b1] + "s", new Object[] { arrayOfString1[b1] }));
        paramStringBuilder.append(" ");
      } 
      paramStringBuilder.append("\n");
    } 
  }
  
  public static boolean isFloatingLicense() {
    return (License.a().h() == Features.a && "FLS".equals((License.a()).d));
  }
  
  private static void a(HttpExchange paramHttpExchange, int paramInt, String paramString) {
    paramHttpExchange.sendResponseHeaders(paramInt, paramString.length());
    OutputStream outputStream = paramHttpExchange.getResponseBody();
    try {
      outputStream.write(paramString.getBytes(StandardCharsets.UTF_8));
      if (outputStream != null)
        outputStream.close(); 
    } catch (Throwable throwable) {
      if (outputStream != null)
        try {
          outputStream.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
  }
}
