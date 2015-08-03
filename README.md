# s60-mymoney-2-feidee-money
v1.0.0 2012-12-30

Automatically exported from code.google.com/p/s60-mymoney-2-feidee-money

![google-code](https://rawgit.com/ufologist/s60-mymoney-2-feidee-money/master/google-code.png)

![google-code-download](https://rawgit.com/ufologist/s60-mymoney-2-feidee-money/master/google-code-download.png)

[将S60我的财务中的数据导入随手记](http://www.douban.com/note/255604807/)

# 为什么做这个?
早些年智能手机还是NOKIA Symbian的天下, 而我从2009就开始使用S60平台下 `MyMoney|我的财务(来电通作者苏志宏出品)` 来记账.

这么一晃就是几年过去了, 记的账也越来越多(几千条了吧).

如今我换了Android手机, 记账软件也换成了随手记Android版.

令人头疼的问题随之而来 -- 如何将我的财务中的数据导入到随手记中?

## 自己动手丰衣足食
搜索未果后我决定自己写一个小工具, 用于将我的财务中导出的数据迁移到随手记

1. 首先要搞清楚的就是通过什么方法/途径将我的财务数据导入到随手记中
   
   Q: 我的财务可以将记账数据导出来吗? 导出文件是什么格式?
   
   A: 当初就是看中我的财务可以将数据导出成CSV才用的, 如果一个软件对数据是封闭的, 就像去哪个银行存钱无法取出一样, 谁敢用啊
   
   Q: 随手记支持导入数据吗? 什么格式?
   
   A: 找了半天都没找到, 只能导出数据.
      搜索"随手记导入"发现随手记Web版才提供<a href="http://www.feidee.com/money/help/gs-2.html#step5">数据导入功能</a>, 再通过<a href="http://www.feidee.com/money/help/gs-3.html#step2">数据同步</a>即可实现将数据同步到随手记Android版
   
   *备注*
   
   数据同步会将客户端(本地)数据和你在随手记Web导入的数据合并到一起, 因此只需将`我的财务.csv`转成随手记的csv格式即可
   
   同步的时候不会识别重复数据, 单纯的就是数据合并, 最终客户端和随手记网站都会有一样的数据, 同步意义由此而来
   
   例如
   
   你的随手记客户端现在有3条数据, 你通过随手记导入功能导入了4条数据(其中有2条是和客户端一样的数据), 那么最终你的客户端和随手记网站都会有7条数据(包含2条重复数据)

2. 确定导入方案
   ```
   我的财务导出.csv -> 随手记客户端导出.csv -> 随手记网站导入 -> 随手记客户端同步 -> 打完收工!
   ```

3. 问题来了
   
   怎样将我的财务数据转成随手记能够导入的数据(也就是随手记客户端导出的数据格式)
   
   我们对比下两种数据(都是csv数据格式, 结构简单), 发现他们之间的联系, 就知道如何转了

4. 编写工具来转换数据(我讨厌复制/粘帖)
   
   于是就有了 [S60MyMoney2FeideeMoney-1.0.jar](https://github.com/ufologist/s60-mymoney-2-feidee-money/releases/download/v1.0.0/S60MyMoney2FeideeMoney-1.0.jar)


## 我的财务导入随手记实战攻略
### 准备工作
*注意事项*

随手记Android版需要升级到v7.0.6(v7版本以上?)才能进行同步数据

1. 备份手机上随手记客户端的数据以备不时之需!!!
   
   导出csv, 备份数据
2. 准备好从我的财务导出的csv文件
   
   例如导出文件名为`我的财务.csv`
3. 下载将我的财务数据转成随手记的工具
   
   [S60MyMoney2FeideeMoney-1.0.jar](https://github.com/ufologist/s60-mymoney-2-feidee-money/releases/download/v1.0.0/S60MyMoney2FeideeMoney-1.0.jar)
   
   因为转数据的工具是使用java编写的, 因此请自备最新版的JDK(起码1.5以上), 如何安装请自行[求助搜索引擎](https://www.baidu.com/s?wd=%E5%AE%89%E8%A3%85%20jdk)
4. 随手记网站帐号一枚

### 先在电脑上转换数据并导入数据
1. 通过`S60MyMoney2FeideeMoney-1.0.jar`将`我的财务.csv`文件转成随手记csv数据格式
   
   使用该工具最便捷的方式就是将`我的财务.csv`和工具放在同一目录下, 直接双击运行
   
   稍等几秒后(等待时间与`我的财务.csv`数据的多少成正比, 我试过81KB的csv文件, 也就5秒钟)
   
   会在相同目录下获得类似`xx_mymoney_data.csv`这样一个文件, 这就是我们将要导入的文件
   
   *注意事项*
   由于本工具没有编写UI, 因此会给人造成运行后没有反应的错觉, 工具运行过程中出现问题也不会给予提示.
   
   因此建议大家在数据没有转换成功的情况下, 在命令行(cmd)中运行该工具
   ```
   java -jar S60MyMoney2FeideeMoney-1.0.jar
   ```

2. 登录随手记网站导入刚才转出来的数据`xx_mymoney_data.csv`
   
   注意要选择"随手记客户端版"

### 再通过手机客户端(以随手记Android版为例, iOS或其他版本应该类似)同步数据
1. 设置同步帐号(就是随手记网站帐号)
2. 同步数据
3. 核对数据
   
   主要是看金额总数是否对, 以确定是否同步了全部数据
3. 注销同步数据的帐号
   
   点击帐号 - 清空












最后清空随手记网站上的记账数据(可选操作, 如果你不担心记账信息的安全)
可以先删一次随手记网站上所有的记账信息, 再通过设置 - 高级设置 - 网站初始化再确认一次, 保证数据清空掉