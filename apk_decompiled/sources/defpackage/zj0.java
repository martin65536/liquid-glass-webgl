package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zj0 {
    public final x5 a;
    public final int b;
    public final int c;

    public zj0(x5 x5Var, int i, int i2) {
        this.a = x5Var;
        this.b = i;
        this.c = i2;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof zj0) {
                zj0 zj0Var = (zj0) obj;
                if (this.a == zj0Var.a && this.b == zj0Var.b && this.c == zj0Var.c) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return (((this.a.hashCode() * 31) + this.b) * 31) + this.c;
    }

    public final String toString() {
        return "ParagraphIntrinsicInfo(intrinsics=" + this.a + ", startIndex=" + this.b + ", endIndex=" + this.c + ')';
    }
}
