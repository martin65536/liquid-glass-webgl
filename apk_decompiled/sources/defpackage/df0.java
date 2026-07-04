package defpackage;

import java.util.List;
import java.util.ListIterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class df0 implements ListIterator, q30 {
    public final List e;
    public int f;

    public df0(int i, List list) {
        this.e = list;
        this.f = i;
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        this.e.add(this.f, obj);
        this.f++;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        if (this.f < this.e.size()) {
            return true;
        }
        return false;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        if (this.f > 0) {
            return true;
        }
        return false;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        int i = this.f;
        this.f = i + 1;
        return this.e.get(i);
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.f;
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        int i = this.f - 1;
        this.f = i;
        return this.e.get(i);
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.f - 1;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final void remove() {
        int i = this.f - 1;
        this.f = i;
        this.e.remove(i);
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        this.e.set(this.f, obj);
    }
}
