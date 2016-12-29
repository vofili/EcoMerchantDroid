package android.livespace.com.ecobankmerchant.Utils;



    import android.content.Context;
    import android.graphics.Paint;
    import android.graphics.Typeface;
    import android.widget.TextView;

    import java.util.HashMap;
    import java.util.Map;

    public class FontUtil {

        private static final Map<String, Typeface> FONTS = new HashMap<String, Typeface>();

        public static Typeface getTypeface(Context context, String typefaceName) {
            typefaceName = typefaceName.intern();
            Typeface typeface = FONTS.get(typefaceName);
            if (typeface == null) {
                typeface = Typeface.createFromAsset(
                        context.getAssets(),
                        "fonts/" + typefaceName);
                FONTS.put(typefaceName, typeface);
            }
            return typeface;
        }

        public static void setTypeface(TextView view, String typeface) {
            if (typeface != null && !view.isInEditMode()) {
                setTypeface(view, getTypeface(view.getContext(), typeface));
            }
        }

        public static void setTypefaceBad(TextView view, String typefaceName) {
            Typeface typeface = Typeface.createFromAsset(
                    view.getContext().getAssets(),
                    "fonts/" + typefaceName);
            setTypeface(view, typeface);
        }

        private static void setTypeface(TextView view, Typeface typeface) {
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
            view.setTypeface(typeface);
        }



}
