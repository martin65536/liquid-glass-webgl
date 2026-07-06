package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qg extends z30 implements vu {
    public final /* synthetic */ int f;
    public final /* synthetic */ af0 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ qg(af0 af0Var, int i) {
        super(0);
        this.f = i;
        this.g = af0Var;
    }

    @Override // defpackage.vu
    public final Object a() {
        int i = this.f;
        x31 x31Var = x31.a;
        af0 af0Var = this.g;
        switch (i) {
            case 0:
                af0Var.setValue(bd.e);
                return x31Var;
            case 1:
                af0Var.setValue(Boolean.valueOf(!((Boolean) af0Var.getValue()).booleanValue()));
                return x31Var;
            case 2:
                Boolean bool = (Boolean) af0Var.getValue();
                bool.booleanValue();
                return bool;
            default:
                Boolean bool2 = (Boolean) af0Var.getValue();
                bool2.booleanValue();
                return bool2;
        }
    }
}
