# star-zone
## 项目介绍：
后端：Springboot+Mybatis+Maven+Jpa+GitHub+Nginx
前端：React+Dva+umi2.0+Es6+AntMobile+Echarts
### 技术
2019/4/13  2019/9/14
1.整合双数据源，支持多个数据源接入
2.支持代码生成，将更多精力放在业务开发上，开发更迅速
3.支持根据环境打jar包，只要后端打包执行：package -P prd 就会生成jar包，并且会跟前端的一起打，就是说打出来的包是包括前后端的。
4.支持swagger接口测试，swagger访问地址：http://localhost:9000/swagger-ui.html
5.解决前后端工程跨域访问问题
6.都是RESTful 风格的接口，直接返回json给前端
7.支持查询日志输出
8.用nginx做图片服务器
9.修改之前版本的在nginx上部署前端工程，通过反向代理的方式去调后端接口，现在前后端都在一个包下，所以用同一个端口，不需要反向代理
10.支持shell脚本管理应用：sh app.sh [start|stop|restart|status]
11.部署到阿里云服务器
采坑：
1.当dao层继承了JpaRepository后，自定义持久化接口会报错，会被校验说没按Jpa的规则命名什么的。加@NoRepositoryBean注解可以解决。
2.分页用的是github的pagehelper分页插件，要把它默认的分页插件(pagehelper-spring-boot-starter这个依赖，提供了自动配置分页插件的功能，要排除这个自动配置，用自己在多数据源中配置的分页)去掉：在项目启动类上加上：exclude = PageHelperAutoConfiguration.class。
3.引用自定义jar包（非maven库中的），打包时不会被打到jar包中。把这些jar包放在resources下面，在prom文件中引用。
4.头像涉及到图片上传：如果将base64转出来的图片信息存到数据库，那太大了，虽然这样讲返回的数据放在图片的src下可以显示图片，后面用nginx做图片服务器可解决。
5.选择组件：蚂蚁金服提供的时间选择组件，只能选到前后20年的。其他的选择组件为了样式好看，都是用一个input框去代替，点下去展示选择组件，在手机上这样会导致界面既显示选择组件也展示键盘。在onFocus方法中，加 document.activeElement.blur()，formItem里加 readOnly
6.导出：数据量不大可以用js-export-excel插件
7.输入时键盘挡住输入框：用document.getElementById(value).scrollIntoView()
8.滑动弹框界面，下面的页面也会滚动。写一个toggleBody方法，弹框时给body加固定样式，关闭弹框时去掉该样式。
9.shell脚本要在linux界面中编辑，外面写好上传到服务器会有问题，脚本写好后要先授权。
10.配置多环境打包后，在启动项目的arguments中要配置: -Dspring.profiles.active=dev 这样开发启动项目时才会去加载开发环境配置
11.resources/static静态资源路径的配置，不要把static那层再加上，不然前端打出来的包资源引用会有问题，找不到资源
12.这样前后端一起打包，要写一个访问首页资源的controller，然后跳到这个index界面，这里要引入模板引擎，不然没法访问首页。
13.有了模板引擎，也写了访问首页的action后，是跳到了index界面，但是会出现标签元素没闭合的问题，因为前段打出来的是H5页面，所以这里要去掉标签闭合的检测
14.打包：之前前后端一起打包maven配置搞不懂，就分开部署，用nginx做反向代理，后面解决了，其实就是将前端工程放在后端工程里面，然后在prom文件中可以配置打包的时候跟前端的一起打，然后将前端打出来的资源一起再打进到jar包的static目录下。
15.部署：阿里云服务器上也要装nodejs最好版本跟开发环境一样。
16.springboot项目部署到阿里云，启动后第一次post请求很慢。打开$JAVA_HOME/jre/lib/security/java.security这个文件将securerandom.source=file:/dev/urandom改成： securerandom.source=file:/dev/./urandom
#####打包部署：本地：通过配置Nginx，将前端打出来的文件放在Nginx的HTML目录下，然后将打成的jar包项目启动。
###多环境配置：
打生产包：package -P prd
开发环境需右键springboot启动类中，Run As --> Run Configurations...，在JRE选项卡中添加VM arguments
添加的参数为：-Dspring.profiles.active=dev
###打完包部署：
1.将打好的jar包放到服务器某个路径下
2.将resources/deploy下的app.sh文件放在jar包同一目录下
      注意：
              编写app.sh文件步骤（第一次创建应用控制脚本时）：
               1.进入jar包目录
               2.touch app.sh
               3.vim app.sh
               4.将app.sh中的代码复制进去，保存并退出。
                                                   第一次创建时不能在外面建好然后上传到服务器，在外面创建的文件会有问题。
                                                   该app.sh创建好后，后面那个应用要用就直接复制过去，执行前记得先授权：chmod 770 app.sh
                                                   授权后文件的颜色是绿色的。
3.进入该路径
4.输入：   sh app.sh start 启动项目(会自动生成catalina.log(文件名称可以自行修改脚本) 文件可供查阅日志)
       sh app.sh status 查看项目状态（running or not running）
       sh app.sh stop 会杀掉当前目录下的服务进程
       sh app.sh restart 平滑重启服务
       tail -f catalina.log  查阅实时日志（这是脚本默认生成的日志，本工程的设置的日志在工程项目目录下的logs/star-zone-info.log中）
###功能：
1.实现记账管理（分为九大类），自定义动态添加
2.总的消费统计，各小类的统计
3.实现我帮你选，类似抽奖的效果，实现自定义添加主题，与主题配置项配置
4.实现图片上传、回显
5.实现数据导出（前端导出）
6.界面优化：键盘遮住输入框、弹框界面滑动，底层也会滑动
7.上拉刷新页面，下拉加载更多
8.为了保障用户信息的安全，左右注册用户的用户名，密码，秘钥都是加密存储的，秘钥相当于登入时用户给的动态的盐。
       