package defpackage;

import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class f11 {
    public final TextPaint a;
    public final TextUtils.TruncateAt b;
    public final boolean c;
    public final boolean d;
    public final Layout e;
    public final int f;
    public final int g;
    public final int h;
    public final float i;
    public final float j;
    public final boolean k;
    public final Paint.FontMetricsInt l;
    public final int m;
    public final y80[] n;
    public final Rect o = new Rect();
    public a9 p;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x017b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0231  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x028d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x031f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public f11(java.lang.CharSequence r22, float r23, android.text.TextPaint r24, int r25, android.text.TextUtils.TruncateAt r26, int r27, boolean r28, int r29, int r30, int r31, int r32, int r33, int r34, defpackage.p40 r35) {
        /*
            Method dump skipped, instructions count: 849
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.f11.<init>(java.lang.CharSequence, float, android.text.TextPaint, int, android.text.TextUtils$TruncateAt, int, boolean, int, int, int, int, int, int, p40):void");
    }

    public final int a() {
        int height;
        boolean z = this.d;
        Layout layout = this.e;
        if (z) {
            height = layout.getLineBottom(this.f - 1);
        } else {
            height = layout.getHeight();
        }
        return height + this.g + this.h + this.m;
    }

    public final a9 b() {
        a9 a9Var = this.p;
        if (a9Var == null) {
            a9 a9Var2 = new a9(this.e);
            this.p = a9Var2;
            return a9Var2;
        }
        return a9Var;
    }

    public final float c(int i) {
        float lineBaseline;
        Paint.FontMetricsInt fontMetricsInt;
        float f = this.g;
        if (i == this.f - 1 && (fontMetricsInt = this.l) != null) {
            lineBaseline = f(i) - fontMetricsInt.ascent;
        } else {
            lineBaseline = this.e.getLineBaseline(i);
        }
        return f + lineBaseline;
    }

    public final float d(int i) {
        int i2;
        Paint.FontMetricsInt fontMetricsInt;
        int i3 = this.f;
        int i4 = i3 - 1;
        Layout layout = this.e;
        if (i == i4 && (fontMetricsInt = this.l) != null) {
            return layout.getLineBottom(i - 1) + fontMetricsInt.bottom;
        }
        float lineBottom = this.g + layout.getLineBottom(i);
        if (i == i3 - 1) {
            i2 = this.h;
        } else {
            i2 = 0;
        }
        return lineBottom + i2;
    }

    public final int e(int i) {
        ThreadLocal threadLocal = i11.a;
        Layout layout = this.e;
        if (layout.getEllipsisCount(i) > 0 && this.b == TextUtils.TruncateAt.END) {
            return layout.getText().length();
        }
        return layout.getLineEnd(i);
    }

    public final float f(int i) {
        int i2;
        float lineTop = this.e.getLineTop(i);
        if (i == 0) {
            i2 = 0;
        } else {
            i2 = this.g;
        }
        return lineTop + i2;
    }

    public final float g(int i, boolean z) {
        float f;
        float e = b().e(i, true, z);
        if (this.e.getLineForOffset(i) == this.f - 1) {
            f = this.i + this.j;
        } else {
            f = 0.0f;
        }
        return f + e;
    }

    public final float h(int i, boolean z) {
        float f;
        float e = b().e(i, false, z);
        if (this.e.getLineForOffset(i) == this.f - 1) {
            f = this.i + this.j;
        } else {
            f = 0.0f;
        }
        return f + e;
    }
}
