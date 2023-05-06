# spring-workshop-sample

a sample repository for an introduction into the spring framework in kotlin

## Spring Annotation Cheat Sheet

https://www.jrebel.com/sites/rebel/files/pdfs/cheatsheet-jrebel-spring-annotations.pdf

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

## Weitere Übungsmöglichkeiten

- Kotlin:
  - Übungsplattform: https://play.kotlinlang.org/koans/overview
- Spring Boot:
  - Offizielle Übungsplattform: https://spring.academy/courses
  - Kostenlose Kurse (nach Registrierung)
