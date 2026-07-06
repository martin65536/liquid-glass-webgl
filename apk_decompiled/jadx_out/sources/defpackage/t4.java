package defpackage;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewStructure;
import android.view.autofill.AutofillId;
import android.view.contentcapture.ContentCaptureSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class t4 implements yl, View.OnAttachStateChangeListener {
    public final b4 e;
    public final s3 f;
    public c4 g;
    public final ArrayList h = new ArrayList();
    public final long i = 100;
    public q4 j = q4.e;
    public boolean k = true;
    public final zb l = f31.c(1, 6, null);
    public he0 m;
    public long n;
    public final he0 o;
    public tu0 p;
    public boolean q;
    public final n r;

    public t4(b4 b4Var, s3 s3Var) {
        this.e = b4Var;
        this.f = s3Var;
        new Handler(Looper.getMainLooper());
        he0 he0Var = u10.a;
        he0Var.getClass();
        this.m = he0Var;
        this.o = new he0();
        this.p = new tu0(b4Var.getSemanticsOwner().a(), he0Var);
        this.r = new n(2, this);
    }

    @Override // defpackage.yl
    public final void b(j80 j80Var) {
        p(this.e.getSemanticsOwner().a());
        l();
        this.g = null;
    }

    @Override // defpackage.yl
    public final void c(j80 j80Var) {
        this.g = (c4) this.f.a();
        o(-1, this.e.getSemanticsOwner().a());
        l();
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x004e, code lost:
    
        if (r8 != r4) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0082, code lost:
    
        if (defpackage.f31.r(r7.i, r0) == r4) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0084, code lost:
    
        return r4;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0082 -> B:11:0x0046). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object g(defpackage.jj r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof defpackage.s4
            if (r0 == 0) goto L13
            r0 = r8
            s4 r0 = (defpackage.s4) r0
            int r1 = r0.k
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.k = r1
            goto L18
        L13:
            s4 r0 = new s4
            r0.<init>(r7, r8)
        L18:
            java.lang.Object r8 = r0.i
            int r1 = r0.k
            r2 = 2
            r3 = 1
            ik r4 = defpackage.ik.e
            if (r1 == 0) goto L39
            if (r1 == r3) goto L33
            if (r1 != r2) goto L2c
            yb r1 = r0.h
            defpackage.o30.x(r8)
            goto L46
        L2c:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r7)
            r7 = 0
            return r7
        L33:
            yb r1 = r0.h
            defpackage.o30.x(r8)
            goto L51
        L39:
            defpackage.o30.x(r8)
            zb r8 = r7.l
            r8.getClass()
            yb r1 = new yb
            r1.<init>(r8)
        L46:
            r0.h = r1
            r0.k = r3
            java.lang.Object r8 = r1.b(r0)
            if (r8 != r4) goto L51
            goto L84
        L51:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L85
            r1.c()
            boolean r8 = r7.k()
            if (r8 == 0) goto L65
            r7.l()
        L65:
            b4 r8 = r7.e
            android.os.Handler r8 = r8.getHandler()
            boolean r5 = r7.q
            if (r5 != 0) goto L78
            if (r8 == 0) goto L78
            r7.q = r3
            n r5 = r7.r
            r8.post(r5)
        L78:
            r0.h = r1
            r0.k = r2
            long r5 = r7.i
            java.lang.Object r8 = defpackage.f31.r(r5, r0)
            if (r8 != r4) goto L46
        L84:
            return r4
        L85:
            x31 r7 = defpackage.x31.a
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.t4.g(jj):java.lang.Object");
    }

    public final void i(t10 t10Var) {
        int[] iArr;
        int[] iArr2;
        long j;
        char c;
        long j2;
        int i;
        int i2;
        su0 su0Var;
        long j3;
        l7 l7Var;
        l7 l7Var2;
        long j4;
        l7 l7Var3;
        t10 t10Var2 = t10Var;
        int[] iArr3 = t10Var2.b;
        long[] jArr = t10Var2.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i3 = 0;
            while (true) {
                long j5 = jArr[i3];
                char c2 = 7;
                long j6 = -9187201950435737472L;
                if ((((~j5) << 7) & j5 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i4 = 8;
                    int i5 = 8 - ((~(i3 - length)) >>> 31);
                    int i6 = 0;
                    while (i6 < i5) {
                        if ((j5 & 255) < 128) {
                            int i7 = iArr3[(i3 << 3) + i6];
                            c = c2;
                            tu0 tu0Var = (tu0) this.o.b(i7);
                            uu0 uu0Var = (uu0) t10Var2.b(i7);
                            if (uu0Var != null) {
                                su0Var = uu0Var.a;
                            } else {
                                su0Var = null;
                            }
                            if (su0Var != null) {
                                j2 = j6;
                                int i8 = su0Var.f;
                                ve0 ve0Var = su0Var.d.e;
                                if (tu0Var == null) {
                                    Object[] objArr = ve0Var.b;
                                    long[] jArr2 = ve0Var.a;
                                    int length2 = jArr2.length - 2;
                                    iArr2 = iArr3;
                                    if (length2 >= 0) {
                                        int i9 = i4;
                                        int i10 = 0;
                                        while (true) {
                                            long j7 = jArr2[i10];
                                            j = j5;
                                            if ((((~j7) << c) & j7 & j2) != j2) {
                                                int i11 = 8 - ((~(i10 - length2)) >>> 31);
                                                for (int i12 = 0; i12 < i11; i12++) {
                                                    if ((j7 & 255) < 128) {
                                                        j4 = j7;
                                                        av0 av0Var = (av0) objArr[(i10 << 3) + i12];
                                                        av0 av0Var2 = wu0.z;
                                                        if (o20.e(av0Var, av0Var2)) {
                                                            Object g = ve0Var.g(av0Var2);
                                                            if (g == null) {
                                                                g = null;
                                                            }
                                                            List list = (List) g;
                                                            if (list != null) {
                                                                l7Var3 = (l7) me.T(list);
                                                            } else {
                                                                l7Var3 = null;
                                                            }
                                                            n(String.valueOf(l7Var3), i8);
                                                        }
                                                    } else {
                                                        j4 = j7;
                                                    }
                                                    j7 = j4 >> i9;
                                                }
                                                if (i11 != i9) {
                                                    break;
                                                }
                                            }
                                            if (i10 == length2) {
                                                break;
                                            }
                                            i10++;
                                            j5 = j;
                                            i9 = 8;
                                        }
                                    } else {
                                        j = j5;
                                    }
                                } else {
                                    iArr2 = iArr3;
                                    j = j5;
                                    Object[] objArr2 = ve0Var.b;
                                    long[] jArr3 = ve0Var.a;
                                    int length3 = jArr3.length - 2;
                                    if (length3 >= 0) {
                                        long[] jArr4 = jArr3;
                                        int i13 = 0;
                                        while (true) {
                                            long j8 = jArr4[i13];
                                            long[] jArr5 = jArr4;
                                            i = i6;
                                            if ((((~j8) << c) & j8 & j2) != j2) {
                                                int i14 = 8 - ((~(i13 - length3)) >>> 31);
                                                int i15 = 0;
                                                while (i15 < i14) {
                                                    if ((j8 & 255) < 128) {
                                                        j3 = j8;
                                                        av0 av0Var3 = (av0) objArr2[(i13 << 3) + i15];
                                                        av0 av0Var4 = wu0.z;
                                                        if (o20.e(av0Var3, av0Var4)) {
                                                            Object g2 = tu0Var.a.e.g(av0Var4);
                                                            if (g2 == null) {
                                                                g2 = null;
                                                            }
                                                            List list2 = (List) g2;
                                                            if (list2 != null) {
                                                                l7Var = (l7) me.T(list2);
                                                            } else {
                                                                l7Var = null;
                                                            }
                                                            Object g3 = ve0Var.g(av0Var4);
                                                            if (g3 == null) {
                                                                g3 = null;
                                                            }
                                                            List list3 = (List) g3;
                                                            if (list3 != null) {
                                                                l7Var2 = (l7) me.T(list3);
                                                            } else {
                                                                l7Var2 = null;
                                                            }
                                                            if (!o20.e(l7Var, l7Var2)) {
                                                                n(String.valueOf(l7Var2), i8);
                                                            }
                                                        }
                                                    } else {
                                                        j3 = j8;
                                                    }
                                                    i15++;
                                                    j8 = j3 >> 8;
                                                }
                                                if (i14 != 8) {
                                                    break;
                                                }
                                            }
                                            if (i13 == length3) {
                                                break;
                                            }
                                            i13++;
                                            i6 = i;
                                            jArr4 = jArr5;
                                        }
                                        i2 = 8;
                                    }
                                }
                                i = i6;
                                i2 = 8;
                            } else {
                                throw d3.t("no value for specified key");
                            }
                        } else {
                            iArr2 = iArr3;
                            j = j5;
                            c = c2;
                            j2 = j6;
                            i = i6;
                            i2 = i4;
                        }
                        j5 = j >> i2;
                        i6 = i + 1;
                        i4 = i2;
                        c2 = c;
                        j6 = j2;
                        iArr3 = iArr2;
                        t10Var2 = t10Var;
                    }
                    iArr = iArr3;
                    if (i5 != i4) {
                        return;
                    }
                } else {
                    iArr = iArr3;
                }
                if (i3 != length) {
                    i3++;
                    t10Var2 = t10Var;
                    iArr3 = iArr;
                } else {
                    return;
                }
            }
        }
    }

    public final t10 j() {
        if (this.k) {
            this.k = false;
            this.m = o20.p(this.e.getSemanticsOwner(), w3.i);
            this.n = System.currentTimeMillis();
        }
        return this.m;
    }

    public final boolean k() {
        if (this.g != null) {
            return true;
        }
        return false;
    }

    public final void l() {
        c4 c4Var = this.g;
        if (c4Var != null) {
            Object obj = c4Var.f;
            if (Build.VERSION.SDK_INT >= 29) {
                ArrayList arrayList = this.h;
                if (!arrayList.isEmpty()) {
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        vi viVar = (vi) arrayList.get(i);
                        int ordinal = viVar.c.ordinal();
                        if (ordinal != 0) {
                            if (ordinal == 1) {
                                AutofillId r = c4Var.r(viVar.a);
                                if (r != null && Build.VERSION.SDK_INT >= 29) {
                                    xi.e(h3.d(obj), r);
                                }
                            } else {
                                v7.k();
                                return;
                            }
                        } else {
                            u41 u41Var = viVar.d;
                            if (u41Var != null) {
                                ViewStructure viewStructure = (ViewStructure) u41Var.a;
                                if (Build.VERSION.SDK_INT >= 29) {
                                    xi.d(h3.d(obj), viewStructure);
                                }
                            }
                        }
                    }
                    if (Build.VERSION.SDK_INT >= 29) {
                        ContentCaptureSession d = h3.d(obj);
                        j1 t = m20.t((View) c4Var.g);
                        Objects.requireNonNull(t);
                        xi.g(d, z0.e(t.a), new long[]{Long.MIN_VALUE});
                    }
                    arrayList.clear();
                }
            }
        }
    }

    public final void m(su0 su0Var, tu0 tu0Var) {
        v2 v2Var = new v2(1, tu0Var, this);
        su0Var.getClass();
        List j = su0.j(4, su0Var);
        int size = j.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = j.get(i2);
            if (j().a(((su0) obj).f)) {
                v2Var.d(Integer.valueOf(i), obj);
                i++;
            }
        }
        List j2 = su0.j(4, su0Var);
        int size2 = j2.size();
        for (int i3 = 0; i3 < size2; i3++) {
            su0 su0Var2 = (su0) j2.get(i3);
            t10 j3 = j();
            int i4 = su0Var2.f;
            if (j3.a(i4)) {
                he0 he0Var = this.o;
                if (he0Var.a(i4)) {
                    Object b = he0Var.b(i4);
                    if (b != null) {
                        m(su0Var2, (tu0) b);
                    } else {
                        throw d3.t("node not present in pruned tree before this change");
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public final void n(String str, int i) {
        c4 c4Var;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 29 && (c4Var = this.g) != null) {
            AutofillId r = c4Var.r(i);
            if (r != null) {
                if (i2 >= 29) {
                    xi.f(h3.d(c4Var.f), r, str);
                    return;
                }
                return;
            }
            throw d3.t("Invalid content capture ID");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x0097, code lost:
    
        if (r8 == null) goto L34;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x01cb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void o(int r19, defpackage.su0 r20) {
        /*
            Method dump skipped, instructions count: 489
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.t4.o(int, su0):void");
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        Handler handler = this.e.getHandler();
        handler.getClass();
        handler.removeCallbacks(this.r);
        this.g = null;
    }

    public final void p(su0 su0Var) {
        if (k()) {
            this.h.add(new vi(su0Var.f, this.n, wi.f, null));
            List j = su0.j(4, su0Var);
            int size = j.size();
            for (int i = 0; i < size; i++) {
                p((su0) j.get(i));
            }
        }
    }

    public final void q() {
        he0 he0Var = this.o;
        he0Var.c();
        t10 j = j();
        int[] iArr = j.b;
        Object[] objArr = j.c;
        long[] jArr = j.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j2 = jArr[i];
                if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j2) < 128) {
                            int i4 = (i << 3) + i3;
                            he0Var.h(iArr[i4], new tu0(((uu0) objArr[i4]).a, j()));
                        }
                        j2 >>= 8;
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
        this.p = new tu0(this.e.getSemanticsOwner().a(), j());
    }

    @Override // defpackage.yl
    public final void a(j80 j80Var) {
    }

    @Override // defpackage.yl
    public final void d(j80 j80Var) {
    }

    @Override // defpackage.yl
    public final void e(j80 j80Var) {
    }

    @Override // defpackage.yl
    public final void f(j80 j80Var) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
    }
}
