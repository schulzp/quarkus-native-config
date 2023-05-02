# \[Quarkus] Minimal Working Example for Broken Test Profile on Native Docker Container

Ever since migrating to Quarkus 3.0.1 our native integration tests started to fail.

## Steps to Reproduce

Build and verify a linux-native containerized version of the application:
 
```shell script
./mvnw clean verify -Pnative
```

It will fail when executing the integration tests, see `target/quarkus.log`:

```text
May 02, 2023 11:00:09 AM io.quarkus.runtime.ApplicationLifecycleManager run
ERROR: Failed to start application (with profile [prod])
io.smallrye.config.ConfigValidationException: Configuration validation failed:
	java.util.NoSuchElementException: SRCFG00014: The config property example.push.apn.server is required but it could not be found in any config source
	java.util.NoSuchElementException: SRCFG00014: The config property example.push.apn.enabled is required but it could not be found in any config source
	at io.smallrye.config.ConfigMappingProvider.mapConfigurationInternal(ConfigMappingProvider.java:979)
	at io.smallrye.config.ConfigMappingProvider.lambda$mapConfiguration$3(ConfigMappingProvider.java:935)
	at io.smallrye.config.SecretKeys.doUnlocked(SecretKeys.java:28)
	at io.smallrye.config.ConfigMappingProvider.mapConfiguration(ConfigMappingProvider.java:935)
	at io.smallrye.config.ConfigMappings.mapConfiguration(ConfigMappings.java:91)
	at io.smallrye.config.SmallRyeConfigBuilder.build(SmallRyeConfigBuilder.java:624)
	at io.quarkus.runtime.generated.Config.readConfig(Unknown Source)
	at io.quarkus.deployment.steps.RuntimeConfigSetup.deploy(Unknown Source)
	at io.quarkus.runner.ApplicationImpl.doStart(Unknown Source)
	at io.quarkus.runtime.Application.start(Application.java:101)
	at io.quarkus.runtime.ApplicationLifecycleManager.run(ApplicationLifecycleManager.java:111)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:71)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:44)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:124)
	at io.quarkus.runner.GeneratedMain.main(Unknown Source)
```

However, the properties exists and are provided by the `application.properties` source.
That can be seen in the logs created by [`org.example.Application`](src/main/java/org/example/Application.java), see `target/quarkus.log`:

```text
[SOURCE]: PropertiesConfigSource[source=jar:file:///project/native-config-0.0.0-SNAPSHOT-runner.jar!/application.properties]
 * quarkus.test.native-image-profile: test,
 * example.push.apn.enabled: false,
 * example.push.apn.topic: bundle-id,
 * example.push.apn.server: api.sandbox.push.apple.com,
 * example.push.apn.signing-key-id: key-id,
 * example.push.apn.team-id: team-id,
 * %prod.example.push.apn.server: api.push.apple.com,
 * %prod.example.push.apn.enabled: true,
 * example.push.apn.signing-key: base64-encoded-key
```
