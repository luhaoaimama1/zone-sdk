package com.facebook.drawee.generic

import android.content.res.Resources

class GenericDraweeHierarchyBuilderProgress(resources: Resources?) : GenericDraweeHierarchyBuilder(resources) {

    override fun build(): GenericDraweeHierarchy {
//        validate()
        val cls = GenericDraweeHierarchyBuilder::class.java
        val method=cls.getDeclaredMethod("validate")
        method.isAccessible=true
        method.invoke(this)
        return GenericDraweeHierarchyProgress(this)
    }
}
