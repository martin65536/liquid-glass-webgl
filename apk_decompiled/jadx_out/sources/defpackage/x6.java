package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class x6 extends sz0 implements gv {
    public final /* synthetic */ y6 i;
    public final /* synthetic */ Object j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public x6(y6 y6Var, Object obj, ij ijVar) {
        super(1, ijVar);
        this.i = y6Var;
        this.j = obj;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        x6 x6Var = new x6(this.i, this.j, (ij) obj);
        x31 x31Var = x31.a;
        x6Var.k(x31Var);
        return x31Var;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        o30.x(obj);
        y6 y6Var = this.i;
        y6.b(y6Var);
        Object a = y6.a(y6Var, this.j);
        y6Var.c.f.setValue(a);
        y6Var.e.setValue(a);
        return x31.a;
    }
}
