# invitacionLicitaciones

Proyecto web ,el cual permite el manejo de invitaciones de proveedores a campañas, compra o licitacion.

El sistema permite creacion de campaña , y envio de mails a proveedores , los proveedores se podran ingresar via archivo .cvs (pueden ser varios proveedores)o mediante ingreso de mail individual.

En caso que sea opcion por .cvs , el prograba validara si son correctos los datos en el mismo,e ira ingresando cada proveedor a la base de datos en caso de que no existe, luego
generara una licitacion y enviara un link que llegara via mail , para poder ingresar en una pantalla donde estara la campaña y permitiendo descargar un archivo PDF.

Validacion de archivo .cvs implica que el formato sea el de un mail, y que no haya caracteres especiales, ni espacios vacios.

El sistema puede manejar el seguimiento del envio de cada mail, desde que el proveedor hizo click en el link, hasta cuando descarga el proveedor

Ese seguimiento se podra ver desde una de las opciones del sistema, y se podra buscar campañas mediante filtros de busquedas.
