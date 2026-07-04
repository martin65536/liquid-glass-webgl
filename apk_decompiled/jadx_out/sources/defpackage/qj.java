package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qj extends sz0 implements kv {
    public /* synthetic */ Object i;
    public final /* synthetic */ y6 j;
    public final /* synthetic */ float k;
    public final /* synthetic */ float l;
    public final /* synthetic */ y6 m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public qj(y6 y6Var, float f, float f2, y6 y6Var2, ij ijVar) {
        super(2, ijVar);
        this.j = y6Var;
        this.k = f;
        this.l = f2;
        this.m = y6Var2;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        qj qjVar = (qj) i((ij) obj2, (hk) obj);
        x31 x31Var = x31.a;
        qjVar.k(x31Var);
        return x31Var;
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        qj qjVar = new qj(this.j, this.k, this.l, this.m, ijVar);
        qjVar.i = obj;
        return qjVar;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        hk hkVar = (hk) this.i;
        o30.x(obj);
        y6 y6Var = this.j;
        float f = this.k;
        f31.G(hkVar, null, new pj(y6Var, f, this.l, null, 0), 3);
        f31.G(hkVar, null, new mj(this.m, f, null, 2), 3);
        return x31.a;
    }
}
