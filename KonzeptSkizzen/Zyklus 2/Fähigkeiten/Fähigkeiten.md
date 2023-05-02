Titel: Konzeptskizze für Zyklus 2

Author: Bent Schöne, Marvin Petschulat, Edwin Dik

---

## Beschreibung der Aufgabe

Implementierung von zwei Fähigkeiten, welche der Spieler nutzen kann. Der Spieler soll mit
den Zaubern/Fähigkeiten keinen Schaden verursachen. Die Fähigkeiten sollen von
Ressourcen wie Lebenspunkte (HP), Mana (MP) oder Ausdauer abhängig sein.

Bei jedem Level aufstieg sollen die Charakterwerte des Helden erhöht werden und der Held
soll die Zaubersprüche/Fähigkeiten erlernen können.

Zusätzlich soll das bereits vorhandene Skill-System, Feuerball-Skill und XP-System
analysiert und ihre Funktionalitäten erläutert werden.

---

## Beschreibung der Lösung

Gravity Storm / Black hole:

| Kosten | Cooldown | Freigeschalten ab | Verhalten               | Effekt               | Hotkey | Aussehen? |
|--------|----------|-------------------|-------------------------|----------------------|--------|-----------|
| 5 MP   | 30 sec   | LVL 10?           | Langsam fliegender ball | Zieht gegner in sich |        |           |

Frost Nova:

| Kosten | Cooldown | Freigeschalten ab | Verhalten                   | Effekt             | Hotkey | Aussehen? |
|--------|----------|-------------------|-----------------------------|--------------------|--------|-----------|
| 2 MP   | 10 sec   | LVL 3?            | Kreis aus Eis unterm Helden | Verlangsamt um 30% |        |           |

Neue tastenbelegung in dungeon_configs.json

zahlen 1,2,3,4,5 ?

popup mit Hotkeys ?? (GUI)

Mana implementierung. Skaliert mit Level. in XPComponent? Alle Skills Kosten ?

### XP-System

Das XP-System holt sich von allen Entities im Spiel dessen XPComponent. Ist von einem
Entity das benötigte XP erreicht, um einen LevelUp durchzuführen, so wird die
performLevelUP() Methode im XP-System aufgerufen.

In dieser Methode wird das level von der Entity erhöht. Danach wird die XP vom Entity auf
`xpLeft` gesetzt. Als Letztes wird die LevelUp Methode von der Entity aufgerufen.

### XP-Component

Das XP-Component verwaltet, wie viel XP ein Entity braucht, um in das nächste Level (nicht
das Dungeon Level gemeint) zu steigen oder wie viel XP ein Entity "fallen lässt”, wenn es
besiegt wird. Bei einem LevelUp wird mit der Formel:
`neededXP = 0.5 * level^2 + 100` die für das nächste Level benötigte Anzahl an XP berechnet.

Wenn ein Entity besiegt wird, dann wird mit der Methode getLootXP() die Anzahl an XP
über gegeben. Wurde kein Wert für den zu übergebenden XP gesetzt (lootXP == -1), dann
wird die Hälfte der Menge an XP, die das Entity enthält, übergeben.

---

### Skill-System

In der klasse SkillSystem wird jeden Frame die Methode update() ausgeführt.
Diese methode reduziert alle cool-downs für alle Skills für jede Entity die ein
SkillComponent haben.

### Skill-Component

In der Klasse SkillComponent wird eine Liste aus Skills gespeichert. Diese Liste kann man mit get, add und remove
manipulieren.
Von allen Skills wird der Cooldown von SkillSystem reduziert mithilfe der Methode reduceAllCoolDowns() in
SkillComponent.

#### Beispiel Skill: Feuerball

Um ein FireballSkill zu erstellt wird in dem Konstruktor der Oberklasse DamageProjectileSkill:

- Der Path der Texturen
- Die Geschwindigkeit
- Der Schadenstyp und Wert
- Die Position
- Das Ziel des Projektils
- Die Reichweite

des FireballSkills übergeben.

???

Dieser Skill wird nicht wie erwartet in einem SkillComponent geschpeichert sondern
in der PlayableComponent vom Helden

???


---

## Methoden und Techniken

Keine Pattern

---

## Ansatz und Modellierung

ISkillfunktion update() hinzufügen update wird jeden frame aufgerufen in Game ``skillFunktion.update(entity)``

SkillTools neue methode getEntitysInRange

````
Blackhole extends Damageprojektileskill implements ISkillfunciton:
    execute():
        super(0 dmg rest schon)
    update():
        list = getEntitysInRange
        ??? cooldown
        foreach list:
            v = unidirectionalVector(this,list)
            list.setcurrentvelocity(list.getcurrentvelocity + v)


FrostNova extents Entity implements ISkillFunktion:
    list<Entity> list
    execte():
        neue Entity mit Hitbox
        Hitboxenter: slow alle entity und speichert in array
        Hitboxleave: slow weg machen und löscht aus array
    update():
        wie lange es existert reduzieren
        wenn 0 und array remove
        sonst alle entities im array schneller machen dann remove

Problem: hitbox is square vlt fällt es nicht auf
````
---

## UML

![FähigkeitenUML](Fähigkeiten.png)
