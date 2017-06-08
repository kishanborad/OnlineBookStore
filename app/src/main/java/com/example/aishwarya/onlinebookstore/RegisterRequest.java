package com.example.aishwarya.onlinebookstore;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mayur on 07-01-2017.
 */

public class RegisterRequest extends StringRequest {
    // login url
    
    private Map<String,String> params;

    public RegisterRequest(String fullname, String username, String password, Response.Listener<String> listener){

        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);

        params = new HashMap<>();
        params.put("fullname",fullname);
        params.put("username", username);
        params.put("password", password);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

}
