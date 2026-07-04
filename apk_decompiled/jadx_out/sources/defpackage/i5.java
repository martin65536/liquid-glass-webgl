package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class i5 {
    public final float a;
    public final float b;

    public i5(float f, float f2) {
        this.a = f;
        this.b = f2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof i5)) {
            return false;
        }
        i5 i5Var = (i5) obj;
        if (Float.compare(this.a, i5Var.a) == 0 && Float.compare(this.b, i5Var.b) == 0) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.b) + (Float.floatToIntBits(this.a) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("FlingResult(distanceCoefficient=");
        sb.append(this.a);
        sb.append(", velocityCoefficient=");
        return d3.v(sb, this.b, ')');
    }
}
