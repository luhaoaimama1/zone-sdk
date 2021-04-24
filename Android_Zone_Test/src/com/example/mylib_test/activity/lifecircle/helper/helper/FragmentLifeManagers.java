package com.example.mylib_test.activity.lifecircle.helper.helper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mylib_test.activity.lifecircle.helper.FragmentLifeController;

import java.util.WeakHashMap;

public class FragmentLifeManagers {

    private static volatile FragmentLifeManagers singleton;

    private FragmentLifeManagers() {
    }

    public static FragmentLifeManagers getInstance() {
        if (singleton == null) {
            synchronized (FragmentLifeManagers.class) {
                if (singleton == null) {
                    singleton = new FragmentLifeManagers();
                }
            }
        }
        return singleton;
    }

    private WeakHashMap<Fragment, FragmentLifeController> hashMap = new WeakHashMap();

    public void add(Fragment fragment, FragmentLifeController helper) {
        hashMap.put(fragment, helper);
    }

    @Nullable
    public FragmentLifeController get(Fragment fragment) {
        return hashMap.get(fragment);
    }

    public void remove(Fragment fragment) {
        hashMap.remove(fragment);
    }
}