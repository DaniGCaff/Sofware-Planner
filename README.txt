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