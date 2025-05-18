# RedisComment

---

## 🌟 项目概述

RedisComment 是一个集**餐厅/商铺评分、折扣券购买、社交互动**于一体的综合性本地生活服务平台。用户可以：

- 浏览店铺信息（支持地理位置查询）
- 购买普通优惠券和限时抢购（Seckill）优惠券
- 发布、点赞、浏览博客动态
- 关注其他用户，构建社交关系网络

本系统采用了高并发架构设计，结合 **Redis 缓存、RabbitMQ 异步处理、分布式锁等技术**，保障了系统的高性能与稳定性。

---

## 🧱 技术栈

| 模块             | 技术/框架                                 | 说明           |
|------------------|---------------------------------------|--------------|
| 后端框架         | Spring Boot 2.3.12                    | 构建服务核心       |
| 数据库           | MySQL 5.1.47                          | 存储持久化数据      |
| ORM 框架         | MyBatis-Plus 3.4.3                    | 简化数据库操作      |
| 缓存             | Redis + Redisson + Lettuce + Caffeine | 高性能多级缓存与分布式锁 |
| 消息队列         | RabbitMQ                              | 异步订单处理       |
| 工具类           | Hutool 5.7.17                         | Java 工具封装    |
| 配置管理         | application.yaml                      | 应用配置文件       |
| Maven 依赖管理   | pom.xml                               | 项目依赖管理       |

---

## 🗺️ 系统架构概览

### 🧩 核心模块

| 模块名称        | 功能说明                                                   |
|-----------------|------------------------------------------------------------|
| **UserService** | 用户注册、登录、token管理、个人资料维护                    |
| **ShopService** | 商铺信息展示、多级缓存策略、地理空间搜索                   |
| **VoucherService** | 优惠券管理，包括普通券与高并发抢购券（Seckill）            |
| **VoucherOrderService** | 订单生成、异步下单流程（通过 RabbitMQ 实现）              |
| **BlogService** | 博客发布、浏览、点赞功能                                   |
| **FollowService** | 用户之间的关注与取消关注，社交关系维护                     |

---

## 📁 项目结构简述

```
src/
├── main/
│   ├── java/
│   │   └── com/hmdp/
│   │       ├── config/          配置类（如 Redis、Cache、Spring Security）
│   │       ├── controller/      REST API 控制器
│   │       ├── service/         业务逻辑层接口及实现
│   │       ├── mapper/          MyBatis Plus 映射接口
│   │       ├── entity/          数据实体类（对应数据库表）
│   │       ├── utils/           工具类（如 Redis 缓存工具、Token 工具）
│   │       └── Application.java 主启动类
│   └── resources/
│       ├── application.yaml     配置文件（数据库、Redis、日志等）
│       └── mapper/              XML 映射文件
└── test/                        单元测试
```

---

## 🔑 核心技术亮点

### ✅ 多级缓存机制（防穿透、击穿、雪崩）

- 使用 **Redis 缓存 + 互斥锁 + TTL 随机化**
- 支持逻辑过期时间策略，应对热点数据访问
- 提供空值缓存防止缓存穿透
- 使用Caffeine作为多级缓存框架,让热点数据读取速度更快
### ⚡ 高并发秒杀券处理

- 利用 **Redis 原子操作 + Lua 脚本** 控制库存
- 分布式锁防止超卖问题
- RabbitMQ 异步处理订单落库，提升响应速度

### 💬 社交功能优化

- 使用 Redis Sorted Set 实现博客点赞计数
- 利用 Feed 流机制为用户推荐个性化内容
- Follow 关系存储在 Redis 中，便于快速读取

### 📍 地理位置服务优化

- 使用 Redis GeoHash 实现附近店铺搜索
- 支持基于经纬度的周边店铺查找

---

## 📦 数据模型

主要实体及其关系如下：

- **User**: 用户信息（手机号、昵称、密码等）
- **Shop**: 商铺信息（类型、地址、坐标等）
- **Voucher / SeckillVoucher**: 优惠券信息（价格、库存、有效期）
- **VoucherOrder**: 用户下单记录
- **Blog**: 用户发布的博客动态
- **Follow**: 用户之间的关注关系

完整 ER 图请参考项目文档或数据库设计页面。

---

## 🛠️ 部署与运行

### 1. 依赖安装

确保已安装以下组件：

- JDK 8+
- MySQL 5.7+
- Redis 6.x+
- RabbitMQ 3.x+

### 2. 初始化数据库

执行 SQL 文件初始化表结构：

```sql
-- 可在 src/main/resources/sql/init.sql 中找到建表语句
```

### 3. 修改配置文件

编辑 `application.yaml`，配置数据库、Redis、RabbitMQ 等参数。

### 4. 启动项目

使用 Maven 构建并运行主类：

```bash
mvn clean package
java -jar target/hm-dianping.jar
```

或者直接使用 IDE（如 IntelliJ IDEA）运行 `Application.java`

---

## 📚 文档链接（内部 Wiki）

更多详细设计文档，请参考以下页面：

- [用户系统](#)
- [优惠券系统（含 Seckill）](#)
- [博客与社交功能](#)
- [Redis 使用详解](#)
- [商铺管理系统](#)

---

## 📌 开发建议

- 所有新增接口需编写 Swagger 注解
- Redis Key 命名规范见 `RedisConstants.java`
- 敏感操作需加入日志审计
- 高并发场景务必使用分布式锁保护关键资源

---

## 📝 致谢

感谢阿里巴巴云 Qwen 团队提供技术支持与文档辅助！

--- 

如有任何问题或建议，请提交 Issue 或联系项目负责人。欢迎贡献代码！🌟
