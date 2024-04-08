CREATE DATABASE DeModaOutlet;
USE DeModaOutlet;

CREATE TABLE Store(
id_store INT auto_increment PRIMARY KEY,
name VARCHAR(255) NOT NULL,
location VARCHAR(255) NOT NULL
);

CREATE TABLE Product(
id_product INT auto_increment PRIMARY KEY,
name VARCHAR(255) NOT NULL,
price decimal(10,2) NOT NULL,
-- Stock INT NOT NULL
id_store INT NOT NULL,
FOREIGN KEY (id_store) REFERENCES Store (id_store) ON DELETE CASCADE
);

CREATE TABLE Client (
id_client INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
last_name VARCHAR(255) NOT NULL,
email VARCHAR(255) UNIQUE
);

CREATE TABLE Purchase(
id_purchase INT AUTO_INCREMENT PRIMARY KEY,
purchase_date DATE NOT NULL,
amount INT NOT NULL,
id_client INT NOT NULL,
id_product INT NOT NULL,
FOREIGN KEY (id_client) REFERENCES Client (id_client) ON DELETE CASCADE,
FOREIGN KEY (id_product) REFERENCES Product (id_product)
);

ALTER TABLE Product ADD COLUMN stock INT NOT NULL;

-- SELECT * FROM Client;
-- SELECT * FROM Client WHERE name LIKE '%'"Manu"'%';

-- INSERT INTO Store (name,location) VALUES ("D1","Calle 20"), ("DollarCity","Calle 50"), ("Justo y bueno","Calle 20"), ("Exito","Calle 80");
-- SELECT * FROM Store;

-- SELECT * FROM Product JOIN Store ON Product.id_store = Store.id_store;

-- SELECT * FROM Product JOIN Store ON Store.id_store = Product.id_store WHERE Store.id_store = Product.id_store;
-- select * FROM Store JOIN Product ON Store.id_store = Product.id_store WHERE Store.id_store = Product.id_store;

-- SELECT * FROM Purchase JOIN Client ON Client.id_client = Purchase.id_client JOIN Product ON Product.id_product = Purchase.id_purchase JOIN Store ON Store.id_store = Product.id_store;

-- SELECT * FROM Purchase JOIN Client ON Client.id_client = Purchase.id_client JOIN Product ON Product.id_product = Purchase.id_purchase JOIN Store ON Store.id_store = Product.id_store WHERE Product.name LIKE '%'"m" '%';

-- SELECT * FROM Product;
-- SELECT * FROM Purchase  JOIN Client ON Client.id_client = Purchase.id_client  JOIN Product ON Product.id_product = Purchase.id_product  JOIN Store ON Store.id_store = Product.id_store
