package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class j50 extends w40 {
    public final /* synthetic */ n50 a;
    public final /* synthetic */ kv b;

    public j50(n50 n50Var, kv kvVar) {
        this.a = n50Var;
        this.b = kvVar;
    }

    @Override // defpackage.pc0
    public final qc0 e(rc0 rc0Var, List list, long j) {
        n50 n50Var = this.a;
        h50 h50Var = n50Var.l;
        h50Var.e = rc0Var.getLayoutDirection();
        h50Var.f = rc0Var.B();
        h50Var.g = rc0Var.y();
        boolean D = rc0Var.D();
        kv kvVar = this.b;
        if (!D && n50Var.e.l != null) {
            n50Var.i = 0;
            qc0 qc0Var = (qc0) kvVar.d(n50Var.m, new si(j));
            return new i50(qc0Var, n50Var, n50Var.i, qc0Var, 0);
        }
        n50Var.h = 0;
        qc0 qc0Var2 = (qc0) kvVar.d(h50Var, new si(j));
        return new i50(qc0Var2, n50Var, n50Var.h, qc0Var2, 1);
    }
}
