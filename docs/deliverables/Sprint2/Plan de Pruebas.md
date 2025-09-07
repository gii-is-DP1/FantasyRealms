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

El informe de cobertura de pruebas se puede consultar [aquí](
https://html-preview.github.io/?url=https://raw.githubusercontent.com/gii-is-DP1/group-project-seed/main/target/site/jacoco/index.html).

### 5.2 Matriz de Trazabilidad

| Historia de Usuario | Prueba | Descripción | Estado |Tipo |
|---------------------|--------|-------------|--------|--------|
| HU-01: Registro en el sistema | [UTB-1:UserControllerTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/user/UserControllerTests.java) | Verifica que como usuario el sistema me permita registrarme para poder acceder a las funcionalidades del juego. | Implementada | Unitaria en backend |
| HU-03: Hacer login en el sistema | [UTB-1:UserControllerTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/user/UserControllerTests.java) | Verifica que como usuario el sistema me permita identificarme para poder crear y unirme a partidas. | Implementada |Unitaria en backend |
| HU-04: Listado de usuarios | [UTB-2:UserServiceTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/user/UserServiceTests.java) | Verifica que como administrador pueda visualizar el listado de usuarios registrados para poder gestionar las cuentas en el sistema. | Implementada |Unitaria en backend |
| HU-05: CRUD sobre usuarios | [UTB-2:UserServiceTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/user/UserServiceTests.java) | Verificar que como administrador pueda realizar operaciones CRUD sobre los usuarios para gestionar sus cuentas adecuadamente. | Implementada |Unitaria en backend |
| HU-07: Edición del perfil personal | [UTB-2:UserServiceTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/user/UserServiceTests.java) | Verificar que como usuario puedo editar mi perfil personal para actualizar mi información en el sistema. | Implementada |Unitaria en backend |
| HU-09: Crear partida en el sistema | [UTB-3:MatchControllerTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/match/MatchControllerTests.java) | Verificar que como jugador puedo crear una partida para poder competir con otros jugadores. | Implementada |Unitaria en backend |
| HU-10: Ver listado de partidas | [UTB-4:MatchServiceTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/match/MatchServiceTests.java) | Verificar que como jugador puedo ver un listado de todas las partidas en el sistema para conocer las disponibles. | A implementar |Unitaria en backend |
| HU-12: Unirse a una partida | [UTB-3:MatchControllerTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/match/MatchControllerTests.java) | Verificar que como jugador puedo unirme a partidas en búsqueda de jugadores para poder participar en una partida existente. | Implementada |Unitaria en backend |
| HU-13: Abandonar partida | [UTB-3:MatchControllerTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/match/MatchControllerTests.java) | Verificar que como jugador puedo abandonar la partida en la que estoy actualmente para salir del juego si no quiero continuar.| Implentada |Unitaria en backend |
| HU-14: Eliminar partida | [UTB-3:MatchControllerTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/match/MatchControllerTests.java) | Verificar que como creador de una partida quiero eliminarla para cancelar la partida en cualquier momento si aún no ha comenzado. | Implementada |Unitaria en backend |
| HU-15: Iniciar partida | [UTB-3:MatchControllerTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/match/MatchControllerTests.java) | Verificar que como creador de una partida quiero iniciarla cuando el número de jugadores sea igual o mayor a tres para comenzar el juego. | Implementada |Unitaria en backend |
| HU-19: Robar carta | [UTB-3:PlayerServiceTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/player/PLayerServiceTests.java) | Verificar que como jugador quiero robar una carta cuando sea mi turno para mejorar mi mano | Implementada |Unitaria en backend |
| HU-20: Descartar carta | [UTB-3:PlayerServiceTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/player/PLayerServiceTests.java) | Verificar que como juagdor quiero descartar una carta después de robar para mantener el límite de cartas en mi mano. | Implementada |Unitaria en backend |

### 5.3 Matriz de Trazabilidad entre Pruebas e Historias de Usuario

| Prueba                      | HU-01 | HU-03 | HU-04 | HU-05 | HU-07 | HU-09 | HU-10 | HU-12 | HU-13 | HU-14 | HU-15 |
|-----------------------------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|      
| UTB-1:UserControllerTests   |   X   |   X   |       |       |       |       |       |       |       |       |       |
| UTB-2:UserServiceTests      |       |       |   X   |   X   |   X   |       |       |       |       |       |       |
| UTB-3:MatchControllerTests  |       |       |       |       |       |   X   |       |   X   |   X   |   X   |   X   |
| UTB-4:MatchServiceTests     |       |       |       |       |       |       |   X   |       |       |       |       |


| Prueba                      | HU-19 | HU-20 | HU-XX | HU-XX | HU-XX | HU-XX | HU-XX | HU-XX | HU-XX 
|-----------------------------|-------|-------|-------|-------|-------|-------|-------|-------|------|      
| UTB-5:PlayerServiceTests     |   X   |   X   |       |       |       |       |       |       |       |       |       |

## 6. Criterios de Aceptación

- Todas las pruebas unitarias deben pasar con éxito antes de la entrega final del proyecto.
- La cobertura de código debe ser al menos del 70%.
- No debe haber fallos críticos en las pruebas de integración y en la funcionalidad.

## 7. Conclusión

Este plan de pruebas establece la estructura y los criterios para asegurar la calidad del software desarrollado. Es responsabilidad del equipo de desarrollo y pruebas seguir este plan para garantizar la entrega de un producto funcional y libre de errores.
