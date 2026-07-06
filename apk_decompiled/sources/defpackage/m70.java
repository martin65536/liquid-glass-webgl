package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class m70 implements au0 {
    public static final c4 x;
    public final xl a;
    public boolean b;
    public h70 c;
    public boolean d;
    public final ud e;
    public final ik0 f;
    public final je0 g;
    public float h;
    public final dm i;
    public final boolean j;
    public z40 k;
    public final k70 l;
    public final x8 m;
    public final c60 n;
    public final ib o;
    public final q60 p;
    public final j2 q;
    public final n60 r;
    public final af0 s;
    public final ik0 t;
    public final ik0 u;
    public final af0 v;
    public final c4 w;

    static {
        w4 w4Var = new w4(8, (byte) 0);
        pb pbVar = new pb(10);
        wa waVar = new wa(4, w4Var);
        f31.n(1, pbVar);
        x = new c4(21, waVar, pbVar);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [xl, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, ud] */
    /* JADX WARN: Type inference failed for: r1v7, types: [x8, java.lang.Object] */
    public m70(int i, int i2) {
        ?? obj = new Object();
        obj.a = -1;
        obj.d = -1;
        this.a = obj;
        ?? obj2 = new Object();
        obj2.b = new fk0(i);
        obj2.c = new fk0(i2);
        obj2.e = new k60(i);
        this.e = obj2;
        h70 h70Var = o70.a;
        x1 x1Var = x1.S;
        this.f = new ik0(h70Var, x1Var);
        this.g = new je0();
        this.i = new dm(new l(7, this));
        this.j = true;
        this.l = new k70(this);
        this.m = new Object();
        this.n = new c60();
        this.o = new ib(1);
        this.p = new q60(new cz(this, i));
        this.q = new j2(11, this);
        this.r = new n60();
        x31 x31Var = x31.a;
        this.s = new ik0(x31Var, x1Var);
        Boolean bool = Boolean.FALSE;
        this.t = n30.B(bool);
        this.u = n30.B(bool);
        this.v = new ik0(x31Var, x1Var);
        c4 c4Var = new c4(10, false);
        c4 c4Var2 = dl.t;
        Float valueOf = Float.valueOf(0.0f);
        c4Var.g = new d7(c4Var2, valueOf, (i7) ((gv) c4Var2.f).e(valueOf), Long.MIN_VALUE, Long.MIN_VALUE, false);
        this.w = c4Var;
    }

    @Override // defpackage.au0
    public final boolean a() {
        return ((Boolean) this.u.getValue()).booleanValue();
    }

    @Override // defpackage.au0
    public final boolean b() {
        return this.i.b();
    }

    @Override // defpackage.au0
    public final boolean c() {
        return ((Boolean) this.t.getValue()).booleanValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0077, code lost:
    
        if (r10 == r6) goto L34;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0088 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0089 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /* JADX WARN: Type inference failed for: r1v4, types: [l30, nf] */
    @Override // defpackage.au0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object d(defpackage.gf0 r8, defpackage.kv r9, defpackage.jj r10) {
        /*
            r7 = this;
            boolean r0 = r10 instanceof defpackage.l70
            if (r0 == 0) goto L13
            r0 = r10
            l70 r0 = (defpackage.l70) r0
            int r1 = r0.l
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.l = r1
            goto L18
        L13:
            l70 r0 = new l70
            r0.<init>(r7, r10)
        L18:
            java.lang.Object r10 = r0.j
            int r1 = r0.l
            x31 r2 = defpackage.x31.a
            r3 = 2
            r4 = 0
            r5 = 1
            ik r6 = defpackage.ik.e
            if (r1 == 0) goto L3e
            if (r1 == r5) goto L33
            if (r1 != r3) goto L2d
            defpackage.o30.x(r10)
            return r2
        L2d:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r7)
            return r4
        L33:
            sz0 r8 = r0.i
            r9 = r8
            kv r9 = (defpackage.kv) r9
            gf0 r8 = r0.h
            defpackage.o30.x(r10)
            goto L7a
        L3e:
            defpackage.o30.x(r10)
            ik0 r10 = r7.f
            java.lang.Object r10 = r10.getValue()
            h70 r1 = defpackage.o70.a
            if (r10 != r1) goto L7a
            r0.h = r8
            r10 = r9
            sz0 r10 = (defpackage.sz0) r10
            r0.i = r10
            r0.l = r5
            x8 r10 = r7.m
            nf r1 = r10.b
            if (r1 != 0) goto L6f
            nf r1 = new nf
            r1.<init>(r5)
            r1.T(r4)
            r10.b = r1
            w8 r10 = r10.a
            if (r10 == 0) goto L6f
            boolean r5 = r10.r
            if (r5 == 0) goto L6f
            r10.D0()
        L6f:
            java.lang.Object r10 = r1.C(r0)
            if (r10 != r6) goto L76
            goto L77
        L76:
            r10 = r2
        L77:
            if (r10 != r6) goto L7a
            goto L88
        L7a:
            r0.h = r4
            r0.i = r4
            r0.l = r3
            dm r7 = r7.i
            java.lang.Object r7 = r7.d(r8, r9, r0)
            if (r7 != r6) goto L89
        L88:
            return r6
        L89:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.m70.d(gf0, kv, jj):java.lang.Object");
    }

    @Override // defpackage.au0
    public final float e(float f) {
        return this.i.e(f);
    }

    public final void f(h70 h70Var, boolean z, boolean z2) {
        int i;
        boolean z3;
        String str;
        long j;
        long j2;
        Object obj;
        int i2;
        ww0 t;
        gv gvVar;
        ww0 C;
        gv gvVar2;
        c4 c4Var = dl.t;
        List list = h70Var.k;
        int i3 = h70Var.n;
        int i4 = h70Var.b;
        i70 i70Var = h70Var.a;
        this.p.e = list.size();
        c4 c4Var2 = this.w;
        ud udVar = this.e;
        ij ijVar = null;
        if (!z && this.b) {
            this.c = h70Var;
            t = t20.t();
            if (t != null) {
                gvVar2 = t.e();
            } else {
                gvVar2 = null;
            }
            C = t20.C(t);
            try {
                if (((Number) ((d7) c4Var2.g).f.getValue()).floatValue() != 0.0f && i70Var != null && i70Var.a == ((fk0) udVar.b).g() && i4 == ((fk0) udVar.c).g()) {
                    dy0 dy0Var = (dy0) c4Var2.f;
                    if (dy0Var != null) {
                        dy0Var.a(null);
                    }
                    c4Var2.g = new d7(c4Var, Float.valueOf(0.0f), null, 60);
                }
                return;
            } finally {
                t20.K(t, C, gvVar);
            }
        }
        boolean z4 = true;
        if (z) {
            this.b = true;
        }
        if (i70Var != null) {
            i = i70Var.a;
        } else {
            i = 0;
        }
        if (i == 0 && i4 == 0) {
            z3 = false;
        } else {
            z3 = true;
        }
        this.u.setValue(Boolean.valueOf(z3));
        this.t.setValue(Boolean.valueOf(h70Var.c));
        this.h -= h70Var.d;
        this.f.setValue(h70Var);
        if (z2) {
            udVar.getClass();
            if (i4 < 0.0f) {
                t00.c("scrollOffset should be non-negative");
            }
            ((fk0) udVar.c).h(i4);
        } else {
            i70 i70Var2 = (i70) me.T(list);
            i70 i70Var3 = (i70) me.Y(list);
            if (i70Var2 != null) {
                str = "scrollOffset should be non-negative";
                j = i70Var2.a;
            } else {
                str = "scrollOffset should be non-negative";
                j = -1;
            }
            f31.V("firstVisibleItem:index", j);
            if (i70Var3 != null) {
                j2 = i70Var3.a;
            } else {
                j2 = -1;
            }
            f31.V("lastVisibleItem:index", j2);
            udVar.getClass();
            if (i70Var != null) {
                obj = i70Var.g;
            } else {
                obj = null;
            }
            udVar.d = obj;
            if (udVar.a || i3 > 0) {
                udVar.a = true;
                if (i4 < 0.0f) {
                    t00.c(str);
                }
                if (i70Var != null) {
                    i2 = i70Var.a;
                } else {
                    i2 = 0;
                }
                udVar.b(i2, i4);
            }
            if (this.j) {
                xl xlVar = this.a;
                int i5 = xlVar.a;
                boolean z5 = xlVar.c;
                if (i5 != -1 && !list.isEmpty() && i5 != xl.a(h70Var, z5)) {
                    xlVar.a = -1;
                    p60 p60Var = xlVar.b;
                    if (p60Var != null) {
                        p60Var.cancel();
                    }
                    xlVar.b = null;
                }
                int i6 = xlVar.d;
                if (i6 != -1 && xlVar.e != 0.0f && i6 != i3 && !list.isEmpty()) {
                    if (xlVar.e >= 0.0f) {
                        z4 = false;
                    }
                    int a = xl.a(h70Var, z4);
                    if (a >= 0 && a < i3) {
                        xlVar.a = a;
                        xlVar.b = d3.B(this.q, a);
                    }
                }
                xlVar.d = i3;
            }
        }
        if (z) {
            float f = h70Var.f;
            mm mmVar = h70Var.i;
            hk hkVar = h70Var.h;
            c4Var2.getClass();
            if (f > mmVar.G(1.0f)) {
                t = t20.t();
                if (t != null) {
                    gvVar = t.e();
                } else {
                    gvVar = null;
                }
                C = t20.C(t);
                try {
                    float floatValue = ((Number) ((d7) c4Var2.g).f.getValue()).floatValue();
                    dy0 dy0Var2 = (dy0) c4Var2.f;
                    if (dy0Var2 != null) {
                        dy0Var2.a(null);
                    }
                    d7 d7Var = (d7) c4Var2.g;
                    if (d7Var.j) {
                        c4Var2.g = o4.v(d7Var, floatValue - f);
                    } else {
                        c4Var2.g = new d7(c4Var, Float.valueOf(-f), null, 60);
                    }
                    c4Var2.f = f31.G(hkVar, null, new m8(c4Var2, ijVar, 3), 3);
                } finally {
                }
            }
        }
    }

    public final h70 g() {
        return (h70) this.f.getValue();
    }

    public final void h(float f, h70 h70Var) {
        boolean z;
        p60 p60Var;
        p60 p60Var2;
        if (this.j) {
            boolean isEmpty = h70Var.k.isEmpty();
            xl xlVar = this.a;
            if (!isEmpty) {
                if (f < 0.0f) {
                    z = true;
                } else {
                    z = false;
                }
                int a = xl.a(h70Var, z);
                if (a >= 0 && a < h70Var.n) {
                    if (a != xlVar.a) {
                        if (xlVar.c != z) {
                            xlVar.a = -1;
                            p60 p60Var3 = xlVar.b;
                            if (p60Var3 != null) {
                                p60Var3.cancel();
                            }
                            xlVar.b = null;
                        }
                        xlVar.c = z;
                        xlVar.a = a;
                        xlVar.b = d3.B(this.q, a);
                    }
                    List list = h70Var.k;
                    if (z) {
                        i70 i70Var = (i70) me.X(list);
                        if (((i70Var.j + i70Var.k) + h70Var.q) - h70Var.m < (-f) && (p60Var2 = xlVar.b) != null) {
                            p60Var2.a();
                        }
                    } else if (h70Var.l - ((i70) me.S(list)).j < f && (p60Var = xlVar.b) != null) {
                        p60Var.a();
                    }
                }
            }
            xlVar.e = f;
        }
    }
}
