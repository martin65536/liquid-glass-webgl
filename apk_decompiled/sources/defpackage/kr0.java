package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kr0 {
    public final float a;
    public final float b;
    public final float c;
    public final float d;

    public kr0(float f, float f2, float f3, float f4) {
        this.a = f;
        this.b = f2;
        this.c = f3;
        this.d = f4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof kr0)) {
            return false;
        }
        kr0 kr0Var = (kr0) obj;
        if (Float.compare(this.a, kr0Var.a) == 0 && Float.compare(this.b, kr0Var.b) == 0 && Float.compare(this.c, kr0Var.c) == 0 && Float.compare(this.d, kr0Var.d) == 0) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.d) + d3.s(this.c, d3.s(this.b, Float.floatToIntBits(this.a) * 31, 31), 31);
    }

    public final String toString() {
        return "Corners(topLeft=" + this.a + ", topRight=" + this.b + ", bottomRight=" + this.c + ", bottomLeft=" + this.d + ")";
    }
}
