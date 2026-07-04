package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pm implements mm {
    public final float e;
    public final float f;

    public pm(float f, float f2) {
        this.e = f;
        this.f = f2;
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
    public final /* synthetic */ float O(long j) {
        return d3.e(this, j);
    }

    @Override // defpackage.mm
    public final /* synthetic */ int S(float f) {
        return d3.c(f, this);
    }

    @Override // defpackage.mm
    public final /* synthetic */ long Z(long j) {
        return d3.h(this, j);
    }

    @Override // defpackage.mm
    public final /* synthetic */ float d0(long j) {
        return d3.g(this, j);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof pm)) {
            return false;
        }
        pm pmVar = (pm) obj;
        if (Float.compare(this.e, pmVar.e) == 0 && Float.compare(this.f, pmVar.f) == 0) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.f) + (Float.floatToIntBits(this.e) * 31);
    }

    @Override // defpackage.mm
    public final long j0(float f) {
        return d3.i(o0(f), this);
    }

    @Override // defpackage.mm
    public final float o0(float f) {
        return f / B();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DensityImpl(density=");
        sb.append(this.e);
        sb.append(", fontScale=");
        return d3.v(sb, this.f, ')');
    }

    @Override // defpackage.mm
    public final float y() {
        return this.f;
    }
}
