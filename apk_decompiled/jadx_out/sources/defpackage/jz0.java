package defpackage;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class jz0 implements Collection, q30 {
    public final /* synthetic */ int e = 0;
    public final Object f;

    public jz0() {
        int i = cj0.a;
        this.f = new qe0(6);
    }

    @Override // java.util.Collection
    public final boolean add(Object obj) {
        switch (this.e) {
            case 0:
                return ((qe0) this.f).a(obj);
            default:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection collection) {
        switch (this.e) {
            case 0:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            default:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    @Override // java.util.Collection
    public final void clear() {
        switch (this.e) {
            case 0:
                ((qe0) this.f).b();
                return;
            default:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    @Override // java.util.Collection
    public final boolean contains(Object obj) {
        switch (this.e) {
            case 0:
                return ((qe0) this.f).c(obj);
            default:
                return ((ve0) this.f).d(obj);
        }
    }

    @Override // java.util.Collection
    public final boolean containsAll(Collection collection) {
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    if (!((qe0) obj).c(it.next())) {
                        return false;
                    }
                }
                return true;
            default:
                collection.getClass();
                Collection collection2 = collection;
                if (!collection2.isEmpty()) {
                    Iterator it2 = collection2.iterator();
                    while (it2.hasNext()) {
                        if (!((ve0) obj).d(it2.next())) {
                            return false;
                        }
                    }
                }
                return true;
        }
    }

    @Override // java.util.Collection
    public final boolean isEmpty() {
        switch (this.e) {
            case 0:
                if (((qe0) this.f).g == 0) {
                    return true;
                }
                return false;
            default:
                return ((ve0) this.f).i();
        }
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        switch (this.e) {
            case 0:
                qe0 qe0Var = (qe0) this.f;
                qe0Var.getClass();
                return new iw(new se0(qe0Var));
            default:
                return g30.y(new jr(this, null, 3));
        }
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        switch (this.e) {
            case 0:
                return ((qe0) this.f).g(obj);
            default:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        switch (this.e) {
            case 0:
                return ((qe0) this.f).g(collection);
            default:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    @Override // java.util.Collection
    public final boolean removeIf(Predicate predicate) {
        switch (this.e) {
            case 0:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            default:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection collection) {
        switch (this.e) {
            case 0:
                return ((qe0) this.f).i(collection);
            default:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    @Override // java.util.Collection
    public final int size() {
        switch (this.e) {
            case 0:
                return ((qe0) this.f).g;
            default:
                return ((ve0) this.f).e;
        }
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        switch (this.e) {
            case 0:
                return o20.I(this, objArr);
            default:
                objArr.getClass();
                return o20.I(this, objArr);
        }
    }

    public jz0(ve0 ve0Var) {
        ve0Var.getClass();
        this.f = ve0Var;
    }

    @Override // java.util.Collection
    public final Object[] toArray() {
        switch (this.e) {
            case 0:
                return o20.H(this);
            default:
                return o20.H(this);
        }
    }
}
