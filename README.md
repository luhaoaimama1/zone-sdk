>天下文章一大抄，因为从开始学习到现在大约一年多 积攒的工具类。很多都是别人的。
>但是也不知道是谁的了 如果涉及什么的问题。请联系我。我会做出相应修改；
>`PS`:不懂怎么用的,都可以看demo 一些都是已经在demo中使用了~;
>`PS2`:customView库正打算摘出去 暂时就不介绍了~;

#功能介绍

##and.base包

| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| Fragment_Lazy | 懒加载的Frament | 兼容ViewPager与正常fragment的替换 |
| Pop_Zone   | popWindow的基名字 | 解耦出来，写起来方便不少 |

##and.base.activity包

| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| BaseActivity,BaseAppCompatActivity,BaseFragmentActivity | 万能解耦基名字，三个主要是为了兼容 | 解耦，各司其职。拍照等涉及onActivityResult的封装 |

##and.utils.activity_fragment_ui包

| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| ActivityCollectionUtils （尚未测试) | activity的若引用收集 | 兼容ViewPager与正常fragment的替换 |
| FragmentSwitcher   | fragment切换的封装 | 使用相当起来方便举个例子：fragmentSwitcher.switchPage(0);|
| ~~MeasureUtils~~用View.post()代替   | 主要在onCreate中来测量高度 | |
| ToastUtils   | 借来的~ | |
| UiHelper   | rootView与 statuBar高度的获取| |
| UiHelper   | rootView与 statuBar高度的获取| |

##and.utils.check
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| check包下 | 主要用来检测各种是否为空等功能 | |

##and.utils.convert包
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| and.utils.data.convert.de2encode包 | 解码编码用的 | 仅仅使用过MD5Utils  |
| and.utils.data.convert.pinyin包  | 汉子转拼音 | |
| ArraysUtil | 数组与list的相互转化。深拷贝 | 系统的Arrays是浅拷贝 |
| DateUtil | str,Date,Car的相互转化,与set方法(防Month坑),两个时间所差的long值,与几天星期几等功能| |
| DensityUtils | 貌似人人都有 | |
| GsonUtils | 增加fromJsonToList fromJsonToArray方法 |  防止多写实体类,或者写的太复杂去转化 |
| MediaTypeUtils | okhttp拿过来的 未使用过 |  |
| ByteUtil |  | 未使用过 |


##and.utils.file2io2data包
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| Assets2RawUtils  | 从assets文件夹获取 | |
| EnvironmentUtils | 获取系统cache,file,与智能获取(判断sd卡) |  |
| FileUtils | 删除，获取文件等 | 范例：getFile("test001","test002","test003.txt"); |
| HashMapZ | HashMap的put的连续使用 ,与get是null的默认值处理 | |
| IOUtils | io的read,write。 支持file，string,流 |   |
| SDCardUtils | SD卡等获取信息的封装 |  |
| SharedUtils| 主要为了省写系统的获取；与存到一个地方 |  |
| SerializeUtils | 未使用 |  |
| TypefaceUtils| 未使用|  |

##and.utils.info包
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| AppUtils  |  包名,app名,版本等, 有调用系统分享 | |
| CpuUtil | 关于cpu的信息 |  |
| MemoryUtil | 未使用 |  |
| MobileUtil | 获取mobile的信息 | |
| ProcessUtils | 获取当前进程名字 （未使用） | |
| ScreenUtils | 得到Screen大小，与请求窗口 是否去掉tilted和全屏的功能 | |
| TelephoneUtil | IMEI,IMSI等信息 | |

##and.utils.executor包
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| ExecutorUtils  |  集中管理,自己开启的线程 | 经常会自己开线程去浪费资源 |

##and.utils.image.compress2sample包
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| CompressUtils  | 压缩，保存位图|  |
| SampleUtils  |  小型的获取位图工具 | 范例：`SampleUtils .load(activity,R.drawable.ic_launcher)/load(path)/load(path,isFileDescriptor) .override(targetWidth,targetHeight).config(config).FitCenter()/CenterCrop() .bitmap()/.calculateInSampleSize()/justDecodeBounds()` |

