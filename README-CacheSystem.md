
>**可视区域**：如果view，上半显示，下半超出屏幕不显示，那么可视区域是**上半**，而不是整个；

#View中方法
| 方法 | 坐标系  | 含义 | 
| :------------: |:---------------:|:---------------:| 
| setDrawingCacheEnabled(boolean enabled) |  | 是否开启缓存，如果开启 绘制会快点,关闭的话 缓存会清除|
| isDrawingCacheEnabled() |  | |
| getDrawingCache(boolean autoScale) |  |包含buildDrawingCache|
| buildDrawingCache(boolean autoScale) |  |autoScale false(**常用**)为view大小,true会缩放；getDrawingCacheBackgroundColor会修改位图的config|
| destroyDrawingCache() |  | 清除缓存 |

获取cache通常会占用一定的内存，所以通常不需要的时候有必要对其进行清理，通过destroyDrawingCache或setDrawingCacheEnabled(false)实现。