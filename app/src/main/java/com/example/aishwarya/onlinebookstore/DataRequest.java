package com.example.aishwarya.onlinebookstore;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aishwarya on 03-Apr-17.
 */

public class DataRequest extends StringRequest {
    // login url
   
    private Map<String,String> params;

    public DataRequest(Response.Listener<String> listener){

        super(Method.POST,DATA_REQUEST_URL,listener,null);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

}

