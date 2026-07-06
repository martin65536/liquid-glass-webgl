package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class f7 extends i7 {
    public float a;
    public float b;

    public f7(float f, float f2) {
        this.a = f;
        this.b = f2;
    }

    @Override // defpackage.i7
    public final float a(int i) {
        if (i != 0) {
            if (i != 1) {
                return 0.0f;
            }
            return this.b;
        }
        return this.a;
    }

    @Override // defpackage.i7
    public final int b() {
        return 2;
    }

    @Override // defpackage.i7
    public final i7 c() {
        return new f7(0.0f, 0.0f);
    }

    @Override // defpackage.i7
    public final void d() {
        this.a = 0.0f;
        this.b = 0.0f;
    }

    @Override // defpackage.i7
    public final void e(float f, int i) {
        if (i != 0) {
            if (i != 1) {
                return;
            }
            this.b = f;
            return;
        }
        this.a = f;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof f7) {
            f7 f7Var = (f7) obj;
            if (f7Var.a == this.a && f7Var.b == this.b) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.b) + (Float.floatToIntBits(this.a) * 31);
    }

    public final String toString() {
        return "AnimationVector2D: v1 = " + this.a + ", v2 = " + this.b;
    }
}
