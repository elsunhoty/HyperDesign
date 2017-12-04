package com.tmoo7.hyperdesign;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.Toast;

import com.tmoo7.hyperdesign.Adapters.Product_recycler_adapter;
import com.tmoo7.hyperdesign.Layers.VolleyRequests;
import com.tmoo7.hyperdesign.Models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements VolleyRequests.OnRequestFinished {


    RecyclerView product_recycler;
    Product_recycler_adapter mAdapter;
    VolleyRequests volleyRequests;
     List<ProductModel> mList;
    StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    boolean userScrolled = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int LastIdsaved = 0;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        volleyRequests = new VolleyRequests(getApplicationContext(),this);

        mList = new ArrayList<>();
        product_recycler = (RecyclerView) findViewById(R.id.product_recycler);
        mAdapter = new Product_recycler_adapter(MainActivity.this,mList,product_recycler);
        product_recycler.setAdapter(mAdapter);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        product_recycler.setLayoutManager(mStaggeredGridLayoutManager);

        scrollevent();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mList.size() != 0)
        {
            if (mList.get(mList.size()-1) == null)
            {
                outState.putInt("value",mList.get(mList.size()-2).getId());

            }
            else
            {
                outState.putInt("value",mList.get(mList.size()-1).getId());

            }
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState == null){
            LastIdsaved = 0;
        }else{
            LastIdsaved = savedInstanceState.getInt("value",0);
        }
    }

    private void scrollevent() {
        product_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = mStaggeredGridLayoutManager.getChildCount();
                totalItemCount = mStaggeredGridLayoutManager.getItemCount();
                pastVisiblesItems = mStaggeredGridLayoutManager.findFirstVisibleItemPositions(null)[0];
                if (userScrolled
                        && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                    userScrolled = false;

                    updateRecyclerView();
                }
            }
        });



    }

    private void updateRecyclerView() {
        request(mList.get(mList.size()-1).getId()+1,10);
        mList.add(null);
        mAdapter.notifyItemInserted(mList.size() - 1);
             Log.e("SS", "SS");



    }

    private void request(int fromid,int count)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("grapesnberries.getsandbox.com")
                .appendPath("products")
                .appendQueryParameter("count", String.valueOf(count))
                .appendQueryParameter("from", String.valueOf(fromid));
        String myUrl = builder.build().toString();
        volleyRequests.volleyJsonArrayRequest(myUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        request(LastIdsaved,10);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mList.size() != 0)
        {
            if (mList.get(mList.size()-1) == null)
            {
                LastIdsaved = mList.get(mList.size()-2).getId();

            }
            else
            {
                LastIdsaved = mList.get(mList.size()-1).getId();

            }
        }
    }

    @Override
    public void onrequestCompeleted(int Code, List<ProductModel> productModels) {
             if (Code == 200)
            {
                if (mList.size() !=0) {
                    mList.remove(mList.size() - 1);
                    mAdapter.notifyItemRemoved(mList.size());
                }
                    mList.addAll(productModels);
                    mAdapter.notifyDataSetChanged();


            }
        else
             {
                 Toast.makeText(this, "Can`t Get More Products Right Now !", Toast.LENGTH_SHORT).show();
             }
    }
}
