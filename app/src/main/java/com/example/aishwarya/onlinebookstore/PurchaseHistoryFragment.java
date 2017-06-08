package com.example.aishwarya.onlinebookstore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by aishwarya on 04-Apr-17.
 */

public class PurchaseHistoryFragment extends Fragment {

    ListView bookList;
    public static final String JSON_ARRAY = "result";
    public static final String TAG_bookId = "book_id";
    public static final String TAG_bookName = "book_name";
    public static final String TAG_bookPath = "book_path";
    ArrayList<String> bookName,bookId,bookPath;
    public static String path;

    public static String getTAG_bookPath() {
        return TAG_bookPath;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.purchase_history_fragment, container, false);
        UserHomeActivity.title.setText("Purchased Books");

        bookList=(ListView)mainView.findViewById(R.id.xBookList);

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
                    bookPath = new ArrayList<>();

                    for (int i = 0; i < eventsArray.length(); i++) {
                        //Creating a json object of the current index
                        JSONObject obj = null;
                        try {
                            //getting json object from current index
                            obj = eventsArray.getJSONObject(i);
                            bookId.add(obj.getString(TAG_bookId));
                            bookName.add(obj.getString(TAG_bookName));
                            bookPath.add(obj.getString(TAG_bookPath));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    bookList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,bookName));

                    bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            path = bookPath.get(i);
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.beginTransaction().replace(R.id.xUserFrame,new DisplayBookFragment()).addToBackStack(null).commit();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String userid = UserHomeActivity.userid+"";
        PurchaseHistoryRequest purchaseHistoryRequest = new PurchaseHistoryRequest(userid, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(purchaseHistoryRequest);

        return mainView;

    }

}
