package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rj0 extends gd0 {
    public final float a;
    public final float b;
    public final float c;
    public final float d;

    public rj0(float f, float f2, float f3, float f4) {
        boolean z;
        boolean z2;
        boolean z3;
        this.a = f;
        this.b = f2;
        this.c = f3;
        this.d = f4;
        boolean z4 = true;
        if (f < 0.0f && !Float.isNaN(f)) {
            z = false;
        } else {
            z = true;
        }
        if (f2 < 0.0f && !Float.isNaN(f2)) {
            z2 = false;
        } else {
            z2 = true;
        }
        boolean z5 = z & z2;
        if (f3 < 0.0f && !Float.isNaN(f3)) {
            z3 = false;
        } else {
            z3 = true;
        }
        boolean z6 = z5 & z3;
        if (f4 < 0.0f && !Float.isNaN(f4)) {
            z4 = false;
        }
        if (!(z6 & z4)) {
            o00.a("Padding must be non-negative");
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [sj0, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? bd0Var = new bd0();
        bd0Var.s = this.a;
        bd0Var.t = this.b;
        bd0Var.u = this.c;
        bd0Var.v = this.d;
        bd0Var.w = true;
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        rj0 rj0Var;
        if (obj instanceof rj0) {
            rj0Var = (rj0) obj;
        } else {
            rj0Var = null;
        }
        if (rj0Var != null && eo.a(this.a, rj0Var.a) && eo.a(this.b, rj0Var.b) && eo.a(this.c, rj0Var.c) && eo.a(this.d, rj0Var.d)) {
            return true;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        sj0 sj0Var = (sj0) bd0Var;
        sj0Var.s = this.a;
        sj0Var.t = this.b;
        sj0Var.u = this.c;
        sj0Var.v = this.d;
        sj0Var.w = true;
    }

    public final int hashCode() {
        return ((Float.floatToIntBits(this.d) + d3.s(this.c, d3.s(this.b, Float.floatToIntBits(this.a) * 31, 31), 31)) * 31) + 1231;
    }
}
