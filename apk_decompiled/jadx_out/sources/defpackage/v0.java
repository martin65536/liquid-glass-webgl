package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class v0 extends s0 {
    public static v0 c;

    @Override // defpackage.s0
    public final int[] a(int i) {
        int length = c().length();
        if (length > 0 && i < length) {
            if (i < 0) {
                i = 0;
            }
            while (i < length && c().charAt(i) == '\n' && (c().charAt(i) == '\n' || (i != 0 && c().charAt(i - 1) != '\n'))) {
                i++;
            }
            if (i >= length) {
                return null;
            }
            int i2 = i + 1;
            while (i2 < length && !e(i2)) {
                i2++;
            }
            return b(i, i2);
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x002c, code lost:
    
        return null;
     */
    @Override // defpackage.s0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int[] d(int r5) {
        /*
            r4 = this;
            java.lang.String r0 = r4.c()
            int r0 = r0.length()
            if (r0 > 0) goto Lb
            goto L2c
        Lb:
            if (r5 > 0) goto Le
            goto L2c
        Le:
            if (r5 <= r0) goto L11
            r5 = r0
        L11:
            r0 = 10
            if (r5 <= 0) goto L2a
            java.lang.String r1 = r4.c()
            int r2 = r5 + (-1)
            char r1 = r1.charAt(r2)
            if (r1 != r0) goto L2a
            boolean r1 = r4.e(r5)
            if (r1 != 0) goto L2a
            int r5 = r5 + (-1)
            goto L11
        L2a:
            if (r5 > 0) goto L2e
        L2c:
            r4 = 0
            return r4
        L2e:
            int r1 = r5 + (-1)
        L30:
            if (r1 <= 0) goto L4e
            java.lang.String r2 = r4.c()
            char r2 = r2.charAt(r1)
            if (r2 == r0) goto L4b
            if (r1 == 0) goto L4e
            java.lang.String r2 = r4.c()
            int r3 = r1 + (-1)
            char r2 = r2.charAt(r3)
            if (r2 != r0) goto L4b
            goto L4e
        L4b:
            int r1 = r1 + (-1)
            goto L30
        L4e:
            int[] r4 = r4.b(r1, r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.v0.d(int):int[]");
    }

    public final boolean e(int i) {
        if (i > 0 && c().charAt(i - 1) != '\n') {
            if (i == c().length() || c().charAt(i) == '\n') {
                return true;
            }
            return false;
        }
        return false;
    }
}
