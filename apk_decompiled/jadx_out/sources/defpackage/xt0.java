package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xt0 extends sz0 implements kv {
    public /* synthetic */ Object i;
    public final /* synthetic */ long j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public xt0(long j, ij ijVar) {
        super(2, ijVar);
        this.j = j;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        xt0 xt0Var = (xt0) i((ij) obj2, (fu0) obj);
        x31 x31Var = x31.a;
        xt0Var.k(x31Var);
        return x31Var;
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        xt0 xt0Var = new xt0(this.j, ijVar);
        xt0Var.i = obj;
        return xt0Var;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        o30.x(obj);
        hu0 hu0Var = ((fu0) this.i).a;
        hu0Var.c(hu0Var.k, this.j, 1);
        return x31.a;
    }
}
