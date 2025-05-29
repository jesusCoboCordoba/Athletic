# 🏋️‍♂️ Athletic App

**Documento Maestro EPMB02-G4**

## 👤 Integrantes

| Apellidos        | Nombres        | Correo                                                          |
| ---------------- | -------------- | --------------------------------------------------------------- |
| Cobo Cordoba     | Jesus          | [jcobo@poligran.edu.co](mailto:jcobo@poligran.edu.co)           |
| Estrada Gonzalez | Brandon David  | [brestrada1@poligran.edu.co](mailto:brestrada1@poligran.edu.co) |
| Duarte Monroy    | Emanuel        | [eduartem@poligran.edu.co](mailto:eduartem@poligran.edu.co)     |
| Chisaca Rubio    | Daniel         | [dchisaca@poligran.edu.co](mailto:dchisaca@poligran.edu.co)     |
| Cuervo Barragan  | Maria Fernanda | [mfecuervo@poligran.edu.co](mailto:mfecuervo@poligran.edu.co)   |

## 📅 Fecha

(Agregar aquí la fecha de entrega o creación del proyecto)

## 🔗 Enlaces Importantes

* 🗂️ Repositorio: [GitHub - Athletic](https://github.com/jesusCoboCordoba/Athletic-.git)
* 🎨 Mockups: [Moqups](https://app.moqups.com/Wsrl0QGXTtpN00ltOz9oN1blSGyQpWiw/view/page/a7bc758b4)
* 🔄 Diagrama de flujo de navegación: [Google Drive](https://drive.google.com/file/d/1Mvg0SGpya7IOUU8y2xJnJ2pawN_tSdot/view?usp=sharing)

---

## 📘 Sinopsis

**Athletic** es una aplicación móvil desarrollada en **Kotlin con Android Studio**, diseñada para ayudar a los usuarios a crear, personalizar y hacer seguimiento a rutinas de entrenamiento físico. Incluye una base de datos de ejercicios clasificados por grupo muscular, tipo de entrenamiento y nivel de dificultad. Los usuarios pueden configurar metas, registrar su progreso, y recibir recordatorios y estadísticas motivacionales para mantenerse activos.

---

## 🎯 Objetivo General

Crear una app móvil en **Kotlin** que permita a los usuarios organizar, adaptar y realizar rutinas de ejercicio físico, fomentando hábitos saludables y facilitando el monitoreo de su progreso.

### 🎯 Objetivos Específicos

* Sistema de registro y seguimiento de entrenamientos.
* Personalización de rutinas por objetivos (bajar de peso, ganar masa muscular, etc.).
* Alertas y recordatorios para mantener la motivación.
* Base de datos de ejercicios categorizados.
* Funcionalidad offline y online.
* Compatibilidad con Android.

---

## 🧠 Justificación

La creciente conciencia sobre la importancia del bienestar físico ha generado una alta demanda por apps que ayuden a establecer rutinas de ejercicio efectivas. Muchas soluciones actuales no ofrecen personalización ni una interfaz motivadora. **Athletic** surge para resolver esta brecha, brindando una herramienta práctica, accesible y motivadora para alcanzar metas físicas desde cualquier lugar.

---

## ✅ Requerimientos Funcionales

* Registro e inicio de sesión.
* Creación de perfil con datos y metas del usuario.
* Generación de rutinas personalizadas.
* Registro de progreso: repeticiones, tiempo, peso.
* Visualización de métricas y avances.
* Sección de comunidad (comentarios, publicaciones).
* Almacenamiento local (SQLite o Room).
* Navegación fluida entre pantallas: rutinas, comunidad, progreso, perfil.

## ❌ Requerimientos No Funcionales

* Interfaz moderna e intuitiva.
* Compatibilidad con Android 7.0 (API 24) o superior.
* Rendimiento eficiente en gama media.
* Arquitectura MVVM o Clean Architecture.
* Código limpio, comentado y documentado.
* Validaciones básicas en formularios y entradas.

---

## 🧭 Diagrama de Navegación

![Diagrama de Flujo](https://drive.google.com/uc?id=1Mvg0SGpya7IOUU8y2xJnJ2pawN_tSdot)

---

## 🖼️ Mockups / Wireframes

Mockups disponibles aquí:
👉 [Mockups Athletic - Moqups](https://app.moqups.com/Wsrl0QGXTtpN00ltOz9oN1blSGyQpWiw/view/page/a7bc758b4)

### Pantallas principales:

* **Inicio de sesión (Sign In)**: Acceso con correo y contraseña.
* **Registro (Sign Up)**: Creación de cuentas nuevas.
* **Menú lateral (Sidebar)**: Acceso a rutinas, planes alimenticios, progreso y configuración.
* **Panel principal del usuario**: Accesos a funciones clave como rutina actual y comunidad.
* **Your Workouts**: Módulo de elección de rutina (cardio, fuerza, etc.).
* **Día 1 (DAY 1)**: Detalles del ejercicio del día con progreso visual.
* **Meal Plan**: Planes alimenticios personalizados.
* **Healthy Eating Plans**: Sugerencias saludables y menús semanales.
* **Progress Tracking**: Seguimiento gráfico del avance físico.

---

## 🛠️ Tecnologías Usadas

* **Lenguaje:** Kotlin
* **IDE:** Android Studio
* **Base de datos local:** SQLite o Room (persistencia offline)
* **Arquitectura:** MVVM / Clean Architecture
* **Compatibilidad mínima:** Android 7.0 (API 24)

---

## 🧪 Cómo clonar y ejecutar

```bash
git clone https://github.com/jesusCoboCordoba/Athletic-.git
cd Athletic-
# Abre el proyecto en Android Studio
# Compila y ejecuta en un emulador o dispositivo Android
```
