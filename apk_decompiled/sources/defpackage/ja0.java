package defpackage;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ja0 extends y implements RandomAccess, Serializable {
    public Object[] e;
    public final int f;
    public int g;
    public final ja0 h;
    public final ka0 i;

    public ja0(Object[] objArr, int i, int i2, ja0 ja0Var, ka0 ka0Var) {
        int i3;
        objArr.getClass();
        ka0Var.getClass();
        this.e = objArr;
        this.f = i;
        this.g = i2;
        this.h = ja0Var;
        this.i = ka0Var;
        i3 = ((AbstractList) ka0Var).modCount;
        ((AbstractList) this).modCount = i3;
    }

    @Override // defpackage.y
    public final int a() {
        f();
        return this.g;
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        g();
        f();
        int i2 = this.g;
        if (i >= 0 && i <= i2) {
            e(this.f + i, obj);
        } else {
            v7.f(d3.u("index: ", i, ", size: ", i2));
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection collection) {
        collection.getClass();
        g();
        f();
        int i2 = this.g;
        if (i >= 0 && i <= i2) {
            int size = collection.size();
            d(this.f + i, collection, size);
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
        g();
        f();
        int i2 = this.g;
        if (i >= 0 && i < i2) {
            return h(this.f + i);
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
        return null;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        g();
        f();
        i(this.f, this.g);
    }

    public final void d(int i, Collection collection, int i2) {
        ((AbstractList) this).modCount++;
        ka0 ka0Var = this.i;
        ja0 ja0Var = this.h;
        if (ja0Var != null) {
            ja0Var.d(i, collection, i2);
        } else {
            ka0 ka0Var2 = ka0.h;
            ka0Var.d(i, collection, i2);
        }
        this.e = ka0Var.e;
        this.g += i2;
    }

    public final void e(int i, Object obj) {
        ((AbstractList) this).modCount++;
        ka0 ka0Var = this.i;
        ja0 ja0Var = this.h;
        if (ja0Var != null) {
            ja0Var.e(i, obj);
        } else {
            ka0 ka0Var2 = ka0.h;
            ka0Var.e(i, obj);
        }
        this.e = ka0Var.e;
        this.g++;
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        f();
        if (obj != this) {
            if (obj instanceof List) {
                List list = (List) obj;
                Object[] objArr = this.e;
                int i = this.g;
                if (i == list.size()) {
                    for (int i2 = 0; i2 < i; i2++) {
                        if (o20.e(objArr[this.f + i2], list.get(i2))) {
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
        int i;
        i = ((AbstractList) this.i).modCount;
        if (i == ((AbstractList) this).modCount) {
        } else {
            throw new ConcurrentModificationException();
        }
    }

    public final void g() {
        if (!this.i.g) {
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        f();
        int i2 = this.g;
        if (i >= 0 && i < i2) {
            return this.e[this.f + i];
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
        return null;
    }

    public final Object h(int i) {
        Object h;
        ((AbstractList) this).modCount++;
        ja0 ja0Var = this.h;
        if (ja0Var != null) {
            h = ja0Var.h(i);
        } else {
            ka0 ka0Var = ka0.h;
            h = this.i.h(i);
        }
        this.g--;
        return h;
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int i;
        f();
        Object[] objArr = this.e;
        int i2 = this.g;
        int i3 = 1;
        for (int i4 = 0; i4 < i2; i4++) {
            Object obj = objArr[this.f + i4];
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
        ja0 ja0Var = this.h;
        if (ja0Var != null) {
            ja0Var.i(i, i2);
        } else {
            ka0 ka0Var = ka0.h;
            this.i.i(i, i2);
        }
        this.g -= i2;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        f();
        for (int i = 0; i < this.g; i++) {
            if (o20.e(this.e[this.f + i], obj)) {
                return i;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        f();
        if (this.g == 0) {
            return true;
        }
        return false;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return listIterator(0);
    }

    public final int j(int i, int i2, Collection collection, boolean z) {
        int j;
        ja0 ja0Var = this.h;
        if (ja0Var != null) {
            j = ja0Var.j(i, i2, collection, z);
        } else {
            ka0 ka0Var = ka0.h;
            j = this.i.j(i, i2, collection, z);
        }
        if (j > 0) {
            ((AbstractList) this).modCount++;
        }
        this.g -= j;
        return j;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int lastIndexOf(Object obj) {
        f();
        for (int i = this.g - 1; i >= 0; i--) {
            if (o20.e(this.e[this.f + i], obj)) {
                return i;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator(int i) {
        f();
        int i2 = this.g;
        if (i >= 0 && i <= i2) {
            return new ny(this, i);
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
        return null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        g();
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
        g();
        f();
        if (j(this.f, this.g, collection, false) <= 0) {
            return false;
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean retainAll(Collection collection) {
        collection.getClass();
        g();
        f();
        if (j(this.f, this.g, collection, true) > 0) {
            return true;
        }
        return false;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        g();
        f();
        int i2 = this.g;
        if (i >= 0 && i < i2) {
            Object[] objArr = this.e;
            int i3 = this.f;
            Object obj2 = objArr[i3 + i];
            objArr[i3 + i] = obj;
            return obj2;
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
        return null;
    }

    @Override // java.util.AbstractList, java.util.List
    public final List subList(int i, int i2) {
        k81.n(i, i2, this.g);
        return new ja0(this.e, this.f + i, i2 - i, this, this.i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray(Object[] objArr) {
        objArr.getClass();
        f();
        int length = objArr.length;
        int i = this.g;
        Object[] objArr2 = this.e;
        int i2 = this.f;
        if (length < i) {
            Object[] copyOfRange = Arrays.copyOfRange(objArr2, i2, i + i2, objArr.getClass());
            copyOfRange.getClass();
            return copyOfRange;
        }
        i8.N(objArr2, objArr, 0, i2, i + i2);
        int i3 = this.g;
        if (i3 < objArr.length) {
            objArr[i3] = null;
        }
        return objArr;
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        f();
        return d20.d(this.e, this.f, this.g, this);
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        g();
        f();
        e(this.f + this.g, obj);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray() {
        f();
        Object[] objArr = this.e;
        int i = this.g;
        int i2 = this.f;
        return i8.Q(objArr, i2, i + i2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        collection.getClass();
        g();
        f();
        int size = collection.size();
        d(this.f + this.g, collection, size);
        return size > 0;
    }
}
