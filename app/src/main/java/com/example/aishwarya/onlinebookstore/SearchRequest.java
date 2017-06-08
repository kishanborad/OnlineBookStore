package com.example.aishwarya.onlinebookstore;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aishwarya on 05-Apr-17.
 */

public class SearchRequest extends StringRequest {
    // login url
    
    private Map<String,String> params;

    public SearchRequest(String key, Response.Listener<String> listener){

        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);

        params = new HashMap<>();
        params.put("key", key);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

}

