package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class z00 extends gd0 {
    public final bw0 a;
    public final vu b;

    public z00(bw0 bw0Var, vu vuVar) {
        this.a = bw0Var;
        this.b = vuVar;
    }

    @Override // defpackage.gd0
    public final bd0 e() {
        return new c10(this.a, this.b);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof z00) {
                z00 z00Var = (z00) obj;
                if (this.a == z00Var.a && this.b.equals(z00Var.b)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        c10 c10Var = (c10) bd0Var;
        c10Var.getClass();
        c10Var.s = this.a;
        c10Var.t = this.b;
        o20.t(c10Var);
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 31);
    }
}
