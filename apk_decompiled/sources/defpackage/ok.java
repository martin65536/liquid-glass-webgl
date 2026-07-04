package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ok implements eq {
    public final float e;
    public final float f;
    public final float g;
    public final float h;
    public final float i;
    public final float j;

    public ok(float f, float f2, float f3, float f4) {
        boolean z;
        int i;
        this.e = f;
        this.f = f2;
        this.g = f3;
        this.h = f4;
        if (!Float.isNaN(f) && !Float.isNaN(f2) && !Float.isNaN(f3) && !Float.isNaN(f4)) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            en0.a("Parameters to CubicBezierEasing cannot be NaN. Actual parameters are: " + f + ", " + f2 + ", " + f3 + ", " + f4 + '.');
        }
        float[] fArr = new float[5];
        float f5 = (f2 - 0.0f) * 3.0f;
        float f6 = (f4 - f2) * 3.0f;
        float f7 = (1.0f - f4) * 3.0f;
        double d = f5;
        double d2 = f6;
        double d3 = f7;
        double d4 = d2 * 2.0d;
        double d5 = (d - d4) + d3;
        if (d5 == 0.0d) {
            if (d2 == d3) {
                i = 0;
            } else {
                i = jc0.M((float) ((d4 - d3) / (d4 - (d3 * 2.0d))), fArr, 0);
            }
        } else {
            double d6 = -Math.sqrt((d2 * d2) - (d3 * d));
            double d7 = (-d) + d2;
            int M = jc0.M((float) ((-(d6 + d7)) / d5), fArr, 0);
            int M2 = jc0.M((float) ((d6 - d7) / d5), fArr, M) + M;
            if (M2 > 1) {
                float f8 = fArr[0];
                float f9 = fArr[1];
                if (f8 > f9) {
                    fArr[0] = f9;
                    fArr[1] = f8;
                } else if (f8 == f9) {
                    i = M2 - 1;
                }
            }
            i = M2;
        }
        float f10 = (f6 - f5) * 2.0f;
        int M3 = jc0.M((-f10) / (((f7 - f6) * 2.0f) - f10), fArr, i) + i;
        float min = Math.min(0.0f, 1.0f);
        float max = Math.max(0.0f, 1.0f);
        for (int i2 = 0; i2 < M3; i2++) {
            float f11 = fArr[i2];
            float f12 = (((((((((f2 - f4) * 3.0f) + 1.0f) - 0.0f) * f11) + (((f4 - (f2 * 2.0f)) + 0.0f) * 3.0f)) * f11) + f5) * f11) + 0.0f;
            min = Math.min(min, f12);
            max = Math.max(max, f12);
        }
        long floatToRawIntBits = (Float.floatToRawIntBits(min) << 32) | (Float.floatToRawIntBits(max) & 4294967295L);
        this.i = Float.intBitsToFloat((int) (floatToRawIntBits >> 32));
        this.j = Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L));
    }

    /* JADX WARN: Code restructure failed: missing block: B:117:0x0206, code lost:
    
        if (java.lang.Math.abs(r3 - r2) > 1.05E-6f) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0236, code lost:
    
        if (java.lang.Math.abs(r3 - r2) > 1.05E-6f) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x008e, code lost:
    
        if (java.lang.Math.abs(r3 - r2) > 1.05E-6f) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0092, code lost:
    
        r15 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00e5, code lost:
    
        if (java.lang.Math.abs(r3 - r2) > 1.05E-6f) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01bb, code lost:
    
        if (java.lang.Math.abs(r3 - r2) > 1.05E-6f) goto L129;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0242  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0261  */
    @Override // defpackage.eq
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final float b(float r27) {
        /*
            Method dump skipped, instructions count: 658
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ok.b(float):float");
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ok) {
            ok okVar = (ok) obj;
            if (this.e == okVar.e && this.f == okVar.f && this.g == okVar.g && this.h == okVar.h) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.h) + d3.s(this.g, d3.s(this.f, Float.floatToIntBits(this.e) * 31, 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CubicBezierEasing(a=");
        sb.append(this.e);
        sb.append(", b=");
        sb.append(this.f);
        sb.append(", c=");
        sb.append(this.g);
        sb.append(", d=");
        return d3.v(sb, this.h, ')');
    }
}
