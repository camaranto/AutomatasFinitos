# AutomatasFinitos
Compiler assignment

Estos son las variables que he agregado y deben tener en cuenta.

OP : Este es un array que guarda los caracteres especiales que vienen con una expresión regular, como son los paréntesis o el asterisco.

#Alfabeto: array que almacena los caracteres del alfabeto.

jTableAFD --> Tabla para mostrar el AFD optimo

jTableSubConjuntos --> Tabla para mostrar el AFD no optimo

jTableThompson -->  Tabla para el AFN

jTextAlfa -->  Text área para escribir el alfabeto (Ya lo implemente)

jTextConjuntos -->  Para mostrar los conjuntos formados del AFN para el AFD no optimo.

jTextMueve --> Text área para visualizar el proceso del mueve para poder decir si una palabra esta en la expresión regular.

jTextSignificativos -->  Text área para mostrar los estados significativos del AFD no optimo para conseguir el AFD definitivo.

jTextField2 -->  Campo para escribir cadenas para comprobar la expresion regular.

jButton2 -->  Botón para activar el proceso de validación de la cadena.
 
En el codigo esta, pero lo recalco de nuevo...

Definen un Jtable:
JTable table;
 
 Luego le asignan los respectivos datos apartir de un Object.

Object[][] data ={
{"A","A","B"},
{"C", "C", ""}
};

Para facilitar esta parte, no es necesario que lo definan el TransD como object, sino que luego pásenlo. Ahora un vector para el nombre de las columnas.

String[] columnNames = {"", "a", "b"};

Con estos arreglos, se crea la tabla.
     
table = new JTable(data, columnNames);

Y al final le asignan los datos a la tabla correspondiente a dicho proceso.

jTableThompson.setModel(table.getModel());
