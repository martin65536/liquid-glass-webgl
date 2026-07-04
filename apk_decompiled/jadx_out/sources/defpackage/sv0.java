package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class sv0 {
    public static final sv0 f = new sv0(0, 0.0f, 31);
    public final float a;
    public final long b;
    public final long c;
    public final float d;
    public final int e;

    public sv0(long j, float f2, int i) {
        float f3;
        if ((i & 1) != 0) {
            f3 = 24.0f;
        } else {
            f3 = 4.0f;
        }
        long floatToRawIntBits = (Float.floatToRawIntBits(0.0f) << 32) | (Float.floatToRawIntBits(f3 / 6.0f) & 4294967295L);
        j = (i & 4) != 0 ? se.b(se.b, 0.1f) : j;
        f2 = (i & 8) != 0 ? 1.0f : f2;
        this.a = f3;
        this.b = floatToRawIntBits;
        this.c = j;
        this.d = f2;
        this.e = 3;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof sv0) {
                sv0 sv0Var = (sv0) obj;
                if (eo.a(this.a, sv0Var.a) && this.b == sv0Var.b && se.c(this.c, sv0Var.c) && Float.compare(this.d, sv0Var.d) == 0 && this.e == sv0Var.e) {
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
        return "Shadow(radius=" + eo.b(this.a) + ", offset=" + fo.a(this.b) + ", color=" + se.j(this.c) + ", alpha=" + this.d + ", blendMode=" + f31.T(this.e) + ")";
    }
}
