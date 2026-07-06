package defpackage;

import android.os.Build;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class op extends bd0 implements r40, tp, ww, ah0 {
    public hx A;
    public final mp B;
    public final ik0 C;
    public final ek0 D;
    public final mp E;
    public final mp F;
    public m9 s;
    public bw0 t;
    public gv u;
    public gv v;
    public c40 w;
    public kv x;
    public gv y;
    public final np z;

    public op(m9 m9Var, bw0 bw0Var, gv gvVar, gv gvVar2, c40 c40Var, kv kvVar, gv gvVar3) {
        m9Var.getClass();
        gvVar.getClass();
        this.s = m9Var;
        this.t = bw0Var;
        this.u = gvVar;
        this.v = gvVar2;
        this.w = c40Var;
        this.x = kvVar;
        this.y = gvVar3;
        this.z = new np(this);
        this.B = new mp(this, 0);
        this.C = new ik0(null, x1.S);
        this.D = new ek0(0.0f);
        this.E = new mp(this, 1);
        this.F = new mp(this, 2);
    }

    public final void D0() {
        if (Build.VERSION.SDK_INT >= 31) {
            gv gvVar = this.u;
            np npVar = this.z;
            npVar.getClass();
            gvVar.getClass();
            npVar.i = 0.0f;
            npVar.j = null;
            gvVar.e(npVar);
            hx hxVar = this.A;
            if (hxVar != null) {
                gh ghVar = npVar.j;
                jx jxVar = hxVar.a;
                if (!o20.e(jxVar.x(), ghVar)) {
                    jxVar.p(ghVar);
                }
            }
            this.D.h(npVar.i);
        }
    }

    @Override // defpackage.ww
    public final void E(ng0 ng0Var) {
        if (ng0Var.P0().r) {
            boolean a = this.s.a();
            ik0 ik0Var = this.C;
            if (a) {
                ik0Var.setValue(ng0Var);
            } else if (((l40) ik0Var.getValue()) != null) {
                ik0Var.setValue(null);
            }
            c40 c40Var = this.w;
            if (c40Var != null) {
                c40Var.d(ng0Var);
            }
        }
    }

    @Override // defpackage.ah0
    public final void P() {
        o30.u(this, new f6(1, this));
    }

    @Override // defpackage.tp
    public final void R(b50 b50Var) {
        boolean z;
        hx hxVar;
        np npVar = this.z;
        npVar.getClass();
        yc ycVar = b50Var.e;
        float B = ycVar.B();
        float y = ycVar.y();
        long j = b50Var.j();
        m40 layoutDirection = b50Var.getLayoutDirection();
        if (B == npVar.e && y == npVar.f && mw0.a(j, npVar.g) && layoutDirection == npVar.h) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            npVar.e = B;
            npVar.f = y;
            npVar.g = j;
            layoutDirection.getClass();
            npVar.h = layoutDirection;
        }
        if (z) {
            D0();
        }
        this.F.e(b50Var);
        gv gvVar = this.y;
        if (gvVar != null) {
            gvVar.e(b50Var);
        }
        b50Var.r();
        c40 c40Var = this.w;
        if (c40Var != null && (hxVar = c40Var.a) != null) {
            b50Var.M(hxVar, d20.I(b50Var.j()), new c(11, k81.E(this).A, new mp(this, 3)));
        }
    }

    @Override // defpackage.r40
    public final qc0 Y(ob0 ob0Var, kc0 kc0Var, long j) {
        kc0Var.getClass();
        em0 v = kc0Var.v(j);
        return ob0Var.e0(v.e, v.f, fr.e, new c(7, v, this));
    }

    @Override // defpackage.bd0
    public final void t0() {
        this.A = ((n5) k81.C(this)).a();
        o30.u(this, new f6(1, this));
    }

    @Override // defpackage.bd0
    public final void v0() {
        ex C = k81.C(this);
        hx hxVar = this.A;
        if (hxVar != null) {
            ((n5) C).c(hxVar);
            this.A = null;
        }
        np npVar = this.z;
        npVar.e = 1.0f;
        npVar.f = 1.0f;
        npVar.g = 9205357640488583168L;
        npVar.h = m40.e;
        npVar.i = 0.0f;
        npVar.j = null;
        npVar.k.e.clear();
        this.C.setValue(null);
        c40 c40Var = this.w;
        if (c40Var != null) {
            c40Var.d(null);
        }
    }

    @Override // defpackage.tp
    public final /* bridge */ void m0() {
    }
}
