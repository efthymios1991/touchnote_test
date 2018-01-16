package eu.applogic.touchnote.di.components;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import eu.applogic.touchnote.App;
import eu.applogic.touchnote.data.AppDataManager;
import eu.applogic.touchnote.di.ApplicationContext;
import eu.applogic.touchnote.di.modules.AppModule;
import retrofit2.Retrofit;

/**
 * Created by efthymioskontis on 13/1/18.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(App app);

    @ApplicationContext
    Context context();

    Application application();

    Retrofit retrofit();

    AppDataManager appDataManager();
}
