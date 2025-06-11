package com.wisecoders.dbs.sys;

import com.wisecoders.dbs.config.system.Sys;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import javafx.scene.image.Image;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@DoNotObfuscate
public class StringUtil {
  public static boolean isEmpty(Object paramObject) {
    return (paramObject == null || paramObject.toString() == null || paramObject.toString().isEmpty());
  }
  
  public static boolean isFilled(Object paramObject) {
    return (paramObject != null && paramObject.toString() != null && !paramObject.toString().isEmpty());
  }
  
  public static boolean isFilledTrim(Object paramObject) {
    return (paramObject != null && paramObject.toString() != null && !paramObject.toString().trim().isEmpty());
  }
  
  public static boolean isEmptyTrim(String paramString) {
    return (paramString == null || paramString.trim().isEmpty());
  }
  
  public static String cutOfSuffix(String paramString1, String paramString2) {
    if (paramString1 != null && paramString1.endsWith(paramString2))
      return paramString1.substring(0, paramString1.length() - paramString2.length()); 
    return paramString1;
  }
  
  public static String cutOfWithDots(String paramString, int paramInt) {
    if (paramString != null)
      return (paramString.length() > paramInt) ? (paramString.substring(0, paramInt - 3) + "...") : paramString; 
    return null;
  }
  
  public static String cutOfMiddleWithDots(String paramString, int paramInt) {
    if (paramString != null)
      return (paramString.length() > paramInt) ? (paramString.substring(0, paramInt / 2) + "..." + paramString.substring(0, paramInt / 2)) : paramString; 
    return null;
  }
  
  public static String cutOfNoDots(String paramString, int paramInt) {
    if (paramString != null)
      return (paramString.length() > paramInt) ? paramString.substring(0, paramInt) : paramString; 
    return paramString;
  }
  
  public static String splitMultiline(String paramString) {
    return splitMultiline(paramString, 120, "<br>");
  }
  
  public static String splitMultiline(String paramString1, int paramInt, String paramString2) {
    int i = paramInt / 2;
    if (paramString1 == null)
      return null; 
    StringBuilder stringBuilder = new StringBuilder();
    byte b1 = 0;
    for (byte b2 = 0; b2 < paramString1.length(); b2++) {
      char c = paramString1.charAt(b2);
      switch (c) {
        case '\n':
          stringBuilder.append(paramString2);
          b1 = 0;
          break;
        case ' ':
          if (b1 > paramInt && paramString1.indexOf(b2, 10) > i) {
            stringBuilder.append(paramString2);
            b1 = 0;
            break;
          } 
          stringBuilder.append(c);
          break;
        default:
          stringBuilder.append(c);
          break;
      } 
      b1++;
    } 
    return stringBuilder.toString();
  }
  
  public static String[] splitTextInLines(String paramString) {
    String[] arrayOfString = paramString.split("\\r\\n|\\n|\\r");
    if (paramString.endsWith("\r\n") || paramString.endsWith("\n") || paramString.endsWith("\r")) {
      String[] arrayOfString1 = new String[arrayOfString.length + 1];
      System.arraycopy(arrayOfString, 0, arrayOfString1, 0, arrayOfString.length);
      arrayOfString1[arrayOfString1.length - 1] = "";
      arrayOfString = arrayOfString1;
    } 
    return arrayOfString;
  }
  
  public static String getFileNameWithoutExtension(File paramFile) {
    if (paramFile != null) {
      String str = paramFile.getName();
      int i;
      if ((i = str.lastIndexOf('.')) > 0)
        str = str.substring(0, i); 
      return str;
    } 
    return null;
  }
  
  public static boolean areEqual(String paramString1, String paramString2) {
    if (paramString1 == null)
      return (paramString2 == null); 
    return paramString1.equals(paramString2);
  }
  
  public static boolean areEqualRemoveChars(String paramString1, String paramString2, String paramString3) {
    if (paramString1 == null)
      return (paramString2 == null); 
    if (paramString2 == null)
      return false; 
    for (byte b = 0; b < paramString3.length(); b++) {
      String str = Pattern.quote("" + paramString3.charAt(b));
      paramString1 = paramString1.replaceAll(str, "");
      paramString2 = paramString2.replaceAll(str, "");
    } 
    return paramString1.equals(paramString2);
  }
  
