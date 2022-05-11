# WanAndroid

## 介绍
- 鸿洋玩Android的APP，准备编写不同的架构版本，每一个分支代表一个版本，目前已开发完单一MVVM版、MVVM+组件化版
- compose版、Flutter、uniApp后续都会进行改造
MVVM版：使用kotlin+协程+liveData+viewModel+dataBinding构造的MVVM架构的单体项目
MVVM+组件化版：将MVVM版进行组件划分

## 功能展示
- 首页Tab
- 项目Tab (包含最新项目、完整项目、跨平台应用...)
- 广场Tab (包含广场、每日一问、体系、导航)
- 公众号Tab (各路大神的博客)
- 我的Tab (积分、我的收藏、我的分享、系统设置)
- 登录、注册
- 搜索，包含热搜和搜索历史

## APK下载
- https://raw.githubusercontent.com/BTPJ/WanAndroid/MVVM/app/release/WanAndroid_V1.5.apk

## 开源库
- 图片处理：Glide
- 强大的Adapter：BaseRecyclerViewAdapterHelper
- 网络框架：Retrofit
- 基于MMKV内存映射的移动端通用 key-value 组件-MMKV
- 基于Android WebView的一个强大的库-Agentweb
- MD风格的material-dialogs对话框
- 屏幕适配：AndroidAutoSize
- 内存泄漏分析：leakcanary
- bugly应用升级与错误上传
- ARouter路由（在组件化中引入）

## 项目地址
- [Github](https://github.com/BTPJ/WanAndroid/tree/MVVM)
- [Gitee](https://gitee.com/BTPJ_git/WanAndroid/tree/MVVM)

## 致谢
- WanAndroid网站提供的开放API, 和里面的一些优秀开源项目和文章
- 参考鸡哥的些许样式和代码：https://github.com/hegaojian/JetpackMvvm
