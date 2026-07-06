package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class uw extends z30 implements kv {
    public final /* synthetic */ c40 f;
    public final /* synthetic */ cb g;
    public final /* synthetic */ hk h;
    public final /* synthetic */ y6 i;
    public final /* synthetic */ y6 j;
    public final /* synthetic */ y6 k;
    public final /* synthetic */ af0 l;
    public final /* synthetic */ ek0 m;
    public final /* synthetic */ ek0 n;
    public final /* synthetic */ ek0 o;
    public final /* synthetic */ ek0 p;
    public final /* synthetic */ ek0 q;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public uw(c40 c40Var, cb cbVar, hk hkVar, y6 y6Var, y6 y6Var2, y6 y6Var3, af0 af0Var, ek0 ek0Var, ek0 ek0Var2, ek0 ek0Var3, ek0 ek0Var4, ek0 ek0Var5) {
        super(2);
        this.f = c40Var;
        this.g = cbVar;
        this.h = hkVar;
        this.i = y6Var;
        this.j = y6Var2;
        this.k = y6Var3;
        this.l = af0Var;
        this.m = ek0Var;
        this.n = ek0Var2;
        this.o = ek0Var3;
        this.p = ek0Var4;
        this.q = ek0Var5;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        boolean z;
        bw bwVar = (bw) obj;
        int intValue = ((Number) obj2).intValue();
        if ((intValue & 3) != 2) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(intValue & 1, z)) {
            Object L = bwVar.L();
            af0 af0Var = this.l;
            dt0 dt0Var = ph.a;
            if (L == dt0Var) {
                L = new qg(af0Var, 1);
                bwVar.f0(L);
            }
            zc0 zc0Var = zc0.a;
            cd0 C = dl.C(zc0Var, 20.0f);
            m41 m41Var = jc0.r;
            cd0 K = jc0.K(C, m41Var);
            ba baVar = x1.m;
            cb cbVar = this.g;
            g30.c((vu) L, this.f, cbVar.a(K, baVar), false, f31.f(4294937896L), 0L, jc0.C(-1159480605, new qw(0, af0Var), bwVar), bwVar, 1597446, 40);
            boolean h = bwVar.h(this.h) | bwVar.h(this.i) | bwVar.h(this.j) | bwVar.h(this.k);
            Object L2 = bwVar.L();
            if (h || L2 == dt0Var) {
                L2 = new tw(this.h, this.i, this.j, this.k, this.m, this.n, this.o, this.p, this.q);
                bwVar.f0(L2);
            }
            g30.c((vu) L2, this.f, cbVar.a(jc0.K(dl.C(zc0Var, 20.0f), m41Var), x1.o), false, f31.f(4294937896L), 0L, lg.a, bwVar, 1597440, 40);
        } else {
            bwVar.R();
        }
        return x31.a;
    }
}
