package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ij0 {
    public final long a;
    public final tj0 b;

    public ij0() {
        long f = f31.f(4284900966L);
        tj0 tj0Var = new tj0(0.0f, 0.0f, 0.0f, 0.0f);
        this.a = f;
        this.b = tj0Var;
    }

    public final boolean equals(Object obj) {
        Class<?> cls;
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            cls = obj.getClass();
        } else {
            cls = null;
        }
        if (!ij0.class.equals(cls)) {
            return false;
        }
        obj.getClass();
        ij0 ij0Var = (ij0) obj;
        if (se.c(this.a, ij0Var.a) && o20.e(this.b, ij0Var.b)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.b.hashCode() + (se.i(this.a) * 31);
    }

    public final String toString() {
        return "OverscrollConfiguration(glowColor=" + ((Object) se.j(this.a)) + ", drawPadding=" + this.b + ')';
    }
}
