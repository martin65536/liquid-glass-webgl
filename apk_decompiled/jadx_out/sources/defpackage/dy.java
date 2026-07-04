package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class dy {
    public static final dy e = new dy(null, 15);
    public static final dy f;
    public static final dy g;
    public final float a;
    public final float b;
    public final float c;
    public final ky d;

    static {
        ky.a.getClass();
        f = new dy(hy.c, 7);
        g = new dy(hy.d, 7);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public dy(defpackage.ky r3, int r4) {
        /*
            r2 = this;
            r4 = r4 & 8
            if (r4 == 0) goto Lb
            hy r3 = defpackage.ky.a
            r3.getClass()
            iy r3 = defpackage.hy.b
        Lb:
            r4 = 1056964608(0x3f000000, float:0.5)
            r0 = 1048576000(0x3e800000, float:0.25)
            r1 = 1065353216(0x3f800000, float:1.0)
            r2.<init>(r4, r0, r1, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dy.<init>(ky, int):void");
    }

    public static dy a(dy dyVar, float f2, float f3, float f4, int i) {
        if ((i & 1) != 0) {
            f2 = dyVar.a;
        }
        if ((i & 2) != 0) {
            f3 = dyVar.b;
        }
        ky kyVar = dyVar.d;
        dyVar.getClass();
        kyVar.getClass();
        return new dy(f2, f3, f4, kyVar);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof dy) {
                dy dyVar = (dy) obj;
                if (!eo.a(this.a, dyVar.a) || !eo.a(this.b, dyVar.b) || Float.compare(this.c, dyVar.c) != 0 || !o20.e(this.d, dyVar.d)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return this.d.hashCode() + d3.s(this.c, d3.s(this.b, Float.floatToIntBits(this.a) * 31, 31), 31);
    }

    public final String toString() {
        return "Highlight(width=" + eo.b(this.a) + ", blurRadius=" + eo.b(this.b) + ", alpha=" + this.c + ", style=" + this.d + ")";
    }

    public dy(float f2, float f3, float f4, ky kyVar) {
        kyVar.getClass();
        this.a = f2;
        this.b = f3;
        this.c = f4;
        this.d = kyVar;
    }
}
