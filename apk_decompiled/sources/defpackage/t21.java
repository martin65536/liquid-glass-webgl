package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class t21 extends ze0 {
    public final ze0 o;
    public final boolean p;
    public final boolean q;
    public gv r;
    public gv s;
    public final long t;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public t21(defpackage.ze0 r8, defpackage.gv r9, defpackage.gv r10, boolean r11, boolean r12) {
        /*
            r7 = this;
            ts0 r0 = defpackage.cx0.a
            if (r8 == 0) goto La
            gv r0 = r8.e()
            if (r0 != 0) goto Le
        La:
            ax r0 = defpackage.cx0.j
            gv r0 = r0.e
        Le:
            gv r5 = defpackage.cx0.k(r9, r0, r11)
            if (r8 == 0) goto L1a
            gv r9 = r8.i()
            if (r9 != 0) goto L1e
        L1a:
            ax r9 = defpackage.cx0.j
            gv r9 = r9.f
        L1e:
            gv r6 = defpackage.cx0.l(r10, r9)
            r2 = 0
            ax0 r4 = defpackage.ax0.i
            r1 = r7
            r1.<init>(r2, r4, r5, r6)
            r1.o = r8
            r1.p = r11
            r1.q = r12
            gv r7 = r1.e
            r1.r = r7
            gv r7 = r1.f
            r1.s = r7
            long r7 = defpackage.m20.o()
            r1.t = r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.t21.<init>(ze0, gv, gv, boolean, boolean):void");
    }

    @Override // defpackage.ze0
    public final void C(we0 we0Var) {
        o30.y();
        throw null;
    }

    @Override // defpackage.ze0
    public final ze0 D(gv gvVar, gv gvVar2) {
        gv k = cx0.k(gvVar, this.r, true);
        gv l = cx0.l(gvVar2, this.s);
        if (!this.p) {
            return new t21(E().D(null, l), k, l, false, true);
        }
        return E().D(k, l);
    }

    public final ze0 E() {
        ze0 ze0Var = this.o;
        if (ze0Var == null) {
            return cx0.j;
        }
        return ze0Var;
    }

    @Override // defpackage.ze0, defpackage.ww0
    public final void c() {
        ze0 ze0Var;
        this.c = true;
        if (this.q && (ze0Var = this.o) != null) {
            ze0Var.c();
        }
    }

    @Override // defpackage.ww0
    public final ax0 d() {
        return E().d();
    }

    @Override // defpackage.ze0, defpackage.ww0
    public final gv e() {
        return this.r;
    }

    @Override // defpackage.ze0, defpackage.ww0
    public final boolean f() {
        return E().f();
    }

    @Override // defpackage.ww0
    public final long g() {
        return E().g();
    }

    @Override // defpackage.ze0, defpackage.ww0
    public final int h() {
        return E().h();
    }

    @Override // defpackage.ze0, defpackage.ww0
    public final gv i() {
        return this.s;
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
        E().m();
    }

    @Override // defpackage.ze0, defpackage.ww0
    public final void n(ny0 ny0Var) {
        E().n(ny0Var);
    }

    @Override // defpackage.ww0
    public final void r(ax0 ax0Var) {
        o30.y();
        throw null;
    }

    @Override // defpackage.ww0
    public final void s(long j) {
        o30.y();
        throw null;
    }

    @Override // defpackage.ze0, defpackage.ww0
    public final void t(int i) {
        E().t(i);
    }

    @Override // defpackage.ze0, defpackage.ww0
    public final ww0 u(gv gvVar) {
        gv k = cx0.k(gvVar, this.r, true);
        if (!this.p) {
            return cx0.g(E().u(null), k, true);
        }
        return E().u(k);
    }

    @Override // defpackage.ze0
    public final y20 w() {
        return E().w();
    }

    @Override // defpackage.ze0
    public final we0 x() {
        return E().x();
    }

    @Override // defpackage.ze0
    /* renamed from: y */
    public final gv e() {
        return this.r;
    }
}