  public static int compare(String paramString1, String paramString2) {
    if (paramString1 == null)
      return (paramString2 == null) ? 0 : -1; 
    if (paramString2 == null)
      return 1; 
    return paramString1.compareTo(paramString2);
  }
  
  public static boolean areEqualIgnoreCase(String paramString1, String paramString2) {
    if (paramString1 == null)
      return (paramString2 == null); 
    return paramString1.equalsIgnoreCase(paramString2);
  }
  
  public static boolean equalNotNull(String paramString1, String paramString2) {
    if (paramString1 == null)
      return false; 
    return paramString1.equals(paramString2);
  }
  
  public static boolean stringsAreEqual(String paramString1, String paramString2) {
    if (paramString1 == null)
      return (paramString2 == null); 
    if (paramString2 == null)
      return false; 
    return paramString1.equals(paramString2);
  }
  
  public static boolean stringsAreEqualIgnoreCasesAndSpaces(String paramString1, String paramString2) {
    if (paramString1 == null)
      return (paramString2 == null); 
    if (paramString2 == null)
      return false; 
    return removeSpaces(paramString1).equalsIgnoreCase(removeSpaces(paramString2));
  }
  
  public static boolean stringsAreEqualIgnoreCasesAndSpacesAndEndDelimiter(String paramString1, String paramString2) {
    if (paramString1 == null)
      return (paramString2 == null); 
    if (paramString2 == null)
      return false; 
    String str1 = removeSpaces(paramString1);
    String str2 = removeSpaces(paramString2);
    if (str2.equals(str1 + ";") || str1.equals(str2 + ";"))
      return true; 
    if (str2.equals(str1 + "/") || str1.equals(str2 + "/"))
      return true; 
    return str1.equalsIgnoreCase(str2);
  }
  
  public static boolean stringsAreEqualIgnoreCaseSpaces(String paramString1, String paramString2) {
    if (paramString1 != null && paramString2 != null) {
      paramString1 = paramString1.replaceAll(" ", "");
      paramString2 = paramString2.replaceAll(" ", "");
      return paramString1.equalsIgnoreCase(paramString2);
    } 
    return false;
  }
  
  public static String removeSpaces(String paramString) {
    if (paramString != null)
      return paramString.replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", "").replaceAll(" ", ""); 
    return paramString;
  }
  
  public static String unescapeComment(Object paramObject) {
    if (paramObject != null && paramObject.toString() != null)
      return unescapeNewLine(paramObject).replaceAll("''", "'"); 
    return null;
  }
  
  public static String unescapeNewLine(Object paramObject) {
    if (paramObject != null && paramObject.toString() != null)
      return paramObject.toString().replaceAll("\\\\n", "\n").replaceAll("\\\\r", "\r").replaceAll("\\\\t", "\t"); 
    return null;
  }
  
  public static String flatternText(String paramString) {
    if (paramString != null)
      return paramString.replaceAll("[\\n\\r]+", " ").replaceAll("[\\s]{2,}+", " "); 
    return paramString;
  }
  
  public static String valueOfEmptyIfNull(Object paramObject) {
    return (paramObject != null) ? String.valueOf(paramObject) : "";
  }
  
  public static String valueOfWithNull(Object paramObject) {
    return (paramObject != null) ? String.valueOf(paramObject) : null;
  }
  
  public static String nullIfEmpty(String paramString) {
    return (paramString != null && paramString.trim().length() == 0) ? null : paramString;
  }
  
  public static String fillWithBlanks(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append(" ".repeat(Math.max(0, paramInt)));
    return stringBuilder.toString();
  }
  
  public static String toPlainText(String paramString) {
    paramString = paramString.replaceAll("<br>", "\n");
    paramString = paramString.replaceAll("<ul>", "\n");
    paramString = paramString.replaceAll("</ul>", "\n");
    paramString = paramString.replaceAll("<li>", "\n");
    paramString = paramString.replaceAll("<b>", "");
    paramString = paramString.replaceAll("</b>", "");
    paramString = paramString.replaceAll("\n\n", "\n");
    return paramString;
  }
  
