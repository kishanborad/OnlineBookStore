package com.example.aishwarya.onlinebookstore;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by aishwarya on 02-Apr-17.
 */

public class CategoriesFragment extends Fragment {

    ListView categoryList;
    public static final String JSON_ARRAY = "result";
    public static final String TAG_bookId = "book_id";
    public static final String TAG_bookName = "book_name";
    public static final String TAG_authorName = "author_name";
    public static final String TAG_publication = "publication";
    public static final String TAG_category = "category";
    public static final String TAG_price = "price";
    public static final String TAG_description = "description";
    public static final String TAG_bookImagePath = "book_image_path";
    public static String key;
    public static ProgressDialog pd;
    EditText searchet;
    Button searchbtn;
    public static ArrayList<DataCategoryWise> art;
    public static ArrayList<DataCategoryWise> fiction;
    public static ArrayList<DataCategoryWise> history;
    public static ArrayList<DataCategoryWise> howItWorks;
    public static ArrayList<DataCategoryWise> other;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.categories_fragment, container, false);
        UserHomeActivity.title.setText("Book Store");

        categoryList = (ListView)mainView.findViewById(R.id.xCategoryListView);
        searchet=(EditText)mainView.findViewById(R.id.xSearchEditText);
        searchbtn=(Button)mainView.findViewById(R.id.xSearchButton);

        //Toast.makeText(getActivity(), UserHomeActivity.userid+"", Toast.LENGTH_SHORT).show();
        pd = ProgressDialog.show(getActivity(), "Please wait...", "Fetching data...", false, false);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONArray eventsArray = null;
                JSONObject jsonObject = null;
                if (pd.isShowing()) {
                    pd.dismiss();
                    pd = null;
                }
                try {
                    //Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                    jsonObject = new JSONObject(response);
                    eventsArray = jsonObject.getJSONArray(JSON_ARRAY);
                    //For category = Art
                    art = new ArrayList<>();
                    //For category = Fiction
                    fiction = new ArrayList<>();

                    //For category = History
                    history = new ArrayList<>();

                    //For category = How It Works
                    howItWorks = new ArrayList<>();

                    //For category = Other
                    other = new ArrayList<>();

                    for (int i = 0; i < eventsArray.length(); i++) {
                        //Creating a json object of the current index
                        JSONObject obj = null;
                        try {
                            //getting json object from current index
                            obj = eventsArray.getJSONObject(i);
                            String tempCategory = obj.getString(TAG_category);
                            if (tempCategory.equalsIgnoreCase("Art")){
                                DataCategoryWise art_obj = new DataCategoryWise();
                                art_obj.bookId = obj.getString(TAG_bookId);
                                art_obj.bookName=obj.getString(TAG_bookName);
                                art_obj.authorName=obj.getString(TAG_authorName);
                                art_obj.publication=obj.getString(TAG_publication);
                                art_obj.category=obj.getString(TAG_category);
                                art_obj.price=obj.getString(TAG_price);
                                art_obj.description=obj.getString(TAG_description);
                                art_obj.bookImagePath=obj.getString(TAG_bookImagePath);
                                art.add(art_obj);
                            } else if (tempCategory.equalsIgnoreCase("Fiction")){
                                DataCategoryWise fiction_obj = new DataCategoryWise();
                                fiction_obj.bookId = obj.getString(TAG_bookId);
                                fiction_obj.bookName=obj.getString(TAG_bookName);
                                fiction_obj.authorName=obj.getString(TAG_authorName);
                                fiction_obj.publication=obj.getString(TAG_publication);
                                fiction_obj.category=obj.getString(TAG_category);
                                fiction_obj.price=obj.getString(TAG_price);
                                fiction_obj.description=obj.getString(TAG_description);
                                fiction_obj.bookImagePath=obj.getString(TAG_bookImagePath);
                                fiction.add(fiction_obj);
                            } else if (tempCategory.equalsIgnoreCase("History")){
                                DataCategoryWise history_obj = new DataCategoryWise();
                                history_obj.bookId = obj.getString(TAG_bookId);
                                history_obj.bookName=obj.getString(TAG_bookName);
                                history_obj.authorName=obj.getString(TAG_authorName);
                                history_obj.publication=obj.getString(TAG_publication);
                                history_obj.category=obj.getString(TAG_category);
                                history_obj.price=obj.getString(TAG_price);
                                history_obj.description=obj.getString(TAG_description);
                                history_obj.bookImagePath=obj.getString(TAG_bookImagePath);
                                history.add(history_obj);
                            } else if (tempCategory.equalsIgnoreCase("How It Works")){
                                DataCategoryWise hiw_obj = new DataCategoryWise();
                                hiw_obj.bookId = obj.getString(TAG_bookId);
                                hiw_obj.bookName=obj.getString(TAG_bookName);
                                hiw_obj.authorName=obj.getString(TAG_authorName);
                                hiw_obj.publication=obj.getString(TAG_publication);
                                hiw_obj.category=obj.getString(TAG_category);
                                hiw_obj.price=obj.getString(TAG_price);
                                hiw_obj.description=obj.getString(TAG_description);
                                hiw_obj.bookImagePath=obj.getString(TAG_bookImagePath);
                                howItWorks.add(hiw_obj);
                            } else if (tempCategory.equalsIgnoreCase("Other")){
                                DataCategoryWise other_obj = new DataCategoryWise();
                                other_obj.bookId = obj.getString(TAG_bookId);
                                other_obj.bookName=obj.getString(TAG_bookName);
                                other_obj.authorName=obj.getString(TAG_authorName);
                                other_obj.publication=obj.getString(TAG_publication);
                                other_obj.category=obj.getString(TAG_category);
                                other_obj.price=obj.getString(TAG_price);
                                other_obj.description=obj.getString(TAG_description);
                                other_obj.bookImagePath=obj.getString(TAG_bookImagePath);
                                other.add(other_obj);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    String[] category = {"Arts","Fiction","History","How It Works","Other"};
                    categoryList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,category));

                    categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            if (i==0){
                                fm.beginTransaction().replace(R.id.xUserFrame,new ArtsFragment()).addToBackStack(null).commit();
                            } else if (i==1){
                                fm.beginTransaction().replace(R.id.xUserFrame,new FictionFragment()).addToBackStack(null).commit();
                            } else if (i==2){
                                fm.beginTransaction().replace(R.id.xUserFrame,new HistoryFragment()).addToBackStack(null).commit();
                            } else if (i==3){
                                fm.beginTransaction().replace(R.id.xUserFrame,new HowItWorksFragment()).addToBackStack(null).commit();
                            } else if (i==4){
                                fm.beginTransaction().replace(R.id.xUserFrame,new OtherFragment()).addToBackStack(null).commit();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DataRequest dataRequest = new DataRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(dataRequest);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = searchet.getText().toString();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.xUserFrame,new SearchFragment()).addToBackStack(null).commit();
            }
        });

        return mainView;
    }

    class DataCategoryWise{
        String bookId,bookName,authorName,publication,category,price,description,bookImagePath;

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getPublication() {
            return publication;
        }

        public void setPublication(String publication) {
            this.publication = publication;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBookImagePath() {
            return bookImagePath;
        }

        public void setBookImagePath(String bookImagePath) {
            this.bookImagePath = bookImagePath;
        }
    }

    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    // handle back button

                    return true;

                }

                return false;
            }
        });
    }

}