package defpackage;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class jw0 {
    public int[] e = o4.c;
    public Object[] f = o4.e;
    public int g;

    public final int a(Object obj) {
        int i = this.g * 2;
        Object[] objArr = this.f;
        if (obj == null) {
            for (int i2 = 1; i2 < i; i2 += 2) {
                if (objArr[i2] == null) {
                    return i2 >> 1;
                }
            }
            return -1;
        }
        for (int i3 = 1; i3 < i; i3 += 2) {
            if (obj.equals(objArr[i3])) {
                return i3 >> 1;
            }
        }
        return -1;
    }

    public final int b(int i, Object obj) {
        int i2 = this.g;
        if (i2 == 0) {
            return -1;
        }
        int m = o4.m(this.e, i2, i);
        if (m < 0 || o20.e(obj, this.f[m << 1])) {
            return m;
        }
        int i3 = m + 1;
        while (i3 < i2 && this.e[i3] == i) {
            if (o20.e(obj, this.f[i3 << 1])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = m - 1; i4 >= 0 && this.e[i4] == i; i4--) {
            if (o20.e(obj, this.f[i4 << 1])) {
                return i4;
            }
        }
        return ~i3;
    }

    public final int c(Object obj) {
        if (obj == null) {
            return d();
        }
        return b(obj.hashCode(), obj);
    }

    public final void clear() {
        if (this.g > 0) {
            this.e = o4.c;
            this.f = o4.e;
            this.g = 0;
        }
        if (this.g <= 0) {
        } else {
            throw new ConcurrentModificationException();
        }
    }

    public boolean containsKey(Object obj) {
        if (c(obj) >= 0) {
            return true;
        }
        return false;
    }

    public boolean containsValue(Object obj) {
        if (a(obj) >= 0) {
            return true;
        }
        return false;
    }

    public final int d() {
        int i = this.g;
        if (i == 0) {
            return -1;
        }
        int m = o4.m(this.e, i, 0);
        if (m < 0 || this.f[m << 1] == null) {
            return m;
        }
        int i2 = m + 1;
        while (i2 < i && this.e[i2] == 0) {
            if (this.f[i2 << 1] == null) {
                return i2;
            }
            i2++;
        }
        for (int i3 = m - 1; i3 >= 0 && this.e[i3] == 0; i3--) {
            if (this.f[i3 << 1] == null) {
                return i3;
            }
        }
        return ~i2;
    }

    public final Object e(int i) {
        boolean z = false;
        if (i >= 0 && i < this.g) {
            z = true;
        }
        if (z) {
            return this.f[i << 1];
        }
        v7.g("Expected index to be within 0..size()-1, but was ", i);
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        try {
            if (obj instanceof jw0) {
                int i = this.g;
                if (i != ((jw0) obj).g) {
                    return false;
                }
                jw0 jw0Var = (jw0) obj;
                for (int i2 = 0; i2 < i; i2++) {
                    Object e = e(i2);
                    Object h = h(i2);
                    Object obj2 = jw0Var.get(e);
                    if (h == null) {
                        if (obj2 != null || !jw0Var.containsKey(e)) {
                            return false;
                        }
                    } else if (!h.equals(obj2)) {
                        return false;
                    }
                }
                return true;
            }
            if (!(obj instanceof Map) || this.g != ((Map) obj).size()) {
                return false;
            }
            int i3 = this.g;
            for (int i4 = 0; i4 < i3; i4++) {
                Object e2 = e(i4);
                Object h2 = h(i4);
                Object obj3 = ((Map) obj).get(e2);
                if (h2 == null) {
                    if (obj3 != null || !((Map) obj).containsKey(e2)) {
                        return false;
                    }
                } else if (!h2.equals(obj3)) {
                    return false;
                }
            }
            return true;
        } catch (ClassCastException | NullPointerException unused) {
        }
        return false;
    }

    public final Object f(int i) {
        int i2;
        if (i >= 0 && i < (i2 = this.g)) {
            Object[] objArr = this.f;
            int i3 = i << 1;
            Object obj = objArr[i3 + 1];
            if (i2 <= 1) {
                clear();
                return obj;
            }
            int i4 = i2 - 1;
            int[] iArr = this.e;
            int i5 = 8;
            if (iArr.length > 8 && i2 < iArr.length / 3) {
                if (i2 > 8) {
                    i5 = i2 + (i2 >> 1);
                }
                this.e = Arrays.copyOf(iArr, i5);
                this.f = Arrays.copyOf(this.f, i5 << 1);
                if (i2 == this.g) {
                    if (i > 0) {
                        i8.L(iArr, this.e, 0, 0, i);
                        i8.N(objArr, this.f, 0, 0, i3);
                    }
                    if (i < i4) {
                        int i6 = i + 1;
                        i8.L(iArr, this.e, i, i6, i2);
                        i8.N(objArr, this.f, i3, i6 << 1, i2 << 1);
                    }
                } else {
                    throw new ConcurrentModificationException();
                }
            } else {
                if (i < i4) {
                    int i7 = i + 1;
                    i8.L(iArr, iArr, i, i7, i2);
                    Object[] objArr2 = this.f;
                    i8.N(objArr2, objArr2, i3, i7 << 1, i2 << 1);
                }
                Object[] objArr3 = this.f;
                int i8 = i4 << 1;
                objArr3[i8] = null;
                objArr3[i8 + 1] = null;
            }
            if (i2 == this.g) {
                this.g = i4;
                return obj;
            }
            throw new ConcurrentModificationException();
        }
        v7.g("Expected index to be within 0..size()-1, but was ", i);
        return null;
    }

    public final Object g(int i, Object obj) {
        boolean z = false;
        if (i >= 0 && i < this.g) {
            z = true;
        }
        if (z) {
            int i2 = (i << 1) + 1;
            Object[] objArr = this.f;
            Object obj2 = objArr[i2];
            objArr[i2] = obj;
            return obj2;
        }
        v7.g("Expected index to be within 0..size()-1, but was ", i);
        return null;
    }

    public Object get(Object obj) {
        int c = c(obj);
        if (c >= 0) {
            return this.f[(c << 1) + 1];
        }
        return null;
    }

    public final Object getOrDefault(Object obj, Object obj2) {
        int c = c(obj);
        if (c >= 0) {
            return this.f[(c << 1) + 1];
        }
        return obj2;
    }

    public final Object h(int i) {
        boolean z = false;
        if (i >= 0 && i < this.g) {
            z = true;
        }
        if (z) {
            return this.f[(i << 1) + 1];
        }
        v7.g("Expected index to be within 0..size()-1, but was ", i);
        return null;
    }

    public final int hashCode() {
        int i;
        int[] iArr = this.e;
        Object[] objArr = this.f;
        int i2 = this.g;
        int i3 = 1;
        int i4 = 0;
        int i5 = 0;
        while (i4 < i2) {
            Object obj = objArr[i3];
            int i6 = iArr[i4];
            if (obj != null) {
                i = obj.hashCode();
            } else {
                i = 0;
            }
            i5 += i ^ i6;
            i4++;
            i3 += 2;
        }
        return i5;
    }

    public final boolean isEmpty() {
        if (this.g <= 0) {
            return true;
        }
        return false;
    }

    public final Object put(Object obj, Object obj2) {
        int i;
        int d;
        int i2 = this.g;
        if (obj != null) {
            i = obj.hashCode();
        } else {
            i = 0;
        }
        if (obj != null) {
            d = b(i, obj);
        } else {
            d = d();
        }
        if (d >= 0) {
            int i3 = (d << 1) + 1;
            Object[] objArr = this.f;
            Object obj3 = objArr[i3];
            objArr[i3] = obj2;
            return obj3;
        }
        int i4 = ~d;
        int[] iArr = this.e;
        if (i2 >= iArr.length) {
            int i5 = 8;
            if (i2 >= 8) {
                i5 = (i2 >> 1) + i2;
            } else if (i2 < 4) {
                i5 = 4;
            }
            this.e = Arrays.copyOf(iArr, i5);
            this.f = Arrays.copyOf(this.f, i5 << 1);
            if (i2 != this.g) {
                throw new ConcurrentModificationException();
            }
        }
        if (i4 < i2) {
            int[] iArr2 = this.e;
            int i6 = i4 + 1;
            i8.L(iArr2, iArr2, i6, i4, i2);
            Object[] objArr2 = this.f;
            i8.N(objArr2, objArr2, i6 << 1, i4 << 1, this.g << 1);
        }
        int i7 = this.g;
        if (i2 == i7) {
            int[] iArr3 = this.e;
            if (i4 < iArr3.length) {
                iArr3[i4] = i;
                Object[] objArr3 = this.f;
                int i8 = i4 << 1;
                objArr3[i8] = obj;
                objArr3[i8 + 1] = obj2;
                this.g = i7 + 1;
                return null;
            }
        }
        throw new ConcurrentModificationException();
    }

    public final Object putIfAbsent(Object obj, Object obj2) {
        Object obj3 = get(obj);
        if (obj3 == null) {
            return put(obj, obj2);
        }
        return obj3;
    }

    public final boolean remove(Object obj, Object obj2) {
        int c = c(obj);
        if (c >= 0 && o20.e(obj2, h(c))) {
            f(c);
            return true;
        }
        return false;
    }

    public final boolean replace(Object obj, Object obj2, Object obj3) {
        int c = c(obj);
        if (c >= 0 && o20.e(obj2, h(c))) {
            g(c, obj3);
            return true;
        }
        return false;
    }

    public final int size() {
        return this.g;
    }

    public final String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.g * 28);
        sb.append('{');
        int i = this.g;
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            Object e = e(i2);
            if (e != sb) {
                sb.append(e);
            } else {
                sb.append("(this Map)");
            }
            sb.append('=');
            Object h = h(i2);
            if (h != sb) {
                sb.append(h);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public Object remove(Object obj) {
        int c = c(obj);
        if (c >= 0) {
            return f(c);
        }
        return null;
    }

    public final Object replace(Object obj, Object obj2) {
        int c = c(obj);
        if (c >= 0) {
            return g(c, obj2);
        }
        return null;
    }
}
