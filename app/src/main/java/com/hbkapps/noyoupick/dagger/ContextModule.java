package com.hbkapps.noyoupick.dagger;

import android.content.Context;
import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    public Context getContext() {
        return context;
    }

    @Provides
    @ApplicationScope
    public Resources getResources(Context context) {
        return context.getResources();
    }

}
