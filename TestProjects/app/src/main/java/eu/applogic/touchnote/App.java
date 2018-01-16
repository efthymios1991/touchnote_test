package eu.applogic.touchnote;

import android.app.Application;

import eu.applogic.touchnote.di.components.AppComponent;
import eu.applogic.touchnote.di.components.DaggerAppComponent;
import eu.applogic.touchnote.di.modules.AppModule;


/**
 * Created by efthymioskontis on 13/1/18.
 */

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        /*
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        */

        initializeAppComponent();
    }

    private void initializeAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
