# quarkus-native

Dieses Projekt dient als Showcase für die Quarkus Native Technologie.

Es basiert auf https://github.com/quarkusio/quarkus-quickstarts/tree/main/getting-started
und den Guides https://quarkus.io/version/2.7/guides/building-native-image und https://quarkus.io/guides/getting-started .

## Herkömmliche Quarkus-Verwendung

### Anwendung im dev mode ausführen

Die Anwendung lässt sich wie gehabt im Qarkus-Dev-Mode inklusive Live Coding ausführen:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

### Packen und Ausführen der Anwendung

Die Anwendung kann ganz normal gepackt werden:
```shell script
./mvnw package
```
Dies produziert die `quarkus-run.jar` Datei im `target/quarkus-app/` Verzeichnis.

## Erstellen einer nativ ausführbaren Anwendung (native executable)

You can create a native executable using: 
Wenn alle notwendigen Pakete und GraalVM installiert (siehe 
https://github.com/quarkusio/quarkus-quickstarts/tree/main/getting-started) sind kann eine Native Executable erstellt
werden

```shell script
./mvnw package -Pnative
```

Falls GraalVM nicht installiert ist, kann das native executable build in einem Container ausgeführt werden (Dafür ist 
nur Docker nötig?):
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

> Mit GraalVM kann nur für das Betriebssystem kompiliert werden, auf dem es selbst installiert ist. D.h., dass mit diesem
Befehl immer eine Linuxanwendung kreiert wird, weil ein Linuximage benutzt wird. Mit `./mvnw package -Pnative` wird eine
dem verwendeten Betriebssytem entsprechende ausführbare Datei erzeugt wird.

Die entstandende Linux-Datei kanna ausgeführt werden mit `./target/getting-started-1.0.0-SNAPSHOT-runner`.

Die Windows-Datei hat eine `.exe`-Endung `./target/getting-started-1.0.0-SNAPSHOT-runner.exe`.

