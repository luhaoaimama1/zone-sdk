package com.zone.zrflist;
import android.content.Context;
import com.zone.zrflist.headercustom.HeaderCustom;
import com.zone.zrflist.headercustom.RentalsSunHeaderView;
import com.zone.zrflist.utils.LocalDisplay;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 2016/3/31.
 * 用的 第三方的库来集成~
 * 仅仅是更换头的工具 剩下的都没有弄
 */
public class UltraHeaderUtils {
    public static void setMyCustomHeader(Context context,PtrFrameLayout ptrMain){
        HeaderCustom headerView=new HeaderCustom(context);
        ptrMain.setHeaderView(headerView);
        ptrMain.addPtrUIHandler(headerView);
    }
    public static void setPtrClassicDefaultHeader(Context context,PtrFrameLayout ptrMain){
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(context);
        ptrMain.setHeaderView(header);
        ptrMain.addPtrUIHandler(header);
    }
    public static void setStoreHouseHeader(Context context,PtrFrameLayout ptrMain){
        final StoreHouseHeader storeHouseHeader = new StoreHouseHeader(context);
        storeHouseHeader.setPadding(0, LocalDisplay.dp2px(15), 0, 0);
        // using string array from resource xml file
        storeHouseHeader.initWithStringArray(R.array.storehouse);
        ptrMain.setHeaderView(storeHouseHeader);
        ptrMain.addPtrUIHandler(storeHouseHeader);
    }

    public static void setStoreHouseStringHeader(Context context,PtrFrameLayout ptrMain,String str){
        final StoreHouseHeader storeHouseHeader = new StoreHouseHeader(context);
        storeHouseHeader.setPadding(0, LocalDisplay.dp2px(15), 0, 0);
        // using string array from resource xml file
        storeHouseHeader.initWithString(str);
        ptrMain.setHeaderView(storeHouseHeader);
        ptrMain.addPtrUIHandler(storeHouseHeader);
    }
    public static void setRentalsSunHeaderView(Context context,PtrFrameLayout ptrMain){
       final RentalsSunHeaderView header = new RentalsSunHeaderView(context);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setUp(ptrMain);
        ptrMain.setHeaderView(header);
        ptrMain.addPtrUIHandler(header);
    }


}
