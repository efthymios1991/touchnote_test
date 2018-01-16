package eu.applogic.touchnote.data.api;

import java.util.List;

import eu.applogic.touchnote.models.Post;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by efthymioskontis on 13/1/18.
 */

public interface ApiInterface {

    @GET("v2/57ee2ca8260000f80e1110fa")
    Observable<List<Post>> getPosts();

}
