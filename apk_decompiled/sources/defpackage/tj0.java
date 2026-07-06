package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tj0 {
    public final float a;
    public final float b;
    public final float c;
    public final float d;

    public tj0(float f, float f2, float f3, float f4) {
        boolean z;
        boolean z2;
        boolean z3;
        this.a = f;
        this.b = f2;
        this.c = f3;
        this.d = f4;
        if (f >= 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (f2 >= 0.0f) {
            z2 = true;
        } else {
            z2 = false;
        }
        boolean z4 = z & z2;
        if (f3 >= 0.0f) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (!(z4 & z3 & (f4 >= 0.0f))) {
            o00.a("Padding must be non-negative");
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof tj0) {
            tj0 tj0Var = (tj0) obj;
            if (eo.a(this.a, tj0Var.a) && eo.a(this.b, tj0Var.b) && eo.a(this.c, tj0Var.c) && eo.a(this.d, tj0Var.d)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.d) + d3.s(this.c, d3.s(this.b, Float.floatToIntBits(this.a) * 31, 31), 31);
    }

    public final String toString() {
        return "PaddingValues(start=" + ((Object) eo.b(this.a)) + ", top=" + ((Object) eo.b(this.b)) + ", end=" + ((Object) eo.b(this.c)) + ", bottom=" + ((Object) eo.b(this.d)) + ')';
    }
}
