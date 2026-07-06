package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class i20 extends sz0 implements kv {
    public final /* synthetic */ int i;
    public /* synthetic */ Object j;
    public final /* synthetic */ Object k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ i20(Object obj, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = obj;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                ((i20) i(ijVar, hkVar)).k(x31Var);
                return x31Var;
            case 1:
                ((i20) i(ijVar, hkVar)).k(x31Var);
                return x31Var;
            case 2:
                ((i20) i(ijVar, hkVar)).k(x31Var);
                return x31Var;
            default:
                ((i20) i(ijVar, hkVar)).k(x31Var);
                return x31Var;
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        Object obj2 = this.k;
        switch (i) {
            case 0:
                i20 i20Var = new i20((k20) obj2, ijVar, 0);
                i20Var.j = obj;
                return i20Var;
            case 1:
                i20 i20Var2 = new i20((k20) obj2, ijVar, 1);
                i20Var2.j = obj;
                return i20Var2;
            case 2:
                i20 i20Var3 = new i20((k20) obj2, ijVar, 2);
                i20Var3.j = obj;
                return i20Var3;
            default:
                i20 i20Var4 = new i20((al) obj2, ijVar, 3);
                i20Var4.j = obj;
                return i20Var4;
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        int i2 = 2;
        int i3 = 1;
        x31 x31Var = x31.a;
        Object obj2 = this.k;
        ij ijVar = null;
        int i4 = 3;
        hk hkVar = (hk) this.j;
        switch (i) {
            case 0:
                o30.x(obj);
                k20 k20Var = (k20) obj2;
                f31.G(hkVar, null, new h20(k20Var, ijVar, 0), 3);
                f31.G(hkVar, null, new h20(k20Var, ijVar, i3), 3);
                return x31Var;
            case 1:
                o30.x(obj);
                k20 k20Var2 = (k20) obj2;
                f31.G(hkVar, null, new h20(k20Var2, ijVar, i2), 3);
                f31.G(hkVar, null, new h20(k20Var2, ijVar, i4), 3);
                return x31Var;
            case 2:
                o30.x(obj);
                k20 k20Var3 = (k20) obj2;
                f31.G(hkVar, null, new h20(k20Var3, ijVar, 4), 3);
                f31.G(hkVar, null, new h20(k20Var3, ijVar, 5), 3);
                return x31Var;
            default:
                o30.x(obj);
                al alVar = (al) obj2;
                f31.G(hkVar, null, new rk(alVar, ijVar, i3), 3);
                f31.G(hkVar, null, new rk(alVar, ijVar, i2), 3);
                f31.G(hkVar, null, new rk(alVar, ijVar, i4), 3);
                return x31Var;
        }
    }
}
