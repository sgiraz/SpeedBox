# WifiTransfer
Direct wifi file transfer between 2 computers

DA SISTEMARE:
- Se Windows fa da server il usando la LanNetwork OS-X non è in grado di connettersi, viceversa invece funziona
- Gestire il caso in cui il tipo di file traferito non è un "file" riconosciuto dal sistema operativo (ovvero is a directory), magari avvisando l'utente che il file per essere inviato deve prima essere compattato, se questo caso non viene gestito l'applicazione assume un comportamento instabile.


I SEGUENTI PROBLEMI SONO STATI SISTEMATI:
- Nascondere il pulsante "start server" da direct wifi per osx (visto che non può fare da server)
- Il frame configuration è always on top, non va bene su tutte le finestre del siste, ma solo quelle del programma stesso.
- Quando viene aperta la finestra di dialogo nel caso in cui start server fallisca, bisogna poi torare al main menù, altirmenti si è costretti a chiudere l'applicazione.

