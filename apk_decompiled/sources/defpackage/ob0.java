package defpackage;

import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class ob0 extends em0 implements rc0, od0 {
    public lb0 j;
    public gv k;
    public gm0 l;
    public boolean m;
    public boolean n;
    public boolean o;
    public final pb0 p = new pb0(0, this);
    public rr0 q;
    public ve0 r;

    public static void A0(ng0 ng0Var) {
        z40 z40Var;
        a50 a50Var;
        ng0 ng0Var2 = ng0Var.t;
        z40 z40Var2 = ng0Var.s;
        if (ng0Var2 != null) {
            z40Var = ng0Var2.s;
        } else {
            z40Var = null;
        }
        if (!o20.e(z40Var, z40Var2)) {
            z40Var2.I.p.A.f();
            return;
        }
        a3 K = z40Var2.I.p.K();
        if (K != null && (a50Var = ((oc0) K).A) != null) {
            a50Var.f();
        }
    }

    public final qc0 B0(int i, int i2, Map map, gv gvVar, gv gvVar2) {
        if ((i & (-16777216)) != 0 || ((-16777216) & i2) != 0) {
            q00.b("Size(" + i + " x " + i2 + ") is out of range. Each dimension must be between 0 and 16777215.");
        }
        return new nb0(i, i2, map, gvVar, gvVar2, this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void C0(we0 we0Var) {
        z40 z40Var;
        Object[] objArr = we0Var.b;
        long[] jArr = we0Var.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128 && (z40Var = (z40) ((a61) objArr[(i << 3) + i3]).get()) != null) {
                            if (D()) {
                                z40Var.Q(false);
                            } else {
                                z40Var.S(false);
                            }
                        }
                        j >>= 8;
                    }
                    if (i2 != 8) {
                        return;
                    }
                }
                if (i != length) {
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    @Override // defpackage.rc0
    public boolean D() {
        return false;
    }

    public abstract void D0();

    @Override // defpackage.od0
    public final void E(boolean z) {
        z40 z40Var;
        v40 v40Var;
        ob0 x0 = x0();
        v40 v40Var2 = null;
        if (x0 != null) {
            z40Var = x0.v0();
        } else {
            z40Var = null;
        }
        if (o20.e(z40Var, v0())) {
            this.m = z;
            return;
        }
        if (z40Var != null) {
            v40Var = z40Var.I.d;
        } else {
            v40Var = null;
        }
        if (v40Var != v40.g) {
            if (z40Var != null) {
                v40Var2 = z40Var.I.d;
            }
            if (v40Var2 != v40.h) {
                return;
            }
        }
        this.m = z;
    }

    @Override // defpackage.mm
    public final float G(float f) {
        return B() * f;
    }

    @Override // defpackage.mm
    public final /* synthetic */ float O(long j) {
        return d3.e(this, j);
    }

    @Override // defpackage.mm
    public final /* synthetic */ int S(float f) {
        return d3.c(f, this);
    }

    @Override // defpackage.mm
    public final /* synthetic */ long Z(long j) {
        return d3.h(this, j);
    }

    @Override // defpackage.mm
    public final /* synthetic */ float d0(long j) {
        return d3.g(this, j);
    }

    @Override // defpackage.rc0
    public final qc0 e0(int i, int i2, Map map, gv gvVar) {
        return B0(i, i2, map, null, gvVar);
    }

    @Override // defpackage.mm
    public final long j0(float f) {
        return d3.i(f / B(), this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0175  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void m0(defpackage.z40 r32, defpackage.ty r33) {
        /*
            Method dump skipped, instructions count: 394
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ob0.m0(z40, ty):void");
    }

    public abstract int n0(ry ryVar);

    @Override // defpackage.mm
    public final float o0(float f) {
        return f / B();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void p0(gm0 gm0Var, long j, long j2) {
        char c;
        long j3;
        long j4;
        long j5;
        z40 z40Var;
        int i;
        char c2;
        long j6;
        ob0 ob0Var;
        ob0 x0;
        we0 we0Var;
        pj0 snapshotObserver;
        ve0 ve0Var = this.r;
        rr0 rr0Var = this.q;
        if (rr0Var == null) {
            rr0Var = new rr0();
            this.q = rr0Var;
        }
        rr0 rr0Var2 = rr0Var;
        mj0 mj0Var = v0().r;
        if (mj0Var != null && (snapshotObserver = ((b4) mj0Var).getSnapshotObserver()) != null) {
            snapshotObserver.a.b(gm0Var, w3.A, new mb0(this, j, j2, gm0Var));
        }
        boolean D = D();
        we0 we0Var2 = rr0Var2.e;
        we0 we0Var3 = rr0Var2.f;
        int i2 = rr0Var2.a;
        for (int i3 = 0; i3 < i2; i3++) {
            byte b = rr0Var2.d[i3];
            if (b == 3) {
                ty tyVar = rr0Var2.b[i3];
                tyVar.getClass();
                we0Var3.k(tyVar);
            } else if (b != 0 && ve0Var != null) {
                ty tyVar2 = rr0Var2.b[i3];
                tyVar2.getClass();
                we0 we0Var4 = (we0) ve0Var.k(tyVar2);
                if (we0Var4 != null) {
                    we0Var2.j(we0Var4);
                }
            }
        }
        int i4 = rr0Var2.a;
        int i5 = 0;
        for (int i6 = 0; i6 < i4; i6++) {
            byte[] bArr = rr0Var2.d;
            if (bArr[i6] == 2) {
                i5++;
            } else if (i5 > 0) {
                ty[] tyVarArr = rr0Var2.b;
                tyVarArr[i6 - i5] = tyVarArr[i6];
            }
            bArr[i6] = 2;
        }
        int i7 = rr0Var2.a;
        for (int i8 = i7 - i5; i8 < i7; i8++) {
            rr0Var2.b[i8] = null;
        }
        rr0Var2.a -= i5;
        ob0 x02 = x0();
        Object[] objArr = we0Var3.b;
        long[] jArr = we0Var3.a;
        int length = jArr.length - 2;
        char c3 = 7;
        long j7 = -9187201950435737472L;
        int i9 = 8;
        if (length >= 0) {
            j4 = 128;
            int i10 = 0;
            while (true) {
                long j8 = jArr[i10];
                j5 = 255;
                if ((((~j8) << c3) & j8 & j7) != j7) {
                    int i11 = 8 - ((~(i10 - length)) >>> 31);
                    int i12 = 0;
                    while (i12 < i11) {
                        if ((j8 & 255) < 128) {
                            c2 = c3;
                            ty tyVar3 = (ty) objArr[(i10 << 3) + i12];
                            j6 = j7;
                            if (x02 == null) {
                                ob0Var = this;
                            } else {
                                ob0Var = x02;
                            }
                            i = i9;
                            ob0 ob0Var2 = ob0Var;
                            while (true) {
                                rr0 rr0Var3 = ob0Var2.q;
                                if ((rr0Var3 == null || i8.U(rr0Var3.b, tyVar3) < 0) && (x0 = ob0Var2.x0()) != null) {
                                    ob0Var2 = x0;
                                }
                            }
                            ve0 ve0Var2 = ob0Var2.r;
                            if (ve0Var2 != null) {
                                we0Var = (we0) ve0Var2.k(tyVar3);
                            } else {
                                we0Var = null;
                            }
                            if (we0Var != null) {
                                ob0Var.C0(we0Var);
                            }
                        } else {
                            i = i9;
                            c2 = c3;
                            j6 = j7;
                        }
                        j8 >>= i;
                        i12++;
                        c3 = c2;
                        j7 = j6;
                        i9 = i;
                    }
                    c = c3;
                    j3 = j7;
                    if (i11 != i9) {
                        break;
                    }
                } else {
                    c = c3;
                    j3 = j7;
                }
                if (i10 == length) {
                    break;
                }
                i10++;
                c3 = c;
                j7 = j3;
                i9 = 8;
            }
        } else {
            c = 7;
            j3 = -9187201950435737472L;
            j4 = 128;
            j5 = 255;
        }
        we0Var3.b();
        Object[] objArr2 = we0Var2.b;
        long[] jArr2 = we0Var2.a;
        int length2 = jArr2.length - 2;
        if (length2 >= 0) {
            int i13 = 0;
            while (true) {
                long j9 = jArr2[i13];
                if ((((~j9) << c) & j9 & j3) != j3) {
                    int i14 = 8 - ((~(i13 - length2)) >>> 31);
                    for (int i15 = 0; i15 < i14; i15++) {
                        if ((j9 & j5) < j4 && (z40Var = (z40) ((a61) objArr2[(i13 << 3) + i15]).get()) != null) {
                            if (D) {
                                z40Var.Q(false);
                            } else {
                                z40Var.S(false);
                            }
                        }
                        j9 >>= 8;
                    }
                    if (i14 != 8) {
                        break;
                    }
                }
                if (i13 == length2) {
                    break;
                } else {
                    i13++;
                }
            }
        }
        we0Var2.b();
    }

    public final void q0(qc0 qc0Var) {
        boolean z;
        long j;
        long j2;
        ve0 ve0Var = this.r;
        if (!this.o) {
            gv c = qc0Var.c();
            boolean z2 = false;
            if (c == null) {
                if (ve0Var != null) {
                    Object[] objArr = ve0Var.c;
                    long[] jArr = ve0Var.a;
                    int length = jArr.length - 2;
                    if (length >= 0) {
                        int i = 0;
                        while (true) {
                            long j3 = jArr[i];
                            if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                                int i2 = 8 - ((~(i - length)) >>> 31);
                                for (int i3 = 0; i3 < i2; i3++) {
                                    if ((255 & j3) < 128) {
                                        C0((we0) objArr[(i << 3) + i3]);
                                    }
                                    j3 >>= 8;
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
                    ve0Var.a();
                    return;
                }
                return;
            }
            if (this.k != c) {
                z = true;
            } else {
                z = false;
            }
            if (!z && z0().e) {
                l40 t0 = t0();
                long L = f31.L(t0.u(0L));
                long X = t0.X();
                if (!v10.a(L, z0().f) || !c20.a(X, z0().g)) {
                    z2 = true;
                }
                j2 = L;
                j = X;
                z = z2;
            } else {
                j = 0;
                j2 = 9223372034707292159L;
            }
            if (z) {
                gm0 gm0Var = this.l;
                if (gm0Var != null) {
                    gm0Var.e = qc0Var;
                } else {
                    gm0Var = new gm0(qc0Var, this);
                    this.l = gm0Var;
                }
                p0(gm0Var, j2, j);
                this.k = qc0Var.c();
            }
        }
    }

    public final int r0(ry ryVar) {
        int n0;
        if (!u0() || (n0 = n0(ryVar)) == Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return n0 + ((int) (this.i & 4294967295L));
    }

    public abstract ob0 s0();

    public abstract l40 t0();

    public abstract boolean u0();

    public abstract z40 v0();

    public abstract qc0 w0();

    public abstract ob0 x0();

    public abstract long y0();

    public final lb0 z0() {
        lb0 lb0Var = this.j;
        if (lb0Var == null) {
            lb0 lb0Var2 = new lb0(this);
            this.j = lb0Var2;
            return lb0Var2;
        }
        return lb0Var;
    }
}
