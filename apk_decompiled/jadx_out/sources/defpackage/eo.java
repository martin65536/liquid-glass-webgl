package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class eo implements Comparable {
    public final float e;

    public static final boolean a(float f, float f2) {
        if (Float.compare(f, f2) == 0) {
            return true;
        }
        return false;
    }

    public static String b(float f) {
        if (Float.isNaN(f)) {
            return "Dp.Unspecified";
        }
        return f + ".dp";
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        float f = ((eo) obj).e;
        float f2 = this.e;
        if (!Float.isNaN(f2) && !Float.isNaN(f)) {
            return Float.compare(f2, f);
        }
        return 0;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof eo) {
            if (Float.compare(this.e, ((eo) obj).e) != 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.e);
    }

    public final String toString() {
        return b(this.e);
    }
}
