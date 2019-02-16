package ru.capper2.sale.dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FileDao {
  private static final Logger LOGGER = LoggerFactory.getLogger(FileDao.class);

  protected void saveCollectionToFile(Path file, Collection<String> collect) {
    try {
      if (Files.deleteIfExists(file)) {
        LOGGER.info("File \"{}\" was deleted.", file.toAbsolutePath());
      }
      LOGGER.info("File \"{}\" was created.", file.toAbsolutePath());
      Files.write(
          file,
          collect,
          StandardCharsets.UTF_8,
          StandardOpenOption.CREATE,
          StandardOpenOption.APPEND
      );
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  protected void saveStringToFile(Path file, String entry) {
    try {
      Files.write(
          file,
          Collections.singleton(entry),
          StandardCharsets.UTF_8,
          StandardOpenOption.CREATE,
          StandardOpenOption.APPEND
      );
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
