package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class j5 {
    public static final float[] a;

    static {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float[] fArr = new float[101];
        a = fArr;
        float[] fArr2 = new float[101];
        float f10 = 0.0f;
        int i = 0;
        float f11 = 0.0f;
        while (true) {
            float f12 = 1.0f;
            if (i < 100) {
                float f13 = i / 100.0f;
                float f14 = 1.0f;
                while (true) {
                    f = ((f14 - f10) / 2.0f) + f10;
                    f2 = f12 - f;
                    f3 = f * 3.0f * f2;
                    f4 = f * f * f;
                    float f15 = (((f * 0.35000002f) + (f2 * 0.175f)) * f3) + f4;
                    f5 = f12;
                    if (Math.abs(f15 - f13) < 1.0E-5d) {
                        break;
                    }
                    if (f15 > f13) {
                        f14 = f;
                    } else {
                        f10 = f;
                    }
                    f12 = f5;
                }
                float f16 = 0.5f;
                fArr[i] = (((f2 * 0.5f) + f) * f3) + f4;
                float f17 = f5;
                while (true) {
                    f6 = ((f17 - f11) / 2.0f) + f11;
                    f7 = f5 - f6;
                    f8 = f6 * 3.0f * f7;
                    f9 = f6 * f6 * f6;
                    float f18 = (((f7 * f16) + f6) * f8) + f9;
                    float f19 = f17;
                    if (Math.abs(f18 - f13) >= 1.0E-5d) {
                        if (f18 > f13) {
                            f17 = f6;
                        } else {
                            f11 = f6;
                            f17 = f19;
                        }
                        f16 = 0.5f;
                    }
                }
                fArr2[i] = (((f6 * 0.35000002f) + (f7 * 0.175f)) * f8) + f9;
                i++;
            } else {
                fArr2[100] = 1.0f;
                fArr[100] = 1.0f;
                return;
            }
        }
    }

    public static i5 a(float f) {
        float f2 = 0.0f;
        float f3 = 1.0f;
        float i = n30.i(f, 0.0f, 1.0f);
        int i2 = (int) (100.0f * i);
        if (i2 < 100) {
            float f4 = i2 / 100.0f;
            int i3 = i2 + 1;
            float[] fArr = a;
            float f5 = fArr[i2];
            float f6 = (fArr[i3] - f5) / ((i3 / 100.0f) - f4);
            float f7 = ((i - f4) * f6) + f5;
            f2 = f6;
            f3 = f7;
        }
        return new i5(f3, f2);
    }
}
