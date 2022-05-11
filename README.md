# WanAndroid

## 介绍
- 鸿洋玩Android的APP的MVVM+组件化版
- MVVM+组件化版：将MVVM版进行组件划分

- compose版、Flutter、uniApp后续都会进行改造

## 功能展示
- 首页Tab
- 项目Tab (包含最新项目、完整项目、跨平台应用...)
- 广场Tab (包含广场、每日一问、体系、导航)
- 公众号Tab (各路大神的博客)
- 我的Tab (积分、我的收藏、我的分享、系统设置)
- 登录、注册
- 搜索，包含热搜和搜索历史

## 模块划分
- lib_common：所有模块公用的http请求封装、工具类、资源文件等
- module_main：启动页、包含各子tab的主页
- module_home：首页tab相关
- module_project：项目tab相关
- module_square：广场tab相关
- module_wechat：公众号tab相关
- module_mine：我的tab、登录注册等
- module_web：web页面详情页

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
- 组件路由Arouter

## 项目地址
- [Github MVVM分支](https://github.com/BTPJ/WanAndroid/tree/MVVM)
- [Github Component分支](https://github.com/BTPJ/WanAndroid/tree/Component)

## 致谢
- WanAndroid网站提供的开放API, 和里面的一些优秀开源项目和文章
- 参考[鸡哥](https://github.com/hegaojian/JetpackMvvm.git)的些许样式和代码
