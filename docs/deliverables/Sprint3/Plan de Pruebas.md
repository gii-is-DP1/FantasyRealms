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

![image](https://github.com/user-attachments/assets/5deee1ec-2755-48af-b620-776e3b7a8ab2)

![image](https://github.com/user-attachments/assets/d9ca4167-d8af-476d-ba23-b056a36583d2)

![image](https://github.com/user-attachments/assets/8443f681-c62a-4339-bdd7-229e175f6b10)

![image](https://github.com/user-attachments/assets/06e70a1e-3770-4314-8236-52aa8b067e7d)

![image](https://github.com/user-attachments/assets/fdc0edc8-dfe6-4e21-b749-1c0e9a19c353)

![image](https://github.com/user-attachments/assets/d710db34-c6e0-4e76-9fcf-4597888c4162)

![image](https://github.com/user-attachments/assets/c5d45ad6-d898-4184-afc5-bf9fc8b88e7a)

![image](https://github.com/user-attachments/assets/4de80518-d4f5-40f1-bb45-9093110b6351)

![image](https://github.com/user-attachments/assets/4a01c416-38c6-479e-bdff-4d87aa04fb04)

![image](https://github.com/user-attachments/assets/5849461d-6091-4c61-808e-a6244a334228)

![image](https://github.com/user-attachments/assets/c5d0f382-4a33-4d35-af1e-6374b8138630)

![image](https://github.com/user-attachments/assets/3d2c2952-76cd-4b69-aa61-8f6745d4e069)

![image](https://github.com/user-attachments/assets/b3313745-f656-4434-8e26-881696fcba97)

![image](https://github.com/user-attachments/assets/8dc6b935-9c1e-463a-b6fc-bafe0cc62528)

![image](https://github.com/user-attachments/assets/f9de3e8e-da4e-49ab-8805-feaeee465b9e)

![image](https://github.com/user-attachments/assets/e26ffb15-af80-409e-8d83-f3de98a3b0c5)

![image](https://github.com/user-attachments/assets/6f808df5-b1e9-461e-aaaf-be34f65901b5)

![image](https://github.com/user-attachments/assets/6032cb3b-1ea8-4486-9a46-7a74701b830e)

![image](https://github.com/user-attachments/assets/85a9d72c-5c6c-45bc-b0cf-0d4de2dea311)

![image](https://github.com/user-attachments/assets/1084d822-defa-49bb-ac47-458e19d365c3)

![image](https://github.com/user-attachments/assets/d6e56bbe-a640-4448-829a-5c1c832fd274)

![image](https://github.com/user-attachments/assets/c112e282-c7a8-44ec-83c1-44e7c0cf3a4b)

![image](https://github.com/user-attachments/assets/f57e5c3f-18c7-41f8-a2f3-fa544c396569)






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
| HU-13: Abandonar partida | [UTB-3:MatchControllerTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/match/MatchControllerTests.java) | Verificar que como jugador puedo abandonar la partida en la que estoy actualmente para salir del juego si no quiero continuar.| A implementar |Unitaria en backend |
| HU-14: Eliminar partida | [UTB-3:MatchControllerTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/match/MatchControllerTests.java) | Verificar que como creador de una partida quiero eliminarla para cancelar la partida en cualquier momento si aún no ha comenzado. | A implementar |Unitaria en backend |
| HU-15: Iniciar partida | [UTB-3:MatchControllerTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/match/MatchControllerTests.java) | Verificar que como creador de una partida quiero iniciarla cuando el número de jugadores sea igual o mayor a tres para comenzar el juego. | Implementada |Unitaria en backend |
| HU-16: Recibir mano inicial  | [UTB-4:MatchServiceTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/match/MatchServiceTests.java) |  Verificar que como recibo una mano de siete cartas al inicio de la partida para comenzar en igualdad de condiciones | Implementada |Unitaria en backend |
| HU-19: Robar carta | [UTB-5:PlayerServiceTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/player/PLayerServiceTests.java) | Verificar que como jugador quiero robar una carta cuando sea mi turno para mejorar mi mano | Implementada |Unitaria en backend |
| HU-20: Descartar carta | [UTB-5:PlayerServiceTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/player/PLayerServiceTests.java) | Verificar que como jugador quiero descartar una carta después de robar para mantener el límite de cartas en mi mano. | Implementada |Unitaria en backend |
| HU-21: Cambiar de turno  | [UTB-5:PlayerServiceTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/player/PLayerServiceTests.java) | Verificar que como jugador quiero que el sistema gestione el cambio de turno automáticamente para mantener un flujo de juego ordenado. | Implementada |Unitaria en backend |
| HU-22: Finalizar partida al alcanzar el límite de puntos  | [UTB-4:MatchServiceTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/match/MatchServiceTests.java) |  Verificar que la partida finalizará automáticamente para respetar las normas. | Implementada |Unitaria en backend |
| HU-23: Jugar cartas especiales   | [UTB-6:ModServiceTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/mod/ModServiceTests.java) | Verificar que como jugador quiero poder jugar cartas especiales al finalizar la partida para modificar mi estrategia antes del recuento de puntos. | Implementada |Unitaria en backend |
| HU-24: Ver ranking y desglose de puntos   | [UTB-3:PlayerServiceTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/player/PLayerServiceTests.java) | Verificar que como jugador quiero ver el ranking y desglose de puntos al finalizar una partida para conocer el resultado y analizar mi rendimiento. | Implementada |Unitaria en backend |
| HU-26: Visualizar logros   | [UTB-7:AchievementTests](./src/test/java/es/us/dp1/lx_xy_24_25/fantasy_realms/achievements/AchievementTests.java) | Verificar que como usuario puedo visualizar mis logros para conocer las metas alcanzadas y mis progresos en el sistema. | Implementada |Unitaria en backend |


### 5.3 Matriz de Trazabilidad entre Pruebas e Historias de Usuario

| Prueba                      | HU-01 | HU-03 | HU-04 | HU-05 | HU-07 | HU-09 | HU-10 | HU-12 | HU-13 | HU-14 | HU-15 |
|-----------------------------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|      
| UTB-1:UserControllerTests   |   X   |   X   |       |       |       |       |       |       |       |       |       |
| UTB-2:UserServiceTests      |       |       |   X   |   X   |   X   |       |       |       |       |       |       |
| UTB-3:MatchControllerTests  |       |       |       |       |       |   X   |       |   X   |   X   |   X   |   X   |
| UTB-4:MatchServiceTests     |       |       |       |       |       |       |   X   |       |       |       |       |


| Prueba                      | HU-16 | HU-19 | HU-20 | HU-21 | HU-22 | HU-23 | HU-24 | HU-26 |
|-----------------------------|-------|-------|-------|-------|-------|-------|-------|-------|
| UTB-4:MatchServiceTests     |   X   |       |       |       |   X   |       |       |       |
| UTB-5:PlayerServiceTests    |       |   X   |   X   |   X   |       |       |   X   |       |
| UTB-6:ModServiceTests       |       |       |       |       |       |   X   |       |       |
| UTB-7:AchievementTests      |       |       |       |       |       |       |       |   X   |

## 6. Criterios de Aceptación

- Todas las pruebas unitarias deben pasar con éxito antes de la entrega final del proyecto.
- La cobertura de código debe ser al menos del 70%.
- No debe haber fallos críticos en las pruebas de integración y en la funcionalidad.

## 7. Conclusión

Este plan de pruebas establece la estructura y los criterios para asegurar la calidad del software desarrollado. Es responsabilidad del equipo de desarrollo y pruebas seguir este plan para garantizar la entrega de un producto funcional y libre de errores.
