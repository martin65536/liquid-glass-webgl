package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class i30 extends pc {
    public final l30 o;

    public i30(ij ijVar, l30 l30Var) {
        super(1, ijVar);
        this.o = l30Var;
    }

    @Override // defpackage.pc
    public final String C() {
        return "AwaitContinuation";
    }

    @Override // defpackage.pc
    public final Throwable n(l30 l30Var) {
        Throwable e;
        Object Q = this.o.Q();
        if ((Q instanceof k30) && (e = ((k30) Q).e()) != null) {
            return e;
        }
        if (Q instanceof qf) {
            return ((qf) Q).a;
        }
        return l30Var.m();
    }
}
