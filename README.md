## ClientGUI

 - Start der JMS / Kafka Client GUI über ClientStart
 - Nachrichten können wahlweise via JMS oder Kafka verschickt werden (DropDown Menü zum auswählen)


## Benchmark
 
 - Start des Benchmarks über `BenchmarkingClientUserInterfaceSimulation` 
 - Parameter (Anzahl der Clients, Anzahl Messages & Typ des Servers (TCP, JMS, Kafka) können in der Klasse angepasst werden (Methode doWork())



### Ergebnisse: 
## 10 Clients
*** Meldung: Alle Clients-Threads beendet ***
Testende: 21.12.18 11:00:51:438
Testdauer in s: 32
Gesendete Requests: 1000
Anzahl Responses: 1000
Anzahl verlorener Responses: 0
Mittlere RTT in ms: 220.5495390450013
Maximale RTT in ms: 1376.695004
Minimale RTT in ms: 84.918836
Mittlere Serverbearbeitungszeit in ms: 1.545386
Maximale Heap-Belegung in MByte: 78
Maximale CPU-Auslastung in %: 0.1987442970275879
*** Meldung: JMS-Implementation: Benchmark beendet ***

## 20 Clients
*** Meldung: Alle Clients-Threads beendet ***
Testende: 21.12.18 11:03:45:161
Testdauer in s: 25
Gesendete Requests: 2000
Anzahl Responses: 2000
Anzahl verlorener Responses: 0
Mittlere RTT in ms: 201.63439191800066
Maximale RTT in ms: 605.500934
Minimale RTT in ms: 124.644542
Mittlere Serverbearbeitungszeit in ms: 1.545386
Maximale Heap-Belegung in MByte: 124
Maximale CPU-Auslastung in %: 0.2924070954322815
*** Meldung: JMS-Implementation: Benchmark beendet ***
## 30 Clients
*** Meldung: Alle Clients-Threads beendet ***
Testende: 21.12.18 11:06:05:064
Testdauer in s: 31
Gesendete Requests: 3000
Anzahl Responses: 3000
Anzahl verlorener Responses: 0
Mittlere RTT in ms: 251.33139241499913
Maximale RTT in ms: 1094.132762
Minimale RTT in ms: 106.206524
Mittlere Serverbearbeitungszeit in ms: 1.545386
Maximale Heap-Belegung in MByte: 233
Maximale CPU-Auslastung in %: 0.35310789942741394
*** Meldung: JMS-Implementation: Benchmark beendet ***
## 40 Cleints
*** Meldung: Alle Clients-Threads beendet ***
Testende: 21.12.18 11:08:21:899
Testdauer in s: 34
Gesendete Requests: 4000
Anzahl Responses: 4000
Anzahl verlorener Responses: 0
Mittlere RTT in ms: 268.9692535522513
Maximale RTT in ms: 861.285125
Minimale RTT in ms: 125.829096
Mittlere Serverbearbeitungszeit in ms: 1.545386
Maximale Heap-Belegung in MByte: 206
Maximale CPU-Auslastung in %: 0.33482977747917175
*** Meldung: JMS-Implementation: Benchmark beendet ***
## 50 Clients
*** Meldung: Alle Clients-Threads beendet ***
Laufzeitzaehler: 44
Testende: 21.12.18 11:10:19:888
Testdauer in s: 44
Gesendete Requests: 5000
Anzahl Responses: 5000
Anzahl verlorener Responses: 0
Mittlere RTT in ms: 346.90472619840443
Maximale RTT in ms: 1326.121178
Minimale RTT in ms: 117.100056
Mittlere Serverbearbeitungszeit in ms: 1.545386
Maximale Heap-Belegung in MByte: 256
Maximale CPU-Auslastung in %: 0.2911187708377838
*** Meldung: JMS-Implementation: Benchmark beendet ***
