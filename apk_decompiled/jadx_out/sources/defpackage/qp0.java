package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qp0 implements hk, np0 {
    public static final tc h = new tc(0);
    public final yj e;
    public final qp0 f = this;
    public volatile yj g;

    public qp0(yj yjVar) {
        this.e = yjVar;
    }

    public final void a() {
        synchronized (this.f) {
            try {
                yj yjVar = this.g;
                if (yjVar == null) {
                    this.g = h;
                } else {
                    tu tuVar = new tu(0);
                    d30 d30Var = (d30) yjVar.j(x1.L);
                    if (d30Var != null) {
                        d30Var.a(tuVar);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // defpackage.np0
    public final void f() {
        a();
    }

    @Override // defpackage.hk
    public final yj g() {
        yj yjVar;
        yj yjVar2;
        yj yjVar3 = this.g;
        if (yjVar3 == null || yjVar3 == h) {
            wh whVar = (wh) this.e.j(wh.f);
            if (whVar != null) {
                yjVar = new pp0(whVar, this);
            } else {
                yjVar = cr.e;
            }
            synchronized (this.f) {
                try {
                    yj yjVar4 = this.g;
                    if (yjVar4 == null) {
                        yj yjVar5 = this.e;
                        yjVar2 = yjVar5.i(new f30((d30) yjVar5.j(x1.L))).i(cr.e).i(yjVar);
                    } else if (yjVar4 == h) {
                        yj yjVar6 = this.e;
                        f30 f30Var = new f30((d30) yjVar6.j(x1.L));
                        f30Var.D(new tu(0));
                        yjVar2 = yjVar6.i(f30Var).i(cr.e).i(yjVar);
                    } else {
                        yjVar2 = yjVar4;
                    }
                    this.g = yjVar2;
                } catch (Throwable th) {
                    throw th;
                }
            }
            yjVar3 = yjVar2;
        }
        yjVar3.getClass();
        return yjVar3;
    }

    @Override // defpackage.np0
    public final void k() {
        a();
    }

    @Override // defpackage.np0
    public final void d() {
    }
}
