package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ot0 extends gd0 {
    public final au0 a;
    public final dj0 b;
    public final boolean c;
    public final rl d;
    public final je0 e;
    public final boolean f;
    public final e5 g;

    public ot0(e5 e5Var, rl rlVar, je0 je0Var, dj0 dj0Var, au0 au0Var, boolean z, boolean z2) {
        this.a = au0Var;
        this.b = dj0Var;
        this.c = z;
        this.d = rlVar;
        this.e = je0Var;
        this.f = z2;
        this.g = e5Var;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [jm, pt0, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? jmVar = new jm();
        jmVar.u = this.a;
        jmVar.v = this.b;
        jmVar.w = this.c;
        jmVar.x = this.d;
        jmVar.y = this.e;
        jmVar.z = this.f;
        jmVar.A = this.g;
        return jmVar;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj != null && ot0.class == obj.getClass()) {
                ot0 ot0Var = (ot0) obj;
                if (o20.e(this.a, ot0Var.a) && this.b == ot0Var.b && this.c == ot0Var.c && o20.e(this.d, ot0Var.d) && o20.e(this.e, ot0Var.e) && this.f == ot0Var.f && o20.e(this.g, ot0Var.g)) {
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
        ((pt0) bd0Var).I0(this.g, this.d, this.e, this.b, this.a, this.f, this.c);
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int hashCode = (this.b.hashCode() + (this.a.hashCode() * 31)) * 31;
        int i4 = 1237;
        if (this.c) {
            i = 1231;
        } else {
            i = 1237;
        }
        int i5 = (((hashCode + i) * 31) + 1237) * 31;
        int i6 = 0;
        rl rlVar = this.d;
        if (rlVar != null) {
            i2 = rlVar.hashCode();
        } else {
            i2 = 0;
        }
        int i7 = (i5 + i2) * 31;
        je0 je0Var = this.e;
        if (je0Var != null) {
            i3 = je0Var.hashCode();
        } else {
            i3 = 0;
        }
        int i8 = (i7 + i3) * 961;
        if (this.f) {
            i4 = 1231;
        }
        int i9 = (i8 + i4) * 31;
        e5 e5Var = this.g;
        if (e5Var != null) {
            i6 = e5Var.hashCode();
        }
        return i9 + i6;
    }
}
