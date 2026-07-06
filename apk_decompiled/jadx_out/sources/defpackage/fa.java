package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fa extends bd0 implements r40, qu0 {
    public gv s;

    @Override // defpackage.r40
    public final qc0 Y(ob0 ob0Var, kc0 kc0Var, long j) {
        em0 v = kc0Var.v(j);
        return ob0Var.e0(v.e, v.f, fr.e, new o6(3, v, this));
    }

    @Override // defpackage.qu0
    public final void f0(bv0 bv0Var) {
        zv0 zv0Var;
        boolean z;
        gv gvVar;
        ng0 B = k81.B(this, 2);
        if (!B.J) {
            pq0 pq0Var = k81.c;
            if (pq0Var == null) {
                k81.c = new pq0();
            } else {
                pq0Var.r();
            }
            pq0 pq0Var2 = k81.c;
            pq0Var2.getClass();
            pq0Var2.t = B.s.A;
            pq0Var2.s = d20.J(B.g);
            ww0 t = t20.t();
            if (t != null) {
                gvVar = t.e();
            } else {
                gvVar = null;
            }
            ww0 C = t20.C(t);
            try {
                this.s.e(pq0Var2);
                t20.K(t, C, gvVar);
                zv0Var = pq0Var2.p;
                z = pq0Var2.q;
            } catch (Throwable th) {
                t20.K(t, C, gvVar);
                throw th;
            }
        } else {
            zv0Var = B.H;
            z = B.I;
        }
        if (!z) {
            return;
        }
        t30[] t30VarArr = zu0.a;
        av0 av0Var = wu0.M;
        t30 t30Var = zu0.a[30];
        bv0Var.a(av0Var, zv0Var);
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean h0() {
        return false;
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean i0() {
        return false;
    }

    @Override // defpackage.bd0
    public final boolean q0() {
        return false;
    }

    public final String toString() {
        return "BlockGraphicsLayerModifier(block=" + this.s + ')';
    }

    @Override // defpackage.qu0
    public final boolean u() {
        return false;
    }
}
