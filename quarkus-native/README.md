# quarkus-native

Dieses Projekt dient als Showcase für die Quarkus Native Technologie.

Es basiert auf https://github.com/quarkusio/quarkus-quickstarts/tree/main/getting-started und den Guides
https://quarkus.io/version/2.7/guides/building-native-image und https://quarkus.io/guides/getting-started .

## Herkömmliche Quarkus-Verwendung

### Anwendung im dev mode ausführen

Die Anwendung lässt sich wie gehabt im Qarkus-Dev-Mode inklusive Live Coding ausführen:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_** Quarkus Dev UI ist standardmäßig über http://localhost:8080/q/dev/ erreichbar.

### Packen und Ausführen der Anwendung

Die Anwendung kann ganz normal gepackt werden:
```shell script
./mvnw package
```
Dies produziert die `quarkus-run.jar` Datei im `target/quarkus-app/` Verzeichnis.

## Erstellen einer nativ ausführbaren Anwendung (native executable)

Wenn alle notwendigen Pakete und GraalVM installiert sind (siehe
https://quarkus.io/version/2.7/guides/building-native-image), kann eine Native Executable erstellt werden.

```shell script
./mvnw package -Pnative
```
> **_NOTE:_** Falls es unter Windows Fehlermeldungen bezüglich der native Toolchain gibt, sollte folgender Befehl
> funktionieren (siehe https://quarkus.io/version/2.7/guides/building-native-image):
```shell script
cmd /c 'call "C:\Program Files (x86)\Microsoft Visual Studio\2017\BuildTools\VC\Auxiliary\Build\vcvars64.bat" && mvn package -Pnative'
```

Falls GraalVM nicht installiert ist, kann das native executable build in einem Container ausgeführt werden (Dafür ist 
nur Docker nötig?):
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

> **_NOTE:_** Mit GraalVM kann nur für das Betriebssystem kompiliert werden, auf dem es selbst installiert ist. D.h.,
> dass mit diesem Befehl immer eine Linuxanwendung kreiert wird, weil ein Linuximage benutzt wird. Mit
> `./mvnw package -Pnative` wird eine dem verwendeten Betriebssytem entsprechende ausführbare Datei erzeugt.

Die entstandende Linux-Datei kanna ausgeführt werden mit `./target/quarkus-native-runner`.

Die Windows-Datei hat eine `.exe`-Endung `./target/quarkus-native-runner.exe`.

