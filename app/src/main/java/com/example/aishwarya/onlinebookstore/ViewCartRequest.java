package com.example.aishwarya.onlinebookstore;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aishwarya on 04-Apr-17.
 */

public class ViewCartRequest extends StringRequest {
    // Url is missing please enter login url
   
    private Map<String,String> params;

    public ViewCartRequest(String userid, Response.Listener<String> listener){

        super(Method.POST,VIEW_REQUEST_URL,listener,null);

        params = new HashMap<>();
        params.put("userid", userid);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

}

