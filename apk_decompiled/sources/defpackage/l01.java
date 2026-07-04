package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l01 extends sz0 implements kv {
    public final /* synthetic */ int i;
    public final /* synthetic */ mn0 j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ l01(mn0 mn0Var, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.j = mn0Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                ((l01) i(ijVar, hkVar)).k(x31Var);
                return x31Var;
            case 1:
                ((l01) i(ijVar, hkVar)).k(x31Var);
                return x31Var;
            case 2:
                ((l01) i(ijVar, hkVar)).k(x31Var);
                return x31Var;
            case 3:
                ((l01) i(ijVar, hkVar)).k(x31Var);
                return x31Var;
            case 4:
                ((l01) i(ijVar, hkVar)).k(x31Var);
                return x31Var;
            default:
                ((l01) i(ijVar, hkVar)).k(x31Var);
                return x31Var;
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        switch (this.i) {
            case 0:
                return new l01(this.j, ijVar, 0);
            case 1:
                return new l01(this.j, ijVar, 1);
            case 2:
                return new l01(this.j, ijVar, 2);
            case 3:
                return new l01(this.j, ijVar, 3);
            case 4:
                return new l01(this.j, ijVar, 4);
            default:
                return new l01(this.j, ijVar, 5);
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        x31 x31Var = x31.a;
        mn0 mn0Var = this.j;
        switch (i) {
            case 0:
                o30.x(obj);
                mn0Var.r();
                return x31Var;
            case 1:
                o30.x(obj);
                qf0 qf0Var = mn0Var.f;
                if (qf0Var.d()) {
                    qf0Var.g(null);
                }
                return x31Var;
            case 2:
                o30.x(obj);
                mn0Var.r();
                return x31Var;
            case 3:
                o30.x(obj);
                mn0Var.r();
                return x31Var;
            case 4:
                o30.x(obj);
                qf0 qf0Var2 = mn0Var.f;
                if (qf0Var2.d()) {
                    qf0Var2.g(null);
                }
                return x31Var;
            default:
                o30.x(obj);
                mn0Var.r();
                return x31Var;
        }
    }
}
