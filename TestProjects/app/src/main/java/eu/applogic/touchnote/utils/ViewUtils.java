package eu.applogic.touchnote.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by efthymioskontis on 13/1/18.
 */

public class ViewUtils {

    public static int dpToPx(Resources r, int dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
