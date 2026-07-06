package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mk0 extends fl0 {
    public final float c;
    public final float d;
    public final float e;
    public final boolean f;
    public final boolean g;
    public final float h;
    public final float i;

    public mk0(float f, float f2, float f3, boolean z, boolean z2, float f4, float f5) {
        super(3);
        this.c = f;
        this.d = f2;
        this.e = f3;
        this.f = z;
        this.g = z2;
        this.h = f4;
        this.i = f5;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof mk0)) {
            return false;
        }
        mk0 mk0Var = (mk0) obj;
        if (Float.compare(this.c, mk0Var.c) == 0 && Float.compare(this.d, mk0Var.d) == 0 && Float.compare(this.e, mk0Var.e) == 0 && this.f == mk0Var.f && this.g == mk0Var.g && Float.compare(this.h, mk0Var.h) == 0 && Float.compare(this.i, mk0Var.i) == 0) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i;
        int s = d3.s(this.e, d3.s(this.d, Float.floatToIntBits(this.c) * 31, 31), 31);
        int i2 = 1237;
        if (this.f) {
            i = 1231;
        } else {
            i = 1237;
        }
        int i3 = (s + i) * 31;
        if (this.g) {
            i2 = 1231;
        }
        return Float.floatToIntBits(this.i) + d3.s(this.h, (i3 + i2) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ArcTo(horizontalEllipseRadius=");
        sb.append(this.c);
        sb.append(", verticalEllipseRadius=");
        sb.append(this.d);
        sb.append(", theta=");
        sb.append(this.e);
        sb.append(", isMoreThanHalf=");
        sb.append(this.f);
        sb.append(", isPositiveArc=");
        sb.append(this.g);
        sb.append(", arcStartX=");
        sb.append(this.h);
        sb.append(", arcStartY=");
        return d3.v(sb, this.i, ')');
    }
}
