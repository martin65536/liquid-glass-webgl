package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class d40 extends gd0 {
    public final c40 a;

    public d40(c40 c40Var) {
        c40Var.getClass();
        this.a = c40Var;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [e40, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        c40 c40Var = this.a;
        c40Var.getClass();
        ?? bd0Var = new bd0();
        bd0Var.s = c40Var;
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof d40)) {
            return false;
        }
        if (o20.e(this.a, ((d40) obj).a)) {
            return true;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        e40 e40Var = (e40) bd0Var;
        e40Var.getClass();
        c40 c40Var = e40Var.s;
        c40 c40Var2 = this.a;
        if (!o20.e(c40Var, c40Var2)) {
            e40Var.s.d(null);
            c40Var2.getClass();
            e40Var.s = c40Var2;
        }
        o20.t(e40Var);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }
}
