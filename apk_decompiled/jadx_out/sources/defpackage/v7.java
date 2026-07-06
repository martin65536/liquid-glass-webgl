package defpackage;

import java.util.NoSuchElementException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class v7 implements rc, bo, eq, iw0 {
    public final /* synthetic */ int e;

    public /* synthetic */ v7(int i) {
        this.e = i;
    }

    public static /* synthetic */ void d() {
        throw new ClassCastException();
    }

    public static /* synthetic */ void e(Object obj, String str) {
        throw new IllegalStateException((str + obj).toString());
    }

    public static /* synthetic */ void f(String str) {
        throw new IndexOutOfBoundsException(str);
    }

    public static /* synthetic */ void g(String str, int i) {
        throw new IllegalArgumentException(str + i);
    }

    public static /* synthetic */ void h(String str, int i, Object obj) {
        throw new IllegalArgumentException((str + i + obj).toString());
    }

    public static /* synthetic */ void i(String str, Object obj, Object obj2) {
        throw new IllegalArgumentException((str + obj + obj2).toString());
    }

    public static /* synthetic */ void j(String str, Object obj, Throwable th) {
        throw new RuntimeException(str + obj, th);
    }

    public static /* synthetic */ void k() {
        throw new RuntimeException();
    }

    public static /* synthetic */ void l(Object obj, String str) {
        throw new IllegalStateException(str + obj);
    }

    public static /* synthetic */ void m(String str) {
        throw new IllegalArgumentException(str);
    }

    public static /* synthetic */ void n() {
        throw new NoSuchElementException();
    }

    public static /* synthetic */ void o(String str) {
        throw new IllegalStateException(str);
    }

    @Override // defpackage.iw0
    public boolean a() {
        return false;
    }

    @Override // defpackage.eq
    public float b(float f) {
        float f2;
        float f3;
        switch (this.e) {
            case 12:
                if (f < 0.36363637f) {
                    return 7.5625f * f * f;
                }
                if (f < 0.72727275f) {
                    float f4 = f - 0.54545456f;
                    f2 = 7.5625f * f4 * f4;
                    f3 = 0.75f;
                } else if (f < 0.90909094f) {
                    float f5 = f - 0.8181818f;
                    f2 = 7.5625f * f5 * f5;
                    f3 = 0.9375f;
                } else {
                    float f6 = f - 0.95454544f;
                    f2 = 7.5625f * f6 * f6;
                    f3 = 0.984375f;
                }
                return f2 + f3;
            default:
                return f;
        }
    }

    @Override // defpackage.bo
    public double c(double d) {
        double d2;
        double d3;
        double d4;
        double d5;
        switch (this.e) {
            case 4:
                if (d < 0.0d) {
                    d2 = -d;
                } else {
                    d2 = d;
                }
                if (d2 >= 0.0031308049535603718d) {
                    d3 = (Math.pow(d2, 0.4166666666666667d) - 0.05213270142180095d) / 0.9478672985781991d;
                } else {
                    d3 = d2 / 0.07739938080495357d;
                }
                return Math.copySign(d3, d);
            case 5:
                if (d < 0.0d) {
                    d4 = -d;
                } else {
                    d4 = d;
                }
                if (d4 >= 0.04045d) {
                    d5 = Math.pow((0.9478672985781991d * d4) + 0.05213270142180095d, 2.4d);
                } else {
                    d5 = d4 * 0.07739938080495357d;
                }
                return Math.copySign(d5, d);
            case 6:
                float[] fArr = af.a;
                return af.b(af.c, d);
            case 7:
                float[] fArr2 = af.a;
                return af.a(af.c, d);
            case 8:
                float[] fArr3 = af.a;
                return af.d(af.d, d);
            case 9:
                float[] fArr4 = af.a;
                return af.c(af.d, d);
            default:
                return d;
        }
    }

    @Override // defpackage.rc
    public void cancel() {
    }
}
