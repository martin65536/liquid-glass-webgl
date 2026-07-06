package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class he implements ie {
    public final float e;

    public he(float f) {
        this.e = f;
    }

    public static boolean c(Float f, Float f2) {
        if (f.floatValue() <= f2.floatValue()) {
            return true;
        }
        return false;
    }

    @Override // defpackage.ie
    public final Comparable a() {
        return Float.valueOf(0.0f);
    }

    @Override // defpackage.ie
    public final Comparable b() {
        return Float.valueOf(this.e);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof he) {
            if ((isEmpty() && ((he) obj).isEmpty()) || this.e == ((he) obj).e) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return Float.floatToIntBits(this.e) + (Float.floatToIntBits(0.0f) * 31);
    }

    @Override // defpackage.ie
    public final boolean isEmpty() {
        if (0.0f > this.e) {
            return true;
        }
        return false;
    }

    public final String toString() {
        return "0.0.." + this.e;
    }
}
