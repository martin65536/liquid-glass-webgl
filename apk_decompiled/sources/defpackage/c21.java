package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class c21 {
    public final he0 a;
    public b21 b;
    public long c;
    public long d;
    public long e;
    public long f;
    public float[] g;

    public c21() {
        he0 he0Var = u10.a;
        this.a = new he0();
        this.c = -1L;
        this.d = 0L;
        this.e = 0L;
    }

    public final void a(b21 b21Var, long j, long j2, float[] fArr, long j3) {
        boolean z;
        long j4 = b21Var.g;
        if (j3 - j4 <= 0 && j4 != Long.MIN_VALUE) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            b21Var.g = j3;
            b21Var.a(b21Var.e, b21Var.f, j, j2, fArr);
        }
    }

    public final boolean b(long j, long j2, float[] fArr, int i, int i2) {
        boolean z;
        if (!v10.a(j2, this.d)) {
            this.d = j2;
            z = true;
        } else {
            z = false;
        }
        if (!v10.a(j, this.e)) {
            this.e = j;
            z = true;
        }
        if (fArr != null) {
            this.g = fArr;
            z = true;
        }
        long j3 = (i << 32) | (i2 & 4294967295L);
        if (j3 != this.f) {
            this.f = j3;
            return true;
        }
        return z;
    }
}
