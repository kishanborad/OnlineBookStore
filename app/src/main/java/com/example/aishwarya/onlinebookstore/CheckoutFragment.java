package com.example.aishwarya.onlinebookstore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by aishwarya on 04-Apr-17.
 */

public class CheckoutFragment extends Fragment {

    ListView bookList;
    TextView total;
    Button checkout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.checkout_fragment, container, false);
        UserHomeActivity.title.setText("Confirm Order");

        bookList=(ListView) mainView.findViewById(R.id.xCartListView);
        total=(TextView)mainView.findViewById(R.id.xTotal);
        checkout=(Button)mainView.findViewById(R.id.xConfirmButton);

        bookList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,CartViewFragment.bookName));
        total.setText("Total Payable Amount : Rs. "+CartViewFragment.totalPrice);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        CartViewFragment.totalPrice = 0;
                        Toast.makeText(getActivity(), "Books purchased.", Toast.LENGTH_SHORT).show();
                    }
                };
                String userid = UserHomeActivity.userid + "";
                ConfirmOrderRequest confirmCartRequest = new ConfirmOrderRequest(userid, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(confirmCartRequest);
            }
        });

        return  mainView;
    }
}
