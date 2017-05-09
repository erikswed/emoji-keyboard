package com.gotcreations.emojilibrary;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by Andersson G. Acosta on 5/05/17.
 */

public class DimensionUtils {

    public static int dpToPixel(Context context, int dp) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
