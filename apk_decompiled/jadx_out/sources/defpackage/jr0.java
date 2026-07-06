package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class jr0 implements lr0 {
    public final float a;
    public final ir0 b = ir0.f;

    public jr0(float f) {
        this.a = f;
    }

    @Override // defpackage.lr0
    public final kr0 a(long j, m40 m40Var, np npVar) {
        m40Var.getClass();
        npVar.getClass();
        float B = npVar.B() * this.a;
        float b = mw0.b(j) * 0.5f;
        if (B < 0.0f) {
            B = 0.0f;
        }
        if (B <= b) {
            b = B;
        }
        return new kr0(b, b, b, b);
    }

    @Override // defpackage.zv0
    public final g30 b(long j, m40 m40Var, mm mmVar) {
        m40Var.getClass();
        mmVar.getClass();
        float G = mmVar.G(this.a);
        float b = mw0.b(j) * 0.5f;
        if (G < 0.0f) {
            G = 0.0f;
        }
        if (G <= b) {
            b = G;
        }
        return t20.L(j, b, this.b);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof jr0) {
                jr0 jr0Var = (jr0) obj;
                if (!eo.a(this.a, jr0Var.a) || this.b != jr0Var.b) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return this.b.hashCode() + (Float.floatToIntBits(this.a) * 31);
    }

    public final String toString() {
        return "RoundedRectangle(cornerRadius=" + eo.b(this.a) + ", style=" + this.b + ")";
    }
}
