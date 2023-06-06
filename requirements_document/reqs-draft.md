# Documento dei requisiti
## Definizione dei requisiti dell'utente
### Funzionali
L'utente deve essere in grado di specificare al sistema diversi tipi di sorgenti come input per l'elaborazione
di articoli.<br>
Il sistema deve quindi poter accettare come sorgente un file contenente articoli o un servizio
da cui scaricarli, in base a quanto specificato dall'utente nella fase di input.<br>
Il sistema deve visualizzare un messaggio di errore nel caso in cui la sorgente specificata non sia raggiungibile.<br>
Dopo aver verificato l'input, il sistema deve elaborare gli articoli specificati dall'utente e visualizzare in output le 50 parole (o meno in caso non siano 
presenti 50 parole analizzabili) più frequentemente presenti nelle testate giornalistiche.<br>
Nell'elaborazione degli articoli, il sistema deve scartare le parole di uso comune dal conteggio di frequenza, confrontandole con una lista 
che può essere modificata dall'utente in base alle proprie esigenze.<br>

### Non funzionali
[//]: # (L'utente deve essere in grado di utilizzare il sistema se ha previa conoscenza del funzionamento di un terminale o di una riga di comando)
L'interfaccia utente è limitata alla finestra di un terminale con cui l'utente può avviare l'esecuzione del sistema.<br>
L'utente deve essere in grado di operare il sistema senza previa conoscenza del suo funzionamento, ma sfruttando i messaggi di errore visualizzati
per aiutare nell'apprendimento del corretto utilizzo del sistema in meno di 3 tentativi.<br>
Anziché terminare la propria esecuzione e perdere il progresso dell'utente, il sistema deve chiedere nuovamente l'inserimento dei campi necessari nella fase di input.<br>
L'utente deve essere in grado di visualizzare i risultati della propria ricerca in meno di un minuto.
L'utente è in grado di accedere ai file del sistema e modificarli a proprio piacimento, inoltre deve poter accedere alle cartelle contenenti le risorse del progetto per poter specificare 
delle sorgenti diverse utilizzando le cartelle preesistenti.<br>
Il sistema deve poter funzionare anche in assenza di connessione qualora si vogliano utilizzare sorgenti locali già precedentemente scaricate, ma è necessaria una connessione per poter sfruttare 
le funzionalità che sfruttano i servizi online, altrimenti il sistema visualizza un messaggio di errore.<br>
Il sistema non deve pubblicare o condividere dati personali forniti dall'utente, ad esempio chiavi private da utilizzare per la comunicazione con servizi online.<br>
Il sistema deve poter analizzare un articolo ed ottenere il numero di parole in esso contenute in meno di 1 secondo.<br>
Il sistema è in grado di gestire carichi di lavoro diversi in base alle specifiche dell'hardware utilizzato dall'utente e alla dimensione delle sorgenti o al numero degli articoli specificati durante la fase di input, 
che possono rallentare l'elaborazione dei dati e la visualizzazione dell'output, ma deve elaborare i dati in modo efficiente e tale da permettere l'analisi di 500 articoli entro 30 secondi.<br>

### *Note sulla specifica dei requisiti* (ignore)
-- Descrizione servizi forniti all'utente (requisiti funzionali e non).
In linguaggio naturale, con diagrammi o notazioni comprensibili ai clienti.
Specifica di standard di prodotto e dei processi. -- <br><br>
https://www.altexsoft.com/blog/business/functional-and-non-functional-requirements-specification-and-types/ <br>
http://www1.isti.cnr.it/~polini/downloads/SE0708/IdS_4.pdf <br><br>
Requisiti funzionali:
<ul>
<li>definizione dei servizi che il sistema deve fornire - come reagire a particolari input</li>
<li>come reagire in particolari situazioni</li></ul>
Requisiti non funzionali:
<ul>
<li>vincoli sulle funzioni/servizi offerti dal sistema - vincoli temporali sul processo di sviluppo</li>
<li>vincoli legati a normative</li>
<li>di solito applicati al sistema completo</li></ul>
Esempi di <i>requisiti non funzionali</i>:

- affidabilità
- tempo di risposta
- uso della memoria
- rappresentazione dati per interfacciamento con altri sistemi

[//]: # (Gli utenti devono essere in grado di utilizzare il sistema sfruttando i messaggi di errori forniti quando l'input non rispetta le condizioni imposte dal sistema stesso)