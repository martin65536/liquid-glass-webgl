package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class s7 extends gd0 implements ou0 {
    public final gv a;

    public s7(gv gvVar) {
        this.a = gvVar;
    }

    @Override // defpackage.ou0
    public final nu0 c() {
        nu0 nu0Var = new nu0();
        nu0Var.g = false;
        this.a.e(nu0Var);
        return nu0Var;
    }

    @Override // defpackage.gd0
    public final bd0 e() {
        return new vj(this.a, false);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof s7) || this.a != ((s7) obj).a) {
                return false;
            }
            return true;
        }
        return true;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        vj vjVar = (vj) bd0Var;
        vjVar.getClass();
        vjVar.t = this.a;
    }

    public final int hashCode() {
        return this.a.hashCode() + 38347;
    }
}
