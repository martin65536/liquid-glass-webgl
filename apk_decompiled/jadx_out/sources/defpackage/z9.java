package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class z9 {
    public final float a;

    public z9(float f) {
        this.a = f;
    }

    public final int a(int i, int i2, m40 m40Var) {
        float f = (i2 - i) / 2.0f;
        m40 m40Var2 = m40.e;
        float f2 = this.a;
        if (m40Var != m40Var2) {
            f2 *= -1.0f;
        }
        return Math.round((1.0f + f2) * f);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof z9) && Float.compare(this.a, ((z9) obj).a) == 0) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.a);
    }

    public final String toString() {
        return d3.v(new StringBuilder("Horizontal(bias="), this.a, ')');
    }
}
