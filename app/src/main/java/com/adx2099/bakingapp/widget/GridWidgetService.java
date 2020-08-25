package com.adx2099.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.adx2099.bakingapp.R;
import com.adx2099.bakingapp.provider.BakingContentProvider;
import com.adx2099.bakingapp.provider.BakingDbContract;

import static com.adx2099.bakingapp.provider.BakingDbContract.BASE_CONTENT_URI;
import static com.adx2099.bakingapp.provider.BakingDbContract.PATH_BAKING;

public class GridWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewFactory(this.getApplicationContext());
    }

    private class GridRemoteViewFactory implements RemoteViewsFactory {
        private Context mContext;
        private Cursor mCursor;

        public GridRemoteViewFactory(Context applicationContext) {
            this.mContext = applicationContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            if(mCursor != null) mCursor.close();


        }

        @Override
        public void onDestroy() {
            if(mCursor != null){
                mCursor.close();
            }
        }

        @Override
        public int getCount() {
            return (mCursor != null) ? mCursor.getCount(): 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (mCursor == null || mCursor.getCount() == 0) return null;

            mCursor.moveToPosition(position);

            int foodNameIndex = mCursor.getColumnIndex(BakingDbContract.BakingEntry.COLUMN_NAME);
            int quantityIndex = mCursor.getColumnIndex(BakingDbContract.BakingEntry.COLUMN_INGREDIENTS);

            String foodName = mCursor.getString(foodNameIndex);
            String quantity = mCursor.getString(quantityIndex);

            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.ingredients_widget_item);

            remoteViews.setTextViewText(R.id.tvWidgetRecipeName, foodName);
            remoteViews.setViewVisibility(R.id.tvWidgetRecipeName, View.VISIBLE);

            remoteViews.setTextViewText(R.id.tvWidgetRecipeDetails, quantity);
            remoteViews.setViewVisibility(R.id.tvWidgetRecipeDetails, View.VISIBLE);

            Intent fillIntent = new Intent();
            remoteViews.setOnClickFillInIntent(R.id.tvWidgetRecipeName, fillIntent);
            remoteViews.setOnClickFillInIntent(R.id.tvWidgetRecipeDetails, fillIntent);

            return remoteViews;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
