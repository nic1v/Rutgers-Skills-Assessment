package com.example.rutgersapiassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static EditText searchText;
    public static EditText ingredientText;
    public static WebView webView;

    private Button searchButton;
    public static TextView textView;
    private Toolbar toolbar;
    public ApiCall mApiCall;
    public static LinearLayout linearLayout;
    private Button btnText;
    private Button htmlView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout)findViewById(R.id.ll_main);
        searchButton = (Button) findViewById(R.id.button);
        searchText = (EditText)findViewById(R.id.et_input);
        ingredientText = (EditText) findViewById(R.id.et_ingredients);
        webView = (WebView) findViewById(R.id.web_view);
        btnText = (Button) findViewById(R.id.button3);
        htmlView = (Button) findViewById(R.id.button2);
        webView.setVisibility(View.INVISIBLE);
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                webView.setVisibility(View.INVISIBLE);
            }
        });
        htmlView.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           webView.setVisibility(View.VISIBLE);
                                           linearLayout.setVisibility(View.INVISIBLE);
                                       }
                                   });




        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senderString="";
               mApiCall = new ApiCall(MainActivity.this);
               mApiCall.execute(senderString);

            }
        });
    }
}
