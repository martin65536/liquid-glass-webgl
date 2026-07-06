package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ub0 extends em0 implements kc0, a3, od0 {
    public Object A;
    public boolean E;
    public final d50 j;
    public boolean k;
    public boolean o;
    public boolean p;
    public si q;
    public gv s;
    public boolean x;
    public int l = Integer.MAX_VALUE;
    public int m = Integer.MAX_VALUE;
    public x40 n = x40.g;
    public long r = 0;
    public sb0 t = sb0.g;
    public final a50 u = new a50(this, 1);
    public final ef0 v = new ef0(new ub0[16]);
    public boolean w = true;
    public final tb0 y = new tb0(this, 0);
    public boolean z = true;
    public long B = ti.b(0, 0, 15);
    public final tb0 C = new tb0(this, 2);
    public final tb0 D = new tb0(this, 1);

    public ub0(d50 d50Var) {
        this.j = d50Var;
        this.A = d50Var.p.u;
    }

    @Override // defpackage.em0, defpackage.kc0
    public final Object A() {
        return this.A;
    }

    @Override // defpackage.od0
    public final void E(boolean z) {
        Boolean bool;
        qb0 N0;
        d50 d50Var = this.j;
        qb0 N02 = d50Var.a().N0();
        if (N02 != null) {
            bool = Boolean.valueOf(N02.m);
        } else {
            bool = null;
        }
        if (!Boolean.valueOf(z).equals(bool) && (N0 = d50Var.a().N0()) != null) {
            N0.m = z;
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
            return d50Var.q;
        }
        return null;
    }

    @Override // defpackage.a3
    public final void N() {
        si siVar;
        this.x = true;
        a50 a50Var = this.u;
        a50Var.h();
        d50 d50Var = this.j;
        boolean z = d50Var.f;
        z40 z40Var = d50Var.a;
        if (z) {
            ef0 w = z40Var.w();
            Object[] objArr = w.e;
            int i = w.g;
            for (int i2 = 0; i2 < i; i2++) {
                z40 z40Var2 = (z40) objArr[i2];
                d50 d50Var2 = z40Var2.I;
                if (d50Var2.e && z40Var2.r() == x40.e) {
                    ub0 ub0Var = d50Var2.q;
                    ub0Var.getClass();
                    ub0 ub0Var2 = d50Var2.q;
                    if (ub0Var2 != null) {
                        siVar = ub0Var2.q;
                    } else {
                        siVar = null;
                    }
                    siVar.getClass();
                    if (ub0Var.t0(siVar.a)) {
                        z40.R(z40Var, false, 7);
                    }
                }
            }
        }
        v00 v00Var = I().V;
        v00Var.getClass();
        if (d50Var.g || (!v00Var.o && d50Var.f)) {
            d50Var.f = false;
            v40 v40Var = d50Var.d;
            d50Var.d = v40.h;
            d50Var.i(false);
            pj0 snapshotObserver = ((b4) c50.a(z40Var)).getSnapshotObserver();
            snapshotObserver.a.b(z40Var, snapshotObserver.h, this.y);
            d50Var.d = v40Var;
            if (d50Var.m && v00Var.o) {
                requestLayout();
            }
            d50Var.g = false;
        }
        if (a50Var.b && a50Var.e()) {
            a50Var.g();
        }
        this.x = false;
    }

    @Override // defpackage.a3
    public final int Y() {
        return this.m;
    }

    @Override // defpackage.a3
    public final void c0() {
        z40.R(this.j.a, false, 7);
    }

    @Override // defpackage.em0
    public final void i0(long j, float f, gv gvVar) {
        s0(j, gvVar);
    }

    public final boolean m0() {
        d50 d50Var = this.j;
        if (!t20.B(d50Var.a) && !d50Var.c) {
            return false;
        }
        return true;
    }

    public final void n0(boolean z) {
        if (!z || !m0()) {
            if (z || m0()) {
                this.t = sb0.g;
                ef0 w = this.j.a.w();
                Object[] objArr = w.e;
                int i = w.g;
                for (int i2 = 0; i2 < i; i2++) {
                    ub0 ub0Var = ((z40) objArr[i2]).I.q;
                    ub0Var.getClass();
                    ub0Var.n0(true);
                }
            }
        }
    }

    public final void p0() {
        sb0 sb0Var = this.t;
        d50 d50Var = this.j;
        boolean z = d50Var.c;
        z40 z40Var = d50Var.a;
        sb0 sb0Var2 = sb0.e;
        if (z) {
            this.t = sb0.f;
        } else {
            this.t = sb0Var2;
        }
        if (sb0Var != sb0Var2 && d50Var.e) {
            z40.R(z40Var, true, 6);
        }
        ef0 w = z40Var.w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            z40 z40Var2 = (z40) objArr[i2];
            ub0 ub0Var = z40Var2.I.q;
            if (ub0Var != null) {
                if (ub0Var.m != Integer.MAX_VALUE) {
                    ub0Var.p0();
                    z40.U(z40Var2);
                }
            } else {
                v7.m("Error: Child node's lookahead pass delegate cannot be null when in a lookahead scope.");
                return;
            }
        }
    }

    public final void q0() {
        d50 d50Var = this.j;
        if (d50Var.o > 0) {
            ef0 w = d50Var.a.w();
            Object[] objArr = w.e;
            int i = w.g;
            for (int i2 = 0; i2 < i; i2++) {
                z40 z40Var = (z40) objArr[i2];
                d50 d50Var2 = z40Var.I;
                if ((d50Var2.m || d50Var2.n) && !d50Var2.f) {
                    z40Var.Q(false);
                }
                ub0 ub0Var = d50Var2.q;
                if (ub0Var != null) {
                    ub0Var.q0();
                }
            }
        }
    }

    @Override // defpackage.a3
    public final a50 r() {
        return this.u;
    }

    public final void r0() {
        v40 v40Var;
        this.E = true;
        d50 d50Var = this.j;
        z40 s = d50Var.a.s();
        sb0 sb0Var = this.t;
        if ((sb0Var != sb0.e && !d50Var.c) || (sb0Var != sb0.f && d50Var.c)) {
            p0();
            if (this.k && s != null) {
                s.Q(false);
            }
        }
        if (s != null) {
            d50 d50Var2 = s.I;
            if (!this.k && ((v40Var = d50Var2.d) == v40.g || v40Var == v40.h)) {
                if (this.m != Integer.MAX_VALUE) {
                    q00.b("Place was called on a node which was placed already");
                }
                int i = d50Var2.h;
                this.m = i;
                d50Var2.h = i + 1;
            }
        } else {
            this.m = 0;
        }
        N();
    }

    @Override // defpackage.a3
    public final void requestLayout() {
        this.j.a.Q(false);
    }

    public final void s0(long j, gv gvVar) {
        v40 v40Var;
        d50 d50Var = this.j;
        z40 z40Var = d50Var.a;
        z40 z40Var2 = d50Var.a;
        try {
            z40 s = z40Var.s();
            if (s != null) {
                v40Var = s.I.d;
            } else {
                v40Var = null;
            }
            v40 v40Var2 = v40.h;
            if (v40Var == v40Var2) {
                d50Var.c = false;
            }
            if (z40Var2.Q) {
                q00.a("place is called on a deactivated node");
            }
            d50Var.d = v40Var2;
            boolean z = true;
            this.o = true;
            this.E = false;
            if (!v10.a(j, this.r)) {
                if (d50Var.n || d50Var.m) {
                    d50Var.f = true;
                }
                q0();
            }
            mj0 a = c50.a(z40Var2);
            this.r = j;
            if (!d50Var.f) {
                if (this.t == sb0.g) {
                    z = false;
                }
                if (z) {
                    qb0 N0 = d50Var.a().N0();
                    N0.getClass();
                    N0.G0(v10.c(j, N0.i));
                    r0();
                    this.s = gvVar;
                    d50Var.d = v40.i;
                }
            }
            d50Var.h(false);
            this.u.e = false;
            pj0 snapshotObserver = ((b4) a).getSnapshotObserver();
            snapshotObserver.a.b(z40Var2, snapshotObserver.g, this.D);
            this.s = gvVar;
            d50Var.d = v40.i;
        } catch (Throwable th) {
            z40Var.W(th);
            throw null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:3:0x0006, B:5:0x000a, B:6:0x0013, B:9:0x001f, B:13:0x0027, B:15:0x002f, B:20:0x003e, B:22:0x0042, B:23:0x0047, B:26:0x0035, B:27:0x004b, B:29:0x0064, B:31:0x0077, B:33:0x007b, B:34:0x0083, B:37:0x0095, B:39:0x00b2, B:43:0x0090), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0064 A[Catch: all -> 0x0010, LOOP:0: B:28:0x0062->B:29:0x0064, LOOP_END, TryCatch #0 {all -> 0x0010, blocks: (B:3:0x0006, B:5:0x000a, B:6:0x0013, B:9:0x001f, B:13:0x0027, B:15:0x002f, B:20:0x003e, B:22:0x0042, B:23:0x0047, B:26:0x0035, B:27:0x004b, B:29:0x0064, B:31:0x0077, B:33:0x007b, B:34:0x0083, B:37:0x0095, B:39:0x00b2, B:43:0x0090), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007b A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:3:0x0006, B:5:0x000a, B:6:0x0013, B:9:0x001f, B:13:0x0027, B:15:0x002f, B:20:0x003e, B:22:0x0042, B:23:0x0047, B:26:0x0035, B:27:0x004b, B:29:0x0064, B:31:0x0077, B:33:0x007b, B:34:0x0083, B:37:0x0095, B:39:0x00b2, B:43:0x0090), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0090 A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:3:0x0006, B:5:0x000a, B:6:0x0013, B:9:0x001f, B:13:0x0027, B:15:0x002f, B:20:0x003e, B:22:0x0042, B:23:0x0047, B:26:0x0035, B:27:0x004b, B:29:0x0064, B:31:0x0077, B:33:0x007b, B:34:0x0083, B:37:0x0095, B:39:0x00b2, B:43:0x0090), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x007e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean t0(long r14) {
        /*
            r13 = this;
            d50 r0 = r13.j
            z40 r1 = r0.a
            z40 r2 = r0.a
            boolean r3 = r1.Q     // Catch: java.lang.Throwable -> L10
            if (r3 == 0) goto L13
            java.lang.String r3 = "measure is called on a deactivated node"
            defpackage.q00.a(r3)     // Catch: java.lang.Throwable -> L10
            goto L13
        L10:
            r13 = move-exception
            goto Lbc
        L13:
            z40 r3 = r2.s()     // Catch: java.lang.Throwable -> L10
            boolean r4 = r2.G     // Catch: java.lang.Throwable -> L10
            r5 = 1
            r6 = 0
            if (r4 != 0) goto L26
            if (r3 == 0) goto L24
            boolean r3 = r3.G     // Catch: java.lang.Throwable -> L10
            if (r3 == 0) goto L24
            goto L26
        L24:
            r3 = r6
            goto L27
        L26:
            r3 = r5
        L27:
            r2.G = r3     // Catch: java.lang.Throwable -> L10
            d50 r3 = r2.I     // Catch: java.lang.Throwable -> L10
            boolean r3 = r3.e     // Catch: java.lang.Throwable -> L10
            if (r3 != 0) goto L4b
            si r3 = r13.q     // Catch: java.lang.Throwable -> L10
            if (r3 != 0) goto L35
            r3 = r6
            goto L3b
        L35:
            long r3 = r3.a     // Catch: java.lang.Throwable -> L10
            boolean r3 = defpackage.si.b(r3, r14)     // Catch: java.lang.Throwable -> L10
        L3b:
            if (r3 != 0) goto L3e
            goto L4b
        L3e:
            mj0 r13 = r2.r     // Catch: java.lang.Throwable -> L10
            if (r13 == 0) goto L47
            b4 r13 = (defpackage.b4) r13     // Catch: java.lang.Throwable -> L10
            r13.o(r2, r5)     // Catch: java.lang.Throwable -> L10
        L47:
            r2.V()     // Catch: java.lang.Throwable -> L10
            return r6
        L4b:
            si r3 = new si     // Catch: java.lang.Throwable -> L10
            r3.<init>(r14)     // Catch: java.lang.Throwable -> L10
            r13.q = r3     // Catch: java.lang.Throwable -> L10
            r13.l0(r14)     // Catch: java.lang.Throwable -> L10
            a50 r3 = r13.u     // Catch: java.lang.Throwable -> L10
            r3.d = r6     // Catch: java.lang.Throwable -> L10
            ef0 r2 = r2.w()     // Catch: java.lang.Throwable -> L10
            java.lang.Object[] r3 = r2.e     // Catch: java.lang.Throwable -> L10
            int r2 = r2.g     // Catch: java.lang.Throwable -> L10
            r4 = r6
        L62:
            if (r4 >= r2) goto L77
            r7 = r3[r4]     // Catch: java.lang.Throwable -> L10
            z40 r7 = (defpackage.z40) r7     // Catch: java.lang.Throwable -> L10
            d50 r7 = r7.I     // Catch: java.lang.Throwable -> L10
            ub0 r7 = r7.q     // Catch: java.lang.Throwable -> L10
            r7.getClass()     // Catch: java.lang.Throwable -> L10
            a50 r7 = r7.u     // Catch: java.lang.Throwable -> L10
            r7.getClass()     // Catch: java.lang.Throwable -> L10
            int r4 = r4 + 1
            goto L62
        L77:
            boolean r2 = r13.p     // Catch: java.lang.Throwable -> L10
            if (r2 == 0) goto L7e
            long r2 = r13.g     // Catch: java.lang.Throwable -> L10
            goto L83
        L7e:
            r2 = -9223372034707292160(0x8000000080000000, double:-1.0609978955E-314)
        L83:
            r13.p = r5     // Catch: java.lang.Throwable -> L10
            ng0 r4 = r0.a()     // Catch: java.lang.Throwable -> L10
            qb0 r4 = r4.N0()     // Catch: java.lang.Throwable -> L10
            if (r4 == 0) goto L90
            goto L95
        L90:
            java.lang.String r7 = "Lookahead result from lookaheadRemeasure cannot be null"
            defpackage.q00.b(r7)     // Catch: java.lang.Throwable -> L10
        L95:
            r0.c(r14)     // Catch: java.lang.Throwable -> L10
            int r14 = r4.e     // Catch: java.lang.Throwable -> L10
            int r15 = r4.f     // Catch: java.lang.Throwable -> L10
            long r7 = (long) r14     // Catch: java.lang.Throwable -> L10
            r14 = 32
            long r7 = r7 << r14
            long r9 = (long) r15     // Catch: java.lang.Throwable -> L10
            r11 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r9 = r9 & r11
            long r7 = r7 | r9
            r13.k0(r7)     // Catch: java.lang.Throwable -> L10
            long r13 = r2 >> r14
            int r13 = (int) r13     // Catch: java.lang.Throwable -> L10
            int r14 = r4.e     // Catch: java.lang.Throwable -> L10
            if (r13 != r14) goto Lbb
            long r13 = r2 & r11
            int r13 = (int) r13     // Catch: java.lang.Throwable -> L10
            int r14 = r4.f     // Catch: java.lang.Throwable -> L10
            if (r13 == r14) goto Lba
            goto Lbb
        Lba:
            return r6
        Lbb:
            return r5
        Lbc:
            r1.W(r13)
            r13 = 0
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ub0.t0(long):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0025, code lost:
    
        if (r1 == defpackage.v40.h) goto L14;
     */
    @Override // defpackage.kc0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final defpackage.em0 v(long r7) {
        /*
            r6 = this;
            d50 r0 = r6.j
            z40 r1 = r0.a
            z40 r2 = r0.a
            z40 r1 = r1.s()
            r3 = 0
            if (r1 == 0) goto L12
            d50 r1 = r1.I
            v40 r1 = r1.d
            goto L13
        L12:
            r1 = r3
        L13:
            v40 r4 = defpackage.v40.f
            if (r1 == r4) goto L27
            z40 r1 = r2.s()
            if (r1 == 0) goto L22
            d50 r1 = r1.I
            v40 r1 = r1.d
            goto L23
        L22:
            r1 = r3
        L23:
            v40 r4 = defpackage.v40.h
            if (r1 != r4) goto L2a
        L27:
            r1 = 0
            r0.b = r1
        L2a:
            z40 r0 = r2.s()
            x40 r1 = defpackage.x40.g
            if (r0 == 0) goto L64
            d50 r0 = r0.I
            x40 r4 = r6.n
            if (r4 == r1) goto L42
            boolean r4 = r2.G
            if (r4 == 0) goto L3d
            goto L42
        L3d:
            java.lang.String r4 = "measure() may not be called multiple times on the same Measurable. If you want to get the content size of the Measurable before calculating the final constraints, please use methods like minIntrinsicWidth()/maxIntrinsicWidth() and minIntrinsicHeight()/maxIntrinsicHeight()"
            defpackage.q00.b(r4)
        L42:
            v40 r4 = r0.d
            int r4 = r4.ordinal()
            if (r4 == 0) goto L5f
            r5 = 1
            if (r4 == r5) goto L5f
            r5 = 2
            if (r4 == r5) goto L5c
            r5 = 3
            if (r4 != r5) goto L54
            goto L5c
        L54:
            java.lang.String r6 = "Measurable could be only measured from the parent's measure or layout block. Parents state is "
            v40 r7 = r0.d
            defpackage.v7.l(r7, r6)
            return r3
        L5c:
            x40 r0 = defpackage.x40.f
            goto L61
        L5f:
            x40 r0 = defpackage.x40.e
        L61:
            r6.n = r0
            goto L66
        L64:
            r6.n = r1
        L66:
            x40 r0 = r2.E
            if (r0 != r1) goto L6d
            r2.e()
        L6d:
            r6.t0(r7)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ub0.v(long):em0");
    }

    @Override // defpackage.a3
    public final void x(q2 q2Var) {
        ef0 w = this.j.a.w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            ub0 ub0Var = ((z40) objArr[i2]).I.q;
            ub0Var.getClass();
            q2Var.e(ub0Var);
        }
    }
}
