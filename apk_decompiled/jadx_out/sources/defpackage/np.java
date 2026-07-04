package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class np implements sr0, mm {
    public float i;
    public gh j;
    public final /* synthetic */ op l;
    public float e = 1.0f;
    public float f = 1.0f;
    public long g = 9205357640488583168L;
    public m40 h = m40.e;
    public final wb0 k = new wb0(1);

    public np(op opVar) {
        this.l = opVar;
    }

    @Override // defpackage.mm
    public final float B() {
        return this.e;
    }

    @Override // defpackage.mm
    public final float G(float f) {
        return B() * f;
    }

    @Override // defpackage.mm
    public final /* bridge */ float O(long j) {
        return d3.e(this, j);
    }

    @Override // defpackage.mm
    public final /* bridge */ int S(float f) {
        return d3.c(f, this);
    }

    @Override // defpackage.mm
    public final /* bridge */ long Z(long j) {
        return d3.h(this, j);
    }

    @Override // defpackage.mm
    public final /* bridge */ float d0(long j) {
        return d3.g(this, j);
    }

    @Override // defpackage.mm
    public final long j0(float f) {
        return d3.i(o0(f), this);
    }

    @Override // defpackage.mm
    public final float o0(float f) {
        return f / B();
    }

    @Override // defpackage.sr0
    public final h6 r(String str, String str2) {
        return this.k.r(str, str2);
    }

    @Override // defpackage.mm
    public final float y() {
        return this.f;
    }
}
