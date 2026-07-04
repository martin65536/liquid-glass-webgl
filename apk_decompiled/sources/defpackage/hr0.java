package defpackage;

import android.graphics.Point;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hr0 {
    public final int a;
    public final int b;
    public final Point c;

    public hr0(int i, int i2, Point point) {
        int i3 = point.x;
        int i4 = point.y;
        this.a = i;
        this.b = i2;
        this.c = new Point(i3, i4);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof hr0) {
            hr0 hr0Var = (hr0) obj;
            if (this.a == hr0Var.a && this.b == hr0Var.b && this.c.equals(hr0Var.c)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.c.hashCode() + (((this.a * 31) + this.b) * 31);
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("RoundedCornerCompat{position=");
        int i = this.a;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        str = "Invalid";
                    } else {
                        str = "BottomLeft";
                    }
                } else {
                    str = "BottomRight";
                }
            } else {
                str = "TopRight";
            }
        } else {
            str = "TopLeft";
        }
        sb.append(str);
        sb.append(", radius=");
        sb.append(this.b);
        sb.append(", center=");
        sb.append(this.c);
        sb.append('}');
        return sb.toString();
    }
}
