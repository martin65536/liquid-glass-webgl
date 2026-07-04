package defpackage;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Build;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class r5 {
    public final Paint a;
    public int b = 3;
    public Shader c;
    public te d;

    public r5(Paint paint) {
        this.a = paint;
    }

    public final void a(float f) {
        this.a.setAlpha((int) Math.rint(f * 255.0f));
    }

    public final void b(int i) {
        if (this.b == i) {
            return;
        }
        this.b = i;
        int i2 = Build.VERSION.SDK_INT;
        Paint paint = this.a;
        if (i2 >= 29) {
            paint.setBlendMode(f31.O(i));
        } else {
            paint.setXfermode(new PorterDuffXfermode(f31.S(i)));
        }
    }

    public final void c(long j) {
        this.a.setColor(f31.P(j));
    }

    public final void d(te teVar) {
        ColorFilter colorFilter;
        this.d = teVar;
        if (teVar != null) {
            colorFilter = teVar.a;
        } else {
            colorFilter = null;
        }
        this.a.setColorFilter(colorFilter);
    }

    public final void e(int i) {
        Paint.Style style;
        if (i == 1) {
            style = Paint.Style.STROKE;
        } else {
            style = Paint.Style.FILL;
        }
        this.a.setStyle(style);
    }
}
