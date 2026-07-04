package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class c90 extends e90 {
    public final String a;
    public final j11 b;

    public c90(String str, j11 j11Var) {
        this.a = str;
        this.b = j11Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof c90) {
                c90 c90Var = (c90) obj;
                if (!this.a.equals(c90Var.a) || !o20.e(this.b, c90Var.b)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int hashCode = this.a.hashCode() * 31;
        j11 j11Var = this.b;
        if (j11Var != null) {
            i = j11Var.hashCode();
        } else {
            i = 0;
        }
        return (hashCode + i) * 31;
    }

    public final String toString() {
        return "LinkAnnotation.Clickable(tag=" + this.a + ')';
    }
}