##and.utils.image.lruutils包
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| DiskLruUtils  |  DiskLru管理封装 |  |
| LruCacheUtils  |  LruCacheUtils管理封装 |  |

##and.utils.image
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| BitmapUtils  |  处理位图的工具类 旋转,缩放,深拷贝,模糊，与drawable转化bt等|  |
| ImageTypeUtils  |  获取图片类型 |  |
| PhotoUtils  |  `让系统接受广播刷新图片库  能马上看到该图片`，`通过uri获取文件的绝对路径` 等关于相册的功能 |  |

##and.utils.reflect
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| Reflect  | 用法:`Reflect.on(Kale.class).create().get();` | 参考 [天之接线的最佳实践](https://github.com/tianzhijiexian/Android-Best-Practices/blob/master/2015.9/reflect/reflect.md "悬停显示") |
| ReflectCloneUtils  | 深克隆一切对象,支持 list,map,实体嵌套   |  |
| ReflectGenericUtils  | `泛型获取工具支持：Self_,Super_,Interface_,Field_ ` |  |

##and.utils.system_hardware_software_receiver_shell
>（里的包 出了software都没用过~）

| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| ClipboardUtil  | 关于剪切板的工具类 | |
| KeyBoardUtils  | 有关键盘的工具类   |  |

##and.utils.system_hardware_software_receiver_shell.software.wifi
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| NetManager  | 查看当前网络类型与是否联网 |  |
| MyWifiAnd3G  | 查看wifi列表，关闭/打开wifi/3G,startScans,获取ipAddress等 |  |
| NetStatusReceiver  | `查看网络类型,与关于网络广播的类` |  |
| NetworkManagerFinal  | 别人的留着 有些bug碰到或许可以参考下 |  |

##and.utils.unused
>暂时不要用吧~

##and.utils.view
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| AnimationUtils  | explode动画自己试验用的~大家看看就行了 |  |
| ViewUtils  | `recurrenceClipChildren(迭代的parent,设置ClipChildren)`,`getCacheBitmap(获取View的DrawingCache)`,`clipView（可以把一个ImageView切成N*M的ImageView` |  |

##and.utils.view.gesture
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| ZGestrueDetector  | 支持 旋转，缩放，位移的回调参数 |  |

##and.utils.view.graphics.animation
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| FlexibleBallAnimation  | Bézier三阶 圆形动画位移封装|  |

##and.utils.view.graphics.basic
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| ZPointF  | 主要是：让点与点之间有父子关系,例如 parent点移动40,那么不需要给child点做任何操作即可移动40 |  |
| ZPath  | 主要是为了支持ZPointF |  |
| MatrixUtils  | `暂时没用` |  |
| Circle  | Bézier三阶 圆形动画位移封装|  |
| DrawBind  | `可以绑定bt,view 可得到九个点入下图所示，与其所有点位移的操作` ,`getRect` |  |
```
* 0---1---2
* |       |
* 7---8---3
* |       |
* 6---5---4
```

##and.utils.view
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| BizierUtils  | qq红点的两种 实现方法的封装 |  |
| DampingUitls  | 阻尼方法 |  |
| DrawUtils  | 可不用，不是很好~ |  |
| GeometryUtils  | 支持ZPointF的,两点之间的长度，终点与三点角度等几何工具类 |  |
| MathUtils  | 映射工具类,AE表达式里有,Math函数里却没有,封装下 |  |
| MatteUtils  | PorterDuff.Mode 的封装工具类 |  |

##and
| 名字 | 功能  | 优点|
| :------------: |:---------------:| :-----:|
| LogUtil  | 简单log工具类 可实现点击链接 |  |
| TimeDiffUtils  | 计算两点时间插的工具类 | 范例：TimeDiffUtils.start("Method1") ,TimeDiffUtils.end("Method1") |


[▲ 回到顶部](#top)
