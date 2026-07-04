package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zr extends gd0 {
    public final gn a;
    public final float b;

    public zr(gn gnVar, float f) {
        this.a = gnVar;
        this.b = f;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [as, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? bd0Var = new bd0();
        bd0Var.s = this.a;
        bd0Var.t = this.b;
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof zr) {
                zr zrVar = (zr) obj;
                if (this.a == zrVar.a && this.b == zrVar.b) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        as asVar = (as) bd0Var;
        asVar.s = this.a;
        asVar.t = this.b;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.b) + (this.a.hashCode() * 31);
    }
}
