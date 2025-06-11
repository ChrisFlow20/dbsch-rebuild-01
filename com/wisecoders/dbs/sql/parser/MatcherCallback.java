package com.wisecoders.dbs.sql.parser;

@FunctionalInterface
public interface MatcherCallback {
  Object call(PatternPhrase paramPatternPhrase);
}
