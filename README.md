# spring-workshop-sample

a sample repository for an introduction into the spring framework in kotlin

## Precheck

Folgende Befehle sollten ohne Fehler auf einem Commandprompt ausgeführt werden können, um zu sehen, dass die
Entwicklungsumgebung richtig konfiguriert ist:

Wir nutzen GIT als Versionskontrollsystem. Eine Version um 2.20 oder höher sollte installiert sein:

    git --version 

Unser Schulungsservice wird auf einer Java Virtual Machine (JVM) laufen. Die Java Runtime sollte in der Version 17 oder
höher vorliegen:

    java --version

Um Kotlincode kompilieren zu können, wird neben der Java Virtual Machine das Java Development Kit (JDK)). Der Compiler
sollte dieselbe Version wie die Runtime besitzen.

    javac --version

Eine IDE hilft eklatant bei der Entwicklung mit Kotlin. Selbstverständlich ist eine IDE immer eine persönliche Sache.
Für die Schulung empfehle ich aber `IntelliJ` in der Version `2022.X`

## Setup

Folgende Befehle sollten zu einem erfolgreichen Build des Schulungsprojekt führen und die Tests ausführen. Genutzt wird
dabei das Buildtool
`Gradle` in der Version `7.6.1` mit einem `openJDK-17` als JVM. Gradle muss nicht extra installiert werden. Das Projekt
zieht sich
seine Buildumgebung automatisch.

Per `git clone` wird das Projekt zunächst von der Git Hostingplattform gitlab kopiert.

    git clone https://github.com/stopal/SpringSchulung

Über einen Sprung ins Projekt wird dann `gradlew` ausgeführt. Es handelt sich dabei um ein Shellskript. Bitte schauen
Sie für welches Betriebssystem Sie welches Skript ausführen.

    $ cd kotlin-workshop
    $ ./gradlew clean test

Der Aufruf von `gradlew` lädt das Gradle-Builtool herunter. Im Anschluss wird `gradle` selbst ausgeführt
und lädt alle Abhängigkeiten des Repositories herunter und führt dann den Compiler und die Testsuites aus. Der
Projektbuild muss mit einem `BUILD SUCCESSFULL` am Ende quittieren. Etwaige Warning und Exceptions während des Build
können ignoriert werden. Dies sind Artefakte aus den Schulungsunterlagen.

## Build

Buildartefakte löschen und das Projekt aufräumen

    ./gradlew clean

Tests ausführen

    ./gradlew test

Projekt paketieren.

    ./gradlew assemble

Projekt paketieren und tests ausführen.

    ./gradlew build

Service starten

    ./gradlew bootRun

## Übersicht

In den einzelnen Modulen innerhalb des Projekts finden sich folgende Inhalte:

`examples` Alle theoretischen Blöcke sind hier noch einmal, mit Kommentaren versehen, aufgelistet.

`service` Ein Beispielservice, an dem wir die meiste Zeit arbeiten werden.

Jedes Modul hat eine feste Verzeichnisstruktur:

    ./build                 # Buildartefakte. Kompilierte Dateien, gepackte Archive. Geparste Ressourcen
    ./src                   # Alle Source-Files des Moduls
    ./src/main              # Alle produktiven Sourcefiles für den späteren Service, bzw. Programm.
    ./src/main/kotlin       # Die Kotlin-Sourcefiles
    ./src/main/resources    # Alle Resource-Files die neben Programmdateien existieren. Konfigurationsdaten, etc.
    ./src/test              # Alle Test-Sourcefiles. Hier liegen alle Unit- und Componenttests
    ./src/test/kotlin       # Die Kotlin Test-Sourcefiles
    ./src/test/resources    # Alle Test-Ressourcen. Konfiguration, etc.
    ./build.gradle          # Die Gradle-Konfiguration. Dependencies, Buildkonfiguration, etc.

## Links:

Diese Links liefern Informationen zu Kotlin und den genutzten Testframeworks.

Ein gutes GIT Cheatsheet

* https://education.github.com/git-cheat-sheet-education.pdf

Informationen zu Kotlin, der Syntax und APIs.

* https://kotlinlang.org/docs/getting-started.html
* https://kotlinlang.org/docs/reference/

Informationen zum genutzten Spring Boot Framework:

* https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/

Spring Annotation Cheatsheet

* https://www.jrebel.com/sites/rebel/files/pdfs/cheatsheet-jrebel-spring-annotations.pdf

Informationen zu den genutzten Testframework JUnit 5:

* https://junit.org/junit5/docs/current/user-guide/

Weitere Übungsmöglichkeiten:

- Kotlin:
  - Übungsplattform: https://play.kotlinlang.org/koans/overview
- Spring Boot:
  - Offizielle Übungsplattform: https://spring.academy/courses
  - Kostenlose Kurse (nach Registrierung)

# Aufgaben

## Einstiegsaufgaben

### Einstieg Controller

- [KundeController](src/main/kotlin/com/example/springschulung/kunde/KundeController.kt)

### Einstieg Service

- [KundeService](src/main/kotlin/com/example/springschulung/kunde/KundeService.kt)

### Einstieg Repository

- [KundeRepository](src/main/kotlin/com/example/springschulung/kunde/KundeRepository.kt)

## Weiterführende Aufgaben

### Response Konfiguration mit ResponseEntity\<T>

- Stellt den Rückgabetypen von allen Endpunkten auf ResponseEntity\<T> um
  - [Javadoc](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html)
- Stellt die Rückgabe auf HTTP-Statuscodes um die Sinn machen
- Unterscheidet dabei, ob die angefragte Ressource auch wirklich existiert, um ggfs. einen anderen Status-Code zu
  schicken
- Ihr müsst ggfs. die bestehenden Tests anpassen
- Neue Tests Schreiben für die Fehlerfälle

### Unittests für den KundeService (Mocking Übung)

- Fügt ‘mockk’ als Dependency hinzu:
  - testImplementation("io.mockk:mockk:1.13.4")
- Erstellt eine Testklasse für den KundeService (falls noch nicht geschehen)
- Versucht die Funktion zu testen, welche die Kunden mit Vorname nach dem Buchstaben filtert
- Mockt das Repository
- Positivtest
- Negativtest

### Operationen finalisieren für den KundeController

- Implementiert für den KundeController die restlichen HTTP-Operationen
  - PUT - um einen bestehenden Eintrag zu ändern
  - DELETE - um einen Eintrag zu löschen
- Tests nicht vergessen

### Vertrag Entity implementieren

- Wir möchten jetzt auch Verträge im Service verwalten
- VertragEntity:
  - Vertragsnummer - Primärschlüssel
  - Vertragsart: Strom / Gas / Wasser Typ: Enum
  - Vertragsbeginn - Typ: Localdate
  - Vertragsende - Typ: Localdate
- Controller und Service anlegen
- Alle CRUD Operationen implementieren

### Relationale Verknüpfung von Vertrag und Kunde

- Die Entities Kunden und Verträge relational verknüpfen
  - KundeEntity benötigt @OneToMany
  - VertragsEntity benötigt @ManyToOne sowie @JoinColumn
- Vertragscontroller / Vertrags-API anpassen sodass die Operationen immer die kundennummer verlangen um einen
  eindeutigen Kunden zu referenzieren
  - z.B: für GET / PUT: /kunde/{kundennummer}/vertrag/{vertragsnummer}
- VertragRepository ggfs anpassen, da jetzt die Operationen immer mit einem Kundenobjekt erfolgen müssen!
