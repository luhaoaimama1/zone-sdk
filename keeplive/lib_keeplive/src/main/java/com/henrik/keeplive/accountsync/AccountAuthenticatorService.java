package com.henrik.keeplive.accountsync;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.zone.base_keeplive.KeepLiveBroadcasts;

/**
 * Created by henrikwu on 2017/5/4.
 */

public class AccountAuthenticatorService extends Service {

    private AccountAuthenticator accountAuthenticator;


    @Override
    public void onCreate() {
        accountAuthenticator = new AccountAuthenticator(this);
        accountAuthenticator.toString();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        KeepLiveBroadcasts.logE("AccountAuthenticator", accountAuthenticator.toString());
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (intent.getAction().equals(
                android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT))
            return accountAuthenticator.getIBinder();
        return null;
    }

    private static class AccountAuthenticator extends
            AbstractAccountAuthenticator {
        final Context context;

        public AccountAuthenticator(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        public Bundle addAccount(AccountAuthenticatorResponse response,
                                 String accountType, String authTokenType,
                                 String[] requiredFeatures, Bundle options)
                throws NetworkErrorException {
//            Intent intent = new Intent(context, LoginActivity.class);
//            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,
//                    response);
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(AccountManager.KEY_INTENT, intent);
            return null;
        }

        @Override
        public Bundle confirmCredentials(AccountAuthenticatorResponse response,
                                         Account account, Bundle options) throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle editProperties(AccountAuthenticatorResponse response,
                                     String accountType) {
            return null;
        }

        @Override
        public Bundle getAuthToken(AccountAuthenticatorResponse response,
                                   Account account, String authTokenType, Bundle options)
                throws NetworkErrorException {
            return null;
        }

        @Override
        public String getAuthTokenLabel(String authTokenType) {
            return null;
        }

        @Override
        public Bundle hasFeatures(AccountAuthenticatorResponse response,
                                  Account account, String[] features)
                throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle updateCredentials(AccountAuthenticatorResponse response,
                                        Account account, String authTokenType, Bundle options)
                throws NetworkErrorException {
            return null;
        }

    }
}
