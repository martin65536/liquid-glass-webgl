package defpackage;

import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ef0 implements RandomAccess {
    public Object[] e;
    public bf0 f;
    public int g = 0;

    public ef0(Object[] objArr) {
        this.e = objArr;
    }

    public final void a(int i, Object obj) {
        int i2 = this.g + 1;
        if (this.e.length < i2) {
            m(i2);
        }
        Object[] objArr = this.e;
        int i3 = this.g;
        if (i != i3) {
            System.arraycopy(objArr, i, objArr, i + 1, i3 - i);
        }
        objArr[i] = obj;
        this.g++;
    }

    public final void b(Object obj) {
        int i = this.g + 1;
        if (this.e.length < i) {
            m(i);
        }
        Object[] objArr = this.e;
        int i2 = this.g;
        objArr[i2] = obj;
        this.g = i2 + 1;
    }

    public final void c(int i, ef0 ef0Var) {
        int i2 = ef0Var.g;
        if (i2 == 0) {
            return;
        }
        int i3 = this.g + i2;
        if (this.e.length < i3) {
            m(i3);
        }
        Object[] objArr = this.e;
        int i4 = this.g;
        if (i != i4) {
            System.arraycopy(objArr, i, objArr, i + i2, i4 - i);
        }
        System.arraycopy(ef0Var.e, 0, objArr, i, i2);
        this.g += i2;
    }

    public final void d(int i, List list) {
        if (list.isEmpty()) {
            return;
        }
        int size = list.size();
        int i2 = this.g + size;
        if (this.e.length < i2) {
            m(i2);
        }
        Object[] objArr = this.e;
        int i3 = this.g;
        if (i != i3) {
            System.arraycopy(objArr, i, objArr, i + size, i3 - i);
        }
        int size2 = list.size();
        for (int i4 = 0; i4 < size2; i4++) {
            objArr[i + i4] = list.get(i4);
        }
        this.g += size;
    }

    public final boolean e(int i, Collection collection) {
        int i2 = 0;
        if (collection.isEmpty()) {
            return false;
        }
        int size = collection.size();
        int i3 = this.g + size;
        if (this.e.length < i3) {
            m(i3);
        }
        Object[] objArr = this.e;
        int i4 = this.g;
        if (i != i4) {
            System.arraycopy(objArr, i, objArr, i + size, i4 - i);
        }
        for (Object obj : collection) {
            int i5 = i2 + 1;
            if (i2 >= 0) {
                objArr[i2 + i] = obj;
                i2 = i5;
            } else {
                jc0.H();
                throw null;
            }
        }
        this.g += size;
        return true;
    }

    public final List f() {
        bf0 bf0Var = this.f;
        if (bf0Var == null) {
            bf0 bf0Var2 = new bf0(this);
            this.f = bf0Var2;
            return bf0Var2;
        }
        return bf0Var;
    }

    public final void g() {
        Object[] objArr = this.e;
        int i = this.g;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.g = 0;
    }

    public final boolean h(Object obj) {
        int i = this.g - 1;
        if (i >= 0) {
            for (int i2 = 0; !o20.e(this.e[i2], obj); i2++) {
                if (i2 != i) {
                }
            }
            return true;
        }
        return false;
    }

    public final int i(Object obj) {
        Object[] objArr = this.e;
        int i = this.g;
        for (int i2 = 0; i2 < i; i2++) {
            if (o20.e(obj, objArr[i2])) {
                return i2;
            }
        }
        return -1;
    }

    public final boolean j(Object obj) {
        int i = i(obj);
        if (i >= 0) {
            k(i);
            return true;
        }
        return false;
    }

    public final Object k(int i) {
        Object[] objArr = this.e;
        Object obj = objArr[i];
        int i2 = this.g;
        if (i != i2 - 1) {
            int i3 = i + 1;
            System.arraycopy(objArr, i3, objArr, i, i2 - i3);
        }
        int i4 = this.g - 1;
        this.g = i4;
        objArr[i4] = null;
        return obj;
    }

    public final void l(int i, int i2) {
        if (i2 > i) {
            int i3 = this.g;
            if (i2 < i3) {
                Object[] objArr = this.e;
                System.arraycopy(objArr, i2, objArr, i, i3 - i2);
            }
            int i4 = this.g;
            int i5 = i4 - (i2 - i);
            int i6 = i4 - 1;
            if (i5 <= i6) {
                int i7 = i5;
                while (true) {
                    this.e[i7] = null;
                    if (i7 == i6) {
                        break;
                    } else {
                        i7++;
                    }
                }
            }
            this.g = i5;
        }
    }

    public final void m(int i) {
        Object[] objArr = this.e;
        int length = objArr.length;
        Object[] objArr2 = new Object[Math.max(i, length * 2)];
        System.arraycopy(objArr, 0, objArr2, 0, length);
        this.e = objArr2;
    }
}
