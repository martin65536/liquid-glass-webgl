package defpackage;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ax0 implements Iterable, q30 {
    public static final ax0 i = new ax0(0, 0, 0, null);
    public final long e;
    public final long f;
    public final long g;
    public final long[] h;

    public ax0(long j, long j2, long j3, long[] jArr) {
        this.e = j;
        this.f = j2;
        this.g = j3;
        this.h = jArr;
    }

    public final ax0 a(ax0 ax0Var) {
        long[] jArr;
        ax0 ax0Var2 = this;
        ax0 ax0Var3 = i;
        if (ax0Var == ax0Var3) {
            return ax0Var2;
        }
        if (ax0Var2 == ax0Var3) {
            return ax0Var3;
        }
        long j = ax0Var.g;
        long j2 = ax0Var.g;
        long[] jArr2 = ax0Var.h;
        long j3 = ax0Var.f;
        long j4 = ax0Var.e;
        long j5 = ax0Var2.g;
        if (j == j5 && jArr2 == (jArr = ax0Var2.h)) {
            return new ax0(ax0Var2.e & (~j4), ax0Var2.f & (~j3), j5, jArr);
        }
        if (jArr2 != null) {
            for (long j6 : jArr2) {
                ax0Var2 = ax0Var2.b(j6);
            }
        }
        if (j3 != 0) {
            for (int i2 = 0; i2 < 64; i2++) {
                if (((1 << i2) & j3) != 0) {
                    ax0Var2 = ax0Var2.b(i2 + j2);
                }
            }
        }
        if (j4 != 0) {
            for (int i3 = 0; i3 < 64; i3++) {
                if (((1 << i3) & j4) != 0) {
                    ax0Var2 = ax0Var2.b(i3 + j2 + 64);
                }
            }
        }
        return ax0Var2;
    }

    public final ax0 b(long j) {
        long[] jArr;
        int k;
        long[] jArr2;
        long j2 = j - this.g;
        if (o20.j(j2, 0L) >= 0 && o20.j(j2, 64L) < 0) {
            long j3 = 1 << ((int) j2);
            long j4 = this.f;
            if ((j4 & j3) != 0) {
                return new ax0(this.e, j4 & (~j3), this.g, this.h);
            }
        } else if (o20.j(j2, 64L) >= 0 && o20.j(j2, 128L) < 0) {
            long j5 = 1 << (((int) j2) - 64);
            long j6 = this.e;
            if ((j6 & j5) != 0) {
                return new ax0(j6 & (~j5), this.f, this.g, this.h);
            }
        } else if (o20.j(j2, 0L) < 0 && (jArr = this.h) != null && (k = g30.k(jArr, j)) >= 0) {
            int length = jArr.length;
            int i2 = length - 1;
            if (i2 == 0) {
                jArr2 = null;
            } else {
                long[] jArr3 = new long[i2];
                if (k > 0) {
                    i8.M(jArr, jArr3, 0, 0, k);
                }
                if (k < i2) {
                    i8.M(jArr, jArr3, k, k + 1, length);
                }
                jArr2 = jArr3;
            }
            return new ax0(this.e, this.f, this.g, jArr2);
        }
        return this;
    }

    public final boolean c(long j) {
        long[] jArr;
        long j2 = j - this.g;
        if (o20.j(j2, 0L) >= 0 && o20.j(j2, 64L) < 0) {
            if (((1 << ((int) j2)) & this.f) != 0) {
                return true;
            }
            return false;
        }
        if (o20.j(j2, 64L) >= 0 && o20.j(j2, 128L) < 0) {
            if (((1 << (((int) j2) - 64)) & this.e) != 0) {
                return true;
            }
            return false;
        }
        if (o20.j(j2, 0L) <= 0 && (jArr = this.h) != null && g30.k(jArr, j) >= 0) {
            return true;
        }
        return false;
    }

    public final ax0 d(ax0 ax0Var) {
        ax0 ax0Var2;
        long[] jArr;
        ax0 ax0Var3 = this;
        ax0 ax0Var4 = i;
        if (ax0Var == ax0Var4) {
            return ax0Var3;
        }
        if (ax0Var3 == ax0Var4) {
            return ax0Var;
        }
        long j = ax0Var.g;
        long j2 = ax0Var.g;
        long[] jArr2 = ax0Var.h;
        long j3 = ax0Var.f;
        long j4 = ax0Var.e;
        long j5 = ax0Var3.g;
        long j6 = ax0Var3.f;
        long j7 = ax0Var3.e;
        if (j == j5 && jArr2 == (jArr = ax0Var3.h)) {
            return new ax0(j7 | j4, j6 | j3, j5, jArr);
        }
        long[] jArr3 = ax0Var3.h;
        if (jArr3 == null) {
            if (jArr3 != null) {
                ax0Var2 = ax0Var;
                for (long j8 : jArr3) {
                    ax0Var2 = ax0Var2.e(j8);
                }
            } else {
                ax0Var2 = ax0Var;
            }
            long j9 = ax0Var3.g;
            if (j6 != 0) {
                for (int i2 = 0; i2 < 64; i2++) {
                    if (((1 << i2) & j6) != 0) {
                        ax0Var2 = ax0Var2.e(i2 + j9);
                    }
                }
            }
            if (j7 != 0) {
                for (int i3 = 0; i3 < 64; i3++) {
                    if (((1 << i3) & j7) != 0) {
                        ax0Var2 = ax0Var2.e(i3 + j9 + 64);
                    }
                }
            }
            return ax0Var2;
        }
        if (jArr2 != null) {
            for (long j10 : jArr2) {
                ax0Var3 = ax0Var3.e(j10);
            }
        }
        if (j3 != 0) {
            for (int i4 = 0; i4 < 64; i4++) {
                if (((1 << i4) & j3) != 0) {
                    ax0Var3 = ax0Var3.e(i4 + j2);
                }
            }
        }
        if (j4 != 0) {
            for (int i5 = 0; i5 < 64; i5++) {
                if (((1 << i5) & j4) != 0) {
                    ax0Var3 = ax0Var3.e(i5 + j2 + 64);
                }
            }
        }
        return ax0Var3;
    }

    public final ax0 e(long j) {
        long j2;
        long j3;
        long[] jArr;
        long[] jArr2;
        int i2;
        long j4;
        long j5 = this.g;
        long j6 = j - j5;
        long j7 = 0;
        int j8 = o20.j(j6, 0L);
        long j9 = this.f;
        if (j8 >= 0 && o20.j(j6, 64L) < 0) {
            long j10 = 1 << ((int) j6);
            if ((j9 & j10) == 0) {
                return new ax0(this.e, j9 | j10, this.g, this.h);
            }
        } else {
            int j11 = o20.j(j6, 64L);
            long j12 = this.e;
            int i3 = 64;
            if (j11 >= 0 && o20.j(j6, 128L) < 0) {
                long j13 = 1 << (((int) j6) - 64);
                if ((j12 & j13) == 0) {
                    return new ax0(j12 | j13, this.f, this.g, this.h);
                }
            } else {
                int j14 = o20.j(j6, 128L);
                long[] jArr3 = this.h;
                if (j14 >= 0) {
                    if (!c(j)) {
                        long j15 = ((j + 1) / 64) * 64;
                        if (o20.j(j15, 0L) < 0) {
                            j15 = 9223372036854775680L;
                        }
                        long j16 = j12;
                        j2 j2Var = null;
                        while (true) {
                            if (o20.j(j5, j15) < 0) {
                                if (j9 != j7) {
                                    if (j2Var == null) {
                                        j2Var = new j2(jArr3);
                                    }
                                    int i4 = 0;
                                    i2 = i3;
                                    while (i4 < i2) {
                                        if ((j9 & (1 << i4)) != j7) {
                                            j4 = j7;
                                            ((ke0) j2Var.f).a(i4 + j5);
                                        } else {
                                            j4 = j7;
                                        }
                                        i4++;
                                        j7 = j4;
                                    }
                                } else {
                                    i2 = i3;
                                }
                                long j17 = j7;
                                if (j16 == j17) {
                                    j2 = j15;
                                    j3 = j17;
                                    break;
                                }
                                j5 += 64;
                                j7 = j17;
                                j9 = j16;
                                i3 = i2;
                                j16 = j7;
                            } else {
                                j2 = j5;
                                j3 = j9;
                                break;
                            }
                        }
                        if (j2Var != null) {
                            ke0 ke0Var = (ke0) j2Var.f;
                            int i5 = ke0Var.b;
                            if (i5 == 0) {
                                jArr2 = null;
                            } else {
                                long[] jArr4 = new long[i5];
                                long[] jArr5 = ke0Var.a;
                                for (int i6 = 0; i6 < i5; i6++) {
                                    jArr4[i6] = jArr5[i6];
                                }
                                jArr2 = jArr4;
                            }
                            if (jArr2 != null) {
                                jArr = jArr2;
                                return new ax0(j16, j3, j2, jArr).e(j);
                            }
                        }
                        jArr = jArr3;
                        return new ax0(j16, j3, j2, jArr).e(j);
                    }
                } else {
                    if (jArr3 == null) {
                        return new ax0(this.e, this.f, this.g, new long[]{j});
                    }
                    int k = g30.k(jArr3, j);
                    if (k < 0) {
                        int i7 = -(k + 1);
                        int length = jArr3.length;
                        long[] jArr6 = new long[length + 1];
                        i8.M(jArr3, jArr6, 0, 0, i7);
                        i8.M(jArr3, jArr6, i7 + 1, i7, length);
                        jArr6[i7] = j;
                        return new ax0(this.e, this.f, this.g, jArr6);
                    }
                }
            }
        }
        return this;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return g30.y(new zw0(this, null));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" [");
        ArrayList arrayList = new ArrayList(ne.N(this));
        Iterator it = iterator();
        while (true) {
            mv0 mv0Var = (mv0) it;
            if (!mv0Var.hasNext()) {
                break;
            }
            arrayList.add(String.valueOf(((Number) mv0Var.next()).longValue()));
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append((CharSequence) "");
        int size = arrayList.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            Object obj = arrayList.get(i3);
            boolean z = true;
            i2++;
            if (i2 > 1) {
                sb2.append((CharSequence) ", ");
            }
            if (obj != null) {
                z = obj instanceof CharSequence;
            }
            if (z) {
                sb2.append((CharSequence) obj);
            } else if (obj instanceof Character) {
                sb2.append(((Character) obj).charValue());
            } else {
                sb2.append((CharSequence) obj.toString());
            }
        }
        sb2.append((CharSequence) "");
        sb.append(sb2.toString());
        sb.append(']');
        return sb.toString();
    }
}
