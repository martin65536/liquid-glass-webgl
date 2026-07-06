package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class x10 implements Iterator, q30 {
    public final int e;
    public final int f;
    public boolean g;
    public int h;

    public x10(int i, int i2, int i3) {
        this.e = i3;
        this.f = i2;
        boolean z = false;
        if (i3 <= 0 ? i >= i2 : i <= i2) {
            z = true;
        }
        this.g = z;
        this.h = z ? i : i2;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.g;
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i = this.h;
        if (i == this.f) {
            if (this.g) {
                this.g = false;
            } else {
                v7.n();
                return null;
            }
        } else {
            this.h = this.e + i;
        }
        return Integer.valueOf(i);
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
