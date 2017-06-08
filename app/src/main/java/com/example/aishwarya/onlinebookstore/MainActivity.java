package com.example.aishwarya.onlinebookstore;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button login,register;
    public static ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=(EditText)findViewById(R.id.xUsername);
        password=(EditText)findViewById(R.id.xPassword);
        login=(Button)findViewById(R.id.xLogin);
        register=(Button)findViewById(R.id.xRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                pd = ProgressDialog.show(MainActivity.this, "Please wait...", "Loggin in...", false, false);
                final String etpassword = password.getText().toString();
                final String etusername = username.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");

                            if (pd.isShowing()) {
                                pd.dismiss();
                                pd = null;
                            }
                            if (success) {
                                int userid = jsonResponse.getInt("user_id");
                                String role = jsonResponse.getString("role");
                                if(role.equalsIgnoreCase("user")) {
                                    Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                                    intent.putExtra("userid", userid);
                                    MainActivity.this.startActivity(intent);
                                    //Toast.makeText(MainActivity.this, userid+"", Toast.LENGTH_SHORT).show();
                                } else if(role.equalsIgnoreCase("admin")){
                                    //Toast.makeText(MainActivity.this, "admin", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                                    intent.putExtra("userid", userid);
                                    MainActivity.this.startActivity(intent);
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Retry")
                                        .setNegativeButton("OK",null)
                                        .create()
                                        .show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(etusername, etpassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    @Override
    public void onBackPressed() {
        /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)) {
            finish();
        }*/
        finish();
    }

}
