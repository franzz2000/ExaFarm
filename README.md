Este programa se realizó como proyecto final de carrera.
Se trata de un proyecto de generador de exámenes para el departamento de Farmacología de la Facultad de Medicina de la Universidad de Oviedo.

El programa se ha realizado en entorno Java2EE, utilizando JSF, el servidor de aplicaciones Glassfish y la base de datos Mysql, aunque es posible utilizar cualquier otra base de datos relacional.

Instrucciones de instalación

Primeramente es necesario tener un servidor de aplicaciones Glassfish. Yo lo he probado con Glassfish 4.0.

1. Instalar la conexión con la base de datos.
    * He utilizado una base de datos MySQL, aunque se podría utilizar otras.
    * Hay que tener una conexión con la misma y conocer el nombre de usuario y contraseña para acceder a ella.
    * Generar la base de datos a partir del fichero "Estructura.sql"
    * Las contraseñas de los usuarios se generan con SHA-256

2. Crear una conexión entre glassfish y la base de datos
    * Descargar el conector de mysql para jpa de la web de MySQL.
    * Copiarlo en el directorio lib del servidor glassfish o en el directorio lib del dominio que se quiera

3. Configurar glassfish para que tenga un conector a la base de datos 
