package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ns implements ls {
    public final int a;
    public final eq b;
    public final long c;
    public final long d = 0;

    public ns(int i, eq eqVar) {
        this.a = i;
        this.b = eqVar;
        this.c = i * 1000000;
    }

    @Override // defpackage.ls
    public final float a(long j, float f, float f2, float f3) {
        float f4;
        long j2 = j - this.d;
        if (j2 < 0) {
            j2 = 0;
        }
        long j3 = this.c;
        if (j2 > j3) {
            j2 = j3;
        }
        if (this.a == 0) {
            f4 = 1.0f;
        } else {
            f4 = ((float) j2) / ((float) j3);
        }
        float b = this.b.b(f4);
        return (f2 * b) + ((1.0f - b) * f);
    }

    @Override // defpackage.ls
    public final float b(long j, float f, float f2, float f3) {
        long j2;
        long j3 = j - this.d;
        if (j3 < 0) {
            j3 = 0;
        }
        long j4 = this.c;
        if (j3 > j4) {
            j2 = j4;
        } else {
            j2 = j3;
        }
        if (j2 == 0) {
            return f3;
        }
        return (a(j2, f, f2, f3) - a(j2 - 1000000, f, f2, f3)) * 1000.0f;
    }

    @Override // defpackage.c7
    public final s41 c(c4 c4Var) {
        return new e3((ls) this);
    }

    @Override // defpackage.ls
    public final long d(float f, float f2, float f3) {
        return this.d + this.c;
    }

    @Override // defpackage.ls
    public final float e(float f, float f2, float f3) {
        return b(d(f, f2, f3), f, f2, f3);
    }
}
