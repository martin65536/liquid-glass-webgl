package defpackage;

import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ie0 {
    public long[] a;
    public int[] b;
    public int c;
    public int d;
    public int e;

    public ie0(int i) {
        boolean z;
        this.a = zs0.a;
        this.b = b20.a;
        if (i >= 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            d(zs0.d(i));
        } else {
            v7.m("Capacity must be a positive value.");
            throw null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0070, code lost:
    
        r21 = r12;
        r4 = '\b';
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x007c, code lost:
    
        if (((((~r8) << 6) & r8) & r21) == 0) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x007e, code lost:
    
        r3 = c(r5);
        r12 = 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0086, code lost:
    
        if (r37.e != 0) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x009a, code lost:
    
        if (((r37.a[r3 >> 3] >> ((r3 & 7) << 3)) & 255) != 254) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00a8, code lost:
    
        r3 = r37.c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00aa, code lost:
    
        if (r3 <= 8) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00ac, code lost:
    
        r17 = 128;
        r23 = r10;
        r6 = 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00c7, code lost:
    
        if (java.lang.Long.compare((r37.d * 32) ^ Long.MIN_VALUE, (r3 * 25) ^ Long.MIN_VALUE) > 0) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00c9, code lost:
    
        r3 = r37.a;
        r7 = r37.c;
        r8 = r37.b;
        r9 = (r7 + 7) >> 3;
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00d5, code lost:
    
        if (r10 >= r9) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00d7, code lost:
    
        r29 = r12;
        r12 = r3[r10] & r21;
        r3[r10] = (-72340172838076674L) & ((~r12) + (r12 >>> 7));
        r10 = r10 + 1;
        r15 = r15;
        r14 = r14;
        r12 = r29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00f3, code lost:
    
        r29 = r12;
        r27 = r14;
        r11 = r15;
        r9 = defpackage.i8.T(r3);
        r10 = r9 - 1;
        r3[r10] = (r3[r10] & 72057594037927935L) | (-72057594037927936L);
        r3[r9] = r3[0];
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0112, code lost:
    
        if (r9 == r7) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0114, code lost:
    
        r10 = r9 >> 3;
        r21 = (r9 & 7) << 3;
        r12 = (r3[r10] >> r21) & r29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0122, code lost:
    
        if (r12 != 128) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0129, code lost:
    
        if (r12 == 254) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x012c, code lost:
    
        r12 = r8[r9] * r27;
        r13 = (r12 ^ (r12 << 16)) >>> 7;
        r22 = c(r13);
        r13 = r13 & r7;
        r31 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0147, code lost:
    
        if ((((r22 - r13) & r7) / 8) != (((r9 - r13) & r7) / 8)) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x016c, code lost:
    
        r4 = r6;
        r28 = r7;
        r6 = r22 >> 3;
        r32 = r3[r6];
        r7 = (r22 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x017d, code lost:
    
        if (((r32 >> r7) & r29) != 128) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x017f, code lost:
    
        r34 = r11 ? 1 : 0;
        r3[r6] = (r32 & (~(r29 << r7))) | ((r12 & 127) << r7);
        r3[r10] = (r3[r10] & (~(r29 << r21))) | (128 << r21);
        r8[r22] = r8[r9];
        r8[r9] = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x01bb, code lost:
    
        r3[r3.length - 1] = (r3[0] & 72057594037927935L) | Long.MIN_VALUE;
        r9 = r9 + 1;
        r6 = r4;
        r7 = r28;
        r4 = r31;
        r11 = r34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x01a3, code lost:
    
        r34 = r11 ? 1 : 0;
        r3[r6] = ((r12 & 127) << r7) | (r32 & (~(r29 << r7)));
        r6 = r8[r22];
        r8[r22] = r8[r9];
        r8[r9] = r6;
        r9 = r9 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0149, code lost:
    
        r3[r10] = (r3[r10] & (~(r29 << r21))) | ((r12 & 127) << r21);
        r3[r3.length - (r11 ? 1 : 0)] = (r3[0] & 72057594037927935L) | Long.MIN_VALUE;
        r9 = r9 + 1;
        r6 = r6;
        r7 = r7;
        r4 = r31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0124, code lost:
    
        r9 = r9 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x01d0, code lost:
    
        r4 = r6;
        r34 = r11 ? 1 : 0;
        r37.e = defpackage.zs0.a(r37.c) - r37.d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x025d, code lost:
    
        r3 = c(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0261, code lost:
    
        r37.d++;
        r4 = r37.e;
        r5 = r37.a;
        r6 = r3 >> 3;
        r7 = r5[r6];
        r9 = (r3 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0279, code lost:
    
        if (((r7 >> r9) & r29) != r17) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x027b, code lost:
    
        r10 = r34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0280, code lost:
    
        r37.e = r4 - r10;
        r4 = r37.c;
        r7 = (r7 & (~(r29 << r9))) | (r23 << r9);
        r5[r6] = r7;
        r5[(((r3 - 7) & r4) + (r4 & 7)) >> 3] = r7;
        r18 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x027e, code lost:
    
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x01e3, code lost:
    
        r4 = 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x01e4, code lost:
    
        r29 = 255;
        r34 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x01f1, code lost:
    
        r3 = defpackage.zs0.b(r37.c);
        r6 = r37.a;
        r7 = r37.b;
        r8 = r37.c;
        d(r3);
        r3 = r37.a;
        r9 = r37.b;
        r10 = r37.c;
        r11 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0208, code lost:
    
        if (r11 >= r8) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0217, code lost:
    
        if (((r6[r11 >> 3] >> ((r11 & 7) << 3)) & 255) >= r17) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0219, code lost:
    
        r12 = r7[r11];
        r13 = r12 * r14;
        r13 = r13 ^ (r13 << 16);
        r14 = c(r13 >>> 7);
        r19 = r4;
        r15 = r5;
        r4 = r13 & 127;
        r13 = r14 >> 3;
        r20 = (r14 & 7) << 3;
        r25 = r3;
        r3 = (r3[r13] & (~(255 << r20))) | (r4 << r20);
        r25[r13] = r3;
        r25[(((r14 - 7) & r10) + (r10 & 7)) >> 3] = r3;
        r9[r14] = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0255, code lost:
    
        r11 = r11 + 1;
        r5 = r15;
        r4 = r19;
        r3 = r25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0250, code lost:
    
        r25 = r3;
        r19 = r4;
        r15 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01eb, code lost:
    
        r23 = r10;
        r4 = 7;
        r17 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x009c, code lost:
    
        r23 = r10;
        r29 = 255;
        r34 = true;
        r17 = 128;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v8, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean a(int r38) {
        /*
            Method dump skipped, instructions count: 691
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ie0.a(int):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0062, code lost:
    
        if (((r7 & ((~r7) << 6)) & (-9187201950435737472L)) == 0) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0064, code lost:
    
        r11 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean b(int r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r2 = r2 * r1
            int r3 = r2 << 16
            r2 = r2 ^ r3
            r3 = r2 & 127(0x7f, float:1.78E-43)
            int r4 = r0.c
            int r2 = r2 >>> 7
            r2 = r2 & r4
            r5 = 0
            r6 = r5
        L14:
            long[] r7 = r0.a
            int r8 = r2 >> 3
            r9 = r2 & 7
            int r9 = r9 << 3
            r10 = r7[r8]
            long r10 = r10 >>> r9
            r12 = 1
            int r8 = r8 + r12
            r13 = r7[r8]
            int r7 = 64 - r9
            long r7 = r13 << r7
            long r13 = (long) r9
            long r13 = -r13
            r9 = 63
            long r13 = r13 >> r9
            long r7 = r7 & r13
            long r7 = r7 | r10
            long r9 = (long) r3
            r13 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r9 = r9 * r13
            long r9 = r9 ^ r7
            long r13 = r9 - r13
            long r9 = ~r9
            long r9 = r9 & r13
            r13 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r9 = r9 & r13
        L40:
            r15 = 0
            int r11 = (r9 > r15 ? 1 : (r9 == r15 ? 0 : -1))
            if (r11 == 0) goto L5b
            int r11 = java.lang.Long.numberOfTrailingZeros(r9)
            int r11 = r11 >> 3
            int r11 = r11 + r2
            r11 = r11 & r4
            int[] r15 = r0.b
            r15 = r15[r11]
            if (r15 != r1) goto L55
            goto L65
        L55:
            r15 = 1
            long r15 = r9 - r15
            long r9 = r9 & r15
            goto L40
        L5b:
            long r9 = ~r7
            r11 = 6
            long r9 = r9 << r11
            long r7 = r7 & r9
            long r7 = r7 & r13
            int r7 = (r7 > r15 ? 1 : (r7 == r15 ? 0 : -1))
            if (r7 == 0) goto L69
            r11 = -1
        L65:
            if (r11 < 0) goto L68
            return r12
        L68:
            return r5
        L69:
            int r6 = r6 + 8
            int r2 = r2 + r6
            r2 = r2 & r4
            goto L14
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ie0.b(int):boolean");
    }

    public final int c(int i) {
        int i2 = this.c;
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

    public final void d(int i) {
        int i2;
        long[] jArr;
        if (i > 0) {
            i2 = Math.max(7, zs0.c(i));
        } else {
            i2 = 0;
        }
        this.c = i2;
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
        this.e = zs0.a(this.c) - this.d;
        this.b = new int[i2];
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0062, code lost:
    
        if (((r7 & ((~r7) << 6)) & (-9187201950435737472L)) == 0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0064, code lost:
    
        r11 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean e(int r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r2 = r2 * r1
            int r3 = r2 << 16
            r2 = r2 ^ r3
            r3 = r2 & 127(0x7f, float:1.78E-43)
            int r4 = r0.c
            int r2 = r2 >>> 7
            r2 = r2 & r4
            r5 = 0
            r6 = r5
        L14:
            long[] r7 = r0.a
            int r8 = r2 >> 3
            r9 = r2 & 7
            int r9 = r9 << 3
            r10 = r7[r8]
            long r10 = r10 >>> r9
            r12 = 1
            int r8 = r8 + r12
            r13 = r7[r8]
            int r7 = 64 - r9
            long r7 = r13 << r7
            long r13 = (long) r9
            long r13 = -r13
            r9 = 63
            long r13 = r13 >> r9
            long r7 = r7 & r13
            long r7 = r7 | r10
            long r9 = (long) r3
            r13 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r9 = r9 * r13
            long r9 = r9 ^ r7
            long r13 = r9 - r13
            long r9 = ~r9
            long r9 = r9 & r13
            r13 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r9 = r9 & r13
        L40:
            r15 = 0
            int r11 = (r9 > r15 ? 1 : (r9 == r15 ? 0 : -1))
            if (r11 == 0) goto L5b
            int r11 = java.lang.Long.numberOfTrailingZeros(r9)
            int r11 = r11 >> 3
            int r11 = r11 + r2
            r11 = r11 & r4
            int[] r15 = r0.b
            r15 = r15[r11]
            if (r15 != r1) goto L55
            goto L65
        L55:
            r15 = 1
            long r15 = r9 - r15
            long r9 = r9 & r15
            goto L40
        L5b:
            long r9 = ~r7
            r11 = 6
            long r9 = r9 << r11
            long r7 = r7 & r9
            long r7 = r7 & r13
            int r7 = (r7 > r15 ? 1 : (r7 == r15 ? 0 : -1))
            if (r7 == 0) goto L6e
            r11 = -1
        L65:
            if (r11 < 0) goto L68
            r5 = r12
        L68:
            if (r5 == 0) goto L6d
            r0.f(r11)
        L6d:
            return r5
        L6e:
            int r6 = r6 + 8
            int r2 = r2 + r6
            r2 = r2 & r4
            goto L14
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ie0.e(int):boolean");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ie0)) {
            return false;
        }
        ie0 ie0Var = (ie0) obj;
        if (ie0Var.d != this.d) {
            return false;
        }
        int[] iArr = this.b;
        long[] jArr = this.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128 && !ie0Var.b(iArr[(i << 3) + i3])) {
                            return false;
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
        }
        return true;
    }

    public final void f(int i) {
        this.d--;
        long[] jArr = this.a;
        int i2 = this.c;
        int i3 = i >> 3;
        int i4 = (i & 7) << 3;
        long j = (jArr[i3] & (~(255 << i4))) | (254 << i4);
        jArr[i3] = j;
        jArr[(((i - 7) & i2) + (i2 & 7)) >> 3] = j;
    }

    public final int hashCode() {
        int[] iArr = this.b;
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
                        i2 += iArr[(i << 3) + i4];
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
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) "[");
        int[] iArr = this.b;
        long[] jArr = this.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            int i2 = 0;
            loop0: while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i3 = 8 - ((~(i - length)) >>> 31);
                    for (int i4 = 0; i4 < i3; i4++) {
                        if ((255 & j) < 128) {
                            int i5 = iArr[(i << 3) + i4];
                            if (i2 == -1) {
                                sb.append((CharSequence) "...");
                                break loop0;
                            }
                            if (i2 != 0) {
                                sb.append((CharSequence) ", ");
                            }
                            sb.append(i5);
                            i2++;
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
        sb.append((CharSequence) "]");
        return sb.toString();
    }

    public /* synthetic */ ie0() {
        this(6);
    }
}
