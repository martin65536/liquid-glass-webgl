package defpackage;

import java.util.concurrent.CancellationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ql extends sz0 implements kv {
    public bp0 i;
    public d7 j;
    public int k;
    public final /* synthetic */ float l;
    public final /* synthetic */ rl m;
    public final /* synthetic */ du0 n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ql(float f, rl rlVar, du0 du0Var, ij ijVar) {
        super(2, ijVar);
        this.l = f;
        this.m = rlVar;
        this.n = du0Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((ql) i((ij) obj2, (hk) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        return new ql(this.l, this.m, this.n, ijVar);
    }

    /* JADX WARN: Type inference failed for: r5v0, types: [bp0, java.lang.Object] */
    @Override // defpackage.s9
    public final Object k(Object obj) {
        float f;
        d7 d7Var;
        bp0 bp0Var;
        d7 d7Var2;
        fl flVar;
        v6 v6Var;
        int i = this.k;
        if (i != 0) {
            if (i == 1) {
                d7Var2 = this.j;
                bp0Var = this.i;
                try {
                    o30.x(obj);
                } catch (CancellationException unused) {
                    bp0Var.e = ((Number) ((gv) d7Var2.e.g).e(d7Var2.g)).floatValue();
                    f = bp0Var.e;
                    return new Float(f);
                }
            } else {
                v7.o("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
        } else {
            o30.x(obj);
            f = this.l;
            if (Math.abs(f) > 1.0f) {
                ?? obj2 = new Object();
                obj2.e = f;
                Object obj3 = new Object();
                d7 a = o4.a(f, 28);
                try {
                    rl rlVar = this.m;
                    flVar = rlVar.a;
                    v6Var = new v6(obj3, this.n, obj2, rlVar, 2);
                    this.i = obj2;
                    this.j = a;
                    this.k = 1;
                    d7Var = a;
                } catch (CancellationException unused2) {
                    d7Var = a;
                }
                try {
                    Object f2 = d20.f(d7Var, new el(flVar, dl.t, a.f.getValue(), a.g), Long.MIN_VALUE, v6Var, this);
                    Object obj4 = ik.e;
                    if (f2 != obj4) {
                        f2 = x31.a;
                    }
                    if (f2 == obj4) {
                        return obj4;
                    }
                    bp0Var = obj2;
                } catch (CancellationException unused3) {
                    bp0Var = obj2;
                    d7Var2 = d7Var;
                    bp0Var.e = ((Number) ((gv) d7Var2.e.g).e(d7Var2.g)).floatValue();
                    f = bp0Var.e;
                    return new Float(f);
                }
            }
            return new Float(f);
        }
        f = bp0Var.e;
        return new Float(f);
    }
}
