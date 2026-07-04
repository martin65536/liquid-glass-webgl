package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
final class j8 extends gd0 {
    public final float a;

    public j8(float f) {
        this.a = f;
        if (f > 0.0f) {
            return;
        }
        o00.a("aspectRatio " + f + " must be > 0");
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [l8, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? bd0Var = new bd0();
        bd0Var.s = this.a;
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        j8 j8Var;
        if (this == obj) {
            return true;
        }
        if (obj instanceof j8) {
            j8Var = (j8) obj;
        } else {
            j8Var = null;
        }
        if (j8Var != null && this.a == j8Var.a) {
            ((j8) obj).getClass();
            return true;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        ((l8) bd0Var).s = this.a;
    }

    public final int hashCode() {
        return (Float.floatToIntBits(this.a) * 31) + 1237;
    }
}
