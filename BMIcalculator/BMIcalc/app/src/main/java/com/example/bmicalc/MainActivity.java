package com.example.bmicalc;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button callapi;
    Button educate;
    TextView label;
    TextView message;
    String educateurl = "https://www.cdc.gov/bmi/?CDC_AAref_Val=https://www.cdc.gov/healthyweight/assessing/bmi/index.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        callapi = findViewById(R.id.button);
        educate = findViewById(R.id.button2);
        label = findViewById(R.id.textView6);
        message = findViewById(R.id.textView5);
        callapi.setOnClickListener(View -> getBMI());
        educate.setOnClickListener(View -> getEducation());
    }
    private void getEducation(){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(educateurl));
        startActivity(i);
    }
    private void getBMI(){
        RequestQueue volleyqueue = Volley.newRequestQueue(MainActivity.this);

        String url = "https://jig2ag6wwdvb52n6jrexlf3n7u0comxh.lambda-url.us-west-2.on.aws/?height=" + getHeight() + "&weight=" + getWeight();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    String s;
                    String risk;
                    try {
                        s = response.getString("bmi");
                        risk = response.getString("risk");
                        label.setText(s);
                        message.setText(risk);
                        double bmi = Double.parseDouble(s);
                        if(bmi < 18){
                            message.setTextColor(Color.parseColor("#0000FF"));
                        }else if((bmi >= 18) && (bmi < 25)){
                            message.setTextColor(Color.parseColor("#008000"));
                        }else if((bmi >= 25) && (bmi <= 30)){
                            message.setTextColor(Color.parseColor("##A020F0"));
                        }else {
                            message.setTextColor(Color.parseColor("#FF0000"));
                        }

                    } catch(JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
            Toast.makeText(MainActivity.this, "uh oh", Toast.LENGTH_SHORT).show();

        }
        );
        volleyqueue.add(request);
    }
    private String getHeight(){
        EditText id = findViewById(R.id.height_id);
        return id.getText().toString();
    }

    private String getWeight(){
        EditText id = findViewById(R.id.weight_id);
        return id.getText().toString();

    }
}