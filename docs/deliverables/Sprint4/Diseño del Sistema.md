# Documento de diseño del sistema

## Introducción

# ¡Descubre el emocionante mundo de **Fantasy Realms** en su versión digital!

Este proyecto representa una inmersiva y desafiante adaptación del aclamado juego de cartas, llevándolo al ámbito multijugador en línea. Diseñado para ofrecer una experiencia competitiva, interactiva y estratégica, **Fantasy Realms Digital** no solo captura la esencia del juego original, sino que también añade una serie de funcionalidades modernas para mejorar la experiencia de los jugadores.

---

## **¿Qué hace que este proyecto sea especial?**

El propósito principal del proyecto es crear una plataforma en la que jugadores de cualquier lugar puedan registrarse, conectarse y competir para conseguir la mejor puntuación, siguiendo fielmente las reglas del juego. Este sistema combina la estrategia y la suerte que han hecho de **Fantasy Realms** un éxito, con herramientas tecnológicas avanzadas que aseguran una experiencia fluida y atractiva.

---

## **Características destacadas**

### 1. **Registro y autenticación:**
- Cada jugador puede crear su propio perfil personalizado, gestionarlo y asegurar sus datos mediante un sistema robusto de registro y autenticación.
- Interfaz intuitiva y accesible para facilitar la incorporación de nuevos jugadores.

### 2. **Creación y gestión de partidas:**
- Los jugadores pueden **crear partidas** para jugar con amigos o unirse a **partidas públicas** en curso.
- Los espectadores tienen la posibilidad de **observar partidas en vivo**, promoviendo una experiencia social y colaborativa.

### 3. **Juego multijugador:**
- Un sistema que permite **interacción en tiempo real**, como enviar mensajes durante las partidas, tomar decisiones estratégicas como robar y descartar cartas, y calcular puntuaciones basadas en las reglas originales.
- Implementación de **fases de juego dinámicas**, turnos bien definidos y gestión precisa de las reglas y bonificaciones de las cartas.

### 4. **Estadísticas y logros:**
- Los jugadores pueden acceder a un **historial completo de partidas**, permitiendo analizar su desempeño y mejorar sus estrategias.
- Un sistema de **logros desbloqueables** motiva a los usuarios a alcanzar metas específicas, como victorias consecutivas, jugar con combinaciones especiales de cartas, o incluso partidas perfectas.
- **Estadísticas detalladas** que incluyen tiempo de juego, porcentaje de victorias, cartas jugadas y más.

### 5. **Reglas y tutoriales accesibles:**
- Una sección de **reglas del juego** integrada en la aplicación que incluye un PDF descargable y recursos adicionales.
- Un video explicativo donde el equipo juega una partida física y describe las mecánicas principales, perfecto para aprender rápidamente.

---

## **Ventajas de la experiencia digital**

- **Automatización de reglas:** El sistema aplica automáticamente todas las bonificaciones, penalizaciones y condiciones especiales, eliminando errores humanos y haciendo que el juego fluya sin interrupciones.
- **Juego multijugador remoto:** Conéctate y juega con amigos o desconocidos desde cualquier parte del mundo.
- **Personalización:** Desde logros únicos hasta estadísticas individuales, cada jugador tiene su propio espacio dentro del mundo de **Fantasy Realms Digital**.
- **Accesibilidad:** Diseñado para ser intuitivo y acogedor para nuevos jugadores, al mismo tiempo que desafía a los veteranos con estrategias avanzadas.

---

## **¿Cómo empezar?**

1. **Consulta las reglas:** Aprende las mecánicas del juego en nuestra sección de reglas o mediante el PDF descargable en la aplicación.
2. **Consulta las funcionalidades:** Entiende qué ofrecemos como proyecto y qué es lo que puedes hacer aquí.
3. **Observa una partida:** Mira nuestro video donde mostramos una partida en la versión física, con explicaciones detalladas de cada movimiento.
4. **Regístrate y juega:** Únete a nuestra comunidad y demuestra tus habilidades estratégicas en partidas multijugador.

---

## **Tu próxima aventura te espera**

Este proyecto combina tecnología avanzada con el espíritu competitivo y divertido del juego original. Ya sea que busques desafiar a tus amigos, mejorar tu estrategia o simplemente pasar un buen rato, es el lugar ideal para ti. ¡Únete a nosotros ahora!

---

## **Si desea obtener información sobre cómo jugar, puede consultar lo siguiente:**

1. **Reglas del juego**  
   Puedes consultar las reglas del juego en el siguiente enlace o en la propia aplicación en el apartado de "Reglas"
   [Reglas del Juego (PDF)](../../information/rules.pdf)

