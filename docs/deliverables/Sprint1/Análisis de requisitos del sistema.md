# Documento de análisis de requisitos del sistema

## Introducción

El proyecto consiste en la creación de una **aplicación de juego de cartas multijugador**, basada en el juego de mesa 'Fantasy Realms'. Permite a los jugadores interactuar en tiempo real y competir entre sí utilizando un conjunto de cartas con diferentes efectos.

### Funcionalidad General

El objetivo del juego es **acumular la mayor cantidad de puntos** posible al jugar cartas estratégicamente y aprovechar las sinergias entre ellas. Cada carta tiene un valor base y puede verse modificada por efectos especiales, que dependen del resto de cartas que el jugador posea en su mano.

### Objetivos del Proyecto

- Desarrollar una plataforma para que los jugadores puedan **crear y unirse a partidas** fácilmente.
- Proporcionar una experiencia de usuario fluida y entretenida, con un **sistema de puntuación claro y transparente**.
- **Fomentar la rejugabilidad** mediante dinámicas de juego que cambian constantemente, según las cartas jugadas y las interacciones entre los jugadores, así como un sistema de logros.

### Número de Jugadores

El juego está diseñado para un **mínimo de 3 jugadores** y un **máximo de 6 jugadores** por partida.

### Desarrollo de una Partida

Una partida comienza con cada jugador **recibiendo una mano de 7 cartas** y se define un orden aleatorio para los turnos. En el turno de cada jugador, este robará una carta bien del mazo o de la zona de cartas descartadas previamente por otros jugadores. Posteriormente, deberá descartar una de sus cartas a la zona de descarte para quedarse con  **7**.

- **Duración**: Las partidas suelen tener una duración promedio de **10 a 15 minutos**, aunque pueden prolongarse dependiendo de las interacciones entre jugadores.
- **Fin del juego**:  La partida **finalizará** cuando haya **10** cartas en el área de descarte. Finalmente, se hace el conteo de puntos de cada jugador.

### Criterios para Determinar el Ganador

El vencedor se determina **calculando los puntos de cada jugador** al final de la partida. Cada carta tiene un **valor base**, pero este valor puede verse afectado por los efectos del resto de cartas de la mano del jugador, aunque no por las cartas del resto de jugadores.

- **Sistema de Puntuación**: Las cartas cuentan con modificadores que pueden aumentar o disminuir la puntuación final. El jugador con la mayor cantidad de puntos al finalizar la partida es declarado el ganador.

### Conclusión

En resumen, este proyecto tiene como objetivo crear una experiencia de juego divertida, competitiva y accesible para todos los jugadores, con un enfoque en la estrategia y la interacción social.

_

