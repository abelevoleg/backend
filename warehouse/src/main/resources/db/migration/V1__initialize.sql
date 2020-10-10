-- таблица пользователей приложения
create table users ( id bigint NOT NULL AUTO_INCREMENT, login VARCHAR(50) not null unique, password VARCHAR(80) not null, firstname VARCHAR(50), lastname VARCHAR(50), phone VARCHAR(30), email VARCHAR(50), PRIMARY KEY (id) );
insert into users (login,password, firstname, lastname, phone, email)
values ('admin', '$2a$10$iIngXqEsF7aTmZ4IvPZs/enNAba47.OOq5OlqWpQM3JGdlrPCuHTS','Иван', 'Иванов', '+7 (987) 654-32-10', 'admin@example.com');
insert into users (login,password, firstname, lastname, phone, email)
values ('in', '$2a$10$iIngXqEsF7aTmZ4IvPZs/enNAba47.OOq5OlqWpQM3JGdlrPCuHTS','Иван', 'Иванов', '+7 (987) 654-32-10', 'admin@example.com');
-- пароль: admin

-- таблица ролей пользователя приложения
create table roles ( id bigint NOT NULL AUTO_INCREMENT, name VARCHAR(50) not null, title VARCHAR(50) not null, primary key (id));
insert into roles (name, title) values('ROLE_CUSTOMER', 'клиент'), ('ROLE_MANAGER', 'менеджер'), ('ROLE_ADMIN', 'администратор');

-- таблица товаров
create table products (id bigint NOT NULL AUTO_INCREMENT, title varchar(255), description varchar(1000), primary key(id));
insert into products (title) values ('Шляпа'), ('Панама'), ('Очки');

-- таблица категорий товаров
create table categories (id bigint NOT NULL AUTO_INCREMENT, title varchar(255) NOT NULL, primary key(id));
insert into categories (title) values ('Головные уборы'), ('Оптика');

-- таблица единиц измерения количества товаром
CREATE TABLE units (id bigint NOT NULL AUTO_INCREMENT, title VARCHAR(40) NOT NULL UNIQUE, description varchar(1000), PRIMARY KEY (id));
insert into units (title, description) values ('шт', 'штуки');

-- таблица контрагентов
CREATE TABLE contractors (id bigint NOT NULL AUTO_INCREMENT, title VARCHAR(255) NULL, PRIMARY KEY (id));
insert into contractors (title) values ('ООО "Поставщик"'),('ООО "Получатель"');

-- таблица транзакций товаров (поступления и отгрузки)
create table product_transactions (id bigint NOT NULL AUTO_INCREMENT, transaction_date DATETIME, quantity DECIMAL(13,3),
comment varchar(255), primary key(id));
insert into product_transactions (transaction_date, quantity, comment) values (now() - 1, 100, 'за полцены'), (now(), -10, 'в долг');

-- таблица истории действий пользователя
create table user_actions (id bigint NOT NULL AUTO_INCREMENT, type varchar(25), product_id bigint not null,
product_name varchar(25), data DATETIME, author varchar(255), primary key(id));

-- таблица изображений
create table images (id bigint NOT NULL AUTO_INCREMENT, title varchar(255), image MEDIUMTEXT, primary key(id));

-- таблица удаленных пользователей
create table deleted_users (id bigint NOT NULL AUTO_INCREMENT, primary key(id));
--insert into deleted_users (id) values (1);

-- Вспомогательные таблицы для организации связей между сущностями
-- связь пользователь - удаленный пользователь
create table link__deleted_users ( deleted_user_id BIGINT NOT NULL, user_id BIGINT NOT NULL, primary key (deleted_user_id, user_id),
FOREIGN KEY (deleted_user_id) REFERENCES deleted_users (id), FOREIGN KEY (user_id) REFERENCES users (id) );
--insert into link__deleted_users (deleted_user_id, user_id) values (1, 2);

-- связь пользователь - роль
create table link__users_roles ( user_id BIGINT NOT NULL, role_id BIGINT NOT NULL, primary key (user_id, role_id),
FOREIGN KEY (user_id) REFERENCES users (id), FOREIGN KEY (role_id) REFERENCES roles (id) );
insert into link__users_roles (user_id, role_id) values (1, 1), (1, 2), (1, 3);

-- связь товар - категория
create table link__products_categories (product_id bigint not null, category_id bigint not null, primary key(product_id, category_id),
foreign key (product_id) references products(id), foreign key (category_id) references categories(id));
insert into link__products_categories (product_id, category_id) values (1, 1), (2, 1), (3, 2);

-- связь товар - единица изерения
create table link__products_units (product_id bigint not null, unit_id bigint not null, primary key(product_id, unit_id),
foreign key (product_id) references products(id), foreign key (unit_id) references units(id));
insert into link__products_units values (1,1),(2,1),(3,1);

-- связь транзакция - товар
create table link__transactions_products (transaction_id bigint not null, product_id bigint not null, primary key(transaction_id, product_id),
foreign key (transaction_id) references product_transactions(id), foreign key (product_id) references products(id));
insert into link__transactions_products values (1, 1),(2,1);

-- связь транзакция - контрагент
create table link__transactions_contractors (transaction_id bigint not null, contractor_id bigint not null, primary key(transaction_id, contractor_id),
foreign key (transaction_id) references product_transactions(id), foreign key (contractor_id) references contractors(id));
insert into link__transactions_contractors values (1, 1),(2,2);

-- связь транзакция - пользователь приложения, проведший транзакцию
create table link__transactions_users (transaction_id bigint not null, user_id bigint not null, primary key(transaction_id, user_id),
foreign key (transaction_id) references product_transactions(id), foreign key (user_id) references users(id));
insert into link__transactions_users values (1, 1),(2,1);

-- связь товар - изображение
create table link__products_images (product_id bigint not null, image_id bigint not null, primary key(product_id, image_id),
foreign key (product_id) references products(id), foreign key (image_id) references images(id));

-- представления (view) данных
-- остатки товаров на складе
create view view__funds as
select
    prod.id,
    ifnull(sum(quantity),0) quantity
from link__transactions_products l_tp
join product_transactions trans on 1 = 1
right join products prod on 1 = 1
    and l_tp.product_id = prod.id
    and l_tp.transaction_id = trans.id
join units on 1 = 1
join link__products_units l_pu on 1 = 1
    and l_pu.product_id = prod.id
    and l_pu.unit_id = units.id
group by prod.id;