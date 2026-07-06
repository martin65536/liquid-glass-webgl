package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class cq0 {
    public final a40 a;
    public final hp0 b;
    public final v11 c;
    public final qm d;

    public cq0(a40 a40Var, hp0 hp0Var, v11 v11Var, qm qmVar) {
        this.a = a40Var;
        this.b = hp0Var;
        this.c = v11Var;
        this.d = qmVar;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj != null && cq0.class == obj.getClass()) {
                cq0 cq0Var = (cq0) obj;
                if (this.a.equals(cq0Var.a) && this.b.equals(cq0Var.b) && this.c == cq0Var.c && this.d == cq0Var.d) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return this.d.hashCode() + ((this.c.hashCode() + ((this.b.a.hashCode() + (this.a.a.hashCode() * 31)) * 31)) * 31);
    }
}
