package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class u60 implements gv {
    public final /* synthetic */ int e;
    public final /* synthetic */ x60 f;

    public /* synthetic */ u60(x60 x60Var, int i) {
        this.e = i;
        this.f = x60Var;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.e;
        x60 x60Var = this.f;
        switch (i) {
            case 0:
                c70 c70Var = (c70) x60Var.s.a();
                int c = c70Var.c();
                int i2 = 0;
                while (true) {
                    if (i2 < c) {
                        if (!c70Var.d(i2).equals(obj)) {
                            i2++;
                        }
                    } else {
                        i2 = -1;
                    }
                }
                return Integer.valueOf(i2);
            default:
                int intValue = ((Integer) obj).intValue();
                c70 c70Var2 = (c70) x60Var.s.a();
                if (intValue < 0 || intValue >= c70Var2.c()) {
                    t00.a("Can't scroll to index " + intValue + ", it is out of bounds [0, " + c70Var2.c() + ')');
                }
                f31.G(x60Var.p0(), null, new w60(x60Var, intValue, null), 3);
                return Boolean.TRUE;
        }
    }
}
