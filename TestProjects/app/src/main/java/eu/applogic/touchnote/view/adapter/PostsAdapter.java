package eu.applogic.touchnote.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.applogic.touchnote.R;
import eu.applogic.touchnote.models.Post;

/**
 * Created by efthymioskontis on 13/1/18.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {

    private List<Post> postList;
    private Context mContext;
    private boolean isGrid = false;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;

        @BindView(R.id.title)
        TextView title;

        @Nullable
        @BindView(R.id.description)
        TextView description;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public PostsAdapter(Context context, List<Post> postList) {
        this.postList = postList;
        this.mContext = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                (isGrid ? R.layout.grid_row : R.layout.list_row), parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.title.setText(post.getTitle());
        Glide.with(mContext)
                .load(post.getImage())
                .into(holder.image);

        if(!isGrid){
            holder.description.setText(post.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return (postList == null ? 0 : postList.size());
    }

    public void setData(List<Post> postList){
        this.postList = postList;
    }

    public List<Post> getPostList(){
        return this.postList;
    }

    public void setGrid(boolean isGrid){
        this.isGrid = isGrid;
    }
}