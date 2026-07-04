package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class h7 extends i7 {
    public float a;
    public float b;
    public float c;
    public float d;

    public h7(float f, float f2, float f3, float f4) {
        this.a = f;
        this.b = f2;
        this.c = f3;
        this.d = f4;
    }

    @Override // defpackage.i7
    public final float a(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return 0.0f;
                    }
                    return this.d;
                }
                return this.c;
            }
            return this.b;
        }
        return this.a;
    }

    @Override // defpackage.i7
    public final int b() {
        return 4;
    }

    @Override // defpackage.i7
    public final i7 c() {
        return new h7(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override // defpackage.i7
    public final void d() {
        this.a = 0.0f;
        this.b = 0.0f;
        this.c = 0.0f;
        this.d = 0.0f;
    }

    @Override // defpackage.i7
    public final void e(float f, int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return;
                    }
                    this.d = f;
                    return;
                }
                this.c = f;
                return;
            }
            this.b = f;
            return;
        }
        this.a = f;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof h7) {
            h7 h7Var = (h7) obj;
            if (h7Var.a == this.a && h7Var.b == this.b && h7Var.c == this.c && h7Var.d == this.d) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.d) + d3.s(this.c, d3.s(this.b, Float.floatToIntBits(this.a) * 31, 31), 31);
    }

    public final String toString() {
        return "AnimationVector4D: v1 = " + this.a + ", v2 = " + this.b + ", v3 = " + this.c + ", v4 = " + this.d;
    }
}
