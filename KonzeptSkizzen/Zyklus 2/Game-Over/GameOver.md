[Titel: Konzeptskizze für Zyklus

Author: Bent Schöne, Marvin Petschulat, Edwin Dik

---
## Beschreibung der Aufgabe

Game over screen

Sobald der Hero stirbt soll das Spiel gestoppt werden und ein neues Fenster gestartet werden.
Der Nutzer soll die Entscheidung bekommen das Spiel zu schließen oder neuzustarten.

---

## Beschreibung der Lösung

Das Spiel wird pausiert wenn der Hero stirbt, danach wird mit Java Swing ein neues Fenster erstellt.
Das neue Fenster soll eine GUI sein mit 2 Buttons wo man entweder das Spiel neustarten, oder es beenden kann.
Entweder wird es mit einem Text gemacht wo Game Over steht, oder wir fügen ein Bild was wir selber Designen hinzu und legen die Buttons drüber.


---

## Methoden und Techniken

GameOverOnDeath() in den HealthComponent vom Hero bei der OnDeath funktion

Eine LibGDX Applikation kann sicher durch den Befehl "Gdx.app.exit();" beendet werden.

---

## Ansatz und Modellierung

Wir brauchen eine neue Klasse HeroOnDeath, der Hero benutzt dann die Klasse,
um auf die onDeathFunction zuzugreifen, die er von dem IOnDeathFunction interface bekommt.
Dazu wird noch eine Klasse erstellt für den GameOverScreen, der bekommt alle Komponnenten
wie: JPanel, JButtons, Größe des Fensters usw.

HeroOnDeath benutzt die Klasse dann in der Funktion onDeath,
wo dann der Hero drauf zugreift wenn er stirbt.




---

## UML
