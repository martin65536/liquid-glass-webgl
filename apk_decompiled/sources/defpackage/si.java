package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class si {
    public final long a;

    public /* synthetic */ si(long j) {
        this.a = j;
    }

    public static long a(long j, int i, int i2, int i3, int i4, int i5) {
        if ((i5 & 1) != 0) {
            i = j(j);
        }
        if ((i5 & 2) != 0) {
            i2 = h(j);
        }
        if ((i5 & 4) != 0) {
            i3 = i(j);
        }
        if ((i5 & 8) != 0) {
            i4 = g(j);
        }
        if (i2 < i || i4 < i3 || i < 0 || i3 < 0) {
            s00.a("maxWidth must be >= than minWidth,\nmaxHeight must be >= than minHeight,\nminWidth and minHeight must be >= 0");
        }
        return ti.g(i, i2, i3, i4);
    }

    public static final boolean b(long j, long j2) {
        if (j == j2) {
            return true;
        }
        return false;
    }

    public static final boolean c(long j) {
        int i = (int) (3 & j);
        int i2 = (((i & 2) >> 1) * 3) + ((i & 1) << 1);
        if ((((int) (j >> (i2 + 46))) & ((1 << (18 - i2)) - 1)) != 0) {
            return true;
        }
        return false;
    }

    public static final boolean d(long j) {
        int i = (int) (3 & j);
        if ((((int) (j >> 33)) & ((1 << (((((i & 2) >> 1) * 3) + ((i & 1) << 1)) + 13)) - 1)) != 0) {
            return true;
        }
        return false;
    }

    public static final boolean e(long j) {
        int i;
        int i2 = (int) (3 & j);
        int i3 = (((i2 & 2) >> 1) * 3) + ((i2 & 1) << 1);
        int i4 = (1 << (18 - i3)) - 1;
        int i5 = ((int) (j >> (i3 + 15))) & i4;
        int i6 = ((int) (j >> (i3 + 46))) & i4;
        if (i6 == 0) {
            i = Integer.MAX_VALUE;
        } else {
            i = i6 - 1;
        }
        if (i5 == i) {
            return true;
        }
        return false;
    }

    public static final boolean f(long j) {
        int i;
        int i2 = (int) (3 & j);
        int i3 = (1 << (((((i2 & 2) >> 1) * 3) + ((i2 & 1) << 1)) + 13)) - 1;
        int i4 = ((int) (j >> 2)) & i3;
        int i5 = ((int) (j >> 33)) & i3;
        if (i5 == 0) {
            i = Integer.MAX_VALUE;
        } else {
            i = i5 - 1;
        }
        if (i4 == i) {
            return true;
        }
        return false;
    }

    public static final int g(long j) {
        int i = (int) (3 & j);
        int i2 = (((i & 2) >> 1) * 3) + ((i & 1) << 1);
        int i3 = ((int) (j >> (i2 + 46))) & ((1 << (18 - i2)) - 1);
        if (i3 == 0) {
            return Integer.MAX_VALUE;
        }
        return i3 - 1;
    }

    public static final int h(long j) {
        int i = (int) (3 & j);
        int i2 = (int) (j >> 33);
        int i3 = i2 & ((1 << (((((i & 2) >> 1) * 3) + ((i & 1) << 1)) + 13)) - 1);
        if (i3 == 0) {
            return Integer.MAX_VALUE;
        }
        return i3 - 1;
    }

    public static final int i(long j) {
        int i = (int) (3 & j);
        int i2 = (((i & 2) >> 1) * 3) + ((i & 1) << 1);
        return ((int) (j >> (i2 + 15))) & ((1 << (18 - i2)) - 1);
    }

    public static final int j(long j) {
        int i = (int) (3 & j);
        return ((int) (j >> 2)) & ((1 << (((((i & 2) >> 1) * 3) + ((i & 1) << 1)) + 13)) - 1);
    }

    public static String k(long j) {
        String valueOf;
        int h = h(j);
        String str = "Infinity";
        if (h == Integer.MAX_VALUE) {
            valueOf = "Infinity";
        } else {
            valueOf = String.valueOf(h);
        }
        int g = g(j);
        if (g != Integer.MAX_VALUE) {
            str = String.valueOf(g);
        }
        return "Constraints(minWidth = " + j(j) + ", maxWidth = " + valueOf + ", minHeight = " + i(j) + ", maxHeight = " + str + ')';
    }

    public final boolean equals(Object obj) {
        if (obj instanceof si) {
            if (this.a != ((si) obj).a) {
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
        return k(this.a);
    }
}
