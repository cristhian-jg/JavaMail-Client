# COMUNICACIÓN CON SERVIDOR EMAIL

###### Alumno: Cristhian González
###### Módulo: Programación de servicios y procesos
###### Curso: 2º DAM


#### INDICE

### 1. INTRODUCCIÓN
### 2. CREANDO MI SERVIDOR DE CORREO
### 3. AUNTENTICANDO E-MAILS EN JAVA
### 4. ENVIANDO MAILS A TRAVES DE JAVA
### 5. LEYENDO MAILS A TRAVES DE JAVA

## 1. INTRODUCCIÓN

En esta pratica realizaré un programa en Java que permita enviar mails a través de un servidor de correo propio, se capturarán los mensajes que vaya mostrando el servidor. La cuenta de correo que envía un mail estará autenticada y utilizará el protocolo estándar SMTP (Port 25), dicho mail se enviará a una segunda cuenta de correo del servidor. Además realizaré un segundo programa en Java realizaré una consulta del mail mediante el protocolo IMAP (Port 143) y mostrará en pantalla un listado de los mails existentes en el buzón. Las herramientas de desarrollo que se utilizarán son Eclipse como IDE, Swing para el desarrollo de ventanas y la librería JavaMail para poder enviar correos. 

Además que nada quería añadir que mi programa está dividido en tres ventanas JFRAME, la primera será la de autenticación (LoginWindow) que se encarga de comprobar el usuario y contraseña introducidos, por otro lado tendré la bandeja de entrada la cual muestra los mensajes recibidos de la cuenta a la que estamos conectados (InboxWindow), y por utlimo la ventana que se encarga de enviar los correos (MessageWindow). 
