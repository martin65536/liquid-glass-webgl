package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ip0 extends m20 {
    public final m20 c;
    public final int d;

    public ip0(m20 m20Var, int i) {
        this.c = m20Var;
        this.d = i;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ip0) {
            ip0 ip0Var = (ip0) obj;
            if (ip0Var.c.equals(this.c) && ip0Var.d == this.d) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        return this.c.hashCode() + (this.d * 31);
    }
}
