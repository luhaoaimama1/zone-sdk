package com.example.mylib_test.activity.pop_dialog.pop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mylib_test.R;

public class TopPop extends  BaseAppFloatPopWindow{
    @Nullable
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup viewGroup) {
        return inflater.inflate(R.layout.pop_top, viewGroup, false);
    }
}
