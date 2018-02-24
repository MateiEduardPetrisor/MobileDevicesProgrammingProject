package petrisor.eduard.matei.mobiledevicesprogrammingproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BarChart extends View {

    public List<Review> lstRev = new ArrayList<Review>();

    public BarChart(Context context) {
        super(context);
    }

    public BarChart(Context context, ArrayList<Review> list) {
        super(context);
        this.lstRev = list;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
            int ratings[] = {1, 2, 3, 4, 5};
            int countedRevs[] = countRev(lstRev);
            int totalReviews = countedRevs[0] + countedRevs[1] + countedRevs[2] + countedRevs[3] + countedRevs[4];
            Log.wtf("Total revs; ", totalReviews + "");
            int width = canvas.getWidth() / countedRevs.length;
            int max = 0;

            for (int x : countedRevs) {
                max = (x > max) ? x : max;
            }

            for (int i = 0; i < countedRevs.length; i++) {
                int height = canvas.getHeight() * countedRevs[i] / max;
                Rect rectangle = new Rect(i * width, canvas.getHeight() - height, (i + 1) * width, canvas.getHeight());
                Paint p = new Paint();
                Random r = new Random();
                p.setColor(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
                p.setStyle(Paint.Style.FILL);
                canvas.drawRect(rectangle, p);
                p.setColor(Color.WHITE);
                p.setStyle(Paint.Style.STROKE);
                p.setStrokeWidth(10);
                canvas.drawRect(rectangle, p);
                p.setStyle(Paint.Style.FILL);
                p.setTextSize(50);
                canvas.drawText(ratings[i] + " Star", i * width + width / 4, canvas.getHeight() - height + 50, p);
                canvas.drawText(countedRevs[i] + "", i * width + width / 2, canvas.getHeight() - height + 150, p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int[] countRev(List<Review> list) throws Exception {
        int oneStar = 0;
        int twoStar = 0;
        int threeStar = 0;
        int fourStar = 0;
        int fiveStar = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRating() == 1) {
                oneStar = oneStar + 1;
            } else if (list.get(i).getRating() == 2) {
                twoStar = twoStar + 1;
            } else if (list.get(i).getRating() == 3) {
                threeStar = threeStar + 1;
            } else if (list.get(i).getRating() == 4) {
                fourStar = fourStar + 1;
            } else {
                fiveStar = fiveStar + 1;
            }
        }
        int countedRevs[] = {oneStar, twoStar, threeStar, fourStar, fiveStar};
        return countedRevs;
    }
}