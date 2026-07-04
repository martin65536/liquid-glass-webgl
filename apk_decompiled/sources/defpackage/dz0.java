package defpackage;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class dz0 implements List, q30 {
    public final mx0 e;
    public final int f;
    public int g;
    public int h;

    public dz0(mx0 mx0Var, int i, int i2) {
        this.e = mx0Var;
        this.f = i;
        this.g = o4.J(mx0Var);
        this.h = i2 - i;
    }

    public final void a() {
        if (o4.J(this.e) == this.g) {
        } else {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.List, java.util.Collection
    public final boolean add(Object obj) {
        a();
        int i = this.f + this.h;
        mx0 mx0Var = this.e;
        mx0Var.add(i, obj);
        this.h++;
        this.g = o4.J(mx0Var);
        return true;
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        a();
        int i2 = i + this.f;
        mx0 mx0Var = this.e;
        boolean addAll = mx0Var.addAll(i2, collection);
        if (addAll) {
            this.h = collection.size() + this.h;
            this.g = o4.J(mx0Var);
        }
        return addAll;
    }

    @Override // java.util.List, java.util.Collection
    public final void clear() {
        if (this.h > 0) {
            a();
            int i = this.h;
            int i2 = this.f;
            mx0 mx0Var = this.e;
            mx0Var.d(i2, i + i2);
            this.h = 0;
            this.g = o4.J(mx0Var);
        }
    }

    @Override // java.util.List, java.util.Collection
    public final boolean contains(Object obj) {
        if (indexOf(obj) >= 0) {
            return true;
        }
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean containsAll(Collection collection) {
        Collection collection2 = collection;
        if ((collection2 instanceof Collection) && collection2.isEmpty()) {
            return true;
        }
        Iterator it = collection2.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.List
    public final Object get(int i) {
        a();
        o4.i(i, this.h);
        return this.e.get(this.f + i);
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        int i;
        a();
        int i2 = this.h;
        int i3 = this.f;
        Iterator it = n30.K(i3, i2 + i3).iterator();
        do {
            x10 x10Var = (x10) it;
            boolean z = x10Var.g;
            if (z) {
                i = x10Var.h;
                if (i == x10Var.f) {
                    if (z) {
                        x10Var.g = false;
                    } else {
                        v7.n();
                        return 0;
                    }
                } else {
                    x10Var.h = x10Var.e + i;
                }
            } else {
                return -1;
            }
        } while (!o20.e(obj, this.e.get(i)));
        return i - i3;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean isEmpty() {
        if (this.h == 0) {
            return true;
        }
        return false;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return listIterator(0);
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        a();
        int i = this.h;
        int i2 = this.f;
        for (int i3 = (i + i2) - 1; i3 >= i2; i3--) {
            if (o20.e(obj, this.e.get(i3))) {
                return i3 - i2;
            }
        }
        return -1;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [cp0, java.lang.Object] */
    @Override // java.util.List
    public final ListIterator listIterator(int i) {
        a();
        ?? obj = new Object();
        obj.e = i - 1;
        return new qq0((cp0) obj, this);
    }

    @Override // java.util.List
    public final Object remove(int i) {
        a();
        int i2 = this.f + i;
        mx0 mx0Var = this.e;
        Object remove = mx0Var.remove(i2);
        this.h--;
        this.g = o4.J(mx0Var);
        return remove;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean removeAll(Collection collection) {
        Iterator it = collection.iterator();
        while (true) {
            boolean z = false;
            while (it.hasNext()) {
                if (remove(it.next()) || z) {
                    z = true;
                }
            }
            return z;
        }
    }

    @Override // java.util.List, java.util.Collection
    public final boolean retainAll(Collection collection) {
        int i;
        b0 b0Var;
        ww0 j;
        boolean l;
        a();
        mx0 mx0Var = this.e;
        int i2 = this.f;
        int i3 = this.h + i2;
        int size = mx0Var.size();
        do {
            synchronized (o4.h) {
                my0 my0Var = mx0Var.e;
                my0Var.getClass();
                my0 my0Var2 = (my0) cx0.h(my0Var);
                i = my0Var2.d;
                b0Var = my0Var2.c;
            }
            b0Var.getClass();
            yl0 e = b0Var.e();
            e.subList(i2, i3).retainAll(collection);
            b0 c = e.c();
            if (o20.e(c, b0Var)) {
                break;
            }
            my0 my0Var3 = mx0Var.e;
            my0Var3.getClass();
            synchronized (cx0.c) {
                j = cx0.j();
                l = o4.l((my0) cx0.x(my0Var3, mx0Var, j), i, c, true);
            }
            cx0.o(j, mx0Var);
        } while (!l);
        int size2 = size - mx0Var.size();
        if (size2 > 0) {
            this.g = o4.J(this.e);
            this.h -= size2;
        }
        if (size2 > 0) {
            return true;
        }
        return false;
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        o4.i(i, this.h);
        a();
        int i2 = i + this.f;
        mx0 mx0Var = this.e;
        Object obj2 = mx0Var.set(i2, obj);
        this.g = o4.J(mx0Var);
        return obj2;
    }

    @Override // java.util.List, java.util.Collection
    public final int size() {
        return this.h;
    }

    @Override // java.util.List
    public final List subList(int i, int i2) {
        if (i < 0 || i > i2 || i2 > this.h) {
            cn0.a("fromIndex or toIndex are out of bounds");
        }
        a();
        int i3 = this.f;
        return new dz0(this.e, i + i3, i2 + i3);
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
    public final ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean remove(Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf < 0) {
            return false;
        }
        remove(indexOf);
        return true;
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        a();
        int i2 = this.f + i;
        mx0 mx0Var = this.e;
        mx0Var.add(i2, obj);
        this.h++;
        this.g = o4.J(mx0Var);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean addAll(Collection collection) {
        return addAll(this.h, collection);
    }
}
