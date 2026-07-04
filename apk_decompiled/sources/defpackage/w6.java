package defpackage;

import java.util.concurrent.CancellationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class w6 extends sz0 implements gv {
    public d7 i;
    public ap0 j;
    public int k;
    public final /* synthetic */ y6 l;
    public final /* synthetic */ Object m;
    public final /* synthetic */ p01 n;
    public final /* synthetic */ long o;
    public final /* synthetic */ gv p;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public w6(y6 y6Var, Object obj, p01 p01Var, long j, gv gvVar, ij ijVar) {
        super(1, ijVar);
        this.l = y6Var;
        this.m = obj;
        this.n = p01Var;
        this.o = j;
        this.p = gvVar;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        long j = this.o;
        gv gvVar = this.p;
        return new w6(this.l, this.m, this.n, j, gvVar, (ij) obj).k(x31.a);
    }

    /* JADX WARN: Type inference failed for: r11v1, types: [ap0, java.lang.Object] */
    @Override // defpackage.s9
    public final Object k(Object obj) {
        d7 d7Var;
        ap0 ap0Var;
        a7 a7Var;
        p01 p01Var = this.n;
        int i = this.k;
        int i2 = 1;
        y6 y6Var = this.l;
        try {
            if (i != 0) {
                if (i == 1) {
                    ap0Var = this.j;
                    d7Var = this.i;
                    o30.x(obj);
                } else {
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
            } else {
                o30.x(obj);
                y6Var.c.g = (i7) ((gv) y6Var.a.f).e(this.m);
                y6Var.e.setValue(p01Var.c);
                y6Var.d.setValue(Boolean.TRUE);
                d7 d7Var2 = y6Var.c;
                d7 d7Var3 = new d7(d7Var2.e, d7Var2.f.getValue(), dl.p(d7Var2.g), d7Var2.h, Long.MIN_VALUE, d7Var2.j);
                ?? obj2 = new Object();
                long j = this.o;
                v6 v6Var = new v6(y6Var, d7Var3, this.p, obj2, 0);
                this.i = d7Var3;
                this.j = obj2;
                this.k = 1;
                Object f = d20.f(d7Var3, p01Var, j, v6Var, this);
                ik ikVar = ik.e;
                if (f == ikVar) {
                    return ikVar;
                }
                d7Var = d7Var3;
                ap0Var = obj2;
            }
            if (ap0Var.e) {
                a7Var = a7.e;
            } else {
                a7Var = a7.f;
            }
            y6.b(y6Var);
            return new c4(i2, d7Var, a7Var);
        } catch (CancellationException e) {
            y6.b(y6Var);
            throw e;
        }
    }
}
