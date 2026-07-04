package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qd0 {
    public final long a;
    public final long b;
    public final boolean c;

    public qd0(long j, long j2, boolean z) {
        this.a = j;
        this.b = j2;
        this.c = z;
    }

    public final qd0 a(qd0 qd0Var) {
        return new qd0(ch0.g(this.a, qd0Var.a), Math.max(this.b, qd0Var.b), this.c);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof qd0) {
                qd0 qd0Var = (qd0) obj;
                if (!ch0.c(this.a, qd0Var.a) || this.b != qd0Var.b || this.c != qd0Var.c) {
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
        int e = ch0.e(this.a) * 31;
        long j = this.b;
        int i2 = (e + ((int) (j ^ (j >>> 32)))) * 31;
        if (this.c) {
            i = 1231;
        } else {
            i = 1237;
        }
        return i2 + i;
    }

    public final String toString() {
        return "MouseWheelScrollDelta(value=" + ((Object) ch0.i(this.a)) + ", timeMillis=" + this.b + ", shouldApplyImmediately=" + this.c + ')';
    }
}
