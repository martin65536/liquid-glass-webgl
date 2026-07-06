package defpackage;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class cf0 implements List, q30 {
    public final List e;
    public final int f;
    public int g;

    public cf0(List list, int i, int i2) {
        this.e = list;
        this.f = i;
        this.g = i2;
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        this.e.add(i + this.f, obj);
        this.g++;
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        this.e.addAll(i + this.f, collection);
        int size = collection.size();
        this.g += size;
        if (size > 0) {
            return true;
        }
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public final void clear() {
        int i = this.g - 1;
        int i2 = this.f;
        if (i2 <= i) {
            while (true) {
                this.e.remove(i);
                if (i == i2) {
                    break;
                } else {
                    i--;
                }
            }
        }
        this.g = i2;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean contains(Object obj) {
        int i = this.g;
        for (int i2 = this.f; i2 < i; i2++) {
            if (o20.e(this.e.get(i2), obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean containsAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.List
    public final Object get(int i) {
        ff0.a(i, this);
        return this.e.get(i + this.f);
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        int i = this.g;
        int i2 = this.f;
        for (int i3 = i2; i3 < i; i3++) {
            if (o20.e(this.e.get(i3), obj)) {
                return i3 - i2;
            }
        }
        return -1;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean isEmpty() {
        if (this.g == this.f) {
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
        int i = this.g - 1;
        int i2 = this.f;
        if (i2 <= i) {
            while (!o20.e(this.e.get(i), obj)) {
                if (i != i2) {
                    i--;
                } else {
                    return -1;
                }
            }
            return i - i2;
        }
        return -1;
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        return new df0(0, this);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean remove(Object obj) {
        int i = this.g;
        for (int i2 = this.f; i2 < i; i2++) {
            List list = this.e;
            if (o20.e(list.get(i2), obj)) {
                list.remove(i2);
                this.g--;
                return true;
            }
        }
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean removeAll(Collection collection) {
        int i = this.g;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
        if (i != this.g) {
            return true;
        }
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean retainAll(Collection collection) {
        int i = this.g;
        int i2 = i - 1;
        int i3 = this.f;
        if (i3 <= i2) {
            while (true) {
                List list = this.e;
                if (!collection.contains(list.get(i2))) {
                    list.remove(i2);
                    this.g--;
                }
                if (i2 == i3) {
                    break;
                }
                i2--;
            }
        }
        if (i != this.g) {
            return true;
        }
        return false;
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        ff0.a(i, this);
        return this.e.set(i + this.f, obj);
    }

    @Override // java.util.List, java.util.Collection
    public final int size() {
        return this.g - this.f;
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
    public final ListIterator listIterator(int i) {
        return new df0(i, this);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean add(Object obj) {
        int i = this.g;
        this.g = i + 1;
        this.e.add(i, obj);
        return true;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean addAll(Collection collection) {
        this.e.addAll(this.g, collection);
        int size = collection.size();
        this.g += size;
        return size > 0;
    }

    @Override // java.util.List
    public final Object remove(int i) {
        ff0.a(i, this);
        this.g--;
        return this.e.remove(i + this.f);
    }
}
