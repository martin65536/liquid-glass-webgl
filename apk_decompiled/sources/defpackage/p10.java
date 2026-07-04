package defpackage;

import java.io.Serializable;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class p10 implements Comparable, Serializable {
    public static final p10 g = new p10(0, -31557014167219200L);
    public static final p10 h = new p10(999999999, 31556889864403199L);
    public final long e;
    public final int f;

    public p10(int i, long j) {
        this.e = j;
        this.f = i;
        if (-31557014167219200L <= j && j < 31556889864403200L) {
            return;
        }
        v7.m("Instant exceeds minimum or maximum instant");
        throw null;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        p10 p10Var = (p10) obj;
        p10Var.getClass();
        int j = o20.j(this.e, p10Var.e);
        if (j != 0) {
            return j;
        }
        return o20.i(this.f, p10Var.f);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof p10) {
                p10 p10Var = (p10) obj;
                if (this.e != p10Var.e || this.f != p10Var.f) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        long j = this.e;
        return (this.f * 51) + ((int) (j ^ (j >>> 32)));
    }

    public final String toString() {
        long j;
        int[] iArr;
        StringBuilder sb = new StringBuilder();
        long j2 = this.e;
        long j3 = j2 / 86400;
        if ((j2 ^ 86400) < 0 && j3 * 86400 != j2) {
            j3--;
        }
        long j4 = j2 % 86400;
        int i = (int) (j4 + (86400 & (((j4 ^ 86400) & ((-j4) | j4)) >> 63)));
        long j5 = 719468 + j3;
        if (j5 < 0) {
            long j6 = ((j3 + 719469) / 146097) - 1;
            j = j6 * 400;
            j5 += (-j6) * 146097;
        } else {
            j = 0;
        }
        long j7 = ((400 * j5) + 591) / 146097;
        long j8 = j5 - ((j7 / 400) + (((j7 / 4) + (365 * j7)) - (j7 / 100)));
        if (j8 < 0) {
            j7--;
            j8 = j5 - ((j7 / 400) + (((j7 / 4) + (365 * j7)) - (j7 / 100)));
        }
        int i2 = (int) j8;
        int i3 = ((i2 * 5) + 2) / 153;
        int i4 = ((i3 + 2) % 12) + 1;
        int i5 = (i2 - (((i3 * 306) + 5) / 10)) + 1;
        int i6 = (int) (j7 + j + (i3 / 10));
        int i7 = i / 3600;
        int i8 = i - (i7 * 3600);
        int i9 = i8 / 60;
        int i10 = i8 - (i9 * 60);
        int i11 = 0;
        if (Math.abs(i6) < 1000) {
            StringBuilder sb2 = new StringBuilder();
            if (i6 >= 0) {
                sb2.append(i6 + 10000);
                sb2.deleteCharAt(0).getClass();
            } else {
                sb2.append(i6 - 10000);
                sb2.deleteCharAt(1).getClass();
            }
            sb.append((CharSequence) sb2);
        } else {
            if (i6 >= 10000) {
                sb.append('+');
            }
            sb.append(i6);
        }
        sb.append('-');
        n20.u(sb, sb, i4);
        sb.append('-');
        n20.u(sb, sb, i5);
        sb.append('T');
        n20.u(sb, sb, i7);
        sb.append(':');
        n20.u(sb, sb, i9);
        sb.append(':');
        n20.u(sb, sb, i10);
        int i12 = this.f;
        if (i12 != 0) {
            sb.append('.');
            while (true) {
                iArr = n20.e;
                int i13 = i11 + 1;
                if (i12 % iArr[i13] != 0) {
                    break;
                }
                i11 = i13;
            }
            int i14 = i11 - (i11 % 3);
            String valueOf = String.valueOf((i12 / iArr[i14]) + iArr[9 - i14]);
            valueOf.getClass();
            sb.append(valueOf.substring(1));
        }
        sb.append('Z');
        return sb.toString();
    }
}
