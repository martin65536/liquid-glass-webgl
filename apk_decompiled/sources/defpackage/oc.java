package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class oc implements lv {
    public final /* synthetic */ int e;
    public final /* synthetic */ Object f;

    public /* synthetic */ oc(qf0 qf0Var, pf0 pf0Var) {
        this.e = 1;
        this.f = qf0Var;
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        int i = this.e;
        x31 x31Var = x31.a;
        Object obj4 = this.f;
        switch (i) {
            case 0:
                ((l) obj4).e((Throwable) obj);
                return x31Var;
            case 1:
                qf0 qf0Var = (qf0) obj4;
                qf0.i.set(qf0Var, null);
                qf0Var.g(null);
                return x31Var;
            default:
                ((gv0) obj4).b();
                return x31Var;
        }
    }

    public /* synthetic */ oc(int i, Object obj) {
        this.e = i;
        this.f = obj;
    }
}
