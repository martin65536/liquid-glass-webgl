package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k7 {
    public final Object a;
    public final int b;
    public final int c;
    public final String d;

    public k7(Object obj, int i, int i2, String str) {
        boolean z;
        this.a = obj;
        this.b = i;
        this.c = i2;
        this.d = str;
        if (i <= i2) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            r00.a("Reversed range is not supported");
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof k7)) {
            return false;
        }
        k7 k7Var = (k7) obj;
        if (o20.e(this.a, k7Var.a) && this.b == k7Var.b && this.c == k7Var.c && o20.e(this.d, k7Var.d)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int hashCode;
        Object obj = this.a;
        if (obj == null) {
            hashCode = 0;
        } else {
            hashCode = obj.hashCode();
        }
        return this.d.hashCode() + (((((hashCode * 31) + this.b) * 31) + this.c) * 31);
    }

    public final String toString() {
        return "Range(item=" + this.a + ", start=" + this.b + ", end=" + this.c + ", tag=" + this.d + ')';
    }

    public k7(int i, int i2, Object obj) {
        this(obj, i, i2, "");
    }
}
