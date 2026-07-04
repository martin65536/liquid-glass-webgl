package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bf implements a11 {
    public final long a;

    public bf(long j) {
        this.a = j;
        if (j != 16) {
            return;
        }
        r00.a("ColorStyle value must be specified, use TextForegroundStyle.Unspecified instead.");
    }

    @Override // defpackage.a11
    public final long a() {
        return this.a;
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
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof bf) && se.c(this.a, ((bf) obj).a)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return se.i(this.a);
    }

    @Override // defpackage.a11
    public final float r() {
        return se.d(this.a);
    }

    public final String toString() {
        return "ColorStyle(value=" + ((Object) se.j(this.a)) + ')';
    }
}
