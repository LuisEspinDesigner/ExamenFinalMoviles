package com.example.examenfinalmoviles.MoreView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.examenfinalmoviles.Models.ModIssues;
import com.example.examenfinalmoviles.R;
import com.example.examenfinalmoviles.views.viewIssus;
import com.mindorks.placeholderview.InfinitePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.infinite.LoadMore;

import java.util.ArrayList;
@Layout(R.layout.cardviewissues)
public class MviewIssus {
    public static final int LOAD_VIEW_SET_COUNT = 3;
    private InfinitePlaceHolderView mLoadMoreView;
    private ArrayList<ModIssues> mFeedList;

    public MviewIssus(InfinitePlaceHolderView loadMoreView, ArrayList<ModIssues> feedList) {
        this.mLoadMoreView = loadMoreView;
        this.mFeedList = feedList;
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
                    int count = mLoadMoreView.getViewCount();
                    Log.d("DEBUG", "count " + count);
                    for (int i = count - 1;
                         i < (count - 1 + MviewIssus.LOAD_VIEW_SET_COUNT) && mFeedList.size() > i;
                         i++) {
                        mLoadMoreView.addView(new viewIssus(mFeedList.get(i), mLoadMoreView.getContext()));

                        if(i == mFeedList.size() - 1){
                            mLoadMoreView.noMoreToLoad();
                            break;
                        }
                    }
                    mLoadMoreView.loadingDone();
                }
            });
        }
    }
}
