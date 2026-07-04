package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rm implements mm {
    public final float e;
    public final float f;
    public final iu g;

    public rm(float f, float f2, iu iuVar) {
        this.e = f;
        this.f = f2;
        this.g = iuVar;
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
    public final float O(long j) {
        if (u11.a(t11.b(j), 4294967296L)) {
            return this.g.b(t11.c(j));
        }
        v7.o("Only Sp can convert to Px");
        return 0.0f;
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
        if (this != obj) {
            if (obj instanceof rm) {
                rm rmVar = (rm) obj;
                if (Float.compare(this.e, rmVar.e) != 0 || Float.compare(this.f, rmVar.f) != 0 || !this.g.equals(rmVar.g)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return this.g.hashCode() + d3.s(this.f, Float.floatToIntBits(this.e) * 31, 31);
    }

    @Override // defpackage.mm
    public final long j0(float f) {
        return d20.A(4294967296L, this.g.a(o0(f)));
    }

    @Override // defpackage.mm
    public final float o0(float f) {
        return f / B();
    }

    public final String toString() {
        return "DensityWithConverter(density=" + this.e + ", fontScale=" + this.f + ", converter=" + this.g + ')';
    }

    @Override // defpackage.mm
    public final float y() {
        return this.f;
    }
}
