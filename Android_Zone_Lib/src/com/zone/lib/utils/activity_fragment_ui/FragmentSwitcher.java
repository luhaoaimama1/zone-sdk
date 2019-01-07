package com.zone.lib.utils.activity_fragment_ui;


import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by Zone on 2016/1/25.
 */
public class FragmentSwitcher {
    public static final String TAG="FragmentSwitcher";
    private final FragmentActivity frameActivity;
    private final int frameId;
    private BackStatue allowBack=BackStatue.NO_BACK;
    private FragmentManager manager;
    private List<FragmentEntity> fragmentEntityList=new ArrayList<FragmentEntity>();
    private FragmentEntity nowFragmentEntity=null;
    private boolean isFirst=true;

    public enum BackStatue{
        NO_BACK,BACK;
    }
    public static class FragmentEntity{
        public Fragment fragment;
        public String tag=null;
        public int ani_in=-1;
        public int ani_out=-1;
    }
    public void initFragment(Fragment... fragments){
        for (int i = 0; i < fragments.length; i++) {
            FragmentEntity entity = new FragmentEntity();
            entity.fragment=fragments[i];
            if(!(ani_in_pri!=-1||ani_out_pri!=-1)){
                entity.ani_in=ani_in_pri;
                entity.ani_out=ani_out_pri;
            }
            entity.tag=TAG+i;
            fragmentEntityList.add(entity);
        }
        init=true;
    }
    public void initFragment(FragmentEntity... fragments){
        for (int i = 0; i < fragments.length; i++) {
            if(fragments[i].fragment==null){
                throw new IllegalArgumentException("FragmentEntity里的fragment 不能为null");
            }
            fragmentEntityList.add(fragments[i]);
        }
        init=true;
    }
    public void initFragment(Class... fragments){
        for (int i = 0; i < fragments.length; i++) {
            Class fragment = fragments[i];
            if(!Fragment.class.isAssignableFrom(fragment))
                throw new IllegalArgumentException("类型不是frament 不能展示");
            else{
                FragmentEntity entity = new FragmentEntity();
                entity.fragment=Fragment.instantiate(frameActivity,fragment.getName());
                if((ani_in_pri!=-1||ani_out_pri!=-1)){
                    entity.ani_in=ani_in_pri;
                    entity.ani_out=ani_out_pri;
                }
                entity.tag=TAG+i;
                fragmentEntityList.add(entity);
            }
        }
        init=true;
    }
    private boolean init=false;
    /**
     *
     * @param frameActivity
     * @param frameId  被替换的frame
     * @param allowBack  是否允许回退
     */
    public FragmentSwitcher(FragmentActivity frameActivity, int frameId,BackStatue allowBack) {
        this.frameActivity = frameActivity;
        this.frameId = frameId;
        this.allowBack=allowBack;
    }
    /**
     * 默认：不允许 回退
     * @param frameActivity
     * @param frameId
     */
    public FragmentSwitcher(FragmentActivity frameActivity, int frameId) {
        this(frameActivity,frameId,BackStatue.NO_BACK);
    }


    //全局动画
    private static int ani_in=-1;
    private static int ani_out=-1;
    public static void setDefaultAnimal(int ani_in,int ani_out){
        FragmentSwitcher.ani_in=ani_in;
        FragmentSwitcher.ani_out=ani_out;
    }
    //个体动画
    private  int ani_in_pri=-1;
    private  int ani_out_pri=-1;
    public  void setPriDefaultAnimal(int ani_in_pri,int ani_out_pri){
        if(init)
            throw new IllegalStateException("this method must be used  before init");
        this.ani_in_pri=ani_in_pri;
        this.ani_out_pri=ani_out_pri;
    }

    private void checkAni(FragmentTransaction tran, FragmentEntity entity){
        if (entity!=null) {
            if((entity.ani_in!=-1||entity.ani_out!=-1)){
                tran.setCustomAnimations(entity.ani_in,entity.ani_out);
    //			tran.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
            }else{
                if((ani_in!=-1||ani_out!=-1)){
                    tran.setCustomAnimations(ani_in,ani_out);
                }
            }
        }else{
            if((ani_in_pri!=-1||ani_out_pri!=-1)){
                tran.setCustomAnimations(ani_in_pri,ani_out_pri);
                //			tran.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
            }else{
                if((ani_in!=-1||ani_out!=-1)){
                    tran.setCustomAnimations(ani_in,ani_out);
                }
            }
        }
    }

    public void switchPage(int  index){
        switchPage(fragmentEntityList.get(index));
    }
    public void switchToNull(){
        switchPage(null);
    }
    private void switchPage(FragmentEntity entity) {
        manager = frameActivity.getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        checkAni(tran, entity);
        if(!isFirst){
            if (entity!=null) {
                if (nowFragmentEntity != null) {
                    if (nowFragmentEntity.equals(entity)) {
                        // 还是这个页面
                        return;
                    }
                    Fragment targetFm = null;
                    if(manager.getFragments()!=null){
                        for (Fragment item : manager.getFragments()) {
                            if (item!=null&&item.equals(entity.fragment)) {
                                // 如果有直接控制 显隐
                                tran.hide(nowFragmentEntity.fragment);
                                targetFm=nowFragmentEntity.fragment;
                                tran.show(entity.fragment);
                                nowFragmentEntity=entity;
                            }
                        }
                    }
                    if (targetFm == null) {
                        // 没有则 生成 然后显示隐藏
                        tran.hide(nowFragmentEntity.fragment);
                        tran.add(frameId, entity.fragment, entity.tag).show(entity.fragment);
                        nowFragmentEntity=entity;
                    }
                }else{
                    //nowFragmentEntity 是null
                    Fragment targetFm = null;
                    if(manager.getFragments()!=null){
                        for (Fragment item : manager.getFragments()) {
                            if (item!=null&&item.equals(entity.fragment)) {
                                tran.show(entity.fragment);
                                nowFragmentEntity=entity;
                                targetFm=nowFragmentEntity.fragment;
                            }
                        }
                    }
                    if (targetFm == null) {
                        // 没有则 生成 然后显示隐藏
                        tran.add(frameId, entity.fragment, entity.tag).show(entity.fragment);
                        nowFragmentEntity=entity;
                    }
                }
            }else{
                //切换到 nullframent
                if(nowFragmentEntity!=null){
                    tran.hide(nowFragmentEntity.fragment);
                    nowFragmentEntity=null;
                }else{
                    return;
                }
            }
        }else{
            //第一次的时候
            if (entity!=null) {
                tran.add(frameId, entity.fragment,entity.tag).show(entity.fragment);
                nowFragmentEntity=entity;
            }else{
                return ;
            }
        }

        tranCommit(tran);
    }

    private void tranCommit(FragmentTransaction tran) {
        //第一次不可以回退  即第二次以后 通过allowBack 是否添加回退
        switch (allowBack) {
            case BACK:
                tran.addToBackStack(null);
                break;
            case NO_BACK:

                break;
            default:
                break;
        }
        tran.commit();
        isFirst=false;
    }


    public FragmentEntity getNowFragmentEntity() {
        return nowFragmentEntity;
    }

    public FragmentManager getManager() {
        return manager;
    }

    public List<FragmentEntity> getFragmentEntityList() {
        return fragmentEntityList;
    }


}
