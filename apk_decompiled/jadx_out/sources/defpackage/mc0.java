package defpackage;

import android.os.Trace;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mc0 {
    public final z40 a;
    public boolean c;
    public boolean d;
    public si i;
    public final r7 b = new r7(3);
    public final c4 e = new c4(13);
    public final ef0 f = new ef0(new z40[16]);
    public final long g = 1;
    public final ef0 h = new ef0(new lc0[16]);

    public mc0(z40 z40Var) {
        this.a = z40Var;
    }

    public static final boolean a(mc0 mc0Var, z40 z40Var, boolean z) {
        si siVar;
        boolean z2;
        dm0 placementScope;
        w00 w00Var;
        z40 s;
        z40 z40Var2 = mc0Var.a;
        boolean z3 = z40Var.Q;
        d50 d50Var = z40Var.I;
        boolean z4 = false;
        if (!z3 && k(z40Var)) {
            if (z40Var == z40Var2) {
                siVar = mc0Var.i;
                siVar.getClass();
            } else {
                siVar = null;
            }
            if (z) {
                if (d50Var.e) {
                    z4 = c(z40Var, siVar);
                }
                if ((z4 || d50Var.f) && o20.e(z40Var.G(), Boolean.TRUE)) {
                    z40Var.I();
                }
            } else {
                if (z40Var.p()) {
                    z2 = d(z40Var, siVar);
                } else {
                    z2 = false;
                }
                if (z40Var.o() && (z40Var == z40Var2 || ((s = z40Var.s()) != null && s.F() && d50Var.p.w))) {
                    if (z40Var == z40Var2) {
                        if (z40Var.E == x40.g) {
                            z40Var.f();
                        }
                        z40 s2 = z40Var.s();
                        if (s2 == null || (w00Var = s2.H.c) == null || (placementScope = w00Var.p) == null) {
                            placementScope = ((b4) c50.a(z40Var)).getPlacementScope();
                        }
                        dm0.C(placementScope, d50Var.p, 0, 0);
                    } else {
                        z40Var.P();
                    }
                    c4 c4Var = mc0Var.e;
                    c4Var.getClass();
                    if (z40Var.P > 0) {
                        ((ef0) c4Var.f).b(z40Var);
                        z40Var.O = true;
                    }
                }
                z4 = z2;
            }
            mc0Var.e();
        }
        return z4;
    }

    public static boolean c(z40 z40Var, si siVar) {
        si siVar2;
        boolean t0;
        z40 z40Var2 = z40Var.l;
        d50 d50Var = z40Var.I;
        if (z40Var2 == null) {
            return false;
        }
        if (siVar != null) {
            if (z40Var2 != null) {
                ub0 ub0Var = d50Var.q;
                ub0Var.getClass();
                t0 = ub0Var.t0(siVar.a);
            }
            t0 = false;
        } else {
            ub0 ub0Var2 = d50Var.q;
            if (ub0Var2 != null) {
                siVar2 = ub0Var2.q;
            } else {
                siVar2 = null;
            }
            if (siVar2 != null && z40Var2 != null) {
                ub0Var2.getClass();
                t0 = ub0Var2.t0(siVar2.a);
            }
            t0 = false;
        }
        z40 s = z40Var.s();
        if (t0 && s != null) {
            if (s.l == null) {
                z40.T(s, false, 3);
                return t0;
            }
            if (z40Var.r() == x40.e) {
                z40.R(s, false, 3);
                return t0;
            }
            if (z40Var.r() == x40.f) {
                s.Q(false);
            }
        }
        return t0;
    }

    public static boolean d(z40 z40Var, si siVar) {
        si siVar2;
        boolean z;
        x40 x40Var = x40.g;
        if (siVar != null) {
            if (z40Var.E == x40Var) {
                z40Var.e();
            }
            z = z40Var.I.p.s0(siVar.a);
        } else {
            oc0 oc0Var = z40Var.I.p;
            if (oc0Var.n) {
                siVar2 = new si(oc0Var.h);
            } else {
                siVar2 = null;
            }
            if (siVar2 != null) {
                if (z40Var.E == x40Var) {
                    z40Var.e();
                }
                z = z40Var.I.p.s0(siVar2.a);
            } else {
                z = false;
            }
        }
        z40 s = z40Var.s();
        if (z && s != null) {
            if (z40Var.q() == x40.e) {
                z40.T(s, false, 3);
                return z;
            }
            if (z40Var.q() == x40.f) {
                s.S(false);
            }
        }
        return z;
    }

    public static boolean i(z40 z40Var) {
        ub0 ub0Var;
        a50 a50Var;
        if (z40Var.I.e) {
            if (z40Var.r() != x40.g || ((ub0Var = z40Var.I.q) != null && (a50Var = ub0Var.u) != null && a50Var.e())) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static boolean j(z40 z40Var) {
        v40 v40Var;
        if (!z40Var.p()) {
            return false;
        }
        do {
            if (z40Var.q() == x40.g && !z40Var.I.p.A.e()) {
                z40 s = z40Var.s();
                if (s != null) {
                    v40Var = s.I.d;
                } else {
                    v40Var = null;
                }
                if (v40Var != v40.e) {
                    return false;
                }
            }
            z40Var = z40Var.s();
            if (z40Var == null) {
                return false;
            }
        } while (!z40Var.F());
        return true;
    }

    public static boolean k(z40 z40Var) {
        ub0 ub0Var;
        a50 a50Var;
        d50 d50Var = z40Var.I;
        if (z40Var.F() || d50Var.p.w || j(z40Var) || o20.e(z40Var.G(), Boolean.TRUE) || i(z40Var) || d50Var.p.A.e() || ((ub0Var = d50Var.q) != null && (a50Var = ub0Var.u) != null && a50Var.e())) {
            return true;
        }
        return false;
    }

    public final void b(boolean z) {
        c4 c4Var = this.e;
        if (z) {
            ef0 ef0Var = (ef0) c4Var.f;
            z40 z40Var = this.a;
            if (z40Var.P > 0) {
                ef0Var.g();
                ef0Var.b(z40Var);
                z40Var.O = true;
            }
        }
        if (((ef0) c4Var.f).g != 0) {
            Trace.beginSection("Compose:onPositionedCallbacks");
            try {
                c4Var.o();
            } finally {
                Trace.endSection();
            }
        }
    }

    public final void e() {
        ef0 ef0Var = this.h;
        int i = ef0Var.g;
        if (i != 0) {
            Object[] objArr = ef0Var.e;
            for (int i2 = 0; i2 < i; i2++) {
                lc0 lc0Var = (lc0) objArr[i2];
                if (lc0Var.a.E()) {
                    boolean z = lc0Var.b;
                    z40 z40Var = lc0Var.a;
                    boolean z2 = lc0Var.c;
                    if (!z) {
                        z40.T(z40Var, z2, 2);
                    } else {
                        z40.R(z40Var, z2, 2);
                    }
                }
            }
            ef0Var.g();
        }
    }

    public final void f(z40 z40Var) {
        ef0 w = z40Var.w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            z40 z40Var2 = (z40) objArr[i2];
            if (o20.e(z40Var2.G(), Boolean.TRUE) && !z40Var2.Q) {
                if (this.b.m(z40Var2)) {
                    z40Var2.I();
                }
                f(z40Var2);
            }
        }
    }

    public final void g(z40 z40Var, boolean z) {
        boolean p;
        if (!this.c) {
            q00.b("forceMeasureTheSubtree should be executed during the measureAndLayout pass");
        }
        if (z) {
            p = z40Var.I.e;
        } else {
            p = z40Var.p();
        }
        if (p) {
            q00.a("node not yet measured");
        }
        h(z40Var, z);
    }

    public final void h(z40 z40Var, boolean z) {
        boolean p;
        ub0 ub0Var;
        a50 a50Var;
        boolean p2;
        boolean p3;
        ef0 w = z40Var.w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            z40 z40Var2 = (z40) objArr[i2];
            x40 x40Var = x40.e;
            if ((!z && (z40Var2.q() == x40Var || z40Var2.I.p.A.e())) || (z && (z40Var2.r() == x40Var || ((ub0Var = z40Var2.I.q) != null && (a50Var = ub0Var.u) != null && a50Var.e())))) {
                boolean B = t20.B(z40Var2);
                d50 d50Var = z40Var2.I;
                if (B && !z) {
                    if (d50Var.e && this.b.m(z40Var2)) {
                        o(z40Var2, true);
                    } else {
                        g(z40Var2, true);
                    }
                }
                if (z) {
                    p2 = d50Var.e;
                } else {
                    p2 = z40Var2.p();
                }
                if (p2) {
                    o(z40Var2, z);
                }
                if (z) {
                    p3 = d50Var.e;
                } else {
                    p3 = z40Var2.p();
                }
                if (!p3) {
                    h(z40Var2, z);
                }
            }
        }
        if (z) {
            p = z40Var.I.e;
        } else {
            p = z40Var.p();
        }
        if (p) {
            o(z40Var, z);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v1 */
    /* JADX WARN: Type inference failed for: r12v11 */
    /* JADX WARN: Type inference failed for: r12v2, types: [bd0] */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v1, types: [int] */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v3, types: [int] */
    /* JADX WARN: Type inference failed for: r15v4 */
    public final boolean l(t3 t3Var) {
        boolean z;
        bd0 bd0Var;
        bd0 bd0Var2;
        boolean z2;
        z40 z40Var;
        boolean z3;
        boolean o;
        r7 r7Var = this.b;
        z40 z40Var2 = this.a;
        if (!z40Var2.E()) {
            q00.a("performMeasureAndLayout called with unattached root");
        }
        if (!z40Var2.F()) {
            q00.a("performMeasureAndLayout called with unplaced root");
        }
        if (this.c) {
            q00.a("performMeasureAndLayout called during measure layout");
        }
        boolean z4 = false;
        if (this.i != null) {
            this.c = true;
            this.d = true;
            try {
                boolean x = r7Var.x();
                j2 j2Var = (j2) r7Var.f;
                if (x) {
                    z = false;
                    while (true) {
                        j2 j2Var2 = (j2) r7Var.h;
                        j2 j2Var3 = (j2) r7Var.g;
                        if (!((rx0) j2Var.f).isEmpty()) {
                            z40Var = (z40) ((rx0) j2Var.f).first();
                            j2Var.m(z40Var);
                            if (z40Var.l != null) {
                                z3 = true;
                            } else {
                                z3 = false;
                            }
                            z2 = false;
                        } else if (!((rx0) j2Var3.f).isEmpty()) {
                            z40Var = (z40) ((rx0) j2Var3.f).first();
                            j2Var3.m(z40Var);
                            if (z40Var.l != null) {
                                z3 = true;
                            } else {
                                z3 = false;
                            }
                            z2 = true;
                        } else {
                            if (((rx0) j2Var2.f).isEmpty()) {
                                break;
                            }
                            z40 z40Var3 = (z40) ((rx0) j2Var2.f).first();
                            j2Var2.m(z40Var3);
                            z2 = true;
                            z40Var = z40Var3;
                            z3 = false;
                        }
                        if (z2) {
                            o = a(this, z40Var, z3);
                        } else {
                            o = o(z40Var, z3);
                            if (z40Var.I.f) {
                                r7Var.k(z40Var, u20.f);
                            }
                            if (z40Var.o()) {
                                r7Var.k(z40Var, u20.h);
                            }
                        }
                        if (z40Var == z40Var2 && o) {
                            z = true;
                        }
                    }
                    if (t3Var != null) {
                        t3Var.a();
                    }
                } else {
                    z = false;
                }
            } finally {
            }
        } else {
            z = false;
        }
        ef0 ef0Var = this.f;
        Object[] objArr = ef0Var.e;
        int i = ef0Var.g;
        int i2 = 0;
        while (i2 < i) {
            lg0 lg0Var = ((z40) objArr[i2]).H;
            w00 w00Var = lg0Var.c;
            boolean f = og0.f(4194304);
            if (f) {
                bd0Var = w00Var.U;
            } else {
                bd0Var = w00Var.U.i;
                if (bd0Var == null) {
                    i2++;
                    z4 = false;
                }
            }
            pq0 pq0Var = ng0.Q;
            bd0 R0 = w00Var.R0(f);
            while (R0 != null && (R0.h & 4194304) != 0) {
                if ((R0.g & 4194304) != 0) {
                    jm jmVar = R0;
                    ef0 ef0Var2 = null;
                    while (jmVar != 0) {
                        if (jmVar instanceof j40) {
                            ((j40) jmVar).x(lg0Var.c);
                        } else if ((jmVar.g & 4194304) != 0 && (jmVar instanceof jm)) {
                            bd0 bd0Var3 = jmVar.t;
                            ?? r15 = z4;
                            bd0Var2 = jmVar;
                            ef0Var2 = ef0Var2;
                            while (bd0Var3 != null) {
                                if ((bd0Var3.g & 4194304) != 0) {
                                    r15++;
                                    ef0Var2 = ef0Var2;
                                    if (r15 == 1) {
                                        bd0Var2 = bd0Var3;
                                    } else {
                                        if (ef0Var2 == null) {
                                            ef0Var2 = new ef0(new bd0[16]);
                                        }
                                        if (bd0Var2 != null) {
                                            ef0Var2.b(bd0Var2);
                                            bd0Var2 = null;
                                        }
                                        ef0Var2.b(bd0Var3);
                                    }
                                }
                                bd0Var3 = bd0Var3.j;
                                bd0Var2 = bd0Var2;
                                ef0Var2 = ef0Var2;
                                r15 = r15;
                            }
                            if (r15 == 1) {
                                z4 = false;
                                jmVar = bd0Var2;
                                ef0Var2 = ef0Var2;
                            }
                        }
                        bd0Var2 = k81.h(ef0Var2);
                        z4 = false;
                        jmVar = bd0Var2;
                        ef0Var2 = ef0Var2;
                    }
                }
                if (R0 != bd0Var) {
                    R0 = R0.j;
                    z4 = false;
                }
            }
            i2++;
            z4 = false;
        }
        ef0Var.g();
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v2, types: [bd0] */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5, types: [bd0] */
    /* JADX WARN: Type inference failed for: r7v6, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3, types: [ef0] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6, types: [ef0] */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    public final void m(z40 z40Var, long j) {
        bd0 bd0Var;
        if (z40Var.Q) {
            return;
        }
        z40 z40Var2 = this.a;
        if (z40Var == z40Var2) {
            q00.a("measureAndLayout called on root");
        }
        if (!z40Var2.E()) {
            q00.a("performMeasureAndLayout called with unattached root");
        }
        if (!z40Var2.F()) {
            q00.a("performMeasureAndLayout called with unplaced root");
        }
        if (this.c) {
            q00.a("performMeasureAndLayout called during measure layout");
        }
        if (this.i != null) {
            this.c = true;
            this.d = false;
            try {
                r7 r7Var = this.b;
                ((j2) r7Var.f).m(z40Var);
                ((j2) r7Var.g).m(z40Var);
                ((j2) r7Var.h).m(z40Var);
                if ((c(z40Var, new si(j)) || z40Var.I.f) && o20.e(z40Var.G(), Boolean.TRUE)) {
                    z40Var.I();
                }
                f(z40Var);
                d(z40Var, new si(j));
                if (z40Var.o() && z40Var.F()) {
                    z40Var.P();
                    c4 c4Var = this.e;
                    c4Var.getClass();
                    if (z40Var.P > 0) {
                        ((ef0) c4Var.f).b(z40Var);
                        z40Var.O = true;
                    }
                }
                e();
            } finally {
            }
        }
        ef0 ef0Var = this.f;
        Object[] objArr = ef0Var.e;
        int i = ef0Var.g;
        for (int i2 = 0; i2 < i; i2++) {
            lg0 lg0Var = ((z40) objArr[i2]).H;
            w00 w00Var = lg0Var.c;
            boolean f = og0.f(4194304);
            if (f) {
                bd0Var = w00Var.U;
            } else {
                bd0Var = w00Var.U.i;
                if (bd0Var == null) {
                }
            }
            pq0 pq0Var = ng0.Q;
            for (bd0 R0 = w00Var.R0(f); R0 != null && (R0.h & 4194304) != 0; R0 = R0.j) {
                if ((R0.g & 4194304) != 0) {
                    jm jmVar = R0;
                    ?? r8 = 0;
                    while (jmVar != 0) {
                        if (jmVar instanceof j40) {
                            ((j40) jmVar).x(lg0Var.c);
                        } else if ((jmVar.g & 4194304) != 0 && (jmVar instanceof jm)) {
                            bd0 bd0Var2 = jmVar.t;
                            int i3 = 0;
                            jmVar = jmVar;
                            r8 = r8;
                            while (bd0Var2 != null) {
                                if ((bd0Var2.g & 4194304) != 0) {
                                    i3++;
                                    r8 = r8;
                                    if (i3 == 1) {
                                        jmVar = bd0Var2;
                                    } else {
                                        if (r8 == 0) {
                                            r8 = new ef0(new bd0[16]);
                                        }
                                        if (jmVar != 0) {
                                            r8.b(jmVar);
                                            jmVar = 0;
                                        }
                                        r8.b(bd0Var2);
                                    }
                                }
                                bd0Var2 = bd0Var2.j;
                                jmVar = jmVar;
                                r8 = r8;
                            }
                            if (i3 == 1) {
                            }
                        }
                        jmVar = k81.h(r8);
                    }
                }
                if (R0 != bd0Var) {
                }
            }
        }
        ef0Var.g();
    }

    public final void n() {
        boolean z;
        r7 r7Var = this.b;
        if (r7Var.x()) {
            z40 z40Var = this.a;
            if (!z40Var.E()) {
                q00.a("performMeasureAndLayout called with unattached root");
            }
            if (!z40Var.F()) {
                q00.a("performMeasureAndLayout called with unplaced root");
            }
            if (this.c) {
                q00.a("performMeasureAndLayout called during measure layout");
            }
            if (this.i != null) {
                this.c = true;
                this.d = false;
                try {
                    if (!((rx0) ((j2) r7Var.h).f).isEmpty() && !((rx0) ((j2) r7Var.f).f).isEmpty()) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        if (z40Var.l != null) {
                            q(z40Var, true);
                        } else {
                            p(z40Var);
                        }
                    }
                    q(z40Var, false);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } finally {
                        this.c = false;
                        this.d = false;
                    }
                }
            }
        }
    }

    public final boolean o(z40 z40Var, boolean z) {
        si siVar;
        boolean z2 = false;
        if (!z40Var.Q && k(z40Var)) {
            if (z40Var == this.a) {
                siVar = this.i;
                siVar.getClass();
            } else {
                siVar = null;
            }
            if (z) {
                if (z40Var.I.e) {
                    z2 = c(z40Var, siVar);
                }
            } else if (z40Var.p()) {
                z2 = d(z40Var, siVar);
            }
            e();
        }
        return z2;
    }

    public final void p(z40 z40Var) {
        ef0 w = z40Var.w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            z40 z40Var2 = (z40) objArr[i2];
            if (z40Var2.q() == x40.e || z40Var2.I.p.A.e()) {
                if (t20.B(z40Var2)) {
                    q(z40Var2, true);
                } else {
                    p(z40Var2);
                }
            }
        }
    }

    public final void q(z40 z40Var, boolean z) {
        si siVar;
        if (z40Var.Q) {
            return;
        }
        if (z40Var == this.a) {
            siVar = this.i;
            siVar.getClass();
        } else {
            siVar = null;
        }
        if (z) {
            c(z40Var, siVar);
        } else {
            d(z40Var, siVar);
        }
    }

    public final boolean r(z40 z40Var, boolean z) {
        int ordinal = z40Var.I.d.ordinal();
        if (ordinal != 0 && ordinal != 1) {
            if (ordinal != 2 && ordinal != 3) {
                if (ordinal == 4) {
                    if (!z40Var.p() || z) {
                        z40Var.I.p.x = true;
                        if (!z40Var.Q && (z40Var.F() || j(z40Var))) {
                            z40 s = z40Var.s();
                            if (s == null || !s.p()) {
                                this.b.k(z40Var, u20.g);
                            }
                            if (!this.d) {
                                return true;
                            }
                        }
                    }
                } else {
                    v7.k();
                    return false;
                }
            } else {
                this.h.b(new lc0(z40Var, false, z));
            }
        }
        return false;
    }

    public final void s(long j) {
        boolean b;
        u20 u20Var;
        si siVar = this.i;
        if (siVar == null) {
            b = false;
        } else {
            b = si.b(siVar.a, j);
        }
        if (!b) {
            if (this.c) {
                q00.a("updateRootConstraints called while measuring");
            }
            this.i = new si(j);
            z40 z40Var = this.a;
            z40 z40Var2 = z40Var.l;
            d50 d50Var = z40Var.I;
            if (z40Var2 != null) {
                d50Var.e = true;
            }
            d50Var.p.x = true;
            if (z40Var2 != null) {
                u20Var = u20.e;
            } else {
                u20Var = u20.g;
            }
            this.b.k(z40Var, u20Var);
        }
    }
}
