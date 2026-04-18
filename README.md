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

##  Herramientas Utilizadas

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

## Estructura del Proyecto 
El código está organizado siguiendo principios de modularidad y separación de responsabilidades:

* **`com.registrocivil.logica`**: Contiene el motor del sistema.
    * `GestionSistema`: Clase central que coordina la lógica de negocio y la persistencia.
    * `Persona` / `Region`: Modelos de dominio que representan las entidades básicas.
    * `Exceptions`: Clases personalizadas para el manejo de errores lógicos (`RutInvalidoException`, `FechaInvalidaException`).
* **`com.registrocivil.console`**: Capa de presentación para el usuario.
    * `MenuConsola`: Menú principal que hereda de una clase base común.
    * `SubMenuCiudadanos` / `SubMenuRegiones`: Submenús especializados para cada área.
    * `Gestores`: Clases que procesan la entrada de datos del usuario y llaman a la lógica.
* **`com.registrocivil.ventanas`** (Vista - GUI): Implementación de la interfaz gráfica utilizando Java Swing. Cuenta con un diseño estandarizado mediante una paleta de colores unificada para una experiencia de usuario fluida. 
* **`com.registrocivil.main`**: Punto de entrada de la aplicación, interactua con el usuario preguntando si desea utilizar el programa en modo Consola o en modo Ventana.

## Funcionalidades
### Gestión de Ciudadanos (Módulo Central)
El sistema permite un control total sobre el ciclo de vida del ciudadano a través de su panel de control:
* **Inscripción General:** Registro manual de ciudadanos existentes con validación de RUT y fecha.
* **Inscripción de Nacimiento:** Generación automática de un nuevo RUT y vinculación directa con objetos Persona de los progenitores.
* **Matrimonios:** Lógica que vincula a dos ciudadanos, validando que ambos sean solteros o viudos, actualizando el estado civil de ambos de forma concurrente.
* **Registro de Defunciones:** Cambia el estado vital a "Fallecido". El sistema detecta automáticamente al cónyuge (si existe) y actualiza su estado civil a "Viudo/a".
* **Búsqueda Global:** Localización instantánea de cualquier ciudadano por su RUT a nivel nacional.
* **Edición y Eliminación:** Permite corregir datos o eliminar registros de manera segura.
* **Emisión de Certificados:** Generación de documentos con formato oficial (Nacimiento/Antecedentes y Matrimonio).

###  Gestión Regiones 
El sistema permite una gestión de regiones donde es posible observar la organización demográfica de los ciudadanos dentro del país:
* **Mostrar todas las regiones y su información**: Despliega un resumen completo de todas las zonas geográficas, indicando métricas clave de cada una (como la cantidad total de ciudadanos inscritos, distinguiendo entre población viva y fallecida).
* **Consultar listado de Ciudadanos por región**: Permite al usuario consultar una región específica y obtener el registro detallado de todas las personas que han sido inscritas en dicha zona.
* **Ver listado de matrimonios de una región**: Filtra la base de datos para mostrar de manera estructurada únicamente a aquellas parejas que han contraído matrimonio dentro de una zona geográfica seleccionada.
* **Ver estadísticas generales**: Genera un reporte demográfico global a nivel país, consolidando y sumando los datos de todas las regiones para otorgar una vista panorámica y rápida del estado completo del sistema.

## Guía de Formatos de Ingreso (Modo Consola)

Para asegurar una experiencia fluida y evitar bloqueos de validación durante el uso de la aplicación en consola, es obligatorio respetar los siguientes formatos de entrada:

* **Formato de RUT**: El identificador debe ingresarse **sin puntos y con guion**. 
  * *Correcto*: `12345678-9` o `12345678-k` (o `K` mayúscula).
  * *Incorrecto*: `12.345.678-9` o `123456789`.
  
* **Formato de Fechas**: Las fechas se solicitan paso a paso para reducir errores.
  * **Día**: Valores numéricos entre `1` y `31` (dependiendo del mes).
  * **Mes**: Valores numéricos entre `1` y `12`.
  * **Año**: Números de cuatro dígitos lógicos. El sistema está protegido para no aceptar años de nacimiento en el futuro (ej. > 2026).
  * Inconsistencias en estos rangos activarán la `FechaInvalidaException`.

*  **Formato de Sexo**: El sistema es flexible y cuenta con validación que ignora mayúsculas y minúsculas donde se permite el ingreso del usuario de la siguiente forma. 
    * *Correctos*: `MASCULINO`, `masculino`, `m`. De igual forma para Femenino: `FEMENINO`, `femenino`, `F`.
   * *Incorrectos*: `Hombre`, `Mujer`, `H`, `1`, espacios en blanco o números.
* **Uso de Menús Numéricos**: Para navegar por el sistema, ingrese únicamente el **número** de la opción deseada y presione la tecla `Enter`. No ingrese letras ni símbolos.


## Posibles Mejoras y Trabajo Futuro 

Aunque el sistema es completamente funcional y cumple de manera con  los requerimientos princiaples, se han identificado las siguientes áreas de oportunidad para futuras versiones:

1. **Verificación Algorítmica del RUT**: Actualmente, el sistema valida que el RUT cumpla con la estructura correcta visualmente mediante Regex. Una mejora excelente sería implementar el cálculo matemático del "Módulo 11" para asegurar que el dígito verificador corresponda criptográficamente al número ingresado.
2. **Exportación Física de Certificados**: Escalar el módulo actual de "Emisión de Certificados" (que actualmente se imprime en la interfaz/consola) integrando alguna libreria para generar documentos descargables y oficiales en formato `.pdf`.
3. **Implementación de Herencia en el Modelo de Dominio**: Expandir la clase base `Persona` creando subclases específicas que hereden de ella. Por ejemplo, categorizar a los ciudadanos por ocupación/estatus (creando clases como `Estudiante`, `Profesional`, `Jubilado`). Esto permitiría añadir atributos únicos a cada sector de la población y aplicar **Polimorfismo** avanzado en las lógicas del sistema.

##  Requisitos del Sistema y Ejecución

### Requisitos

* **Java Development Kit (JDK) 8** o superior (recomendado 8 o 11).
* **Apache Maven** para la gestión de dependencias y construcción del proyecto.
* **SQLite JDBC Driver** (gestionado automáticamente por Maven).
* Un IDE como **NetBeans** o **IntelliJ IDEA** (opcional).

### Ejecución 
Opción 2: Ejecución mediante Terminal 
1. Abre tu terminal de comandos y ubícate en la carpeta raíz del proyecto (donde se encuentra el archivo `pom.xml`).
2. Limpia y compila el proyecto. Este comando descargará automáticamente el driver de SQLite y preparará los archivos:
   ```bash
   mvn clean compile
3. Ejecua la clase principal del sistema.
   ```bash
      mvn exec:java -Dexec.mainClass="com.registrocivil.main.Main"
Opción 2: 
1. Abre NetBeans y carga el proyecto mediante `File > Open Project`.
2. En la pestaña de proyectos, haz clic derecho sobre el nombre del proyecto y selecciona "Clean and Build". Espera a que la consola inferior indique `BUILD SUCCESS`.
3. Navega hasta `Source Packages` -> `com.registrocivil.main`.
5. Haz clic derecho sobre el archivo `Main.java` y selecciona "Run File".


## Autores del programa 
* Manuel Moreno Galleguilos.
* Nicolas Echeverría Okroj 
* Hans Paz Bonilla. 
