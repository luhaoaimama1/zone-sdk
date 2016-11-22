###已知：
ReParent: onTouchEvent dispatchTouchEvent onInterceptTouchEvent 
ViewChild: onTouchEvent dispatchTouchEvent
>return 的都是super.

###开始测试：
* 第一次 修改child  onTouchEvent return true 

    ```
    ReParent->dispatchTouchEvent ACTION_MOVE
    ReParent->onInterceptTouchEvent ACTION_MOVE
    ViewChild->dispatchTouchEvent ACTION_MOVE
    ViewChild->onTouchEvent ACTION_MOVE
    ```
    >说明 即使我消耗了,其父dispatch,onInterceptTouchEvent也会相继走,
    
    >而不是直接跳到目标View执行 dispatchTouchEvent,onTouchEvent 与传言不符~

* 第二次 child dispatchTouchEvent return true 

    ```
    ReParent->dispatchTouchEvent ACTION_MOVE
    ReParent->onInterceptTouchEvent ACTION_MOVE
    ViewChild->dispatchTouchEvent ACTION_MOVE
    ```
    >为啥没走  ReParent->onTouchEvent ACTION_DOWN 因为 addTouchTarget(child, idBitsToAssign)是dispatchTouchEvent return true就执行 

    >会找到对应的目标;所以下面代码不走 
    
    ```
    源码
    if (mFirstTouchTarget == null) 
            // No touch targets so treat this as an ordinary view.
            handled = dispatchTransformedTouchEvent(ev, canceled, null,
                    TouchTarget.ALL_POINTER_IDS);
    ```

* 第三次：  child dispatchTouchEvent return true  但是我没有触碰到child 
 
    ```
        ReParent->dispatchTouchEvent ACTION_DOWN
        ReParent->onInterceptTouchEvent ACTION_DOWN
        ReParent->onTouchEvent ACTION_DOWN
    ```
    >说明 第二次的源码是正确执行的;

* `requestDisallowInterceptTouchEvent探究1`
    >把代码改回 child onTouchEvent return true dispatchTouchEvent return super.dispatchTouchEvent

    测试源码
    ```
                case MotionEvent.ACTION_DOWN :
                    downX =event.getX();
                    Log.i(TAG, "ViewChild->onTouchEvent ACTION_DOWN");
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(Math.abs(event.getX()-downX)>200){
                        Log.i(TAG, "ViewChild- >200!!! 请求拦截");
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    else{
                        Log.i(TAG, "ViewChild- <200!!! 父亲不拦截");
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    Log.i(TAG, "ViewChild->onTouchEvent ACTION_MOVE");
                    break;
    ```
    
    ```
    ViewChild- <200!!! 父亲不拦截
    ViewChild->onTouchEvent ACTION_MOVE
    ReParent->dispatchTouchEvent ACTION_MOVE
    ViewChild->dispatchTouchEvent ACTION_MOVE
    
    ViewChild- >200!!! 请求拦截
    ViewChild->onTouchEvent ACTION_MOVE
    ReParent->dispatchTouchEvent ACTION_MOVE
    ReParent->onInterceptTouchEvent ACTION_MOVE
    ViewChild->dispatchTouchEvent ACTION_MOVE
    
    ViewChild- <200!!! 父亲不拦截
    ViewChild->onTouchEvent ACTION_MOVE
    ReParent->dispatchTouchEvent ACTION_MOVE
    ViewChild->dispatchTouchEvent ACTION_MOVE
    ```


* `requestDisallowInterceptTouchEvent 探究2`
    >把ReParent onInterceptTouchEvent return true 
    ```
        ReParent->dispatchTouchEvent ACTION_DOWN
        ReParent->onInterceptTouchEvent ACTION_DOWN
        ReParent->onTouchEvent ACTION_DOWN
    ```
    >说明 requestDisallowInterceptTouchEvent代码如果不走是不能让他停止拦截的；

