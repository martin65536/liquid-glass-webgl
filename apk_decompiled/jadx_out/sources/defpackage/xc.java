package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xc {
    public mm a;
    public m40 b;
    public uc c;
    public long d;

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof xc) {
                xc xcVar = (xc) obj;
                if (!o20.e(this.a, xcVar.a) || this.b != xcVar.b || !o20.e(this.c, xcVar.c) || !mw0.a(this.d, xcVar.d)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int hashCode = (this.c.hashCode() + ((this.b.hashCode() + (this.a.hashCode() * 31)) * 31)) * 31;
        long j = this.d;
        return ((int) (j ^ (j >>> 32))) + hashCode;
    }

    public final String toString() {
        return "DrawParams(density=" + this.a + ", layoutDirection=" + this.b + ", canvas=" + this.c + ", size=" + ((Object) mw0.d(this.d)) + ')';
    }
}
