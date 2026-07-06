package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wp extends gd0 {
    public final gv a;

    public wp(gv gvVar) {
        this.a = gvVar;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [xp, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? bd0Var = new bd0();
        bd0Var.s = this.a;
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof wp)) {
            return false;
        }
        if (this.a == ((wp) obj).a) {
            return true;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        ((xp) bd0Var).s = this.a;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }
}
