package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class d90 extends e90 {
    public final String a;
    public final j11 b;

    public d90(String str, j11 j11Var) {
        this.a = str;
        this.b = j11Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof d90) {
                d90 d90Var = (d90) obj;
                if (!this.a.equals(d90Var.a) || !o20.e(this.b, d90Var.b)) {
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
        return "LinkAnnotation.Url(url=" + this.a + ')';
    }
}
