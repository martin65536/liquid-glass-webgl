package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vb implements a11 {
    public final qv0 a;
    public final float b;

    public vb(qv0 qv0Var, float f) {
        this.a = qv0Var;
        this.b = f;
    }

    @Override // defpackage.a11
    public final long a() {
        int i = se.h;
        return se.g;
    }

    @Override // defpackage.a11
    public final /* synthetic */ a11 b(a11 a11Var) {
        return d3.b(this, a11Var);
    }

    @Override // defpackage.a11
    public final a11 c(vu vuVar) {
        if (!equals(z01.a)) {
            return this;
        }
        return (a11) vuVar.a();
    }

    @Override // defpackage.a11
    public final jc0 d() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof vb)) {
            return false;
        }
        vb vbVar = (vb) obj;
        if (o20.e(this.a, vbVar.a) && Float.compare(this.b, vbVar.b) == 0) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.b) + (this.a.hashCode() * 31);
    }

    @Override // defpackage.a11
    public final float r() {
        return this.b;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BrushStyle(value=");
        sb.append(this.a);
        sb.append(", alpha=");
        return d3.v(sb, this.b, ')');
    }
}
