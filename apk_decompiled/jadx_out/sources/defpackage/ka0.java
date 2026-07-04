package defpackage;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ka0 extends y implements RandomAccess, Serializable {
    public static final ka0 h;
    public Object[] e;
    public int f;
    public boolean g;

    static {
        ka0 ka0Var = new ka0(0);
        ka0Var.g = true;
        h = ka0Var;
    }

    public ka0(int i) {
        if (i >= 0) {
            this.e = new Object[i];
        } else {
            v7.m("capacity must be non-negative.");
            throw null;
        }
    }

    public static final /* synthetic */ int c(ka0 ka0Var) {
        return ((AbstractList) ka0Var).modCount;
    }

    @Override // defpackage.y
    public final int a() {
        return this.f;
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        f();
        int i2 = this.f;
        if (i >= 0 && i <= i2) {
            ((AbstractList) this).modCount++;
            g(i, 1);
            this.e[i] = obj;
            return;
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
    }

    @Override // java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection collection) {
        collection.getClass();
        f();
        int i2 = this.f;
        if (i >= 0 && i <= i2) {
            int size = collection.size();
            d(i, collection, size);
            if (size <= 0) {
                return false;
            }
            return true;
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
        return false;
    }

    @Override // defpackage.y
    public final Object b(int i) {
        f();
        int i2 = this.f;
        if (i >= 0 && i < i2) {
            return h(i);
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
        return null;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        f();
        i(0, this.f);
    }

    public final void d(int i, Collection collection, int i2) {
        ((AbstractList) this).modCount++;
        g(i, i2);
        Iterator it = collection.iterator();
        for (int i3 = 0; i3 < i2; i3++) {
            this.e[i + i3] = it.next();
        }
    }

    public final void e(int i, Object obj) {
        ((AbstractList) this).modCount++;
        g(i, 1);
        this.e[i] = obj;
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (obj != this) {
            if (obj instanceof List) {
                List list = (List) obj;
                Object[] objArr = this.e;
                int i = this.f;
                if (i == list.size()) {
                    for (int i2 = 0; i2 < i; i2++) {
                        if (o20.e(objArr[i2], list.get(i2))) {
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public final void f() {
        if (!this.g) {
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public final void g(int i, int i2) {
        int i3 = this.f + i2;
        if (i3 >= 0) {
            Object[] objArr = this.e;
            if (i3 > objArr.length) {
                int length = objArr.length;
                int i4 = length + (length >> 1);
                if (i4 - i3 < 0) {
                    i4 = i3;
                }
                if (i4 - 2147483639 > 0) {
                    if (i3 > 2147483639) {
                        i4 = Integer.MAX_VALUE;
                    } else {
                        i4 = 2147483639;
                    }
                }
                this.e = Arrays.copyOf(objArr, i4);
            }
            Object[] objArr2 = this.e;
            i8.N(objArr2, objArr2, i + i2, i, this.f);
            this.f += i2;
            return;
        }
        throw new OutOfMemoryError();
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        int i2 = this.f;
        if (i >= 0 && i < i2) {
            return this.e[i];
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
        return null;
    }

    public final Object h(int i) {
        ((AbstractList) this).modCount++;
        Object[] objArr = this.e;
        Object obj = objArr[i];
        i8.N(objArr, objArr, i, i + 1, this.f);
        Object[] objArr2 = this.e;
        int i2 = this.f - 1;
        objArr2.getClass();
        objArr2[i2] = null;
        this.f--;
        return obj;
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int i;
        Object[] objArr = this.e;
        int i2 = this.f;
        int i3 = 1;
        for (int i4 = 0; i4 < i2; i4++) {
            Object obj = objArr[i4];
            int i5 = i3 * 31;
            if (obj != null) {
                i = obj.hashCode();
            } else {
                i = 0;
            }
            i3 = i5 + i;
        }
        return i3;
    }

    public final void i(int i, int i2) {
        if (i2 > 0) {
            ((AbstractList) this).modCount++;
        }
        Object[] objArr = this.e;
        i8.N(objArr, objArr, i, i + i2, this.f);
        Object[] objArr2 = this.e;
        int i3 = this.f;
        d20.F(objArr2, i3 - i2, i3);
        this.f -= i2;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        for (int i = 0; i < this.f; i++) {
            if (o20.e(this.e[i], obj)) {
                return i;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        if (this.f == 0) {
            return true;
        }
        return false;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return listIterator(0);
    }

    public final int j(int i, int i2, Collection collection, boolean z) {
        Object[] objArr;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            objArr = this.e;
            if (i3 >= i2) {
                break;
            }
            int i5 = i + i3;
            if (collection.contains(objArr[i5]) == z) {
                Object[] objArr2 = this.e;
                i3++;
                objArr2[i4 + i] = objArr2[i5];
                i4++;
            } else {
                i3++;
            }
        }
        int i6 = i2 - i4;
        i8.N(objArr, objArr, i + i4, i2 + i, this.f);
        Object[] objArr3 = this.e;
        int i7 = this.f;
        d20.F(objArr3, i7 - i6, i7);
        if (i6 > 0) {
            ((AbstractList) this).modCount++;
        }
        this.f -= i6;
        return i6;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int lastIndexOf(Object obj) {
        for (int i = this.f - 1; i >= 0; i--) {
            if (o20.e(this.e[i], obj)) {
                return i;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator(int i) {
        int i2 = this.f;
        if (i >= 0 && i <= i2) {
            return new ny(this, i);
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
        return null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        f();
        int indexOf = indexOf(obj);
        if (indexOf >= 0) {
            b(indexOf);
        }
        if (indexOf >= 0) {
            return true;
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean removeAll(Collection collection) {
        collection.getClass();
        f();
        if (j(0, this.f, collection, false) <= 0) {
            return false;
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean retainAll(Collection collection) {
        collection.getClass();
        f();
        if (j(0, this.f, collection, true) <= 0) {
            return false;
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        f();
        int i2 = this.f;
        if (i >= 0 && i < i2) {
            Object[] objArr = this.e;
            Object obj2 = objArr[i];
            objArr[i] = obj;
            return obj2;
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
        return null;
    }

    @Override // java.util.AbstractList, java.util.List
    public final List subList(int i, int i2) {
        k81.n(i, i2, this.f);
        return new ja0(this.e, i, i2 - i, null, this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray(Object[] objArr) {
        objArr.getClass();
        int length = objArr.length;
        int i = this.f;
        Object[] objArr2 = this.e;
        if (length < i) {
            Object[] copyOfRange = Arrays.copyOfRange(objArr2, 0, i, objArr.getClass());
            copyOfRange.getClass();
            return copyOfRange;
        }
        i8.N(objArr2, objArr, 0, 0, i);
        int i2 = this.f;
        if (i2 < objArr.length) {
            objArr[i2] = null;
        }
        return objArr;
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        return d20.d(this.e, 0, this.f, this);
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        f();
        int i = this.f;
        ((AbstractList) this).modCount++;
        g(i, 1);
        this.e[i] = obj;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray() {
        return i8.Q(this.e, 0, this.f);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        collection.getClass();
        f();
        int size = collection.size();
        d(this.f, collection, size);
        return size > 0;
    }
}
