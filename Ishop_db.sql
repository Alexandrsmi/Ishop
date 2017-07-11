-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.7.16-log - MySQL Community Server (GPL)
-- Операционная система:         Win64
-- HeidiSQL Версия:              9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Дамп структуры базы данных shop
CREATE DATABASE IF NOT EXISTS `shop` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `shop`;



-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица shop.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `order_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `finished` enum('Y','N') NOT NULL DEFAULT 'N',
  `price` double NOT NULL DEFAULT '0',
  `products_count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`order_id`),
  KEY `FK_orders_users` (`user_id`),
  CONSTRAINT `FK_orders_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица shop.order_products
CREATE TABLE IF NOT EXISTS `order_products` (
  `order_product_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  PRIMARY KEY (`order_product_id`),
  UNIQUE KEY `product_id_order_id` (`product_id`,`order_id`),
  KEY `FK_order_products_orders` (`order_id`),
  CONSTRAINT `FK_order_products_orders` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `FK_order_products_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица shop.products
CREATE TABLE IF NOT EXISTS `products` (
  `product_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL DEFAULT '0',
  `short_description` varchar(250) NOT NULL DEFAULT '0',
  `full_description` varchar(5000) NOT NULL DEFAULT '0',
  `price` double NOT NULL DEFAULT '0',
  `count` bigint(20) NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=520 DEFAULT CHARSET=utf8;

-- Экспортируемые данные не выделены.
-- Дамп структуры для процедура shop.sp_orders_create
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_orders_create`(
	IN `_user_id` BIGINT

,
	OUT `_order_id` BIGINT

)
BEGIN
insert into orders (user_id)
values (_user_id);
select last_insert_id() into _order_id;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_orders_finish
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_orders_finish`(
	IN `_user_id` BIGINT
)
BEGIN
declare bucket_id BIGINT;
declare _products_count INT;
declare _order_price DOUBLE;
call sp_orders_get_or_create_bucket_id(_user_id,bucket_id);

select count(*), sum(order_products.count*price) into _products_count, _order_price
from order_products 
join products on order_products.product_id = products.product_id
where order_id = bucket_id
group by order_id;

update orders set finished = 'Y', products_count = _products_count, price = _order_price, order_date = now()
where order_id = bucket_id;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_orders_get
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_orders_get`(
	IN `_order_id` BIGINT

)
BEGIN
select product_id, count,order_id from order_products where order_id = _order_id;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_orders_get_bucket
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_orders_get_bucket`(
	IN `_user_id` BIGINT



)
BEGIN
declare bucket_id BIGINT;
call sp_orders_get_or_create_bucket_id(_user_id,bucket_id); 
call sp_orders_get (bucket_id);
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_orders_get_bucket_products_count
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_orders_get_bucket_products_count`(
	IN `_user_id` BIGINT
)
BEGIN
declare bucket_id BIGINT;
call sp_orders_get_or_create_bucket_id(_user_id,bucket_id); 
select count(*) as products_count from order_products where order_id = bucket_id;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_orders_get_history
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_orders_get_history`(
	IN `_user_id` BIGINT


)
BEGIN
select order_id, order_date, products_count, price
from orders
where user_id = _user_id 
and finished = 'Y';
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_orders_get_info
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_orders_get_info`(
	IN `_order_id` BIGINT

)
BEGIN
select order_id, order_date, products_count, price,user_id, finished = 'Y'
from orders
where order_id = _order_id ;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_orders_get_or_create_bucket_id
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_orders_get_or_create_bucket_id`(
	IN `_user_id` BIGINT
,
	OUT `_bucket_id` BIGINT


)
BEGIN
select order_id into _bucket_id from orders where user_id = _user_id
and finished = 'N';
if (_bucket_id is null) then
 	call sp_orders_create(_user_id, _bucket_id);
