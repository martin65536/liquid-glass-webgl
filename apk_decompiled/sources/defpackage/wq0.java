package defpackage;

import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wq0 extends we {
    public static final v7 r = new v7(21);
    public final c61 d;
    public final float e;
    public final float f;
    public final q21 g;
    public final float[] h;
    public final float[] i;
    public final float[] j;
    public final bo k;
    public final vq0 l;
    public final sq0 m;
    public final bo n;
    public final vq0 o;
    public final sq0 p;
    public final boolean q;

    /* JADX WARN: Code restructure failed: missing block: B:29:0x01e0, code lost:
    
        if ((((r25 - r12) * r3) - ((r1 - r15) * r10)) >= 0.0f) goto L40;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r44v1 */
    /* JADX WARN: Type inference failed for: r44v2 */
    /* JADX WARN: Type inference failed for: r44v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public wq0(java.lang.String r36, float[] r37, defpackage.c61 r38, float[] r39, defpackage.bo r40, defpackage.bo r41, float r42, float r43, defpackage.q21 r44, int r45) {
        /*
            Method dump skipped, instructions count: 655
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.wq0.<init>(java.lang.String, float[], c61, float[], bo, bo, float, float, q21, int):void");
    }

    @Override // defpackage.we
    public final float a(int i) {
        return this.f;
    }

    @Override // defpackage.we
    public final float b(int i) {
        return this.e;
    }

    @Override // defpackage.we
    public final boolean c() {
        return this.q;
    }

    @Override // defpackage.we
    public final long d(float f, float f2, float f3) {
        double d = f;
        sq0 sq0Var = this.p;
        float c = (float) sq0Var.c(d);
        float c2 = (float) sq0Var.c(f2);
        float c3 = (float) sq0Var.c(f3);
        float[] fArr = this.i;
        if (fArr.length < 9) {
            return 0L;
        }
        float f4 = (fArr[6] * c3) + (fArr[3] * c2) + (fArr[0] * c);
        float f5 = (fArr[7] * c3) + (fArr[4] * c2) + (fArr[1] * c);
        return (Float.floatToRawIntBits(f4) << 32) | (4294967295L & Float.floatToRawIntBits(f5));
    }

    @Override // defpackage.we
    public final float e(float f, float f2, float f3) {
        double d = f;
        sq0 sq0Var = this.p;
        float c = (float) sq0Var.c(d);
        float c2 = (float) sq0Var.c(f2);
        float c3 = (float) sq0Var.c(f3);
        float[] fArr = this.i;
        return (fArr[8] * c3) + (fArr[5] * c2) + (fArr[2] * c);
    }

    @Override // defpackage.we
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || wq0.class != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        wq0 wq0Var = (wq0) obj;
        if (Float.compare(wq0Var.e, this.e) != 0 || Float.compare(wq0Var.f, this.f) != 0 || !o20.e(this.d, wq0Var.d) || !Arrays.equals(this.h, wq0Var.h)) {
            return false;
        }
        q21 q21Var = wq0Var.g;
        q21 q21Var2 = this.g;
        if (q21Var2 != null) {
            return o20.e(q21Var2, q21Var);
        }
        if (q21Var == null) {
            return true;
        }
        if (!o20.e(this.k, wq0Var.k)) {
            return false;
        }
        return o20.e(this.n, wq0Var.n);
    }

    @Override // defpackage.we
    public final long f(float f, float f2, float f3, float f4, we weVar) {
        float[] fArr = this.j;
        float f5 = (fArr[6] * f3) + (fArr[3] * f2) + (fArr[0] * f);
        float f6 = (fArr[7] * f3) + (fArr[4] * f2) + (fArr[1] * f);
        float f7 = (fArr[8] * f3) + (fArr[5] * f2) + (fArr[2] * f);
        sq0 sq0Var = this.m;
        return f31.d((float) sq0Var.c(f5), (float) sq0Var.c(f6), (float) sq0Var.c(f7), f4, weVar);
    }

    @Override // defpackage.we
    public final int hashCode() {
        int floatToIntBits;
        int floatToIntBits2;
        int hashCode = (Arrays.hashCode(this.h) + ((this.d.hashCode() + (super.hashCode() * 31)) * 31)) * 31;
        float f = this.e;
        int i = 0;
        if (f == 0.0f) {
            floatToIntBits = 0;
        } else {
            floatToIntBits = Float.floatToIntBits(f);
        }
        int i2 = (hashCode + floatToIntBits) * 31;
        float f2 = this.f;
        if (f2 == 0.0f) {
            floatToIntBits2 = 0;
        } else {
            floatToIntBits2 = Float.floatToIntBits(f2);
        }
        int i3 = (i2 + floatToIntBits2) * 31;
        q21 q21Var = this.g;
        if (q21Var != null) {
            i = q21Var.hashCode();
        }
        int i4 = i3 + i;
        if (q21Var == null) {
            return this.n.hashCode() + ((this.k.hashCode() + (i4 * 31)) * 31);
        }
        return i4;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public wq0(java.lang.String r19, float[] r20, defpackage.c61 r21, final defpackage.q21 r22, int r23) {
        /*
            r18 = this;
            r9 = r22
            double r0 = r9.a
            r2 = -4609434218613702656(0xc008000000000000, double:-3.0)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r5 = 0
            r6 = 1
            if (r4 != 0) goto Le
            r4 = r6
            goto Lf
        Le:
            r4 = r5
        Lf:
            double r7 = r9.g
            double r10 = r9.f
            r12 = -4611686018427387904(0xc000000000000000, double:-2.0)
            r14 = 0
            if (r4 == 0) goto L22
            uq0 r4 = new uq0
            r16 = r2
            r2 = 4
            r4.<init>()
            goto L44
        L22:
            r16 = r2
            int r2 = (r0 > r12 ? 1 : (r0 == r12 ? 0 : -1))
            if (r2 != 0) goto L2f
            uq0 r4 = new uq0
            r2 = 5
            r4.<init>()
            goto L44
        L2f:
            int r2 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1))
            if (r2 != 0) goto L3e
            int r2 = (r7 > r14 ? 1 : (r7 == r14 ? 0 : -1))
            if (r2 != 0) goto L3e
            uq0 r4 = new uq0
            r2 = 6
            r4.<init>()
            goto L44
        L3e:
            uq0 r4 = new uq0
            r2 = 7
            r4.<init>()
        L44:
            int r2 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r2 != 0) goto L4f
            uq0 r0 = new uq0
            r0.<init>()
        L4d:
            r6 = r0
            goto L6f
        L4f:
            int r0 = (r0 > r12 ? 1 : (r0 == r12 ? 0 : -1))
            if (r0 != 0) goto L59
            uq0 r0 = new uq0
            r0.<init>()
            goto L4d
        L59:
            int r0 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1))
            if (r0 != 0) goto L68
            int r0 = (r7 > r14 ? 1 : (r7 == r14 ? 0 : -1))
            if (r0 != 0) goto L68
            uq0 r0 = new uq0
            r1 = 2
            r0.<init>()
            goto L4d
        L68:
            uq0 r0 = new uq0
            r1 = 3
            r0.<init>()
            goto L4d
        L6f:
            r7 = 0
            r8 = 1065353216(0x3f800000, float:1.0)
            r5 = r4
            r4 = 0
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r21
            r10 = r23
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.wq0.<init>(java.lang.String, float[], c61, q21, int):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public wq0(java.lang.String r18, float[] r19, defpackage.c61 r20, final double r21, float r23, float r24, int r25) {
        /*
            r17 = this;
            r1 = r21
            r3 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            int r0 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            v7 r3 = defpackage.wq0.r
            if (r0 != 0) goto Lc
            r11 = r3
            goto L13
        Lc:
            tq0 r4 = new tq0
            r5 = 0
            r4.<init>()
            r11 = r4
        L13:
            if (r0 != 0) goto L17
        L15:
            r12 = r3
            goto L1e
        L17:
            tq0 r3 = new tq0
            r0 = 1
            r3.<init>()
            goto L15
        L1e:
            q21 r15 = new q21
            r7 = 0
            r9 = 0
            r3 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r5 = 0
            r0 = r15
            r0.<init>(r1, r3, r5, r7, r9)
            r10 = 0
            r6 = r17
            r7 = r18
            r8 = r19
            r9 = r20
            r13 = r23
            r14 = r24
            r16 = r25
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.wq0.<init>(java.lang.String, float[], c61, double, float, float, int):void");
    }
}
