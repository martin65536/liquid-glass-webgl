package defpackage;

import android.graphics.Rect;
import android.os.Build;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import com.kyant.backdrop.catalog.R;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class m20 {
    public static boolean a = false;
    public static Method b;

    /* JADX WARN: Multi-variable type inference failed */
    public static List A(uw0 uw0Var, int i, uw0 uw0Var2, boolean z, boolean z2, boolean z3) {
        boolean z4;
        er erVar;
        boolean z5;
        boolean z6;
        int i2;
        int i3;
        int i4;
        int u = uw0Var.u(i);
        int i5 = i + u;
        int f = uw0Var.f(i);
        int f2 = uw0Var.f(i5);
        int i6 = f2 - f;
        if (i >= 0 && (uw0Var.b[(uw0Var.r(i) * 5) + 1] & 201326592) != 0) {
            z4 = true;
        } else {
            z4 = false;
        }
        uw0Var2.w(u);
        uw0Var2.x(i6, uw0Var2.t);
        if (uw0Var.g < i5) {
            uw0Var.B(i5);
        }
        if (uw0Var.k < f2) {
            uw0Var.C(f2, i5);
        }
        int[] iArr = uw0Var2.b;
        int i7 = uw0Var2.t;
        int i8 = i7 * 5;
        i8.L(uw0Var.b, iArr, i8, i * 5, i5 * 5);
        Object[] objArr = uw0Var2.c;
        int i9 = uw0Var2.i;
        System.arraycopy(uw0Var.c, f, objArr, i9, i6);
        int i10 = uw0Var2.v;
        iArr[i8 + 2] = i10;
        int i11 = i7 - i;
        int i12 = i7 + u;
        int g = i9 - uw0Var2.g(iArr, i7);
        int i13 = uw0Var2.m;
        int i14 = uw0Var2.l;
        int length = objArr.length;
        boolean z7 = z4;
        int i15 = i13;
        int i16 = i7;
        while (i16 < i12) {
            if (i16 != i7) {
                int i17 = (i16 * 5) + 2;
                iArr[i17] = iArr[i17] + i11;
            }
            int[] iArr2 = iArr;
            int g2 = uw0Var2.g(iArr, i16) + g;
            if (i15 < i16) {
                i3 = i7;
                i4 = 0;
            } else {
                i3 = i7;
                i4 = uw0Var2.k;
            }
            iArr2[(i16 * 5) + 4] = uw0.i(g2, i4, i14, length);
            if (i16 == i15) {
                i15++;
            }
            i16++;
            i7 = i3;
            iArr = iArr2;
        }
        int[] iArr3 = iArr;
        uw0Var2.m = i15;
        int a2 = tw0.a(uw0Var.d, i, uw0Var.p());
        int a3 = tw0.a(uw0Var.d, i5, uw0Var.p());
        if (a2 < a3) {
            ArrayList arrayList = uw0Var.d;
            ArrayList arrayList2 = new ArrayList(a3 - a2);
            for (int i18 = a2; i18 < a3; i18++) {
                wv wvVar = (wv) arrayList.get(i18);
                wvVar.a += i11;
                arrayList2.add(wvVar);
            }
            uw0Var2.d.addAll(tw0.a(uw0Var2.d, uw0Var2.t, uw0Var2.p()), arrayList2);
            arrayList.subList(a2, a3).clear();
            erVar = arrayList2;
        } else {
            erVar = er.e;
        }
        if (!erVar.isEmpty()) {
            HashMap hashMap = uw0Var.e;
            HashMap hashMap2 = uw0Var2.e;
            if (hashMap != null && hashMap2 != null) {
                int size = erVar.size();
                for (int i19 = 0; i19 < size; i19++) {
                }
            }
        }
        int i20 = uw0Var2.v;
        uw0Var2.O(i10);
        int E = uw0Var.E(uw0Var.b, i);
        if (!z3) {
            z5 = false;
        } else if (z) {
            if (E >= 0) {
                z6 = true;
            } else {
                z6 = false;
            }
            if (z6) {
                uw0Var.P();
                uw0Var.a(E - uw0Var.t);
                uw0Var.P();
            }
            uw0Var.a(i - uw0Var.t);
            boolean H = uw0Var.H();
            if (z6) {
                uw0Var.M();
                uw0Var.j();
                uw0Var.M();
                uw0Var.j();
            }
            z5 = H;
        } else {
            boolean I = uw0Var.I(i, u);
            uw0Var.J(f, i6, i - 1);
            z5 = I;
        }
        if (z5) {
            rh.a("Unexpectedly removed anchors");
        }
        int i21 = uw0Var2.o;
        int i22 = iArr3[i8 + 1];
        if ((1073741824 & i22) != 0) {
            i2 = 1;
        } else {
            i2 = i22 & 67108863;
        }
        uw0Var2.o = i21 + i2;
        if (z2) {
            uw0Var2.t = i12;
            uw0Var2.i = i9 + i6;
        }
        if (z7) {
            uw0Var2.T(i10);
        }
        return erVar;
    }

    public static final void B(uw0 uw0Var, t7 t7Var, int i) {
        while (true) {
            int i2 = uw0Var.v;
            if (i <= i2 || i >= uw0Var.u) {
                if (i2 == 0 && i == 0) {
                    return;
                }
                uw0Var.M();
                if (uw0Var.y(uw0Var.v)) {
                    t7Var.j();
                }
                uw0Var.j();
            } else {
                return;
            }
        }
    }

    public static final void C(gv gvVar, bw bwVar) {
        bwVar.b(new wa(11, gvVar), x31.a);
    }

    public static final void D(float[] fArr) {
        if (fArr.length < 16) {
            return;
        }
        fArr[0] = 1.0f;
        fArr[1] = 0.0f;
        fArr[2] = 0.0f;
        fArr[3] = 0.0f;
        fArr[4] = 0.0f;
        fArr[5] = 1.0f;
        fArr[6] = 0.0f;
        fArr[7] = 0.0f;
        fArr[8] = 0.0f;
        fArr[9] = 0.0f;
        fArr[10] = 1.0f;
        fArr[11] = 0.0f;
        fArr[12] = 0.0f;
        fArr[13] = 0.0f;
        fArr[14] = 0.0f;
        fArr[15] = 1.0f;
    }

    public static final cd0 E(cd0 cd0Var) {
        return k81.x(cd0Var, null, null, 524284);
    }

    public static final void F(kv kvVar, bw bwVar, Object obj) {
        if (!bwVar.S && o20.e(bwVar.L(), obj)) {
            return;
        }
        bwVar.f0(obj);
        bwVar.b(kvVar, obj);
    }

    public static final long G(String str, long j, long j2, long j3) {
        String str2;
        boolean z;
        int i = d01.a;
        Long l = null;
        try {
            str2 = System.getProperty(str);
        } catch (SecurityException unused) {
            str2 = null;
        }
        if (str2 == null) {
            return j;
        }
        int i2 = 10;
        k81.m(10);
        int length = str2.length();
        if (length != 0) {
            int i3 = 0;
            char charAt = str2.charAt(0);
            long j4 = -9223372036854775807L;
            if (o20.i(charAt, 48) < 0) {
                z = true;
                if (length != 1) {
                    if (charAt != '+') {
                        if (charAt == '-') {
                            j4 = Long.MIN_VALUE;
                            i3 = 1;
                        }
                    } else {
                        z = false;
                        i3 = 1;
                    }
                }
            } else {
                z = false;
            }
            long j5 = 0;
            long j6 = -256204778801521550L;
            while (true) {
                if (i3 < length) {
                    int digit = Character.digit((int) str2.charAt(i3), i2);
                    if (digit < 0) {
                        break;
                    }
                    if (j5 < j6) {
                        if (j6 != -256204778801521550L) {
                            break;
                        }
                        j6 = j4 / 10;
                        if (j5 < j6) {
                            break;
                        }
                    }
                    long j7 = j5 * 10;
                    int i4 = length;
                    long j8 = digit;
                    if (j7 < j4 + j8) {
                        break;
                    }
                    j5 = j7 - j8;
                    i3++;
                    length = i4;
                    i2 = 10;
                } else {
                    l = z ? Long.valueOf(j5) : Long.valueOf(-j5);
                }
            }
        }
        if (l != null) {
            long longValue = l.longValue();
            if (j2 <= longValue && longValue <= j3) {
                return longValue;
            }
            throw new IllegalStateException(("System property '" + str + "' should be in range " + j2 + ".." + j3 + ", but is '" + longValue + '\'').toString());
        }
        throw new IllegalStateException(("System property '" + str + "' has unrecognized value '" + str2 + '\'').toString());
    }

    public static int H(int i, int i2, String str) {
        int i3;
        if ((i2 & 8) != 0) {
            i3 = Integer.MAX_VALUE;
        } else {
            i3 = 2097150;
        }
        return (int) G(str, i, 1L, i3);
    }

    public static final Rect I(z10 z10Var) {
        return new Rect(z10Var.a, z10Var.b, z10Var.c, z10Var.d);
    }

    public static final m10 J(g10 g10Var) {
        return new m10(g10Var.a, g10Var.b, g10Var.c, g10Var.d);
    }

    public static void K(float[] fArr, float f, float f2) {
        if (fArr.length < 16) {
            return;
        }
        float f3 = (fArr[8] * 0.0f) + (fArr[4] * f2) + (fArr[0] * f) + fArr[12];
        float f4 = (fArr[9] * 0.0f) + (fArr[5] * f2) + (fArr[1] * f) + fArr[13];
        float f5 = (fArr[10] * 0.0f) + (fArr[6] * f2) + (fArr[2] * f) + fArr[14];
        float f6 = (fArr[11] * 0.0f) + (fArr[7] * f2) + (fArr[3] * f) + fArr[15];
        fArr[12] = f3;
        fArr[13] = f4;
        fArr[14] = f5;
        fArr[15] = f6;
    }

    public static final void a(final vu vuVar, final cd0 cd0Var, final q60 q60Var, final g70 g70Var, bw bwVar, final int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        boolean z;
        bwVar.W(1055276397);
        if (bwVar.h(vuVar)) {
            i2 = 4;
        } else {
            i2 = 2;
        }
        int i6 = i2 | i;
        if (bwVar.f(cd0Var)) {
            i3 = 32;
        } else {
            i3 = 16;
        }
        int i7 = i6 | i3;
        if (bwVar.f(q60Var)) {
            i4 = 256;
        } else {
            i4 = 128;
        }
        int i8 = i7 | i4;
        if (bwVar.f(g70Var)) {
            i5 = 2048;
        } else {
            i5 = 1024;
        }
        int i9 = i8 | i5;
        if ((i9 & 1171) != 1170) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i9 & 1, z)) {
            final af0 D = n30.D(vuVar, bwVar);
            g30.b(jc0.C(-933153643, new lv() { // from class: g60
                @Override // defpackage.lv
                public final Object c(Object obj, Object obj2, Object obj3) {
                    cd0 b2;
                    cs0 cs0Var = (cs0) obj;
                    bw bwVar2 = (bw) obj2;
                    ((Integer) obj3).getClass();
                    Object L = bwVar2.L();
                    Object obj4 = ph.a;
                    if (L == obj4) {
                        L = new e60(cs0Var, new i60(D, 0));
                        bwVar2.f0(L);
                    }
                    e60 e60Var = (e60) L;
                    Object L2 = bwVar2.L();
                    if (L2 == obj4) {
                        L2 = new hz0(new c4(e60Var));
                        bwVar2.f0(L2);
                    }
                    hz0 hz0Var = (hz0) L2;
                    int i10 = 4;
                    q60 q60Var2 = q60.this;
                    if (q60Var2 != null) {
                        bwVar2.V(1743490539);
                        bwVar2.V(887527095);
                        Object obj5 = kn0.a;
                        if (obj5 != null) {
                            bwVar2.V(1345554384);
                        } else {
                            bwVar2.V(1345603457);
                            View view = (View) bwVar2.j(p4.e);
                            boolean f = bwVar2.f(view);
                            Object L3 = bwVar2.L();
                            if (f || L3 == obj4) {
                                Object tag = view.getTag(R.id.compose_prefetch_scheduler);
                                if (tag instanceof in0) {
                                    L3 = (in0) tag;
                                } else {
                                    L3 = null;
                                }
                                if (L3 == null) {
                                    L3 = new d6(view);
                                    view.setTag(R.id.compose_prefetch_scheduler, L3);
                                }
                                bwVar2.f0(L3);
                            }
                            obj5 = (in0) L3;
                        }
                        bwVar2.p(false);
                        Object obj6 = obj5;
                        bwVar2.p(false);
                        Object[] objArr = {q60Var2, e60Var, hz0Var, obj6};
                        boolean f2 = bwVar2.f(q60Var2) | bwVar2.h(e60Var) | bwVar2.h(hz0Var) | bwVar2.h(obj6);
                        Object L4 = bwVar2.L();
                        if (f2 || L4 == obj4) {
                            Object v6Var = new v6(q60Var2, e60Var, hz0Var, obj6, 3);
                            bwVar2.f0(v6Var);
                            L4 = v6Var;
                        }
                        gv gvVar = (gv) L4;
                        boolean z2 = false;
                        for (Object obj7 : Arrays.copyOf(objArr, 4)) {
                            z2 |= bwVar2.f(obj7);
                        }
                        Object L5 = bwVar2.L();
                        if (z2 || L5 == obj4) {
                            bwVar2.f0(new rn(gvVar));
                        }
                        bwVar2.p(false);
                    } else {
                        bwVar2.V(1744076749);
                        bwVar2.p(false);
                    }
                    int i11 = r60.a;
                    cd0 cd0Var2 = cd0Var;
                    if (q60Var2 != null && (b2 = cd0Var2.b(new x21(q60Var2))) != null) {
                        cd0Var2 = b2;
                    }
                    boolean f3 = bwVar2.f(e60Var);
                    Object obj8 = g70Var;
                    boolean f4 = f3 | bwVar2.f(obj8);
                    Object L6 = bwVar2.L();
                    if (f4 || L6 == obj4) {
                        L6 = new eb(i10, e60Var, obj8);
                        bwVar2.f0(L6);
                    }
                    jc0.e(hz0Var, cd0Var2, (kv) L6, bwVar2, 8);
                    return x31.a;
                }
            }, bwVar), bwVar, 6);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new kv(cd0Var, q60Var, g70Var, i) { // from class: h60
                public final /* synthetic */ cd0 f;
                public final /* synthetic */ q60 g;
                public final /* synthetic */ g70 h;

                @Override // defpackage.kv
                public final Object d(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int O = d20.O(1);
                    m20.a(vu.this, this.f, this.g, this.h, (bw) obj, O);
                    return x31.a;
                }
            };
        }
    }

    public static final void b(final Boolean bool, final Object obj, j80 j80Var, final gv gvVar, bw bwVar, final int i) {
        int i2;
        boolean z;
        int i3;
        int i4;
        int i5;
        bwVar.W(696924721);
        if ((i & 6) == 0) {
            if (bwVar.h(bool)) {
                i5 = 4;
            } else {
                i5 = 2;
            }
            i2 = i5 | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            if (bwVar.h(obj)) {
                i4 = 32;
            } else {
                i4 = 16;
            }
            i2 |= i4;
        }
        if ((i & 384) == 0) {
            i2 |= 128;
        }
        if ((i & 3072) == 0) {
            if (bwVar.h(gvVar)) {
                i3 = 2048;
            } else {
                i3 = 1024;
            }
            i2 |= i3;
        }
        if ((i2 & 1171) != 1170) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i2 & 1, z)) {
            bwVar.S(-127, null, 0, null);
            if ((i & 1) != 0 && !bwVar.y()) {
                bwVar.R();
            } else {
                j80Var = (j80) bwVar.j(oa0.a);
            }
            int i6 = i2 & (-897);
            bwVar.q();
            boolean f = bwVar.f(bool) | bwVar.f(obj) | bwVar.f(j80Var);
            Object L = bwVar.L();
            if (f || L == ph.a) {
                L = new p80(j80Var.f());
                bwVar.f0(L);
            }
            c(j80Var, (p80) L, gvVar, bwVar, (i6 >> 3) & 896);
        } else {
            bwVar.R();
        }
        final j80 j80Var2 = j80Var;
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new kv() { // from class: d80
                @Override // defpackage.kv
                public final Object d(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    m20.b(bool, obj, j80Var2, gvVar, (bw) obj2, d20.O(i | 1));
                    return x31.a;
                }
            };
        }
    }

    public static final void c(j80 j80Var, p80 p80Var, gv gvVar, bw bwVar, int i) {
        int i2;
        boolean z;
        int i3;
        int i4;
        int i5;
        bwVar.W(228371534);
        if ((i & 6) == 0) {
            if (bwVar.h(j80Var)) {
                i5 = 4;
            } else {
                i5 = 2;
            }
            i2 = i5 | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            if (bwVar.h(p80Var)) {
                i4 = 32;
            } else {
                i4 = 16;
            }
            i2 |= i4;
        }
        if ((i & 384) == 0) {
            if (bwVar.h(gvVar)) {
                i3 = 256;
            } else {
                i3 = 128;
            }
            i2 |= i3;
        }
        boolean z2 = false;
        if ((i2 & 147) != 146) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i2 & 1, z)) {
            boolean h = bwVar.h(p80Var);
            if ((i2 & 896) == 256) {
                z2 = true;
            }
            boolean h2 = h | z2 | bwVar.h(j80Var);
            Object L = bwVar.L();
            if (h2 || L == ph.a) {
                L = new zi(j80Var, p80Var, gvVar, 3);
                bwVar.f0(L);
            }
            dl.g(j80Var, p80Var, (gv) L, bwVar);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new fb(j80Var, p80Var, gvVar, i, 4);
        }
    }

    public static t5 d(String str, r11 r11Var, long j, pm pmVar, wt wtVar, int i) {
        er erVar = er.e;
        return new t5(new x5(str, r11Var, erVar, erVar, wtVar, pmVar), i, 1, j);
    }

    public static final gr0 e(float f, float f2, float f3, float f4, long j) {
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (4294967295L & Float.floatToRawIntBits(intBitsToFloat2));
        return new gr0(f, f2, f3, f4, floatToRawIntBits, floatToRawIntBits, floatToRawIntBits, floatToRawIntBits);
    }

    public static final int f(int i, ef0 ef0Var) {
        int i2 = ef0Var.g - 1;
        int i3 = 0;
        while (i3 < i2) {
            int i4 = ((i2 - i3) / 2) + i3;
            Object[] objArr = ef0Var.e;
            int i5 = ((l20) objArr[i4]).a;
            if (i5 != i) {
                if (i5 < i) {
                    i3 = i4 + 1;
                    if (i < ((l20) objArr[i3]).a) {
                    }
                } else {
                    i2 = i4 - 1;
                }
            }
            return i4;
        }
        return i3;
    }

    public static final Object[] g(Object[] objArr, int i, Object obj, Object obj2) {
        Object[] objArr2 = new Object[objArr.length + 2];
        i8.P(objArr, objArr2, 0, i, 6);
        i8.N(objArr, objArr2, i + 2, i, objArr.length);
        objArr2[i] = obj;
        objArr2[i + 1] = obj2;
        return objArr2;
    }

    public static final Object[] h(int i, Object[] objArr) {
        Object[] objArr2 = new Object[objArr.length - 2];
        i8.P(objArr, objArr2, 0, i, 6);
        i8.N(objArr, objArr2, i, i + 2, objArr.length);
        return objArr2;
    }

    public static final Object[] i(int i, Object[] objArr) {
        Object[] objArr2 = new Object[objArr.length - 1];
        i8.P(objArr, objArr2, 0, i, 6);
        i8.N(objArr, objArr2, i, i + 1, objArr.length);
        return objArr2;
    }

    public static final void j(int i, int i2) {
        if (i >= 0 && i < i2) {
            return;
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
    }

    public static void k(Object obj, String str) {
        if (obj != null) {
        } else {
            throw new NullPointerException(str);
        }
    }

    public static final void l(int i, int i2) {
        if (i >= 0 && i <= i2) {
            return;
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
    }

    public static final void m(int i, int i2, int i3) {
        if (i >= 0 && i2 <= i3) {
            if (i <= i2) {
                return;
            }
            v7.m(d3.u("fromIndex: ", i, " > toIndex: ", i2));
        } else {
            throw new IndexOutOfBoundsException("fromIndex: " + i + ", toIndex: " + i2 + ", size: " + i3);
        }
    }

    public static float[] n() {
        return new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    }

    public static final long o() {
        return Thread.currentThread().getId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [i51, java.lang.Object] */
    public static boolean p(View view, KeyEvent keyEvent) {
        ArrayList arrayList;
        int size;
        int indexOfKey;
        int i = j51.a;
        if (Build.VERSION.SDK_INT < 28) {
            ArrayList arrayList2 = i51.d;
            i51 i51Var = (i51) view.getTag(R.id.tag_unhandled_key_event_manager);
            WeakReference weakReference = null;
            i51 i51Var2 = i51Var;
            if (i51Var == null) {
                ?? obj = new Object();
                obj.a = null;
                obj.b = null;
                obj.c = null;
                view.setTag(R.id.tag_unhandled_key_event_manager, obj);
                i51Var2 = obj;
            }
            WeakReference weakReference2 = i51Var2.c;
            if (weakReference2 == null || weakReference2.get() != keyEvent) {
                i51Var2.c = new WeakReference(keyEvent);
                if (i51Var2.b == null) {
                    i51Var2.b = new SparseArray();
                }
                SparseArray sparseArray = i51Var2.b;
                if (keyEvent.getAction() == 1 && (indexOfKey = sparseArray.indexOfKey(keyEvent.getKeyCode())) >= 0) {
                    weakReference = (WeakReference) sparseArray.valueAt(indexOfKey);
                    sparseArray.removeAt(indexOfKey);
                }
                if (weakReference == null) {
                    weakReference = (WeakReference) sparseArray.get(keyEvent.getKeyCode());
                }
                if (weakReference != null) {
                    View view2 = (View) weakReference.get();
                    if (view2 == null || !view2.isAttachedToWindow() || (arrayList = (ArrayList) view2.getTag(R.id.tag_unhandled_key_listeners)) == null || (size = arrayList.size() - 1) < 0) {
                        return true;
                    }
                    arrayList.get(size).getClass();
                    v7.d();
                    return false;
                }
            }
        }
        return false;
    }

    public static final wo0 q(bd0 bd0Var, boolean z, boolean z2) {
        if (!bd0Var.e.r) {
            return wo0.e;
        }
        if (!z) {
            ng0 B = k81.B(bd0Var, 8);
            return o30.n(B).U(B, z2);
        }
        return k81.B(bd0Var, 8).l1();
    }

    public static final int r(int i, List list) {
        int i2;
        char c;
        int i3 = ((yj0) me.X(list)).c;
        if (i > ((yj0) me.X(list)).c) {
            r00.a("Index " + i + " should be less or equal than last line's end " + i3);
        }
        int size = list.size() - 1;
        int i4 = 0;
        while (true) {
            if (i4 <= size) {
                i2 = (i4 + size) >>> 1;
                yj0 yj0Var = (yj0) list.get(i2);
                if (yj0Var.b > i) {
                    c = 1;
                } else if (yj0Var.c <= i) {
                    c = 65535;
                } else {
                    c = 0;
                }
                if (c < 0) {
                    i4 = i2 + 1;
                } else {
                    if (c <= 0) {
                        break;
                    }
                    size = i2 - 1;
                }
            } else {
                i2 = -(i4 + 1);
                break;
            }
        }
        if (i2 >= 0 && i2 < list.size()) {
            return i2;
        }
        r00.a("Found paragraph index " + i2 + " should be in range [0, " + list.size() + ").\nDebug info: index=" + i + ", paragraphs=[" + ma0.a(list, null, new pb(12), 31) + ']');
        return i2;
    }

    public static final int s(int i, List list) {
        char c;
        int size = list.size() - 1;
        int i2 = 0;
        while (i2 <= size) {
            int i3 = (i2 + size) >>> 1;
            yj0 yj0Var = (yj0) list.get(i3);
            if (yj0Var.d > i) {
                c = 1;
            } else if (yj0Var.e <= i) {
                c = 65535;
            } else {
                c = 0;
            }
            if (c < 0) {
                i2 = i3 + 1;
            } else if (c > 0) {
                size = i3 - 1;
            } else {
                return i3;
            }
        }
        return -(i2 + 1);
    }

    public static j1 t(View view) {
        if (Build.VERSION.SDK_INT >= 26) {
            return new j1(ye.b(view));
        }
        return null;
    }

    public static final int u(int i, int i2) {
        return (i >> i2) & 31;
    }

    public static final void v(r40 r40Var) {
        k81.E(r40Var).B();
    }

    public static final void w(qu0 qu0Var) {
        k81.E(qu0Var).C();
    }

    public static final boolean x(gr0 gr0Var) {
        long j = gr0Var.e;
        if ((j >>> 32) == (4294967295L & j) && j == gr0Var.f && j == gr0Var.g && j == gr0Var.h) {
            return true;
        }
        return false;
    }

    public static final long y(float[] fArr, long j) {
        if (fArr.length < 16) {
            return j;
        }
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = fArr[3];
        float f4 = fArr[4];
        float f5 = fArr[5];
        float f6 = fArr[7];
        float f7 = fArr[12];
        float f8 = fArr[13];
        float f9 = fArr[15];
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
        float f10 = 1.0f / (((f6 * intBitsToFloat2) + (f3 * intBitsToFloat)) + f9);
        if ((Float.floatToRawIntBits(f10) & Integer.MAX_VALUE) >= 2139095040) {
            f10 = 0.0f;
        }
        float f11 = ((f5 * intBitsToFloat2) + (f2 * intBitsToFloat) + f8) * f10;
        return (Float.floatToRawIntBits((((f4 * intBitsToFloat2) + (f * intBitsToFloat)) + f7) * f10) << 32) | (Float.floatToRawIntBits(f11) & 4294967295L);
    }

    public static final void z(float[] fArr, ue0 ue0Var) {
        if (fArr.length < 16) {
            return;
        }
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = fArr[3];
        float f4 = fArr[4];
        float f5 = fArr[5];
        float f6 = fArr[7];
        float f7 = fArr[12];
        float f8 = fArr[13];
        float f9 = fArr[15];
        float f10 = ue0Var.a;
        float f11 = ue0Var.b;
        float f12 = ue0Var.c;
        float f13 = ue0Var.d;
        float f14 = f3 * f10;
        float f15 = f6 * f11;
        float f16 = 1.0f / ((f14 + f15) + f9);
        float f17 = 0.0f;
        if ((Float.floatToRawIntBits(f16) & Integer.MAX_VALUE) >= 2139095040) {
            f16 = 0.0f;
        }
        float f18 = f * f10;
        float f19 = f4 * f11;
        float f20 = (f18 + f19 + f7) * f16;
        float f21 = f10 * f2;
        float f22 = f11 * f5;
        float f23 = (f21 + f22 + f8) * f16;
        float f24 = f6 * f13;
        float f25 = 1.0f / ((f14 + f24) + f9);
        if ((Float.floatToRawIntBits(f25) & Integer.MAX_VALUE) >= 2139095040) {
            f25 = 0.0f;
        }
        float f26 = f4 * f13;
        float f27 = (f18 + f26 + f7) * f25;
        float f28 = f5 * f13;
        float f29 = (f21 + f28 + f8) * f25;
        float f30 = f3 * f12;
        float f31 = 1.0f / ((f15 + f30) + f9);
        if ((Float.floatToRawIntBits(f31) & Integer.MAX_VALUE) >= 2139095040) {
            f31 = 0.0f;
        }
        float f32 = f * f12;
        float f33 = (f32 + f19 + f7) * f31;
        float f34 = f12 * f2;
        float f35 = (f22 + f34 + f8) * f31;
        float f36 = 1.0f / ((f30 + f24) + f9);
        if ((Float.floatToRawIntBits(f36) & Integer.MAX_VALUE) < 2139095040) {
            f17 = f36;
        }
        float f37 = (f32 + f26 + f7) * f17;
        float f38 = (f34 + f28 + f8) * f17;
        ue0Var.a = Math.min(f20, Math.min(f27, Math.min(f33, f37)));
        ue0Var.b = Math.min(f23, Math.min(f29, Math.min(f35, f38)));
        ue0Var.c = Math.max(f20, Math.max(f27, Math.max(f33, f37)));
        ue0Var.d = Math.max(f23, Math.max(f29, Math.max(f35, f38)));
    }
}
