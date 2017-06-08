package com.example.aishwarya.onlinebookstore;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aishwarya on 04-Apr-17.
 */

public class AddToCartRequest extends StringRequest {
    // login url
   
    private Map<String,String> params;

    public AddToCartRequest(String userid, String bookid, Response.Listener<String> listener){

        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);

        params = new HashMap<>();
        params.put("userid", userid);
        params.put("bookid", bookid);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

}

