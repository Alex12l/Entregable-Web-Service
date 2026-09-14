CREATE DATABASE IF NOT EXISTS store_peru;
USE store_peru;

CREATE TABLE IF NOT EXISTS Productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(70) NOT NULL,
    categoria VARCHAR(70) NOT NULL,    
    descripcion VARCHAR(70) NOT NULL,
    garantia TINYINT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock TINYINT NOT NULL,
    create_at DATETIME NOT NULL DEFAULT NOW(),
    update_at DATETIME NULL    
) ENGINE = INNODB;

INSERT INTO Productos (nombre, categoria, descripcion, garantia, precio, stock) VALUES
('Teclado Mecánico HyperX Alloy Origins', 'Periféricos', 'RGB, Switches Red, Aluminio', 12, 389.90, 20),
('Mouse Logitech G Pro X Superlight', 'Periféricos', 'Inalámbrico, Sensor HERO 25K', 12, 499.00, 15),
('Audífonos Gamer Razer BlackShark V2', 'Audio', 'THX Spatial Audio, Micrófono', 6, 299.50, 40),
('Silla Gamer Cougar Armor One', 'Muebles', 'Ergonómico, Cuero sintético', 12, 799.00, 8);

INSERT INTO Productos (nombre, categoria, descripcion, garantia, precio, stock) VALUES
('Monitor Gamer LG UltraGear 27"', 'Monitores', 'IPS, 144Hz, 1ms, Full HD', 12, 1099.00, 10),
('Laptop ASUS TUF Gaming F15', 'Laptops', 'Core i5, RTX 3050, 16GB RAM, 512GB SSD', 12, 3499.00, 5),
('Procesador AMD Ryzen 5 5600X', 'Componentes', '6 núcleos, 12 hilos, 3.7 GHz', 24, 680.00, 25),
('Tarjeta de Video NVIDIA RTX 4060', 'Componentes', '8GB GDDR6, DLSS 3', 12, 1550.00, 7),
('Memoria RAM Kingston Fury Beast 16GB', 'Componentes', 'DDR4 3200MHz (2x8GB)', 36, 199.90, 30),
('Disco Sólido SSD Kingston NV2 1TB', 'Almacenamiento', 'M.2 NVMe PCIe 4.0, 3500 MB/s', 36, 269.90, 50),
('Impresora Multifuncional Epson EcoTank L3250', 'Impresión', 'Sistema continuo de tinta, Wi-Fi', 12, 749.00, 12),
('Micrófono HyperX QuadCast S', 'Audio', 'USB Condensador, RGB, Antivibración', 12, 529.00, 18),
('Router Wi-Fi 6 TP-Link Archer AX10', 'Redes', 'Dual-Band Gigabit, OFDMA', 12, 229.00, 22),
('Pasta Térmica Arctic MX-4 4g', 'Accesorios', 'Alta conductividad, Durabilidad de 8 años', 6, 35.00, 60);
select * from productos;