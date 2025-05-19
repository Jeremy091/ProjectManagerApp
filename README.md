# ProjectManagerApp

Gestor de proyectos y actividades para Android, construido con Android Studio y SQLite, usando Material Design.

---

## 📋 Descripción

ProjectManagerApp es una aplicación móvil que permite:

- Autenticación de usuarios (login/register/recover password).
- **CRUD** de proyectos: crear, editar, eliminar y listar proyectos.
- **CRUD** de actividades asociadas a cada proyecto, con estados (Planificado, En ejecución, Realizado).
- Cálculo y visualización del avance de cada proyecto con una barra de progreso.
- Persistencia de datos local en SQLite.

---

## 🚀 Características

1. **Login/Registro**  
   - Validación de credenciales.
   - Recuperación de contraseña simulada.

2. **Gestión de Proyectos**  
   - Crear, editar y eliminar proyectos.
   - Fecha de inicio y fin.

3. **Gestión de Actividades**  
   - Crear, editar y eliminar actividades por proyecto.
   - Estado de la actividad y fechas.

4. **Avance de Proyecto**  
   - Barra de progreso basada en el porcentaje de actividades “Realizado”.

5. **Diseño**  
   - Material Components (RecyclerView, CardView, FloatingActionButton).
   - Temas y estilos personalizados.

---

## 🛠️ Requisitos

- Android Studio **Electric Eel** o superior  
- **minSdkVersion**: 21  
- **targetSdkVersion**: 36  
- Dependencias:
  ```groovy
  implementation "com.google.android.material:material:1.11.0"
  implementation "androidx.recyclerview:recyclerview:1.3.0"


## 📥 Uso
🔐 **Registro / Login**
Regístrate con un usuario y contraseña.

![image](https://github.com/user-attachments/assets/0c31da6e-f08e-46b6-81e3-1b30813b2f36)


Inicia sesión con tus credenciales para acceder a tus proyectos.

📁 **Crear Proyecto**
Toca el botón ➕ en la pantalla de Proyectos.

Ingresa nombre, descripción, fecha de inicio y fecha de fin.
![image](https://github.com/user-attachments/assets/51e330dc-c41e-4f3f-a927-fde655f98a0c)

Guarda el proyecto.

✏️ **Editar / 🗑️ Eliminar Proyecto**
Cada tarjeta de proyecto incluye:

Un botón ✏️ para editar.
![image](https://github.com/user-attachments/assets/094503a3-9ed4-4fe8-865e-ea299791c577)


Un botón 🗑️ para eliminar.

✅ **Ver Actividades**
Toca cualquier tarjeta de proyecto para acceder a la lista de actividades relacionadas.

📝 **Crear Actividad**
Dentro del proyecto, pulsa el botón ➕.

Llena los campos: título, descripción, fecha de inicio, fin y estado (Planificado, En ejecución, Realizado).

![image](https://github.com/user-attachments/assets/e2e95345-091a-4cca-9215-c16c4bbdb03c)

✏️ **Editar / 🗑️ Eliminar Actividad**
En cada actividad verás los íconos para editar (✏️) o eliminar (🗑️).

Puedes cambiar el estado para actualizar el avance del proyecto.
![image](https://github.com/user-attachments/assets/3693378a-4f05-43ff-a9d9-c2e7e34124b1)

## 👨‍💻 Autor

**Jeremy Pavon**

## 📥 Instalación

Clona el repositorio:

```bash
git clone https://github.com/Jeremy091/ProjectManagerApp.git



