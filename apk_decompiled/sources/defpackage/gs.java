package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gs {
    public final float a;
    public final float b;
    public final long c;

    public gs(float f, float f2, long j) {
        this.a = f;
        this.b = f2;
        this.c = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof gs)) {
            return false;
        }
        gs gsVar = (gs) obj;
        if (Float.compare(this.a, gsVar.a) == 0 && Float.compare(this.b, gsVar.b) == 0 && this.c == gsVar.c) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int s = d3.s(this.b, Float.floatToIntBits(this.a) * 31, 31);
        long j = this.c;
        return s + ((int) (j ^ (j >>> 32)));
    }

    public final String toString() {
        return "FlingInfo(initialVelocity=" + this.a + ", distance=" + this.b + ", duration=" + this.c + ')';
    }
}
