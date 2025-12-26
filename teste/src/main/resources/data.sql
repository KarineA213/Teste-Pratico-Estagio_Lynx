INSERT INTO customers (name, email, created_at) VALUES
                                                    ('ana silva', 'ana@email.com', CURRENT_TIMESTAMP()),
                                                    ('bruno costa', 'bruno@gmail.com', CURRENT_TIMESTAMP()),
                                                    ('carla souza', 'carla@gmail.com', CURRENT_TIMESTAMP);

INSERT INTO products ( name, category, price_cents, active) VALUES
                                                                ('mouse', 'perifericos', 5000, 1),    -- ID 1
                                                                ('teclado', 'perifericos', 12000, 1), -- ID 2
                                                                ('monitor', 'eletronicos', 90000, 1); -- ID 3

-- AGORA COM O TOTAL CALCULADO:
-- Order 1: 2 mouses (10.000) + 1 teclado (12.000) = 22.000
-- Order 2: 1 monitor (90.000) = 90.000
-- Order 3: 1 monitor (35.000??) + 1 mouse (5.000) = 40.000
-- (Nota: No Order 3 item 1 você colocou preço 35.000 manual, diferente do cadastro do produto. Mantive sua lógica.)

INSERT INTO orders (customer_id, status, created_at, total_amount) VALUES
                                                                       (1, 'PAID', current_timestamp(), 22000.00),
                                                                       (2, 'NEW', current_timestamp(), 90000.00),
                                                                       (3, 'PAID', current_timestamp(), 40000.00);

INSERT INTO order_items (order_id, product_id, quantity, unit_price_cents) VALUES
                                                                               (1,1,2,5000),
                                                                               (1,2,1,12000),
                                                                               (2,3,1,90000),
                                                                               (3,3,1,35000),
                                                                               (3,1,1,5000);

INSERT INTO payments (order_id, method, amount_cents, paid_at) VALUES
                                                                   (1,'PIX', 10000, CURRENT_TIMESTAMP()),
                                                                   (1,'CARD', 7000, CURRENT_TIMESTAMP()),
                                                                   (3,'CARD', 200000, CURRENT_TIMESTAMP()),
                                                                   (3, 'PIX', 155000, CURRENT_TIMESTAMP());