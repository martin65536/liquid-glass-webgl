package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zx0 {
    public float a;
    public double b;
    public float c;

    public final long a(float f, float f2, long j) {
        double sin;
        double cos;
        double exp;
        double exp2;
        float f3 = f - this.a;
        double d = j / 1000.0d;
        float f4 = this.c;
        double d2 = f4 * f4;
        double d3 = this.b;
        double d4 = (-f4) * d3;
        if (f4 > 1.0f) {
            double sqrt = Math.sqrt(d2 - 1.0d) * d3;
            double d5 = d4 + sqrt;
            double d6 = d4 - sqrt;
            double d7 = f3;
            double d8 = ((d6 * d7) - f2) / (d6 - d5);
            double d9 = d7 - d8;
            double d10 = d6 * d;
            double d11 = d * d5;
            sin = (Math.exp(d11) * d8) + (Math.exp(d10) * d9);
            exp = Math.exp(d10) * d9 * d6;
            exp2 = Math.exp(d11) * d8 * d5;
        } else if (f4 == 1.0f) {
            double d12 = f3;
            double d13 = (d3 * d12) + f2;
            double d14 = (-d3) * d;
            double d15 = (d * d13) + d12;
            sin = Math.exp(d14) * d15;
            exp = Math.exp(d14) * d15 * (-this.b);
            exp2 = Math.exp(d14) * d13;
        } else {
            double sqrt2 = Math.sqrt(1.0d - d2) * d3;
            double d16 = f3;
            double d17 = (((-d4) * d16) + f2) * (1.0d / sqrt2);
            double d18 = sqrt2 * d;
            double d19 = d * d4;
            sin = ((Math.sin(d18) * d17) + (Math.cos(d18) * d16)) * Math.exp(d19);
            cos = (((Math.cos(d18) * sqrt2 * d17) + (Math.sin(d18) * (-sqrt2) * d16)) * Math.exp(d19)) + (d4 * sin);
            return (Float.floatToRawIntBits((float) cos) & 4294967295L) | (Float.floatToRawIntBits((float) (sin + this.a)) << 32);
        }
        cos = exp2 + exp;
        return (Float.floatToRawIntBits((float) cos) & 4294967295L) | (Float.floatToRawIntBits((float) (sin + this.a)) << 32);
    }
}
