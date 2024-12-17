### Wat is Selection Sort?

Voordat er antwoord gegeven kan worden op de onderzoeksvraag, moet er eerst gekeken worden naar wat Selection Sort precies inhoudt. Selection Sort is algoritme gebaseerd op vergelijking. Dit algoritme zoekt in een reeks aan data steeds naar de kleinste (of grootste) waarde en verwisselt deze vervolgens met het getal aan de start van de reeks. Dit proces wordt constant uitgevoerd totdat de reeks aan data correct gesorteerd is.

### Hoe werkt Selection Sort?

Om Selection Sort toe te passen hebben we eerst een reeks (array) aan data nodig om te sorteren. Neem deze reeks als voorbeeld:

[5, 9, 7, 2, 8, 4, 6]

Het algoritme start aan het begin van de reeks, bij index = 0, oftewel 5 in deze reeks. Dit getal wordt nu gedefinieerd als het kleinste getal. Het algoritme loopt vervolgens door de reeks heen op zoek naar een getal kleiner dan 5. Elke keer dat het algoritme een getal in de reeks heeft gevonden die kleiner is dan het kleinste getal (5), wordt het gevonden getal als het kleinste getal gedefinieerd. Zodra het algoritme bij het laatste getal van de reeks is aangekomen, dan wordt het kleinste getal (nu 2) gewisseld met het getal waarbij de index gelijk is aan 0. De nieuwe reeks ziet er nu zo uit:

[**2**, 9, 7, 5, 8, 4, 6]

Het algoritme start na de eerste loop nu bij index = 1, aangezien het eerste getal in de reeks al op de juiste plek staat. 5 wordt nu gedefinieerd als het kleinste getal en het algoritme gaat nu weer opzoek naar een getal in de reeks dat kleiner is dat 5 (i < 5). Aan het einde van de reeks is 4 het kleinste getal en wordt deze gewisseld met het getal waar het algoritme deze keer is gestart, oftewel index = 1. De nieuwe reeks ziet er nu zo uit:

[**2**, **4**, 7, 5, 8, 9, 6]

Het algoritme blijft zichzelf herhalen, nu bij index = 2, totdat de volledige reeks correct gesorteerd is. Zodra het algoritme klaar is, is dit het resultaat:

[**2**, **4**, **5**, **6**, **7**, **8**, **9**]

### Tijd Complexiteit

Selection Sort doorloopt een reeks van n waarden in totaal (n − 1) keer. Dit is zo omdat wanneer het algoritme de een-na-laatste index heeft gesorteerd, de laatste waarde automatisch ook op de goede plek staat. De eerste keer dat de array door de reeks heen loopt wordt elke waarde vergeleken, bij de tweede keer wordt elke waarde behalve de eerste waarde (index = 0) vergeleken welke waarde de laagste is. Het ongesorteerde gedeelte van de reeks wordt dus telkens kor

Op deze manier wordt het ongesorteerde deel van de array steeds korter totdat de sortering voltooid is. Gemiddeld worden n 2 elementen beschouwd terwijl het algoritme door de array gaat om de laagste waarde te vinden en deze naar voren te verplaatsen.

Naast alle benodigde vergelijkingen is het aantal benodigde swaps precies n .

We kunnen beginnen met het berekenen van het aantal operaties dat nodig is voor het Selection Sort-algoritme:

### Resulaten

| **Aspect**                | **Algorithm**  | **Beste Geval** | **Gemiddelde Geval** | **Slechtste Geval** | **Voorbeeld (\(n = 1000\))**                                             |
| ------------------------- | -------------- | --------------- | -------------------- | ------------------- | ------------------------------------------------------------------------ |
| **Tijdcomplexiteit**      | Selection Sort | O(n²)           | O(n²)                | O(n²)               | \(1000^2 = 1,000,000\)                                                   |
|                           | Quicksort      | O(n log n)      | O(n log n)           | O(n²)               | \(1000 \cdot \log\_{2}(1000) \approx 10,000\)                            |
|                           | Mergesort      | O(n log n)      | O(n log n)           | O(n log n)          | \(1000 \cdot \log\_{2}(1000) \approx 10,000\)                            |
| **Ruimtecomplexiteit**    | Selection Sort | O(1)            | O(1)                 | O(1)                | Geen extra geheugen nodig.                                               |
|                           | Quicksort      | O(log n)        | O(log n)             | O(n)                | Recursie stapelruimte: \(\log\_{2}(1000) \approx 10\) tot maximaal 1000. |
|                           | Mergesort      | O(n)            | O(n)                 | O(n)                | Extra array van grootte \(1000\).                                        |
| **Aantal vergelijkingen** | Selection Sort | O(n²)           | O(n²)                | O(n²)               | \(1000^2 = 1,000,000\)                                                   |
|                           | Quicksort      | O(n log n)      | O(n log n)           | O(n²)               | \(1000 \cdot \log\_{2}(1000) \approx 10,000\)                            |
|                           | Mergesort      | O(n log n)      | O(n log n)           | O(n log n)          | \(1000 \cdot \log\_{2}(1000) \approx 10,000\)                            |

### Bronnen

Satish, S. (2024, 13 november). What is selection sort algorithm in data structures? Simplilearn.com. https://www.simplilearn.com/tutorials/data-structure-tutorial/selection-sort-algorithm#the_complexity_of_selection_sort_algorithm. (Satish, 2024)

W3Schools.com. (z.d.). https://www.w3schools.com/dsa/dsa_timecomplexity_selsort.php. (W3Schools.com, z.d.)
