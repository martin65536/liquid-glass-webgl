package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rj extends sz0 implements lv {
    public /* synthetic */ float i;
    public final /* synthetic */ y6 j;
    public final /* synthetic */ hk k;
    public final /* synthetic */ y6 l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public rj(y6 y6Var, hk hkVar, y6 y6Var2, ij ijVar) {
        super(3, ijVar);
        this.j = y6Var;
        this.k = hkVar;
        this.l = y6Var2;
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        float floatValue = ((Number) obj2).floatValue();
        hk hkVar = this.k;
        y6 y6Var = this.l;
        rj rjVar = new rj(this.j, hkVar, y6Var, (ij) obj3);
        rjVar.i = floatValue;
        x31 x31Var = x31.a;
        rjVar.k(x31Var);
        return x31Var;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        float f;
        float f2 = this.i;
        o30.x(obj);
        if (f2 < 0.0f || (f2 <= 0.0f && ((Number) this.j.d()).floatValue() < 0.5f)) {
            f = 0.0f;
        } else {
            f = 1.0f;
        }
        f31.G(this.k, null, new qj(this.j, f, f2, this.l, null), 3);
        return x31.a;
    }
}
