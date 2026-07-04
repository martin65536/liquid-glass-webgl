package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class j30 extends h30 {
    public final l30 l;
    public final k30 m;
    public final td n;
    public final Object o;

    public j30(l30 l30Var, k30 k30Var, td tdVar, Object obj) {
        this.l = l30Var;
        this.m = k30Var;
        this.n = tdVar;
        this.o = obj;
    }

    @Override // defpackage.h30
    public final boolean r() {
        return false;
    }

    @Override // defpackage.h30
    public final void s(Throwable th) {
        td tdVar = this.n;
        td Y = l30.Y(tdVar);
        l30 l30Var = this.l;
        k30 k30Var = this.m;
        Object obj = this.o;
        if (Y == null || !l30Var.l0(k30Var, Y, obj)) {
            k30Var.e.e(new la0(2), 2);
            td Y2 = l30.Y(tdVar);
            if (Y2 != null && l30Var.l0(k30Var, Y2, obj)) {
                return;
            }
            l30Var.A(l30Var.K(k30Var, obj));
        }
    }
}
