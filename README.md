# Gestor Personal — Agenda de Contactos, Notas y Recordatorios

> Aplicación de escritorio desarrollada en **Java Swing** como proyecto grupal de la asignatura **Programación Orientada a Objetos** (2.º semestre de Ingeniería de Software).

Proyecto **académico** cuyo objetivo fue aplicar, de forma práctica, los fundamentos de la programación orientada a objetos y el desarrollo de interfaces gráficas de escritorio: encapsulamiento, separación de responsabilidades, manejo de eventos y estructuración del código bajo el patrón arquitectónico **MVC** (Modelo – Vista – Controlador).

---

## Descripción

**Gestor Personal** permite administrar de forma sencilla tres tipos de información desde una única ventana con navegación por pestañas:

- **Contactos:** nombre, teléfono y correo electrónico.
- **Notas:** título y contenido, con marca de fecha automática.
- **Recordatorios:** asunto, fecha y hora, con estado de completado.

Toda la información se almacena **en memoria** durante la ejecución (no se persiste en disco ni base de datos), ya que el foco del proyecto era la lógica de negocio y la interfaz gráfica, no la persistencia.

---

## Características

### Panel Principal (Inicio)
- Tabla interactiva de recordatorios con casillas de verificación para marcar tareas completadas.
- Resumen en cascada de las **3 notas más recientes**.
- Diálogo modal para agregar recordatorios con **validación de fecha** (`AAAA-MM-DD`) y **hora** (formato 24 h `HH:MM`).

### Módulo de Contactos
- Registro, edición y eliminación de contactos (CRUD completo).
- Listado tabular no editable con selección de fila.
- **Validaciones de entrada:**
  - Nombre: solo letras y espacios.
  - Teléfono: exactamente 10 dígitos numéricos (filtrado en tiempo real).
  - Correo: estructura de email válida mediante expresión regular.
- Confirmación previa antes de eliminar.

### Módulo de Notas
- CRUD completo con edición en el mismo formulario.
- **Validaciones:** campos obligatorios, título entre 3 y 60 caracteres, contenido máximo de 1000 caracteres.
- Registro automático de la fecha de creación/modificación.
- **Sincronización en tiempo real** con el resumen del panel de inicio.

---

## Tecnologías y conceptos aplicados

| Área | Detalle |
|------|---------|
| Lenguaje | Java (JDK 17 o superior) |
| Interfaz gráfica | Java Swing / AWT |
| Arquitectura | Patrón MVC |
| Estructuras de datos | `ArrayList` |
| Fechas | API `java.time` (`LocalDate`, `DateTimeFormatter`) |
| Validación | Expresiones regulares y `DocumentFilter` |
| IDE | Visual Studio Code (Java Extension Pack) |

**Aprendizajes clave:** programación orientada a objetos, composición de componentes Swing (`JFrame`, `JPanel`, `CardLayout`, `JTable`, `JOptionPane`), manejo de eventos (`ActionListener`, `ListSelectionListener`, `TableModelListener`), validación de datos y trabajo colaborativo en equipo.

---

## Estructura del Proyecto

```text
AgendaContactosJava/
├── src/
│   ├── App.java                   # Punto de entrada (inicia la UI en el EDT con invokeLater)
│   ├── gui/                       # Capa de presentación (Vistas Swing)
│   │   ├── VentanaPrincipal.java  # Marco principal con CardLayout y barra de navegación
│   │   ├── PanelInicio.java       # Resumen de recordatorios y últimas notas
│   │   ├── PanelContacto.java     # Formulario y tabla de contactos
│   │   └── PanelNotas.java        # CRUD de notas
│   ├── logica/                    # Capa de negocio / Controlador
│   │   └── GestorAgenda.java      # Gestión y almacenamiento en memoria
│   └── modelo/                    # Capa de datos / Entidades
│       ├── Contacto.java
│       ├── Nota.java
│       └── Recordatorio.java
├── bin/                           # Clases compiladas (.class)
└── lib/                           # Dependencias externas (vacío)
```

---

## Arquitectura (MVC)

- **Modelo (`modelo/`):** clases `Contacto`, `Nota` y `Recordatorio`. Representan las entidades con sus atributos encapsulados, constructores y métodos `get`/`set`.
- **Lógica / Controlador (`logica/GestorAgenda.java`):** centraliza el estado de la aplicación y expone las operaciones CRUD sobre cada colección.
- **Vista (`gui/`):** componentes Swing que capturan la interacción del usuario y delegan la lógica al `GestorAgenda`. La navegación entre paneles se realiza con `CardLayout`.

---

## Equipo

Proyecto **grupal** desarrollado por **2 integrantes** para la asignatura de Programación Orientada a Objetos, 2.º semestre.

---

## Notas

- Proyecto de carácter **académico y demostrativo**; no está diseñado para producción.
- Los datos no se guardan entre ejecuciones (almacenamiento en memoria).
