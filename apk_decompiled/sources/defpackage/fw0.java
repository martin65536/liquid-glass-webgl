package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fw0 extends m0 {
    public long a;
    public pc b;

    @Override // defpackage.m0
    public final boolean a(l0 l0Var) {
        ew0 ew0Var = (ew0) l0Var;
        if (this.a >= 0) {
            return false;
        }
        long j = ew0Var.m;
        if (j < ew0Var.n) {
            ew0Var.n = j;
        }
        this.a = j;
        return true;
    }

    @Override // defpackage.m0
    public final ij[] b(l0 l0Var) {
        long j = this.a;
        this.a = -1L;
        this.b = null;
        return ((ew0) l0Var).v(j);
    }
}
