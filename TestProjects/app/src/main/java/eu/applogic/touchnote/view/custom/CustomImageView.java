package eu.applogic.touchnote.view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;

import eu.applogic.touchnote.utils.DebugLogger;

/**
 * Created by efthymioskontis on 13/1/18.
 */

public class CustomImageView extends android.support.v7.widget.AppCompatImageView {

    private Bitmap mBitmap;
    private int width;

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
        setMeasuredDimension(width, width);

        DebugLogger.debug("Is drawable null? "+(getDrawable()==null));
        if(getDrawable()!=null){
            mBitmap = drawableToBitmap(getDrawable());
        }
    }

    public void changeCornerRadius(float radius){
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), mBitmap);
        roundedBitmapDrawable.setCornerRadius(radius);
        roundedBitmapDrawable.setAntiAlias(true);
        DebugLogger.debug("Corner radius is: "+roundedBitmapDrawable.getCornerRadius());
        setImageDrawable(roundedBitmapDrawable);
    }

    private Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
