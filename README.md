# Sistema de Gestión de Registro Civil en Chile.

Este proyecto es una aplicación de escritorio desarrollada en **Java** diseñada para gestionar los trámites fundamentales de un Registro Civil, tales como el registro de nacimientos, matrimonios, defunciones y emisión de certificados (Nacimiento y Matrimonio). El sistema está organizado por zonas geográficas y sus respectivos ciudadanos inscritos y permite la persistencia de datos mediante una base de datos local.

## Caracteristicas Principales

* **Organización Geográfica**: El sistema maneja las 16 regiones de Chile, permitiendo la gestión descentralizada de ciudadanos.
* **Gestión de Ciudadanos (CRUD)**: Permite registrar, editar, buscar y eliminar ciudadanos con datos completos (RUT, Nombres, Sexo, Fecha de Nacimiento).
* **Lógica Avanzada**:
    * **Matrimonios**: Vincula a dos ciudadanos, valida su estado civil previo y actualiza sus registros de forma concurrente.
    * **Nacimientos**: Generación automática de RUT y vinculación con progenitores.
    * **Defunciones**: Manejo de estado vital y actualización de estado civil de cónyuges.
* **Doble Interfaz**: Al realizar la ejecución del programa se le pregunta a la usuario si desea uso de interfaz mediante **Consola (Texto)** o una interfaz gráfica de **Ventana**.

##  Tecnologías Utilizadas

* **Lenguaje**: Java 8 / 11.
* **Gestión de Dependencias**: Maven.
* **Base de Datos**: SQLite (mediante JDBC) para persistencia de datos.
* **Estructuras de Datos**: 
    * `HashMap` para el acceso rápido a regiones.
    * `ArrayList` anidado para el almacenamiento de ciudadanos por región.
    * `HashSet` para el filtrado de duplicados durante el guardado batch.

## Arquitectura del Proyecto

El código sigue principios de **Programación Orientada a Objetos (POO)**:
- **Encapsulamiento**: Clases de dominio (`Persona`, `Region`) con atributos privados y métodos getters/setters.
- **Persistencia Batch**: Carga de datos al iniciar y guardado automático al salir del sistema para asegurar la integridad de la información, a través de una base de datos externa SQLite. 

### Estructura del Proyecto 
El código está organizado siguiendo principios de modularidad y separación de responsabilidades:

* **`com.registrocivil.logica`**: Contiene el motor del sistema.
    * `GestionSistema`: Clase central que coordina la lógica de negocio y la persistencia.
    * `Persona` / `Region`: Modelos de dominio que representan las entidades básicas.
    * `Exceptions`: Clases personalizadas para el manejo de errores lógicos (`RutInvalidoException`, `FechaInvalidaException`).
* **`com.registrocivil.console`**: Capa de presentación para el usuario.
    * `MenuConsola`: Menú principal que hereda de una clase base común.
    * `SubMenuCiudadanos` / `SubMenuRegiones`: Submenús especializados para cada área.
    * `Gestores`: Clases que procesan la entrada de datos del usuario y llaman a la lógica.
* **`com.registrocivil.main`**: Punto de entrada de la aplicación.

##  Requisitos del Sistema y Ejecución

Para ejecutar este proyecto, necesitas tener instalado:

* **Java Development Kit (JDK) 8** o superior (recomendado 8 o 11).
* **Apache Maven** para la gestión de dependencias y construcción del proyecto.
* **SQLite JDBC Driver** (gestionado automáticamente por Maven).
* Un IDE como **NetBeans** o **IntelliJ IDEA** (opcional).

### Ejecución 

