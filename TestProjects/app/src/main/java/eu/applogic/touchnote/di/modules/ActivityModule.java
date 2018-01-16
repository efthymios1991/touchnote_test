package eu.applogic.touchnote.di.modules;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import eu.applogic.touchnote.data.AppDataManager;
import eu.applogic.touchnote.di.ActivityContext;
import eu.applogic.touchnote.di.ApplicationContext;
import eu.applogic.touchnote.di.PerActivity;
import eu.applogic.touchnote.view.adapter.PostsAdapter;
import eu.applogic.touchnote.view.main.MainPresenterImpl;
import retrofit2.Retrofit;

/**
 * Created by efthymioskontis on 13/1/18.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    @PerActivity
    MainPresenterImpl providesMainPresenterImpl(AppDataManager appDataManager){
        return new MainPresenterImpl(appDataManager);
    }

    @Provides
    @PerActivity
    PostsAdapter provideAdapter(@ApplicationContext Context context){
        return new PostsAdapter(context, new ArrayList<>());
    }
}
