Titel: Konzeptskizze für Zyklus 3

Author: Bent Schöne, Marvin Petschulat, Edwin Dik

---
## Beschreibung der Aufgabe

Es sollen 2 neue Fallen im Dungeon implementiert werden

---

## Beschreibung der Lösung

Es soll eine Spikefalle erstellt werden, die soll Damage verursachen, wenn der Hero darüber läuft.
Die Spikefalle soll deaktivierbar sein mit dem Hebel.

Die zweite Falle soll eine Pfeilfalle sein, die soll schießen wenn der Hero vor die Falle rennt.
Die Falle hat einen längeren Cooldown das sie erst nach einer gewissen Zeit wieder schießt.

Spikefalle:
- Spikes die dmg verursachen, wenn der Held darüber läuft
- Die Falle soll nur einmal aufgehen, sie macht aber Damage wenn man durchläuft und wenn sie hochgeht.
- Es soll einen Schalter geben der die Falle ausmacht
- kurzer Cooldown

Pfeilfalle:
- Die Falle schießt ein Pfeil sobald der Hero in der Hitbox ist.
- Es soll ein Cooldown von 2s geben. Das die Falle nicht die ganze Zeit schießt

---

## Methoden und Techniken

Methoden werden wieder gemäß der Vorlesung mit Javadoc dokumentiert.

Für die Versionskontrolle wird Git verwendet.


---

## Ansatz und Modellierung

Es wird eine neue Oberklasse ``Trap`` erstellt. Diese Klasse kümmert sich darum,
die Entity zu erstellen und diese alle Components zu geben.

Die Klasse ``SpikeTrap`` erbt von der Oberklasse ``Trap``. In dem Konstruktor werden
2 neue ICollide Funktionen erstellt die weitergegeben werden. In diesen Funktionen wird
beschrieben wie die Falle schaden macht.



Es wird eine neue Ober Klasse ``Trap`` erstellt, die soll einen Konstruktor haben in dem Konstruktor werden die Texturen angegeben und mit der LambdaMethode ob der Hero in die Hitbox betritt und wieder verlässt.
Dazu soll eine Methode setupEntity erstellt werden mit mit PostionComponent, AnimationComponent, und einem HitboxComponent
Außerdem sollen Methoden für die Animationen erstellt werden.


Um die Spikefalle zu implementieren erstellen wir auch eine neue Klasse ``SpikeTrap``.
Die Spikefalle eerbt von der Ober Klasse ``Trap``.
Die Klasse bekommt einen Konstruktor mit einem HitboxComponent wenn der Hero in der Hitbox ist bekommt er Schaden.
Es soll zusätzlich ein Schalter von der Klasse Schalter Variable hinzugefügt werden.


Um die Pfeilfalle zu implementieren erstellen wir auch eine neue Klasse ``ArrowTrap``.
Die Pfeilklasse eerbt von der Ober Klasse ``Trap``.
Die Klasse bekommt einen Konstruktor der die veerbungs Attribute übernimmt und einer langer Hitbox die in die entgegengesetze Richtung der Wand geht.
Es soll zusätzlich noch eine Methode changehitbox() geben die, die Länger der Hitbox ändert.

Die Abstrakte Klasse ``getPosFromRandomWall()`` soll eine Random Position von einer Wand in dem Raum aufgreifen.
Die Methode ist für die Klasse ``ArrowTrap`` gedacht.

Für den Schalter implementieren wir eine Schalter Klasse ``Lever``.
Die Schalter Klasse hat eine Variable pressed vom Typ Boolean die true wird wernn der Schalter gedrückt wurde.
Dazu gibt es einen Konstruktor, in dem wird ein EntityComponent, InteractionComponent, AnimationComponent erstellt.

neue abstracte hilfmethdoe getposFromRandomWall()
Neue ober klasse Falle:
- falle(String texture davor, String texture während, String textur dannach, onhiboxenter, onhitboxleave)
- setupEntity
    - neue entity mit pos, anim, hitbox comp
- animationTodavor
- animationTowährent
- animationtodannach
-
spikefalle:
- spikefalle
    - hitbox die dmg macht

pfeilfalle:
- schalter variable
- pefilfalle
    - neue entity
    - mit langer hitbox in die gegengesetzte richtung von wand
    - hitboxenter:
        - wenn pressed false neue animtaion
        - neu pfeil
- changehitbox()
    - ändert hitbox länge

Schalter klasse:
- boolean pressed
- schalter
    - erstellt neue entity
    - hitbox wenn hero entered pressed = true

---

## UML

![FallenUML](fallenUML.png)
