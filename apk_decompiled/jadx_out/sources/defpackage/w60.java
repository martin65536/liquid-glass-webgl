package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class w60 extends sz0 implements kv {
    public int i;
    public final /* synthetic */ x60 j;
    public final /* synthetic */ int k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public w60(x60 x60Var, int i, ij ijVar) {
        super(2, ijVar);
        this.j = x60Var;
        this.k = i;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((w60) i((ij) obj2, (hk) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        return new w60(this.j, this.k, ijVar);
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        x31 x31Var = x31.a;
        if (i != 0) {
            if (i == 1) {
                o30.x(obj);
                return x31Var;
            }
            v7.o("call to 'resume' before 'invoke' with coroutine");
            return null;
        }
        o30.x(obj);
        s60 s60Var = this.j.t;
        this.i = 1;
        m70 m70Var = s60Var.b;
        c4 c4Var = m70.x;
        m70Var.getClass();
        Object d = m70Var.d(gf0.e, new m8(m70Var, this.k, (ij) null), this);
        ik ikVar = ik.e;
        if (d != ikVar) {
            d = x31Var;
        }
        if (d != ikVar) {
            d = x31Var;
        }
        if (d == ikVar) {
            return ikVar;
        }
        return x31Var;
    }
}
