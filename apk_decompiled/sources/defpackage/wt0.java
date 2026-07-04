package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class wt0 implements vu {
    public final /* synthetic */ int e;
    public final /* synthetic */ zt0 f;

    public /* synthetic */ wt0(zt0 zt0Var, int i) {
        this.e = i;
        this.f = zt0Var;
    }

    @Override // defpackage.vu
    public final Object a() {
        int i = this.e;
        zt0 zt0Var = this.f;
        switch (i) {
            case 0:
                return Boolean.valueOf(zt0Var.r);
            default:
                pt ptVar = zt0Var.T;
                if (!ptVar.e.r) {
                    return null;
                }
                ot I0 = ptVar.I0();
                int ordinal = I0.ordinal();
                if (ordinal != 0 && ordinal != 1 && ordinal != 2) {
                    if (ordinal == 3) {
                        return null;
                    }
                    v7.k();
                    return null;
                }
                if (I0.a()) {
                    return ptVar.G0(null);
                }
                pt f = ((lt) ((b4) k81.F(ptVar)).getFocusOwner()).f();
                if (f == null) {
                    return null;
                }
                return f.G0(k81.D(ptVar));
        }
    }
}
