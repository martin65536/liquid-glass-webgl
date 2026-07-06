package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gz0 extends z30 implements kv {
    public final /* synthetic */ int f;
    public final /* synthetic */ hz0 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ gz0(hz0 hz0Var, int i) {
        super(2);
        this.f = i;
        this.g = hz0Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.f;
        x31 x31Var = x31.a;
        hz0 hz0Var = this.g;
        switch (i) {
            case 0:
                hz0Var.a().f = (th) obj2;
                return x31Var;
            case 1:
                z40 z40Var = (z40) obj;
                j50 j50Var = new j50(hz0Var.a(), (kv) obj2);
                if (!o20.e(z40Var.z, j50Var)) {
                    z40Var.z = j50Var;
                    z40Var.B();
                }
                return x31Var;
            default:
                z40 z40Var2 = (z40) obj;
                kz0 kz0Var = hz0Var.a;
                n50 n50Var = z40Var2.J;
                if (n50Var == null) {
                    n50Var = new n50(z40Var2, kz0Var);
                    z40Var2.J = n50Var;
                }
                hz0Var.b = n50Var;
                hz0Var.a().h();
                n50 a = hz0Var.a();
                if (a.g != kz0Var) {
                    a.g = kz0Var;
                    a.i(false);
                    z40.T(a.e, false, 7);
                }
                return x31Var;
        }
    }
}