[Enlace al vídeo de explicación de las reglas del juego / partida jugada por el grupo](https://www.youtube.com/watch?v=Hymg4SimYrw / https://youtu.be/nJwS8-HFg4o)

## Tipos de Usuarios / Roles

_Administrador_: Persona encargada de la gestión general de la aplicación. Tiene permisos especiales para supervisar y mantener el sistema.

_Usuario_: Persona con cuenta registrada en la aplicación, que se ha logado correctamente.

_Jugador_: Usuario que ha accedido a una partida la cual ha sido iniciada.

_Espectador_: Usuario que ha accedido a una partida para verla pero no la juega.



## Historias de Usuario

### HE1 – Módulos Obligatorios

### HE1.1 – Gestión de usuarios y autenticación

#### HU-001: Registro en el sistema ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **usuario** quiero que el sistema **me permita registrarme** para poder **acceder a las funcionalidades del juego.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/c6ebc339-2584-47e4-9e1a-7a0c80196275)

|
| **Interacciones**: Al acceder a la página de registro e introducir los datos necesarios, (nombre de usuario, contraseña) el sistema creará un nuevo perfil para el jugador y confirmará el registro. |

#### HU-002: Navegación con Menu Desplegable ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como usuario, quiero tener acceso a un botón de menú desplegable en la barra superior para navegar fácilmente entre las diferentes secciones de la aplicación. |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/e1af1246-8c41-46d3-bbc9-8a8a74b0d39f)

| **Interacciones**: El menú desplegable estará ubicado en la esquina superior izquierda de la pantalla. Al hacer click en él, se le mostrarán al usuario los enlaces de navegación para las distintas pantallas de la aplicación. Si el usuario está logado, se mostrará su avatar a la derecha del menú |

#### HU-003: Hacer Login en el sistema ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **usuario** quiero que el sistema **me permita identificarme** para poder **crear y unirme a partidas.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/1838c3ed-6df2-4e6a-8eb2-734246685869)

| **Interacciones**: Usuario con credenciales registradas en el sistema introduce sus credenciales en el formulario de login, el sistema verificará las credenciales y, si son correctas, iniciará sesión para el usuario. |

#### HU-004: Listado de usuarios ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **administrador** quiero **visualizar el listado de usuarios registrados** para poder **gestionar las cuentas en el sistema.** |
|-----|
| **Mockups**: (Ver HU-005) |
| **Interacciones**: Al acceder al apartado de administración de usuarios, el sistema mostrará una lista de todos los usuarios registrados con sus datos relevantes. |

#### HU-005: CRUD sobre usuarios ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **administrador** quiero poder **realizar operaciones CRUD sobre los usuarios** para **gestionar sus cuentas adecuadamente.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/0e1023ed-c35f-4add-82f2-210661e3bc08)

|
| **Interacciones**: El administrador podrá consultar los usuarios existentes, modificar datos de usuarios y eliminarlos según sea necesario. |

#### HU-006: Hacer Logout en el sistema ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **usuario** quiero poder **cerrar sesión en el sistema** para **desconectar mi cuenta de manera segura.** |
|-----|
| **Mockups**: (Ver HU-007) |
| **Interacciones**: Dado un jugador logado que pulse el botón de logout, el sistema aceptará la solicitud y cerrará la sesión. |

#### HU-007: Edición del perfil personal ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **usuario** quiero poder **editar mi perfil personal** para **actualizar mi información en el sistema.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/1525a23c-0803-4f17-aed3-f1a32dfd8ae3)

|
| **Interacciones**: Al acceder a la configuración del perfil, el jugador podrá editar datos personales como nombre de usuario, correo electrónico, avatar, etc., y guardar los cambios. |

#### HU-008: Ver las reglas del juego ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **usuario** quiero que el sistema **me permita consultar las reglas del juego** para poder **conocer las normas y mecánicas antes de jugar.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/dc3ba6ce-6d51-4aca-85ef-1ed7f6d1877e)

|
| **Interacciones**: Al acceder a la opción de "Reglas", el sistema mostrará las reglas del juego para que el usuario pueda leerlas antes de empezar a jugar. |

---

### HE1.2 – Gestión y administración de partidas

#### HU-009: Crear partida en el sistema ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **jugador** quiero **crear una partida** para poder **competir con otros jugadores.** |
|-----|
| **Mockups**: (Ver HU-010) |
| **Interacciones**: Estando autenticado, el jugador podrá crear una partida única mediante el botón “Crear Partida”. Si el jugador no está asignado como creador de ninguna otra partida, el sistema creará la partida y lo añadirá como el primer jugador. |

#### HU-010: Ver listado de partidas ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **jugador** quiero **ver un listado de todas las partidas** en el sistema para **conocer las disponibles.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/113c2fe7-2470-4b6b-9d0c-5f65a7690a59)

|
| **Interacciones**: Al acceder al apartado de partidas, el sistema mostrará un listado completo de partidas en estado de búsqueda de jugadores. |

#### HU-011: Ver listado de partidas Administrador ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **administrador** quiero **ver un listado de todas las partidas** en el sistema **tanto las que están en fase de búsqueda de jugadores como las que están en juego o terminadas.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/450a4251-3f18-41e4-9f75-9d59676b0f30)

|
| **Interacciones**: Al acceder al apartado de partidas, el sistema mostrará un listado completo de partidas en estado de búsqueda de jugadores. |

#### HU-012: Ver integrantes de partida ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **jugador** quiero **ver los integrantes actuales de una partida** para **conocer a mis futuros rivales.** |
|-----|
| **Mockups**: (Ver HU-012) |
| **Interacciones**: Al pulsar en una partida dentro del listado, el sistema mostrará el listado de jugadores que ya están unidos a la partida seleccionada. |

#### HU-013: Unirse a partida ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **jugador** quiero **unirme a partidas en búsqueda de jugadores** para poder **participar en una partida existente.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/2d6acfa1-092f-4c89-9fed-9ac409925e90)

|
| **Interacciones**: Si la partida tiene cinco o menos jugadores, al presionar el botón de unirse, el sistema añadirá al jugador como parte de la partida. |

#### HU-014: Eliminar una partida Administrador ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **administrador** quiero **poder eliminar una partida** siempre que **se encuentre en fase de búsqueda de jugadores.** |
|-----|

#### HU-015: Iniciar partida ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **creador de una partida** quiero **iniciarla** cuando el número de jugadores sea igual o mayor a tres para **comenzar el juego.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/739fc519-765c-499d-9ffc-4ce99d92a5e5)

|
| **Interacciones**: Si hay al menos tres jugadores en la partida, el jugador creador podrá pulsar el botón de iniciar, y el sistema comenzará la partida. |

---

### HE1.3 – Desarrollar la mecánica de juego

#### HU-016: Recibir mano inicial ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **jugador** quiero **recibir una mano de siete cartas** al inicio de la partida para **comenzar en igualdad de condiciones.** |
|-----|
| **Mockups**: (Ver HU-018) |
| **Interacciones**: Al iniciar la partida, el sistema repartirá siete cartas aleatorias a cada jugador para que puedan comenzar a jugar. Este reparto solo ocurre una vez durante la partida y ocurrirá automáticamente después de que el creador la lance. |

#### HU-017: Visualizar mano ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **jugador** quiero **ver mi mano de cartas durante la partida** para **planificar mi estrategia.** |
|-----|
| **Mockups**: (Ver HU-018) |
| **Interacciones**: Durante la partida, el sistema mostrará la mano actual del jugador, permitiéndole ver sus cartas en todo momento. |

#### HU-018: Conocer tiempo de turno ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**:  Como **jugador** quiero **ser consciente del tiempo restante para mi turno**, para **efectuar mi jugada**.|
|-----|
| **Mockups**:  (Ver HU-019) |
| **Interacciones**:  El jugador tendrá disponible en el lado derecho de su pantalla el listado de jugadores de la partida junto a un temporizador en caso de que ese jugador esté jugando su turno. |

#### HU-019: Robar carta ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **jugador** quiero **robar una carta** cuando sea mi turno para **mejorar mi mano.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/6fa9b9ed-a2ed-4b1b-9d86-772783cf0cee)

|
| **Interacciones**: En el turno del jugador, al realizar la petición de robar, el sistema le permitirá escoger una carta de las colocadas boca arriba en la zona de descarte o la carta en la parte superior del mazo. |

#### HU-020: Descartar carta ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **jugador** quiero **descartar una carta después de robar** para **mantener el límite de cartas en mi mano.** |
|-----|
| **Mockups**: (Ver HU-21) |
| **Interacciones**: Tras robar, el jugador debe descartar una carta de su mano, y el sistema moverá la carta seleccionada a la zona de descarte boca arriba. Esto finalizará su turno. |

#### HU-021: Cambiar de turno ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **jugador** quiero que el sistema **gestione el cambio de turno automáticamente** para **mantener un flujo de juego ordenado.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/4662fefd-9e1f-49e1-b3ee-3bc5781573b9)

|
| **Interacciones**: Tras completar su turno (robar y descartar), el turno pasará al siguiente jugador. El sistema indicará de quién es el turno en todo momento. |

#### HU-022: Finalizar partida al alcanzar el límite de cartas ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **jugador** quiero que la partida **finalice automáticamente** para **respetar las normas.** |
|-----|
| **Mockups**: (Ver HU-023) |
| **Interacciones**: Cuando un jugador coloque la décima carta en la zona de descarte, el sistema finalizará la partida, sin dar paso al turno del siguiente jugador. |

#### HU-023: Jugar cartas especiales ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **jugador** quiero poder **jugar cartas especiales** al finalizar la partida para **modificar mi estrategia antes del recuento de puntos.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/858fba27-f3ef-4daa-b8d5-11e195c28185)

|
| **Interacciones**: Cuando un jugador coloque la décima carta en la zona de descarte, el sistema debe permitir a los jugadores con cartas especiales (como el nigromante o comodines) ejecutar sus efectos antes del recuento de puntos. En caso de que varios jugadores tengan estas cartas, se respetará el orden de turnos definido anteriormente. |

#### HU-024: Ver ranking y desglose de puntos ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **jugador** quiero **ver el ranking y desglose de puntos** al finalizar una partida para **conocer el resultado y analizar mi rendimiento.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/95ecba67-2a83-4bd0-89d8-ba88a83b1ca9)

|
| **Interacciones**: Al finalizar la partida, el sistema mostrará la clasificación de los jugadores y un desglose de los puntos obtenidos por cada uno, mostrando el total de puntos otorgados a cada jugador por cada una de sus cartas. |

---

### HE1.4 – Historial y progresos

#### HU-025: Historial de partidas ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **usuario** quiero **visualizar mi historial de partidas** para **revisar el resultado de mis partidas anteriores.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/7536a4b6-77eb-4cf6-b9ad-af496210fb44)

|
| **Interacciones**: Al acceder a la sección de historial, el sistema mostrará un listado de las partidas en las que el jugador ha participado, con información relevante como la fecha, el id, el nombre de la partida y su creador. |

### HE2 – Módulos Opcionales

### HE2.1 – Módulo Estadística

#### HU-027: Visualizar Estadísticas ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **usuario** quiero **visualizar mis estadísticas** y **las globales del juego** para poder ver un resumen de todo mi progreso. |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/50ba1224-b508-42ed-b7d1-95ad2850c9ff)

|
| **Interacciones**: Al acceder a la sección de estadística de la página del perfil, el sistema mostrará todas las estadísticas calculadas. |

#### HU-028: Visualizar Ranking Global ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **usuario** quiero **visualizar el ranking global** del juego y **poder ordenarlo tanto por victorias como por puntos** para poder ver quienes son los mejores jugadores. |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/30558368-d27c-4ebd-9122-d60d9ee5f8d2)

|
| **Interacciones**: Al acceder a la sección de estadística de la página del perfil, el sistema mostrará todas las estadísticas calculadas. |

#### HU-029: Visualizar logros ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **usuario** quiero **visualizar mis logros** para **conocer las metas alcanzadas y mis progresos en el sistema.** |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/467ad143-b3c4-4257-9162-7c84af4ba0cd)

|
| **Interacciones**: Al acceder a la sección de logros, el sistema mostrará un listado de los logros completados por el jugador. |

#### HU-030: Crear Logros Administrador ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **administrador** quiero **poder crear nuevos logros** alcanzables por los usuarios en el juego. |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/a8a2302a-e457-4292-8703-bc858fb239b1)

|
| **Interacciones**: Al pulsar el boton de "Crear Logro" de la página de logros, el sistema deberá mostrar un formulario para crear el nuevo logro y guardarlo.|

#### HU-031: Visualización y CRUD de logros Administrador ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **administrador** quiero **poder ver los logros** disponibles en el juego, así como **poder eliminar o editar** los que ya hay. |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/58bf2563-59b9-4e80-ba34-c402b7e51e1a)

|
| **Interacciones**: Al pulsar en los botones de "Eliminar" o "Editar" el sistema deberá completar la acción correspondiente a botón seleccionado.|

### HE2.2 – Módulo Social

#### HU-032: Gestión de amigos ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **usuario** quiero **poder gestionar mis amigos**, incluyendo esto poder **enviar solicitudes de amistad**, **aceptar solicitudes entrantes o eliminar amigos actuales** y **ser notificado de cuando alguno de mis amigos está en línea**, para poder conocer y socializar con mis posibles contrincantes. |
|-----|
| **Mockups**: (Ver HU-034) |
| **Interacciones**: En la pantalla de "Amigos" el usuario tendrá la opción de enviar solicitudes de amistad a través del nombre de usuario, aceptar solicitudes pendientes mediante un botón y eliminar amigos actuales de igual manera.|

#### HU-033: Aceptar invitaciones a partidas ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **usuario** quiero **poder aceptar las invitaciones a partidas que reciba**. |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/f75f64d4-0696-46aa-bed2-e3f03ca59f65)

|
| **Interacciones**: En la pantalla de "Amigos" si el usuario tiene invitaciones a partidas pendientes, las podrá aceptar mediante un botón que lo lleve a la partida aceptada.|

#### HU-034: Invitar a amigos a una partida ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **usuario** quiero **poder invitar a mis amigos a una partida**, tanto en modo jugador como en modo espectador. |
|-----|
| **Mockups**: (Ver HU-035) |
| **Interacciones**: En en looby de la partida, si el usuario autenticado tiene amigos en linea, le aparecerá un botón para cada uno de sus amigos en línea que le permita invitarlos.|

#### HU-035: Modo espectador para amigos ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **usuario** quiero **poder unirme a una partida en modo espectador** siempre y cuando todos los participantes en la partida sean mis amigos. |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/15c7f64c-73f8-41f7-a7cf-44a74d82016b)

|
| **Interacciones**: En el looby de partida, una vez que el usuario se une a la partida le aparecerá un botón para cambiar su rol entre jugador y espectador.|

#### HU-036: Chat en partida ([Enlace a la Issue asociada a la historia de usuario]())
| **Descripción**: Como **usuario** quiero **poder enviar mensajes a los jugadores con los que estoy jugando la partida** mientras que esta está en curso. |
|-----|
| **Mockups**: 

![image](https://github.com/user-attachments/assets/71178989-b3e1-436f-b099-a2a282e54383)

|
| **Interacciones**: En la pantalla de partida, aparecerá un chat a la derecha que permita la comunicación entre todos los jugadores.|

## Diagrama conceptual del sistema

![image](https://github.com/user-attachments/assets/aad1d89f-4ded-4776-96d3-48cd152a4780)

## Reglas de Negocio
 
### R1 – Inicio del juego:

El sistema debe asegurarse de que cada jugador recibe exactamente 7 cartas y de seleccionar aleatoriamente el orden de turnos.


### R2 – Orden de acciones:

El sistema debe permitir a los jugadores robar una carta de dos opciones: del mazo o del área de descarte, y debe obligar al jugador a descartar posteriormente.


### R3 – Fin de la partida:

El sistema debe detectar automáticamente cuando hay diez cartas en el área de descarte y finalizar la partida. Si hay un empate, el sistema debe comparar las fuerzas base de las manos de los jugadores.


### R4 – Cálculo de puntos:

El sistema debe calcular correctamente las bonificaciones y penalizaciones de las cartas basándose únicamente en las cartas que posee el jugador en su mano.


### R5 – Contradicciones entre cartas:

Si dos cartas generan efectos contradictorios, el orden de resolución debe seguir este patrón:
  1) Decidir los efectos del Doppelgänger, Mirage, y Shapeshifter, en ese orden.
  2) Aplicar los efectos del Book of Changes.
  3) Seguir cualquier instrucción que implique la eliminación de una penalización o parte de una penalización.
  4) Aplicar todas las penalizaciones restantes, comenzando con las cartas que no han sido anuladas por otras cartas.


