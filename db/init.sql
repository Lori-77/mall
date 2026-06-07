-- ABC商城 数据库初始化脚本
-- 先创建数据库：CREATE DATABASE mall DEFAULT CHARSET utf8mb4;

USE mall;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL,
    `password` VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 商品表
CREATE TABLE IF NOT EXISTS `product` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL,
    `price` DOUBLE NOT NULL,
    `stock` INT DEFAULT 0,
    `image` VARCHAR(200) DEFAULT NULL,
    `description` VARCHAR(500) DEFAULT NULL,
    `status` INT DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 订单表
CREATE TABLE IF NOT EXISTS `orders` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `username` VARCHAR(50) DEFAULT NULL,
    `items` VARCHAR(1000) DEFAULT NULL,
    `total_price` DOUBLE DEFAULT NULL,
    `create_time` DATETIME DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 管理员账号（密码123456）
INSERT IGNORE INTO `user` (`username`, `password`) VALUES ('admin', '123456');

-- 示例商品
INSERT IGNORE INTO `product` (`name`, `price`, `stock`, `image`, `description`, `status`) VALUES
('机械键盘', 299, 50, '机械键盘.jpg', '青轴机械键盘，RGB背光，104键全键无冲', 1),
('游戏鼠标', 159, 100, '游戏鼠标.jpg', '电竞游戏鼠标，16000DPI，可编程按键', 1),
('游戏耳机', 399, 30, '耳机.jpg', '7.1虚拟环绕声游戏耳机，降噪麦克风', 1),
('高性能电脑', 5999, 10, '电脑.jpg', 'i7处理器，RTX4060显卡，16G内存，512G固态', 1);
