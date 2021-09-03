package com.example.examenfinalmoviles.views;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.examenfinalmoviles.Issues;
import com.example.examenfinalmoviles.Models.Journals;
import com.example.examenfinalmoviles.R;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@Layout(R.layout.cardviewjournal)
public class Journal {
    @View(R.id.name)
    private TextView nameTxt;

    @View(R.id.abreviatura)
    private TextView abreviaturaTxt;

    @View(R.id.journalid)
    private TextView journalidTxt;

    @View(R.id.descripcion)
    private TextView descripcionTxt;

    @View(R.id.portada)
    private ImageView portada;

    private Journals mInfo;
    private Context mContext;

    public Journal(Context applicationContext, Journals info) {
        mContext = applicationContext;
        mInfo = info;
    }


    @Resolve
    private void onResolved() {
        nameTxt.setText(mInfo.getName());
        abreviaturaTxt.setText(mInfo.getAbbreviation());
        descripcionTxt.setText(Html.fromHtml(mInfo.getDescripcion()));
        journalidTxt.setText(mInfo.getJournal_id());
        Glide.with(mContext).load(mInfo.getPortada()).into(portada);
    }

    @Click(R.id.CardViewjournal)
    public void onCardClick() {
        //Toast.makeText(mContext, mInfo.getJournal_id(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, Issues.class);
        intent.putExtra("j_id", mInfo.getJournal_id());
        Log.d("j_id", "onClick");
        mContext.startActivity(intent);
    }


}
