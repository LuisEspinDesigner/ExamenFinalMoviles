package com.example.examenfinalmoviles.MoreView;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.examenfinalmoviles.Models.Journals;
import com.example.examenfinalmoviles.R;
import com.example.examenfinalmoviles.views.Journal;
import com.mindorks.placeholderview.InfinitePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.infinite.LoadMore;

import java.util.ArrayList;

@SuppressLint("NonConstantResourceId")
@Layout(R.layout.progrescarga)
public class MviewJournal {

    public static final int LOAD_VIEW_SET_COUNT = 3;

    private InfinitePlaceHolderView Phviejournal;
    private ArrayList<Journals> ListJournal;

    public MviewJournal(InfinitePlaceHolderView loadMoreView, ArrayList<Journals> JournalList) {
        this.Phviejournal = loadMoreView;
        this.ListJournal = JournalList;
    }

    @LoadMore
    private void onLoadMore(){
        Log.d("DEBUG", "onLoadMore");
        new ForcedWaitedLoading();
    }

    class ForcedWaitedLoading implements Runnable{

        public ForcedWaitedLoading() {
            new Thread(this).start();
        }

        @Override
        public void run() {

            try {
                Thread.currentThread().sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    int count = Phviejournal.getViewCount();
                    Log.d("DEBUG", "count " + count);
                    for (int i = count - 1;
                         i < (count - 1 + MviewJournal.LOAD_VIEW_SET_COUNT) && ListJournal.size() > i;
                         i++) {
                        Phviejournal.addView(new Journal(Phviejournal.getContext(), ListJournal.get(i)));

                        if(i == ListJournal.size() - 1){
                            Phviejournal.noMoreToLoad();
                            break;
                        }
                    }
                    Phviejournal.loadingDone();
                }
            });
        }
    }
}