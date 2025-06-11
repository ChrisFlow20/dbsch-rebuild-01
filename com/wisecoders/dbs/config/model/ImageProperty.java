package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.nio.file.Path;

public class ImageProperty extends FileProperty {
  ImageProperty(Property paramProperty, String paramString, Path paramPath) {
    super(paramProperty, paramString, paramPath);
  }
  
  public Glyph g() {
    return BootstrapIcons.image;
  }
}
