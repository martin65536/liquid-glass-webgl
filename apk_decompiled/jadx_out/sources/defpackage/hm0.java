package defpackage;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hm0 extends ReplacementSpan {
    public Paint.FontMetricsInt e;
    public int f;
    public int g;
    public boolean h;

    public final Paint.FontMetricsInt a() {
        Paint.FontMetricsInt fontMetricsInt = this.e;
        if (fontMetricsInt != null) {
            return fontMetricsInt;
        }
        o20.G("fontMetrics");
        throw null;
    }

    public final int b() {
        if (!this.h) {
            r00.b("PlaceholderSpan is not laid out yet.");
        }
        return this.g;
    }

    public final int c() {
        if (!this.h) {
            r00.b("PlaceholderSpan is not laid out yet.");
        }
        return this.f;
    }

    @Override // android.text.style.ReplacementSpan
    public final int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        this.h = true;
        paint.getTextSize();
        this.e = paint.getFontMetricsInt();
        if (a().descent <= a().ascent) {
            r00.a("Invalid fontMetrics: line height can not be negative.");
        }
        this.f = (int) Math.ceil(0.0d);
        this.g = (int) Math.ceil(0.0d);
        if (fontMetricsInt != null) {
            fontMetricsInt.ascent = a().ascent;
            fontMetricsInt.descent = a().descent;
            fontMetricsInt.leading = a().leading;
            if (fontMetricsInt.ascent > (-b())) {
                fontMetricsInt.ascent = -b();
            }
            fontMetricsInt.top = Math.min(a().top, fontMetricsInt.ascent);
            fontMetricsInt.bottom = Math.max(a().bottom, fontMetricsInt.descent);
        }
        return c();
    }

    @Override // android.text.style.ReplacementSpan
    public final void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
    }
}
