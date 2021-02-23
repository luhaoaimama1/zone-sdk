package com.zone.pulllive.accountsync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;
import com.zone.KeepLives;
import com.zone.recevier.PullLiveReceiver;

/**
 * Created by henrikwu on 2017/5/4.
 */
public class AccountSyncService extends Service {

    private static final Object syncLock = new Object();
    private static SyncAdapter syncAdapter = null;

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (syncLock) {
            if (syncAdapter == null) {
                syncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }


    class SyncAdapter extends AbstractThreadedSyncAdapter {

        public SyncAdapter(Context context, boolean autoInitialize) {
            super(context, autoInitialize);
        }

        @Override
        public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
            //do sync here
            if (getContext() != null) {
                PullLiveReceiver.sendBroadcast(getContext(), "SyncService", "onPerformSync");
            } else {
                KeepLives.logE("SyncService ", "context is null!");
            }
        }
    }

    /**
     * 利用帐号同步机制拉活
     *
     * @param context
     */
    public static void startAccountSync(Context context, String accountName, String accountType) {
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account account = null;
        Account[] accounts = accountManager.getAccountsByType(accountType);
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = new Account(accountName, accountType);
        }

        if (accountManager.addAccountExplicitly(account, null, null)) {
            long sync_interval = 15 * 60;
            ContentResolver.setIsSyncable(account, accountType, 1);
            ContentResolver.setSyncAutomatically(account, accountType, true);  //自动同步
            ContentResolver.addPeriodicSync(account, accountType, new Bundle(), sync_interval);//设置同步时间间隔
        }
    }

}