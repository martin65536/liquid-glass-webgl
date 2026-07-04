package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gg0 extends bd0 implements w21 {
    public final vt0 s;
    public final e3 t;
    public gg0 u;
    public final String v = "androidx.compose.ui.input.nestedscroll.NestedScrollNode";

    public gg0(vt0 vt0Var, e3 e3Var) {
        this.s = vt0Var;
        this.t = e3Var;
    }

    public final hk D0() {
        hk hkVar;
        gg0 E0 = E0();
        if (E0 != null) {
            hkVar = E0.D0();
        } else {
            hkVar = null;
        }
        if (hkVar != null && dl.y(hkVar)) {
            return hkVar;
        }
        hk hkVar2 = (hk) this.t.d;
        if (hkVar2 != null) {
            return hkVar2;
        }
        v7.o("in order to access nested coroutine scope you need to attach dispatcher to the `Modifier.nestedScroll` first.");
        return null;
    }

    public final gg0 E0() {
        lg0 lg0Var;
        w21 w21Var = null;
        if (!this.r) {
            return null;
        }
        if (!this.e.r) {
            q00.b("visitAncestors called on an unattached node");
        }
        bd0 bd0Var = this.e.i;
        z40 E = k81.E(this);
        loop0: while (true) {
            if (E == null) {
                break;
            }
            if ((E.H.f.h & 262144) != 0) {
                while (bd0Var != null) {
                    if ((bd0Var.g & 262144) != 0) {
                        bd0 bd0Var2 = bd0Var;
                        ef0 ef0Var = null;
                        while (bd0Var2 != null) {
                            if (bd0Var2 instanceof w21) {
                                w21 w21Var2 = (w21) bd0Var2;
                                if (o20.e(this.v, w21Var2.z()) && gg0.class == w21Var2.getClass()) {
                                    w21Var = w21Var2;
                                    break loop0;
                                }
                            }
                            if ((bd0Var2.g & 262144) != 0 && (bd0Var2 instanceof jm)) {
                                int i = 0;
                                for (bd0 bd0Var3 = ((jm) bd0Var2).t; bd0Var3 != null; bd0Var3 = bd0Var3.j) {
                                    if ((bd0Var3.g & 262144) != 0) {
                                        i++;
                                        if (i == 1) {
                                            bd0Var2 = bd0Var3;
                                        } else {
                                            if (ef0Var == null) {
                                                ef0Var = new ef0(new bd0[16]);
                                            }
                                            if (bd0Var2 != null) {
                                                ef0Var.b(bd0Var2);
                                                bd0Var2 = null;
                                            }
                                            ef0Var.b(bd0Var3);
                                        }
                                    }
                                }
                                if (i == 1) {
                                }
                            }
                            bd0Var2 = k81.h(ef0Var);
                        }
                    }
                    bd0Var = bd0Var.i;
                }
            }
            E = E.s();
            if (E != null && (lg0Var = E.H) != null) {
                bd0Var = lg0Var.e;
            } else {
                bd0Var = null;
            }
        }
        return (gg0) w21Var;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object F0(long r13, long r15, defpackage.jj r17) {
        /*
            r12 = this;
            r1 = r17
            boolean r2 = r1 instanceof defpackage.eg0
            if (r2 == 0) goto L16
            r2 = r1
            eg0 r2 = (defpackage.eg0) r2
            int r3 = r2.l
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r3 & r4
            if (r5 == 0) goto L16
            int r3 = r3 - r4
            r2.l = r3
        L14:
            r8 = r2
            goto L1c
        L16:
            eg0 r2 = new eg0
            r2.<init>(r12, r1)
            goto L14
        L1c:
            java.lang.Object r1 = r8.j
            int r2 = r8.l
            r9 = 0
            r10 = 2
            r3 = 1
            ik r11 = defpackage.ik.e
            if (r2 == 0) goto L3f
            if (r2 == r3) goto L37
            if (r2 != r10) goto L31
            long r2 = r8.h
            defpackage.o30.x(r1)
            goto L81
        L31:
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r0)
            return r9
        L37:
            long r2 = r8.i
            long r4 = r8.h
            defpackage.o30.x(r1)
            goto L55
        L3f:
            defpackage.o30.x(r1)
            r8.h = r13
            r6 = r15
            r8.i = r6
            r8.l = r3
            vt0 r3 = r12.s
            r4 = r13
            java.lang.Object r1 = r3.a(r4, r6, r8)
            if (r1 != r11) goto L53
            goto L7f
        L53:
            r4 = r13
            r2 = r15
        L55:
            v41 r1 = (defpackage.v41) r1
            long r6 = r1.a
            boolean r1 = r12.r
            if (r1 == 0) goto L64
            if (r1 == 0) goto L66
            gg0 r9 = r12.E0()
            goto L66
        L64:
            gg0 r9 = r12.u
        L66:
            if (r9 == 0) goto L87
            long r0 = defpackage.v41.e(r4, r6)
            long r2 = defpackage.v41.d(r2, r6)
            r8.h = r6
            r8.l = r10
            r13 = r0
            r15 = r2
            r17 = r8
            r12 = r9
            java.lang.Object r1 = r12.F0(r13, r15, r17)
            if (r1 != r11) goto L80
        L7f:
            return r11
        L80:
            r2 = r6
        L81:
            v41 r1 = (defpackage.v41) r1
            long r0 = r1.a
            r6 = r2
            goto L89
        L87:
            r0 = 0
        L89:
            long r0 = defpackage.v41.e(r6, r0)
            v41 r2 = new v41
            r2.<init>(r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.gg0.F0(long, long, jj):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final long G0(int r11, long r12, long r14) {
        /*
            r10 = this;
            vt0 r0 = r10.s
            boolean r1 = r0.b
            r2 = 0
            if (r1 == 0) goto L2a
            hu0 r0 = r0.a
            au0 r1 = r0.a
            boolean r1 = r1.b()
            if (r1 == 0) goto L13
            goto L2a
        L13:
            au0 r1 = r0.a
            float r4 = r0.g(r14)
            float r4 = r0.d(r4)
            float r1 = r1.e(r4)
            float r1 = r0.d(r1)
            long r0 = r0.h(r1)
            goto L2b
        L2a:
            r0 = r2
        L2b:
            boolean r4 = r10.r
            if (r4 == 0) goto L35
            gg0 r10 = r10.E0()
        L33:
            r4 = r10
            goto L37
        L35:
            r10 = 0
            goto L33
        L37:
            if (r4 == 0) goto L46
            long r6 = defpackage.ch0.g(r12, r0)
            long r8 = defpackage.ch0.f(r14, r0)
            r5 = r11
            long r2 = r4.G0(r5, r6, r8)
        L46:
            long r10 = defpackage.ch0.g(r0, r2)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.gg0.G0(int, long, long):long");
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x006a, code lost:
    
        if (r11 == r7) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x006c, code lost:
    
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0052, code lost:
    
        if (r11 == r7) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object H0(long r9, defpackage.ij r11) {
        /*
            r8 = this;
            boolean r0 = r11 instanceof defpackage.fg0
            if (r0 == 0) goto L13
            r0 = r11
            fg0 r0 = (defpackage.fg0) r0
            int r1 = r0.k
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.k = r1
            goto L1a
        L13:
            fg0 r0 = new fg0
            jj r11 = (defpackage.jj) r11
            r0.<init>(r8, r11)
        L1a:
            java.lang.Object r11 = r0.i
            int r1 = r0.k
            r2 = 0
            r3 = 0
            r5 = 2
            r6 = 1
            ik r7 = defpackage.ik.e
            if (r1 == 0) goto L3d
            if (r1 == r6) goto L37
            if (r1 != r5) goto L31
            long r8 = r0.h
            defpackage.o30.x(r11)
            goto L6d
        L31:
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r8)
            return r2
        L37:
            long r9 = r0.h
            defpackage.o30.x(r11)
            goto L55
        L3d:
            defpackage.o30.x(r11)
            boolean r11 = r8.r
            if (r11 == 0) goto L48
            gg0 r2 = r8.E0()
        L48:
            if (r2 == 0) goto L5c
            r0.h = r9
            r0.k = r6
            java.lang.Object r11 = r2.H0(r9, r0)
            if (r11 != r7) goto L55
            goto L6c
        L55:
            v41 r11 = (defpackage.v41) r11
            long r1 = r11.a
            r10 = r9
            r8 = r1
            goto L5e
        L5c:
            r10 = r9
            r8 = r3
        L5e:
            defpackage.v41.d(r10, r8)
            r0.h = r8
            r0.k = r5
            v41 r11 = new v41
            r11.<init>(r3)
            if (r11 != r7) goto L6d
        L6c:
            return r7
        L6d:
            v41 r11 = (defpackage.v41) r11
            long r10 = r11.a
            long r8 = defpackage.v41.e(r8, r10)
            v41 r10 = new v41
            r10.<init>(r8)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.gg0.H0(long, ij):java.lang.Object");
    }

    public final long I0(int i, long j) {
        gg0 gg0Var;
        long j2;
        if (this.r) {
            gg0Var = E0();
        } else {
            gg0Var = null;
        }
        if (gg0Var != null) {
            j2 = gg0Var.I0(i, j);
        } else {
            j2 = 0;
        }
        ch0.f(j, j2);
        return ch0.g(j2, 0L);
    }

    @Override // defpackage.bd0
    public final void t0() {
        e3 e3Var = this.t;
        e3Var.a = this;
        e3Var.b = null;
        this.u = null;
        e3Var.c = new n9(13, this);
        e3Var.d = p0();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [ep0, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15, types: [bd0] */
    /* JADX WARN: Type inference failed for: r6v16, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v22 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v7, types: [bd0] */
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
    @Override // defpackage.bd0
    public final void v0() {
        lg0 lg0Var;
        boolean z;
        ?? obj = new Object();
        v3 v3Var = new v3(1, obj);
        gg0 gg0Var = this;
        if (!gg0Var.e.r) {
            q00.b("visitAncestors called on an unattached node");
        }
        bd0 bd0Var = gg0Var.e.i;
        z40 E = k81.E(this);
        loop0: while (E != null) {
            if ((E.H.f.h & 262144) != 0) {
                while (bd0Var != null) {
                    if ((bd0Var.g & 262144) != 0) {
                        jm jmVar = bd0Var;
                        ?? r8 = 0;
                        while (jmVar != 0) {
                            if (jmVar instanceof w21) {
                                w21 w21Var = (w21) jmVar;
                                if (o20.e(z(), w21Var.z()) && getClass() == w21Var.getClass()) {
                                    z = ((Boolean) v3Var.e(w21Var)).booleanValue();
                                } else {
                                    z = true;
                                }
                                if (!z) {
                                    break loop0;
                                }
                            } else if ((jmVar.g & 262144) != 0 && (jmVar instanceof jm)) {
                                bd0 bd0Var2 = jmVar.t;
                                int i = 0;
                                jmVar = jmVar;
                                r8 = r8;
                                while (bd0Var2 != null) {
                                    if ((bd0Var2.g & 262144) != 0) {
                                        i++;
                                        r8 = r8;
                                        if (i == 1) {
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
                                if (i == 1) {
                                }
                            }
                            jmVar = k81.h(r8);
                        }
                    }
                    bd0Var = bd0Var.i;
                }
            }
            E = E.s();
            if (E != null && (lg0Var = E.H) != null) {
                bd0Var = lg0Var.e;
            } else {
                bd0Var = null;
            }
        }
        gg0 gg0Var2 = (gg0) ((w21) obj.e);
        this.u = gg0Var2;
        e3 e3Var = this.t;
        e3Var.b = gg0Var2;
        if (((gg0) e3Var.a) == this) {
            e3Var.a = null;
        }
    }

    @Override // defpackage.w21
    public final Object z() {
        return this.v;
    }
}
