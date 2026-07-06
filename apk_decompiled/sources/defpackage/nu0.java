package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class nu0 implements bv0, Iterable, q30 {
    public final ve0 e;
    public ic0 f;
    public boolean g;
    public boolean h;

    public nu0() {
        long[] jArr = zs0.a;
        this.e = new ve0();
    }

    @Override // defpackage.bv0
    public final void a(av0 av0Var, Object obj) {
        boolean z = obj instanceof n0;
        ve0 ve0Var = this.e;
        if (z && ve0Var.c(av0Var)) {
            Object g = ve0Var.g(av0Var);
            g.getClass();
            n0 n0Var = (n0) g;
            n0 n0Var2 = (n0) obj;
            String str = n0Var2.a;
            if (str == null) {
                str = n0Var.a;
            }
            sv svVar = n0Var2.b;
            if (svVar == null) {
                svVar = n0Var.b;
            }
            ve0Var.m(av0Var, new n0(str, svVar));
        } else {
            ve0Var.m(av0Var, obj);
        }
        av0Var.getClass();
    }

    public final boolean b(av0 av0Var) {
        return this.e.c(av0Var);
    }

    public final nu0 c() {
        nu0 nu0Var = new nu0();
        nu0Var.g = this.g;
        nu0Var.h = this.h;
        ve0 ve0Var = nu0Var.e;
        ve0Var.getClass();
        ve0 ve0Var2 = this.e;
        ve0Var2.getClass();
        Object[] objArr = ve0Var2.b;
        Object[] objArr2 = ve0Var2.c;
        long[] jArr = ve0Var2.a;
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
                            ve0Var.m(objArr[i4], objArr2[i4]);
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
        return nu0Var;
    }

    public final Object d(av0 av0Var) {
        Object g = this.e.g(av0Var);
        if (g != null) {
            return g;
        }
        throw new IllegalStateException("Key not present: " + av0Var + " - consider getOrElse or getOrNull");
    }

    public final void e(nu0 nu0Var) {
        ve0 ve0Var = nu0Var.e;
        Object[] objArr = ve0Var.b;
        Object[] objArr2 = ve0Var.c;
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
                            Object obj = objArr[i4];
                            Object obj2 = objArr2[i4];
                            av0 av0Var = (av0) obj;
                            ve0 ve0Var2 = this.e;
                            Object g = ve0Var2.g(av0Var);
                            av0Var.getClass();
                            Object d = av0Var.b.d(g, obj2);
                            if (d != null) {
                                ve0Var2.m(av0Var, d);
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

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof nu0) {
                nu0 nu0Var = (nu0) obj;
                if (!o20.e(this.e, nu0Var.e) || this.g != nu0Var.g || this.h != nu0Var.h) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int hashCode = this.e.hashCode() * 31;
        int i2 = 1237;
        if (this.g) {
            i = 1231;
        } else {
            i = 1237;
        }
        int i3 = (hashCode + i) * 31;
        if (this.h) {
            i2 = 1231;
        }
        return i3 + i2;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        ic0 ic0Var = this.f;
        if (ic0Var == null) {
            ve0 ve0Var = this.e;
            ve0Var.getClass();
            ic0 ic0Var2 = new ic0(ve0Var);
            this.f = ic0Var2;
            ic0Var = ic0Var2;
        }
        return ((kr) ic0Var.entrySet()).iterator();
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        if (this.g) {
            sb.append("mergeDescendants=true");
            str = ", ";
        } else {
            str = "";
        }
        if (this.h) {
            sb.append(str);
            sb.append("isClearingSemantics=true");
            str = ", ";
        }
        ve0 ve0Var = this.e;
        Object[] objArr = ve0Var.b;
        Object[] objArr2 = ve0Var.c;
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
                            Object obj = objArr[i4];
                            Object obj2 = objArr2[i4];
                            sb.append(str);
                            sb.append(((av0) obj).a);
                            sb.append(" : ");
                            sb.append(obj2);
                            str = ", ";
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
        return n30.F(this) + "{ " + ((Object) sb) + " }";
    }
}
