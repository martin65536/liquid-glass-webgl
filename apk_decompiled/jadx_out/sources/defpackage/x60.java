package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class x60 extends bd0 implements qu0 {
    public vu s;
    public s60 t;
    public dj0 u;
    public boolean v;
    public et0 w;
    public final u60 x = new u60(this, 0);
    public u60 y;

    public x60(vu vuVar, s60 s60Var, dj0 dj0Var, boolean z) {
        this.s = vuVar;
        this.t = s60Var;
        this.u = dj0Var;
        this.v = z;
        D0();
    }

    public final void D0() {
        u60 u60Var;
        this.w = new et0(new v60(this, 0), new v60(this, 1));
        if (this.v) {
            u60Var = new u60(this, 1);
        } else {
            u60Var = null;
        }
        this.y = u60Var;
    }

    @Override // defpackage.qu0
    public final void f0(bv0 bv0Var) {
        t30[] t30VarArr = zu0.a;
        av0 av0Var = wu0.m;
        t30[] t30VarArr2 = zu0.a;
        t30 t30Var = t30VarArr2[6];
        bv0Var.a(av0Var, Boolean.TRUE);
        bv0Var.a(wu0.J, this.x);
        dj0 dj0Var = this.u;
        et0 et0Var = this.w;
        if (dj0Var == dj0.e) {
            if (et0Var != null) {
                av0 av0Var2 = wu0.v;
                t30 t30Var2 = t30VarArr2[13];
                bv0Var.a(av0Var2, et0Var);
            } else {
                o20.G("scrollAxisRange");
                throw null;
            }
        } else if (et0Var != null) {
            av0 av0Var3 = wu0.u;
            t30 t30Var3 = t30VarArr2[12];
            bv0Var.a(av0Var3, et0Var);
        } else {
            o20.G("scrollAxisRange");
            throw null;
        }
        u60 u60Var = this.y;
        if (u60Var != null) {
            bv0Var.a(mu0.f, new n0(null, u60Var));
        }
        bv0Var.a(mu0.B, new n0(null, new q2(22, new v60(this, 2))));
        s60 s60Var = this.t;
        s60Var.getClass();
        le leVar = new le(((Number) s60Var.a.getValue()).intValue(), 1);
        av0 av0Var4 = wu0.f;
        t30 t30Var4 = t30VarArr2[24];
        bv0Var.a(av0Var4, leVar);
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

    @Override // defpackage.qu0
    public final /* synthetic */ boolean u() {
        return true;
    }
}
