# 安装groovy 

## JDK的安装
groovy基于JDK,在安装groovy需要先安装JDK

- window环境安装JDK:
```html
https://jingyan.baidu.com/article/154b463111e7f028ca8f41a6.html
```
- mac环境安装JDK:
```html
https://blog.csdn.net/vvv_110/article/details/72897142

```

## groovy的安装

- window环境安装groovy:

```html

https://www.jianshu.com/p/777cc61a6202

```

- mac环境安装groovy:

```html

https://www.jianshu.com/p/b5f4b33c7c24

```

# nodemon安装为可选，个人习惯而定

## 安装 node.js

```angular2html

    https://nodejs.org/en/
    
```

只需要点击下载，并安装就可以,跟安装普通软件一样


## 安装 nodemon (用于自动重启groovy)

windows环境下。打开 cmd,或者，powershell，或者，gitshell 输入如下命令
 
```angular2html

npm i -g nodemon

```

mac下，打开终端 输入如下命令

```angular2html

npm i -g nodemon

```

## 在项目下创建 nodemon.json （这个配置是能nodemon用的）

输入如下内容

````angularjs

{
  "restartable": "rs",
  "ignore": [
    ".git",
    "node_modules/**/node_modules"
  ],
  "verbose": true,
  "execMap": {
    "":"groovy",
    "groovy": "groovy"
  },
  "watch": ["src/*.*"],
  "env": {
    "NODE_ENV": "development"
  },
  "ext": "js,json"
}




````


"watch"字段，表示要监听的目录


## IDE安装（任选）

- vscode

```bash

https://code.visualstudio.com/

```

- jetbrains idea

````bash

https://www.jetbrains.com/idea/download/

````

- ecplise


```html

https://www.eclipse.org/


```
