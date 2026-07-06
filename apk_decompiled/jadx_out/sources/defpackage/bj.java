package defpackage;

import java.util.concurrent.CancellationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bj extends sz0 implements kv {
    public int i;
    public /* synthetic */ Object j;
    public final /* synthetic */ cj k;
    public final /* synthetic */ f41 l;
    public final /* synthetic */ ob m;
    public final /* synthetic */ long n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bj(cj cjVar, f41 f41Var, ob obVar, long j, ij ijVar) {
        super(2, ijVar);
        this.k = cjVar;
        this.l = f41Var;
        this.m = obVar;
        this.n = j;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((bj) i((ij) obj2, (hk) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        bj bjVar = new bj(this.k, this.l, this.m, this.n, ijVar);
        bjVar.j = obj;
        return bjVar;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        cj cjVar = this.k;
        ib ibVar = cjVar.w;
        int i = this.i;
        try {
            try {
                if (i != 0) {
                    if (i == 1) {
                        o30.x(obj);
                    } else {
                        v7.o("call to 'resume' before 'invoke' with coroutine");
                        return null;
                    }
                } else {
                    o30.x(obj);
                    d30 s = g30.s(((hk) this.j).g());
                    cjVar.z = true;
                    hu0 hu0Var = cjVar.t;
                    gf0 gf0Var = gf0.e;
                    aj ajVar = new aj(this.l, cjVar, this.m, this.n, s, null);
                    this.i = 1;
                    Object f = hu0Var.f(gf0Var, ajVar, this);
                    ik ikVar = ik.e;
                    if (f == ikVar) {
                        return ikVar;
                    }
                }
                ibVar.b();
                cjVar.z = false;
                ibVar.a(null);
                cjVar.x = false;
                return x31.a;
            } catch (CancellationException e) {
                throw e;
            }
        } catch (Throwable th) {
            cjVar.z = false;
            ibVar.a(null);
            cjVar.x = false;
            throw th;
        }
    }
}
