package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qx0 extends jc0 {
    public final long s;

    public qx0(long j) {
        this.s = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof qx0)) {
            return false;
        }
        if (se.c(this.s, ((qx0) obj).s)) {
            return true;
        }
        return false;
    }

    @Override // defpackage.jc0
    public final void g(float f, long j, r5 r5Var) {
        r5Var.a(1.0f);
        long j2 = this.s;
        if (f != 1.0f) {
            j2 = se.b(j2, se.d(j2) * f);
        }
        r5Var.c(j2);
        if (r5Var.c != null) {
            r5Var.c = null;
            r5Var.a.setShader(null);
        }
    }

    public final int hashCode() {
        return se.i(this.s);
    }

    public final String toString() {
        return "SolidColor(value=" + ((Object) se.j(this.s)) + ')';
    }
}
