package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class ju {
    public static final float[] a = {8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f};
    public static volatile xx0 b = new xx0();
    public static final Object[] c;

    static {
        Object[] objArr = new Object[0];
        c = objArr;
        synchronized (objArr) {
            b.c(115, new ku(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{9.2f, 11.5f, 13.8f, 16.4f, 19.8f, 21.8f, 25.2f, 30.0f, 100.0f}));
            b.c(130, new ku(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{10.4f, 13.0f, 15.6f, 18.8f, 21.6f, 23.6f, 26.4f, 30.0f, 100.0f}));
            b.c(150, new ku(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{12.0f, 15.0f, 18.0f, 22.0f, 24.0f, 26.0f, 28.0f, 30.0f, 100.0f}));
            b.c(180, new ku(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{14.4f, 18.0f, 21.6f, 24.4f, 27.6f, 30.8f, 32.8f, 34.8f, 100.0f}));
            b.c(200, new ku(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{16.0f, 20.0f, 24.0f, 26.0f, 30.0f, 34.0f, 36.0f, 38.0f, 100.0f}));
        }
        if ((b.e[0] / 100.0f) - 0.01f > 1.03f) {
            return;
        }
        s00.b("You should only apply non-linear scaling to font scales > 1");
    }

    public static iu a(float f) {
        float f2;
        iu iuVar;
        float f3;
        float[] fArr = a;
        if (f >= 1.03f) {
            int i = (int) (f * 100.0f);
            iu iuVar2 = (iu) b.b(i);
            if (iuVar2 != null) {
                return iuVar2;
            }
            xx0 xx0Var = b;
            int m = o4.m(xx0Var.e, xx0Var.g, i);
            if (m >= 0) {
                return (iu) b.d(m);
            }
            int i2 = -(m + 1);
            int i3 = i2 - 1;
            if (i2 >= b.g) {
                ku kuVar = new ku(new float[]{1.0f}, new float[]{f});
                b(f, kuVar);
                return kuVar;
            }
            if (i3 < 0) {
                iuVar = new ku(fArr, fArr);
                f2 = 1.0f;
            } else {
                f2 = b.e[i3] / 100.0f;
                iuVar = (iu) b.d(i3);
            }
            float f4 = b.e[i2] / 100.0f;
            if (f2 == f4) {
                f3 = 0.0f;
            } else {
                f3 = (f - f2) / (f4 - f2);
            }
            float max = (Math.max(0.0f, Math.min(1.0f, f3)) * 1.0f) + 0.0f;
            iu iuVar3 = (iu) b.d(i2);
            float[] fArr2 = new float[9];
            for (int i4 = 0; i4 < 9; i4++) {
                float f5 = fArr[i4];
                float b2 = iuVar.b(f5);
                fArr2[i4] = ((iuVar3.b(f5) - b2) * max) + b2;
            }
            ku kuVar2 = new ku(fArr, fArr2);
            b(f, kuVar2);
            return kuVar2;
        }
        return null;
    }

    public static void b(float f, ku kuVar) {
        synchronized (c) {
            xx0 clone = b.clone();
            clone.c((int) (f * 100.0f), kuVar);
            b = clone;
        }
    }
}
