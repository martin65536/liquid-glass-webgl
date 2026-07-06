package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class e40 extends bd0 implements tp, ww {
    public c40 s;

    @Override // defpackage.ww
    public final void E(ng0 ng0Var) {
        if (ng0Var.P0().r) {
            this.s.d(ng0Var);
        }
    }

    @Override // defpackage.tp
    public final void R(b50 b50Var) {
        b50Var.r();
        hx hxVar = this.s.a;
        c cVar = new c(10, this, b50Var);
        long I = d20.I(b50Var.j());
        hxVar.getClass();
        b50Var.M(hxVar, I, new c(11, k81.E(this).A, cVar));
    }

    @Override // defpackage.bd0
    public final void v0() {
        this.s.d(null);
    }

    @Override // defpackage.tp
    public final /* bridge */ void m0() {
    }
}
