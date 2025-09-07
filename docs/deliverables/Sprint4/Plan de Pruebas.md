# Plan de Pruebas

## 1. Introducción

Este documento describe el plan de pruebas para el proyecto Fantasy Realms desarrollado en el marco de la asignatura **Diseño y Pruebas 1** por el grupo **L2-05**. El objetivo del plan de pruebas es garantizar que el software desarrollado cumple con los requisitos especificados en las historias de usuario y que se han realizado las pruebas necesarias para validar su funcionamiento.

## 2. Alcance

El alcance de este plan de pruebas incluye:

- Pruebas unitarias.
  - Pruebas unitarias de backend incluyendo pruebas servicios o repositorios
  - Pruebas unitarias de frontend: pruebas de las funciones javascript creadas en frontend.
  - Pruebas unitarias de interfaz de usuario. Usan la interfaz de  usuario de nuestros componentes frontend.
- Pruebas de integración.  En nuestro caso principalmente son pruebas de controladores.

## 3. Estrategia de Pruebas

### 3.1 Tipos de Pruebas

#### 3.1.1 Pruebas Unitarias
Las pruebas unitarias se realizarán para verificar el correcto funcionamiento de los componentes individuales del software. Se utilizarán herramientas de automatización de pruebas como **JUnit** en background y .

#### 3.1.2 Pruebas de Integración
Las pruebas de integración se enfocarán en evaluar la interacción entre los distintos módulos o componentes del sistema, nosotros las realizaremos a nivel de API, probando nuestros controladores Spring.

## 4. Herramientas y Entorno de Pruebas

### 4.1 Herramientas
- **Maven**: Gestión de dependencias y ejecución de las pruebas.
- **JUnit**: Framework de pruebas unitarias.
- **Jacoco**: Generación de informes de cobertura de código.
- **Jest**: Framework para pruebas unitarias en javascript.
- **React-test**: Liberaría para la creación de pruebas unitarias de componentes React.

### 4.2 Entorno de Pruebas
Las pruebas se ejecutarán en el entorno de desarrollo y, eventualmente, en el entorno de pruebas del servidor de integración continua.

## 5. Planificación de Pruebas

### 5.1 Cobertura de Pruebas

