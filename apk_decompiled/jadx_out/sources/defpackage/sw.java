package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class sw extends sz0 implements kv {
    public /* synthetic */ Object i;
    public final /* synthetic */ y6 j;
    public final /* synthetic */ y6 k;
    public final /* synthetic */ y6 l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public sw(y6 y6Var, y6 y6Var2, y6 y6Var3, ij ijVar) {
        super(2, ijVar);
        this.j = y6Var;
        this.k = y6Var2;
        this.l = y6Var3;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        sw swVar = (sw) i((ij) obj2, (hk) obj);
        x31 x31Var = x31.a;
        swVar.k(x31Var);
        return x31Var;
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        sw swVar = new sw(this.j, this.k, this.l, ijVar);
        swVar.i = obj;
        return swVar;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        hk hkVar = (hk) this.i;
        o30.x(obj);
        f31.G(hkVar, null, new rw(this.j, null, 0), 3);
        f31.G(hkVar, null, new rw(this.k, null, 1), 3);
        f31.G(hkVar, null, new rw(this.l, null, 2), 3);
        return x31.a;
    }
}
