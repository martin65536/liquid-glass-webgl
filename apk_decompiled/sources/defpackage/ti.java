package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class ti {
    public static final long a(int i, int i2, int i3, int i4) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = false;
        if (i2 >= i) {
            z = true;
        } else {
            z = false;
        }
        if (i4 >= i3) {
            z2 = true;
        } else {
            z2 = false;
        }
        boolean z5 = z & z2;
        if (i >= 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        boolean z6 = z5 & z3;
        if (i3 >= 0) {
            z4 = true;
        }
        if (!(z4 & z6)) {
            s00.a("maxWidth must be >= than minWidth,\nmaxHeight must be >= than minHeight,\nminWidth and minHeight must be >= 0");
        }
        return g(i, i2, i3, i4);
    }

    public static /* synthetic */ long b(int i, int i2, int i3) {
        if ((i3 & 2) != 0) {
            i = Integer.MAX_VALUE;
        }
        if ((i3 & 8) != 0) {
            i2 = Integer.MAX_VALUE;
        }
        return a(0, i, 0, i2);
    }

    public static final int c(int i) {
        if (i < 8191) {
            return 13;
        }
        if (i < 32767) {
            return 15;
        }
        if (i < 65535) {
            return 16;
        }
        if (i < 262143) {
            return 18;
        }
        return 255;
    }

    public static final long d(long j, long j2) {
        int i = (int) (j2 >> 32);
        int j3 = si.j(j);
        int h = si.h(j);
        if (i < j3) {
            i = j3;
        }
        if (i <= h) {
            h = i;
        }
        int i2 = (int) (j2 & 4294967295L);
        int i3 = si.i(j);
        int g = si.g(j);
        if (i2 < i3) {
            i2 = i3;
        }
        if (i2 <= g) {
            g = i2;
        }
        return (h << 32) | (4294967295L & g);
    }

    public static final int e(int i, long j) {
        int i2 = si.i(j);
        int g = si.g(j);
        if (i < i2) {
            i = i2;
        }
        if (i > g) {
            return g;
        }
        return i;
    }

    public static final int f(int i, long j) {
        int j2 = si.j(j);
        int h = si.h(j);
        if (i < j2) {
            i = j2;
        }
        if (i > h) {
            return h;
        }
        return i;
    }

    public static final long g(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        if (i4 == Integer.MAX_VALUE) {
            i5 = i3;
        } else {
            i5 = i4;
        }
        int c = c(i5);
        if (i2 == Integer.MAX_VALUE) {
            i6 = i;
        } else {
            i6 = i2;
        }
        int c2 = c(i6);
        if (c + c2 > 31) {
            i(i6, i5);
        }
        int i7 = i2 + 1;
        int i8 = i4 + 1;
        int i9 = c2 - 13;
        return ((i7 & (~(i7 >> 31))) << 33) | ((i9 >> 1) + (i9 & 1)) | (i << 2) | (i3 << (c2 + 2)) | ((i8 & (~(i8 >> 31))) << (c2 + 33));
    }

    public static final long h(int i, int i2, long j) {
        int j2 = si.j(j) + i;
        int i3 = 0;
        if (j2 < 0) {
            j2 = 0;
        }
        int h = si.h(j);
        if (h != Integer.MAX_VALUE && (h = h + i) < 0) {
            h = 0;
        }
        int i4 = si.i(j) + i2;
        if (i4 < 0) {
            i4 = 0;
        }
        int g = si.g(j);
        if (g == Integer.MAX_VALUE || (g = g + i2) >= 0) {
            i3 = g;
        }
        return a(j2, h, i4, i3);
    }

    public static final void i(int i, int i2) {
        throw new IllegalArgumentException("Can't represent a width of " + i + " and height of " + i2 + " in Constraints");
    }

    public static final Void j(int i) {
        throw new IllegalArgumentException("Can't represent a size of " + i + " in Constraints");
    }
}
