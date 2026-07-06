package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gf implements m9 {
    public final m9 a;
    public final m9 b;
    public final boolean c;

    public gf(m9 m9Var, m9 m9Var2) {
        boolean z;
        m9Var.getClass();
        m9Var2.getClass();
        this.a = m9Var;
        this.b = m9Var2;
        if (!m9Var.a() && !m9Var2.a()) {
            z = false;
        } else {
            z = true;
        }
        this.c = z;
    }

    @Override // defpackage.m9
    public final boolean a() {
        return this.c;
    }

    @Override // defpackage.m9
    public final void b(up upVar, mm mmVar, l40 l40Var, gv gvVar) {
        upVar.getClass();
        mmVar.getClass();
        this.a.b(upVar, mmVar, l40Var, gvVar);
        this.b.b(upVar, mmVar, l40Var, gvVar);
    }
}
