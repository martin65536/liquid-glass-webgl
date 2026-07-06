package defpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class so0 extends sz0 implements lv {
    public List i;
    public List j;
    public List k;
    public we0 l;
    public we0 m;
    public we0 n;
    public Set o;
    public we0 p;
    public int q;
    public /* synthetic */ q6 r;
    public final /* synthetic */ to0 s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public so0(to0 to0Var, ij ijVar) {
        super(3, ijVar);
        this.s = to0Var;
    }

    public static final void m(to0 to0Var, List list, List list2, List list3, we0 we0Var, we0 we0Var2, we0 we0Var3, we0 we0Var4) {
        char c;
        long j;
        long j2;
        synchronized (to0Var.c) {
            try {
                list.clear();
                list2.clear();
                int size = list3.size();
                for (int i = 0; i < size; i++) {
                    yh yhVar = (yh) list3.get(i);
                    yhVar.a();
                    to0Var.R(yhVar);
                }
                list3.clear();
                Object[] objArr = we0Var.b;
                long[] jArr = we0Var.a;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i2 = 0;
                    j = 255;
                    while (true) {
                        long j3 = jArr[i2];
                        c = 7;
                        j2 = -9187201950435737472L;
                        if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i3 = 8 - ((~(i2 - length)) >>> 31);
                            for (int i4 = 0; i4 < i3; i4++) {
                                if ((j3 & 255) < 128) {
                                    yh yhVar2 = (yh) objArr[(i2 << 3) + i4];
                                    yhVar2.a();
                                    to0Var.R(yhVar2);
                                }
                                j3 >>= 8;
                            }
                            if (i3 != 8) {
                                break;
                            }
                        }
                        if (i2 == length) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                } else {
                    c = 7;
                    j = 255;
                    j2 = -9187201950435737472L;
                }
                we0Var.b();
                Object[] objArr2 = we0Var2.b;
                long[] jArr2 = we0Var2.a;
                int length2 = jArr2.length - 2;
                if (length2 >= 0) {
                    int i5 = 0;
                    while (true) {
                        long j4 = jArr2[i5];
                        if ((((~j4) << c) & j4 & j2) != j2) {
                            int i6 = 8 - ((~(i5 - length2)) >>> 31);
                            for (int i7 = 0; i7 < i6; i7++) {
                                if ((j4 & j) < 128) {
                                    ((yh) objArr2[(i5 << 3) + i7]).g();
                                }
                                j4 >>= 8;
                            }
                            if (i6 != 8) {
                                break;
                            }
                        }
                        if (i5 == length2) {
                            break;
                        } else {
                            i5++;
                        }
                    }
                }
                we0Var2.b();
                we0Var3.b();
                Object[] objArr3 = we0Var4.b;
                long[] jArr3 = we0Var4.a;
                int length3 = jArr3.length - 2;
                if (length3 >= 0) {
                    int i8 = 0;
                    while (true) {
                        long j5 = jArr3[i8];
                        if ((((~j5) << c) & j5 & j2) != j2) {
                            int i9 = 8 - ((~(i8 - length3)) >>> 31);
                            for (int i10 = 0; i10 < i9; i10++) {
                                if ((j5 & j) < 128) {
                                    yh yhVar3 = (yh) objArr3[(i8 << 3) + i10];
                                    yhVar3.a();
                                    to0Var.R(yhVar3);
                                }
                                j5 >>= 8;
                            }
                            if (i9 != 8) {
                                break;
                            }
                        }
                        if (i8 == length3) {
                            break;
                        } else {
                            i8++;
                        }
                    }
                }
                we0Var4.b();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static final void n(List list, to0 to0Var) {
        list.clear();
        synchronized (to0Var.c) {
            try {
                ArrayList arrayList = to0Var.k;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    list.add((wd0) arrayList.get(i));
                }
                to0Var.k.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        so0 so0Var = new so0(this.s, (ij) obj3);
        so0Var.r = (q6) obj2;
        so0Var.k(x31.a);
        return ik.e;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0098 A[DONT_GENERATE] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x00ee -> B:6:0x00f6). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0117 -> B:7:0x0093). Please report as a decompilation issue!!! */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r18) {
        /*
            Method dump skipped, instructions count: 289
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.so0.k(java.lang.Object):java.lang.Object");
    }
}
