package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class lw implements gv {
    public final /* synthetic */ int e;
    public final /* synthetic */ gv f;

    public /* synthetic */ lw(gv gvVar, int i) {
        this.e = i;
        this.f = gvVar;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.e;
        gv gvVar = this.f;
        switch (i) {
            case 0:
                w21 w21Var = (w21) obj;
                if (w21Var instanceof kw) {
                    Boolean bool = (Boolean) gvVar.e(((kw) w21Var).s);
                    bool.getClass();
                    return bool;
                }
                v7.o("Node is not a GestureNode instance");
                return null;
            case 1:
                ww0 ww0Var = (ww0) gvVar.e((ax0) obj);
                synchronized (cx0.c) {
                    cx0.d = cx0.d.e(ww0Var.g());
                }
                return ww0Var;
            default:
                Long l = (Long) obj;
                l.getClass();
                return gvVar.e(l);
        }
    }
}
