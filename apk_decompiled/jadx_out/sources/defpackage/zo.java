package defpackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class zo extends jm implements xm0, k00, ai, jw {
    public bp A;
    public boolean B;
    public boolean C;
    public jo D;
    public mo E;
    public lo F;
    public ko G;
    public n20 H;
    public u41 I;
    public j21 K;
    public j00 L;
    public dj0 u;
    public gv v;
    public boolean w;
    public je0 x;
    public kw y;
    public zb z;
    public long J = 9205357640488583168L;
    public long M = 0;

    public zo(gv gvVar, boolean z, je0 je0Var, dj0 dj0Var) {
        this.u = dj0Var;
        this.v = gvVar;
        this.w = z;
        this.x = je0Var;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object G0(defpackage.zo r5, defpackage.jj r6) {
        /*
            boolean r0 = r6 instanceof defpackage.vo
            if (r0 == 0) goto L13
            r0 = r6
            vo r0 = (defpackage.vo) r0
            int r1 = r0.j
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.j = r1
            goto L18
        L13:
            vo r0 = new vo
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.h
            int r1 = r0.j
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L2c
            if (r1 != r3) goto L26
            defpackage.o30.x(r6)
            goto L47
        L26:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r5)
            return r2
        L2c:
            defpackage.o30.x(r6)
            bp r6 = r5.A
            if (r6 == 0) goto L49
            je0 r1 = r5.x
            if (r1 == 0) goto L47
            ap r4 = new ap
            r4.<init>(r6)
            r0.j = r3
            java.lang.Object r6 = r1.a(r4, r0)
            ik r0 = defpackage.ik.e
            if (r6 != r0) goto L47
            return r0
        L47:
            r5.A = r2
        L49:
            qo r6 = new qo
            r0 = 0
            r2 = 0
            r6.<init>(r0, r2)
            r5.Q0(r6)
            x31 r5 = defpackage.x31.a
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zo.G0(zo, jj):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0053, code lost:
    
        if (r1.a(r5, r0) == r4) goto L27;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* JADX WARN: Type inference failed for: r8v4, types: [g20, java.lang.Object, bp] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object H0(defpackage.zo r6, defpackage.po r7, defpackage.jj r8) {
        /*
            boolean r0 = r8 instanceof defpackage.wo
            if (r0 == 0) goto L13
            r0 = r8
            wo r0 = (defpackage.wo) r0
            int r1 = r0.l
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.l = r1
            goto L18
        L13:
            wo r0 = new wo
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.j
            int r1 = r0.l
            r2 = 2
            r3 = 1
            ik r4 = defpackage.ik.e
            if (r1 == 0) goto L3b
            if (r1 == r3) goto L35
            if (r1 != r2) goto L2e
            bp r7 = r0.i
            po r0 = r0.h
            defpackage.o30.x(r8)
            goto L6e
        L2e:
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r6)
            r6 = 0
            return r6
        L35:
            po r7 = r0.h
            defpackage.o30.x(r8)
            goto L56
        L3b:
            defpackage.o30.x(r8)
            bp r8 = r6.A
            if (r8 == 0) goto L56
            je0 r1 = r6.x
            if (r1 == 0) goto L56
            ap r5 = new ap
            r5.<init>(r8)
            r0.h = r7
            r0.l = r3
            java.lang.Object r8 = r1.a(r5, r0)
            if (r8 != r4) goto L56
            goto L6b
        L56:
            bp r8 = new bp
            r8.<init>()
            je0 r1 = r6.x
            if (r1 == 0) goto L70
            r0.h = r7
            r0.i = r8
            r0.l = r2
            java.lang.Object r0 = r1.a(r8, r0)
            if (r0 != r4) goto L6c
        L6b:
            return r4
        L6c:
            r0 = r7
            r7 = r8
        L6e:
            r8 = r7
            r7 = r0
        L70:
            r6.A = r8
            long r7 = r7.a
            r6.P0(r7)
            x31 r6 = defpackage.x31.a
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zo.H0(zo, po, jj):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object I0(defpackage.zo r5, defpackage.qo r6, defpackage.jj r7) {
        /*
            boolean r0 = r7 instanceof defpackage.xo
            if (r0 == 0) goto L13
            r0 = r7
            xo r0 = (defpackage.xo) r0
            int r1 = r0.k
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.k = r1
            goto L18
        L13:
            xo r0 = new xo
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.i
            int r1 = r0.k
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L2e
            if (r1 != r3) goto L28
            qo r6 = r0.h
            defpackage.o30.x(r7)
            goto L4b
        L28:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r5)
            return r2
        L2e:
            defpackage.o30.x(r7)
            bp r7 = r5.A
            if (r7 == 0) goto L4d
            je0 r1 = r5.x
            if (r1 == 0) goto L4b
            cp r4 = new cp
            r4.<init>(r7)
            r0.h = r6
            r0.k = r3
            java.lang.Object r7 = r1.a(r4, r0)
            ik r0 = defpackage.ik.e
            if (r7 != r0) goto L4b
            return r0
        L4b:
            r5.A = r2
        L4d:
            r5.Q0(r6)
            x31 r5 = defpackage.x31.a
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zo.I0(zo, qo, jj):java.lang.Object");
    }

    /* JADX WARN: Type inference failed for: r9v4, types: [lo, java.lang.Object] */
    public static void N0(zo zoVar, um0 um0Var, long j, long j2, int i) {
        if ((i & 4) != 0) {
            j2 = 0;
        }
        lo loVar = zoVar.F;
        lo loVar2 = loVar;
        if (loVar == null) {
            ?? obj = new Object();
            obj.t = null;
            obj.u = Long.MAX_VALUE;
            obj.v = false;
            zoVar.F = obj;
            loVar2 = obj;
        }
        loVar2.t = um0Var;
        loVar2.u = j;
        j21 j21Var = zoVar.K;
        dj0 dj0Var = zoVar.u;
        if (j21Var == null) {
            zoVar.K = new j21(dj0Var, 0);
        } else {
            j21Var.a = dj0Var;
            j21Var.b = j2;
        }
        loVar2.v = false;
        zoVar.H = loVar2;
    }

    @Override // defpackage.xm0
    public final long A() {
        return o4.i;
    }

    @Override // defpackage.k00
    public final void I() {
        j00 j00Var = this.L;
        if (j00Var != null) {
            j00Var.a();
            zo zoVar = j00Var.a;
            if (zoVar.B) {
                zoVar.O0(no.a);
            }
            j00Var.g = null;
            pu puVar = j00Var.k;
            puVar.a = 0;
            ((ke0) puVar.b).b = 0;
        }
    }

    public final void J0() {
        bp bpVar = this.A;
        if (bpVar != null) {
            je0 je0Var = this.x;
            if (je0Var != null) {
                je0Var.b(new ap(bpVar));
            }
            this.A = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v13 */
    /* JADX WARN: Type inference failed for: r11v14 */
    /* JADX WARN: Type inference failed for: r11v15, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r24v0, types: [jm, zo, jw] */
    /* JADX WARN: Type inference failed for: r2v16, types: [h00, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v10, types: [h00, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v13, types: [e00, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v27, types: [java.lang.Object] */
    @Override // defpackage.k00
    public final void K(p5 p5Var, qm0 qm0Var) {
        Object obj;
        Object obj2;
        Object obj3;
        qm0 qm0Var2;
        c00 c00Var;
        c00 c00Var2;
        d00 d00Var;
        int i = p5Var.a;
        ArrayList arrayList = (ArrayList) p5Var.b;
        if (this.y == null) {
            kw kwVar = new kw(this);
            D0(kwVar);
            this.y = kwVar;
        }
        if (this.w) {
            if (this.L == null) {
                this.L = new j00(this);
            }
            j00 j00Var = this.L;
            if (j00Var != null) {
                zo zoVar = j00Var.a;
                if (j00Var.f == null) {
                    e00 e00Var = j00Var.b;
                    e00 e00Var2 = e00Var;
                    if (e00Var == null) {
                        ?? obj4 = new Object();
                        obj4.v = d00.g;
                        obj4.w = false;
                        j00Var.b = obj4;
                        e00Var2 = obj4;
                    }
                    j00Var.f = e00Var2;
                }
                dl dlVar = j00Var.f;
                if (dlVar != null) {
                    boolean z = dlVar instanceof e00;
                    qm0 qm0Var3 = qm0.e;
                    boolean z2 = true;
                    qm0 qm0Var4 = qm0.f;
                    if (z) {
                        e00 e00Var3 = (e00) dlVar;
                        if (!arrayList.isEmpty()) {
                            int size = arrayList.size();
                            for (int i2 = 0; i2 < size; i2++) {
                                if (!n20.i((c00) arrayList.get(i2))) {
                                    return;
                                }
                            }
                            c00 c00Var3 = (c00) me.S(arrayList);
                            int i3 = i00.a[e00Var3.v.ordinal()];
                            d00 d00Var2 = d00.f;
                            d00 d00Var3 = d00.e;
                            if (i3 == 1) {
                                if (!zoVar.V0()) {
                                    d00Var = d00Var3;
                                } else {
                                    d00Var = d00Var2;
                                }
                            } else {
                                d00Var = e00Var3.v;
                            }
                            e00Var3.v = d00Var;
                            if (qm0Var == qm0Var3 && d00Var == d00Var2) {
                                c00Var3.i = true;
                                e00Var3.w = true;
                            }
                            if (qm0Var == qm0Var4) {
                                if (d00Var == d00Var3) {
                                    j00.c(j00Var, c00Var3, c00Var3.a, 0L, 12);
                                    return;
                                }
                                if (e00Var3.w) {
                                    j00Var.f(c00Var3, c00Var3, new b00(i), 0L);
                                    j00Var.e(c00Var3, new b00(i), 0L);
                                    long j = c00Var3.a;
                                    h00 h00Var = j00Var.c;
                                    h00 h00Var2 = h00Var;
                                    if (h00Var == null) {
                                        ?? obj5 = new Object();
                                        obj5.v = Long.MAX_VALUE;
                                        j00Var.c = obj5;
                                        h00Var2 = obj5;
                                    }
                                    h00Var2.v = j;
                                    j00Var.f = h00Var2;
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    boolean z3 = dlVar instanceof g00;
                    qm0 qm0Var5 = qm0.g;
                    if (z3) {
                        g00 g00Var = (g00) dlVar;
                        if (qm0Var != qm0Var3) {
                            int size2 = arrayList.size();
                            int i4 = 0;
                            while (true) {
                                if (i4 < size2) {
                                    obj3 = arrayList.get(i4);
                                    if (n30.s(((c00) obj3).a, g00Var.w)) {
                                        break;
                                    } else {
                                        i4++;
                                    }
                                } else {
                                    obj3 = null;
                                    break;
                                }
                            }
                            c00 c00Var4 = (c00) obj3;
                            if (c00Var4 == null) {
                                int size3 = arrayList.size();
                                int i5 = 0;
                                while (true) {
                                    if (i5 < size3) {
                                        c00Var2 = arrayList.get(i5);
                                        if (((c00) c00Var2).d) {
                                            break;
                                        } else {
                                            i5++;
                                        }
                                    } else {
                                        c00Var2 = 0;
                                        break;
                                    }
                                }
                                c00Var4 = c00Var2;
                                if (c00Var4 == null) {
                                    j00Var.a();
                                    return;
                                }
                                g00Var.w = c00Var4.a;
                            }
                            c00 c00Var5 = c00Var4;
                            if (qm0Var == qm0Var4) {
                                if (!c00Var5.i) {
                                    if (n20.e(c00Var5)) {
                                        int size4 = arrayList.size();
                                        int i6 = 0;
                                        while (true) {
                                            if (i6 < size4) {
                                                ?? r6 = arrayList.get(i6);
                                                if (((c00) r6).d) {
                                                    c00Var = r6;
                                                    break;
                                                }
                                                i6++;
                                            } else {
                                                c00Var = null;
                                                break;
                                            }
                                        }
                                        c00 c00Var6 = c00Var;
                                        if (c00Var6 == null) {
                                            j00Var.a();
                                        } else {
                                            g00Var.w = c00Var6.a;
                                        }
                                    } else {
                                        l51 l51Var = (l51) n20.p(zoVar, fi.t);
                                        float f = so.a;
                                        float d = l51Var.d();
                                        j21 j21Var = j00Var.i;
                                        if (j21Var != null) {
                                            long a = j21Var.a(n20.H(c00Var5, zoVar.u, new b00(i), true), d, true);
                                            if ((9223372034707292159L & a) != 9205357640488583168L) {
                                                c00Var5.i = true;
                                                c00 c00Var7 = g00Var.v;
                                                c00Var7.getClass();
                                                qm0Var2 = qm0Var5;
                                                j00Var.f(c00Var7, c00Var5, new b00(i), a);
                                                j00Var.e(c00Var5, new b00(i), a);
                                                long j2 = c00Var5.a;
                                                h00 h00Var3 = j00Var.c;
                                                h00 h00Var4 = h00Var3;
                                                if (h00Var3 == null) {
                                                    ?? obj6 = new Object();
                                                    obj6.v = Long.MAX_VALUE;
                                                    j00Var.c = obj6;
                                                    h00Var4 = obj6;
                                                }
                                                h00Var4.v = j2;
                                                j00Var.f = h00Var4;
                                            } else {
                                                qm0Var2 = qm0Var5;
                                                g00Var.x = true;
                                            }
                                        } else {
                                            v7.m("Touch slop detector not initialized.");
                                            return;
                                        }
                                    }
                                } else {
                                    qm0Var2 = qm0Var5;
                                    c00 c00Var8 = g00Var.v;
                                    if (c00Var8 != null) {
                                        long j3 = g00Var.w;
                                        j21 j21Var2 = j00Var.i;
                                        if (j21Var2 != null) {
                                            j00Var.b(c00Var8, j3, j21Var2);
                                        } else {
                                            v7.m("AwaitTouchSlop.touchSlopDetector was not initialized");
                                            return;
                                        }
                                    } else {
                                        v7.m("AwaitTouchSlop.initialDown was not initialized");
                                        return;
                                    }
                                }
                                if (qm0Var != qm0Var2 && g00Var.x) {
                                    if (c00Var5.i) {
                                        c00 c00Var9 = g00Var.v;
                                        if (c00Var9 != null) {
                                            long j4 = g00Var.w;
                                            j21 j21Var3 = j00Var.i;
                                            if (j21Var3 != null) {
                                                j00Var.b(c00Var9, j4, j21Var3);
                                                return;
                                            } else {
                                                v7.m("AwaitTouchSlop.touchSlopDetector was not initialized");
                                                return;
                                            }
                                        }
                                        v7.m("AwaitTouchSlop.initialDown was not initialized");
                                        return;
                                    }
                                    g00Var.x = false;
                                    return;
                                }
                                return;
                            }
                            qm0Var2 = qm0Var5;
                            if (qm0Var != qm0Var2) {
                                return;
                            } else {
                                return;
                            }
                        }
                        return;
                    }
                    if (dlVar instanceof f00) {
                        f00 f00Var = (f00) dlVar;
                        if (qm0Var == qm0Var5) {
                            int size5 = arrayList.size();
                            int i7 = 0;
                            while (true) {
                                if (i7 >= size5) {
                                    break;
                                }
                                if (((c00) arrayList.get(i7)).i) {
                                    z2 = false;
                                    break;
                                }
                                i7++;
                            }
                            int size6 = arrayList.size();
                            int i8 = 0;
                            while (true) {
                                if (i8 >= size6) {
                                    break;
                                }
                                if (((c00) arrayList.get(i8)).d) {
                                    if (!arrayList.isEmpty()) {
                                        if (z2) {
                                            long I = n20.I((c00) me.S(arrayList), zoVar.u, new b00(i));
                                            c00 c00Var10 = f00Var.v;
                                            c00Var10.getClass();
                                            long f2 = ch0.f(I, n20.I(c00Var10, zoVar.u, new b00(i)));
                                            c00 c00Var11 = f00Var.v;
                                            if (c00Var11 != null) {
                                                j00.c(j00Var, c00Var11, f00Var.w, f2, 8);
                                                return;
                                            } else {
                                                v7.m("AwaitGesturePickup.initialDown was not initialized.");
                                                return;
                                            }
                                        }
                                        return;
                                    }
                                } else {
                                    i8++;
                                }
                            }
                            j00Var.a();
                            return;
                        }
                        return;
                    }
                    if (dlVar instanceof h00) {
                        h00 h00Var5 = (h00) dlVar;
                        if (qm0Var == qm0Var4) {
                            long j5 = h00Var5.v;
                            int size7 = arrayList.size();
                            int i9 = 0;
                            while (true) {
                                if (i9 < size7) {
                                    obj = arrayList.get(i9);
                                    if (n30.s(((c00) obj).a, j5)) {
                                        break;
                                    } else {
                                        i9++;
                                    }
                                } else {
                                    obj = null;
                                    break;
                                }
                            }
                            c00 c00Var12 = (c00) obj;
                            if (c00Var12 != null) {
                                boolean e = n20.e(c00Var12);
                                no noVar = no.a;
                                if (e) {
                                    int size8 = arrayList.size();
                                    int i10 = 0;
                                    while (true) {
                                        if (i10 < size8) {
                                            obj2 = arrayList.get(i10);
                                            if (((c00) obj2).d) {
                                                break;
                                            } else {
                                                i10++;
                                            }
                                        } else {
                                            obj2 = null;
                                            break;
                                        }
                                    }
                                    c00 c00Var13 = (c00) obj2;
                                    if (c00Var13 == null) {
                                        if (!c00Var12.i && n20.e(c00Var12)) {
                                            n20.d(j00Var.d(), c00Var12, zoVar.u, new b00(i), j00Var.j, j00Var.l);
                                            float a2 = ((l51) n20.p(zoVar, fi.t)).a();
                                            long b = j00Var.d().b(o30.c(a2, a2));
                                            fm fmVar = (fm) j00Var.d().a;
                                            x41 x41Var = fmVar.a;
                                            bl[] blVarArr = x41Var.d;
                                            Arrays.fill(blVarArr, 0, blVarArr.length, (Object) null);
                                            x41Var.e = 0;
                                            x41 x41Var2 = fmVar.b;
                                            bl[] blVarArr2 = x41Var2.d;
                                            Arrays.fill(blVarArr2, 0, blVarArr2.length, (Object) null);
                                            x41Var2.e = 0;
                                            fmVar.c = 0L;
                                            zoVar.O0(new qo(ip.a(b), true));
                                        } else {
                                            zoVar.O0(noVar);
                                        }
                                        j00Var.a();
                                        return;
                                    }
                                    h00Var5.v = c00Var13.a;
                                    return;
                                }
                                if (c00Var12.i) {
                                    zoVar.O0(noVar);
                                    return;
                                } else {
                                    if (ch0.d(n20.H(c00Var12, zoVar.u, new b00(i), true)) != 0.0f) {
                                        j00Var.e(c00Var12, new b00(i), n20.H(c00Var12, zoVar.u, new b00(i), false));
                                        c00Var12.i = true;
                                        return;
                                    }
                                    return;
                                }
                            }
                            return;
                        }
                        return;
                    }
                    v7.k();
                    return;
                }
                v7.m("currentDragState should not be null");
            }
        }
    }

    public abstract Object K0(yo yoVar, yo yoVar2);

    /* JADX WARN: Type inference failed for: r0v2, types: [jo, java.lang.Object] */
    public final void L0() {
        jo joVar = this.D;
        io ioVar = io.g;
        jo joVar2 = joVar;
        if (joVar == null) {
            ?? obj = new Object();
            obj.t = ioVar;
            obj.u = false;
            this.D = obj;
            joVar2 = obj;
        }
        joVar2.t = ioVar;
        joVar2.u = false;
        this.H = joVar2;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [ko, java.lang.Object] */
    public final void M0(um0 um0Var, long j, j21 j21Var) {
        ko koVar = this.G;
        ko koVar2 = koVar;
        if (koVar == null) {
            ?? obj = new Object();
            obj.t = null;
            obj.u = Long.MAX_VALUE;
            this.G = obj;
            koVar2 = obj;
        }
        koVar2.t = um0Var;
        koVar2.u = j;
        j21Var.b = 0L;
        this.H = koVar2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r18v0, types: [jm, zo, ai, jw] */
    /* JADX WARN: Type inference failed for: r1v38, types: [mo, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v16, types: [mo, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v16, types: [jo, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v24 */
    /* JADX WARN: Type inference failed for: r9v25 */
    /* JADX WARN: Type inference failed for: r9v26, types: [java.lang.Object] */
    @Override // defpackage.xm0
    public void N(pm0 pm0Var, qm0 qm0Var, long j) {
        Object obj;
        Object obj2;
        Object obj3;
        float d;
        kw kwVar;
        jw jwVar;
        boolean z;
        um0 um0Var;
        um0 um0Var2;
        io ioVar;
        boolean z2 = true;
        this.C = true;
        if (this.y == null) {
            kw kwVar2 = new kw(this);
            D0(kwVar2);
            this.y = kwVar2;
        }
        if (this.w) {
            if (this.H == null) {
                jo joVar = this.D;
                jo joVar2 = joVar;
                if (joVar == null) {
                    ?? obj4 = new Object();
                    obj4.t = io.g;
                    obj4.u = false;
                    this.D = obj4;
                    joVar2 = obj4;
                }
                this.H = joVar2;
            }
            n20 n20Var = this.H;
            if (n20Var != null) {
                boolean z3 = n20Var instanceof jo;
                qm0 qm0Var2 = qm0.e;
                qm0 qm0Var3 = qm0.f;
                if (z3) {
                    jo joVar3 = (jo) n20Var;
                    if (!pm0Var.a.isEmpty() && o01.d(pm0Var, false)) {
                        um0 um0Var3 = (um0) me.S(pm0Var.a);
                        int i = uo.a[joVar3.t.ordinal()];
                        io ioVar2 = io.f;
                        io ioVar3 = io.e;
                        if (i == 1) {
                            if (!V0()) {
                                ioVar = ioVar3;
                            } else {
                                ioVar = ioVar2;
                            }
                        } else {
                            ioVar = joVar3.t;
                        }
                        joVar3.t = ioVar;
                        if (qm0Var == qm0Var2 && ioVar == ioVar2) {
                            um0Var3.a();
                            joVar3.u = true;
                        }
                        if (qm0Var == qm0Var3) {
                            if (ioVar == ioVar3) {
                                N0(this, um0Var3, um0Var3.a, 0L, 12);
                                return;
                            }
                            if (joVar3.u) {
                                U0(um0Var3, um0Var3, 0L);
                                T0(um0Var3, 0L);
                                long j2 = um0Var3.a;
                                mo moVar = this.E;
                                mo moVar2 = moVar;
                                if (moVar == null) {
                                    ?? obj5 = new Object();
                                    obj5.t = Long.MAX_VALUE;
                                    this.E = obj5;
                                    moVar2 = obj5;
                                }
                                moVar2.t = j2;
                                this.H = moVar2;
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                boolean z4 = n20Var instanceof lo;
                qm0 qm0Var4 = qm0.g;
                if (z4) {
                    lo loVar = (lo) n20Var;
                    if (qm0Var != qm0Var2) {
                        List list = pm0Var.a;
                        int size = list.size();
                        int i2 = 0;
                        while (true) {
                            if (i2 < size) {
                                obj3 = list.get(i2);
                                int i3 = size;
                                if (n30.s(((um0) obj3).a, loVar.u)) {
                                    break;
                                }
                                i2++;
                                size = i3;
                            } else {
                                obj3 = null;
                                break;
                            }
                        }
                        um0 um0Var4 = (um0) obj3;
                        if (um0Var4 == null) {
                            int size2 = list.size();
                            int i4 = 0;
                            while (true) {
                                if (i4 < size2) {
                                    um0Var2 = list.get(i4);
                                    if (((um0) um0Var2).d) {
                                        break;
                                    } else {
                                        i4++;
                                    }
                                } else {
                                    um0Var2 = 0;
                                    break;
                                }
                            }
                            um0Var4 = um0Var2;
                            if (um0Var4 == null) {
                                L0();
                                return;
                            }
                            loVar.u = um0Var4.a;
                        }
                        if (qm0Var == qm0Var3) {
                            if (!um0Var4.b()) {
                                if (g30.n(um0Var4)) {
                                    int size3 = list.size();
                                    int i5 = 0;
                                    while (true) {
                                        if (i5 < size3) {
                                            ?? r8 = list.get(i5);
                                            if (((um0) r8).d) {
                                                um0Var = r8;
                                                break;
                                            }
                                            i5++;
                                        } else {
                                            um0Var = null;
                                            break;
                                        }
                                    }
                                    um0 um0Var5 = um0Var;
                                    if (um0Var5 == null) {
                                        L0();
                                    } else {
                                        loVar.u = um0Var5.a;
                                    }
                                } else {
                                    l51 l51Var = (l51) n20.p(this, fi.t);
                                    int i6 = um0Var4.i;
                                    float f = so.a;
                                    if (i6 == 2) {
                                        d = l51Var.d() * so.a;
                                    } else {
                                        d = l51Var.d();
                                    }
                                    j21 j21Var = this.K;
                                    if (j21Var != null) {
                                        long a = j21Var.a(g30.E(um0Var4, true), d, true);
                                        if ((9223372034707292159L & a) != 9205357640488583168L) {
                                            boolean r = r(um0Var4);
                                            w21 p = d20.p(this, kw.t);
                                            if (p instanceof kw) {
                                                kwVar = (kw) p;
                                            } else {
                                                kwVar = null;
                                            }
                                            if (kwVar != null) {
                                                jwVar = kwVar.s;
                                            } else {
                                                jwVar = null;
                                            }
                                            if (jwVar != null && jwVar.r(um0Var4)) {
                                                z = true;
                                            } else {
                                                z = false;
                                            }
                                            if (!r && z) {
                                                loVar.v = true;
                                            } else {
                                                um0Var4.a();
                                                um0 um0Var6 = loVar.t;
                                                um0Var6.getClass();
                                                U0(um0Var6, um0Var4, a);
                                                T0(um0Var4, a);
                                                long j3 = um0Var4.a;
                                                mo moVar3 = this.E;
                                                mo moVar4 = moVar3;
                                                if (moVar3 == null) {
                                                    ?? obj6 = new Object();
                                                    obj6.t = Long.MAX_VALUE;
                                                    this.E = obj6;
                                                    moVar4 = obj6;
                                                }
                                                moVar4.t = j3;
                                                this.H = moVar4;
                                            }
                                        } else {
                                            loVar.v = true;
                                        }
                                    } else {
                                        v7.m("Touch slop detector not initialized.");
                                        return;
                                    }
                                }
                            } else {
                                um0 um0Var7 = loVar.t;
                                if (um0Var7 != null) {
                                    long j4 = loVar.u;
                                    j21 j21Var2 = this.K;
                                    if (j21Var2 != null) {
                                        M0(um0Var7, j4, j21Var2);
                                    } else {
                                        v7.m("AwaitTouchSlop.touchSlopDetector was not initialized");
                                        return;
                                    }
                                } else {
                                    v7.m("AwaitTouchSlop.initialDown was not initialized");
                                    return;
                                }
                            }
                        }
                        if (qm0Var == qm0Var4 && loVar.v) {
                            if (um0Var4.b()) {
                                um0 um0Var8 = loVar.t;
                                if (um0Var8 != null) {
                                    long j5 = loVar.u;
                                    j21 j21Var3 = this.K;
                                    if (j21Var3 != null) {
                                        M0(um0Var8, j5, j21Var3);
                                        return;
                                    } else {
                                        v7.m("AwaitTouchSlop.touchSlopDetector was not initialized");
                                        return;
                                    }
                                }
                                v7.m("AwaitTouchSlop.initialDown was not initialized");
                                return;
                            }
                            loVar.v = false;
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (n20Var instanceof ko) {
                    ko koVar = (ko) n20Var;
                    if (qm0Var == qm0Var4) {
                        List list2 = pm0Var.a;
                        int size4 = list2.size();
                        int i7 = 0;
                        while (true) {
                            if (i7 >= size4) {
                                break;
                            }
                            if (((um0) list2.get(i7)).b()) {
                                z2 = false;
                                break;
                            }
                            i7++;
                        }
                        int size5 = list2.size();
                        int i8 = 0;
                        while (true) {
                            if (i8 >= size5) {
                                break;
                            }
                            if (((um0) list2.get(i8)).d) {
                                if (!list2.isEmpty()) {
                                    if (z2) {
                                        long j6 = ((um0) me.S(list2)).c;
                                        um0 um0Var9 = koVar.t;
                                        um0Var9.getClass();
                                        long f2 = ch0.f(j6, um0Var9.c);
                                        um0 um0Var10 = koVar.t;
                                        if (um0Var10 != null) {
                                            N0(this, um0Var10, koVar.u, f2, 8);
                                            return;
                                        } else {
                                            v7.m("AwaitGesturePickup.initialDown was not initialized.");
                                            return;
                                        }
                                    }
                                    return;
                                }
                            } else {
                                i8++;
                            }
                        }
                        L0();
                        return;
                    }
                    return;
                }
                if (n20Var instanceof mo) {
                    mo moVar5 = (mo) n20Var;
                    if (qm0Var == qm0Var3) {
                        long j7 = moVar5.t;
                        List list3 = pm0Var.a;
                        int size6 = list3.size();
                        int i9 = 0;
                        while (true) {
                            if (i9 < size6) {
                                obj = list3.get(i9);
                                if (n30.s(((um0) obj).a, j7)) {
                                    break;
                                } else {
                                    i9++;
                                }
                            } else {
                                obj = null;
                                break;
                            }
                        }
                        um0 um0Var11 = (um0) obj;
                        if (um0Var11 != null) {
                            boolean n = g30.n(um0Var11);
                            no noVar = no.a;
                            if (n) {
                                List list4 = pm0Var.a;
                                int size7 = list4.size();
                                int i10 = 0;
                                while (true) {
                                    if (i10 < size7) {
                                        obj2 = list4.get(i10);
                                        if (((um0) obj2).d) {
                                            break;
                                        } else {
                                            i10++;
                                        }
                                    } else {
                                        obj2 = null;
                                        break;
                                    }
                                }
                                um0 um0Var12 = (um0) obj2;
                                if (um0Var12 == null) {
                                    if (!um0Var11.b() && g30.n(um0Var11)) {
                                        d20.e(S0(), um0Var11, 0L);
                                        float a2 = ((l51) n20.p(this, fi.t)).a();
                                        long b = S0().b(o30.c(a2, a2));
                                        fm fmVar = (fm) S0().a;
                                        x41 x41Var = fmVar.a;
                                        bl[] blVarArr = x41Var.d;
                                        Arrays.fill(blVarArr, 0, blVarArr.length, (Object) null);
                                        x41Var.e = 0;
                                        x41 x41Var2 = fmVar.b;
                                        bl[] blVarArr2 = x41Var2.d;
                                        Arrays.fill(blVarArr2, 0, blVarArr2.length, (Object) null);
                                        x41Var2.e = 0;
                                        fmVar.c = 0L;
                                        R0().q(new qo(ip.a(b), false));
                                        this.C = false;
                                    } else {
                                        R0().q(noVar);
                                    }
                                    L0();
                                    return;
                                }
                                moVar5.t = um0Var12.a;
                                return;
                            }
                            if (um0Var11.b()) {
                                R0().q(noVar);
                                return;
                            } else {
                                if (ch0.d(g30.E(um0Var11, true)) != 0.0f) {
                                    T0(um0Var11, g30.E(um0Var11, false));
                                    um0Var11.a();
                                    return;
                                }
                                return;
                            }
                        }
                        return;
                    }
                    return;
                }
                v7.k();
                return;
            }
            v7.m("currentDragState should not be null");
        }
    }

    public final void O0(ro roVar) {
        if ((roVar instanceof po) && !this.B) {
            this.B = true;
            W0();
        }
        R0().q(roVar);
    }

    public abstract void P0(long j);

    @Override // defpackage.jw
    public final boolean Q(c00 c00Var) {
        if (n20.i(c00Var) && this.w) {
            return true;
        }
        return false;
    }

    public abstract void Q0(qo qoVar);

    public final ed R0() {
        zb zbVar = this.z;
        if (zbVar != null) {
            return zbVar;
        }
        v7.m("Events channel not initialized.");
        return null;
    }

    public final u41 S0() {
        u41 u41Var = this.I;
        if (u41Var != null) {
            return u41Var;
        }
        v7.m("Velocity Tracker not initialized.");
        return null;
    }

    public final void T0(um0 um0Var, long j) {
        long u = k81.D(this.e).u(0L);
        if (!ch0.c(this.J, 9205357640488583168L) && !ch0.c(u, this.J)) {
            this.M = ch0.g(this.M, ch0.f(u, this.J));
        }
        this.J = u;
        d20.e(S0(), um0Var, this.M);
        R0().q(new oo(j, false));
    }

    public final void U0(um0 um0Var, um0 um0Var2, long j) {
        if (this.I == null) {
            this.I = new u41();
        }
        d20.e(S0(), um0Var, 0L);
        long f = ch0.f(um0Var2.c, j);
        this.M = 0L;
        if (((Boolean) this.v.e(new an0(um0Var.i))).booleanValue()) {
            if (!this.B) {
                if (this.z == null) {
                    this.z = f31.c(Integer.MAX_VALUE, 6, null);
                }
                W0();
            }
            this.J = k81.D(this).u(0L);
            R0().q(new po(f));
        }
    }

    public abstract boolean V0();

    public final void W0() {
        this.B = true;
        if (this.z == null) {
            this.z = f31.c(Integer.MAX_VALUE, 6, null);
        }
        f31.G(p0(), null, new yo(this, null), 3);
    }

    @Override // defpackage.xm0
    public final /* synthetic */ boolean X() {
        return false;
    }

    public final void X0(gv gvVar, boolean z, je0 je0Var, dj0 dj0Var, boolean z2) {
        this.v = gvVar;
        boolean z3 = true;
        if (this.w != z) {
            this.w = z;
            if (!z) {
                J0();
                this.L = null;
            }
            z2 = true;
        }
        if (!o20.e(this.x, je0Var)) {
            J0();
            this.x = je0Var;
        }
        if (this.u != dj0Var) {
            this.u = dj0Var;
        } else {
            z3 = z2;
        }
        if (z3) {
            boolean z4 = this.C;
            no noVar = no.a;
            if (z4) {
                L0();
                if (this.B) {
                    R0().q(noVar);
                }
                this.I = null;
            }
            j00 j00Var = this.L;
            if (j00Var != null) {
                j00Var.a();
                zo zoVar = j00Var.a;
                if (zoVar.B) {
                    zoVar.O0(noVar);
                }
                j00Var.g = null;
                pu puVar = j00Var.k;
                puVar.a = 0;
                ((ke0) puVar.b).b = 0;
            }
        }
    }

    @Override // defpackage.xm0
    public final void c0() {
        g0();
    }

    @Override // defpackage.xm0
    public final void g0() {
        if (this.C) {
            L0();
            if (this.B) {
                R0().q(no.a);
            }
            this.I = null;
        }
        this.C = false;
    }

    @Override // defpackage.xm0
    public final /* synthetic */ boolean n0() {
        return false;
    }

    @Override // defpackage.jw
    public final boolean r(um0 um0Var) {
        int i;
        if (g30.l(um0Var)) {
            return this.w;
        }
        if (!g30.n(um0Var)) {
            if (this.K == null) {
                this.K = new j21(this.u, 0);
            }
            float d = ((l51) n20.p(this, fi.t)).d();
            long E = g30.E(um0Var, false);
            j21 j21Var = this.K;
            if (j21Var != null) {
                if (!ch0.c(j21Var.a(E, d, false), 9205357640488583168L)) {
                    long g = ch0.g(j21Var.b, E);
                    double atan2 = (((float) Math.atan2(Math.abs(Float.intBitsToFloat((int) (g & 4294967295L))), Math.abs(Float.intBitsToFloat((int) (g >> 32))))) * 180.0f) / 3.141592653589793d;
                    dj0 dj0Var = j21Var.a;
                    if (dj0Var == null) {
                        i = -1;
                    } else {
                        i = i21.a[dj0Var.ordinal()];
                    }
                    if (i == 1 ? atan2 < 30.0d : !(i != 2 || atan2 <= 30.0d)) {
                        return true;
                    }
                }
            } else {
                v7.m("Touch slop detector not initialized.");
                return false;
            }
        }
        return false;
    }

    @Override // defpackage.bd0
    public void u0() {
        g0();
    }

    @Override // defpackage.bd0
    public final void v0() {
        this.B = false;
        J0();
        this.M = 0L;
        kw kwVar = this.y;
        if (kwVar != null) {
            E0(kwVar);
        }
        this.y = null;
    }
}
