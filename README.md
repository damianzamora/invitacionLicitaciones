# Invitaciones a licitaciones

Proyecto web ,el cual permite el manejo de invitaciones de proveedores a campañas, compra o licitacion.

El sistema permite creacion de campaña , y envio de mails a proveedores , los proveedores se podran ingresar via archivo .cvs (pueden ser varios proveedores)o mediante ingreso de mail individual.

En caso que sea opcion por .csv , el prograba validara si son correctos los datos en el mismo,e ira ingresando cada proveedor a la base de datos en caso de que no existe, luego
generara una licitacion y enviara un link que llegara via mail , para poder ingresar en una pantalla donde estara la campaña y permitiendo descargar un archivo PDF.

Validacion de archivo .csv implica que el formato sea el de un mail, y que no haya caracteres especiales, ni espacios vacios.

El sistema puede manejar el seguimiento del envio de cada mail, desde que el proveedor hizo click en el link, hasta cuando descarga el proveedor

Ese seguimiento se podra ver desde una de las opciones del sistema, y se podra buscar campañas mediante filtros de busquedas.

Sistema subido a Heroku: https://invitacionescampanias.herokuapp.com/panelcampanias.html

Tecnologias BackEnd:
-Java8
-Maven
-SpringBoot
-JPA

Tecnologias FrontEnd:
-HTML5
-CSS3
-BOOTSTRAP
-VUE.JS3.0
-AXIOS

Base de datos:
-H2

Programas:
-IntelliJ
-VisualStudioCode

Capturas del sistemas.

Menu principal

![image](https://user-images.githubusercontent.com/63264380/141693983-06359e01-ce7f-475f-a71b-2bd353a7acd4.png)

Creacion de Campaña

![image](https://user-images.githubusercontent.com/63264380/141693686-722a64be-a256-4902-872e-be94aef2b162.png)


Pantalla de Referentes

![image](https://user-images.githubusercontent.com/63264380/141693699-2e7d96a2-a4bb-4534-8e33-7c2a17d46091.png)


Pantalla vista Seguimiento campañas enviadas

![image](https://user-images.githubusercontent.com/63264380/141693735-03129b6b-9bcb-43eb-8e26-7f3b29045af9.png)

Pantalla para armar envio de solicitud mediante .csv

![image](https://user-images.githubusercontent.com/63264380/141693773-063abd19-0b1e-4556-90cd-4b536ff3fe35.png)

Pantalla que llega a referente o proveedor cuando entra a link

![image](https://user-images.githubusercontent.com/63264380/141694011-28f139a5-dafa-4dbc-a32d-4113dbe77c01.png)


