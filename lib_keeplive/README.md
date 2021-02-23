1.Application onCreate:
        KeepLives.config

2.res目录
    确保项目中有@mipmap/ic_launcher ,如果不想用@mipmap/ic_launcher 去authenticator.xml 更换下想用的名字 不用xml中管红色的问题

3.定义账号：account_name，account_type是点进入账号后显示的title 一般相同。account_authority这个如果手机上有两个应用的这个名字一样那么第二个应用安装不上
在build.gradle 中配置。 defaultConfig
    resValue "string", "account_authority", applicationId + '.provider'
    resValue "string", "account_type", 'account_type'
    resValue "string", "account_name", 'kuaiyin'

不懂配置的话 定义参考：https://stackoverflow.com/questions/16777534/using-build-types-in-gradle-to-run-same-app-that-uses-contentprovider-on-one-dev
为什么Strings里还用站位呢？不用的话编译过不了 就算我定义了我也用不了上层的String.如果多个applicationId的话 用resValue 就方便多了。 图标的话没办法

遇到的问题：
1.账号同步后拉活 无界面 息屏无法监控到。所以一像素这种保活导致无用！
解决方案：在service里 每三秒 通过代码获取一次屏幕是否息屏。而不是用广播因为广播8.0以后失效
2.拉活后没有主界面的后台,无法启动activity除非在通知中点击。所以息屏一像素包活无法使用。
参考：https://stackoverflow.com/questions/59419653/cannot-start-activity-background-in-android-10-android-q
https://developer.android.com/guide/components/activities/background-starts

=============??????????????=======================
3.没用JobHandlerService的原因？ todo 最后弄这个
后台清掉会触发 Activity的onDestroy

现在的思路拉活：账号同步,因为没有主界面所以adj等级低容易被杀死。所以包活增加adj等级。而app启动后则不需要做包活了

4.为验证 8.0以下广播拉起有用吗？


