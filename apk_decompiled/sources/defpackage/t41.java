package defpackage;

import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class t41 {
    public static final /* synthetic */ int a = 0;

    static {
        char c;
        dt0[] dt0VarArr;
        char c2;
        boolean z;
        int i = 1;
        float[][] fArr = {new float[2], new float[2]};
        int i2 = new int[2][0];
        char c3 = 3;
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 != 2 && i2 != 3) {
                    if (i2 != 4) {
                        if (i2 == 5) {
                            c = 5;
                        }
                    } else {
                        c = 4;
                    }
                } else {
                    c = 2;
                }
            }
            c = 1;
        } else {
            c = 3;
        }
        float[] fArr2 = fArr[0];
        float[] fArr3 = fArr[1];
        int length = (fArr2.length % 2) + (fArr2.length / 2);
        dt0[] dt0VarArr2 = new dt0[length];
        int i3 = 0;
        while (i3 < length) {
            int i4 = i3 * 2;
            float f = fArr2[i4];
            int i5 = i4 + 1;
            float f2 = fArr2[i5];
            float f3 = fArr3[i4];
            float f4 = fArr3[i5];
            dt0 dt0Var = new dt0(13);
            float f5 = f3 - f;
            float f6 = f4 - f2;
            int i6 = i;
            float[] fArr4 = new float[101];
            if (c == c3 || Math.abs(f5) < 0.001f || Math.abs(f6) < 0.001f) {
                z = false;
                dt0VarArr = dt0VarArr2;
                c2 = c;
                Math.hypot(f6, f5);
            } else {
                float f7 = f2 - f4;
                float[] fArr5 = n20.a;
                float f8 = f7;
                int i7 = i6;
                float f9 = 0.0f;
                float f10 = 0.0f;
                while (true) {
                    double d = (float) (((i7 * 90.0d) / 90.0d) * 0.017453292519943295d);
                    float f11 = f7;
                    float sin = ((float) Math.sin(d)) * f5;
                    float cos = f11 * ((float) Math.cos(d));
                    float f12 = cos - f8;
                    dt0VarArr = dt0VarArr2;
                    c2 = c;
                    f9 += (float) Math.hypot(sin - f10, f12);
                    fArr5[i7] = f9;
                    if (i7 == 90) {
                        break;
                    }
                    i7++;
                    f10 = sin;
                    c = c2;
                    dt0VarArr2 = dt0VarArr;
                    f8 = cos;
                    f7 = f11;
                }
                int i8 = i6;
                while (true) {
                    fArr5[i8] = fArr5[i8] / f9;
                    if (i8 == 90) {
                        break;
                    } else {
                        i8++;
                    }
                }
                for (int i9 = 0; i9 < 101; i9++) {
                    float f13 = i9 / 100.0f;
                    int binarySearch = Arrays.binarySearch(fArr5, 0, 91, f13);
                    if (binarySearch >= 0) {
                        fArr4[i9] = binarySearch / 90.0f;
                    } else if (binarySearch == -1) {
                        fArr4[i9] = 0.0f;
                    } else {
                        int i10 = -binarySearch;
                        int i11 = i10 - 2;
                        float f14 = i11;
                        float f15 = fArr5[i11];
                        fArr4[i9] = (((f13 - f15) / (fArr5[i10 - 1] - f15)) + f14) / 90.0f;
                    }
                }
                z = false;
            }
            dt0VarArr[i3] = dt0Var;
            i3++;
            i = i6;
            c = c2;
            dt0VarArr2 = dt0VarArr;
            c3 = 3;
        }
    }
}
