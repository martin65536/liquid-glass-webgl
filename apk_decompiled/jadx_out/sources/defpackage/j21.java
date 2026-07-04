package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class j21 {
    public dj0 a;
    public long b = 0;

    public j21(dj0 dj0Var, int i) {
        this.a = dj0Var;
    }

    public final long a(long j, float f, boolean z) {
        long g;
        float abs;
        long j2;
        long j3 = this.b;
        if (z) {
            g = ch0.g(j3, j);
            this.b = g;
        } else {
            g = ch0.g(j3, j);
        }
        if (this.a == null) {
            abs = ch0.d(g);
        } else {
            abs = Math.abs(b(g));
        }
        if (abs >= f) {
            dj0 dj0Var = this.a;
            long j4 = this.b;
            if (dj0Var == null) {
                return ch0.f(this.b, ch0.h(ch0.b(j4, ch0.d(j4)), f));
            }
            float b = b(j4) - (Math.signum(b(this.b)) * f);
            long j5 = this.b;
            dj0 dj0Var2 = this.a;
            dj0 dj0Var3 = dj0.f;
            if (dj0Var2 == dj0Var3) {
                j2 = j5 & 4294967295L;
            } else {
                j2 = j5 >> 32;
            }
            float intBitsToFloat = Float.intBitsToFloat((int) j2);
            if (this.a == dj0Var3) {
                return (Float.floatToRawIntBits(b) << 32) | (Float.floatToRawIntBits(intBitsToFloat) & 4294967295L);
            }
            return (Float.floatToRawIntBits(b) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
        }
        return 9205357640488583168L;
    }

    public final float b(long j) {
        int i;
        if (this.a == dj0.f) {
            i = (int) (j >> 32);
        } else {
            i = (int) (j & 4294967295L);
        }
        return Float.intBitsToFloat(i);
    }
}
