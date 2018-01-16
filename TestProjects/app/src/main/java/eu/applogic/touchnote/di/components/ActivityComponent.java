package eu.applogic.touchnote.di.components;

import dagger.Component;
import eu.applogic.touchnote.di.PerActivity;
import eu.applogic.touchnote.di.modules.ActivityModule;
import eu.applogic.touchnote.view.main.MainActivity;

/**
 * Created by efthymioskontis on 13/1/18.
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

}
