package eu.applogic.touchnote.data;

import java.util.List;

import eu.applogic.touchnote.models.Post;
import io.reactivex.Observable;

/**
 * Created by efthymioskontis on 16/1/18.
 */

public interface DataManager {

    Observable<List<Post>> getPosts();
}
