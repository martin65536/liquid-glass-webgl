package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class jt0 extends bd0 implements r40, qu0 {
    public nt0 s;
    public boolean t;

    @Override // defpackage.r40
    public final qc0 Y(ob0 ob0Var, kc0 kc0Var, long j) {
        dj0 dj0Var;
        int g;
        gv gvVar;
        int i;
        int i2;
        if (this.t) {
            dj0Var = dj0.e;
        } else {
            dj0Var = dj0.f;
        }
        o4.s(j, dj0Var);
        int i3 = Integer.MAX_VALUE;
        if (this.t) {
            g = Integer.MAX_VALUE;
        } else {
            g = si.g(j);
        }
        if (this.t) {
            i3 = si.h(j);
        }
        em0 v = kc0Var.v(si.a(j, 0, i3, 0, g, 5));
        int i4 = v.e;
        int h = si.h(j);
        if (i4 > h) {
            i4 = h;
        }
        int i5 = v.f;
        int g2 = si.g(j);
        if (i5 > g2) {
            i5 = g2;
        }
        int i6 = v.f - i5;
        int i7 = v.e - i4;
        if (!this.t) {
            i6 = i7;
        }
        nt0 nt0Var = this.s;
        fk0 fk0Var = nt0Var.e;
        fk0 fk0Var2 = nt0Var.a;
        fk0Var.h(i6);
        ww0 t = t20.t();
        if (t != null) {
            gvVar = t.e();
        } else {
            gvVar = null;
        }
        ww0 C = t20.C(t);
        try {
            if (fk0Var2.g() > i6) {
                fk0Var2.h(i6);
            }
            t20.K(t, C, gvVar);
            nt0 nt0Var2 = this.s;
            if (this.t) {
                i = i5;
            } else {
                i = i4;
            }
            nt0Var2.b.h(i);
            nt0 nt0Var3 = this.s;
            if (this.t) {
                i2 = v.f;
            } else {
                i2 = v.e;
            }
            nt0Var3.c.h(i2);
            return ob0Var.e0(i4, i5, fr.e, new lo0(i6, 1, this, v));
        } catch (Throwable th) {
            t20.K(t, C, gvVar);
            throw th;
        }
    }

    @Override // defpackage.qu0
    public final void f0(bv0 bv0Var) {
        t30[] t30VarArr = zu0.a;
        av0 av0Var = wu0.m;
        t30[] t30VarArr2 = zu0.a;
        t30 t30Var = t30VarArr2[6];
        bv0Var.a(av0Var, Boolean.TRUE);
        final int i = 0;
        final int i2 = 1;
        et0 et0Var = new et0(new vu(this) { // from class: it0
            public final /* synthetic */ jt0 f;

            {
                this.f = this;
            }

            @Override // defpackage.vu
            public final Object a() {
                int g;
                int i3 = i;
                jt0 jt0Var = this.f;
                switch (i3) {
                    case 0:
                        g = jt0Var.s.a.g();
                        break;
                    default:
                        g = jt0Var.s.e.g();
                        break;
                }
                return Float.valueOf(g);
            }
        }, new vu(this) { // from class: it0
            public final /* synthetic */ jt0 f;

            {
                this.f = this;
            }

            @Override // defpackage.vu
            public final Object a() {
                int g;
                int i3 = i2;
                jt0 jt0Var = this.f;
                switch (i3) {
                    case 0:
                        g = jt0Var.s.a.g();
                        break;
                    default:
                        g = jt0Var.s.e.g();
                        break;
                }
                return Float.valueOf(g);
            }
        });
        if (this.t) {
            av0 av0Var2 = wu0.v;
            t30 t30Var2 = t30VarArr2[13];
            bv0Var.a(av0Var2, et0Var);
        } else {
            av0 av0Var3 = wu0.u;
            t30 t30Var3 = t30VarArr2[12];
            bv0Var.a(av0Var3, et0Var);
        }
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean h0() {
        return false;
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean i0() {
        return false;
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean u() {
        return true;
    }
}
