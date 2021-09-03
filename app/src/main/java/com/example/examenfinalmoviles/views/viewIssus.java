package com.example.examenfinalmoviles.views;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.examenfinalmoviles.Models.ModIssues;
import com.example.examenfinalmoviles.R;
import com.example.examenfinalmoviles.publicacion;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
@Layout(R.layout.cardviewissues)
public class viewIssus {

    @View(R.id.issuesid)
    private TextView issuesidTxt;

    @View(R.id.title)
    private TextView titleTxt;

    @View(R.id.datos)
    private TextView datosTxt;

    @View(R.id.date_published)
    private TextView date_publishedTxt;

    @View(R.id.doi)
    private TextView doiTxt;

    @View(R.id.cover)
    private ImageView cover;

    public viewIssus(ModIssues mInfo, Context mContext) {
        this.IssusMod = mInfo;
        this.mContext = mContext;
    }

    private ModIssues IssusMod;
    private Context mContext;
    @Resolve
    private void onResolved() {
        try {
            issuesidTxt.setText(IssusMod.getIssue_id());
            titleTxt.setText(IssusMod.getTitle());
            datosTxt.setText("Volumen: " + IssusMod.getVolume() + " - Número: " + IssusMod.getNumber() +  " - Año: " + IssusMod.getYear());
            doiTxt.setText(IssusMod.getDoi());
            date_publishedTxt.setText(IssusMod.getDate_published());
            Glide.with(mContext).load(IssusMod.getCover()).into(cover);
        }catch (Exception exception) {
            Toast.makeText(null, "ex", Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.rootView)
    public void onCardClick() {
        Intent intent = new Intent(mContext, publicacion.class);
        intent.putExtra("issues_id", IssusMod.getIssue_id());
        Log.d("issues_id", "onClick");
        mContext.startActivity(intent);
    }
}
