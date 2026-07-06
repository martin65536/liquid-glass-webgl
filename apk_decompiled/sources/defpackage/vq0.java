package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vq0 extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ wq0 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ vq0(wq0 wq0Var, int i) {
        super(1);
        this.f = i;
        this.g = wq0Var;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.f;
        wq0 wq0Var = this.g;
        switch (i) {
            case 0:
                return Double.valueOf(wq0Var.n.c(n30.h(((Number) obj).doubleValue(), wq0Var.e, wq0Var.f)));
            default:
                return Double.valueOf(n30.h(wq0Var.k.c(((Number) obj).doubleValue()), wq0Var.e, wq0Var.f));
        }
    }
}
