# LayoutManagerDemo
[![](https://jitpack.io/v/mcxtzhang/LayoutManagerDemo.svg)](https://jitpack.io/#mcxtzhang/LayoutManagerDemo)

利用自定义LayoutManager 的一些实战实例。


相关博文：

[流式布局](http://blog.csdn.net/zxt0601/article/details/52956504)

[仿探探、人人影视 卡片层叠 炫动滑动布局](http://blog.csdn.net/zxt0601/article/details/52956504)


想经济上支持我 or 想通过视频看我是怎么实现的:

http://edu.csdn.net/course/detail/3956


If you like, point a star .Thank you very much!

喜欢随手点个star 多谢

##  在哪里找到我：

我的github：

https://github.com/mcxtzhang

我的CSDN博客：

http://blog.csdn.net/zxt0601

我的稀土掘金：

http://gold.xitu.io/user/56de210b816dfa0052e66495

我的简书：

http://www.jianshu.com/users/8e91ff99b072/timeline
***


# 效果一览：


[仿探探、人人影视 卡片层叠 炫动滑动布局](http://blog.csdn.net/zxt0601/article/details/52956504)

探探皇帝翻牌子即视感

![探探皇帝翻牌子即视感](https://github.com/mcxtzhang/LayoutManagerDemo/blob/master/gifs/tantan.gif)

人人美剧订阅界面

![人人美剧订阅界面](https://github.com/mcxtzhang/LayoutManagerDemo/blob/master/gifs/renren.gif)

可配置参数(同时显示6页)：

![人人美剧订阅界面](https://github.com/mcxtzhang/LayoutManagerDemo/blob/master/gifs/tantan_6page.gif)


[流式布局](http://blog.csdn.net/zxt0601/article/details/52956504)

![这里写图片描述](https://github.com/mcxtzhang/FlowLayoutManager/blob/master/gifs/gif1)

艾玛，换成妹子图后貌似好看了许多，我都不认识它了，好吧，项目里它一般长下面这样：

![这里写图片描述](https://github.com/mcxtzhang/FlowLayoutManager/blob/master/gifs/gif2)

往常这种效果，我们一般使用自定义ViewGroup实现，我以前也写了一个。[自定义VG实现流式布局](http://blog.csdn.net/zxt0601/article/details/50533658)


# 使用：

**Step 1. 在项目根build.gradle文件中增加JitPack仓库依赖。**
```
    allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
Step 2. Add the dependency
```
    dependencies {
	        compile 'com.github.mcxtzhang:ZLayoutManager:V1.1.0'
	}
```

Step 3.
[仿探探、人人影视 卡片层叠 炫动滑动布局](http://blog.csdn.net/zxt0601/article/details/52956504):

以后老板让你做这种效果，你只需要：
```
	mRv.setLayoutManager(new OverLayCardLayoutManager());
        CardConfig.initConfig(this);
        ItemTouchHelper.Callback callback = new RenRenCallback(mRv, mAdapter, mDatas);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRv);
```

如果需要定制特殊的参数，例如显示6层：

```
		 CardConfig.MAX_SHOW_COUNT = 6;
```

设置仿探探竖直上下滑动，不删除Item。

水平方向判断误差阈值x：
```
        final TanTanCallback callback = new TanTanCallback(mRv, mAdapter, mDatas);
        callback.setHorizontalDeviation(x);
```

[流式布局](http://blog.csdn.net/zxt0601/article/details/52956504):
```
        mRv.setLayoutManager(new FlowLayoutManager());
```

刚建了个QQ搞基交流群：
557266366 
里面现在没有人。
嗯，就这样吧。

## 使用的Adapter：
https://github.com/mcxtzhang/all-base-adapter
