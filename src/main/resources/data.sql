-- Tabla de marcas
CREATE TABLE IF NOT EXISTS  brands (
                        id   INT PRIMARY KEY,
                        name VARCHAR(50) NOT NULL
);


INSERT INTO brands (id, name) VALUES (1, 'ZARA');
INSERT INTO brands (id, name) VALUES (2, 'PULL&BEAR');
INSERT INTO brands (id, name) VALUES (3, 'BERSHKA');
INSERT INTO brands (id, name) VALUES (4, 'STRADIVARIUS');

-- Tabla de precios
CREATE TABLE IF NOT EXISTS  prices (
                        id          INT AUTO_INCREMENT PRIMARY KEY,
                        brand_id    INT NOT NULL,
                        start_date  TIMESTAMP NOT NULL,
                        end_date    TIMESTAMP NOT NULL,
                        price_list  INT NOT NULL,
                        product_id  INT NOT NULL,
                        priority    INT NOT NULL,
                        price       DECIMAL(19, 4) NOT NULL,
                        currency    VARCHAR(3) NOT NULL,
                        CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES brands(id)
);

INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, currency)
VALUES (1, '2020-06-14T00:00:00', '2020-12-31T23:59:59', 1, 35455, 0, 35.50, 'EUR');

INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, currency)
VALUES (1, '2020-06-14T15:00:00', '2020-06-14T18:30:00', 2, 35455, 1, 25.45, 'EUR');

INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, currency)
VALUES (1, '2020-06-15T00:00:00', '2020-06-15T11:00:00', 3, 35455, 1, 30.50, 'EUR');

INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, currency)
VALUES (1, '2020-06-15T16:00:00', '2020-12-31T23:59:59', 4, 35455, 1, 38.95, 'EUR');
