Titel: Konzeptskizze für Zyklus 2

Author: Bent Schöne, Marvin Petschulat, Edwin Dik

---
## Beschreibung der Aufgabe
Implementierung von zwei Fähigkeiten, welche der Spieler nutzen kann. Der Spieler soll mit
den Zaubern/Fähigkeiten keinen Schaden verursachen. Die Fähigkeiten sollen von
Ressourcen wie Lebenspunkte (HP), Mana (MP) oder Ausdauer abhängig sein.


Bei jedem Levelaufstieg sollen die Charakterwerte des Helden erhöht werden und der Held
soll die Zaubersprüche/Fähigkeiten erlernen können.


Zusätzlich soll das bereits vorhandene Skill-System, Feuerball-Skill und XP-System
analysiert und ihre Funktionalitäten erläutert werden.


2 Fähigkeiten
- Gravity storm / Black hole
- frost nova

Mana implementieren

---

## Beschreibung der Lösung

Gravity Storm / Black hole:

Zieht alle gegner in die Mitte.

Kosten: 5 mana

Frost Nova:

Friert den Boden unter dem Helden ein. Alle Monster die auf dem Eis rennen werden verlangsamt.

Kosten: 2 mana

Beide benutzen mana

Neue tastenbelegung in dungeon_configs.json


### XP-System

Das XP-System holt sich von allen Entities im Spiel dessen XPComponent. Ist von einem
Entity das benötigte XP erreicht, um einen LevelUp durchzuführen, so wird die
performLevelUP() Methode aufgerufen.


In dieser Methode wird die XP vom Entity auf 0 gesetzt und die Anzahl an XP, die über den
zu benötigten Level Aufstieg gebraucht wurden, werden hinzugerechnet. Danach wird das
Level vom Entity um 1 erhöht.


### XP-Component

Das XP-Component verwaltet, wie viel XP ein Entity braucht, um in das nächste Level (nicht
das Dungeon Level gemeint) zu steigen oder wie viel XP ein Entity "fallen lässt”, wenn es
besiegt wird. Bei einem LevelUp wird mit der Formel:
`𝑓(𝑥) = 𝑠𝑙𝑜𝑝𝑒 * (depth + 100)^2` die für das nächste Level benötigte Anzahl an XP berechnet.


Wenn ein Entity besiegt wird, dann wird mit der Methode getLootXP() die Anzahl an XP
übergegeben. Wurde kein Wert für den zu übergebenden XP gesetzt (lootXP == -1), dann
wird die Hälfte der Menge an XP, die das Entity enthält, übergeben.

### Skillsystem erklären



FireballSkill


Wenn ein FireballSkill Objekt erstellt wird, dann wird dem Konstruktor der Oberklasse
DamageProjectileSkill:
- Der Path der Texturen
- Die Geschwindigkeit
- Der Schadenstyp und Wert
- Die Position
- Das Ziel des Projektils
- Die Reichweite

des FireballSkills übergeben.


---

## Methoden und Techniken

Keine Pattern

---

## Ansatz und Modellierung


---

## UML