### R6 – Doppelgänger y Basilisco:

Si el Doppelgänger copia el Basilisco y no hay cartas que eliminen las penalizaciones, ambas cartas serán anuladas.



### R7 – Uso del Libro de Cambios:

El Libro de Cambios transforma el tipo de una carta antes de que se apliquen cualquier bonificación o penalización. Sin embargo, no cambia las penalizaciones, bonificaciones, ni la fuerza base de la carta.
El nombre de la carta no cambia, por lo que cualquier bonificación que haga referencia a esa carta permanece activa.



### R8 – Interacción entre Exploradores e Incendio:

La carta Exploradores solo elimina la palabra "Ejército" de la sección de penalización de las cartas.
Si el término "Ejército" no aparece en la carta (como es el caso de Incendio), los Exploradores no protegen a los ejércitos de ser anulados por el Incendio.


### R9 - Límite de tiempo para turnos:

Los jugadores tendrán un límite de tiempo de 1 minuto para realizar sus acciones. Pasado este minuto, su turno finalizará automáticamente.


### R10 - Una partida por jugador:

Un jugador solo puede tener asociada una partida en curso o estar en un lobby de partida.


### R11 - Crear una partida:

Solo un jugador con rol creador puede crear una partida.


### R12 - Iniciar una partida: 

Solo un jugador con rol creador puede iniciar una partida.


### R13 - Eliminar partida:

Solo un jugador con rol creador puede eliminar una partida.


### R14 - Un solo creador por partida:

Solo puede haber un jugador con rol creador en una partida. El resto de jugadores tendrán el rol participante.


### R15 - Número de jugadores:

Una partida tendrá un mínimo de 3 jugadores y un máximo de 6, incluyendo el creador.





