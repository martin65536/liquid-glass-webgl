package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pw extends z30 implements kv {
    public final /* synthetic */ cb f;
    public final /* synthetic */ c40 g;
    public final /* synthetic */ af0 h;
    public final /* synthetic */ ek0 i;
    public final /* synthetic */ ek0 j;
    public final /* synthetic */ ek0 k;
    public final /* synthetic */ ek0 l;
    public final /* synthetic */ ek0 m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public pw(cb cbVar, c40 c40Var, af0 af0Var, ek0 ek0Var, ek0 ek0Var2, ek0 ek0Var3, ek0 ek0Var4, ek0 ek0Var5) {
        super(2);
        this.f = cbVar;
        this.g = c40Var;
        this.h = af0Var;
        this.i = ek0Var;
        this.j = ek0Var2;
        this.k = ek0Var3;
        this.l = ek0Var4;
        this.m = ek0Var5;
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
            if (((Boolean) this.h.getValue()).booleanValue()) {
                bwVar.V(-696994370);
                c40 E = jc0.E(bwVar);
                zc0 zc0Var = zc0.a;
                cd0 K = jc0.K(dl.F(dl.C(zc0Var, 16.0f), 0.0f, 7), jc0.r);
                Object L = bwVar.L();
                dt0 dt0Var = ph.a;
                if (L == dt0Var) {
                    L = di.A;
                    bwVar.f0(L);
                }
                vu vuVar = (vu) L;
                Object L2 = bwVar.L();
                if (L2 == dt0Var) {
                    L2 = w3.s;
                    bwVar.f0(L2);
                }
                gv gvVar = (gv) L2;
                Object L3 = bwVar.L();
                if (L3 == dt0Var) {
                    L3 = di.B;
                    bwVar.f0(L3);
                }
                vu vuVar2 = (vu) L3;
                Object L4 = bwVar.L();
                if (L4 == dt0Var) {
                    L4 = w3.t;
                    bwVar.f0(L4);
                }
                cd0 a = this.f.a(dl.C(f31.s(K, this.g, vuVar, gvVar, vuVar2, null, null, null, E, null, (gv) L4, 2928), 24.0f), x1.n);
                ef a2 = cf.a(o20.F(16.0f), x1.s, bwVar, 54);
                long j = bwVar.T;
                int i = (int) (j ^ (j >>> 32));
                ll0 l = bwVar.l();
                cd0 B = dl.B(bwVar, a);
                jh.c.getClass();
                di diVar = ih.b;
                bwVar.Y();
                if (bwVar.S) {
                    bwVar.k(diVar);
                } else {
                    bwVar.i0();
                }
                kf kfVar = ih.e;
                m20.F(kfVar, bwVar, a2);
                kf kfVar2 = ih.d;
                m20.F(kfVar2, bwVar, l);
                Integer valueOf = Integer.valueOf(i);
                kf kfVar3 = ih.f;
                m20.F(kfVar3, bwVar, valueOf);
                w3 w3Var = ih.g;
                m20.C(w3Var, bwVar);
                kf kfVar4 = ih.c;
                m20.F(kfVar4, bwVar, B);
                x7 F = o20.F(8.0f);
                z9 z9Var = x1.r;
                ef a3 = cf.a(F, z9Var, bwVar, 6);
                long j2 = bwVar.T;
                int i2 = (int) (j2 ^ (j2 >>> 32));
                ll0 l2 = bwVar.l();
                cd0 B2 = dl.B(bwVar, zc0Var);
                bwVar.Y();
                if (bwVar.S) {
                    bwVar.k(diVar);
                } else {
                    bwVar.i0();
                }
                m20.F(kfVar, bwVar, a3);
                m20.F(kfVar2, bwVar, l2);
                d3.x(i2, bwVar, kfVar3, bwVar, w3Var);
                m20.F(kfVar4, bwVar, B2);
                dl.b("Corner radius", null, null, 0, false, 0, 0, null, bwVar, 6, 1022);
                Object L5 = bwVar.L();
                ek0 ek0Var = this.i;
                if (L5 == dt0Var) {
                    L5 = new mw(ek0Var, 1);
                    bwVar.f0(L5);
                }
                vu vuVar3 = (vu) L5;
                Object L6 = bwVar.L();
                if (L6 == dt0Var) {
                    L6 = new ow(ek0Var, 0);
                    bwVar.f0(L6);
                }
                n30.b(vuVar3, (gv) L6, new he(1.0f), 0.001f, E, null, bwVar, 3126, 32);
                bwVar.p(true);
                ef a4 = cf.a(o20.F(8.0f), z9Var, bwVar, 6);
                long j3 = bwVar.T;
                int i3 = (int) (j3 ^ (j3 >>> 32));
                ll0 l3 = bwVar.l();
                cd0 B3 = dl.B(bwVar, zc0Var);
                bwVar.Y();
                if (bwVar.S) {
                    bwVar.k(diVar);
                } else {
                    bwVar.i0();
                }
                m20.F(kfVar, bwVar, a4);
                m20.F(kfVar2, bwVar, l3);
                d3.x(i3, bwVar, kfVar3, bwVar, w3Var);
                m20.F(kfVar4, bwVar, B3);
                dl.b("Blur radius", null, null, 0, false, 0, 0, null, bwVar, 6, 1022);
                Object L7 = bwVar.L();
                ek0 ek0Var2 = this.j;
                if (L7 == dt0Var) {
                    L7 = new mw(ek0Var2, 2);
                    bwVar.f0(L7);
                }
                vu vuVar4 = (vu) L7;
                Object L8 = bwVar.L();
                if (L8 == dt0Var) {
                    L8 = new ow(ek0Var2, 1);
                    bwVar.f0(L8);
                }
                n30.b(vuVar4, (gv) L8, new he(32.0f), 0.01f, E, null, bwVar, 3126, 32);
                bwVar.p(true);
                ef a5 = cf.a(o20.F(8.0f), z9Var, bwVar, 6);
                long j4 = bwVar.T;
                int i4 = (int) (j4 ^ (j4 >>> 32));
                ll0 l4 = bwVar.l();
                cd0 B4 = dl.B(bwVar, zc0Var);
                bwVar.Y();
                if (bwVar.S) {
                    bwVar.k(diVar);
                } else {
                    bwVar.i0();
                }
                m20.F(kfVar, bwVar, a5);
                m20.F(kfVar2, bwVar, l4);
                d3.x(i4, bwVar, kfVar3, bwVar, w3Var);
                m20.F(kfVar4, bwVar, B4);
                dl.b("Refraction height", null, null, 0, false, 0, 0, null, bwVar, 6, 1022);
                Object L9 = bwVar.L();
                ek0 ek0Var3 = this.k;
                if (L9 == dt0Var) {
                    L9 = new mw(ek0Var3, 3);
                    bwVar.f0(L9);
                }
                vu vuVar5 = (vu) L9;
                Object L10 = bwVar.L();
                if (L10 == dt0Var) {
                    L10 = new ow(ek0Var3, 2);
                    bwVar.f0(L10);
                }
                n30.b(vuVar5, (gv) L10, new he(1.0f), 0.001f, E, null, bwVar, 3126, 32);
                bwVar.p(true);
                ef a6 = cf.a(o20.F(8.0f), z9Var, bwVar, 6);
                long j5 = bwVar.T;
                int i5 = (int) (j5 ^ (j5 >>> 32));
                ll0 l5 = bwVar.l();
                cd0 B5 = dl.B(bwVar, zc0Var);
                bwVar.Y();
                if (bwVar.S) {
                    bwVar.k(diVar);
                } else {
                    bwVar.i0();
                }
                m20.F(kfVar, bwVar, a6);
                m20.F(kfVar2, bwVar, l5);
                d3.x(i5, bwVar, kfVar3, bwVar, w3Var);
                m20.F(kfVar4, bwVar, B5);
                dl.b("Refraction amount", null, null, 0, false, 0, 0, null, bwVar, 6, 1022);
                Object L11 = bwVar.L();
                ek0 ek0Var4 = this.l;
                if (L11 == dt0Var) {
                    L11 = new mw(ek0Var4, 4);
                    bwVar.f0(L11);
                }
                vu vuVar6 = (vu) L11;
                Object L12 = bwVar.L();
                if (L12 == dt0Var) {
                    L12 = new ow(ek0Var4, 3);
                    bwVar.f0(L12);
                }
                n30.b(vuVar6, (gv) L12, new he(1.0f), 0.001f, E, null, bwVar, 3126, 32);
                bwVar.p(true);
                ef a7 = cf.a(o20.F(8.0f), z9Var, bwVar, 6);
                long j6 = bwVar.T;
                int i6 = (int) (j6 ^ (j6 >>> 32));
                ll0 l6 = bwVar.l();
                cd0 B6 = dl.B(bwVar, zc0Var);
                bwVar.Y();
                if (bwVar.S) {
                    bwVar.k(diVar);
                } else {
                    bwVar.i0();
                }
                m20.F(kfVar, bwVar, a7);
                m20.F(kfVar2, bwVar, l6);
                d3.x(i6, bwVar, kfVar3, bwVar, w3Var);
                m20.F(kfVar4, bwVar, B6);
                dl.b("Chromatic aberration", null, null, 0, false, 0, 0, null, bwVar, 6, 1022);
                Object L13 = bwVar.L();
                ek0 ek0Var5 = this.m;
                if (L13 == dt0Var) {
                    L13 = new mw(ek0Var5, 5);
                    bwVar.f0(L13);
                }
                vu vuVar7 = (vu) L13;
                Object L14 = bwVar.L();
                if (L14 == dt0Var) {
                    L14 = new ow(ek0Var5, 4);
                    bwVar.f0(L14);
                }
                n30.b(vuVar7, (gv) L14, new he(1.0f), 0.001f, E, null, bwVar, 3126, 32);
                bwVar.p(true);
                bwVar.p(true);
                bwVar.p(false);
            } else {
                bwVar.V(-693412723);
                bwVar.p(false);
            }
        } else {
            bwVar.R();
        }
        return x31.a;
    }
}
