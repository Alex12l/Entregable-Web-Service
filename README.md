# Store Peru - (Web Service)


---

## 🚀 Tecnologías Utilizadas

* **Node.js** - Entorno de ejecución para JavaScript.
* **Express** - Framework web para Node.js.
* **MySQL / MySQL2** - Base de datos relacional y controlador para Node.js.
* **Dotenv** - Manejo de variables de entorno seguras.
* **Nodemon** - Herramienta de desarrollo para reinicio automático del servidor.

---

## 📁 Estructura del Proyecto

```text
Entregable/
│
├── .env                # Variables de entorno y credenciales de la BD
├── .gitignore          # Archivos ignorados por Git (node_modules, .env)
├── database.js         # Configuración del pool de conexión a MySQL
├── index.js            # Servidor principal, rutas y lógica del CRUD
├── package.json        # Dependencias y scripts del proyecto
└── package-lock.json   # Registro de versiones de dependencias
