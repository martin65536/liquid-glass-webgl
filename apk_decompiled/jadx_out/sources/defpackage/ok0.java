package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ok0 extends fl0 {
    public final float c;
    public final float d;
    public final float e;
    public final float f;
    public final float g;
    public final float h;

    public ok0(float f, float f2, float f3, float f4, float f5, float f6) {
        super(2);
        this.c = f;
        this.d = f2;
        this.e = f3;
        this.f = f4;
        this.g = f5;
        this.h = f6;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ok0)) {
            return false;
        }
        ok0 ok0Var = (ok0) obj;
        if (Float.compare(this.c, ok0Var.c) == 0 && Float.compare(this.d, ok0Var.d) == 0 && Float.compare(this.e, ok0Var.e) == 0 && Float.compare(this.f, ok0Var.f) == 0 && Float.compare(this.g, ok0Var.g) == 0 && Float.compare(this.h, ok0Var.h) == 0) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.h) + d3.s(this.g, d3.s(this.f, d3.s(this.e, d3.s(this.d, Float.floatToIntBits(this.c) * 31, 31), 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CurveTo(x1=");
        sb.append(this.c);
        sb.append(", y1=");
        sb.append(this.d);
        sb.append(", x2=");
        sb.append(this.e);
        sb.append(", y2=");
        sb.append(this.f);
        sb.append(", x3=");
        sb.append(this.g);
        sb.append(", y3=");
        return d3.v(sb, this.h, ')');
    }
}
