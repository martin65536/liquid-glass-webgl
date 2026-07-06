package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class b31 implements Iterator, q30 {
    public Object[] e = a31.e.d;
    public int f;
    public int g;

    public final void a(Object[] objArr, int i, int i2) {
        this.e = objArr;
        this.f = i;
        this.g = i2;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.g < this.f) {
            return true;
        }
        return false;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
