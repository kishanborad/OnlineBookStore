package com.example.aishwarya.onlinebookstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by aishwarya on 03-Apr-17.
 */

public class AdminViewBookFragment extends Fragment {

    ImageView bookImage;
    TextView bookName,authorName,description,price;
    Button delete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.admin_view_book_fragment, container, false);
        AdminHomeActivity.title.setText("Book Store");

        bookImage=(ImageView)mainView.findViewById(R.id.xBookImageView);
        bookName=(TextView)mainView.findViewById(R.id.xBookNameTextView);
        authorName=(TextView)mainView.findViewById(R.id.xAuthorNameTextView);
        description=(TextView)mainView.findViewById(R.id.xDescriptionTextView);
        price=(TextView)mainView.findViewById(R.id.xPriceTextView);
        delete=(Button)mainView.findViewById(R.id.xDelete);

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(AdminHomeActivity.imagePath).getContent());
                    bookImage.setImageBitmap(bitmap);
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

        bookName.setText(AdminHomeActivity.book_name);
        authorName.setText(AdminHomeActivity.author_name);
        description.setText(AdminHomeActivity.description);
        price.setText("Price : Rs. "+AdminHomeActivity.price);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String bookid = AdminHomeActivity.book_id;
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Book deleted.")
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                                fm.beginTransaction().replace(R.id.xUserFrame,new AdminCategoriesFragment()).addToBackStack(null).commit();
                                            }
                                        })
                                        .create()
                                        .show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                DeleteBookRequest deleteRequest = new DeleteBookRequest(bookid, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(deleteRequest);
            }
        });

        return mainView;
    }

}
