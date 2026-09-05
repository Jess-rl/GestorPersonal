# Gestor Personal - Agenda en Java Swing

Proyecto práctico grupal desarrollado para la materia de Programación Orientada a Objetos (Segundo Semestre de Ingeniería de Software). 

El objetivo del proyecto fue aplicar conceptos iniciales de desarrollo de interfaces gráficas de escritorio con Java Swing, manejo de eventos y estructuración del código bajo el patrón arquitectónico MVC (Modelo - Vista - Controlador).

## Características

* **Panel Principal (Inicio):** 
  * Tabla interactiva de recordatorios con casillas de verificación para marcar tareas completadas.
  * Resumen dinámico en cascada de las últimas 3 notas añadidas.
  * Diálogos modales para agregar recordatorios (con validación de fecha y hora) y notas rápidas.
* **Módulo de Contactos:**
  * Registro y listado tabular de contactos.
  * Validaciones de entrada: solo texto en nombres, solo números en teléfonos y estructura válida de correo electrónico.
* **Módulo de Notas:**
  * Creación, edición y eliminación de notas.
  * Registro de marcas de tiempo automáticas.
  * Sincronización en tiempo real con la pantalla de inicio.

## Estructura del Proyecto

```text
src/
├── App.java                   # Punto de entrada (EDT con invokeLater)
├── gui/                       # Capa de presentación (Vistas Swing)
│   ├── VentanaPrincipal.java  # Marco principal con CardLayout y navegación
│   ├── PanelInicio.java       # Resumen de tareas y notas recientes
│   ├── PanelContacto.java     # Formulario y tabla de contactos
│   └── PanelNotas.java        # CRUD de notas
├── logica/                    # Capa de negocio / Controlador
│   └── GestorAgenda.java      # Gestión y almacenamiento en memoria
└── modelo/                    # Capa de datos / Entidades
    ├── Contacto.java
    ├── Nota.java
    └── Recordatorio.java