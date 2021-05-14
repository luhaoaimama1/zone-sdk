/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.recyclerview.widget;

import android.view.View;

import androidx.annotation.Nullable;

import com.example.mylib_test.LogApp;

public class FilterPagerSnapHelper extends PagerSnapHelperV2 {

    private IFindSnapView findSnapView;
    private RecyclerView recyclerView;

    @Override
    protected boolean isClosetViewNewLogic(RecyclerView.LayoutManager layoutManager, int absClosest, int absDistance, @Nullable View child) {
        boolean isCloset = absDistance < absClosest;
        if (findSnapView != null && child != null) {
            return isCloset && findSnapView.findValidSnapView(layoutManager.getPosition(child), true);
        }
        return isCloset;
    }

    @Override
    void snapToTargetExistingView() {
        super.snapToTargetExistingView();
    }

    public void snapNotFling() {
        snapToTargetExistingView();
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        this.recyclerView = recyclerView;
        super.attachToRecyclerView(this.recyclerView);
    }

    @Nullable
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int targetSnapPosition = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
        targetSnapPosition = findValidPosi(targetSnapPosition, velocityY > 0);
        return targetSnapPosition;
    }

    public int nextValidPosi(int findTargetSnapPosition, boolean forwardDirection) {
        if (recyclerView == null || recyclerView.getAdapter() == null)
            throw new IllegalStateException("状态错误!");
        if (findTargetSnapPosition < 0 || findTargetSnapPosition > recyclerView.getAdapter().getItemCount() - 1) {
            LogApp.INSTANCE.e("无法找到  因为越界位置:" + findTargetSnapPosition);
            return RecyclerView.NO_POSITION;
        }

        int findTargetSnapPosition2;
        if (forwardDirection) {
            findTargetSnapPosition2 = findTargetSnapPosition + 1;
        } else {
            findTargetSnapPosition2 = findTargetSnapPosition - 1;
        }
        return findValidPosi(findTargetSnapPosition2, forwardDirection);
    }

    private int findValidPosi(int findTargetSnapPosition, boolean forwardDirection) {
        if (recyclerView == null || recyclerView.getAdapter() == null)
            throw new IllegalStateException("状态错误!");

        if (findSnapView == null) {
            return findTargetSnapPosition;
        }
        int findTargetSnapPositionTemp = findTargetSnapPosition;
        if (!findSnapView.findValidSnapView(findTargetSnapPosition, false)) {
            int findTargetSnapPosition2;
            if (forwardDirection) {
                findTargetSnapPosition2 = findTargetSnapPosition + 1;
            } else {
                findTargetSnapPosition2 = findTargetSnapPosition - 1;
            }
            if (findTargetSnapPosition2 < 0 || findTargetSnapPosition2 > recyclerView.getAdapter().getItemCount() - 1) {
                LogApp.INSTANCE.e("无法找到  因为越界位置:" + findTargetSnapPosition2);
                return RecyclerView.NO_POSITION;
            }

            findTargetSnapPositionTemp = findValidPosi(findTargetSnapPosition2, forwardDirection);
        }
        return findTargetSnapPositionTemp;
    }


    public IFindSnapView getFindSnapView() {
        return findSnapView;
    }

    public void setFindSnapView(IFindSnapView findSnapView) {
        this.findSnapView = findSnapView;
    }

}
