package com.caballero.tictactoe.util;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;

import com.caballero.tictactoe.R;

public class ThemeUtils {

    public static int getAccentColor(Context context) {
        return ResourcesCompat.getColor(context.getResources(), R.color.accentColor, null);
    }

    public static int getPrimaryColor(Context context) {
        return ResourcesCompat.getColor(context.getResources(), R.color.primaryColor, null);
    }

    public static int getPrimaryDarkColor(Context context) {
        return ResourcesCompat.getColor(context.getResources(), R.color.primaryDarkColor, null);
    }

    public static int getBackgroundColor(Context context) {
        return ResourcesCompat.getColor(context.getResources(), R.color.white_opaque, null);
    }
}
