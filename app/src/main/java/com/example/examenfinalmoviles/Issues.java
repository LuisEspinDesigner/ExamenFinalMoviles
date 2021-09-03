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
import com.example.examenfinalmoviles.Models.ModIssues;
import com.example.examenfinalmoviles.MoreView.MviewJournal;
import com.example.examenfinalmoviles.views.Journal;
import com.example.examenfinalmoviles.views.viewIssus;
import com.mindorks.placeholderview.InfinitePlaceHolderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Issues extends AppCompatActivity {
    private String journal_id;
    private ModIssues issues;
    private ArrayList<ModIssues> issuesList;
    private InfinitePlaceHolderView Phview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        journal_id = getIntent().getExtras().getString("j_id");
        Phview = findViewById(R.id.PhvIssues);
        getIssues();
    }

    private void getIssues() {
        issuesList = new ArrayList<>();
        String url = "https://revistas.uteq.edu.ec/ws/issues.php?j_id=" + journal_id;
        JsonArrayRequest llamado = new JsonArrayRequest(
                Request.Method.POST,
                url,
                null,
                response -> {
                    int size = response.length();
                    for (int i = 0; i < size; i++) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.get(i).toString());
                            issues = new ModIssues();
                            issues.setIssue_id(jsonObject.getString("issue_id"));
                            issues.setVolume(jsonObject.getString("volume"));
                            issues.setNumber(jsonObject.getString("number"));
                            issues.setYear(jsonObject.getString("year"));
                            issues.setDate_published(jsonObject.getString("date_published"));
                            issues.setTitle(jsonObject.getString("title"));
                            issues.setDoi(jsonObject.getString("doi"));
                            issues.setCover(jsonObject.getString("cover"));
                            issuesList.add(issues);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int i = 0; i < MviewJournal.LOAD_VIEW_SET_COUNT; i++) {
                        Phview.addView(new viewIssus(issuesList.get(i), this.getApplicationContext()));
                    }
                    //Phview.setLoadMoreResolver(new LoadMoreViewIssues(mLoadMoreView, feedList));
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