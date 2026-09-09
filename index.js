const express = require('express');
const pool = require('./database');
require('dotenv').config();

const app = express();
app.use(express.json());

const PORT = process.env.PORT || 3000;


//  CREATE 

app.post('/productos', async (req, res) => {
    try {
        const { nombre, categoria, descripcion, garantia, precio, stock } = req.body;

        if (!nombre || !categoria || !descripcion || garantia === undefined || !precio || stock === undefined) {
            return res.status(400).json({
                success: false,
                message: 'Todos los campos son obligatorios.'
            });
        }

        const query = `INSERT INTO Productos (nombre, categoria, descripcion, garantia, precio, stock) VALUES (?, ?, ?, ?, ?, ?)`;
        const [result] = await pool.query(query, [nombre, categoria, descripcion, garantia, precio, stock]);

        res.status(201).json({
            success: true,
            message: 'Producto creado exitosamente.',
            data: { id: result.insertId, nombre, categoria, descripcion, garantia, precio, stock }
        });
    } catch (error) {
        res.status(500).json({
            success: false,
            message: 'Error en el servidor al crear el producto.',
            error: error.message
        });
    }
});


//  READ 

app.get('/productos', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT * FROM Productos');
        res.status(200).json({
            success: true,
            total: rows.length,
            data: rows
        });
    } catch (error) {
        res.status(500).json({
            success: false,
            message: 'Error en el servidor al obtener los productos.',
            error: error.message
        });
    }
});


//  SEARCH 

app.get('/productos/:id', async (req, res) => {
    try {
        const { id } = req.params;
        const [rows] = await pool.query('SELECT * FROM Productos WHERE id = ?', [id]);

        if (rows.length === 0) {
            return res.status(404).json({
                success: false,
                message: `No se encontró el producto con ID: ${id}`
            });
        }

        res.status(200).json({
            success: true,
            data: rows[0]
        });
    } catch (error) {
        res.status(500).json({
            success: false,
            message: 'Error en el servidor al buscar el producto.',
            error: error.message
        });
    }
});


//UPDATE

app.put('/productos/:id', async (req, res) => {
    try {
        const { id } = req.params;
        const { nombre, categoria, descripcion, garantia, precio, stock } = req.body;

        const [existe] = await pool.query('SELECT id FROM Productos WHERE id = ?', [id]);
        if (existe.length === 0) {
            return res.status(404).json({
                success: false,
                message: `El producto con ID ${id} no existe.`
            });
        }

        if (!nombre || !categoria || !descripcion || garantia === undefined || !precio || stock === undefined) {
            return res.status(400).json({
                success: false,
                message: 'Todos los campos son obligatorios para actualizar.'
            });
        }

        const query = `UPDATE Productos SET nombre = ?, categoria = ?, descripcion = ?, garantia = ?, precio = ?, stock = ?, update_at = NOW() WHERE id = ?`;
        await pool.query(query, [nombre, categoria, descripcion, garantia, precio, stock, id]);

        res.status(200).json({
            success: true,
            message: 'Producto actualizado exitosamente.'
        });
    } catch (error) {
        res.status(500).json({
            success: false,
            message: 'Error en el servidor al actualizar.',
            error: error.message
        });
    }
});


//  DELETE 

app.delete('/productos/:id', async (req, res) => {
    try {
        const { id } = req.params;

        const [existe] = await pool.query('SELECT id FROM Productos WHERE id = ?', [id]);
        if (existe.length === 0) {
            return res.status(404).json({
                success: false,
                message: `El producto con ID ${id} no existe.`
            });
        }

        await pool.query('DELETE FROM Productos WHERE id = ?', [id]);

        res.status(200).json({
            success: true,
            message: 'Producto eliminado exitosamente.'
        });
    } catch (error) {
        res.status(500).json({
            success: false,
            message: 'Error en el servidor al eliminar.',
            error: error.message
        });
    }
});


app.listen(PORT, () => {
    console.log(`Servidor corriendo en el puerto ${PORT}`);
});