El informe de cobertura de pruebas se puede consultar [aquí](https://gii-is-dp1.github.io/DP1-2024-2025--l2-05/target/site/jacoco/index.html).








### 5.2 Matriz de Trazabilidad

| Historia de Usuario | Prueba | Descripción | Estado |Tipo |
|---------------------|--------|-------------|--------|--------|
| HU-01: Registro en el sistema | [UTB-1:UserControllerTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/user/UserControllerTests.java) | Verifica que como usuario el sistema me permita registrarme para poder acceder a las funcionalidades del juego. | Implementada | Unitaria en backend |
| HU-03: Hacer login en el sistema | [UTB-1:UserControllerTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/user/UserControllerTests.java) | Verifica que como usuario el sistema me permita identificarme para poder crear y unirme a partidas. | Implementada |Unitaria en backend |
| HU-04: Listado de usuarios | [UTB-2:UserServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/user/UserServiceTests.java) | Verifica que como administrador pueda visualizar el listado de usuarios registrados para poder gestionar las cuentas en el sistema. | Implementada |Unitaria en backend |
| HU-05: CRUD sobre usuarios | [UTB-2:UserServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/user/UserServiceTests.java) | Verificar que como administrador pueda realizar operaciones CRUD sobre los usuarios para gestionar sus cuentas adecuadamente. | Implementada |Unitaria en backend |
| HU-07: Edición del perfil personal | [UTB-2:UserServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/user/UserServiceTests.java) | Verificar que como usuario puedo editar mi perfil personal para actualizar mi información en el sistema. | Implementada |Unitaria en backend |
| HU-09: Crear partida en el sistema | [UTB-3:MatchControllerTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/match/MatchControllerTests.java) | Verificar que como jugador puedo crear una partida para poder competir con otros jugadores. | Implementada |Unitaria en backend y End-to-End.|
| HU-10: Ver listado de partidas | [UTB-4:MatchServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/match/MatchServiceTests.java) | Verificar que como jugador puedo ver un listado de todas las partidas en el sistema para conocer las disponibles. | Implementada |Unitaria en backend |
| HU-13: Unirse a una partida | [UTB-3:MatchControllerTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/match/MatchControllerTests.java) | Verificar que como jugador puedo unirme a partidas en búsqueda de jugadores para poder participar en una partida existente. | Implementada |Unitaria en backend y End-to-End.|
| HU-14: Eliminar partida | [UTB-3:MatchControllerTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/match/MatchControllerTests.java) | Verificar que como creador de una partida quiero eliminarla para cancelar la partida en cualquier momento si aún no ha comenzado. | Implementada |Unitaria en backend |
| HU-15: Iniciar partida | [UTB-3:MatchControllerTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/match/MatchControllerTests.java) | Verificar que como creador de una partida quiero iniciarla cuando el número de jugadores sea igual o mayor a tres para comenzar el juego. | Implementada |Unitaria en backend |
| HU-16: Recibir mano inicial  | [UTB-4:MatchServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/match/MatchServiceTests.java) |  Verificar que como recibo una mano de siete cartas al inicio de la partida para comenzar en igualdad de condiciones | Implementada |Unitaria en backend |
| HU-19: Robar carta | [UTB-5:PlayerServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/player/PLayerServiceTests.java) | Verificar que como jugador quiero robar una carta cuando sea mi turno para mejorar mi mano | Implementada |Unitaria en backend |
| HU-20: Descartar carta | [UTB-5:PlayerServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/player/PLayerServiceTests.java) | Verificar que como jugador quiero descartar una carta después de robar para mantener el límite de cartas en mi mano. | Implementada |Unitaria en backend |
| HU-21: Cambiar de turno  | [UTB-5:PlayerServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/player/PLayerServiceTests.java) | Verificar que como jugador quiero que el sistema gestione el cambio de turno automáticamente para mantener un flujo de juego ordenado. | Implementada |Unitaria en backend |
| HU-22: Finalizar partida al alcanzar el límite de cartas  | [UTB-4:MatchServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/match/MatchServiceTests.java) |  Verificar que cuando un jugador coloque la décima carta en la zona de descarte, el sistema finalizará la partida, sin dar paso al turno del siguiente jugador. | Implementada |Unitaria en backend |
| HU-23: Jugar cartas especiales   | [UTB-6:ModServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/mod/ModServiceTests.java) | Verificar que como jugador quiero poder jugar cartas especiales al finalizar la partida para modificar mi estrategia antes del recuento de puntos. | Implementada |Unitaria en backend |
| HU-24: Ver ranking y desglose de puntos   | [UTB-3:PlayerServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/player/PlayerServiceTests.java) | Verificar que como jugador quiero ver el ranking y desglose de puntos al finalizar una partida para conocer el resultado y analizar mi rendimiento. | Implementada |Unitaria en backend |
| HU-25: Historial de partidas | [UTB-4:MatchServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/match/MatchServiceTests.java) | Verificar que como usuario puedo visualizar mi historial de partidas para revisar el resultado de mis partidas anteriores. | Implementada |Unitaria en backend |
| HU-26: Visualizar estadísticas   | [UTB-7:StatisticServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/modules/statistic/StatisticServiceTests.java) | Verificar que como usuario puedo visualizar mis estadísticas y las globales del juego para poder ver un resumen de todo mi progreso. | Implementada | Unitaria en backend |
| HU-27: Visualizar ranking global   | [UTB-7:StatisticServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/modules/statistic/StatisticServiceTests.java) | Verificar que como usuario puedo visualizar el ranking global del juego y poder ordenarlo tanto por victorias como por puntos para poder ver quienes son los mejores jugadores. | Implementada | Unitaria en backend |
| HU-28: Visualizar logros   | [UTB-8:AchievementServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/achievements/AchievementServiceTests.java) | Verificar que como usuario puedo visualizar mis logros para conocer las metas alcanzadas y mis progresos en el sistema. | Implementada |Unitaria en backend |
| HU-29: Crear logros Administrador   | [UTB-8:AchievementServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/achievements/AchievementServiceTests.java) | Verificar que como administrador puedo crear nuevos logros alcanzables por los usuarios en el juego.. | Implementada |Unitaria en backend |
| HU-30: Visualización y CRUD de logros Administrador   | [UTB-8:AchievementServiceTests](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/achievements/AchievementServiceTests.java) | Verificar que como administrador puedo ver los logros disponibles en el juego, así como poder eliminar o editar los que ya hay. | Implementada |Unitaria en backend |
| HU-31: Gestión de amigos   | [UTB-9:FriendshipServiceTests.java](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/modules/social/user/friendship/FriendshipServiceTests.java) | Verificar que como usuario puedo gestionar mis amigos, incluyendo esto poder enviar solicitudes de amistad, aceptar solicitudes entrantes o eliminar amigos actuales y ser notificado de cuando alguno de mis amigos está en línea, para poder conocer y socializar con mis posibles contrincantes. | Implementada |Unitaria en backend |
| HU-32: Aceptar invitaciones a partidas   | [UTB-9:FriendshipServiceTests.java](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/modules/social/user/friendship/FriendshipServiceTests.java) | Verificar que en la pantalla de "Amigos" el usuario tendrá la opción de enviar solicitudes de amistad a través del nombre de usuario, aceptar solicitudes pendientes mediante un botón y eliminar amigos actuales de igual manera. | Implementada |Unitaria en backend |
| HU-33: Invitar a amigos a una partida   | [UTB-9:FriendshipServiceTests.java](../../../src/test/java/es/us/dp1/l2_05_24_25/fantasy_realms/modules/social/user/friendship/FriendshipServiceTests.java) | Verificar que como usuario puedo invitar a mis amigos a una partida, tanto en modo jugador como en modo espectador. | Implementada |Unitaria en backend |


### 5.3 Matriz de Trazabilidad entre Pruebas e Historias de Usuario

| Prueba                      | HU-01 | HU-03 | HU-04 | HU-05 | HU-07 | HU-09 | HU-10 | HU-12 | HU-13 | HU-14 | HU-15 |
|-----------------------------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|      
| UTB-1:UserControllerTests   |   X   |   X   |       |       |       |       |       |       |       |       |       |
| UTB-2:UserServiceTests      |       |       |   X   |   X   |   X   |       |       |       |       |       |       |
| UTB-3:MatchControllerTests  |       |       |       |       |       |   X   |       |   X   |   X   |   X   |   X   |
| UTB-4:MatchServiceTests     |       |       |       |       |       |       |   X   |       |       |       |       |


| Prueba                       | HU-16 | HU-19 | HU-20 | HU-21 | HU-22 | HU-23 | HU-24 | HU-25 | HU-26 | HU-27 | HU-28 |
|------------------------------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| UTB-4:MatchServiceTests      |   X   |       |       |       |   X   |       |       |   X   |       |       |       |  
| UTB-5:PlayerServiceTests     |       |   X   |   X   |   X   |       |       |   X   |       |       |       |       |
| UTB-6:ModServiceTests        |       |       |       |       |       |   X   |       |       |       |       |       |
| UTB-7:StatisticServiceTests  |       |       |       |       |       |       |       |       |   X   |   X   |       |
| UTB-8:AchievementServiceTests|       |       |       |       |       |       |       |       |       |       |   X   |


| Prueba                       | HU-29 | HU-30 | HU-31 | HU-32 | HU-33 |
|------------------------------|-------|-------|-------|-------|-------|
| UTB-8:AchievementServiceTests|   X   |   X   |       |       |       |
| UTB-9:FriendshipServiceTests |       |       |   X   |   X   |   X   |


## 6. Criterios de Aceptación

- Todas las pruebas unitarias deben pasar con éxito antes de la entrega final del proyecto.
- La cobertura de código debe ser al menos del 70%.
- No debe haber fallos críticos en las pruebas de integración y en la funcionalidad.

## 7. Conclusión

Este plan de pruebas establece la estructura y los criterios para asegurar la calidad del software desarrollado. Es responsabilidad del equipo de desarrollo y pruebas seguir este plan para garantizar la entrega de un producto funcional y libre de errores.