1. **Video sobre funcionalidades del sistema**  
   Puedes consultar el siguiente video grabado por un miembro del equipo en el que explica todo lo que se puede hacer en la aplicación
   [Enlace para visualizar el video de funcionalidades](https://drive.google.com/drive/folders/13l83rs_7ODAYYnMgho6iNgehaFMg7sLR?usp=drive_link)

3. **Video sobre partida jugada**  
   Si lo prefiere, puede consultar ver el siguiente video donde el equipo juega una partida en la versión física.
   [Enlace para visualizar la partida jugada por el equipo](https://youtu.be/nJwS8-HFg4o?si=wTtp-5bdgQfJ7-PC)

## Diagrama(s) UML:

### Diagrama de Dominio/Diseño

![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/diagrams/DP1Entrega.drawio.png)

### Diagrama de Dominio/Diseño Mod

![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/diagrams/DP1%20-Mod.drawio.png)

### Diagrama de Capas (incluyendo Controladores, Servicios y Repositorios)

![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/diagrams/capasEntrega.drawio%20(1).png)

### Diagrama Servicio-Excepciones

![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/diagrams/ServiciosExcepciones.drawio%20(1).png)

## Descomposición del mockups del tablero de juego en componentes

En esta sección procesaremos el mockup del tablero de juego (o los mockups si el tablero cambia en las distintas fases del juego). Etiquetaremos las zonas de cada una de las pantallas para identificar componentes a implementar. Para cada mockup se especificará el árbol de jerarquía de componentes, así como, para cada componente el estado que necesita mantener, las llamadas a la API que debe realizar y los parámetros de configuración global que consideramos que necesita usar cada componente concreto.
Por ejemplo, para la pantalla de visualización de métricas del usuario en un hipotético módulo de juego social:

### Barra de Navegación
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/HOME_Y_NAV.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
  - **NavBar – Brand**: Imagen que redirige a la página principal.
  - **NavBar – DropdownMenu**: Menú desplegable que agrupa opciones de navegación.
    - **DropdownItem**: Botones dentro del menú desplegable que permiten navegar a diferentes rutas o realizar acciones.
  - **NavBar – Buttons**: Botones que ejecutan acciones o enlazan a páginas específicas.

**Nota**: El NavBar está en todas las páginas, pero solo se especificará una vez.

### HOME

#### Árbol de Componentes
- **App – Componente principal de la aplicación**
  - **NavBar** – Barra de navegación superior
  - **Home** – Página principal en la que aparece cualquier usuario.

### LOGIN
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/LOGIN.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **Login** – Componente de la página de inicio de sesión
  - **Section (Login)** – Sección para el formulario de inicio de sesión
    - **FormGenerator** – Componente para generar dinámicamente el formulario de inicio de sesión
    - **Inputs** – Campos definidos para capturar datos del usuario
    - **Button (Login)** – Botón para enviar el formulario  
      **Endpoint**: `POST /api/v1/auth/signin`
  - **Section** – Sección con el enlace al registro
    - **Button (Register now!)** – Botón para redirigir al formulario de registro de usuario

### REGISTRO DE USUARIO
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/REGISTER.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **Register** – Componente de la página de registro de usuario
  - **Section (Register)** – Sección con el formulario de registro
    - **FormGenerator** – Componente para generar dinámicamente el formulario de registro
    - **Inputs** – Campos definidos para capturar datos del usuario (e.g., username, password, email)
    - **Button (Register)** – Botón para enviar el formulario  
      **Endpoint**: `POST /api/v1/auth/signup`

### PARTIDAS
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/MATCHES.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **MatchList** – Componente donde se muestran las partidas disponibles
  - **Button (Crear Partida)** – Botón para crear las partidas  
    **Endpoint**: `POST /api/v1/matches/create`
  - **List** – Contenedor que muestra el listado de partidas por empezar
    - **ListGroupItem** – Elementos individuales dentro del listado de partidas que representan cada partida
      - **Button (Ver Detalles)** – Botón que muestra los detalles de las partidas  
        **Endpoint**: `GET /api/v1/matches/{id}`
    - **Buttons** – Contenedor para los botones de paginación (Anterior y Siguiente)

### DETALLES DE UNA PARTIDA
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/MATCHDETAIL.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **MatchDetail** – Componente que muestra los detalles de una partida
  - **Section (Título de la Partida)** – Muestra el nombre de la partida
  - **Section (Jugadores y Espectadores)** – Contenedor para listas de jugadores y espectadores
    - **Jugadores** – Lista de jugadores que no son espectadores. Si eres el creador, aparece una estrella indicativa.
      - **Button (Eliminar)** – Botón para eliminar jugadores (visible solo para el creador)  
        **Endpoint**: `DELETE /api/v1/matches/{id}/remove-player/{userId}`
    - **Espectadores** – Lista de jugadores con rol de espectador
      - **Button (Cambiar Rol)** – Botón para cambiar el rol  
        **Endpoint**: `POST /api/v1/matches/{id}/toggle-role`
  - **Section (Invitar Amigos)** – Lista para invitar amigos a la partida
    - **Amigos** – Contenedor para la lista de amigos
      - **Amigo** – Cada amigo de la lista de amigos
        - **Button (Invitar)** – Botón para invitar amigos a la partida  
          **Endpoint**: `POST /api/v1/game-invitation/send/{receiver}/{id}`
  - **Botones de Acción** – Contiene los botones principales de interacción
    - **Button (Unirme)** – Botón para unirse a la partida (visible solo si no estás unido a la partida)  
      **Endpoint**: `POST /api/v1/matches/{id}/join`
    - **Button (Comenzar Partida)** – Botón para comenzar la partida (visible solo para el creador)  
      **Endpoint**: `POST /api/v1/matches/{id}/start`

### REGLAS
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/RULES.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **Rules** – Página que muestra las reglas del juego
  - **Buttons** – Botones para mostrar/ocultar las diferentes secciones
  - **Collapses** – Contenedores colapsables para las diferentes secciones, visibles al pulsar el botón correspondiente

### USERS
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/USERS.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **UserListAdmin** – Página de administración de usuarios
  - **Section** – Sección principal que contiene los elementos clave de la lista de usuarios
    - **Button** – Botón para agregar un usuario. Redirige a la página de creación de usuarios.
    - **Users** – Tabla de usuarios con sus respectivos botones:
      - **Eliminar** – Botón para eliminar un usuario.
      - **Editar** – Botón que redirige a la página de edición de usuarios.
    - **Pagination** – Botones relacionados con la paginación (Anterior y Siguiente).

### AÑADIR USUARIOS
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/ADDUSERS.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **UserEditAdmin** – Componente para añadir o editar usuarios
  - **UserForm** – Formulario para añadir un nuevo usuario con sus respectivos campos
    - **Button (Guardar)** – Botón para enviar los datos  
      **Endpoint**: `POST /api/v1/auth/signup`
    - **Button (Cancelar)** – Enlace para cancelar y volver a la página de usuarios

### EDITAR USUARIOS
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/EDITUSERS.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **UserEditAdmin** – Página de edición de usuarios
  - **Form** – Formulario de edición de usuario
    - **Form-Items** – Campos necesarios con su correspondiente etiqueta para crear un Usuario
    - **Button (Guardar)** – Botón para enviar los datos  
      **Endpoint**: `PUT /api/v1/users/{id}`
    - **Button (Cancelar)** – Enlace para cancelar y volver a la página de usuarios

### RANKING PARTIDA
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/MATCHRANKING.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **MatchRanking** – Componente que muestra el ranking de jugadores de una partida
  - **Section (RANKING)** – Encabezado que muestra el título de la página
  - **Section (Players)** – Sección que contiene la lista de jugadores con los puntos obtenidos y su puesto en el ranking
    - **Player** – Elemento individual que representa a un jugador  
      Si se pulsa, abre un modal con el desglose de puntos de cada jugador.
  - **Button** – Botón que redirige a la lista de partidas

### PARTIDA
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/MATCH.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **Match** – Componente principal de la partida
  - **Section (Área de Juego)** – Muestra las diferentes zonas del tablero de juego
    - **GameSection** – Contenedor superior con el mazo y la pila de descartes
    - **Section (Mazo)** – Componente que muestra el mazo de cartas para robar
      - **Button** – Componente que aparece solo si es tu turno y permite al jugador robar cartas  
        **Endpoint**: `GET /api/v1/deck/{deckId}`
    - **Section (Pila de Descartes)** – Componente que muestra la pila de cartas descartadas por los jugadores  
      Si es tu turno y hay cartas disponibles, puedes robar una carta de esta zona.
    - **Section (Mano del Jugador)** – Componente que muestra las cartas en la mano del jugador
      - **Button** – Componente que aparece al seleccionar una carta y solo si se ha robado  
        Permite descartar una carta de la mano a la pila de descartes  
        **Endpoint**: `POST /api/v1/turns/{matchId}/discard`
  - **Section (Panel de Jugadores y Chat)** – Panel lateral derecho
    - **MatchPlayersList (Lista de Jugadores)** – Muestra la lista de jugadores en la partida  
      Se destaca el turno actual con un temporizador.
    - **Chat** – Muestra el chat de la partida  
      Componente que permite a los jugadores comunicarse durante la partida.

### RANKING GLOBAL
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/GLOBALRANKING.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **Ranking** – Componente que muestra el ranking global de jugadores
  - **CardTitle (Ranking Global)** – Título que muestra "Ranking Global"
  - **Order (Opciones de Orden)** – Contenedor para las opciones de orden del ranking
    - **Victories (Victorias)** – Selección para ordenar por número de victorias
    - **Points (Puntos)** – Selección para ordenar por puntos
  - **PlayerList** – Contenedor para la lista de jugadores
    - **Players** – Elemento individual que muestra un jugador en el ranking

### AMIGOS
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/FRIENDS.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **Friends** – Componente que gestiona las solicitudes, amigos y las invitaciones a partidas
  - **Section (Enviar nueva solicitud)** – Formulario para enviar solicitudes de amistad  
    Tiene un campo para poner el nombre del receptor y un botón para enviar la solicitud.
    - **button (Enviar)** – Botón para enviar la solicitud  
      **Endpoint**: `POST /api/v1/friendships/request?receiverUsername={receiverUsername}`
  - **Section (Solicitudes de amistad pendientes)** – Lista de solicitudes de amistad recibidas  
    Aparece de quién es la solicitud y las opciones de rechazar o aceptar.
    - **button (Aceptar)** – Botón para aceptar la solicitud  
      **Endpoint**: `POST /api/v1/friendships/accept?senderId={senderId}`
    - **button (Rechazar)** – Botón para rechazar la solicitud  
      **Endpoint**: `DELETE /api/v1/friendships/delete?friendId={friendId}`
  - **Section (Mis Amigos)** – Lista de amigos actuales  
    Aparece el nombre de cada amigo y la opción de eliminarlo.
    - **button (Eliminar)** – Botón para eliminar al amigo  
      **Endpoint**: `DELETE /api/v1/friendships/delete?friendId={friendId}`
  - **Section (Invitaciones a partidas)** – Lista de invitaciones a partidas recibidas  
    Aparece el nombre del remitente de la invitación y las opciones para aceptar o rechazar.
    - **button (Aceptar)** – Botón para aceptar la invitación  
      **Endpoint**: `POST /api/v1/game-invitation/accept/{matchId}/{senderName}`
    - **button (Rechazar)** – Botón para rechazar la invitación  
      **Endpoint**: `POST /api/v1/game-invitation/reject/{matchId}`

### PERFIL
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/PROFILE.png)

#### Árbol de Componentes: Profile
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **Profile** – Componente de perfil del usuario
  - **Section (Perfil del Usuario)** – Muestra la información del perfil y la opción de editar los datos
    - **Button (Editar Perfil)** – Botón para alternar entre edición y visualización
  - **Section (Logros)** – Lista de logros desbloqueados
    - **ListGroup** – Contiene:
      - La imagen del logro con un borde según la dificultad de obtención.
      - Un botón para ver la descripción del logro.
  - **Section (Estadísticas)** – Muestra las diferentes estadísticas del usuario
  - **Section (Partidas)** – Lista de partidas del usuario
    - **CreatedMatches (Filtro de Partidas Creadas)** – Switch para mostrar solo partidas creadas por el usuario
    - **FinishedMatches (Lista de Partidas)** – Lista de partidas finalizadas con detalles de cada una como: ID, fechas, creador, etc.

### LISTADO DE LOGROS
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/ACHIEVEMENTLIST.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **LogrosListAdmin** – Componente que muestra la lista de logros en la interfaz administrativa
  - **AchievementTable** – Tabla que muestra la lista de logros con información de descripción, condición y valor requerido
    - **Achievement** – Elemento individual con:
      - Descripción
      - Condición
      - Valor Requerido
      - Acciones disponibles:
        - **Button (Edit)** – Redirige a la página de edición de un logro
        - **Button (Delete)** – Elimina el logro seleccionado  
          **Endpoint**: `DELETE /api/v1/achievements/{id}`
  - **Pagination** – Controles de paginación con botones para ir a la página anterior y a la siguiente
  - **Button (Agregar Logro)** – Botón para agregar un nuevo logro que redirige a la página de añadir logros

### EDITAR Y CREAR LOGROS
![image](https://github.com/gii-is-DP1/DP1-2024-2025--l2-05/blob/main/docs/components/ACHIEVEMENTEDIT.png)

#### Árbol de Componentes
**App – Componente principal de la aplicación**
- **NavBar** – Barra de navegación superior
- **LogrosEditAdmin** – Componente para crear o editar logros en la interfaz administrativa
  - **Form** – Formulario para crear o editar un logro  
    Contiene todos los campos que se pueden editar o añadir al crear un logro
    - **Button (Guardar)** – Botón para enviar los datos del formulario  
      **Endpoints**:  
      - `PUT /api/v1/achievements/{id}` (editar un logro existente)  
      - `POST /api/v1/achievements` (crear un nuevo logro)
    - **Button (Cancelar)** – Botón para cancelar la acción y volver a la lista de logros


## Documentación de las APIs

La documentación de las APIs está disponible en los siguientes formatos:

1. **Formato PDF**  
   Puedes consultar la documentación completa en formato PDF desde el siguiente enlace:  
   [Documentación Generada (PDF)](../../api/documentacion_generada.pdf)

2. **Formato Interactivo a través de Swagger UI**  
   También puedes explorar la documentación de forma interactiva ejecutando la aplicación y accediendo a Swagger UI:  
   [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Patrones de diseño y arquitectónicos aplicados

### Patrón: Prototipo

_Tipo_: De Diseño

_Contexto de Aplicación_

El patrón prototipo se utilizó en la aplicación para gestionar el uso de un mazo universal que actúa como plantilla para cada partida. Este mazo contiene todas las cartas iniciales del juego y se utiliza como referencia para generar mazos independientes para cada sesión de juego. Para lograr esta independencia y evitar conflictos al compartir referencias entre cartas, se implementó una lógica de clonación profunda.

Así, al crear una partida se va a realizar una clonación superficial inicial de cada una de sus cartas (a excepción de los modificadores de cada una), para posteriormente clonar los modificadores profundamente y asignarlos a las cartas superficialmente clonadas. Esto se encuente en deckService (cliente del patrón), más concretamente en el método 'getShuffledDeckForMatch()'.

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

### Decisión 5: Orden de ejecución de los modificadores (R5,R6,R7)

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

Inicialmente, no existía ninguna forma de diferenciar entre los tipos de modificadores, sea BONUS, PENALTY, PENALTY_AND_BLANK, BLANK, CLEAR, STATE, ALLHAND o NECROMANCER. Esta falta de clasificación hacía que los efectos de modificadores como clear no pudieran aplicarse correctamente, ya que no se podía determinar si un modificador era una bonificación, una penalización u otro tipo de efecto.

#### Alternativas de solución evaluadas:

_Alternativa 6.a_: Mantener la implementación sin distinguir los tipos de modificadores.

_Ventajas:_

• Código menos complejo.

_Inconvenientes:_

• No permite aplicar los modificadores de tipo clear adecuadamente.

_Alternativa 6.b_: Añadir un atributo modType a la entidad Mod para clasificar los modificadores según lo mencionado.

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

### Decisión 8: Modificación del Target para Modificadores de Cartas de Tipo ARMA ("Buque de guerra" y "Dirigible de guerra")

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

Se decidió optar por la alternativa 8.b. Esta solución va a facilitar en gran parte el código para manejar la aplicación de modificadores tipo clear, permitiendo ahorrar tiempo y complicaciones que puedan suceder.

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

### Decisión 12: Robar carta divido en dos métodos (R2)

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

### Decisión 13: Gestión del tiempo de turnos (R9)

#### Descripción del problema:

En un principio, hemos tenido problemas sobre como afrontar la lógica de tiempo en nuestro juego

#### Alternativas de solución evaluadas:

_Alternativa 13.a_: Utilizar un ScheduledExecutorService que cada segundo reste al contador del turno (atributo en backend), de forma que al completar las acciones se pase al siguiente turno y si éste no se completa se cancelen las acciones y se pase al siguiente.

_Ventajas:_

• Centralizamos toda la lógica de juego en backend.

• Fácil sincronización frontend - backend gracias a lo anterior.

_Inconvenientes:_

• Puede generar problemas para gestionar múltiples partidas simultáneamente.

• Hace que el código sea más complejo de leer.

_Alternativa 13.b_: En el backend no llevar nada relacionado con el tiempo de turno, estableciendo un contador en el frontend que, pasado el tiempo, llamará al backend para cancelar el turno del jugador.

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

### Decisión 17: Implementación de una ronda de turnos especiales para efectos dinámicos y automáticos (R3,R4)

#### Descripción del problema:

En el diseño inicial del sistema de modificadores, existía una ambigüedad sobre cuándo y cómo aplicar los efectos automáticos y dinámicos de las cartas. Esto generaba problemas de sincronización y flujo, ya que algunos efectos requerían decisiones del jugador (dinámicos), mientras que otros podían aplicarse automáticamente (automáticos). No existía un consenso sobre si manejar ambos tipos de efectos en el mismo flujo de turnos o separarlos en momentos distintos.

#### Alternativas de solución evaluadas:

_Alternativa 17.a_: Aplicar modificadores automáticos y dinámicos al final del turno estándar.

_Ventajas:_

• A simple vista, parece lo más simple debido a la aplicación de modificadores en el presente sin dejarlos en "en cola".

_Inconvenientes:_

• Riesgo de errores en la ejecución de modificadores.

• Mayor carga al sistema al tener que aplicar modificadores reiteradamente.

_Alternativa 17.b_: Implementar una ronda especial de turnos antes de finalizar la partida para manejar los efectos dinámicos y automáticos.

_Ventajas:_

• Permite a los jugadores realizar todas las decisiones dinámicas necesarias en una ronda específica (special turn).

• Los efectos automáticos se aplican de forma ordenada al finalizar las decisiones dinámicas, asegurando consistencia.

• Claridad para los jugadores.

_Inconvenientes:_

• Requiere una implementación adicional para manejar la lógica de la ronda especial.

_Justificación de la solución adoptada_

Se decidió adoptar la alternativa 17.b, implementando una ronda especial de turnos (“special turn”) que permite a los jugadores realizar decisiones dinámicas antes de aplicar los efectos automáticos de las cartas. Esta solución ofrece un balance óptimo entre claridad, consistencia y escalabilidad, ya que asegura que cada tipo de modificador se maneje en su contexto adecuado.

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

### Decisión 19: Estrategias para el comienzo de una partida (R1,R19)

#### Descripción del problema:

Al implementar el inicio de una partida, surgieron varias decisiones relacionadas con cómo manejar el mazo de cartas, el reparto inicial de manos y la asignación del primer turno. Era necesario determinar el orden en que estas operaciones se llevarían a cabo, así como garantizar que las mecánicas iniciales respetaran las reglas del juego, como que el jugador del primer turno solo pueda robar del mazo y no del área de descarte.

#### Alternativas de solución evaluadas:

_Alternativa 19.a_: Barajar el mazo universal después de repartir las manos, estableciendo siempre un primer jugador fijo.

_Ventajas:_

• Implementación inicial sencilla.

• Los jugadores siempre recibirían las primeras cartas del mazo universal en el orden predefinido.

_Inconvenientes:_

• La distribución inicial de cartas no sería completamente aleatoria, ya que las primeras cartas del mazo serían siempre las mismas.

• Partidas menos dinámicas.

_Alternativa 19.b_: arajar el mazo universal antes de repartir las manos y asignar las cartas desde la cima del mazo, seleccionando al primer jugador de forma aleatoria y asignando el siguiente turno de forma circular.

_Ventajas:_

• Distribución inicial de cartas completamente aleatoria, generando partidas únicas desde el inicio.

• Partidas más dinámicas y entretenidas.

_Inconvenientes:_

• Introduce una ligera carga de procesamiento.

_Justificación de la solución adoptada_

Se decidió implementar la alternativa 19.b, ya que si bien es algo más complejo de implementar, permite que los jugadores tengan una experienca mucho más fluida y entretenida. La alternativa para restringir las acciones del primer turno es la misma en ambas, simplemente validar al robar que solo se pueda realizar del mazo.

## Refactorizaciones aplicadas

### Refactorización 1: Refactorización general del módulo obligatorio

En las entidades, servicios y controladores principales de la aplicación se ha buscado realizar una refactorización general que promueva correctamente la separación de repsonsabilidades, reduciendo la complejidad y la legibilidad del código. Sirva de ejemplo el método 'createMatch()' de MatchService. Así, hemos buscado la creación de excepciones personalizadas, la separación de validaciones de la lógica fundamental de código, el encapsulamiento, la documentación, etc.

#### Estado inicial del código (se muestra servicio y controlador unificados para simplificar)

```Java
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
```

#### Estado del código refactorizado

```Java
     /**
     * Crea una nueva partida con el usuario actual como creador.
     *
     * @param matchName Nombre de la partida a crear.
     * @return La partida recién creada (DTO) con estado HTTP 201.
     */
    @Operation(summary = "Crear nueva partida",
               description = "Crea una partida con el usuario actual como CREADOR.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Partida creada con éxito"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la petición", 
                     content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", 
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "El usuario tiene un jugador asociado que está en partida", 
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Mazo universal no encontrado", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Error al crear la partida", 
                     content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<MatchDTO> createMatch(@RequestParam("matchName") String matchName) {

        User currentUser = userService.findCurrentUser();

        Match match = matchService.createMatch(currentUser, matchName);

        return new ResponseEntity<>(new MatchDTO(match), HttpStatus.CREATED);

    }

     /**
     * Crea una nueva partida con el usuario como creador (estableciendo su jugador
     * oportuno).
     * 
     * @param currentUser Usuario que crea la partida.
     * @param matchName   Nombre de la partida.
     * @return La partida creada.
     */
    @Transactional
    public Match createMatch(User currentUser, String matchName) {

        // 1. Validar restricciones
        validateCreateMatch(currentUser);

        // 2. Crear el perfil de creador
        Player creator = playerService.createPlayerForUser(currentUser.getId(), PlayerType.CREADOR);

        // 3. Crear el mazo para la partida
        Deck deckForMatch = deckService.getShuffledDeckForMatch();

        // 4. Crear la partida
        Match match = new MatchBuilder()
                .setName(matchName)
                .setDeck(deckForMatch)
                .setDiscard(new Discard())
                .setPlayers(List.of(creator))
                .build();

        // 5. Guardar la partida
        return matchRepository.save(match);
    }

     /**
     * Valida las condiciones necesarias para crear una partida.
     * 
     * @param currentUser Usuario que intenta crear la partida.
     */
    public void validateCreateMatch(User currentUser) {
        ensureUserNotInMatch(currentUser);
    }

     /**
     * Verifica que el usuario no esté ya participando en otra partida.
     * 
     * @param currentUser Usuario a validar.
     * @throws PlayerAlreadyInMatchException si el usuario ya está en una partida.
     */
    public void ensureUserNotInMatch(User currentUser) {
        if (currentUser.isPlaying()) {
            throw new PlayerAlreadyInMatchException("The current user has an associated player who is in game.");
        }
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

• Se consigue una API mucho más documentada y legible para usuarios externos.

### Refactorización 2: Clonación del mazo universal

En esta refactorización se mejoró la forma en que las cartas se clonan en el método getShuffledDeck. Antes, las cartas eran clonadas superficialmente, lo que podía generar problemas al compartir referencias entre cartas originales y clonadas. Ahora, se realiza una clonación profunda que incluye la estructura completa de modificadores, garantizando la independencia de las cartas clonadas y donde se aplica el patrón prototipo.

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
    /**
     * Obtiene el mazo universal (ID=1) y crea una copia profunda barajada
     * para usarla en una partida nueva.
     * 
     * @return Deck completamente clonado y barajado.
     */
    @Transactional(readOnly = true)
    public Deck getShuffledDeckForMatch() {
        // Asumimos que el universalDeck está en la BD con ID=1
        Deck universalDeck = deckRepository.findById(1).orElseThrow(() -> new ResourceNotFoundException("Deck", "id", 1));
        
        // Clonar y barajar
        return deepCloneAndShuffle(universalDeck);
    }

    /**
     * Crea una copia profunda de un deck, baraja sus cartas y devuelve
     * un nuevo Deck completamente independiente.
     *
     * @param originalDeck Deck original (el universal)
     * @return Deck clonado y barajado
     */
    @Transactional
    public Deck deepCloneAndShuffle(Deck originalDeck) {
        // Clonar profundamente las cartas
        List<Card> clonedCards = deepCloneCards(originalDeck.getCards());
        // Mezclar las cartas clonadas
        Collections.shuffle(clonedCards);
        // Crear un nuevo Deck con esas cartas (también rellena 'initialCards')
        return new Deck(clonedCards);
    }

    /**
     * Realiza un clonado profundo de las cartas (y sus modificadores), utilizando el patrón prototipo.
     * 
     * @param originalCards Lista de cartas originales
     * @return Lista nueva con cartas y mods completamente independientes
     */
    private List<Card> deepCloneCards(List<Card> originalCards) {
        Map<Card, Card> cardCloneMap = new HashMap<>();
        
        // 1. Clon superficial de cada carta usando getClone()
        for (Card original : originalCards) {
            cardCloneMap.put(original, original.getClone());
        }

        // 2. Clonar y reubicar Mods
        for (Card original : originalCards) {
            Card cloned = cardCloneMap.get(original);

            List<Mod> clonedMods = new ArrayList<>();
            for (Mod originalMod : original.getMods()) {
                Mod clonedMod = originalMod.getClone();
                clonedMod.setOriginCard(cloned);

                // Ajustar el target para las cartas clonadas
                if (originalMod.getTarget() != null && !originalMod.getTarget().isEmpty()) {
                    List<Card> clonedTargets = originalMod.getTarget().stream()
                            .map(cardCloneMap::get)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    clonedMod.setTarget(clonedTargets);
                } else {
                    clonedMod.setTarget(new ArrayList<>());
                }

                clonedMods.add(clonedMod);
            }

            cloned.setMods(clonedMods);
        }

        return new ArrayList<>(cardCloneMap.values());
    }
```

#### Problema que nos hizo realizar la refactorización

El problema con la clonación superficial previa era que las cartas originales y las cartas clonadas compartían referencias internas. Esto generaba inconsistencias, especialmente al interactuar con los modificadores, ya que las modificaciones realizadas en una instancia afectaban indirectamente a la otra.

#### Ventajas que presenta la nueva versión del código respecto de la versión original

• Independencia total entre cartas originales y clonadas.

• Garantiza que los mazos utilizados durante la partida no se vean afectados por modificaciones no intencionadas en el mazo universal.

### Refactorización 3: Unificación de varios atibutos booleanos en Turn a un enum de estado

En esta refactorización se mejoró la forma en que la entidad Turn y el servicio TurnService manejan la información de si el jugador ha robado carta del mazo, de la zona de descartes o ha descartado. Antes, en la clase Turn existían varios campos booleanos (hasDrawnCardDeck, hasDrawnCardDiscard, hasDiscarded) que complicaban el control de estado del turno e incrementaban la probabilidad de inconsistencias. Ahora, se unifica esa lógica en un solo campo drawSource de tipo enum, y se emplean métodos más expresivos para el resto de estados.

#### Estado inicial del código

```Java
@Entity
@Getter
@Setter
public class Turn extends BaseEntity {

    @ManyToOne
    private Player player;

    // Varios booleans para controlar de dónde se robó y si se descartó
    private boolean hasDrawnCardDeck = false;
    private boolean hasDrawnCardDiscard = false;
    private boolean hasDiscarded = false;

    @ManyToOne(cascade = CascadeType.ALL)
    private Card cardDrawn;

    private Integer turnCount = 0;

    public boolean canDrawCard() {
        return !hasDrawnCardDeck && !hasDrawnCardDiscard;
    }

    public boolean hasActionsToRevert() {
        return hasDrawnCardDeck && !hasDiscarded;
    }
    // ... (resto de métodos)
}

// Así, en algunos métodos de TurnService

@Service
public class TurnService {

    // ...resto del código

    @Transactional
    public void cancelTurn(User user, Integer matchId) {

        Match match = findMatchById(matchId);

        Turn currentTurn = match.getCurrentTurn();

        // Comprobamos que el usuario es el que tiene el jugador asociado al turno

        if(!currentTurn.getPlayer().getUser().getUsername().equals(user.getUsername())) {
            throw new TurnStatesException("The user must be the one with the current turn!");
        }

        // Comprobamos que la partida esté en curso

        if(!match.isInProgress()) {
            throw new MatchStatesException("Cannot end a match that is not in progress.");
        }

        if (currentTurn.canDrawCard() || !currentTurn.isHasDiscarded()) {
            cancelTurnActions(match,currentTurn);
        }
    
    }

    @Transactional
    public void cancelTurnActions(Match match, Turn currentTurn) {
    
        Player currentPlayer = currentTurn.getPlayer();
    
        // Revertir la carta robada si existe
        if (currentTurn.getCardDrawn() != null) {
            Card cardDrawn = currentTurn.getCardDrawn();
            
            // Remover la carta de la mano del jugador
            currentPlayer.getPlayerHand().remove(cardDrawn);
    
            // Verificar de dónde provino la carta y devolverla
            if (currentTurn.isHasDrawnCardDeck()) {
                match.getDeck().getCards().add(0, cardDrawn); // Volver a agregar la carta al principio del mazo
            } else if (currentTurn.isHasDrawnCardDiscard()) {
                match.getDiscard().getCards().add(cardDrawn); // Volver a agregar la carta a la pila de descarte
            }
    
            // Limpiar la carta robada del turno
            currentTurn.setCardDrawn(null);
        }
    
        // Restablecer el estado del turno
        currentTurn.setHasDrawnCardDeck(false);
        currentTurn.setHasDrawnCardDiscard(false);
        currentTurn.setHasDiscarded(false);
    
        // Guardar cambios en el jugador y la partida
        saveMatch(match);  // Guardar cambios de la partida
    
        // Notificar a los jugadores que el turno ha sido cancelado
        String message = "El turno de " + currentPlayer.getUser().getUsername() + " ha sido cancelado.";
        try {
            MatchWebSocket.sendMessage(String.valueOf(match.getId()), message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        nextTurn(match);
        
    }

    // ...resto del código

}
```

#### Estado del código refactorizado

```Java
@Entity
@Getter
@Setter
@BuilderPattern.Product
public class Turn extends BaseEntity {

    @ManyToOne
    private Player player;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private DrawEnum drawSource = DrawEnum.NONE; // NONE, DECK, DISCARD

    @ManyToOne(cascade = CascadeType.ALL)
    private Card cardDrawn;

    private Integer turnCount = 0;
    private boolean discarded = false;

    // Constructor sin parámetros (Hibernate)
    public Turn() {}

    public Turn(Player player, int turnCount) {
        this.player = player;
        this.turnCount = turnCount;
    }

    public boolean canDrawCard() {
        return drawSource == DrawEnum.NONE;
    }

    public boolean hasDrawnCard() {
        return drawSource != DrawEnum.NONE;
    }

    public boolean hasDiscarded() {
        return discarded;
    }

    public boolean hasActionsToRevert() {
        return drawSource != DrawEnum.NONE && !discarded;
    }

    public void drawFromDeck(Card card) {
        this.cardDrawn = card;
        this.drawSource = DrawEnum.DECK;
    }

    public void drawFromDiscard(Card card) {
        this.cardDrawn = card;
        this.drawSource = DrawEnum.DISCARD;
    }
}

// Enum que sustituye los booleanos
public enum DrawEnum {
    NONE,
    DECK,
    DISCARD
}

// Y ahora, en TurnService

@Service
public class TurnService {

    // ...resto del código

    /**
     * Cancela el turno actual de la partida, revirtiendo acciones si es necesario 
     * y pasando al siguiente turno.
     *
     * @param user  Usuario que solicita la cancelación del turno.
     * @param match Partida en curso.
     */    
    @Transactional
    public void cancelTurn(User user, Match match) {

        // 1. Obtener el turno actual de la partida
        Turn currentTurn = match.getCurrentTurn();

        // 2. Validar condiciones: el usuario debe tener el turno y la partida debe estar en curso
        validateConditionsForCancel(currentTurn, match, user);

        // 3. Si el turno tiene acciones que revertir (se robó carta y no se descartó),
        //    las revertimos
        if (currentTurn.hasActionsToRevert()) {
            cancelTurnActions(match, currentTurn);
        }

        // 4. Pasamos al siguiente turno
        nextTurn(match);
    }

    /**
     * Revierten las acciones realizadas en un turno cuando es cancelado.
     *
     * @param match        Partida en curso.
     * @param currentTurn  Turno a cancelar.
     */
    public void cancelTurnActions(Match match, Turn currentTurn) {

        // 1. Jugador y carta robada
        Player player = currentTurn.getPlayer();
        Card cardDrawn = currentTurn.getCardDrawn();

        // 2. Eliminar la carta de la mano del jugador
        player.getPlayerHand().remove(cardDrawn);

        // 3. Revertir: si vino del mazo, la devolvemos a la parte superior;
        //    si vino del descarte, la regresamos allí.
        revertActions(match, cardDrawn, currentTurn);
    }

    /**
     * Devuelve una carta a su origen (mazo o pila de descartes) tras cancelar acciones.
     *
     * @param match        Partida en curso.
     * @param cardDrawn    Carta robada a devolver.
     * @param currentTurn  Turno actual con las acciones revertidas.
     */
    public void revertActions(Match match, Card cardDrawn, Turn currentTurn) {

        // Si la carta se robó del mazo:
        if (currentTurn.getDrawSource() == DrawEnum.DECK) {
            match.getDeck().addCardDeck(cardDrawn);
        } else {
            // Si se robó de la pila de descartes:
            match.getDiscard().addCardDiscard(cardDrawn);
        }
    }

    // ...resto del código

}
```

#### Problema que nos hizo realizar la refactorización

El uso de varios booleanos complicaba el control de estado del turno. Podía llevar a inconsistencias en la entidad Turn, como tener hasDrawnCardDeck = true y hasDrawnCardDiscard = true simultáneamente si no se desactivaban correctamente. Además, el uso excesivo de flags reducía la legibilidad de la lógica en TurnService.

#### Ventajas que presenta la nueva versión del código respecto de la versión original

• Menos booleanos, estado más claro.

• Legibilidad y mantenimiento.

• Código más expresivo en TurnService.

