package defpackage;

import java.util.LinkedHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class qb0 extends ob0 implements kc0 {
    public final ng0 s;
    public LinkedHashMap u;
    public qc0 w;
    public final oe0 x;
    public long t = 0;
    public final rb0 v = new rb0(this);

    public qb0(ng0 ng0Var) {
        this.s = ng0Var;
        oe0 oe0Var = xg0.a;
        this.x = new oe0();
    }

    public static final void E0(qb0 qb0Var, qc0 qc0Var) {
        LinkedHashMap linkedHashMap;
        if (qc0Var != null) {
            qb0Var.k0((qc0Var.b() & 4294967295L) | (qc0Var.d() << 32));
        } else {
            qb0Var.k0(0L);
        }
        if (!o20.e(qb0Var.w, qc0Var) && qc0Var != null && ((((linkedHashMap = qb0Var.u) != null && !linkedHashMap.isEmpty()) || !qc0Var.r().isEmpty()) && !o20.e(qc0Var.r(), qb0Var.u))) {
            ub0 ub0Var = qb0Var.s.s.I.q;
            ub0Var.getClass();
            ub0Var.u.f();
            LinkedHashMap linkedHashMap2 = qb0Var.u;
            if (linkedHashMap2 == null) {
                linkedHashMap2 = new LinkedHashMap();
                qb0Var.u = linkedHashMap2;
            }
            linkedHashMap2.clear();
            linkedHashMap2.putAll(qc0Var.r());
        }
        qb0Var.w = qc0Var;
    }

    @Override // defpackage.em0, defpackage.kc0
    public final Object A() {
        return this.s.A();
    }

    @Override // defpackage.mm
    public final float B() {
        return this.s.B();
    }

    @Override // defpackage.ob0, defpackage.rc0
    public final boolean D() {
        return true;
    }

    @Override // defpackage.ob0
    public final void D0() {
        i0(this.t, 0.0f, null);
    }

    public void F0() {
        w0().a();
    }

    public final void G0(long j) {
        if (!v10.a(this.t, j)) {
            this.t = j;
            ng0 ng0Var = this.s;
            ub0 ub0Var = ng0Var.s.I.q;
            if (ub0Var != null) {
                ub0Var.q0();
            }
            ob0.A0(ng0Var);
        }
        if (!this.o) {
            q0(w0());
        }
    }

    public final long H0(qb0 qb0Var, boolean z) {
        long j = 0;
        while (!this.equals(qb0Var)) {
            if (!this.m || !z) {
                j = v10.c(j, this.t);
            }
            ng0 ng0Var = this.s.u;
            ng0Var.getClass();
            this = ng0Var.N0();
            this.getClass();
        }
        return j;
    }

    @Override // defpackage.rc0
    public final m40 getLayoutDirection() {
        return this.s.s.B;
    }

    @Override // defpackage.em0
    public final void i0(long j, float f, gv gvVar) {
        G0(j);
        if (this.n) {
            return;
        }
        F0();
    }

    @Override // defpackage.ob0
    public final ob0 s0() {
        ng0 ng0Var = this.s.t;
        if (ng0Var != null) {
            return ng0Var.N0();
        }
        return null;
    }

    @Override // defpackage.ob0
    public final l40 t0() {
        return this.v;
    }

    @Override // defpackage.ob0
    public final boolean u0() {
        if (this.w != null) {
            return true;
        }
        return false;
    }

    @Override // defpackage.ob0
    public final z40 v0() {
        return this.s.s;
    }

    @Override // defpackage.ob0
    public final qc0 w0() {
        qc0 qc0Var = this.w;
        if (qc0Var != null) {
            return qc0Var;
        }
        throw d3.t("LookaheadDelegate has not been measured yet when measureResult is requested.");
    }

    @Override // defpackage.ob0
    public final ob0 x0() {
        ng0 ng0Var = this.s.u;
        if (ng0Var != null) {
            return ng0Var.N0();
        }
        return null;
    }

    @Override // defpackage.mm
    public final float y() {
        return this.s.y();
    }

    @Override // defpackage.ob0
    public final long y0() {
        return this.t;
    }
}
