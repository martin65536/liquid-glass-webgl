package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fo {
    public final long a;

    public static String a(long j) {
        if (j != 9205357640488583168L) {
            return "(" + ((Object) eo.b(Float.intBitsToFloat((int) (j >> 32)))) + ", " + ((Object) eo.b(Float.intBitsToFloat((int) (j & 4294967295L)))) + ')';
        }
        return "DpOffset.Unspecified";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof fo) {
            if (this.a != ((fo) obj).a) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        long j = this.a;
        return (int) (j ^ (j >>> 32));
    }

    public final String toString() {
        return a(this.a);
    }
}
