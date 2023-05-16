Titel: Konzeptskizze für Zyklus

Author: Bent Schöne, Marvin Petschulat, Edwin Dik

---
## Beschreibung der Aufgabe

Es wird ein freundlicher Geist als NPC im Dungeon implementiert, welcher entweder den Spieler langsam
verfolgt, zufällig durch das Level wandert oder auch manchmal verschwindet.

Darüber hinaus, wird ein Grabstein implementiert, der im Level mit einem Geist platziert wird. Findet der Spieler
den Grabstein, während der Geist ihn verfolgt, soll der Spieler belohnt werden.

---

## Beschreibung der Lösung

Der Geist hat eine Chance im Level zu spawnen und bewegt sich dann zufällig durch das Level. Wenn der Spieler
eine bestimmte Range zum Geist betritt fängt der Geist an den Spieler langsam zu verfolgen.

Der Spieler soll den Geist nicht angreifen können und auch er selbst soll nicht vom Geist angegriffen werden.

Findet der Spieler den Grabstein, wenn der Geist ihn verfolgt, bekommt der Spieler 10 XP und wird geheilt. Außerdem
verschwindet der Grabstein und der Geist.

---

## Methoden und Techniken

Für den Geist und den Grabstein wird das Singleton-Pattern verwendet, da von beidem jeweils nur eins im Level
sein kann. Beide Objekte können dann in anderen Leveln wiederverwendet werden, anstatt neue Objekte zu erstellen.

Methoden werden wieder gemäß der Vorlesung mit Javadoc dokumentiert.

Für die Versionskontrolle wird Git verwendet.

---

## Ansatz und Modellierung

Der Geist benötigt folgende Components:
- PositionComponent - erklärung
- VelocityComponent - erklärung
- AnimationComponent - erklärung
- AIComponent - erklärung

Der Grabstein braucht folgende Components:
- PositionComponent - erklärung
- AnimationComponent - erklärung
- HitboxComponent - erklärung

Neue Geist Klasse mit Singleton-Pattern.

Neue Grabstein Klasse mit Singleton-pattern.

---

## UML
