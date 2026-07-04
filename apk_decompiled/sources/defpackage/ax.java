package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ax extends ze0 {
    @Override // defpackage.ze0
    public final ze0 D(gv gvVar, gv gvVar2) {
        return (ze0) ((ww0) cx0.e(new lw(new zw(0, gvVar, gvVar2), 1)));
    }

    @Override // defpackage.ze0, defpackage.ww0
    public final void c() {
        synchronized (cx0.c) {
            o();
        }
    }

    @Override // defpackage.ze0, defpackage.ww0
    public final void k() {
        o30.y();
        throw null;
    }

    @Override // defpackage.ze0, defpackage.ww0
    public final void l() {
        o30.y();
        throw null;
    }

    @Override // defpackage.ze0, defpackage.ww0
    public final void m() {
        cx0.a();
    }

    @Override // defpackage.ze0, defpackage.ww0
    public final ww0 u(gv gvVar) {
        int i = 1;
        return (jo0) ((ww0) cx0.e(new lw(new tb(i, gvVar), i)));
    }

    @Override // defpackage.ze0
    public final y20 w() {
        throw new IllegalStateException("Cannot apply the global snapshot directly. Call Snapshot.advanceGlobalSnapshot");
    }
}
