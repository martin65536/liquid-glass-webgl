package defpackage;

import java.util.Iterator;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rl0 implements Iterator, q30 {
    public final /* synthetic */ int e = 1;
    public final Iterator f;

    public rl0(ol0 ol0Var) {
        b31[] b31VarArr = new b31[8];
        for (int i = 0; i < 8; i++) {
            b31VarArr[i] = new d31(this);
        }
        this.f = new pl0(ol0Var, b31VarArr);
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        switch (this.e) {
            case 0:
                return ((pl0) this.f).g;
            default:
                return this.f.hasNext();
        }
    }

    @Override // java.util.Iterator
    public final Object next() {
        switch (this.e) {
            case 0:
                return (Map.Entry) ((pl0) this.f).next();
            default:
                return (p41) this.f.next();
        }
    }

    @Override // java.util.Iterator
    public final void remove() {
        switch (this.e) {
            case 0:
                ((pl0) this.f).remove();
                return;
            default:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    public rl0(n41 n41Var) {
        this.f = n41Var.n.iterator();
    }
}
