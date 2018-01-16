package eu.applogic.touchnote.data;

import android.content.Context;

import java.util.List;

import javax.inject.Singleton;

import eu.applogic.touchnote.data.api.ApiInterface;
import eu.applogic.touchnote.di.ApplicationContext;
import eu.applogic.touchnote.models.Post;
import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by efthymioskontis on 16/1/18.
 */

@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final Retrofit retrofit;

    public AppDataManager(@ApplicationContext Context context,
                          Retrofit retrofit){
        this.mContext = context;
        this.retrofit = retrofit;
    }

    @Override
    public Observable<List<Post>> getPosts() {
        return retrofit.create(ApiInterface.class).getPosts();
    }
}
