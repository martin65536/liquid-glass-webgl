package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wo0 {
    public static final wo0 e = new wo0(0.0f, 0.0f, 0.0f, 0.0f);
    public final float a;
    public final float b;
    public final float c;
    public final float d;

    public wo0(float f, float f2, float f3, float f4) {
        this.a = f;
        this.b = f2;
        this.c = f3;
        this.d = f4;
    }

    public final long a() {
        float f = this.c;
        float f2 = this.a;
        float f3 = ((f - f2) / 2.0f) + f2;
        float f4 = this.d;
        float f5 = this.b;
        return (Float.floatToRawIntBits(((f4 - f5) / 2.0f) + f5) & 4294967295L) | (Float.floatToRawIntBits(f3) << 32);
    }

    public final long b() {
        float f = this.c - this.a;
        float f2 = this.d - this.b;
        return (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
    }

    public final wo0 c(wo0 wo0Var) {
        return new wo0(Math.max(this.a, wo0Var.a), Math.max(this.b, wo0Var.b), Math.min(this.c, wo0Var.c), Math.min(this.d, wo0Var.d));
    }

    public final wo0 d(float f, float f2) {
        return new wo0(this.a + f, this.b + f2, this.c + f, this.d + f2);
    }

    public final wo0 e(long j) {
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        return new wo0(Float.intBitsToFloat(i) + this.a, Float.intBitsToFloat(i2) + this.b, Float.intBitsToFloat(i) + this.c, Float.intBitsToFloat(i2) + this.d);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof wo0)) {
            return false;
        }
        wo0 wo0Var = (wo0) obj;
        if (Float.compare(this.a, wo0Var.a) == 0 && Float.compare(this.b, wo0Var.b) == 0 && Float.compare(this.c, wo0Var.c) == 0 && Float.compare(this.d, wo0Var.d) == 0) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.d) + d3.s(this.c, d3.s(this.b, Float.floatToIntBits(this.a) * 31, 31), 31);
    }

    public final String toString() {
        return "Rect.fromLTRB(" + o4.Z(this.a) + ", " + o4.Z(this.b) + ", " + o4.Z(this.c) + ", " + o4.Z(this.d) + ')';
    }
}
