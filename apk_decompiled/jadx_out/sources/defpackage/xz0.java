package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xz0 implements mm, ij {
    public final /* synthetic */ yz0 e;
    public final pc f;
    public pc g;
    public qm0 h = qm0.f;
    public final cr i = cr.e;
    public final /* synthetic */ yz0 j;

    public xz0(yz0 yz0Var, pc pcVar) {
        this.j = yz0Var;
        this.e = yz0Var;
        this.f = pcVar;
    }

    public final Object A(qm0 qm0Var, s9 s9Var) {
        pc pcVar = new pc(1, t20.w(s9Var));
        pcVar.s();
        this.h = qm0Var;
        this.g = pcVar;
        return pcVar.p();
    }

    @Override // defpackage.mm
    public final float B() {
        return this.e.B();
    }

    public final long C() {
        yz0 yz0Var = this.j;
        yz0Var.getClass();
        long h = d3.h(yz0Var, k81.E(yz0Var).C.e());
        long j = yz0Var.B;
        float max = Math.max(0.0f, Float.intBitsToFloat((int) (h >> 32)) - ((int) (j >> 32))) / 2.0f;
        float max2 = Math.max(0.0f, Float.intBitsToFloat((int) (h & 4294967295L)) - ((int) (j & 4294967295L))) / 2.0f;
        return (Float.floatToRawIntBits(max) << 32) | (Float.floatToRawIntBits(max2) & 4294967295L);
    }

    public final l51 E() {
        yz0 yz0Var = this.j;
        yz0Var.getClass();
        return k81.E(yz0Var).C;
    }

    @Override // defpackage.mm
    public final float G(float f) {
        return this.e.B() * f;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object H(long r8, defpackage.kv r10, defpackage.jj r11) {
        /*
            r7 = this;
            boolean r0 = r11 instanceof defpackage.vz0
            if (r0 == 0) goto L13
            r0 = r11
            vz0 r0 = (defpackage.vz0) r0
            int r1 = r0.k
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.k = r1
            goto L18
        L13:
            vz0 r0 = new vz0
            r0.<init>(r7, r11)
        L18:
            java.lang.Object r11 = r0.i
            int r1 = r0.k
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L30
            if (r1 != r3) goto L2a
            dy0 r7 = r0.h
            defpackage.o30.x(r11)     // Catch: java.lang.Throwable -> L28
            goto L68
        L28:
            r8 = move-exception
            goto L72
        L2a:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r7)
            return r2
        L30:
            defpackage.o30.x(r11)
            r4 = 0
            int r11 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r11 > 0) goto L4a
            pc r11 = r7.g
            if (r11 == 0) goto L4a
            rm0 r1 = new rm0
            r1.<init>(r8)
            jq0 r4 = new jq0
            r4.<init>(r1)
            r11.u(r4)
        L4a:
            yz0 r11 = r7.j
            hk r11 = r11.p0()
            o90 r1 = new o90
            r1.<init>(r8, r7, r2)
            r8 = 3
            dy0 r8 = defpackage.f31.G(r11, r2, r1, r8)
            r0.h = r8     // Catch: java.lang.Throwable -> L6e
            r0.k = r3     // Catch: java.lang.Throwable -> L6e
            java.lang.Object r11 = r10.d(r7, r0)     // Catch: java.lang.Throwable -> L6e
            ik r7 = defpackage.ik.e
            if (r11 != r7) goto L67
            return r7
        L67:
            r7 = r8
        L68:
            mc r8 = defpackage.mc.f
            r7.a(r8)
            return r11
        L6e:
            r7 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
        L72:
            mc r9 = defpackage.mc.f
            r7.a(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.xz0.H(long, kv, jj):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object I(long r5, defpackage.kv r7, defpackage.jj r8) {
        /*
            r4 = this;
            boolean r0 = r8 instanceof defpackage.wz0
            if (r0 == 0) goto L13
            r0 = r8
            wz0 r0 = (defpackage.wz0) r0
            int r1 = r0.j
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.j = r1
            goto L18
        L13:
            wz0 r0 = new wz0
            r0.<init>(r4, r8)
        L18:
            java.lang.Object r8 = r0.h
            int r1 = r0.j
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L2c
            if (r1 != r3) goto L26
            defpackage.o30.x(r8)     // Catch: defpackage.rm0 -> L3b
            return r8
        L26:
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r4)
            return r2
        L2c:
            defpackage.o30.x(r8)
            r0.j = r3     // Catch: defpackage.rm0 -> L3b
            java.lang.Object r4 = r4.H(r5, r7, r0)     // Catch: defpackage.rm0 -> L3b
            ik r5 = defpackage.ik.e
            if (r4 != r5) goto L3a
            return r5
        L3a:
            return r4
        L3b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.xz0.I(long, kv, jj):java.lang.Object");
    }

    @Override // defpackage.mm
    public final float O(long j) {
        yz0 yz0Var = this.e;
        yz0Var.getClass();
        return d3.e(yz0Var, j);
    }

    @Override // defpackage.mm
    public final int S(float f) {
        yz0 yz0Var = this.e;
        yz0Var.getClass();
        return d3.c(f, yz0Var);
    }

    @Override // defpackage.mm
    public final long Z(long j) {
        yz0 yz0Var = this.e;
        yz0Var.getClass();
        return d3.h(yz0Var, j);
    }

    @Override // defpackage.mm
    public final float d0(long j) {
        yz0 yz0Var = this.e;
        yz0Var.getClass();
        return d3.g(yz0Var, j);
    }

    @Override // defpackage.mm
    public final long j0(float f) {
        return this.e.j0(f);
    }

    @Override // defpackage.mm
    public final float o0(float f) {
        return f / this.e.B();
    }

    @Override // defpackage.ij
    public final yj r() {
        return this.i;
    }

    @Override // defpackage.ij
    public final void u(Object obj) {
        yz0 yz0Var = this.j;
        synchronized (yz0Var.y) {
            yz0Var.x.j(this);
        }
        this.f.u(obj);
    }

    @Override // defpackage.mm
    public final float y() {
        return this.e.y();
    }
}
