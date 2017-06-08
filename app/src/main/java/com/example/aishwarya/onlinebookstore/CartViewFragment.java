package com.example.aishwarya.onlinebookstore;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class CartViewFragment extends Fragment {

    ListView cartList;
    TextView total,price;
    Button checkout;
    ImageView removeBook;
    public static final String JSON_ARRAY = "result";
    public static final String TAG_bookId = "book_id";
    public static final String TAG_bookName = "book_name";
    public static final String TAG_price = "price";
    public static final String TAG_cartId = "cart_id";
    public static int totalPrice=0;
    public static ArrayList<String> bookId,bookName,prices,cartId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.view_cart_fragment, container, false);

        cartList=(ListView)mainView.findViewById(R.id.xCartListView);
        total=(TextView)mainView.findViewById(R.id.xTotal);
        checkout=(Button)mainView.findViewById(R.id.xCheckoutButton);

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
                    prices = new ArrayList<>();
                    cartId = new ArrayList<>();

                    for (int i = 0; i < eventsArray.length(); i++) {
                        //Creating a json object of the current index
                        JSONObject obj = null;
                        try {
                            //getting json object from current index
                            obj = eventsArray.getJSONObject(i);
                            bookId.add(obj.getString(TAG_bookId));
                            bookName.add(obj.getString(TAG_bookName));
                            prices.add(obj.getString(TAG_price));
                            cartId.add(obj.getString(TAG_cartId));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    cartList.setAdapter(new ArrayAdapter<String>(
                            getActivity(), R.layout.cart_row,
                            R.id.xBookName,bookName) {
                        @Override
                        public View getView(final int position, View convertView, ViewGroup parent) {
                            View inflatedView = super.getView(position, convertView, parent);

                            price = (TextView)inflatedView.findViewById(R.id.xPrice);
                            price.setText("Rs. : "+prices.get(position));

                            removeBook = (ImageView)inflatedView.findViewById(R.id.xRemoveBook);
                            removeBook.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //Toast.makeText(getActivity(), cartId.get(position), Toast.LENGTH_SHORT).show();
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            FragmentManager fm = getActivity().getSupportFragmentManager();
                                            fm.beginTransaction().replace(R.id.xUserFrame,new CartViewFragment()).addToBackStack(null).commit();
                                        }
                                    };
                                    RemoveBookRequest removeBookRequest = new RemoveBookRequest(cartId.get(position), responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                                    queue.add(removeBookRequest);
                                }
                            });

                            return inflatedView;
                        }
                    });

                    totalPrice = 0;
                    for (int i = 0; i<prices.size(); i++){
                        try {
                            totalPrice += Integer.parseInt(prices.get(i));
                        } catch(NumberFormatException nfe) {
                        }
                    }

                    total.setText("Total : Rs. "+totalPrice);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String userid = UserHomeActivity.userid+"";
        ViewCartRequest viewCartRequest = new ViewCartRequest(userid, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(viewCartRequest);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.xUserFrame,new CheckoutFragment()).addToBackStack(null).commit();
            }
        });

        return mainView;
    }

}
