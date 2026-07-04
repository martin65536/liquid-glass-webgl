package defpackage;

import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class m90 extends z30 implements kv {
    public final /* synthetic */ c40 f;
    public final /* synthetic */ hy0 g;
    public final /* synthetic */ m9 h;
    public final /* synthetic */ al i;
    public final /* synthetic */ long j;
    public final /* synthetic */ k20 k;
    public final /* synthetic */ long l;
    public final /* synthetic */ gg m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public m90(c40 c40Var, hy0 hy0Var, m9 m9Var, al alVar, long j, k20 k20Var, long j2, gg ggVar) {
        super(2);
        this.f = c40Var;
        this.g = hy0Var;
        this.h = m9Var;
        this.i = alVar;
        this.j = j;
        this.k = k20Var;
        this.l = j2;
        this.m = ggVar;
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
            Object obj3 = ph.a;
            if (L == obj3) {
                L = w3.x;
                bwVar.f0(L);
            }
            AtomicInteger atomicInteger = pu0.a;
            cd0 H = f31.H(k81.x(new ae((gv) L), null, null, 520187), this.f);
            hy0 hy0Var = this.g;
            boolean f = bwVar.f(hy0Var);
            Object L2 = bwVar.L();
            if (f || L2 == obj3) {
                L2 = new l90(hy0Var, 1);
                bwVar.f0(L2);
            }
            cd0 w = k81.w(H, (gv) L2);
            Object L3 = bwVar.L();
            if (L3 == obj3) {
                L3 = di.I;
                bwVar.f0(L3);
            }
            vu vuVar = (vu) L3;
            al alVar = this.i;
            boolean h = bwVar.h(alVar);
            Object L4 = bwVar.L();
            int i = 6;
            if (h || L4 == obj3) {
                L4 = new uk(alVar, i);
                bwVar.f0(L4);
            }
            gv gvVar = (gv) L4;
            boolean h2 = bwVar.h(alVar);
            Object L5 = bwVar.L();
            if (h2 || L5 == obj3) {
                L5 = new vk(alVar, i);
                bwVar.f0(L5);
            }
            vu vuVar2 = (vu) L5;
            long j = this.j;
            boolean e = bwVar.e(j);
            Object L6 = bwVar.L();
            if (e || L6 == obj3) {
                L6 = new tj(4, j);
                bwVar.f0(L6);
            }
            cd0 x = k81.x(dl.D(k81.r(k81.z(f31.s(w, this.h, vuVar, gvVar, vuVar2, null, null, null, null, null, (gv) L6, 3056).b(this.k.i), 56.0f), 1.0f), 4.0f), null, new da(5, this.l), 262143);
            pr0 a = or0.a(o20.a, bwVar, 48);
            long j2 = bwVar.T;
            int i2 = (int) (j2 ^ (j2 >>> 32));
            ll0 l = bwVar.l();
            cd0 B = dl.B(bwVar, x);
            jh.c.getClass();
            vu vuVar3 = ih.b;
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(vuVar3);
            } else {
                bwVar.i0();
            }
            m20.F(ih.e, bwVar, a);
            m20.F(ih.d, bwVar, l);
            m20.F(ih.f, bwVar, Integer.valueOf(i2));
            m20.C(ih.g, bwVar);
            m20.F(ih.c, bwVar, B);
            this.m.c(qr0.a, bwVar, 6);
            bwVar.p(true);
        } else {
            bwVar.R();
        }
        return x31.a;
    }
}
