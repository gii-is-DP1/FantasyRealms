# Documento de diseño del sistema

_Esta es una plantilla que sirve como guía para realizar este entregable. Por favor, mantén las mismas secciones y los contenidos que se indican para poder hacer su revisión más ágil._

## Introducción

_En esta sección debes describir de manera general cual es la funcionalidad del proyecto a rasgos generales. ¿Qué valor puede aportar? ¿Qué objetivos pretendemos alcanzar con su implementación? ¿Cuántos jugadores pueden intervenir en una partida como máximo y como mínimo? ¿Cómo se desarrolla normalmente una partida?¿Cuánto suelen durar?¿Cuando termina la partida?¿Cuantos puntos gana cada jugador o cual es el criterio para elegir al vencedor?_

[Enlace al vídeo de explicación de las reglas del juego / partida jugada por el grupo](http://youtube.com)

## Diagrama(s) UML:

### Diagrama de Dominio/Diseño

![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/diagrams/DP1.drawio.png)

### Diagrama de Dominio/Diseño Mod

![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/diagrams/DP1%20-Mod.drawio.png)

### Diagrama de Capas (incluyendo Controladores, Servicios y Repositorios)

![image](https://github.com/user-attachments/assets/40f2b867-a51b-43d3-a826-e6c026804329)

## Descomposición del mockups del tablero de juego en componentes

En esta sección procesaremos el mockup del tablero de juego (o los mockups si el tablero cambia en las distintas fases del juego). Etiquetaremos las zonas de cada una de las pantallas para identificar componentes a implementar. Para cada mockup se especificará el árbol de jerarquía de componentes, así como, para cada componente el estado que necesita mantener, las llamadas a la API que debe realizar y los parámetros de configuración global que consideramos que necesita usar cada componente concreto.
Por ejemplo, para la pantalla de visualización de métricas del usuario en un hipotético módulo de juego social:

![Descomposición en componentes de la interfaz de estadísticas](https://github.com/gii-is-DP1/react-petclinic/assets/756431/12b36c37-39ed-422e-b8d9-56c94753cbdc)

- App – Componente principal de la aplicación
  - $\color{orange}{\textsf{NavBar – Barra de navegación lateral}}$
    - $\color{darkred}{\textsf{[ NavButton ]. Muestra un botón de navegación con un icono asociado.}}$
  - $\color{darkblue}{\textsf{UserNotificationArea – Área de notificaciones e identificación del usuario actual}}$
  - $\color{blue}{\textsf{MetricsBar – En este componente se muestran las métricas principales del juego. Se mostrarán 4 métricas: partidas jugadas, puntos logrados, tiempo total, y cartas jugadas.}}$
    - $\color{darkgreen}{\textsf{[ MetricWell ] – Proporciona el valor y el incremento semanal de una métrica concreta. }}$
  - $\color{purple}{\textsf{GamesEvolutionChart – Muestra la tendencia de evolución en ellos últimos 4 meses en cuanto a partida jugadas, ganadas, perdidas y abandonadas.}}$
  - $\color{yellow}{\textsf{PopularCardsChart – Muestra la proporción de las N (parámetro de configuración) cartas más jugadas en el juego por el jugador.}}$
  - $\color{red}{\textsf{FrequentCoPlayersTable – Muestra los jugadores  con los que más se  ha jugado (de M en M donde M es un parámetro definido por la configuración del componente). Concretamente, se mostrarán la el nombre, la fecha de la última partida, la localización del jugador el porcentaje de partidas jugadas por ambos en las que el usuario ha ganado y si el jugador es amigo o no del usuario.}}$

## Documentación de las APIs

Se considerará parte del documento de diseño del sistema la documentación generada para las APIs, que debe incluir como mínimo, una descripción general de las distintas APIs/tags proporcionadas. Una descripción de los distintos endpoints y operaciones soportadas. Y la especificación de las políticas de seguridad especificadas para cada endpoint y operación. Por ejemplo: “la operación POST sobre el endpoint /api/v1/game, debe realizarse por parte de un usuario autenticado como Player”.

Si lo desea puede aplicar la aproximación descrita en https://vmaks.github.io/2020/02/09/how-to-export-swagger-specification-as-html-or-word-document/ para generar una versión en formato Word de la especificación de la API generada por OpenAPI, colgarla en el propio repositorio y enlazarla en esta sección del documento. En caso contrario debe asegurarse de que la interfaz de la documentación open-api de su aplicación está accesible, funciona correctamente, y está especificada conforme a las directrices descritas arriba.

## Patrones de diseño y arquitectónicos aplicados

### Patrón: Prototipo

_Tipo_: De Diseño

_Contexto de Aplicación_

El patrón prototipo se utilizó en la aplicación para gestionar el uso de un mazo universal que actúa como plantilla para cada partida. Este mazo contiene todas las cartas iniciales del juego y se utiliza como referencia para generar mazos independientes para cada sesión de juego. Para lograr esta independencia y evitar conflictos al compartir referencias entre cartas, se implementó una lógica de clonación profunda.

Así, al crear una partida se va a realizar una clonación superficial inicial de cada una de sus cartas (a excepción de los modificadores de cada una), para posteriormente clonar los modificadores profundamente y asignarlos a las cartas superficialmente clonadas. Esto se encuente en deckService (cliente del patrón), más concretamente en el método 'getShuffledDeckForMatch()'.

El contexto específico de esta aplicación se centra en los métodos relacionados con la creación de mazos en la entidad Deck, particularmente en la función getShuffledDeck. Este método asegura que todas las cartas, incluyendo sus atributos y modificadores, sean clonadas de forma independiente para cada partida.

_Clases o paquetes creados_

• Prototype: Interfaz genérica para el Patrón Prototype (implementa 'getClone() genérico'). Esta clase se encuentra en el paquete 'patterns/prototype'.
• El resto del patrón queda aplicado en las entidades Card y Mod (clases 'ConcretePrototype' que implementan 'getClone()') y en deckService, que es el cliente donde se realiza la clonación del mazo universal a partir de sus cartas.

_Ventajas alcanzadas al aplicar el patrón_

• Cada partida tiene su propio mazo con cartas independientes, evitando conflictos derivados de referencias compartidas.
• La lógica del mazo universal actúa como una plantilla central, lo que permite realizar cambios globales en las cartas sin afectar las instancias.

### Patrón: Builder

_Tipo_: De Diseño

_Contexto de Aplicación_

El patrón Builder se utilizó para simplificar la creación de objetos complejos en el proyecto, especialmente en las entidades relacionadas con el juego. En este contexto, la lógica de construcción permite instanciar objetos como partidas, jugadores, y turnos con configuraciones específicas de manera fluida y extensible. La motivación principal ha sido dejar el proyecto preparado para futuras adaptaciones, otorgando elegancia y más profesionalidad en la creación de instancias.

El contexto principal de aplicación es la creación de:

1. Partidas (Match): Construidas a partir de un nombre, mazo de cartas, lista de jugadores y pila de descartes.
2. Jugadores (Player): Configurados con un usuario y un rol (creador o participante).
3. Turnos (Turn): Inicializados con el jugador activo y un contador de turnos.

_Clases o paquetes creados_

• Builder: Interfaz genérica para construir objetos en nuestro proyecto siguiendo el patrón Builder.
• MatchBuilder: Construye objetos de tipo Match.
• PlayerBuilder: Construye objetos de tipo Player.
• TurnBuilder: Construye objetos de tipo Turn.

Todas estas clases se encuentran en el paquete 'patterns/builder'.

_Ventajas alcanzadas al aplicar el patrón_

• Claridad en la creación de objetos: Los objetos se construyen paso a paso, con una sintaxis fluida y legible.
• Flexibilidad: Es fácil extender los Builders para admitir nuevos atributos o comportamientos.

### Patrón: Template Method

_Tipo_: De Diseño

_Contexto de Aplicación_

El patrón Template Method se ha utilizado en dos contextos principales dentro del proyecto:

1. Aplicación de modificadores específicos (applyMod): Este patrón se aplicó en la implementación de la clase base Mod, que define el método general applyMod. Este método estructura la lógica de aplicación de modificadores en todas las subclases específicas, de forma que cada una implementa su propia lógica de aplicación.

2. Clonación de modificadores (clone) para el uso del Patrón Prototipo: La clase Mod define un contrato para la clonación a través del método clone, que asegura una clonación profunda y correcta de los modificadores. Este método es sobrescrito en cada subclase, permitiendo que atributos específicos de cada modificador sean clonados de forma adecuada.

_Clases o paquetes creados_

Actualmente la funcionalidad del patrón se encuentra implementada en la propia clase abstracta Mod y en todas sus subinstancias específicas, tanto para la aplicación de modificadores (applyMod), como para la clonación (clone).

_Ventajas alcanzadas al aplicar el patrón_

• Es fácil añadir nuevos tipos de modificadores, ya que solo requieren implementar los métodos applyMod y/o clone respetando la estructura definida.
• La lógica específica de cada modificador está encapsulada en sus respectivas subclases, lo que facilita la depuración y mantenimiento del código.
• El uso de Template Method en la implementación de clone asegura que el Patrón Prototipo se aplique correctamente.

## Decisiones de diseño

_En esta sección describiremos las decisiones de diseño que se han tomado a lo largo del desarrollo de la aplicación que vayan más allá de la mera aplicación de patrones de diseño o arquitectónicos._

### Decisión 1: Implementación de una jerarquía de clases para los modificadores (applyMod)

#### Descripción del problema:

En el desarrollo del proyecto, uno de los grandes problemas ha sido el manejo de los efectos (modificadores) de las cartas, concretamente en como realizar su aplicación si cada efecto era distinto y solo había unos pocos que se repetían y que se podían generalizar.

#### Alternativas de solución evaluadas:

_Alternativa 1.a_: Utilizar un switch con múltiples case para manejar cada tipo de modificador.

_Ventajas:_
• Fácil de entender inicialmente para casos simples.
• La lógica queda en un solo lugar, lo que facilita su acceso.
_Inconvenientes:_
• El código se vuelve rápidamente inmanejable conforme aumenta el número de modificadores.
• Alta probabilidad de errores.

_Alternativa 1.b_: Implementar una jerarquía de clases de modificadores, donde Mod sea una clase abstracta y cada tipo de modificador sea una subclase específica.

_Ventajas:_
• Mejora la extensibilidad, ya que para agregar un nuevo modificador simplemente se implementa una nueva clase (incluso se puede llegar a agrupar en otra).
• Promueve la separación de responsabilidades.
• Reduce la complejidad del código.
_Inconvenientes:_
• Requiere más esfuerzo inicial para diseñar la jerarquía de clases.
• Puede parecer más complejo al tener que trabajar con múltiples clases en lugar de un solo bloque de código.

_Justificación de la solución adoptada_
Después de considerar la importancia de la extensibilidad y la mantenibilidad del sistema, se optó por implementar la alternativa 1.b, utilizando una jerarquía de clases para los modificadores. Esta decisión permite mantener el código más limpio, modular y fácil de extender, facilitando el proceso de añadir nuevos modificadores sin necesidad de modificar grandes bloques de lógica ya existente.

### Decisión 2: Eliminación de la relación entre Mod y Card

#### Descripción del problema:

Inicialmente, en el proyecto existe una relación entre Mod y Card que establece una conexión directa entre cada modificador y las cartas a las que afecta (target). Esta estructura, sin embargo, puede ser innecesaria cuando aplicamos los modificadores sobre las cartas de la mano del jugador, ya que toda la información necesaria está en la mano del jugador. Esto hace que mantener esta relación sea redundante y complique el diseño. Sin embargo, mantener la relación puede ser beneficioso para modificadores muy básicos, puesto que mantener la relación puede permitir la generalización de estos métodos, al sacar las cartas a las que afectan estos modificadores de dicho atributo.

#### Alternativas de solución evaluadas:

_Alternativa 2.a_: Mantener la relación entre Mod y Card y utilizar el atributo target en la aplicación de todos los modificadores.

_Ventajas:_
• Simplicidad a la hora de identificar la carta objetivo del modificador.
• Permite la generalización de modificadores básicos.
_Inconvenientes:_
• Para modificadores específicos utilizar el target va a ser redundante y va a complicar el código de los mismos.
• Menor legibilidad del código.

_Alternativa 2.b_: Eliminar la relación directa entre Mod y Card y basarse en la mano del jugador.

_Ventajas:_
• Simplificación del modelo de datos.
_Inconvenientes:_
• Debilita la generalización de modificadores básicos, ya que desaparece el único atributo con el que puedes generalizar las cartas que buscas en la mano.

_Alternativa 2.c_: Mantener la relación entre Mod y Card y utilizar el atributo target solo en aquellos modificadores que lo necesiten para generalizar.

_Ventajas:_
• Permite la generalización de modificadores sencillos.
• Facilita la comprensión y legibilidad de modificadores específicos.
_Inconvenientes:_
• Aumenta la diferencia estructural de los códigos de los distintos modificadores.
• Puede complicar la compresión de los modificadores.

_Justificación de la solución adoptada_
Se decidió optar por la alternativa 2.c, ya que ofrece un equilibrio entre simplificación y flexibilidad. Esta solución permite la generalización de los modificadores más sencillos, gracias al atributo target, mientras que también evita la redundancia y la complejidad excesiva para los modificadores específicos. Esta combinación facilita la comprensión y legibilidad del código al permitir una estructura uniforme para los modificadores más básicos y una mayor flexibilidad en aquellos más complejos.

### Decisión 3: Uso del método básico para bonus y penalty

#### Descripción del problema:

En el desarrollo del sistema, inicialmente se contemplaban métodos separados para aplicar bonus y penalty a las cartas. Esta separación, aunque clara, generaba una duplicidad de código, especialmente en el caso de modificadores sencillos donde la lógica de aplicación es similar, variando únicamente en el signo del valor.

#### Alternativas de solución evaluadas:

_Alternativa 3.a_: Mantener métodos separados para bonus y penalty.

_Ventajas:_
• Claridad semántica: cada método tiene una única responsabilidad.
• Mejora la legibilidad al ser explícito sobre si se está aplicando un bonus o una penalty.
_Inconvenientes:_
• Aumenta la duplicidad de código, ya que la lógica es la misma para bonus y penalty.

_Alternativa 3.b_: Unificar el método básico para calcular bonus para aplicar también penalty, utilizando valores negativos.

_Ventajas:_
• Reducción de la duplicidad de código.
• Facilita la implementación de modificadores básicos que solo necesitan sumar o restar valores.
_Inconvenientes:_
• Menor claridad semántica.
• Puede generar confusión sobre cuándo se aplica un bonus y cuándo un penalty.

_Justificación de la solución adoptada_
Como solución, se decidió optar por la alternativa 3.b, unificando el método basic de bonus para que también pueda aplicar penalty. Esta unificación se logra mediante el uso de valores negativos en el atributo primaryValue del modificador (mod), lo cual permite que la misma lógica se aplique tanto a bonus como a penalty.

### Decisión 4: Manejo de múltiples valores en los modificadores

#### Descripción del problema:

En el diseño inicial, los atributos primaryValue y secondaryValue del modificador (mod) estaban destinados a manejar uno o dos valores. Sin embargo, algunos modificadores, como BonusCardRun o BonusSameSuit, requieren manejar múltiples valores para determinar sus efectos. Esto llevó a considerar si era mejor cambiar la estructura de los valores de los modificadores para tener más flexibilidad.

#### Alternativas de solución evaluadas:

_Alternativa 4.a_: Cambiar primaryValue y secondaryValue por una lista de valores (List<Integer>).

_Ventajas:_
• No aporta nada especialmente útil, simplemente que el código de algunos modificadores se ve más generalizado.
_Inconvenientes:_
• Va a dar problemas porque Spring va a tratar de entender dicho atributo como una relación.

_Alternativa 4.b_: Mantener primaryValue y secondaryValue, y crear una implementación específica para modificadores que necesiten múltiples valores.

_Ventajas:_
• Reduce la complejidad general, ya que la mayoría de los modificadores no requieren múltiples valores y pueden seguir utilizando primaryValue y secondaryValue.
_Inconvenientes:_
• El único inconveniente es precisamente que disminuye la generalización al tener lógica tan específica en algunos modificadores.

_Justificación de la solución adoptada_
Se decidió optar por la alternativa 4.b, manteniendo primaryValue y secondaryValue para la mayoría de los modificadores y creando una implementación específica para aquellos que requieran manejar múltiples valores, además de que se quiere evitar al máximo posible problemas con Spring.

### Decisión 5: Orden de ejecución de los modificadores

#### Descripción del problema:

En el sistema existen modificadores de diferentes tipos, como clear, que eliminan penalizaciones u otros efectos de cartas, y blank, que anulan cartas parcial o completamente. Para asegurar una ejecución consistente y evitar conflictos, es necesario establecer un orden de ejecución para estos modificadores. Esta es una decisión que se establece en el presente, pero que se va a implementar en un futuro.

#### Alternativas de solución evaluadas:

_Alternativa 5.a_: Ejecutar los modificadores sin un orden explícito.

_Ventajas:_
• Implementación (a futuro) más simple, puesto que no hay que establecer orden en la ejecución de los modificadores.
_Inconvenientes:_
• Los modificadores de tipo blank podrían aplicarse antes que los de tipo clear, lo cual podría impedir que las penalizaciones o efectos se eliminen correctamente, generando errores.

_Alternativa 5.b_: Definir un orden explícito de ejecución, empezando por los modificadores de tipo clear y luego aplicando los de tipo blank.

_Ventajas:_
• Asegura un orden controlado y predecible en la ejecución de los modificadores.
• Permite la ejecución correcta de los modificadores. Esto se debe a que una clear puede anular total o parcialmente una blank.
_Inconvenientes:_
• Introduce una mayor complejidad en la lógica de aplicación.

_Justificación de la solución adoptada_
Se decidió adoptar la alternativa 5.b, estableciendo un orden claro de ejecución de los modificadores, ya que va a permitir que el juego funcione correctamente y que no se calculen los puntos de cada jugador de forma incorrecta.

### Decisión 6: Clasificación de los modificadores

#### Descripción del problema:

Inicialmente, no existía ninguna forma de diferenciar entre los tipos de modificadores, ya sea BONUS, PENALTY, o OTHER. Esta falta de clasificación hacía que los efectos de modificadores como clear no pudieran aplicarse correctamente, ya que no se podía determinar si un modificador era una bonificación, una penalización u otro tipo de efecto.

#### Alternativas de solución evaluadas:

_Alternativa 6.a_: Mantener la implementación sin distinguir los tipos de modificadores.

_Ventajas:_
• Código menos complejo.
_Inconvenientes:_
• No permite aplicar los modificadores de tipo clear adecuadamente.

_Alternativa 6.b_: Añadir un atributo modType a la entidad Mod para clasificar los modificadores como BONUS, PENALTY, o OTHER.

_Ventajas:_
• Facilita la identificación del tipo de modificador.
• Permite aplicar correctamente los modificadores de tipo clear.
_Inconvenientes:_
• Requiere un esfuerzo adicional para la correcta asignación del tipo a cada modificador.
• Introduce complejidad en el modelo de datos al tener que añadir un enumerado.

_Justificación de la solución adoptada_
Se opta por la alternativa 6.b, ya que esta clasificación es esencial para que los modificadores de tipo clear puedan aplicar sus efectos de manera adecuada.

### Decisión 7: Relación bidireccional entre Carta y Modificador

#### Descripción del problema:

En el diseño inicial, la relación entre Card (Carta) y Mod (Modificador) se modeló como unidireccional, lo cual significa que solo se podía acceder a los modificadores desde la carta. Sin embargo, surgió la necesidad de acceder tanto a los modificadores asociados a cada carta como a la carta de origen desde un modificador específico, puesto que es lo que permite aplicar las bonificaciones/penalizaciones para muchos modificadores.

#### Alternativas de solución evaluadas:

_Alternativa 7.a_: Mantener la relación unidireccional desde Card hacia Mod.

_Ventajas:_
• Menor esfuerzo de implementación inicial.
• Evita el uso de relaciones bidireccionales, que pueden dar un gran número de problemas a largo plazo.
_Inconvenientes:_
• Complica mucho más la lógica para obtener la carta de origen de un modificador.

_Alternativa 7.b_: Implementar una relación bidireccional entre Card y Mod.

_Ventajas:_
• Flexibilidad para acceder tanto a la carta desde el modificador como a los modificadores desde la carta.
• Facilita la implementación de los modificadores, llamando simplemente a la carta origen para aplicarlos.
_Inconvenientes:_
• Mayor complejidad en el manejo de la relación.

_Justificación de la solución adoptada_
Por todo lo anterior, se opta claramente por la alternativa 7.b, ya que se prefiere añadir una relación bidireccional, a tener que hacer varias llamadas para obtener la carta origen, puesto que es algo más ineficiente y dificil de comprender en código.

### Decisión 8: Modificación del Target para Modificadores de Cartas de Tipo Weapon ("Buque de guerra" y "Dirigible de guerra")

#### Descripción del problema:

En el diseño actual del proyecto, ciertas cartas del tipo Weapon tienen penalizaciones sobre sí mismas que dependen de la presencia de otros tipos de cartas en la mano. Por ejemplo, la carta "Buque de Guerra" tiene una penalización que donde la carta se anula si hay al menos una carta de tipo "Inundación" en la mano. Inicialmente, el target de los modificadores apuntaba a la propia carta (pues afecta a sí misma), pero esta implementación tenía el riesgo de no aplicar correctamente los efectos de modificadores clear, que podrían afectar a los tipos de las penalizaciones de dichas cartas.

#### Alternativas de solución evaluadas:

_Alternativa 8.a_: Mantener la propia carta del modificador como target.

_Ventajas:_
• Permite que todos los modificadores tengan en target las cartas a las que afecta, y que no haya ningún modificador con target especiales.
• Mayor comprensión para los desarrolladores que trabajen en esa parte del proyecto.
_Inconvenientes:_
• Complica la lógica para los modificadores de tipo clear.

_Alternativa 8.b_: Incluir en el target del modificador las cartas del tipo que necesitan estar presentes en la mano para evitar la anulación.

_Ventajas:_
• Facilita la lógica para los modificadores de tipo clear.
_Inconvenientes:_
• El target puede malinterpretarse puesto que no cumple su función real.

_Justificación de la solución adoptada_
Se decidió optar por la alternativa 8.b. Esta solución va a falicar en gran parte el código para manejar la aplicación de modificadores tipo clear, permitiendo ahorrar tiempo y complicaciones que puedan suceder.

### Decisión 9: Implementación de Modificadores Dinámicos en Mod

#### Descripción del problema:

Algunos modificadores requieren definir su objetivo (targetCard) y parámetros como el tipo de carta (CardType) en tiempo de ejecución, particularmente cuando el jugador elige dinámicamente la carta a modificar. La implementación actual no contempla esta dinámica, lo cual hace difícil aplicar modificadores que dependan de una selección específica del jugador durante la partida.

#### Alternativas de solución evaluadas:

_Alternativa 9.a_: Sobrecarga del método applyMod para incluir nuevos parámetros como targetCard o CardType.

_Ventajas:_
• Permite que los jugadores puedan hacer elecciones en tiempo de ejecución (en juego).
_Inconvenientes:_
• Incrementa la complejidad, puesto que así para modificadores con decisiones dinámicas se debe implementar el applyMod normal sin hacer nada y el applyMod sobrecargado con los nuevos parámetros.

_Alternativa 9.b_: Modificar el objetivo (target) dinámicamente antes de ejecutar applyMod.

_Ventajas:_
• Simplifica la cantidad de parámetros del método applyMod.
_Inconvenientes:_
• Dificulta el seguimiento del flujo del programa al tener que modificar el target del modificador desde el servicio, quedando fuera del alcance del método applyMod.

_Justificación de la solución adoptada_
Se decidió optar por la alternativa 9.a, es decir, sobrecargar el método applyMod. Esta decisión permite tener un control más explícito sobre los parámetros del modificador, lo que incrementa la seguridad y facilita el mantenimiento del código. Al sobrecargar applyMod, se evita la manipulación directa del target en la base de datos.

### Decisión 10: Cambio en el diseño del modelo entre Usuario, Jugador y Partida

#### Descripción del problema:

Inicialmente, en el modelo se definía que un Usuario podía ser o no un Jugador y este jugador podía ser Creador o Participante de una Partida, teniendo atributos como el score y el rol asociados al jugador. También, un jugador podía pertenecer a varias partidas, haber ganado varias partidas y haber creado varias partidas. Este diseño presentaba incongruencias, ya que los atributos score y rol deberían estar asociados al contexto específico de una partida, y no tener múltiples asociaciones simultáneas.

Además, se contempla eliminar el atributo creador de partida, pues se puede sacar de la lista de jugadores de la misma gracias al atributo rol. Se contempla esta necesidad ya que generaba una incongruencia en la base de datos, puesto que una partida necesitaba un creador y un jugador creador requería una partida.

#### Alternativas de solución evaluadas:

_Alternativa 10.a_: Redefinir la relación para que un Jugador solo pueda pertenecer a una única Partida, eliminando la relación de un jugador con múltiples partidas y asociando el rol y el score al contexto específico de una única partida. También se busca definir la relación jugador-partida como bidireccional, para poder sacar las partidas de un usuario a partir de sus jugadores, y la relación usuario-jugador pasa a ser OneToMany bidireccional. También consiste en eliminar el atributo creador de partida.

_Ventajas:_
• El rol de Creador o Participante queda claramente asociado a una partida específica, mejorando la claridad del código y la compresión del modelo.
• Se elimina el atributo creador de Partida, ya que se puede deducir de la lista de jugadores en la partida y su rol.
• Elimina los problemas en la base de datos entre tablas.
_Inconvenientes:_
• Se requiere un cambio significativo en la lógica de negocio y en la base de datos.
• Atributos como creador dejan de ser explícitos.

_Justificación de la solución adoptada_
Se decidió optar por la única alternativa contemplada para mejorar el diseño del modelo y permitir que el juego funcione correctamente.

### Decisión 11: Implementación del Mazo Universal para Partidas

#### Descripción del problema:

En el diseño inicial, al crear una partida se pasaba como parámetros el id del creador y un objeto Deck. Esto significaba que se necesitaba gestionar y crear diferentes mazos cada vez que se creaba una nueva partida, lo cual incrementaba la complejidad y generaba inconsistencias.

#### Alternativas de solución evaluadas:

_Alternativa 11.a_: Mantener el enfoque inicial, donde cada partida tiene su propio mazo independiente que se pasa como parámetro en la creación de la partida.

_Ventajas:_
• Permite personalizar el mazo de cada partida según las necesidades específicas.
_Inconvenientes:_
• Incrementa la complejidad de la lógica de creación de partidas.
• Innecesario.

_Alternativa 11.b_: Utilizar un mazo universal que actúe como prototipo y hacer una copia del mismo cada vez que se cree una nueva partida (mezclando sus cartas), siguiendo el patrón de diseño Prototipo.

_Ventajas:_
• Simplificación del código y eliminación de la necesidad de pasar un mazo como parámetro en la creación de partidas.
• Garantiza la uniformidad del juego.

_Inconvenientes:_
• Necesidad de persistir el mazo universal en la base de datos y gestionar su acceso.

_Justificación de la solución adoptada_
Se decidió optar por la alternativa 11.b, utilizando un mazo universal que se persiste como un único objeto en la base de datos y actúa como un prototipo para la creación de nuevas partidas. Así, al crear una nueva partida, se realiza una clonación profunda del mismo, mezclando sus cartas en el proceso, y esta copia se guarda como mazo de la partida.

### Decisión 12: Robar carta divido en dos métodos

#### Descripción del problema:

En el diseño inicial, en cada turno un jugador tiene que robar una carta, para ello tiene dos opciones robar del mazo o de la zona de descarte. Se planteo una método que incluyera las dos funcionalidades, pero finalmente hemos decidio crearlos por separados ya que eso facilitará su implantación en frontend.

#### Alternativas de solución evaluadas:

_Alternativa 12.a_: Mantener el enfoque inicial, donde robar una carta permitiera dos opciones dentro del trablero.

_Ventajas:_
• Optimización del código
_Inconvenientes:_
• Incrementa la complejidad de implementación.

_Alternativa 12.b_: Crear dos funcionalidades por separadas.
_Ventajas:_
• Simplificación a la hora de implementar los componentes en el tablero.

_Inconvenientes:_
• Código repetitivo

_Justificación de la solución adoptada_
Se decidió optar por la alternativa 12.b, utilizando dos métodos que implementaran dos funcionalidades por separado facilita la implantación y jugabilidad de los componentes de la partida( mazo y descarte).

### Decisión 13: Utilización de WebSocket o ScheduledExecutorService

#### Descripción del problema:

En un principio, hemos tenido problemas sobre como afrontar la comunicación y la lógica de tiempo en nuestro juego.

#### Alternativas de solución evaluadas:

_Alternativa 13.a_: Utilizar un ScheduledExecutorService que cada segundo reste al contador del turno (atributo en backend), de forma que al completar las acciones se pase al siguiente turno y si éste no se completa se cancelen las acciones y se pase al siguiente.

_Ventajas:_
• Centralizamos toda la lógica de juego en backend.
• Fácil sincronización frontend - backend gracias a lo anterior.
_Inconvenientes:_
• Puede generar problemas para gestionar múltiples partidas simultáneamente.
• Hace que el código sea más complejo de leer.

_Alternativa 13.b_: Usar WebSockets, de forma que permitan la comunicación efectiva del backend con los jugadores una partida en curso, llevando un contador en frontend de forma que si se finaliza el turno y no se han completado las acciones, se llame a un endpoind del backend para manejar la cancelación de las acciones de dicho turno.

_Ventajas:_
• Código más sencillo de leer.

_Inconvenientes:_
• Frontend y Backend son responsables de manejar la lógica de juego.
• Puede hacer que la sincronización entre ambos sea más compleja.

_Justificación de la solución adoptada_
Se decidió optar por la alternativa 13.b), ya que se trata de una implementación más sencilla que va a permitir que todo el equipo entienda mejor el código y cumple con la funcionalidad que buscamos.

### Decisión 14: Lógica de aplicación de modificadores mediante un mapa de decisiones

#### Descripción del problema:

En el sistema de modificadores, algunos requieren decisiones adicionales por parte del jugador (como ya se ha mencionado), mientras que otros pueden aplicarse automáticamente. Esto genera una complejidad adicional en la aplicación de modificadores, ya que es necesario garantizar que se respeten las reglas de cada tipo de modificador y que se permita la interacción del jugador en los casos correspondientes.

El reto era implementar una solución que integrara esta lógica de decisiones con la ejecución ordenada de los modificadores, asegurando la consistencia del sistema y evitando errores en los flujos de aplicación.

#### Alternativas de solución evaluadas:

_Alternativa 14.a_: Implementar un sistema sin decisiones dinámicas.

_Ventajas:_
• Implementación inicial más simple.
_Inconvenientes:_
• Se pierde gran parte del sentido del juego si toda la aplicación de modificadores se realizase de forma automática.

_Alternativa 14.b_: Gestionar todas las decisiones con un flujo fijo para cada modificador.

_Ventajas:_
• Menor riesgo de errores.

_Inconvenientes:_
• Implementación compleja y poco flexible, ya que cada tipo de modificador requeriría su propio flujo de aplicación.

_Alternativa 14.c_: Utilizar un mapa de decisiones para gestionar modificadores dinámicos

_Ventajas:_
• Escalable y flexible, ya que nuevos tipos de modificadores pueden agregarse sin alterar significativamente la estructura existente.
• Reduce la complejidad en la capa del frontend.

_Inconvenientes:_
• Requiere establecer un formato estándar para las decisiones.
• Mayor carga para asegurar que las decisiones sean coherentes.

_Justificación de la solución adoptada_
Se decidió adoptar la alternativa 14.c, implementando un mapa de decisiones en el backend, ya que proporciona una solución escalable y flexible para gestionar tanto modificadores automáticos como aquellos que requieren interacción del jugador. Esta solución facilita el mantenimiento y extensión futura del sistema, garantizando que el juego funcione de forma coherente y predecible para los jugadores.

### Decisión 15: Llamadas del frontend al backend para la aplicación de modificadores especiales

#### Descripción del problema:

La aplicación de modificadores especiales requiere que el jugador tome decisiones. Estas decisiones deben ser enviadas desde el frontend al backend para que el sistema pueda procesarlas y aplicarlas correctamente. El problema radica en que la decisión optada para la aplicación de modificadores especiales en backend, es la de usar un mapa de decisiones. Entonces, el frontend directamente no podía enviar un mapa en el cuerpo de la solicitud, lo que estaba dando ciertos problemas al equipo.

#### Alternativas de solución evaluadas:

_Alternativa 15.a_: Enviar las decisiones como múltiples llamadas individuales.

_Ventajas:_
• Cada decisión se procesa de forma independiente.
• Fácil de implementar para decisiones simples.
_Inconvenientes:_
• Dificultad para garantizar la coherencia entre decisiones.
• Incrementa la complejidad de manejo en el backend

_Alternativa 15.b_: Utilizar una lista de decisiones en el frontend que se convierta en un mapa en el backend.

_Ventajas:_
• Organización clara y estructurada de las decisiones en el backend mediante el uso de un mapa de decisiones.
• Escalable y flexible.

_Inconvenientes:_
• Requiere estandarizar la estructura de las decisiones en el frontend para garantizar una conversión precisa en el backend.
• Carga de validación para asegurar que las decisiones sean coherentes.

_Justificación de la solución adoptada_
Se decidió adoptar la alternativa 15.b, donde el frontend envía una lista de decisiones al backend, y esta se convierte en un mapa de decisiones para su procesamiento. Esta solución permite una gestión clara y eficiente de los modificadores especiales, al tiempo que asegura la flexibilidad necesaria para futuros cambios o ampliaciones.

### Decisión 16: Uso del mazo universal mediante clonación profunda

#### Descripción del problema:

En la Decisión 11 se introdujo el uso de un mazo universal que contiene todas las cartas disponibles en el juego, permitiendo la simplicidad. Sin embargo, en la implementación inicial, las cartas del prototipo y las cartas del mazo de una partida compartían referencias, lo que causaba problemas de consistencia, puesto que solo se clonaba la carta, pero no su estructura interna.

#### Alternativas de solución evaluadas:

_Alternativa 16.a_: Usar referencias directas del mazo universal.

_Ventajas:_
• Simplicidad en la implementación.
• Ahorro de memoria, ya que todas las instancias comparten las mismas referencias.
_Inconvenientes:_
• Modificaciones realizadas a las cartas durante el juego afectan al mazo universal, lo que introduce inconsistencias graves.
• Difícil de depurar y mantener.

_Alternativa 16.b_: Crear copias superficiales de las cartas al iniciar una partida.

_Ventajas:_
• Permite que las cartas de la partida tengan atributos propios independientes del mazo universal.
• Menor uso de memoria que clonando toda la estructura completa.

_Inconvenientes:_
• Las referencias a estructuras complejas dentro de las cartas, como modificadores o relaciones entre cartas, aún podrían compartirse, lo que introduce inconsistencias.
• Problemas al modificar elementos anidados dentro de las cartas.

_Alternativa 16.c_: Implementar una clonación profunda de las cartas y su estructura al inicializar el mazo de partida.

_Ventajas:_
• Garantiza la independencia total entre el mazo universal y las cartas de la partida.
• Escalable y flexible.

_Inconvenientes:_
• Requiere una implementación más compleja, ya que es necesario clonar no solo las cartas sino también todos sus atributos relacionados.
• Mayor consumo de memoria al crear instancias completamente independientes.

_Justificación de la solución adoptada_
Se decidió adoptar la alternativa 16.c, implementando una clonación profunda de las cartas y toda su estructura al inicializar una partida. Esta solución asegura que el mazo universal permanezca inmutable, sirviendo únicamente como referencia para generar nuevas cartas de partida. De esta forma, las cartas del juego son completamente independientes, lo que elimina los problemas de consistencia y facilita el mantenimiento del sistema.

### Decisión 17: Implementación de una ronda de turnos especiales para efectos dinámicos y automáticos

#### Descripción del problema:

En el diseño inicial del sistema de modificadores, existía una ambigüedad sobre cuándo y cómo aplicar los efectos automáticos y dinámicos de las cartas. Esto generaba problemas de sincronización y flujo, ya que algunos efectos requerían decisiones del jugador (dinámicos), mientras que otros podían aplicarse automáticamente (automáticos). No existía un consenso sobre si manejar ambos tipos de efectos en el mismo flujo de turnos o separarlos en momentos distintos.

#### Alternativas de solución evaluadas:

_Alternativa 17.a_: Aplicar modificadores automáticos y dinámicos al final del turno estándar.

_Ventajas:_
• A simple vista, parece lo más simple debido a la aplicación de modificadores en el presente sin dejarlos en "en cola".
_Inconvenientes:_
• Riesgo de errores en la ejecución de modificadores.
• Mayor carga al sistema al tener que aplicar modificadores reiteradamente.

_Alternativa 17.b_: Implementar una ronda especial de turnos para manejar los efectos dinámicos y automáticos.

_Ventajas:_
• Permite a los jugadores realizar todas las decisiones dinámicas necesarias en una ronda específica (special turn).
• Los efectos automáticos se aplican de forma ordenada al finalizar las decisiones dinámicas, asegurando consistencia.
• Claridad para los jugadores.

_Inconvenientes:_
• Requiere una implementación adicional para manejar la lógica de la ronda especial.

_Justificación de la solución adoptada_
Se decidió adoptar la alternativa 17.b, implementando una ronda especial de turnos (“special turn”) que permite a los jugadores realizar decisiones dinámicas antes de aplicar los efectos automáticos de las cartas. Esta solución ofrece un balance óptimo entre claridad, consistencia y escalabilidad, ya que asegura que cada tipo de modificador se maneje en su contexto adecuado.

## Refactorizaciones aplicadas

### Refactorización 1:

En esta refactorización se cambió la forma en que se copiaba la entidad Deck para cada partida, pasando de una copia superficial a una copia profunda que garantice la independencia de las cartas en la base de datos.

### Decisión 18: Almacenamiento inmutable de todas las cartas de la partida mediante copia profunda

#### Descripción del problema:

Al implementar modificadores que permiten al jugador seleccionar cualquier carta del juego, surgió la necesidad de garantizar que dichas cartas estuvieran accesibles de forma segura desde el frontend. En el diseño inicial, el mazo de la partida contaba con una lista con todas las cartas de la partida y otra con las cartas manipuladas por los jugadores, pero compartían referencias, lo que podía provocar inconsistencias al modificar atributos de las cartas o al aplicar efectos en cascada

#### Alternativas de solución evaluadas:

_Alternativa 18.a_: Acceso directo al mazo mutable de la partida desde el frontend.

_Ventajas:_
• Implementación inicial sencilla.
• Menor uso de memoria al no duplicar las cartas.
_Inconvenientes:_
• Difícil de garantizar la consistencia y seguridad de los datos al aplicar modificadores que manipulan las cartas.

_Alternativa 18.b_: Crear una copia profunda de las cartas al inicio de la partida para almacenarlas de forma inmutable, mediante una relación "copia" con las cartas del mazo.

_Ventajas:_
• Garantiza que todas las cartas del juego estén disponibles y no puedan ser modificadas accidentalmente.

_Inconvenientes:_
• Aumenta el uso de memoria al duplicar las cartas.
• Se introduce una relación extra para el correcto funcionamiento con Hibernate.

_Justificación de la solución adoptada_
Se decidió implementar la alternativa 18.b, creando una copia profunda de todas las cartas al inicio de la partida y almacenándolas de forma inmutable. Así, se tiene una lista de cartas que va a representar al mazo y se va a ir modificando mientras se juega, y una lista inmutable con todas las cartas de la partida. Esta solución permite al frontend interactuar con un mazo de cartas que refleja fielmente el estado inicial del juego, evitando conflictos o inconsistencias durante el uso de modificadores.

## Refactorizaciones aplicadas

### Refactorización 1:

En esta refactorización se simplificó el método createMatch en el servicio MatchService, así como el controlador MatchController, para reducir la complejidad y mejorar la claridad del código al crear una partida nueva. También la refactorización ha permitido la devolución de respuestas personalizadas a través del uso de DTOs, evitando dependencias circulares.

#### Estado inicial del código (se muestra servicio y controlador unificados para simplificar)

```Java
@Transactional
public Match createMatch(User currentUser) {
    // Verificar que no haya un player del usuario que esté en una partida en progreso
    boolean userIsPlaying = currentUser.getPlayers().stream().filter(p -> p.isPlaying()).findFirst().isPresent();

    if (userIsPlaying) {
        throw new IllegalStateException("User has a player that is already in the match.");
    }

    Player pCreator = new Player(currentUser);

    // Obtener el mazo universal -> mezclar las cartas -> guardarlo como mazo de la partida
    Deck universalDeck = ds.findUniversalDeck();
    Deck deckForMatch = universalDeck.getShuffledDeck();

    // Guardar la partida con los datos en la BD
    Match m = new Match();
    m.setDeck(deckForMatch);
    Discard d = new Discard(); // Inicialmente la zona de descarte está vacía en cada partida.
    m.setDiscard(d);
    List<Player> players = new ArrayList<>();
    players.add(pCreator);
    m.setPlayers(players);
    return mr.save(m);
}

// Usuario pulsa botón de crear partida desde el frontend
@PostMapping("/create")
public ResponseEntity<Match> createMatch() {
    try {
        User currentUser = us.findCurrentUser();
        Match m = ms.createMatch(currentUser);
        return new ResponseEntity<>(m, HttpStatus.CREATED);
    } catch (ResourceNotFoundException e) {
        throw new ResourceNotFoundException("Usuario actual no encontrado");
    }
}
```

#### Estado del código refactorizado

```Java
    @Transactional
    public Match createMatch(User currentUser, String matchName) {
    
        if (currentUser.isPlaying()) {
            throw new PlayerAlreadyInMatchException("The current user has an associated player who is in game.");
        }
    
        // Crear el jugador creador y asignarle el usuario actual
        Player creator = new Player(currentUser);
    
        // Crear el mazo para la partida
        Deck universalDeck = deckService.findUniversalDeck();
        Deck deckForMatch = universalDeck.getShuffledDeck();
    
        Match match = new Match(matchName, deckForMatch,new Discard(), List.of(creator));
    
        // Guardar la partida
        return matchRepository.save(match);
    }

    @PostMapping("/create")
    public ResponseEntity<MatchDTO> createMatch(@RequestParam("matchName") String matchName) {

        User currentUser = us.findCurrentUser();

        Match m = ms.createMatch(currentUser, matchName);

        return new ResponseEntity<>(new MatchDTO(m), HttpStatus.CREATED);

    }
```

#### Problema que nos hizo realizar la refactorización

El código original presentaba una complejidad innecesaria en la creación de partidas. La verificación manual del estado del jugador y la construcción de la partida eran redundantes y complicaban la lectura del código. Además, se utilizaban bloques try-catch que también dificultaban la lectura. También las respuestas que devolvía el controlador contenían dependencias circulares que hacían que la respuesta fuese muy extensa.

#### Ventajas que presenta la nueva versión del código respecto de la versión original

• Se redujo la complejidad al encapsular la lógica de negocio en sus clases correspondientes.

• La creación del objeto Match se simplificó, utilizando un constructor más claro que mejora la comprensión y reduce la cantidad de código escrito.

• Se eliminó el bloque try-catch haciendo uso de excepciones personalizadas, lo que facilita la comprensión del código y errores.

• Se promueve la personalización de las respuestas gracias al uso de DTOs.

• Se mejoró la separación entre las responsabilidades del controlador y del servicio, lo cual facilita el mantenimiento y futuras extensiones del código.

### Refactorización 2:

En esta refactorización se mejoró la forma en que las cartas se clonan en el método getShuffledDeck. Antes, las cartas eran clonadas superficialmente, lo que podía generar problemas al compartir referencias entre cartas originales y clonadas. Ahora, se realiza una clonación profunda que incluye la estructura completa de modificadores, garantizando la independencia de las cartas clonadas.

#### Estado inicial del código

```Java
@JsonIgnore
public Deck getShuffledDeck() {

    // Hacemos copia profunda para evitar problemas de persistencia
    List<Card> cloneCards = new ArrayList<>();

    for (Card c : this.cards) {
        cloneCards.add(new Card(c));
    }

    Deck shuffledDeck = new Deck(cloneCards);
    shuffledDeck.shuffle();
    return shuffledDeck;
}

public void shuffle() {
    Collections.shuffle(cards);  // Mezclar el mazo
}
```

#### Estado del código refactorizado

```Java
@JsonIgnore
public Deck getShuffledDeck() {

    // Crear un mapa para almacenar la relación entre las cartas originales y las cartas clonadas
    Map<Card, Card> cardCloneMap = new HashMap<>();

    // Crear una lista para almacenar las cartas clonadas
    List<Card> cloneCards = new ArrayList<>();

    // Clonar las cartas y añadirlas al mapa y a la lista de cartas clonadas
    for (Card originalCard : this.cards) {
        Card clonedCard = new Card(originalCard);
        cloneCards.add(clonedCard);
        cardCloneMap.put(originalCard, clonedCard);
    }

    // Iterar sobre las cartas clonadas para clonar y ajustar los modificadores
    for (Card originalCard : this.cards) {
        Card clonedCard = cardCloneMap.get(originalCard);
        List<Mod> clonedMods = new ArrayList<>();

        for (Mod originalMod : originalCard.getMods()) {
            // Clonar el modificador
            Mod clonedMod = originalMod.clone();

            // Asignar la carta clonada como carta origen del modificador
            clonedMod.setOriginCard(clonedCard);

            // Ajustar el target del modificador con cartas clonadas
            if (originalMod.getTarget() != null && !originalMod.getTarget().isEmpty()) {
                List<Card> clonedTargets = originalMod.getTarget().stream()
                        .map(cardCloneMap::get) // Mapear las cartas originales a sus clones
                        .filter(Objects::nonNull) // Asegurarse de que el mapeo sea válido
                        .collect(Collectors.toList());

                clonedMod.setTarget(clonedTargets);
            } else {
                clonedMod.setTarget(new ArrayList<>());
            }

            clonedMods.add(clonedMod);
        }

        // Asignar los modificadores clonados a la carta clonada
        clonedCard.setMods(clonedMods);
    }

    // Mezclar las cartas clonadas
    Collections.shuffle(cloneCards);

    // Crear y devolver el mazo mezclado con las cartas clonadas
    return new Deck(cloneCards);
}
```

#### Problema que nos hizo realizar la refactorización

El problema con la clonación superficial previa era que las cartas originales y las cartas clonadas compartían referencias internas. Esto generaba inconsistencias, especialmente al interactuar con los modificadores, ya que las modificaciones realizadas en una instancia afectaban indirectamente a la otra.

#### Ventajas que presenta la nueva versión del código respecto de la versión original

• Independencia total entre cartas originales y clonadas.

• Garantiza que los mazos utilizados durante la partida no se vean afectados por modificaciones no intencionadas en el mazo universal.
