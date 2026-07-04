package defpackage;

import java.util.Arrays;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class z40 implements xg, nj0, jh {
    public static final fr0 R = new fr0(1);
    public static final u40 S = new Object();
    public static final b6 T = new b6(4);
    public mm A;
    public m40 B;
    public l51 C;
    public ci D;
    public x40 E;
    public x40 F;
    public boolean G;
    public final lg0 H;
    public final d50 I;
    public n50 J;
    public ng0 K;
    public boolean L;
    public cd0 M;
    public cd0 N;
    public boolean O;
    public int P;
    public boolean Q;
    public final boolean e;
    public int f;
    public boolean g;
    public long h;
    public boolean i;
    public boolean j;
    public boolean k;
    public z40 l;
    public int m;
    public final c4 n;
    public ef0 o;
    public boolean p;
    public z40 q;
    public mj0 r;
    public int s;
    public boolean t;
    public boolean u;
    public nu0 v;
    public boolean w;
    public final ef0 x;
    public boolean y;
    public pc0 z;

    public z40(int i, boolean z) {
        this.e = z;
        this.f = i;
        this.h = 9223372034707292159L;
        this.i = true;
        this.j = true;
        this.n = new c4(11, new ef0(new z40[16]), new n9(9, this));
        this.x = new ef0(new z40[16]);
        this.y = true;
        this.z = R;
        this.A = c50.a;
        this.B = m40.e;
        this.C = S;
        ci.d.getClass();
        this.D = bi.b;
        x40 x40Var = x40.g;
        this.E = x40Var;
        this.F = x40Var;
        this.H = new lg0(this);
        this.I = new d50(this);
        this.L = true;
        this.M = zc0.a;
    }

    public static void R(z40 z40Var, boolean z, int i) {
        boolean z2;
        z40 s;
        boolean z3 = false;
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if ((i & 4) != 0) {
            z3 = true;
        }
        if (z40Var.l == null) {
            q00.b("Lookahead measure cannot be requested on a node that is not a part of the LookaheadScope");
        }
        mj0 mj0Var = z40Var.r;
        if (mj0Var != null && !z40Var.t && !z40Var.e) {
            ((b4) mj0Var).B(z40Var, true, z, z2);
            if (z3) {
                ub0 ub0Var = z40Var.I.q;
                ub0Var.getClass();
                d50 d50Var = ub0Var.j;
                z40 s2 = d50Var.a.s();
                x40 x40Var = d50Var.a.E;
                if (s2 != null && x40Var != x40.g) {
                    while (s2.E == x40Var && (s = s2.s()) != null) {
                        s2 = s;
                    }
                    int ordinal = x40Var.ordinal();
                    if (ordinal != 0) {
                        if (ordinal == 1) {
                            if (s2.l != null) {
                                s2.Q(z);
                                return;
                            } else {
                                s2.S(z);
                                return;
                            }
                        }
                        v7.o("Intrinsics isn't used by the parent");
                        return;
                    }
                    if (s2.l != null) {
                        R(s2, z, 6);
                    } else {
                        T(s2, z, 6);
                    }
                }
            }
        }
    }

    public static void T(z40 z40Var, boolean z, int i) {
        boolean z2;
        boolean z3;
        mj0 mj0Var;
        z40 s;
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if ((i & 4) != 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (!z40Var.t && !z40Var.e && (mj0Var = z40Var.r) != null) {
            ((b4) mj0Var).B(z40Var, false, z, z2);
            if (z3) {
                d50 d50Var = z40Var.I.p.j;
                z40 s2 = d50Var.a.s();
                x40 x40Var = d50Var.a.E;
                if (s2 != null && x40Var != x40.g) {
                    while (s2.E == x40Var && (s = s2.s()) != null) {
                        s2 = s;
                    }
                    int ordinal = x40Var.ordinal();
                    if (ordinal != 0) {
                        if (ordinal == 1) {
                            s2.S(z);
                            return;
                        } else {
                            v7.o("Intrinsics isn't used by the parent");
                            return;
                        }
                    }
                    T(s2, z, 6);
                }
            }
        }
    }

    public static void U(z40 z40Var) {
        int i = y40.a[z40Var.I.d.ordinal()];
        d50 d50Var = z40Var.I;
        if (i == 1) {
            if (d50Var.e) {
                R(z40Var, true, 6);
                return;
            }
            if (d50Var.f) {
                z40Var.Q(true);
            }
            if (z40Var.p()) {
                T(z40Var, true, 6);
                return;
            } else {
                if (z40Var.o()) {
                    z40Var.S(true);
                    return;
                }
                return;
            }
        }
        v7.l(d50Var.d, "Unexpected state ");
    }

    private final String j(z40 z40Var) {
        String str;
        StringBuilder sb = new StringBuilder("Cannot insert ");
        sb.append(z40Var);
        sb.append(" because it already has a parent or an owner. This tree: ");
        sb.append(g(0));
        sb.append(" Other tree: ");
        z40 z40Var2 = z40Var.q;
        if (z40Var2 != null) {
            str = z40Var2.g(0);
        } else {
            str = null;
        }
        sb.append(str);
        return sb.toString();
    }

    public final void A() {
        lg0 lg0Var = this.H;
        ng0 ng0Var = lg0Var.d;
        w00 w00Var = lg0Var.c;
        while (ng0Var != w00Var) {
            ng0Var.getClass();
            t40 t40Var = (t40) ng0Var;
            lj0 lj0Var = t40Var.P;
            if (lj0Var != null) {
                ((kx) lj0Var).c();
            }
            ng0Var = t40Var.t;
        }
        lj0 lj0Var2 = lg0Var.c.P;
        if (lj0Var2 != null) {
            ((kx) lj0Var2).c();
        }
    }

    public final void B() {
        if (this.e) {
            z40 s = s();
            if (s != null) {
                s.B();
                return;
            }
            return;
        }
        if (this.l != null) {
            R(this, false, 7);
        } else {
            T(this, false, 7);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [ep0, java.lang.Object] */
    public final void C() {
        if (this.w) {
            return;
        }
        if (this.H.b.j != null || this.N != null) {
            this.u = true;
            return;
        }
        nu0 nu0Var = this.v;
        this.w = true;
        ?? obj = new Object();
        obj.e = new nu0();
        pj0 snapshotObserver = ((b4) c50.a(this)).getSnapshotObserver();
        u3 u3Var = new u3(5, this, obj);
        snapshotObserver.a.b(this, snapshotObserver.d, u3Var);
        this.w = false;
        this.v = (nu0) obj.e;
        this.u = false;
        b4 b4Var = (b4) c50.a(this);
        b4Var.getSemanticsOwner().b(this, nu0Var);
        b4Var.D();
    }

    public final void D() {
        z40 z40Var;
        if (this.m > 0) {
            this.p = true;
        }
        if (this.e && (z40Var = this.q) != null) {
            z40Var.D();
        }
    }

    public final boolean E() {
        if (this.r != null) {
            return true;
        }
        return false;
    }

    public final boolean F() {
        return this.I.p.v;
    }

    public final Boolean G() {
        boolean z;
        ub0 ub0Var = this.I.q;
        if (ub0Var != null) {
            if (ub0Var.t != sb0.g) {
                z = true;
            } else {
                z = false;
            }
            return Boolean.valueOf(z);
        }
        return null;
    }

    @Override // defpackage.nj0
    public final boolean H() {
        return E();
    }

    public final void I() {
        z40 s;
        if (this.E == x40.g) {
            f();
        }
        ub0 ub0Var = this.I.q;
        ub0Var.getClass();
        boolean z = true;
        try {
            ub0Var.k = true;
            if (!ub0Var.o) {
                q00.b("replace() called on item that was not placed");
            }
            ub0Var.E = false;
            if (ub0Var.t == sb0.g) {
                z = false;
            }
            ub0Var.s0(ub0Var.r, ub0Var.s);
            if (z && !ub0Var.E && (s = ub0Var.j.a.s()) != null) {
                s.Q(false);
            }
            ub0Var.k = false;
        } catch (Throwable th) {
            ub0Var.k = false;
            throw th;
        }
    }

    public final void J(int i, int i2, int i3) {
        int i4;
        if (i == i2) {
            return;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            if (i > i2) {
                i4 = i + i5;
            } else {
                i4 = i;
            }
            int i6 = i > i2 ? i2 + i5 : (i2 + i3) - 2;
            c4 c4Var = this.n;
            ef0 ef0Var = (ef0) c4Var.f;
            n9 n9Var = (n9) c4Var.g;
            Object k = ef0Var.k(i4);
            n9Var.a();
            ((ef0) c4Var.f).a(i6, (z40) k);
            n9Var.a();
        }
        M();
        D();
        B();
    }

    public final void K(z40 z40Var) {
        if (z40Var.I.l > 0) {
            this.I.d(r0.l - 1);
        }
        if (this.r != null) {
            z40Var.h();
        }
        z40Var.q = null;
        if (z40Var.P > 0) {
            Y(this.P - 1);
        }
        z40Var.H.d.u = null;
        if (z40Var.e) {
            this.m--;
            ef0 ef0Var = (ef0) z40Var.n.f;
            Object[] objArr = ef0Var.e;
            int i = ef0Var.g;
            for (int i2 = 0; i2 < i; i2++) {
                ((z40) objArr[i2]).H.d.u = null;
            }
        }
        D();
        M();
    }

    public final void L(ng0 ng0Var) {
        yo0 yo0Var;
        boolean z;
        mj0 mj0Var = this.r;
        if (mj0Var != null) {
            yo0Var = ((b4) mj0Var).getRectManager();
        } else {
            yo0Var = null;
        }
        d50 d50Var = this.I;
        int i = 0;
        if (d50Var.d == v40.i && !p() && !o()) {
            z = false;
        } else {
            z = true;
        }
        if (this.k && yo0Var != null) {
            if (ng0Var == this.H.d) {
                this.j = true;
                if (!z) {
                    yo0Var.f(this);
                }
            } else {
                this.i = true;
                ef0 w = w();
                Object[] objArr = w.e;
                int i2 = w.g;
                for (int i3 = 0; i3 < i2; i3++) {
                    z40 z40Var = (z40) objArr[i3];
                    z40Var.j = true;
                    if (!z) {
                        yo0Var.f(z40Var);
                    }
                }
                if (this.k) {
                    yo0Var.e = true;
                    p5 p5Var = yo0Var.b;
                    int i4 = this.f & 33554431;
                    long[] jArr = (long[]) p5Var.b;
                    int i5 = p5Var.a;
                    while (true) {
                        if (i >= jArr.length - 2 || i >= i5) {
                            break;
                        }
                        int i6 = i + 2;
                        long j = jArr[i6];
                        if ((((int) j) & 33554431) == i4) {
                            jArr[i6] = (((j >> 63) & 1) << 60) | j;
                            break;
                        }
                        i += 3;
                    }
                }
                yo0Var.i();
            }
        }
        d50Var.p.t0();
    }

    public final void M() {
        if (this.e) {
            z40 s = s();
            if (s != null) {
                s.M();
                return;
            }
            return;
        }
        this.y = true;
    }

    public final void N() {
        c4 c4Var = this.n;
        int i = ((ef0) c4Var.f).g;
        while (true) {
            i--;
            ef0 ef0Var = (ef0) c4Var.f;
            if (-1 < i) {
                K((z40) ef0Var.e[i]);
            } else {
                ef0Var.g();
                ((n9) c4Var.g).a();
                return;
            }
        }
    }

    public final void O(int i, int i2) {
        if (i2 < 0) {
            q00.a("count (" + i2 + ") must be greater than 0");
        }
        int i3 = (i2 + i) - 1;
        if (i > i3) {
            return;
        }
        while (true) {
            c4 c4Var = this.n;
            K((z40) ((ef0) c4Var.f).e[i3]);
            Object k = ((ef0) c4Var.f).k(i3);
            ((n9) c4Var.g).a();
            if (i3 != i) {
                i3--;
            } else {
                return;
            }
        }
    }

    public final void P() {
        z40 s;
        if (this.E == x40.g) {
            f();
        }
        oc0 oc0Var = this.I.p;
        d50 d50Var = oc0Var.j;
        try {
            oc0Var.k = true;
            if (!oc0Var.o) {
                q00.b("replace called on unplaced item");
            }
            boolean z = oc0Var.v;
            oc0Var.r0(oc0Var.q, oc0Var.s, oc0Var.r);
            if (z && !oc0Var.I && (s = d50Var.a.s()) != null) {
                s.S(false);
            }
        } finally {
        }
    }

    public final void Q(boolean z) {
        mj0 mj0Var;
        if (!this.e && (mj0Var = this.r) != null) {
            ((b4) mj0Var).C(this, true, z);
        }
    }

    public final void S(boolean z) {
        mj0 mj0Var;
        if (!this.e && (mj0Var = this.r) != null) {
            ((b4) mj0Var).C(this, false, z);
        }
    }

    public final void V() {
        ef0 w = w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            z40 z40Var = (z40) objArr[i2];
            x40 x40Var = z40Var.F;
            z40Var.E = x40Var;
            if (x40Var != x40.g) {
                z40Var.V();
            }
        }
    }

    public final void W(Throwable th) {
        ci ciVar = this.D;
        qy0 qy0Var = xh.a;
        ll0 ll0Var = (ll0) ciVar;
        ll0Var.getClass();
        wh whVar = (wh) jc0.A(ll0Var, qy0Var);
        if (whVar != null) {
            k81.O(th, new f9(2, whVar, this));
            throw th;
        }
        throw th;
    }

    public final void X(mm mmVar) {
        if (!o20.e(this.A, mmVar)) {
            this.A = mmVar;
            B();
            z40 s = s();
            if (s != null) {
                s.z();
            } else {
                mj0 mj0Var = this.r;
                if (mj0Var != null) {
                    ((b4) mj0Var).invalidate();
                }
            }
            A();
            for (bd0 bd0Var = this.H.f; bd0Var != null; bd0Var = bd0Var.j) {
                bd0Var.u0();
            }
        }
    }

    public final void Y(int i) {
        z40 s;
        z40 s2;
        int i2 = this.P;
        if (i2 != i) {
            if (i > 0 && i2 == 0 && (s2 = s()) != null) {
                s2.Y(s2.P + 1);
            }
            if (i == 0 && this.P > 0 && (s = s()) != null) {
                s.Y(s.P - 1);
            }
            this.P = i;
        }
    }

    public final void Z(z40 z40Var) {
        if (!o20.e(z40Var, this.l)) {
            this.l = z40Var;
            d50 d50Var = this.I;
            if (z40Var != null) {
                if (d50Var.q == null) {
                    d50Var.q = new ub0(d50Var);
                }
                lg0 lg0Var = this.H;
                ng0 ng0Var = lg0Var.c.t;
                for (ng0 ng0Var2 = lg0Var.d; !o20.e(ng0Var2, ng0Var) && ng0Var2 != null; ng0Var2 = ng0Var2.t) {
                    ng0Var2.K0();
                }
            } else {
                d50Var.q = null;
                d50Var.f = false;
                d50Var.e = false;
            }
            B();
        }
    }

    @Override // defpackage.xg
    public final void a() {
        n50 n50Var = this.J;
        if (n50Var != null) {
            n50Var.a();
        }
        lg0 lg0Var = this.H;
        ng0 ng0Var = lg0Var.c.t;
        for (ng0 ng0Var2 = lg0Var.d; !o20.e(ng0Var2, ng0Var) && ng0Var2 != null; ng0Var2 = ng0Var2.t) {
            ng0Var2.c1();
        }
    }

    public final void a0(cd0 cd0Var) {
        if (this.e && this.M != zc0.a) {
            q00.a("Modifiers are not supported on virtual LayoutNodes");
        }
        if (this.Q) {
            q00.a("modifier is updated when deactivated");
        }
        if (E()) {
            c(cd0Var);
            if (this.u) {
                C();
                return;
            }
            return;
        }
        this.N = cd0Var;
    }

    @Override // defpackage.xg
    public final void b() {
        g3 g3Var;
        n50 n50Var = this.J;
        if (n50Var != null) {
            n50Var.i(true);
        }
        this.Q = true;
        bd0 bd0Var = this.H.e;
        for (bd0 bd0Var2 = bd0Var; bd0Var2 != null; bd0Var2 = bd0Var2.i) {
            if (bd0Var2.r) {
                bd0Var2.y0();
            }
        }
        for (bd0 bd0Var3 = bd0Var; bd0Var3 != null; bd0Var3 = bd0Var3.i) {
            if (bd0Var3.r) {
                bd0Var3.A0();
            }
        }
        while (bd0Var != null) {
            if (bd0Var.r) {
                bd0Var.s0();
            }
            bd0Var = bd0Var.i;
        }
        if (E()) {
            this.v = null;
            this.u = false;
        }
        mj0 mj0Var = this.r;
        if (mj0Var != null) {
            b4 b4Var = (b4) mj0Var;
            if (b4.k() && (g3Var = b4Var.Q) != null && g3Var.k.e(this.f)) {
                g3Var.e.k(g3Var.g, this.f, false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [bd0] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [bd0] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [ef0] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [ef0] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    public final void b0(l51 l51Var) {
        if (!o20.e(this.C, l51Var)) {
            this.C = l51Var;
            bd0 bd0Var = this.H.f;
            if ((bd0Var.h & 16) != 0) {
                while (bd0Var != null) {
                    if ((bd0Var.g & 16) != 0) {
                        jm jmVar = bd0Var;
                        ?? r2 = 0;
                        while (jmVar != 0) {
                            if (jmVar instanceof xm0) {
                                ((xm0) jmVar).c0();
                            } else if ((jmVar.g & 16) != 0 && (jmVar instanceof jm)) {
                                bd0 bd0Var2 = jmVar.t;
                                int i = 0;
                                jmVar = jmVar;
                                r2 = r2;
                                while (bd0Var2 != null) {
                                    if ((bd0Var2.g & 16) != 0) {
                                        i++;
                                        r2 = r2;
                                        if (i == 1) {
                                            jmVar = bd0Var2;
                                        } else {
                                            if (r2 == 0) {
                                                r2 = new ef0(new bd0[16]);
                                            }
                                            if (jmVar != 0) {
                                                r2.b(jmVar);
                                                jmVar = 0;
                                            }
                                            r2.b(bd0Var2);
                                        }
                                    }
                                    bd0Var2 = bd0Var2.j;
                                    jmVar = jmVar;
                                    r2 = r2;
                                }
                                if (i == 1) {
                                }
                            }
                            jmVar = k81.h(r2);
                        }
                    }
                    if ((bd0Var.h & 16) != 0) {
                        bd0Var = bd0Var.j;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v3, types: [ng0, bd0] */
    public final void c(cd0 cd0Var) {
        int i;
        ?? r7;
        boolean z;
        lg0 lg0Var;
        kg0 kg0Var;
        ef0 ef0Var;
        boolean z2;
        w00 w00Var;
        boolean z3;
        boolean z4;
        ef0 ef0Var2;
        boolean z5;
        boolean z6;
        q2 q2Var;
        lg0 lg0Var2 = this.H;
        boolean d = lg0Var2.d(16);
        bd0 bd0Var = lg0Var2.e;
        boolean d2 = lg0Var2.d(1024);
        this.M = cd0Var;
        w00 w00Var2 = lg0Var2.c;
        z40 z40Var = lg0Var2.a;
        bd0 bd0Var2 = lg0Var2.f;
        kg0 kg0Var2 = lg0Var2.b;
        if (bd0Var2 == kg0Var2) {
            q00.b("padChain called on already padded chain");
        }
        bd0 bd0Var3 = lg0Var2.f;
        bd0Var3.i = kg0Var2;
        kg0Var2.j = bd0Var3;
        ef0 ef0Var3 = lg0Var2.g;
        if (ef0Var3 != null) {
            i = ef0Var3.g;
        } else {
            i = 0;
        }
        ef0 ef0Var4 = lg0Var2.h;
        if (ef0Var4 == null) {
            ef0Var4 = new ef0(new ad0[16]);
        }
        ef0 ef0Var5 = lg0Var2.i;
        ef0Var5.b(cd0Var);
        q2 q2Var2 = null;
        while (true) {
            int i2 = ef0Var5.g;
            if (i2 == 0) {
                break;
            }
            cd0 cd0Var2 = (cd0) ef0Var5.k(i2 - 1);
            if (cd0Var2 instanceof lf) {
                lf lfVar = (lf) cd0Var2;
                ef0Var5.b(lfVar.b);
                ef0Var5.b(lfVar.a);
            } else if (cd0Var2 instanceof ad0) {
                ef0Var4.b(cd0Var2);
            } else {
                if (q2Var2 == null) {
                    q2Var = new q2(18, ef0Var4);
                    q2Var2 = q2Var;
                } else {
                    q2Var = q2Var2;
                }
                cd0Var2.d(q2Var);
            }
        }
        int i3 = ef0Var4.g;
        if (i3 == i) {
            bd0 bd0Var4 = kg0Var2.j;
            int i4 = 0;
            while (bd0Var4 != null && i4 < i) {
                if (ef0Var3 != null) {
                    ad0 ad0Var = (ad0) ef0Var3.e[i4];
                    ad0 ad0Var2 = (ad0) ef0Var4.e[i4];
                    if (o20.e(ad0Var, ad0Var2)) {
                        ef0Var2 = ef0Var3;
                        z6 = 2;
                    } else {
                        ef0Var2 = ef0Var3;
                        if (ad0Var.getClass() == ad0Var2.getClass()) {
                            z6 = true;
                        } else {
                            z6 = false;
                        }
                    }
                    if (z6) {
                        if (z6) {
                            lg0.h(ad0Var, ad0Var2, bd0Var4);
                        }
                        bd0Var4 = bd0Var4.j;
                        i4++;
                        ef0Var3 = ef0Var2;
                    } else {
                        bd0Var4 = bd0Var4.i;
                        break;
                    }
                } else {
                    throw d3.t("expected prior modifier list to be non-empty");
                }
            }
            ef0Var2 = ef0Var3;
            if (i4 < i) {
                if (ef0Var2 != null) {
                    if (bd0Var4 != null) {
                        if (z40Var.N != null) {
                            z5 = true;
                        } else {
                            z5 = false;
                        }
                        bd0 bd0Var5 = bd0Var4;
                        lg0Var = lg0Var2;
                        ef0Var = ef0Var4;
                        ef0Var3 = ef0Var2;
                        z4 = false;
                        lg0Var.f(i4, ef0Var3, ef0Var, bd0Var5, !z5);
                        kg0Var = kg0Var2;
                        z2 = true;
                        r7 = z4;
                    } else {
                        throw d3.t("structuralUpdate requires a non-null tail");
                    }
                } else {
                    throw d3.t("expected prior modifier list to be non-empty");
                }
            } else {
                lg0Var2 = lg0Var2;
                ef0Var3 = ef0Var2;
                z3 = false;
                lg0Var = lg0Var2;
                kg0Var = kg0Var2;
                ef0Var = ef0Var4;
                z2 = false;
                r7 = z3;
            }
        } else {
            r7 = 0;
            z4 = false;
            z3 = false;
            cd0 cd0Var3 = z40Var.N;
            if (cd0Var3 != null && i == 0) {
                bd0 bd0Var6 = kg0Var2;
                for (int i5 = 0; i5 < ef0Var4.g; i5++) {
                    bd0Var6 = lg0.b((ad0) ef0Var4.e[i5], bd0Var6);
                }
                int i6 = 0;
                for (bd0 bd0Var7 = bd0Var.i; bd0Var7 != null && bd0Var7 != kg0Var2; bd0Var7 = bd0Var7.i) {
                    i6 |= bd0Var7.g;
                    bd0Var7.h = i6;
                }
                lg0Var = lg0Var2;
                kg0Var = kg0Var2;
                ef0Var = ef0Var4;
                z2 = true;
                r7 = z4;
            } else if (i3 == 0) {
                if (ef0Var3 != null) {
                    bd0 bd0Var8 = kg0Var2.j;
                    for (int i7 = 0; bd0Var8 != null && i7 < ef0Var3.g; i7++) {
                        bd0Var8 = lg0.c(bd0Var8).j;
                    }
                    z40 s = z40Var.s();
                    if (s != null) {
                        w00Var = s.H.c;
                    } else {
                        w00Var = null;
                    }
                    w00Var2.u = w00Var;
                    lg0Var2.d = w00Var2;
                    lg0Var = lg0Var2;
                    kg0Var = kg0Var2;
                    ef0Var = ef0Var4;
                    z2 = false;
                    r7 = z3;
                } else {
                    throw d3.t("expected prior modifier list to be non-empty");
                }
            } else {
                if (ef0Var3 == null) {
                    ef0Var3 = new ef0(new ad0[16]);
                }
                if (cd0Var3 != null) {
                    z = true;
                } else {
                    z = false;
                }
                lg0Var = lg0Var2;
                kg0Var = kg0Var2;
                ef0Var = ef0Var4;
                lg0Var.f(0, ef0Var3, ef0Var, kg0Var, !z);
                z2 = true;
            }
        }
        lg0Var.g = ef0Var;
        if (ef0Var3 != null) {
            ef0Var3.g();
        } else {
            ef0Var3 = r7;
        }
        lg0Var.h = ef0Var3;
        bd0 bd0Var9 = kg0Var.j;
        if (bd0Var9 != null) {
            bd0Var = bd0Var9;
        }
        bd0Var.i = r7;
        kg0Var.j = r7;
        kg0Var.h = -1;
        kg0Var.l = r7;
        if (bd0Var == kg0Var) {
            q00.b("trimChain did not update the head");
        }
        lg0Var.f = bd0Var;
        if (z2) {
            lg0Var.g();
        }
        boolean d3 = lg0Var.d(16);
        boolean d4 = lg0Var.d(1024);
        this.I.j();
        if (this.l == null && lg0Var.d(512)) {
            Z(this);
        }
        if (d != d3 || d2 != d4) {
            yo0 rectManager = ((b4) c50.a(this)).getRectManager();
            rectManager.getClass();
            if (E()) {
                p5 p5Var = rectManager.b;
                int i8 = this.f & 33554431;
                long[] jArr = (long[]) p5Var.b;
                int i9 = p5Var.a;
                for (int i10 = 0; i10 < jArr.length - 2 && i10 < i9; i10 += 3) {
                    int i11 = i10 + 2;
                    long j = jArr[i11];
                    if ((((int) j) & 33554431) == i8) {
                        jArr[i11] = ((-6917529027641081857L) & j) | ((d4 ? 1L : 0L) * 2305843009213693952L) | ((d3 ? 1L : 0L) * 4611686018427387904L);
                        return;
                    }
                }
            }
        }
    }

    public final void c0() {
        if (this.m > 0 && this.p) {
            this.p = false;
            ef0 ef0Var = this.o;
            if (ef0Var == null) {
                ef0Var = new ef0(new z40[16]);
                this.o = ef0Var;
            }
            ef0Var.g();
            ef0 ef0Var2 = (ef0) this.n.f;
            Object[] objArr = ef0Var2.e;
            int i = ef0Var2.g;
            for (int i2 = 0; i2 < i; i2++) {
                z40 z40Var = (z40) objArr[i2];
                if (z40Var.e) {
                    ef0Var.c(ef0Var.g, z40Var.w());
                } else {
                    ef0Var.b(z40Var);
                }
            }
            d50 d50Var = this.I;
            d50Var.p.C = true;
            ub0 ub0Var = d50Var.q;
            if (ub0Var != null) {
                ub0Var.w = true;
            }
        }
    }

    public final void d(mj0 mj0Var) {
        w00 w00Var;
        int i;
        z40 z40Var;
        g3 g3Var;
        nu0 u;
        mj0 mj0Var2;
        String str;
        if (this.r != null) {
            q00.b("Cannot attach " + this + " as it already is attached.  Tree: " + g(0));
        }
        z40 z40Var2 = this.q;
        if (z40Var2 != null && !o20.e(z40Var2.r, mj0Var)) {
            StringBuilder sb = new StringBuilder("Attaching to a different owner(");
            sb.append(mj0Var);
            sb.append(") than the parent's owner(");
            z40 s = s();
            if (s != null) {
                mj0Var2 = s.r;
            } else {
                mj0Var2 = null;
            }
            sb.append(mj0Var2);
            sb.append("). This tree: ");
            sb.append(g(0));
            sb.append(" Parent tree: ");
            z40 z40Var3 = this.q;
            if (z40Var3 != null) {
                str = z40Var3.g(0);
            } else {
                str = null;
            }
            sb.append(str);
            q00.b(sb.toString());
        }
        z40 s2 = s();
        d50 d50Var = this.I;
        if (s2 == null) {
            d50Var.p.v = true;
            ((b4) mj0Var).getRectManager().f(this);
            ub0 ub0Var = d50Var.q;
            if (ub0Var != null) {
                ub0Var.t = sb0.e;
            }
        }
        lg0 lg0Var = this.H;
        ng0 ng0Var = lg0Var.d;
        if (s2 != null) {
            w00Var = s2.H.c;
        } else {
            w00Var = null;
        }
        ng0Var.u = w00Var;
        this.r = mj0Var;
        if (s2 != null) {
            i = s2.s;
        } else {
            i = -1;
        }
        this.s = i + 1;
        cd0 cd0Var = this.N;
        if (cd0Var != null) {
            c(cd0Var);
        }
        this.N = null;
        ((b4) mj0Var).m7getLayoutNodes().h(this.f, this);
        z40 z40Var4 = this.q;
        if (z40Var4 == null || (z40Var = z40Var4.l) == null) {
            z40Var = this.l;
        }
        Z(z40Var);
        if (this.l == null && lg0Var.d(512)) {
            Z(this);
        }
        if (!this.Q) {
            for (bd0 bd0Var = lg0Var.f; bd0Var != null; bd0Var = bd0Var.j) {
                bd0Var.r0();
            }
        }
        ef0 ef0Var = (ef0) this.n.f;
        Object[] objArr = ef0Var.e;
        int i2 = ef0Var.g;
        for (int i3 = 0; i3 < i2; i3++) {
            ((z40) objArr[i3]).d(mj0Var);
        }
        if (!this.Q) {
            lg0Var.e();
        }
        B();
        if (s2 != null) {
            s2.B();
        }
        d50Var.j();
        if (!this.Q && lg0Var.d(8)) {
            C();
        }
        b4 b4Var = (b4) mj0Var;
        if (b4.k() && (g3Var = b4Var.Q) != null && (u = u()) != null && u.e.b(wu0.q)) {
            g3Var.k.a(this.f);
            g3Var.e.k(g3Var.g, this.f, true);
        }
    }

    public final void e() {
        this.F = this.E;
        x40 x40Var = x40.g;
        this.E = x40Var;
        ef0 w = w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            z40 z40Var = (z40) objArr[i2];
            if (z40Var.E != x40Var) {
                z40Var.e();
            }
        }
    }

    public final void f() {
        this.F = this.E;
        this.E = x40.g;
        ef0 w = w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            z40 z40Var = (z40) objArr[i2];
            if (z40Var.E == x40.f) {
                z40Var.f();
            }
        }
    }

    public final String g(int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("  ");
        }
        sb.append("|-");
        sb.append(toString());
        sb.append('\n');
        ef0 w = w();
        Object[] objArr = w.e;
        int i3 = w.g;
        for (int i4 = 0; i4 < i3; i4++) {
            sb.append(((z40) objArr[i4]).g(i + 1));
        }
        String sb2 = sb.toString();
        if (i == 0) {
            return sb2.substring(0, sb2.length() - 1);
        }
        return sb2;
    }

    public final void h() {
        g3 g3Var;
        a50 a50Var;
        mj0 mj0Var = this.r;
        String str = null;
        if (mj0Var == null) {
            StringBuilder sb = new StringBuilder("Cannot detach node that is already detached!  Tree: ");
            z40 s = s();
            if (s != null) {
                str = s.g(0);
            }
            sb.append(str);
            q00.c(sb.toString());
            throw new RuntimeException();
        }
        z40 s2 = s();
        d50 d50Var = this.I;
        if (s2 != null) {
            s2.z();
            s2.B();
            oc0 oc0Var = d50Var.p;
            x40 x40Var = x40.g;
            oc0Var.p = x40Var;
            ub0 ub0Var = d50Var.q;
            if (ub0Var != null) {
                ub0Var.n = x40Var;
            }
        }
        a50 a50Var2 = d50Var.p.A;
        a50Var2.b = true;
        a50Var2.c = false;
        a50Var2.d = false;
        a50Var2.e = false;
        a50Var2.f = null;
        ub0 ub0Var2 = d50Var.q;
        if (ub0Var2 != null && (a50Var = ub0Var2.u) != null) {
            a50Var.b = true;
            a50Var.c = false;
            a50Var.d = false;
            a50Var.e = false;
            a50Var.f = null;
        }
        lg0 lg0Var = this.H;
        bd0 bd0Var = lg0Var.e;
        ng0 ng0Var = lg0Var.c.t;
        for (ng0 ng0Var2 = lg0Var.d; !o20.e(ng0Var2, ng0Var) && ng0Var2 != null; ng0Var2 = ng0Var2.t) {
            ng0Var2.i1();
            if (ng0Var2.s.F()) {
                ng0Var2.d1();
            }
        }
        for (bd0 bd0Var2 = bd0Var; bd0Var2 != null; bd0Var2 = bd0Var2.i) {
            if (bd0Var2.r) {
                bd0Var2.A0();
            }
        }
        this.t = true;
        ef0 ef0Var = (ef0) this.n.f;
        Object[] objArr = ef0Var.e;
        int i = ef0Var.g;
        for (int i2 = 0; i2 < i; i2++) {
            ((z40) objArr[i2]).h();
        }
        this.t = false;
        while (bd0Var != null) {
            if (bd0Var.r) {
                bd0Var.s0();
            }
            bd0Var = bd0Var.i;
        }
        b4 b4Var = (b4) mj0Var;
        b4Var.m7getLayoutNodes().g(this.f);
        mc0 mc0Var = b4Var.c0;
        r7 r7Var = mc0Var.b;
        ((j2) r7Var.f).m(this);
        ((j2) r7Var.g).m(this);
        ((j2) r7Var.h).m(this);
        ((ef0) mc0Var.e.f).j(this);
        b4Var.R = true;
        if (b4.k() && (g3Var = b4Var.Q) != null && g3Var.k.e(this.f)) {
            g3Var.e.k(g3Var.g, this.f, false);
        }
        b4Var.getRectManager().g(this);
        this.r = null;
        Z(null);
        this.s = 0;
        oc0 oc0Var2 = d50Var.p;
        oc0Var2.m = Integer.MAX_VALUE;
        oc0Var2.l = Integer.MAX_VALUE;
        oc0Var2.v = false;
        ub0 ub0Var3 = d50Var.q;
        if (ub0Var3 != null) {
            ub0Var3.m = Integer.MAX_VALUE;
            ub0Var3.l = Integer.MAX_VALUE;
            ub0Var3.t = sb0.g;
        }
        if (lg0Var.d(8)) {
            nu0 nu0Var = this.v;
            this.v = null;
            this.u = false;
            b4Var.getSemanticsOwner().b(this, nu0Var);
            b4Var.D();
        }
    }

    public final void i(uc ucVar, hx hxVar) {
        try {
            this.H.d.I0(ucVar, hxVar);
        } catch (Throwable th) {
            W(th);
            throw null;
        }
    }

    public final void k() {
        si siVar;
        if (this.l != null) {
            R(this, false, 5);
        } else {
            T(this, false, 5);
        }
        oc0 oc0Var = this.I.p;
        if (oc0Var.n) {
            siVar = new si(oc0Var.h);
        } else {
            siVar = null;
        }
        mj0 mj0Var = this.r;
        if (siVar != null) {
            if (mj0Var != null) {
                ((b4) mj0Var).y(this, siVar.a);
                return;
            }
            return;
        }
        if (mj0Var != null) {
            ((b4) mj0Var).x(true);
        }
    }

    public final List l() {
        ub0 ub0Var = this.I.q;
        ub0Var.getClass();
        ef0 ef0Var = ub0Var.v;
        d50 d50Var = ub0Var.j;
        d50Var.a.m();
        if (!ub0Var.w) {
            return ef0Var.f();
        }
        z40 z40Var = d50Var.a;
        ef0 w = z40Var.w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            z40 z40Var2 = (z40) objArr[i2];
            if (ef0Var.g <= i2) {
                ub0 ub0Var2 = z40Var2.I.q;
                ub0Var2.getClass();
                ef0Var.b(ub0Var2);
            } else {
                ub0 ub0Var3 = z40Var2.I.q;
                ub0Var3.getClass();
                Object[] objArr2 = ef0Var.e;
                Object obj = objArr2[i2];
                objArr2[i2] = ub0Var3;
            }
        }
        ef0Var.l(((bf0) z40Var.m()).e.g, ef0Var.g);
        ub0Var.w = false;
        return ef0Var.f();
    }

    public final List m() {
        return w().f();
    }

    public final List n() {
        return ((ef0) this.n.f).f();
    }

    public final boolean o() {
        return this.I.p.y;
    }

    public final boolean p() {
        return this.I.p.x;
    }

    public final x40 q() {
        return this.I.p.p;
    }

    public final x40 r() {
        x40 x40Var;
        ub0 ub0Var = this.I.q;
        if (ub0Var != null && (x40Var = ub0Var.n) != null) {
            return x40Var;
        }
        return x40.g;
    }

    public final z40 s() {
        z40 z40Var = this.q;
        while (z40Var != null && z40Var.e) {
            z40Var = z40Var.q;
        }
        return z40Var;
    }

    public final int t() {
        return this.I.p.m;
    }

    public final String toString() {
        return n30.F(this) + " children: " + ((bf0) m()).e.g + " measurePolicy: " + this.z + " deactivated: " + this.Q;
    }

    public final nu0 u() {
        if (E() && !this.Q && this.H.d(8)) {
            return this.v;
        }
        return null;
    }

    public final ef0 v() {
        boolean z = this.y;
        ef0 ef0Var = this.x;
        if (z) {
            ef0Var.g();
            ef0Var.c(ef0Var.g, w());
            Arrays.sort(ef0Var.e, 0, ef0Var.g, T);
            this.y = false;
        }
        return ef0Var;
    }

    public final ef0 w() {
        c0();
        if (this.m == 0) {
            return (ef0) this.n.f;
        }
        ef0 ef0Var = this.o;
        ef0Var.getClass();
        return ef0Var;
    }

    public final void x(long j, py pyVar, int i, boolean z) {
        lg0 lg0Var = this.H;
        ng0 ng0Var = lg0Var.d;
        pq0 pq0Var = ng0.Q;
        lg0Var.d.U0(ng0.S, ng0Var.M0(j), pyVar, i, z);
    }

    public final void y(int i, z40 z40Var) {
        if (z40Var.q != null && z40Var.r != null) {
            q00.b(j(z40Var));
        }
        z40Var.q = this;
        c4 c4Var = this.n;
        ((ef0) c4Var.f).a(i, z40Var);
        ((n9) c4Var.g).a();
        M();
        if (z40Var.e) {
            this.m++;
        }
        D();
        mj0 mj0Var = this.r;
        if (mj0Var != null) {
            z40Var.d(mj0Var);
        }
        if (z40Var.I.l > 0) {
            d50 d50Var = this.I;
            d50Var.d(d50Var.l + 1);
        }
        if (z40Var.P > 0) {
            Y(this.P + 1);
        }
    }

    public final void z() {
        lj0 lj0Var;
        if (this.L) {
            lg0 lg0Var = this.H;
            ng0 ng0Var = lg0Var.c;
            ng0 ng0Var2 = lg0Var.d.u;
            this.K = null;
            while (true) {
                if (o20.e(ng0Var, ng0Var2)) {
                    break;
                }
                if (ng0Var != null) {
                    lj0Var = ng0Var.P;
                } else {
                    lj0Var = null;
                }
                if (lj0Var != null) {
                    this.K = ng0Var;
                    break;
                } else if (ng0Var != null) {
                    ng0Var = ng0Var.u;
                } else {
                    ng0Var = null;
                }
            }
            this.L = false;
        }
        ng0 ng0Var3 = this.K;
        if (ng0Var3 != null && ng0Var3.P == null) {
            throw d3.t("layer was not set. This error is usually caused by operating off of the UI thread. Did you call invalidate() instead of postInvalidate()?");
        }
        if (ng0Var3 != null) {
            ng0Var3.W0();
            return;
        }
        z40 s = s();
        if (s != null) {
            s.z();
            return;
        }
        mj0 mj0Var = this.r;
        if (mj0Var != null) {
            ((b4) mj0Var).invalidate();
        }
    }

    public z40(int i) {
        this(pu0.a.addAndGet(1), (i & 1) == 0);
    }
}
