#keep learning...

>##init project repository  
> 1.git网站上创建远程仓库  
> 2.复制ssh地址 本地仓库管理远程仓库  
> 3.gitignore 文件用于过滤 提交文件  

> ##git命令
>* `touch README.md` 创建文件
>* `git init` #初始化项目
>* `git status` #查看状态
>* `git add .` #添加变更文件到本地仓库
>* `git commit -am"XXXXX"` #提交到本地仓库+注释
>* `git remote add origin` #要关联一个远程库复制ssh地址跟在后面
>* `git push origin HEAD -u` #提交到远程创建到分支   
>* `git pull` #拉取git网站上的代码
>* `git push -u origin master` #推送代码到分支
>* `git push -u -f origin master` #如果上面没成功 采用这个强制更新
>* `git log` #可查看历史记录 
>* `git diff HEAD` -- 文件名  #查看工作区和版本库中文件的区别 
>* `git checkout` -- 文件名 #可以撤销工作区的修改 总之，就是让这个文件回到最近一次git commit或git add时的状态
>* `git reset HEAD file`  #可以把暂存区的修改撤销掉（unstage），重新放回工作区

> ####分支操作
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
####别名--偷懒
>* `git config --global alias.co checkout`
>* `git config --global alias.ci commit`
>* `git config --global alias.br branch`

####多人协作的工作模式通常是这样：
> #####1.首先，可以试图用`git push origin branch-name`推送自己的修改；
> #####2.如果推送失败，则因为远程分支比你的本地更新，需要先用`git pull`试图合并；
> #####3.如果合并有冲突，则解决冲突，并在本地提交；
> #####4.没有冲突或者解决掉冲突后， 再用`git push origin branch-name`推送就能成功！
> #####5.如果`git pull`提示“no tracking information”，则说明本地分支和远程分支的链接关系没有创建，用命令`git branch --set-upstream branch-name origin/branch-name`


###Coding... ...  
####User module
>1.enum 枚举 其本身就是一个java类，他继承类java.lang.enum
[详细介绍连接](http://www.cnblogs.com/hemingwang0902/archive/2011/12/29/2306263.html#title-1),本项目做常量使用。
>2.StringUtils 字符串工具类 解决了原来频繁判断null和空串的编码,后期长期使用org.apache.commons.commons-lang3
>3.缓存的使用,本项目中用的是google的Guava缓存,主要用来储存一定有效期的数据，通过缓存设置存储空间大小，有效期时间，key-value形式存储[参考资料](http://ifeve.com/google-guava-cachesexplained/)

####Category module
1.linux下安装ftp服务器--[cenos7 优秀教程](http://blog.csdn.net/uq_jin/article/details/51684722)  
> 1.`sudo yum -y install vsftpd`  安装  
> 2.`cd /`  切换到根目录  
> 3.`sudo mkdir ftpfile`   创建文件目录  
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
* 搭建花了 一天半时间 --!! 收获   
    1.增加了对linux的熟悉程度  
    2.一般教程都把整个流程写的一蹴而就，实际应当对颗粒修改立即调试，逐步完成整套安装达到最终效果。  
    3.最坑的是安装完了,最后 put[本地文件目录及文件名] [远程文件目录和文件名] 命令格式不熟悉搞了接近小半天,一直以为权限带来的问题



