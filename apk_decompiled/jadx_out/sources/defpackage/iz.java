package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class iz {
    public static int k;
    public static final rt l = new rt(5);
    public final String a;
    public final float b;
    public final float c;
    public final float d;
    public final float e;
    public final n41 f;
    public final long g;
    public final int h;
    public final boolean i;
    public final int j;

    public iz(String str, float f, float f2, float f3, float f4, n41 n41Var, long j, int i, boolean z) {
        int i2;
        synchronized (l) {
            i2 = k;
            k = i2 + 1;
        }
        this.a = str;
        this.b = f;
        this.c = f2;
        this.d = f3;
        this.e = f4;
        this.f = n41Var;
        this.g = j;
        this.h = i;
        this.i = z;
        this.j = i2;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof iz) {
                iz izVar = (iz) obj;
                if (o20.e(this.a, izVar.a) && eo.a(this.b, izVar.b) && eo.a(this.c, izVar.c) && this.d == izVar.d && this.e == izVar.e && this.f.equals(izVar.f) && se.c(this.g, izVar.g) && this.h == izVar.h && this.i == izVar.i) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2 = (((se.i(this.g) + ((this.f.hashCode() + d3.s(this.e, d3.s(this.d, d3.s(this.c, d3.s(this.b, this.a.hashCode() * 31, 31), 31), 31), 31)) * 31)) * 31) + this.h) * 31;
        if (this.i) {
            i = 1231;
        } else {
            i = 1237;
        }
        return i2 + i;
    }
}
