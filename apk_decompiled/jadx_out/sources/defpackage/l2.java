package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l2 extends sz0 implements kv {
    public int i;
    public final /* synthetic */ y6 j;
    public final /* synthetic */ double k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public l2(y6 y6Var, double d, ij ijVar) {
        super(2, ijVar);
        this.j = y6Var;
        this.k = d;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((l2) i((ij) obj2, (hk) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        return new l2(this.j, this.k, ijVar);
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        long j;
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
            if (this.k > 0.5d) {
                j = se.b;
            } else {
                j = se.c;
            }
            se seVar = new se(j);
            e31 P = k81.P(1000, null, 6);
            this.i = 1;
            Object c = y6.c(this.j, seVar, P, null, null, this, 12);
            ik ikVar = ik.e;
            if (c == ikVar) {
                return ikVar;
            }
        }
        return x31.a;
    }
}
