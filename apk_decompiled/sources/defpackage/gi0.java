package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gi0 extends yi0 {
    public static final gi0 d;
    public static final gi0 e;
    public static final gi0 f;
    public static final gi0 g;
    public final /* synthetic */ int c;

    static {
        int i = 1;
        d = new gi0(i, 2, 0);
        int i2 = 1;
        e = new gi0(i2, i2, 1);
        f = new gi0(i, 2, 2);
        int i3 = 1;
        g = new gi0(i3, i3, 3);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ gi0(int i, int i2, int i3) {
        super(i, i2);
        this.c = i3;
    }

    @Override // defpackage.yi0
    public final void a(aj0 aj0Var, t7 t7Var, uw0 uw0Var, mp0 mp0Var, zi0 zi0Var) {
        switch (this.c) {
            case 0:
                Object a = ((vu) aj0Var.b(0)).a();
                wv wvVar = (wv) aj0Var.b(1);
                int a2 = aj0Var.a(0);
                wvVar.getClass();
                uw0Var.U(uw0Var.c(wvVar), a);
                t7Var.d(a2, a);
                t7Var.b(a);
                return;
            case 1:
                wv wvVar2 = (wv) aj0Var.b(0);
                int a3 = aj0Var.a(0);
                t7Var.j();
                wvVar2.getClass();
                t7Var.a(a3, uw0Var.D(uw0Var.c(wvVar2)));
                return;
            case 2:
                Object b = aj0Var.b(0);
                wv wvVar3 = (wv) aj0Var.b(1);
                int a4 = aj0Var.a(0);
                if (b instanceof gw) {
                    gw gwVar = (gw) b;
                    mp0Var.e.b(gwVar);
                    mp0Var.d.a(gwVar);
                }
                Object K = uw0Var.K(uw0Var.c(wvVar3), a4, b);
                if (K instanceof gw) {
                    mp0Var.e((gw) K);
                    return;
                } else {
                    if (K instanceof mo0) {
                        ((mo0) K).c();
                        return;
                    }
                    return;
                }
            default:
                Object b2 = aj0Var.b(0);
                int a5 = aj0Var.a(0);
                if (b2 instanceof gw) {
                    gw gwVar2 = (gw) b2;
                    mp0Var.e.b(gwVar2);
                    mp0Var.d.a(gwVar2);
                }
                Object K2 = uw0Var.K(uw0Var.t, a5, b2);
                if (K2 instanceof gw) {
                    mp0Var.e((gw) K2);
                    return;
                } else {
                    if (K2 instanceof mo0) {
                        ((mo0) K2).c();
                        return;
                    }
                    return;
                }
        }
    }

    @Override // defpackage.yi0
    public wv b(aj0 aj0Var) {
        switch (this.c) {
            case 0:
                return (wv) aj0Var.b(1);
            case 1:
                return (wv) aj0Var.b(0);
            default:
                return super.b(aj0Var);
        }
    }
}
