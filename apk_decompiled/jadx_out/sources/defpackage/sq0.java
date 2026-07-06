package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class sq0 implements bo {
    public final /* synthetic */ int e;
    public final /* synthetic */ wq0 f;

    public /* synthetic */ sq0(wq0 wq0Var, int i) {
        this.e = i;
        this.f = wq0Var;
    }

    @Override // defpackage.bo
    public final double c(double d) {
        int i = this.e;
        wq0 wq0Var = this.f;
        switch (i) {
            case 0:
                return n30.h(wq0Var.k.c(d), wq0Var.e, wq0Var.f);
            default:
                return wq0Var.n.c(n30.h(d, wq0Var.e, wq0Var.f));
        }
    }
}
