package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ad implements lr0 {
    public final ir0 a = ir0.f;

    @Override // defpackage.lr0
    public final kr0 a(long j, m40 m40Var, np npVar) {
        m40Var.getClass();
        npVar.getClass();
        float b = mw0.b(j) * 0.5f;
        return new kr0(b, b, b, b);
    }

    @Override // defpackage.zv0
    public final g30 b(long j, m40 m40Var, mm mmVar) {
        m40Var.getClass();
        mmVar.getClass();
        return t20.L(j, mw0.b(j) * 0.5f, this.a);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ad)) {
            return false;
        }
        if (this.a == ((ad) obj).a) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "Capsule(style=" + this.a + ")";
    }
}
