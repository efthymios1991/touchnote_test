package eu.applogic.touchnote.di.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.applogic.touchnote.data.AppDataManager;
import eu.applogic.touchnote.di.ApplicationContext;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static eu.applogic.touchnote.utils.Constrants.BASE_URL;

/**
 * Created by efthymioskontis on 13/1/18.
 */

@Module
public class AppModule {

    private final Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return mApplication;
    }

    @Provides
    Application providesApplication(){
        return mApplication;
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory(){
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory,
                             RxJava2CallAdapterFactory rxJava2CallAdapterFactory,
                             OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    AppDataManager provideAppDataManager(@ApplicationContext Context context, Retrofit retrofit){
        return new AppDataManager(context, retrofit);
    }
}