  public static int lastPositionOfAnyOfTheStrings(String paramString, String[] paramArrayOfString) {
    int i = -1;
    if (paramString != null && paramArrayOfString != null)
      for (String str : paramArrayOfString) {
        int j = paramString.lastIndexOf(str);
        if (j > i)
          i = j; 
      }  
    return i;
  }
  
  public static boolean isNumberOrSpecialCharacter(char paramChar) {
    return (isNumber(paramChar) || paramChar == '#' || paramChar == '&' || paramChar == '$' || paramChar == '@' || paramChar == '/');
  }
  
  public static boolean isNumber(char paramChar) {
    return (paramChar == '0' || paramChar == '1' || paramChar == '2' || paramChar == '3' || paramChar == '4' || paramChar == '5' || paramChar == '6' || paramChar == '7' || paramChar == '8' || paramChar == '9');
  }
  
  private static final Pattern a = Pattern.compile("[\n\r]+", 2);
  
  public static int getPositionInString(String paramString, int paramInt1, int paramInt2) {
    if (paramInt1 > -1 && paramInt2 > -1) {
      String[] arrayOfString = a.split(paramString);
      if (paramInt1 < arrayOfString.length) {
        int i = paramInt2;
        for (byte b = 0; b < paramInt1; b++)
          i += arrayOfString[b].length() + 1; 
        return i;
      } 
    } 
    return -1;
  }
  
