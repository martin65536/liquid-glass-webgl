package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class nj extends sz0 implements kv {
    public /* synthetic */ Object i;
    public final /* synthetic */ y6 j;
    public final /* synthetic */ float k;
    public final /* synthetic */ y6 l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public nj(y6 y6Var, float f, y6 y6Var2, ij ijVar) {
        super(2, ijVar);
        this.j = y6Var;
        this.k = f;
        this.l = y6Var2;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        nj njVar = (nj) i((ij) obj2, (hk) obj);
        x31 x31Var = x31.a;
        njVar.k(x31Var);
        return x31Var;
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        nj njVar = new nj(this.j, this.k, this.l, ijVar);
        njVar.i = obj;
        return njVar;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        hk hkVar = (hk) this.i;
        o30.x(obj);
        y6 y6Var = this.j;
        float f = this.k;
        f31.G(hkVar, null, new mj(y6Var, f, null, 0), 3);
        f31.G(hkVar, null, new mj(this.l, f, null, 1), 3);
        return x31.a;
    }
}
