package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zk extends sz0 implements kv {
    public /* synthetic */ Object i;
    public final /* synthetic */ al j;
    public final /* synthetic */ float k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zk(al alVar, float f, ij ijVar) {
        super(2, ijVar);
        this.j = alVar;
        this.k = f;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        zk zkVar = (zk) i((ij) obj2, (hk) obj);
        x31 x31Var = x31.a;
        zkVar.k(x31Var);
        return x31Var;
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        zk zkVar = new zk(this.j, this.k, ijVar);
        zkVar.i = obj;
        return zkVar;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        hk hkVar = (hk) this.i;
        o30.x(obj);
        f31.G(hkVar, null, new qk(this.j, this.k, null, 1), 3);
        return x31.a;
    }
}
