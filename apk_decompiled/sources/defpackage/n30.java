package defpackage;

import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.kyant.backdrop.catalog.R;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class n30 {
    public static long a;
    public static Method b;

    public static a01 A(vu vuVar) {
        vuVar.getClass();
        return new a01(vuVar);
    }

    public static ik0 B(Object obj) {
        return new ik0(obj, dt0.g);
    }

    public static final af0 C(zp zpVar, gl glVar, Object obj, vu vuVar, kv kvVar, bw bwVar) {
        glVar.getClass();
        obj.getClass();
        vuVar.getClass();
        kvVar.getClass();
        ((dq0) bwVar.j(eq0.b)).getClass();
        cq0 a2 = dq0.a(bwVar);
        boolean f = bwVar.f(zpVar) | bwVar.f(glVar) | bwVar.f(obj) | bwVar.f(a2);
        Object L = bwVar.L();
        if (f || L == ph.a) {
            L = B(f31.M(cr.e, new d(kvVar, a2, null, 16)));
            bwVar.f0(L);
        }
        return (af0) L;
    }

    public static final af0 D(Object obj, bw bwVar) {
        Object L = bwVar.L();
        if (L == ph.a) {
            L = B(obj);
            bwVar.f0(L);
        }
        af0 af0Var = (af0) L;
        af0Var.setValue(obj);
        return af0Var;
    }

    public static final q41 E(iz izVar, bw bwVar) {
        da daVar;
        mm mmVar = (mm) bwVar.j(fi.h);
        float f = izVar.j;
        float B = mmVar.B();
        boolean e = bwVar.e((Float.floatToRawIntBits(B) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
        Object L = bwVar.L();
        if (e || L == ph.a) {
            sx sxVar = new sx();
            o(sxVar, izVar.f);
            float f2 = izVar.b;
            float f3 = izVar.c;
            float G = mmVar.G(f2);
            float G2 = mmVar.G(f3);
            long floatToRawIntBits = (Float.floatToRawIntBits(G) << 32) | (Float.floatToRawIntBits(G2) & 4294967295L);
            float f4 = izVar.d;
            float f5 = izVar.e;
            if (Float.isNaN(f4)) {
                f4 = Float.intBitsToFloat((int) (floatToRawIntBits >> 32));
            }
            if (Float.isNaN(f5)) {
                f5 = Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L));
            }
            long floatToRawIntBits2 = (Float.floatToRawIntBits(f4) << 32) | (4294967295L & Float.floatToRawIntBits(f5));
            q41 q41Var = new q41(sxVar);
            String str = izVar.a;
            long j = izVar.g;
            int i = izVar.h;
            if (j != 16) {
                daVar = new da(i, j);
            } else {
                daVar = null;
            }
            boolean z = izVar.i;
            q41Var.e.setValue(new mw0(floatToRawIntBits));
            q41Var.f.setValue(Boolean.valueOf(z));
            l41 l41Var = q41Var.g;
            l41Var.g.setValue(daVar);
            l41Var.i.setValue(new mw0(floatToRawIntBits2));
            l41Var.c = str;
            bwVar.f0(q41Var);
            L = q41Var;
        }
        return (q41) L;
    }

    public static final String F(Object obj) {
        String simpleName;
        if (obj.getClass().isAnonymousClass()) {
            simpleName = obj.getClass().getName();
        } else {
            simpleName = obj.getClass().getSimpleName();
        }
        return simpleName + '@' + String.format("%07x", Arrays.copyOf(new Object[]{Integer.valueOf(System.identityHashCode(obj))}, 1));
    }

    public static final j2 G(vu vuVar) {
        return new j2(21, new m2(vuVar, null));
    }

    public static w10 H(y10 y10Var) {
        int i;
        y10Var.getClass();
        int i2 = y10Var.e;
        int i3 = y10Var.f;
        if (y10Var.g > 0) {
            i = 2;
        } else {
            i = -2;
        }
        return new w10(i2, i3, i);
    }

    public static String I(long j) {
        return "PointerId(value=" + j + ')';
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001f, code lost:
    
        if (r4 == '+') goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final int J(java.lang.String r11) {
        /*
            r0 = 16
            defpackage.k81.m(r0)
            int r1 = r11.length()
            r2 = 0
            if (r1 != 0) goto Ld
            goto L65
        Ld:
            r3 = 0
            char r4 = r11.charAt(r3)
            r5 = 48
            int r5 = defpackage.o20.i(r4, r5)
            if (r5 >= 0) goto L22
            r5 = 1
            if (r1 == r5) goto L65
            r6 = 43
            if (r4 == r6) goto L23
            goto L65
        L22:
            r5 = r3
        L23:
            r4 = 119304647(0x71c71c7, float:1.1769572E-34)
            r6 = r4
        L27:
            if (r5 >= r1) goto L60
            char r7 = r11.charAt(r5)
            int r7 = java.lang.Character.digit(r7, r0)
            if (r7 >= 0) goto L34
            goto L65
        L34:
            r8 = -2147483648(0xffffffff80000000, float:-0.0)
            r9 = r3 ^ r8
            r10 = r6 ^ r8
            int r10 = java.lang.Integer.compare(r9, r10)
            if (r10 <= 0) goto L4f
            if (r6 != r4) goto L65
            r6 = -1879048193(0xffffffff8fffffff, float:-2.5243547E-29)
            int r6 = java.lang.Integer.compare(r9, r6)
            if (r6 <= 0) goto L4c
            goto L65
        L4c:
            r6 = 268435455(0xfffffff, float:2.5243547E-29)
        L4f:
            int r3 = r3 * 16
            int r7 = r7 + r3
            r9 = r7 ^ r8
            r3 = r3 ^ r8
            int r3 = java.lang.Integer.compare(r9, r3)
            if (r3 >= 0) goto L5c
            goto L65
        L5c:
            int r5 = r5 + 1
            r3 = r7
            goto L27
        L60:
            t31 r2 = new t31
            r2.<init>(r3)
        L65:
            if (r2 == 0) goto L6a
            int r11 = r2.e
            return r11
        L6a:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Invalid number format: '"
            r1.<init>(r2)
            r1.append(r11)
            r11 = 39
            r1.append(r11)
            java.lang.String r11 = r1.toString()
            r0.<init>(r11)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.n30.J(java.lang.String):int");
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [w10, y10] */
    public static y10 K(int i, int i2) {
        if (i2 <= Integer.MIN_VALUE) {
            y10 y10Var = y10.h;
            return y10.h;
        }
        return new w10(i, i2 - 1, 1);
    }

    public static final void a(bw bwVar, int i) {
        boolean z;
        bwVar.W(-2048788304);
        boolean z2 = false;
        if (i != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i & 1, z)) {
            f31.b(null, ng.c, bwVar, 48, 1);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new yu0(i, 9, z2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0094  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void b(defpackage.vu r19, defpackage.gv r20, defpackage.he r21, float r22, defpackage.m9 r23, defpackage.cd0 r24, defpackage.bw r25, int r26, int r27) {
        /*
            Method dump skipped, instructions count: 288
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.n30.b(vu, gv, he, float, m9, cd0, bw, int, int):void");
    }

    public static final void c(bw bwVar, int i) {
        boolean z;
        long j;
        br0 br0Var;
        bwVar.W(-387943243);
        boolean z2 = false;
        if (i != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i & 1, z)) {
            boolean D = n20.D(bwVar);
            gi giVar = wz.a;
            if (!D) {
                j = se.b;
            } else {
                j = se.c;
            }
            br0 br0Var2 = zq0.a;
            if (eo.a(Float.NaN, Float.NaN) && se.c(j, se.g)) {
                br0Var = zq0.a;
            } else {
                br0Var = new br0(j, true);
            }
            o20.a(giVar.a(br0Var), rg.a, bwVar, 56);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new yu0(i, 12, z2);
        }
    }

    public static final long d(int i, int i2) {
        if (i < 0 || i2 < 0) {
            r00.a("start and end cannot be negative. [start: " + i + ", end: " + i2 + ']');
        }
        long j = (i2 & 4294967295L) | (i << 32);
        int i3 = m11.c;
        return j;
    }

    public static final bd0 e(im imVar, int i) {
        bd0 bd0Var = ((bd0) imVar).e.j;
        if (bd0Var != null && (bd0Var.h & i) != 0) {
            while (bd0Var != null) {
                int i2 = bd0Var.g;
                if ((i2 & 2) == 0) {
                    if ((i2 & i) != 0) {
                        return bd0Var;
                    }
                    bd0Var = bd0Var.j;
                } else {
                    return null;
                }
            }
            return null;
        }
        return null;
    }

    public static void f(String str) {
        if (str.length() > 127) {
            str = str.substring(0, 127);
        }
        Trace.beginSection(str);
    }

    public static final void g(uc ucVar, g30 g30Var, y5 y5Var) {
        g30Var.getClass();
        if (g30Var instanceof gj0) {
            ucVar.k(((gj0) g30Var).a);
            return;
        }
        if (g30Var instanceof hj0) {
            y5Var.getClass();
            y5Var.a.rewind();
            d3.l(y5Var, ((hj0) g30Var).a);
            ucVar.q(y5Var);
            return;
        }
        if (g30Var instanceof fj0) {
            ucVar.q(((fj0) g30Var).a);
        } else {
            v7.k();
        }
    }

    public static double h(double d, double d2, double d3) {
        if (d2 <= d3) {
            if (d < d2) {
                return d2;
            }
            if (d > d3) {
                return d3;
            }
            return d;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + d3 + " is less than minimum " + d2 + '.');
    }

    public static float i(float f, float f2, float f3) {
        if (f2 <= f3) {
            if (f < f2) {
                return f2;
            }
            if (f > f3) {
                return f3;
            }
            return f;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + f3 + " is less than minimum " + f2 + '.');
    }

    public static int j(int i, int i2, int i3) {
        if (i2 <= i3) {
            if (i < i2) {
                return i2;
            }
            if (i > i3) {
                return i3;
            }
            return i;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + i3 + " is less than minimum " + i2 + '.');
    }

    public static long k(long j) {
        if (j < -4611686018427387903L) {
            return -4611686018427387903L;
        }
        if (j > 4611686018427387903L) {
            return 4611686018427387903L;
        }
        return j;
    }

    public static Comparable l(Float f, he heVar) {
        float f2 = heVar.e;
        Float valueOf = Float.valueOf(0.0f);
        if (!heVar.isEmpty()) {
            if (he.c(f, valueOf) && !he.c(valueOf, f)) {
                return valueOf;
            }
            if (he.c(Float.valueOf(f2), f) && !he.c(f, Float.valueOf(f2))) {
                return Float.valueOf(f2);
            }
            return f;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: " + heVar + '.');
    }

    public static Comparable m(Float f, ie ieVar) {
        if (ieVar instanceof he) {
            return l(f, (he) ieVar);
        }
        if (!ieVar.isEmpty()) {
            if (f.compareTo((Float) ieVar.a()) < 0) {
                return ieVar.a();
            }
            if (f.compareTo((Float) ieVar.b()) > 0) {
                return ieVar.b();
            }
            return f;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: " + ieVar + '.');
    }

    public static StaticLayout n(CharSequence charSequence, TextPaint textPaint, int i, int i2, TextDirectionHeuristic textDirectionHeuristic, Layout.Alignment alignment, int i3, TextUtils.TruncateAt truncateAt, int i4, int i5, boolean z, int i6, int i7, int i8, int i9) {
        if (i2 < 0) {
            r00.a("invalid start value");
        }
        int length = charSequence.length();
        if (i2 < 0 || i2 > length) {
            r00.a("invalid end value");
        }
        if (i3 < 0) {
            r00.a("invalid maxLines value");
        }
        if (i < 0) {
            r00.a("invalid width value");
        }
        if (i4 < 0) {
            r00.a("invalid ellipsizedWidth value");
        }
        StaticLayout.Builder obtain = StaticLayout.Builder.obtain(charSequence, 0, i2, textPaint, i);
        obtain.setTextDirection(textDirectionHeuristic);
        obtain.setAlignment(alignment);
        obtain.setMaxLines(i3);
        obtain.setEllipsize(truncateAt);
        obtain.setEllipsizedWidth(i4);
        obtain.setLineSpacing(0.0f, 1.0f);
        obtain.setIncludePad(z);
        obtain.setBreakStrategy(i6);
        obtain.setHyphenationFrequency(i9);
        obtain.setIndents(null, null);
        int i10 = Build.VERSION.SDK_INT;
        if (i10 >= 26) {
            obtain.setJustificationMode(i5);
        }
        if (i10 >= 28) {
            obtain.setUseLineSpacingFromFallbacks(true);
        }
        if (i10 >= 33) {
            d1.o(obtain, d1.e(d1.t(d1.d(d1.c(), i7), i8)));
        }
        if (i10 >= 35) {
            obtain.setUseBoundsForWidth(false);
        }
        return obtain.build();
    }

    public static final void o(sx sxVar, n41 n41Var) {
        Path.FillType fillType;
        List list = n41Var.n;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            p41 p41Var = (p41) list.get(i);
            if (p41Var instanceof r41) {
                lk0 lk0Var = new lk0();
                r41 r41Var = (r41) p41Var;
                lk0Var.d = r41Var.f;
                lk0Var.n = true;
                lk0Var.c();
                int i2 = r41Var.g;
                Path path = lk0Var.s.a;
                if (i2 == 1) {
                    fillType = Path.FillType.EVEN_ODD;
                } else {
                    fillType = Path.FillType.WINDING;
                }
                path.setFillType(fillType);
                lk0Var.c();
                lk0Var.c();
                lk0Var.b = r41Var.h;
                lk0Var.c();
                lk0Var.c = r41Var.i;
                lk0Var.c();
                lk0Var.g = r41Var.j;
                lk0Var.c();
                lk0Var.e = r41Var.k;
                lk0Var.c();
                lk0Var.f = r41Var.l;
                lk0Var.o = true;
                lk0Var.c();
                lk0Var.h = r41Var.m;
                lk0Var.o = true;
                lk0Var.c();
                lk0Var.i = r41Var.n;
                lk0Var.o = true;
                lk0Var.c();
                lk0Var.j = r41Var.o;
                lk0Var.o = true;
                lk0Var.c();
                lk0Var.k = r41Var.p;
                lk0Var.p = true;
                lk0Var.c();
                lk0Var.l = r41Var.q;
                lk0Var.p = true;
                lk0Var.c();
                lk0Var.m = r41Var.r;
                lk0Var.p = true;
                lk0Var.c();
                sxVar.e(i, lk0Var);
            } else if (p41Var instanceof n41) {
                sx sxVar2 = new sx();
                n41 n41Var2 = (n41) p41Var;
                sxVar2.k = n41Var2.e;
                sxVar2.c();
                sxVar2.l = n41Var2.f;
                sxVar2.s = true;
                sxVar2.c();
                sxVar2.o = n41Var2.i;
                sxVar2.s = true;
                sxVar2.c();
                sxVar2.p = n41Var2.j;
                sxVar2.s = true;
                sxVar2.c();
                sxVar2.q = n41Var2.k;
                sxVar2.s = true;
                sxVar2.c();
                sxVar2.r = n41Var2.l;
                sxVar2.s = true;
                sxVar2.c();
                sxVar2.m = n41Var2.g;
                sxVar2.s = true;
                sxVar2.c();
                sxVar2.n = n41Var2.h;
                sxVar2.s = true;
                sxVar2.c();
                sxVar2.f = n41Var2.m;
                sxVar2.g = true;
                sxVar2.c();
                o(sxVar2, n41Var2);
                sxVar.e(i, sxVar2);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [is0, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v5, types: [is0, java.lang.Object] */
    public static is0 p(Bundle bundle, Bundle bundle2) {
        if (bundle == null) {
            bundle = bundle2;
        }
        if (bundle == null) {
            ?? obj = new Object();
            new LinkedHashMap();
            obj.a = new a9(fr.e);
            return obj;
        }
        ClassLoader classLoader = is0.class.getClassLoader();
        classLoader.getClass();
        bundle.setClassLoader(classLoader);
        ec0 ec0Var = new ec0(bundle.size());
        for (String str : bundle.keySet()) {
            str.getClass();
            ec0Var.put(str, bundle.get(str));
        }
        ec0Var.b();
        ec0Var.q = true;
        if (ec0Var.m <= 0) {
            ec0Var = ec0.r;
            ec0Var.getClass();
        }
        ?? obj2 = new Object();
        new LinkedHashMap();
        obj2.a = new a9(ec0Var);
        return obj2;
    }

    public static final ef0 q() {
        r7 r7Var = jx0.b;
        ef0 ef0Var = (ef0) r7Var.p();
        if (ef0Var == null) {
            ef0 ef0Var2 = new ef0(new aw[0]);
            r7Var.C(ef0Var2);
            return ef0Var2;
        }
        return ef0Var;
    }

    public static final ym r(vu vuVar) {
        r7 r7Var = jx0.a;
        return new ym(vuVar, null);
    }

    public static final boolean s(long j, long j2) {
        if (j == j2) {
            return true;
        }
        return false;
    }

    public static final ps0 t(View view) {
        ps0 ps0Var;
        while (view != null) {
            Object tag = view.getTag(R.id.view_tree_saved_state_registry_owner);
            if (tag instanceof ps0) {
                ps0Var = (ps0) tag;
            } else {
                ps0Var = null;
            }
            if (ps0Var != null) {
                return ps0Var;
            }
            Object j = y20.j(view);
            if (j instanceof View) {
                view = (View) j;
            } else {
                view = null;
            }
        }
        return null;
    }

    public static final ku0 u(Object obj) {
        if (obj != k81.a) {
            return (ku0) obj;
        }
        v7.o("Does not contain segment");
        return null;
    }

    public static final boolean v(Object obj) {
        if (obj == k81.a) {
            return true;
        }
        return false;
    }

    public static boolean w() {
        if (Build.VERSION.SDK_INT >= 29) {
            return k21.a();
        }
        try {
            if (b == null) {
                a = Trace.class.getField("TRACE_TAG_APP").getLong(null);
                b = Trace.class.getMethod("isTagEnabled", Long.TYPE);
            }
            return ((Boolean) b.invoke(null, Long.valueOf(a))).booleanValue();
        } catch (Exception e) {
            if (e instanceof InvocationTargetException) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                throw new RuntimeException(cause);
            }
            Log.v("Trace", "Unable to call isTagEnabled via reflection", e);
            return false;
        }
    }

    public static final boolean x(float f, float f2, y5 y5Var) {
        float f3 = f - 0.005f;
        float f4 = f2 - 0.005f;
        float f5 = f + 0.005f;
        float f6 = f2 + 0.005f;
        y5 a2 = a6.a();
        Path path = a2.a;
        if (Float.isNaN(f3) || Float.isNaN(f4) || Float.isNaN(f5) || Float.isNaN(f6)) {
            a6.b("Invalid rectangle, make sure no value is NaN");
        }
        if (a2.b == null) {
            a2.b = new RectF();
        }
        RectF rectF = a2.b;
        rectF.getClass();
        rectF.set(f3, f4, f5, f6);
        RectF rectF2 = a2.b;
        rectF2.getClass();
        path.addRect(rectF2, Path.Direction.CCW);
        Path path2 = a6.a().a;
        Path.Op op = Path.Op.INTERSECT;
        if (y5Var instanceof y5) {
            Path path3 = y5Var.a;
            if (d3.A(a2)) {
                path2.op(path3, path, op);
                boolean isEmpty = path2.isEmpty();
                path2.reset();
                path.reset();
                return !isEmpty;
            }
            throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
        }
        throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
    }

    public static final boolean y(float f, float f2, float f3, float f4, long j) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
        if (((f6 * f6) / (intBitsToFloat2 * intBitsToFloat2)) + ((f5 * f5) / (intBitsToFloat * intBitsToFloat)) <= 1.0f) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [c41, java.lang.Object, q50] */
    public static q50 z(vu vuVar) {
        dt0 dt0Var = dt0.h;
        ?? obj = new Object();
        obj.e = vuVar;
        obj.f = dt0Var;
        return obj;
    }
}
