package org.example;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.ConfigValue;

@QuarkusMain
public class Application {

  public static void main(String... args) {
    Config config = ConfigProvider.getConfig();
    final Logger logger = Logger.getLogger("CONFIG_DEBUG");
    config.getConfigSources().forEach(source -> {
      logger.log(Level.WARNING, "[SOURCE]: " + source.getName() + "\n" + source.getProperties().entrySet().stream().map((e) -> " * " + e.getKey() + ": " + e.getValue()).collect(
          Collectors.joining(",\n")));
    });
    Stream.of("example.push.apn.server", "example.push.apn.enabled")
        .forEach(key -> {
          try {
            ConfigValue value = config.getConfigValue(key);
            logger.log(Level.WARNING, "[PROPERTY] " + key + ": " + value.getValue());
          } catch (Exception e) {
            logger.log(Level.SEVERE, "[PROPERTY.ERROR] " + key, e);
          }
        });
    Quarkus.run(args);
  }

}
