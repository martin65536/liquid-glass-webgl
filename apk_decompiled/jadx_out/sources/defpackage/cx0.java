package defpackage;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class cx0 {
    public static final ts0 a = new ts0(27);
    public static final r7 b = new r7(11);
    public static final Object c = new Object();
    public static ax0 d;
    public static long e;
    public static final xd0 f;
    public static final p5 g;
    public static List h;
    public static List i;
    public static final ax j;
    public static final o8 k;

    /* JADX WARN: Type inference failed for: r0v12, types: [java.util.concurrent.atomic.AtomicInteger, o8] */
    /* JADX WARN: Type inference failed for: r0v4, types: [xd0, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v5, types: [p5, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v4, types: [ww0, ze0, ax] */
    static {
        ax0 ax0Var = ax0.i;
        d = ax0Var;
        e = 2L;
        ?? obj = new Object();
        obj.c = new long[16];
        obj.d = new int[16];
        int[] iArr = new int[16];
        int i2 = 0;
        while (i2 < 16) {
            int i3 = i2 + 1;
            iArr[i2] = i3;
            i2 = i3;
        }
        obj.e = iArr;
        f = obj;
        ?? obj2 = new Object();
        obj2.b = new int[16];
        obj2.c = new b61[16];
        g = obj2;
        er erVar = er.e;
        h = erVar;
        i = erVar;
        long j2 = e;
        e = 1 + j2;
        ?? ze0Var = new ze0(j2, ax0Var, null, new pb(5));
        d = d.e(ze0Var.b);
        j = ze0Var;
        k = new AtomicInteger(0);
    }

    public static final void a() {
        e(a);
    }

    public static final HashMap b(long j2, ze0 ze0Var, ax0 ax0Var) {
        long[] jArr;
        ax0 ax0Var2;
        long[] jArr2;
        ax0 ax0Var3;
        int i2;
        int i3;
        py0 t;
        we0 x = ze0Var.x();
        if (x != null) {
            long g2 = ze0Var.g();
            ax0 d2 = ze0Var.d().e(g2).d(ze0Var.j);
            Object[] objArr = x.b;
            long[] jArr3 = x.a;
            int length = jArr3.length - 2;
            if (length >= 0) {
                int i4 = 0;
                HashMap hashMap = null;
                while (true) {
                    long j3 = jArr3[i4];
                    if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i5 = 8;
                        int i6 = 8 - ((~(i4 - length)) >>> 31);
                        int i7 = 0;
                        while (i7 < i6) {
                            if ((j3 & 255) < 128) {
                                ny0 ny0Var = (ny0) objArr[(i4 << 3) + i7];
                                py0 a2 = ny0Var.a();
                                jArr2 = jArr3;
                                i2 = i5;
                                i3 = i7;
                                py0 t2 = t(a2, j2, ax0Var);
                                if (t2 != null && (t = t(a2, g2, d2)) != null && !t2.equals(t)) {
                                    ax0Var3 = d2;
                                    py0 t3 = t(a2, g2, ze0Var.d());
                                    if (t3 != null) {
                                        py0 b2 = ny0Var.b(t, t2, t3);
                                        if (b2 == null) {
                                            return null;
                                        }
                                        if (hashMap == null) {
                                            hashMap = new HashMap();
                                        }
                                        hashMap.put(t2, b2);
                                        hashMap = hashMap;
                                    } else {
                                        s();
                                        throw null;
                                    }
                                } else {
                                    ax0Var3 = d2;
                                }
                            } else {
                                jArr2 = jArr3;
                                ax0Var3 = d2;
                                i2 = i5;
                                i3 = i7;
                            }
                            j3 >>= i2;
                            i7 = i3 + 1;
                            i5 = i2;
                            jArr3 = jArr2;
                            d2 = ax0Var3;
                        }
                        jArr = jArr3;
                        ax0Var2 = d2;
                        if (i6 != i5) {
                            return hashMap;
                        }
                    } else {
                        jArr = jArr3;
                        ax0Var2 = d2;
                    }
                    if (i4 != length) {
                        i4++;
                        jArr3 = jArr;
                        d2 = ax0Var2;
                    } else {
                        return hashMap;
                    }
                }
            }
        }
        return null;
    }

    public static final void c(ww0 ww0Var) {
        ze0 ze0Var;
        Object obj;
        long j2;
        if (!d.c(ww0Var.g())) {
            StringBuilder sb = new StringBuilder("Snapshot is not open: snapshotId=");
            sb.append(ww0Var.g());
            sb.append(", disposed=");
            sb.append(ww0Var.c);
            sb.append(", applied=");
            if (ww0Var instanceof ze0) {
                ze0Var = (ze0) ww0Var;
            } else {
                ze0Var = null;
            }
            if (ze0Var != null) {
                obj = Boolean.valueOf(ze0Var.m);
            } else {
                obj = "read-only";
            }
            sb.append(obj);
            sb.append(", lowestPin=");
            synchronized (c) {
                xd0 xd0Var = f;
                if (xd0Var.a > 0) {
                    j2 = ((long[]) xd0Var.c)[0];
                } else {
                    j2 = -1;
                }
            }
            sb.append(j2);
            throw new IllegalStateException(sb.toString().toString());
        }
    }

    public static final ax0 d(ax0 ax0Var, long j2, long j3) {
        while (o20.j(j2, j3) < 0) {
            ax0Var = ax0Var.e(j2);
            j2++;
        }
        return ax0Var;
    }

    public static final Object e(gv gvVar) {
        we0 we0Var;
        Object w;
        ax axVar = j;
        synchronized (c) {
            try {
                we0Var = axVar.h;
                if (we0Var != null) {
                    k.addAndGet(1);
                }
                w = w(axVar, gvVar);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (we0Var != null) {
            try {
                List list = h;
                bt0 bt0Var = new bt0(we0Var);
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((kv) list.get(i2)).d(bt0Var, axVar);
                }
            } finally {
                k.addAndGet(-1);
            }
        }
        synchronized (c) {
            f();
            if (we0Var != null) {
                Object[] objArr = we0Var.b;
                long[] jArr = we0Var.a;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i3 = 0;
                    while (true) {
                        long j2 = jArr[i3];
                        if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i4 = 8 - ((~(i3 - length)) >>> 31);
                            for (int i5 = 0; i5 < i4; i5++) {
                                if ((255 & j2) < 128) {
                                    r((ny0) objArr[(i3 << 3) + i5]);
                                }
                                j2 >>= 8;
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
        }
        return w;
    }

    public static final void f() {
        p5 p5Var = g;
        int i2 = p5Var.a;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            Object obj = null;
            if (i3 >= i2) {
                break;
            }
            b61 b61Var = ((b61[]) p5Var.c)[i3];
            if (b61Var != null) {
                obj = b61Var.get();
            }
            if (obj != null && q((ny0) obj)) {
                if (i4 != i3) {
                    ((b61[]) p5Var.c)[i4] = b61Var;
                    int[] iArr = (int[]) p5Var.b;
                    iArr[i4] = iArr[i3];
                }
                i4++;
            }
            i3++;
        }
        for (int i5 = i4; i5 < i2; i5++) {
            ((b61[]) p5Var.c)[i5] = null;
            ((int[]) p5Var.b)[i5] = 0;
        }
        if (i4 != i2) {
            p5Var.a = i4;
        }
    }

    public static final ww0 g(ww0 ww0Var, gv gvVar, boolean z) {
        ze0 ze0Var;
        boolean z2 = ww0Var instanceof ze0;
        if (!z2 && ww0Var != null) {
            return new u21(ww0Var, gvVar, false, z);
        }
        if (z2) {
            ze0Var = (ze0) ww0Var;
        } else {
            ze0Var = null;
        }
        return new t21(ze0Var, gvVar, null, false, z);
    }

    public static final py0 h(py0 py0Var) {
        py0 t;
        ww0 j2 = j();
        py0 t2 = t(py0Var, j2.g(), j2.d());
        if (t2 == null) {
            synchronized (c) {
                ww0 j3 = j();
                t = t(py0Var, j3.g(), j3.d());
            }
            if (t != null) {
                return t;
            }
            s();
            throw null;
        }
        return t2;
    }

    public static final py0 i(py0 py0Var, ww0 ww0Var) {
        py0 t;
        py0 t2 = t(py0Var, ww0Var.g(), ww0Var.d());
        if (t2 == null) {
            synchronized (c) {
                t = t(py0Var, ww0Var.g(), ww0Var.d());
            }
            if (t != null) {
                return t;
            }
            s();
            throw null;
        }
        return t2;
    }

    public static final ww0 j() {
        ww0 ww0Var = (ww0) b.p();
        if (ww0Var == null) {
            return j;
        }
        return ww0Var;
    }

    public static final gv k(gv gvVar, gv gvVar2, boolean z) {
        if (!z) {
            gvVar2 = null;
        }
        if (gvVar != null && gvVar2 != null && gvVar != gvVar2) {
            return new bx0(gvVar, gvVar2, 0);
        }
        if (gvVar == null) {
            return gvVar2;
        }
        return gvVar;
    }

    public static final gv l(gv gvVar, gv gvVar2) {
        if (gvVar != null && gvVar2 != null && gvVar != gvVar2) {
            return new bx0(gvVar, gvVar2, 1);
        }
        if (gvVar == null) {
            return gvVar2;
        }
        return gvVar;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0044, code lost:
    
        r3 = r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final defpackage.py0 m(defpackage.py0 r10, defpackage.ny0 r11) {
        /*
            py0 r0 = r11.a()
            long r1 = defpackage.cx0.e
            xd0 r3 = defpackage.cx0.f
            int r4 = r3.a
            if (r4 <= 0) goto L14
            java.lang.Object r1 = r3.c
            long[] r1 = (long[]) r1
            r2 = 0
            r2 = r1[r2]
            r1 = r2
        L14:
            r3 = 1
            long r1 = r1 - r3
            r3 = 0
            r4 = r3
        L19:
            if (r0 == 0) goto L4b
            long r5 = r0.a
            r7 = 0
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 != 0) goto L24
            goto L44
        L24:
            int r7 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r7 == 0) goto L48
            int r7 = defpackage.o20.j(r5, r1)
            if (r7 > 0) goto L48
            ax0 r7 = defpackage.ax0.i
            boolean r5 = r7.c(r5)
            if (r5 != 0) goto L48
            if (r4 != 0) goto L3a
            r4 = r0
            goto L48
        L3a:
            long r1 = r0.a
            long r5 = r4.a
            int r1 = defpackage.o20.j(r1, r5)
            if (r1 >= 0) goto L46
        L44:
            r3 = r0
            goto L4b
        L46:
            r3 = r4
            goto L4b
        L48:
            py0 r0 = r0.b
            goto L19
        L4b:
            r0 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            if (r3 == 0) goto L55
            r3.a = r0
            return r3
        L55:
            py0 r10 = r10.b(r0)
            py0 r0 = r11.a()
            r10.b = r0
            r11.c(r10)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cx0.m(py0, ny0):py0");
    }

    public static final py0 n(py0 py0Var, ym ymVar, ww0 ww0Var) {
        py0 m;
        synchronized (c) {
            m = m(py0Var, ymVar);
            m.a(py0Var);
            m.a = ww0Var.g();
        }
        return m;
    }

    public static final void o(ww0 ww0Var, ny0 ny0Var) {
        ww0Var.t(ww0Var.h() + 1);
        gv i2 = ww0Var.i();
        if (i2 != null) {
            i2.e(ny0Var);
        }
    }

    public static final py0 p(py0 py0Var, oy0 oy0Var, ww0 ww0Var, py0 py0Var2) {
        py0 m;
        if (ww0Var.f()) {
            ww0Var.n(oy0Var);
        }
        long g2 = ww0Var.g();
        if (py0Var2.a == g2) {
            return py0Var2;
        }
        synchronized (c) {
            m = m(py0Var, oy0Var);
        }
        m.a = g2;
        if (py0Var2.a != 1) {
            ww0Var.n(oy0Var);
        }
        return m;
    }

    public static final boolean q(ny0 ny0Var) {
        py0 py0Var;
        long j2 = e;
        xd0 xd0Var = f;
        if (xd0Var.a > 0) {
            j2 = ((long[]) xd0Var.c)[0];
        }
        py0 py0Var2 = null;
        py0 py0Var3 = null;
        int i2 = 0;
        for (py0 a2 = ny0Var.a(); a2 != null; a2 = a2.b) {
            long j3 = a2.a;
            if (j3 != 0) {
                if (o20.j(j3, j2) < 0) {
                    if (py0Var2 == null) {
                        i2++;
                        py0Var2 = a2;
                    } else {
                        if (o20.j(a2.a, py0Var2.a) < 0) {
                            py0Var = py0Var2;
                            py0Var2 = a2;
                        } else {
                            py0Var = a2;
                        }
                        if (py0Var3 == null) {
                            py0Var3 = ny0Var.a();
                            py0 py0Var4 = py0Var3;
                            while (true) {
                                if (py0Var3 != null) {
                                    if (o20.j(py0Var3.a, j2) >= 0) {
                                        break;
                                    }
                                    if (o20.j(py0Var4.a, py0Var3.a) < 0) {
                                        py0Var4 = py0Var3;
                                    }
                                    py0Var3 = py0Var3.b;
                                } else {
                                    py0Var3 = py0Var4;
                                    break;
                                }
                            }
                        }
                        py0Var2.a = 0L;
                        py0Var2.a(py0Var3);
                        py0Var2 = py0Var;
                    }
                } else {
                    i2++;
                }
            }
        }
        if (i2 <= 1) {
            return false;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void r(ny0 ny0Var) {
        Object obj;
        Object obj2;
        Object obj3;
        if (q(ny0Var)) {
            p5 p5Var = g;
            int i2 = p5Var.a;
            int identityHashCode = System.identityHashCode(ny0Var);
            int i3 = -1;
            if (i2 > 0) {
                int i4 = p5Var.a - 1;
                int i5 = 0;
                while (true) {
                    if (i5 <= i4) {
                        int i6 = (i5 + i4) >>> 1;
                        int i7 = ((int[]) p5Var.b)[i6];
                        if (i7 < identityHashCode) {
                            i5 = i6 + 1;
                        } else if (i7 > identityHashCode) {
                            i4 = i6 - 1;
                        } else {
                            b61 b61Var = ((b61[]) p5Var.c)[i6];
                            if (b61Var != null) {
                                obj = b61Var.get();
                            } else {
                                obj = null;
                            }
                            if (ny0Var != obj) {
                                for (int i8 = i6 - 1; -1 < i8 && ((int[]) p5Var.b)[i8] == identityHashCode; i8--) {
                                    b61 b61Var2 = ((b61[]) p5Var.c)[i8];
                                    if (b61Var2 != null) {
                                        obj3 = b61Var2.get();
                                    } else {
                                        obj3 = null;
                                    }
                                    if (obj3 == ny0Var) {
                                        i3 = i8;
                                        break;
                                    }
                                }
                                i6++;
                                int i9 = p5Var.a;
                                while (true) {
                                    if (i6 < i9) {
                                        if (((int[]) p5Var.b)[i6] != identityHashCode) {
                                            i3 = -(i6 + 1);
                                            break;
                                        }
                                        b61 b61Var3 = ((b61[]) p5Var.c)[i6];
                                        if (b61Var3 != null) {
                                            obj2 = b61Var3.get();
                                        } else {
                                            obj2 = null;
                                        }
                                        if (obj2 == ny0Var) {
                                            break;
                                        } else {
                                            i6++;
                                        }
                                    } else {
                                        i3 = -(p5Var.a + 1);
                                        break;
                                    }
                                }
                            }
                            i3 = i6;
                        }
                    } else {
                        i3 = -(i5 + 1);
                        break;
                    }
                }
                if (i3 >= 0) {
                    return;
                }
            }
            int i10 = -(i3 + 1);
            b61[] b61VarArr = (b61[]) p5Var.c;
            int length = b61VarArr.length;
            if (i2 == length) {
                int i11 = length * 2;
                b61[] b61VarArr2 = new b61[i11];
                int[] iArr = new int[i11];
                int i12 = i10 + 1;
                System.arraycopy(b61VarArr, i10, b61VarArr2, i12, i2 - i10);
                System.arraycopy((b61[]) p5Var.c, 0, b61VarArr2, 0, i10);
                i8.L((int[]) p5Var.b, iArr, i12, i10, i2);
                i8.O((int[]) p5Var.b, iArr, 0, i10, 6);
                p5Var.c = b61VarArr2;
                p5Var.b = iArr;
            } else {
                int i13 = i10 + 1;
                System.arraycopy(b61VarArr, i10, b61VarArr, i13, i2 - i10);
                int[] iArr2 = (int[]) p5Var.b;
                i8.L(iArr2, iArr2, i13, i10, i2);
            }
            ((b61[]) p5Var.c)[i10] = new WeakReference(ny0Var);
            ((int[]) p5Var.b)[i10] = identityHashCode;
            p5Var.a++;
        }
    }

    public static final void s() {
        throw new IllegalStateException("Reading a state that was created after the snapshot was taken or in a snapshot that has not yet been applied");
    }

    public static final py0 t(py0 py0Var, long j2, ax0 ax0Var) {
        py0 py0Var2 = null;
        while (py0Var != null) {
            long j3 = py0Var.a;
            if (j3 != 0 && o20.j(j3, j2) <= 0 && !ax0Var.c(j3) && (py0Var2 == null || o20.j(py0Var2.a, py0Var.a) < 0)) {
                py0Var2 = py0Var;
            }
            py0Var = py0Var.b;
        }
        if (py0Var2 == null) {
            return null;
        }
        return py0Var2;
    }

    public static final py0 u(py0 py0Var, ny0 ny0Var) {
        py0 t;
        ww0 j2 = j();
        gv e2 = j2.e();
        if (e2 != null) {
            e2.e(ny0Var);
        }
        py0 t2 = t(py0Var, j2.g(), j2.d());
        if (t2 == null) {
            synchronized (c) {
                ww0 j3 = j();
                py0 a2 = ny0Var.a();
                a2.getClass();
                t = t(a2, j3.g(), j3.d());
                if (t == null) {
                    s();
                    throw null;
                }
            }
            return t;
        }
        return t2;
    }

    public static final void v(int i2) {
        xd0 xd0Var = f;
        int i3 = ((int[]) xd0Var.e)[i2];
        xd0Var.c(i3, xd0Var.a - 1);
        xd0Var.a--;
        long[] jArr = (long[]) xd0Var.c;
        long j2 = jArr[i3];
        int i4 = i3;
        while (i4 > 0) {
            int i5 = ((i4 + 1) >> 1) - 1;
            if (o20.j(jArr[i5], j2) <= 0) {
                break;
            }
            xd0Var.c(i5, i4);
            i4 = i5;
        }
        long[] jArr2 = (long[]) xd0Var.c;
        int i6 = xd0Var.a >> 1;
        while (i3 < i6) {
            int i7 = (i3 + 1) << 1;
            int i8 = i7 - 1;
            if (i7 < xd0Var.a && o20.j(jArr2[i7], jArr2[i8]) < 0) {
                if (o20.j(jArr2[i7], jArr2[i3]) >= 0) {
                    break;
                }
                xd0Var.c(i7, i3);
                i3 = i7;
            } else {
                if (o20.j(jArr2[i8], jArr2[i3]) >= 0) {
                    break;
                }
                xd0Var.c(i8, i3);
                i3 = i8;
            }
        }
        ((int[]) xd0Var.e)[i2] = xd0Var.b;
        xd0Var.b = i2;
    }

    public static final Object w(ax axVar, gv gvVar) {
        long j2 = axVar.b;
        Object e2 = gvVar.e(d.b(j2));
        long j3 = e;
        e = 1 + j3;
        ax0 b2 = d.b(j2);
        d = b2;
        axVar.b = j3;
        axVar.a = b2;
        axVar.g = 0;
        axVar.h = null;
        axVar.o();
        d = d.e(j3);
        return e2;
    }

    public static final py0 x(py0 py0Var, ny0 ny0Var, ww0 ww0Var) {
        py0 t;
        if (ww0Var.f()) {
            ww0Var.n(ny0Var);
        }
        long g2 = ww0Var.g();
        py0 t2 = t(py0Var, g2, ww0Var.d());
        if (t2 != null) {
            if (t2.a == ww0Var.g()) {
                return t2;
            }
            synchronized (c) {
                t = t(ny0Var.a(), g2, ww0Var.d());
                if (t != null) {
                    if (t.a != g2) {
                        py0 m = m(t, ny0Var);
                        m.a(t);
                        m.a = ww0Var.g();
                        t = m;
                    }
                } else {
                    s();
                    throw null;
                }
            }
            if (t2.a != 1) {
                ww0Var.n(ny0Var);
            }
            return t;
        }
        s();
        throw null;
    }
}
