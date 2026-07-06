package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tx0 implements vh, Iterable, q30 {
    public final rw0 e;
    public final int f;
    public final ip0 g;

    public tx0(rw0 rw0Var, int i, dw dwVar, ip0 ip0Var) {
        this.e = rw0Var;
        this.f = i;
        this.g = ip0Var;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof tx0) {
            tx0 tx0Var = (tx0) obj;
            if (tx0Var.f == this.f && tx0Var.e == this.e && tx0Var.g.equals(this.g)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        return this.g.hashCode() + ((this.e.hashCode() + (this.f * 31)) * 31);
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new sx0(this.e, this.f, null, this.g);
    }
}
