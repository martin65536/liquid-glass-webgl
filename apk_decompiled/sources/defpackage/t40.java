package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class t40 extends ng0 {
    public static final r5 W;
    public r40 U;
    public s40 V;

    static {
        r5 f = o4.f();
        f.c(se.e);
        f.a.setStrokeWidth(1.0f);
        f.e(1);
        W = f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public t40(z40 z40Var, r40 r40Var) {
        super(z40Var);
        s40 s40Var;
        this.U = r40Var;
        if (z40Var.l != null) {
            s40Var = new s40(this);
        } else {
            s40Var = null;
        }
        this.V = s40Var;
        if ((((bd0) r40Var).e.g & 512) == 0) {
            return;
        }
        v7.d();
        throw null;
    }

    @Override // defpackage.ng0
    public final void K0() {
        if (this.V == null) {
            this.V = new s40(this);
        }
    }

    @Override // defpackage.ng0
    public final qb0 N0() {
        return this.V;
    }

    @Override // defpackage.ng0
    public final bd0 P0() {
        return ((bd0) this.U).e;
    }

    @Override // defpackage.ng0
    public final void f1(uc ucVar, hx hxVar) {
        ng0 ng0Var;
        ng0 ng0Var2 = this.t;
        ng0Var2.getClass();
        ng0Var2.I0(ucVar, hxVar);
        if (((b4) c50.a(this.s)).getShowLayoutBounds() && (ng0Var = this.t) != null) {
            if (!c20.a(this.g, ng0Var.g) || !v10.a(ng0Var.D, 0L)) {
                long j = this.g;
                ucVar.m(0.5f, 0.5f, ((int) (j >> 32)) - 0.5f, ((int) (j & 4294967295L)) - 0.5f, W);
            }
        }
    }

    @Override // defpackage.em0
    public final void i0(long j, float f, gv gvVar) {
        g1(j, f, gvVar);
        if (!this.n) {
            b1();
            ng0 ng0Var = this.t;
            ng0Var.getClass();
            ng0Var.o = this.o;
            w0().a();
            ng0Var.o = false;
        }
    }

    @Override // defpackage.ob0
    public final int n0(ry ryVar) {
        s40 s40Var = this.V;
        if (s40Var != null) {
            oe0 oe0Var = s40Var.x;
            int d = oe0Var.d(ryVar);
            if (d >= 0) {
                return oe0Var.c[d];
            }
            return Integer.MIN_VALUE;
        }
        return d20.b(this, ryVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void p1(r40 r40Var) {
        if (!r40Var.equals(this.U) && (((bd0) r40Var).e.g & 512) != 0) {
            v7.d();
        } else {
            this.U = r40Var;
        }
    }

    @Override // defpackage.kc0
    public final em0 v(long j) {
        l0(j);
        r40 r40Var = this.U;
        ng0 ng0Var = this.t;
        ng0Var.getClass();
        j1(r40Var.Y(this, ng0Var, j));
        a1();
        return this;
    }
}
