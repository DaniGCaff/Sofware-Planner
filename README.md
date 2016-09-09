# Spring-samples

Well this is a repository originally planned to be my spring samples repository. However, in a last-year course, we were asked to deploy a java app, so I used this as final delivery. 

It is a "Sofware Planner" app that consist of a mashup of two APIs: Github and Trello. Main idea is to plann in Trello, and then assign goals to your team, then each programmer should commit with a specific string in the commit description.

If you wanna have more information, dont hesistate to contact me. 

Thanks to my peer:
Roberto Arranz García


# Spanish set up instructions
Para realizar pruebas sobre la práctica y ver su funcionamiento se debe disponer de:

-MongoDB
	Instalar MongoDB
	Arrancar mongod desde cmd
	En otra consola: mongoimport --db trellogithub --collection <nombre_fichero_sin_extension> --file <nombre_fichero_con_extension>
	Ejecutar el comando: mongo
						use trellogithub
	Probar que hay datos en una colección: db.<nombre_coll>.find();
-Crear cuenta de GitHub y Trello.
-Crear repositorio en GitHub y board con tarjetas en Trello.
-Ejecutar las app (Listener, puerto 8081 y AplicaciónWeb, puerto 8080)
-Ejecutar ngrok: ngrok http 8081
-Copiar la dirección de ngrok a un webhook de los settings del repositorio de GitHub, seguida de "/listen/repos/github".
-Abrir la dirección localhost:8080, registrarse e iniciar sesión.
-Vincular el repositorio con el board de trello.
-Hacer commit y en el mensaje incluir @<dirección_carta_trello@
-Comprobar que en dicha carta aparece el commit y los datos de este
