package com.wisecoders.dbs.loader.engine;

import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.Map;

@DoNotObfuscate
public interface LoaderConsumer {
  void consumeRecord(Map paramMap);
}
