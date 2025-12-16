# Sistema-Oficina-Mecanica
Sistema de gestión de oficina mecánica en Java 17 – Proyecto personal
# Sistema de Gestión de Oficina Mecánica (Taller Mecánico)

## Descripción
Sistema de gestión de oficina mecánica desarrollado en **Java 17**, aplicando POO avanzada, excepciones personalizadas y buenas prácticas.

## Arquitectura
- Clases de dominio: `Coche`, `Matricula`, `Parte`
- Servicios: `GestionCoche`, `GestionParte`, `GestionTaller`
- Interfaz: Consola interactiva
- Excepciones personalizadas

## Funcionalidades
- CRUD de vehículos y servicios
- Validación de datos
- Manejo robusto de errores

## Tecnologías
- Java 17: Records, Pattern Matching, Sealed Classes
- POO avanzada
- Patrones: Service Layer, DTO, Exception Handling
- Clean Code, SOLID

## Ejecución
```bash
git clone [URL-del-repositorio]
javac -d out src/com/barasiqueira/**/*.java
java -cp out com.barasiqueira.App.AppTallerMecanico
