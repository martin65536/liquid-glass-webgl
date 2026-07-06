package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class br0 implements a00 {
    public final boolean a;
    public final long b;

    public br0(long j, boolean z) {
        this.a = z;
        this.b = j;
    }

    @Override // defpackage.a00
    public final im a(je0 je0Var) {
        je0Var.getClass();
        return new km(je0Var, this.a, new u2(1, this));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof br0) {
            br0 br0Var = (br0) obj;
            if (this.a != br0Var.a || !eo.a(Float.NaN, Float.NaN)) {
                return false;
            }
            return se.c(this.b, br0Var.b);
        }
        return false;
    }

    @Override // defpackage.a00
    public final int hashCode() {
        int i;
        if (this.a) {
            i = 1231;
        } else {
            i = 1237;
        }
        return se.i(this.b) + d3.s(Float.NaN, i * 31, 961);
    }
}
