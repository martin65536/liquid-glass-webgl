package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hj0 extends g30 {
    public final gr0 a;
    public final y5 b;

    public hj0(gr0 gr0Var) {
        y5 y5Var;
        this.a = gr0Var;
        if (!m20.x(gr0Var)) {
            y5Var = a6.a();
            d3.l(y5Var, gr0Var);
        } else {
            y5Var = null;
        }
        this.b = y5Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof hj0) {
                if (!this.a.equals(((hj0) obj).a)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // defpackage.g30
    public final wo0 r() {
        gr0 gr0Var = this.a;
        return new wo0(gr0Var.a, gr0Var.b, gr0Var.c, gr0Var.d);
    }
}
