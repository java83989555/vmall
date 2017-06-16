#keep learning...

---
>### init project repository
> #####1.git网站上创建远程仓库
> #####2.复制ssh地址 本地仓库管理远程仓库
> #####3.gitignore 文件用于过滤 提交文件

> ##git命令
>* touch README.md 创建文件
>* git init #初始化项目
>* git status #查看状态
>* git add . #添加变更文件到本地仓库
>* git commit -am"XXXXX" #提交到本地仓库+注释
>* git remote add origin #要关联一个远程库复制ssh地址跟在后面
>* git push origin HEAD -u #提交到远程创建到分支   
>* git pull #拉取git网站上的代码
>* git push -u origin master #推送代码到分支
>* git push -u -f origin master #如果上面没成功 采用这个强制更新
>* git log #可查看历史记录 
>* git diff HEAD -- 文件名  #查看工作区和版本库中文件的区别 
>* git checkout -- 文件名 #可以撤销工作区的修改 总之，就是让这个文件回到最近一次git commit或git add时的状态
>* git reset HEAD file  #可以把暂存区的修改撤销掉（unstage），重新放回工作区

> ####分支操作
>* git branch 查看分支：
>* git branch -a  查看远程仓库和本地分支
>* git branch <name> 创建分支：
>* git checkout <name> 切换分支：
>* git checkout -b <name> 创建+切换分支：
>* git branch -d <name> 删除分支：
>* git branch -r -d origin/branch-name  删除远程仓库的分支   
>* git log --graph命令可以看到分支合并图
>* git merge <name> 合并某分支到当前分支：
>* git merge --no-ff -m "merge with no-ff" dev 加上--no-ff参数就可以用普通模式合并，合并后的历史有分支，能看出来曾经做过合并，而fast forward合并就看不出来曾经做过合并。 