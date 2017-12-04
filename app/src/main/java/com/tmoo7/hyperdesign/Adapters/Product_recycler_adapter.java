package com.tmoo7.hyperdesign.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tmoo7.hyperdesign.Models.ProductModel;
import com.tmoo7.hyperdesign.R;

import java.util.List;

/**
 * Created by othello on 12/3/2017.
 */

public class Product_recycler_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  static final String TAG = "rec";
    private Context mContext;
    private List<ProductModel> mProductModels;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public Product_recycler_adapter(Context context, List<ProductModel> productModels ,RecyclerView recyclerView) {
        mContext = context;
        mProductModels = productModels;
     }
    @Override
    public int getItemViewType(int position) {
        return mProductModels.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
  //      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
  //      return new MyViewHolder(view);
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_item, parent, false);

            vh = new MyViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {

            MyViewHolder holders = (MyViewHolder) holder;

            holders.product_description.setText(mProductModels.get(position).getProductDescription());
            holders.product_price.setText(mProductModels.get(position).getPrice() + " $");
            Picasso.with(mContext)
                    .load(mProductModels.get(position).getUrl())
                    .placeholder(R.drawable.loading)
                    .into(holders.product_image);
        }
        else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
     }

    public ProductModel getitem (int posistion)
    {
        return  ((mProductModels != null) && ( mProductModels.size() != 0) ? mProductModels.get(posistion) : null);
    }

    @Override
    public int getItemCount() {
        return ((mProductModels !=null) && (mProductModels.size() != 0) ? mProductModels.size() : 0) ;
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView product_description= null;
        TextView product_price= null;
        ImageView product_image = null;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.product_description = (TextView) itemView.findViewById(R.id.product_description);
            this.product_price = (TextView) itemView.findViewById(R.id.product_price);
            this.product_image = (ImageView) itemView.findViewById(R.id.product_image);
        }
    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }
}
