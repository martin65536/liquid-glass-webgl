package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class se {
    public static final long b = f31.f(4278190080L);
    public static final long c;
    public static final long d;
    public static final long e;
    public static final long f;
    public static final long g;
    public static final /* synthetic */ int h = 0;
    public final long a;

    static {
        f31.f(4282664004L);
        f31.f(4287137928L);
        f31.f(4291611852L);
        c = f31.f(4294967295L);
        d = f31.f(4294901760L);
        f31.f(4278255360L);
        e = f31.f(4278190335L);
        f31.f(4294967040L);
        f31.f(4278255615L);
        f31.f(4294902015L);
        f = f31.e(0);
        g = f31.d(0.0f, 0.0f, 0.0f, 0.0f, af.u);
    }

    public /* synthetic */ se(long j) {
        this.a = j;
    }

    public static final long a(long j, we weVar) {
        pi piVar;
        we f2 = f(j);
        int i = f2.c;
        int i2 = weVar.c;
        if ((i | i2) < 0) {
            piVar = o4.w(f2, weVar);
        } else {
            he0 he0Var = qi.a;
            int i3 = i | (i2 << 6);
            Object b2 = he0Var.b(i3);
            if (b2 == null) {
                b2 = o4.w(f2, weVar);
                he0Var.h(i3, b2);
            }
            piVar = (pi) b2;
        }
        return piVar.a(j);
    }

    public static long b(long j, float f2) {
        return f31.d(h(j), g(j), e(j), f2, f(j));
    }

    public static final boolean c(long j, long j2) {
        if (j == j2) {
            return true;
        }
        return false;
    }

    public static final float d(long j) {
        float N;
        float f2;
        if ((63 & j) == 0) {
            N = (float) d20.N((j >>> 56) & 255);
            f2 = 255.0f;
        } else {
            N = (float) d20.N((j >>> 6) & 1023);
            f2 = 1023.0f;
        }
        return N / f2;
    }

    public static final float e(long j) {
        int i;
        int i2;
        int i3;
        if ((63 & j) == 0) {
            return ((float) d20.N((j >>> 32) & 255)) / 255.0f;
        }
        short s = (short) ((j >>> 16) & 65535);
        int i4 = 32768 & s;
        int i5 = ((65535 & s) >>> 10) & 31;
        int i6 = s & 1023;
        if (i5 == 0) {
            if (i6 != 0) {
                float intBitsToFloat = Float.intBitsToFloat(i6 + 1056964608) - ks.a;
                if (i4 == 0) {
                    return intBitsToFloat;
                }
                return -intBitsToFloat;
            }
            i3 = 0;
            i2 = 0;
        } else {
            int i7 = i6 << 13;
            if (i5 == 31) {
                i = 255;
                if (i7 != 0) {
                    i7 |= 4194304;
                }
            } else {
                i = i5 + 112;
            }
            int i8 = i;
            i2 = i7;
            i3 = i8;
        }
        return Float.intBitsToFloat((i3 << 23) | (i4 << 16) | i2);
    }

    public static final we f(long j) {
        float[] fArr = af.a;
        return af.y[(int) (j & 63)];
    }

    public static final float g(long j) {
        int i;
        int i2;
        int i3;
        if ((63 & j) == 0) {
            return ((float) d20.N((j >>> 40) & 255)) / 255.0f;
        }
        short s = (short) ((j >>> 32) & 65535);
        int i4 = 32768 & s;
        int i5 = ((65535 & s) >>> 10) & 31;
        int i6 = s & 1023;
        if (i5 == 0) {
            if (i6 != 0) {
                float intBitsToFloat = Float.intBitsToFloat(i6 + 1056964608) - ks.a;
                if (i4 == 0) {
                    return intBitsToFloat;
                }
                return -intBitsToFloat;
            }
            i3 = 0;
            i2 = 0;
        } else {
            int i7 = i6 << 13;
            if (i5 == 31) {
                i = 255;
                if (i7 != 0) {
                    i7 |= 4194304;
                }
            } else {
                i = i5 + 112;
            }
            int i8 = i;
            i2 = i7;
            i3 = i8;
        }
        return Float.intBitsToFloat((i3 << 23) | (i4 << 16) | i2);
    }

    public static final float h(long j) {
        int i;
        int i2;
        int i3;
        if ((63 & j) == 0) {
            return ((float) d20.N((j >>> 48) & 255)) / 255.0f;
        }
        short s = (short) ((j >>> 48) & 65535);
        int i4 = 32768 & s;
        int i5 = ((65535 & s) >>> 10) & 31;
        int i6 = s & 1023;
        if (i5 == 0) {
            if (i6 != 0) {
                float intBitsToFloat = Float.intBitsToFloat(i6 + 1056964608) - ks.a;
                if (i4 == 0) {
                    return intBitsToFloat;
                }
                return -intBitsToFloat;
            }
            i3 = 0;
            i2 = 0;
        } else {
            int i7 = i6 << 13;
            if (i5 == 31) {
                i = 255;
                if (i7 != 0) {
                    i7 |= 4194304;
                }
            } else {
                i = i5 + 112;
            }
            int i8 = i;
            i2 = i7;
            i3 = i8;
        }
        return Float.intBitsToFloat((i3 << 23) | (i4 << 16) | i2);
    }

    public static int i(long j) {
        return (int) (j ^ (j >>> 32));
    }

    public static String j(long j) {
        return "Color(" + h(j) + ", " + g(j) + ", " + e(j) + ", " + d(j) + ", " + f(j).a + ')';
    }

    public final boolean equals(Object obj) {
        if (obj instanceof se) {
            if (this.a != ((se) obj).a) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return i(this.a);
    }

    public final String toString() {
        return j(this.a);
    }
}
