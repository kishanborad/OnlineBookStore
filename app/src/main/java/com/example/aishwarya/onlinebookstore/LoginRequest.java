package com.example.aishwarya.onlinebookstore;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mayur on 24-10-2016.
 */

public class LoginRequest extends StringRequest {
    // login url
    
    private Map<String,String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener){

        super(Method.POST,LOGIN_REQUEST_URL,listener,null);

        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

}
