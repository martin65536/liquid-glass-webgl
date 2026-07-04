package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class y00 {
    public final float a;
    public final long b;
    public final long c;
    public final float d;
    public final int e;

    static {
        new y00(0.0f, 0.0f, 31);
    }

    public y00(float f, float f2, int i) {
        f = (i & 1) != 0 ? 24.0f : f;
        long floatToRawIntBits = (Float.floatToRawIntBits(0.0f) << 32) | (Float.floatToRawIntBits(f) & 4294967295L);
        long b = se.b(se.b, 0.15f);
        f2 = (i & 8) != 0 ? 1.0f : f2;
        this.a = f;
        this.b = floatToRawIntBits;
        this.c = b;
        this.d = f2;
        this.e = 3;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof y00) {
                y00 y00Var = (y00) obj;
                if (eo.a(this.a, y00Var.a) && this.b == y00Var.b && se.c(this.c, y00Var.c) && Float.compare(this.d, y00Var.d) == 0 && this.e == y00Var.e) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int floatToIntBits = Float.floatToIntBits(this.a) * 31;
        long j = this.b;
        return d3.s(this.d, (se.i(this.c) + ((((int) (j ^ (j >>> 32))) + floatToIntBits) * 31)) * 31, 31) + this.e;
    }

    public final String toString() {
        return "InnerShadow(radius=" + eo.b(this.a) + ", offset=" + fo.a(this.b) + ", color=" + se.j(this.c) + ", alpha=" + this.d + ", blendMode=" + f31.T(this.e) + ")";
    }
}
