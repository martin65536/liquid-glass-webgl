package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class j11 {
    public final ux0 a;
    public final ux0 b;
    public final ux0 c;
    public final ux0 d;

    public j11(ux0 ux0Var, ux0 ux0Var2, ux0 ux0Var3, ux0 ux0Var4) {
        this.a = ux0Var;
        this.b = ux0Var2;
        this.c = ux0Var3;
        this.d = ux0Var4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof j11)) {
            return false;
        }
        j11 j11Var = (j11) obj;
        if (o20.e(this.a, j11Var.a) && o20.e(this.b, j11Var.b) && o20.e(this.c, j11Var.c) && o20.e(this.d, j11Var.d)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int i4 = 0;
        ux0 ux0Var = this.a;
        if (ux0Var != null) {
            i = ux0Var.hashCode();
        } else {
            i = 0;
        }
        int i5 = i * 31;
        ux0 ux0Var2 = this.b;
        if (ux0Var2 != null) {
            i2 = ux0Var2.hashCode();
        } else {
            i2 = 0;
        }
        int i6 = (i5 + i2) * 31;
        ux0 ux0Var3 = this.c;
        if (ux0Var3 != null) {
            i3 = ux0Var3.hashCode();
        } else {
            i3 = 0;
        }
        int i7 = (i6 + i3) * 31;
        ux0 ux0Var4 = this.d;
        if (ux0Var4 != null) {
            i4 = ux0Var4.hashCode();
        }
        return i7 + i4;
    }
}
