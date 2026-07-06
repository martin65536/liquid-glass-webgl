package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tt extends jm implements qu0, ww, ai, ah0, w21 {
    public static final rt A = new rt(0);
    public je0 u;
    public final e v;
    public ct w;
    public l60 x;
    public ng0 y;
    public final pt z;

    /* JADX WARN: Type inference failed for: r0v0, types: [uv, kv] */
    public tt(je0 je0Var, e eVar) {
        this.u = je0Var;
        this.v = eVar;
        pt ptVar = new pt(0, new uv(2, this, tt.class, "onFocusStateChange", "onFocusStateChange(Landroidx/compose/ui/focus/FocusState;Landroidx/compose/ui/focus/FocusState;)V", 0, 0), 10);
        D0(ptVar);
        this.z = ptVar;
    }

    @Override // defpackage.ww
    public final void E(ng0 ng0Var) {
        this.y = ng0Var;
        if (this.z.I0().a()) {
            boolean z = ng0Var.P0().r;
            rt rtVar = ut.s;
            if (z) {
                ng0 ng0Var2 = this.y;
                if (ng0Var2 != null && ng0Var2.P0().r && this.r) {
                    d20.p(this, rtVar);
                    return;
                }
                return;
            }
            if (this.r) {
                d20.p(this, rtVar);
            }
        }
    }

    public final void G0(je0 je0Var, g20 g20Var) {
        un unVar;
        if (this.r) {
            d30 d30Var = (d30) ((hj) p0()).e.j(x1.L);
            ij ijVar = null;
            if (d30Var != null) {
                unVar = d30Var.p(new c(8, je0Var, g20Var));
            } else {
                unVar = null;
            }
            f31.G(p0(), null, new f(je0Var, g20Var, unVar, ijVar, 8), 3);
            return;
        }
        je0Var.b(g20Var);
    }

    public final void H0(je0 je0Var) {
        ct ctVar;
        if (!o20.e(this.u, je0Var)) {
            je0 je0Var2 = this.u;
            if (je0Var2 != null && (ctVar = this.w) != null) {
                je0Var2.b(new dt(ctVar));
            }
            this.w = null;
            this.u = je0Var;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [ep0, java.lang.Object] */
    @Override // defpackage.ah0
    public final void P() {
        ?? obj = new Object();
        o30.u(this, new f9(3, obj, this));
        l60 l60Var = (l60) obj.e;
        if (this.z.I0().a()) {
            l60 l60Var2 = this.x;
            if (l60Var2 != null) {
                l60Var2.b();
            }
            if (l60Var != null) {
                l60Var.a();
            } else {
                l60Var = null;
            }
            this.x = l60Var;
        }
    }

    @Override // defpackage.qu0
    public final void f0(bv0 bv0Var) {
        boolean a = this.z.I0().a();
        t30[] t30VarArr = zu0.a;
        av0 av0Var = wu0.l;
        t30 t30Var = zu0.a[4];
        bv0Var.a(av0Var, Boolean.valueOf(a));
        bv0Var.a(mu0.v, new n0(null, new s3(0, this, tt.class, "requestFocus", "requestFocus()Z", 0, 0, 2)));
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

    @Override // defpackage.bd0
    public final void x0() {
        l60 l60Var = this.x;
        if (l60Var != null) {
            l60Var.b();
        }
        this.x = null;
    }

    @Override // defpackage.w21
    public final Object z() {
        return A;
    }
}
