package defpackage;

import android.os.Build;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class c10 extends bd0 implements tp {
    public bw0 s;
    public vu t;
    public hx u;
    public y5 w;
    public final r5 v = o4.f();
    public float x = Float.NaN;

    public c10(bw0 bw0Var, vu vuVar) {
        this.s = bw0Var;
        this.t = vuVar;
    }

    @Override // defpackage.tp
    public final void R(b50 b50Var) {
        y00 y00Var;
        y5 y5Var;
        yc ycVar = b50Var.e;
        b50Var.r();
        if (Build.VERSION.SDK_INT >= 31 && (y00Var = (y00) this.t.a()) != null) {
            long j = y00Var.b;
            hx hxVar = this.u;
            if (hxVar != null) {
                jx jxVar = hxVar.a;
                long v = ycVar.f.v();
                m40 layoutDirection = b50Var.getLayoutDirection();
                float G = b50Var.G(y00Var.a);
                final float G2 = b50Var.G(Float.intBitsToFloat((int) (j >> 32)));
                final float G3 = b50Var.G(Float.intBitsToFloat((int) (j & 4294967295L)));
                final g30 b = this.s.g.b(v, layoutDirection, b50Var);
                ia iaVar = null;
                if (b instanceof hj0) {
                    y5Var = this.w;
                    if (y5Var == null) {
                        y5Var = a6.a();
                        this.w = y5Var;
                    }
                } else {
                    y5Var = null;
                }
                this.v.c(y00Var.c);
                hxVar.g(y00Var.d);
                int i = y00Var.e;
                if (jxVar.u() != i) {
                    jxVar.k(i);
                }
                if (this.x != G) {
                    if (G > 0.0f) {
                        iaVar = new ia(null, G, G, 3);
                    }
                    if (!o20.e(jxVar.x(), iaVar)) {
                        jxVar.p(iaVar);
                    }
                    this.x = G;
                }
                final y5 y5Var2 = y5Var;
                b50Var.M(hxVar, d20.I(b50Var.j()), new gv() { // from class: b10
                    @Override // defpackage.gv
                    public final Object e(Object obj) {
                        up upVar = (up) obj;
                        upVar.getClass();
                        uc q = upVar.J().q();
                        q.h();
                        g30 g30Var = g30.this;
                        n30.g(q, g30Var, y5Var2);
                        o30.m(q, g30Var, this.v);
                        float f = G2;
                        float f2 = G3;
                        q.d(f, f2);
                        o30.m(q, g30Var, a10.a);
                        q.d(-f, -f2);
                        q.f();
                        return x31.a;
                    }
                });
                uc q = ycVar.f.q();
                q.h();
                n30.g(q, b, y5Var2);
                n20.r(b50Var, hxVar);
                q.f();
            }
        }
    }

    @Override // defpackage.bd0
    public final boolean q0() {
        return false;
    }

    @Override // defpackage.bd0
    public final void t0() {
        hx a = ((n5) k81.C(this)).a();
        jx jxVar = a.a;
        if (jxVar.E() != 1) {
            jxVar.H(1);
        }
        this.u = a;
    }

    @Override // defpackage.bd0
    public final void v0() {
        ex C = k81.C(this);
        hx hxVar = this.u;
        if (hxVar != null) {
            ((n5) C).c(hxVar);
            this.u = null;
        }
    }

    @Override // defpackage.tp
    public final /* bridge */ void m0() {
    }
}
