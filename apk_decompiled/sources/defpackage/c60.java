package defpackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class c60 {
    public final ve0 a;
    public p5 b;
    public final we0 c;
    public final ArrayList d;
    public final ArrayList e;
    public final ArrayList f;
    public final ArrayList g;
    public final ArrayList h;
    public final cd0 i;

    public c60() {
        long[] jArr = zs0.a;
        this.a = new ve0();
        we0 we0Var = at0.a;
        this.c = new we0();
        this.d = new ArrayList();
        this.e = new ArrayList();
        this.f = new ArrayList();
        this.g = new ArrayList();
        this.h = new ArrayList();
        this.i = new z50(this);
    }

    public static int e(int[] iArr, i70 i70Var) {
        i70Var.getClass();
        int i = iArr[0] + i70Var.l;
        iArr[0] = i;
        return Math.max(0, i);
    }

    public final long a() {
        ArrayList arrayList = this.h;
        if (arrayList.size() <= 0) {
            return 0L;
        }
        d3.z(arrayList.get(0));
        throw null;
    }

    public final void b(int i, int i2, ArrayList arrayList, p5 p5Var, f70 f70Var, boolean z, boolean z2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        we0 we0Var;
        p5 p5Var2 = this.b;
        this.b = p5Var;
        int size = arrayList.size();
        for (int i11 = 0; i11 < size; i11++) {
            i70 i70Var = (i70) arrayList.get(i11);
            int size2 = i70Var.b.size();
            for (int i12 = 0; i12 < size2; i12++) {
                ((em0) i70Var.b.get(i12)).A();
            }
        }
        ve0 ve0Var = this.a;
        if (ve0Var.i()) {
            c();
            return;
        }
        boolean z3 = z || !z2;
        Object[] objArr = ve0Var.b;
        long[] jArr = ve0Var.a;
        int length = jArr.length - 2;
        we0 we0Var2 = this.c;
        int i13 = 8;
        boolean z4 = z3;
        if (length >= 0) {
            int i14 = 0;
            while (true) {
                long j = jArr[i14];
                we0 we0Var3 = we0Var2;
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i15 = 8 - ((~(i14 - length)) >>> 31);
                    long j2 = j;
                    int i16 = 0;
                    while (i16 < i15) {
                        if ((j2 & 255) < 128) {
                            we0Var = we0Var3;
                            we0Var.a(objArr[(i14 << 3) + i16]);
                        } else {
                            we0Var = we0Var3;
                        }
                        j2 >>= 8;
                        i16++;
                        we0Var3 = we0Var;
                    }
                    we0Var2 = we0Var3;
                    if (i15 != 8) {
                        break;
                    }
                } else {
                    we0Var2 = we0Var3;
                }
                if (i14 == length) {
                    break;
                } else {
                    i14++;
                }
            }
        }
        int size3 = arrayList.size();
        for (int i17 = 0; i17 < size3; i17++) {
            i70 i70Var2 = (i70) arrayList.get(i17);
            Object obj = i70Var2.g;
            List list = i70Var2.b;
            we0Var2.l(obj);
            int size4 = list.size();
            for (int i18 = 0; i18 < size4; i18++) {
                ((em0) list.get(i18)).A();
            }
            d3.z(ve0Var.k(i70Var2.g));
        }
        int[] iArr = new int[1];
        ArrayList arrayList2 = this.e;
        ArrayList arrayList3 = this.d;
        if (z4 && p5Var2 != null) {
            if (arrayList3.isEmpty()) {
                i10 = 0;
            } else {
                if (arrayList3.size() > 1) {
                    qe.O(arrayList3, new b60(p5Var2, 2));
                }
                if (arrayList3.size() <= 0) {
                    i10 = 0;
                    Arrays.fill(iArr, 0, 1, 0);
                } else {
                    i70 i70Var3 = (i70) arrayList3.get(0);
                    e(iArr, i70Var3);
                    Object g = ve0Var.g(i70Var3.g);
                    g.getClass();
                    d3.z(g);
                    i70Var3.a(0);
                    throw null;
                }
            }
            if (!arrayList2.isEmpty()) {
                if (arrayList2.size() > 1) {
                    qe.O(arrayList2, new b60(p5Var2, i10));
                }
                if (arrayList2.size() <= 0) {
                    Arrays.fill(iArr, i10, 1, i10);
                } else {
                    i70 i70Var4 = (i70) arrayList2.get(i10);
                    e(iArr, i70Var4);
                    Object g2 = ve0Var.g(i70Var4.g);
                    g2.getClass();
                    d3.z(g2);
                    i70Var4.a(i10);
                    throw null;
                }
            }
        }
        Object[] objArr2 = we0Var2.b;
        long[] jArr2 = we0Var2.a;
        int length2 = jArr2.length - 2;
        if (length2 >= 0) {
            int i19 = 0;
            while (true) {
                long j3 = jArr2[i19];
                if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i20 = 8 - ((~(i19 - length2)) >>> 31);
                    int i21 = 0;
                    while (i21 < i20) {
                        if ((j3 & 255) < 128) {
                            i9 = i13;
                            d3.z(ve0Var.g(objArr2[(i19 << 3) + i21]));
                        } else {
                            i9 = i13;
                        }
                        j3 >>= i9;
                        i21++;
                        i13 = i9;
                    }
                    i8 = i13;
                    if (i20 != i8) {
                        break;
                    }
                } else {
                    i8 = i13;
                }
                if (i19 == length2) {
                    break;
                }
                i19++;
                i13 = i8;
            }
        }
        ArrayList arrayList4 = this.f;
        if (arrayList4.isEmpty()) {
            i5 = i;
            i6 = i2;
            i7 = 1;
        } else {
            if (arrayList4.size() > 1) {
                qe.O(arrayList4, new b60(p5Var, 3));
            }
            int size5 = arrayList4.size();
            for (int i22 = 0; i22 < size5; i22++) {
                i70 i70Var5 = (i70) arrayList4.get(i22);
                Object g3 = ve0Var.g(i70Var5.g);
                g3.getClass();
                d3.z(g3);
                i70Var5.c((z ? (int) (4294967295L & ((i70) me.S(arrayList)).a(0)) : 0) - e(iArr, i70Var5), i, i2);
                if (z4) {
                    d(i70Var5, true);
                    throw null;
                }
            }
            i5 = i;
            i6 = i2;
            i7 = 1;
            Arrays.fill(iArr, 0, 1, 0);
        }
        ArrayList arrayList5 = this.g;
        if (!arrayList5.isEmpty()) {
            if (arrayList5.size() > i7) {
                qe.O(arrayList5, new b60(p5Var, i7));
            }
            int size6 = arrayList5.size();
            for (int i23 = 0; i23 < size6; i23++) {
                i70 i70Var6 = (i70) arrayList5.get(i23);
                Object g4 = ve0Var.g(i70Var6.g);
                g4.getClass();
                d3.z(g4);
                i70Var6.c((0 - i70Var6.l) + e(iArr, i70Var6), i5, i6);
                if (z4) {
                    d(i70Var6, true);
                    throw null;
                }
            }
        }
        Collections.reverse(arrayList4);
        arrayList.addAll(0, arrayList4);
        arrayList.addAll(arrayList5);
        arrayList3.clear();
        arrayList2.clear();
        arrayList4.clear();
        arrayList5.clear();
        we0Var2.b();
    }

    public final void c() {
        ve0 ve0Var = this.a;
        if (ve0Var.j()) {
            Object[] objArr = ve0Var.c;
            long[] jArr = ve0Var.a;
            int length = jArr.length - 2;
            if (length >= 0) {
                int i = 0;
                while (true) {
                    long j = jArr[i];
                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i2 = 8 - ((~(i - length)) >>> 31);
                        for (int i3 = 0; i3 < i2; i3++) {
                            if ((255 & j) >= 128) {
                                j >>= 8;
                            } else {
                                d3.z(objArr[(i << 3) + i3]);
                                throw null;
                            }
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
        }
    }

    public final void d(i70 i70Var, boolean z) {
        Object g = this.a.g(i70Var.g);
        g.getClass();
        d3.z(g);
        throw null;
    }
}
