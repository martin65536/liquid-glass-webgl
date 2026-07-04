package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class oc0 extends em0 implements kc0, a3, od0 {
    public boolean D;
    public float H;
    public boolean I;
    public gv J;
    public float L;
    public boolean N;
    public final d50 j;
    public boolean k;
    public boolean n;
    public boolean o;
    public gv r;
    public float s;
    public Object u;
    public boolean v;
    public boolean w;
    public boolean x;
    public boolean y;
    public boolean z;
    public int l = Integer.MAX_VALUE;
    public int m = Integer.MAX_VALUE;
    public x40 p = x40.g;
    public long q = 0;
    public boolean t = true;
    public final a50 A = new a50(this, 0);
    public final ef0 B = new ef0(new oc0[16]);
    public boolean C = true;
    public long E = ti.b(0, 0, 15);
    public final nc0 F = new nc0(this, 1);
    public final nc0 G = new nc0(this, 0);
    public long K = 0;
    public final nc0 M = new nc0(this, 2);

    public oc0(d50 d50Var) {
        this.j = d50Var;
    }

    @Override // defpackage.em0, defpackage.kc0
    public final Object A() {
        return this.u;
    }

    @Override // defpackage.od0
    public final void E(boolean z) {
        d50 d50Var = this.j;
        if (z != d50Var.a().m) {
            d50Var.a().m = z;
            this.N = true;
        }
    }

    @Override // defpackage.a3
    public final w00 I() {
        return this.j.a.H.c;
    }

    @Override // defpackage.a3
    public final a3 K() {
        d50 d50Var;
        z40 s = this.j.a.s();
        if (s != null && (d50Var = s.I) != null) {
            return d50Var.p;
        }
        return null;
    }

    @Override // defpackage.a3
    public final void N() {
        si siVar;
        boolean z;
        this.D = true;
        a50 a50Var = this.A;
        a50Var.h();
        boolean z2 = this.y;
        d50 d50Var = this.j;
        if (z2) {
            ef0 w = d50Var.a.w();
            Object[] objArr = w.e;
            int i = w.g;
            for (int i2 = 0; i2 < i; i2++) {
                z40 z40Var = (z40) objArr[i2];
                boolean p = z40Var.p();
                d50 d50Var2 = z40Var.I;
                if (p && z40Var.q() == x40.e) {
                    oc0 oc0Var = d50Var2.p;
                    if (oc0Var.n) {
                        siVar = new si(oc0Var.h);
                    } else {
                        siVar = null;
                    }
                    if (siVar != null) {
                        if (z40Var.E == x40.g) {
                            z40Var.e();
                        }
                        z = d50Var2.p.s0(siVar.a);
                    } else {
                        z = false;
                    }
                    if (z) {
                        z40.T(d50Var.a, false, 7);
                    }
                }
            }
        }
        if (this.z || (!I().o && this.y)) {
            this.y = false;
            v40 v40Var = d50Var.d;
            d50Var.d = v40.g;
            d50Var.g(false);
            z40 z40Var2 = d50Var.a;
            pj0 snapshotObserver = ((b4) c50.a(z40Var2)).getSnapshotObserver();
            snapshotObserver.a.b(z40Var2, snapshotObserver.e, this.G);
            d50Var.d = v40Var;
            this.z = false;
        }
        if (a50Var.b && a50Var.e()) {
            a50Var.g();
        }
        this.D = false;
    }

    @Override // defpackage.a3
    public final int Y() {
        return this.m;
    }

    @Override // defpackage.a3
    public final void c0() {
        z40.T(this.j.a, false, 7);
    }

    @Override // defpackage.em0
    public final int f0() {
        return this.j.a().f0();
    }

    @Override // defpackage.em0
    public final int g0() {
        return this.j.a().g0();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0032 A[Catch: all -> 0x001b, TryCatch #0 {all -> 0x001b, blocks: (B:3:0x0007, B:5:0x0012, B:7:0x0016, B:10:0x002e, B:12:0x0032, B:14:0x003a, B:17:0x0043, B:18:0x0045, B:20:0x0049, B:22:0x004f, B:24:0x0057, B:26:0x0065, B:28:0x0070, B:29:0x0074, B:30:0x005b, B:31:0x0088, B:33:0x008c, B:35:0x0090, B:36:0x0095, B:40:0x001e, B:42:0x0022, B:44:0x0026, B:46:0x002a), top: B:2:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0070 A[Catch: all -> 0x001b, TryCatch #0 {all -> 0x001b, blocks: (B:3:0x0007, B:5:0x0012, B:7:0x0016, B:10:0x002e, B:12:0x0032, B:14:0x003a, B:17:0x0043, B:18:0x0045, B:20:0x0049, B:22:0x004f, B:24:0x0057, B:26:0x0065, B:28:0x0070, B:29:0x0074, B:30:0x005b, B:31:0x0088, B:33:0x008c, B:35:0x0090, B:36:0x0095, B:40:0x001e, B:42:0x0022, B:44:0x0026, B:46:0x002a), top: B:2:0x0007 }] */
    @Override // defpackage.em0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void i0(long r9, float r11, defpackage.gv r12) {
        /*
            r8 = this;
            d50 r0 = r8.j
            z40 r1 = r0.a
            z40 r2 = r0.a
            r3 = 1
            r8.w = r3     // Catch: java.lang.Throwable -> L1b
            long r4 = r8.q     // Catch: java.lang.Throwable -> L1b
            boolean r4 = defpackage.v10.a(r9, r4)     // Catch: java.lang.Throwable -> L1b
            r5 = 0
            if (r4 == 0) goto L1e
            gv r4 = r8.r     // Catch: java.lang.Throwable -> L1b
            if (r12 != r4) goto L1e
            boolean r4 = r8.N     // Catch: java.lang.Throwable -> L1b
            if (r4 == 0) goto L2e
            goto L1e
        L1b:
            r8 = move-exception
            goto L99
        L1e:
            boolean r4 = r0.k     // Catch: java.lang.Throwable -> L1b
            if (r4 != 0) goto L2a
            boolean r4 = r0.j     // Catch: java.lang.Throwable -> L1b
            if (r4 != 0) goto L2a
            boolean r4 = r8.N     // Catch: java.lang.Throwable -> L1b
            if (r4 == 0) goto L2e
        L2a:
            r8.y = r3     // Catch: java.lang.Throwable -> L1b
            r8.N = r5     // Catch: java.lang.Throwable -> L1b
        L2e:
            ub0 r4 = r0.q     // Catch: java.lang.Throwable -> L1b
            if (r4 == 0) goto L45
            d50 r6 = r4.j     // Catch: java.lang.Throwable -> L1b
            sb0 r4 = r4.t     // Catch: java.lang.Throwable -> L1b
            sb0 r7 = defpackage.sb0.g     // Catch: java.lang.Throwable -> L1b
            if (r4 != r7) goto L45
            z40 r4 = r6.a     // Catch: java.lang.Throwable -> L1b
            boolean r4 = defpackage.t20.B(r4)     // Catch: java.lang.Throwable -> L1b
            if (r4 == 0) goto L43
            goto L45
        L43:
            r6.c = r3     // Catch: java.lang.Throwable -> L1b
        L45:
            ub0 r4 = r0.q     // Catch: java.lang.Throwable -> L1b
            if (r4 == 0) goto L88
            boolean r4 = r4.m0()     // Catch: java.lang.Throwable -> L1b
            if (r4 != r3) goto L88
            ng0 r3 = r0.a()     // Catch: java.lang.Throwable -> L1b
            ng0 r3 = r3.u     // Catch: java.lang.Throwable -> L1b
            if (r3 == 0) goto L5b
            pb0 r3 = r3.p     // Catch: java.lang.Throwable -> L1b
            if (r3 != 0) goto L65
        L5b:
            mj0 r3 = defpackage.c50.a(r2)     // Catch: java.lang.Throwable -> L1b
            b4 r3 = (defpackage.b4) r3     // Catch: java.lang.Throwable -> L1b
            dm0 r3 = r3.getPlacementScope()     // Catch: java.lang.Throwable -> L1b
        L65:
            ub0 r4 = r0.q     // Catch: java.lang.Throwable -> L1b
            r4.getClass()     // Catch: java.lang.Throwable -> L1b
            z40 r2 = r2.s()     // Catch: java.lang.Throwable -> L1b
            if (r2 == 0) goto L74
            d50 r2 = r2.I     // Catch: java.lang.Throwable -> L1b
            r2.h = r5     // Catch: java.lang.Throwable -> L1b
        L74:
            r2 = 2147483647(0x7fffffff, float:NaN)
            r4.m = r2     // Catch: java.lang.Throwable -> L1b
            r2 = 32
            long r5 = r9 >> r2
            int r2 = (int) r5     // Catch: java.lang.Throwable -> L1b
            r5 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r5 = r5 & r9
            int r5 = (int) r5     // Catch: java.lang.Throwable -> L1b
            defpackage.dm0.z(r3, r4, r2, r5)     // Catch: java.lang.Throwable -> L1b
        L88:
            ub0 r0 = r0.q     // Catch: java.lang.Throwable -> L1b
            if (r0 == 0) goto L95
            boolean r0 = r0.o     // Catch: java.lang.Throwable -> L1b
            if (r0 != 0) goto L95
            java.lang.String r0 = "Error: Placement happened before lookahead."
            defpackage.q00.b(r0)     // Catch: java.lang.Throwable -> L1b
        L95:
            r8.r0(r9, r11, r12)     // Catch: java.lang.Throwable -> L1b
            return
        L99:
            r1.W(r8)
            r8 = 0
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.oc0.i0(long, float, gv):void");
    }

    public final List m0() {
        d50 d50Var = this.j;
        d50Var.a.c0();
        boolean z = this.C;
        ef0 ef0Var = this.B;
        if (!z) {
            return ef0Var.f();
        }
        z40 z40Var = d50Var.a;
        ef0 w = z40Var.w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            z40 z40Var2 = (z40) objArr[i2];
            if (ef0Var.g <= i2) {
                ef0Var.b(z40Var2.I.p);
            } else {
                oc0 oc0Var = z40Var2.I.p;
                Object[] objArr2 = ef0Var.e;
                Object obj = objArr2[i2];
                objArr2[i2] = oc0Var;
            }
        }
        ef0Var.l(((bf0) z40Var.m()).e.g, ef0Var.g);
        this.C = false;
        return ef0Var.f();
    }

    public final void n0() {
        boolean z = this.v;
        this.v = true;
        d50 d50Var = this.j;
        z40 z40Var = d50Var.a;
        lg0 lg0Var = z40Var.H;
        if (!z) {
            lg0Var.c.b1();
            ((b4) c50.a(z40Var)).getRectManager().f(d50Var.a);
            if (z40Var.p()) {
                z40.T(z40Var, true, 6);
            } else if (z40Var.I.e) {
                z40.R(z40Var, true, 6);
            }
        }
        ng0 ng0Var = lg0Var.c.t;
        for (ng0 ng0Var2 = lg0Var.d; !o20.e(ng0Var2, ng0Var) && ng0Var2 != null; ng0Var2 = ng0Var2.t) {
            if (ng0Var2.O) {
                ng0Var2.W0();
            }
        }
        ef0 w = z40Var.w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            z40 z40Var2 = (z40) objArr[i2];
            if (z40Var2.t() != Integer.MAX_VALUE) {
                z40Var2.I.p.n0();
                z40.U(z40Var2);
            }
        }
    }

    public final void p0() {
        if (this.v) {
            this.v = false;
            d50 d50Var = this.j;
            z40 z40Var = d50Var.a;
            z40 z40Var2 = d50Var.a;
            ((b4) c50.a(z40Var)).getRectManager().g(z40Var2);
            lg0 lg0Var = z40Var2.H;
            ng0 ng0Var = lg0Var.c.t;
            for (ng0 ng0Var2 = lg0Var.d; !o20.e(ng0Var2, ng0Var) && ng0Var2 != null; ng0Var2 = ng0Var2.t) {
                ng0Var2.d1();
                ng0Var2.i1();
            }
            ef0 w = z40Var2.w();
            Object[] objArr = w.e;
            int i = w.g;
            for (int i2 = 0; i2 < i; i2++) {
                ((z40) objArr[i2]).I.p.p0();
            }
        }
    }

    public final void q0() {
        this.I = true;
        d50 d50Var = this.j;
        z40 s = d50Var.a.s();
        float f = I().E;
        z40 z40Var = d50Var.a;
        lg0 lg0Var = z40Var.H;
        ng0 ng0Var = lg0Var.d;
        w00 w00Var = lg0Var.c;
        while (ng0Var != w00Var) {
            ng0Var.getClass();
            t40 t40Var = (t40) ng0Var;
            f += t40Var.E;
            ng0Var = t40Var.t;
        }
        if (f != this.H) {
            this.H = f;
            if (s != null) {
                s.M();
            }
            if (s != null) {
                s.z();
            }
        }
        if (!I().o) {
            boolean z = this.v;
            if (!z || this.A.d()) {
                n0();
            }
            if (!z) {
                if (s != null) {
                    s.z();
                }
                if (this.k && s != null) {
                    s.S(false);
                }
            } else {
                z40Var.H.c.b1();
            }
        }
        if (s != null) {
            d50 d50Var2 = s.I;
            if (!this.k && d50Var2.d == v40.g) {
                if (this.m != Integer.MAX_VALUE) {
                    q00.b("Place was called on a node which was placed already");
                }
                int i = d50Var2.i;
                this.m = i;
                d50Var2.i = i + 1;
            }
        } else {
            this.m = 0;
        }
        N();
    }

    @Override // defpackage.a3
    public final a50 r() {
        return this.A;
    }

    public final void r0(long j, float f, gv gvVar) {
        d50 d50Var = this.j;
        z40 z40Var = d50Var.a;
        z40 z40Var2 = d50Var.a;
        if (z40Var.Q) {
            q00.a("place is called on a deactivated node");
        }
        d50Var.d = v40.g;
        this.q = j;
        this.s = f;
        this.r = gvVar;
        this.I = false;
        mj0 a = c50.a(z40Var2);
        if (!this.y && this.v) {
            ng0 a2 = d50Var.a();
            a2.g1(v10.c(j, a2.i), f, gvVar);
            q0();
        } else {
            this.A.e = false;
            d50Var.f(false);
            this.J = gvVar;
            this.K = j;
            this.L = f;
            pj0 snapshotObserver = ((b4) a).getSnapshotObserver();
            snapshotObserver.a.b(z40Var2, snapshotObserver.f, this.M);
        }
        d50Var.d = v40.i;
        if (d50Var.a().o && (d50Var.k || d50Var.j)) {
            requestLayout();
        }
        this.o = true;
    }

    @Override // defpackage.a3
    public final void requestLayout() {
        this.j.a.S(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0054 A[Catch: all -> 0x0010, LOOP:0: B:22:0x0052->B:23:0x0054, LOOP_END, TryCatch #0 {all -> 0x0010, blocks: (B:3:0x0006, B:5:0x000a, B:6:0x0013, B:9:0x0023, B:13:0x002b, B:15:0x0033, B:18:0x003c, B:21:0x0045, B:23:0x0054, B:25:0x0064, B:28:0x007b, B:30:0x009a, B:31:0x00a0, B:33:0x00ac, B:35:0x00b6, B:39:0x00c2, B:41:0x0076), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x009a A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:3:0x0006, B:5:0x000a, B:6:0x0013, B:9:0x0023, B:13:0x002b, B:15:0x0033, B:18:0x003c, B:21:0x0045, B:23:0x0054, B:25:0x0064, B:28:0x007b, B:30:0x009a, B:31:0x00a0, B:33:0x00ac, B:35:0x00b6, B:39:0x00c2, B:41:0x0076), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0076 A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:3:0x0006, B:5:0x000a, B:6:0x0013, B:9:0x0023, B:13:0x002b, B:15:0x0033, B:18:0x003c, B:21:0x0045, B:23:0x0054, B:25:0x0064, B:28:0x007b, B:30:0x009a, B:31:0x00a0, B:33:0x00ac, B:35:0x00b6, B:39:0x00c2, B:41:0x0076), top: B:2:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean s0(long r11) {
        /*
            Method dump skipped, instructions count: 227
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.oc0.s0(long):boolean");
    }

    public final void t0() {
        d50 d50Var = this.j;
        z40 z40Var = d50Var.a;
        z40 z40Var2 = d50Var.a;
        if (z40Var.F() && d50Var.l > 0) {
            d50 d50Var2 = z40Var2.I;
            if ((d50Var2.j || d50Var2.k) && !d50Var2.p.y) {
                z40Var2.S(false);
            }
            ef0 w = z40Var2.w();
            Object[] objArr = w.e;
            int i = w.g;
            for (int i2 = 0; i2 < i; i2++) {
                ((z40) objArr[i2]).I.p.t0();
            }
        }
    }

    @Override // defpackage.kc0
    public final em0 v(long j) {
        x40 x40Var;
        d50 d50Var = this.j;
        z40 z40Var = d50Var.a;
        z40 z40Var2 = d50Var.a;
        x40 x40Var2 = z40Var.E;
        x40 x40Var3 = x40.g;
        if (x40Var2 == x40Var3) {
            z40Var.e();
        }
        if (t20.B(z40Var2)) {
            ub0 ub0Var = d50Var.q;
            ub0Var.getClass();
            ub0Var.n = x40Var3;
            ub0Var.v(j);
        }
        z40 s = z40Var2.s();
        if (s != null) {
            d50 d50Var2 = s.I;
            if (this.p != x40Var3 && !z40Var2.G) {
                q00.b("measure() may not be called multiple times on the same Measurable. If you want to get the content size of the Measurable before calculating the final constraints, please use methods like minIntrinsicWidth()/maxIntrinsicWidth() and minIntrinsicHeight()/maxIntrinsicHeight()");
            }
            int ordinal = d50Var2.d.ordinal();
            if (ordinal != 0) {
                if (ordinal == 2) {
                    x40Var = x40.f;
                } else {
                    v7.l(d50Var2.d, "Measurable could be only measured from the parent's measure or layout block. Parents state is ");
                    return null;
                }
            } else {
                x40Var = x40.e;
            }
            this.p = x40Var;
        } else {
            this.p = x40Var3;
        }
        s0(j);
        return this;
    }

    @Override // defpackage.a3
    public final void x(q2 q2Var) {
        ef0 w = this.j.a.w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            q2Var.e(((z40) objArr[i2]).I.p);
        }
    }
}
