DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;


CREATE TABLE categories
(
    categoryId   INT AUTO_INCREMENT,
    categoryName VARCHAR(20) NOT NULL,
    PRIMARY KEY (categoryId)
);


CREATE TABLE products
(
    productId       INT AUTO_INCREMENT,
    categoryId      INT         NOT NULL,
    productName     VARCHAR(20) NOT NULL,
    productProducer VARCHAR(30),
    productPrice    INT,
    PRIMARY KEY (productId),
    FOREIGN KEY (categoryId) REFERENCES categories (categoryId) ON DELETE CASCADE
);

CREATE TABLE orders
(
    orderId              INT AUTO_INCREMENT,
    productId            INT NOT NULL,
    orderStatus          VARCHAR(30),
    dateOfStartExecution DATETIME,
    executionTime        INT,
    PRIMARY KEY (orderId),
    FOREIGN KEY (productId) REFERENCES products (productId) ON DELETE CASCADE
);


INSERT INTO categories(categoryName)
VALUES ('Phones'),
       ('Laptops'),
       ('Headphones');

INSERT INTO products(categoryId, productName, productProducer, productPrice)
VALUES (1, 'IPhone 13 PRO', 'Apple', 1500),
       (1, 'IPhone 7', 'Apple', 800),
       (1, 'Samsung Galaxy S7', 'Samsung', 1000),
       (2, 'Asus VivoBook S15', 'Asus', 1000),
       (2, 'Lenovo ThinkPad 14', 'Lenovo', 750),
       (2, 'HP Probook 15', 'HP', 1200),
       (3, 'AirdDots 3', 'Xiaomi', 50),
       (3, 'AirPods', 'Apple', 100),
       (3, 'AirBads', 'Samsung', 80);