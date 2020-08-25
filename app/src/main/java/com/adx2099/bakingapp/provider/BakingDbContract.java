package com.adx2099.bakingapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class BakingDbContract {
    public static final String AUTHORITY = "com.adx2099.bakingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_BAKING = "baking";


    public static final class BakingEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BAKING).build();
        public static final String TABLE_NAME = "baking";

        public static final String COLUMN_INGREDIENTS = "ingredients";
        public static final String COLUMN_STEPS = "steps";
        public static final String COLUMN_SERVINGS = "servings";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_FAV = "favorite";

        /*
           baking
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        | _id  |    ingredients     |    steps      |   servings   |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        |  1   |  Json String      |  JsonString    |    8        |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        |  2   |  Json String      |  Json String       |               |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        .
        .
        .

        * */

    }
}
