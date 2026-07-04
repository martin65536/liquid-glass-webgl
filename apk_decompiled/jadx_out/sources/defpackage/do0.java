package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class do0 {
    public final s70 a;

    public do0(vu vuVar) {
        this.a = new s70(vuVar);
    }

    public abstract eo0 a(Object obj);

    public i41 b() {
        return this.a;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final i41 c(eo0 eo0Var, i41 i41Var) {
        Object obj = eo0Var.e;
        boolean z = eo0Var.d;
        dq dqVar = null;
        if (i41Var instanceof dq) {
            if (z) {
                dqVar = (dq) i41Var;
                dqVar.a.setValue(eo0Var.a());
            }
        } else if ((i41Var instanceof ry0) && ((eo0Var.b || obj != null) && !z)) {
            ry0 ry0Var = (ry0) i41Var;
            if (o20.e(eo0Var.a(), ry0Var.a)) {
                dqVar = ry0Var;
            }
        }
        if (dqVar == null) {
            if (z) {
                ix0 ix0Var = eo0Var.c;
                if (ix0Var == null) {
                    ix0Var = dt0.g;
                }
                return new dq(new ik0(obj, ix0Var));
            }
            return new ry0(eo0Var.a());
        }
        return dqVar;
    }
}
