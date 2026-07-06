package defpackage;

import android.os.Trace;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bw {
    public int A;
    public int B;
    public boolean C;
    public final aw D;
    public final ArrayList E;
    public boolean F;
    public qw0 G;
    public rw0 H;
    public uw0 I;
    public boolean J;
    public ll0 K;
    public cd L;
    public final qh M;
    public wv N;
    public fs O;
    public iw0 P;
    public final wh Q;
    public final yj R;
    public boolean S;
    public long T;
    public cw U;
    public final r7 a;
    public final th b;
    public final rw0 c;
    public final ye0 d;
    public final cd e;
    public final cd f;
    public final j2 g;
    public final yh h;
    public fw j;
    public int k;
    public int l;
    public int m;
    public int[] o;
    public fe0 p;
    public boolean q;
    public boolean r;
    public he0 v;
    public boolean w;
    public boolean y;
    public final ArrayList i = new ArrayList();
    public final e20 n = new e20();
    public final ArrayList s = new ArrayList();
    public final e20 t = new e20();
    public ll0 u = ll0.h;
    public final e20 x = new e20();
    public int z = -1;

    public bw(r7 r7Var, th thVar, rw0 rw0Var, ye0 ye0Var, cd cdVar, cd cdVar2, j2 j2Var, yh yhVar) {
        boolean z;
        this.a = r7Var;
        this.b = thVar;
        this.c = rw0Var;
        this.d = ye0Var;
        this.e = cdVar;
        this.f = cdVar2;
        this.g = j2Var;
        this.h = yhVar;
        if (!thVar.f() && !thVar.d()) {
            z = false;
        } else {
            z = true;
        }
        this.C = z;
        this.D = new aw(0, this);
        this.E = new ArrayList();
        qw0 c = rw0Var.c();
        c.c();
        this.G = c;
        rw0 rw0Var2 = new rw0();
        if (thVar.f()) {
            rw0Var2.b();
        }
        if (thVar.d()) {
            rw0Var2.o = new he0();
        }
        this.H = rw0Var2;
        uw0 d = rw0Var2.d();
        d.e(true);
        this.I = d;
        this.M = new qh(this, cdVar);
        qw0 c2 = this.H.c();
        try {
            wv a = c2.a(0);
            c2.c();
            this.N = a;
            this.O = new fs();
            this.Q = new wh(this);
            yj j = thVar.j();
            yj z2 = z();
            this.R = j.i(z2 == null ? cr.e : z2);
        } catch (Throwable th) {
            c2.c();
            throw th;
        }
    }

    public static final int N(bw bwVar, int i, boolean z, int i2) {
        int i3;
        boolean z2;
        int i4;
        gw gwVar;
        Object obj;
        long[] jArr;
        int i5;
        long[] jArr2;
        int i6;
        int i7;
        qw0 qw0Var;
        qw0 qw0Var2 = bwVar.G;
        int i8 = 0;
        if (qw0Var2.j(i)) {
            int i9 = qw0Var2.i(i);
            Object p = qw0Var2.p(qw0Var2.b, i);
            if (i9 == 206 && o20.e(p, rh.e)) {
                Object h = qw0Var2.h(i, 0);
                yv yvVar = null;
                if (h instanceof gw) {
                    gwVar = (gw) h;
                } else {
                    gwVar = null;
                }
                if (gwVar != null) {
                    obj = gwVar.a;
                } else {
                    obj = null;
                }
                if (obj instanceof yv) {
                    yvVar = (yv) obj;
                }
                if (yvVar != null) {
                    we0 we0Var = yvVar.e.e;
                    Object[] objArr = we0Var.b;
                    long[] jArr3 = we0Var.a;
                    int length = jArr3.length - 2;
                    if (length >= 0) {
                        int i10 = 0;
                        while (true) {
                            long j = jArr3[i10];
                            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                int i11 = 8;
                                int i12 = 8 - ((~(i10 - length)) >>> 31);
                                int i13 = i8;
                                while (i13 < i12) {
                                    if ((255 & j) < 128) {
                                        bw bwVar2 = (bw) objArr[(i10 << 3) + i13];
                                        rw0 rw0Var = bwVar2.c;
                                        if (rw0Var.f > 0 && (rw0Var.e[1] & 67108864) != 0) {
                                            yh yhVar = bwVar2.h;
                                            synchronized (yhVar.h) {
                                                yhVar.p();
                                                i7 = i11;
                                                ve0 ve0Var = yhVar.r;
                                                yhVar.r = t20.n();
                                                try {
                                                    yhVar.z.b0(ve0Var);
                                                } finally {
                                                }
                                            }
                                            cd cdVar = new cd();
                                            bwVar2.L = cdVar;
                                            qw0 c = bwVar2.c.c();
                                            try {
                                                bwVar2.G = c;
                                                qh qhVar = bwVar2.M;
                                                cd cdVar2 = qhVar.b;
                                                try {
                                                    qhVar.b = cdVar;
                                                    bwVar2.M(0);
                                                    qh qhVar2 = bwVar2.M;
                                                    qhVar2.b();
                                                    jArr2 = jArr3;
                                                    try {
                                                        if (qhVar2.c) {
                                                            qw0Var = c;
                                                            try {
                                                                qhVar2.b.w.G(ri0.c);
                                                                if (qhVar2.c) {
                                                                    qhVar2.d(false);
                                                                    qhVar2.d(false);
                                                                    qhVar2.b.w.G(bi0.c);
                                                                    i6 = 0;
                                                                    qhVar2.c = false;
                                                                    qhVar.b = cdVar2;
                                                                    qw0Var.c();
                                                                }
                                                            } catch (Throwable th) {
                                                                th = th;
                                                                qhVar.b = cdVar2;
                                                                throw th;
                                                            }
                                                        } else {
                                                            qw0Var = c;
                                                        }
                                                        qhVar.b = cdVar2;
                                                        qw0Var.c();
                                                    } catch (Throwable th2) {
                                                        th = th2;
                                                        qw0Var.c();
                                                        throw th;
                                                    }
                                                    i6 = 0;
                                                } catch (Throwable th3) {
                                                    th = th3;
                                                    qw0Var = c;
                                                }
                                            } catch (Throwable th4) {
                                                th = th4;
                                                qw0Var = c;
                                            }
                                        } else {
                                            jArr2 = jArr3;
                                            i6 = i8;
                                            i7 = i11;
                                        }
                                        bwVar.b.r(bwVar2.h);
                                    } else {
                                        jArr2 = jArr3;
                                        i6 = i8;
                                        i7 = i11;
                                    }
                                    j >>= i7;
                                    i13++;
                                    i11 = i7;
                                    i8 = i6;
                                    jArr3 = jArr2;
                                }
                                jArr = jArr3;
                                i5 = i8;
                                if (i12 != i11) {
                                    break;
                                }
                            } else {
                                jArr = jArr3;
                                i5 = i8;
                            }
                            if (i10 == length) {
                                break;
                            }
                            i10++;
                            i8 = i5;
                            jArr3 = jArr;
                        }
                    }
                }
                return qw0Var2.o(i);
            }
            i3 = 1;
            if (!qw0Var2.l(i)) {
                return qw0Var2.o(i);
            }
        } else {
            i3 = 1;
            if (qw0Var2.d(i)) {
                int i14 = qw0Var2.b[(i * 5) + 3] + i;
                int i15 = 0;
                for (int i16 = i + 1; i16 < i14; i16 += qw0Var2.b[(i16 * 5) + 3]) {
                    boolean l = qw0Var2.l(i16);
                    if (l) {
                        bwVar.M.c();
                        qh qhVar3 = bwVar.M;
                        Object n = qw0Var2.n(i16);
                        qhVar3.c();
                        qhVar3.h.add(n);
                    }
                    if (!l && !z) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (l) {
                        i4 = 0;
                    } else {
                        i4 = i2 + i15;
                    }
                    i15 += N(bwVar, i16, z2, i4);
                    if (l) {
                        bwVar.M.c();
                        bwVar.M.a();
                    }
                }
                if (!qw0Var2.l(i)) {
                    return i15;
                }
            } else if (!qw0Var2.l(i)) {
                return qw0Var2.o(i);
            }
        }
        return i3;
    }

    public final boolean A() {
        mo0 x;
        if (!this.S && !this.y && !this.w && (x = x()) != null && (x.b & 8) == 0) {
            return true;
        }
        return false;
    }

    public final void B(ArrayList arrayList) {
        bw bwVar = this;
        cd cdVar = bwVar.f;
        qh qhVar = bwVar.M;
        cd cdVar2 = qhVar.b;
        try {
            qhVar.b = cdVar;
            cdVar.w.G(pi0.c);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                xj0 xj0Var = (xj0) arrayList.get(i);
                wd0 wd0Var = (wd0) xj0Var.e;
                wd0Var.getClass();
                wv i2 = k81.i(null);
                rw0 d = tw0.d(null);
                int a = d.a(i2);
                a20 a20Var = new a20();
                qhVar.b();
                bj0 bj0Var = qhVar.b.w;
                bj0Var.G(yh0.c);
                t20.P(bj0Var, 0, a20Var, 1, i2);
                if (d == bwVar.H) {
                    if (!bwVar.I.w) {
                        rh.a("Check failed");
                    }
                    bwVar.v();
                }
                qw0 c = d.c();
                try {
                    c.r(a);
                    qhVar.f = a;
                    cd cdVar3 = new cd();
                    bwVar.G(null, null, null, er.e, new y8(bwVar, cdVar3, c, wd0Var));
                    cd cdVar4 = qhVar.b;
                    cdVar4.getClass();
                    if (!cdVar3.w.F()) {
                        bj0 bj0Var2 = cdVar4.w;
                        bj0Var2.G(uh0.c);
                        t20.P(bj0Var2, 0, cdVar3, 1, a20Var);
                    }
                    c.c();
                    qhVar.b.w.G(ri0.c);
                    i++;
                    bwVar = this;
                } catch (Throwable th) {
                    c.c();
                    throw th;
                }
            }
            qhVar.b();
            qhVar.b.w.G(ci0.c);
            qhVar.f = 0;
            qhVar.b = cdVar2;
        } catch (Throwable th2) {
            qhVar.b = cdVar2;
            throw th2;
        }
    }

    public final void C(ll0 ll0Var, Object obj) {
        boolean z;
        S(126665345, null, 0, null);
        D();
        g0(obj);
        long j = this.T;
        int i = 2;
        try {
            this.T = 126665345L;
            if (this.S) {
                uw0.z(this.I);
            }
            if (this.S || o20.e(this.G.f(), ll0Var)) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                J(ll0Var);
            }
            S(202, rh.c, 0, ll0Var);
            this.K = null;
            boolean z2 = this.w;
            this.w = z;
            jc0.t(this, new gg(-59194059, true, new wa(i, obj)));
            this.w = z2;
        } finally {
        }
    }

    public final Object D() {
        boolean z = this.S;
        dt0 dt0Var = ph.a;
        if (z) {
            if (this.r) {
                rh.a("A call to createNode(), emitNode() or useNode() expected");
                return dt0Var;
            }
        } else {
            Object m = this.G.m();
            if (!this.y || (m instanceof oq0)) {
                return m;
            }
        }
        return dt0Var;
    }

    public final List E() {
        yh yhVar;
        th thVar = this.b;
        sh h = thVar.h();
        if (d3.A(h)) {
            yhVar = (yh) h;
        } else {
            yhVar = null;
        }
        if (yhVar != null) {
            rw0 rw0Var = yhVar.j;
            qw0 c = tw0.d(rw0Var).c();
            try {
                Integer w = f31.w(c, thVar, 0, c.c);
                if (w != null) {
                    c = tw0.d(rw0Var).c();
                    try {
                        ArrayList U = f31.U(c, w.intValue(), 0);
                        c.c();
                        return me.b0(U, yhVar.z.E());
                    } finally {
                    }
                }
            } finally {
            }
        }
        return er.e;
    }

    public final int F(int i) {
        int q = this.G.q(i) + 1;
        int i2 = 0;
        while (q < i) {
            if (!this.G.k(q)) {
                i2++;
            }
            q += this.G.b[(q * 5) + 3];
        }
        return i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0053, code lost:
    
        if (r10 == null) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object G(defpackage.yh r9, defpackage.yh r10, java.lang.Integer r11, java.util.List r12, defpackage.vu r13) {
        /*
            r8 = this;
            boolean r0 = r8.F
            int r1 = r8.k
            r2 = 1
            r8.F = r2     // Catch: java.lang.Throwable -> L24
            r2 = 0
            r8.k = r2     // Catch: java.lang.Throwable -> L24
            int r3 = r12.size()     // Catch: java.lang.Throwable -> L24
            r4 = r2
        Lf:
            r5 = 0
            if (r4 >= r3) goto L2c
            java.lang.Object r6 = r12.get(r4)     // Catch: java.lang.Throwable -> L24
            xj0 r6 = (defpackage.xj0) r6     // Catch: java.lang.Throwable -> L24
            java.lang.Object r7 = r6.e     // Catch: java.lang.Throwable -> L24
            mo0 r7 = (defpackage.mo0) r7     // Catch: java.lang.Throwable -> L24
            java.lang.Object r6 = r6.f     // Catch: java.lang.Throwable -> L24
            if (r6 == 0) goto L26
            r8.a0(r7, r6)     // Catch: java.lang.Throwable -> L24
            goto L29
        L24:
            r9 = move-exception
            goto L5e
        L26:
            r8.a0(r7, r5)     // Catch: java.lang.Throwable -> L24
        L29:
            int r4 = r4 + 1
            goto Lf
        L2c:
            if (r9 == 0) goto L55
            if (r11 == 0) goto L35
            int r11 = r11.intValue()     // Catch: java.lang.Throwable -> L24
            goto L36
        L35:
            r11 = -1
        L36:
            if (r10 == 0) goto L4f
            if (r10 == r9) goto L4f
            if (r11 < 0) goto L4f
            r9.v = r10     // Catch: java.lang.Throwable -> L24
            r9.w = r11     // Catch: java.lang.Throwable -> L24
            java.lang.Object r10 = r13.a()     // Catch: java.lang.Throwable -> L49
            r9.v = r5     // Catch: java.lang.Throwable -> L24
            r9.w = r2     // Catch: java.lang.Throwable -> L24
            goto L53
        L49:
            r10 = move-exception
            r9.v = r5     // Catch: java.lang.Throwable -> L24
            r9.w = r2     // Catch: java.lang.Throwable -> L24
            throw r10     // Catch: java.lang.Throwable -> L24
        L4f:
            java.lang.Object r10 = r13.a()     // Catch: java.lang.Throwable -> L24
        L53:
            if (r10 != 0) goto L59
        L55:
            java.lang.Object r10 = r13.a()     // Catch: java.lang.Throwable -> L24
        L59:
            r8.F = r0
            r8.k = r1
            return r10
        L5e:
            r8.F = r0
            r8.k = r1
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bw.G(yh, yh, java.lang.Integer, java.util.List, vu):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x003b, code lost:
    
        if (r4.b < r6) goto L11;
     */
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0339  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void H() {
        /*
            Method dump skipped, instructions count: 887
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bw.H():void");
    }

    public final void I() {
        int i;
        M(this.G.g);
        qh qhVar = this.M;
        qhVar.d(false);
        e20 e20Var = qhVar.d;
        bw bwVar = qhVar.a;
        qw0 qw0Var = bwVar.G;
        if (qw0Var.c > 0 && e20Var.a(-2) != (i = qw0Var.i)) {
            if (!qhVar.c && qhVar.e) {
                qhVar.d(false);
                qhVar.b.w.G(fi0.c);
                qhVar.c = true;
            }
            if (i > 0) {
                wv a = qw0Var.a(i);
                e20Var.c(i);
                qhVar.d(false);
                bj0 bj0Var = qhVar.b.w;
                bj0Var.G(ei0.c);
                t20.O(bj0Var, 0, a);
                qhVar.c = true;
            }
        }
        qhVar.b.w.G(ni0.c);
        int i2 = qhVar.f;
        qw0 qw0Var2 = bwVar.G;
        qhVar.f = qw0Var2.b[(qw0Var2.g * 5) + 3] + i2;
    }

    public final void J(ll0 ll0Var) {
        he0 he0Var = this.v;
        if (he0Var == null) {
            he0Var = new he0();
            this.v = he0Var;
        }
        he0Var.h(this.G.g, ll0Var);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void K(int r7, int r8, int r9) {
        /*
            r6 = this;
            qw0 r0 = r6.G
            if (r7 != r8) goto L5
            goto L1a
        L5:
            if (r7 == r9) goto L6b
            if (r8 != r9) goto Lb
            goto L6b
        Lb:
            int r1 = r0.q(r7)
            if (r1 != r8) goto L14
            r9 = r8
            goto L6b
        L14:
            int r1 = r0.q(r8)
            if (r1 != r7) goto L1c
        L1a:
            r9 = r7
            goto L6b
        L1c:
            int r1 = r0.q(r7)
            int r2 = r0.q(r8)
            if (r1 != r2) goto L2b
            int r9 = r0.q(r7)
            goto L6b
        L2b:
            r1 = 0
            r2 = r7
            r3 = r1
        L2e:
            if (r2 <= 0) goto L39
            if (r2 == r9) goto L39
            int r2 = r0.q(r2)
            int r3 = r3 + 1
            goto L2e
        L39:
            r2 = r8
            r4 = r1
        L3b:
            if (r2 <= 0) goto L46
            if (r2 == r9) goto L46
            int r2 = r0.q(r2)
            int r4 = r4 + 1
            goto L3b
        L46:
            int r9 = r3 - r4
            r5 = r7
            r2 = r1
        L4a:
            if (r2 >= r9) goto L53
            int r5 = r0.q(r5)
            int r2 = r2 + 1
            goto L4a
        L53:
            int r4 = r4 - r3
            r9 = r8
        L55:
            if (r1 >= r4) goto L5e
            int r9 = r0.q(r9)
            int r1 = r1 + 1
            goto L55
        L5e:
            r1 = r9
            r9 = r5
        L60:
            if (r9 == r1) goto L6b
            int r9 = r0.q(r9)
            int r1 = r0.q(r1)
            goto L60
        L6b:
            if (r7 <= 0) goto L7f
            if (r7 == r9) goto L7f
            boolean r1 = r0.l(r7)
            if (r1 == 0) goto L7a
            qh r1 = r6.M
            r1.a()
        L7a:
            int r7 = r0.q(r7)
            goto L6b
        L7f:
            r6.o(r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bw.K(int, int, int):void");
    }

    public final Object L() {
        boolean z = this.S;
        dt0 dt0Var = ph.a;
        if (z) {
            if (this.r) {
                rh.a("A call to createNode(), emitNode() or useNode() expected");
                return dt0Var;
            }
        } else {
            Object m = this.G.m();
            if (!this.y || (m instanceof oq0)) {
                if (m instanceof gw) {
                    return ((gw) m).a;
                }
                return m;
            }
        }
        return dt0Var;
    }

    public final void M(int i) {
        boolean l = this.G.l(i);
        qh qhVar = this.M;
        if (l) {
            qhVar.c();
            Object n = this.G.n(i);
            qhVar.c();
            qhVar.h.add(n);
        }
        N(this, i, l, 0);
        qhVar.c();
        if (l) {
            qhVar.a();
        }
    }

    public final boolean O(int i, boolean z) {
        mo0 x;
        int i2;
        if ((i & 1) == 0 && (this.S || this.y)) {
            iw0 iw0Var = this.P;
            if (iw0Var != null && (x = x()) != null && iw0Var.a()) {
                int i3 = x.b;
                if ((i3 & 512) != 0) {
                    return true;
                }
                int i4 = i3 | 1;
                x.b = i4;
                if (this.y) {
                    i2 = i3 | 129;
                } else {
                    i2 = i4 & (-129);
                }
                x.b = i2 | 256;
                bj0 bj0Var = this.M.b.w;
                bj0Var.G(mi0.c);
                t20.O(bj0Var, 0, x);
                this.b.q(x);
                return false;
            }
        } else if (!z && A()) {
            return false;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00cd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void P() {
        /*
            Method dump skipped, instructions count: 249
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bw.P():void");
    }

    public final void Q() {
        int i;
        qw0 qw0Var = this.G;
        int i2 = qw0Var.i;
        if (i2 >= 0) {
            i = qw0Var.b[(i2 * 5) + 1] & 67108863;
        } else {
            i = 0;
        }
        this.l = i;
        qw0Var.t();
    }

    public final void R() {
        if (this.l != 0) {
            rh.a("No nodes can be emitted before calling skipAndEndGroup");
        }
        if (!this.S) {
            mo0 x = x();
            if (x != null) {
                int i = x.b;
                if ((i & 128) == 0) {
                    x.b = i | 16;
                }
            }
            if (this.s.isEmpty()) {
                Q();
            } else {
                H();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0142  */
    /* JADX WARN: Type inference failed for: r2v7, types: [uw0] */
    /* JADX WARN: Type inference failed for: r30v0, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v46, types: [uw0] */
    /* JADX WARN: Type inference failed for: r8v0, types: [dt0, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void S(int r27, defpackage.rh0 r28, int r29, java.lang.Object r30) {
        /*
            Method dump skipped, instructions count: 927
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bw.S(int, rh0, int, java.lang.Object):void");
    }

    public final void T(int i, rh0 rh0Var) {
        S(i, rh0Var, 0, null);
    }

    public final void U(Object obj, boolean z) {
        if (z) {
            qw0 qw0Var = this.G;
            if (qw0Var.k <= 0) {
                if ((qw0Var.b[(qw0Var.g * 5) + 1] & 1073741824) == 0) {
                    cn0.a("Expected a node group");
                }
                qw0Var.u();
                return;
            }
            return;
        }
        if (obj != null && this.G.f() != obj) {
            qh qhVar = this.M;
            qhVar.getClass();
            qhVar.d(false);
            bj0 bj0Var = qhVar.b.w;
            bj0Var.G(ui0.c);
            t20.O(bj0Var, 0, obj);
        }
        this.G.u();
    }

    public final void V(int i) {
        int i2;
        int i3;
        if (this.j != null) {
            S(i, null, 0, null);
            return;
        }
        if (this.r) {
            rh.a("A call to createNode(), emitNode() or useNode() expected");
        }
        this.T = Long.rotateLeft(Long.rotateLeft(this.T, 3) ^ i, 3) ^ this.m;
        this.m++;
        qw0 qw0Var = this.G;
        boolean z = this.S;
        dt0 dt0Var = ph.a;
        if (z) {
            qw0Var.k++;
            this.I.Q(i, dt0Var, false, dt0Var);
            u(false, null);
            return;
        }
        if (qw0Var.g() == i && ((i3 = qw0Var.g) >= qw0Var.h || (qw0Var.b[(i3 * 5) + 1] & 536870912) == 0)) {
            qw0Var.u();
            u(false, null);
            return;
        }
        if (qw0Var.k <= 0 && (i2 = qw0Var.g) != qw0Var.h) {
            int i4 = this.k;
            I();
            this.M.e(i4, qw0Var.s());
            f31.i(this.s, i2, qw0Var.g);
        }
        qw0Var.k++;
        this.S = true;
        this.K = null;
        if (this.I.w) {
            uw0 d = this.H.d();
            this.I = d;
            d.M();
            this.J = false;
            this.K = null;
        }
        uw0 uw0Var = this.I;
        uw0Var.d();
        int i5 = uw0Var.t;
        uw0Var.Q(i, dt0Var, false, dt0Var);
        this.N = uw0Var.b(i5);
        u(false, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final defpackage.bw W(int r7) {
        /*
            r6 = this;
            r6.V(r7)
            boolean r7 = r6.S
            j2 r0 = r6.g
            java.util.ArrayList r1 = r6.E
            yh r2 = r6.h
            if (r7 == 0) goto L26
            mo0 r7 = new mo0
            r7.<init>(r2)
            r1.add(r7)
            r6.g0(r7)
            int r1 = r6.B
            r7.e = r1
            int r1 = r7.b
            r1 = r1 & (-17)
            r7.b = r1
            r0.g()
            return r6
        L26:
            qw0 r7 = r6.G
            int r7 = r7.i
            java.util.ArrayList r3 = r6.s
            int r7 = defpackage.f31.v(r7, r3)
            if (r7 < 0) goto L39
            java.lang.Object r7 = r3.remove(r7)
            v20 r7 = (defpackage.v20) r7
            goto L3a
        L39:
            r7 = 0
        L3a:
            qw0 r3 = r6.G
            java.lang.Object r3 = r3.m()
            dt0 r4 = defpackage.ph.a
            boolean r4 = defpackage.o20.e(r3, r4)
            if (r4 == 0) goto L51
            mo0 r3 = new mo0
            r3.<init>(r2)
            r6.g0(r3)
            goto L56
        L51:
            r3.getClass()
            mo0 r3 = (defpackage.mo0) r3
        L56:
            r2 = 0
            r4 = 1
            if (r7 != 0) goto L6e
            int r7 = r3.b
            r5 = r7 & 64
            if (r5 == 0) goto L62
            r5 = r4
            goto L63
        L62:
            r5 = r2
        L63:
            if (r5 == 0) goto L69
            r7 = r7 & (-65)
            r3.b = r7
        L69:
            if (r5 == 0) goto L6c
            goto L6e
        L6c:
            r7 = r2
            goto L6f
        L6e:
            r7 = r4
        L6f:
            int r5 = r3.b
            if (r7 == 0) goto L76
            r7 = r5 | 8
            goto L78
        L76:
            r7 = r5 & (-9)
        L78:
            r3.b = r7
            r1.add(r3)
            int r7 = r6.B
            r3.e = r7
            int r7 = r3.b
            r7 = r7 & (-17)
            r3.b = r7
            r0.g()
            int r7 = r3.b
            r0 = r7 & 256(0x100, float:3.59E-43)
            if (r0 == 0) goto Lba
            r7 = r7 & (-257(0xfffffffffffffeff, float:NaN))
            r7 = r7 | 512(0x200, float:7.17E-43)
            r3.b = r7
            qh r7 = r6.M
            cd r7 = r7.b
            bj0 r7 = r7.w
            si0 r0 = defpackage.si0.c
            r7.G(r0)
            defpackage.t20.O(r7, r2, r3)
            boolean r7 = r6.y
            if (r7 != 0) goto Lba
            int r7 = r3.b
            r0 = r7 & 128(0x80, float:1.8E-43)
            if (r0 == 0) goto Lba
            r6.y = r4
            qw0 r0 = r6.G
            int r0 = r0.i
            r6.z = r0
            r7 = r7 | 1024(0x400, float:1.435E-42)
            r3.b = r7
        Lba:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bw.W(int):bw");
    }

    public final void X(Object obj) {
        if (!this.S && this.G.g() == 207 && !o20.e(this.G.f(), obj) && this.z < 0) {
            this.z = this.G.g;
            this.y = true;
        }
        S(207, null, 0, obj);
    }

    public final void Y() {
        S(125, null, 2, null);
        this.r = true;
    }

    public final void Z() {
        this.m = 0;
        this.G = this.c.c();
        S(100, null, 0, null);
        th thVar = this.b;
        thVar.t();
        ll0 i = thVar.i();
        this.x.c(this.w ? 1 : 0);
        this.w = f(i);
        this.K = null;
        if (!this.q) {
            this.q = thVar.e();
        }
        if (!this.C) {
            this.C = thVar.f();
        }
        if (this.C) {
            qy0 qy0Var = xh.a;
            qy0Var.getClass();
            i = i.b(qy0Var, new ry0(z()));
        }
        this.u = i;
        Set set = (Set) jc0.A(i, o10.a);
        if (set != null) {
            set.add(w());
            thVar.o(set);
        }
        long g = thVar.g();
        S((int) (g ^ (g >>> 32)), null, 0, null);
    }

    public final void a() {
        i();
        this.i.clear();
        this.n.b = 0;
        this.t.b = 0;
        this.x.b = 0;
        this.v = null;
        fs fsVar = this.O;
        fsVar.b.D();
        fsVar.a.D();
        this.T = 0L;
        this.A = 0;
        this.r = false;
        this.S = false;
        this.y = false;
        this.F = false;
        this.z = -1;
        qw0 qw0Var = this.G;
        if (!qw0Var.f) {
            qw0Var.c();
        }
        if (!this.I.w) {
            v();
        }
    }

    public final boolean a0(mo0 mo0Var, Object obj) {
        wv wvVar = mo0Var.c;
        if (wvVar != null) {
            int a = this.G.a.a(k81.i(wvVar));
            if (this.F && a >= this.G.g) {
                ArrayList arrayList = this.s;
                int v = f31.v(a, arrayList);
                if (v < 0) {
                    int i = -(v + 1);
                    if (!(obj instanceof ym)) {
                        obj = null;
                    }
                    arrayList.add(i, new v20(mo0Var, a, obj));
                    return true;
                }
                v20 v20Var = (v20) arrayList.get(v);
                if (obj instanceof ym) {
                    Object obj2 = v20Var.c;
                    if (obj2 == null) {
                        v20Var.c = obj;
                        return true;
                    }
                    if (obj2 instanceof we0) {
                        ((we0) obj2).a(obj);
                        return true;
                    }
                    we0 we0Var = at0.a;
                    we0 we0Var2 = new we0(2);
                    we0Var2.k(obj2);
                    we0Var2.k(obj);
                    v20Var.c = we0Var2;
                    return true;
                }
                v20Var.c = null;
                return true;
            }
            return false;
        }
        return false;
    }

    public final void b(kv kvVar, Object obj) {
        if (this.S) {
            bj0 bj0Var = this.O.a;
            bj0Var.G(vi0.c);
            t20.O(bj0Var, 0, obj);
            kvVar.getClass();
            f31.n(2, kvVar);
            t20.O(bj0Var, 1, kvVar);
            return;
        }
        qh qhVar = this.M;
        qhVar.b();
        bj0 bj0Var2 = qhVar.b.w;
        bj0Var2.G(vi0.c);
        kvVar.getClass();
        f31.n(2, kvVar);
        t20.P(bj0Var2, 0, obj, 1, kvVar);
    }

    public final void b0(ve0 ve0Var) {
        wv wvVar;
        ArrayList arrayList = this.s;
        for (int q = jc0.q(arrayList); -1 < q; q--) {
            v20 v20Var = (v20) arrayList.get(q);
            wv wvVar2 = v20Var.a.c;
            if (wvVar2 != null) {
                wvVar = k81.i(wvVar2);
            } else {
                wvVar = null;
            }
            if (wvVar != null && wvVar.a()) {
                int i = v20Var.b;
                int i2 = wvVar.a;
                if (i != i2) {
                    v20Var.b = i2;
                }
            } else {
                arrayList.remove(q);
            }
        }
        Object[] objArr = ve0Var.b;
        Object[] objArr2 = ve0Var.c;
        long[] jArr = ve0Var.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i3 = 0;
            while (true) {
                long j = jArr[i3];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i4 = 8 - ((~(i3 - length)) >>> 31);
                    for (int i5 = 0; i5 < i4; i5++) {
                        if ((255 & j) < 128) {
                            int i6 = (i3 << 3) + i5;
                            Object obj = objArr[i6];
                            Object obj2 = objArr2[i6];
                            obj.getClass();
                            mo0 mo0Var = (mo0) obj;
                            wv wvVar3 = mo0Var.c;
                            if (wvVar3 != null) {
                                int i7 = k81.i(wvVar3).a;
                                if (obj2 == dt0.f) {
                                    obj2 = null;
                                }
                                arrayList.add(new v20(mo0Var, i7, obj2));
                            }
                        }
                        j >>= 8;
                    }
                    if (i4 != 8) {
                        break;
                    }
                }
                if (i3 == length) {
                    break;
                } else {
                    i3++;
                }
            }
        }
        qe.O(arrayList, f31.e);
    }

    public final boolean c(float f) {
        Object D = D();
        if ((D instanceof Float) && f == ((Number) D).floatValue()) {
            return false;
        }
        g0(Float.valueOf(f));
        return true;
    }

    public final void c0(int i, int i2) {
        if (h0(i) != i2) {
            if (i < 0) {
                fe0 fe0Var = this.p;
                if (fe0Var == null) {
                    fe0Var = new fe0();
                    this.p = fe0Var;
                }
                fe0Var.f(i, i2);
                return;
            }
            int[] iArr = this.o;
            if (iArr == null) {
                int i3 = this.G.c;
                int[] iArr2 = new int[i3];
                Arrays.fill(iArr2, 0, i3, -1);
                this.o = iArr2;
                iArr = iArr2;
            }
            iArr[i] = i2;
        }
    }

    public final boolean d(int i) {
        Object D = D();
        if ((D instanceof Integer) && i == ((Number) D).intValue()) {
            return false;
        }
        g0(Integer.valueOf(i));
        return true;
    }

    public final void d0(int i, int i2) {
        int h0 = h0(i);
        if (h0 != i2) {
            int i3 = i2 - h0;
            ArrayList arrayList = this.i;
            int size = arrayList.size() - 1;
            while (i != -1) {
                int h02 = h0(i) + i3;
                c0(i, h02);
                int i4 = size;
                while (true) {
                    if (-1 < i4) {
                        fw fwVar = (fw) arrayList.get(i4);
                        if (fwVar != null && fwVar.a(i, h02)) {
                            size = i4 - 1;
                            break;
                        }
                        i4--;
                    } else {
                        break;
                    }
                }
                qw0 qw0Var = this.G;
                if (i < 0) {
                    i = qw0Var.i;
                } else if (!qw0Var.l(i)) {
                    i = this.G.q(i);
                } else {
                    return;
                }
            }
        }
    }

    public final boolean e(long j) {
        Object D = D();
        if ((D instanceof Long) && j == ((Number) D).longValue()) {
            return false;
        }
        g0(Long.valueOf(j));
        return true;
    }

    public final ll0 e0(ll0 ll0Var, ll0 ll0Var2) {
        ll0Var.getClass();
        kl0 kl0Var = new kl0(ll0Var);
        kl0Var.putAll(ll0Var2);
        ll0 b = kl0Var.b();
        T(204, rh.d);
        D();
        g0(b);
        D();
        g0(ll0Var2);
        p(false);
        return b;
    }

    public final boolean f(Object obj) {
        if (!o20.e(D(), obj)) {
            g0(obj);
            return true;
        }
        return false;
    }

    public final void f0(Object obj) {
        if (obj instanceof np0) {
            gw gwVar = new gw((np0) obj, this.m - 1);
            if (this.S) {
                bj0 bj0Var = this.M.b.w;
                bj0Var.G(li0.c);
                t20.O(bj0Var, 0, gwVar);
            }
            this.d.add(obj);
            obj = gwVar;
        }
        g0(obj);
    }

    public final boolean g(boolean z) {
        Object D = D();
        if ((D instanceof Boolean) && z == ((Boolean) D).booleanValue()) {
            return false;
        }
        g0(Boolean.valueOf(z));
        return true;
    }

    public final void g0(Object obj) {
        if (this.S) {
            uw0 uw0Var = this.I;
            if (uw0Var.n > 0 && uw0Var.i != uw0Var.k) {
                he0 he0Var = uw0Var.s;
                if (he0Var == null) {
                    he0Var = new he0();
                }
                uw0Var.s = he0Var;
                int i = uw0Var.v;
                Object b = he0Var.b(i);
                if (b == null) {
                    b = new pe0();
                    he0Var.h(i, b);
                }
                ((pe0) b).a(obj);
                return;
            }
            uw0Var.F(obj);
            return;
        }
        qw0 qw0Var = this.G;
        boolean z = qw0Var.n;
        qh qhVar = this.M;
        if (z) {
            int b2 = (qw0Var.l - tw0.b(qw0Var.b, qw0Var.i)) - 1;
            if (qhVar.a.G.i - qhVar.f < 0) {
                qw0 qw0Var2 = this.G;
                wv a = qw0Var2.a(qw0Var2.i);
                bj0 bj0Var = qhVar.b.w;
                bj0Var.G(gi0.f);
                t20.P(bj0Var, 0, obj, 1, a);
                bj0Var.c[bj0Var.d - bj0Var.a[bj0Var.b - 1].a] = b2;
                return;
            }
            qhVar.d(true);
            bj0 bj0Var2 = qhVar.b.w;
            bj0Var2.G(gi0.g);
            t20.O(bj0Var2, 0, obj);
            bj0Var2.c[bj0Var2.d - bj0Var2.a[bj0Var2.b - 1].a] = b2;
            return;
        }
        wv a2 = qw0Var.a(qw0Var.i);
        bj0 bj0Var3 = qhVar.b.w;
        bj0Var3.G(th0.c);
        t20.P(bj0Var3, 0, a2, 1, obj);
    }

    public final boolean h(Object obj) {
        if (D() != obj) {
            g0(obj);
            return true;
        }
        return false;
    }

    public final int h0(int i) {
        int i2;
        if (i < 0) {
            fe0 fe0Var = this.p;
            if (fe0Var == null || fe0Var.c(i) < 0) {
                return 0;
            }
            int c = fe0Var.c(i);
            if (c >= 0) {
                return fe0Var.c[c];
            }
            throw new NoSuchElementException("Cannot find value for key " + i);
        }
        int[] iArr = this.o;
        if (iArr != null && (i2 = iArr[i]) >= 0) {
            return i2;
        }
        return this.G.o(i);
    }

    public final void i() {
        this.j = null;
        this.k = 0;
        this.l = 0;
        this.T = 0L;
        this.r = false;
        qh qhVar = this.M;
        qhVar.c = false;
        qhVar.d.b = 0;
        qhVar.f = 0;
        qhVar.e = true;
        qhVar.g = 0;
        qhVar.h.clear();
        qhVar.i = -1;
        qhVar.j = -1;
        qhVar.k = -1;
        qhVar.l = 0;
        this.E.clear();
        this.o = null;
        this.p = null;
    }

    public final void i0() {
        if (!this.r) {
            rh.a("A call to createNode(), emitNode() or useNode() expected was not expected");
        }
        this.r = false;
        if (this.S) {
            rh.a("useNode() called while inserting");
        }
        qw0 qw0Var = this.G;
        Object n = qw0Var.n(qw0Var.i);
        qh qhVar = this.M;
        qhVar.c();
        qhVar.h.add(n);
        if (this.y && (n instanceof xg)) {
            qhVar.b();
            qhVar.b.w.G(xi0.c);
        }
    }

    public final Object j(do0 do0Var) {
        return jc0.A(l(), do0Var);
    }

    public final void k(vu vuVar) {
        if (!this.r) {
            rh.a("A call to createNode(), emitNode() or useNode() expected was not expected");
        }
        this.r = false;
        if (!this.S) {
            rh.a("createNode() can only be called when inserting");
        }
        e20 e20Var = this.n;
        int i = e20Var.a[e20Var.b - 1];
        uw0 uw0Var = this.I;
        wv b = uw0Var.b(uw0Var.v);
        this.l++;
        fs fsVar = this.O;
        bj0 bj0Var = fsVar.a;
        bj0Var.G(gi0.d);
        t20.O(bj0Var, 0, vuVar);
        bj0Var.c[bj0Var.d - bj0Var.a[bj0Var.b - 1].a] = i;
        t20.O(bj0Var, 1, b);
        bj0 bj0Var2 = fsVar.b;
        bj0Var2.G(gi0.e);
        bj0Var2.c[bj0Var2.d - bj0Var2.a[bj0Var2.b - 1].a] = i;
        t20.O(bj0Var2, 0, b);
    }

    public final ll0 l() {
        ll0 ll0Var;
        ll0 ll0Var2 = this.K;
        if (ll0Var2 != null) {
            return ll0Var2;
        }
        int i = this.G.i;
        boolean z = this.S;
        rh0 rh0Var = rh.c;
        if (z && this.J) {
            int i2 = this.I.v;
            while (i2 > 0) {
                if (this.I.s(i2) == 202 && o20.e(this.I.t(i2), rh0Var)) {
                    Object q = this.I.q(i2);
                    q.getClass();
                    ll0 ll0Var3 = (ll0) q;
                    this.K = ll0Var3;
                    return ll0Var3;
                }
                uw0 uw0Var = this.I;
                i2 = uw0Var.E(uw0Var.b, i2);
            }
        }
        if (this.G.c > 0) {
            while (i > 0) {
                if (this.G.i(i) == 202) {
                    qw0 qw0Var = this.G;
                    if (o20.e(qw0Var.p(qw0Var.b, i), rh0Var)) {
                        he0 he0Var = this.v;
                        if (he0Var == null || (ll0Var = (ll0) he0Var.b(i)) == null) {
                            qw0 qw0Var2 = this.G;
                            Object b = qw0Var2.b(qw0Var2.b, i);
                            b.getClass();
                            ll0Var = (ll0) b;
                        }
                        this.K = ll0Var;
                        return ll0Var;
                    }
                }
                i = this.G.q(i);
            }
        }
        ll0 ll0Var4 = this.u;
        this.K = ll0Var4;
        return ll0Var4;
    }

    public final fh m() {
        Collection collection;
        Object obj;
        if (!this.b.k()) {
            return null;
        }
        ka0 ka0Var = new ka0(10);
        uw0 uw0Var = this.I;
        ka0Var.addAll(f31.o(uw0Var, null, uw0Var.t, null));
        qw0 qw0Var = this.G;
        boolean z = qw0Var.f;
        int[] iArr = qw0Var.b;
        if (!z && qw0Var.c != 0) {
            io0 io0Var = new io0(qw0Var);
            int i = qw0Var.i;
            Object valueOf = Integer.valueOf(qw0Var.l - tw0.b(iArr, i));
            while (i >= 0) {
                if (qw0Var.k(i)) {
                    obj = qw0Var.p(iArr, i);
                } else {
                    obj = ph.a;
                }
                io0Var.h(qw0Var.i(i), obj, qw0Var.a.f(i), valueOf);
                valueOf = qw0Var.a(i);
                i = qw0Var.q(i);
            }
            collection = (ArrayList) io0Var.a;
        } else {
            collection = er.e;
        }
        ka0Var.addAll(collection);
        ka0Var.addAll(E());
        return new fh(jc0.j(ka0Var), this.C);
    }

    public final void n(ve0 ve0Var, kv kvVar) {
        ArrayList arrayList = this.s;
        if (this.F) {
            rh.a("Reentrant composition is not supported");
        }
        this.g.g();
        Trace.beginSection("Compose:recompose");
        try {
            long g = cx0.j().g();
            this.B = (int) (g ^ (g >>> 32));
            this.v = null;
            b0(ve0Var);
            this.k = 0;
            this.F = true;
            try {
                Z();
                Object D = D();
                if (D != kvVar && kvVar != null) {
                    g0(kvVar);
                }
                aw awVar = this.D;
                ef0 q = n30.q();
                try {
                    q.b(awVar);
                    rh0 rh0Var = rh.a;
                    if (kvVar != null) {
                        T(200, rh0Var);
                        jc0.t(this, kvVar);
                        p(false);
                    } else if (this.w && D != null && !D.equals(ph.a)) {
                        T(200, rh0Var);
                        f31.n(2, D);
                        jc0.t(this, (kv) D);
                        p(false);
                    } else {
                        P();
                    }
                    q.k(q.g - 1);
                    t();
                    this.F = false;
                    arrayList.clear();
                    if (!this.I.w) {
                        rh.a("Check failed");
                    }
                    v();
                } catch (Throwable th) {
                    q.k(q.g - 1);
                    throw th;
                }
            } finally {
            }
        } finally {
            Trace.endSection();
        }
    }

    public final void o(int i, int i2) {
        if (i > 0 && i != i2) {
            o(this.G.q(i), i2);
            if (this.G.l(i)) {
                Object n = this.G.n(i);
                qh qhVar = this.M;
                qhVar.c();
                qhVar.h.add(n);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:147:0x03a9  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x042f  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x05b2  */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v29, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v32 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void p(boolean r43) {
        /*
            Method dump skipped, instructions count: 1608
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bw.p(boolean):void");
    }

    public final void q() {
        p(false);
        mo0 x = x();
        if (x != null) {
            int i = x.b;
            if ((i & 1) != 0) {
                x.b = i | 2;
            }
        }
    }

    public final mo0 r() {
        mo0 mo0Var;
        mo0 mo0Var2;
        wv a;
        lo0 lo0Var;
        ArrayList arrayList = this.E;
        if (!arrayList.isEmpty()) {
            mo0Var = (mo0) arrayList.remove(arrayList.size() - 1);
        } else {
            mo0Var = null;
        }
        int i = 0;
        if (mo0Var != null) {
            mo0Var.b &= -9;
            this.g.g();
            int i2 = this.B;
            oe0 oe0Var = mo0Var.f;
            if (oe0Var != null && (mo0Var.b & 16) == 0) {
                Object[] objArr = oe0Var.b;
                int[] iArr = oe0Var.c;
                long[] jArr = oe0Var.a;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i3 = 0;
                    loop0: while (true) {
                        long j = jArr[i3];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i4 = 8 - ((~(i3 - length)) >>> 31);
                            for (int i5 = 0; i5 < i4; i5++) {
                                if ((j & 255) < 128) {
                                    int i6 = (i3 << 3) + i5;
                                    Object obj = objArr[i6];
                                    if (iArr[i6] != i2) {
                                        lo0Var = new lo0(i2, i, mo0Var, oe0Var);
                                        break loop0;
                                    }
                                }
                                j >>= 8;
                            }
                            if (i4 != 8) {
                                break;
                            }
                        }
                        if (i3 == length) {
                            break;
                        }
                        i3++;
                    }
                }
            }
            lo0Var = null;
            qh qhVar = this.M;
            if (lo0Var != null) {
                bj0 bj0Var = qhVar.b.w;
                bj0Var.G(ai0.c);
                t20.P(bj0Var, 0, lo0Var, 1, this.h);
            }
            int i7 = mo0Var.b;
            if ((i7 & 512) != 0) {
                mo0Var.b = i7 & (-513);
                bj0 bj0Var2 = qhVar.b.w;
                bj0Var2.G(di0.c);
                t20.O(bj0Var2, 0, mo0Var);
                int i8 = mo0Var.b;
                mo0Var.b = i8 & (-129);
                if ((i8 & 1024) != 0) {
                    mo0Var.b = i8 & (-1153);
                    if (this.z == this.G.i) {
                        this.y = false;
                        this.z = -1;
                    }
                }
            }
        }
        if (mo0Var != null) {
            int i9 = mo0Var.b;
            if ((i9 & 16) == 0 && ((i9 & 1) != 0 || this.q)) {
                if (mo0Var.c == null) {
                    if (this.S) {
                        uw0 uw0Var = this.I;
                        a = uw0Var.b(uw0Var.v);
                    } else {
                        qw0 qw0Var = this.G;
                        a = qw0Var.a(qw0Var.i);
                    }
                    mo0Var.c = a;
                }
                mo0Var.b &= -5;
                mo0Var2 = mo0Var;
                p(false);
                return mo0Var2;
            }
        }
        mo0Var2 = null;
        p(false);
        return mo0Var2;
    }

    public final void s() {
        if (this.F || this.z != 0) {
            cn0.a("Cannot disable reuse from root if it was caused by other groups");
        }
        this.z = -1;
        this.y = false;
    }

    public final void t() {
        boolean z = false;
        p(false);
        this.b.c();
        p(false);
        qh qhVar = this.M;
        if (qhVar.c) {
            qhVar.d(false);
            qhVar.d(false);
            qhVar.b.w.G(bi0.c);
            qhVar.c = false;
        }
        qhVar.b();
        if (qhVar.d.b != 0) {
            rh.a("Missed recording an endGroup()");
        }
        if (!this.i.isEmpty()) {
            rh.a("Start/end imbalance");
        }
        i();
        this.G.c();
        if (this.x.b() != 0) {
            z = true;
        }
        this.w = z;
    }

    public final void u(boolean z, fw fwVar) {
        this.i.add(this.j);
        this.j = fwVar;
        int i = this.l;
        e20 e20Var = this.n;
        e20Var.c(i);
        e20Var.c(this.m);
        e20Var.c(this.k);
        if (z) {
            this.k = 0;
        }
        this.l = 0;
        this.m = 0;
    }

    public final void v() {
        rw0 rw0Var = new rw0();
        if (this.C) {
            rw0Var.b();
        }
        if (this.b.d()) {
            rw0Var.o = new he0();
        }
        this.H = rw0Var;
        uw0 d = rw0Var.d();
        d.e(true);
        this.I = d;
    }

    public final vh w() {
        cw cwVar = this.U;
        if (cwVar == null) {
            cw cwVar2 = new cw(this.h);
            this.U = cwVar2;
            return cwVar2;
        }
        return cwVar;
    }

    public final mo0 x() {
        if (this.A == 0) {
            ArrayList arrayList = this.E;
            if (!arrayList.isEmpty()) {
                return (mo0) arrayList.get(arrayList.size() - 1);
            }
            return null;
        }
        return null;
    }

    public final boolean y() {
        if (A() && !this.w) {
            mo0 x = x();
            if (x == null || (x.b & 4) == 0) {
                return false;
            }
            return true;
        }
        return true;
    }

    public final wh z() {
        if (this.b.k()) {
            return this.Q;
        }
        return null;
    }
}
