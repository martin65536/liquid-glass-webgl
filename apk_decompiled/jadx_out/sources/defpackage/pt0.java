package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pt0 extends jm implements ai, ah0 {
    public e5 A;
    public zt0 B;
    public im C;
    public f5 D;
    public e5 E;
    public boolean F;
    public au0 u;
    public dj0 v;
    public boolean w;
    public rl x;
    public je0 y;
    public boolean z;

    public final void G0() {
        e5 e5Var;
        im imVar = this.C;
        if (imVar == null) {
            if (this.z) {
                o30.u(this, new f6(7, this));
            }
            if (this.z) {
                e5Var = this.E;
            } else {
                e5Var = this.A;
            }
            if (e5Var != null) {
                jm jmVar = e5Var.i;
                if (!jmVar.e.r) {
                    D0(jmVar);
                    this.C = jmVar;
                    return;
                }
                return;
            }
            return;
        }
        if (!((bd0) imVar).e.r) {
            D0(imVar);
        }
    }

    public final boolean H0() {
        m40 m40Var;
        if (this.r) {
            m40Var = k81.E(this).B;
        } else {
            m40Var = m40.e;
        }
        dj0 dj0Var = this.v;
        if (m40Var == m40.f && dj0Var != dj0.e) {
            return false;
        }
        return true;
    }

    public final void I0(e5 e5Var, rl rlVar, je0 je0Var, dj0 dj0Var, au0 au0Var, boolean z, boolean z2) {
        boolean z3;
        e5 e5Var2;
        this.u = au0Var;
        this.v = dj0Var;
        boolean z4 = true;
        if (this.z != z) {
            this.z = z;
            z3 = true;
        } else {
            z3 = false;
        }
        if (!o20.e(this.A, e5Var)) {
            this.A = e5Var;
        } else {
            z4 = false;
        }
        if (z3 || (z4 && !z)) {
            im imVar = this.C;
            if (imVar != null) {
                E0(imVar);
            }
            this.C = null;
            G0();
        }
        this.w = z2;
        this.x = rlVar;
        this.y = je0Var;
        boolean H0 = H0();
        this.F = H0;
        zt0 zt0Var = this.B;
        if (zt0Var != null) {
            if (this.z) {
                e5Var2 = this.E;
            } else {
                e5Var2 = this.A;
            }
            zt0Var.Y0(e5Var2, rlVar, je0Var, dj0Var, au0Var, z2, H0);
        }
    }

    @Override // defpackage.ah0
    public final void P() {
        e5 e5Var;
        f5 f5Var = (f5) n20.p(this, kj0.a);
        if (!o20.e(f5Var, this.D)) {
            this.D = f5Var;
            this.E = null;
            im imVar = this.C;
            if (imVar != null) {
                E0(imVar);
            }
            this.C = null;
            G0();
            zt0 zt0Var = this.B;
            if (zt0Var != null) {
                au0 au0Var = this.u;
                dj0 dj0Var = this.v;
                if (this.z) {
                    e5Var = this.E;
                } else {
                    e5Var = this.A;
                }
                e5 e5Var2 = e5Var;
                zt0Var.Y0(e5Var2, this.x, this.y, dj0Var, au0Var, this.w, this.F);
            }
        }
    }

    @Override // defpackage.bd0
    public final boolean q0() {
        return false;
    }

    @Override // defpackage.bd0
    public final void t0() {
        e5 e5Var;
        this.F = H0();
        G0();
        if (this.B == null) {
            au0 au0Var = this.u;
            if (this.z) {
                e5Var = this.E;
            } else {
                e5Var = this.A;
            }
            e5 e5Var2 = e5Var;
            zt0 zt0Var = new zt0(e5Var2, this.x, this.y, this.v, au0Var, this.w, this.F);
            D0(zt0Var);
            this.B = zt0Var;
        }
    }

    @Override // defpackage.bd0
    public final void v0() {
        im imVar = this.C;
        if (imVar != null) {
            E0(imVar);
        }
    }

    @Override // defpackage.bd0
    public final void w0() {
        e5 e5Var;
        boolean H0 = H0();
        if (this.F != H0) {
            this.F = H0;
            au0 au0Var = this.u;
            dj0 dj0Var = this.v;
            boolean z = this.z;
            if (z) {
                e5Var = this.E;
            } else {
                e5Var = this.A;
            }
            e5 e5Var2 = e5Var;
            I0(e5Var2, this.x, this.y, dj0Var, au0Var, z, this.w);
        }
    }
}
