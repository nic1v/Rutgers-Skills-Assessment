package com.example.rutgersapiassignment;

import android.app.ActionBar;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.rutgersapiassignment.MainActivity.webView;

public class ApiCall extends AsyncTask<String,Void,String> {
    Context context;
    public ApiCall(Context c) {

        context = c;
    }
        @Override
        protected String doInBackground (String...params){
            try {
                URL url = new URL("http://www.recipepuppy.com/api/?q=" + MainActivity.searchText.getText()+"&i="+MainActivity.ingredientText.getText());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s){
        try{

            Log.e("JSONSTRING",s);
            JSONObject obj = new JSONObject(s);
            JSONArray results = obj.getJSONArray("results");

            String myTable="";
            for(int i=0; i<results.length()-1;i++){
                JSONObject currentObj = results.getJSONObject(i);
                TextView textView =  new TextView(context);
                ImageView imageView = new ImageView(context);
                View view = new View(context);
                view.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,5));
                imageView.setLayoutParams(new ViewGroup.LayoutParams(300,300));

                String[] array =  currentObj.getString("ingredients").split(",");
                Arrays.sort(array);


                textView.setText(currentObj.getString("title")+"\n\n"+"Indredients: \n"+printArray(array)+"\n\n"+currentObj.getString("href")+"\n\n");

                String imagePath = currentObj.getString("thumbnail");
                if(currentObj.getString("thumbnail")!=null){
                    try{
                        Picasso.get().load(String.valueOf(new URL(imagePath.trim()))).into(imageView);
                        MainActivity.linearLayout.addView(imageView);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
                myTable = myTable + "<table border=1>" +
                        "<tr>" +
                        "<td>"+currentObj.getString("title")+"</td>" +
                        "<td>"+printArray(array)+"</td>" +
                        "<td>"+imagePath+"</td>"+
                        "</tr>" +
                        "</table>";

                MainActivity.linearLayout.addView(textView);
                MainActivity.linearLayout.addView(view);

            }webView.loadDataWithBaseURL(null, myTable, "text/html", "utf-8", null);

            //JSONArray recipe = title.getJSONArray("ingredients");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        }

        public IBinder onBind (Intent intent){
            return null;
        }

        private String printArray(String[] arrayLists){
        String ingredients = "";
        for(int z = 0; z<arrayLists.length-1;z++){
            ingredients= ingredients +","+arrayLists[z];
        }
        ingredients.substring(1);
        return ingredients;
        }
    }



