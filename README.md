#keep learning...

###init project repository  
> 1.git网站上创建远程仓库  
> 2.复制ssh地址 本地仓库管理远程仓库  
> 3.gitignore 文件用于过滤 提交文件  

###git命令
>* `touch README.md` 创建文件
>* `git init` #初始化项目
>* `git status` #查看状态
>* `git add .` #添加变更文件到本地仓库
>* `git commit -am"XXXXX"` #提交到本地仓库+注释
>* `git remote add origin` #要关联一个远程库复制ssh地址跟在后面
>* `git push origin HEAD -u` #推送本地分支到 远程仓库  
>* `git pull` #拉取git网站上的代码
>* `git push -u origin master` #推送代码到分支
>* `git push -u -f origin master` #如果上面没成功 采用这个强制更新
>* `git log` #可查看历史记录 
>* `git diff HEAD` -- 文件名  #查看工作区和版本库中文件的区别 
>* `git checkout` -- 文件名 #可以撤销工作区的修改 总之，就是让这个文件回到最近一次git commit或git add时的状态
>* `git reset HEAD file`  #可以把暂存区的修改撤销掉（unstage），重新放回工作区

####分支操作
>* `git branch` 查看分支：
>* `git branch -a`  查看远程仓库和本地分支
>* `git branch branch-name` 创建分支：
>* `git checkout branch-name` 切换分支：
>* `git checkout -b branch-name` 创建+切换分支：
>* `git branch -d branch-name` 删除分支：
>* `git branch -r -d origin/branch-name`  删除远程仓库的分支   
>* `git log --graph` 命令可以看到分支合并图
>* `git merge branch-name` 合并某分支到当前分支：
>* `git merge --no-ff -m "merge with no-ff" dev` 加上--no-ff参数就可以用普通模式合并，合并后的历史有分支，能看出来曾经做过合并，而fast forward合并就看不出来曾经做过合并。
>* `git stash` 当临时要处理其他工作例如bug，手头的事情又没办法提交到远程仓库,可使用此命令
>* `git stash list`  查看列表
>* `git stash apply` 恢复，但是恢复后，stash内容并不删除
>* `git stash drop` 删除
>* `git stash pop`  当结束bug修复后可调用下一个命令回到未完成的工作中，恢复的同时把stash内容也删了
>* `git stash apply stash@{0}` 多次stash，恢复的时候，先用git stash list查看，然后恢复指定的stash，用此命令
>* `git branch -D branch-name` 开发一个新feature，最好新建一个分支； 如果要丢弃一个没有被合并过的分支，强行删除.
>* `git remote -v` 获取远程仓库信息
>* `git branch --set-upstream branch-name origin/branch-name` 建立本地分支和远程分支的关联

####多人协作的工作模式通常是这样：
> 1.首先，可以试图用git push origin branch-name推送自己的修改；
> 2.如果推送失败，则因为远程分支比你的本地更新，需要先用git pull试图合并；
> 3.如果合并有冲突，则解决冲突，并在本地提交；
> 4.没有冲突或者解决掉冲突后， 再用git push origin branch-name推送就能成功！
> 5.如果git pull提示“no tracking information”，则说明本地分支和远程分支的链接关系没有创建，用命令git branch --set-upstream branch-name origin/branch-name

#### 标签
>* `git tag branch-name `
>* `git tag <name>`用于新建一个标签
>* `git tag v0.9 6224937` 对指定commit id 打标签
>* `git show <tagname>` 查看指定标签的信息  
>*  需要注意不能与分支重名否则push不上
####别名--偷懒
>* `git config --global alias.co checkout`
>* `git config --global alias.ci commit`
>* `git config --global alias.br branch`

####多人协作的工作模式通常是这样：
> 1.首先，可以试图用`git push origin branch-name`推送自己的修改；   
> 2.如果推送失败，则因为远程分支比你的本地更新，需要先用`git pull`试图合并；  
> 3.如果合并有冲突，则解决冲突，并在本地提交；  
> 4.没有冲突或者解决掉冲突后， 再用`git push origin branch-name`推送就能成功！  
> 5.如果`git pull`提示“no tracking information”，则说明本地分支和远程分支的链接关系没有创建，用命令`git branch --set-upstream branch-name origin/branch-name`


