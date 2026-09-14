//Importar librerías:
const express = require('express');         //Framework
const mysql = require('mysql2');            //Acceso a la BD
const bodyParser = require('body-parser');  //Manejo de datos (JSON - FORM)
const PORT = 3000; //Puerto es el canal de comunicación

const app = express();
app.use(bodyParser.json()); //Formato de intercambio de datos

//Configurar la conexión 
// - pendiente .env
// - pendiente poolConexion
const db = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'store_peru'
});

//Verificar la conexión a la BD
db.connect((err) => {
  if (err) throw err;
  console.log('Conectado a la base de datos Store_Peru');
});

//Verbos HTTP
//require   : requerimiento | solicitud
//result    : respuesta

//Crear Producto
app.post('/productos', (require, result) => {
  //Paso 1. Recepcionar los datos que ingresan como JSON
  const { nombre, categoria, descripcion, garantia, precio, stock } = require.body;

  //Paso 2. Construir una consulta SQL utilizando comodines para evitar SQLInjection
  const sql = `
  INSERT INTO Productos (nombre, categoria, descripcion, garantia, precio, stock) 
    VALUES (?,?,?,?,?,?)
  `;

  //Paso 3. Ejecutar la consulta
  db.query(sql, [nombre, categoria, descripcion, garantia, precio, stock], (err, res) => {
    if (err) return result.status(500).send(err);

    //Logramos guardar un registro
    result.send({ message: "Producto guardado", id: res.insertId });
  });
});

//Actualizar Producto (ID + datos que se requiere actualizar)
//Ruta queda => http://localhost:3000/productos/3
app.put('/productos/:id', (require, result) => {
  //Paso 1: Obtener el ID (parámetro URL)
  const { id } = require.params;

  //Paso 2: Obtener los datos a actualizar
  const { nombre, categoria, descripcion, garantia, precio, stock } = require.body;

  //Paso 3: Construir consulta
  const sql = `
  UPDATE Productos SET
    nombre = ?,
    categoria = ?,
    descripcion = ?,
    garantia = ?,
    precio = ?,
    stock = ?,
    update_at = NOW()
  WHERE id = ?
  `;

  //Paso 4: Ejecutar consulta
  db.query(sql, [nombre, categoria, descripcion, garantia, precio, stock, id], (err, res) => {
    if (err) return result.status(500).send(err);
    result.send({ message: 'Actualizado correctamente' });
  });
});

//Listar Productos
app.get('/productos', (require, result) => {
  const sql = `
  SELECT id, nombre, categoria, descripcion, garantia, precio, stock, create_at, update_at 
  FROM Productos 
    ORDER BY id DESC 
    LIMIT 20;
  `;

  db.query(sql, (err, res) => {
    if (err) return result.status(500).send(err);
    result.json(res);
  });
});

//Buscar Producto por ID
//Ruta queda => http://localhost:3000/productos/2
app.get('/productos/:id', (require, result) => {
  const { id } = require.params;
  const sql = `SELECT id, nombre, categoria, descripcion, garantia, precio, stock, create_at, update_at FROM Productos WHERE id = ?`;

  db.query(sql, [id], (err, res) => {
    if (err) return result.status(500).send(err);
    if (res.length == 0) return result.status(404).send({ message: "No encontrado" });
    
    return result.json(res[0]); //retorna un JSON
  });
});

//Eliminar Producto
//Ruta queda => http://localhost:3000/productos/5
app.delete('/productos/:id', (require, result) => {
  const { id } = require.params;
  const sql = `DELETE FROM Productos WHERE id = ?`;
  
  db.query(sql, [id], (err, res) => {
    if (err) return result.status(500).send(err);
    if (res.affectedRows == 0) return result.status(404).send({ message: "No encontrado" });

    return result.send({ message: 'Eliminado correctamente' });
  });
});

//Iniciando servidor del WebService (JS)
app.listen(PORT, () => {
  console.log(`Servidor iniciado en http://localhost:${PORT}`);
});