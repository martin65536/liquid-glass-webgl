package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bg0 extends ww0 {
    public final gv e;
    public final ww0 f;

    public bg0(long j, ax0 ax0Var, gv gvVar, ww0 ww0Var) {
        super(j, ax0Var);
        this.e = gvVar;
        this.f = ww0Var;
        ww0Var.k();
    }

    @Override // defpackage.ww0
    public final void c() {
        ww0 ww0Var = this.f;
        if (!this.c) {
            if (this.b != ww0Var.g()) {
                a();
            }
            ww0Var.l();
            this.c = true;
            synchronized (cx0.c) {
                o();
            }
        }
    }

    @Override // defpackage.ww0
    public final gv e() {
        return this.e;
    }

    @Override // defpackage.ww0
    public final boolean f() {
        return true;
    }

    @Override // defpackage.ww0
    public final gv i() {
        return null;
    }

    @Override // defpackage.ww0
    public final void k() {
        o30.y();
        throw null;
    }

    @Override // defpackage.ww0
    public final void l() {
        o30.y();
        throw null;
    }

    @Override // defpackage.ww0
    public final void n(ny0 ny0Var) {
        ts0 ts0Var = cx0.a;
        throw new IllegalStateException("Cannot modify a state object in a read-only snapshot");
    }

    @Override // defpackage.ww0
    public final ww0 u(gv gvVar) {
        return new bg0(this.b, this.a, cx0.k(gvVar, this.e, true), this.f);
    }

    @Override // defpackage.ww0
    public final void m() {
    }
}
