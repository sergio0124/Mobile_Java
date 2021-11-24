package com.example.paintmainapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Face(this));
    }

}

class Face extends View {
    Paint paint;

    public Face(Context context) {
        super(context);
        paint = new Paint();
    }

    private boolean IsInTheBeard(int center_x, int center_y, int radius, int y, int x, int oval_radius) {

        if (Math.pow((x - center_x), 2) / Math.pow(radius, 2)
                + Math.pow((y - center_y), 2) / Math.pow(oval_radius, 2) >= 1) {
            if (Math.pow((x - center_x), 2) + Math.pow((y - center_y), 2) <= Math.pow(radius, 2)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int radius = 300;
        int center_x = 360;
        int center_y = 560;
        int eye_radius = 80;
        int pupil_radius = eye_radius / 2;
        int mouth_radius = 80;

        canvas.drawRGB(0, 0, 0);

        //Head
        paint.setARGB(255,240,248,255);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(center_x, center_y, radius, paint);

        //Hat
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        RectF oval = new RectF();
        oval.set(center_x - radius, center_y - radius - 20, center_x + radius,
                center_y + radius - 160);
        canvas.drawArc(oval, 180, 180, true, paint);

        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(50);
        oval.set(center_x - radius,
                center_y - radius + 200,
                center_x + radius,
                center_y + radius - 360);
        canvas.drawArc(oval, 180, 180, true, paint);

        //Left eye
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        oval.set(center_x - 60 - eye_radius * 2,
                center_y - eye_radius,
                center_x - 60,
                center_y + eye_radius);
        canvas.drawArc(oval, 0, 180, true, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        canvas.drawArc(oval, 0, 180, true, paint);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        oval.set(center_x - 60 - eye_radius - pupil_radius,
                center_y - eye_radius + pupil_radius,
                center_x - 60 - pupil_radius,
                center_y + eye_radius - pupil_radius);
        canvas.drawArc(oval, 0, 180, true, paint);

        //Right eye
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        oval.set(center_x + 60,
                center_y - eye_radius,
                center_x + 60 + eye_radius * 2,
                center_y + eye_radius);
        canvas.drawArc(oval, 0, 180, true, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        canvas.drawArc(oval, 0, 180, true, paint);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        oval.set(center_x + 60 + pupil_radius,
                center_y - eye_radius + pupil_radius,
                center_x + 60 + eye_radius + pupil_radius,
                center_y + eye_radius - pupil_radius);
        canvas.drawArc(oval, 0, 180, true, paint);

        //Left pouches
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        for (int i = 1; i < 3; i++) {
            oval.set(center_x - 60 - eye_radius * 2,
                    center_y - eye_radius,
                    center_x - 60,
                    center_y + eye_radius + 15 * i);
            canvas.drawArc(oval, 25, 130, false, paint);
        }

        //Right pouches
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        for (int i = 1; i < 3; i++) {
            oval.set(center_x + 60,
                    center_y - eye_radius,
                    center_x + 60 + eye_radius * 2,
                    center_y + eye_radius + 15 * i);
            canvas.drawArc(oval, 25, 130, false, paint);
        }

        //Mouth
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        oval.set(center_x - mouth_radius,
                center_y + radius - mouth_radius,
                center_x + mouth_radius,
                center_y + radius + mouth_radius);
        canvas.drawArc(oval, 240, 60, false, paint);

        //Drawing beard
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        int step = 10;
        Random random = new Random();
        for (int i = center_y; i < center_y + radius; i += step) {
            for (int j = center_x - radius; j < center_x + radius; j += step) {
                int y = i + random.nextInt(step);
                int x = j + random.nextInt(step);
                if (IsInTheBeard(center_x, center_y,
                        radius, y, x, radius-120)) {
                    canvas.drawLine(x, y, x + 1, y + 1, paint);
                }
            }
        }
    }
}