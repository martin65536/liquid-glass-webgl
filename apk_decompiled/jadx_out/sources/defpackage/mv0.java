package defpackage;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mv0 implements Iterator, ij, q30 {
    public int e;
    public Object f;
    public ij g;

    public final RuntimeException a() {
        int i = this.e;
        if (i != 4) {
            if (i != 5) {
                return new IllegalStateException("Unexpected state of the iterator: " + this.e);
            }
            return new IllegalStateException("Iterator has failed.");
        }
        return new NoSuchElementException();
    }

    public final void b(ij ijVar, Object obj) {
        this.f = obj;
        this.e = 3;
        this.g = ijVar;
        ijVar.getClass();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i;
        while (true) {
            i = this.e;
            if (i != 0) {
                break;
            }
            this.e = 5;
            ij ijVar = this.g;
            ijVar.getClass();
            this.g = null;
            ijVar.u(x31.a);
        }
        if (i != 1) {
            if (i == 2 || i == 3) {
                return true;
            }
            if (i == 4) {
                return false;
            }
            throw a();
        }
        throw null;
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i = this.e;
        if (i != 0 && i != 1) {
            if (i != 2) {
                if (i == 3) {
                    this.e = 0;
                    Object obj = this.f;
                    this.f = null;
                    return obj;
                }
                throw a();
            }
            this.e = 1;
            throw null;
        }
        if (hasNext()) {
            return next();
        }
        v7.n();
        return null;
    }

    @Override // defpackage.ij
    public final yj r() {
        return cr.e;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // defpackage.ij
    public final void u(Object obj) {
        o30.x(obj);
        this.e = 4;
    }
}
