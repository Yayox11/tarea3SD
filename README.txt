Integrantes:	Orlando Andrade	201459504-2
		Juan Pablo Castillo 201573534-4

Instrucciones de ejecuci�n:

	1) Ingresar a las m�quinas virtuales asignadas.
	2) A�adir en la carpeta src/json los archivos son los siguientes nombres:
		2.1) requerimientos.json
		2.2) pacientes.json
		3.3) staff.json (archivo con la informaci�n de doctores, enfermeros y paramedicos.)
	2) Ejecutar por consola ant compile.
	3) Ejecutar por consola ant jar.
	4) Ejecutar por consola ant run. 

Consideraciones:
	- Al no estar en el archivo de muestra, se asumi� que recetar se usa para medicamentos en la seccion recetados,
	  pedir para examenes en la seccion no realizados, realizar para examenes en la seccion realizados,
	  colocar para tratamientos/procedimientos en la secci�n completados, asignar para tratamientos/procedimientos
	  en la secci�n asignados y suministrar para medicamentos en la secci�n suministrados.
	- Se considera que el array de pacientes viene ordenado por id.