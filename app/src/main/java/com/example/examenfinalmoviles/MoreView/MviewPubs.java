package com.example.examenfinalmoviles.MoreView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.examenfinalmoviles.Models.Pubs;
import com.example.examenfinalmoviles.views.viewPubs;
import com.mindorks.placeholderview.InfinitePlaceHolderView;
import com.mindorks.placeholderview.annotations.infinite.LoadMore;

import java.util.ArrayList;

public class MviewPubs {
    public static final int LOAD_VIEW_SET_COUNT = 6;
    private InfinitePlaceHolderView mLoadMoreView;
    private ArrayList<Pubs> mFeedList;

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
                         i < (count - 1 + MviewPubs.LOAD_VIEW_SET_COUNT) && mFeedList.size() > i;
                         i++) {
                        mLoadMoreView.addView(new viewPubs(mLoadMoreView.getContext(), mFeedList.get(i)));

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
