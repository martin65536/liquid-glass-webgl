package defpackage;

import android.graphics.Path;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.w3c.dom.Node;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class t20 implements k51 {
    public static final boolean A(float[] fArr) {
        if (fArr.length < 16 || fArr[0] != 1.0f || fArr[1] != 0.0f || fArr[2] != 0.0f || fArr[3] != 0.0f || fArr[4] != 0.0f || fArr[5] != 1.0f || fArr[6] != 0.0f || fArr[7] != 0.0f || fArr[8] != 0.0f || fArr[9] != 0.0f || fArr[10] != 1.0f || fArr[11] != 0.0f || fArr[12] != 0.0f || fArr[13] != 0.0f || fArr[14] != 0.0f || fArr[15] != 1.0f) {
            return false;
        }
        return true;
    }

    public static final boolean B(z40 z40Var) {
        z40 z40Var2;
        if (z40Var.l != null) {
            z40 s = z40Var.s();
            if (s != null) {
                z40Var2 = s.l;
            } else {
                z40Var2 = null;
            }
            if (z40Var2 == null || z40Var.I.b) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static ww0 C(ww0 ww0Var) {
        if (ww0Var instanceof t21) {
            t21 t21Var = (t21) ww0Var;
            if (t21Var.t == m20.o()) {
                t21Var.r = null;
                return ww0Var;
            }
        }
        if (ww0Var instanceof u21) {
            u21 u21Var = (u21) ww0Var;
            if (u21Var.i == m20.o()) {
                u21Var.h = null;
                return ww0Var;
            }
        }
        ww0 g = cx0.g(ww0Var, null, false);
        g.j();
        return g;
    }

    public static Object D(wm wmVar, vu vuVar) {
        ze0 ze0Var;
        ww0 t21Var;
        ww0 ww0Var = (ww0) cx0.b.p();
        if (ww0Var instanceof t21) {
            t21 t21Var2 = (t21) ww0Var;
            if (t21Var2.t == m20.o()) {
                gv gvVar = t21Var2.r;
                gv gvVar2 = t21Var2.s;
                try {
                    ((t21) ww0Var).r = cx0.k(wmVar, gvVar, true);
                    ((t21) ww0Var).s = gvVar2;
                    return vuVar.a();
                } finally {
                    t21Var2.r = gvVar;
                    t21Var2.s = gvVar2;
                }
            }
        }
        if (ww0Var != null && !(ww0Var instanceof ze0)) {
            t21Var = ww0Var.u(wmVar);
        } else {
            if (ww0Var instanceof ze0) {
                ze0Var = (ze0) ww0Var;
            } else {
                ze0Var = null;
            }
            t21Var = new t21(ze0Var, wmVar, null, true, false);
        }
        try {
            ww0 j = t21Var.j();
            try {
                Object a = vuVar.a();
                ww0.q(j);
                t21Var.c();
                return a;
            } catch (Throwable th) {
                ww0.q(j);
                throw th;
            }
        } catch (Throwable th2) {
            t21Var.c();
            throw th2;
        }
    }

    public static final xj0[] E(jq jqVar) {
        Integer num;
        Integer num2;
        xj0 xj0Var;
        Integer num3 = null;
        int i = 0;
        List G = nv0.G(new cs(new cs(new ov0(i, new j81(jqVar, null)), j70.i, i), new m41(23), i));
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        for (Object obj : G) {
            int i3 = i2 + 1;
            if (i2 >= 0) {
                jq jqVar2 = (jq) obj;
                float f = i2;
                int i4 = 1;
                int size = G.size() - 1;
                if (size >= 1) {
                    i4 = size;
                }
                float f2 = f / i4;
                String i5 = i(jqVar2, "offset");
                if (i5 != null) {
                    f2 = Float.parseFloat(i5);
                }
                String i6 = i(jqVar2, "color");
                if (i6 != null) {
                    xj0Var = new xj0(Float.valueOf(f2), new se(f31.e(g30.B(i6))));
                } else {
                    xj0Var = null;
                }
                if (xj0Var != null) {
                    arrayList.add(xj0Var);
                }
                i2 = i3;
            } else {
                jc0.H();
                throw null;
            }
        }
        if (arrayList.isEmpty()) {
            String i7 = i(jqVar, "startColor");
            if (i7 != null) {
                num = Integer.valueOf(g30.B(i7));
            } else {
                num = null;
            }
            String i8 = i(jqVar, "centerColor");
            if (i8 != null) {
                num2 = Integer.valueOf(g30.B(i8));
            } else {
                num2 = null;
            }
            String i9 = i(jqVar, "endColor");
            if (i9 != null) {
                num3 = Integer.valueOf(g30.B(i9));
            }
            if (num != null) {
                arrayList.add(new xj0(Float.valueOf(0.0f), new se(f31.e(num.intValue()))));
            }
            if (num2 != null) {
                arrayList.add(new xj0(Float.valueOf(0.5f), new se(f31.e(num2.intValue()))));
            }
            if (num3 != null) {
                arrayList.add(new xj0(Float.valueOf(1.0f), new se(f31.e(num3.intValue()))));
            }
        }
        return (xj0[]) arrayList.toArray(new xj0[0]);
    }

    public static final qv0 F(jq jqVar) {
        Object obj;
        String i;
        float f;
        float f2;
        float f3;
        int i2;
        float f4;
        float f5;
        int i3;
        float f6;
        int i4 = 0;
        bs bsVar = new bs(new cs(new ov0(i4, new j81(jqVar, null)), j70.j, i4));
        while (true) {
            if (bsVar.hasNext()) {
                obj = bsVar.next();
                String nodeName = ((Node) ((jq) obj).f).getNodeName();
                nodeName.getClass();
                if (nodeName.equals("gradient")) {
                    break;
                }
            } else {
                obj = null;
                break;
            }
        }
        jq jqVar2 = (jq) obj;
        if (jqVar2 != null && (i = i(jqVar2, "type")) != null) {
            int hashCode = i.hashCode();
            float f7 = 0.0f;
            if (hashCode != -1102672091) {
                if (hashCode != -938579425) {
                    if (hashCode == 109850348 && i.equals("sweep")) {
                        xj0[] E = E(jqVar2);
                        xj0[] xj0VarArr = (xj0[]) Arrays.copyOf(E, E.length);
                        String i5 = i(jqVar2, "centerX");
                        if (i5 != null) {
                            f6 = Float.parseFloat(i5);
                        } else {
                            f6 = 0.0f;
                        }
                        String i6 = i(jqVar2, "centerY");
                        if (i6 != null) {
                            f7 = Float.parseFloat(i6);
                        }
                        long floatToRawIntBits = (Float.floatToRawIntBits(f6) << 32) | (Float.floatToRawIntBits(f7) & 4294967295L);
                        ArrayList arrayList = new ArrayList(xj0VarArr.length);
                        for (xj0 xj0Var : xj0VarArr) {
                            arrayList.add(new se(((se) xj0Var.f).a));
                        }
                        int length = xj0VarArr.length;
                        ArrayList arrayList2 = new ArrayList(length);
                        while (i4 < length) {
                            arrayList2.add(Float.valueOf(((Number) xj0VarArr[i4].e).floatValue()));
                            i4++;
                        }
                        return new zz0(floatToRawIntBits, arrayList, arrayList2);
                    }
                } else if (i.equals("radial")) {
                    xj0[] E2 = E(jqVar2);
                    xj0[] xj0VarArr2 = (xj0[]) Arrays.copyOf(E2, E2.length);
                    String i7 = i(jqVar2, "centerX");
                    if (i7 != null) {
                        f4 = Float.parseFloat(i7);
                    } else {
                        f4 = 0.0f;
                    }
                    String i8 = i(jqVar2, "centerY");
                    if (i8 != null) {
                        f5 = Float.parseFloat(i8);
                    } else {
                        f5 = 0.0f;
                    }
                    long floatToRawIntBits2 = (Float.floatToRawIntBits(f4) << 32) | (Float.floatToRawIntBits(f5) & 4294967295L);
                    String i9 = i(jqVar2, "gradientRadius");
                    if (i9 != null) {
                        f7 = Float.parseFloat(i9);
                    }
                    float f8 = f7;
                    String i10 = i(jqVar2, "tileMode");
                    if (i10 != null) {
                        i3 = g30.D(i10);
                    } else {
                        i3 = 0;
                    }
                    ArrayList arrayList3 = new ArrayList(xj0VarArr2.length);
                    for (xj0 xj0Var2 : xj0VarArr2) {
                        arrayList3.add(new se(((se) xj0Var2.f).a));
                    }
                    int length2 = xj0VarArr2.length;
                    ArrayList arrayList4 = new ArrayList(length2);
                    while (i4 < length2) {
                        arrayList4.add(Float.valueOf(((Number) xj0VarArr2[i4].e).floatValue()));
                        i4++;
                    }
                    return new fo0(arrayList3, arrayList4, floatToRawIntBits2, f8, i3);
                }
            } else if (i.equals("linear")) {
                xj0[] E3 = E(jqVar2);
                xj0[] xj0VarArr3 = (xj0[]) Arrays.copyOf(E3, E3.length);
                String i11 = i(jqVar2, "startX");
                if (i11 != null) {
                    f = Float.parseFloat(i11);
                } else {
                    f = 0.0f;
                }
                String i12 = i(jqVar2, "startY");
                if (i12 != null) {
                    f2 = Float.parseFloat(i12);
                } else {
                    f2 = 0.0f;
                }
                long floatToRawIntBits3 = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f2) & 4294967295L);
                String i13 = i(jqVar2, "endX");
                if (i13 != null) {
                    f3 = Float.parseFloat(i13);
                } else {
                    f3 = 0.0f;
                }
                String i14 = i(jqVar2, "endY");
                if (i14 != null) {
                    f7 = Float.parseFloat(i14);
                }
                long floatToRawIntBits4 = (Float.floatToRawIntBits(f3) << 32) | (4294967295L & Float.floatToRawIntBits(f7));
                String i15 = i(jqVar2, "tileMode");
                if (i15 != null) {
                    i2 = g30.D(i15);
                } else {
                    i2 = 0;
                }
                ArrayList arrayList5 = new ArrayList(xj0VarArr3.length);
                for (xj0 xj0Var3 : xj0VarArr3) {
                    arrayList5.add(new se(((se) xj0Var3.f).a));
                }
                int length3 = xj0VarArr3.length;
                ArrayList arrayList6 = new ArrayList(length3);
                while (i4 < length3) {
                    arrayList6.add(Float.valueOf(((Number) xj0VarArr3[i4].e).floatValue()));
                    i4++;
                }
                return new a90(arrayList5, arrayList6, floatToRawIntBits3, floatToRawIntBits4, i2);
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x02d4  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x02e5  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02ca  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02b9  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x023a  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x027f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void G(defpackage.jq r44, defpackage.hz r45, defpackage.fc r46) {
        /*
            Method dump skipped, instructions count: 862
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.t20.G(jq, hz, fc):void");
    }

    public static g2 H(wa waVar) {
        cx0.e(cx0.a);
        synchronized (cx0.c) {
            cx0.h = me.a0(cx0.h, waVar);
        }
        return new g2(waVar);
    }

    public static final boolean I(ve0 ve0Var, Object obj, Object obj2) {
        Object g = ve0Var.g(obj);
        if (g == null) {
            return false;
        }
        if (g instanceof we0) {
            we0 we0Var = (we0) g;
            boolean l = we0Var.l(obj2);
            if (l && we0Var.g()) {
                ve0Var.k(obj);
            }
            return l;
        }
        if (!g.equals(obj2)) {
            return false;
        }
        ve0Var.k(obj);
        return true;
    }

    public static final void J(ve0 ve0Var, Object obj) {
        boolean z;
        long[] jArr = ve0Var.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            int i4 = (i << 3) + i3;
                            Object obj2 = ve0Var.b[i4];
                            Object obj3 = ve0Var.c[i4];
                            if (obj3 instanceof we0) {
                                we0 we0Var = (we0) obj3;
                                we0Var.l(obj);
                                z = we0Var.g();
                            } else if (obj3 == obj) {
                                z = true;
                            } else {
                                z = false;
                            }
                            if (z) {
                                ve0Var.l(i4);
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

    public static void K(ww0 ww0Var, ww0 ww0Var2, gv gvVar) {
        if (ww0Var == ww0Var2) {
            if (ww0Var instanceof t21) {
                ((t21) ww0Var).r = gvVar;
                return;
            } else if (ww0Var instanceof u21) {
                ((u21) ww0Var).h = gvVar;
                return;
            } else {
                v7.e(ww0Var, "Non-transparent snapshot was reused: ");
                return;
            }
        }
        ww0Var2.getClass();
        ww0.q(ww0Var);
        ww0Var2.c();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00c3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final defpackage.g30 L(long r38, float r40, defpackage.ir0 r41) {
        /*
            Method dump skipped, instructions count: 792
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.t20.L(long, float, ir0):g30");
    }

    public static final boolean M(int i, oj ojVar, pt ptVar, wo0 wo0Var) {
        pt q;
        ef0 ef0Var = new ef0(new pt[16]);
        if (!ptVar.e.r) {
            q00.b("visitChildren called on an unattached node");
        }
        ef0 ef0Var2 = new ef0(new bd0[16]);
        bd0 bd0Var = ptVar.e;
        bd0 bd0Var2 = bd0Var.j;
        if (bd0Var2 == null) {
            k81.f(ef0Var2, bd0Var);
        } else {
            ef0Var2.b(bd0Var2);
        }
        while (true) {
            int i2 = ef0Var2.g;
            if (i2 == 0) {
                break;
            }
            bd0 bd0Var3 = (bd0) ef0Var2.k(i2 - 1);
            if ((bd0Var3.h & 1024) == 0) {
                k81.f(ef0Var2, bd0Var3);
            } else {
                while (true) {
                    if (bd0Var3 == null) {
                        break;
                    }
                    if ((bd0Var3.g & 1024) != 0) {
                        ef0 ef0Var3 = null;
                        while (bd0Var3 != null) {
                            if (bd0Var3 instanceof pt) {
                                pt ptVar2 = (pt) bd0Var3;
                                if (ptVar2.r) {
                                    ef0Var.b(ptVar2);
                                }
                            } else if ((bd0Var3.g & 1024) != 0 && (bd0Var3 instanceof jm)) {
                                int i3 = 0;
                                for (bd0 bd0Var4 = ((jm) bd0Var3).t; bd0Var4 != null; bd0Var4 = bd0Var4.j) {
                                    if ((bd0Var4.g & 1024) != 0) {
                                        i3++;
                                        if (i3 == 1) {
                                            bd0Var3 = bd0Var4;
                                        } else {
                                            if (ef0Var3 == null) {
                                                ef0Var3 = new ef0(new bd0[16]);
                                            }
                                            if (bd0Var3 != null) {
                                                ef0Var3.b(bd0Var3);
                                                bd0Var3 = null;
                                            }
                                            ef0Var3.b(bd0Var4);
                                        }
                                    }
                                }
                                if (i3 == 1) {
                                }
                            }
                            bd0Var3 = k81.h(ef0Var3);
                        }
                    } else {
                        bd0Var3 = bd0Var3.j;
                    }
                }
            }
        }
        while (ef0Var.g != 0 && (q = q(ef0Var, wo0Var, i)) != null) {
            if (q.F0().a) {
                return ((Boolean) ojVar.e(q)).booleanValue();
            }
            if (s(i, ojVar, q, wo0Var)) {
                return true;
            }
            ef0Var.j(q);
        }
        return false;
    }

    public static void N() {
        boolean z;
        synchronized (cx0.c) {
            we0 we0Var = cx0.j.h;
            z = false;
            if (we0Var != null) {
                if (we0Var.h()) {
                    z = true;
                }
            }
        }
        if (z) {
            cx0.a();
        }
    }

    public static final void O(bj0 bj0Var, int i, Object obj) {
        bj0Var.e[(bj0Var.f - bj0Var.a[bj0Var.b - 1].b) + i] = obj;
    }

    public static final void P(bj0 bj0Var, int i, Object obj, int i2, Object obj2) {
        int i3 = bj0Var.f - bj0Var.a[bj0Var.b - 1].b;
        Object[] objArr = bj0Var.e;
        objArr[i + i3] = obj;
        objArr[i3 + i2] = obj2;
    }

    public static final int Q(ge0 ge0Var) {
        int b;
        int i = ge0Var.b;
        int b2 = ge0Var.b(0);
        while (ge0Var.b != 0 && ge0Var.b(0) == b2) {
            int i2 = ge0Var.b;
            if (i2 != 0) {
                ge0Var.d(0, ge0Var.a[i2 - 1]);
                ge0Var.c(ge0Var.b - 1);
                int i3 = ge0Var.b;
                int i4 = i3 >>> 1;
                int i5 = 0;
                while (i5 < i4) {
                    int b3 = ge0Var.b(i5);
                    int i6 = (i5 + 1) * 2;
                    int i7 = i6 - 1;
                    int b4 = ge0Var.b(i7);
                    if (i6 < i3 && (b = ge0Var.b(i6)) > b4) {
                        if (b > b3) {
                            ge0Var.d(i5, b);
                            ge0Var.d(i6, b3);
                            i5 = i6;
                        }
                    } else if (b4 > b3) {
                        ge0Var.d(i5, b4);
                        ge0Var.d(i7, b3);
                        i5 = i7;
                    }
                }
            } else {
                throw new NoSuchElementException("IntList is empty.");
            }
        }
        return b2;
    }

    public static final void R(List list, y5 y5Var) {
        boolean z;
        fl0 fl0Var;
        Path path;
        int i;
        float f;
        int i2;
        fl0 fl0Var2;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        List list2 = list;
        y5 y5Var2 = y5Var;
        Path path2 = y5Var2.a;
        Path path3 = y5Var2.a;
        Path.FillType fillType = path2.getFillType();
        Path.FillType fillType2 = Path.FillType.EVEN_ODD;
        if (fillType == fillType2) {
            z = true;
        } else {
            z = false;
        }
        path3.rewind();
        if (!z) {
            fillType2 = Path.FillType.WINDING;
        }
        path3.setFillType(fillType2);
        if (list2.isEmpty()) {
            fl0Var = nk0.c;
        } else {
            fl0Var = (fl0) list2.get(0);
        }
        int size = list2.size();
        float f11 = 0.0f;
        int i3 = 0;
        float f12 = 0.0f;
        float f13 = 0.0f;
        float f14 = 0.0f;
        float f15 = 0.0f;
        float f16 = 0.0f;
        float f17 = 0.0f;
        while (i3 < size) {
            fl0 fl0Var3 = (fl0) list2.get(i3);
            if (fl0Var3 instanceof nk0) {
                path3.close();
                path = path3;
                i = size;
                f = f11;
                i2 = i3;
                fl0Var2 = fl0Var3;
                f12 = f16;
                f14 = f12;
                f13 = f17;
                f15 = f13;
            } else {
                if (fl0Var3 instanceof zk0) {
                    zk0 zk0Var = (zk0) fl0Var3;
                    float f18 = zk0Var.c;
                    f14 += f18;
                    float f19 = zk0Var.d;
                    f15 += f19;
                    path3.rMoveTo(f18, f19);
                    path = path3;
                    i = size;
                    f = f11;
                    i2 = i3;
                    f16 = f14;
                    f17 = f15;
                } else {
                    if (fl0Var3 instanceof rk0) {
                        rk0 rk0Var = (rk0) fl0Var3;
                        float f20 = rk0Var.c;
                        float f21 = rk0Var.d;
                        path3.moveTo(f20, f21);
                        f15 = f21;
                        f17 = f15;
                        path = path3;
                        f14 = f20;
                        f16 = f14;
                    } else {
                        if (fl0Var3 instanceof yk0) {
                            yk0 yk0Var = (yk0) fl0Var3;
                            float f22 = yk0Var.d;
                            float f23 = yk0Var.c;
                            path3.rLineTo(f23, f22);
                            f14 += f23;
                            f15 += f22;
                        } else if (fl0Var3 instanceof qk0) {
                            qk0 qk0Var = (qk0) fl0Var3;
                            float f24 = qk0Var.d;
                            float f25 = qk0Var.c;
                            y5Var2.b(f25, f24);
                            f14 = f25;
                            path = path3;
                            f15 = f24;
                        } else if (fl0Var3 instanceof xk0) {
                            float f26 = ((xk0) fl0Var3).c;
                            path3.rLineTo(f26, f11);
                            f14 += f26;
                        } else if (fl0Var3 instanceof pk0) {
                            float f27 = ((pk0) fl0Var3).c;
                            y5Var2.b(f27, f15);
                            f14 = f27;
                        } else if (fl0Var3 instanceof dl0) {
                            float f28 = ((dl0) fl0Var3).c;
                            path3.rLineTo(f11, f28);
                            f15 += f28;
                        } else if (fl0Var3 instanceof el0) {
                            float f29 = ((el0) fl0Var3).c;
                            y5Var2.b(f14, f29);
                            f15 = f29;
                        } else if (fl0Var3 instanceof wk0) {
                            wk0 wk0Var = (wk0) fl0Var3;
                            path3.rCubicTo(wk0Var.c, wk0Var.d, wk0Var.e, wk0Var.f, wk0Var.g, wk0Var.h);
                            Path path4 = path3;
                            float f30 = wk0Var.e + f14;
                            float f31 = wk0Var.f + f15;
                            f14 += wk0Var.g;
                            f15 += wk0Var.h;
                            f13 = f31;
                            path = path4;
                            i = size;
                            f = f11;
                            i2 = i3;
                            fl0Var2 = fl0Var3;
                            f12 = f30;
                        } else {
                            Path path5 = path3;
                            if (fl0Var3 instanceof ok0) {
                                ok0 ok0Var = (ok0) fl0Var3;
                                y5Var2.a(ok0Var.c, ok0Var.d, ok0Var.e, ok0Var.f, ok0Var.g, ok0Var.h);
                                f5 = ok0Var.e;
                                f6 = ok0Var.f;
                                f7 = ok0Var.g;
                                f8 = ok0Var.h;
                            } else {
                                if (fl0Var3 instanceof bl0) {
                                    if (fl0Var.a) {
                                        f9 = f14 - f12;
                                        f10 = f15 - f13;
                                    } else {
                                        f9 = f11;
                                        f10 = f9;
                                    }
                                    bl0 bl0Var = (bl0) fl0Var3;
                                    path5.rCubicTo(f9, f10, bl0Var.c, bl0Var.d, bl0Var.e, bl0Var.f);
                                    path5 = path5;
                                    float f32 = bl0Var.c + f14;
                                    float f33 = bl0Var.d + f15;
                                    f14 += bl0Var.e;
                                    f15 += bl0Var.f;
                                    f12 = f32;
                                    f13 = f33;
                                } else if (fl0Var3 instanceof tk0) {
                                    if (fl0Var.a) {
                                        f14 = (f14 * 2.0f) - f12;
                                        f15 = (2.0f * f15) - f13;
                                    }
                                    tk0 tk0Var = (tk0) fl0Var3;
                                    y5Var.a(f14, f15, tk0Var.c, tk0Var.d, tk0Var.e, tk0Var.f);
                                    f5 = tk0Var.c;
                                    f6 = tk0Var.d;
                                    f7 = tk0Var.e;
                                    f8 = tk0Var.f;
                                } else {
                                    if (fl0Var3 instanceof al0) {
                                        al0 al0Var = (al0) fl0Var3;
                                        float f34 = al0Var.f;
                                        float f35 = al0Var.e;
                                        float f36 = al0Var.d;
                                        float f37 = al0Var.c;
                                        path5.rQuadTo(f37, f36, f35, f34);
                                        f5 = f37 + f14;
                                        f4 = f36 + f15;
                                        f14 += f35;
                                        f15 += f34;
                                    } else if (fl0Var3 instanceof sk0) {
                                        sk0 sk0Var = (sk0) fl0Var3;
                                        float f38 = sk0Var.f;
                                        float f39 = sk0Var.e;
                                        f4 = sk0Var.d;
                                        f5 = sk0Var.c;
                                        path5.quadTo(f5, f4, f39, f38);
                                        f15 = f38;
                                        f14 = f39;
                                    } else if (fl0Var3 instanceof cl0) {
                                        if (fl0Var.b) {
                                            f2 = f14 - f12;
                                            f3 = f15 - f13;
                                        } else {
                                            f2 = f11;
                                            f3 = f2;
                                        }
                                        cl0 cl0Var = (cl0) fl0Var3;
                                        float f40 = cl0Var.d;
                                        float f41 = cl0Var.c;
                                        path5.rQuadTo(f2, f3, f41, f40);
                                        float f42 = f2 + f14;
                                        float f43 = f3 + f15;
                                        f14 += f41;
                                        f15 += f40;
                                        f12 = f42;
                                        f13 = f43;
                                    } else if (fl0Var3 instanceof uk0) {
                                        if (fl0Var.b) {
                                            f14 = (f14 * 2.0f) - f12;
                                            f15 = (2.0f * f15) - f13;
                                        }
                                        uk0 uk0Var = (uk0) fl0Var3;
                                        float f44 = uk0Var.d;
                                        float f45 = uk0Var.c;
                                        path5.quadTo(f14, f15, f45, f44);
                                        path = path5;
                                        i = size;
                                        f = f11;
                                        i2 = i3;
                                        f12 = f14;
                                        f13 = f15;
                                        fl0Var2 = fl0Var3;
                                        f14 = f45;
                                        f15 = f44;
                                    } else if (fl0Var3 instanceof vk0) {
                                        vk0 vk0Var = (vk0) fl0Var3;
                                        float f46 = vk0Var.h + f14;
                                        float f47 = vk0Var.i + f15;
                                        i = size;
                                        i2 = i3;
                                        path = path5;
                                        f = 0.0f;
                                        p(y5Var, f14, f15, f46, f47, vk0Var.c, vk0Var.d, vk0Var.e, vk0Var.f, vk0Var.g);
                                        f12 = f46;
                                        f14 = f12;
                                        f13 = f47;
                                        f15 = f13;
                                        fl0Var2 = fl0Var3;
                                    } else {
                                        path = path5;
                                        i = size;
                                        f = f11;
                                        i2 = i3;
                                        if (fl0Var3 instanceof mk0) {
                                            mk0 mk0Var = (mk0) fl0Var3;
                                            float f48 = mk0Var.i;
                                            float f49 = mk0Var.h;
                                            fl0Var2 = fl0Var3;
                                            p(y5Var, f14, f15, f49, f48, mk0Var.c, mk0Var.d, mk0Var.e, mk0Var.f, mk0Var.g);
                                            f13 = f48;
                                            f15 = f13;
                                            f12 = f49;
                                            f14 = f12;
                                        } else {
                                            v7.k();
                                            return;
                                        }
                                    }
                                    f13 = f4;
                                    path = path5;
                                    i = size;
                                    f = f11;
                                    i2 = i3;
                                    fl0Var2 = fl0Var3;
                                    f12 = f5;
                                }
                                path = path5;
                            }
                            f13 = f6;
                            f14 = f7;
                            f15 = f8;
                            path = path5;
                            i = size;
                            f = f11;
                            i2 = i3;
                            fl0Var2 = fl0Var3;
                            f12 = f5;
                        }
                        path = path3;
                    }
                    i = size;
                    f = f11;
                    i2 = i3;
                }
                fl0Var2 = fl0Var3;
            }
            i3 = i2 + 1;
            list2 = list;
            y5Var2 = y5Var;
            path3 = path;
            size = i;
            fl0Var = fl0Var2;
            f11 = f;
        }
    }

    public static String S(int i) {
        if (i == 0) {
            return "Clamp";
        }
        if (i == 1) {
            return "Repeated";
        }
        if (i == 2) {
            return "Mirror";
        }
        if (i == 3) {
            return "Decal";
        }
        return "Unknown";
    }

    public static final Boolean T(int i, oj ojVar, pt ptVar, wo0 wo0Var) {
        int ordinal = ptVar.I0().ordinal();
        if (ordinal != 0) {
            if (ordinal != 1) {
                if (ordinal != 2) {
                    if (ordinal == 3) {
                        if (ptVar.F0().a) {
                            return (Boolean) ojVar.e(ptVar);
                        }
                        if (wo0Var == null) {
                            return Boolean.valueOf(r(ptVar, i, ojVar));
                        }
                        return Boolean.valueOf(M(i, ojVar, ptVar, wo0Var));
                    }
                    v7.k();
                    return null;
                }
            } else {
                pt v = n20.v(ptVar);
                if (v != null) {
                    int ordinal2 = v.I0().ordinal();
                    if (ordinal2 != 0) {
                        if (ordinal2 != 1) {
                            if (ordinal2 != 2) {
                                if (ordinal2 != 3) {
                                    v7.k();
                                    return null;
                                }
                                v7.o("ActiveParent must have a focusedChild");
                                return null;
                            }
                        } else {
                            Boolean T = T(i, ojVar, v, wo0Var);
                            if (!o20.e(T, Boolean.FALSE)) {
                                return T;
                            }
                            if (wo0Var == null) {
                                if (v.I0() == ot.f) {
                                    pt s = n20.s(v);
                                    if (s != null) {
                                        wo0Var = n20.t(s);
                                    } else {
                                        v7.o("ActiveParent must have a focusedChild");
                                        return null;
                                    }
                                } else {
                                    v7.o("Searching for active node in inactive hierarchy");
                                    return null;
                                }
                            }
                            return Boolean.valueOf(s(i, ojVar, ptVar, wo0Var));
                        }
                    }
                    if (wo0Var == null) {
                        wo0Var = n20.t(v);
                    }
                    return Boolean.valueOf(s(i, ojVar, ptVar, wo0Var));
                }
                v7.o("ActiveParent must have a focusedChild");
                return null;
            }
        }
        return Boolean.valueOf(r(ptVar, i, ojVar));
    }

    public static Object U(kv kvVar, Object obj, ij ijVar) {
        Object jjVar;
        kvVar.getClass();
        yj r = ijVar.r();
        if (r == cr.e) {
            jjVar = new hq0(ijVar);
        } else {
            jjVar = new jj(ijVar, r);
        }
        f31.n(2, kvVar);
        return kvVar.d(obj, jjVar);
    }

    public static final void a(final Object obj, final int i, final n60 n60Var, final gg ggVar, bw bwVar, final int i2) {
        int i3;
        boolean z;
        gv gvVar;
        int i4;
        int i5;
        int i6;
        int i7;
        bwVar.W(872548579);
        if ((i2 & 6) == 0) {
            if (bwVar.h(obj)) {
                i7 = 4;
            } else {
                i7 = 2;
            }
            i3 = i7 | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            if (bwVar.d(i)) {
                i6 = 32;
            } else {
                i6 = 16;
            }
            i3 |= i6;
        }
        if ((i2 & 384) == 0) {
            if (bwVar.h(n60Var)) {
                i5 = 256;
            } else {
                i5 = 128;
            }
            i3 |= i5;
        }
        if ((i2 & 3072) == 0) {
            if (bwVar.h(ggVar)) {
                i4 = 2048;
            } else {
                i4 = 1024;
            }
            i3 |= i4;
        }
        if ((i3 & 1171) != 1170) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i3 & 1, z)) {
            boolean f = bwVar.f(obj) | bwVar.f(n60Var);
            Object L = bwVar.L();
            Object obj2 = ph.a;
            if (f || L == obj2) {
                L = new l60(obj, n60Var);
                bwVar.f0(L);
            }
            l60 l60Var = (l60) L;
            l60Var.c = i;
            ik0 ik0Var = l60Var.g;
            do0 do0Var = cm0.a;
            l60 l60Var2 = (l60) bwVar.j(do0Var);
            ww0 t = t();
            if (t != null) {
                gvVar = t.e();
            } else {
                gvVar = null;
            }
            ww0 C = C(t);
            try {
                if (l60Var2 != ((l60) ik0Var.getValue())) {
                    ik0Var.setValue(l60Var2);
                    if (l60Var.d > 0) {
                        l60 l60Var3 = l60Var.e;
                        if (l60Var3 != null) {
                            l60Var3.b();
                        }
                        if (l60Var2 != null) {
                            l60Var2.a();
                        } else {
                            l60Var2 = null;
                        }
                        l60Var.e = l60Var2;
                    }
                }
                K(t, C, gvVar);
                boolean f2 = bwVar.f(l60Var);
                Object L2 = bwVar.L();
                int i8 = 6;
                if (f2 || L2 == obj2) {
                    L2 = new l(i8, l60Var);
                    bwVar.f0(L2);
                }
                dl.f(l60Var, (gv) L2, bwVar);
                o20.a(do0Var.a(l60Var), ggVar, bwVar, ((i3 >> 6) & 112) | 8);
            } catch (Throwable th) {
                K(t, C, gvVar);
                throw th;
            }
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new kv() { // from class: m60
                @Override // defpackage.kv
                public final Object d(Object obj3, Object obj4) {
                    ((Integer) obj4).getClass();
                    t20.a(obj, i, n60Var, ggVar, (bw) obj3, d20.O(i2 | 1));
                    return x31.a;
                }
            };
        }
    }

    public static final void b(bw bwVar, int i) {
        boolean z;
        bwVar.W(1681090384);
        boolean z2 = false;
        if (i != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i & 1, z)) {
            f31.b(null, pg.a, bwVar, 48, 1);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new yu0(i, 10, z2);
        }
    }

    public static final wo0 c(long j, long j2) {
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        return new wo0(Float.intBitsToFloat(i), Float.intBitsToFloat(i2), Float.intBitsToFloat((int) (j2 >> 32)) + Float.intBitsToFloat(i), Float.intBitsToFloat((int) (j2 & 4294967295L)) + Float.intBitsToFloat(i2));
    }

    public static final su0 d(z40 z40Var, boolean z) {
        bd0 bd0Var = z40Var.H.f;
        im imVar = null;
        if ((bd0Var.h & 8) != 0) {
            loop0: while (true) {
                if (bd0Var == null) {
                    break;
                }
                if ((bd0Var.g & 8) != 0) {
                    bd0 bd0Var2 = bd0Var;
                    ef0 ef0Var = null;
                    while (bd0Var2 != null) {
                        if (bd0Var2 instanceof qu0) {
                            imVar = bd0Var2;
                            break loop0;
                        }
                        if ((bd0Var2.g & 8) != 0 && (bd0Var2 instanceof jm)) {
                            int i = 0;
                            for (bd0 bd0Var3 = ((jm) bd0Var2).t; bd0Var3 != null; bd0Var3 = bd0Var3.j) {
                                if ((bd0Var3.g & 8) != 0) {
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
                if ((bd0Var.h & 8) == 0) {
                    break;
                }
                bd0Var = bd0Var.j;
            }
        }
        imVar.getClass();
        bd0 bd0Var4 = ((bd0) ((qu0) imVar)).e;
        nu0 u = z40Var.u();
        if (u == null) {
            u = new nu0();
        }
        return new su0(bd0Var4, z, z40Var, u);
    }

    public static final void e(bw bwVar, cd0 cd0Var) {
        xa xaVar = xa.f;
        long j = bwVar.T;
        int i = (int) (j ^ (j >>> 32));
        cd0 B = dl.B(bwVar, cd0Var);
        ll0 l = bwVar.l();
        jh.c.getClass();
        di diVar = ih.b;
        bwVar.Y();
        if (bwVar.S) {
            bwVar.k(diVar);
        } else {
            bwVar.i0();
        }
        m20.F(ih.e, bwVar, xaVar);
        m20.F(ih.d, bwVar, l);
        m20.C(ih.g, bwVar);
        m20.F(ih.c, bwVar, B);
        m20.F(ih.f, bwVar, Integer.valueOf(i));
        bwVar.p(true);
    }

    public static final void f(ge0 ge0Var, int i) {
        if (ge0Var.b != 0 && (ge0Var.b(0) == i || ge0Var.b(ge0Var.b - 1) == i)) {
            return;
        }
        int i2 = ge0Var.b;
        ge0Var.a(i);
        while (i2 > 0) {
            int i3 = ((i2 + 1) >>> 1) - 1;
            int b = ge0Var.b(i3);
            if (i <= b) {
                break;
            }
            ge0Var.d(i2, b);
            i2 = i3;
        }
        ge0Var.d(i2, i);
    }

    public static final void g(ve0 ve0Var, Object obj, Object obj2) {
        boolean z;
        Object obj3;
        int f = ve0Var.f(obj);
        if (f < 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            obj3 = null;
        } else {
            obj3 = ve0Var.c[f];
        }
        if (obj3 != null) {
            if (obj3 instanceof we0) {
                ((we0) obj3).a(obj2);
            } else if (obj3 != obj2) {
                we0 we0Var = new we0();
                we0Var.a(obj3);
                we0Var.a(obj2);
                obj2 = we0Var;
            }
            obj2 = obj3;
        }
        if (z) {
            int i = ~f;
            ve0Var.b[i] = obj;
            ve0Var.c[i] = obj2;
            return;
        }
        ve0Var.c[f] = obj2;
    }

    public static final jq h(jq jqVar, String str) {
        String lookupPrefix = ((Node) jqVar.f).lookupPrefix("http://schemas.android.com/apk/res/android");
        lookupPrefix.getClass();
        Object obj = null;
        int i = 0;
        bs bsVar = new bs(new cs(new ov0(i, new j81(jqVar, null)), j70.h, i));
        while (true) {
            if (!bsVar.hasNext()) {
                break;
            }
            Object next = bsVar.next();
            jq jqVar2 = (jq) next;
            String namespaceURI = ((Node) jqVar2.f).getNamespaceURI();
            namespaceURI.getClass();
            if (namespaceURI.equals("http://schemas.android.com/aapt")) {
                String localName = ((Node) jqVar2.f).getLocalName();
                localName.getClass();
                if (localName.equals("attr")) {
                    String attribute = jqVar2.h.getAttribute("name");
                    attribute.getClass();
                    if (attribute.equals(lookupPrefix + ":" + str)) {
                        obj = next;
                        break;
                    }
                } else {
                    continue;
                }
            }
        }
        return (jq) obj;
    }

    public static final String i(jq jqVar, String str) {
        jqVar.getClass();
        String attributeNS = jqVar.h.getAttributeNS("http://schemas.android.com/apk/res/android", str);
        attributeNS.getClass();
        if (!uy0.B(attributeNS)) {
            return attributeNS;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x004a, code lost:
    
        if (r21 != 3) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x004d, code lost:
    
        if (r21 != 4) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0050, code lost:
    
        if (r21 != 3) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0052, code lost:
    
        r1 = r11 - r19.c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x006d, code lost:
    
        if (r1 >= 0.0f) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x006f, code lost:
    
        r1 = 0.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0071, code lost:
    
        if (r21 != 3) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0073, code lost:
    
        r11 = r11 - r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0087, code lost:
    
        if (r11 >= 1.0f) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0089, code lost:
    
        r11 = 1.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x008c, code lost:
    
        if (r1 >= r11) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x008e, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x008f, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0075, code lost:
    
        if (r21 != 4) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0077, code lost:
    
        r11 = r2 - r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x007a, code lost:
    
        if (r21 != 5) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x007c, code lost:
    
        r11 = r9 - r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x007f, code lost:
    
        if (r21 != 6) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0081, code lost:
    
        r11 = r6 - r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0090, code lost:
    
        defpackage.v7.o("This function should only be used for 2-D focus search");
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0093, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0057, code lost:
    
        if (r21 != 4) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0059, code lost:
    
        r1 = r19.a - r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x005d, code lost:
    
        if (r21 != 5) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x005f, code lost:
    
        r1 = r9 - r19.d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0064, code lost:
    
        if (r21 != 6) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0066, code lost:
    
        r1 = r19.b - r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0094, code lost:
    
        defpackage.v7.o("This function should only be used for 2-D focus search");
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0097, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x004f, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x003a, code lost:
    
        if (r10 <= r7) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0041, code lost:
    
        if (r9 >= r6) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0048, code lost:
    
        if (r8 <= r5) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0033, code lost:
    
        if (r11 >= r2) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0098, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final boolean j(defpackage.wo0 r18, defpackage.wo0 r19, defpackage.wo0 r20, int r21) {
        /*
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r21
            boolean r4 = k(r3, r2, r0)
            float r5 = r2.b
            float r6 = r2.d
            float r7 = r2.a
            float r2 = r2.c
            float r8 = r0.d
            float r9 = r0.b
            float r10 = r0.c
            float r11 = r0.a
            r12 = 0
            if (r4 != 0) goto L9c
            boolean r0 = k(r3, r1, r0)
            if (r0 != 0) goto L27
            goto L9c
        L27:
            java.lang.String r4 = "This function should only be used for 2-D focus search"
            r13 = 6
            r14 = 5
            r15 = 4
            r18 = 1
            r0 = 3
            if (r3 != r0) goto L36
            int r16 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r16 < 0) goto L98
            goto L4a
        L36:
            if (r3 != r15) goto L3d
            int r16 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1))
            if (r16 > 0) goto L98
            goto L4a
        L3d:
            if (r3 != r14) goto L44
            int r16 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r16 < 0) goto L98
            goto L4a
        L44:
            if (r3 != r13) goto L99
            int r16 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r16 > 0) goto L98
        L4a:
            if (r3 != r0) goto L4d
            goto L4f
        L4d:
            if (r3 != r15) goto L50
        L4f:
            return r18
        L50:
            if (r3 != r0) goto L57
            float r1 = r1.c
            float r1 = r11 - r1
            goto L69
        L57:
            if (r3 != r15) goto L5d
            float r1 = r1.a
            float r1 = r1 - r10
            goto L69
        L5d:
            if (r3 != r14) goto L64
            float r1 = r1.d
            float r1 = r9 - r1
            goto L69
        L64:
            if (r3 != r13) goto L94
            float r1 = r1.b
            float r1 = r1 - r8
        L69:
            r16 = 0
            int r17 = (r1 > r16 ? 1 : (r1 == r16 ? 0 : -1))
            if (r17 >= 0) goto L71
            r1 = r16
        L71:
            if (r3 != r0) goto L75
            float r11 = r11 - r7
            goto L83
        L75:
            if (r3 != r15) goto L7a
            float r11 = r2 - r10
            goto L83
        L7a:
            if (r3 != r14) goto L7f
            float r11 = r9 - r5
            goto L83
        L7f:
            if (r3 != r13) goto L90
            float r11 = r6 - r8
        L83:
            r0 = 1065353216(0x3f800000, float:1.0)
            int r2 = (r11 > r0 ? 1 : (r11 == r0 ? 0 : -1))
            if (r2 >= 0) goto L8a
            r11 = r0
        L8a:
            int r0 = (r1 > r11 ? 1 : (r1 == r11 ? 0 : -1))
            if (r0 >= 0) goto L8f
            return r18
        L8f:
            return r12
        L90:
            defpackage.v7.o(r4)
            return r12
        L94:
            defpackage.v7.o(r4)
            return r12
        L98:
            return r18
        L99:
            defpackage.v7.o(r4)
        L9c:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.t20.j(wo0, wo0, wo0, int):boolean");
    }

    public static final boolean k(int i, wo0 wo0Var, wo0 wo0Var2) {
        if (i == 3 || i == 4) {
            if (wo0Var.d <= wo0Var2.b || wo0Var.b >= wo0Var2.d) {
                return false;
            }
            return true;
        }
        if (i == 5 || i == 6) {
            if (wo0Var.c <= wo0Var2.a || wo0Var.a >= wo0Var2.c) {
                return false;
            }
            return true;
        }
        v7.o("This function should only be used for 2-D focus search");
        return false;
    }

    public static final void l(int i) {
        if (i >= 1) {
            return;
        }
        throw new IllegalArgumentException(("Expected positive parallelism level, but got " + i).toString());
    }

    public static final void m(pt ptVar, ef0 ef0Var) {
        if (!ptVar.e.r) {
            q00.b("visitChildren called on an unattached node");
        }
        ef0 ef0Var2 = new ef0(new bd0[16]);
        bd0 bd0Var = ptVar.e;
        bd0 bd0Var2 = bd0Var.j;
        if (bd0Var2 == null) {
            k81.f(ef0Var2, bd0Var);
        } else {
            ef0Var2.b(bd0Var2);
        }
        while (true) {
            int i = ef0Var2.g;
            if (i != 0) {
                bd0 bd0Var3 = (bd0) ef0Var2.k(i - 1);
                if ((bd0Var3.h & 1024) == 0) {
                    k81.f(ef0Var2, bd0Var3);
                } else {
                    while (true) {
                        if (bd0Var3 == null) {
                            break;
                        }
                        if ((bd0Var3.g & 1024) != 0) {
                            ef0 ef0Var3 = null;
                            while (bd0Var3 != null) {
                                if (bd0Var3 instanceof pt) {
                                    pt ptVar2 = (pt) bd0Var3;
                                    if (ptVar2.r && !k81.E(ptVar2).Q) {
                                        if (ptVar2.F0().a) {
                                            ef0Var.b(ptVar2);
                                        } else {
                                            m(ptVar2, ef0Var);
                                        }
                                    }
                                } else if ((bd0Var3.g & 1024) != 0 && (bd0Var3 instanceof jm)) {
                                    int i2 = 0;
                                    for (bd0 bd0Var4 = ((jm) bd0Var3).t; bd0Var4 != null; bd0Var4 = bd0Var4.j) {
                                        if ((bd0Var4.g & 1024) != 0) {
                                            i2++;
                                            if (i2 == 1) {
                                                bd0Var3 = bd0Var4;
                                            } else {
                                                if (ef0Var3 == null) {
                                                    ef0Var3 = new ef0(new bd0[16]);
                                                }
                                                if (bd0Var3 != null) {
                                                    ef0Var3.b(bd0Var3);
                                                    bd0Var3 = null;
                                                }
                                                ef0Var3.b(bd0Var4);
                                            }
                                        }
                                    }
                                    if (i2 == 1) {
                                    }
                                }
                                bd0Var3 = k81.h(ef0Var3);
                            }
                        } else {
                            bd0Var3 = bd0Var3.j;
                        }
                    }
                }
            } else {
                return;
            }
        }
    }

    public static ve0 n() {
        long[] jArr = zs0.a;
        return new ve0();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static ij o(ij ijVar, ij ijVar2, kv kvVar) {
        kvVar.getClass();
        if (kvVar instanceof s9) {
            return ((s9) kvVar).i(ijVar2, ijVar);
        }
        yj r = ijVar2.r();
        if (r == cr.e) {
            return new p20(ijVar2, ijVar, kvVar);
        }
        return new q20(ijVar2, r, kvVar, ijVar);
    }

    public static final void p(y5 y5Var, double d, double d2, double d3, double d4, double d5, double d6, double d7, boolean z, boolean z2) {
        double d8;
        double d9;
        boolean z3;
        double d10 = d5;
        double d11 = (d7 / 180.0d) * 3.141592653589793d;
        double cos = Math.cos(d11);
        double sin = Math.sin(d11);
        double d12 = ((d2 * sin) + (d * cos)) / d10;
        double d13 = ((d2 * cos) + ((-d) * sin)) / d6;
        double d14 = ((d4 * sin) + (d3 * cos)) / d10;
        double d15 = ((d4 * cos) + ((-d3) * sin)) / d6;
        double d16 = d12 - d14;
        double d17 = d13 - d15;
        double d18 = (d12 + d14) / 2.0d;
        double d19 = (d13 + d15) / 2.0d;
        double d20 = (d17 * d17) + (d16 * d16);
        if (d20 != 0.0d) {
            double d21 = (1.0d / d20) - 0.25d;
            if (d21 < 0.0d) {
                double sqrt = (float) (Math.sqrt(d20) / 1.99999d);
                p(y5Var, d, d2, d3, d4, d10 * sqrt, d6 * sqrt, d7, z, z2);
                return;
            }
            double sqrt2 = Math.sqrt(d21);
            double d22 = d16 * sqrt2;
            double d23 = sqrt2 * d17;
            if (z == z2) {
                d8 = d18 - d23;
                d9 = d19 + d22;
            } else {
                d8 = d18 + d23;
                d9 = d19 - d22;
            }
            double atan2 = Math.atan2(d13 - d9, d12 - d8);
            double atan22 = Math.atan2(d15 - d9, d14 - d8) - atan2;
            if (atan22 >= 0.0d) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (z2 != z3) {
                if (atan22 > 0.0d) {
                    atan22 -= 6.283185307179586d;
                } else {
                    atan22 += 6.283185307179586d;
                }
            }
            double d24 = d8 * d10;
            double d25 = d9 * d6;
            double d26 = (d24 * cos) - (d25 * sin);
            double d27 = (d25 * cos) + (d24 * sin);
            int ceil = (int) Math.ceil(Math.abs((atan22 * 4.0d) / 3.141592653589793d));
            double cos2 = Math.cos(d11);
            double sin2 = Math.sin(d11);
            double cos3 = Math.cos(atan2);
            double sin3 = Math.sin(atan2);
            double d28 = -d10;
            double d29 = d28 * cos2;
            double d30 = d6 * sin2;
            double d31 = (d29 * sin3) - (d30 * cos3);
            double d32 = d28 * sin2;
            double d33 = d6 * cos2;
            double d34 = (cos3 * d33) + (sin3 * d32);
            double d35 = atan22 / ceil;
            double d36 = atan2;
            double d37 = d31;
            int i = 0;
            double d38 = d34;
            double d39 = d2;
            while (i < ceil) {
                double d40 = d36 + d35;
                double sin4 = Math.sin(d40);
                double cos4 = Math.cos(d40);
                int i2 = ceil;
                double d41 = (((d10 * cos2) * cos4) + d26) - (d30 * sin4);
                double d42 = (d33 * sin4) + (d10 * sin2 * cos4) + d27;
                double d43 = (d29 * sin4) - (d30 * cos4);
                double d44 = (cos4 * d33) + (sin4 * d32);
                double d45 = d40 - d36;
                double tan = Math.tan(d45 / 2.0d);
                double sqrt3 = ((Math.sqrt(((tan * 3.0d) * tan) + 4.0d) - 1.0d) * Math.sin(d45)) / 3.0d;
                y5Var.a((float) ((d37 * sqrt3) + d), (float) ((d38 * sqrt3) + d39), (float) (d41 - (sqrt3 * d43)), (float) (d42 - (sqrt3 * d44)), (float) d41, (float) d42);
                d35 = d35;
                d = d41;
                i++;
                d32 = d32;
                sin2 = sin2;
                d26 = d26;
                d36 = d40;
                d38 = d44;
                d37 = d43;
                ceil = i2;
                d39 = d42;
                d10 = d5;
            }
        }
    }

    public static final pt q(ef0 ef0Var, wo0 wo0Var, int i) {
        wo0 d;
        pt ptVar = null;
        if (i == 3) {
            d = wo0Var.d((wo0Var.c - wo0Var.a) + 1.0f, 0.0f);
        } else if (i == 4) {
            d = wo0Var.d(-((wo0Var.c - wo0Var.a) + 1.0f), 0.0f);
        } else if (i == 5) {
            d = wo0Var.d(0.0f, (wo0Var.d - wo0Var.b) + 1.0f);
        } else if (i == 6) {
            d = wo0Var.d(0.0f, -((wo0Var.d - wo0Var.b) + 1.0f));
        } else {
            v7.o("This function should only be used for 2-D focus search");
            return null;
        }
        Object[] objArr = ef0Var.e;
        int i2 = ef0Var.g;
        for (int i3 = 0; i3 < i2; i3++) {
            pt ptVar2 = (pt) objArr[i3];
            if (n20.B(ptVar2)) {
                wo0 t = n20.t(ptVar2);
                if (x(t, d, wo0Var, i)) {
                    ptVar = ptVar2;
                    d = t;
                }
            }
        }
        return ptVar;
    }

    public static final boolean r(pt ptVar, int i, gv gvVar) {
        wo0 wo0Var;
        Object obj;
        ef0 ef0Var = new ef0(new pt[16]);
        m(ptVar, ef0Var);
        int i2 = ef0Var.g;
        if (i2 <= 1) {
            if (i2 == 0) {
                obj = null;
            } else {
                obj = ef0Var.e[0];
            }
            pt ptVar2 = (pt) obj;
            if (ptVar2 != null) {
                return ((Boolean) gvVar.e(ptVar2)).booleanValue();
            }
        } else {
            if (i == 7) {
                i = 4;
            }
            if (i == 4 || i == 6) {
                wo0 t = n20.t(ptVar);
                float f = t.a;
                float f2 = t.b;
                wo0Var = new wo0(f, f2, f, f2);
            } else if (i == 3 || i == 5) {
                wo0 t2 = n20.t(ptVar);
                float f3 = t2.c;
                float f4 = t2.d;
                wo0Var = new wo0(f3, f4, f3, f4);
            } else {
                v7.o("This function should only be used for 2-D focus search");
                return false;
            }
            pt q = q(ef0Var, wo0Var, i);
            if (q != null) {
                return ((Boolean) gvVar.e(q)).booleanValue();
            }
        }
        return false;
    }

    public static final boolean s(int i, oj ojVar, pt ptVar, wo0 wo0Var) {
        if (M(i, ojVar, ptVar, wo0Var)) {
            return true;
        }
        Boolean bool = (Boolean) o20.B(ptVar, i, new ph0(((lt) ((b4) k81.F(ptVar)).getFocusOwner()).f(), ptVar, wo0Var, i, ojVar, 1));
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public static ww0 t() {
        return (ww0) cx0.b.p();
    }

    public static final int u(KeyEvent keyEvent) {
        int action = keyEvent.getAction();
        if (action != 0) {
            if (action == 1) {
                return 1;
            }
            return 0;
        }
        return 2;
    }

    public static final int v(int i, int i2) {
        return (i >> i2) & 31;
    }

    public static ij w(ij ijVar) {
        jj jjVar;
        ij ijVar2;
        ijVar.getClass();
        if (ijVar instanceof jj) {
            jjVar = (jj) ijVar;
        } else {
            jjVar = null;
        }
        if (jjVar != null && (ijVar = jjVar.g) == null) {
            ak akVar = (ak) jjVar.r().j(x1.A);
            if (akVar != null) {
                ijVar2 = new in(akVar, jjVar);
            } else {
                ijVar2 = jjVar;
            }
            jjVar.g = ijVar2;
            return ijVar2;
        }
        return ijVar;
    }

    public static final boolean x(wo0 wo0Var, wo0 wo0Var2, wo0 wo0Var3, int i) {
        if (y(i, wo0Var, wo0Var3)) {
            if (y(i, wo0Var2, wo0Var3) && !j(wo0Var3, wo0Var, wo0Var2, i)) {
                if (!j(wo0Var3, wo0Var2, wo0Var, i) && z(i, wo0Var3, wo0Var) < z(i, wo0Var3, wo0Var2)) {
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    public static final boolean y(int i, wo0 wo0Var, wo0 wo0Var2) {
        if (i == 3) {
            float f = wo0Var2.c;
            float f2 = wo0Var2.a;
            float f3 = wo0Var.c;
            if ((f <= f3 && f2 < f3) || f2 <= wo0Var.a) {
                return false;
            }
            return true;
        }
        if (i == 4) {
            float f4 = wo0Var2.a;
            float f5 = wo0Var2.c;
            float f6 = wo0Var.a;
            if ((f4 >= f6 && f5 > f6) || f5 >= wo0Var.c) {
                return false;
            }
            return true;
        }
        if (i == 5) {
            float f7 = wo0Var2.d;
            float f8 = wo0Var2.b;
            float f9 = wo0Var.d;
            if ((f7 <= f9 && f8 < f9) || f8 <= wo0Var.b) {
                return false;
            }
            return true;
        }
        if (i == 6) {
            float f10 = wo0Var2.b;
            float f11 = wo0Var2.d;
            float f12 = wo0Var.b;
            if ((f10 >= f12 && f11 > f12) || f11 >= wo0Var.d) {
                return false;
            }
            return true;
        }
        v7.o("This function should only be used for 2-D focus search");
        return false;
    }

    public static final long z(int i, wo0 wo0Var, wo0 wo0Var2) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        if (i == 3) {
            f = wo0Var.a;
            f2 = wo0Var2.c;
        } else if (i == 4) {
            f = wo0Var2.a;
            f2 = wo0Var.c;
        } else if (i == 5) {
            f = wo0Var.b;
            f2 = wo0Var2.d;
        } else if (i == 6) {
            f = wo0Var2.b;
            f2 = wo0Var.d;
        } else {
            v7.o("This function should only be used for 2-D focus search");
            return 0L;
        }
        float f6 = f - f2;
        if (f6 < 0.0f) {
            f6 = 0.0f;
        }
        long j = f6;
        if (i == 3 || i == 4) {
            float f7 = wo0Var.b;
            f3 = ((wo0Var.d - f7) / 2.0f) + f7;
            f4 = wo0Var2.b;
            f5 = wo0Var2.d;
        } else if (i == 5 || i == 6) {
            float f8 = wo0Var.a;
            f3 = ((wo0Var.c - f8) / 2.0f) + f8;
            f4 = wo0Var2.a;
            f5 = wo0Var2.c;
        } else {
            v7.o("This function should only be used for 2-D focus search");
            return 0L;
        }
        long j2 = f3 - (((f5 - f4) / 2.0f) + f4);
        return (j2 * j2) + (13 * j * j);
    }
}
