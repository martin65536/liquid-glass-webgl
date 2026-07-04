package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class aj extends sz0 implements kv {
    public int i;
    public /* synthetic */ Object j;
    public final /* synthetic */ f41 k;
    public final /* synthetic */ cj l;
    public final /* synthetic */ ob m;
    public final /* synthetic */ long n;
    public final /* synthetic */ d30 o;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public aj(f41 f41Var, cj cjVar, ob obVar, long j, d30 d30Var, ij ijVar) {
        super(2, ijVar);
        this.k = f41Var;
        this.l = cjVar;
        this.m = obVar;
        this.n = j;
        this.o = d30Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((aj) i((ij) obj2, (fu0) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        aj ajVar = new aj(this.k, this.l, this.m, this.n, this.o, ijVar);
        ajVar.j = obj;
        return ajVar;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        if (i != 0) {
            if (i == 1) {
                o30.x(obj);
            } else {
                v7.o("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
        } else {
            o30.x(obj);
            fu0 fu0Var = (fu0) this.j;
            long j = this.n;
            cj cjVar = this.l;
            ob obVar = this.m;
            float D0 = cj.D0(cjVar, obVar, j);
            f41 f41Var = this.k;
            f41Var.e = D0;
            zi ziVar = new zi(cjVar, f41Var, this.o, fu0Var);
            y8 y8Var = new y8(cjVar, f41Var, obVar, 2);
            this.i = 1;
            Object a = f41Var.a(ziVar, y8Var, this);
            ik ikVar = ik.e;
            if (a == ikVar) {
                return ikVar;
            }
        }
        return x31.a;
    }
}
