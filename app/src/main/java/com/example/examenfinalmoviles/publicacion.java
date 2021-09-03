package com.example.examenfinalmoviles;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.examenfinalmoviles.Models.Pubs;
import com.example.examenfinalmoviles.MoreView.MviewPubs;
import com.example.examenfinalmoviles.views.viewPubs;
import com.mindorks.placeholderview.InfinitePlaceHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class publicacion extends AppCompatActivity {
    private String id;
    private ArrayList<Pubs> ListaPubs;
    Pubs pubs;
    private InfinitePlaceHolderView PhvPubs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion);
        id = getIntent().getExtras().getString("issues_id");
        PhvPubs = findViewById(R.id.PhviewPubs);
        getPublic();
    }

    private void getPublic() {
        ListaPubs = new ArrayList<>();
        String url = "https://revistas.uteq.edu.ec/ws/pubs.php?i_id=" + id;
        JsonArrayRequest llamado = new JsonArrayRequest(
                Request.Method.POST,
                url,
                null,
                response -> {
                    int size = response.length();
                    for (int i = 0; i < size; i++) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.get(i).toString());
                            pubs = new Pubs();
                            pubs.setSection(jsonObject.getString("section"));
                            pubs.setPublication_id(jsonObject.getString("publication_id"));
                            pubs.setTitle(jsonObject.getString("title"));
                            pubs.setDoi(jsonObject.getString("doi"));
                            pubs.setPabstract(jsonObject.getString("abstract"));
                            pubs.setDate_published(jsonObject.getString("date_published"));
                            pubs.setSubmission_id(jsonObject.getString("submission_id"));
                            ListaPubs.add(pubs);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    for(int i = 0; i < MviewPubs.LOAD_VIEW_SET_COUNT; i++){
                        PhvPubs.addView(new viewPubs(this.getApplicationContext(), ListaPubs.get(i)));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error is ", "" + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                //params.put("Authorization", "Bearer " + AccessToken);
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue ejecVolley = Volley.newRequestQueue(this);
        ejecVolley.add(llamado);
    }
}