package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class lw0 extends gh {
    public Object b;
    public Object c;
    public we0 d;
    public we0 e;
    public jv0 f;
    public final l g;
    public final g2 h;

    public lw0() {
        super(2);
        this.g = new l(17, this);
        wa waVar = new wa(9, this);
        cx0.e(cx0.a);
        synchronized (cx0.c) {
            cx0.h = me.a0(cx0.h, waVar);
        }
        this.h = new g2(waVar);
    }

    @Override // defpackage.gh
    public final void d(jv0 jv0Var) {
        this.c = null;
        this.e = null;
    }

    @Override // defpackage.gh
    public final void e() {
        synchronized (this.a) {
            try {
                this.b = this.c;
                if (this.e == null) {
                    this.d = null;
                } else {
                    if (this.d == null) {
                        we0 we0Var = at0.a;
                        this.d = new we0();
                    }
                    we0 we0Var2 = this.d;
                    this.d = this.e;
                    this.e = we0Var2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // defpackage.gh
    public final void g() {
        this.h.a();
        this.c = null;
        this.e = null;
        synchronized (this.a) {
            this.f = null;
            this.b = null;
            this.d = null;
        }
    }

    @Override // defpackage.gh
    public final gv i(jv0 jv0Var) {
        jv0 jv0Var2 = this.f;
        if (jv0Var2 != null && !jv0Var2.equals(jv0Var)) {
            cn0.b("Requested a SingleSubscriptionSnapshotFlowManager to manage multiple subscriptions");
        }
        this.f = jv0Var;
        return this.g;
    }

    @Override // defpackage.gh
    public final void j(ed edVar) {
        this.f = null;
        this.c = null;
        this.e = null;
        e();
    }
}
