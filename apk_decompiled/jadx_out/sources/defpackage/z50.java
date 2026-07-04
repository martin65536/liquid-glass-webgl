package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class z50 extends gd0 {
    public final c60 a;

    public z50(c60 c60Var) {
        this.a = c60Var;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [a60, bd0] */
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
        if ((obj instanceof z50) && this.a == ((z50) obj).a) {
            return true;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        a60 a60Var = (a60) bd0Var;
        c60 c60Var = a60Var.s;
        c60 c60Var2 = this.a;
        if (!o20.e(c60Var, c60Var2) && a60Var.e.r) {
            c60 c60Var3 = a60Var.s;
            c60Var3.c();
            c60Var3.b = null;
            a60Var.s = c60Var2;
        }
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "DisplayingDisappearingItemsElement(animator=" + this.a + ')';
    }
}
