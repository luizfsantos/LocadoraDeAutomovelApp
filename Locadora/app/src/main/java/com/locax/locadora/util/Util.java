package com.locax.locadora.util;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Field;

import com.locax.locadora.R;

/**
 * Created by Luiz on 13/10/2015.
 */
public class Util {
    public static Drawable getDrawable(Activity context, String drawableName){
        //troca hifen por underline, pois hifen e invalido para nome de recursos
        drawableName = drawableName.replace('-', '_');
        drawableName = drawableName.replace('â', 'a');
        //procurar a imagem por meio de reflection
        Class<?> c = R.drawable.class;
        try {
            Field idField = c.getDeclaredField(drawableName);
            int id = idField.getInt(idField);
            return context.getResources().getDrawable(id);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return context.getResources().getDrawable(R.drawable.padrao);
    }
}
