# Sounds
Titel: Konzeptskizze für Zyklus 4

Author: Bent Schöne, Marvin Petschulat, Edwin Dik

---
## Beschreibung der Aufgabe

Es sollen für alle möglichen Situationen für Sound Effekte, Sounds eingebaut werden.

---

## Beschreibung der Lösung

Implementiert werden sollen Sounds für:
- Main-Game (ab verschiedenen Etagen andere Musik)
- Game-Over Screen
- Kisten öffnen
- Leiter klettern
- Potion einsetzen
- Items aufnehmen
- Schwert/Bogen wechseln
- Skills anwenden(Pfeil, Schwert, Fireball)
- Levelup Sound
- Fallen Sounds (+Lever)
- NPC Geist beim Heilen
- Monster stirbt
- Monster wird getroffen

Wir haben vor die Sounds zu samplen bzw teilweise selber zuerstellen über FL-Studio,
damit wir die einheitlich bekommen und wieder einzigartige Elemente ins Dungeon bekommen.

---

## Methoden und Techniken

Methoden werden wieder gemäß der Vorlesung mit Javadoc dokumentiert.

Sounds werden geloggt.

Für die Versionskontrolle wird Git verwendet.

---

## Ansatz und Modellierung

Es wird das vorhandene Interface `Sound.java` aus der Bibliothek gdx verwendet
und als Attribut ``Sound sound`` in den jeweiligen Klassen hinzugefügt, um die Sounds abzuspielen.
``sound`` wird dann in einem try-catch mit dem Code: `Gdx.audio.newSound(Gdx.files.internal("file.mp3"));`,
initialisiert und anschließend kann mit den Methoden `play(volume)` `loop(volume)` der Sound abgespielt werden.
Dabei wird die Exception `GdxRuntimeException` abgefangen.


#### x

Manche Sounds wie z.b "Entity wird getroffen" oder "Entity stirbt", passieren von dem Hero an einem
anderen Ort deswegen macht es Sinn es zu pannen.
Es soll geguckt werden wo der Hero ist und wo der Sound spielen soll. Dazu soll berechnet werden wie
weit Links oder wie weit rechts der Sound abgespielt werden soll.
So wird mit der Methode aus dem Interface `play (volume, pitch, pan)` die Variable pan dann gesetzt.

#### x

Für die Hintergrundmusik vom Spiel, wird eine neue Methode ``playSound()`` in `Game.java` erstellt, dort wird
abhängig von der Variable `depth` die jeweilige Hintergrundmusik für diese Ebene abgespielt.

Der Parameter `volume`, der für die Lautstärke benutzt wird (Range 0 bis 1),
wird je nach ermessen gewählt (Abhängig davon wie laut der eigentliche Sound ist und wie laut er im
Spiel zuhören sein soll).

---

## UML

![sound2](Sound2.png)
