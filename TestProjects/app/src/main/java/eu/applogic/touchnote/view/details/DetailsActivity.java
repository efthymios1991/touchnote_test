package eu.applogic.touchnote.view.details;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.applogic.touchnote.R;
import eu.applogic.touchnote.models.Post;
import eu.applogic.touchnote.utils.Constrants;
import eu.applogic.touchnote.utils.DebugLogger;
import eu.applogic.touchnote.view.custom.CustomImageView;

/**
 * Created by efthymioskontis on 13/1/18.
 */

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.customImage)
    CustomImageView customImageView;

    @BindView(R.id.seekBar)
    SeekBar seekBar;

    Unbinder unbinder;
    Post fetchedPost;

    int seekBarProgress = 0;
    int width;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.details_activity);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;

        unbinder = ButterKnife.bind(this);

        if(savedInstanceState!=null){
            fetchedPost = savedInstanceState.getParcelable(Constrants.POST);
            seekBarProgress = savedInstanceState.getInt(Constrants.SEEK_BAR_PROGRESS);
        }else{
            if(getIntent().getExtras()!=null){
                fetchedPost = getIntent().getExtras().getParcelable(Constrants.POST);
            }
        }

        if(fetchedPost == null){

            //Cause if finish is called inside onCreate, onDestroy wont be called
            unbindView();
            finish();
        }

        setToolbar();

        setLayout();

        loadViewData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constrants.SEEK_BAR_PROGRESS, seekBar.getProgress());
        outState.putParcelable(Constrants.POST, fetchedPost);
    }

    private void loadViewData() {
        Glide.with(getApplicationContext())
                .load(fetchedPost.getImage())
                .into(customImageView);
    }

    private void setLayout() {
        seekBar.setMax(width/2);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                DebugLogger.debug("Progress of bar: "+progress);
                customImageView.changeCornerRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        seekBar.setProgress(seekBarProgress);
        customImageView.changeCornerRadius(seekBarProgress);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(fetchedPost.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindView();
    }

    private void unbindView(){
        if(unbinder!=null){
            unbinder.unbind();
            unbinder = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