  public static String readStringFromInputStream(InputStream paramInputStream) {
    if (paramInputStream == null)
      throw new IOException("Got empty Input Stream"); 
    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramInputStream, StandardCharsets.UTF_8));
    try {
      String str;
      while (null != (str = bufferedReader.readLine())) {
        if (stringBuilder.length() > 0)
          stringBuilder.append("\n"); 
        stringBuilder.append(str);
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
    return stringBuilder.toString();
  }
  
  public static String firstLetterUppercase(Object paramObject) {
    String str = String.valueOf(paramObject);
    if (str != null && str.length() > 1)
      return str.substring(0, 1).toUpperCase() + str.substring(0, 1).toUpperCase(); 
    return str;
  }
  
  public static String afterLastSlash(String paramString) {
    if (paramString != null) {
      int i = paramString.lastIndexOf("/");
      if (i > -1)
        paramString = paramString.substring(i + 1); 
    } 
    return paramString;
  }
  
  public static String afterFirstSlash(String paramString) {
    if (paramString != null) {
      int i = paramString.indexOf("/");
      if (i > -1)
        paramString = paramString.substring(i + 1); 
    } 
    return paramString;
  }
  
  public static String escapeForFileName(String paramString) {
    return paramString.replace(" ", "").replace("\"", "");
  }
  
  public static String removeNewLine(String paramString, boolean paramBoolean) {
    if (paramString == null)
      return null; 
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (c == '\n') {
        if (paramBoolean)
          stringBuilder.append("\\n"); 
      } else if (c != '\r') {
        stringBuilder.append(c);
      } 
    } 
    return stringBuilder.toString().trim();
  }
  
  public static String buildJSArray(List paramList) {
    StringBuilder stringBuilder = new StringBuilder();
    for (String str : paramList) {
      if (stringBuilder.length() > 0)
        stringBuilder.append(","); 
      if (isFilledTrim(str))
        stringBuilder.append("'").append(str).append("'"); 
    } 
    return stringBuilder.toString();
  }
  
  public static String join(List paramList, String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramList != null) {
      boolean bool = false;
      for (Object object : paramList) {
        if (object != null) {
          if (bool)
            stringBuilder.append(paramString); 
          bool = true;
          stringBuilder.append(object);
        } 
      } 
    } 
    return stringBuilder.toString();
  }
  
  private static final HashMap b = new HashMap<>();
  
  public static final String ISO_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  
  public static String encodeURL(String paramString) {
    String str = null;
    if (paramString != null) {
      str = (String)b.get(paramString);
      if (str == null)
        b.put(paramString, str = URLEncoder.encode(paramString, StandardCharsets.UTF_8)); 
    } 
    return str;
  }
  
  public static String encodeURLBasic(String paramString) {
    return (paramString != null) ? paramString.replaceAll("\\\\", "%5C") : null;
  }
  
  public static String trimRemoveQuotesAndUnescapeComment(Object paramObject) {
    if (paramObject != null && paramObject.toString() != null) {
      String str = paramObject.toString().trim();
      if (str.startsWith("E'") && str.endsWith("'")) {
        str = str.substring(2, str.length() - 1);
        return str.replaceAll("\\\\\\\\n", "\n").replaceAll("\\\\\\\\r", "\r").replaceAll("\\\\\\\\t", "\t");
      } 
      return unescapeComment(removeQuotes(str));
    } 
    return null;
  }
  
  public static String removeQuotes(String paramString) {
    if (paramString != null && ((
      paramString.startsWith("'") && paramString.endsWith("'")) || (paramString
      .startsWith("\"") && paramString.endsWith("\"")) || (paramString
      .startsWith("`") && paramString.endsWith("`"))))
      return paramString.substring(1, paramString.length() - 1); 
    return paramString;
  }
  
  public static double wordSimilarityByLevensthein(String paramString1, String paramString2) {
    if (isEmptyTrim(paramString1) || isEmptyTrim(paramString2))
      return 0.0D; 
    int i = Math.min(paramString1.length(), paramString2.length());
    int j = Math.max(paramString1.length(), paramString2.length());
    byte b = 0;
    while (b < i && paramString1.charAt(b) == paramString2.charAt(b))
      b++; 
    return (b > 3) ? (b / j) : 0.0D;
  }
  
  public static Date convertDateFromString(String paramString) {
    if (paramString != null) {
      String[] arrayOfString = { 
          "yyyy-MM-dd HH:mm:ss", Sys.B.dateFormat.c_(), Sys.B.timestampFormat.c_(), "yyyy-MM-dd", "yyyy-MM", "yyyy-MM-dd'T'HH:mm:ssX", "yyyy-MM-dd HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
          "HH:mm", "HH:mm:ss", "HH:mm:ss.SSS" };
      for (String str : arrayOfString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        simpleDateFormat.setLenient(false);
        try {
          return simpleDateFormat.parse(paramString);
        } catch (ParseException parseException) {
        
        } catch (Throwable throwable) {
          Log.b(throwable);
        } 
      } 
      Log.f("Cannot parse date from string '" + paramString + "'.");
    } 
    return null;
  }
  
  public static String convertDateToString(Date paramDate) {
    return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(paramDate);
  }
  
  public static String objectToString(Object paramObject) {
    if (paramObject != null) {
      if (paramObject instanceof Date)
        return convertDateToString((Date)paramObject); 
      return paramObject.toString();
    } 
    return null;
  }
  
  public static String ltrim(String paramString) {
    if (paramString != null)
      while (paramString.startsWith(" "))
        paramString = paramString.substring(1);  
    return paramString;
  }
  
  public static String readBinaryStringAsHex(InputStream paramInputStream, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramInputStream != null) {
      int i = paramInputStream.available();
      if (i > paramInt)
        i = paramInt; 
      byte[] arrayOfByte = new byte[i];
      paramInputStream.read(arrayOfByte);
      paramInputStream.close();
      for (byte b : arrayOfByte) {
        stringBuilder.append(String.format("%02x", new Object[] { Byte.valueOf(b) }));
      } 
    } 
    return stringBuilder.toString();
  }
  
  public static String getIdFromString(String paramString) {
    String str = paramString.replaceAll("[^A-Za-z0-9]", "");
    if (!str.isEmpty() && Character.isDigit(str.charAt(0)))
      str = "id" + str; 
    return str;
  }
  
  public static List parseLOV(String paramString) {
    ArrayList<String> arrayList = new ArrayList();
    try {
      if (isFilledTrim(paramString))
        for (CSVRecord cSVRecord : CSVParser.parse(paramString, CSVFormat.DEFAULT)) {
          for (byte b = 0; b < cSVRecord.size(); b++) {
            String str = cSVRecord.get(b);
            arrayList.add(removeQuotes(str.trim()));
          } 
        }  
    } catch (IOException iOException) {
      Log.b("Cannot parse CSV: " + paramString, iOException);
    } 
    return arrayList;
  }
  
  public static boolean isInvalidJavaIdentifier(String paramString) {
    if (paramString == null || paramString.isEmpty())
      return true; 
    char[] arrayOfChar = paramString.toCharArray();
    if (!Character.isJavaIdentifierStart(arrayOfChar[0]))
      return true; 
    for (byte b = 1; b < arrayOfChar.length; b++) {
      if (!Character.isJavaIdentifierPart(arrayOfChar[b]))
        return true; 
    } 
    return false;
  }
  
  public static String firstNotEmpty(String... paramVarArgs) {
    for (String str : paramVarArgs) {
      if (isFilledTrim(str))
        return str; 
    } 
    return null;
  }
  
  public static String getPluralOf(String paramString) {
    if (paramString != null) {
      if (paramString.endsWith("x"))
        return paramString + "es"; 
      if (paramString.endsWith("ey"))
        return paramString + "s"; 
      if (paramString.endsWith("y"))
        return paramString.substring(0, paramString.length() - 1) + "ies"; 
      return paramString + "s";
    } 
    return null;
  }
  
  public static boolean stringStartsWith(String paramString, String... paramVarArgs) {
    paramString = paramString.toLowerCase().trim();
    for (String str : paramVarArgs) {
      if (paramString.startsWith(str))
        return true; 
    } 
    return false;
  }
  
  private static final Pattern c = Pattern.compile("(\\w+)='*((?<=')[^']+(?=')|([^\\s]+))'*");
  
  public static Map parseParameterList(String paramString) {
    HashMap<Object, Object> hashMap = new HashMap<>();
    if (paramString != null) {
      paramString = removeQuotes(paramString);
      Matcher matcher = c.matcher(paramString);
      while (matcher.find())
        hashMap.put(matcher.group(1).toLowerCase(), matcher.group(2)); 
    } 
    return hashMap;
  }
  
  public static String readFileFromURL(URL paramURL) {
    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramURL.openStream()));
    String str;
    while ((str = bufferedReader.readLine()) != null)
      stringBuilder.append(str).append("\n"); 
    bufferedReader.close();
    return stringBuilder.toString();
  }
  
  public static String fixTextForDefaultValue(String paramString) {
    if (paramString != null) {
      paramString = paramString.trim();
      return "NULL".equalsIgnoreCase(paramString.trim()) ? null : paramString;
    } 
    return null;
  }
  
  public static String toJSON(Object paramObject) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramObject instanceof Map) {
      Map map = (Map)paramObject;
      stringBuilder.append("{");
      for (Object object : map.keySet())
        stringBuilder.append(object).append(", ").append(toJSON(map.get(object))); 
      stringBuilder.append("}");
    } else if (paramObject instanceof List) {
      List list = (List)paramObject;
      stringBuilder.append("[");
      boolean bool = true;
      for (Object object : list) {
        if (!bool)
          stringBuilder.append(","); 
        stringBuilder.append(toJSON(object));
        bool = false;
      } 
      stringBuilder.append("]");
    } else if (paramObject instanceof Number || paramObject instanceof Boolean) {
      stringBuilder.append(paramObject);
    } else {
      stringBuilder.append("\"").append(paramObject).append("\"");
    } 
    return stringBuilder.toString();
  }
  
  public static String durationAsString(long paramLong) {
    return String.format("%02d:%02d", new Object[] { Long.valueOf(paramLong % 3600L / 60L), Long.valueOf(paramLong % 60L) });
  }
  
  public static boolean containsOneChar(String paramString1, String paramString2) {
    if (paramString1 != null && paramString2 != null)
      for (byte b = 0; b < paramString2.length(); b++) {
        if (paramString1.contains("" + paramString2.charAt(b)))
          return true; 
      }  
    return false;
  }
  
  public static String getTagValue(String paramString) {
    if ("currentDate()".endsWith(paramString))
      return (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()); 
    return paramString;
  }
  
  public static long getCRC32Checksum(String paramString) {
    if (isFilled(paramString)) {
      byte[] arrayOfByte = paramString.getBytes();
      CRC32 cRC32 = new CRC32();
      cRC32.update(arrayOfByte, 0, arrayOfByte.length);
      return cRC32.getValue();
    } 
    return -1L;
  }
  
  public static String printExceptionFirstLinesOfStackTrace(Throwable paramThrowable) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramThrowable).append("\n\n");
    byte b = 0;
    for (StackTraceElement stackTraceElement : paramThrowable.getStackTrace()) {
      if (b > 25) {
        stringBuilder.append("\n");
        break;
      } 
      stringBuilder.append(stackTraceElement).append("\n");
      b++;
    } 
    return stringBuilder.toString();
  }
  
  public static String escapeForGroovy(String paramString) {
    return (paramString != null) ? paramString.replaceAll("\\$", Matcher.quoteReplacement("\\$")) : null;
  }
  
  public static String MD5(String paramString) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      byte[] arrayOfByte = messageDigest.digest(paramString.getBytes());
      StringBuilder stringBuilder = new StringBuilder();
      for (byte b : arrayOfByte)
        stringBuilder.append(Integer.toHexString(b & 0xFF | 0x100), 1, 3); 
      return stringBuilder.toString();
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      return paramString;
    } 
  }
  
  public static boolean isInteger(String paramString) {
    return (paramString != null && paramString.matches("-?\\d+"));
  }
  
  public static boolean isDouble(String paramString) {
    return (paramString != null && paramString.matches("-?\\d+.\\d+"));
  }
  
  public static String generateRandomString(int paramInt) {
    String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    StringBuilder stringBuilder = new StringBuilder(paramInt);
    for (byte b = 0; b < paramInt; b++) {
      int i = (int)(str.length() * Math.random());
      stringBuilder.append(str.charAt(i));
    } 
    return stringBuilder.toString();
  }
  
  public static String sizeString(URL paramURL) {
    String str = "";
    if (paramURL != null)
      try {
        Image image = new Image(paramURL.toString());
        str = "width='" + image.getWidth() + "' height='" + image.getHeight() + "'";
      } catch (Exception exception) {
        Log.c(exception);
      }  
    return str;
  }
  
  public static String getValueFromKeyValuePair(String paramString1, String paramString2) {
    if (paramString2 != null && paramString1 != null && paramString2.startsWith(paramString1.toUpperCase() + "=")) {
      String str = paramString2.substring(paramString1.length() + 1).trim();
      if (!str.isEmpty())
        return str; 
    } 
    return null;
  }
  
  public static String addSpaceBeforeUppercase(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    for (char c : paramString.toCharArray()) {
      if (Character.isUpperCase(c) && !stringBuilder.isEmpty())
        stringBuilder.append(" "); 
      stringBuilder.append(c);
    } 
    return stringBuilder.toString();
  }
  
  private static final Pattern d = Pattern.compile("<ctx>(.*)</ctx>");
  
  public static String removeCtxKeyword(String paramString) {
    if (paramString != null) {
      boolean bool = paramString.startsWith("<ctx>");
      paramString = d.matcher(paramString).replaceAll("").trim();
      if (bool)
        paramString = firstLetterUppercase(paramString); 
    } 
    return paramString;
  }
  
  public static String getOppositePluralSingular(String paramString) {
    return isPlural(paramString) ? getSingular(paramString) : getPlural(paramString);
  }
  
  public static boolean isPlural(String paramString) {
    if (paramString.endsWith("ies"))
      return true; 
    if (paramString.endsWith("es"))
      return true; 
    if (paramString.endsWith("s"))
      return (!paramString.endsWith("ss") && !paramString.endsWith("us")); 
    return false;
  }
  
  public static String getPlural(String paramString) {
    if (paramString.endsWith("y") && !a(paramString.charAt(paramString.length() - 2)))
      return paramString.substring(0, paramString.length() - 1) + "ies"; 
    if (paramString.endsWith("s") || paramString.endsWith("sh") || paramString.endsWith("ch") || paramString.endsWith("x") || paramString.endsWith("z"))
      return paramString + "es"; 
    return paramString + "s";
  }
  
  private static boolean a(char paramChar) {
    return ("AEIOUaeiou".indexOf(paramChar) != -1);
  }
  
  public static String getSingular(String paramString) {
    if (paramString.endsWith("ies"))
      return paramString.substring(0, paramString.length() - 3) + "y"; 
    if (paramString.endsWith("es"))
      return paramString.substring(0, paramString.length() - 2); 
    if (paramString.endsWith("s"))
      return paramString.substring(0, paramString.length() - 1); 
    return paramString;
  }
}
