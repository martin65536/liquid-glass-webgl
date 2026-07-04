package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class x80 {
    public static final x80 d = new x80(u80.c, 17, 0);
    public final float a;
    public final int b;
    public final int c;

    public x80(float f, int i, int i2) {
        this.a = f;
        this.b = i;
        this.c = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof x80)) {
            return false;
        }
        x80 x80Var = (x80) obj;
        float f = x80Var.a;
        float f2 = u80.b;
        if (Float.compare(this.a, f) == 0 && this.b == x80Var.b && this.c == x80Var.c) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        float f = u80.b;
        return (((Float.floatToIntBits(this.a) * 31) + this.b) * 31) + this.c;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("LineHeightStyle(alignment=");
        sb.append((Object) u80.b(this.a));
        sb.append(", trim=");
        String str2 = "Invalid";
        int i = this.b;
        if (i == 1) {
            str = "LineHeightStyle.Trim.FirstLineTop";
        } else if (i == 16) {
            str = "LineHeightStyle.Trim.LastLineBottom";
        } else if (i == 17) {
            str = "LineHeightStyle.Trim.Both";
        } else if (i != 0) {
            str = "Invalid";
        } else {
            str = "LineHeightStyle.Trim.None";
        }
        sb.append((Object) str);
        sb.append(",mode=");
        int i2 = this.c;
        if (i2 == 0) {
            str2 = "LineHeightStyle.Mode.Fixed";
        } else if (i2 == 1) {
            str2 = "LineHeightStyle.Mode.Minimum";
        } else if (i2 == 2) {
            str2 = "LineHeightStyle.Mode.Tight";
        }
        sb.append((Object) str2);
        sb.append(')');
        return sb.toString();
    }
}
