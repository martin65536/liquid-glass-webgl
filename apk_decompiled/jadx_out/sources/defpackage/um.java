package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class um extends i10 implements r40 {
    public f61 u;
    public v7 v;
    public f61 w;

    @Override // defpackage.i10
    public final void D0() {
        f61 f61Var = this.u;
        f61 f61Var2 = this.s;
        this.w = new tr(f61Var, f61Var2);
        this.t = f61Var2;
        d20.L(this, "androidx.compose.foundation.layout.ConsumedInsetsProvider", new h10(this, 0));
        m20.v(this);
    }

    @Override // defpackage.r40
    public final qc0 Y(ob0 ob0Var, kc0 kc0Var, long j) {
        int b;
        v7 v7Var = this.v;
        f61 f61Var = this.w;
        switch (v7Var.e) {
            case 27:
                b = f61Var.b(ob0Var);
                break;
            default:
                b = f61Var.d(ob0Var);
                break;
        }
        int i = b;
        fr frVar = fr.e;
        if (i == 0) {
            return ob0Var.e0(0, 0, frVar, new pb(6));
        }
        em0 v = kc0Var.v(si.a(j, 0, 0, i, i, 3));
        return ob0Var.e0(v.e, i, frVar, new k8(v, 1));
    }
}
