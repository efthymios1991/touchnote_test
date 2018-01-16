package eu.applogic.touchnote.view.main;

import java.util.List;

import javax.inject.Inject;

import eu.applogic.touchnote.data.AppDataManager;
import eu.applogic.touchnote.data.api.ApiInterface;
import eu.applogic.touchnote.models.Post;
import eu.applogic.touchnote.utils.DebugLogger;
import eu.applogic.touchnote.utils.NetworkUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by efthymioskontis on 13/1/18.
 */

public class MainPresenterImpl implements MainContract.MainPresenter {

    MainContract.MainView view;
    AppDataManager appDataManager;

    @Inject
    public MainPresenterImpl(AppDataManager appDataManager){
        this.appDataManager = appDataManager;
    }

    @Override
    public void onAttachView(MainContract.MainView view) {
        this.view = view;
    }

    @Override
    public void onDetachView() {
        this.view = null;
    }

    @Override
    public void loadData() {

        view.showNoData(false);
        view.showProgressIndicator(true);

        appDataManager.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError);
    }

    private void handleResults(List<Post> posts){
        view.showProgressIndicator(false);
        if(posts.size() > 0){
            view.showData(posts);
        }else{
            view.showNoData(true);
        }
    }

    private void handleError(Throwable t){
        DebugLogger.debug("Observer: "+t.toString());
        view.showError(t.getLocalizedMessage());
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
