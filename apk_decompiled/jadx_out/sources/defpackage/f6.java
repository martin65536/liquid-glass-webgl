package defpackage;

import android.os.Bundle;
import android.view.ViewParent;
import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class f6 implements vu {
    public final /* synthetic */ int e;
    public final /* synthetic */ Object f;

    public /* synthetic */ f6(int i, Object obj) {
        this.e = i;
        this.f = obj;
    }

    @Override // defpackage.vu
    public final Object a() {
        int i;
        e5 e5Var = null;
        switch (this.e) {
            case 0:
                o20.t((g6) this.f);
                return x31.a;
            case 1:
                ((op) this.f).D0();
                return x31.a;
            case 2:
                return Integer.valueOf(((m70) this.f).g().n);
            case 3:
                return new lh0((mh0) this.f);
            case 4:
                bs0 bs0Var = (bs0) this.f;
                ss0 ss0Var = bs0Var.e;
                Object obj = bs0Var.h;
                if (obj != null) {
                    return ss0Var.j(bs0Var, obj);
                }
                v7.m("Value should be initialized");
                return null;
            case 5:
                c4 c4Var = ((hs0) this.f).g;
                if (c4Var == null) {
                    return null;
                }
                Bundle l = k81.l((xj0[]) Arrays.copyOf(new xj0[0], 0));
                c4Var.t(l);
                if (l.isEmpty()) {
                    return null;
                }
                return l;
            case 6:
                ps0 ps0Var = (ps0) this.f;
                ps0Var.f().a(new vo0(0, ps0Var));
                return x31.a;
            case 7:
                pt0 pt0Var = (pt0) this.f;
                f5 f5Var = (f5) n20.p(pt0Var, kj0.a);
                pt0Var.D = f5Var;
                if (f5Var != null) {
                    e5Var = new e5(f5Var.a, f5Var.b, f5Var.c, f5Var.d);
                }
                pt0Var.E = e5Var;
                return x31.a;
            case 8:
                return (ViewParent) this.f;
            case 9:
                rv0 rv0Var = (rv0) this.f;
                ik0 ik0Var = rv0Var.g;
                if (((mw0) ik0Var.getValue()).a == 9205357640488583168L || mw0.c(((mw0) ik0Var.getValue()).a)) {
                    return null;
                }
                return rv0Var.e.N(((mw0) ik0Var.getValue()).a);
            case 10:
                ox0 ox0Var = (ox0) this.f;
                do {
                    synchronized (ox0Var.g) {
                        try {
                            if (!ox0Var.c) {
                                ox0Var.c = true;
                                try {
                                    ef0 ef0Var = ox0Var.f;
                                    Object[] objArr = ef0Var.e;
                                    int i2 = ef0Var.g;
                                    for (int i3 = 0; i3 < i2; i3++) {
                                        nx0 nx0Var = (nx0) objArr[i3];
                                        we0 we0Var = nx0Var.g;
                                        gv gvVar = nx0Var.a;
                                        Object[] objArr2 = we0Var.b;
                                        long[] jArr = we0Var.a;
                                        int length = jArr.length - 2;
                                        if (length >= 0) {
                                            int i4 = 0;
                                            while (true) {
                                                long j = jArr[i4];
                                                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                                    int i5 = 8;
                                                    int i6 = 8 - ((~(i4 - length)) >>> 31);
                                                    int i7 = 0;
                                                    while (i7 < i6) {
                                                        if ((j & 255) < 128) {
                                                            i = i5;
                                                            gvVar.e(objArr2[(i4 << 3) + i7]);
                                                        } else {
                                                            i = i5;
                                                        }
                                                        j >>= i;
                                                        i7++;
                                                        i5 = i;
                                                    }
                                                    if (i6 != i5) {
                                                    }
                                                }
                                                if (i4 != length) {
                                                    i4++;
                                                }
                                            }
                                        }
                                        we0Var.b();
                                    }
                                    ox0Var.c = false;
                                } catch (Throwable th) {
                                    ox0Var.c = false;
                                    throw th;
                                }
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                } while (ox0Var.a());
                return x31.a;
            case 11:
                return (a11) this.f;
            default:
                q11 q11Var = (q11) this.f;
                q11Var.E = null;
                m20.w(q11Var);
                m20.v(q11Var);
                o20.t(q11Var);
                return Boolean.TRUE;
        }
    }
}
