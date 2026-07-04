package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yo0 {
    public final b4 a;
    public final p5 b;
    public final c21 c;
    public final pe0 d;
    public boolean e;
    public boolean f;
    public boolean g;
    public n3 h;
    public long i;
    public final n9 j;
    public final ue0 k;

    /* JADX WARN: Type inference failed for: r3v1, types: [p5, java.lang.Object] */
    public yo0(b4 b4Var) {
        this.a = b4Var;
        ?? obj = new Object();
        obj.b = new long[192];
        obj.c = new long[192];
        this.b = obj;
        this.c = new c21();
        this.d = new pe0();
        this.i = -1L;
        this.j = new n9(14, this);
        this.k = new ue0();
    }

    public static boolean c(ng0 ng0Var) {
        lj0 lj0Var = ng0Var.P;
        if (lj0Var != null && !t20.A(((kx) lj0Var).b())) {
            return true;
        }
        return false;
    }

    public static long e(z40 z40Var) {
        lg0 lg0Var = z40Var.H;
        ng0 ng0Var = lg0Var.d;
        long j = 0;
        for (ng0 ng0Var2 = lg0Var.c; ng0Var2 != null && ng0Var2 != ng0Var; ng0Var2 = ng0Var2.u) {
            if (c(ng0Var2)) {
                return 9223372034707292159L;
            }
            j = v10.c(j, ng0Var2.D);
        }
        return j;
    }

    public static void h(z40 z40Var) {
        if (z40Var.g && !c(z40Var.H.d)) {
            z40Var.g = false;
            if (z40Var.i) {
                z40Var.h = e(z40Var);
                z40Var.i = false;
            }
            if (!v10.a(z40Var.h, 9223372034707292159L)) {
                ef0 w = z40Var.w();
                Object[] objArr = w.e;
                int i = w.g;
                for (int i2 = 0; i2 < i; i2++) {
                    h((z40) objArr[i2]);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:128:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:131:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x022c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void a() {
        /*
            Method dump skipped, instructions count: 643
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.yo0.a():void");
    }

    public final long b(z40 z40Var) {
        long j;
        int i = z40Var.f & 33554431;
        p5 p5Var = this.b;
        long[] jArr = (long[]) p5Var.b;
        int i2 = p5Var.a;
        for (int i3 = 0; i3 < jArr.length - 2 && i3 < i2; i3 += 3) {
            if ((((int) jArr[i3 + 2]) & 33554431) == i) {
                j = jArr[i3];
                break;
            }
        }
        j = Long.MAX_VALUE;
        if (j == Long.MAX_VALUE) {
            return 9223372034707292159L;
        }
        return (((int) j) & 4294967295L) | (((int) (j >> 32)) << 32);
    }

    public final void d(z40 z40Var) {
        boolean z;
        int i;
        boolean z2 = true;
        z40Var.g = true;
        lg0 lg0Var = z40Var.H;
        ng0 ng0Var = lg0Var.d;
        oc0 oc0Var = z40Var.I.p;
        int g0 = oc0Var.g0();
        float f0 = oc0Var.f0();
        ue0 ue0Var = this.k;
        ue0Var.a = 0.0f;
        ue0Var.b = 0.0f;
        ue0Var.c = g0;
        ue0Var.d = f0;
        while (true) {
            if (ng0Var == null) {
                break;
            }
            z40 z40Var2 = ng0Var.s;
            if (ng0Var == z40Var2.H.d && !z40Var2.g) {
                if (!v10.a(b(z40Var2), 9223372034707292159L)) {
                    ue0Var.c((Float.floatToRawIntBits((int) (r9 >> 32)) << 32) | (Float.floatToRawIntBits((int) (r9 & 4294967295L)) & 4294967295L));
                    break;
                }
            }
            lj0 lj0Var = ng0Var.P;
            if (lj0Var != null) {
                float[] b = ((kx) lj0Var).b();
                if (!t20.A(b)) {
                    m20.z(b, ue0Var);
                }
            }
            long j = ng0Var.D;
            ue0Var.c((4294967295L & Float.floatToRawIntBits((int) (j & 4294967295L))) | (Float.floatToRawIntBits((int) (j >> 32)) << 32));
            ng0Var = ng0Var.u;
        }
        int i2 = (int) ue0Var.a;
        int i3 = (int) ue0Var.b;
        int i4 = (int) ue0Var.c;
        int i5 = (int) ue0Var.d;
        int i6 = z40Var.f;
        boolean z3 = z40Var.k;
        z40Var.k = true;
        p5 p5Var = this.b;
        if (z3) {
            int i7 = i6 & 33554431;
            long[] jArr = (long[]) p5Var.b;
            int i8 = p5Var.a;
            int i9 = 0;
            while (i9 < jArr.length - 2 && i9 < i8) {
                int i10 = i9 + 2;
                long j2 = jArr[i10];
                z = z2;
                if ((((int) j2) & 33554431) == i7) {
                    jArr[i9] = (i2 << 32) | (i3 & 4294967295L);
                    jArr[i9 + 1] = (i4 << 32) | (i5 & 4294967295L);
                    jArr[i10] = (((j2 >> 63) & 1) << 60) | j2;
                    break;
                }
                i9 += 3;
                z2 = z;
            }
        }
        z = z2;
        z40 s = z40Var.s();
        if (s != null) {
            i = s.f;
        } else {
            i = -1;
        }
        p5.e(p5Var, i6, i2, i3, i4, i5, i, lg0Var.d(1024), lg0Var.d(16), this.c.a.a(i6), 512);
        z40Var.j = false;
        this.e = z;
        ef0 w = z40Var.w();
        Object[] objArr = w.e;
        int i11 = w.g;
        for (int i12 = 0; i12 < i11; i12++) {
            z40 z40Var3 = (z40) objArr[i12];
            if (z40Var3.F()) {
                d(z40Var3);
            }
        }
    }

    public final void f(z40 z40Var) {
        long j;
        boolean z;
        boolean z2;
        boolean F = z40Var.F();
        lg0 lg0Var = z40Var.H;
        if (F && z40Var.j) {
            z40 s = z40Var.s();
            if (s != null && !s.g) {
                if (s.i) {
                    s.i = false;
                    s.h = e(s);
                }
                j = s.h;
            } else if (s == null) {
                j = 0;
            } else {
                j = 9223372034707292159L;
            }
            ng0 ng0Var = lg0Var.d;
            if (!v10.a(j, 9223372034707292159L) && !c(ng0Var)) {
                if (!z40Var.g) {
                    long c = v10.c(j, ng0Var.D);
                    oc0 oc0Var = z40Var.I.p;
                    int g0 = oc0Var.g0();
                    int f0 = oc0Var.f0();
                    int i = z40Var.f;
                    boolean z3 = z40Var.k;
                    p5 p5Var = this.b;
                    long j2 = 4294967295L;
                    if (z3) {
                        if (s != null) {
                            int i2 = s.f;
                            int i3 = (int) (c >> 32);
                            int i4 = (int) (c & 4294967295L);
                            int i5 = i & 33554431;
                            long[] jArr = (long[]) p5Var.b;
                            int i6 = p5Var.a;
                            int i7 = 0;
                            while (true) {
                                if (i7 >= jArr.length - 2 || i7 >= i6) {
                                    break;
                                }
                                long j3 = j2;
                                if ((((int) jArr[i7 + 2]) & 33554431) == i2) {
                                    long j4 = jArr[i7];
                                    int i8 = ((int) (j4 >> 32)) + i3;
                                    int i9 = ((int) j4) + i4;
                                    int i10 = i8 + g0;
                                    int i11 = i9 + f0;
                                    i7 += 3;
                                    while (i7 < jArr.length - 2 && i7 < i6) {
                                        int i12 = i7 + 2;
                                        int i13 = i2;
                                        int i14 = i3;
                                        long j5 = jArr[i12];
                                        int i15 = i4;
                                        if ((((int) j5) & 33554431) == i5) {
                                            long j6 = jArr[i7];
                                            long[] jArr2 = jArr;
                                            int i16 = i8 - ((int) (j6 >> 32));
                                            int i17 = i9 - ((int) j6);
                                            jArr2[i7] = (i9 & j3) | (i8 << 32);
                                            jArr2[i7 + 1] = (i10 << 32) | (i11 & j3);
                                            jArr2[i12] = j5 | (((j5 >> 63) & 1) << 60);
                                            if (i16 != 0 || i17 != 0) {
                                                int i18 = xo0.b;
                                                p5Var.g(i16, i17, (j5 & (-1125899873288193L)) | (((i7 + 3) & 33554431) << 25));
                                            }
                                        } else {
                                            i7 += 3;
                                            i2 = i13;
                                            i3 = i14;
                                            i4 = i15;
                                        }
                                    }
                                }
                                i7 += 3;
                                jArr = jArr;
                                j2 = j3;
                                i2 = i2;
                                i3 = i3;
                                i4 = i4;
                            }
                        } else {
                            int i19 = (int) (c >> 32);
                            int i20 = (int) (c & 4294967295L);
                            int i21 = g0 + i19;
                            int i22 = i20 + f0;
                            int i23 = i & 33554431;
                            long[] jArr3 = (long[]) p5Var.b;
                            int i24 = p5Var.a;
                            int i25 = 0;
                            while (true) {
                                if (i25 >= jArr3.length - 2 || i25 >= i24) {
                                    break;
                                }
                                int i26 = i25 + 2;
                                long j7 = jArr3[i26];
                                if ((((int) j7) & 33554431) == i23) {
                                    long j8 = jArr3[i25];
                                    int i27 = i25;
                                    jArr3[i27] = (i19 << 32) | (i20 & 4294967295L);
                                    jArr3[i27 + 1] = (i21 << 32) | (i22 & 4294967295L);
                                    jArr3[i26] = (((j7 >> 63) & 1) << 60) | j7;
                                    int i28 = i19 - ((int) (j8 >> 32));
                                    int i29 = i20 - ((int) j8);
                                    if (i28 != 0) {
                                        z = true;
                                    } else {
                                        z = false;
                                    }
                                    if (i29 != 0) {
                                        z2 = true;
                                    } else {
                                        z2 = false;
                                    }
                                    if (z | z2) {
                                        int i30 = xo0.b;
                                        p5Var.g(i28, i29, (j7 & (-1125899873288193L)) | (((i27 + 3) & 33554431) << 25));
                                    }
                                } else {
                                    i25 += 3;
                                }
                            }
                        }
                    } else {
                        z40Var.k = true;
                        boolean d = lg0Var.d(1024);
                        boolean d2 = lg0Var.d(16);
                        boolean a = this.c.a.a(i);
                        if (s != null) {
                            int i31 = s.f;
                            int i32 = (int) (c >> 32);
                            int i33 = (int) (c & 4294967295L);
                            int i34 = i & 33554431;
                            long[] jArr4 = (long[]) p5Var.b;
                            int i35 = p5Var.a - 3;
                            while (true) {
                                if (i35 < 0) {
                                    break;
                                }
                                if ((((int) jArr4[i35 + 2]) & 33554431) == i31) {
                                    long j9 = jArr4[i35];
                                    int i36 = ((int) (j9 >> 32)) + i32;
                                    int i37 = ((int) j9) + i33;
                                    p5Var.d(i34, i36, i37, i36 + g0, i37 + f0, i31, d, d2, a, i35);
                                    break;
                                }
                                i35 -= 3;
                            }
                        } else {
                            int i38 = (int) (c >> 32);
                            int i39 = (int) (c & 4294967295L);
                            p5.e(p5Var, i, i38, i39, i38 + g0, i39 + f0, 0, d, d2, a, 544);
                        }
                    }
                } else {
                    d(z40Var);
                    h(z40Var);
                }
            } else {
                d(z40Var);
            }
            z40Var.j = false;
            this.e = true;
            i();
        }
    }

    public final void g(z40 z40Var) {
        if (z40Var.k) {
            int i = z40Var.f & 33554431;
            p5 p5Var = this.b;
            long[] jArr = (long[]) p5Var.b;
            int i2 = p5Var.a;
            int i3 = 0;
            while (true) {
                if (i3 >= jArr.length - 2 || i3 >= i2) {
                    break;
                }
                int i4 = i3 + 2;
                if ((((int) jArr[i4]) & 33554431) == i) {
                    jArr[i3] = -1;
                    jArr[i3 + 1] = -1;
                    jArr[i4] = xo0.a;
                    break;
                }
                i3 += 3;
            }
            z40Var.k = false;
            z40Var.j = true;
            this.e = true;
            this.g = true;
        }
    }

    public final void i() {
        boolean z;
        n3 n3Var = this.h;
        if (n3Var != null) {
            z = true;
        } else {
            z = false;
        }
        long j = this.c.c;
        if (j >= 0 || !z) {
            if (this.i == j && z) {
                return;
            }
            b4 b4Var = this.a;
            if (n3Var != null) {
                if (!d3.A(n3Var)) {
                    n3Var = null;
                }
                if (n3Var != null) {
                    b4Var.removeCallbacks(n3Var);
                }
            }
            long currentTimeMillis = System.currentTimeMillis();
            long max = Math.max(j, 16 + currentTimeMillis);
            this.i = max;
            n3 n3Var2 = new n3(this.j, 0);
            b4Var.postDelayed(n3Var2, max - currentTimeMillis);
            this.h = n3Var2;
        }
    }
}
