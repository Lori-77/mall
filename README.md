# ABC商城

Web框架课程实验项目，Spring Boot + Vue3 商城系统。

## 功能

- 商品浏览、搜索、排序、详情
- 用户注册、登录
- 购物车（localStorage 存储）
- 下单结算（模拟）
- 管理员后台：商品上下架、添加编辑删除商品、用户管理

## 技术栈

| 后端 | 前端 |
|------|------|
| Spring Boot 4 | Vue 3 |
| Spring Data JPA | Vue Router |
| MySQL | Axios |
| Lombok | Vite |

## 环境要求

- **JDK 17** 以上
- **MySQL 8.0**（或 5.7）
- **Node.js 18** 以上
- **Maven**（项目自带 mvnw，不需要装）

## 安装运行

### 1. 克隆项目

```bash
git clone https://github.com/Lori-77/mall.git
cd mall
```

### 2. 初始化数据库

打开 MySQL，执行 `db/init.sql`，或者手动：

```sql
CREATE DATABASE mall DEFAULT CHARSET utf8mb4;
```

然后导入：

```bash
mysql -u root -p mall < db/init.sql
```

### 3. 配置数据库连接

修改 `src/main/resources/application.yml`，改成你自己的 MySQL 用户名和密码：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mall?useSSL=false&serverTimezone=UTC
    username: root      # 改成你的
    password: 123456    # 改成你的
```

### 4. 启动后端

```bash
# Windows
mvnw.cmd spring-boot:run

# Mac / Linux
./mvnw spring-boot:run
```

后端跑在 `http://localhost:8080`

### 5. 启动前端

```bash
cd mall-vue
npm install    # 第一次需要装依赖
npm run dev
```

前端跑在 `http://localhost:5173`

### 6. 打开浏览器

访问 http://localhost:5173

- 管理员账号：`admin` / `123456`
- 也可以自己注册普通用户

## 项目结构

```
proj2-2
├── src/main/java/com/example/mall/
│   ├── MallApplication.java          # 启动类
│   ├── DataInitializer.java          # 初始化示例数据
│   ├── common/
│   │   ├── Result.java               # 统一返回格式
│   │   └── ResultCode.java           # 状态码
│   ├── entity/
│   │   ├── Product.java              # 商品实体
│   │   ├── User.java                 # 用户实体
│   │   └── Order.java                # 订单实体
│   ├── repository/
│   │   ├── ProductRepository.java
│   │   ├── UserRepository.java
│   │   └── OrderRepository.java
│   └── controller/
│       ├── ProductController.java    # 商品接口
│       ├── UserController.java       # 用户接口
│       └── OrderController.java      # 订单接口
├── mall-vue/
│   └── src/
│       ├── views/
│       │   ├── Home.vue              # 首页
│       │   ├── Product.vue           # 商品列表
│       │   ├── ProductDetail.vue     # 商品详情
│       │   ├── Login.vue             # 登录注册
│       │   ├── Cart.vue             # 购物车
│       │   ├── OrderConfirm.vue      # 确认订单
│       │   ├── UserCenter.vue        # 用户中心
│       │   └── Admin.vue             # 管理员后台
│       ├── components/
│       │   ├── Navbar.vue            # 导航栏
│       │   └── Footer.vue            # 页脚
│       └── store.js                  # 全局用户状态
├── db/
│   └── init.sql                      # 数据库初始化脚本
└── pom.xml
```

## 常见问题

**Q: 启动报错 "Access denied for user"**  
A: application.yml 里的数据库用户名或密码不对。

**Q: 前端页面没有数据**  
A: 检查后端是否启动成功（http://localhost:8080/products 能访问）。

**Q: 登录后导航栏没变化**  
A: 新版已修复，如果还有问题刷新一下浏览器。

**Q: 后台入口在哪里**  
A: 用 admin/123456 登录后，导航栏会多一个"后台"链接。
