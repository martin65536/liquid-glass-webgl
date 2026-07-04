package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pf0 implements nc, y51 {
    public final pc e;
    public final /* synthetic */ qf0 f;

    public pf0(qf0 qf0Var, pc pcVar) {
        this.f = qf0Var;
        this.e = pcVar;
    }

    @Override // defpackage.y51
    public final void a(ku0 ku0Var, int i) {
        this.e.a(ku0Var, i);
    }

    @Override // defpackage.ij
    public final yj r() {
        return this.e.i;
    }

    @Override // defpackage.ij
    public final void u(Object obj) {
        this.e.u(obj);
    }

    @Override // defpackage.nc
    public final wq v(Object obj, lv lvVar) {
        qf0 qf0Var = this.f;
        oc ocVar = new oc(qf0Var, this);
        wq J = this.e.J((x31) obj, ocVar);
        if (J != null) {
            qf0.i.set(qf0Var, null);
        }
        return J;
    }

    @Override // defpackage.nc
    public final boolean x(Throwable th) {
        return this.e.x(th);
    }

    @Override // defpackage.nc
    public final void z(Object obj) {
        this.e.z(obj);
    }
}
