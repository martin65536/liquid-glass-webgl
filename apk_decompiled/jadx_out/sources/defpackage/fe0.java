package defpackage;

import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fe0 {
    public long[] a;
    public int[] b;
    public int[] c;
    public int d;
    public int e;
    public int f;

    public fe0(int i) {
        this.a = zs0.a;
        int[] iArr = b20.a;
        this.b = iArr;
        this.c = iArr;
        if (i >= 0) {
            e(zs0.d(i));
        } else {
            v7.m("Capacity must be a positive value.");
            throw null;
        }
    }

    public final void a() {
        this.e = 0;
        long[] jArr = this.a;
        if (jArr != zs0.a) {
            i8.S(jArr, -9187201950435737472L);
            long[] jArr2 = this.a;
            int i = this.d;
            int i2 = i >> 3;
            long j = 255 << ((i & 7) << 3);
            jArr2[i2] = (jArr2[i2] & (~j)) | j;
        }
        this.f = zs0.a(this.d) - this.e;
    }

    public final int b(int i) {
        int i2 = this.d;
        int i3 = i & i2;
        int i4 = 0;
        while (true) {
            long[] jArr = this.a;
            int i5 = i3 >> 3;
            int i6 = (i3 & 7) << 3;
            long j = ((jArr[i5 + 1] << (64 - i6)) & ((-i6) >> 63)) | (jArr[i5] >>> i6);
            long j2 = j & ((~j) << 7) & (-9187201950435737472L);
            if (j2 != 0) {
                return (i3 + (Long.numberOfTrailingZeros(j2) >> 3)) & i2;
            }
            i4 += 8;
            i3 = (i3 + i4) & i2;
        }
    }

    public final int c(int i) {
        int i2 = (-862048943) * i;
        int i3 = i2 ^ (i2 << 16);
        int i4 = i3 & 127;
        int i5 = this.d;
        int i6 = (i3 >>> 7) & i5;
        int i7 = 0;
        while (true) {
            long[] jArr = this.a;
            int i8 = i6 >> 3;
            int i9 = (i6 & 7) << 3;
            long j = ((jArr[i8 + 1] << (64 - i9)) & ((-i9) >> 63)) | (jArr[i8] >>> i9);
            long j2 = (i4 * 72340172838076673L) ^ j;
            for (long j3 = (~j2) & (j2 - 72340172838076673L) & (-9187201950435737472L); j3 != 0; j3 &= j3 - 1) {
                int numberOfTrailingZeros = ((Long.numberOfTrailingZeros(j3) >> 3) + i6) & i5;
                if (this.b[numberOfTrailingZeros] == i) {
                    return numberOfTrailingZeros;
                }
            }
            if ((j & ((~j) << 6) & (-9187201950435737472L)) != 0) {
                return -1;
            }
            i7 += 8;
            i6 = (i6 + i7) & i5;
        }
    }

    public final int d(int i) {
        int c = c(i);
        if (c >= 0) {
            return this.c[c];
        }
        return -1;
    }

    public final void e(int i) {
        int i2;
        long[] jArr;
        if (i > 0) {
            i2 = Math.max(7, zs0.c(i));
        } else {
            i2 = 0;
        }
        this.d = i2;
        if (i2 == 0) {
            jArr = zs0.a;
        } else {
            int i3 = ((i2 + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i3];
            Arrays.fill(jArr2, 0, i3, -9187201950435737472L);
            jArr = jArr2;
        }
        this.a = jArr;
        int i4 = i2 >> 3;
        long j = 255 << ((i2 & 7) << 3);
        jArr[i4] = (jArr[i4] & (~j)) | j;
        this.f = zs0.a(this.d) - this.e;
        this.b = new int[i2];
        this.c = new int[i2];
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof fe0)) {
            return false;
        }
        fe0 fe0Var = (fe0) obj;
        if (fe0Var.e != this.e) {
            return false;
        }
        int[] iArr = this.b;
        int[] iArr2 = this.c;
        long[] jArr = this.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            loop0: while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            int i4 = (i << 3) + i3;
                            int i5 = iArr[i4];
                            int i6 = iArr2[i4];
                            int c = fe0Var.c(i5);
                            if (c < 0 || i6 != fe0Var.c[c]) {
                                break loop0;
                            }
                        }
                        j >>= 8;
                    }
                    if (i2 != 8) {
                        break;
                    }
                }
                if (i == length) {
                    break;
                }
                i++;
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x006d, code lost:
    
        r20 = r11;
        r3 = '\b';
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0079, code lost:
    
        if (((((~r7) << 6) & r7) & r20) == 0) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x007b, code lost:
    
        r2 = b(r4);
        r11 = 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0083, code lost:
    
        if (r36.f != 0) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0097, code lost:
    
        if (((r36.a[r2 >> 3] >> ((r2 & 7) << 3)) & 255) != 254) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00a5, code lost:
    
        r2 = r36.d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00a7, code lost:
    
        if (r2 <= 8) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00a9, code lost:
    
        r16 = 128;
        r22 = r9;
        r5 = 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00c4, code lost:
    
        if (java.lang.Long.compare((r36.e * 32) ^ Long.MIN_VALUE, (r2 * 25) ^ Long.MIN_VALUE) > 0) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00c6, code lost:
    
        r2 = r36.a;
        r6 = r36.d;
        r7 = r36.b;
        r8 = r36.c;
        r9 = (r6 + 7) >> 3;
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00d3, code lost:
    
        if (r10 >= r9) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00d5, code lost:
    
        r28 = r11;
        r11 = r2[r10] & r20;
        r2[r10] = (-72340172838076674L) & ((~r11) + (r11 >>> 7));
        r10 = r10 + 1;
        r14 = r14;
        r13 = r13;
        r11 = r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00f3, code lost:
    
        r28 = r11;
        r27 = r13;
        r26 = r14;
        r9 = defpackage.i8.T(r2);
        r10 = r9 - 1;
        r13 = 72057594037927935L;
        r2[r10] = (r2[r10] & 72057594037927935L) | (-72057594037927936L);
        r2[r9] = r2[0];
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0112, code lost:
    
        if (r9 == r6) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0114, code lost:
    
        r10 = r9 >> 3;
        r20 = (r9 & 7) << 3;
        r11 = (r2[r10] >> r20) & r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0122, code lost:
    
        if (r11 != 128) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0129, code lost:
    
        if (r11 == 254) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x012c, code lost:
    
        r11 = r7[r9] * r27;
        r12 = (r11 ^ (r11 << 16)) >>> 7;
        r21 = b(r12);
        r12 = r12 & r6;
        r31 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0147, code lost:
    
        if ((((r21 - r12) & r6) / 8) != (((r9 - r12) & r6) / 8)) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x016d, code lost:
    
        r3 = r5;
        r30 = r6;
        r5 = r21 >> 3;
        r32 = r2[r5];
        r6 = (r21 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x017e, code lost:
    
        if (((r32 >> r6) & r28) != 128) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0180, code lost:
    
        r34 = r13;
        r2[r5] = ((r11 & 127) << r6) | (r32 & (~(r28 << r6)));
        r2[r10] = (r2[r10] & (~(r28 << r20))) | (128 << r20);
        r7[r21] = r7[r9];
        r7[r9] = 0;
        r8[r21] = r8[r9];
        r8[r9] = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x01c6, code lost:
    
        r2[r2.length - 1] = (r2[0] & r34) | Long.MIN_VALUE;
        r9 = r9 + 1;
        r5 = r3;
        r6 = r30;
        r3 = r31;
        r13 = r34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x01a6, code lost:
    
        r34 = r13;
        r2[r5] = ((r11 & 127) << r6) | (r32 & (~(r28 << r6)));
        r5 = r7[r21];
        r7[r21] = r7[r9];
        r7[r9] = r5;
        r5 = r8[r21];
        r8[r21] = r8[r9];
        r8[r9] = r5;
        r9 = r9 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0149, code lost:
    
        r2[r10] = (r2[r10] & (~(r28 << r20))) | ((r11 & 127) << r20);
        r2[r2.length - 1] = (r2[0] & r13) | Long.MIN_VALUE;
        r9 = r9 + 1;
        r5 = r5;
        r6 = r6;
        r3 = r31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0124, code lost:
    
        r9 = r9 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x01dc, code lost:
    
        r3 = r5;
        r36.f = defpackage.zs0.a(r36.d) - r36.e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x026f, code lost:
    
        r2 = b(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0273, code lost:
    
        r36.e++;
        r1 = r36.f;
        r3 = r36.a;
        r4 = r2 >> 3;
        r5 = r3[r4];
        r7 = (r2 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x028b, code lost:
    
        if (((r5 >> r7) & r28) != r16) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x028d, code lost:
    
        r15 = r26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x028f, code lost:
    
        r36.f = r1 - r15;
        r1 = r36.d;
        r5 = (r5 & (~(r28 << r7))) | (r22 << r7);
        r3[r4] = r5;
        r3[(((r2 - 7) & r1) + (r1 & 7)) >> 3] = r5;
        r1 = ~r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x01ec, code lost:
    
        r3 = 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x01ed, code lost:
    
        r28 = 255;
        r26 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x01fa, code lost:
    
        r2 = defpackage.zs0.b(r36.d);
        r5 = r36.a;
        r6 = r36.b;
        r7 = r36.c;
        r8 = r36.d;
        e(r2);
        r2 = r36.a;
        r9 = r36.b;
        r10 = r36.c;
        r11 = r36.d;
        r12 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0214, code lost:
    
        if (r12 >= r8) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0224, code lost:
    
        if (((r5[r12 >> 3] >> ((r12 & 7) << 3)) & 255) >= r16) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0226, code lost:
    
        r13 = r6[r12];
        r14 = r13 * r13;
        r14 = r14 ^ (r14 << 16);
        r18 = r3;
        r3 = b(r14 >>> 7);
        r19 = r2;
        r1 = r14 & 127;
        r14 = r3 >> 3;
        r20 = (r3 & 7) << 3;
        r1 = (r19[r14] & (~(255 << r20))) | (r1 << r20);
        r19[r14] = r1;
        r19[(((r3 - 7) & r11) + (r11 & 7)) >> 3] = r1;
        r9[r3] = r13;
        r10[r3] = r7[r12];
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0266, code lost:
    
        r12 = r12 + 1;
        r3 = r18;
        r2 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0262, code lost:
    
        r19 = r2;
        r18 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01f4, code lost:
    
        r22 = r9;
        r3 = 7;
        r16 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0099, code lost:
    
        r22 = r9;
        r28 = 255;
        r26 = 1;
        r16 = 128;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void f(int r37, int r38) {
        /*
            Method dump skipped, instructions count: 708
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fe0.f(int, int):void");
    }

    public final int hashCode() {
        int[] iArr = this.b;
        int[] iArr2 = this.c;
        long[] jArr = this.a;
        int length = jArr.length - 2;
        if (length < 0) {
            return 0;
        }
        int i = 0;
        int i2 = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i3 = 8 - ((~(i - length)) >>> 31);
                for (int i4 = 0; i4 < i3; i4++) {
                    if ((255 & j) < 128) {
                        int i5 = (i << 3) + i4;
                        i2 += iArr2[i5] ^ iArr[i5];
                    }
                    j >>= 8;
                }
                if (i3 != 8) {
                    return i2;
                }
            }
            if (i != length) {
                i++;
            } else {
                return i2;
            }
        }
    }

    public final String toString() {
        if (this.e == 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        int[] iArr = this.b;
        int[] iArr2 = this.c;
        long[] jArr = this.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            int i2 = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i3 = 8 - ((~(i - length)) >>> 31);
                    for (int i4 = 0; i4 < i3; i4++) {
                        if ((255 & j) < 128) {
                            int i5 = (i << 3) + i4;
                            int i6 = iArr[i5];
                            int i7 = iArr2[i5];
                            sb.append(i6);
                            sb.append("=");
                            sb.append(i7);
                            i2++;
                            if (i2 < this.e) {
                                sb.append(", ");
                            }
                        }
                        j >>= 8;
                    }
                    if (i3 != 8) {
                        break;
                    }
                }
                if (i == length) {
                    break;
                }
                i++;
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public /* synthetic */ fe0() {
        this(6);
    }
}
