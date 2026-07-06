package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class v00 extends qb0 {
    @Override // defpackage.qb0
    public final void F0() {
        ub0 ub0Var = this.s.s.I.q;
        ub0Var.getClass();
        ub0Var.r0();
    }

    @Override // defpackage.ob0
    public final int n0(ry ryVar) {
        int i;
        ub0 ub0Var = this.s.s.I.q;
        ub0Var.getClass();
        d50 d50Var = ub0Var.j;
        v40 v40Var = d50Var.d;
        a50 a50Var = ub0Var.u;
        if (v40Var == v40.f) {
            a50Var.d = true;
            if (a50Var.b) {
                d50Var.f = true;
                d50Var.g = true;
            }
        } else {
            a50Var.e = true;
        }
        v00 v00Var = ub0Var.I().V;
        if (v00Var != null) {
            v00Var.o = true;
        }
        ub0Var.N();
        v00 v00Var2 = ub0Var.I().V;
        if (v00Var2 != null) {
            v00Var2.o = false;
        }
        Integer num = (Integer) a50Var.g.get(ryVar);
        if (num != null) {
            i = num.intValue();
        } else {
            i = Integer.MIN_VALUE;
        }
        this.x.g(i, ryVar);
        return i;
    }

    @Override // defpackage.kc0
    public final em0 v(long j) {
        l0(j);
        ng0 ng0Var = this.s;
        ef0 w = ng0Var.s.w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            ub0 ub0Var = ((z40) objArr[i2]).I.q;
            ub0Var.getClass();
            ub0Var.n = x40.g;
        }
        z40 z40Var = ng0Var.s;
        qb0.E0(this, z40Var.z.e(this, z40Var.l(), j));
        return this;
    }
}
