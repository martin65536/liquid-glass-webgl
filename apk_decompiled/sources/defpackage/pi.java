package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class pi {
    public final we a;
    public final we b;
    public final we c;
    public final float[] d;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public pi(defpackage.we r9, defpackage.we r10, int r11) {
        /*
            r8 = this;
            long r0 = r9.b
            r2 = 12884901888(0x300000000, double:6.365987373E-314)
            boolean r0 = defpackage.k81.q(r0, r2)
            if (r0 == 0) goto L12
            we r0 = defpackage.o4.j(r9)
            goto L13
        L12:
            r0 = r9
        L13:
            long r4 = r10.b
            boolean r1 = defpackage.k81.q(r4, r2)
            if (r1 == 0) goto L20
            we r1 = defpackage.o4.j(r10)
            goto L21
        L20:
            r1 = r10
        L21:
            float[] r4 = defpackage.dl.p
            r5 = 3
            if (r11 != r5) goto L6c
            long r6 = r9.b
            boolean r11 = defpackage.k81.q(r6, r2)
            long r6 = r10.b
            boolean r2 = defpackage.k81.q(r6, r2)
            if (r11 == 0) goto L37
            if (r2 == 0) goto L37
            goto L6c
        L37:
            if (r11 != 0) goto L3b
            if (r2 == 0) goto L6c
        L3b:
            if (r11 == 0) goto L3e
            goto L3f
        L3e:
            r9 = r10
        L3f:
            wq0 r9 = (defpackage.wq0) r9
            c61 r9 = r9.d
            if (r11 == 0) goto L4a
            float[] r11 = r9.a()
            goto L4b
        L4a:
            r11 = r4
        L4b:
            if (r2 == 0) goto L51
            float[] r4 = r9.a()
        L51:
            r9 = 0
            r2 = r11[r9]
            r3 = r4[r9]
            float r2 = r2 / r3
            r3 = 1
            r6 = r11[r3]
            r7 = r4[r3]
            float r6 = r6 / r7
            r7 = 2
            r11 = r11[r7]
            r4 = r4[r7]
            float r11 = r11 / r4
            float[] r4 = new float[r5]
            r4[r9] = r2
            r4[r3] = r6
            r4[r7] = r11
            goto L6d
        L6c:
            r4 = 0
        L6d:
            r8.<init>(r10, r0, r1, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.pi.<init>(we, we, int):void");
    }

    public long a(long j) {
        float h = se.h(j);
        float g = se.g(j);
        float e = se.e(j);
        float d = se.d(j);
        we weVar = this.b;
        long d2 = weVar.d(h, g, e);
        float intBitsToFloat = Float.intBitsToFloat((int) (d2 >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (d2 & 4294967295L));
        float e2 = weVar.e(h, g, e);
        float[] fArr = this.d;
        if (fArr != null) {
            intBitsToFloat *= fArr[0];
            intBitsToFloat2 *= fArr[1];
            e2 *= fArr[2];
        }
        float f = intBitsToFloat;
        float f2 = intBitsToFloat2;
        return this.c.f(f, f2, e2, d, this.a);
    }

    public pi(we weVar, we weVar2, we weVar3, float[] fArr) {
        this.a = weVar;
        this.b = weVar2;
        this.c = weVar3;
        this.d = fArr;
    }
}
