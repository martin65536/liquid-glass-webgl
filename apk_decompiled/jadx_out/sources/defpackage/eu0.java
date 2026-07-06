package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class eu0 extends sz0 implements kv {
    public hu0 i;
    public dp0 j;
    public long k;
    public int l;
    public /* synthetic */ Object m;
    public final /* synthetic */ hu0 n;
    public final /* synthetic */ dp0 o;
    public final /* synthetic */ long p;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public eu0(hu0 hu0Var, dp0 dp0Var, long j, ij ijVar) {
        super(2, ijVar);
        this.n = hu0Var;
        this.o = dp0Var;
        this.p = j;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((eu0) i((ij) obj2, (fu0) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        eu0 eu0Var = new eu0(this.n, this.o, this.p, ijVar);
        eu0Var.m = obj;
        return eu0Var;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        hu0 hu0Var;
        dp0 dp0Var;
        float c;
        hu0 hu0Var2;
        long j;
        long a;
        int i = this.l;
        dj0 dj0Var = dj0.f;
        if (i != 0) {
            if (i == 1) {
                j = this.k;
                dp0Var = this.j;
                hu0Var = this.i;
                hu0Var2 = (hu0) this.m;
                o30.x(obj);
            } else {
                v7.o("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
        } else {
            o30.x(obj);
            fu0 fu0Var = (fu0) this.m;
            hu0Var = this.n;
            du0 du0Var = new du0(hu0Var, fu0Var);
            rl rlVar = hu0Var.c;
            dp0Var = this.o;
            long j2 = dp0Var.e;
            dj0 dj0Var2 = hu0Var.d;
            long j3 = this.p;
            if (dj0Var2 == dj0Var) {
                c = v41.b(j3);
            } else {
                c = v41.c(j3);
            }
            float d = hu0Var.d(c);
            this.m = hu0Var;
            this.i = hu0Var;
            this.j = dp0Var;
            this.k = j2;
            this.l = 1;
            rlVar.getClass();
            obj = f31.Z(n20.p, new ql(d, rlVar, du0Var, null), this);
            ik ikVar = ik.e;
            if (obj == ikVar) {
                return ikVar;
            }
            hu0Var2 = hu0Var;
            j = j2;
        }
        float d2 = hu0Var2.d(((Number) obj).floatValue());
        if (hu0Var.d == dj0Var) {
            a = v41.a(j, d2, 0.0f, 2);
        } else {
            a = v41.a(j, 0.0f, d2, 1);
        }
        dp0Var.e = a;
        return x31.a;
    }
}
