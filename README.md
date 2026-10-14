# 📇 Gestor Personal — Agenda de Contactos, Notas y Recordatorios

Aplicación de escritorio desarrollada en **Java Swing** como **proyecto académico** (2.º semestre). Permite administrar contactos, notas y recordatorios desde una única ventana con navegación por pestañas, aplicando el patrón arquitectónico **MVC** (Modelo – Vista – Controlador).

> **Nota:** proyecto de escritorio con fines educativos; no está destinado a producción.

---

## ✨ Características

- **Gestión de contactos:** alta, modificación y eliminación con validación de nombre, teléfono (10 dígitos) y correo electrónico.
- **Gestión de notas:** CRUD completo con fecha automática y límites de longitud (título 3–60 caracteres, contenido máx. 1000).
- **Gestión de recordatorios:** agendamiento con validación real de fecha (`LocalDate.parse`, rechaza fechas inexistentes como `2026-13-45`) y hora (formato 24 h `HH:MM`), con marcado de tareas completadas.
- **Panel de inicio:** resumen en cascada de las 3 notas más recientes (ordenadas por fecha) y tabla de recordatorios con casillas de verificación.
- **Fuente única de verdad:** todos los datos (contactos, notas y recordatorios) viven en `GestorAgenda`; las vistas nunca mantienen copias propias.
- **Sincronización en tiempo real:** las notas creadas, editadas o eliminadas se reflejan al instante en el panel de inicio.
- **Confirmaciones y avisos:** diálogos modales para cada operación y confirmación previa antes de eliminar.

---

## 🛠️ Tecnologías

| Categoría | Tecnología |
| --- | --- |
| Lenguaje | Java (JDK 17 o superior) |
| UI | Java Swing / AWT |
| Arquitectura | Patrón MVC |
| Estructuras de datos | `ArrayList` |
| Fechas | API `java.time` (`LocalDate`, `DateTimeFormatter`) |
| Validación | Expresiones regulares y `DocumentFilter` |
| IDE | Visual Studio Code (Java Extension Pack) |

---

## 🏗️ Arquitectura

Proyecto estructurado bajo el patrón **MVC** con dependencias unidireccionales:

```
┌────────────────────────────┐
│  gui/  (Vistas Swing)      │  ← Formularios e interacción del usuario
└──────────┬─────────────────┘
           ▼
┌────────────────────────────┐
│  logica/GestorAgenda       │  ← Lógica de negocio y estado en memoria
└──────────┬─────────────────┘
           ▼
┌────────────────────────────┐
│  modelo/  (Entidades)      │  ← Modelos / POCOs del dominio
└────────────────────────────┘
```

- **Controlador centralizado:** `GestorAgenda` reúne las colecciones y expone las operaciones CRUD de cada entidad. Es la única fuente de verdad de la aplicación.
- **Vista desacoplada:** los paneles Swing capturan la interacción y delegan en el gestor; la navegación usa `CardLayout`.
- **Modelo encapsulado:** entidades con atributos privados, constructores y métodos `get`/`set`.
- **Colecciones protegidas:** los métodos `get` de `GestorAgenda` devuelven **copias** (`new ArrayList<>(...)`), de modo que las vistas no pueden alterar el estado interno directamente.
- **Almacenamiento en memoria** durante la ejecución (sin base de datos), ya que el foco era la lógica y la interfaz.

---

## 📦 Estructura del proyecto

```
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
├── bin/                           # Salida de compilación (generada, ignorada por git)
└── lib/                           # Dependencias externas (vacío)
```

> `bin/` y los archivos `.class` están excluidos del control de versiones mediante `.gitignore`.

---

## 💾 Modelo de datos

| Entidad | Descripción | Campos principales |
| --- | --- | --- |
| `Contacto` | Datos de una persona | nombre, teléfono, correo |
| `Nota` | Apunte rápido con fecha | título, contenido, fecha |
| `Recordatorio` | Tarea con fecha y hora | asunto, fecha, hora, completado |

> Las entidades se almacenan en `ArrayList` dentro de `GestorAgenda`; no hay persistencia en disco.

---

## 📱 Módulos de la aplicación

### PanelInicio
Pantalla principal con dos secciones:
- **Recordatorios:** tabla interactiva con casilla de verificación para marcar tareas completadas.
- **Últimas notas:** tarjetas en cascada con las 3 notas más recientes.

### PanelContacto
Módulo de contactos:
- Formulario de alta con validación de nombre, teléfono y correo.
- Tabla de listado con selección de fila para modificar o eliminar.

### PanelNotas
Módulo de notas:
- Formulario con título y contenido multilínea, con validación de longitud.
- CRUD completo y sincronización automática con el panel de inicio.

---

## 📋 Próximos pasos (mejoras previstas)

- [ ] Persistencia en archivos o base de datos (hoy todo vive en memoria).
- [ ] Edición de recordatorios (actualmente solo alta y cambio de estado).
- [ ] Búsqueda y ordenamiento en las tablas de contactos y notas.
- [ ] Exportación e impresión de contactos y notas.
- [ ] Tema oscuro y ajustes de accesibilidad visual.
- [ ] Pruebas unitarias para la capa de lógica (`GestorAgenda`).

---

## 🎓 Contexto académico

Proyecto **grupal** desarrollado por **2 integrantes** en el 2.º semestre de Ingeniería de Software, como parte de la asignatura **Programación Orientada a Objetos**, aplicando:

- Programación orientada a objetos y encapsulamiento
- Arquitectura MVC (Modelo – Vista – Controlador)
- Interfaz gráfica con Java Swing
- Manejo de eventos (`ActionListener`, `ListSelectionListener`, `TableModelListener`)
- Validación de datos con expresiones regulares
- Trabajo colaborativo en equipo

---

## 📄 Licencia

Este proyecto es de uso académico. Para más información, contacta al autor.
