package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ux implements Iterator, q30 {
    public final rw0 e;
    public final int f;
    public int g;
    public final int h;

    public ux(rw0 rw0Var, int i, int i2) {
        this.e = rw0Var;
        this.f = i2;
        this.g = i;
        this.h = rw0Var.l;
        if (rw0Var.k) {
            tw0.f();
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.g < this.f) {
            return true;
        }
        return false;
    }

    @Override // java.util.Iterator
    public final Object next() {
        rw0 rw0Var = this.e;
        int i = rw0Var.l;
        int i2 = this.h;
        if (i != i2) {
            tw0.f();
        }
        int i3 = this.g;
        this.g = rw0Var.e[(i3 * 5) + 3] + i3;
        return new sw0(rw0Var, i3, i2);
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
