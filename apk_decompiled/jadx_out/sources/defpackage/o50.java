package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class o50 extends gd0 {
    public final float a;

    public o50(float f) {
        this.a = f;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [p50, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? bd0Var = new bd0();
        bd0Var.s = this.a;
        bd0Var.t = true;
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        o50 o50Var;
        if (this == obj) {
            return true;
        }
        if (obj instanceof o50) {
            o50Var = (o50) obj;
        } else {
            o50Var = null;
        }
        if (o50Var != null && this.a == o50Var.a) {
            return true;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        p50 p50Var = (p50) bd0Var;
        p50Var.s = this.a;
        p50Var.t = true;
    }

    public final int hashCode() {
        return (Float.floatToIntBits(this.a) * 31) + 1231;
    }
}
