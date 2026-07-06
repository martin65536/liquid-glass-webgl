package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xk0 extends fl0 {
    public final float c;

    public xk0(float f) {
        super(3);
        this.c = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof xk0) && Float.compare(this.c, ((xk0) obj).c) == 0) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.c);
    }

    public final String toString() {
        return d3.v(new StringBuilder("RelativeHorizontalTo(dx="), this.c, ')');
    }
}
