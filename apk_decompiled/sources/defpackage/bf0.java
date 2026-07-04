package defpackage;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bf0 implements List, q30 {
    public final ef0 e;

    public bf0(ef0 ef0Var) {
        this.e = ef0Var;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean add(Object obj) {
        this.e.b(obj);
        return true;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean addAll(Collection collection) {
        ef0 ef0Var = this.e;
        return ef0Var.e(ef0Var.g, collection);
    }

    @Override // java.util.List, java.util.Collection
    public final void clear() {
        this.e.g();
    }

    @Override // java.util.List, java.util.Collection
    public final boolean contains(Object obj) {
        return this.e.h(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean containsAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!this.e.h(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.List
    public final Object get(int i) {
        ff0.a(i, this);
        return this.e.e[i];
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        return this.e.i(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean isEmpty() {
        if (this.e.g == 0) {
            return true;
        }
        return false;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new df0(0, this);
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        ef0 ef0Var = this.e;
        Object[] objArr = ef0Var.e;
        for (int i = ef0Var.g - 1; i >= 0; i--) {
            if (o20.e(obj, objArr[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        return new df0(0, this);
    }

    @Override // java.util.List
    public final Object remove(int i) {
        ff0.a(i, this);
        return this.e.k(i);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean removeAll(Collection collection) {
        if (!collection.isEmpty()) {
            ef0 ef0Var = this.e;
            int i = ef0Var.g;
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                ef0Var.j(it.next());
            }
            if (i != ef0Var.g) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean retainAll(Collection collection) {
        ef0 ef0Var = this.e;
        int i = ef0Var.g;
        for (int i2 = i - 1; -1 < i2; i2--) {
            if (!collection.contains(ef0Var.e[i2])) {
                ef0Var.k(i2);
            }
        }
        if (i != ef0Var.g) {
            return true;
        }
        return false;
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        ff0.a(i, this);
        Object[] objArr = this.e.e;
        Object obj2 = objArr[i];
        objArr[i] = obj;
        return obj2;
    }

    @Override // java.util.List, java.util.Collection
    public final int size() {
        return this.e.g;
    }

    @Override // java.util.List
    public final List subList(int i, int i2) {
        ff0.b(this, i, i2);
        return new cf0(this, i, i2);
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray() {
        return o20.H(this);
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return o20.I(this, objArr);
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        this.e.a(i, obj);
    }

    @Override // java.util.List
    public final ListIterator listIterator(int i) {
        return new df0(i, this);
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        return this.e.e(i, collection);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean remove(Object obj) {
        return this.e.j(obj);
    }
}
