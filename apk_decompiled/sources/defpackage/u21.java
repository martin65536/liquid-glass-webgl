package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class u21 extends ww0 {
    public final ww0 e;
    public final boolean f;
    public final boolean g;
    public gv h;
    public final long i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public u21(ww0 ww0Var, gv gvVar, boolean z, boolean z2) {
        super(0L, ax0.i);
        gv e;
        ts0 ts0Var = cx0.a;
        this.e = ww0Var;
        this.f = z;
        this.g = z2;
        this.h = cx0.k(gvVar, (ww0Var == null || (e = ww0Var.e()) == null) ? cx0.j.e : e, z);
        this.i = m20.o();
    }

    @Override // defpackage.ww0
    public final void c() {
        ww0 ww0Var;
        this.c = true;
        if (this.g && (ww0Var = this.e) != null) {
            ww0Var.c();
        }
    }

    @Override // defpackage.ww0
    public final ax0 d() {
        return v().d();
    }

    @Override // defpackage.ww0
    public final gv e() {
        return this.h;
    }

    @Override // defpackage.ww0
    public final boolean f() {
        return v().f();
    }

    @Override // defpackage.ww0
    public final long g() {
        return v().g();
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
    public final void m() {
        v().m();
    }

    @Override // defpackage.ww0
    public final void n(ny0 ny0Var) {
        v().n(ny0Var);
    }

    @Override // defpackage.ww0
    public final ww0 u(gv gvVar) {
        gv k = cx0.k(gvVar, this.h, true);
        if (!this.f) {
            return cx0.g(v().u(null), k, true);
        }
        return v().u(k);
    }

    public final ww0 v() {
        ww0 ww0Var = this.e;
        if (ww0Var == null) {
            return cx0.j;
        }
        return ww0Var;
    }
}
