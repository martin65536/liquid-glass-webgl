package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class y10 extends w10 implements ie {
    public static final y10 h = new w10(1, 0, 1);

    @Override // defpackage.ie
    public final Comparable a() {
        return Integer.valueOf(this.e);
    }

    @Override // defpackage.ie
    public final Comparable b() {
        return Integer.valueOf(this.f);
    }

    @Override // defpackage.w10
    public final boolean equals(Object obj) {
        if (obj instanceof y10) {
            if (!isEmpty() || !((y10) obj).isEmpty()) {
                y10 y10Var = (y10) obj;
                if (this.e == y10Var.e && this.f == y10Var.f) {
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    @Override // defpackage.w10
    public final int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (this.e * 31) + this.f;
    }

    @Override // defpackage.w10, defpackage.ie
    public final boolean isEmpty() {
        if (this.e > this.f) {
            return true;
        }
        return false;
    }

    @Override // defpackage.w10
    public final String toString() {
        return this.e + ".." + this.f;
    }
}
