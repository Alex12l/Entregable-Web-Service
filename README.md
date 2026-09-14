# 🛒 Store Perú 📱

¡Hola! Este es el repositorio de mi proyecto integrador que consta de una aplicación móvil conectada a un servidor backend propio. Aquí podrás encontrar tanto la app como la API para gestionar productos de manera rápida y sencilla.

---

## 🛠️ ¿Cómo está organizado?

* 📂 **Android Studio/** -> Aquí vive todo el código de la app móvil (interfaz, vistas en XML, lógica en Java y las peticiones usando Volley).
* 📂 **Web Service/** -> Aquí está el backend hecho en Node.js que se encarga de conectar la app con la base de datos.

---

## ⚙️ Instrucciones

### 💻 Paso 1: Levantar el Backend (Web Service)
1. Abre tu terminal favorita y entra a la carpeta del Web Service.
2. Instala los paquetes necesarios corriendo:
npm install
3. Configura tus datos de conexión a MySQL y arranca el servidor con:
npm start

### 📱 Paso 2: Configurar y abrir la App (Android Studio)
1. Abre la carpeta Android Studio directamente desde el programa Android Studio.
2. **Ojo con la red:** Busca en el código de la app la variable de la ruta del servidor y cámbiala por tu IP local
private final String URL = "http://TU_IP_AQUI:3000/productos";
3. Conéctate a un emulador o a tu celular físico y ¡listo para probar!

---

## 🚀 Tecnologías que usé en el proyecto
* **App Móvil:** Android Studio, Java, XML, Volley, ConstraintLayout.
* **Servidor / API:** Node.js, Express.
* **Base de Datos:** MySQL.
* **Control de Versiones:** Git & GitHub.
