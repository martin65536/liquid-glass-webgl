package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class t11 {
    public static final u11[] b = {new u11(0), new u11(4294967296L), new u11(8589934592L)};
    public static final long c = d20.A(0, Float.NaN);
    public final long a;

    public static final boolean a(long j, long j2) {
        if (j == j2) {
            return true;
        }
        return false;
    }

    public static final long b(long j) {
        return b[(int) ((j & 1095216660480L) >>> 32)].a;
    }

    public static final float c(long j) {
        return Float.intBitsToFloat((int) (j & 4294967295L));
    }

    public static int d(long j) {
        return (int) (j ^ (j >>> 32));
    }

    public static String e(long j) {
        long b2 = b(j);
        if (u11.a(b2, 0L)) {
            return "Unspecified";
        }
        if (u11.a(b2, 4294967296L)) {
            return c(j) + ".sp";
        }
        if (u11.a(b2, 8589934592L)) {
            return c(j) + ".em";
        }
        return "Invalid";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof t11) {
            if (this.a != ((t11) obj).a) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return d(this.a);
    }

    public final String toString() {
        return e(this.a);
    }
}
