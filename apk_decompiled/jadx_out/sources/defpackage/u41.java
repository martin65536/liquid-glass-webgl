package defpackage;

import android.view.ViewStructure;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class u41 implements s41 {
    public final Object a;

    public u41(float f, float f2, i7 i7Var) {
        j2 j2Var;
        int i = t41.a;
        if (i7Var != null) {
            j2Var = new j2(f, f2, i7Var);
        } else {
            j2Var = new j2(f, f2);
        }
        this.a = new e3(j2Var);
    }

    @Override // defpackage.s41
    public void a() {
        ((e3) this.a).getClass();
    }

    public long b(long j) {
        fm fmVar = (fm) this.a;
        fmVar.getClass();
        if (v41.b(j) <= 0.0f || v41.c(j) <= 0.0f) {
            q00.b("maximumVelocity should be a positive value. You specified=" + ((Object) v41.g(j)));
        }
        return o30.c(fmVar.a.b(v41.b(j)), fmVar.b.b(v41.c(j)));
    }

    @Override // defpackage.s41
    public i7 c(long j, i7 i7Var, i7 i7Var2, i7 i7Var3) {
        return ((e3) this.a).c(j, i7Var, i7Var2, i7Var3);
    }

    @Override // defpackage.s41
    public i7 d(long j, i7 i7Var, i7 i7Var2, i7 i7Var3) {
        return ((e3) this.a).d(j, i7Var, i7Var2, i7Var3);
    }

    @Override // defpackage.s41
    public i7 e(i7 i7Var, i7 i7Var2, i7 i7Var3) {
        return ((e3) this.a).e(i7Var, i7Var2, i7Var3);
    }

    @Override // defpackage.s41
    public long f(i7 i7Var, i7 i7Var2, i7 i7Var3) {
        return ((e3) this.a).f(i7Var, i7Var2, i7Var3);
    }

    public u41(ViewStructure viewStructure) {
        this.a = viewStructure;
    }

    public u41() {
        this.a = new fm();
    }
}
