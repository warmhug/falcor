## falcor-java

> 注意：`rxnetty-0.5.0-SNAPSHOT`包mvn里没有，`rxnetty-0.5.0-SNAPSHOT.zip`解压后使用。

1. test/Starter 启动页面server(端口8000)，rxnetty server(端口8900)
2. 访问页面 [http://localhost:8000/](http://localhost:8000/)，打开控制台，看响应结果。

### [falcor](http://netflix.github.io/falcor/) 特点：
> [why falcor 视频](http://netflix.github.io/falcor/starter/why-falcor.html) (netflix 首席UI架构师架构)

- 结合了 Rest 和 Rpc 的优点。[Demand driven architecture (CQRS/Falcor)](http://www.javacodegeeks.com/2015/10/transcending-rest-and-rpc.html)。
    - rpc优却点：低延迟，数据量小；不可缓存(手动管理)，紧耦合
    - rest优却点：可缓存，松耦合；高延迟，数据量大
- **只有一个端点（一个接口）**，向这个接口请求不同的模型数据。（不需要向不同的rest端点请求不同的数据，减少约定接口的麻烦，rest接口很多、可能导致难以管理）
- 请求什么数据模型、返回什么数据。**The data is the API**。只获取你需要的数据，没有冗余、足够灵活。
- **客户端提供数据缓存、请求合并等功能**，减少服务端响应时间；也和前端`react.js`的模块化组织相匹配。