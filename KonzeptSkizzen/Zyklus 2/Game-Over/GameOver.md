[Titel: Konzeptskizze für Zyklus

Author: Bent Schöne, Marvin Petschulat, Edwin Dik

---
## Beschreibung der Aufgabe

Dem Spieler soll es ermöglicht werden zu sterben. Nachdem der Spieler stirbt, hat er 2 Möglichkeiten.
Entweder beendet er das Spiel oder er startet einen neuen versuch.

Game over screen

Sobald der Hero stirbt soll das Spiel gestoppt werden und ein neues Fenster gestartet werden.
Der Nutzer soll die Entscheidung bekommen das Spiel zu schließen oder neuzustarten.

---

## Beschreibung der Lösung

Wenn der Spieler stirbt wird das Spiel pausiert und ein neues Fenster geöffnet. In dem Fenster steht Game Over und man
hat die möglichkeit das Spiel neuzustarten oder es zu beenden mithilfe von 2 Buttons.

Das Spiel wird pausiert wenn der Hero stirbt, danach wird mit Java Swing ein neues Fenster erstellt.
Das neue Fenster soll eine GUI sein mit 2 Buttons wo man entweder das Spiel neustarten, oder es beenden kann.
Entweder wird es mit einem Text gemacht wo Game Over steht, oder wir fügen ein Bild was wir selber Designen hinzu und legen die Buttons drüber.


---

## Methoden und Techniken

GameOverOnDeath() in den HealthComponent vom Hero bei der OnDeath funktion

Eine LibGDX Applikation kann sicher durch den Befehl "Gdx.app.exit();" beendet werden.

---

## Ansatz und Modellierung

Damit wir code ausführen können wenn der Spieler stirbt benutzen wir die IOnDeathFunktion im Healthcomponent
vom Hero. In der IOnDeathFunktion muss man eine funktion angeben von einer Klasse die IOnDeathFunktion implementiert.
Deswegen erstellen wir eine neue Klasse `ExitGameOnDeath`. In dieser Klasse erstellen wir dann eine Funktion die das Game-Over
Fenster erscheinen lässt.


Wir brauchen eine neue Klasse HeroOnDeath, der Hero benutzt dann die Klasse,
um auf die onDeathFunction zuzugreifen, die er von dem IOnDeathFunction interface bekommt.
Dazu wird noch eine Klasse erstellt für den GameOverScreen, der bekommt alle Komponnenten
wie: JPanel, JButtons, Größe des Fensters usw.

HeroOnDeath benutzt die Klasse dann in der Funktion onDeath,
wo dann der Hero drauf zugreift wenn er stirbt.

Den Neustart könnte man mit der Setup Methode aus der Klasse Game realisieren, um das Spiel neuzustarten.

---

## UML

![Game-Over UML](GameOver.png)