end if;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_order_products_add
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_order_products_add`(
	IN `_user_id` BIGINT,
	IN `_product_id` BIGINT,
	IN `_count` INT


)
BEGIN
declare bucket_id BIGINT;
call sp_orders_get_or_create_bucket_id(_user_id,bucket_id); 
insert into order_products(product_id,count,order_id) 
values (_product_id,_count,bucket_id);
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_order_products_delete
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_order_products_delete`(
	IN `_user_id` BIGINT,
	IN `_product_id` BIGINT

)
BEGIN
declare bucket_id BIGINT;
call sp_orders_get_or_create_bucket_id(_user_id,bucket_id); 
delete from order_products where order_id = bucket_id and product_id = _product_id;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_order_products_update_count
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_order_products_update_count`(
	IN `_user_id` BIGINT,
	IN `_product_id` BIGINT,
	IN `_count` INT
)
BEGIN
declare bucket_id BIGINT;
call sp_orders_get_or_create_bucket_id(_user_id,bucket_id);
update order_products set count = _count where order_id = bucket_id and product_id = _product_id;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_products_count
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_products_count`(

)
BEGIN
select count(*) as products_count
from products;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_products_create
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_products_create`(
	IN `_title` VARCHAR(100),
	IN `_short_description` VARCHAR(250),
	IN `_full_description` VARCHAR(5000),
	IN `_price` DOUBLE,
	IN `_count` BIGINT
,
	OUT `_product_id` BIGINT
)
BEGIN
insert into products 
(title,short_description,full_description,price,count)
values
(_title,_short_description,_full_description,_price,_count);
select last_insert_id() into _product_id;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_products_delete
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_products_delete`(
	IN _product_id BIGINT
)
BEGIN
delete 
from products
where product_id=_product_id;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_products_get_by_id
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_products_get_by_id`(
   IN `_product_id` BIGINT

)
BEGIN
select  
product_id,
title,
short_description,
full_description,
price,
count,
create_datetime
from products
Where
product_id=_product_id;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_products_list
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_products_list`(
	IN `_offset` BIGINT,
	IN `_max_count` BIGINT

,
	IN `_sort_field` ENUM('no','name','price')
)
BEGIN

select  
product_id,
title,
short_description,
full_description,
price,
count,
create_datetime
from products
order by
	case 
		when _sort_field = 'name' then title 
		when _sort_field = 'price' then price 
		else create_datetime
	end
limit _offset, _max_count;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_products_update
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_products_update`(
   IN `_product_id` BIGINT,
	IN `_title` VARCHAR(100),
	IN `_short_description` VARCHAR(250),
	IN `_full_description` VARCHAR(5000),
	IN `_price` DOUBLE,
	IN `_count` BIGINT

)
BEGIN
update products 
set
title = _title,
short_description=_short_description,
full_description=_full_description,
price=_price,
count=_count
Where
product_id=_product_id;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_users_create
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_users_create`(
	IN `_first_name` VARCHAR(50),
	IN `_last_name` VARCHAR(50),
	IN `_email` VARCHAR(50),
	IN `_password` VARCHAR(50)
,
	OUT `_user_id` BIGINT
)
BEGIN
insert into users 
(first_name,last_name,email,password)
values
(_first_name,_last_name,_email,_password);
select last_insert_id() into _user_id;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_users_exists_email
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_users_exists_email`(
	IN `_email` VARCHAR(50)

)
BEGIN
select count(*)=1 as exists_email
from users
where email=_email;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_users_get_by_email
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_users_get_by_email`(
	IN `_email` VARCHAR(50),
	IN `_password` VARCHAR(50)


)
BEGIN
select user_id, first_name, last_name, email, password, state
from users
where email=_email and password=_password;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_users_get_by_id
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_users_get_by_id`(
	IN `_user_id` BIGINT(20)

)
BEGIN
select user_id, first_name, last_name, email, password,state
from users
where user_id=_user_id;
END//
DELIMITER ;

-- Дамп структуры для процедура shop.sp_users_update
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_users_update`(
	IN  `_user_id` BIGINT(20),
	IN `_first_name` VARCHAR(50),
	IN `_last_name` VARCHAR(50),
	IN `_email` VARCHAR(50),
	IN `_password` VARCHAR(50)

)
BEGIN
update users 
set first_name=_first_name,
last_name=_last_name,
email=_email,
password=_password
where user_id =_user_id;
END//
DELIMITER ;

-- Дамп структуры для таблица shop.users
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `state` enum('ADMIN','CUSTOMER') NOT NULL DEFAULT 'CUSTOMER',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8;

-- Экспортируемые данные не выделены.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
