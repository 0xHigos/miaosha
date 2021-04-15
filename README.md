# miaosha

本文章基础思路来自于慕课网若鱼1919老师 ( https://coding.imooc.com/class/168.html)

技术栈： springboot + redis + rabbitMQ + nginx + mysql

学习目的：(应对大并发)

+ 如何利用缓存
+ 如何使用异步
+ 如何编写优雅的代码

章节目录：

##### 第一章 :框架搭建

+ spring boot项目框架搭建

+ 集成thymeleaf,result结果封装
+ 集成Mybatis + druid
+ 集成jedis + redis 安装 + 通用缓存key封装

##### 第二章：实现登录功能

+ 数据库设计
+ 明文密码两次md5处理
+ JSR303 参数检验 + 全局异常处理器
+ 分布式Session

##### 第三章：实现秒杀功能

+ 数据库设计
+ 商品列表页
+ 商品详情页
+ 订单详情页

##### 第四章：Jmeter 压测

+ Jmeter
+ 自定义变量模拟多用户
+ Jmeter命令行使用
+ Spring boot 打war包

##### 第五章 :页面优化技术

+ 页面缓存 + URL缓存 + 对象缓存
+ 页面静态化，前后端分离
+ 静态资源优化
+ cdn优化

##### 第六章：接口优化

+ Redis预减库存减少数据库访问
+ 内存标记减少Redis访问
+ RabbitMQ队列缓存，异步下单
+ RabbitMQ安装与Spring boot 集成
+ 访问nginx水平扩展
+ 压测

##### 第七章：安全优化

+ 秒杀接口地址隐藏

+ 数学公式验证码

+ 接口防刷

  



