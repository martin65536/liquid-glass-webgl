package defpackage;

import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l7 implements CharSequence {
    public final List e;
    public final String f;
    public final ArrayList g;
    public final ArrayList h;

    static {
        c4 c4Var = xs0.a;
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00b2, code lost:
    
        r8.a(r1.c);
        r0 = r0 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public l7(java.util.List r8, java.lang.String r9) {
        /*
            r7 = this;
            r7.<init>()
            r7.e = r8
            r7.f = r9
            r9 = 0
            if (r8 == 0) goto L3b
            int r0 = r8.size()
            r1 = 0
            r2 = r9
            r3 = r2
        L11:
            if (r1 >= r0) goto L3d
            java.lang.Object r4 = r8.get(r1)
            k7 r4 = (defpackage.k7) r4
            java.lang.Object r5 = r4.a
            boolean r6 = r5 instanceof defpackage.ux0
            if (r6 == 0) goto L2a
            if (r2 != 0) goto L26
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
        L26:
            r2.add(r4)
            goto L38
        L2a:
            boolean r5 = r5 instanceof defpackage.ck0
            if (r5 == 0) goto L38
            if (r3 != 0) goto L35
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
        L35:
            r3.add(r4)
        L38:
            int r1 = r1 + 1
            goto L11
        L3b:
            r2 = r9
            r3 = r2
        L3d:
            r7.g = r2
            r7.h = r3
            if (r3 == 0) goto L4d
            qt r7 = new qt
            r8 = 5
            r7.<init>(r8)
            java.util.List r9 = defpackage.me.c0(r3, r7)
        L4d:
            if (r9 == 0) goto Lba
            boolean r7 = r9.isEmpty()
            if (r7 == 0) goto L56
            goto Lba
        L56:
            java.lang.Object r7 = defpackage.me.S(r9)
            k7 r7 = (defpackage.k7) r7
            int r7 = r7.c
            ge0 r8 = defpackage.s10.a
            ge0 r8 = new ge0
            r0 = 1
            r8.<init>(r0)
            r8.a(r7)
            int r7 = r9.size()
        L6d:
            if (r0 >= r7) goto Lba
            java.lang.Object r1 = r9.get(r0)
            k7 r1 = (defpackage.k7) r1
        L75:
            int r2 = r8.b
            if (r2 == 0) goto Lb2
            if (r2 == 0) goto Laa
            int[] r3 = r8.a
            int r4 = r2 + (-1)
            r3 = r3[r4]
            int r4 = r1.b
            int r5 = r1.c
            if (r4 < r3) goto L8d
            int r2 = r2 + (-1)
            r8.c(r2)
            goto L75
        L8d:
            if (r5 > r3) goto L90
            goto Lb2
        L90:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = "Paragraph overlap not allowed, end "
            r2.<init>(r4)
            r2.append(r5)
            java.lang.String r4 = " should be less than or equal to "
            r2.append(r4)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            defpackage.r00.a(r2)
            goto Lb2
        Laa:
            java.util.NoSuchElementException r7 = new java.util.NoSuchElementException
            java.lang.String r8 = "IntList is empty."
            r7.<init>(r8)
            throw r7
        Lb2:
            int r1 = r1.c
            r8.a(r1)
            int r0 = r0 + 1
            goto L6d
        Lba:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l7.<init>(java.util.List, java.lang.String):void");
    }

    @Override // java.lang.CharSequence
    public final char charAt(int i) {
        return this.f.charAt(i);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof l7)) {
            return false;
        }
        l7 l7Var = (l7) obj;
        if (o20.e(this.f, l7Var.f) && o20.e(this.e, l7Var.e)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i;
        int hashCode = this.f.hashCode() * 31;
        List list = this.e;
        if (list != null) {
            i = list.hashCode();
        } else {
            i = 0;
        }
        return hashCode + i;
    }

    @Override // java.lang.CharSequence
    public final int length() {
        return this.f.length();
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0093, code lost:
    
        if (r0.isEmpty() != false) goto L26;
     */
    @Override // java.lang.CharSequence
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.CharSequence subSequence(int r10, int r11) {
        /*
            r9 = this;
            r0 = 41
            java.lang.String r1 = "start ("
            if (r10 > r11) goto L7
            goto L21
        L7:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r1)
            r2.append(r10)
            java.lang.String r3 = ") should be less or equal to end ("
            r2.append(r3)
            r2.append(r11)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            defpackage.r00.a(r2)
        L21:
            java.lang.String r2 = r9.f
            if (r10 != 0) goto L2c
            int r3 = r2.length()
            if (r11 != r3) goto L2c
            return r9
        L2c:
            java.lang.String r2 = r2.substring(r10, r11)
            int r3 = defpackage.m7.a
            if (r10 > r11) goto L35
            goto L4f
        L35:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r1)
            r3.append(r10)
            java.lang.String r1 = ") should be less than or equal to end ("
            r3.append(r1)
            r3.append(r11)
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            defpackage.r00.a(r0)
        L4f:
            java.util.List r9 = r9.e
            if (r9 != 0) goto L54
            goto L95
        L54:
            java.util.ArrayList r0 = new java.util.ArrayList
            int r1 = r9.size()
            r0.<init>(r1)
            int r1 = r9.size()
            r3 = 0
        L62:
            if (r3 >= r1) goto L8f
            java.lang.Object r4 = r9.get(r3)
            k7 r4 = (defpackage.k7) r4
            int r5 = r4.b
            int r6 = r4.c
            boolean r5 = defpackage.m7.a(r10, r11, r5, r6)
            if (r5 == 0) goto L8c
            k7 r5 = new k7
            java.lang.Object r7 = r4.a
            int r8 = r4.b
            int r8 = java.lang.Math.max(r10, r8)
            int r8 = r8 - r10
            int r6 = java.lang.Math.min(r11, r6)
            int r6 = r6 - r10
            java.lang.String r4 = r4.d
            r5.<init>(r7, r8, r6, r4)
            r0.add(r5)
        L8c:
            int r3 = r3 + 1
            goto L62
        L8f:
            boolean r9 = r0.isEmpty()
            if (r9 == 0) goto L96
        L95:
            r0 = 0
        L96:
            l7 r9 = new l7
            r9.<init>(r0, r2)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l7.subSequence(int, int):java.lang.CharSequence");
    }

    @Override // java.lang.CharSequence
    public final String toString() {
        return this.f;
    }

    public /* synthetic */ l7(String str) {
        this(str, er.e);
    }

    public l7(String str, List list) {
        this(list.isEmpty() ? null : list, str);
    }
}
