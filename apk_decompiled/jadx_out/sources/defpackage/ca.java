package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ca extends uj0 {
    public final o5 e;
    public final long f;
    public final int g = 1;
    public final long h;
    public float i;
    public te j;

    public ca(o5 o5Var) {
        int i;
        long width = (o5Var.a.getWidth() << 32) | (o5Var.a.getHeight() & 4294967295L);
        this.e = o5Var;
        this.f = width;
        int i2 = (int) (width >> 32);
        if (i2 >= 0 && (i = (int) (width & 4294967295L)) >= 0 && i2 <= o5Var.a.getWidth() && i <= o5Var.a.getHeight()) {
            this.h = width;
            this.i = 1.0f;
        } else {
            v7.m("Failed requirement.");
            throw null;
        }
    }

    @Override // defpackage.uj0
    public final void a(float f) {
        this.i = f;
    }

    @Override // defpackage.uj0
    public final void b(te teVar) {
        this.j = teVar;
    }

    @Override // defpackage.uj0
    public final long d() {
        return d20.J(this.h);
    }

    @Override // defpackage.uj0
    public final void e(b50 b50Var) {
        int round = Math.round(Float.intBitsToFloat((int) (b50Var.j() >> 32)));
        int round2 = Math.round(Float.intBitsToFloat((int) (b50Var.j() & 4294967295L)));
        d3.o(b50Var, this.e, this.f, (round << 32) | (round2 & 4294967295L), this.i, this.j, this.g, 328);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof ca) {
                ca caVar = (ca) obj;
                if (o20.e(this.e, caVar.e) && v10.a(0L, 0L) && c20.a(this.f, caVar.f) && this.g == caVar.g) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int hashCode = this.e.hashCode() * 961;
        long j = this.f;
        return ((((int) (j ^ (j >>> 32))) + hashCode) * 31) + this.g;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("BitmapPainter(image=");
        sb.append(this.e);
        sb.append(", srcOffset=");
        sb.append((Object) v10.d(0L));
        sb.append(", srcSize=");
        sb.append((Object) c20.b(this.f));
        sb.append(", filterQuality=");
        int i = this.g;
        if (i == 0) {
            str = "None";
        } else if (i == 1) {
            str = "Low";
        } else if (i == 2) {
            str = "Medium";
        } else if (i == 3) {
            str = "High";
        } else {
            str = "Unknown";
        }
        sb.append((Object) str);
        sb.append(')');
        return sb.toString();
    }
}
