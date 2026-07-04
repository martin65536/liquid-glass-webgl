package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class q extends l30 implements ij, hk {
    public final yj i;

    public q(yj yjVar, boolean z) {
        super(z);
        T((d30) yjVar.j(x1.L));
        this.i = yjVar.i(this);
    }

    @Override // defpackage.l30
    public final String G() {
        return getClass().getSimpleName().concat(" was cancelled");
    }

    @Override // defpackage.l30
    public final void S(rf rfVar) {
        o4.K(this.i, rfVar);
    }

    @Override // defpackage.l30
    public final void a0(Object obj) {
        boolean z;
        if (obj instanceof qf) {
            qf qfVar = (qf) obj;
            Throwable th = qfVar.a;
            if (qf.b.get(qfVar) != 0) {
                z = true;
            } else {
                z = false;
            }
            m0(th, z);
            return;
        }
        n0(obj);
    }

    @Override // defpackage.hk
    public final yj g() {
        return this.i;
    }

    public final void o0(kk kkVar, q qVar, kv kvVar) {
        Object d;
        int ordinal = kkVar.ordinal();
        x31 x31Var = x31.a;
        if (ordinal != 0) {
            if (ordinal != 1) {
                if (ordinal != 2) {
                    if (ordinal == 3) {
                        try {
                            yj yjVar = this.i;
                            Object Q = k81.Q(yjVar, null);
                            try {
                                if (!(kvVar instanceof s9)) {
                                    d = t20.U(kvVar, qVar, this);
                                } else {
                                    f31.n(2, kvVar);
                                    d = kvVar.d(qVar, this);
                                }
                                k81.G(yjVar, Q);
                                if (d != ik.e) {
                                    u(d);
                                    return;
                                }
                                return;
                            } catch (Throwable th) {
                                k81.G(yjVar, Q);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            return;
                        }
                    }
                    v7.k();
                    return;
                }
                kvVar.getClass();
                t20.w(t20.o(qVar, this, kvVar)).u(x31Var);
                return;
            }
            return;
        }
        try {
            n20.N(t20.w(t20.o(qVar, this, kvVar)), x31Var);
        } finally {
            u(new jq0(th2));
        }
    }

    @Override // defpackage.ij
    public final yj r() {
        return this.i;
    }

    @Override // defpackage.ij
    public final void u(Object obj) {
        Throwable a = kq0.a(obj);
        if (a != null) {
            obj = new qf(a, false);
        }
        Object W = W(obj);
        if (W == o20.h) {
            return;
        }
        B(W);
    }

    public void n0(Object obj) {
    }

    public void m0(Throwable th, boolean z) {
    }
}