###Coding... ...  
####User module
>1.enum 枚举 其本身就是一个java类，他继承类java.lang.enum[详细介绍连接](http://www.cnblogs.com/hemingwang0902/archive/2011/12/29/2306263.html#title-1),本项目做常量使用。  
>2.StringUtils 字符串工具类 解决了原来频繁判断null和空串的编码,后期长期使用org.apache.commons.commons-lang3    
>3.缓存的使用,本项目中用的是google的Guava缓存,主要用来储存一定有效期的数据，通过缓存设置存储空间大小，有效期时间，key-value形式存储[参考资料](http://ifeve.com/google-guava-cachesexplained/)  

前台接口  
* `public Object login(String username, String password, HttpSession session)`登陆接口，验证账号及md5加密后的密码正确性，登陆成功用户保存入session    
* `public ServerResponse<String> logout(HttpSession session)`登出接口，将用户移除session  
* `public ServerResponse<String> register(User user)`注册接口，需验证邮箱和用户名保证唯一性，确定角色md5加密密码储存    
* `public ServerResponse<String> checkValid(String str,String type)`提供给前端验证邮箱和用户名  
* `public ServerResponse<User> geUserInfo(HttpSession session)` 从session获取当前登陆用户的信息，注意密码不可传递到前端  
* `public ServerResponse<String> forgetGetQuestion(String username)` 通过用户名获取密码提示问题
* `public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer)` 验证用户的提示问题和答案是否正确，如果正确生成token加入缓存
* `public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken)` 重置密码需要回传token，并验证旧密码，防止横向越权，验证成功重置密码

后台接口  
*`public ServerResponse<User> login(HttpSession session,String username,String password)`后台管理员登陆，验证角色及账号密码  
  
####Category module
>1.递归表结构
>2.递归查询  

后台接口  
*`public ServerResponse addCategory(HttpSession session, Integer parentId, String categoryName) `添加分类，如果是根节点则parentId=0
*`public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName)` 更新分类名称
*`public ServerResponse getChildrenParallelCategory(HttpSession session, @RequestParam(defaultValue = "0", value = "categoryId") Integer categoryId) `获取当前分类下所有子分类
*`public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(defaultValue = "0", value = "categoryId")Integer categoryId) `获取传入分类下所有子分类，递归获取所有子分类，注意返回值用的set集合，故要重写hashcode 和 equals方法    



####Product module
1.PageHelper的基本用法
> 1.声明分页和排序  
`PageHelper.startPage(page, size);`  
`PageHelper.orderBy(orderBys[0]+" "+orderBys[1]);`  
> 2.执行正常查询  
> 3.处理结果  
`PageInfo pageInfo=new PageInfo(productList);`  
`pageInfo.setList(productListVoList);`  

3.spring mvc 文件上传  
> 1.前端`<form name="form1" action="/manage/product/upload.do" method="post" enctype="multipart/form-data"></form>`  
> 2.后台接收`public ServerResponse upload(HttpSession session,@RequestParam(value = "upload_file") MultipartFile file,HttpServletRequest request)`  
> 3.重命名暂存在服务器路径下  
> 4.发送到ftp服务器储存,此处用到FTPUtil工具类负责连接ftp服务及上传文件,构建方式值得学习  
> 5.返回地址和文件名至前端  
  
4.表结构中主图，子图，富文本详情的储存

前台接口  
*`public ServerResponse detail(Integer productId)`
*`public ServerResponse list(@RequestParam(value = "keyWord",required = false) String keyWord,
                                 @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                 @RequestParam(value = "page",defaultValue = "1") int page,
                                 @RequestParam(value = "size",defaultValue = "10") int size,
                                 @RequestParam(value = "orderBy",defaultValue = "")String orderBy)`根据关键字或者分类id分页获取商品，注意获得是分类及所有子分类的商品
后台接口  
*`public ServerResponse save(HttpSession session, Product product)`新增或更新商品  
*`public ServerResponse detail(HttpSession session,Integer productId)`获取商品详情  
*`public ServerResponse setSaleStatus(HttpSession session,Integer productId,Integer status)`设置商品的销售状态
*`public ServerResponse list(HttpSession session,
                                 @RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "size",defaultValue = "10") Integer size)`分页获取商品列表
*`public ServerResponse search(HttpSession session,
                                   String productName,
                                   Integer productId,
                                   @RequestParam(value = "page",defaultValue = "1") Integer page,
                                   @RequestParam(value = "size",defaultValue = "10") Integer size)`根据商品名模糊查询 和 商品id查询                                     
*` public ServerResponse upload(HttpSession session,
                                   @RequestParam(value = "upload_file") MultipartFile file,
                                   HttpServletRequest request)` 上传图片，后台将图片传至http服务器，返回uri url，主要用在添加商品时增加商品的上传图片，数据库储存上传返回的uri ,url 用于预览
*`public Map richtextImgUpload(HttpSession session,
                                   @RequestParam(value = "upload_file") MultipartFile file,
                                   HttpServletRequest request,
                                   HttpServletResponse response)`富文本上传图片，返回值为富文本要求格式，其余同上
                                   
####Cart module
1.对购物车的所有操作最终都将返回整体购物车的信息，所以封装了高复用的购物车信息方法  

*`public ServerResponse list(HttpSession session)`获取购物车列表数据  
*`public ServerResponse add(HttpSession session, Integer productId, Integer count) `用户将指定数量的指定商品加入购物车  
*`public ServerResponse update(HttpSession session, Integer productId, Integer count)`用户更新指定商品在购物车内的数量  
*`public ServerResponse delete(HttpSession session, String productIds) `用户删除购物车内的商品可批量  
*`public ServerResponse selectAll(HttpSession session)`全选  
*`public ServerResponse unSelectAll(HttpSession session)`取消全选  
*`public ServerResponse select(HttpSession session, Integer productId)`选中指定商品  
*`public ServerResponse unSelect(HttpSession session, Integer productId)`取消选中指定商品  
*`public ServerResponse getCartProductCount(HttpSession session)`获取商品的累加数量  

####Shipping module
> 1.常规模块 熟悉一下数据表结构
> 2.useGeneratedKeys="true" keyProperty="id" mybatis 插入数据返回id设置

前台接口  
* 常规的增删改查结构，不多赘述了

####Order module
> 1.对接支付宝当面付款流程  
> 2.沙箱测试环境的调试  
> 3.订单操作接口中保证数据的对称,及返回前端的数据VO对象结构思想值得学习借鉴  
> 4.` ./natapp -authtoken=2021a2d30ae70b65`外网穿透
  
前台接口  
*`public ServerResponse create(HttpSession session, Integer shippingId)`用户结算购物车内所有勾选的商品，创建订单 并 生成订单详情存入数据库（每件结算商品生成一个订单详情），注意减少库存和清除购物车；  
*`public ServerResponse cancel(HttpSession session, Long orderNo)`用户取消订单，即更新订单状态  
*`public ServerResponse getOrderCartProduct(HttpSession session)`获取用户购物车内勾选商品的信息  
*`public ServerResponse detail(HttpSession session,Long orderNo)`获取用户指定订单的详情  
*`public ServerResponse list(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize)`分页用户的获取订单列表  
*`public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request)`支付宝支付接口，向支付宝申请预下单，调用支付宝接入，得到支付宝下单成功则返回前端二维码图片的地址，提供回调接口被支付宝调用
*`public ServerResponse<Boolean> queryOrderPayStatus(HttpSession session, Long orderNo)`前端用于轮询的订单状态  
后台接口  
*` public ServerResponse<PageInfo> orderList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize",defaultValue = "10")int pageSize)`管理员分页获取订单列表  
*`public ServerResponse<OrderVo> orderDetail(HttpSession session, Long orderNo)`管理员获取指定订单的详情
*`public ServerResponse<PageInfo> orderSearch(HttpSession session, Long orderNo,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                  @RequestParam(value = "pageSize",defaultValue = "10")int pageSize)`根据订单编号搜索订单
*`public ServerResponse<String> orderSendGoods(HttpSession session, Long orderNo)`管理员对订单发货处理  

####linux deploy
准备服务器,如果购买阿里云ECS服务器,按正常购买步骤,注意配置安全组,可选择镜像市场中的配置好的镜像,也可自己配置以下为自己配置的全过程
  
1.配置aliyun数据源  
> 1.`sudo mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repo.d/CentOS-base.repo.backup`备份原始源    
> 2.然后下载阿里云的数据源CentOS-Base.repo 到 /etc/yum.repos.d/ 目录 ,运行`wget -O /etc/yum.repos.d/CentOS-base.repo http://mirrors.aliyun.com/repo/Centos-7.repo`  
> 3.运行`yum makecache`生成缓存  
> 4.添加用户 并赋予sudo权限  `sudo vim /etc/sudoers` 
 
2.安装配置jdk
> 1.在根目录下创建`sudo mkdir developer` 然后创建 `sudo mkdir setup`  
> 2.检查是否安装 openjdk `rpm -qa | grep jdk`  注意中间的是竖杠  `yum -y remove `后面根查询到的openjdk
> 3.oracle 官网 拷贝连接 需要oracle账号 连接后要有 athtoken  
> 4.`sudo mv 原文件名 新文件名`重命名    
> 5.更改权限`sudo chmod 775`      
> 6.安装jdk`sudo rpm -ivh 文件名` 安装结束可以到/usr/java/..文件夹下查看  
> 7.配置环境变量 `sudo vim /etc/profile`   最后添加   
`export JAVA_HOME=/usr/java/jdk1.8.0_121`  
`export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar`
> 8.`source /etc/profile`刷新配置  
> 9.`java -version`检查安装是否安装成功  

3.安装配置tomcat
> 1.在developer目录下,下载tomcat 然后解压`sudo tar -zxvf 文件名`  将压缩的安装文件,移动到setup文件下`sudo mv 文件名 目录`    
> 2.找到tomcat的conf文件夹下的server.xml文件,增加配置字符集`URIEncoding="UTF-8"`  
> 3.运行tomcat测试bin目录下运行`sudo ./startup.sh`  
> 4.访问ip：8080端口 查看
> 5.关闭`sudo ./shutdown.sh`

4.Maven
> 1.在developer目录下,下载maven 然后解压`sudo tar -zxvf 文件名`  将压缩的安装文件,移动到setup文件下`sudo mv 文件名 目录`  
> 2.配置环境变量 `sudo vim /etc/profile` 最后添加 `export MAVEN_HOME=/developer/apache-maven-3.0.5`  
> 3.`source /etc/profile`刷新配置  
> 4.`mvn -version` 验证

5.Vsftpd
linux下安装ftp服务器--[cenos7 优秀教程](http://blog.csdn.net/uq_jin/article/details/51684722)  
> 1.`sudo yum -y install vsftpd`  安装  
> 2.`cd /`  切换到根目录  
> 4.`sudo rpm -qa|grep vsftpd`   检查安装情况  
> 5.`sudo mkdir ftpfile`   创建文件目录  
> 6.`[systemctl/service] vsftpd [status/start/stop/restart]` 分别为查看状态／启动／停止／重启
--此处即可编写防火墙开放21端,进行远程连接测试  
> 7.`sudo useradd ftpuser -d /ftpfile/ -s /sbin/nologin`  添加虚拟用户  
> 8.`sudo chown -R ftpuser.ftpuser /ftpfile/` 为用户授权  
> 9.`sudo passwd ftpuser` 设置密码  
> 10.`cd ftpfile/` 跳转到目录下  
> 11.`sudo vim  index.html` 文件夹下创建文件  
> 12.`whereis vsftpd` 查找ftp文件目录  
> 13.`sudo vim /etc/vsftpd/vsftpd.conf` 编辑vsftpd配置文件 [配置地址](http://learning.happymmall.com/vsftpdconfig/vsftpd.conf.readme.html)  
> 14.`sudo vim /etc/vsftpd/chroot_list` 创建文件并添加虚拟用户ftpuser(用于指定用户列表文件)  
> 15.`sudo service vsftpd restart` 配置结束重启vsftpd  
> 16.`sudo vim /etc/sysconfig/iptables` 编辑防火墙配置  
> 17.添加如下配置,这里很容易出问题  
`-A INPUT -p TCP --dport 61001:62000 -j ACCEPT`   
`-A OUTPUT -p TCP --sport 61001:62000 -j ACCEPT`  
`-A INPUT -p TCP --dport 20 -j ACCEPT`   
`-A OUTPUT -p TCP --sport 20 -j ACCEPT`  
`-A INPUT -p TCP --dport 21 -j ACCEPT`  
`-A OUTPUT -p TCP --sport 21 -j ACCEPT`  
> 18.`sudo service iptables restart` 配置结束重启防火墙     
> 19.`sudo vim /etc/selinux/config` 修改SELINUX=disabled  
> 20.`sudo setenforce 0`   
> 21.`sudo service vsftpd restart` 重启vsftpd   
> 22.`netstat -an|grep 21` 查看21端口下状态  
> 23.由于安装的centOS 7 所以需要停用firewalld 开启iptables [配置连接](https://oracle-base.com/articles/linux/linux-firewall-firewalld?utm_source=tuicool#reverting-to-iptables)

* 搭建花了 一天半时间 --!! 收获   
    1.增加了对linux的熟悉程度  
    2.一般教程都把整个流程写的一蹴而就，实际应当对颗粒修改立即调试，逐步完成整套安装达到最终效果。  
    3.最坑的是安装完了,最后 put[本地文件目录及文件名] [远程文件目录和文件名] 命令格式不熟悉搞了接近小半天,一直以为权限带来的问题


6.Nginx
> 1.在developer目录下,下载nginx 然后解压`sudo tar -zxvf 文件名`  将压缩的安装文件,移动到setup文件下`sudo mv 文件名 目录`  
> 2.`yum -y install gcc zlib zlib-devel pcre-devel openssl openssl-devel`批量安装nginx依赖  
> 3.进入在developer目录下nginx目录，`sudo ./configure`执行配置,结束继续执行`sudo make`,结束继续执行`sudo make install`  
> 4.`cd /usr/local/nginx/conf/`编辑配置文件`sudo vim nginx.conf`,文件末尾添加`include vhost/*.conf;`用于导入反向代理配置文件,保存退出创建`sudo mkdir vhost`目录  
> 5.在`vhost`目录下添加配置文件,... ...  
> 6.切换到`sbin`目录下，`sudo ./nginx`启动nginx  
> 7.`sbin`目录下,检查nginx配置文件是否有错误`./nginx -t`        
> 8.`sbin`目录下,停止服务`./nginx -s quit` 或者 `./nginx -s stop`  
> 9.`sbin`目录下,重启服务`./nginx -s reload`  
> 10.`ps -ef | grep nginx` 查看进程  
> 11.`kill -HUP [nginx的主进程号]` 平滑重启  
> 12.`sudo vim /etc/sysconfig/iptables` 开放80端口给nginx,重启防火墙  

nginx虚拟域名的设置
> 1.在本机和linux服务器修改host `sudo vim /etc/hosts`  
`10.211.55.7 www.happyvmall.com`  
`10.211.55.7 image.happyvmall.com`

  
8.MySql
> 1.检查是否安装了mysql `sudo rpm -qa | grep mysql-server`  
> 2.安装`sudo yum mysql-server mysql-devel`  
> 3.默认的配置文件位置在`vim /etc/my.cnf` 编辑字符集添加 `default-character-set=utf8` `character-set-server=utf8`    
> 4.设置自动启动 `chkconfig mysqld on`,执行`chkconfig --list mysqld`查看（如果2-5位都是on则ok）   
> 5.编辑防火墙开放3306端口，重启防火墙  

Mysql 的配置
> 1. 查看所有用户`select user,host,password from mysql.user`  mysql 5.7密码字段是 authentication_string     
> 2. `set password for root@localhost=password('123456');` `set password for root@127.0.0.1=password('123456');` `ALTER USER 'root'@'localhost' IDENTIFIED BY '123456';`    
> 3. 删除匿名用户 `delete from mysql.user where user=''` 再次查看，然后刷新`flush privilege;`
> 4. 插入新用户 `insert into mysql.user(Host,User,Password) values ("localhost","username",password("passowrd"))` 刷新
> 5. 创建新的数据库database `CREATE DATABASE `vmall` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci` 注意数据库名称的点是数字1左边的点  
> 6. 本地用户赋予所有权限`grant all privileges on vmall.* to username@localhost identified by 'password'`或者`grant all on *.* to username;`指定用户对所有表都都权限    
> 7. 本地用户开通外网所有权限`grant all privileges on vmall.*to 'username'@'%' identified by 'password'`
     指定ip开通指定权限`grant select,update,insert on vmall.*to 'username'@'192.168.1.104' identified by 'password'`  
     
9.Git
> 1.在developer目录下,下载git ，然后解压`sudo tar -zxvf 文件名`  将压缩的安装文件,移动到setup文件下`sudo mv 文件名 目录`  
> 2.下载依赖`sudo yum -y install zlib-devel openssl-devel cpio expat-devel gettext-devel curl-devel perl-ExtUtils-CBuilder perl-ExtUtils- MakeMaker`  
> 3.安装到 `sudo make preifx=/usr/local/git all` 结束后继续`sudo make prefix=/usr/local/git install`  
> 4.配置环境变量`sudo vim /etc/profile` 刷新环境变量`source /etc/profile`  
> 5.设置用户名`git config --global user.name "Your Name"`设置邮箱`git config --global user.email "youremail@domain.com"`      
> 6.`git config --global core.autocrlf false` 换行符转换问题 
> 7.`git config --global core.quotepath off`  避免中文乱码问题  
> 8.`git config --global gui.encoding utf-8`  
> 9.`ssh-keygen -t rsa -C "83989555@qq.com"` 生成密钥  
> 10.`ssh-add ~/.ssh/id_rsa` 如果出现`Could not open a connection to your authentication agent.` 继续  eval `ssh-agent`  

##deploy.sh脚本
`echo "===========进入git项目happymmall目录============="
 cd /developer/git-repository/mmall
 echo "==========git切换分之到mmall-v1.0==============="
 git checkout mmall-v1.0
 echo "==================git fetch======================"
 git fetch
 echo "==================git pull======================"
 git pull 
 echo "===========编译并跳过单元测试===================="
 mvn clean package -Dmaven.test.skip=true
 echo "============删除旧的ROOT.war==================="
 rm /developer/apache-tomcat-7.0.73/webapps/ROOT.war
 echo "======拷贝编译出来的war包到tomcat下-ROOT.war======="
 cp /developer/git-repository/mmall/target/mmall.war  /developer/apache-tomcat-7.0.73/webapps/ROOT.war
 echo "============删除tomcat下旧的ROOT文件夹============="
 rm -rf /developer/apache-tomcat-7.0.73/webapps/ROOT
 echo "====================关闭tomcat====================="
 /developer/apache-tomcat-7.0.73/bin/shutdown.sh
 echo "================sleep 10s========================="
 for i in {1..10}
 do
 	echo $i"s"
 	sleep 1s
 done
 echo "====================启动tomcat====================="
 /developer/apache-tomcat-7.0.73/bin/startup.sh
  `

