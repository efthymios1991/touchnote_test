package eu.applogic.touchnote.view.main;

import java.util.List;

import eu.applogic.touchnote.models.Post;

/**
 * Created by efthymioskontis on 13/1/18.
 */

public interface MainContract {

    interface MainView{

        void showProgressIndicator(boolean show);

        void showData(List<Post> mData);

        void showNoData(boolean show);

        void showNoInternet();

        void showError(String message);
    }

    interface MainPresenter{

        void onAttachView(MainView view);

        void onDetachView();

        void loadData();

        void onDestroy();

    }
}
