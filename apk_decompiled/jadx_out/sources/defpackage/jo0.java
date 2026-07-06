package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class jo0 extends ww0 {
    public final gv e;
    public int f;

    public jo0(long j, ax0 ax0Var, gv gvVar) {
        super(j, ax0Var);
        this.e = gvVar;
        this.f = 1;
    }

    @Override // defpackage.ww0
    public final void c() {
        if (!this.c) {
            l();
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
        this.f++;
    }

    @Override // defpackage.ww0
    public final void l() {
        int i = this.f - 1;
        this.f = i;
        if (i == 0) {
            a();
        }
    }

    @Override // defpackage.ww0
    public final void n(ny0 ny0Var) {
        ts0 ts0Var = cx0.a;
        throw new IllegalStateException("Cannot modify a state object in a read-only snapshot");
    }

    @Override // defpackage.ww0
    public final ww0 u(gv gvVar) {
        cx0.c(this);
        return new bg0(this.b, this.a, cx0.k(gvVar, this.e, true), this);
    }

    @Override // defpackage.ww0
    public final void m() {
    }
}
