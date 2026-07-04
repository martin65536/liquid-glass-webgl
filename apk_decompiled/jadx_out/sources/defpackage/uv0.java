package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class uv0 extends gd0 {
    public final bw0 a;
    public final vu b;

    public uv0(bw0 bw0Var, vu vuVar) {
        this.a = bw0Var;
        this.b = vuVar;
    }

    @Override // defpackage.gd0
    public final bd0 e() {
        return new xv0(this.a, this.b);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof uv0) {
                uv0 uv0Var = (uv0) obj;
                if (this.a == uv0Var.a && this.b.equals(uv0Var.b)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        xv0 xv0Var = (xv0) bd0Var;
        xv0Var.getClass();
        xv0Var.s = this.a;
        xv0Var.t = this.b;
        o20.t(xv0Var);
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 31);
    }
}
