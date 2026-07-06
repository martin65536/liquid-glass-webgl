package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gr0 {
    public final float a;
    public final float b;
    public final float c;
    public final float d;
    public final long e;
    public final long f;
    public final long g;
    public final long h;

    static {
        m20.e(0.0f, 0.0f, 0.0f, 0.0f, 0L);
    }

    public gr0(float f, float f2, float f3, float f4, long j, long j2, long j3, long j4) {
        this.a = f;
        this.b = f2;
        this.c = f3;
        this.d = f4;
        this.e = j;
        this.f = j2;
        this.g = j3;
        this.h = j4;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof gr0) {
                gr0 gr0Var = (gr0) obj;
                if (Float.compare(this.a, gr0Var.a) != 0 || Float.compare(this.b, gr0Var.b) != 0 || Float.compare(this.c, gr0Var.c) != 0 || Float.compare(this.d, gr0Var.d) != 0 || !o20.o(this.e, gr0Var.e) || !o20.o(this.f, gr0Var.f) || !o20.o(this.g, gr0Var.g) || !o20.o(this.h, gr0Var.h)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int s = d3.s(this.d, d3.s(this.c, d3.s(this.b, Float.floatToIntBits(this.a) * 31, 31), 31), 31);
        long j = this.e;
        long j2 = this.f;
        int i = (((int) (j2 ^ (j2 >>> 32))) + ((((int) (j ^ (j >>> 32))) + s) * 31)) * 31;
        long j3 = this.g;
        int i2 = (((int) (j3 ^ (j3 >>> 32))) + i) * 31;
        long j4 = this.h;
        return ((int) (j4 ^ (j4 >>> 32))) + i2;
    }

    public final String toString() {
        String str = o4.Z(this.a) + ", " + o4.Z(this.b) + ", " + o4.Z(this.c) + ", " + o4.Z(this.d);
        long j = this.e;
        long j2 = this.f;
        boolean o = o20.o(j, j2);
        long j3 = this.g;
        long j4 = this.h;
        if (o && o20.o(j2, j3) && o20.o(j3, j4)) {
            int i = (int) (j >> 32);
            int i2 = (int) (j & 4294967295L);
            if (Float.intBitsToFloat(i) == Float.intBitsToFloat(i2)) {
                return "RoundRect(rect=" + str + ", radius=" + o4.Z(Float.intBitsToFloat(i)) + ')';
            }
            return "RoundRect(rect=" + str + ", x=" + o4.Z(Float.intBitsToFloat(i)) + ", y=" + o4.Z(Float.intBitsToFloat(i2)) + ')';
        }
        return "RoundRect(rect=" + str + ", topLeft=" + ((Object) o20.J(j)) + ", topRight=" + ((Object) o20.J(j2)) + ", bottomRight=" + ((Object) o20.J(j3)) + ", bottomLeft=" + ((Object) o20.J(j4)) + ')';
    }
}
