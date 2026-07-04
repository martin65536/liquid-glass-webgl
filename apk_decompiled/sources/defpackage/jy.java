package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class jy implements ky {
    public final long b = se.b(se.c, 0.38f);
    public final int c = 12;

    @Override // defpackage.ky
    public final long a() {
        return this.b;
    }

    @Override // defpackage.ky
    public final h6 b(b50 b50Var, aw0 aw0Var, sr0 sr0Var) {
        sr0Var.getClass();
        return null;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof jy) {
                jy jyVar = (jy) obj;
                if (se.c(this.b, jyVar.b) && this.c == jyVar.c) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return (se.i(this.b) * 31) + this.c;
    }

    public final String toString() {
        return "Plain(color=" + se.j(this.b) + ", blendMode=" + f31.T(this.c) + ")";
    }

    @Override // defpackage.ky
    public final int u() {
        return this.c;
    }
}
