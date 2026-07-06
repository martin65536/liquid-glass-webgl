package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fq0 {
    public final String a;

    public fq0(String str) {
        this.a = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof fq0) {
            fq0 fq0Var = (fq0) obj;
            ir irVar = ir.e;
            if (irVar.equals(irVar) && this.a.equals(fq0Var.a)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode() * 961;
    }

    public final String toString() {
        return "ResourceItem(qualifiers=" + ir.e + ", path=" + this.a + ", offset=-1, size=-1)";
    }
}
