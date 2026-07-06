package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class n40 extends gd0 {
    public final lv a;

    public n40(lv lvVar) {
        this.a = lvVar;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [q40, bd0] */
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
        if (!(obj instanceof n40)) {
            return false;
        }
        if (this.a == ((n40) obj).a) {
            return true;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        ((q40) bd0Var).s = this.a;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }
}
