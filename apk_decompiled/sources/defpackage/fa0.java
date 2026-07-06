package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fa0 extends sz0 implements kv {
    public /* synthetic */ boolean i;
    public final /* synthetic */ al j;
    public final /* synthetic */ ek0 k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public fa0(al alVar, ek0 ek0Var, ij ijVar) {
        super(2, ijVar);
        this.j = alVar;
        this.k = ek0Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        fa0 fa0Var = (fa0) i((ij) obj2, bool);
        x31 x31Var = x31.a;
        fa0Var.k(x31Var);
        return x31Var;
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        fa0 fa0Var = new fa0(this.j, this.k, ijVar);
        fa0Var.i = ((Boolean) obj).booleanValue();
        return fa0Var;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        float f;
        boolean z = this.i;
        o30.x(obj);
        if (z) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        ek0 ek0Var = this.k;
        if (f != ek0Var.g()) {
            ek0Var.h(f);
            this.j.a(f);
        }
        return x31.a;
    }
}
