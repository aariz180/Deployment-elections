# Onderzoeksverslag Selection Sort vs Cyclesort

De onderzoeksvraag van dit verslag op gebaseerd is duidt: **Hoe verhouden het aantal vergelijkingen, verwisselingen, de tijdcomplexiteit en de ruimtecomplexiteit van het selection-sort-algoritme zich tot die van cyclesort bij het sorteren van de kandidaten van de Verkiezingen in 2023?** Om deze vraag te kunnen beantwoorden, moeten eerst enkele aspecten worden besproken.

#### **De kandidaten van de Verkiezingen in 2023**
De dataset bestaat uit 1128 voor- en achternamen die door Team 2 gebruikt wordt voor hun applicatie.

#### **De Array/Reeks**
Om de werking van beide algoritmes te demonstreren, wordt er gebruik gemaakt van deze reeks [3, 4, 2, 5, 1]. **Dikgedrukte cijfers** in de reeks zijn getallen die correct zijn gesorteerd.

### **Selection Sort**
Selection Sort is een simpel sorteeralgoritme dat steeds de kleinste (of grootste) waarde zoekt en deze op de juiste plek in een reeks plaatst. Het algoritme is gebaseerd op het concept van vergelijken en wisselen, en het proces herhaalt zich totdat de hele reeks gesorteerd is.

**Werking van Selection Sort:**
1. Bij de eerste iteratie zoekt het algoritme in een ongesorteerde array naar de kleinste of grootste waarde en verplaats wissel deze om met het getaal waarbij de index 0 is.
De reeks gaat na de eerste iteratie van dit: [3, 4, 2, 5, 1] naar dit: [**1**, 4, 2, 5, 3]
2. Bij de tweede iteratie start het algoritme bij het getal waarbij de index gelijk staat aan 1, aangezien het eerste getal (index = 0) op de juiste plaatst is gezet bij de eerste iteratie.
De reeks gaat na de tweede iteratie. van dit:[**1**, 4, 2, 5, 3] naar dit: [**1**, **2**, 4, 5, 3]
3. Het algoritme blijft zichzelf herhalen totdat de volledige reeks correct gesorteerd is.

### **Cyclesort**
Cyclesort is een sorteeralgoritme dat elementen direct naar de juiste positie verplaatst binnen een reeks. In plaats van telkens te vergelijken en te wisselen zoals bij andere Selection sort, identificeert Cyclesort de juiste positie van een element in de reeks en verplaatst het gelijk naar die positie. Dit proces wordt herhaald totdat alle elementen op hun juiste plaats staan.

**Werking van Cyclesort:**
1. Voor elk element in de reeks bepaalt het algoritme hoeveel waarden kleiner zijn om de juiste positie te vinden.
In de reeks [3, 4, 2, 5, 1] hoort 3 op index = 2 omdat er 2 waarden kleiner zijn dan 3 (1 en 2). 3 wordt nu op de plek gezet waar de index gelijk is aan 2. De nieuwe reeks is nu [3, 4, **3**, 5, 1].
2. 2, wat oorspronkelijk op de plek van index = 2 stond, moet nu correct geplaatst worden in de reeks. Alleen 1 is kleiner 2, dus moet 2 op de plek waar de index 1 is. De nieuwe reeks is nu: [3, **2**, **3**, 5, 1]. 
3. Het algoritme gaat nu op deze manier verder totdat de hele reeks gesorteerd is.


### Resultaten

| Kenmerk               | Selection Sort                              | Cyclesort                                | Selection Sort n = 1128         | Cyclesort n = 1128              |
|-----------------------|---------------------------------------------|-----------------------------------------|---------------------------------------------|---------------------------------------------|
| **Aantal vergelijkingen** | \( O(n^2) \) | \( O(n^2) \)| 1,272,384 vergelijkingen | 1,272,384 vergelijkingen |
| **Aantal verwisselingen** | \( O(n) \): Slechts één wissel per element | \( O(n) \): Slechts één wissel per element | \( 1128 \) verwisselingen | \( 1128 \) verwisselingen, met minder totale schrijfbewerkingen |
| **Tijdcomplexiteit**     | \( O(n^2) \) | \( O(n^2) \) | 1,272,384|1,272,384|
| **Ruimtecomplexiteit**   | \( O(1) \)| \( O(1) \)| Geen extra geheugen nodig                   | Geen extra geheugen nodig                   |

 ### Analyse
Selection Sort en Cyclesort hebben beide een vergelijkbare tijdcomplexiteit (O(n^2)) en voeren hetzelfde aantal vergelijkingen uit. Voor het sorteren van de kandidaten van de Verkiezingen van 2023, resulteert dit in 1.272.384 vergelijkingen. Hoewel het aantal verwisselingen identiek is (1128), maakt Cyclesort minder totale schrijfbewerkingen doordat het elementen direct naar hun juiste positie verplaatst. Dit maakt Cyclesort de betere keuze voor wanneer er een limiet is aan schrijfbewerkingen die gemaakt kunnen worden. Beide algoritmen hebben een kwadratische tijdcomplexiteit (\( O(n^2) \)), wat er voor zorgt dat de tijdcomplexiteit exponentieel laat groeien. Puur kijkend naar de tijdcomplexiteit, zijn Selection Sort en Cyclesort allebei niet geschikt om grote datasets te sorteren. Er kan dan beter gebruik gemaakt worden van het QuickSort, waarbij de gemiddelde tijdcomplexiteit gelijk staat aan O(n log n) (GeeksforGeeks, 2024). De ruimtecomplexiteit is echter constant ( O(1)), waardoor er geen extra nodig is. Dit maakt ze interessant voor systemen met beperkte middelen, desondanks de langzame tijdcomplexiteit.

### Conclusie

Uit de vergelijking tussen Selection Sort en Cyclesort blijkt dat beide algoritmes dezelfde tijd- (O(n^2)) en ruimtecomplexiteit (O(1)) hebben, net zoals hetzelfde aantal vergelijkingen en verwisselingen bij het sorteren van de verkiezingskandidaten van 2023. Cyclesort onderscheidt zich echter door minder schrijfbewerkingen, omdat dit algoritme direct de data naar de juiste positie verplaatst.


