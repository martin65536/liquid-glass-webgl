package defpackage;

import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class p5 {
    public int a;
    public Object b;
    public Object c;

    public static /* synthetic */ void e(p5 p5Var, int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2, boolean z3, int i7) {
        int i8;
        if ((i7 & 32) != 0) {
            i8 = -1;
        } else {
            i8 = i6;
        }
        p5Var.d(i, i2, i3, i4, i5, i8, z, z2, z3, -1);
    }

    public void a(int i, r7 r7Var) {
        if (i < 0) {
            t00.a("size should be >=0");
        }
        if (i == 0) {
            return;
        }
        l20 l20Var = new l20(this.a, i, r7Var);
        this.a += i;
        ((ef0) this.b).b(l20Var);
    }

    public l20 b(int i) {
        if (i < 0 || i >= this.a) {
            t00.d("Index " + i + ", size " + this.a);
        }
        l20 l20Var = (l20) this.c;
        if (l20Var != null) {
            int i2 = l20Var.a;
            if (i < l20Var.b + i2 && i2 <= i) {
                return l20Var;
            }
        }
        ef0 ef0Var = (ef0) this.b;
        l20 l20Var2 = (l20) ef0Var.e[m20.f(i, ef0Var)];
        this.c = l20Var2;
        return l20Var2;
    }

    public int c(Object obj) {
        oe0 oe0Var = (oe0) this.b;
        int d = oe0Var.d(obj);
        if (d >= 0) {
            return oe0Var.c[d];
        }
        return -1;
    }

    public void d(int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2, boolean z3, int i7) {
        int i8;
        long[] jArr = (long[]) this.b;
        int i9 = this.a;
        int i10 = i9 + 3;
        this.a = i10;
        int length = jArr.length;
        if (length <= i10) {
            int max = Math.max(length * 2, i10);
            this.b = Arrays.copyOf(jArr, max);
            this.c = Arrays.copyOf((long[]) this.c, max);
        }
        long[] jArr2 = (long[]) this.b;
        jArr2[i9] = (i2 << 32) | (i3 & 4294967295L);
        jArr2[i9 + 1] = (i4 << 32) | (i5 & 4294967295L);
        int i11 = i6 & 33554431;
        jArr2[i9 + 2] = ((z3 ? 1L : 0L) << 63) | ((z2 ? 1L : 0L) << 62) | ((z ? 1L : 0L) << 61) | 1152921504606846976L | (Math.min(0, 1023) << 50) | (i11 << 25) | (i & 33554431);
        if (i6 >= 0) {
            if (i7 != -1) {
                i8 = i7;
            } else {
                i8 = i9 - 3;
            }
            while (i8 >= 0) {
                int i12 = i8 + 2;
                long j = jArr2[i12];
                if ((((int) j) & 33554431) == i11) {
                    int i13 = (i9 - i8) / 3;
                    int i14 = xo0.b;
                    jArr2[i12] = (Math.min(i13, 1023) << 50) | (j & (-1151795604700004353L));
                    return;
                }
                i8 -= 3;
            }
        }
    }

    public void f(int i, boolean z) {
        int i2 = i & 33554431;
        long[] jArr = (long[]) this.b;
        int i3 = this.a;
        for (int i4 = 0; i4 < jArr.length - 2 && i4 < i3; i4 += 3) {
            int i5 = i4 + 2;
            long j = jArr[i5];
            if ((((int) j) & 33554431) == i2) {
                long j2 = 8070450532247928831L & j;
                long j3 = z ? 1L : 0L;
                jArr[i5] = j2 | (1152921504606846976L * j3) | (j3 * Long.MIN_VALUE);
                return;
            }
        }
    }

    public void g(int i, int i2, long j) {
        int i3;
        int i4;
        char c;
        char c2;
        long[] jArr = (long[]) this.b;
        long[] jArr2 = (long[]) this.c;
        jArr2[0] = j;
        int i5 = 1;
        while (i5 > 0) {
            i5--;
            long j2 = jArr2[i5];
            int i6 = 33554431;
            int i7 = ((int) j2) & 33554431;
            char c3 = 25;
            int i8 = ((int) (j2 >> 25)) & 33554431;
            char c4 = '2';
            int i9 = ((int) (j2 >> 50)) & 1023;
            if (i9 == 1023) {
                i3 = this.a;
            } else {
                i3 = (i9 * 3) + i8;
            }
            if (i8 >= 0) {
                while (i8 < jArr.length - 2 && i8 < i3) {
                    int i10 = i8 + 2;
                    long j3 = jArr[i10];
                    if ((((int) (j3 >> c3)) & i6) == i7) {
                        long j4 = jArr[i8];
                        int i11 = i8 + 1;
                        i4 = i6;
                        c = c3;
                        long j5 = jArr[i11];
                        c2 = c4;
                        jArr[i8] = ((((int) j4) + i2) & 4294967295L) | ((((int) (j4 >> 32)) + i) << 32);
                        jArr[i11] = ((((int) j5) + i2) & 4294967295L) | ((((int) (j5 >> 32)) + i) << 32);
                        jArr[i10] = (((j3 >> 63) & 1) << 60) | j3;
                        if ((((int) (j3 >> c2)) & 1023) > 0) {
                            int i12 = xo0.b;
                            jArr2[i5] = ((-1125899873288193L) & j3) | (((i8 + 3) & i4) << c);
                            i5++;
                        }
                    } else {
                        i4 = i6;
                        c = c3;
                        c2 = c4;
                    }
                    i8 += 3;
                    i6 = i4;
                    c3 = c;
                    c4 = c2;
                }
            } else {
                return;
            }
        }
    }

    public void h(int i, mv mvVar) {
        int i2 = i & 33554431;
        long[] jArr = (long[]) this.b;
        int i3 = this.a;
        for (int i4 = 0; i4 < jArr.length - 2 && i4 < i3; i4 += 3) {
            if ((((int) jArr[i4 + 2]) & 33554431) == i2) {
                long j = jArr[i4];
                long j2 = jArr[i4 + 1];
                mvVar.h(Integer.valueOf((int) (j >> 32)), Integer.valueOf((int) j), Integer.valueOf((int) (j2 >> 32)), Integer.valueOf((int) j2));
                return;
            }
        }
    }
}
