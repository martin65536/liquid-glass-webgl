package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class b50 implements up {
    public final yc e = new yc();
    public tp f;

    @Override // defpackage.mm
    public final float B() {
        return this.e.B();
    }

    @Override // defpackage.mm
    public final float G(float f) {
        return this.e.B() * f;
    }

    @Override // defpackage.up
    public final r7 J() {
        return this.e.f;
    }

    @Override // defpackage.up
    public final void M(hx hxVar, long j, gv gvVar) {
        hxVar.f(this, getLayoutDirection(), j, new oj(this, this.f, gvVar, 3));
    }

    @Override // defpackage.mm
    public final float O(long j) {
        yc ycVar = this.e;
        ycVar.getClass();
        return d3.e(ycVar, j);
    }

    @Override // defpackage.mm
    public final int S(float f) {
        yc ycVar = this.e;
        ycVar.getClass();
        return d3.c(f, ycVar);
    }

    @Override // defpackage.up
    public final void V(o5 o5Var, long j, long j2, long j3, float f, te teVar, int i) {
        this.e.V(o5Var, j, j2, j3, f, teVar, i);
    }

    @Override // defpackage.up
    public final long W() {
        return this.e.W();
    }

    @Override // defpackage.mm
    public final long Z(long j) {
        yc ycVar = this.e;
        ycVar.getClass();
        return d3.h(ycVar, j);
    }

    @Override // defpackage.up
    public final void a0(long j, long j2, long j3, float f, jc0 jc0Var, int i) {
        this.e.a0(j, j2, j3, f, jc0Var, i);
    }

    @Override // defpackage.mm
    public final float d0(long j) {
        yc ycVar = this.e;
        ycVar.getClass();
        return d3.g(ycVar, j);
    }

    @Override // defpackage.up
    public final m40 getLayoutDirection() {
        return this.e.e.b;
    }

    @Override // defpackage.up
    public final long j() {
        return this.e.f.v();
    }

    @Override // defpackage.mm
    public final long j0(float f) {
        return this.e.j0(f);
    }

    @Override // defpackage.mm
    public final float o0(float f) {
        return f / this.e.B();
    }

    public final void r() {
        yc ycVar = this.e;
        uc q = ycVar.f.q();
        im imVar = this.f;
        if (imVar != null) {
            bd0 bd0Var = (bd0) imVar;
            bd0 bd0Var2 = bd0Var.e.j;
            if (bd0Var2 != null && (bd0Var2.h & 4) != 0) {
                while (bd0Var2 != null) {
                    int i = bd0Var2.g;
                    if ((i & 2) != 0) {
                        break;
                    } else if ((i & 4) != 0) {
                        break;
                    } else {
                        bd0Var2 = bd0Var2.j;
                    }
                }
            }
            bd0Var2 = null;
            if (bd0Var2 != null) {
                ef0 ef0Var = null;
                while (bd0Var2 != null) {
                    if (bd0Var2 instanceof tp) {
                        tp tpVar = (tp) bd0Var2;
                        hx hxVar = (hx) ycVar.f.g;
                        ng0 B = k81.B(tpVar, 4);
                        long J = d20.J(B.g);
                        z40 z40Var = B.s;
                        z40Var.getClass();
                        ((b4) c50.a(z40Var)).getSharedDrawScope().u(q, J, B, tpVar, hxVar);
                    } else if ((bd0Var2.g & 4) != 0 && (bd0Var2 instanceof jm)) {
                        int i2 = 0;
                        for (bd0 bd0Var3 = ((jm) bd0Var2).t; bd0Var3 != null; bd0Var3 = bd0Var3.j) {
                            if ((bd0Var3.g & 4) != 0) {
                                i2++;
                                if (i2 == 1) {
                                    bd0Var2 = bd0Var3;
                                } else {
                                    if (ef0Var == null) {
                                        ef0Var = new ef0(new bd0[16]);
                                    }
                                    if (bd0Var2 != null) {
                                        ef0Var.b(bd0Var2);
                                        bd0Var2 = null;
                                    }
                                    ef0Var.b(bd0Var3);
                                }
                            }
                        }
                        if (i2 == 1) {
                        }
                    }
                    bd0Var2 = k81.h(ef0Var);
                }
                return;
            }
            ng0 B2 = k81.B(imVar, 4);
            if (B2.P0() == bd0Var.e) {
                B2 = B2.t;
                B2.getClass();
            }
            B2.f1(q, (hx) ycVar.f.g);
            return;
        }
        throw d3.t("Attempting to drawContent for a `null` node. This usually means that a call to ContentDrawScope#drawContent() has been captured inside a lambda, and is being invoked outside of the draw pass. Capturing the scope this way is unsupported - if you are trying to record drawContent with graphicsLayer.record(), make sure you are using the GraphicsLayer#record function within DrawScope, instead of the member function on GraphicsLayer.");
    }

    public final void u(uc ucVar, long j, ng0 ng0Var, tp tpVar, hx hxVar) {
        tp tpVar2 = this.f;
        this.f = tpVar;
        m40 m40Var = ng0Var.s.B;
        yc ycVar = this.e;
        mm s = ycVar.f.s();
        r7 r7Var = ycVar.f;
        m40 u = r7Var.u();
        uc q = r7Var.q();
        long v = r7Var.v();
        hx hxVar2 = (hx) r7Var.g;
        r7Var.E(ng0Var);
        r7Var.F(m40Var);
        r7Var.D(ucVar);
        r7Var.G(j);
        r7Var.g = hxVar;
        ucVar.h();
        try {
            tpVar.R(this);
            ucVar.f();
            r7Var.E(s);
            r7Var.F(u);
            r7Var.D(q);
            r7Var.G(v);
            r7Var.g = hxVar2;
            this.f = tpVar2;
        } catch (Throwable th) {
            ucVar.f();
            r7Var.E(s);
            r7Var.F(u);
            r7Var.D(q);
            r7Var.G(v);
            r7Var.g = hxVar2;
            throw th;
        }
    }

    public final void v(y5 y5Var, long j, jc0 jc0Var) {
        yc ycVar = this.e;
        ycVar.e.c.e(y5Var, yc.r(ycVar, j, jc0Var, 1.0f, 3));
    }

    @Override // defpackage.up
    public final void w(y5 y5Var, jc0 jc0Var, float f, jc0 jc0Var2) {
        this.e.w(y5Var, jc0Var, f, jc0Var2);
    }

    @Override // defpackage.mm
    public final float y() {
        return this.e.y();
    }
}
