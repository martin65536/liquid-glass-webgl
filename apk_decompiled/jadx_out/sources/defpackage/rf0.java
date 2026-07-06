package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rf0 {
    public final int a;
    public final float b;
    public final float c;
    public final float d;
    public final long e;

    public rf0(int i, float f, float f2, float f3, long j) {
        this.a = i;
        this.b = f;
        this.c = f2;
        this.d = f3;
        this.e = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && rf0.class == obj.getClass()) {
            rf0 rf0Var = (rf0) obj;
            if (this.c == rf0Var.c && this.d == rf0Var.d && this.b == rf0Var.b && this.a == rf0Var.a && this.e == rf0Var.e) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        int s = (d3.s(this.b, d3.s(this.d, Float.floatToIntBits(this.c) * 31, 31), 31) + this.a) * 31;
        long j = this.e;
        return s + ((int) (j ^ (j >>> 32)));
    }

    public final String toString() {
        return "NavigationEvent(touchX=" + this.c + ", touchY=" + this.d + ", progress=" + this.b + ", swipeEdge=" + this.a + ", frameTimeMillis=" + this.e + ')';
    }
}
