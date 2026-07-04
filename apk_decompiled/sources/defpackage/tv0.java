package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tv0 {
    public static final tv0 d = new tv0(f31.f(4278190080L), 0, 0.0f);
    public final long a;
    public final long b;
    public final float c;

    public tv0(long j, long j2, float f) {
        this.a = j;
        this.b = j2;
        this.c = f;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof tv0) {
                tv0 tv0Var = (tv0) obj;
                if (se.c(this.a, tv0Var.a) && ch0.c(this.b, tv0Var.b) && this.c == tv0Var.c) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.c) + ((ch0.e(this.b) + (se.i(this.a) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Shadow(color=");
        sb.append((Object) se.j(this.a));
        sb.append(", offset=");
        sb.append((Object) ch0.i(this.b));
        sb.append(", blurRadius=");
        return d3.v(sb, this.c, ')');
    }
}
