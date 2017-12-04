package com.tmoo7.hyperdesign.Layers;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmoo7.hyperdesign.Models.ProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by othello on 12/2/2017.
 */

public class VolleyRequests {

    private Context mContext;
    private  OnRequestFinished mOnRequestFinished;
    public VolleyRequests(Context context, final OnRequestFinished onRequestFinished ) {
        this.mContext = context;
        this.mOnRequestFinished = onRequestFinished;
    }
    public interface OnRequestFinished
    {
        void onrequestCompeleted(int Code , List<ProductModel> productModels);

    }

    public void volleyJsonObjectRequest(int Method,String url){

        String  REQUEST_TAG = "HyperDesignObject";
         JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Method,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TAG", response.toString());


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "Error: " + error.getMessage());
             }
        });

         VolleySingleton.getInstance(mContext).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
    }
    public void volleyJsonArrayRequest(String url){

        String  REQUEST_TAG = "HyperDesignArray";

        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("TAG", response.toString());
                        if (response.length() != 0) {
                            List<ProductModel> productModelArrayList = new ArrayList<ProductModel>();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    int id = object.getInt("id");
                                    String productDescription = object.getString("productDescription");
                                    float price = (float) object.getDouble("price");
                                    String url = object.getJSONObject("image").getString("url");
                                    ProductModel productModel = new ProductModel(id, productDescription, url, price);
                                    productModelArrayList.add(productModel);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            mOnRequestFinished.onrequestCompeleted(200, productModelArrayList);

                        }
                        else
                        {
                            mOnRequestFinished.onrequestCompeleted(1, null);

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 Log.e("TAG", "Error: " + error.getMessage());
                mOnRequestFinished.onrequestCompeleted(2, null);

            }
        }

        )
        {

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
              int Code =  response.statusCode;

                return super.parseNetworkResponse(response);
            }
        };





        // Adding JsonObject request to request queue
        VolleySingleton.getInstance(mContext).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
    }
}
