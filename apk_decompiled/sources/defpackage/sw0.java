package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class sw0 implements vh, Iterable, q30 {
    public final rw0 e;
    public final int f;
    public final int g;

    public sw0(rw0 rw0Var, int i, int i2) {
        this.e = rw0Var;
        this.f = i;
        this.g = i2;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof sw0) {
            sw0 sw0Var = (sw0) obj;
            if (sw0Var.f == this.f && sw0Var.g == this.g && sw0Var.e == this.e) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        return (this.e.hashCode() * 31) + this.f;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        rw0 rw0Var = this.e;
        if (rw0Var.l != this.g) {
            tw0.f();
        }
        int i = this.f;
        rw0Var.f(i);
        return new ux(rw0Var, i + 1, rw0Var.e[(i * 5) + 3] + i);
    }
}
