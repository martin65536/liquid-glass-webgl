package defpackage;

import java.util.List;
import java.util.ListIterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qq0 implements ListIterator, q30 {
    public final /* synthetic */ int e = 1;
    public final Object f;
    public final /* synthetic */ Object g;

    public qq0(rq0 rq0Var, int i) {
        this.g = rq0Var;
        List list = rq0Var.e;
        if (i >= 0 && i <= rq0Var.a()) {
            this.f = list.listIterator(rq0Var.a() - i);
            return;
        }
        throw new IndexOutOfBoundsException("Position index " + i + " must be in range [" + new w10(0, rq0Var.a(), 1) + "].");
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        switch (this.e) {
            case 0:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            default:
                throw new IllegalStateException("Cannot modify a state list through an iterator");
        }
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                return ((ListIterator) obj).hasPrevious();
            default:
                if (((cp0) obj).e < ((dz0) this.g).h - 1) {
                    return true;
                }
                return false;
        }
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                return ((ListIterator) obj).hasNext();
            default:
                if (((cp0) obj).e >= 0) {
                    return true;
                }
                return false;
        }
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                return ((ListIterator) obj).previous();
            default:
                cp0 cp0Var = (cp0) obj;
                int i2 = cp0Var.e + 1;
                dz0 dz0Var = (dz0) this.g;
                o4.i(i2, dz0Var.h);
                cp0Var.e = i2;
                return dz0Var.get(i2);
        }
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                rq0 rq0Var = (rq0) this.g;
                return (rq0Var.size() - 1) - ((ListIterator) obj).previousIndex();
            default:
                return ((cp0) obj).e + 1;
        }
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                return ((ListIterator) obj).next();
            default:
                cp0 cp0Var = (cp0) obj;
                int i2 = cp0Var.e;
                dz0 dz0Var = (dz0) this.g;
                o4.i(i2, dz0Var.h);
                cp0Var.e = i2 - 1;
                return dz0Var.get(i2);
        }
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                rq0 rq0Var = (rq0) this.g;
                return (rq0Var.size() - 1) - ((ListIterator) obj).nextIndex();
            default:
                return ((cp0) obj).e;
        }
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final void remove() {
        switch (this.e) {
            case 0:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            default:
                throw new IllegalStateException("Cannot modify a state list through an iterator");
        }
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        switch (this.e) {
            case 0:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            default:
                throw new IllegalStateException("Cannot modify a state list through an iterator");
        }
    }

    public qq0(cp0 cp0Var, dz0 dz0Var) {
        this.f = cp0Var;
        this.g = dz0Var;
    }
}
