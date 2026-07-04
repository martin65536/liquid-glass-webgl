package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class sk0 extends fl0 {
    public final float c;
    public final float d;
    public final float e;
    public final float f;

    public sk0(float f, float f2, float f3, float f4) {
        super(1);
        this.c = f;
        this.d = f2;
        this.e = f3;
        this.f = f4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof sk0)) {
            return false;
        }
        sk0 sk0Var = (sk0) obj;
        if (Float.compare(this.c, sk0Var.c) == 0 && Float.compare(this.d, sk0Var.d) == 0 && Float.compare(this.e, sk0Var.e) == 0 && Float.compare(this.f, sk0Var.f) == 0) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.f) + d3.s(this.e, d3.s(this.d, Float.floatToIntBits(this.c) * 31, 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("QuadTo(x1=");
        sb.append(this.c);
        sb.append(", y1=");
        sb.append(this.d);
        sb.append(", x2=");
        sb.append(this.e);
        sb.append(", y2=");
        return d3.v(sb, this.f, ')');
    }
}
