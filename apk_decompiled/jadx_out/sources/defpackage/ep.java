package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class ep implements gv {
    public final /* synthetic */ int e;
    public final /* synthetic */ af0 f;

    public /* synthetic */ ep(af0 af0Var, int i) {
        this.e = i;
        this.f = af0Var;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.e;
        x31 x31Var = x31.a;
        af0 af0Var = this.f;
        switch (i) {
            case 0:
                ((gv) af0Var.getValue()).e((ch0) obj);
                return x31Var;
            default:
                Float f = (Float) obj;
                f.getClass();
                ((gv) af0Var.getValue()).e(f);
                return x31Var;
        }
    }
}
