package eu.applogic.touchnote.view.main;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.applogic.touchnote.App;
import eu.applogic.touchnote.R;
import eu.applogic.touchnote.di.components.ActivityComponent;
import eu.applogic.touchnote.di.components.DaggerActivityComponent;
import eu.applogic.touchnote.di.modules.ActivityModule;
import eu.applogic.touchnote.models.Post;
import eu.applogic.touchnote.utils.Constrants;
import eu.applogic.touchnote.utils.GridSpacingItemDecoration;
import eu.applogic.touchnote.utils.RecyclerTouchListener;
import eu.applogic.touchnote.utils.ViewUtils;
import eu.applogic.touchnote.view.adapter.PostsAdapter;
import eu.applogic.touchnote.view.details.DetailsActivity;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecycler;

    @BindView(R.id.loadingIndicator)
    ProgressBar loadingIndicator;

    @BindView(R.id.noData)
    TextView noData;

    @Inject
    MainPresenterImpl presenter;

    @Inject
    PostsAdapter mAdapter;

    ActivityComponent mActivityComponent;
    Unbinder unbinder;

    boolean isGrid = false;
    List<Post> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        initializeActivityComponent();

        setToolbar();

        setLayout();

        presenter.onAttachView(this);

        if(savedInstanceState != null){
            restoreState(savedInstanceState);
        }else{
            presenter.loadData();
        }
    }

    private void restoreState(Bundle savedInstanceState) {
        Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(Constrants.VIEW_STATE);
        mData = savedInstanceState.getParcelableArrayList(Constrants.POST_LIST);
        isGrid = savedInstanceState.getBoolean(Constrants.IS_GRID, false);
        invalidateOptionsMenu();
        mAdapter.setGrid(isGrid);

        mRecycler.setLayoutManager(isGrid ?
                new GridLayoutManager(getApplicationContext(), 2) :
                new LinearLayoutManager(getApplicationContext()));

        /*
        mRecycler.addItemDecoration(isGrid ?
                new GridSpacingItemDecoration(2, ViewUtils.dpToPx(getResources(), 10), true) :
                new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                */

        mAdapter.setData(mData);
        mRecycler.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Constrants.IS_GRID, isGrid);
        outState.putParcelableArrayList(Constrants.POST_LIST, new ArrayList<>(mAdapter.getPostList()));
        outState.putParcelable(Constrants.VIEW_STATE, mRecycler.getLayoutManager().onSaveInstanceState());
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem view = menu.findItem(R.id.toggle);
        view.setIcon(getResources().getDrawable((isGrid ? R.drawable.ic_listview_white_24dp : R.drawable.ic_gridview_white_24dp)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggle:
                isGrid = !isGrid;
                mAdapter.setGrid(isGrid);

                mRecycler.setLayoutManager(isGrid ?
                        new GridLayoutManager(getApplicationContext(), 2) :
                        new LinearLayoutManager(getApplicationContext()));

                /*
                mRecycler.addItemDecoration(isGrid ?
                        new GridSpacingItemDecoration(2, ViewUtils.dpToPx(getResources(), 10), true) :
                        new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                        */


                mRecycler.setAdapter(mAdapter);

                invalidateOptionsMenu();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.onDestroy();
            presenter = null;
        }
        unbindView();
    }

    private void unbindView(){
        if(unbinder!=null){
            unbinder.unbind();
            unbinder = null;
        }
    }

    private void setLayout() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Post post = mAdapter.getPostList().get(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(Constrants.POST, post);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {}
        }));
        mRecycler.setAdapter(mAdapter);
    }

    private void initializeActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(((App) getApplication()).getAppComponent())
                .build();
        mActivityComponent.inject(this);
    }

    @Override
    public void showProgressIndicator(boolean show) {
        if(show){
            loadingIndicator.setVisibility(View.VISIBLE);
        }else{
            loadingIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public void showData(List<Post> mData) {

        this.mData.clear();
        this.mData = mData;

        mAdapter.setData(mData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNoData(boolean show) {
        if(show){
            noData.setVisibility(View.VISIBLE);
        }else{
            noData.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNoInternet() {}

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
