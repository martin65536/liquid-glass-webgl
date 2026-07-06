package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kb extends sz0 implements kv {
    public /* synthetic */ Object i;
    public final /* synthetic */ lb j;
    public final /* synthetic */ ng0 k;
    public final /* synthetic */ u3 l;
    public final /* synthetic */ y8 m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public kb(lb lbVar, ng0 ng0Var, u3 u3Var, y8 y8Var, ij ijVar) {
        super(2, ijVar);
        this.j = lbVar;
        this.k = ng0Var;
        this.l = u3Var;
        this.m = y8Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((kb) i((ij) obj2, (hk) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        kb kbVar = new kb(this.j, this.k, this.l, this.m, ijVar);
        kbVar.i = obj;
        return kbVar;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        o30.x(obj);
        hk hkVar = (hk) this.i;
        lb lbVar = this.j;
        ij ijVar = null;
        f31.G(hkVar, null, new f(lbVar, this.k, this.l, ijVar, 1), 3);
        return f31.G(hkVar, null, new d(lbVar, this.m, ijVar, 2), 3);
    }
}
