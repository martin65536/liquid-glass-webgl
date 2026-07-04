package defpackage;

import android.graphics.Paint;
import android.text.style.LineHeightSpan;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class y80 implements LineHeightSpan {
    public final float e;
    public final int f;
    public final boolean g;
    public final boolean h;
    public final float i;
    public final int j;
    public int k = Integer.MIN_VALUE;
    public int l = Integer.MIN_VALUE;
    public int m = Integer.MIN_VALUE;
    public int n = Integer.MIN_VALUE;
    public int o;
    public int p;

    public y80(float f, int i, boolean z, boolean z2, float f2, int i2) {
        this.e = f;
        this.f = i;
        this.g = z;
        this.h = z2;
        this.i = f2;
        this.j = i2;
        if ((0.0f <= f2 && f2 <= 1.0f) || f2 == -1.0f) {
            return;
        }
        r00.b("topRatio should be in [0..1] range or -1");
    }

    @Override // android.text.style.LineHeightSpan
    public final void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt) {
        boolean z;
        boolean z2;
        int i5;
        int i6;
        double ceil;
        int min;
        int max;
        int i7 = fontMetricsInt.descent;
        int i8 = fontMetricsInt.ascent;
        if (i7 - i8 > 0) {
            if (i == 0) {
                z = true;
            } else {
                z = false;
            }
            if (i2 == this.f) {
                z2 = true;
            } else {
                z2 = false;
            }
            int i9 = this.j;
            boolean z3 = this.h;
            boolean z4 = this.g;
            if (z && z2 && z4 && z3 && i9 != 2) {
                return;
            }
            if (this.k == Integer.MIN_VALUE) {
                int i10 = i7 - i8;
                int ceil2 = (int) Math.ceil(this.e);
                int i11 = ceil2 - i10;
                if (i9 == 1 && i11 <= 0) {
                    int i12 = fontMetricsInt.ascent;
                    this.l = i12;
                    int i13 = fontMetricsInt.descent;
                    this.m = i13;
                    this.k = i12;
                    this.n = i13;
                    this.o = 0;
                    this.p = 0;
                } else {
                    float f = this.i;
                    if (f == -1.0f) {
                        f = Math.abs(fontMetricsInt.ascent) / (fontMetricsInt.descent - fontMetricsInt.ascent);
                    }
                    if (i11 <= 0) {
                        ceil = Math.ceil(i11 * f);
                    } else {
                        ceil = Math.ceil((1.0f - f) * i11);
                    }
                    int i14 = (int) ceil;
                    int i15 = fontMetricsInt.descent;
                    int i16 = i14 + i15;
                    this.m = i16;
                    int i17 = i16 - ceil2;
                    this.l = i17;
                    if (i9 == 0 || i11 >= 0) {
                        if (z4) {
                            i17 = fontMetricsInt.ascent;
                        }
                        this.k = i17;
                        if (z3) {
                            i16 = i15;
                        }
                        this.n = i16;
                        this.o = fontMetricsInt.ascent - i17;
                        this.p = i16 - i15;
                    } else if (i9 == 2) {
                        int i18 = fontMetricsInt.ascent;
                        if (z4) {
                            min = Math.max(i18, i17);
                        } else {
                            min = Math.min(i18, i17);
                        }
                        this.k = min;
                        int i19 = fontMetricsInt.descent;
                        int i20 = this.m;
                        if (z3) {
                            max = Math.min(i19, i20);
                        } else {
                            max = Math.max(i19, i20);
                        }
                        this.n = max;
                        this.o = 0;
                        this.p = 0;
                    }
                }
            }
            if (z) {
                i5 = this.k;
            } else {
                i5 = this.l;
            }
            fontMetricsInt.ascent = i5;
            if (z2) {
                i6 = this.n;
            } else {
                i6 = this.m;
            }
            fontMetricsInt.descent = i6;
        }
    }
}
