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
import com.example.examenfinalmoviles.Models.Journals;
import com.example.examenfinalmoviles.MoreView.MviewJournal;
import com.example.examenfinalmoviles.views.Journal;
import com.mindorks.placeholderview.InfinitePlaceHolderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private InfinitePlaceHolderView Phview;
    List<Journals> ListaJournal;
    Journals journal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Phview = findViewById(R.id.PhviewPricipal);
        ListaJournal = new ArrayList<>();
        returnListaJournal();
    }

    private void returnListaJournal() {

        String UrlRegions = "https://revistas.uteq.edu.ec/ws/journals.php";
        JsonArrayRequest llamado = new JsonArrayRequest(
                Request.Method.GET,
                UrlRegions,
                null,
                response -> {
                    int size = response.length();
                    for (int i = 0; i < size; i++) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.get(i).toString());
                            journal = new Journals();
                            journal.setJournal_id(jsonObject.getString("journal_id"));
                            journal.setPortada(jsonObject.getString("portada"));
                            journal.setAbbreviation(jsonObject.getString("abbreviation"));
                            journal.setDescripcion(jsonObject.getString("description"));
                            journal.setName(jsonObject.getString("name"));
                            ListaJournal.add(journal);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int i = 0; i < MviewJournal.LOAD_VIEW_SET_COUNT; i++) {
                        Phview.addView(new Journal(this.getApplicationContext(), ListaJournal.get(i)));
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