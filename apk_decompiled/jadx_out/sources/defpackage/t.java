package defpackage;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class t implements Iterator, q30 {
    public final /* synthetic */ int e = 0;
    public int f;
    public final Object g;

    public t(Object[] objArr) {
        objArr.getClass();
        this.g = objArr;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i = this.e;
        Object obj = this.g;
        switch (i) {
            case 0:
                if (this.f >= ((w) obj).a()) {
                    return false;
                }
                return true;
            default:
                if (this.f >= ((Object[]) obj).length) {
                    return false;
                }
                return true;
        }
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i = this.e;
        Object obj = this.g;
        switch (i) {
            case 0:
                if (hasNext()) {
                    int i2 = this.f;
                    this.f = i2 + 1;
                    return ((w) obj).get(i2);
                }
                v7.n();
                return null;
            default:
                try {
                    int i3 = this.f;
                    this.f = i3 + 1;
                    return ((Object[]) obj)[i3];
                } catch (ArrayIndexOutOfBoundsException e) {
                    this.f--;
                    throw new NoSuchElementException(e.getMessage());
                }
        }
    }

    @Override // java.util.Iterator
    public final void remove() {
        switch (this.e) {
            case 0:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            default:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    public t(w wVar) {
        this.g = wVar;
    }
}
