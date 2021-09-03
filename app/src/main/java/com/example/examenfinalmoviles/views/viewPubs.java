package com.example.examenfinalmoviles.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.widget.TextView;

import com.example.examenfinalmoviles.Models.Pubs;
import com.example.examenfinalmoviles.R;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
@Layout(R.layout.cardview_pubs)
public class viewPubs {
    @View(R.id.section)
    private TextView sectionTxt;

    @View(R.id.title)
    private TextView titleTxt;

    @View(R.id.abstracts)
    private TextView abstractsTxt;

    @View(R.id.date_published)
    private TextView date_publishedTxt;

    @View(R.id.doi)
    private TextView doiTxt;

    @View(R.id.publication_id)
    private TextView publication_idText;

    private Pubs pubs;
    private Context mContext;

    public viewPubs(Context context, Pubs pubs) {
        mContext = context;
        this.pubs = pubs;
    }

    @Resolve
    private void onResolved() {
        publication_idText.setText(pubs.getPublication_id());
        titleTxt.setText(pubs.getTitle());
        abstractsTxt.setText(Html.fromHtml(pubs.getPabstract()));
        doiTxt.setText(pubs.getDoi());
        date_publishedTxt.setText(pubs.getDate_published());
        sectionTxt.setText(pubs.getSection());
    }
    @Click(R.id.rootView)
    public void onCardClick() {
        getPDF();
    }

    private void getPDF() {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pubs.getDoi())));
    }
}
