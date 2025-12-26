INSERT INTO customers (name, email, created_at) VALUES
                                                    ('ana silva', 'ana@email.com', CURRENT_TIMESTAMP()),
                                                    ('bruno costa', 'bruno@gmail.com', CURRENT_TIMESTAMP()),
                                                    ('carla souza', 'carla@gmail.com', CURRENT_TIMESTAMP);

INSERT INTO products ( name, category, price_cents, active) VALUES
                                                                ('mouse', 'perifericos', 5000, 1),    -- ID 1
                                                                ('teclado', 'perifericos', 12000, 1), -- ID 2
                                                                ('monitor', 'eletronicos', 90000, 1); -- ID 3


INSERT INTO orders (customer_id, status, created_at, total_amount) VALUES
                                                                       (1, 'PAID', current_timestamp(), 220),
                                                                       (2, 'NEW', current_timestamp(), 900),
                                                                       (3, 'PAID', current_timestamp(), 400);

INSERT INTO order_items (order_id, product_id, quantity, unit_price_cents) VALUES
                                                                               (1,1,2,50),
                                                                               (1,2,1,120),
                                                                               (2,3,1,900),
                                                                               (3,3,1,350),
                                                                               (3,1,1,50);

INSERT INTO payments (order_id, method, amount_cents, paid_at) VALUES
                                                                   (1,'PIX', 100, CURRENT_TIMESTAMP()),
                                                                   (1,'CARD', 70, CURRENT_TIMESTAMP()),
                                                                   (3,'CARD', 2000, CURRENT_TIMESTAMP()),
                                                                   (3, 'PIX', 1550, CURRENT_TIMESTAMP());