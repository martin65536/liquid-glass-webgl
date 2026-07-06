package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class e31 implements ds {
    public final int a;
    public final eq b;

    public e31(int i, eq eqVar) {
        this.a = i;
        this.b = eqVar;
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [s41, pu, java.lang.Object] */
    @Override // defpackage.c7
    public final s41 c(c4 c4Var) {
        ?? obj = new Object();
        int i = this.a;
        obj.a = i;
        obj.b = new e3((ls) new ns(i, this.b));
        return obj;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof e31) {
            e31 e31Var = (e31) obj;
            if (e31Var.a == this.a && o20.e(e31Var.b, this.b)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        return (this.b.hashCode() + (this.a * 31)) * 31;
    }
}
