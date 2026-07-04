package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class c11 {
    public static final c11 c = new c11(d20.v(0), d20.v(0));
    public final long a;
    public final long b;

    public c11(long j, long j2) {
        this.a = j;
        this.b = j2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c11)) {
            return false;
        }
        c11 c11Var = (c11) obj;
        if (t11.a(this.a, c11Var.a) && t11.a(this.b, c11Var.b)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return t11.d(this.b) + (t11.d(this.a) * 31);
    }

    public final String toString() {
        return "TextIndent(firstLine=" + ((Object) t11.e(this.a)) + ", restLine=" + ((Object) t11.e(this.b)) + ')';
    }
}
