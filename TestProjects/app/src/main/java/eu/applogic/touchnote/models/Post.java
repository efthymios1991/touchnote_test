package eu.applogic.touchnote.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by efthymioskontis on 13/1/18.
 */

public class Post implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("description")
    private String description;

    @SerializedName("date")
    private String date;

    @SerializedName("tags")
    private List<String> tags = new ArrayList<>();

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    public Post(String id, String description, String date, List<String> tags, String title, String image) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.tags = tags;
        this.title = title;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.description);
        dest.writeString(this.date);
        dest.writeList(this.tags);
        dest.writeString(this.title);
        dest.writeString(this.image);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    private Post(Parcel in) {
        this.id = in.readString();
        this.description = in.readString();
        this.date = in.readString();
        in.readList(tags, Post.class.getClassLoader());
        this.title = in.readString();
        this.image = in.readString();
    }
}
