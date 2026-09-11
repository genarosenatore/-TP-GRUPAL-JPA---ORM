# TP Grupal JPA - ORM - Hibernate

Implementacion del Trabajo Practico Grupal de Mapeo Objeto Relacional utilizando JPA, Hibernate y MySQL.

## Requisitos

- Java 17 o superior
- Maven
- MySQL 8

## Configuracion de la base de datos

El archivo `src/main/resources/META-INF/persistence.xml` esta configurado por defecto con:

- Base: `facturacion_jpa`
- Usuario: `root`
- Password: `root`

Cambiar usuario y password segun la instalacion local de MySQL.

La URL incluye `createDatabaseIfNotExist=true`, por lo que Hibernate puede crear la base si el usuario tiene permisos.

## Ejecutar

1. Abrir/importar el proyecto como proyecto Maven.
2. Verificar las credenciales en `persistence.xml`.
3. Ejecutar `com.facturacion.app.Main`.
4. Hibernate crea/actualiza las tablas mediante `hibernate.hbm2ddl.auto=update`.
5. El `Main` realiza un solo `em.persist(facturaVenta)`; los detalles y objetos relacionados necesarios se persisten por cascada.

## Reparto del trabajo

Consultar `REPARTO_5_INTEGRANTES.md`.
