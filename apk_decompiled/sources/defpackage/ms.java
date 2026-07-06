package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ms implements ls {
    public final float a;
    public final zx0 b;

    /* JADX WARN: Type inference failed for: r6v1, types: [zx0, java.lang.Object] */
    public ms(float f, float f2, float f3) {
        this.a = f3;
        ?? obj = new Object();
        obj.a = 1.0f;
        obj.b = Math.sqrt(50.0d);
        obj.c = 1.0f;
        if (f < 0.0f) {
            en0.a("Damping ratio must be non-negative");
        }
        obj.c = f;
        double d = obj.b;
        if (((float) (d * d)) <= 0.0f) {
            en0.a("Spring stiffness constant must be positive.");
        }
        obj.b = Math.sqrt(f2);
        this.b = obj;
    }

    @Override // defpackage.ls
    public final float a(long j, float f, float f2, float f3) {
        zx0 zx0Var = this.b;
        zx0Var.a = f2;
        return Float.intBitsToFloat((int) (zx0Var.a(f, f3, j / 1000000) >> 32));
    }

    @Override // defpackage.ls
    public final float b(long j, float f, float f2, float f3) {
        zx0 zx0Var = this.b;
        zx0Var.a = f2;
        return Float.intBitsToFloat((int) (zx0Var.a(f, f3, j / 1000000) & 4294967295L));
    }

    @Override // defpackage.c7
    public final s41 c(c4 c4Var) {
        return new e3((ls) this);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0132  */
    @Override // defpackage.ls
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final long d(float r34, float r35, float r36) {
        /*
            Method dump skipped, instructions count: 581
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ms.d(float, float, float):long");
    }

    @Override // defpackage.ls
    public final float e(float f, float f2, float f3) {
        return 0.0f;
    }
}
