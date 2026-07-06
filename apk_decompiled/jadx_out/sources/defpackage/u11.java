package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class u11 {
    public final long a;

    public static final boolean a(long j, long j2) {
        if (j == j2) {
            return true;
        }
        return false;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof u11) {
            if (this.a != ((u11) obj).a) {
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
        long j = this.a;
        if (a(j, 0L)) {
            return "Unspecified";
        }
        if (a(j, 4294967296L)) {
            return "Sp";
        }
        if (a(j, 8589934592L)) {
            return "Em";
        }
        return "Invalid";
    }
}
