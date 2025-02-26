package com.umg.umg_backend.util;

import java.nio.file.Path;

public class CoverUtil {
  public static Path COVER_PATH = Path.of("covers");
  public static final String FILE_EXTENSION = "jpg";

  public static String getNameAndExtension(String name) {
    return name + "." + FILE_EXTENSION;
  }
}
