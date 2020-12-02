# 实现一个最简单的Servlet容器
目标：
- 在staticServer的基础上改造，使服务器可以处理对静态资源和servlet请求
- 静态资源 uri：/static/index.html; 在target/classes/static/目录下
- Servlet uri：/servlet/<servlet class name>，如/servlet/PrimitiveServlet; 在target/classes/WEB-INF/classes/目录下
- 需要javax.servlet包
- 实现功能即可，暂不需要完成所有接口的具体实现

## 改造思路
- Servlet类的service方法需要两个参数：javax.servlet.ServletRequest和javax.servlet.ServletResponse实例
- 让原来的Request实现ServletRequest接口，Response如此
- 创建两个类：StaticProcessor和ServletProcessor，在解析uri后，通过这两个类将逻辑分开，可以用单例模式
- Response中原本的send方法改为sendStatic，由StaticProcessor的实例来调用
- ServletProcessor实例使用ClassLoader载入请求的servlet，接着调用它的service方法
- Facade模式：在处理servlet请求的情况下，Request和Response有无关的公用方法，为了避免这些公用方法被后续调用，为这两个类准备门面类，对servlet仅暴露相关的接口

## Class UML
![servlet-01-classuml](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/muscaestar/WebServerForFun/master/src/main/java/servletServer01/servlet-01-classuml.puml)

## Sequence UML - 处理servlet请求
![servlet-01-sequml](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/muscaestar/WebServerForFun/master/src/main/java/servletServer01/servlet-01-sequml.puml)
