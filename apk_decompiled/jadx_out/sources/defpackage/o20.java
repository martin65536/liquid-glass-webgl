package defpackage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.Trace;
import android.text.TextPaint;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.translation.TranslationResponseValue;
import android.view.translation.ViewTranslationResponse;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class o20 {
    public static final wq e;
    public static final wq f;
    public static final wq g;
    public static final wq h;
    public static final wq i;
    public static final wq j;
    public static final wq k;
    public static final wq q;
    public static final wq r;
    public static Method t;
    public static Method u;
    public static boolean v;
    public static final dt0 a = new dt0(15);
    public static final dt0 b = new dt0(16);
    public static final Object[] c = new Object[0];
    public static final Class[] d = {Serializable.class, Parcelable.class, String.class, SparseArray.class, Binder.class, Size.class, SizeF.class};
    public static final zq l = new zq(false);
    public static final zq m = new zq(true);
    public static final StackTraceElement[] n = new StackTraceElement[0];
    public static final uy o = new uy(2);
    public static final wo0 p = new wo0(0.0f, 0.0f, 10.0f, 10.0f);
    public static final es s = new Object();

    /* JADX WARN: Type inference failed for: r0v8, types: [java.lang.Object, es] */
    static {
        int i2 = 1;
        e = new wq("REMOVED_TASK", i2);
        f = new wq("CLOSED_EMPTY", i2);
        g = new wq("COMPLETING_ALREADY", i2);
        h = new wq("COMPLETING_WAITING_CHILDREN", i2);
        i = new wq("COMPLETING_RETRY", i2);
        j = new wq("TOO_LATE_TO_CANCEL", i2);
        k = new wq("SEALED", i2);
        q = new wq("NONE", i2);
        r = new wq("PENDING", i2);
    }

    public static void A(RuntimeException runtimeException, String str) {
        StackTraceElement[] stackTrace = runtimeException.getStackTrace();
        int length = stackTrace.length;
        int i2 = -1;
        for (int i3 = 0; i3 < length; i3++) {
            if (str.equals(stackTrace[i3].getClassName())) {
                i2 = i3;
            }
        }
        runtimeException.setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace, i2 + 1, length));
    }

    /* JADX WARN: Type inference failed for: r5v4, types: [ep0, java.lang.Object] */
    public static final Object B(pt ptVar, int i2, gv gvVar) {
        int i3;
        int i4;
        Object obj;
        bd0 bd0Var;
        y50 H0;
        int max;
        long f2;
        int size;
        int i5;
        lg0 lg0Var;
        if (!ptVar.e.r) {
            q00.b("visitAncestors called on an unattached node");
        }
        bd0 bd0Var2 = ptVar.e.i;
        z40 E = k81.E(ptVar);
        loop0: while (true) {
            i3 = 0;
            i4 = 1;
            obj = null;
            if (E != null) {
                if ((E.H.f.h & 1024) != 0) {
                    while (bd0Var2 != null) {
                        if ((bd0Var2.g & 1024) != 0) {
                            bd0Var = bd0Var2;
                            ef0 ef0Var = null;
                            while (bd0Var != null) {
                                if (bd0Var instanceof pt) {
                                    break loop0;
                                }
                                if ((bd0Var.g & 1024) != 0 && (bd0Var instanceof jm)) {
                                    int i6 = 0;
                                    for (bd0 bd0Var3 = ((jm) bd0Var).t; bd0Var3 != null; bd0Var3 = bd0Var3.j) {
                                        if ((bd0Var3.g & 1024) != 0) {
                                            i6++;
                                            if (i6 == 1) {
                                                bd0Var = bd0Var3;
                                            } else {
                                                if (ef0Var == null) {
                                                    ef0Var = new ef0(new bd0[16]);
                                                }
                                                if (bd0Var != null) {
                                                    ef0Var.b(bd0Var);
                                                    bd0Var = null;
                                                }
                                                ef0Var.b(bd0Var3);
                                            }
                                        }
                                    }
                                    if (i6 == 1) {
                                    }
                                }
                                bd0Var = k81.h(ef0Var);
                            }
                        }
                        bd0Var2 = bd0Var2.i;
                    }
                }
                E = E.s();
                if (E != null && (lg0Var = E.H) != null) {
                    bd0Var2 = lg0Var.e;
                } else {
                    bd0Var2 = null;
                }
            } else {
                bd0Var = null;
                break;
            }
        }
        pt ptVar2 = (pt) bd0Var;
        if ((ptVar2 == null || !e(ptVar2.H0(), ptVar.H0())) && (H0 = ptVar.H0()) != null) {
            int i7 = 5;
            if (i2 != 5) {
                i7 = 6;
                if (i2 != 6) {
                    i7 = 3;
                    if (i2 != 3) {
                        i7 = 4;
                        if (i2 != 4) {
                            if (i2 == 1) {
                                i7 = 2;
                            } else if (i2 == 2) {
                                i7 = 1;
                            } else {
                                v7.o("Unsupported direction for beyond bounds layout");
                            }
                        }
                    }
                }
            }
            if (H0.s.a.g().n > 0 && !H0.s.a.g().k.isEmpty() && H0.r) {
                boolean E0 = H0.E0(i7);
                y60 y60Var = H0.s;
                if (E0) {
                    max = Math.min(y60Var.a.g().n - 1, ((i70) me.X(y60Var.a.g().k)).a);
                } else {
                    max = Math.max(0, ((fk0) y60Var.a.e.b).g());
                }
                ?? obj2 = new Object();
                ib ibVar = H0.t;
                ibVar.getClass();
                u50 u50Var = new u50(max, max);
                ibVar.a.b(u50Var);
                obj2.e = u50Var;
                m70 m70Var = H0.s.a;
                if (m70Var.g().k.isEmpty()) {
                    i4 = 0;
                } else {
                    h70 g2 = m70Var.g();
                    if (g2.o == dj0.e) {
                        f2 = g2.f() & 4294967295L;
                    } else {
                        f2 = g2.f() >> 32;
                    }
                    int i8 = (int) f2;
                    h70 g3 = m70Var.g();
                    List list = g3.k;
                    if (list.isEmpty()) {
                        size = 0;
                    } else {
                        int size2 = list.size();
                        int i9 = 0;
                        for (int i10 = 0; i10 < size2; i10++) {
                            i9 += ((i70) list.get(i10)).k;
                        }
                        size = (i9 / list.size()) + g3.q;
                    }
                    if (size != 0 && (i5 = i8 / size) >= 1) {
                        i4 = i5;
                    }
                }
                int i11 = i4 * 2;
                int i12 = H0.s.a.g().n;
                if (i11 > i12) {
                    i11 = i12;
                }
                while (obj == null && H0.D0((u50) obj2.e, i7) && i3 < i11) {
                    u50 u50Var2 = (u50) obj2.e;
                    int i13 = u50Var2.a;
                    int i14 = u50Var2.b;
                    if (H0.E0(i7)) {
                        i14++;
                    } else {
                        i13--;
                    }
                    ib ibVar2 = H0.t;
                    ibVar2.getClass();
                    u50 u50Var3 = new u50(i13, i14);
                    ibVar2.a.b(u50Var3);
                    H0.t.a.j((u50) obj2.e);
                    obj2.e = u50Var3;
                    i3++;
                    k81.E(H0).k();
                    obj = gvVar.e(new x50(H0, obj2, i7));
                }
                H0.t.a.j((u50) obj2.e);
                k81.E(H0).k();
                return obj;
            }
            return gvVar.e(y50.v);
        }
        return null;
    }

    public static final void C(TextPaint textPaint, float f2) {
        if (!Float.isNaN(f2)) {
            if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            if (f2 > 1.0f) {
                f2 = 1.0f;
            }
            textPaint.setAlpha(Math.round(f2 * 255.0f));
        }
    }

    public static final void D(k1 k1Var, su0 su0Var) {
        int size;
        AccessibilityNodeInfo accessibilityNodeInfo = k1Var.a;
        Object g2 = su0Var.k().e.g(wu0.f);
        Object obj = null;
        if (g2 == null) {
            g2 = null;
        }
        le leVar = (le) g2;
        if (leVar != null) {
            accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(leVar.a, leVar.b, false, 0));
            return;
        }
        ArrayList arrayList = new ArrayList();
        Object g3 = su0Var.k().e.g(wu0.e);
        if (g3 != null) {
            obj = g3;
        }
        if (obj != null) {
            List j2 = su0.j(4, su0Var);
            int size2 = j2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                su0 su0Var2 = (su0) j2.get(i2);
                if (su0Var2.k().e.c(wu0.F)) {
                    arrayList.add(su0Var2);
                }
            }
        }
        if (!arrayList.isEmpty()) {
            boolean g4 = g(arrayList);
            int i3 = 1;
            if (g4) {
                size = 1;
            } else {
                size = arrayList.size();
            }
            if (g4) {
                i3 = arrayList.size();
            }
            accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(size, i3, false, 0));
        }
    }

    public static final void E(k1 k1Var, su0 su0Var) {
        int i2;
        int i3;
        Object g2 = su0Var.k().e.g(wu0.g);
        Object obj = null;
        if (g2 == null) {
            g2 = null;
        }
        if (g2 == null) {
            su0 l2 = su0Var.l();
            if (l2 != null) {
                Object g3 = l2.k().e.g(wu0.e);
                if (g3 == null) {
                    g3 = null;
                }
                if (g3 != null) {
                    Object g4 = l2.k().e.g(wu0.f);
                    if (g4 != null) {
                        obj = g4;
                    }
                    le leVar = (le) obj;
                    if (leVar == null || (leVar.a >= 0 && leVar.b >= 0)) {
                        if (su0Var.k().e.c(wu0.F)) {
                            ArrayList arrayList = new ArrayList();
                            List j2 = su0.j(4, l2);
                            int size = j2.size();
                            int i4 = 0;
                            for (int i5 = 0; i5 < size; i5++) {
                                su0 su0Var2 = (su0) j2.get(i5);
                                if (su0Var2.k().e.c(wu0.F)) {
                                    arrayList.add(su0Var2);
                                    if (su0Var2.c.t() < su0Var.c.t()) {
                                        i4++;
                                    }
                                }
                            }
                            if (!arrayList.isEmpty()) {
                                boolean g5 = g(arrayList);
                                if (g5) {
                                    i2 = 0;
                                } else {
                                    i2 = i4;
                                }
                                if (g5) {
                                    i3 = i4;
                                } else {
                                    i3 = 0;
                                }
                                Object g6 = su0Var.k().e.g(wu0.F);
                                if (g6 == null) {
                                    g6 = Boolean.FALSE;
                                }
                                k1Var.a.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(i2, 1, i3, 1, false, ((Boolean) g6).booleanValue()));
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        v7.d();
    }

    public static x7 F(float f2) {
        return new x7(f2, true, new v7(0));
    }

    public static void G(String str) {
        RuntimeException runtimeException = new RuntimeException("lateinit property " + str + " has not been initialized");
        A(runtimeException, o20.class.getName());
        throw runtimeException;
    }

    public static final Object[] H(Collection collection) {
        collection.getClass();
        int size = collection.size();
        Object[] objArr = c;
        if (size == 0) {
            return objArr;
        }
        Iterator it = collection.iterator();
        if (!it.hasNext()) {
            return objArr;
        }
        Object[] objArr2 = new Object[size];
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            objArr2[i2] = it.next();
            if (i3 >= objArr2.length) {
                if (!it.hasNext()) {
                    return objArr2;
                }
                int i4 = ((i3 * 3) + 1) >>> 1;
                if (i4 <= i3) {
                    i4 = 2147483645;
                    if (i3 >= 2147483645) {
                        throw new OutOfMemoryError();
                    }
                }
                objArr2 = Arrays.copyOf(objArr2, i4);
            } else if (!it.hasNext()) {
                return Arrays.copyOf(objArr2, i3);
            }
            i2 = i3;
        }
    }

    public static final Object[] I(Collection collection, Object[] objArr) {
        Object[] objArr2;
        collection.getClass();
        objArr.getClass();
        int size = collection.size();
        int i2 = 0;
        if (size == 0) {
            if (objArr.length > 0) {
                objArr[0] = null;
                return objArr;
            }
        } else {
            Iterator it = collection.iterator();
            if (!it.hasNext()) {
                if (objArr.length > 0) {
                    objArr[0] = null;
                }
            } else {
                if (size <= objArr.length) {
                    objArr2 = objArr;
                } else {
                    Object newInstance = Array.newInstance(objArr.getClass().getComponentType(), size);
                    newInstance.getClass();
                    objArr2 = (Object[]) newInstance;
                }
                while (true) {
                    int i3 = i2 + 1;
                    objArr2[i2] = it.next();
                    if (i3 >= objArr2.length) {
                        if (!it.hasNext()) {
                            return objArr2;
                        }
                        int i4 = ((i3 * 3) + 1) >>> 1;
                        if (i4 <= i3) {
                            i4 = 2147483645;
                            if (i3 >= 2147483645) {
                                throw new OutOfMemoryError();
                            }
                        }
                        objArr2 = Arrays.copyOf(objArr2, i4);
                    } else if (!it.hasNext()) {
                        if (objArr2 == objArr) {
                            objArr[i3] = null;
                            return objArr;
                        }
                        return Arrays.copyOf(objArr2, i3);
                    }
                    i2 = i3;
                }
            }
        }
        return objArr;
    }

    public static String J(long j2) {
        int i2 = (int) (j2 >> 32);
        int i3 = (int) (j2 & 4294967295L);
        if (Float.intBitsToFloat(i2) == Float.intBitsToFloat(i3)) {
            return "CornerRadius.circular(" + o4.Z(Float.intBitsToFloat(i2)) + ')';
        }
        return "CornerRadius.elliptical(" + o4.Z(Float.intBitsToFloat(i2)) + ", " + o4.Z(Float.intBitsToFloat(i3)) + ')';
    }

    public static final Object K(Object obj) {
        tz tzVar;
        sz szVar;
        if (obj instanceof tz) {
            tzVar = (tz) obj;
        } else {
            tzVar = null;
        }
        if (tzVar != null && (szVar = tzVar.a) != null) {
            return szVar;
        }
        return obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void a(defpackage.eo0 r11, defpackage.gg r12, defpackage.bw r13, int r14) {
        /*
            r0 = -149765515(0xfffffffff712c275, float:-2.9766383E33)
            r13.W(r0)
            e20 r0 = r13.x
            ll0 r1 = r13.l()
            r2 = 201(0xc9, float:2.82E-43)
            rh0 r3 = defpackage.rh.b
            r13.T(r2, r3)
            java.lang.Object r2 = r13.L()
            dt0 r3 = defpackage.ph.a
            boolean r3 = e(r2, r3)
            r4 = 0
            if (r3 == 0) goto L22
            r2 = r4
            goto L27
        L22:
            r2.getClass()
            i41 r2 = (defpackage.i41) r2
        L27:
            do0 r3 = r11.a
            i41 r5 = r3.c(r11, r2)
            boolean r2 = r5.equals(r2)
            if (r2 != 0) goto L36
            r13.f0(r5)
        L36:
            boolean r6 = r13.S
            r7 = 1
            r8 = 0
            if (r6 == 0) goto L4e
            boolean r2 = r11.f
            if (r2 != 0) goto L46
            boolean r2 = r1.containsKey(r3)
            if (r2 != 0) goto L4a
        L46:
            ll0 r1 = r1.b(r3, r5)
        L4a:
            r13.J = r7
        L4c:
            r2 = r8
            goto L89
        L4e:
            qw0 r6 = r13.G
            int r9 = r6.g
            int[] r10 = r6.b
            java.lang.Object r6 = r6.b(r10, r9)
            r6.getClass()
            ll0 r6 = (defpackage.ll0) r6
            boolean r9 = r13.A()
            if (r9 == 0) goto L65
            if (r2 != 0) goto L70
        L65:
            boolean r9 = r11.f
            if (r9 != 0) goto L7e
            boolean r9 = r1.containsKey(r3)
            if (r9 != 0) goto L70
            goto L7e
        L70:
            if (r2 == 0) goto L77
            boolean r2 = r13.w
            if (r2 != 0) goto L77
            goto L7c
        L77:
            boolean r2 = r13.w
            if (r2 == 0) goto L7c
            goto L82
        L7c:
            r1 = r6
            goto L82
        L7e:
            ll0 r1 = r1.b(r3, r5)
        L82:
            boolean r2 = r13.y
            if (r2 != 0) goto L88
            if (r6 == r1) goto L4c
        L88:
            r2 = r7
        L89:
            if (r2 == 0) goto L92
            boolean r3 = r13.S
            if (r3 != 0) goto L92
            r13.J(r1)
        L92:
            boolean r3 = r13.w
            r0.c(r3)
            r13.w = r2
            r13.K = r1
            r2 = 202(0xca, float:2.83E-43)
            rh0 r3 = defpackage.rh.c
            r13.S(r2, r3, r8, r1)
            int r1 = r14 >> 3
            r1 = r1 & 14
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r12.d(r13, r1)
            r13.p(r8)
            r13.p(r8)
            int r0 = r0.b()
            if (r0 == 0) goto Lba
            goto Lbb
        Lba:
            r7 = r8
        Lbb:
            r13.w = r7
            r13.K = r4
            mo0 r13 = r13.r()
            if (r13 == 0) goto Lcc
            eg r0 = new eg
            r0.<init>(r11, r12, r14)
            r13.d = r0
        Lcc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.o20.a(eo0, gg, bw, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void b(defpackage.eo0[] r8, defpackage.kv r9, defpackage.bw r10, int r11) {
        /*
            r0 = 415205898(0x18bf8a0a, float:4.9511727E-24)
            r10.W(r0)
            e20 r0 = r10.x
            ll0 r1 = r10.l()
            r2 = 201(0xc9, float:2.82E-43)
            rh0 r3 = defpackage.rh.b
            r10.T(r2, r3)
            boolean r2 = r10.S
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L27
            ll0 r2 = defpackage.ll0.h
            ll0 r2 = defpackage.jc0.J(r8, r1, r2)
            ll0 r1 = r10.e0(r1, r2)
            r10.J = r3
        L25:
            r2 = r4
            goto L72
        L27:
            qw0 r2 = r10.G
            int r5 = r2.g
            java.lang.Object r2 = r2.h(r5, r4)
            r2.getClass()
            ll0 r2 = (defpackage.ll0) r2
            qw0 r5 = r10.G
            int r6 = r5.g
            java.lang.Object r5 = r5.h(r6, r3)
            r5.getClass()
            ll0 r5 = (defpackage.ll0) r5
            ll0 r6 = defpackage.jc0.J(r8, r1, r5)
            boolean r7 = r10.A()
            if (r7 == 0) goto L63
            boolean r7 = r10.y
            if (r7 != 0) goto L63
            boolean r5 = r5.equals(r6)
            if (r5 != 0) goto L56
            goto L63
        L56:
            int r1 = r10.l
            qw0 r5 = r10.G
            int r5 = r5.s()
            int r5 = r5 + r1
            r10.l = r5
            r1 = r2
            goto L25
        L63:
            ll0 r1 = r10.e0(r1, r6)
            boolean r5 = r10.y
            if (r5 != 0) goto L71
            boolean r2 = e(r1, r2)
            if (r2 != 0) goto L25
        L71:
            r2 = r3
        L72:
            if (r2 == 0) goto L7b
            boolean r5 = r10.S
            if (r5 != 0) goto L7b
            r10.J(r1)
        L7b:
            boolean r5 = r10.w
            r0.c(r5)
            r10.w = r2
            r10.K = r1
            r2 = 202(0xca, float:2.83E-43)
            rh0 r5 = defpackage.rh.c
            r10.S(r2, r5, r4, r1)
            int r1 = r11 >> 3
            r1 = r1 & 14
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r9.d(r10, r1)
            r10.p(r4)
            r10.p(r4)
            int r0 = r0.b()
            if (r0 == 0) goto La3
            goto La4
        La3:
            r3 = r4
        La4:
            r10.w = r3
            r0 = 0
            r10.K = r0
            mo0 r10 = r10.r()
            if (r10 == 0) goto Lb7
            eg r0 = new eg
            r1 = 2
            r0.<init>(r11, r1, r8, r9)
            r10.d = r0
        Lb7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.o20.b(eo0[], kv, bw, int):void");
    }

    public static final ky0 c(Object obj) {
        if (obj == null) {
            obj = o4.f;
        }
        return new ky0(obj);
    }

    public static void d(Throwable th, Throwable th2) {
        boolean z;
        th.getClass();
        th2.getClass();
        if (th != th2) {
            Integer num = b30.a;
            if (num != null && num.intValue() < 19) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                th.addSuppressed(th2);
                return;
            }
            Method method = im0.a;
            if (method != null) {
                method.invoke(th, th2);
            }
        }
    }

    public static boolean e(Object obj, Object obj2) {
        if (obj == null) {
            if (obj2 == null) {
                return true;
            }
            return false;
        }
        return obj.equals(obj2);
    }

    public static final Object f(im imVar, y8 y8Var, sz0 sz0Var) {
        Object obj;
        ng0 D;
        Object k0;
        lg0 lg0Var;
        if (((bd0) imVar).e.r) {
            bd0 bd0Var = (bd0) imVar;
            if (!bd0Var.e.r) {
                q00.b("visitAncestors called on an unattached node");
            }
            bd0 bd0Var2 = bd0Var.e.i;
            z40 E = k81.E(imVar);
            loop0: while (true) {
                obj = null;
                if (E == null) {
                    break;
                }
                if ((E.H.f.h & 524288) != 0) {
                    while (bd0Var2 != null) {
                        if ((bd0Var2.g & 524288) != 0) {
                            bd0 bd0Var3 = bd0Var2;
                            ef0 ef0Var = null;
                            while (bd0Var3 != null) {
                                if (bd0Var3 instanceof hb) {
                                    obj = bd0Var3;
                                    break loop0;
                                }
                                if ((bd0Var3.g & 524288) != 0 && (bd0Var3 instanceof jm)) {
                                    int i2 = 0;
                                    for (bd0 bd0Var4 = ((jm) bd0Var3).t; bd0Var4 != null; bd0Var4 = bd0Var4.j) {
                                        if ((bd0Var4.g & 524288) != 0) {
                                            i2++;
                                            if (i2 == 1) {
                                                bd0Var3 = bd0Var4;
                                            } else {
                                                if (ef0Var == null) {
                                                    ef0Var = new ef0(new bd0[16]);
                                                }
                                                if (bd0Var3 != null) {
                                                    ef0Var.b(bd0Var3);
                                                    bd0Var3 = null;
                                                }
                                                ef0Var.b(bd0Var4);
                                            }
                                        }
                                    }
                                    if (i2 == 1) {
                                    }
                                }
                                bd0Var3 = k81.h(ef0Var);
                            }
                        }
                        bd0Var2 = bd0Var2.i;
                    }
                }
                E = E.s();
                if (E != null && (lg0Var = E.H) != null) {
                    bd0Var2 = lg0Var.e;
                } else {
                    bd0Var2 = null;
                }
            }
            hb hbVar = (hb) obj;
            if (hbVar != null && (k0 = hbVar.k0((D = k81.D(imVar)), new u3(2, y8Var, D), sz0Var)) == ik.e) {
                return k0;
            }
        }
        return x31.a;
    }

    public static final boolean g(ArrayList arrayList) {
        List list;
        long j2;
        if (arrayList.size() >= 2) {
            if (arrayList.size() <= 1) {
                list = er.e;
            } else {
                ArrayList arrayList2 = new ArrayList();
                Object obj = arrayList.get(0);
                int size = arrayList.size() - 1;
                int i2 = 0;
                while (i2 < size) {
                    i2++;
                    Object obj2 = arrayList.get(i2);
                    su0 su0Var = (su0) obj2;
                    su0 su0Var2 = (su0) obj;
                    float abs = Math.abs(Float.intBitsToFloat((int) (su0Var2.g().a() >> 32)) - Float.intBitsToFloat((int) (su0Var.g().a() >> 32)));
                    float abs2 = Math.abs(Float.intBitsToFloat((int) (su0Var2.g().a() & 4294967295L)) - Float.intBitsToFloat((int) (su0Var.g().a() & 4294967295L)));
                    arrayList2.add(new ch0((Float.floatToRawIntBits(abs) << 32) | (Float.floatToRawIntBits(abs2) & 4294967295L)));
                    obj = obj2;
                }
                list = arrayList2;
            }
            if (list.size() == 1) {
                j2 = ((ch0) me.S(list)).a;
            } else {
                if (list.isEmpty()) {
                    ma0.b("Empty collection can't be reduced.");
                }
                Object S = me.S(list);
                int size2 = list.size() - 1;
                if (1 <= size2) {
                    int i3 = 1;
                    while (true) {
                        S = new ch0(ch0.g(((ch0) S).a, ((ch0) list.get(i3)).a));
                        if (i3 == size2) {
                            break;
                        }
                        i3++;
                    }
                }
                j2 = ((ch0) S).a;
            }
            if (Float.intBitsToFloat((int) (4294967295L & j2)) >= Float.intBitsToFloat((int) (j2 >> 32))) {
                return false;
            }
        }
        return true;
    }

    public static final boolean h(Object obj) {
        if (obj instanceof gx0) {
            gx0 gx0Var = (gx0) obj;
            if (gx0Var.d() == x1.S || gx0Var.d() == dt0.g || gx0Var.d() == x1.V) {
                Object value = gx0Var.getValue();
                if (value != null) {
                    return h(value);
                }
                return true;
            }
        } else if (!(obj instanceof sv) || !(obj instanceof Serializable)) {
            for (int i2 = 0; i2 < 7; i2++) {
                if (d[i2].isInstance(obj)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int i(int i2, int i3) {
        if (i2 < i3) {
            return -1;
        }
        if (i2 == i3) {
            return 0;
        }
        return 1;
    }

    public static int j(long j2, long j3) {
        if (j2 < j3) {
            return -1;
        }
        if (j2 == j3) {
            return 0;
        }
        return 1;
    }

    public static Handler k(Looper looper) {
        if (Build.VERSION.SDK_INT >= 28) {
            return nn.a(looper);
        }
        try {
            return (Handler) Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(looper, null, Boolean.TRUE);
        } catch (IllegalAccessException e2) {
            e = e2;
            Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
            return new Handler(looper);
        } catch (InstantiationException e3) {
            e = e3;
            Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
            return new Handler(looper);
        } catch (NoSuchMethodException e4) {
            e = e4;
            Log.w("HandlerCompat", "Unable to invoke Handler(Looper, Callback, boolean) constructor", e);
            return new Handler(looper);
        } catch (InvocationTargetException e5) {
            Throwable cause = e5.getCause();
            if (!(cause instanceof RuntimeException)) {
                if (cause instanceof Error) {
                    throw ((Error) cause);
                }
                throw new RuntimeException(cause);
            }
            throw ((RuntimeException) cause);
        }
    }

    public static final xt l(Context context) {
        int i2;
        dt0 dt0Var = new dt0(8);
        context.getApplicationContext();
        if (Build.VERSION.SDK_INT >= 31) {
            i2 = ou.a.a(context);
        } else {
            i2 = 0;
        }
        return new xt(dt0Var, new k5(i2));
    }

    public static void m(t4 t4Var, LongSparseArray longSparseArray) {
        TranslationResponseValue o2;
        CharSequence s2;
        uu0 uu0Var;
        su0 su0Var;
        gv gvVar;
        int size = longSparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            long keyAt = longSparseArray.keyAt(i2);
            ViewTranslationResponse r2 = l4.r(longSparseArray.get(keyAt));
            if (r2 != null && (o2 = l4.o(r2)) != null && (s2 = l4.s(o2)) != null && (uu0Var = (uu0) t4Var.j().b((int) keyAt)) != null && (su0Var = uu0Var.a) != null) {
                Object g2 = su0Var.d.e.g(mu0.l);
                if (g2 == null) {
                    g2 = null;
                }
                n0 n0Var = (n0) g2;
                if (n0Var != null && (gvVar = (gv) n0Var.b) != null) {
                }
            }
        }
    }

    public static void n(Canvas canvas, boolean z) {
        Method method;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 29) {
            if (z) {
                h3.z(canvas);
                return;
            } else {
                h3.C(canvas);
                return;
            }
        }
        if (!v) {
            try {
                if (i2 == 28) {
                    Method declaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, new Class[0].getClass());
                    t = (Method) declaredMethod.invoke(Canvas.class, "insertReorderBarrier", new Class[0]);
                    u = (Method) declaredMethod.invoke(Canvas.class, "insertInorderBarrier", new Class[0]);
                } else {
                    t = Canvas.class.getDeclaredMethod("insertReorderBarrier", null);
                    u = Canvas.class.getDeclaredMethod("insertInorderBarrier", null);
                }
                Method method2 = t;
                if (method2 != null) {
                    method2.setAccessible(true);
                }
                Method method3 = u;
                if (method3 != null) {
                    method3.setAccessible(true);
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            }
            v = true;
        }
        if (z) {
            try {
                Method method4 = t;
                if (method4 != null) {
                    method4.invoke(canvas, null);
                }
            } catch (IllegalAccessException | InvocationTargetException unused2) {
                return;
            }
        }
        if (!z && (method = u) != null) {
            method.invoke(canvas, null);
        }
    }

    public static final boolean o(long j2, long j3) {
        if (j2 == j3) {
            return true;
        }
        return false;
    }

    public static final he0 p(vu0 vu0Var, gv gvVar) {
        Trace.beginSection("getAllUncoveredSemanticsNodesToIntObjectMap");
        try {
            su0 a2 = vu0Var.a();
            z40 z40Var = a2.c;
            if (z40Var.F() && z40Var.E()) {
                wo0 g2 = a2.g();
                he0 he0Var = new he0(48);
                j2 j2Var = new j2(22);
                j2Var.p(k81.H(g2));
                s(new j2(22), j2Var, gvVar, he0Var, a2, a2);
                return he0Var;
            }
            he0 he0Var2 = u10.a;
            he0Var2.getClass();
            return he0Var2;
        } finally {
            Trace.endSection();
        }
    }

    public static final void q(j2 j2Var, j2 j2Var2, gv gvVar, he0 he0Var, su0 su0Var, su0 su0Var2) {
        boolean z;
        boolean z2;
        boolean z3;
        j2 j2Var3 = j2Var;
        Region region = (Region) j2Var3.f;
        j2 j2Var4 = j2Var2;
        Region region2 = (Region) j2Var4.f;
        z40 z40Var = su0Var2.c;
        z40 z40Var2 = su0Var2.c;
        if (z40Var.F() && z40Var2.E() && !region2.isEmpty()) {
            wo0 m2 = su0Var2.m();
            if (m2.a >= m2.c) {
                z = true;
            } else {
                z = false;
            }
            if (m2.b >= m2.d) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z | z2) {
                im f2 = su0Var2.f();
                if (f2 == null) {
                    w00 w00Var = z40Var2.H.c;
                    m2 = o30.n(w00Var).U(w00Var, false);
                } else {
                    bd0 bd0Var = ((bd0) f2).e;
                    Object g2 = su0Var2.d.e.g(mu0.b);
                    if (g2 == null) {
                        g2 = null;
                    }
                    if (g2 != null) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    m2 = m20.q(bd0Var, z3, false);
                }
            }
            z10 H = k81.H(m2);
            j2Var3.p(H);
            if (region.op(region2, Region.Op.INTERSECT)) {
                int i2 = su0Var2.f;
                su0 su0Var3 = su0Var;
                if (i2 == su0Var3.f) {
                    i2 = -1;
                }
                Rect bounds = region.getBounds();
                uu0 uu0Var = new uu0(su0Var2, new z10(bounds.left, bounds.top, bounds.right, bounds.bottom));
                he0 he0Var2 = he0Var;
                he0Var2.h(i2, uu0Var);
                List j2 = su0.j(4, su0Var2);
                int size = j2.size() - 1;
                while (-1 < size) {
                    if (!((Boolean) gvVar.e(j2.get(size))).booleanValue()) {
                        q(j2Var3, j2Var4, gvVar, he0Var2, su0Var3, (su0) j2.get(size));
                    }
                    size--;
                    j2Var3 = j2Var;
                    j2Var4 = j2Var2;
                    he0Var2 = he0Var;
                    su0Var3 = su0Var;
                }
                if (v(su0Var2)) {
                    region2.op(H.a, H.b, H.c, H.d, Region.Op.DIFFERENCE);
                    return;
                }
                return;
            }
            return;
        }
        if (su0Var2.o()) {
            r(he0Var, su0Var, su0Var2);
        }
    }

    public static final void r(he0 he0Var, su0 su0Var, su0 su0Var2) {
        wo0 wo0Var;
        z40 z40Var;
        su0 l2 = su0Var2.l();
        if (l2 != null && (z40Var = l2.c) != null && z40Var.F()) {
            wo0Var = l2.g();
        } else {
            wo0Var = p;
        }
        int i2 = su0Var2.f;
        if (i2 == su0Var.f) {
            i2 = -1;
        }
        he0Var.h(i2, new uu0(su0Var2, k81.H(wo0Var)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ab, code lost:
    
        if (r5 != null) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00bf, code lost:
    
        if (r0 != null) goto L51;
     */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:64:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void s(defpackage.j2 r17, defpackage.j2 r18, defpackage.gv r19, defpackage.he0 r20, defpackage.su0 r21, defpackage.su0 r22) {
        /*
            Method dump skipped, instructions count: 471
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.o20.s(j2, j2, gv, he0, su0, su0):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void t(tp tpVar) {
        if (((bd0) tpVar).e.r) {
            k81.B(tpVar, 1).W0();
        }
    }

    public static final boolean u(su0 su0Var) {
        boolean z;
        ng0 d2 = su0Var.d();
        ve0 ve0Var = su0Var.d.e;
        if (d2 != null) {
            z = d2.X0();
        } else {
            z = false;
        }
        if (!z && !ve0Var.c(wu0.p) && !ve0Var.c(wu0.o)) {
            return false;
        }
        return true;
    }

    public static final boolean v(su0 su0Var) {
        if (!u(su0Var)) {
            nu0 nu0Var = su0Var.d;
            if (!nu0Var.g) {
                ve0 ve0Var = nu0Var.e;
                Object[] objArr = ve0Var.b;
                Object[] objArr2 = ve0Var.c;
                long[] jArr = ve0Var.a;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i2 = 0;
                    while (true) {
                        long j2 = jArr[i2];
                        if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i3 = 8 - ((~(i2 - length)) >>> 31);
                            for (int i4 = 0; i4 < i3; i4++) {
                                if ((255 & j2) < 128) {
                                    int i5 = (i2 << 3) + i4;
                                    Object obj = objArr[i5];
                                    Object obj2 = objArr2[i5];
                                    if (((av0) obj).c) {
                                        return true;
                                    }
                                }
                                j2 >>= 8;
                            }
                            if (i3 != 8) {
                                break;
                            }
                        }
                        if (i2 == length) {
                            break;
                        }
                        i2++;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public static final cd0 w(cd0 cd0Var, d70 d70Var, s60 s60Var, dj0 dj0Var, boolean z) {
        return cd0Var.b(new t60(d70Var, s60Var, dj0Var, z));
    }

    public static final Object x(Object obj, Object obj2) {
        if (obj == null) {
            return obj2;
        }
        if (obj instanceof ArrayList) {
            ((ArrayList) obj).add(obj2);
            return obj;
        }
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(obj);
        arrayList.add(obj2);
        return arrayList;
    }

    public static final Object y(Object obj) {
        if (obj instanceof qf) {
            return o30.l(((qf) obj).a);
        }
        return obj;
    }

    public static final void z(pc pcVar, ij ijVar, boolean z) {
        Object e2;
        v31 v31Var;
        Object q2 = pcVar.q();
        Throwable d2 = pcVar.d(q2);
        if (d2 != null) {
            e2 = new jq0(d2);
        } else {
            e2 = pcVar.e(q2);
        }
        if (z) {
            ijVar.getClass();
            in inVar = (in) ijVar;
            jj jjVar = inVar.i;
            Object obj = inVar.k;
            yj r2 = jjVar.r();
            Object Q = k81.Q(r2, obj);
            if (Q != k81.o) {
                v31Var = f31.W(jjVar, r2, Q);
            } else {
                v31Var = null;
            }
            try {
                jjVar.u(e2);
                if (v31Var != null && !v31Var.p0()) {
                    return;
                }
                k81.G(r2, Q);
                return;
            } catch (Throwable th) {
                if (v31Var == null || v31Var.p0()) {
                    k81.G(r2, Q);
                }
                throw th;
            }
        }
        ijVar.u(e2);
    }
}
