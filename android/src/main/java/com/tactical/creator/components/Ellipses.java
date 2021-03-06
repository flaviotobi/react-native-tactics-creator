package com.tactical.creator.components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.react.uimanager.ThemedReactContext;
import com.tactical.creator.utis.CustomAnimation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * Created by macmini on 14/11/2017.
 */

public class Ellipses {
    private com.tactical.creator.utis.CustomAnimation CustomAnimation = new CustomAnimation();
    int[] auxColor;

    private Paint paint = new Paint();
    private Paint paintWhite = new Paint();

    public void create(ThemedReactContext context, RelativeLayout base_svg, JSONObject player, Integer screenHeight, Integer screenWidth) {
        try {

            float scale = 1;
            scale = BigDecimal.valueOf(player.getDouble("scale")).floatValue();


            float   testeWidth =((player.getInt("width") * screenWidth) / 906);
            float   testeHeifht = ((player.getInt("height") * screenHeight) / 577);


            int realWidth = (int) (testeWidth * scale);
            int realHeight = (int) (testeHeifht * scale);

            Bitmap b = Bitmap.createBitmap((int) realWidth, (int) realHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(b);

//            //This code is for testing img background width and height
//            Paint paint = new Paint();
//            paint.setStyle(Paint.Style.FILL);
//            paint.setColor(Color.BLUE);
//            canvas.drawPaint(paint);
//            //END

            canvas.save();

            if (player.getBoolean("isFill")) {
                auxColor = CustomAnimation.getColorIntAsColor(player.getInt("color"), true);
                paint.setARGB(auxColor[0], auxColor[1], auxColor[2], auxColor[3]);
                paint.setAntiAlias(true);

                paintWhite.setColor(Color.WHITE);
                paintWhite.setAntiAlias(true);
                paintWhite.setStrokeWidth(player.getInt("strokeWeight"));
                paintWhite.setStyle(Paint.Style.STROKE);
            } else {
                paint.setARGB(0, 0, 0, 0);
                paint.setAntiAlias(true);

                auxColor = CustomAnimation.getColorIntAsColor(player.getInt("color"), true);
                paintWhite.setARGB(auxColor[0], auxColor[1], auxColor[2], auxColor[3]);
                paintWhite.setAntiAlias(true);
                paintWhite.setStrokeWidth(player.getInt("strokeWeight"));
                paintWhite.setStyle(Paint.Style.STROKE);

            }


            RectF rect = new RectF(0f, 0f, realWidth, realHeight);

            canvas.drawOval(rect, paint);
            canvas.drawOval(rect, paintWhite);

            canvas.restore();

            ImageView myImage = new ImageView(context);
            myImage.setImageBitmap(b);
            myImage.setPivotX(0.0f);
            myImage.setPivotY(0.0f);

            myImage.setX(((player.getInt("x") * screenWidth) / 906));
            myImage.setY(((player.getInt("y") * screenHeight) / 577));

            myImage.setRotation((CustomAnimation.doMathForRotation(myImage.getRotation(), (float) player.getDouble("rotation")))[1]);
            base_svg.addView(myImage);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
