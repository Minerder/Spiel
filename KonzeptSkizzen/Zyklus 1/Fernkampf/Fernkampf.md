#### Titel: Konzeptskizze für Zyklus 1

#### Author: Bent Schöne, Marvin Petschulat, Edwin Dik

---

## Beschreibung der Aufgabe

Das Dungeon soll um die folgenden 2 Projektile erweitert werden:
- Pfeil der von Wänden abprallt
- Spark zielt automatisch auf den Helden oder ein Monster und fliegt darauf zu

---

## Beschreibung der Lösung

Damit bestimmte Projektile welche die Wände berühren auch abprallen oder Projektile ihr Ziel im Flug ändern können,
müssen wir die Klasse `ProjectileSystem` überarbeiten. Für beide Projektile werden neue Animationen erstellt.

### Knockback nach einem Treffer

In der Klasse `DamageProjectileSkill` steht ein Lambda Ausdruck der entscheidet, was passiert wenn
eine Entität getroffen wird. Dort können wir einfügen, dass die Velocity von der getroffenen Entität
je nachdem von wo die Entität getroffen wird, erhöht oder verringert wird.

### Erklärung des Projektil Systems

Jeder Skill der Damage machen soll, kann die Klasse `DamageProjectileSkill` benutzen.
Wenn man ein neuen Skill erstellt muss dieser von der Klasse abgeleitet werden. Die neue Skill
Klasse benötigt Texturen, Projektil-Geschwindigkeit, Projektil-Schaden, Projektil-Hitbox,
Projektil-Range und ein Ziel. Den neuen Skill kann man dann entweder dem Helden in der `PlayableComponent`
Klasse oder einem NPC in der `MeleeAI` Klasse geben.

---

## Methoden und Techniken

Um das Ziel vom Skill festzulegen, können wir eine Methoden-Referenz auf die Klasse `SkillTools`
und die Methode `getCursorPositionAsPoint` benutzen.

---

## Ansatz und Modellierung

![Fernkampf UML](FernkampfUML.png)
