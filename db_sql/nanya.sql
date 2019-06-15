DROP DATABASE IF EXISTS `nanya`; 
CREATE DATABASE `nanya`;

USE `nanya`;

/* 管理员表 */
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` varchar(32) NOT NULL,
  `admin_name` varchar(25) NOT NULL UNIQUE,
  `password` varchar(25) NOT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `profile` varchar(300) DEFAULT NULL,
  `head_sculpture_path` varchar(200) DEFAULT NULL,
  `head_sculpture_url` varchar(200) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `previous_login_time` timestamp NULL,
  `current_login_time` timestamp NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
*商品表
*/
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
	`product_id` varchar(32) NOT NULL,
	`product_name` varchar(30) NOT NULL UNIQUE,
	`product_description` varchar(200) DEFAULT '暂无商品描述',
	`product_original_price` decimal(10,2) NOT NULL COMMENT '原价',
	`product_discount_price` decimal(10,2) COMMENT '折扣价',
	`product_stock` bigint NOT NULL COMMENT '库存',
	`flower_material` varchar(100) NOT NULL COMMENT '花材',
	`product_package` varchar(100) NOT NULL COMMENT '包装',
	`product_scene` varchar(100) NOT NULL COMMENT '场景',
	`distribution` varchar(100) NOT NULL COMMENT '配送',
	`sale_volume` bigint NOT NULL DEFAULT 0 COMMENT '销量',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`product_id`),
	FULLTEXT KEY `search` (`product_name`,`product_description`,`flower_material`,`product_scene`,`product_package`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
*商品图片表
*/
DROP TABLE IF EXISTS `product_image`;
CREATE TABLE `product_image` (
	`product_image_id` varchar(32) NOT NULL,
	`product_id` varchar(32) NOT NULL,
	`image_path` varchar(200) NOT NULL,
	`image_url` varchar(200) NOT NULL,
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`product_image_id`),
	CONSTRAINT `FK_PRODUCT_ID` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
*分类表
*/
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
	`category_id` varchar(32) NOT NULL,
	`category_name` varchar(20) NOT NULL UNIQUE,
	`category_description` varchar(50),
	`image_path` varchar(200),
	`image_url` varchar(200),
	`need_show_in_home` bit(1) NOT NULL,
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
*分类商品中间表
*/
DROP TABLE IF EXISTS `category_product`;
CREATE TABLE `category_product`(
	`category_id` varchar(32) NOT NULL,
	`product_id` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table `category_product` add CONSTRAINT `FK_CATEGORY_ID` FOREIGN KEY (`category_id`) REFERENCES `category`(`category_id`) ON DELETE CASCADE;
alter table `category_product` add CONSTRAINT `FK_PRODUCT_ID_001` FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`) ON DELETE CASCADE;

