package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k41 extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ l41 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ k41(l41 l41Var, int i) {
        super(1);
        this.f = i;
        this.g = l41Var;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.f;
        x31 x31Var = x31.a;
        l41 l41Var = this.g;
        switch (i) {
            case 0:
                l41Var.d = true;
                l41Var.f.a();
                return x31Var;
            default:
                up upVar = (up) obj;
                sx sxVar = l41Var.b;
                float f = l41Var.k;
                float f2 = l41Var.l;
                r7 J = upVar.J();
                long v = J.v();
                J.q().h();
                try {
                    ((j2) J.f).o(f, f2, 0L);
                    sxVar.a(upVar);
                    return x31Var;
                } finally {
                    J.q().f();
                    J.G(v);
                }
        }
    }
}
