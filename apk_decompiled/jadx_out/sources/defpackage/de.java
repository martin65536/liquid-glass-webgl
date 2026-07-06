package defpackage;

import android.view.ViewGroup;
import android.view.ViewParent;
import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class de extends jm implements xm0, x30, qu0, w21, ai, ah0, k00, jw {
    public static final dt0 P = new dt0(5);
    public final tt A;
    public a00 B;
    public kw C;
    public im D;
    public on0 E;
    public wy F;
    public final le0 G;
    public long H;
    public on0 I;
    public je0 J;
    public boolean K;
    public dy0 L;
    public final dt0 M;
    public um0 N;
    public c00 O;
    public je0 u;
    public a00 v;
    public boolean w;
    public cr0 x;
    public boolean y = true;
    public vu z;

    public de(je0 je0Var, a00 a00Var, boolean z, cr0 cr0Var, vu vuVar) {
        this.u = je0Var;
        this.v = a00Var;
        this.w = z;
        this.x = cr0Var;
        this.z = vuVar;
        this.A = new tt(je0Var, new e(1, this, de.class, "onFocusChange", "onFocusChange(Z)V", 0, 0, 0));
        int i = eb0.a;
        this.G = new le0(6);
        this.H = 0L;
        je0 je0Var2 = this.u;
        this.J = je0Var2;
        this.K = je0Var2 == null;
        this.M = P;
    }

    @Override // defpackage.xm0
    public final long A() {
        return o4.i;
    }

    public final void G0(boolean z) {
        on0 on0Var;
        un unVar;
        ij ijVar = null;
        if (z) {
            this.O = null;
        } else {
            this.N = null;
        }
        je0 je0Var = this.u;
        if (je0Var != null) {
            dy0 dy0Var = this.L;
            if (dy0Var != null && dy0Var.b()) {
                dy0 dy0Var2 = this.L;
                if (dy0Var2 != null) {
                    dy0Var2.a(null);
                }
            } else {
                if (z) {
                    on0Var = this.I;
                } else {
                    on0Var = this.E;
                }
                if (on0Var != null) {
                    nn0 nn0Var = new nn0(on0Var);
                    d30 d30Var = (d30) ((hj) p0()).e.j(x1.L);
                    if (d30Var != null) {
                        unVar = d30Var.p(new c(0, je0Var, nn0Var));
                    } else {
                        unVar = null;
                    }
                    f31.G(p0(), null, new f(je0Var, nn0Var, unVar, ijVar, 0), 3);
                }
            }
            if (z) {
                this.I = null;
            } else {
                this.E = null;
            }
        }
    }

    public final void H0() {
        je0 je0Var = this.u;
        le0 le0Var = this.G;
        if (je0Var != null) {
            on0 on0Var = this.E;
            if (on0Var != null) {
                je0Var.b(new nn0(on0Var));
            }
            on0 on0Var2 = this.I;
            if (on0Var2 != null) {
                je0Var.b(new nn0(on0Var2));
            }
            wy wyVar = this.F;
            if (wyVar != null) {
                je0Var.b(new xy(wyVar));
            }
            Object[] objArr = le0Var.c;
            long[] jArr = le0Var.a;
            int length = jArr.length - 2;
            if (length >= 0) {
                int i = 0;
                while (true) {
                    long j = jArr[i];
                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i2 = 8 - ((~(i - length)) >>> 31);
                        for (int i3 = 0; i3 < i2; i3++) {
                            if ((255 & j) < 128) {
                                je0Var.b(new nn0((on0) objArr[(i << 3) + i3]));
                            }
                            j >>= 8;
                        }
                        if (i2 != 8) {
                            break;
                        }
                    }
                    if (i == length) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
        this.E = null;
        this.I = null;
        this.F = null;
        le0Var.a();
    }

    @Override // defpackage.k00
    public final void I() {
        G0(true);
    }

    public final void I0(long j, boolean z) {
        on0 on0Var;
        je0 je0Var = this.u;
        if (je0Var != null) {
            dy0 dy0Var = this.L;
            if (dy0Var != null && dy0Var.b()) {
                dy0Var.a(null);
                f31.G(p0(), null, new g(dy0Var, j, je0Var, null, 0), 3);
            } else {
                if (z) {
                    on0Var = this.I;
                } else {
                    on0Var = this.E;
                }
                if (on0Var != null) {
                    f31.G(p0(), null, new h(on0Var, je0Var, null), 3);
                }
            }
            if (z) {
                this.I = null;
            } else {
                this.E = null;
            }
        }
    }

    public final void J0() {
        a00 a00Var;
        if (this.D == null) {
            if (this.w) {
                a00Var = this.B;
            } else {
                a00Var = this.v;
            }
            if (a00Var != null) {
                if (this.u == null) {
                    this.u = new je0();
                }
                this.A.H0(this.u);
                je0 je0Var = this.u;
                je0Var.getClass();
                im a = a00Var.a(je0Var);
                D0(a);
                this.D = a;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r12v10, types: [ap0, java.lang.Object] */
    @Override // defpackage.k00
    public final void K(p5 p5Var, qm0 qm0Var) {
        boolean z;
        je0 je0Var;
        ArrayList arrayList = (ArrayList) p5Var.b;
        J0();
        if (this.y && this.C == null) {
            kw kwVar = new kw(this);
            D0(kwVar);
            this.C = kwVar;
        }
        if (qm0Var == qm0.f) {
            ij ijVar = null;
            if (this.O == null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    if (n20.i((c00) arrayList.get(i))) {
                        c00 c00Var = (c00) arrayList.get(0);
                        c00Var.i = true;
                        this.O = c00Var;
                        if (this.y && (je0Var = this.u) != null) {
                            on0 on0Var = new on0(c00Var.c);
                            ?? obj = new Object();
                            d20.K(this, kw.t, new lw(new c(4, c00Var, obj), 0));
                            if (!obj.e) {
                                int i2 = ee.b;
                                ViewParent parent = o4.Y(this).getParent();
                                while (parent != null && (parent instanceof ViewGroup)) {
                                    ViewGroup viewGroup = (ViewGroup) parent;
                                    if (!viewGroup.shouldDelayChildPressedState()) {
                                        parent = viewGroup.getParent();
                                    }
                                }
                                this.I = on0Var;
                                f31.G(p0(), null, new h(je0Var, on0Var, null, 1), 3);
                                return;
                            }
                            this.L = f31.G(p0(), null, new i(je0Var, on0Var, this, ijVar, 0), 3);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            int size2 = arrayList.size();
            for (int i3 = 0; i3 < size2; i3++) {
                c00 c00Var2 = (c00) arrayList.get(i3);
                if (c00Var2.i || !c00Var2.h || c00Var2.d) {
                    float d = ((l51) n20.p(this, fi.t)).d();
                    int size3 = arrayList.size();
                    for (int i4 = 0; i4 < size3; i4++) {
                        c00 c00Var3 = (c00) arrayList.get(i4);
                        long j = c00Var3.c;
                        c00 c00Var4 = this.O;
                        c00Var4.getClass();
                        if (Math.abs(ch0.d(ch0.f(j, c00Var4.c))) > d) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (c00Var3.i || z) {
                            G0(true);
                            return;
                        }
                    }
                    return;
                }
            }
            ((c00) arrayList.get(0)).i = true;
            if (this.y) {
                c00 c00Var5 = this.O;
                c00Var5.getClass();
                I0(c00Var5.c, true);
                this.z.a();
            }
            this.O = null;
            return;
        }
        if (qm0Var == qm0.g && this.O != null) {
            int size4 = arrayList.size();
            for (int i5 = 0; i5 < size4; i5++) {
                c00 c00Var6 = (c00) arrayList.get(i5);
                if (c00Var6.i && c00Var6 != this.O) {
                    G0(true);
                    return;
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r5v13, types: [ap0, java.lang.Object] */
    @Override // defpackage.xm0
    public final void N(pm0 pm0Var, qm0 qm0Var, long j) {
        je0 je0Var;
        long j2 = ((j >> 33) << 32) | (((j << 32) >> 33) & 4294967295L);
        this.H = (Float.floatToRawIntBits((int) (j2 & 4294967295L)) & 4294967295L) | (Float.floatToRawIntBits((int) (j2 >> 32)) << 32);
        J0();
        boolean z = this.y;
        int i = 5;
        qm0 qm0Var2 = qm0.f;
        ij ijVar = null;
        int i2 = 1;
        int i3 = 0;
        if (z) {
            if (this.C == null) {
                kw kwVar = new kw(this);
                D0(kwVar);
                this.C = kwVar;
            }
            if (qm0Var == qm0Var2) {
                int i4 = pm0Var.d;
                if (i4 == 4) {
                    f31.G(p0(), null, new k(this, ijVar, i3), 3);
                } else if (i4 == 5) {
                    f31.G(p0(), null, new k(this, ijVar, i2), 3);
                }
            }
        }
        if (qm0Var == qm0Var2) {
            if (this.N == null) {
                if (o01.d(pm0Var, true)) {
                    um0 um0Var = (um0) pm0Var.a.get(0);
                    um0Var.a();
                    this.N = um0Var;
                    if (this.y && (je0Var = this.u) != null) {
                        on0 on0Var = new on0(um0Var.c);
                        ?? obj = new Object();
                        d20.K(this, kw.t, new lw(new c(i, um0Var, obj), 0));
                        if (!obj.e) {
                            int i5 = ee.b;
                            ViewParent parent = o4.Y(this).getParent();
                            while (parent != null && (parent instanceof ViewGroup)) {
                                ViewGroup viewGroup = (ViewGroup) parent;
                                if (!viewGroup.shouldDelayChildPressedState()) {
                                    parent = viewGroup.getParent();
                                }
                            }
                            this.E = on0Var;
                            f31.G(p0(), null, new h(je0Var, on0Var, null, 2), 3);
                            return;
                        }
                        this.L = f31.G(p0(), null, new i(je0Var, on0Var, this, ijVar, 1), 3);
                        return;
                    }
                    return;
                }
                return;
            }
            List list = pm0Var.a;
            int size = list.size();
            for (int i6 = 0; i6 < size; i6++) {
                if (!g30.m((um0) list.get(i6))) {
                    float max = Math.max(0.0f, Float.intBitsToFloat((int) (k81.E(this).A.Z(((l51) n20.p(this, fi.t)).e()) >> 32)) - ((int) (j >> 32))) / 2.0f;
                    long floatToRawIntBits = (Float.floatToRawIntBits(Math.max(0.0f, Float.intBitsToFloat((int) (r1 & 4294967295L)) - ((int) (j & 4294967295L))) / 2.0f) & 4294967295L) | (Float.floatToRawIntBits(max) << 32);
                    int size2 = list.size();
                    for (int i7 = 0; i7 < size2; i7++) {
                        um0 um0Var2 = (um0) list.get(i7);
                        if (um0Var2.b() || g30.x(um0Var2, j, floatToRawIntBits)) {
                            G0(false);
                            return;
                        }
                    }
                    return;
                }
            }
            ((um0) list.get(0)).a();
            if (this.y) {
                um0 um0Var3 = this.N;
                um0Var3.getClass();
                I0(um0Var3.c, false);
                this.z.a();
            }
            this.N = null;
            return;
        }
        if (qm0Var == qm0.g && this.N != null) {
            List list2 = pm0Var.a;
            int size3 = list2.size();
            for (int i8 = 0; i8 < size3; i8++) {
                um0 um0Var4 = (um0) list2.get(i8);
                if (um0Var4.b() && um0Var4 != this.N) {
                    G0(false);
                    return;
                }
            }
        }
    }

    @Override // defpackage.ah0
    public final void P() {
        if (this.w) {
            o30.u(this, new b(this, 0));
        }
    }

    @Override // defpackage.jw
    public final /* synthetic */ boolean Q(c00 c00Var) {
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x00cc, code lost:
    
        if (((r7 & ((~r7) << 6)) & r14) == 0) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00ce, code lost:
    
        r16 = -1;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // defpackage.x30
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean U(android.view.KeyEvent r24) {
        /*
            Method dump skipped, instructions count: 293
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.de.U(android.view.KeyEvent):boolean");
    }

    @Override // defpackage.xm0
    public final /* synthetic */ boolean X() {
        return false;
    }

    @Override // defpackage.xm0
    public final void c0() {
        g0();
    }

    @Override // defpackage.qu0
    public final void f0(bv0 bv0Var) {
        cr0 cr0Var = this.x;
        if (cr0Var != null) {
            zu0.a(bv0Var, cr0Var.a);
        }
        b bVar = new b(this, 1);
        t30[] t30VarArr = zu0.a;
        bv0Var.a(mu0.b, new n0(null, bVar));
        if (this.y) {
            this.A.f0(bv0Var);
        } else {
            bv0Var.a(wu0.j, x31.a);
        }
    }

    @Override // defpackage.xm0
    public final void g0() {
        wy wyVar;
        je0 je0Var = this.u;
        if (je0Var != null && (wyVar = this.F) != null) {
            je0Var.b(new xy(wyVar));
        }
        this.F = null;
        G0(false);
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean h0() {
        return false;
    }

    @Override // defpackage.qu0
    public final boolean i0() {
        return true;
    }

    @Override // defpackage.xm0
    public final /* synthetic */ boolean n0() {
        return false;
    }

    @Override // defpackage.bd0
    public final boolean q0() {
        return false;
    }

    @Override // defpackage.jw
    public final /* synthetic */ boolean r(um0 um0Var) {
        return false;
    }

    @Override // defpackage.bd0
    public final void t0() {
        P();
        if (!this.K) {
            J0();
        }
        if (this.y) {
            D0(this.A);
        }
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean u() {
        return true;
    }

    @Override // defpackage.bd0
    public final void u0() {
        g0();
    }

    @Override // defpackage.bd0
    public final void v0() {
        H0();
        if (this.J == null) {
            this.u = null;
        }
        im imVar = this.D;
        if (imVar != null) {
            E0(imVar);
        }
        this.D = null;
        kw kwVar = this.C;
        if (kwVar != null) {
            E0(kwVar);
        }
        this.C = null;
    }

    @Override // defpackage.w21
    public final Object z() {
        return this.M;
    }
}
