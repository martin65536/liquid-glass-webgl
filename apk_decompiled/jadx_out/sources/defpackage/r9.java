package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class r9 extends bd0 implements r40, tp, qu0, xm0, ed0, jk0, j40, ww, nj0 {
    public ad0 s;

    @Override // defpackage.xm0
    public final long A() {
        return o4.i;
    }

    public final void D0(boolean z) {
        if (!this.r) {
            q00.b("initializeModifier called on unattached node");
        }
        ad0 ad0Var = this.s;
        if ((this.g & 4) != 0 && !z) {
            k81.B(this, 2).W0();
        }
        if ((this.g & 2) != 0) {
            e01 e01Var = k81.E(this).H.e;
            e01Var.getClass();
            if (e01Var.s) {
                ng0 ng0Var = this.l;
                ng0Var.getClass();
                ((t40) ng0Var).p1(this);
                lj0 lj0Var = ng0Var.P;
                if (lj0Var != null) {
                    ((kx) lj0Var).c();
                }
            }
            if (!z) {
                k81.B(this, 2).W0();
                k81.E(this).B();
            }
        }
        if (ad0Var instanceof k70) {
            ((k70) ad0Var).a.k = k81.E(this);
        }
        if ((this.g & 8) != 0) {
            ((b4) k81.F(this)).D();
        }
    }

    @Override // defpackage.ww
    public final void E(ng0 ng0Var) {
        this.s.getClass();
        throw new ClassCastException();
    }

    public final void E0() {
        ad0 ad0Var = this.s;
        q00.b("onFocusEvent called on wrong node");
        ad0Var.getClass();
        throw new ClassCastException();
    }

    @Override // defpackage.nj0
    public final boolean H() {
        return this.r;
    }

    @Override // defpackage.xm0
    public final void N(pm0 pm0Var, qm0 qm0Var, long j) {
        this.s.getClass();
        throw new ClassCastException();
    }

    @Override // defpackage.tp
    public final void R(b50 b50Var) {
        ad0 ad0Var = this.s;
        ad0Var.getClass();
        throw null;
    }

    @Override // defpackage.xm0
    public final boolean X() {
        this.s.getClass();
        throw new ClassCastException();
    }

    @Override // defpackage.r40
    public final qc0 Y(ob0 ob0Var, kc0 kc0Var, long j) {
        this.s.getClass();
        throw new ClassCastException();
    }

    @Override // defpackage.xm0
    public final void c0() {
        g0();
        throw null;
    }

    @Override // defpackage.qu0
    public final void f0(bv0 bv0Var) {
        ad0 ad0Var = this.s;
        ad0Var.getClass();
        nu0 c = ((ou0) ad0Var).c();
        bv0Var.getClass();
        nu0 nu0Var = (nu0) bv0Var;
        ve0 ve0Var = nu0Var.e;
        if (c.g) {
            nu0Var.g = true;
        }
        if (c.h) {
            nu0Var.h = true;
        }
        ve0 ve0Var2 = c.e;
        Object[] objArr = ve0Var2.b;
        Object[] objArr2 = ve0Var2.c;
        long[] jArr = ve0Var2.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            int i4 = (i << 3) + i3;
                            Object obj = objArr[i4];
                            Object obj2 = objArr2[i4];
                            av0 av0Var = (av0) obj;
                            if (!ve0Var.b(av0Var)) {
                                ve0Var.m(av0Var, obj2);
                            } else if (obj2 instanceof n0) {
                                Object g = ve0Var.g(av0Var);
                                g.getClass();
                                n0 n0Var = (n0) g;
                                String str = n0Var.a;
                                if (str == null) {
                                    str = ((n0) obj2).a;
                                }
                                sv svVar = n0Var.b;
                                if (svVar == null) {
                                    svVar = ((n0) obj2).b;
                                }
                                ve0Var.m(av0Var, new n0(str, svVar));
                            }
                        }
                        j >>= 8;
                    }
                    if (i2 != 8) {
                        return;
                    }
                }
                if (i != length) {
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    @Override // defpackage.xm0
    public final void g0() {
        this.s.getClass();
        throw new ClassCastException();
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean h0() {
        return false;
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean i0() {
        return false;
    }

    @Override // defpackage.jk0
    public final Object l0(Object obj) {
        this.s.getClass();
        throw new ClassCastException();
    }

    @Override // defpackage.tp
    public final void m0() {
        o20.t(this);
    }

    @Override // defpackage.xm0
    public final boolean n0() {
        this.s.getClass();
        throw new ClassCastException();
    }

    @Override // defpackage.bd0
    public final void t0() {
        D0(true);
    }

    public final String toString() {
        return this.s.toString();
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean u() {
        return true;
    }

    @Override // defpackage.ed0
    public final x1 v() {
        return x1.F;
    }

    @Override // defpackage.bd0
    public final void v0() {
        if (!this.r) {
            q00.b("unInitializeModifier called on unattached node");
        }
        if ((this.g & 8) != 0) {
            ((b4) k81.F(this)).D();
        }
    }

    @Override // defpackage.bd0
    public final void u0() {
    }

    @Override // defpackage.sc0
    public final void C(long j) {
    }

    @Override // defpackage.j40
    public final void x(l40 l40Var) {
    }
}
