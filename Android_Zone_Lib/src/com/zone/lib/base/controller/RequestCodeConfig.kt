package com.zone.lib.base.controller

//1000以后是我用的　想用每次+1
object RequestCodeConfig {
    //可以通过设置他  防止和别的库 请求码相同
    var START_CODE = 1000
    val Feature_Pic_REQUESTCODE_CAMERA = 0
    val Feature_Pic_REQUESTCODE_PHOTOS = 1
    val Feature_SystemClip_REQUESTCODE_Clip = 2
    val Feature_Pic_REQUESTCODE_PICK_PHOTOS = 3

    fun getRequestCode(requestCodeConfig_Code: Int): Int {
        return requestCodeConfig_Code + START_CODE
    }

    fun getSwitchRequestCode(requestCodeConfig_Code: Int): Int {
        return requestCodeConfig_Code - START_CODE
    }
}
