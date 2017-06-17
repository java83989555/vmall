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
>* git branch branch-name 创建分支：
>* git checkout branch-name 切换分支：
>* git checkout -b branch-name 创建+切换分支：
>* git branch -d branch-name 删除分支：
>* git branch -r -d origin/branch-name  删除远程仓库的分支   
>* git log --graph命令可以看到分支合并图
>* git merge branch-name 合并某分支到当前分支：
>* git merge --no-ff -m "merge with no-ff" dev 加上--no-ff参数就可以用普通模式合并，合并后的历史有分支，能看出来曾经做过合并，而fast forward合并就看不出来曾经做过合并。
>* git stash 当临时要处理其他工作例如bug，手头的事情又没办法提交到远程仓库,可使用此命令
>* git stash list  查看列表
>* git stash apply 恢复，但是恢复后，stash内容并不删除
>* git stash drop 删除
>* git stash pop  当结束bug修复后可调用下一个命令回到未完成的工作中，恢复的同时把stash内容也删了
>* git stash apply stash@{0} 多次stash，恢复的时候，先用git stash list查看，然后恢复指定的stash，用此命令
>* git branch -D branch-name 开发一个新feature，最好新建一个分支； 如果要丢弃一个没有被合并过的分支，强行删除.
>* git remote -v 获取远程仓库信息
>* git branch --set-upstream branch-name origin/branch-name 建立本地分支和远程分支的关联

####多人协作的工作模式通常是这样：
> #####1.首先，可以试图用git push origin branch-name推送自己的修改；
> #####2.如果推送失败，则因为远程分支比你的本地更新，需要先用git pull试图合并；
> #####3.如果合并有冲突，则解决冲突，并在本地提交；
> #####4.没有冲突或者解决掉冲突后， 再用git push origin branch-name推送就能成功！
> #####5.如果git pull提示“no tracking information”，则说明本地分支和远程分支的链接关系没有创建，用命令git branch --set-upstream branch-name origin/branch-name

#### 标签
>* git tag branch-name 
>* git tag <name>用于新建一个标签
>* git tag v0.9 6224937 对指定commit id 打标签
>* git show <tagname> 查看指定标签的信息
####别名--偷懒
>* git config --global alias.co checkout
>* git config --global alias.ci commit
>* git config --global alias.br branch