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
 
 Existe un metodo que facilita motrar los datos en las tablas:
 
 DefTable(JTable T,String[][] Mat)
 
 Con dichos argumentos. La matriz de Strings debe incluir el nombre de cada nodo en la primera columna.
 
 Por ejemplo, la matriz seria de la siguiente forma:
 
  A B C
  B B D
  C B C
  D B E
  E B C
