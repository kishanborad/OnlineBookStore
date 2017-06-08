package com.example.aishwarya.onlinebookstore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by aishwarya on 05-Apr-17.
 */

public class SearchFragment extends Fragment {

    GridView grid;
    public static final String JSON_ARRAY = "result";
    public static final String TAG_bookId = "book_id";
    public static final String TAG_bookName = "book_name";
    public static final String TAG_authorName = "author_name";
    public static final String TAG_publication = "publication";
    public static final String TAG_category = "category";
    public static final String TAG_price = "price";
    public static final String TAG_description = "description";
    public static final String TAG_bookImagePath = "book_image_path";
    ArrayList<String> bookId,bookName,authorName,publication,category,price,description,bookImagePath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.search_fragment, container, false);
        UserHomeActivity.title.setText("Book Store");

        grid = (GridView) mainView.findViewById(R.id.xCategoryGridView);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

                    JSONArray eventsArray = null;
                    JSONObject jsonObject = null;

                    jsonObject = new JSONObject(response);
                    eventsArray = jsonObject.getJSONArray(JSON_ARRAY);

                    bookId = new ArrayList<>();
                    bookName = new ArrayList<>();
                    authorName = new ArrayList<>();
                    publication = new ArrayList<>();
                    category = new ArrayList<>();
                    price = new ArrayList<>();
                    description = new ArrayList<>();
                    bookImagePath = new ArrayList<>();

                    for (int i = 0; i<eventsArray.length(); i++){
                        JSONObject obj = null;
                        try {
                            //getting json object from current index
                            obj = eventsArray.getJSONObject(i);
                            bookId.add(obj.getString(TAG_bookId));
                            bookName.add(obj.getString(TAG_bookName));
                            authorName.add(obj.getString(TAG_authorName));
                            publication.add(obj.getString(TAG_publication));
                            category.add(obj.getString(TAG_category));
                            price.add(obj.getString(TAG_price));
                            description.add(obj.getString(TAG_description));
                            bookImagePath.add(obj.getString(TAG_bookImagePath));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    grid.setAdapter(new SearchFragment.gridAdapter(getContext(),bookName,authorName,price,bookImagePath));

                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            UserHomeActivity.book_id = bookId.get(i);
                            UserHomeActivity.book_name=bookName.get(i);
                            UserHomeActivity.author_name=authorName.get(i);
                            UserHomeActivity.description=description.get(i);
                            UserHomeActivity.price=price.get(i);
                            UserHomeActivity.imagePath=bookImagePath.get(i);
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.beginTransaction().replace(R.id.xUserFrame,new ViewBookFragment()).addToBackStack(null).commit();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        SearchRequest dataRequest = new SearchRequest(CategoriesFragment.key, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(dataRequest);

        return mainView;
    }

    class DashboardItem {
        String dashboardIconName;
        String authorName;
        String price;
        DashboardItem(String dashboardIconName,String authorName,String price){
            this.dashboardIconName=dashboardIconName;
            this.authorName=authorName;
            this.price=price;
        }
    }

    class gridAdapter extends BaseAdapter {
        ArrayList<SearchFragment.DashboardItem> list;
        Context context;
        ArrayList<String> bookName = new ArrayList<>();
        ArrayList<String> authorName = new ArrayList<>();
        ArrayList<String> price = new ArrayList<>();
        ArrayList<String> imagePath = new ArrayList<>();

        gridAdapter(Context context, ArrayList<String> bookName, ArrayList<String> authorName, ArrayList<String> price, ArrayList<String> imagePath) {
            this.context = context;
            this.bookName = bookName;
            this.authorName = authorName;
            this.price = price;
            this.imagePath = imagePath;

            list = new ArrayList<SearchFragment.DashboardItem>();

            for (int i = 0; i < bookName.size() ; i++) {
                SearchFragment.DashboardItem tempDashboardItem = new SearchFragment.DashboardItem(
                        bookName.get(i),
                        authorName.get(i),price.get(i));
                list.add(tempDashboardItem);
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        //this one is used for the databases related activities
        public long getItemId(int i) {
            return i;
        }

        class ViewHolder {
            ImageView myDashboardIcon;
            TextView myDashboardIconName,myAuthorName,myPrice;
            RelativeLayout relative;

            ViewHolder(View v) {
                myDashboardIcon = (ImageView) v.findViewById(R.id.singleItem);
                myDashboardIconName=(TextView)v.findViewById(R.id.singleItemName);
                myAuthorName = (TextView)v.findViewById(R.id.xAuthorName);
                myPrice = (TextView)v.findViewById(R.id.xPrice);
                relative=(RelativeLayout)v.findViewById(R.id.xRelative);
            }
        }

        SearchFragment.gridAdapter.ViewHolder holder;
        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View row = view;
            holder = null;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.single_item, viewGroup, false);
                holder = new SearchFragment.gridAdapter.ViewHolder(row);
                row.setTag(holder);
            } else {
                holder = (SearchFragment.gridAdapter.ViewHolder) row.getTag();
            }
            SearchFragment.DashboardItem temp = list.get(i);

            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imagePath.get(i)).getContent());
                        holder.myDashboardIcon.setImageBitmap(bitmap);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            th.start();
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            holder.myDashboardIconName.setText(temp.dashboardIconName);
            holder.myAuthorName.setText(temp.authorName);
            holder.myPrice.setText(temp.price);
            return row;
        }
    }

}
