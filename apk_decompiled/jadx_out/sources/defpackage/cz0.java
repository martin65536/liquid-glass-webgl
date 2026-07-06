package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class cz0 extends jc0 {
    public final float s;
    public final float t;
    public final int u;
    public final int v;

    public cz0(float f, float f2, int i, int i2) {
        this.s = f;
        this.t = f2;
        this.u = i;
        this.v = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof cz0)) {
            return false;
        }
        cz0 cz0Var = (cz0) obj;
        if (this.s == cz0Var.s && this.t == cz0Var.t && this.u == cz0Var.u && this.v == cz0Var.v) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return (((d3.s(this.t, Float.floatToIntBits(this.s) * 31, 31) + this.u) * 31) + this.v) * 31;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("Stroke(width=");
        sb.append(this.s);
        sb.append(", miter=");
        sb.append(this.t);
        sb.append(", cap=");
        String str2 = "Unknown";
        int i = this.u;
        if (i == 0) {
            str = "Butt";
        } else if (i == 1) {
            str = "Round";
        } else if (i != 2) {
            str = "Unknown";
        } else {
            str = "Square";
        }
        sb.append((Object) str);
        sb.append(", join=");
        int i2 = this.v;
        if (i2 == 0) {
            str2 = "Miter";
        } else if (i2 == 1) {
            str2 = "Round";
        } else if (i2 == 2) {
            str2 = "Bevel";
        }
        sb.append((Object) str2);
        sb.append(", pathEffect=null)");
        return sb.toString();
    }
}
