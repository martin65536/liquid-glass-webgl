package defpackage;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mx0 implements Parcelable, ny0, List, RandomAccess, q30 {
    public static final Parcelable.Creator<mx0> CREATOR = new Object();
    public my0 e;

    public mx0(b0 b0Var) {
        ww0 j = cx0.j();
        my0 my0Var = new my0(j.g(), b0Var);
        if (!(j instanceof ax)) {
            my0Var.b = new my0(1L, b0Var);
        }
        this.e = my0Var;
    }

    @Override // defpackage.ny0
    public final py0 a() {
        return this.e;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean add(Object obj) {
        int i;
        b0 b0Var;
        ww0 j;
        boolean l;
        do {
            synchronized (o4.h) {
                my0 my0Var = this.e;
                my0Var.getClass();
                my0 my0Var2 = (my0) cx0.h(my0Var);
                i = my0Var2.d;
                b0Var = my0Var2.c;
            }
            b0Var.getClass();
            b0 c = b0Var.c(obj);
            if (c.equals(b0Var)) {
                return false;
            }
            my0 my0Var3 = this.e;
            my0Var3.getClass();
            synchronized (cx0.c) {
                j = cx0.j();
                l = o4.l((my0) cx0.x(my0Var3, this, j), i, c, true);
            }
            cx0.o(j, this);
        } while (!l);
        return true;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean addAll(Collection collection) {
        int i;
        b0 b0Var;
        ww0 j;
        boolean l;
        do {
            synchronized (o4.h) {
                my0 my0Var = this.e;
                my0Var.getClass();
                my0 my0Var2 = (my0) cx0.h(my0Var);
                i = my0Var2.d;
                b0Var = my0Var2.c;
            }
            b0Var.getClass();
            b0 d = b0Var.d(collection);
            if (o20.e(d, b0Var)) {
                return false;
            }
            my0 my0Var3 = this.e;
            my0Var3.getClass();
            synchronized (cx0.c) {
                j = cx0.j();
                l = o4.l((my0) cx0.x(my0Var3, this, j), i, d, true);
            }
            cx0.o(j, this);
        } while (!l);
        return true;
    }

    @Override // defpackage.ny0
    public final /* synthetic */ py0 b(py0 py0Var, py0 py0Var2, py0 py0Var3) {
        return null;
    }

    @Override // defpackage.ny0
    public final void c(py0 py0Var) {
        py0Var.b = this.e;
        this.e = (my0) py0Var;
    }

    @Override // java.util.List, java.util.Collection
    public final void clear() {
        ww0 j;
        my0 my0Var = this.e;
        my0Var.getClass();
        synchronized (cx0.c) {
            j = cx0.j();
            my0 my0Var2 = (my0) cx0.x(my0Var, this, j);
            synchronized (o4.h) {
                my0Var2.c = vw0.f;
                my0Var2.d++;
                my0Var2.e++;
            }
        }
        cx0.o(j, this);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean contains(Object obj) {
        return o4.I(this).c.contains(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean containsAll(Collection collection) {
        return o4.I(this).c.containsAll(collection);
    }

    public final void d(int i, int i2) {
        int i3;
        b0 b0Var;
        ww0 j;
        boolean l;
        do {
            synchronized (o4.h) {
                my0 my0Var = this.e;
                my0Var.getClass();
                my0 my0Var2 = (my0) cx0.h(my0Var);
                i3 = my0Var2.d;
                b0Var = my0Var2.c;
            }
            b0Var.getClass();
            yl0 e = b0Var.e();
            e.subList(i, i2).clear();
            b0 c = e.c();
            if (!o20.e(c, b0Var)) {
                my0 my0Var3 = this.e;
                my0Var3.getClass();
                synchronized (cx0.c) {
                    j = cx0.j();
                    l = o4.l((my0) cx0.x(my0Var3, this, j), i3, c, true);
                }
                cx0.o(j, this);
            } else {
                return;
            }
        } while (!l);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // java.util.List
    public final Object get(int i) {
        return o4.I(this).c.get(i);
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        return o4.I(this).c.indexOf(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean isEmpty() {
        return o4.I(this).c.isEmpty();
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return listIterator();
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        return o4.I(this).c.lastIndexOf(obj);
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        return new ny(this, 0);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean remove(Object obj) {
        int i;
        b0 b0Var;
        b0 b0Var2;
        ww0 j;
        boolean l;
        do {
            synchronized (o4.h) {
                my0 my0Var = this.e;
                my0Var.getClass();
                my0 my0Var2 = (my0) cx0.h(my0Var);
                i = my0Var2.d;
                b0Var = my0Var2.c;
            }
            b0Var.getClass();
            int indexOf = b0Var.indexOf(obj);
            if (indexOf != -1) {
                b0Var2 = b0Var.g(indexOf);
            } else {
                b0Var2 = b0Var;
            }
            if (b0Var2.equals(b0Var)) {
                return false;
            }
            my0 my0Var3 = this.e;
            my0Var3.getClass();
            synchronized (cx0.c) {
                j = cx0.j();
                l = o4.l((my0) cx0.x(my0Var3, this, j), i, b0Var2, true);
            }
            cx0.o(j, this);
        } while (!l);
        return true;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean removeAll(Collection collection) {
        int i;
        b0 b0Var;
        ww0 j;
        boolean l;
        do {
            synchronized (o4.h) {
                my0 my0Var = this.e;
                my0Var.getClass();
                my0 my0Var2 = (my0) cx0.h(my0Var);
                i = my0Var2.d;
                b0Var = my0Var2.c;
            }
            b0Var.getClass();
            b0 f = b0Var.f(new a0(0, collection));
            if (o20.e(f, b0Var)) {
                return false;
            }
            my0 my0Var3 = this.e;
            my0Var3.getClass();
            synchronized (cx0.c) {
                j = cx0.j();
                l = o4.l((my0) cx0.x(my0Var3, this, j), i, f, true);
            }
            cx0.o(j, this);
        } while (!l);
        return true;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean retainAll(Collection collection) {
        return o4.P(this, new a0(2, collection));
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        int i2;
        b0 b0Var;
        ww0 j;
        boolean l;
        Object obj2 = get(i);
        do {
            synchronized (o4.h) {
                my0 my0Var = this.e;
                my0Var.getClass();
                my0 my0Var2 = (my0) cx0.h(my0Var);
                i2 = my0Var2.d;
                b0Var = my0Var2.c;
            }
            b0Var.getClass();
            b0 h = b0Var.h(i, obj);
            if (h.equals(b0Var)) {
                break;
            }
            my0 my0Var3 = this.e;
            my0Var3.getClass();
            synchronized (cx0.c) {
                j = cx0.j();
                l = o4.l((my0) cx0.x(my0Var3, this, j), i2, h, false);
            }
            cx0.o(j, this);
        } while (!l);
        return obj2;
    }

    @Override // java.util.List, java.util.Collection
    public final int size() {
        return o4.I(this).c.a();
    }

    @Override // java.util.List
    public final List subList(int i, int i2) {
        boolean z;
        if (i >= 0 && i <= i2 && i2 <= size()) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            cn0.a("fromIndex or toIndex are out of bounds");
        }
        return new dz0(this, i, i2);
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray() {
        return o20.H(this);
    }

    public final String toString() {
        my0 my0Var = this.e;
        my0Var.getClass();
        return "SnapshotStateList(value=" + ((my0) cx0.h(my0Var)).c + ")@" + hashCode();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        b0 b0Var = o4.I(this).c;
        int a = b0Var.a();
        parcel.writeInt(a);
        for (int i2 = 0; i2 < a; i2++) {
            parcel.writeValue(b0Var.get(i2));
        }
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return o20.I(this, objArr);
    }

    @Override // java.util.List
    public final ListIterator listIterator(int i) {
        return new ny(this, i);
    }

    public mx0() {
        this(vw0.f);
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        int i2;
        b0 b0Var;
        ww0 j;
        boolean l;
        do {
            synchronized (o4.h) {
                my0 my0Var = this.e;
                my0Var.getClass();
                my0 my0Var2 = (my0) cx0.h(my0Var);
                i2 = my0Var2.d;
                b0Var = my0Var2.c;
            }
            b0Var.getClass();
            b0 b = b0Var.b(i, obj);
            if (b.equals(b0Var)) {
                return;
            }
            my0 my0Var3 = this.e;
            my0Var3.getClass();
            synchronized (cx0.c) {
                j = cx0.j();
                l = o4.l((my0) cx0.x(my0Var3, this, j), i2, b, true);
            }
            cx0.o(j, this);
        } while (!l);
    }

    @Override // java.util.List
    public final boolean addAll(final int i, final Collection collection) {
        return o4.P(this, new gv() { // from class: kx0
            @Override // defpackage.gv
            public final Object e(Object obj) {
                return Boolean.valueOf(((List) obj).addAll(i, collection));
            }
        });
    }

    @Override // java.util.List
    public final Object remove(int i) {
        int i2;
        b0 b0Var;
        ww0 j;
        boolean l;
        Object obj = get(i);
        do {
            synchronized (o4.h) {
                my0 my0Var = this.e;
                my0Var.getClass();
                my0 my0Var2 = (my0) cx0.h(my0Var);
                i2 = my0Var2.d;
                b0Var = my0Var2.c;
            }
            b0Var.getClass();
            b0 g = b0Var.g(i);
            if (g.equals(b0Var)) {
                break;
            }
            my0 my0Var3 = this.e;
            my0Var3.getClass();
            synchronized (cx0.c) {
                j = cx0.j();
                l = o4.l((my0) cx0.x(my0Var3, this, j), i2, g, true);
            }
            cx0.o(j, this);
        } while (!l);
        return obj;
    }
}
