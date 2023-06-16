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
- Items im Inventar verschieben und Droppen

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

Für die Hintergrundmusik vom Spiel, wird eine neue Methode ``playSound()`` in `Game.java` erstellt, dort wird
abhängig von der Variable `depth` die jeweilige Hintergrundmusik für diese Ebene abgespielt.

Der Parameter `volume`, der für die Lautstärke benutzt wird (Range 0 bis 1),
wird je nach ermessen gewählt (Abhängig davon wie laut der eigentliche Sound ist und wie laut er im
Spiel zuhören sein soll).

---

## UML

![Sound3.drawio.png](Sound3.drawio.png)

