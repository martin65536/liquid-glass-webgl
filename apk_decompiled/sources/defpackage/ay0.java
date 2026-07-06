package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ay0 implements ds {
    public final float a;
    public final float b;
    public final Object c;

    public ay0(float f, float f2, Object obj) {
        this.a = f;
        this.b = f2;
        this.c = obj;
    }

    @Override // defpackage.c7
    public final s41 c(c4 c4Var) {
        i7 i7Var;
        Object obj = this.c;
        if (obj == null) {
            i7Var = null;
        } else {
            i7Var = (i7) ((gv) c4Var.f).e(obj);
        }
        return new u41(this.a, this.b, i7Var);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ay0) {
            ay0 ay0Var = (ay0) obj;
            if (ay0Var.a == this.a && ay0Var.b == this.b && o20.e(ay0Var.c, this.c)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i;
        Object obj = this.c;
        if (obj != null) {
            i = obj.hashCode();
        } else {
            i = 0;
        }
        return Float.floatToIntBits(this.b) + d3.s(this.a, i * 31, 31);
    }
}
