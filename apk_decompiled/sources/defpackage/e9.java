package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class e9 extends t20 {
    public final Object a;
    public final long b;

    public e9(long j, Object obj) {
        this.a = obj;
        this.b = j;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof e9) {
                e9 e9Var = (e9) obj;
                if (!this.a.equals(e9Var.a) || this.b != e9Var.b) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        long j = this.b;
        return hashCode + ((int) (j ^ (j >>> 32)));
    }

    public final String toString() {
        return "BackHandlerInfo(owner=" + this.a + ", compositeKey=" + this.b + ')';
    }
}
