package defpackage;

import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class g50 implements qc0 {
    public final /* synthetic */ int a;
    public final /* synthetic */ int b;
    public final /* synthetic */ Map c;
    public final /* synthetic */ gv d;
    public final /* synthetic */ h50 e;
    public final /* synthetic */ n50 f;
    public final /* synthetic */ gv g;

    public g50(int i, int i2, Map map, gv gvVar, h50 h50Var, n50 n50Var, gv gvVar2) {
        this.a = i;
        this.b = i2;
        this.c = map;
        this.d = gvVar;
        this.e = h50Var;
        this.f = n50Var;
        this.g = gvVar2;
    }

    @Override // defpackage.qc0
    public final void a() {
        v00 v00Var;
        z40 z40Var = this.f.e;
        boolean D = this.e.D();
        gv gvVar = this.g;
        if (D && (v00Var = z40Var.H.c.V) != null) {
            gvVar.e(v00Var.p);
        } else {
            gvVar.e(z40Var.H.c.p);
        }
    }

    @Override // defpackage.qc0
    public final int b() {
        return this.b;
    }

    @Override // defpackage.qc0
    public final gv c() {
        return this.d;
    }

    @Override // defpackage.qc0
    public final int d() {
        return this.a;
    }

    @Override // defpackage.qc0
    public final Map r() {
        return this.c;
    }
}
