package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class e7 extends i7 {
    public float a;

    public e7(float f) {
        this.a = f;
    }

    @Override // defpackage.i7
    public final float a(int i) {
        if (i == 0) {
            return this.a;
        }
        return 0.0f;
    }

    @Override // defpackage.i7
    public final int b() {
        return 1;
    }

    @Override // defpackage.i7
    public final i7 c() {
        return new e7(0.0f);
    }

    @Override // defpackage.i7
    public final void d() {
        this.a = 0.0f;
    }

    @Override // defpackage.i7
    public final void e(float f, int i) {
        if (i == 0) {
            this.a = f;
        }
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof e7) && ((e7) obj).a == this.a) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.a);
    }

    public final String toString() {
        return "AnimationVector1D: value = " + this.a;
    }
}
