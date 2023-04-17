Titel: Konzeptskizze für Zyklus 1

Author: Bent Schöne, Marvin Petschulat, Edwin Dik

---
## Beschreibung der Aufgabe

5 Gegner
- Ratte
- Skeleton
- Imp (Texturen vorhanden)
- Chort (Texturen vorhanden)
- Geist (schwer)


---

## Beschreibung der Lösung

### Eigenschaften der Monster
Ratte

| Verhalten | Leben | Geschwindigkeit | Wander Verhalten   |
|-----------|-------|-----------------|--------------------|
| Passiv    | 1HP   | 0.1             | durchs ganze level |

Skeleton

| Verhalten | Leben | Geschwindigkeit | Angriff           | Schaden | Wander Verhalten        |
|-----------|-------|-----------------|-------------------|---------|-------------------------|
| Aggresiv  | 3HP   | 0.5             | Fernkampf (Bogen) | 1HP     | in einem kleinen Radius |


Imp

| Verhalten | Leben | Geschwindigkeit | Angriff                | Schaden | Wander Verhalten        |
|-----------|-------|-----------------|------------------------|---------|-------------------------|
| Aggresiv  | 2HP   | 0.15            | Fernkampf  (Feuerball) | 1HP     | in einem kleinen Radius |

Chort

| Verhalten | Leben | Geschwindigkeit | Angriff  | Schaden | Wander Verhalten   |
|-----------|-------|-----------------|----------|---------|--------------------|
| Aggresiv  | 4HP   | 0.15            | Nahkampf | 2HP     | durchs ganze level |

Geist


| Verhalten | Leben | Geschwindigkeit | Angriff                     | Schaden | Wander Verhalten   | Besonderheit                                                     |
|-----------|-------|-----------------|-----------------------------|---------|--------------------|------------------------------------------------------------------|
| Passiv    | 2HP   | 0.25            | Nahkampf und Kontaktschaden | 1HP     | durchs ganze level | Kann durch Wände Fliegen und wird unsichtbar außerhalb vom Level |

### Einbindung der Monster

Ebenen System. Desto tiefer desto schwerer. Mehr Monster stärkere Monster

| ab Ebene 1 | ab Ebene 3  | ab Ebene 6            | ab Ebene 10         |
|------------|-------------|-----------------------|---------------------|
| Ratten 0-2 | Geister 1-2 | Skelette und 1-2 Imps | Imps und Chorts 1-4 |

---

## Methoden und Techniken

Aus Vorlesung: Methoden Referenzen, Lamda, Git, Javadoc, Streams

Vlt Pattern Strategy

### AISystem Erklären

### Spawning "System"

---

## Ansatz und Modellierung

![Monster UML](MonsterUML.png)
