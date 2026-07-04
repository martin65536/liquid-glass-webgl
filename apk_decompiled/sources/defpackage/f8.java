package defpackage;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class f8 implements Collection {
    public final /* synthetic */ g8 e;

    public f8(g8 g8Var) {
        this.e = g8Var;
    }

    @Override // java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final void clear() {
        this.e.clear();
    }

    @Override // java.util.Collection
    public final boolean contains(Object obj) {
        if (this.e.a(obj) >= 0) {
            return true;
        }
        return false;
    }

    @Override // java.util.Collection
    public final boolean containsAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection
    public final boolean isEmpty() {
        return this.e.isEmpty();
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new c8(this.e, 1);
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        g8 g8Var = this.e;
        int a = g8Var.a(obj);
        if (a >= 0) {
            g8Var.f(a);
            return true;
        }
        return false;
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        g8 g8Var = this.e;
        int i = g8Var.g;
        int i2 = 0;
        boolean z = false;
        while (i2 < i) {
            if (collection.contains(g8Var.h(i2))) {
                g8Var.f(i2);
                i2--;
                i--;
                z = true;
            }
            i2++;
        }
        return z;
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection collection) {
        g8 g8Var = this.e;
        int i = g8Var.g;
        int i2 = 0;
        boolean z = false;
        while (i2 < i) {
            if (!collection.contains(g8Var.h(i2))) {
                g8Var.f(i2);
                i2--;
                i--;
                z = true;
            }
            i2++;
        }
        return z;
    }

    @Override // java.util.Collection
    public final int size() {
        return this.e.g;
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        g8 g8Var = this.e;
        int i = g8Var.g;
        if (objArr.length < i) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
        }
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = g8Var.h(i2);
        }
        if (objArr.length > i) {
            objArr[i] = null;
        }
        return objArr;
    }

    @Override // java.util.Collection
    public final Object[] toArray() {
        g8 g8Var = this.e;
        int i = g8Var.g;
        Object[] objArr = new Object[i];
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = g8Var.h(i2);
        }
        return objArr;
    }
}
