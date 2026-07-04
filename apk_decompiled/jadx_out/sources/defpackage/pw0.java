package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pw0 extends z30 implements mv {
    public final /* synthetic */ int f;
    public final /* synthetic */ long g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ pw0(int i, long j) {
        super(4);
        this.f = i;
        this.g = j;
    }

    @Override // defpackage.mv
    public final Object h(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean z;
        int i;
        boolean z2;
        int i2;
        int i3 = this.f;
        x31 x31Var = x31.a;
        Object obj5 = ph.a;
        zc0 zc0Var = zc0.a;
        long j = this.g;
        switch (i3) {
            case 0:
                c40 c40Var = (c40) obj2;
                bw bwVar = (bw) obj3;
                int intValue = ((Number) obj4).intValue();
                ((cb) obj).getClass();
                c40Var.getClass();
                if ((intValue & 48) == 0) {
                    if (bwVar.f(c40Var)) {
                        i = 32;
                    } else {
                        i = 16;
                    }
                    intValue |= i;
                }
                if ((intValue & 145) != 144) {
                    z = true;
                } else {
                    z = false;
                }
                if (bwVar.O(intValue & 1, z)) {
                    ef a = cf.a(new x7(16.0f, true, new v7(0)), x1.s, bwVar, 54);
                    long j2 = bwVar.T;
                    int i4 = (int) (j2 ^ (j2 >>> 32));
                    ll0 l = bwVar.l();
                    cd0 B = dl.B(bwVar, zc0Var);
                    jh.c.getClass();
                    vu vuVar = ih.b;
                    bwVar.Y();
                    if (bwVar.S) {
                        bwVar.k(vuVar);
                    } else {
                        bwVar.i0();
                    }
                    kf kfVar = ih.e;
                    m20.F(kfVar, bwVar, a);
                    kf kfVar2 = ih.d;
                    m20.F(kfVar2, bwVar, l);
                    Integer valueOf = Integer.valueOf(i4);
                    kf kfVar3 = ih.f;
                    m20.F(kfVar3, bwVar, valueOf);
                    w3 w3Var = ih.g;
                    m20.C(w3Var, bwVar);
                    kf kfVar4 = ih.c;
                    m20.F(kfVar4, bwVar, B);
                    Object[] objArr = new Object[0];
                    Object L = bwVar.L();
                    if (L == obj5) {
                        L = ba0.p;
                        bwVar.f0(L);
                    }
                    ek0 ek0Var = (ek0) y20.r(objArr, (vu) L, bwVar);
                    boolean f = bwVar.f(ek0Var);
                    Object L2 = bwVar.L();
                    if (f || L2 == obj5) {
                        L2 = new mw(ek0Var, 7);
                        bwVar.f0(L2);
                    }
                    vu vuVar2 = (vu) L2;
                    boolean f2 = bwVar.f(ek0Var);
                    Object L3 = bwVar.L();
                    if (f2 || L3 == obj5) {
                        L3 = new ow(ek0Var, 5);
                        bwVar.f0(L3);
                    }
                    n30.b(vuVar2, (gv) L3, new he(100.0f), 0.01f, c40Var, dl.D(zc0Var, 32.0f), bwVar, ((intValue << 9) & 57344) | 199680, 0);
                    cd0 C = dl.C(k81.k(n20.l(dl.C(zc0Var, 24.0f), new jr0(32.0f)), j, o20.o), 24.0f);
                    pc0 d = ya.d(x1.g);
                    long j3 = bwVar.T;
                    int i5 = (int) (j3 ^ (j3 >>> 32));
                    ll0 l2 = bwVar.l();
                    cd0 B2 = dl.B(bwVar, C);
                    bwVar.Y();
                    if (bwVar.S) {
                        bwVar.k(vuVar);
                    } else {
                        bwVar.i0();
                    }
                    m20.F(kfVar, bwVar, d);
                    m20.F(kfVar2, bwVar, l2);
                    d3.x(i5, bwVar, kfVar3, bwVar, w3Var);
                    m20.F(kfVar4, bwVar, B2);
                    boolean f3 = bwVar.f(ek0Var);
                    Object L4 = bwVar.L();
                    if (f3 || L4 == obj5) {
                        L4 = new mw(ek0Var, 8);
                        bwVar.f0(L4);
                    }
                    vu vuVar3 = (vu) L4;
                    boolean f4 = bwVar.f(ek0Var);
                    Object L5 = bwVar.L();
                    if (f4 || L5 == obj5) {
                        L5 = new ow(ek0Var, 6);
                        bwVar.f0(L5);
                    }
                    gv gvVar = (gv) L5;
                    he heVar = new he(100.0f);
                    boolean e = bwVar.e(j);
                    Object L6 = bwVar.L();
                    if (e || L6 == obj5) {
                        L6 = new tj(6, j);
                        bwVar.f0(L6);
                    }
                    n30.b(vuVar3, gvVar, heVar, 0.01f, n20.M((gv) L6, bwVar), dl.D(zc0Var, 32.0f), bwVar, 199680, 0);
                    bwVar.p(true);
                    bwVar.p(true);
                    return x31Var;
                }
                bwVar.R();
                return x31Var;
            default:
                c40 c40Var2 = (c40) obj2;
                bw bwVar2 = (bw) obj3;
                int intValue2 = ((Number) obj4).intValue();
                ((cb) obj).getClass();
                c40Var2.getClass();
                if ((intValue2 & 48) == 0) {
                    if (bwVar2.f(c40Var2)) {
                        i2 = 32;
                    } else {
                        i2 = 16;
                    }
                    intValue2 |= i2;
                }
                int i6 = intValue2;
                if ((i6 & 145) != 144) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (bwVar2.O(i6 & 1, z2)) {
                    ef a2 = cf.a(new x7(16.0f, true, new v7(0)), x1.s, bwVar2, 54);
                    long j4 = bwVar2.T;
                    int i7 = (int) (j4 ^ (j4 >>> 32));
                    ll0 l3 = bwVar2.l();
                    cd0 B3 = dl.B(bwVar2, zc0Var);
                    jh.c.getClass();
                    di diVar = ih.b;
                    bwVar2.Y();
                    if (bwVar2.S) {
                        bwVar2.k(diVar);
                    } else {
                        bwVar2.i0();
                    }
                    kf kfVar5 = ih.e;
                    m20.F(kfVar5, bwVar2, a2);
                    kf kfVar6 = ih.d;
                    m20.F(kfVar6, bwVar2, l3);
                    Integer valueOf2 = Integer.valueOf(i7);
                    kf kfVar7 = ih.f;
                    m20.F(kfVar7, bwVar2, valueOf2);
                    w3 w3Var2 = ih.g;
                    m20.C(w3Var2, bwVar2);
                    kf kfVar8 = ih.c;
                    m20.F(kfVar8, bwVar2, B3);
                    Object[] objArr2 = new Object[0];
                    Object L7 = bwVar2.L();
                    if (L7 == obj5) {
                        L7 = ba0.q;
                        bwVar2.f0(L7);
                    }
                    af0 af0Var = (af0) y20.r(objArr2, (vu) L7, bwVar2);
                    boolean f5 = bwVar2.f(af0Var);
                    Object L8 = bwVar2.L();
                    if (f5 || L8 == obj5) {
                        L8 = new qg(af0Var, 2);
                        bwVar2.f0(L8);
                    }
                    vu vuVar4 = (vu) L8;
                    boolean f6 = bwVar2.f(af0Var);
                    Object L9 = bwVar2.L();
                    if (f6 || L9 == obj5) {
                        L9 = new og(af0Var, 6);
                        bwVar2.f0(L9);
                    }
                    o30.a(vuVar4, (gv) L9, c40Var2, dl.D(zc0Var, 32.0f), bwVar2, ((i6 << 3) & 896) | 3072);
                    cd0 C2 = dl.C(k81.k(n20.l(dl.C(zc0Var, 24.0f), new jr0(32.0f)), j, o20.o), 24.0f);
                    pc0 d2 = ya.d(x1.g);
                    long j5 = bwVar2.T;
                    int i8 = (int) (j5 ^ (j5 >>> 32));
                    ll0 l4 = bwVar2.l();
                    cd0 B4 = dl.B(bwVar2, C2);
                    bwVar2.Y();
                    if (bwVar2.S) {
                        bwVar2.k(diVar);
                    } else {
                        bwVar2.i0();
                    }
                    m20.F(kfVar5, bwVar2, d2);
                    m20.F(kfVar6, bwVar2, l4);
                    d3.x(i8, bwVar2, kfVar7, bwVar2, w3Var2);
                    m20.F(kfVar8, bwVar2, B4);
                    boolean f7 = bwVar2.f(af0Var);
                    Object L10 = bwVar2.L();
                    if (f7 || L10 == obj5) {
                        L10 = new qg(af0Var, 3);
                        bwVar2.f0(L10);
                    }
                    vu vuVar5 = (vu) L10;
                    boolean f8 = bwVar2.f(af0Var);
                    Object L11 = bwVar2.L();
                    if (f8 || L11 == obj5) {
                        L11 = new og(af0Var, 7);
                        bwVar2.f0(L11);
                    }
                    gv gvVar2 = (gv) L11;
                    boolean e2 = bwVar2.e(j);
                    Object L12 = bwVar2.L();
                    if (e2 || L12 == obj5) {
                        L12 = new tj(7, j);
                        bwVar2.f0(L12);
                    }
                    o30.a(vuVar5, gvVar2, n20.M((gv) L12, bwVar2), dl.D(zc0Var, 32.0f), bwVar2, 3072);
                    bwVar2.p(true);
                    bwVar2.p(true);
                } else {
                    bwVar2.R();
                }
                return x31Var;
        }
    }
}
