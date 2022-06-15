## 流量限流demo

## 工具说明
jemeter + springboot + 阿里sential + Google RateLimiter

其它小工具分享使用demo：
javamolody：实例监控（实际项目，prometheus）
stopwatch：耗时日志监控

## 算法原理

### 1.简单计数器算法

### 2.漏桶算法

### 3.令牌桶算法

## 仓库地址
由于两个仓库的jar存在相互依赖，所以如果本地没有分别构建过两个仓库，则需要按照以下步骤重新构建(到对应目录下执行`mvn clean install`)：

1. 两个仓库需要分别下载到不同目录；

2. 依次编译tgit中的tgit-parent、tgit-common、tgit-model、tgit-dao（也可以直接构建整个tgit，待报错停止为止）；

3. 构建整个tgit-server；

4. 构建整个tgit；
