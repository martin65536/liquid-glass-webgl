package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class lj extends z30 implements mv {
    public final /* synthetic */ cd0 f;
    public final /* synthetic */ vu g;
    public final /* synthetic */ gv h;
    public final /* synthetic */ vu i;
    public final /* synthetic */ gv j;
    public final /* synthetic */ gv k;
    public final /* synthetic */ ad l;
    public final /* synthetic */ long m;
    public final /* synthetic */ q41 n;
    public final /* synthetic */ da o;
    public final /* synthetic */ long p;
    public final /* synthetic */ cd0 q;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public lj(cd0 cd0Var, vu vuVar, gv gvVar, vu vuVar2, gv gvVar2, gv gvVar3, ad adVar, long j, q41 q41Var, da daVar, long j2, cd0 cd0Var2) {
        super(4);
        this.f = cd0Var;
        this.g = vuVar;
        this.h = gvVar;
        this.i = vuVar2;
        this.j = gvVar2;
        this.k = gvVar3;
        this.l = adVar;
        this.m = j;
        this.n = q41Var;
        this.o = daVar;
        this.p = j2;
        this.q = cd0Var2;
    }

    @Override // defpackage.mv
    public final Object h(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean z;
        int i;
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
            zc0 zc0Var = zc0.a;
            cd0 b = jc0.K(jc0.K(dl.F(zc0Var, 80.0f, 13), jc0.o), jc0.p).b(k81.n);
            z9 z9Var = x1.s;
            dt0 dt0Var = o20.b;
            ef a = cf.a(dt0Var, z9Var, bwVar, 48);
            long j = bwVar.T;
            int i2 = (int) (j ^ (j >>> 32));
            ll0 l = bwVar.l();
            cd0 B = dl.B(bwVar, b);
            jh.c.getClass();
            di diVar = ih.b;
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            kf kfVar = ih.e;
            m20.F(kfVar, bwVar, a);
            kf kfVar2 = ih.d;
            m20.F(kfVar2, bwVar, l);
            Integer valueOf = Integer.valueOf(i2);
            kf kfVar3 = ih.f;
            m20.F(kfVar3, bwVar, valueOf);
            w3 w3Var = ih.g;
            m20.C(w3Var, bwVar);
            kf kfVar4 = ih.c;
            m20.F(kfVar4, bwVar, B);
            pr0 a2 = or0.a(o20.F(16.0f), bwVar, 54);
            long j2 = bwVar.T;
            int i3 = (int) (j2 ^ (j2 >>> 32));
            ll0 l2 = bwVar.l();
            cd0 B2 = dl.B(bwVar, zc0Var);
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(kfVar, bwVar, a2);
            m20.F(kfVar2, bwVar, l2);
            d3.x(i3, bwVar, kfVar3, bwVar, w3Var);
            m20.F(kfVar4, bwVar, B2);
            vu vuVar = this.g;
            gv gvVar = this.h;
            vu vuVar2 = this.i;
            gv gvVar2 = this.j;
            gv gvVar3 = this.k;
            cd0 C = dl.C(k81.J(f31.s(zc0Var, c40Var, vuVar, gvVar, vuVar2, null, null, gvVar2, null, null, gvVar3, 2976), 152.0f), 16.0f);
            ba baVar = x1.g;
            pc0 d = ya.d(baVar);
            long j3 = bwVar.T;
            int i4 = (int) (j3 ^ (j3 >>> 32));
            ll0 l3 = bwVar.l();
            cd0 B3 = dl.B(bwVar, C);
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(kfVar, bwVar, d);
            m20.F(kfVar2, bwVar, l3);
            d3.x(i4, bwVar, kfVar3, bwVar, w3Var);
            m20.F(kfVar4, bwVar, B3);
            ad adVar = this.l;
            cd0 l4 = n20.l(zc0Var, adVar);
            uy uyVar = o20.o;
            cd0 E = m20.E(k81.k(l4, this.m, uyVar));
            q41 q41Var = this.n;
            da daVar = this.o;
            cd0 J = k81.J(n20.G(E, q41Var, null, 0.0f, daVar, 30), 56.0f);
            db dbVar = db.a;
            ya.a(dbVar.a(J, baVar), bwVar, 0);
            cd0 l5 = n20.l(zc0Var, adVar);
            long j4 = this.p;
            ya.a(dbVar.a(k81.J(n20.G(m20.E(k81.k(l5, j4, uyVar)), q41Var, null, 0.0f, daVar, 30), 56.0f), x1.i), bwVar, 0);
            ya.a(dbVar.a(k81.J(n20.G(m20.E(k81.k(n20.l(zc0Var, adVar), j4, uyVar)), q41Var, null, 0.0f, daVar, 30), 56.0f), x1.m), bwVar, 0);
            bwVar.p(true);
            ya.a(k81.J(f31.s(zc0Var, c40Var, vuVar, gvVar, vuVar2, null, null, gvVar2, null, null, gvVar3, 2976), 152.0f), bwVar, 0);
            bwVar.p(true);
            cd0 cd0Var = this.f;
            t20.e(bwVar, cd0Var);
            pr0 a3 = or0.a(new x7(16.0f, true, new v7(1)), bwVar, 54);
            long j5 = bwVar.T;
            int i5 = (int) (j5 ^ (j5 >>> 32));
            ll0 l6 = bwVar.l();
            cd0 B4 = dl.B(bwVar, zc0Var);
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(kfVar, bwVar, a3);
            m20.F(kfVar2, bwVar, l6);
            d3.x(i5, bwVar, kfVar3, bwVar, w3Var);
            m20.F(kfVar4, bwVar, B4);
            z9 z9Var2 = x1.r;
            ef a4 = cf.a(dt0Var, z9Var2, bwVar, 0);
            long j6 = bwVar.T;
            int i6 = (int) (j6 ^ (j6 >>> 32));
            ll0 l7 = bwVar.l();
            cd0 B5 = dl.B(bwVar, zc0Var);
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(kfVar, bwVar, a4);
            m20.F(kfVar2, bwVar, l7);
            d3.x(i6, bwVar, kfVar3, bwVar, w3Var);
            m20.F(kfVar4, bwVar, B5);
            pr0 a5 = or0.a(o20.F(16.0f), bwVar, 54);
            long j7 = bwVar.T;
            int i7 = (int) (j7 ^ (j7 >>> 32));
            ll0 l8 = bwVar.l();
            cd0 B6 = dl.B(bwVar, zc0Var);
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(kfVar, bwVar, a5);
            m20.F(kfVar2, bwVar, l8);
            d3.x(i7, bwVar, kfVar3, bwVar, w3Var);
            m20.F(kfVar4, bwVar, B6);
            ya.a(k81.J(n20.G(f31.s(zc0Var, c40Var, vuVar, gvVar, vuVar2, null, null, gvVar2, null, null, gvVar3, 2976), q41Var, null, 0.0f, daVar, 30), 68.0f), bwVar, 0);
            ya.a(k81.J(n20.G(f31.s(zc0Var, c40Var, vuVar, gvVar, vuVar2, null, null, gvVar2, null, null, gvVar3, 2976), q41Var, null, 0.0f, daVar, 30), 68.0f), bwVar, 0);
            bwVar.p(true);
            cd0 cd0Var2 = this.q;
            t20.e(bwVar, cd0Var2);
            ya.a(k81.K(f31.s(zc0Var, c40Var, vuVar, gvVar, vuVar2, null, null, gvVar2, null, null, null, 4000), 152.0f, 68.0f), bwVar, 0);
            bwVar.p(true);
            pr0 a6 = or0.a(o20.F(16.0f), bwVar, 54);
            long j8 = bwVar.T;
            int i8 = (int) (j8 ^ (j8 >>> 32));
            ll0 l9 = bwVar.l();
            cd0 B7 = dl.B(bwVar, zc0Var);
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(kfVar, bwVar, a6);
            m20.F(kfVar2, bwVar, l9);
            d3.x(i8, bwVar, kfVar3, bwVar, w3Var);
            m20.F(kfVar4, bwVar, B7);
            ya.a(k81.K(f31.s(zc0Var, c40Var, vuVar, gvVar, vuVar2, null, null, gvVar2, null, null, gvVar3, 2976), 68.0f, 152.0f), bwVar, 0);
            ya.a(k81.K(f31.s(zc0Var, c40Var, vuVar, gvVar, vuVar2, null, null, gvVar2, null, null, gvVar3, 2976), 68.0f, 152.0f), bwVar, 0);
            bwVar.p(true);
            bwVar.p(true);
            t20.e(bwVar, cd0Var);
            pr0 a7 = or0.a(o20.F(16.0f), bwVar, 54);
            long j9 = bwVar.T;
            int i9 = (int) (j9 ^ (j9 >>> 32));
            ll0 l10 = bwVar.l();
            cd0 B8 = dl.B(bwVar, zc0Var);
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(kfVar, bwVar, a7);
            m20.F(kfVar2, bwVar, l10);
            d3.x(i9, bwVar, kfVar3, bwVar, w3Var);
            m20.F(kfVar4, bwVar, B8);
            ya.a(k81.J(f31.s(zc0Var, c40Var, vuVar, gvVar, vuVar2, null, null, gvVar2, null, null, gvVar3, 2976), 152.0f), bwVar, 0);
            ef a8 = cf.a(dt0Var, z9Var2, bwVar, 0);
            long j10 = bwVar.T;
            int i10 = (int) (j10 ^ (j10 >>> 32));
            ll0 l11 = bwVar.l();
            cd0 B9 = dl.B(bwVar, zc0Var);
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(kfVar, bwVar, a8);
            m20.F(kfVar2, bwVar, l11);
            d3.x(i10, bwVar, kfVar3, bwVar, w3Var);
            m20.F(kfVar4, bwVar, B9);
            pr0 a9 = or0.a(o20.F(16.0f), bwVar, 54);
            long j11 = bwVar.T;
            int i11 = (int) (j11 ^ (j11 >>> 32));
            ll0 l12 = bwVar.l();
            cd0 B10 = dl.B(bwVar, zc0Var);
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(kfVar, bwVar, a9);
            m20.F(kfVar2, bwVar, l12);
            d3.x(i11, bwVar, kfVar3, bwVar, w3Var);
            m20.F(kfVar4, bwVar, B10);
            ya.a(k81.J(n20.G(f31.s(zc0Var, c40Var, vuVar, gvVar, vuVar2, null, null, gvVar2, null, null, gvVar3, 2976), q41Var, null, 0.0f, daVar, 30), 68.0f), bwVar, 0);
            ya.a(k81.J(n20.G(f31.s(zc0Var, c40Var, vuVar, gvVar, vuVar2, null, null, gvVar2, null, null, gvVar3, 2976), q41Var, null, 0.0f, daVar, 30), 68.0f), bwVar, 0);
            bwVar.p(true);
            t20.e(bwVar, cd0Var2);
            pr0 a10 = or0.a(o20.F(16.0f), bwVar, 54);
            long j12 = bwVar.T;
            int i12 = (int) (j12 ^ (j12 >>> 32));
            ll0 l13 = bwVar.l();
            cd0 B11 = dl.B(bwVar, zc0Var);
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(kfVar, bwVar, a10);
            m20.F(kfVar2, bwVar, l13);
            d3.x(i12, bwVar, kfVar3, bwVar, w3Var);
            m20.F(kfVar4, bwVar, B11);
            ya.a(k81.J(n20.G(f31.s(zc0Var, c40Var, vuVar, gvVar, vuVar2, null, null, gvVar2, null, null, gvVar3, 2976), q41Var, null, 0.0f, daVar, 30), 68.0f), bwVar, 0);
            bwVar.p(true);
            bwVar.p(true);
            bwVar.p(true);
            bwVar.p(true);
        } else {
            bwVar.R();
        }
        return x31.a;
    }
}
