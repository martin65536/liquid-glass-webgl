package defpackage;

import android.graphics.Insets;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class g10 {
    public static final g10 e = new g10(0, 0, 0, 0);
    public final int a;
    public final int b;
    public final int c;
    public final int d;

    public g10(int i, int i2, int i3, int i4) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
    }

    public static g10 a(g10 g10Var, g10 g10Var2) {
        return b(Math.max(g10Var.a, g10Var2.a), Math.max(g10Var.b, g10Var2.b), Math.max(g10Var.c, g10Var2.c), Math.max(g10Var.d, g10Var2.d));
    }

    public static g10 b(int i, int i2, int i3, int i4) {
        if (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            return e;
        }
        return new g10(i, i2, i3, i4);
    }

    public static g10 c(Insets insets) {
        int i;
        int i2;
        int i3;
        int i4;
        i = insets.left;
        i2 = insets.top;
        i3 = insets.right;
        i4 = insets.bottom;
        return b(i, i2, i3, i4);
    }

    public final Insets d() {
        return xi.h(this.a, this.b, this.c, this.d);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || g10.class != obj.getClass()) {
            return false;
        }
        g10 g10Var = (g10) obj;
        if (this.d == g10Var.d && this.a == g10Var.a && this.c == g10Var.c && this.b == g10Var.b) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return (((((this.a * 31) + this.b) * 31) + this.c) * 31) + this.d;
    }

    public final String toString() {
        return "Insets{left=" + this.a + ", top=" + this.b + ", right=" + this.c + ", bottom=" + this.d + '}';
    }
}
