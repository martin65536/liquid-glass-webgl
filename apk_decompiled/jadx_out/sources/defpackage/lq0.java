package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class lq0 extends h30 {
    public final i30 l;

    public lq0(i30 i30Var) {
        this.l = i30Var;
    }

    @Override // defpackage.h30
    public final boolean r() {
        return false;
    }

    @Override // defpackage.h30
    public final void s(Throwable th) {
        Object Q = q().Q();
        boolean z = Q instanceof qf;
        i30 i30Var = this.l;
        if (z) {
            i30Var.u(o30.l(((qf) Q).a));
        } else {
            i30Var.u(o20.K(Q));
        }
    }
}
