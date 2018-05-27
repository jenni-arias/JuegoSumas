package arias.jenifer.juegosumas;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/*
 * Created by j.arias.gallego on 10/05/2018.
*/

public class MakeToast {

    Context context;
    String message;
    int type;

    public MakeToast(String message, int type, Context context) {
        this.message = message;
        this.type = type;
        this.context = context;
    }

    public static void showToast(Context context, String message, int type) {
        Toasty.Config.getInstance().setTextSize(20).apply();

        switch (type) {
            case 1: //Success
                Toasty.success(context, message,Toast.LENGTH_SHORT, true).show();
                break;
            case 2: //Error
                Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
                break;
            case 3: //Info
                Toasty.info(context, message, Toast.LENGTH_LONG, true).show();
                break;
            case 4: //Warning
                Toasty.warning(context, message, Toast.LENGTH_SHORT, true).show();
                break;
            case 5: //Normal without icon
                Toasty.normal(context, message).show();
                break;
            case 6: //Normal with icon
                //Toasty.normal(context, "Normal toast w/ icon", icon).show();
                break;
            case 7:
                //Toasty.custom(context, "I'm a custom Toast", yourIconDrawable, tintColor, duration, withIcon, shouldTint).show();
                break;
        }
    }
}
