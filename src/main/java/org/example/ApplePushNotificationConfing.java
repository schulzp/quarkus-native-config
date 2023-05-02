package org.example;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "example.push.apn")
public interface ApplePushNotificationConfing {

  boolean enabled();

  String server();

  String teamId();

  String signingKeyId();

  String signingKey();

  String topic();
}
