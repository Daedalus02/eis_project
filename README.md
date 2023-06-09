# Analizzatore di articoli
## Descrizione
Questo programma è in grado di elaborare degli **articoli** di giornale per calcolare il **peso** delle parole che in essi appaiono e farne una classifica. Il peso è inteso come numero di singoli articoli in cui le parole appaiono; pertanto molteplici apparizioni di una stessa parola nello stesso articolo non ne aumentano il peso. Il linguaggio di programmazione utilizzato è Java (versione 8).
## Funzionalità
- Sono disponibili due modalità di **input**: argomento e CLI.
- Sono supportate nativamente due **sorgenti** di articoli: file in formato `CSV` e l'utilizzo delle **API del TheGuardian** per l'acquisizione di articoli dall'archivio online del quotidiano.
- Gli articoli elaborati con le sorgenti vengono archiviati in un file in formato `JSON` e possono essere direttamente rielaborati dal programma
- Il programma è in grado di filtrare i risultati tramite una **blacklist** contenuta nel file `words.txt` di default o fornito dall'utente. Nel caso essa non sia presente non verrà filtrato nessun termine
- Per espandere il codice ed aggiungere nuove sorgenti esse devono implementare l'interfaccia `ArticleSource`
- Per utilizzare altre strutture per la memorizzazione dei termini esse devono implementare l'interfaccia `TokensStorage`
## Requisiti per il funzionamento
- Per l'esecuzione del programma deve essere presente il `jre8`
- Per la compilazione del programma deve essere presente il `jdk8` e `maven`
## Utilizzo
1. Estrarre l'archivio `article-analyzer-1.0.zip`
2. Entrare nella cartella `article_analyzer`
3. Aprire un terminale ed eseguire `java -jar article_analyzer-1.0.jar`, con eventuali argomenti
### Parametri passati per argomento
È disponibile l'argomento `-h` che fornisce una guida rapida all'uso degli argomenti. Gli argomenti funzionali sono :
- `-file,--read-file` Per leggere gli articoli da un'altra ricerca.
- `-json,--json-file-name < arg >` Il nome del file della ricerca da cui leggere.
- `-api,--read-api` Per leggere gli articoli utilizzando le API del  TheGuardian.
- `-apikey,--api-key < arg >` Il valore della API key per autenticarsi con le API del TheGuardian.
> **NOTA:** se non specificato verrà letta quella di default
- `-csv,--read-csv` Per leggere gli articoli da file CSV.
- `-name,--csv-file-name < arg >` Il nome del file CSV.
- `-max,--max-articles < arg >` Il numero massimo di articoli da elaborare.
##### Argomenti opzionali
- `-queries,--queries < args >` I termini con cui fare la ricerca.
- `-tags,--tags < args >` I tag con cui condizionare la ricerca.
- `-show,--show` Per stampare la classifica dei 50 termini.
- `-store,--store < arg >` Il nome del file in cui la ricerca verrà salvata. 
>  **NOTA:** se non specificato il default è "test"

### CLI
Se il programma viene eseguito senza argomenti si avvierà l'interfaccia da riga di comando che con una serie di domande configurerà si preparerà all'elaborazione dei dati.
### Struttura di file e cartelle

Nella release, oltre al file eseguibile, è presente una cartella `resorces` che presenta la seguente struttura:

    /resources
	    /backlog
		    test.json
	    /blacklist
		    words.txt
	    /CSV_sources
		    file.csv
	    /private
		    private.properties
- `backlog` contiene i file dove sono state salvate le ricerche precedenti. 
- `blacklist` contiene il file `words.txt` che serve per filtrare i termini
- `CSV_sources` contiene i file CSV da utilizzare come sorgenti
- `private` contiene il file `private.properties` dove è contenuta la API key di default
> **NOTA:** il nome della proprietà della API key è `apiKey`
## Compilazione
Usare il comando `mvn package` nella cartella `PLACEHOLDER` e il file .jar sarà disponibile nella cartella `target` con il nome `article_analyzer-1.0-jar-with-dependencies.jar`
# TODO
Librerie utilizzate
 

