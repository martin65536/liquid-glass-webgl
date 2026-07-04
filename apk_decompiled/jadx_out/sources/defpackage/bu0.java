package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bu0 extends gd0 {
    public final nt0 a;

    public bu0(nt0 nt0Var) {
        this.a = nt0Var;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [jt0, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? bd0Var = new bd0();
        bd0Var.s = this.a;
        bd0Var.t = true;
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof bu0) {
            if (o20.e(this.a, ((bu0) obj).a)) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        jt0 jt0Var = (jt0) bd0Var;
        jt0Var.s = this.a;
        jt0Var.t = true;
    }

    public final int hashCode() {
        return (((this.a.hashCode() * 31) + 1237) * 31) + 1231;
    }
}
