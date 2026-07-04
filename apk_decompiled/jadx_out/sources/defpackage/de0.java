package defpackage;

import java.util.NoSuchElementException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class de0 {
    public final ve0 a;

    public static final Object a(ve0 ve0Var) {
        Object g = ve0Var.g(null);
        if (g == null) {
            return null;
        }
        if (g instanceof pe0) {
            pe0 pe0Var = (pe0) g;
            if (!pe0Var.h()) {
                int i = pe0Var.b - 1;
                Object f = pe0Var.f(i);
                pe0Var.k(i);
                f.getClass();
                if (pe0Var.h()) {
                    ve0Var.k(null);
                }
                if (pe0Var.b == 1) {
                    ve0Var.m(null, pe0Var.e());
                }
                return f;
            }
            throw new NoSuchElementException("List is empty.");
        }
        ve0Var.k(null);
        return g;
    }

    public static final pe0 b(ve0 ve0Var) {
        if (ve0Var.i()) {
            pe0 pe0Var = yg0.b;
            pe0Var.getClass();
            return pe0Var;
        }
        pe0 pe0Var2 = new pe0();
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
                        if ((255 & j) < 128) {
                            Object obj = objArr[(i << 3) + i3];
                            if (obj instanceof pe0) {
                                pe0Var2.b((pe0) obj);
                            } else {
                                obj.getClass();
                                pe0Var2.a(obj);
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
        return pe0Var2;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof de0) {
            if (!this.a.equals(((de0) obj).a)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "MultiValueMap(map=" + this.a + ')';
    }
}
