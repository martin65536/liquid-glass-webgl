package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class h10 implements gv {
    public final /* synthetic */ int e;
    public final /* synthetic */ i10 f;

    public /* synthetic */ h10(i10 i10Var, int i) {
        this.e = i;
        this.f = i10Var;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.e;
        i10 i10Var = this.f;
        w21 w21Var = (w21) obj;
        switch (i) {
            case 0:
                w21Var.getClass();
                i10 i10Var2 = (i10) w21Var;
                f61 f61Var = i10Var.t;
                if (!o20.e(i10Var2.s, f61Var)) {
                    i10Var2.s = f61Var;
                    i10Var2.D0();
                }
                return v21.f;
            default:
                w21Var.getClass();
                i10Var.s = ((i10) w21Var).t;
                return Boolean.FALSE;
        }
    }
}
