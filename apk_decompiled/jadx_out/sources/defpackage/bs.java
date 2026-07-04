package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bs implements Iterator, q30 {
    public final Iterator e;
    public int f = -1;
    public Object g;
    public final /* synthetic */ cs h;

    public bs(cs csVar) {
        this.h = csVar;
        this.e = ((lv0) csVar.c).iterator();
    }

    public final void a() {
        Object next;
        do {
            Iterator it = this.e;
            if (it.hasNext()) {
                next = it.next();
            } else {
                this.f = 0;
                return;
            }
        } while (!((Boolean) this.h.b.e(next)).booleanValue());
        this.g = next;
        this.f = 1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.f == -1) {
            a();
        }
        if (this.f == 1) {
            return true;
        }
        return false;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.f == -1) {
            a();
        }
        if (this.f != 0) {
            Object obj = this.g;
            this.g = null;
            this.f = -1;
            return obj;
        }
        v7.n();
        return null;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
