package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class z10 {
    public static final z10 e = new z10(0, 0, 0, 0);
    public final int a;
    public final int b;
    public final int c;
    public final int d;

    public z10(int i, int i2, int i3, int i4) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof z10)) {
            return false;
        }
        z10 z10Var = (z10) obj;
        if (this.a == z10Var.a && this.b == z10Var.b && this.c == z10Var.c && this.d == z10Var.d) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return (((((this.a * 31) + this.b) * 31) + this.c) * 31) + this.d;
    }

    public final String toString() {
        return "IntRect.fromLTRB(" + this.a + ", " + this.b + ", " + this.c + ", " + this.d + ')';
    }
}
