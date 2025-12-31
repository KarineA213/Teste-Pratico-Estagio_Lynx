INSERT INTO customers (name, email, created_at) VALUES
                                                    ('ana silva', 'ana@email.com', CURRENT_TIMESTAMP()),
                                                    ('bruno costa', 'bruno@gmail.com', CURRENT_TIMESTAMP()),
                                                    ('carla souza', 'carla@gmail.com', CURRENT_TIMESTAMP());

INSERT INTO products ( name, category, price_cents, active) VALUES
                                                                ('mouse', 'perifericos', 5000, 1),
                                                                ('teclado', 'perifericos', 12000, 1),
                                                                ('monitor', 'eletronicos', 90000, 1);

INSERT INTO orders (customer_id, status, created_at, total_amount) VALUES
                                                                       (1, 'PAID', CURRENT_TIMESTAMP(), 22000),
                                                                       (2, 'NEW', CURRENT_TIMESTAMP(), 90000),
                                                                       (3, 'PAID', CURRENT_TIMESTAMP(), 40000);

INSERT INTO order_items (order_id, product_id, quantity, unit_price_cents) VALUES
                                                                               (1,1,2,5000),
                                                                               (1,2,1,12000),
                                                                               (2,3,1,90000),
                                                                               (3,3,1,90000),
                                                                               (3,1,1,5000);

-- pagamentos em centavos
INSERT INTO payments (order_id, method, amount_cents, paid_at) VALUES
                                                                   (1,'PIX', 20000, CURRENT_TIMESTAMP()),  -- deve bater com total
                                                                   (1,'CARD', 2000, CURRENT_TIMESTAMP()),
                                                                   (3,'CARD', 35000, CURRENT_TIMESTAMP()),
                                                                   (3,'PIX', 5000, CURRENT_TIMESTAMP());
