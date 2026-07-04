package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xm extends py0 {
    public static final Object h = new Object();
    public long c;
    public int d;
    public oe0 e;
    public Object f;
    public int g;

    public xm(long j) {
        super(j);
        oe0 oe0Var = xg0.a;
        oe0Var.getClass();
        this.e = oe0Var;
        this.f = h;
    }

    @Override // defpackage.py0
    public final void a(py0 py0Var) {
        py0Var.getClass();
        xm xmVar = (xm) py0Var;
        this.e = xmVar.e;
        this.f = xmVar.f;
        this.g = xmVar.g;
    }

    @Override // defpackage.py0
    public final py0 b(long j) {
        return new xm(j);
    }

    public final boolean c(ym ymVar, ww0 ww0Var) {
        boolean z;
        boolean z2;
        Object obj = cx0.c;
        synchronized (obj) {
            z = true;
            if (this.c == ww0Var.g()) {
                if (this.d == ww0Var.h()) {
                    z2 = false;
                }
            }
            z2 = true;
        }
        if (this.f == h || (z2 && this.g != d(ymVar, ww0Var))) {
            z = false;
        }
        if (z && z2) {
            synchronized (obj) {
                this.c = ww0Var.g();
                this.d = ww0Var.h();
            }
            return z;
        }
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v10, types: [xm] */
    /* JADX WARN: Type inference failed for: r13v5, types: [py0] */
    /* JADX WARN: Type inference failed for: r13v6, types: [py0, java.lang.Object] */
    public final int d(ym ymVar, ww0 ww0Var) {
        oe0 oe0Var;
        int i;
        long[] jArr;
        int i2;
        Object[] objArr;
        long[] jArr2;
        int i3;
        Object[] objArr2;
        long j;
        long j2;
        int i4;
        ?? i5;
        synchronized (cx0.c) {
            oe0Var = this.e;
        }
        int i6 = 7;
        if (oe0Var.e == 0) {
            return 7;
        }
        ef0 q = n30.q();
        Object[] objArr3 = q.e;
        int i7 = q.g;
        boolean z = false;
        for (int i8 = 0; i8 < i7; i8++) {
            ((aw) objArr3[i8]).b();
        }
        try {
            Object[] objArr4 = oe0Var.b;
            int[] iArr = oe0Var.c;
            long[] jArr3 = oe0Var.a;
            int length = jArr3.length - 2;
            if (length >= 0) {
                i = 7;
                int i9 = 0;
                while (true) {
                    long j3 = jArr3[i9];
                    long j4 = -9187201950435737472L;
                    if ((((~j3) << i6) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i10 = 8;
                        int i11 = 8 - ((~(i9 - length)) >>> 31);
                        i2 = i6;
                        int i12 = z ? 1 : 0;
                        while (i12 < i11) {
                            if ((j3 & 255) < 128) {
                                int i13 = (i9 << 3) + i12;
                                j2 = j4;
                                ny0 ny0Var = (ny0) objArr4[i13];
                                int i14 = i10;
                                if (iArr[i13] != 1) {
                                    jArr2 = jArr3;
                                    i3 = i12;
                                    objArr2 = objArr4;
                                    j = j3;
                                } else {
                                    if (ny0Var instanceof ym) {
                                        ym ymVar2 = (ym) ny0Var;
                                        i5 = ymVar2.g((xm) cx0.i(ymVar2.h, ww0Var), ww0Var, z, ymVar2.f);
                                        oe0 oe0Var2 = i5.e;
                                        Object[] objArr5 = oe0Var2.b;
                                        long[] jArr4 = oe0Var2.a;
                                        int length2 = jArr4.length - 2;
                                        jArr2 = jArr3;
                                        i3 = i12;
                                        objArr2 = objArr4;
                                        if (length2 >= 0) {
                                            int i15 = 0;
                                            while (true) {
                                                long j5 = jArr4[i15];
                                                j = j3;
                                                int i16 = i;
                                                if ((((~j5) << i2) & j5 & j2) != j2) {
                                                    int i17 = 8 - ((~(i15 - length2)) >>> 31);
                                                    for (int i18 = 0; i18 < i17; i18++) {
                                                        if ((j5 & 255) < 128) {
                                                            i16 = (i16 * 31) + System.identityHashCode((ny0) objArr5[(i15 << 3) + i18]);
                                                        }
                                                        j5 >>= i14;
                                                    }
                                                    if (i17 != i14) {
                                                        i = i16;
                                                        break;
                                                    }
                                                }
                                                i = i16;
                                                if (i15 == length2) {
                                                    break;
                                                }
                                                i15++;
                                                j3 = j;
                                                i14 = 8;
                                            }
                                        } else {
                                            j = j3;
                                        }
                                    } else {
                                        jArr2 = jArr3;
                                        i3 = i12;
                                        objArr2 = objArr4;
                                        j = j3;
                                        i5 = cx0.i(ny0Var.a(), ww0Var);
                                    }
                                    int identityHashCode = ((i * 31) + System.identityHashCode(i5)) * 31;
                                    long j6 = i5.a;
                                    i = identityHashCode + ((int) (j6 ^ (j6 >>> 32)));
                                }
                                i4 = 8;
                            } else {
                                jArr2 = jArr3;
                                i3 = i12;
                                objArr2 = objArr4;
                                j = j3;
                                j2 = j4;
                                i4 = i10;
                            }
                            j3 = j >> i4;
                            i10 = i4;
                            j4 = j2;
                            objArr4 = objArr2;
                            z = false;
                            i12 = i3 + 1;
                            jArr3 = jArr2;
                        }
                        jArr = jArr3;
                        objArr = objArr4;
                        if (i11 != i10) {
                            break;
                        }
                    } else {
                        jArr = jArr3;
                        i2 = i6;
                        objArr = objArr4;
                    }
                    if (i9 != length) {
                        i9++;
                        i6 = i2;
                        jArr3 = jArr;
                        objArr4 = objArr;
                        z = false;
                    } else {
                        i6 = i;
                        break;
                    }
                }
            }
            i = i6;
            Object[] objArr6 = q.e;
            int i19 = q.g;
            for (int i20 = 0; i20 < i19; i20++) {
                ((aw) objArr6[i20]).a();
            }
            return i;
        } catch (Throwable th) {
            Object[] objArr7 = q.e;
            int i21 = q.g;
            for (int i22 = 0; i22 < i21; i22++) {
                ((aw) objArr7[i22]).a();
            }
            throw th;
        }
    }
}
