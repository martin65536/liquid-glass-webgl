package defpackage;

import java.util.Set;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class wa implements kv {
    public final /* synthetic */ int e;
    public final /* synthetic */ Object f;

    public /* synthetic */ wa(int i, int i2, Object obj) {
        this.e = i2;
        this.f = obj;
    }

    private final Object f(Object obj, Object obj2) {
        nc ncVar;
        to0 to0Var = (to0) this.f;
        Set set = (Set) obj;
        synchronized (to0Var.c) {
            try {
                if (((po0) to0Var.u.getValue()).compareTo(po0.i) >= 0) {
                    we0 we0Var = to0Var.h;
                    if (set instanceof bt0) {
                        we0 we0Var2 = ((bt0) set).e;
                        Object[] objArr = we0Var2.b;
                        long[] jArr = we0Var2.a;
                        int length = jArr.length - 2;
                        if (length >= 0) {
                            int i = 0;
                            while (true) {
                                long j = jArr[i];
                                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                    int i2 = 8 - ((~(i - length)) >>> 31);
                                    for (int i3 = 0; i3 < i2; i3++) {
                                        if ((255 & j) < 128) {
                                            Object obj3 = objArr[(i << 3) + i3];
                                            if (!(obj3 instanceof oy0) || ((oy0) obj3).e(1)) {
                                                we0Var.a(obj3);
                                            }
                                        }
                                        j >>= 8;
                                    }
                                    if (i2 != 8) {
                                        break;
                                    }
                                }
                                if (i == length) {
                                    break;
                                }
                                i++;
                            }
                        }
                    } else {
                        for (Object obj4 : set) {
                            if (!(obj4 instanceof oy0) || ((oy0) obj4).e(1)) {
                                we0Var.a(obj4);
                            }
                        }
                    }
                    ncVar = to0Var.D();
                } else {
                    ncVar = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (ncVar != null) {
            ((pc) ncVar).u(x31.a);
        }
        return x31.a;
    }

    private final Object g(Object obj, Object obj2) {
        jv0 jv0Var;
        lw0 lw0Var = (lw0) this.f;
        Set set = (Set) obj;
        synchronized (lw0Var.a) {
            try {
                we0 we0Var = lw0Var.d;
                if (we0Var == null) {
                    if (me.R(set, lw0Var.b)) {
                        jv0Var = lw0Var.f;
                    }
                    jv0Var = null;
                } else {
                    Object[] objArr = we0Var.b;
                    long[] jArr = we0Var.a;
                    int length = jArr.length - 2;
                    if (length >= 0) {
                        int i = 0;
                        loop0: while (true) {
                            long j = jArr[i];
                            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                int i2 = 8 - ((~(i - length)) >>> 31);
                                for (int i3 = 0; i3 < i2; i3++) {
                                    if ((255 & j) < 128 && set.contains(objArr[(i << 3) + i3])) {
                                        jv0Var = lw0Var.f;
                                        break loop0;
                                    }
                                    j >>= 8;
                                }
                                if (i2 != 8) {
                                    break;
                                }
                            }
                            if (i == length) {
                                break;
                            }
                            i++;
                        }
                    }
                    jv0Var = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (jv0Var != null) {
            jv0Var.q(x31.a);
        }
        return x31.a;
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x00d8, code lost:
    
        if (r4 == null) goto L47;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00df  */
    @Override // defpackage.kv
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object d(java.lang.Object r22, java.lang.Object r23) {
        /*
            Method dump skipped, instructions count: 700
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.wa.d(java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public /* synthetic */ wa(int i, Object obj) {
        this.e = i;
        this.f = obj;
    }
}
