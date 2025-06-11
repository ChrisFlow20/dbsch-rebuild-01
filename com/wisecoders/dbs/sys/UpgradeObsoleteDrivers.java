package com.wisecoders.dbs.sys;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.project.store.Pref;
import java.io.File;

public final class UpgradeObsoleteDrivers extends Record {
  private final int a;
  
  private final String b;
  
  private static final String c = "lastBuildNo";
  
  public UpgradeObsoleteDrivers(int paramInt, String paramString) {
    this.a = paramInt;
    this.b = paramString;
  }
  
  public final String toString() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> toString : (Lcom/wisecoders/dbs/sys/UpgradeObsoleteDrivers;)Ljava/lang/String;
    //   6: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #8	-> 0
  }
  
  public final int hashCode() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> hashCode : (Lcom/wisecoders/dbs/sys/UpgradeObsoleteDrivers;)I
    //   6: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #8	-> 0
  }
  
  public final boolean equals(Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: <illegal opcode> equals : (Lcom/wisecoders/dbs/sys/UpgradeObsoleteDrivers;Ljava/lang/Object;)Z
    //   7: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #8	-> 0
  }
  
  public int b() {
    return this.a;
  }
  
  public String c() {
    return this.b;
  }
  
  private static final UpgradeObsoleteDrivers[] d = new UpgradeObsoleteDrivers[] { new UpgradeObsoleteDrivers(240614, "SqlServer") };
  
  public static void a() {
    int i = Pref.b("lastBuildNo", 210101);
    for (UpgradeObsoleteDrivers upgradeObsoleteDrivers : d)
      upgradeObsoleteDrivers.a(i); 
    Pref.a("lastBuildNo", Log.c);
  }
  
  public void a(int paramInt) {
    try {
      if (paramInt < this.a && Log.c > this.a) {
        File file = Sys.e.resolve(this.b + "/").toFile();
        if (file.exists()) {
          Log.c("Remove obsolete " + this.b + " drivers.");
          File[] arrayOfFile = file.listFiles();
          if (arrayOfFile != null)
            for (File file1 : arrayOfFile)
              FileUtils.b(file1);  
          FileUtils.b(file);
        } 
      } 
    } catch (Throwable throwable) {
      Log.a("Error removing obsolete " + this.b + " drivers", throwable);
    } 
  }
}
