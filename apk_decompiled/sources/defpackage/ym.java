package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ym extends oy0 implements hy0 {
    public final vu f;
    public final ix0 g;
    public xm h = new xm(cx0.j().g());

    public ym(vu vuVar, x1 x1Var) {
        this.f = vuVar;
        this.g = x1Var;
    }

    @Override // defpackage.ny0
    public final py0 a() {
        return this.h;
    }

    @Override // defpackage.ny0
    public final void c(py0 py0Var) {
        py0Var.getClass();
        this.h = (xm) py0Var;
    }

    public final xm g(xm xmVar, ww0 ww0Var, boolean z, vu vuVar) {
        ef0 q;
        ix0 ix0Var;
        int i;
        xm xmVar2 = xmVar;
        if (xmVar2.c(this, ww0Var)) {
            if (z) {
                q = n30.q();
                Object[] objArr = q.e;
                int i2 = q.g;
                for (int i3 = 0; i3 < i2; i3++) {
                    ((aw) objArr[i3]).b();
                }
                try {
                    oe0 oe0Var = xmVar2.e;
                    r7 r7Var = jx0.a;
                    a20 a20Var = (a20) r7Var.p();
                    if (a20Var == null) {
                        a20Var = new a20();
                        r7Var.C(a20Var);
                    }
                    int i4 = a20Var.a;
                    Object[] objArr2 = oe0Var.b;
                    int[] iArr = oe0Var.c;
                    long[] jArr = oe0Var.a;
                    int length = jArr.length - 2;
                    if (length >= 0) {
                        int i5 = 0;
                        while (true) {
                            long j = jArr[i5];
                            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                int i6 = 8;
                                int i7 = 8 - ((~(i5 - length)) >>> 31);
                                int i8 = 0;
                                while (i8 < i7) {
                                    if ((j & 255) < 128) {
                                        int i9 = (i5 << 3) + i8;
                                        ny0 ny0Var = (ny0) objArr2[i9];
                                        i = i6;
                                        a20Var.a = i4 + iArr[i9];
                                        gv e = ww0Var.e();
                                        if (e != null) {
                                            e.e(ny0Var);
                                        }
                                    } else {
                                        i = i6;
                                    }
                                    j >>= i;
                                    i8++;
                                    i6 = i;
                                }
                                if (i7 != i6) {
                                    break;
                                }
                            }
                            if (i5 == length) {
                                break;
                            }
                            i5++;
                        }
                    }
                    a20Var.a = i4;
                    Object[] objArr3 = q.e;
                    int i10 = q.g;
                    for (int i11 = 0; i11 < i10; i11++) {
                        ((aw) objArr3[i11]).a();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return xmVar2;
        }
        oe0 oe0Var2 = new oe0();
        r7 r7Var2 = jx0.a;
        a20 a20Var2 = (a20) r7Var2.p();
        if (a20Var2 == null) {
            a20Var2 = new a20();
            r7Var2.C(a20Var2);
        }
        int i12 = a20Var2.a;
        q = n30.q();
        Object[] objArr4 = q.e;
        int i13 = q.g;
        for (int i14 = 0; i14 < i13; i14++) {
            ((aw) objArr4[i14]).b();
        }
        try {
            a20Var2.a = i12 + 1;
            Object D = t20.D(new wm(this, a20Var2, oe0Var2, i12), vuVar);
            a20Var2.a = i12;
            Object[] objArr5 = q.e;
            int i15 = q.g;
            for (int i16 = 0; i16 < i15; i16++) {
                ((aw) objArr5[i16]).a();
            }
            Object obj = cx0.c;
            synchronized (obj) {
                try {
                    ww0 j2 = cx0.j();
                    Object obj2 = xmVar2.f;
                    if (obj2 != xm.h && (ix0Var = this.g) != null && ix0Var.b(D, obj2)) {
                        xmVar2.e = oe0Var2;
                        xmVar2.g = xmVar2.d(this, j2);
                    } else {
                        xmVar2 = (xm) cx0.n(this.h, this, j2);
                        xmVar2.e = oe0Var2;
                        xmVar2.g = xmVar2.d(this, j2);
                        xmVar2.f = D;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            a20 a20Var3 = (a20) jx0.a.p();
            if (a20Var3 != null && a20Var3.a == 0) {
                cx0.j().m();
                synchronized (obj) {
                    ww0 j3 = cx0.j();
                    xmVar2.c = j3.g();
                    xmVar2.d = j3.h();
                }
                return xmVar2;
            }
            return xmVar2;
        } finally {
            Object[] objArr6 = q.e;
            int i17 = q.g;
            for (int i18 = 0; i18 < i17; i18++) {
                ((aw) objArr6[i18]).a();
            }
        }
    }

    @Override // defpackage.hy0
    public final Object getValue() {
        gv e = cx0.j().e();
        if (e != null) {
            e.e(this);
        }
        ww0 j = cx0.j();
        return g((xm) cx0.i(this.h, j), j, true, this.f).f;
    }

    public final xm h() {
        ww0 j = cx0.j();
        return g((xm) cx0.i(this.h, j), j, false, this.f);
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("DerivedState(value=");
        xm xmVar = (xm) cx0.h(this.h);
        if (xmVar.c(this, cx0.j())) {
            str = String.valueOf(xmVar.f);
        } else {
            str = "<Not calculated>";
        }
        sb.append(str);
        sb.append(")@");
        sb.append(hashCode());
        return sb.toString();
    }
}
