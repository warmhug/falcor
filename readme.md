## falcor-java

> 注意：`rxnetty-0.5.0-SNAPSHOT`包mvn里没有，`rxnetty-0.5.0-SNAPSHOT.zip`解压后使用。

1. test/Starter 启动页面server(端口8000)，rxnetty server(端口8900)。
2. 访问页面 [http://localhost:8000/](http://localhost:8000/)，打开控制台，看响应结果。
3. 修改`src/main/resources/templates/index.html`文件里`script`部分，实验不同请求下的不同响应结果。对应Java处理请求方法是`src/main/java/com/example/FalcorService.java`文件里`requestHandler`方法。

### [falcor](http://netflix.github.io/falcor/) 特点：
> [why falcor 视频](http://netflix.github.io/falcor/starter/why-falcor.html) (netflix 首席UI架构师)

- 结合了 Rest 和 Rpc 的优点。[Demand driven architecture (CQRS/Falcor)](http://www.javacodegeeks.com/2015/10/transcending-rest-and-rpc.html)。
    - rpc优缺点：低延迟，数据量小；不可缓存(手动管理)，紧耦合
    - rest优缺点：可缓存，松耦合；高延迟，数据量大
- **只有一个端点（一个接口）**，向这个接口请求不同的模型数据。（不需要向不同的rest端点请求不同的数据，减少约定接口的麻烦，rest接口很多、可能导致难以管理）
- 请求什么数据模型、返回什么数据。**The data is the API**。只获取你需要的数据，没有冗余、足够灵活。
- **客户端提供数据缓存、请求合并等功能**，减少服务端响应时间；也和前端`react.js`的模块化组织相匹配。

### 场景：
- 适用于移动端：不同移动终端、可以请求不同数据结构的数据，避免冗余。
- 适用于 Rest 架构的应用：替代 Rest，省去管理维护 rest api 的成本，前后端数据交互更方便，能减少前端http请求数。(Rest 仍适合于 SaaS 应用的 Open API 等场景)
