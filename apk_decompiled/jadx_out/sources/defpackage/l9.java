package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l9 implements m9 {
    public final m9 a;
    public final kv b;
    public final boolean c;

    public l9(m9 m9Var, kv kvVar) {
        m9Var.getClass();
        kvVar.getClass();
        this.a = m9Var;
        this.b = kvVar;
        this.c = m9Var.a();
    }

    @Override // defpackage.m9
    public final boolean a() {
        return this.c;
    }

    @Override // defpackage.m9
    public final void b(up upVar, mm mmVar, l40 l40Var, gv gvVar) {
        upVar.getClass();
        mmVar.getClass();
        this.b.d(upVar, new v6(this, mmVar, l40Var, gvVar));
    }
}
