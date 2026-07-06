package defpackage;

import java.util.Arrays;
import java.util.ListIterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xl0 extends b0 {
    public final Object[] e;
    public final Object[] f;
    public final int g;
    public final int h;

    public xl0(Object[] objArr, Object[] objArr2, int i, int i2) {
        boolean z;
        this.e = objArr;
        this.f = objArr2;
        this.g = i;
        this.h = i2;
        if (a() > 32) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            cn0.a("Trie-based persistent vector should have at least 33 elements, got " + a());
        }
        int length = objArr2.length;
    }

    public static Object[] i(Object[] objArr, int i, int i2, Object obj, j1 j1Var) {
        Object[] copyOf;
        int v = t20.v(i2, i);
        if (i == 0) {
            if (v == 0) {
                copyOf = new Object[32];
            } else {
                copyOf = Arrays.copyOf(objArr, 32);
            }
            i8.N(objArr, copyOf, v + 1, v, 31);
            j1Var.a = objArr[31];
            copyOf[v] = obj;
            return copyOf;
        }
        Object[] copyOf2 = Arrays.copyOf(objArr, 32);
        int i3 = i - 5;
        Object obj2 = objArr[v];
        obj2.getClass();
        copyOf2[v] = i((Object[]) obj2, i3, i2, obj, j1Var);
        while (true) {
            v++;
            if (v >= 32 || copyOf2[v] == null) {
                break;
            }
            Object obj3 = objArr[v];
            obj3.getClass();
            copyOf2[v] = i((Object[]) obj3, i3, 0, j1Var.a, j1Var);
        }
        return copyOf2;
    }

    public static Object[] k(Object[] objArr, int i, int i2, j1 j1Var) {
        Object[] k;
        int v = t20.v(i2, i);
        if (i == 5) {
            j1Var.a = objArr[v];
            k = null;
        } else {
            Object obj = objArr[v];
            obj.getClass();
            k = k((Object[]) obj, i - 5, i2, j1Var);
        }
        if (k == null && v == 0) {
            return null;
        }
        Object[] copyOf = Arrays.copyOf(objArr, 32);
        copyOf[v] = k;
        return copyOf;
    }

    public static Object[] q(Object[] objArr, int i, int i2, Object obj) {
        int v = t20.v(i2, i);
        Object[] copyOf = Arrays.copyOf(objArr, 32);
        if (i == 0) {
            copyOf[v] = obj;
            return copyOf;
        }
        Object obj2 = copyOf[v];
        obj2.getClass();
        copyOf[v] = q((Object[]) obj2, i - 5, i2, obj);
        return copyOf;
    }

    @Override // defpackage.m
    public final int a() {
        return this.g;
    }

    @Override // defpackage.b0
    public final b0 b(int i, Object obj) {
        int i2 = this.g;
        m20.l(i, i2);
        if (i == i2) {
            return c(obj);
        }
        int p = p();
        Object[] objArr = this.e;
        if (i >= p) {
            return j(objArr, i - p, obj);
        }
        j1 j1Var = new j1(null);
        return j(i(objArr, this.h, i, obj, j1Var), 0, j1Var.a);
    }

    @Override // defpackage.b0
    public final b0 c(Object obj) {
        int p = p();
        int i = this.g;
        int i2 = i - p;
        Object[] objArr = this.e;
        Object[] objArr2 = this.f;
        if (i2 < 32) {
            Object[] copyOf = Arrays.copyOf(objArr2, 32);
            copyOf[i2] = obj;
            return new xl0(objArr, copyOf, i + 1, this.h);
        }
        Object[] objArr3 = new Object[32];
        objArr3[0] = obj;
        return l(objArr, objArr2, objArr3);
    }

    @Override // defpackage.b0
    public final yl0 e() {
        return new yl0(this, this.e, this.f, this.h);
    }

    @Override // defpackage.b0
    public final b0 f(a0 a0Var) {
        yl0 yl0Var = new yl0(this, this.e, this.f, this.h);
        yl0Var.y(a0Var);
        return yl0Var.c();
    }

    @Override // defpackage.b0
    public final b0 g(int i) {
        m20.j(i, a());
        int p = p();
        int i2 = this.h;
        Object[] objArr = this.e;
        if (i >= p) {
            return o(objArr, p, i2, i - p);
        }
        return o(n(objArr, i2, i, new j1(this.f[0])), p, i2, 0);
    }

    @Override // java.util.List
    public final Object get(int i) {
        Object[] objArr;
        m20.j(i, a());
        if (p() <= i) {
            objArr = this.f;
        } else {
            Object[] objArr2 = this.e;
            for (int i2 = this.h; i2 > 0; i2 -= 5) {
                Object[] objArr3 = objArr2[t20.v(i, i2)];
                objArr3.getClass();
                objArr2 = objArr3;
            }
            objArr = objArr2;
        }
        return objArr[i & 31];
    }

    @Override // defpackage.b0
    public final b0 h(int i, Object obj) {
        int i2 = this.g;
        m20.j(i, i2);
        int p = p();
        Object[] objArr = this.e;
        Object[] objArr2 = this.f;
        int i3 = this.h;
        if (p <= i) {
            Object[] copyOf = Arrays.copyOf(objArr2, 32);
            copyOf[i & 31] = obj;
            return new xl0(objArr, copyOf, i2, i3);
        }
        return new xl0(q(objArr, i3, i, obj), objArr2, i2, i3);
    }

    public final xl0 j(Object[] objArr, int i, Object obj) {
        int p = p();
        int i2 = this.g;
        int i3 = i2 - p;
        Object[] objArr2 = this.f;
        Object[] copyOf = Arrays.copyOf(objArr2, 32);
        if (i3 < 32) {
            i8.N(objArr2, copyOf, i + 1, i, i3);
            copyOf[i] = obj;
            return new xl0(objArr, copyOf, i2 + 1, this.h);
        }
        Object obj2 = objArr2[31];
        i8.N(objArr2, copyOf, i + 1, i, i3 - 1);
        copyOf[i] = obj;
        Object[] objArr3 = new Object[32];
        objArr3[0] = obj2;
        return l(objArr, copyOf, objArr3);
    }

    public final xl0 l(Object[] objArr, Object[] objArr2, Object[] objArr3) {
        int i = this.g;
        int i2 = i >> 5;
        int i3 = this.h;
        if (i2 > (1 << i3)) {
            Object[] objArr4 = new Object[32];
            objArr4[0] = objArr;
            int i4 = i3 + 5;
            return new xl0(m(i4, objArr4, objArr2), objArr3, i + 1, i4);
        }
        return new xl0(m(i3, objArr, objArr2), objArr3, i + 1, i3);
    }

    @Override // defpackage.w, java.util.List
    public final ListIterator listIterator(int i) {
        m20.l(i, this.g);
        return new zl0(this.e, this.f, i, this.g, (this.h / 5) + 1);
    }

    public final Object[] m(int i, Object[] objArr, Object[] objArr2) {
        Object[] objArr3;
        int v = t20.v(a() - 1, i);
        if (objArr != null) {
            objArr3 = Arrays.copyOf(objArr, 32);
        } else {
            objArr3 = new Object[32];
        }
        if (i == 5) {
            objArr3[v] = objArr2;
            return objArr3;
        }
        objArr3[v] = m(i - 5, (Object[]) objArr3[v], objArr2);
        return objArr3;
    }

    public final Object[] n(Object[] objArr, int i, int i2, j1 j1Var) {
        Object[] copyOf;
        int v = t20.v(i2, i);
        int i3 = 31;
        if (i == 0) {
            if (v == 0) {
                copyOf = new Object[32];
            } else {
                copyOf = Arrays.copyOf(objArr, 32);
            }
            i8.N(objArr, copyOf, v, v + 1, 32);
            copyOf[31] = j1Var.a;
            j1Var.a = objArr[v];
            return copyOf;
        }
        if (objArr[31] == null) {
            i3 = t20.v(p() - 1, i);
        }
        Object[] copyOf2 = Arrays.copyOf(objArr, 32);
        int i4 = i - 5;
        int i5 = v + 1;
        if (i5 <= i3) {
            while (true) {
                Object obj = copyOf2[i3];
                obj.getClass();
                copyOf2[i3] = n((Object[]) obj, i4, 0, j1Var);
                if (i3 == i5) {
                    break;
                }
                i3--;
            }
        }
        Object obj2 = copyOf2[v];
        obj2.getClass();
        copyOf2[v] = n((Object[]) obj2, i4, i2, j1Var);
        return copyOf2;
    }

    public final b0 o(Object[] objArr, int i, int i2, int i3) {
        int i4 = this.g - i;
        if (i4 == 1) {
            if (i2 == 0) {
                if (objArr.length == 33) {
                    objArr = Arrays.copyOf(objArr, 32);
                }
                return new vw0(objArr);
            }
            j1 j1Var = new j1(null);
            Object[] k = k(objArr, i2, i - 1, j1Var);
            k.getClass();
            Object obj = j1Var.a;
            obj.getClass();
            Object[] objArr2 = (Object[]) obj;
            if (k[1] == null) {
                Object obj2 = k[0];
                obj2.getClass();
                return new xl0((Object[]) obj2, objArr2, i, i2 - 5);
            }
            return new xl0(k, objArr2, i, i2);
        }
        Object[] objArr3 = this.f;
        Object[] copyOf = Arrays.copyOf(objArr3, 32);
        int i5 = i4 - 1;
        if (i3 < i5) {
            i8.N(objArr3, copyOf, i3, i3 + 1, i4);
        }
        copyOf[i5] = null;
        return new xl0(objArr, copyOf, (i + i4) - 1, i2);
    }

    public final int p() {
        return (this.g - 1) & (-32);
    }
}