/*
*用户表
*/
DROP TABLE IF EXISTS `t_wx_user`;
CREATE TABLE `t_wx_user` (
  `openid` char(28) NOT NULL COMMENT '小程序用户openid',
  `nickname` varchar(100) DEFAULT 'noOne' COMMENT '用户昵称',
  `avatarurl` varchar(1000) DEFAULT 'https://www.ailejia.club/images/smile.jpg' COMMENT '用户头像',
  `gender` varchar(1) DEFAULT '0' COMMENT '性别   0 未知 1  男  2 女  ',
  `country` varchar(100) DEFAULT NULL COMMENT '所在国家',
  `province` varchar(100) DEFAULT NULL COMMENT '省份',
  `city` varchar(100) DEFAULT '' COMMENT '城市',
  `language` varchar(100) DEFAULT NULL,
  `ctime` int(11) DEFAULT NULL COMMENT '创建时间',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机类型',
  `telnum` char(13) DEFAULT NULL COMMENT '手机号码',
  `session_key` varchar(50) DEFAULT NULL,
  `third_session` varchar(50) DEFAULT NULL,
  `unionid` varchar(50) DEFAULT NULL,
  `birthday` varchar(50) DEFAULT NULL,
  `location` varchar(100) DEFAULT 'miles away',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`openid`),
  KEY `3rdSession` (`third_session`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
*订单表
*/
DROP TABLE IF EXISTS `wx_order`;
CREATE TABLE `wx_order` (
	`order_id` varchar(32),
	`openid` varchar(28) NOT NULL,
	`order_number` varchar(18) NOT NULL UNIQUE COMMENT '订单编号',
	`pay_amount` decimal(12,2) NOT NULL COMMENT '支付金额',
	`pay_status` varchar(25) NOT NULL COMMENT '支付状态',
	`order_status` varchar(25) NOT NULL COMMENT '订单状态',
	`area` varchar(100) NOT NULL COMMENT '地区',
	`address_detail` varchar(100) NOT NULL COMMENT '详细地址',
	`username` varchar(50) NOT NULL COMMENT '用户名',
	`phone_number` varchar(11) NOT NULL COMMENT '手机号码',
	`product_id` varchar(32) NULL COMMENT '外键，商品id',
	`product_name` varchar(30) NOT NULL COMMENT '商品名称',
	`product_description` varchar(200) DEFAULT '暂无商品描述' COMMENT '商品描述',
	`product_price` decimal(10,2) NOT NULL COMMENT '商品价格',
	`product_image_url` varchar(200) NOT NULL COMMENT '商品图片',
	`buy_amount` int UNSIGNED NOT NULL COMMENT '购买数量 ',
	`expected_delivery_time` varchar(50) NOT NULL COMMENT '期望配送时间',
	`label` varchar(20) COMMENT '标签',
	`remark` varchar(100) COMMENT '备注',
	`leave_message` varchar(50) COMMENT '留言',
	`tracking_number` varchar(50) COMMENT '快递单号',
	`order_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
	`pay_time` timestamp NULL COMMENT '支付时间',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table `wx_order` add constraint `FK_PRODUCT_ID_002` foreign key(`product_id`) references `product`(`product_id`) on delete set null;
alter table `wx_order` add constraint `FK_OPEN_ID_001` foreign key(`openid`) references `t_wx_user`(`openid`);

/*
*地址表
*/
DROP TABLE IF EXISTS `t_wx_address`;
CREATE TABLE `t_wx_address` (
  `openid` varchar(32) DEFAULT NULL COMMENT '买家openid',
  `telnum` varchar(15) DEFAULT NULL COMMENT '电话号码',
  `location` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT '',
  `detail_add` varchar(100) DEFAULT NULL COMMENT '收件人详细地址',
  `username` varchar(50) DEFAULT NULL,
  `id` varchar(255) NOT NULL DEFAULT '' COMMENT '地址编号',
  PRIMARY KEY (`id`),
  KEY `openid` (`openid`) USING BTREE,
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
*用户地址关系表
*/
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address` (
	`openid` varchar(28),
	`address_id` varchar(255) NOT NULL,
	PRIMARY KEY(`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table `user_address` add constraint `FK_ADDRESS_ID` foreign key(`address_id`) references `t_wx_address`(`id`) on delete cascade;

/*
*评论表
*/
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `comment_number` varchar(32) NOT NULL COMMENT '评论编号',
  `openid` varchar(32) NOT NULL COMMENT '用户唯一编号',
  `product_id` varchar(32) NOT NULL COMMENT '商品唯一编号',
  `product_image` varchar(255) DEFAULT NULL,
  `image_urls` varchar(1024) DEFAULT NULL,
  `text` varchar(256) DEFAULT NULL COMMENT '评论内容',
  `value` varchar(1) DEFAULT NULL COMMENT '星级',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `order_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`comment_number`,`openid`,`product_id`),
  KEY `FKbss5unl812ghmkqna18i8kats` (`product_id`),
  CONSTRAINT `FKbss5unl812ghmkqna18i8kats` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 已被废弃（只能针对一对多的情况）
DROP VIEW IF EXISTS `mini_program_home_page_view`;
CREATE VIEW `mini_program_home_page_view` AS
    SELECT 
        `c`.`category_id` AS `categoryId`,
        `c`.`category_name` AS `categoryName`,
        `c`.`category_description` AS `categoryDescription`,
        `c`.`image_url` AS `imageUrl`,
        `p`.`product_id` AS `productId`,
        `p`.`product_name` AS `productName`,
        `p`.`product_description` AS `productDescription`,
        `p`.`product_original_price` AS `productOriginalPrice`,
        `p`.`product_discount_price` AS `productDiscountPrice`,
        `pi`.`image_url` AS `productImageUrl`
    FROM
        (((`nanya`.`category` `c`
        JOIN `nanya`.`category_product` `cp` ON ((`c`.`category_id` = `cp`.`category_id`)))
        JOIN `nanya`.`product` `p` ON ((`cp`.`product_id` = `p`.`product_id`)))
        LEFT JOIN (SELECT 
            `pi1`.`product_id` AS `product_id`,
                (SELECT 
                        `pi2`.`image_url`
                    FROM
                        `nanya`.`product_image` `pi2`
                    WHERE
                        (`pi2`.`product_id` = `pi1`.`product_id`)
                    ORDER BY `pi2`.`create_time`
                    LIMIT 1) AS `image_url`
        FROM
            `nanya`.`product_image` `pi1`
        GROUP BY `pi1`.`product_id`) `pi` ON ((`p`.`product_id` = `pi`.`product_id`)))
    WHERE
        ((`c`.`need_show_in_home` = TRUE)
            AND FIND_IN_SET(`p`.`product_id`,
                (SELECT 
                        GROUP_CONCAT(`result`.`id`
                                SEPARATOR ',') AS `id`
                    FROM
                        (SELECT 
                            SUBSTRING_INDEX(GROUP_CONCAT(`p1`.`product_id`
                                    ORDER BY `p1`.`sale_volume` DESC
                                    SEPARATOR ','), ',', 6) AS `id`
                        FROM
                            (`nanya`.`product` `p1`
                        JOIN `nanya`.`category_product` `cp1` ON ((`p1`.`product_id` = `cp1`.`product_id`)))
                        GROUP BY `cp1`.`category_id`) `result`)));

-- 查询单个分类的商品信息	
DROP PROCEDURE IF EXISTS `category_sp`;
delimiter //
CREATE PROCEDURE `category_sp`(IN categoryId varchar(32), IN pageSize int)
BEGIN
    IF pageSize is null then 
		SELECT 
        `c`.`category_id` AS `categoryId`,
        `c`.`category_name` AS `categoryName`,
        `c`.`category_description` AS `categoryDescription`,
        `c`.`image_url` AS `imageUrl`,
        `p`.`product_id` AS `productId`,
        `p`.`product_name` AS `productName`,
        `p`.`product_description` AS `productDescription`,
        `p`.`product_original_price` AS `productOriginalPrice`,
        `p`.`product_discount_price` AS `productDiscountPrice`,
        `pi`.`image_url` AS `productImageUrl`
		FROM
			(((`nanya`.`category` `c`
			JOIN `nanya`.`category_product` `cp` ON ((`c`.`category_id` = `cp`.`category_id`)))
			JOIN `nanya`.`product` `p` ON ((`cp`.`product_id` = `p`.`product_id`)))
			LEFT JOIN (SELECT 
				`pi1`.`product_id` AS `product_id`,
					(SELECT 
							`pi2`.`image_url`
						FROM
							`nanya`.`product_image` `pi2`
						WHERE
							(`pi2`.`product_id` = `pi1`.`product_id`)
						ORDER BY `pi2`.`create_time`
						LIMIT 1) AS `image_url`
			FROM
				`nanya`.`product_image` `pi1`
			GROUP BY `pi1`.`product_id`) `pi` ON ((`p`.`product_id` = `pi`.`product_id`)))
		where c.category_id = categoryId
		order by p.sale_volume desc;
    else
		SELECT 
        `c`.`category_id` AS `categoryId`,
        `c`.`category_name` AS `categoryName`,
        `c`.`category_description` AS `categoryDescription`,
        `c`.`image_url` AS `imageUrl`,
        `p`.`product_id` AS `productId`,
        `p`.`product_name` AS `productName`,
        `p`.`product_description` AS `productDescription`,
        `p`.`product_original_price` AS `productOriginalPrice`,
        `p`.`product_discount_price` AS `productDiscountPrice`,
        `pi`.`image_url` AS `productImageUrl`
		FROM
			(((`nanya`.`category` `c`
			JOIN `nanya`.`category_product` `cp` ON ((`c`.`category_id` = `cp`.`category_id`)))
			JOIN `nanya`.`product` `p` ON ((`cp`.`product_id` = `p`.`product_id`)))
			LEFT JOIN (SELECT 
				`pi1`.`product_id` AS `product_id`,
					(SELECT 
							`pi2`.`image_url`
						FROM
							`nanya`.`product_image` `pi2`
						WHERE
							(`pi2`.`product_id` = `pi1`.`product_id`)
						ORDER BY `pi2`.`create_time`
						LIMIT 1) AS `image_url`
			FROM
				`nanya`.`product_image` `pi1`
			GROUP BY `pi1`.`product_id`) `pi` ON ((`p`.`product_id` = `pi`.`product_id`)))
		where c.category_id = categoryId
		order by p.sale_volume desc
		limit pageSize;
    end if;
END;

-- 查询带单图片的商品信息
DROP VIEW IF EXISTS `product_with_single_image`;
CREATE VIEW `product_with_single_image` AS
    SELECT 
        `p`.`product_id` AS `productId`,
        `p`.`product_name` AS `productName`,
        `p`.`product_description` AS `productDescription`,
        `p`.`product_original_price` AS `productOriginalPrice`,
        `p`.`product_discount_price` AS `productDiscountPrice`,
        `p`.`product_stock` AS `productStock`,
        `p`.`flower_material` AS `flowerMaterial`,
        `p`.`product_package` AS `productPackage`,
        `p`.`product_scene` AS `productScene`,
        `p`.`distribution` AS `distribution`,
        `p`.`sale_volume` AS `sale_volume`,
        (SELECT 
                `pi`.`image_url`
            FROM
                `product_image` `pi`
            WHERE
                (`pi`.`product_id` = `p`.`product_id`)
            ORDER BY `pi`.`create_time`
            LIMIT 1) AS `imageUrl`
    FROM
        `product` `p`;

-- 评论视图
DROP VIEW IF EXISTS `v_comment`;
CREATE VIEW `v_comment` AS
    SELECT 
        `c`.`image_urls` AS `image_urls`,
        `c`.`product_image` AS `product_image`,
        `c`.`value` AS `value`,
        `c`.`update_time` AS `update_time`,
        `c`.`text` AS `text`,
        `u`.`nickname` AS `nickname`,
        `u`.`avatarurl` AS `avatarurl`,
        `u`.`location` AS `location`
    FROM
        (`t_comment` `c`
        LEFT JOIN `t_wx_user` `u` ON ((`c`.`openid` = `u`.`openid`)))
    ORDER BY `c`.`update_time` DESC;
	
INSERT INTO `admin`(`admin_id`, `admin_name`, `password`) VALUE(replace(uuid(), '-', ''), 'nanya', 'nanya');